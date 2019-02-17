package com.honvay.cola.auth.captcha.controller;

import com.honvay.cola.auth.captcha.filter.CaptchaAuthenticationFilter;
import com.honvay.cola.framework.util.ImageUtils;
import com.honvay.cola.framework.util.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LIQIU
 * created on 2018/12/26
 **/
@Controller
public class CaptchaController {

	@RequestMapping("/captcha")
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String code = RandomUtils.generateString(4);
		request.getSession().setAttribute(CaptchaAuthenticationFilter.LOGIN_CAPTCHA_SESSION_KEY, code);
		ImageUtils.outputImage(200, 60, response.getOutputStream(), code);
	}

}
