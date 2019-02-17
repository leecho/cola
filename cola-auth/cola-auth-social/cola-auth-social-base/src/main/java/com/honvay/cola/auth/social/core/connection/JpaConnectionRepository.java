package com.honvay.cola.auth.social.core.connection;

import com.honvay.cola.auth.social.core.domain.UserConnection;
import com.honvay.cola.auth.social.core.repository.UserConnectionRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * created on 2018-11-29
 **/
public class JpaConnectionRepository implements ConnectionRepository {

	private final String userId;

	private final UserConnectionRepository userConnectionRepository;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;


	public JpaConnectionRepository(String userId, UserConnectionRepository userConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		this.userId = userId;
		this.userConnectionRepository = userConnectionRepository;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {

		List<UserConnection> userConnections = this.userConnectionRepository.findAll();
		List<Connection<?>> resultList = userConnections.stream().map(connectionMapper).collect(Collectors.toList());

		MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
		Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		for (String registeredProviderId : registeredProviderIds) {
			connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
		}
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			if (Objects.requireNonNull(connections.get(providerId)).isEmpty()) {
				connections.put(providerId, new LinkedList<>());
			}
			connections.add(providerId, connection);
		}
		return connections;
	}

	@Override
	public List<Connection<?>> findConnections(String providerId) {
		List<UserConnection> userConnections = this.userConnectionRepository.findByUserIdAndProviderId(userId, providerId);
		return userConnections.stream().map(connectionMapper).collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		List<?> connections = findConnections(getProviderId(apiType));
		return (List<Connection<A>>) connections;
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
		if (providerUsers == null || providerUsers.isEmpty()) {
			throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
		}

		List<Connection<?>> resultList = new ArrayList<>();

		for (Iterator<Map.Entry<String, List<String>>> it = providerUsers.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, List<String>> entry = it.next();
			resultList.addAll(this.userConnectionRepository.findByUserIdEqualsAndProviderIdEqualsAndProviderUserIdIsIn(userId, entry.getKey(),
					entry.getValue()).stream().map(connectionMapper).collect(Collectors.toList()));
		}


		MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<>();
		for (Connection<?> connection : resultList) {
			String providerId = connection.getKey().getProviderId();
			List<String> userIds = providerUsers.get(providerId);
			List<Connection<?>> connections = connectionsForUsers.get(providerId);
			if (connections == null) {
				connections = new ArrayList<Connection<?>>(userIds.size());
				for (int i = 0; i < userIds.size(); i++) {
					connections.add(null);
				}
				connectionsForUsers.put(providerId, connections);
			}
			String providerUserId = connection.getKey().getProviderUserId();
			int connectionIndex = userIds.indexOf(providerUserId);
			connections.set(connectionIndex, connection);
		}
		return connectionsForUsers;
	}

	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		UserConnection userConnection = this.userConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
		if (userConnection == null) {
			throw new NoSuchConnectionException(connectionKey);
		}
		return connectionMapper.apply(userConnection);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
		if (connection == null) {
			throw new NotConnectedException(providerId);
		}
		return connection;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		String providerId = getProviderId(apiType);
		return (Connection<A>) findPrimaryConnection(providerId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addConnection(Connection<?> connection) {
		try {
			ConnectionData data = connection.createData();
			UserConnection userConnection = new UserConnection();
			userConnection.setProviderUserName(data.getDisplayName());
			userConnection.setImageUrl(data.getImageUrl());
			userConnection.setAccessToken(encrypt(data.getAccessToken()));
			userConnection.setSecret(encrypt(data.getSecret()));
			userConnection.setRefreshToken(encrypt(data.getRefreshToken()));
			userConnection.setProviderId(data.getProviderId());
			userConnection.setProviderUserId(data.getProviderUserId());
			userConnection.setExpireTime(data.getExpireTime());
			userConnection.setUserId(userId);
			this.userConnectionRepository.save(userConnection);
		} catch (DuplicateKeyException e) {
			throw new DuplicateConnectionException(connection.getKey());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateConnection(Connection<?> connection) {

		ConnectionData data = connection.createData();
		UserConnection userConnection = this.userConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId, data.getProviderId(), data.getProviderUserId());

		Assert.notNull(userConnection, "User connection not found");

		userConnection.setProviderUserName(data.getDisplayName());
		userConnection.setImageUrl(data.getImageUrl());
		userConnection.setAccessToken(encrypt(data.getAccessToken()));
		userConnection.setSecret(encrypt(data.getSecret()));
		userConnection.setRefreshToken(encrypt(data.getRefreshToken()));
		userConnection.setProviderId(data.getProviderId());
		userConnection.setProviderUserId(data.getProviderUserId());
		userConnection.setExpireTime(data.getExpireTime());
		userConnection.setUserId(userId);
		this.userConnectionRepository.save(userConnection);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeConnections(String providerId) {
		this.userConnectionRepository.deleteByUserIdAndProviderId(userId, providerId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeConnection(ConnectionKey connectionKey) {
		this.userConnectionRepository.deleteByUserIdAndProviderIdAndProviderUserId(userId, connectionKey.getProviderId(), connectionKey.getProviderUserId());
	}

	private Connection<?> findPrimaryConnection(String providerId) {
		List<Connection<?>> connections = this.userConnectionRepository.findByUserIdAndProviderId(userId, providerId).stream().map(connectionMapper).collect(Collectors.toList());
		if (connections.size() > 0) {
			return connections.get(0);
		} else {
			return null;
		}
	}

	private final JpaConnectionRepository.ServiceProviderConnectionMapper connectionMapper = new JpaConnectionRepository.ServiceProviderConnectionMapper();

	private final class ServiceProviderConnectionMapper implements Function<UserConnection, Connection<?>> {

		private String decrypt(String encryptedText) {
			return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
		}


		@Override
		public Connection<?> apply(UserConnection userConnection) {
			ConnectionData connectionData = new ConnectionData(userConnection.getProviderId(), userConnection.getProviderUserId(),
					userConnection.getProviderUserName(), null, userConnection.getImageUrl(),
					decrypt(userConnection.getAccessToken()), decrypt(userConnection.getSecret()), userConnection.getRefreshToken(), userConnection.getExpireTime());

			ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
			return connectionFactory.createConnection(connectionData);
		}
	}

	private <A> String getProviderId(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
	}

	private String encrypt(String text) {
		return text != null ? textEncryptor.encrypt(text) : text;
	}

}
