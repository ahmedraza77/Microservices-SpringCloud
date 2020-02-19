//package com.netflix.zuul.filters;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CorrelationIdFilter implements Filter {
//
//	@Autowired
//	FilterUtils filterUtils;
//	
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		
//		String correlationId = ((HttpServletRequest) request).getHeader("x-correlation-id");
//	    correlationId = filterUtils.verifyOrCreateId(correlationId);   
//	    MDC.put("x-correlation-id", correlationId);
//	 
//	    chain.doFilter(request, response);
//	}
//
//}
