package com.honvay.cola.sc.provider;

import com.honvay.cola.sc.api.CredentialService;
import com.honvay.cola.sc.api.model.CredentialConfig;
import com.honvay.cola.sc.api.model.CredentialType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

/**
 * @author LIQIU
 * created on 2018/12/28
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestContext.class)
public class CredentialServiceImplTest {


	@Autowired
	private CredentialService credentialService;

	@Test
	public void generate() {
		CredentialConfig config = CredentialConfig.builder()
				.principal("123")
				.size(6)
				.type(CredentialType.NUMBER)
				.expire(Duration.ofMinutes(5L).toMillis())
				.application("sign_up").build();
		String token = credentialService.generate(config);
		log.info("Generated token : " + token);

		String token2 = credentialService.generate(config);
		Assert.assertEquals(token, token2);
	}
}