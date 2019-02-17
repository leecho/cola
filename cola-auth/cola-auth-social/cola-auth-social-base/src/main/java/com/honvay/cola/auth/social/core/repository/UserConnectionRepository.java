package com.honvay.cola.auth.social.core.repository;

import com.honvay.cola.auth.social.core.domain.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author LIQIU
 * created on 2018-11-29
 **/
public interface UserConnectionRepository extends JpaRepository<UserConnection, Integer> {

	/**
	 * @param providerId
	 * @param providerUserId
	 * @return
	 */
	List<UserConnection> findByProviderIdAndProviderUserId(String providerId, String providerUserId);

	/**
	 * @param userId
	 * @param providerId
	 * @return
	 */
	List<UserConnection> findByUserIdAndProviderId(String userId, String providerId);

	/**
	 * @param userId
	 * @param providerId
	 */
	void deleteByUserIdAndProviderId(String userId, String providerId);

	/**
	 * @param userId
	 * @param providerId
	 * @param providerUserId
	 */
	void deleteByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

	/**
	 * @param userId
	 * @param providerId
	 * @param providerUserId
	 * @return
	 */
	UserConnection findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

	/**
	 * @param providerId
	 * @param providerUserIds
	 * @return
	 */
	List<UserConnection> findByProviderIdEqualsAndProviderUserIdIsIn(String providerId, Set<String> providerUserIds);

	/**
	 * @param userId
	 * @param providerId
	 * @param providerUserIds
	 * @return
	 */
	List<UserConnection> findByUserIdEqualsAndProviderIdEqualsAndProviderUserIdIsIn(String userId, String providerId, List<String> providerUserIds);
}
