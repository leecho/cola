package com.honvay.cola.sample.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
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
	private JdbcClientDetailsService clientDetailsService;

	@Autowired
	private ConnectionRepository connectionRepository;

	@RequestMapping("/")
	public ModelAndView root(Map<String, Object> model, Principal principal) {

		List<Approval> approvals = clientDetailsService.listClientDetails().stream()
				.map(clientDetails -> approvalStore.getApprovals(principal.getName(), clientDetails.getClientId()))
				.flatMap(Collection::stream)
				.collect(Collectors.groupingBy(Approval::getClientId)).entrySet().stream().map((entry) -> {
					//合并
					Optional<Approval> sum = entry.getValue().stream().reduce((v1, v2) -> {
						v1.setScope(v1.getScope() + "," + v2.getScope());
						return v1;
					});
					return sum.get();
				}).collect(Collectors.toList());
		model.put("approvals", approvals);

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

	@Autowired
	private ApprovalStore approvalStore;

	@Autowired
	private TokenStore tokenStore;

	@RequestMapping(value = "/approval/revoke", method = RequestMethod.POST)
	public String revokeApproval(@ModelAttribute Approval approval) {

		approvalStore.revokeApprovals(asList(approval));
		tokenStore.findTokensByClientIdAndUserName(approval.getClientId(), approval.getUserId())
				.forEach(tokenStore::removeAccessToken);
		return "redirect:/";
	}

}
