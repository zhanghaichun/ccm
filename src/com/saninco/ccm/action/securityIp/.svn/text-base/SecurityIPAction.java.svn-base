package com.saninco.ccm.action.securityIp;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.securityIp.ISecurityIPService;
import com.saninco.ccm.util.EhcacheUtils;
import com.saninco.ccm.vo.ViewSecurityIpVo;

public class SecurityIPAction extends CcmActionSupport {
private static final long serialVersionUID = -7778316099624206431L;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private ISecurityIPService securityIPService;
	private ViewSecurityIpVo viewSecurityIpVo;
	

	public String exec() throws Exception {
		
		return null;
	}
	
	public String getSeacurityTotalPageNo() throws Exception {
		logger.info("Enter method getSeacurityTotalPageNo.");
		String result = null;
		try {
			
			result = securityIPService.getSeacurityTotalPageNo(viewSecurityIpVo);
		} catch (Exception e) {
			logger.error("getSeacurityTotalPageNo error: ", e);
			result = "{error:\"getSeacurityTotalPageNo error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getSeacurityTotalPageNo.");
		return null;
	}
	
	public String searchSecurityIp() throws Exception {
		logger.info("Enter method searchSecurityIp.");
		String result = null;
		try {
			result = securityIPService.searchSecurityIp(viewSecurityIpVo);
		} catch (Exception e) {
			logger.error("searchSecurityIp error: ", e);
			result = "{error:\"searchSecurityIp error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchSecurityIp.");
		return null;
	}
	
	public String updateSecurity() throws Exception{
		logger.info("Enter method updateSecurity.");
		
		String result = "{}";
		try {
			result = securityIPService.updateSecurity(viewSecurityIpVo);
			EhcacheUtils.clear("CCM_CACHE");
		} catch (Exception e) {
			logger.error("updateSecurity error: ", e);
			result = "{error:\"updateSecurity error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateSecurity.");
		return null;
	}
	
	public String reactiveSecurity() throws Exception{
		logger.info("Enter method reactiveSecurity.");
		
		String result = "{}";
		try {
			result = securityIPService.reactiveSecurity(viewSecurityIpVo);
			EhcacheUtils.clear("CCM_CACHE");
		} catch (Exception e) {
			logger.error("reactiveSecurity error: ", e);
			result = "{error:\"reactiveSecurity error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method reactiveSecurity.");
		return null;
	}
	
	public String delSecurityIp() throws Exception {
		logger.info("Enter method delSecurityIp.");
		String result = "{}";
		try {
			securityIPService.delSecurityIp(viewSecurityIpVo);
			EhcacheUtils.clear("CCM_CACHE");
		} catch (Exception e) {
			logger.error("delSecurityIp error: ", e);
			result = "{error:\"delSecurityIp error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method delSecurityIp.");
		return null;
	}
	
	public String addSecurityIp() throws Exception{
		logger.info("Enter method addSecurityIp.");

		String result = "{}";
		try {
			result = securityIPService.addSecurityIp(viewSecurityIpVo);
			EhcacheUtils.clear("CCM_CACHE");
		} catch (Exception e) {
			logger.error("addSecurityIp error: ", e);
			result = "{error:\"addSecurityIp error: "+e.getMessage()+"\"}";   
		}
		
		this.writeOutputStream(result);
		logger.info("Exit method addSecurityIp.");
		return null;
	}
	
//	public String addSecurityIpTrueOrFalse() throws Exception{
//		logger.info("Enter method addSecurityIpTrueOrFalse.");
//
//		String result = "{}";
//		try {
//			result = securityIPService.addSecurityIpTrueOrFalse(securityIpA,securityStartDate,securityEndDate,securityUserName);
//			EhcacheUtils.clear("CCM_CACHE");
//		} catch (Exception e) {
//			logger.error("addSecurityIpTrueOrFalse error: ", e);
//			result = "{error:\"addSecurityIpTrueOrFalse error: "+e.getMessage()+"\"}";   
//		}
//		
//		this.writeOutputStream(result);
//		logger.info("Exit method addSecurityIpTrueOrFalse.");
//		return null;
//	}
	
	
	public ISecurityIPService getSecurityIPService() {
		return securityIPService;
	}

	public void setSecurityIPService(ISecurityIPService securityIPService) {
		this.securityIPService = securityIPService;
	}

	public ViewSecurityIpVo getViewSecurityIpVo() {
		return viewSecurityIpVo;
	}

	public void setViewSecurityIpVo(ViewSecurityIpVo viewSecurityIpVo) {
		this.viewSecurityIpVo = viewSecurityIpVo;
	}
	
}
