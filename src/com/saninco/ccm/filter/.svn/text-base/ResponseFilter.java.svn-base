/**
 * 
 */
package com.saninco.ccm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Response filter to clean page cache.
 * 
 * @author Joe.Yang
 *
 */
public class ResponseFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-control", "no-cache,must-revalidate");
		res.setDateHeader("Expires", 0);
		chain.doFilter(request,response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
