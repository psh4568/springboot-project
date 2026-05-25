package com.jwt.spring_security_jwtcasestudy.filter;

import com.jwt.spring_security_jwtcasestudy.service.CustomUserDetailsService;
import com.jwt.spring_security_jwtcasestudy.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
 * This class get called if any request coming with token
 * This runs for every request / intercepts every request before controller
 * JwtFilter uses JetUtil internally
 * Read token
 * validate token
 * extract user details
 * 
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		/*
		 * for h2 console so that it will not be authenticated forcefully by spring
		 * security
		 */
		String path = request.getServletPath();
		if (path.startsWith("/h2-console")) {
			filterChain.doFilter(request, response);
			return;
		}
		/*
		 * validating token - reading token from request
		 */
		String header = request.getHeader("Authorization");

		String token = null;
		String email = null;
		//extracting token and validating signature , expiry ,format
		if (header != null && header.startsWith("Bearer ")) {
			token = header.substring(7);
			email = jwtUtil.extractUsername(token);
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(email);
			//validating token
			if (jwtUtil.validateToken(token)) {
			//creating authentication object
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());

				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//set in security context now spring knows this is user and with this role
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		filterChain.doFilter(request, response);
	}
}