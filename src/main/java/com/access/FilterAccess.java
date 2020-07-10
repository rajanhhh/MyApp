package com.access;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;	
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
//@WebFilter("/*")
//@WebFilter("^\\/(?!(js|css)).*$")
//@ComponentScan(resourcePattern = "*")
public class FilterAccess implements Filter{
	
	static final Pattern STATIC_RESOURCES = Pattern.compile("(^/js/.*)|(^/css/.*)|(^/img/.*)|(^/fonts/.*)|(/favicon.ico)");
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse  myResponse= (HttpServletResponse) response;
		
		if (STATIC_RESOURCES.matcher(httpRequest.getServletPath()).matches()
				|| httpRequest.getRequestURL().toString().endsWith("/login")) {
			chain.doFilter(request, response);
        } else {
        	HttpSession session = httpRequest.getSession(false);
    		if(!StringUtils.isEmpty(session)) {
    			String sessionValidity = String.valueOf(session.getAttribute("session"));
    			if(!httpRequest.getRequestURL().toString().endsWith("/") 
    					&& !httpRequest.getRequestURL().toString().endsWith("/login") 
    					&& !sessionValidity.contentEquals("valid")) {
    				myResponse.sendRedirect("/login");
    				return;
    			}
    		}else if(!httpRequest.getRequestURL().toString().endsWith("/login")){
    			myResponse.sendRedirect("/login");
				return;
    		}
        }
		chain.doFilter(request, response);
		
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("got in");
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		System.out.println("got in");
		// TODO Auto-generated method stub
		
	}
}
