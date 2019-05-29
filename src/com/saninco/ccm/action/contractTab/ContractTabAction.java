package com.saninco.ccm.action.contractTab;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;

public class ContractTabAction extends CcmActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8135676753830252984L;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		
		
		logger.info("Exit method exec.");
		return SUCCESS;
	}
}