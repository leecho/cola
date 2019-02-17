package com.honvay.cola.auth.social.wechatmp.connect;

import com.honvay.cola.auth.social.wechatmp.api.WechatMp;
import com.honvay.cola.auth.social.wechatmp.api.WechatMpUserInfo;
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
public class WechatMpAdapter implements ApiAdapter<WechatMp> {

    private String openId;

    public WechatMpAdapter() {
    }

    public WechatMpAdapter(String openId) {
        this.openId = openId;
    }

    @Override
    public boolean test(WechatMp api) {
        return true;
    }

    @Override
    public void setConnectionValues(WechatMp api, ConnectionValues values) {
        WechatMpUserInfo userInfo = api.getUserInfo(openId);
        values.setProviderUserId(userInfo.getUnionid());
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WechatMp api) {
        return null;
    }

    @Override
    public void updateStatus(WechatMp api, String message) {

    }
}
