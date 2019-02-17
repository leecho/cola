package com.honvay.cola.auth.social.wechatmp.connect;

import com.honvay.cola.auth.social.wechatmp.api.WechatMp;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信连接工厂
 * Created on 2018/1/11.
 *
 * @author LIQIU
 * @since 1.0
 */
public class WechatMpConnectionFactory extends OAuth2ConnectionFactory<WechatMp> {

    /**
     * @param appId
     * @param appSecret
     */
    public WechatMpConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new WechatMpServiceProvider(appId, appSecret), new WechatMpAdapter());
    }

    /**
     * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
     */
    @Override
    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof WechatMpAccessGrant) {
            return ((WechatMpAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }


    @Override
    public Connection<WechatMp> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
    }

    @Override
    public Connection<WechatMp> createConnection(ConnectionData data) {
        return new OAuth2Connection<>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
    }

    private ApiAdapter<WechatMp> getApiAdapter(String providerUserId) {
        return new WechatMpAdapter(providerUserId);
    }

    private OAuth2ServiceProvider<WechatMp> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WechatMp>) getServiceProvider();
    }

}
