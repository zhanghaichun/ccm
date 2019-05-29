package com.saninco.ccm.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.saninco.ccm.service.security.ISecurityManagementService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.EhcacheUtils;


public class UrlSecurityCheckFilter implements Filter {

	private final static String denyPage = "/pages/accessDenied.jsp";
	private final static Log logger = LogFactory.getLog(UrlSecurityCheckFilter.class);

	public void doFilter(ServletRequest req,
			ServletResponse resp,
		 	FilterChain chain)
		throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		// fix struts 2 security bug
		boolean isValid = checkStrutsSecurityBug(request);
        if (!isValid) {
        	logger.warn("This request may cause security problems.");
        	request.setAttribute("errorMessage", "Hi guy, what are you want to do?");
        	response.setStatus(403);
        	request.getRequestDispatcher(denyPage).forward(request, response);
        	return;
        } 
        
        // check ip address security
        Map mapResult = checkIPAddressSecurity(request);
        isValid = Boolean.parseBoolean(mapResult.get("result").toString());
        if (!isValid && !isMobile(request)) {
        	logger.warn("This ip " + request.getRemoteAddr() + " is not allow.");
        	boolean startDateValid = Boolean.parseBoolean(mapResult.get("startDateValid").toString());
            boolean endDateValid = Boolean.parseBoolean(mapResult.get("endDateValid").toString());
        	request.getSession().invalidate();
        	if(endDateValid){
        		request.setAttribute("errorMessage", "Sorry, your location is not authorized. Your IP address is : " + request.getRemoteAddr()+" !");
        	}else{
        		request.setAttribute("errorMessage", "Your location is expired. Please contact our support team. Your IP address is : "+request.getRemoteAddr()+" !");
        	}
        	if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
        		// if request is ajax
        		response.setStatus(403);
        	}
        	request.getRequestDispatcher(denyPage).forward(request, response);
        	return;
        }
        
        chain.doFilter(request, response);
	}
	
	/**
	 * Fixed struts 2 bug
	 * 
	 * S2-016
	 * The Struts 2 DefaultActionMapper supports a method for short-circuit navigation state changes by prefixing parameters with "action:" or "redirect:", followed by a desired navigational target expression. This mechanism was intended to help with attaching navigational information to buttons within forms.
	 * In Struts 2 before 2.3.15.1 the information following "action:", "redirect:" or "redirectAction:" is not properly sanitized. Since said information will be evaluated as OGNL expression against the value stack, this introduces the possibility to inject server side code.
	 * S2-017
	 * The Struts 2 DefaultActionMapper used to support a method for short-circuit navigation state changes by prefixing parameters with "redirect:" or "redirectAction:", followed by a desired redirect target expression. This mechanism was intended to help with attaching navigational information to buttons within forms.
	 * In Struts 2 before 2.3.15.1 the information following "redirect:" or "redirectAction:" can easily be manipulated to redirect to an arbitrary location.
	 */
	private boolean checkStrutsSecurityBug(ServletRequest request) {
		boolean isValid = true;
        Enumeration<String> paramterNames = request.getParameterNames();
        while(paramterNames.hasMoreElements()) {
        	String name = paramterNames.nextElement();
        	//  "action:"/"redirect:"/"redirectAction:" 
        	if (name != null && (name.startsWith("action:") || name.startsWith("redirect:") || name.startsWith("redirectAction:"))) {
        		logger.warn(name);
        		isValid = false;
        		break;
        	}
        }
		return isValid;
	}

	private Map checkIPAddressSecurity(HttpServletRequest request) {
		
		
		String ip = request.getRemoteAddr();
		String uri = request.getRequestURI();
		String userName = (String) request.getSession().getAttribute("SPRING_SECURITY_LAST_USERNAME");
		ApplicationContext app = CcmFormat.getCtx();
		ISecurityManagementService securityService = (ISecurityManagementService) app.getBean("securityManagementService");
		
		Map mapResult = new HashMap();
		if(uri.indexOf("/include/") != -1 || (uri.indexOf("/bb/") != -1 && "Y".equals(securityService.searchBBLoginFlag()))){
			mapResult.put("result", true);
			return mapResult;
		}
		
		mapResult = securityService.checkIpSecurity(ip,userName);
		boolean isValid = Boolean.parseBoolean(mapResult.get("result").toString());
		
		if(!isValid && uri.indexOf("/login.action") != -1){
			EhcacheUtils.clear("CCM_CACHE");
			mapResult = securityService.checkIpSecurity(ip,userName);
		}
		return mapResult;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public void destroy() {
		
	}
	
	public boolean isMobile(HttpServletRequest request){
		String authorization = request.getHeader("Authorization");
		if(authorization!=null && !"".equals(authorization)){
			try{
				String token = new String(Base64.decodeBase64(new String(authorization).getBytes()));
				String[] ss = null;
				if(token.indexOf("isMobile") >= 0){
					ss = token.split("isMobile");
					token = ss[ss.length-1];
				}
				long nowTimeStamp = System.currentTimeMillis();
				long timeStamp = 0;
				if (token!=null && !"".equals(token)){
					timeStamp = nowTimeStamp - Long.parseLong(token);
				}
				long hour = (timeStamp / (60 * 60 * 1000));
				if (hour <= 2) {
					request.getSession().setAttribute("isMobile", "Y");
					return true;
				}else{
					return false;
				}
			}catch (Exception re) {
				return false;
			}
		}else{
			String isMobile = request.getSession().getAttribute("isMobile")+"";
			if(isMobile!=null && "Y".equals(isMobile)){
				return true;
			}else{
				return false;
			}
		}
	}
	

}
