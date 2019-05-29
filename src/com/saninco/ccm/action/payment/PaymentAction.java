/**
 * 
 */
package com.saninco.ccm.action.payment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.payment.IPaymentService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchPaymentVO;

/**
 * @author Joe.Yang (Optimization of complete by xinyu.Liu)
 */
public class PaymentAction extends CcmActionSupport {

	private static final long serialVersionUID = 1641384909568910499L;
	private final Logger logger = Logger.getLogger(this.getClass());
	private IPaymentService paymentService;
	private IQuicklinkService quicklinkService;
	private SearchPaymentVO searchPaymentVO;
	
    private String quicklinkName;
	private String queryString;
	private String selVendorId;

	private List<MapEntryVO<String, String>> vendorList =  new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> currencyList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentApproverList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	
	private List<File> uploads;
	private List<String> uploadsFileName;
	private String uploadsMessage;
	
	public PaymentAction() {
	}
	
	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populatePaymentSearchLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	
	private void populatePaymentSearchLookupList() throws BPLException {
		logger.info("Enter method populatePaymentSearchLookupList.");
		this.vendorList = commonLookupService.getUserPreviledgedVendors();
		this.currencyList = commonLookupService.getCurrency();
		this.paymentStatusList = commonLookupService.getPaymentStatus();
		this.quicklinkList = quicklinkService.getUserQuicklinks(EnumQuicklinkType.PAYMENT);
		this.paymentApproverList = commonLookupService.getCreatedBy(); 
		logger.info("Exit method populatePaymentSearchLookupList.");
	}
	
	public String getPaymentSearchTotalPageNo() throws Exception{
		logger.info("Enter method getPaymentSearchTotalPageNo.");
		String result = null;
		try{
			result = paymentService.getPaymentSearchTotalPageNo(searchPaymentVO);
		}catch(Exception e){
			logger.error("getPaymentSearchTotalPageNo error: ", e);
			result = "{error:\"getPaymentSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaymentSearchTotalPageNo.");
		return null;
	}
	
	public String getPaymentAssignmentSearchTotalPageNo() throws Exception{
		logger.info("Enter method getPaymentAssignmentSearchTotalPageNo.");
		String result = null;
		try {
			result = paymentService.getPaymentAssignmentSearchTotalPageNo(searchPaymentVO);
		} catch (BPLException e) {
			logger.error("getPaymentAssignmentSearchTotalPageNo error: ", e);
			result = "{error:\"getPaymentAssignmentSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getPaymentAssignmentSearchTotalPageNo.");
		return null;
	}
	
	/**
	 * Do search payment action.
	 * @return
	 * @throws Exception
	 */
	public String searchPaymentAssignment() throws Exception {
		logger.info("Enter method searchPaymentAssignment.");
		String result = null;
		try {
			result = paymentService.searchPaymentAssignment(searchPaymentVO);
		} catch (BPLException e) {
			logger.error("searchPaymentAssignment error: ", e);
			result = "{error:\"searchPaymentAssignment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchPaymentAssignment.");
		return null;
	}

	/**
	 * Do search payment action.
	 * @return
	 * @throws Exception
	 */
	public String searchPayment() throws Exception {
		logger.info("Enter method searchPayment.");
		String result = null;
		try{
			result = paymentService.searchPayment(searchPaymentVO);
		}catch(Exception e){
			logger.error("searchPayment error: ", e);
			result = "{error:\"searchPayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchPayment.");
		return null;
	}
	
	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @return
	 * @throws Exception
	 */
	public String getPaymentFileTotalNO()throws Exception {
		logger.info("Enter method getPaymentFileTotalNO.");
		String result = "";
		try{
			result = paymentService.getPaymentFileTotalNO(searchPaymentVO);
		}catch(Exception e){
			logger.error("getPaymentFileTotalNO error: ", e);
			result = "{error:\"getPaymentFileTotalNO error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaymentFileTotalNO.");
		return null;
	}
	/**
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @return
	 * @throws Exception
	 */
	public String searchPaymentFileList()throws Exception {
		logger.info("Enter method searchPaymentFileList.");
		String result = "";
		try{
			result = paymentService.searchPaymentFileList(searchPaymentVO);;
		}catch(Exception e){
			logger.error("searchPaymentFileList error: ", e);
			result = "{error:\"searchPaymentFileList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchPaymentFileList.");
		return null;
	}
	
