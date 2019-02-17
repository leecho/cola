package com.honvay.cola.auth.social.wechat.connect;

import com.honvay.cola.auth.social.wechat.api.Wechat;
import com.honvay.cola.auth.social.wechat.api.WechatUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 * Created on 2018/1/11.
 *
 * @author LIQIU
 * @since 1.0
 */
public class WechatAdapter implements ApiAdapter<Wechat> {

    private String openId;

    public WechatAdapter() {
    }

    public WechatAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(Wechat api) {
        return true;
    }

    @Override
    public void setConnectionValues(Wechat api, ConnectionValues values) {
        WechatUserInfo userInfo = api.getUserInfo(openId);
        values.setProviderUserId(userInfo.getUnionid());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(Wechat api) {
        return null;
    }

    @Override
    public void updateStatus(Wechat api, String message) {

    }
}
