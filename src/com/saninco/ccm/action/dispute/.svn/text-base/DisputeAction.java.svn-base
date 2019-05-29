/**
 * 
 */
package com.saninco.ccm.action.dispute;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.service.dispute.IDisputeService;
import com.saninco.ccm.service.email.ISendEmailService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchDisputeVO;
import com.saninco.ccm.vo.SearchVO;

/**
 * Invoice related UI action class.
 * 
 */
public class DisputeAction extends CcmActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IDisputeService disputeService;
	private IQuicklinkService quicklinkService;
	private ISendEmailService sendEmailService;
	private SearchDisputeVO searchDisputeVO;
	private SearchVO vo;
	private Map<String,String> disputeMessage;
	
	private String invoiceId;
	private String ids;
	private String quicklinkName;
	private String queryString;
	private String selVendorId;
	private Integer accountCodeId;
	private String disputeId;
	private Integer attachmentPointId;
	private Dispute dispute;
	private String disputeAttachmentDownloadFlag;
	private String reserveType;
	private Credit credit;
	private Reconcile reconcile;
	private String actionRequestNotes;
	private String noteNotes;
	private String noteMessage;
	private List<String> effectiveFile;
	private String disputeActionRequestStatus;
	private String jsonResult;


	private List<File> uploads;
	private List<String> uploadsFileName;
	private String uploadsMessage;
	private List<String> titles;
	
	private List<MapEntryVO<String, String>> vendorList =
		new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList =
		new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> disputeReasonList =
		new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> disputeStatusList =
		new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList =
		new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> createdByList =
		new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> accountCodeList =
		new ArrayList<MapEntryVO<String, String>>();
	
	private List<Map<String, Object>> disputeList =
		new ArrayList<Map<String, Object>>();
	
	private Map<String, Object> disputeContactVendorEmail =
		new HashMap<String, Object>();

	public DisputeAction() {
	}

	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@AllGranted(value="FUNCTION_2000")
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populateInvoiceSearchLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	/**
	 * update IncreaseAndDecrease
	 * @return
	 * @author dongjian
	 */
	public String updateIncreaseAndDecrease() throws Exception{
		logger.info("Enter method updateIncreaseAndDecrease.");
		try {
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			Double v = disputeService.updateIncreaseAndDecrease(dispute,reserveType,accountCodeId,point);
			dispute.setOutstandingReserveAmount(v);
		} catch (Exception e) {
			logger.error("updateIncreaseAndDecrease error: ", e);
			return INPUT;
		}
		logger.info("Exit method updateIncreaseAndDecrease.");
		return SUCCESS;
	}
	
	private void populateInvoiceSearchLookupList() throws BPLException {
		logger.info("Enter method populateInvoiceSearchLookupList.");
			//update by donghao.guo 2011/10/25
			this.vendorList = commonLookupService.getSearchConditionVendors();
			//update by donghao.guo 2011/10/25
			this.banList = commonLookupService.getSearchConditionBans();
			this.disputeReasonList = commonLookupService.getDisputeReason();
			this.disputeStatusList = commonLookupService.getDisputeStatus();
			this.createdByList = commonLookupService.getCreatedBy();
			this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method populateInvoiceSearchLookupList.");
	}
	/**
	 * Get dispute search total result number and page.
	 * @return
	 * @throws Exception
	 */
	public String validateReconcileByDispute() throws Exception{
		logger.info("Enter method validateReconcileByDispute.");
		String result = null;
		try {
			result = disputeService.validateReconcileByDispute(disputeId);
		} catch (BPLException e) {
			logger.error("validateReconcileByDispute error: ", e);
			result = "{error:\""+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method validateReconcileByDispute.");
		return null;
	}
	/**
	 * Get dispute search total result number and page.
	 * @return
	 * @throws Exception
	 */
	public String getDisputeAssignmentSearchTotalPageNo() throws Exception{
		logger.info("Enter method getDisputeAssignmentSearchTotalPageNo.");
		String result = null;
		try {
			result = disputeService.getDisputeAssignmentSearchTotalPageNo(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("getDisputeAssignmentSearchTotalPageNo error: ", e);
			result = "{error:\"getDisputeAssignmentSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getDisputeAssignmentSearchTotalPageNo.");
		return null;
	}
	/**
	 * @author dongjian
	 */
	public String getOutstandingReserveAmount() throws Exception{
		logger.info("Enter method getOutstandingReserveAmount.");
		String result = null;
		try {
			Double amount = disputeService.getOutstandingReserveAmount(disputeId);
			result = "{amount : "+amount+"}";
		} catch (BPLException e) {
			logger.error("getOutstandingReserveAmount error: ", e);
			result = "{error:\"getOutstandingReserveAmount error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getOutstandingReserveAmount.");
		return null;
	}
	/**
	 * @author dongjian
	 */
	public String getDisputeAmount() throws Exception{
		logger.info("Enter method getDisputeAmount.");
		String result = null;
		try {
			Double amount = disputeService.getDisputeAmount(disputeId);
			result = "{amount : "+amount+",amountString:\""+CcmFormat.formatCurrency(amount)+"\"}";
		} catch (BPLException e) {
			logger.error("getDisputeAmount error: ", e);
			result = "{error:\"getDisputeAmount error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getDisputeAmount.");
		return null;
	}
	/**
	 * @author dongjian
	 */
	public String getDisputeSCOA() throws Exception{
		logger.info("Enter method getDisputeSCOA.");
		String result = null;
		try {
			List<MapEntryVO<String, String>> scoas = commonLookupService.getAccountCodeListByDisputeProposals(Integer.parseInt(disputeId));
			StringBuilder sb = new StringBuilder();
			sb.append("{scoa:[");
			for(MapEntryVO<String, String> scoa : scoas){
				sb.append("{id:"+scoa.getKey()+",name:\""+scoa.getValue()+"\"},");
			}
			if(scoas.size() > 0)sb.deleteCharAt(sb.length()-1);
			sb.append("]}");
			result = sb.toString();
		} catch (BPLException e) {
			logger.error("getDisputeSCOA error: ", e);
			result = "{error:\"getDisputeSCOA error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getDisputeSCOA.");
		return null;
	}
 
	public String doValidateDisputeBanLock() throws Exception {
		logger.info("Enter method doValidateDisputeBanLock.");
		String result = null;
		try {
			result = disputeService.getValidateDisputeBanLock(disputeId);
		} catch (BPLException e) {
			logger.error("doValidateDisputeBanLock error: ", e);
			result = "{error:\"doValidateDisputeBanLock error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method doValidateDisputeBanLock.");
		return null;
	}	
	
	public String doValiUserPrivilegeBanLockDispute() throws Exception {
		logger.info("Enter method doValiUserPrivilegeBanLockDispute.");
		String result = "{data:false}";
		try {
			Invoice invoice = disputeService.getInvoiceObject(invoiceId);
			Vendor vendor = invoice.getVendor();
			Ban ban = invoice.getBan();
			boolean bo = commonLookupService.validatePreviledgeByVendorAndBan(ban, vendor);
			if(bo) result = "{data:true}";
		} catch (BPLException e) {
			logger.error("doValiUserPrivilegeBanLockDispute error: ", e);
			result = "{error:\"doValiUserPrivilegeBanLockDispute error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method doValiUserPrivilegeBanLock.");
		return null;
	}
	
	/**
	 * Do search dispute action.
	 * @return
	 * @throws Exception
	 */
	public String searchDisputeAssignment() throws Exception {
		logger.info("Enter method searchDisputeAssignment.");
		String result = null;
		try {
			result = disputeService.searchDisputeAssignment(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchDisputeAssignment error: ", e);
			result = "{error:\"searchDisputeAssignment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchDisputeAssignment.");
		return null;
	}	
	/**
	 * Do search dispute action.
	 * @return
	 * @throws Exception
	 */
	public String searchDispute() throws Exception {
		logger.info("Enter method searchDispute.");
		try{
			String result = searchDisputeServiceCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("searchInvoice error: ", e);
		}
		logger.info("Exit method searchDispute.");
		return null;
	}	

	private String searchDisputeServiceCall(){
		logger.info("Enter method searchDisputeServiceCall.");
		String s = null;
			try {
				s = disputeService.searchDispute(searchDisputeVO);
			} catch (BPLException e) {
				logger.error("searchDisputeServiceCall error: ", e);
				s = "{error:\"searchDisputeServiceCall error: "+e.getMessage()+"\"}";
			}
		logger.info("Exit method searchDisputeServiceCall.");
		return s;
	}
	

	/**
	 * Get dispute search total result number and page.
	 * @return
	 * @throws Exception
	 */
	public String getDisputeSearchTotalPageNo() throws Exception{
		logger.info("Enter method getDisputeSearchTotalPageNo.");
		try{
			String result = getDisputeSearchTotalPageNoServiceCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDisputeSearchTotalPageNo error: ", e);
		}
		logger.info("Exit method getDisputeSearchTotalPageNo.");
		return null;
	}
	
	private String getDisputeSearchTotalPageNoServiceCall(){
		logger.info("Enter method getDisputeSearchTotalPageNoServiceCall.");
		String s = null;
		if(testWeb) s = "{totalResultNo:4,totalPageNo:1}";
		else {
			try {
				s = disputeService.getDisputeSearchTotalPageNo(searchDisputeVO);
			} catch (BPLException e) {	
				logger.error("getDisputeSearchTotalPageNo error: ", e);
				s = "{error:\"getDisputeSearchTotalPageNo error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getDisputeSearchTotalPageNoServiceCall.");
		return s;
	}
	/**
	 * Get user previledged vendor list.
	 * @return
	 */
	public String getUserPreviledgedVendorList() throws Exception {
		logger.info("Enter method getUserPreviledgedVendorList.");
		String result = null;
		try{
			result = getUserPreviledgedVendorListCall();
		}catch(Exception e){
			logger.error("getUserPreviledgedVendorList error: ", e);
		}
		this.writeOutputStream(result);
		logger.info("Exit method getUserPreviledgedVendorList.");
		return null;
	}
	
	private String getUserPreviledgedVendorListCall(){
		logger.info("Enter method getUserPreviledgedVendorListCall.");
		String s = null;
		if(testWeb) s = "[{id:1,name:\"Bell Maliant\"},{id:2,name:\"Bell Ontario\"},{id:3,name:\"Metafore\"}]";
		else {
			try {
				s = this.commonLookupService.getUserPreviledgedVendorsJson();
			} catch (BPLException e) {
				logger.error("getUserPreviledgedVendorListCall error: ", e);
				s = "{error:\"getUserPreviledgedVendorListCall error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getUserPreviledgedVendorListCall.");
		return s;
	}

	/**
	 * Do save query and return the saved query name displaying on the quick link column 
	 * in the left side panel.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveDisputeSearch() throws Exception {
		logger.info("Enter method saveDisputeSearch.");
		try{
			String result = saveSearchQueryCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("saveDisputeSearch error: ", e);
		}
		logger.info("Exit method saveDisputeSearch.");
		return null;
	}
	
	private String saveSearchQueryCall(){
		logger.info("Enter method saveSearchQueryCall.");
		String s = null;	
			try {
				s = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.DISPUTE);
			} catch (BPLException e) {
				logger.error("saveSearchQueryCall error: ", e);
				s = "{error:\"Name is repeat!\"}";
			} catch(RuntimeException e){
				logger.error(e);
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				s = "{error:\"Save SearchQuery error: "+bpe.getMessage()+"\"}";
			}
		logger.info("Exit method saveSearchQueryCall.");
		return s;
	}

	/**
	 * 跳转到 Dispute Detail 页面，页面中上半部分的表格信息。	
	 */
	@AllGranted(value="FUNCTION_2100")
	public String viewDisputeDetails() throws Exception {
		logger.info("Enter method viewDisputeDetail.");
		
		disputeService.recalculateDisputeAmountAndDisputeBalance(disputeId);
		
		this.accountCodeList = commonLookupService.getAccountCode();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		dispute = disputeService.findDisputeById(Integer.parseInt(disputeId));
		disputeList = disputeService.searchDisputeMessageByDisputeIds(disputeId);	
		disputeAttachmentDownloadFlag = disputeService.queryDisputeAttachmentCount(disputeId);
		logger.info("Exit method viewDisputeDetail.");
		return SUCCESS;
	}
	
	/**
	 * jump to DisputeDetailView	
	 */
	public String updateDisputeBalance() throws Exception {
		disputeService.updateDisputeBalance(disputeId);
		disputeService.updateDisputeAmount(disputeId);
		return null;
	}
	
	/**
	 * query credit detail total No
	 * @return
	 * @throws Exception
	 */
	public String  getReconcileDetailTotalNo() throws Exception {
		logger.info("Enter method getReconcileDetailTotalNo.");
		String result = null;
		try {
			result = disputeService.getReconcileDetailTotalNo(vo,dispute);
		} catch (BPLException e) {
			logger.error("getReconcileDetailTotalNo error: ", e);
			result = "{error:\"getReconcileDetailTotalNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getReconcileDetailTotalNo.");
		return null;
	}
	
	/**
	 * query reconcile detail info
	 * @return
	 * @throws Exception
	 */
	public String getReconcileDetails() throws Exception {
		logger.info("Enter method getReconcileDetails.");
		String result = null;
		try {
			result = disputeService.getReconcileDetails(dispute,vo);
		} catch (BPLException e) {
			logger.error("getReconcileDetails error: ", e);
			result = "{error:\"Search reconcile error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getReconcileDetails.");
		return null;
	}
	
	/**
	 * query credit detail total No
	 * @return
	 * @throws Exception
	 */
	public String getCreditDetailsTotalNo() throws Exception {
		logger.info("Enter method getCreditDetailsTotalNo.");
		String result = null;
		try {
			result = disputeService.getCreditDetailsTotalNo(vo,dispute);
		} catch (BPLException e) {
			logger.error("getCreditDetailsTotalNo error: ", e);
			result = "{error:\"getCreditDetailsTotalNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getCreditDetailsTotalNo.");
		return null;
	}

	/**
	 * query dispute history detail total No
	 * @return
	 * @throws Exception
	 */
	public String getDisputeDetailTotalNo() throws Exception {
		logger.info("Enter method getDisputeDetailTotalNo.");
		String result = null;
		try {
			dispute = disputeService.findDisputeById(dispute.getId());
			result = disputeService.getDisputeDetailTotalNo(dispute, vo);
		} catch (BPLException e) {
			logger.error("getDisputeHistoryTotalNo error: ", e);
			result = "{error:\"getDisputeHistoryTotalNo error: "
					+ e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getDisputeDetailTotalNo.");
		return null;
	}	
		
	/**
	 * query credit detail info
	 * @return
	 * @throws Exception
	 */
	public String getDisputeDetail() throws Exception {
		logger.info("Enter method getDisputeDetail.");
		String result = null;
		try {
			result = disputeService.getDisputeDetail(dispute,vo);
		} catch (BPLException e) {
			logger.error("getDisputeDetail error: ", e);
			result = "{error:\"getDisputeDetail: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getDisputeDetail.");
		return null;
	}
		
	/**
	 * query credit detail info
	 * @return
	 * @throws Exception
	 */
	public String getCreditDetails() throws Exception {
		logger.info("Enter method getCreditDetails.");
		String result = null;
		try {
			result = disputeService.searchCreditDetails(vo,dispute);
		} catch (BPLException e) {
			logger.error("creditDetailsQueryCall error: ", e);
			result = "{error:\"Search credits error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getCreditDetails.");
		return null;
	}
	
	public String getTicketNumber() throws Exception {
		logger.info("Enter method getCreditDetails.");
		String result = null;
		try {
			dispute = disputeService.findDisputeById(dispute.getId());	
			result = dispute.getTicketNumber();
		} catch (BPLException e) {
			logger.error("creditDetailsQueryCall error: ", e);
			result = "{error:\"Search credits error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getCreditDetails.");
		return null;
	}
	
	/**
	 * Modified By Chao.Liu
	 * creditForReconcile
	 * @throws Exception
	 */
	public String creditForReconcile() throws Exception {
		logger.info("Enter method creditForReconcile.");
		try{
			String[] strs = this.disputeService.updateCreditForReconcile( searchDisputeVO,dispute, credit,reconcile);
			if(uploads != null){
				commonLookupService.saveUploadFile("reconcile", new Integer(strs[0]), uploads, uploadsFileName);
			}
			if(strs[1] == null){
				uploadsMessage = strs[2];
				return INPUT;
			}
			uploadsMessage = strs[1]; 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDisputeSearchTotalPageNo error: ", e);
			uploadsMessage = "\"Credit Reconcile Error!\"";
			return INPUT;
		}
		logger.info("Exit method creditForReconcile.");
		return SUCCESS;
	}
	
	/**
	 *  cancelForReconcile
	 * @throws Exception
	 */
	public String cancelForReconcile() throws Exception {
		logger.info("Enter method cancelForReconcile.");
		String result = null;
		try{
			result = this.disputeService.updateCancelForReconcile(dispute, credit, reconcile);
		}catch(Exception e){
			logger.error("creditForReconcile error: ", e);
			result = "{error:\"creditForReconcile error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method cancelForReconcile.");
		return null;
	}
	
	/**
	 * Saving on the page  ####add PaymentHistory and update Payment
	 */
	public String getDisputeTransaction() throws Exception{
		logger.info("Enter method getDisputeTransaction.");
		String result = null;
		try {
			dispute = disputeService.findDisputeById(dispute.getId());	
			result = disputeService.getDisputeTransaction(dispute);
		} catch (BPLException e) {
			logger.error("getDisputeDetail error: ", e);
			result = "{error:\"getDisputeDetail: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getDisputeTransaction.");
		return null;
	}
	
	/**
	 * Automatically generate disputeSearchExcel.xls file server and turn it into a stream output to the client io
	 * 
	 */
	public String saveExcelBySearchDispute() throws Exception{
		logger.info("Exit method saveExcelBySearchDispute.");
		FileInputStream fis = null;
		try {
			String fileName = "disputeSearchExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeService.createDisputeToExcel(searchDisputeVO,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchDispute error: ", e);
		}
		logger.info("Exit method saveExcelBySearchDispute.");
		return null;
	}
	
	/**
	 * Automatically generate DisputeDetail.xls file server and turn it into a stream output to the client io
	 * 
	 */
	public String saveExcelByDisputeDetail() throws Exception{
		logger.info("Exit method saveExcelByDisputeDetail.");
		FileInputStream fis = null;
		try {
			dispute = disputeService.findDisputeById(dispute.getId());	
			String fileName = "DisputeDetail.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeService.createDisputeDetailToExcel(dispute,vo,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByDisputeDetail error: ", e);
		}
		logger.info("Exit method saveExcelByDisputeDetail.");
		return null;
	}
	
	/**
	 * Automatically generate disputeTransaction.xls file server and turn it into a stream output to the client io
	 * 
	 */
	public String saveExcelByDisputeTransaction() throws Exception{
		logger.info("Exit method saveExcelByDisputeTransaction.");
		FileInputStream fis = null;
		try {
			dispute = disputeService.findDisputeById(dispute.getId());	
			String fileName = "disputeTransaction.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeService.createDisputeTransactionToExcel(dispute,vo,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByDisputeDetail error: ", e);
		}
		logger.info("Exit method saveExcelByDisputeTransaction.");
		return null;
	}
	
	/**
	 * *************************************************************
	 * @author pengfei.wang
	 * create pendingDispute
	 * */
	//pendingDispute.js, jump to it
	public String pendingDispute() throws Exception {
		logger.info("Enter method searchDispute.");
		try{
			String result = pendingDisputeServiceCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("searchDispute error: ", e);
		}
		logger.info("Exit method searchDispute.");
		return null;
	}	
	//Connection to pendingDisputeServiceCall
	private String pendingDisputeServiceCall(){
		logger.info("Enter method searchDisputeServiceCall.");
		String s = null;
			try {
				s = disputeService.searchPendingDispute(searchDisputeVO);
			} catch (BPLException e) {
				logger.error("searchInvoice error: ", e);
				s = "{error:\"Search pendingDispute error: "+e.getMessage()+"\"}";
			}
		logger.info("Exit method searchDisputeServiceCall.");
		return s;
	}
	//paging
	public String getPendingTotalPageNo() throws Exception{
		logger.info("Enter method getDisputeSearchTotalPageNo.");
		try{
			String result = getPendingPageNoServiceCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDisputeSearchTotalPageNo error: ", e);
		}
		logger.info("Exit method getDisputeSearchTotalPageNo.");
		return null;
	}
	/**
	 * Update Dispute Tiket Number
	 * @return
	 * @throws Exception
	 */
	public String updateDisputeTiketNO()throws Exception{
		logger.info("Enter method updateDisputeTiketNO.");
		try{
			dispute = disputeService.findDisputeById(searchDisputeVO.getDisputeId());
			Integer id = disputeService.updateDisputeTiketNO(searchDisputeVO,dispute,uploadsFileName);
			if(uploads != null){
				commonLookupService.saveUploadFile("transaction_history", id, uploads, uploadsFileName);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getDisputeSearchTotalPageNo error: ", e);
			uploadsMessage = "\"Update Ticke Number Error!\"";
			return INPUT;
		}
		logger.info("Exit method getDisputeSearchTotalPageNo.");
		return SUCCESS;
	}
	
	
	//Connection to getPendingPageNoServiceCall
	private String getPendingPageNoServiceCall(){
		logger.info("Enter method getDisputeSearchTotalPageNoServiceCall.");
		String t = null;
		if(testWeb) t = "{totalResultNo:4,totalPageNo:1}";
		else {
			try {
				t = disputeService.getPendingDisputeTotalPageNo(searchDisputeVO);
			} catch (BPLException e) {	
				logger.error("getDisputeSearchTotalPageNo error: ", e);
				t = "{error:\"getDisputeSearchTotalPageNo error: "+e.getMessage()+"\"}";
			}
		}
		return t;
	}
	
	public String getDisputeItemViewListPageNo() throws Exception {
		logger.info("Enter method getDisputeItemViewListPageNo.");
		String result = null;
		try {
			result = disputeService.getDisputeItemViewListPageTotalNo(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("getDisputeItemViewListPageNo error: ", e);
			result = "{error:\"getDisputeItemViewListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getDisputeItemViewListPageNo.");
		return null;
	}	
	
	public String searchDisputeItemList() throws Exception {
		logger.info("Enter method searchDisputeItemList.");
		String result = null;
		try {
			result = disputeService.searchDisputeItem(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchDisputeItemList error: ", e);
			result = "{error:\"searchDisputeItemList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchDisputeItemList.");
		return null;
	}
	
	/**
	 * 获取 Dispute Message 信息。
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String searchDisputeMessageByDisputeIds() throws Exception {
		logger.info("Enter method searchDisputeMessageByDisputeIds.");
		String result = null;
		try {
			disputeList = disputeService.searchDisputeMessageByDisputeIds(ids);
		} catch (BPLException e) {
			logger.error("searchDisputeMessageByDisputeIds error: ", e);
			result = "{error:\"searchDisputeMessageByDisputeIds: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method searchDisputeMessageByDisputeIds.");
		return SUCCESS;
	}
	
	public String searchContactVendorByDisputeIds() throws Exception {
		logger.info("Enter method searchContactVendorByDisputeIds.");
		String result = null;
		try {
			disputeContactVendorEmail = disputeService.searchContactVendorByDisputeIds(ids);
		} catch (BPLException e) {
			logger.error("searchContactVendorByDisputeIds error: ", e);
			result = "{error:\"searchContactVendorByDisputeIds: "+e.getMessage()+"\"}";
			this.writeOutputStream(result);	
		}
		
		logger.info("Exit method searchContactVendorByDisputeIds.");
		return SUCCESS;
	}
	
	public String contactVendorDispute () throws Exception {
		logger.info("Enter method contactVendorDispute.");
		String result = null;
		try{
			AttachmentPoint point = null;
			dispute = disputeService.findDisputeById(Integer.parseInt(disputeMessage.get("disputeId")));
			
			if(dispute.getAttachmentPoint() != null){
				point = dispute.getAttachmentPoint();
			}else{
				point = commonLookupService.createAttachmentPoint("dispute");
				disputeService.upodateDisputeAttachmentPoint(dispute.getId(),point);
			}
			
			if(uploads!=null && uploads.size()>0){
				List<File> filterUploads = new ArrayList<File>();
				List<String> filterUploadsFileName = new ArrayList<String>();
				for(int i=0;i<uploads.size();i++){
					File itemFile = uploads.get(i);
					String fileSize = ""+itemFile.length();
					boolean isExist = false;
					for(int j=0;j<effectiveFile.size();j++){
						String itemString = effectiveFile.get(j);
						String[] itemObject = itemString.split(":");
						if(itemObject != null && itemObject[0] != null && itemObject[1] != null && 
						   itemObject[1].equals(fileSize) && itemObject[0].equals(uploadsFileName.get(i))){
						   isExist = true;
						   effectiveFile.remove(j);
						   break;
						}
					}
					if(isExist){
						filterUploads.add(itemFile);
						filterUploadsFileName.add(uploadsFileName.get(i));
					}
				}
				
				commonLookupService.saveUploadFileToPointId(point,filterUploads, filterUploadsFileName);
				point = commonLookupService.saveUploadFileAndGetPointId("dispute",filterUploads, filterUploadsFileName);
				
			}else{
				point = null;
			}
			//disputeMessage 保存数据和发邮件功能
//		    disputeService.contactVendorDispute(disputeMessage,point);
		    sendEmailService.sendContactVendorDisputeEmail(disputeMessage,point);
		}catch(Exception e){
			logger.error("contactVendorDispute error: ", e);
			return INPUT;
		}

		
		logger.info("Exit method contactVendorDispute.");
		return SUCCESS;
	}
	
	
	
	
	public String forwordDispute() throws Exception {
	//	disputeService.sendEmailToAssignmentUser(disputeMessage);
		logger.info("Enter method forwordDispute.");

		try {
			sendEmailService.sendForwardDisputeEmail(disputeMessage);
		} catch (Exception e) {
			logger.error("forwordDispute error: ", e);
			return INPUT;
		}
		logger.info("Exit method forwordDispute.");
		return SUCCESS;
	}
	
	public String getDisputeReconciliationListPageNo() throws Exception {
		logger.info("Enter method getDisputeReconciliationListPageNo.");
		String result = null;
		try {
			result = disputeService.getDisputeReconciliationListPageTotalNo(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("getDisputeReconciliationListPageNo error: ", e);
			result = "{error:\"getDisputeReconciliationListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getDisputeReconciliationListPageNo.");
		return null;
	}	
	
	public String searchDisputeReconciliationList() throws Exception {
		logger.info("Enter method searchDisputeReconciliationList.");
		String result = null;
		try {
			result = disputeService.searchDisputeReconciliation(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchDisputeReconciliationList error: ", e);
			result = "{error:\"searchDisputeReconciliationList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchDisputeReconciliationList.");
		return null;
	}
	
	public String deleteReconciliationMany() throws Exception {
		logger.info("Enter method deleteReconciliationMany.");
		String result = "{}";
		try {
			result = disputeService.updateReconciliationMany(ids);
		} catch (Exception e) {
			logger.error("deleteReconciliationMany error: ", e);
			result = "{error:\"deleteReconciliationMany error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method deleteReconciliationMany.");
		return null;
	}
	
	public String deleteReconciliation() throws Exception {
		logger.info("Enter method deleteReconciliation.");
		String result = "{}";
		try {
			result = disputeService.updateReconciliation(searchDisputeVO.getReconcileId(),1);
		} catch (Exception e) {
			logger.error("deleteReconciliation error: ", e);
			result = "{error:\"deleteReconciliation error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method deleteReconciliation.");
		return null;
	}
	
	public String downloadDisputeItemExcel() throws Exception {	
		logger.info("Exit method downloadDisputeItemExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "DisputeItem.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeService.getExcelByDisputeItem(searchDisputeVO,excelDirPath,titles);
			this.writeFile(fileName,new FileInputStream(excelPath));
		}catch (Throwable e) {
			logger.error("downloadDisputeItemExcel error: ", e);
		}
		logger.info("Exit method downloadDisputeItemExcel.");
		return null;
	}
		
	public String closeDisputeWin() throws Exception {
		logger.info("Enter method closeDisputeWin.");
		try {
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			if(point != null)searchDisputeVO.setAttachmentPointId(point.getId().toString());
			disputeService.updateDisputeCloseDisputeWin(searchDisputeVO);
		}catch(Exception e){
			logger.error("closeDisputeLose error: ", e);
			jsonResult = "{error:\""+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doOutstandingCreditSplitApply.");
		return SUCCESS;
	}
	
	public String closeDisputeLose() throws Exception {
		logger.info("Enter method closeDisputeLose.");
		try {
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			if(point != null)searchDisputeVO.setAttachmentPointId(point.getId().toString());
			disputeService.updateDisputeCloseDisputeLose(searchDisputeVO);
		}catch(Exception e){
			logger.error("closeDisputeLose error: ", e);
			jsonResult = "{error:\""+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doOutstandingCreditSplitApply.");
		return SUCCESS;
	}
	
	public String splitCloseDispute() throws Exception {
		logger.info("Enter method splitCloseDispute.");
		try {
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			if(point != null)searchDisputeVO.setAttachmentPointId(point.getId().toString());
			disputeService.updateDisputeSplitCloseDispute(searchDisputeVO);
		}catch(Exception e){
			logger.error("splitCloseDispute error: ", e);
			jsonResult = "{error:\""+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doOutstandingCreditSplitApply.");
		return SUCCESS;
	}
	
	/**
	 * search Transaction History List (viewDisputeDetails.js)
	 * @author xinyu.Liu
	 * 
	 */
	public String searchTransactionHistoryList() throws Exception {
		logger.info("Enter method searchTransactionHistoryList.");
		String result = null;
		try {
			result = disputeService.searchTransactionHistory(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchTransactionHistoryList error: ", e);
			result = "{error:\"searchTransactionHistoryList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchTransactionHistoryList.");
		return null;
	}
	
	/**
	 * search Dispute Notes List (viewDisputeDetails.js)
	 * @author wenbo.zhang
	 * 
	 */
	public String searchDisputeNotesList() throws Exception {
		logger.info("Enter method searchDisputeNotesList.");
		String result = null;
		try {
			result = disputeService.searchDisputeNotesList(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchDisputeNotesList error: ", e);
			result = "{error:\"searchDisputeNotesList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchDisputeNotesList.");
		return null;
	}
	
	
	public String getDisputeNotesListPageNo() throws Exception {
		
		logger.info("Enter method getDisputeNotesListPageNo.");
		String result = null;
		try {
			result = disputeService.getDisputeNotesListPageNo(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("getDisputeNotesListPageNo error: ", e);
			result = "{error:\"getDisputeNotesListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getDisputeNotesListPageNo.");
		return null;
	}
	
	
	public String doSaveDisputeNote() throws Exception {
		logger.info("Enter method doSaveDisputeNote.");
		try {
		disputeService.saveDisputeNote(disputeId,noteNotes);	
		} catch (Exception e) {
			logger.error("doSaveDisputeNote error: ", e);
			noteMessage = "doSaveDisputeNote error: " + e;
			return INPUT;
		}
		return SUCCESS;
	}

	/**
	 * 保存 Dispute Action Request.
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String saveDisputeActionRequest() throws Exception {
		
		logger.info("Enter method saveDisputeActionRequest.");
		String result = null;
		try {
			disputeService.saveDisputeActionRequest(actionRequestNotes, disputeId,disputeActionRequestStatus);	
			result = "{\"success\": \"success\"}";
		} catch (Exception e) {
			logger.error("saveDisputeActionRequest error: ", e);
			result = "{error:\"saveDisputeActionRequest error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveDisputeActionRequest.");
		return null;
	}

	
	public String getDisputeActionRequestListPageNo() throws Exception {
		
		logger.info("Enter method getDisputeActionRequestListPageNo.");
		String result = null;
		try {
			result = disputeService.getDisputeActionRequestListPageNo(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("getDisputeActionRequestListPageNo error: ", e);
			result = "{error:\"getDisputeActionRequestListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getDisputeActionRequestListPageNo.");
		return null;
	}
	
	public String getDisputeActionRequestListPageNoByBanId() throws Exception {
			
			logger.info("Enter method getDisputeActionRequestListPageNoByBanId.");
			String result = null;
			try {
				result = disputeService.getDisputeActionRequestListPageNoByBanId(searchDisputeVO);
			} catch (BPLException e) {
				logger.error("getDisputeActionRequestListPageNoByBanId error: ", e);
				result = "{error:\"getDisputeActionRequestListPageNoByBanId error: "+e.getMessage()+"\"}";
			}
		    this.writeOutputStream(result);	
			logger.info("Exit method getDisputeActionRequestListPageNoByBanId.");
			return null;
		}
	
	/**
	 * 查询 dispute reply.
	 * @return [description]
	 * @throws Exception     [description]
	 */
    public String searchDisputeReplyList() throws Exception {
		
		logger.info("Enter method searchDisputeReplyList.");
		String result = null;
		try {
			result = disputeService.searchDisputeReplyList(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchDisputeReplyList error: ", e);
			result = "{error:\"searchDisputeReplyList error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method searchDisputeReplyList.");
		return null;
	}
    
    /**
     * 删除 dispute reply.
     * @return [description]
     * @throws Exception     [description]
     */
    public String deleteDisputeReply() throws Exception {
    	logger.info("Enter method deleteDisputeReply.");
		String result = null;
		try {
			disputeService.deleteDisputeReply(searchDisputeVO);
			result = "{success:\"success\"}";
		} catch (BPLException e) {
			logger.error("deleteDisputeReply error: ", e);
			result = "{error:\"deleteDisputeReply error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method deleteDisputeReply.");
    	return null;
	}
    
    public String addReplyNote() throws Exception {
    	logger.info("Enter method addReplyNote.");
		String result = null;
		try {
			disputeService.addReplyNote(searchDisputeVO,noteNotes);
			result = "{success:\"success\"}";
		} catch (BPLException e) {
			logger.error("addReplyNote error: ", e);
			result = "{error:\"addReplyNote error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method addReplyNote.");
    	return null;
	}
    
    public String updateDisputeActionRequestStatus() throws Exception {
    	logger.info("Enter method updateDisputeActionRequestStatus.");
		String result = null;
		try {
			disputeService.updateDisputeActionRequestStatus(searchDisputeVO,disputeActionRequestStatus);
			result = "{success:\"success\"}";
		} catch (BPLException e) {
			logger.error("updateDisputeActionRequestStatus error: ", e);
			result = "{error:\""+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method updateDisputeActionRequestStatus.");
    	return null;
	}
	
	
	
	public String searchDisputeActionRequestList() throws Exception {
		logger.info("Enter method searchDisputeActionRequestList.");
		String result = null;
		try {
			result = disputeService.searchDisputeActionRequestList(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchDisputeActionRequestList error: ", e);
			result = "{error:\"searchDisputeActionRequestList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchDisputeActionRequestList.");
		return null;
	}
	
	public String searchDisputeActionRequestListByBanId() throws Exception {
		logger.info("Enter method searchDisputeActionRequestListByBanId.");
		String result = null;
		try {
			result = disputeService.searchDisputeActionRequestListByBanId(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchDisputeActionRequestList error: ", e);
			result = "{error:\"searchDisputeActionRequestListByBanId: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchDisputeActionRequestListByBanId.");
		return null;
	}
	
	/**
	 * download Transaction History Excel (viewDisputeDetails.js)
	 * @author xinyu.Liu
	 * 
	 */
	public String downloadTransactionExcel() throws Exception {	
		logger.info("Exit method downloadTransactionExcel.");
		try {
			String fileName = "Transaction.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeService.getExcelByTransaction(searchDisputeVO,excelDirPath,titles);
			this.writeFile(fileName,new FileInputStream(excelPath));
		}catch (Throwable e) {
			logger.error("downloadTransactionExcel error: ", e);
		}
		logger.info("Exit method downloadTransactionExcel.");
		return null;
	}
	
	public String addTransactionHistoryData() throws Exception {
		logger.info("Enter method addTransactionHistoryData.");
		try {
			
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
			
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("transaction history",uploads,uploadsFileName);
			if(point != null)searchDisputeVO.setAttachmentPointId(point.getId().toString());
			disputeService.updateTransactionHistory(searchDisputeVO,point);
		}catch(Exception e){
			logger.error("addTransactionHistoryData error: ", e);
			return INPUT;
		}
		logger.info("Exit method addTransactionHistoryData.");
		return SUCCESS;
	}
	
	public String addOutstandingDisputeItemsSCOA() throws Exception {
		logger.info("Enter method addOutstandingDisputeItemsSCOA.");
		String result = "{}";
		try {
			disputeService.addOutstandingDisputeItemsSCOA(searchDisputeVO);
		} catch (Exception e) {
			logger.error("addOutstandingDisputeItemsSCOA error: ", e);
			result = "{error:\"addOutstandingDisputeItemsSCOA error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method addOutstandingDisputeItemsSCOA.");
		return null;
	}

	public String addReconcileSCOA() throws Exception {
		logger.info("Enter method addReconcileSCOA.");
		String result = "{}";
		try {
			disputeService.addReconcileSCOA(searchDisputeVO);
		} catch (Exception e) {
			logger.error("addReconcileSCOA error: ", e);
			result = "{error:\"addReconcileSCOA error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method addReconcileSCOA.");
		return null;
	}
	
	public String getAccessoriesListPageNo() throws Exception {
		logger.info("Enter method getAccessoriesListPageNo.");
		String result = null;
		try {
			result = disputeService.getAccessoriesListPageTotalNo(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("getAccessoriesListPageNo error: ", e);
			result = "{error:\"getAccessoriesListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getAccessoriesListPageNo.");
		return null;
	}
	
	public String searchAccessoriesList() throws Exception {
		logger.info("Enter method searchAccessoriesList.");
		String result = null;
		try {
			result = disputeService.searchAttachmentFile(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("searchAccessoriesList error: ", e);
			result = "{error:\"searchAccessoriesList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchAccessoriesList.");
		return null;
	}
	
	public String delDisputeAttachmentFile() throws Exception {
		logger.info("Enter method delDisputeAttachmentFile.");
		String result = "{}";
		try {
			disputeService.updateDisputeAttachmentFile(searchDisputeVO);
		} catch (BPLException e) {
			logger.error("delDisputeAttachmentFile error: ", e);
			result = "{error:\"delDisputeAttachmentFile error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method delDisputeAttachmentFile.");
		return null;
	}
	
	public String doRefileDisputeAttachFile() throws Exception {
		logger.info("Enter method doRefileDisputeAttachFile.");
		try{
			if("null".equals(searchDisputeVO.getAttachmentPointId())){
				AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("transaction_history",uploads,uploadsFileName);
				disputeService.upodateDisputeAttachmentPoint(searchDisputeVO.getDisputeId(),point);
				this.attachmentPointId = point.getId();
			}else{
				AttachmentPoint attachmentPoint = disputeService.findAttachmentPoint(Integer.parseInt(searchDisputeVO.getAttachmentPointId()));
				commonLookupService.saveUploadFileToPointId(attachmentPoint, uploads, uploadsFileName);
				this.attachmentPointId = Integer.parseInt(searchDisputeVO.getAttachmentPointId());
			}
		}catch(Exception e){
			logger.error("doRefileDisputeAttachFile error: ", e);
			uploadsMessage = "doRefileDisputeAttachFile Error!";
			return INPUT;
		}
		logger.info("Exit method doRefileDisputeAttachFile.");
		return SUCCESS;
	}
	
	/**
	 * update Dispute and add Transaction History (viewDisputeReconciliation.js)
	 * 
	 * @author xinyu.Liu
	 */
	public String updateRefileDispute() throws Exception {
		logger.info("Enter method updateRefileDispute.");
		String result = "{}";
		try {
			disputeService.updateDispute(searchDisputeVO);
		} catch (Exception e) {
			logger.error("updateRefileDispute error: ", e);
			result = "{error:\"updateRefileDispute error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateRefileDispute.");
		return null;
	}
	
	/**
	 * update Dispute and add Transaction History (creditTab.js)
	 * 
	 * @author dongjian
	 */
	public String updateRefileDisputeByProposal() throws Exception {
		logger.info("Enter method updateRefileDisputeByProposal.");
		String result = "{}";
		try {
			disputeService.updateRefileDisputeByProposal(ids);
		} catch (Exception e) {
			logger.error("updateRefileDisputeByProposal error: ", e);
			result = "{error:\"updateRefileDisputeByProposal error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateRefileDisputeByProposal.");
		return null;
	}
	
	/**
	 * update Cancel Refile Dispute and add Transaction History (viewDisputeReconciliation.js)
	 * 
	 * @author xinyu.Liu
	 */
	public String updateCancelRefileDispute() throws Exception {
		logger.info("Enter method updateCancelRefileDispute.");
		String result = "{}";
		try {
			disputeService.updateCancelDispute(searchDisputeVO);
		} catch (Exception e) {
			logger.error("updateCancelRefileDispute error: ", e);
			result = "{error:\"updateCancelRefileDispute error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateCancelRefileDispute.");
		return null;
	}
	
	public void setDisputeService(IDisputeService disputeService) {
		this.disputeService = disputeService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public SearchDisputeVO getSearchDisputeVO() {
		return searchDisputeVO;
	}

	public void setSearchDisputeVO(SearchDisputeVO searchDisputeVO) {
		this.searchDisputeVO = searchDisputeVO;
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
	
	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public List<MapEntryVO<String, String>> getDisputeReasonList() {
		return disputeReasonList;
	}

	public List<MapEntryVO<String, String>> getDisputeStatusList() {
		return disputeStatusList;
	}
	
	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public List<MapEntryVO<String, String>> getCreatedByList() {
		return createdByList;
	}

	public Dispute getDispute() {
		return dispute;
	}

	public void setDispute(Dispute dispute) {
		this.dispute = dispute;
	}

	public Credit getCredit() {
		return credit;
	}

	public void setCredit(Credit credit) {
		this.credit = credit;
	}

	public Reconcile getReconcile() {
		return reconcile;
	}

	public void setReconcile(Reconcile reconcile) {
		this.reconcile = reconcile;
	}

	public SearchVO getVo() {
		return vo;
	}

	public void setVo(SearchVO vo) {
		this.vo = vo;
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

	public List<MapEntryVO<String, String>> getAccountCodeList() {
		return accountCodeList;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public IDisputeService getDisputeService() {
		return disputeService;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public void setDisputeReasonList(
			List<MapEntryVO<String, String>> disputeReasonList) {
		this.disputeReasonList = disputeReasonList;
	}

	public void setDisputeStatusList(
			List<MapEntryVO<String, String>> disputeStatusList) {
		this.disputeStatusList = disputeStatusList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

	public void setCreatedByList(List<MapEntryVO<String, String>> createdByList) {
		this.createdByList = createdByList;
	}

	public void setAccountCodeList(List<MapEntryVO<String, String>> accountCodeList) {
		this.accountCodeList = accountCodeList;
	}

	public String getDisputeId() {
		return disputeId;
	}

	public void setDisputeId(String disputeId) {
		this.disputeId = disputeId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getAttachmentPointId() {
		return attachmentPointId;
	}
	
	public String getDisputeAttachmentDownloadFlag() {
		return disputeAttachmentDownloadFlag;
	}

	public void setDisputeAttachmentDownloadFlag(
			String disputeAttachmentDownloadFlag) {
		this.disputeAttachmentDownloadFlag = disputeAttachmentDownloadFlag;
	}

	public void setAttachmentPointId(Integer attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getReserveType() {
		return reserveType;
	}

	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}

	public Integer getAccountCodeId() {
		return accountCodeId;
	}

	public void setAccountCodeId(Integer accountCodeId) {
		this.accountCodeId = accountCodeId;
	}

	public List<Map<String, Object>> getDisputeList() {
		return disputeList;
	}

	public void setDisputeList(List<Map<String, Object>> disputeList) {
		this.disputeList = disputeList;
	}

	public Map<String, String> getDisputeMessage() {
		return disputeMessage;
	}

	public void setDisputeMessage(Map<String, String> disputeMessage) {
		this.disputeMessage = disputeMessage;
	}

	public String getNoteNotes() {
		return noteNotes;
	}

	public void setActionRequestNotes(String actionRequestNotes) {
		this.actionRequestNotes = actionRequestNotes;
	}

	public String getActionRequestNotes() {
		return actionRequestNotes;
	}

	public void setNoteNotes(String noteNotes) {
		this.noteNotes = noteNotes;
	}

	public ISendEmailService getSendEmailService() {
		return sendEmailService;
	}

	public void setSendEmailService(ISendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	}

	public Map<String, Object> getDisputeContactVendorEmail() {
		return disputeContactVendorEmail;
	}

	public void setDisputeContactVendorEmail(
			Map<String, Object> disputeContactVendorEmail) {
		this.disputeContactVendorEmail = disputeContactVendorEmail;
	}

	public List<String> getEffectiveFile() {
		return effectiveFile;
	}

	public void setEffectiveFile(List<String> effectiveFile) {
		this.effectiveFile = effectiveFile;
	}

	public String getNoteMessage() {
		return noteMessage;
	}

	public void setNoteMessage(String noteMessage) {
		this.noteMessage = noteMessage;
	}

	public String getDisputeActionRequestStatus() {
		return disputeActionRequestStatus;
	}

	public void setDisputeActionRequestStatus(String disputeActionRequestStatus) {
		this.disputeActionRequestStatus = disputeActionRequestStatus;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}

}
