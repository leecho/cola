package com.honvay.cola.sample.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author LIQIU
 * created on 2018-11-22
 **/
@Controller
public class IndexController {


	@Autowired
	private ConnectionRepository connectionRepository;

	@RequestMapping("/")
	public ModelAndView root(Map<String, Object> model, Principal principal) {

		MultiValueMap<String, Connection<?>> connections = connectionRepository.findAllConnections();
		List<Map<String, Object>> connectionMap = connections.entrySet().stream().map(entry -> {
			Map<String, Object> connection = new HashMap<>();
			connection.put("provider", entry.getKey());
			if (entry.getValue().isEmpty()) {
				connection.put("connected", false);
				connection.put("displayName", "");
			} else {
				connection.put("connected", true);
				connection.put("displayName", entry.getValue().get(0).getDisplayName());
			}
			return connection;
		}).collect(Collectors.toList());
		model.put("connections", connectionMap);
		model.put("name", principal.getName());
		return new ModelAndView("index", model);

	}
}
