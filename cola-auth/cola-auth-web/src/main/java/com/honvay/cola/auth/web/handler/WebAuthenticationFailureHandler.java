package com.honvay.cola.auth.web.handler;

import com.honvay.cola.auth.web.WebAuthenticationConstant;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LIQIU
 * created on 2018-11-24
 **/
public class WebAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


	public WebAuthenticationFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		request.getSession().setAttribute(WebAuthenticationConstant.AUTHENTICATION_FAIL_MESSAGE_KEY, exception.getMessage());
		request.getSession().setAttribute(WebAuthenticationConstant.CAPTCHA_AUTHENTICATION_REQUIRED_KEY, true);
		super.onAuthenticationFailure(request, response, exception);
	}
}
