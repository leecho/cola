package com.honvay.cola.auth.social.view;

import com.honvay.cola.framework.core.protocol.Result;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LIQIU
 * created on 2018-11-24
 **/
@Controller
@RequestMapping("/connect/status")
public class SocialConnectStatusController {

	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public String showStatusPage() {
		return "/connect/status";
	}

	@RequestMapping
	public ResponseEntity<Result> showStatus() {
		return ResponseEntity.ok(Result.success());
	}
}
