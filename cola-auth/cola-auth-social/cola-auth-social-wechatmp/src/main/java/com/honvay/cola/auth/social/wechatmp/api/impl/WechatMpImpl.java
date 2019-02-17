package com.honvay.cola.auth.social.wechatmp.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.honvay.cola.auth.social.wechatmp.api.WechatMp;
import com.honvay.cola.auth.social.wechatmp.api.WechatMpUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created on 2018/1/11.
 *
 * @author zlf
 * @since 1.0
 */
public class WechatMpImpl extends AbstractOAuth2ApiBinding implements WechatMp {

    /**
     * 获取用户信息的url
     */
    private static final String WEIXIN_URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    private ObjectMapper objectMapper = new ObjectMapper();

    public WechatMpImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 获取用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public WechatMpUserInfo getUserInfo(String openId) {
        String url = WEIXIN_URL_GET_USER_INFO + openId;

        String result = getRestTemplate().getForObject(url, String.class);
        if(StringUtils.contains(result, "errcode")) {
            return null;
        }

        WechatMpUserInfo userInfo = null;

        try{
            userInfo = objectMapper.readValue(result, WechatMpUserInfo.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        return userInfo;
    }

    /**
     * 使用utf-8 替换默认的ISO-8859-1编码
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return messageConverters;
    }
}
