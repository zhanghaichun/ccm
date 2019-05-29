/**
 * 
 */
package com.saninco.ccm.action.quoteRequest;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.service.quoteRequest.IQuoteRequestService;

/**
 * Invoice related UI action class.
 * 
 */
public class QuoteRequestAction extends CcmActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IQuoteRequestService quoteRequestService;
	
	public QuoteRequestAction() {
	}
	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@AllGranted(value="FUNCTION_2000")
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	public IQuoteRequestService getQuoteRequestService() {
		return quoteRequestService;
	}
	public void setQuoteRequestService(IQuoteRequestService quoteRequestService) {
		this.quoteRequestService = quoteRequestService;
	}

}
