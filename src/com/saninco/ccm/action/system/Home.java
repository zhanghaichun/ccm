package com.saninco.ccm.action.system;


import com.saninco.ccm.action.CcmActionSupport;

/**
 * Displays home page.
 * 
 * @ Author: Feiwei.Ma
 *
 */
public class Home extends CcmActionSupport {

	private static final long serialVersionUID = -939445513027439180L;

	@Override
	public String exec() throws Exception {
		// display initial page			
		return INPUT;
	}
	
}