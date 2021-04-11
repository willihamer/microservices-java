package com.hh.sbc.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTimeElapsedFilter extends ZuulFilter {

	private static Logger log = org.slf4j.LoggerFactory.getLogger(PostTimeElapsedFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		log.info("Go into post filter");
		Long startedTime = (Long) request.getAttribute("startedTime");
		Long finalTime = System.currentTimeMillis();
		Long transcurredTime = finalTime - startedTime;

		log.info(String.format("Seconds elapsed %s secs. ", transcurredTime.doubleValue() / 1000.00));

		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
