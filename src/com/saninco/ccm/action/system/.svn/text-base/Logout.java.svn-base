package com.saninco.ccm.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.ICommonLookupService;

/**
 * User Logout action.
 * 
 * @ Author: Joe.Yang
 *
 */
public class Logout extends CcmActionSupport {
	private static final long serialVersionUID = 1L;

	@Override
	public String exec() throws Exception {
		
		ActionContext.getContext().getSession().clear();
		
		return SUCCESS;
	}
	
	
	public String securityLogout() throws Exception {
		try {
			commonLookupService.saveLogOutUserActionLog();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return INPUT;
	}

}
