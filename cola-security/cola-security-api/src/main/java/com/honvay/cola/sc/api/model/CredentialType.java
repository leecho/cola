package com.honvay.cola.sc.api.model;

import java.io.Serializable;

/**
 * @author LIQIU
 * @date 2018-7-10
 **/
public enum CredentialType implements Serializable {

    /**
     * 全数字验证码
     */
    NUMBER,

    /**
     * 数字英文随机验证码
     */
    NORMAL;

}
