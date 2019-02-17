package com.honvay.cola.auth.social.alipay.connect;

import com.honvay.cola.auth.social.alipay.AlipayProperties;
import com.honvay.cola.auth.social.alipay.api.Alipay;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author LIQIU
 */
public class AlipayConnectionFactory extends OAuth2ConnectionFactory<Alipay> {
    @Override
    public boolean supportsStateParameter() {
        return false;
    }

    public AlipayConnectionFactory(AlipayProperties properties) {
        super(properties.getProviderId(), new AlipayServiceProvider(properties), new AlipayAdapater());
    }
}