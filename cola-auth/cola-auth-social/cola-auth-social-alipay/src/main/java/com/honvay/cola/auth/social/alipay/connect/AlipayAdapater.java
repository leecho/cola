package com.honvay.cola.auth.social.alipay.connect;

import com.honvay.cola.auth.social.alipay.api.Alipay;
import com.honvay.cola.auth.social.alipay.api.AlipayUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author LIQIU
 * created on 2018-11-26
 **/
public class AlipayAdapater  implements ApiAdapter<Alipay> {
	@Override
	public boolean test(Alipay api) {
		return true;
	}

	@Override
	public void setConnectionValues(Alipay api, ConnectionValues values) {
		AlipayUserInfo alipayUserInfo = api.getUserInfo();
		values.setDisplayName(alipayUserInfo.getNickName());
		values.setImageUrl(alipayUserInfo.getAvatar());
		values.setProviderUserId(alipayUserInfo.getUserId());
	}

	@Override
	public UserProfile fetchUserProfile(Alipay api) {
		return null;
	}

	@Override
	public void updateStatus(Alipay api, String message) {

	}
}
