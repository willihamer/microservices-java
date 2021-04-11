package com.hh.sbc.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreTimeElapsedFilter extends ZuulFilter {
	
	private static Logger log = org.slf4j.LoggerFactory.getLogger(PreTimeElapsedFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		log.info(String.format("%s request routed to %s ", request.getMethod(), request.getRequestURL().toString()));
		Long startedTime = System.currentTimeMillis();
		request.setAttribute("startedTime", startedTime);
		
		

		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
