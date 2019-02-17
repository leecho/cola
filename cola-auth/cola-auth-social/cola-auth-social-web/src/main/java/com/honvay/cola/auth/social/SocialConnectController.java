package com.honvay.cola.auth.social;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LIQIU
 * created on 2018-11-28
 **/
public class SocialConnectController extends ConnectController {

	/**
	 * Constructs a ConnectController.
	 *
	 * @param connectionRepository     the current user's {@link ConnectionRepository} needed to persist connections; must be a proxy to a request-scoped bean
	 */
	public SocialConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
		super(connectionFactoryLocator, connectionRepository);
	}

	@RequestMapping(value="/{providerId}", method= RequestMethod.GET, params="auth_code")
	public RedirectView alipayOauth2Callback(@PathVariable String providerId, NativeWebRequest request) {
		return super.oauth2Callback(providerId,new AlipayOAuth2RequestWrapper((HttpServletRequest)request.getNativeRequest()));
	}

	public static class AlipayOAuth2RequestWrapper extends ServletWebRequest {

		private HttpServletRequest request;

		public AlipayOAuth2RequestWrapper(HttpServletRequest request) {
			super(request);
			this.request = request;
		}


		@Override
		public String getParameter(String s) {
			if("credential".equals(s)){
				return request.getParameter("auth_code");
			}
			return super.getParameter(s);
		}

	}
}
