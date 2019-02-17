package com.honvay.cola.auth.social.alipay.connect;

import com.honvay.cola.auth.social.alipay.api.Alipay;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.provider.OAuth2AuthenticationService;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
public class AlipayOAuth2AuthenticationService extends OAuth2AuthenticationService<Alipay> {

	public AlipayOAuth2AuthenticationService(OAuth2ConnectionFactory<Alipay> connectionFactory) {
		super(connectionFactory);
	}

	private String defaultScope;


	@Override
	public SocialAuthenticationToken getAuthToken(HttpServletRequest request, HttpServletResponse response) throws SocialAuthenticationRedirectException {
		return super.getAuthToken(new AlipayOAuth2RequestWrapper(request), response);
	}

	private static class AlipayOAuth2RequestWrapper extends HttpServletRequestWrapper {

		public AlipayOAuth2RequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			//转换参数，支付宝的返回参数是auth_code
			if ("code".equals(name)) {
				return this.getRequest().getParameter("auth_code");
			}
			return super.getParameter(name);
		}
	}

}
