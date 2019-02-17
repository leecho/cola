package com.honvay.cola.auth.jwt.filter;

import com.honvay.cola.auth.jwt.JwtProperties;
import com.honvay.cola.auth.jwt.JwtTokenProvider;
import com.honvay.cola.auth.jwt.JwtTokenStore;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LIQIU
 * created on 2018/12/26
 **/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenStore jwtTokenStore;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	JwtProperties properties;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && jwtTokenProvider.validate(jwt)) {

				Claims claims = jwtTokenProvider.parse(jwt);

				Authentication authentication = jwtTokenStore.get(claims.getSubject());

				if (System.currentTimeMillis() - claims.getExpiration().getTime() < properties.getExpiration().toMillis()) {
					response.addHeader(properties.getRefreshTokenHeader(), jwtTokenProvider.generate(authentication));
				}

				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public JwtTokenStore getJwtTokenStore() {
		return jwtTokenStore;
	}

	public void setJwtTokenStore(JwtTokenStore jwtTokenStore) {
		this.jwtTokenStore = jwtTokenStore;
	}
}
