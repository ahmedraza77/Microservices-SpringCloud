package com.netflix.zuul.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ResponseFilterCorrelationId extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FilterUtils filterUtils;
    
	@Override
	public boolean shouldFilter() {
        return FilterUtils.SHOULD_FILTER;
	}

	@Override
	public String filterType() {
		return FilterUtils.POST_FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterUtils.FILTER_ORDER;
	}
	
	@Override
	public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        logger.info("Adding the correlation id to the response headers.");
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());

        logger.info("Completing outgoing request for {}.", ctx.getRequest().getRequestURI());

        return null;	
	}
}
