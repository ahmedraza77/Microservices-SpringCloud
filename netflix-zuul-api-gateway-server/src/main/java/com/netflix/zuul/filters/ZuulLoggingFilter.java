package com.netflix.zuul.filters;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean shouldFilter() {
		return FilterUtils.SHOULD_FILTER;                    // execute this filter for every request
	}

	@Override
	public Object run() throws ZuulException {
		
		HttpServletRequest request = 
				RequestContext.getCurrentContext().getRequest();  // give current HTTP request which is being handled
		
		logger.info("request -> {} request uri -> {}", request, request.getRequestURI());
		
		return null;
	}

	@Override
	public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;              // when filter should be happening; before or after execution or on error request
	}

	@Override
	public int filterOrder() {
		return FilterUtils.FILTER_ORDER;               // set priority for this filter
	}

}
