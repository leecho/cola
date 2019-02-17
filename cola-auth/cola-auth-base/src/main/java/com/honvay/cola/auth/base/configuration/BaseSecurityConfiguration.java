package com.honvay.cola.auth.base.configuration;

import com.honvay.cola.auth.channel.config.ChannelSecurityConfigurer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Comparator;
import java.util.List;

/**
 * @author LIQIU
 */
@EnableWebSecurity
@Configuration
@Order(99)
public class BaseSecurityConfiguration extends WebSecurityConfigurerAdapter implements InitializingBean {

	@Autowired(required = false)
	private List<ChannelSecurityConfigurer> configurers;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		if (configurers != null) {
			for (ChannelSecurityConfigurer configurer : configurers) {
				configurer.configure(web);
			}
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (configurers != null) {
			for (ChannelSecurityConfigurer configurer : configurers) {
				configurer.configure(http);
			}
		}
		http.userDetailsService(userDetailsService).authorizeRequests().antMatchers("/**").authenticated();
		http.getSharedObject(AuthenticationManagerBuilder.class).authenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationEventPublisher(new DefaultAuthenticationEventPublisher(applicationEventPublisher))
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	@Override
	public void afterPropertiesSet() {
		if (configurers != null) {
			configurers.sort(Comparator.comparingInt(Ordered::getOrder));
		}
	}
}
