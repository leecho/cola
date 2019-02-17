package com.honvay.cola.auth.web.controller;

import com.honvay.cola.framework.core.protocol.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LIQIU
 * created on 2018-11-25
 **/
@Slf4j
@Controller
@RequestMapping("/session-invalid")
public class SessionInvalidController {

	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public View sessionInvalidPage(HttpServletRequest request) {
		log.info(request.getRequestURI());
		log.info(request.getHeader("Refer"));
		return new RedirectView("/login?session");
	}

	@ResponseBody
	@RequestMapping
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Result<String> sessionInvalid() {
		return Result.success("会话已失效");
	}

}
