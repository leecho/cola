package com.honvay.cola.auth.social.core.connection;

import com.honvay.cola.auth.social.core.domain.UserConnection;
import com.honvay.cola.auth.social.core.repository.UserConnectionRepository;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * created on 2018-11-29
 **/
public class JpaUsersConnectRepository implements UsersConnectionRepository {

	private final UserConnectionRepository userConnectionRepository;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	private ConnectionSignUp connectionSignUp;

	public JpaUsersConnectRepository(UserConnectionRepository userConnectionRepository, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		this.userConnectionRepository = userConnectionRepository;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	@Override
	public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
		this.connectionSignUp = connectionSignUp;
	}

	/**
	 * Sets a table name prefix. This will be prefixed to all the table names before queries are executed. Defaults to "".
	 * This is can be used to qualify the table name with a schema or to distinguish Spring Social tables from other application tables.
	 */

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		ConnectionKey key = connection.getKey();
		List<UserConnection> connections = this.userConnectionRepository.findByProviderIdAndProviderUserId(key.getProviderId(),key.getProviderUserId());
		List<String> localUserIds =connections.stream().map(UserConnection::getUserId).collect(Collectors.toList());
		if (localUserIds.size() == 0 && connectionSignUp != null) {
			String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null){
				createConnectionRepository(newUserId).addConnection(connection);
				return Arrays.asList(newUserId);
			}
		}
		return localUserIds;
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		 List<UserConnection> connections = this.userConnectionRepository.findByProviderIdEqualsAndProviderUserIdIsIn(providerId,providerUserIds);
		return connections.stream().map(UserConnection::getUserId).collect(Collectors.toSet());
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId cannot be null");
		}
		return new JpaConnectionRepository(userId, userConnectionRepository, connectionFactoryLocator, textEncryptor);
	}
}
