package com.honvay.cola.uc.web.controller;

import com.honvay.cola.framework.core.protocol.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author LIQIU
 * created on 2018/12/28
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsernamePasswordSignUpDtoControllerTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate template;

	@Test
	public void sendRegisterSms() {

		ResponseEntity<Result> responseEntity = template.getForEntity("http://localhost:" + port + "/signp/sendSignUpSms", Result.class);

		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Assert.assertTrue(responseEntity.getBody().getSuccess());

		log.debug("Sms token:" + responseEntity.getBody().getData());

	}

	@Test
	public void registerBySms() {
	}
}