	/**
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @return
	 * @throws Exception
	 */
	public String deletePaymentFileByFId()throws Exception {
		logger.info("Enter method deletePaymentFileByFId.");
		String result = "";
		try{
			result = paymentService.deletePaymentFileByFId(searchPaymentVO);;
		}catch(Exception e){
			logger.error("deletePaymentFileByFId error: ", e);
			result = "{error:\"deletePaymentFileByFId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method deletePaymentFileByFId.");
		return null;
	}
	
	/**
	 * Do save query and return the saved query name displaying on the quick link column 
	 * in the left side panel.
	 * @return
	 * @throws Exception
	 */

	public String savePaymentSearch() throws Exception {
		logger.info("Enter method savePaymentQuery.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.PAYMENT);
		}catch(Exception e){
			logger.error("saveSearchQuery error: ", e);
			result = "{error:\"saveSearchQuery error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveSearchQuery.");
		return null;
	}
	
	/**
	 * Get user vendor list.
	 * @return
	 */
	public String getUserPreviledgedVendorList() throws Exception {
		logger.info("Enter method getUserPreviledgedVendorList.");
		String result = null;
		try{
			result = this.commonLookupService.getUserPreviledgedVendorsJson();
		}catch(Exception e){
			logger.error("getUserPreviledgedVendorList error: ", e);
			result = "{error:\"getUserPreviledgedVendorList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getUserPreviledgedVendorList.");
		return null;
	}
	
	/**
	 * @author Chao.Liu
	 * Search Payment By Payment Number
	 * @return
	 * @throws Exception
	 */
	public String searchPaymentByPName()throws Exception {
		logger.info("Enter method searchPaymentByPName.");
		String result = null;
		try{
			result = paymentService.searchPaymentByPName(searchPaymentVO);
		}catch(Exception e){
			logger.error("searchPaymentByPName error: ", e);
			result = "{error:\"searchPaymentByPName error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchPaymentByPName.");
		return null;
	}
	
	/**
	 * @author Chao.Liu
	 * Confirm
	 * @return
	 * @throws Exception
	 */
	public String paymentPTConfirm()throws Exception {
		logger.info("Enter method paymentPTConfirm.");
		try{
			boolean sizeBoo = true;
			if(uploads != null){
//				uploadsMessage = "\" The ";
				for(int i=0; i<uploads.size(); i++){
					if(uploads.get(i) != null && uploads.get(i).length() > (1024*1024*10)){
//						uploadsMessage += "["+(i+1)+"] ";
						sizeBoo = false;
					}
				}
				if(!sizeBoo){
//					uploadsMessage += " Line File Size Less Than 10MB\"";
					uploadsMessage = "\"The file size should be less than 10 MB.\"";
					return INPUT;
				}
			}
			if(uploads != null){
				commonLookupService.saveUploadFile("payment", searchPaymentVO.getPid(), uploads, uploadsFileName);
			}
			uploadsMessage = paymentService.paymentPTConfirm(searchPaymentVO);
		}catch(Exception e){
			logger.error("paymentPTConfirm error: ", e);
			uploadsMessage = "\"Error!\"";
			return INPUT;
		}
		logger.info("Exit method paymentPTConfirm.");
		return SUCCESS;
	}
	
	public SearchPaymentVO getSearchPaymentVO() {
		return searchPaymentVO;
	}

	public void setSearchPaymentVO(SearchPaymentVO searchPaymentVO) {
		this.searchPaymentVO = searchPaymentVO;
	}

	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public List<MapEntryVO<String, String>> getCurrencyList() {
		return currencyList;
	}

	public List<MapEntryVO<String, String>> getPaymentStatusList() {
		return paymentStatusList;
	}

	public List<MapEntryVO<String, String>> getPaymentApproverList() {
		return paymentApproverList;
	}

	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setQuicklinkName(String quicklinkName) {
		this.quicklinkName = quicklinkName;
	}

	public String setQueryString(String queryString) {
		return this.queryString = queryString;
	}

	public void setSelVendorId(String selVendorId) {
		this.selVendorId = selVendorId;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	public List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public String getUploadsMessage() {
		return uploadsMessage;
	}

	public void setUploadsMessage(String uploadsMessage) {
		this.uploadsMessage = uploadsMessage;
	}
	
}
