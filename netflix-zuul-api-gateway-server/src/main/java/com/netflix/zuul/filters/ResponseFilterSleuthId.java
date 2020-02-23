package com.netflix.zuul.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import brave.Tracer;

@Component
public class ResponseFilterSleuthId extends ZuulFilter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FilterUtils filterUtils;

	@Autowired
	Tracer tracer;
		
	@Override
	public boolean shouldFilter() {
		return FilterUtils.SHOULD_FILTER;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext ctx = RequestContext.getCurrentContext();

		ctx.getResponse().addHeader(FilterUtils.SLEUTH_TRACE_ID, tracer.currentSpan().context().traceIdString());
		logger.info("Added traceID to response: -----> "+tracer.currentSpan().context().traceIdString());

		return null;

	}

	@Override
	public String filterType() {
		return FilterUtils.POST_FILTER_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterUtils.FILTER_ORDER;
	}


}
