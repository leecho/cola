package com.honvay.cola.sc.provider.credential;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @author LIQIU
 * created on 2018/12/25
 **/
@Data
@ConfigurationProperties(prefix = "cola.security.credential")
public class CredentialProperties {

	private Duration notifyInterval = Duration.ofMinutes(1L);

	private String credentialPlaceholder = "credential";

}
