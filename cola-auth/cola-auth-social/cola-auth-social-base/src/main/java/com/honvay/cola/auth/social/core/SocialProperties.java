package com.honvay.cola.auth.social.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * created on 2018-11-14
 **/
@Data
@ConfigurationProperties(prefix = "spring.social")
public class SocialProperties {

	private String loginProcessUrl = "/social";

	private String signupUrl = "/social/signup";

	private String signupProcessUrl = "/social/signup";

}
