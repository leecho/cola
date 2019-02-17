package com.honvay.cola.auth.social.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author LIQIU
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cola_user_connection",
		uniqueConstraints = {
				@UniqueConstraint(name = "uniq_user_connection", columnNames = {"userId", "providerId", "providerUserId"})
		})
public class UserConnection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String providerUserId;

	@Column(nullable = false)
	private String providerId;

	private String providerUserName;

	private String imageUrl;

	private String secret;

	private String accessToken;

	private String refreshToken;

	private Long expireTime;

}
