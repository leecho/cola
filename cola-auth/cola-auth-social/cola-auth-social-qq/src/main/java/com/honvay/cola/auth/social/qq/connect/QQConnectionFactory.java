package com.honvay.cola.auth.social.qq.connect;

import com.honvay.cola.auth.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author LIQIU
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {
    @Override
    public boolean supportsStateParameter() {
        return false;
    }

    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }
}