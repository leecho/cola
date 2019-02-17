package com.honvay.cola.auth.social.alipay.connect;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.honvay.cola.auth.social.alipay.AlipayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

/**
 * @author LIQIU
 */
@Slf4j
public class AlipayOAuth2Template extends OAuth2Template {

	private AlipayProperties properties;

	public AlipayOAuth2Template(AlipayProperties alipayProperties, String authorizeUrl, String accessTokenUrl) {
		super(alipayProperties.getAppId(), alipayProperties.getAppSecret(), authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
		this.properties = alipayProperties;
	}

	@Override
	public String buildAuthorizeUrl(OAuth2Parameters parameters) {
		String url = super.buildAuthorizeUrl(parameters);
		url = url.replace("client_id=", "app_id=");
		return url;
	}

	@Override
	public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
		String url = super.buildAuthorizeUrl(grantType, parameters);
		url = url.replace("client_id=", "app_id=");
		return url;
	}


	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", properties.getAppId(), properties.getPrivateKey(), properties.getPrivateKey(), properties.getCharset(), properties.getPublicKey(), properties.getSignType());
		AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
		request.setCode(parameters.getFirst("credential"));
		request.setGrantType("authorization_code");
		try {
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
			if (oauthTokenResponse.isSuccess()) {
				return new AccessGrant(oauthTokenResponse.getAccessToken(), null, oauthTokenResponse.getRefreshToken(), Long.valueOf(oauthTokenResponse.getExpiresIn()));
			}else{
				throw new IllegalArgumentException(oauthTokenResponse.getCode() + ":" + oauthTokenResponse.getMsg());
			}
		} catch (AlipayApiException e) {
			//处理异常
			throw new IllegalArgumentException(e.getMessage());
		}

	}

}