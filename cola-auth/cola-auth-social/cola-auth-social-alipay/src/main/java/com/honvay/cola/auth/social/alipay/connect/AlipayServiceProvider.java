package com.honvay.cola.auth.social.alipay.connect;

import com.honvay.cola.auth.social.alipay.AlipayProperties;
import com.honvay.cola.auth.social.alipay.api.Alipay;
import com.honvay.cola.auth.social.alipay.api.impl.AlipayImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author LIQIU
 */
public class AlipayServiceProvider extends AbstractOAuth2ServiceProvider<Alipay> {

	/**
	 * 获取code
	 */
	private static final String ALIPAY_URL_AUTHORIZE = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";
	/**
	 * 获取access_token 也就是令牌
	 */
	private static final String ALIPAY_URL_ACCESS_TOKEN = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm";

	private AlipayProperties properties;

	public AlipayServiceProvider(AlipayProperties properties) {
		super(new AlipayOAuth2Template(properties, ALIPAY_URL_AUTHORIZE, ALIPAY_URL_ACCESS_TOKEN));
		this.properties = properties;
	}

	@Override
	public Alipay getApi(String accessToken) {

		return new AlipayImpl(accessToken, properties);
	}
}