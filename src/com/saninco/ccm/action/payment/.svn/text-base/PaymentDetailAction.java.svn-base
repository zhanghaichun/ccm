/**
 * 
 */
package com.saninco.ccm.action.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.payment.IPaymentDetailService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewPaymentDetailVO;

/**
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class PaymentDetailAction extends CcmActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ICommonLookupService commonLookupService;
	private IQuicklinkService quicklinkService;
	private IPaymentDetailService paymentDetailService;

	private String quicklinkName;
	private String queryString;
	private String selVendorId;
	private String paymentId;
	
	private ViewPaymentDetailVO viewPaymentDetailVO;
	
	private Payment payment = new Payment();
	
	private List<MapEntryVO<String, String>> actionList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	
	/**
	 * 
	 */
	public PaymentDetailAction() {
	}

	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populatePaymentLookupData();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	/**
	 * Get quicklinkList and actionList and form data
	 */
	private void populatePaymentLookupData() throws BPLException {
		logger.info("Enter method populatePaymentLookupData.");
		payment = paymentDetailService.findPayment(paymentId);
		this.quicklinkList = quicklinkService.getUserQuicklinks(EnumQuicklinkType.PAYMENT);
		logger.info("Exit method populatePaymentLookupData.");
	}
	
	/**
	 * query payment detail data
	 * @return
	 * @throws Exception
	 */
	public String getPaymentDetails() throws Exception {
		logger.info("Enter method getPaymentDetails.");
		String result = null;
		try{
			result = paymentDetailService.searchPaymentDetails(viewPaymentDetailVO);
		}catch(Exception e){
			logger.error("getPaymentDetails error: ", e);
			result = "{error:\"getPaymentDetails error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaymentDetails.");
		return null;
	}

	/**
	 * Pages from payment detail data 
	 * @return
	 * @throws Exception
	 */
	public String getPaymentDetailsTotalPageNo() throws Exception{
		logger.info("Enter method getPaymentDetailsTotalPageNo.");
		String result = null;
		try{
			result = paymentDetailService.getPaymentDetailsTotalPageNo(viewPaymentDetailVO);
		}catch(Exception e){
			logger.error("getPaymentDetailsTotalPageNo error: ", e);
			result = "{error:\"getPaymentDetailsTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaymentDetailsTotalPageNo.");
		return null;
	}
	
	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public String getQuicklinkName() {
		return quicklinkName;
	}

	public void setQuicklinkName(String quicklinkName) {
		this.quicklinkName = quicklinkName;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getSelVendorId() {
		return selVendorId;
	}

	public void setSelVendorId(String selVendorId) {
		this.selVendorId = selVendorId;
	}

	public Logger getLogger() {
		return logger;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public IPaymentDetailService getPaymentDetailService() {
		return paymentDetailService;
	}

	public void setPaymentDetailService(IPaymentDetailService paymentDetailService) {
		this.paymentDetailService = paymentDetailService;
	}
	
	public ViewPaymentDetailVO getViewPaymentDetailVO() {
		return viewPaymentDetailVO;
	}

	public void setViewPaymentDetailVO(ViewPaymentDetailVO viewPaymentDetailVO) {
		this.viewPaymentDetailVO = viewPaymentDetailVO;
	}

	public List<MapEntryVO<String, String>> getActionList() {
		return actionList;
	}

	public void setActionList(List<MapEntryVO<String, String>> actionList) {
		this.actionList = actionList;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}
	
}
