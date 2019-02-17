package com.honvay.cola.auth.social.view;

import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author LIQIU
 */
public class SocialConnectView extends AbstractView {
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String msg = "";
		response.setContentType("text/html;charset=UTF-8");
		if (model.get("connections") == null) {
			msg = "unBindingSuccess";
		} else {
			msg = "bindingSuccess";
		}

		response.sendRedirect("/message/" + msg);
	}
}