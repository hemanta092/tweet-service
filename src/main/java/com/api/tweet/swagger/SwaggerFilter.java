package com.api.tweet.swagger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SwaggerFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Swagger Filter servlet instantiated!!!");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		log.debug("Swagger Filter doFilter method called!!!");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		log.info("Request: {}",servletRequest.toString());
        log.info("Response: {}",servletResponse.toString());
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (request.getRequestURI().equals("/tweet/swagger")) {
			response.sendRedirect("/swagger-ui.html");
			return;
		}
		
        
		filterChain.doFilter(servletRequest, servletResponse);
		log.debug("Swagger Filter doFilter method exited!!!");
	}

	@Override
	public void destroy() {

	}

}
