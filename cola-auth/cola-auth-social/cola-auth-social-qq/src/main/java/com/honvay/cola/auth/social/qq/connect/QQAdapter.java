package com.honvay.cola.auth.social.qq.connect;

import com.honvay.cola.auth.social.qq.api.QQ;
import com.honvay.cola.auth.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author LIQIU
 */
public class QQAdapter implements ApiAdapter<QQ> {
	@Override
	public boolean test(QQ api) {
		return true;
	}

	@Override
	public void setConnectionValues(QQ api, ConnectionValues values) {
		QQUserInfo userInfo = api.getUserInfo();

		//openid 唯一标识
		values.setProviderUserId(userInfo.getOpenId());
		values.setDisplayName(userInfo.getNickname());
		values.setImageUrl(userInfo.getFigureurl_2());
		values.setProfileUrl(null);
	}

	@Override
	public UserProfile fetchUserProfile(QQ api) {
		return null;
	}

	@Override
	public void updateStatus(QQ api, String message) {

	}
}