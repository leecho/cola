package com.honvay.cola.auth.social.wechat.connect;

import com.honvay.cola.auth.social.wechat.api.Wechat;
import com.honvay.cola.auth.social.wechat.api.impl.WechatImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 * Created on 2018/1/11.
 *
 * @author zlf
 * @since 1.0
 */
public class WechatServiceProvider extends AbstractOAuth2ServiceProvider<Wechat> {

	/**
	 * 微信获取授权码的url
	 */
	private static final String WEIXIN_URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
	/**
	 * 微信获取accessToken的url(微信在获取accessToken时也已经返回openId)
	 */
	private static final String WEIXIN_URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

	public WechatServiceProvider(String appId, String appSecret) {
		super(new WechatOAuth2Template(appId, appSecret, WEIXIN_URL_AUTHORIZE, WEIXIN_URL_ACCESS_TOKEN));
	}

	public WechatServiceProvider(String appId, String appSecret, String authorizeUrl) {
		super(new WechatOAuth2Template(appId, appSecret, authorizeUrl, WEIXIN_URL_ACCESS_TOKEN));
	}

	@Override
	public Wechat getApi(String accessToken) {
		return new WechatImpl(accessToken);
	}
}
