package com.saninco.ccm.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.AuthenticationEntryPoint;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;

public class CustomAuthenticationProcessingFilterEntryPoint extends
		AuthenticationProcessingFilterEntryPoint implements
		AuthenticationEntryPoint, InitializingBean {

	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
	private static final Log logger = LogFactory
			.getLog(org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint.class);

	public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String redirectUrl = null;
		String xmlHttpHeader = httpRequest.getHeader("X-Requested-With");
		if (XML_HTTP_REQUEST.equals(xmlHttpHeader)) {
			httpResponse.setStatus(401);
			httpRequest.getSession().setAttribute(AbstractProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY,null);
		} else {
			
			if (isServerSideRedirect()) {
				if (isForceHttps() && "http".equals(request.getScheme()))
					redirectUrl = buildHttpsRedirectUrlForRequest(httpRequest);
				if (redirectUrl == null) {
					String loginForm = determineUrlToUseForThisRequest(httpRequest, httpResponse, authException);
					if (logger.isDebugEnabled())
						logger.debug("Server side forward to: " + loginForm);
					RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginForm);
					dispatcher.forward(request, response);
					return;
				}
			} else {
				redirectUrl = buildRedirectUrlToLoginPage(httpRequest, httpResponse, authException);
			}
			httpResponse.sendRedirect(httpResponse.encodeRedirectURL(redirectUrl));
		}
		
	}

}
