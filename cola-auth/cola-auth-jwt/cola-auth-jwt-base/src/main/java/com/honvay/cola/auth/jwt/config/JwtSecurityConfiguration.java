package com.honvay.cola.auth.jwt.config;

import com.honvay.cola.auth.channel.config.ChannelSecurityConfigurer;
import com.honvay.cola.auth.jwt.DefaultJwtTokenStore;
import com.honvay.cola.auth.jwt.JwtProperties;
import com.honvay.cola.auth.jwt.JwtTokenProvider;
import com.honvay.cola.auth.jwt.JwtTokenStore;
import com.honvay.cola.auth.jwt.filter.JwtAuthenticationFilter;
import com.honvay.cola.auth.jwt.handler.JwtAuthenticationEntryPoint;
import com.honvay.cola.auth.jwt.handler.JwtAuthenticationFailureHandler;
import com.honvay.cola.auth.jwt.handler.JwtAuthenticationSuccessHandler;
import com.honvay.cola.auth.jwt.handler.JwtLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author LIQIU
 * created on 2018/12/26
 **/
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtSecurityConfiguration implements ChannelSecurityConfigurer {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Bean
	public JwtTokenStore jwtTokenStore() {
		return new DefaultJwtTokenStore();
	}

	@Bean
	public JwtAuthenticationFailureHandler failureHandler() {
		return new JwtAuthenticationFailureHandler();
	}

	@Bean
	public JwtAuthenticationSuccessHandler successHandler() {
		return new JwtAuthenticationSuccessHandler(jwtTokenProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
				.antMatchers("/login", "/logout", "/error").permitAll()
				.and()
				.formLogin()
				.loginProcessingUrl("/login")
				.failureHandler(this.failureHandler())
				.successHandler(this.successHandler())
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessHandler(new JwtLogoutSuccessHandler())
				.and()
				.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterAfter(this.jwtAuthenticationFilter, SecurityContextPersistenceFilter.class);
	}


	@Override
	public int getOrder() {
		return 0;
	}


}
