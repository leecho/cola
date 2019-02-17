package com.honvay.cola.sample.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author LIQIU
 */
@EnableJpaRepositories("com.honvay")
@EntityScan("com.honvay")
@ComponentScan("com.honvay")
@SpringBootApplication
public class SocialSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialSampleApplication.class, args);
	}
}
