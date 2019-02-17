package com.honvay.cola.nc.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-3-27
 **/
@Data
public class Notification implements Serializable {

	private Map<String, Object> params;

	private String receiver;

}
