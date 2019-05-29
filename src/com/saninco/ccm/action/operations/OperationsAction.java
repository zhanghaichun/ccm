package com.saninco.ccm.action.operations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Email;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.dispute.IDisputeService;
import com.saninco.ccm.service.operations.IOperationService;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.OperationsVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * Operations action class.
 * 
 * @author Jian.Dong
 */
public class OperationsAction extends CcmActionSupport {
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());

	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private ICommonLookupService commonLookupService;
	private Integer vendorId;
	private Integer banId;
	private String barcode;
	private String invoiceDate;
	private IOperationService operationService;
	private IDisputeService disputeService;
	private String emailId;
	private OperationsVO operationsVO;
	private SearchInvoiceVO searchInvoiceVO;
	private Email email;
	private String[] emailIds;
	private String attachmentPointId;
	
	/**
	 * goto main page
	 * */
	public String exec() throws Exception {
		if(SystemUtil.getCurrentUserId() < 100){
			vendorList = commonLookupService.getUserPreviledgedVendors();
			return SUCCESS;
		}else{
			return INPUT;
		}
	}
	/**
	 * saveOperations
	 * */
	public String saveOperations() throws Exception {
		logger.info("Enter method saveOperations.");
		String result = null;
		try{
			Date d = this.parseDate(invoiceDate);
			operationService.saveOperations(vendorId,banId,barcode,d);
			result = "true";
		}catch(Exception e){
			logger.error("saveOperations error: ", e);
			result = "{error:\"saveOperations error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveOperations.");
		return null;
	}
	
	public String addInvoiceStV()  throws Exception {
		logger.info("Enter method addInvoiceStV.");
		try{
			operationService.addInvoiceStVData(operationsVO);
		}catch(Exception e){
			logger.error("addInvoiceStV error: ", e);
		}
		logger.info("Enter method addInvoiceStV.");
		return null;
	}
	
	public String deleteInvoiceStV()  throws Exception {
		logger.info("Enter method deleteInvoiceStV.");
		try{
			operationService.deleteInvoiceStVData(operationsVO);
		}catch(Exception e){
			logger.error("deleteInvoiceStV error: ", e);
		}
		logger.info("Enter method deleteInvoiceStV.");
		return null;
	}
	
	public String findBarCode() throws Exception{
		logger.info("Enter method findBarCode.");
		String result;
		try {
			result = operationService.operationServiceFindBarCode(operationsVO);
		} catch (BPLException e) {
			logger.error("findBarCode error: ", e);
			result = "{error:\"findBarCode error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method findBarCode.");
		return null;
	}
	
	public String findInvoiceSt() throws Exception{
		logger.info("Enter method findInvoiceSt.");
		String result;
		try {
			result = operationService.operationServiceByBarCode(operationsVO);
		} catch (BPLException e) {
			logger.error("findInvoiceSt error: ", e);
			result = "{error:\"findInvoiceSt error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method findInvoiceSt.");
		return null;
	}
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 */
	public String CreateEmialsAddEmailTable(){
		logger.info("Enter method findInvoiceSt.");
		String result="";
		try {
			disputeService.CreateEmialsAddEmailTable();
		} catch (BPLException e) {
			logger.error("CreateEmialsAddEmailTable error: ", e);
			result = "{error:\"CreateEmialsAddEmailTable error: "+e.getMessage()+"\"}";
		}
		try {
			this.writeOutputStream(result);
		} catch ( Exception e) {
			logger.error(e);
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info("Exit method CreateEmialsAddEmailTable.");
		return null;
	}
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 */
	public String getPreEmailsTotalPageNo(){
		logger.info("Enter method getPreEmailsTotalPageNoOfDispute.");
		String result="";
		try {
			result= disputeService.getPreEmailsTotalPageNoOfDispute(operationsVO);
		} catch (BPLException e) {
			logger.error("getPreEmailsTotalPageNoOfDispute error: ", e);
			result = "{error:\"getPreEmailsTotalPageNoOfDispute error: "+e.getMessage()+"\"}";
		}
		try {
			this.writeOutputStream(result);
		} catch ( Exception e) {
			logger.error(e);
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info("Exit method getPreEmailsTotalPageNoOfDispute.");
		return null;
	}
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 */
	public String searchPreEmailsOfDispute(){
		logger.info("Enter method searchPreEmailsOfDispute.");
		String result="";
		try {
			result= disputeService.searchPreEmailsOfDispute(operationsVO);
			this.writeOutputStream(result);
		} catch ( Exception e) {
			logger.error("searchPreEmailsOfDispute error: ", e);
			result = "{error:\"searchPreEmailsOfDispute error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method searchPreEmailsOfDispute.");
		return null;
	}
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 */
	public String getEmailByEmailId() throws Exception{
		logger.info("Enter method getEmailByEmailId.");
		String result="{}";
		try {
			result= disputeService.getEmailByEmailId(Integer.valueOf(emailId));
		} catch (Exception e) {
			e.printStackTrace();
			result="{error : '"+e.getMessage()+"'}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getEmailByEmailId.");
		return null;
	}
	
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 * @throws Exception 
	 */
	public String updateEmailByEmail() throws Exception{
		logger.info("Enter method updateEmailByEmail.");

		String result="{}";
		try {
			disputeService.updateEmailByEmail(email);

		} catch ( Exception e) {
			logger.error("updateEmailByEmail error: ", e);
			result = "{error:\"updateEmailByEmail error: "+e.getMessage()+"\"}";

		}
		this.writeOutputStream(result);
		logger.info("Exit method updateEmailByEmail.");
		return null;
	}
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 * @throws Exception 
	 */
	public String sendCheckedEmailsAndChangeEmailStatus() throws Exception{
		logger.info("Enter send Checked Emails And Change Email Status.");
		String result="";
		try {
			disputeService.updateSendCheckedEmailsAndChangeEmailStatus(emailIds);
			this.writeOutputStream(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Exit method sendCheckedEmailsAndChangeEmailStatus.");
		return null;
	}
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 */
	public String getAnnexsTotalPageNo(){
		logger.info("Enter method getAnnexsTotalPageNo.");
		String result="";
		searchInvoiceVO.setBoxInId(attachmentPointId);
		try {
			result= disputeService.getAnnexsOfDisputeTotalPageNo(searchInvoiceVO);
		} catch (BPLException e) {
			logger.error("getAnnexsTotalPageNo error: ", e);
			result = "{error:\"getAnnexsTotalPageNo error: "+e.getMessage()+"\"}";
		}
		try {
			this.writeOutputStream(result);
		} catch ( Exception e) {
			logger.error(e);
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info("Exit method getPreEmailsTotalPageNoOfDispute.");
		return null;
	}
	/**
	 * Be ready to send email to email table for moderation
	 * @author suwei
	 * @return
	 */
	public String searchAnnexsOfDispute(){
		logger.info("Enter method searchAnnexsOfDispute.");
		String result="";
		searchInvoiceVO.setBoxInId(attachmentPointId);
		try {
			result= disputeService.searchAnnexsOfDispute(searchInvoiceVO);
			this.writeOutputStream(result);
		} catch ( Exception e) {
			logger.error("searchAnnexsOfDispute error: ", e);
			result = "{error:\"searchAnnexsOfDispute error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method searchPreEmailsOfDispute.");
		return null;
	}
	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getBanId() {
		return banId;
	}

	public void setBanId(Integer banId) {
		this.banId = banId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public IOperationService getOperationService() {
		return operationService;
	}
	public void setOperationService(IOperationService operationService) {
		this.operationService = operationService;
	}
	public OperationsVO getOperationsVO() {
		return operationsVO;
	}
	public void setOperationsVO(OperationsVO operationsVO) {
		this.operationsVO = operationsVO;
	}
	public IDisputeService getDisputeService() {
		return disputeService;
	}
	public void setDisputeService(IDisputeService disputeService) {
		this.disputeService = disputeService;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public String[] getEmailIds() {
		return emailIds;
	}
	public void setEmailIds(String[] emailIds) {
		this.emailIds = emailIds;
	}
	public String getAttachmentPointId() {
		return attachmentPointId;
	}
	public void setAttachmentPointId(String attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}
	public SearchInvoiceVO getSearchInvoiceVO() {
		return searchInvoiceVO;
	}
	public void setSearchInvoiceVO(SearchInvoiceVO searchInvoiceVO) {
		this.searchInvoiceVO = searchInvoiceVO;
	}
}