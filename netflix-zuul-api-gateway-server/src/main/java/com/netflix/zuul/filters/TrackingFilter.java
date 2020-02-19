package com.netflix.zuul.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

@Component
public class TrackingFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FilterUtils filterUtils;
	
	@Override
	public boolean shouldFilter() {
        return FilterUtils.SHOULD_FILTER;
	}

	@Override
	public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
        //return FilterUtils.FILTER_ORDER;
        return 0;
	}

	@Override
	public Object run() throws ZuulException {

        if (filterUtils.isCorrelationIdPresent()) {
            logger.info("x-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        }
        else{
            filterUtils.setCorrelationId(filterUtils.generateCorrelationId());
            logger.info("x-correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }
		
		return null;
	}

}
