/**
 * 
 */
package com.saninco.ccm.action.invoice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.AuditMemory;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceItem;
import com.saninco.ccm.model.InvoiceStatus;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.email.ISendEmailService;
import com.saninco.ccm.service.invoice.IDisputeTabService;
import com.saninco.ccm.service.invoice.IInvoiceDetailService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;


public class InvoiceDetailAction extends CcmActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IQuicklinkService quicklinkService;
	private ICommonLookupService commonLookupService;
	private IInvoiceDetailService invoiceDetailService;
	private IDisputeTabService disputeTabService;
	private ISendEmailService sendEmailService;
	// by hongtao 2011-09-19 
	private String ticketNumber;
	private String claimNumber;
	private String itemId;
	private String labelId;
	private String chestrId;
	private String proposalId;
	private String payVendor;
	private String inco;
	private String tnco;
	private String paynco;
	private String mscid;
	private String invoiceTaxAndOtherFees;
	private String mSortingDirection;
	private String mPaymentAmount;
	private String mAccountCodeId;
	private String invoicePayIdd;
	private String successMsg = "";
	private String inputMsg = "";
	
	private String balanceAmount;
	private Integer accountCodeId;
	private String disputeIds;
	private Integer reconcileId;
	private String inProposalIds;
	private String inIds;
	private String jsonResult;
	private String autopaySwitch;
	
	private String banAuditFlag;
	
	private String invoiceDisputeTabId;
	private int proPaslId;
 
	private boolean bo;
	private String disputeAmount;
	//save proposal in disputeTab
	private String categoryId;
	private String note;
	private String proposalNote;
	private String proposalDiscription;
	private String originatorId;
	private String sCOA;
	private String cricuitNumber;
	private String serviceType;
	private String[] proposalIds;
	private int disputeId;
	private int fromDisputeId;
	private String disputeIdRight;
	private int disputeReasonId;
	private int disputeStatusId;
	private String recPerPage;
	private String handleWorkflowResult;
	private String flagShortpaid;
	private String flagRecurring;
	private String disputeNotes;
	private String proposalData;
	private String confidenceLevel;
	private String emailCopyTo;
	private String totalDisputeAmount;
	private String attachmentPointId;
	private String disputeReasonText;
	private String reservedAmount;
	private List<String> titles;
	private String title;
	private String paramStr;
	
	private String currentAnalystId;
	private String currentAssignmentId;
	private String oldInvoiceStatusId;
	private String invoiceId;
	private String auditSourceType;
	private String invoiceItemId;
	private String workflowUserName;
	
	private String banExemptionFlag;
	
	private String openAutoPay;
	
	private SearchInvoiceVO svo;
	private SearchInvoiceVO pvo;
	private SearchInvoiceVO searchInvoiceVO;
	
	private List<String> effectiveFile;
	private String approveWinUserId;
	private String approveWinRoleId;
	private String itemType;
	
	// Used to receive uploaded scoa data error 
	// file path.
	private String errorFileName;
	
	private Invoice invoice = new Invoice();
	private InvoiceStatus invoiceStatus = new InvoiceStatus();
	private Integer banId;
	private User assignedAnalys = new User();
	private InvoiceItem invoiceItem = new InvoiceItem();
	private Map<String, Object> auditIcon = new HashMap<String, Object>();

	// Autopay error notes
	private List<HashMap<String, String>> autopayErrorNotes = new ArrayList<HashMap<String,String>>();

	private Map<String, Object> memory = new HashMap<String, Object>();
	private User user = new User();
	
	private AuditMemory auditMemory;
	
	private Map<String,Object> returnMap;
	private List<File> uploadSCOAs;
	private List<String> uploadSCOAsFileName;
	
//	private List<String> labelList = new ArrayList<String>();
	private List<MapEntryVO<String, String>> allLabelList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> scoaCodeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> relatedInvoicesList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> accountCodeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> disputeReasonList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> originatorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> disputeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> disputeStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<HashMap<String, String>>  invoiceLabelList = new ArrayList<HashMap<String, String>>();
	private List<Map<String,String>> toleranceRateList = new ArrayList<Map<String,String>>();
	private List<MapEntryVO<String, String>> productList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> productComponentList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> provinceList = new ArrayList<MapEntryVO<String, String>>();
	private List<HashMap<String, String>> banExemptionDateList = new ArrayList<HashMap<String, String>>();

	/**
	 * 
	 */
	private Integer workflowActionId;
	private List<File> uploads;
	private List<String> uploadsFileName;
	private List<String> invoiceSummaryUploads;
	private String uploadsMessage;
	private String emailFlag;
	private String isShowDueDate;
	private List<Map<String, Object>> validationResultList =
		new ArrayList<Map<String, Object>>();
	private Map<String, Object> dataResult = new HashMap<String, Object>();
	private Map<String,String> invoiceMessage;
	private String invoiceSummaryFileName = "Invoice Summary.xls";
	public  String isUsageBan = "N";
	private int term = 3;
	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@AllGranted(value="FUNCTION_1100")
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		boolean bo;
		try {
			bo = populateInvoiceSearchLookupList();
			this.quicklinkList = quicklinkService.getUserQuicklinks();
			commonLookupService.saveInvoiceUserActionLog(Integer.parseInt(invoiceId));		
		} catch (RuntimeException e) {
			logger.error("",e);
			bo = false;
		}
		logger.info("Exit method exec.");
		if(bo) return SUCCESS;
		return INPUT;
	}
	
	/**
	 * Dispose uploaded scoa data
	 */
	public String uploadSCOAData() throws Exception {

		logger.info("Enter method uploadSCOAData.");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		// Upload file names.
		String previewFileName = df.format(new Date())+"_"+uploadSCOAsFileName.get(uploadSCOAsFileName.size()-1);
		
		// Error SCOA Data excel file path.
		String errorExcelDirPath = request.getSession().getServletContext().getRealPath("/")+ "previews\\" + previewFileName;
		
		// Dispose request.
		Map<String,String> map = invoiceDetailService.uploadSCOAData( uploadSCOAs.get(uploadSCOAs.size()-1), 
					uploadSCOAsFileName.get(uploadSCOAsFileName.size()-1), errorExcelDirPath, invoiceId );
		
		// Returned map.
		returnMap = new HashMap<String,Object>();
		
		// Validation map information.
		if(map.get("isSuccess") == null || "".equals(map.get("isSuccess"))){
			
			returnMap.put("validationFailure", "Y");
		}else{
			
			returnMap.put("validationFailure", "N");
		}
		
		returnMap.put("isSuccess", map.get("isSuccess")); // Status.
		returnMap.put("errorFilePath", previewFileName); // Error file path.
		
		logger.info("Exit method uploadSCOAData.");
		
		return SUCCESS;
		
	}
	
	/**
	 * Excel 模版
	 * @return
	 * @throws Exception
	 */
	public String creatExcelByTemplate() throws Exception{
		logger.info("Exit method creatExcelByTemplate.");
		FileInputStream fis = null;
		try {
			String fileName = "SCOA Template.xls";

			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.creatExcelByTemplate(excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("creatExcelByTemplate error: ", e);
		}
		logger.info("Exit method creatExcelByTemplate.");
		return null;
	}
	
	/**
	 * Download uploaded scoa data error file.
	 */
	public String downloadSCOADataErrorFile()throws Exception{
		
		logger.info("Exit method downloadSCOADataErrorFile.");
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(request.getSession().getServletContext().getRealPath("/")+ "previews\\" + errorFileName);
			
			writeFile("SCOA data Import Errors.xls",fis);
			
		}catch (Throwable e) {
			logger.error("downloadSCOADataErrorFile error: ", e);
		}
		
		logger.info("Exit method downloadSCOADataErrorFile.");
		return null;
		
	}
	
	/**
	 * This is the Label of the LIst
	 * */
	private boolean populateInvoiceSearchLookupList() throws BPLException {
		logger.info("Enter method populateInvoiceSearchLookupList.");
		boolean bo = false;
		
		if(invoiceId != null){
			invoice = invoiceDetailService.getInvoiceByInvoiceId(invoiceId);
			invoiceLabelList = invoiceDetailService.getInvoiceLabelsByInvoiceId(invoiceId);
			auditIcon = invoiceDetailService.searchInvoiceAuditStatus(invoiceId);
			
			// Get autopay error notes.
			autopayErrorNotes = invoiceDetailService.searchAutopayErrorNotes(invoiceId);
			
			banAuditFlag = invoiceDetailService.searchBanAuditFlag(invoiceId);
			autopaySwitch = invoiceDetailService.autopaySwitch(invoiceId); 
			toleranceRateList = invoiceDetailService.getToleranceRateList(invoiceId);
			banExemptionFlag = invoiceDetailService.searchBanExemption(invoiceId);
			banExemptionDateList = invoiceDetailService.searchBanExemptionDate(invoiceId);
			openAutoPay = invoiceDetailService.searchBanOpenAutoPay(invoiceId);
			if(invoice.getBan().getAccountNumber().equals("28321") || invoice.getBan().getAccountNumber().equals("68242527") 
					|| invoice.getBan().getAccountNumber().equals("000080000071") || invoice.getBan().getAccountNumber().equals("8191106257")
					 || invoice.getBan().getAccountNumber().equals("418FD84802")){
				isUsageBan = "Y";
			}else if(invoice.getBan().getAccountNumber().equals("M059")){
				isUsageBan = invoiceDetailService.searchIsUasgeBan(invoiceId);
			}else{
				isUsageBan = "N";
			}
			if(invoiceDetailService.getBanDisputeDueDate(Integer.parseInt(invoiceId)) ||
			   invoiceDetailService.getInvoiceDisputeStatusCloseAndActionRequestOpenCount(invoice)){
				isShowDueDate = "true";
			}else {
				isShowDueDate = "false";
			}
		}
		
		if(invoiceItemId != null){
			invoice = invoiceDetailService.getInvoiceInvoiceItem(invoiceItemId);
			invoiceItem = invoiceDetailService.getInvoiceItemByInvoiceItemId(invoiceItemId);
			invoiceId = invoice.getId().toString();
		}
		
		if(invoice.getInvoiceStatus().getId() >= 9 ){
			if(invoice.getInvoiceStatus().getId() == 9){
				invoiceDetailService.updateInvoiceStatusId(invoice);
			}
			assignedAnalys = invoiceDetailService.getAssignedAnalys(assignedAnalys, invoice.getAssignedAnalystId());
			relatedInvoicesList = invoiceDetailService.getRelatedInvoices(invoiceId);
//			labelList = invoiceDetailService.searchInvoiceLabelList(invoice);
			
			allLabelList = commonLookupService.getLabels();
			disputeReasonList = commonLookupService.getDisputeReason();
			originatorList=commonLookupService.getAllOriginators();
			disputeList=commonLookupService.getDisputeByInvoiceId(Integer.valueOf(invoiceId));
			disputeStatusList=commonLookupService.getDisputeStatus();
			invoiceTaxAndOtherFees = invoiceDetailService.addDouble(invoice.getInvoiceTax(), invoice.getInvoiceSurcharge(), invoice.getInvoiceRegulationFee()).toString();
			scoaCodeList = commonLookupService.getAccountCode();
			bo = true;
		}
		logger.info("Exit method populateInvoiceSearchLookupList.");
		return bo;
	}
	
	public String updateBanAutoPay() throws Exception {
		logger.info("Enter method updateBanAutoPay.");
		String result = "";
		try {
			invoiceDetailService.updateBanAutoPay(banId);
		    result = "{data:true}";
		} catch (BPLException e) {
			logger.error("updateBanAutoPay error: ", e);
			result = "{error:\"updateBanAutoPay error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateBanAutoPay.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * Through the user id verification authority
	 * */
	public String doValiUserPrivilegeByUserId() throws Exception {
		logger.info("Enter method doValiUserPrivilegeByUserId.");
		String result = "{data:false}";
		try {
			boolean bo = commonLookupService.validateBackupAndDelegation(currentAssignmentId);
			if(bo) result = "{data:true}";
		} catch (BPLException e) {
			logger.error("doValiUserPrivilegeByUserId error: ", e);
			result = "{error:\"doValiUserPrivilegeByUserId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method doValiUserPrivilegeByUserId.");
		return null;
	}

	/**
	 * @author dongjian
	 * Update the SCOA dispute
	 * */
	public String addOutstandingDisputeSCOA() throws Exception {
		logger.info("Enter method addOutstandingDisputeSCOA.");
		String result = "{}";
		try {
			invoiceDetailService.addOutstandingDisputeSCOA(inProposalIds,sCOA,note);
		} catch (Exception e) {
			logger.error("addOutstandingDisputeSCOA error: ", e);
			result = "{error:\"addOutstandingDisputeSCOA error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method addOutstandingDisputeSCOA.");
		return null;
	}
	/**
	 * @author dongjian
	 * Update the SCOA dispute more
	 * */
	public String addOutstandingDisputeSCOAAll() throws Exception {
		logger.info("Enter method addOutstandingDisputeSCOAAll.");
		String result = "{}";
		try {
			invoiceDetailService.addOutstandingDisputeSCOAAll(inProposalIds,sCOA,note);
		} catch (Exception e) {
			logger.error("addOutstandingDisputeSCOAAll error: ", e);
			result = "{error:\"addOutstandingDisputeSCOAAll error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method addOutstandingDisputeSCOAAll.");
		return null;
	}
	/**
	 * @author dongjian
	 * Verify the BAN
	 * */
	public String doValiUserPrivilegeBanLock() throws Exception {
		logger.info("Enter method doValiUserPrivilegeBanLock.");
		String result = "{data:false}";
		try {
			Invoice invoice = invoiceDetailService.getInvoiceByInvoiceId(invoiceId);
			Vendor vendor = invoice.getVendor();
			Ban ban = invoice.getBan();
			boolean bo = commonLookupService.validatePreviledgeByVendorAndBan(ban, vendor);
			if(bo) result = "{data:true}";
		} catch (BPLException e) {
			logger.error("doValiUserPrivilegeBanLock error: ", e);
			result = "{error:\"doValiUserPrivilegeBanLock error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method doValiUserPrivilegeBanLock.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * Download the CloseAsDisputeLose page Excel
	 */
	public String saveCloseAsDisputeLosePageExcel()throws Exception{
		logger.info("Exit method saveCloseAsDisputeLosePageExcel.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "CloseAsDisputeLost - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.saveCloseAsDisputeLosePageExcel(svo,excelDirPath,titles, invoiceNumber);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveCloseAsDisputeLosePageExcel error: ", e);
		}
		logger.info("Exit method saveCloseAsDisputeLosePageExcel.");
		return null;
	}
	/**
	 * @author wenbo
	 * Download the CloseAsDisputeWin page Excel
	 */
	public String saveCloseAsDisputeWinPageExcel()throws Exception{
		logger.info("Exit method saveCloseAsDisputeWinPageExcel.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "CloseAsDisputeWin - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.saveCloseAsDisputeWinPageExcel(svo,excelDirPath,titles, invoiceNumber);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveCloseAsDisputeWinPageExcel error: ", e);
		}
		logger.info("Exit method saveCloseAsDisputeWinPageExcel.");
		return null;
	}
	/**
	 * @author dongjian
	 * Download the Reconciliation Excel
	 */
	public String saveTheInvoiceReconciliationPageExcel()throws Exception{
		logger.info("Exit method saveTheInvoiceReconciliationPageExcel.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "InvoiceReconciliation-" + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.getTheInvoiceReconciliationPageExcel(svo,excelDirPath,titles, invoiceNumber);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveTheInvoiceReconciliationPageExcel error: ", e);
		}
		logger.info("Exit method saveTheInvoiceReconciliationPageExcel.");
		return null;
	}
	/**
	 * @author dongjian
	 * Download the Outstanding Credit Excel
	 */
	public String saveOutstandingCreditPageExcel()throws Exception{
		logger.info("Exit method saveOutstandingCreditPageExcel.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "OutstandingCredit - "+ invoiceNumber +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.getOutstandingCreditPageExcel(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveOutstandingCreditPageExcel error: ", e);
		}
		logger.info("Exit method saveOutstandingCreditPageExcel.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * Download the Outstanding Dispute Excel
	 */
	public String saveNotReconcileDisputePageExcel()throws Exception{
		logger.info("Exit method saveNotReconcileDisputePageExcel.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "OutstandingDispute - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.getNotReconcileDisputePageExcel(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveNotReconcileDisputePageExcel error: ", e);
		}
		logger.info("Exit method saveNotReconcileDisputePageExcel.");
		return null;
	}
	
	/**
	 * @author Chao.Liu
	 * This is Admin Page Tab Two
	 * @return
	 * @throws Exception
	 */
	public String saveInvoiceReject()throws Exception {
		logger.info("Enter method saveInvoiceReject.");
		String result = null;
		try{
			result = invoiceDetailService.saveInvoiceReject(searchInvoiceVO);
		}catch(Exception e){
			logger.error("saveInvoiceReject error: ", e);
			result = "{error:\"saveInvoiceReject error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method saveInvoiceReject.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * one credit Reconcile with one dispute
	 * */
	public String doReconcileByCurrentCredit11() throws Exception {
		logger.info("Enter method doReconcileByCurrentCredit11.");
		try{
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			jsonResult = invoiceDetailService.createReconcileByCurrentCredit11(new Long(inProposalIds),new Integer(invoiceId),new Long(disputeIds),point,uploadsMessage,new Double(balanceAmount),accountCodeId);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doReconcileByCurrentCredit11 error: ", e);
			jsonResult = "{error:\"Error: "+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doReconcileByCurrentCredit11.");
		return SUCCESS;
	}

	/**
	 * 查询validation result 的相关信息。
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String searchValidationResult() throws Exception {
		logger.info("Enter method searchValidationResult.");
		
		validationResultList = invoiceDetailService.searchValidationResult(proposalId, invoiceId, auditSourceType, cricuitNumber);
		productComponentList = invoiceDetailService.getSearchProductComponentList();
		provinceList = invoiceDetailService.getSearchProvinceList();
		
		if(proposalId!=null && !"".equals(proposalId) && !"null".equals(proposalId)){
			itemType = invoiceDetailService.getItemTypeByProposalId(proposalId);
			productList = invoiceDetailService.getSearchProductList(proposalId);
			memory = invoiceDetailService.searchAuditMemory(proposalId);
		}
			
		logger.info("Exit method searchValidationResult.");
		return SUCCESS;
	}
	
	public String saveMemory() throws Exception {
		logger.info("Enter method saveMemory.");
		
		String result = "";
		String auditMemoryId = null;
		try{
			auditMemoryId = invoiceDetailService.saveMemory(auditMemory);
			result = "{auditMemoryId:"+auditMemoryId+"}";
		}catch(Exception e){
			logger.error("saveMemory error: ", e);
			result = "{error:\"saveMemory error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
			
		logger.info("Exit method saveMemory.");
		return null;
	}
	
	public String updateMemory() throws Exception {
		logger.info("Enter method updateMemory.");
		
		String result = "";
		try{
			invoiceDetailService.updateMemory(auditMemory);
			result = "{success:\"success\"}";
		}catch(Exception e){
			logger.error("updateMemory error: ", e);
			result = "{error:\"updateMemory error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
			
		logger.info("Exit method updateMemory.");
		return null;
	}

	/**
	 * @author dongjian
	 *  one credit Reconcile with more dispute
	 * */
	public String doReconcileByCurrentCredit1M() throws Exception {
		logger.info("Enter method doReconcileByCurrentCredit1M.");
		try{
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			jsonResult = invoiceDetailService.createReconcileByCurrentCredit1M(new Long(inProposalIds),new Integer(invoiceId),disputeIds,point,uploadsMessage,accountCodeId);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doReconcileByCurrentCredit1M error: ", e);
			jsonResult = "{error:\"Error: "+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doReconcileByCurrentCredit1M.");
		return SUCCESS;
	}
	
	/**
	 * @author dongjian
	 *  more credit Reconcile with one dispute
	 * */
	public String doReconcileByCurrentCreditM1() throws Exception {
		logger.info("Enter method doReconcileByCurrentCreditM1.");
		try{
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			jsonResult = invoiceDetailService.createReconcileByCurrentCreditM1(inProposalIds,new Integer(invoiceId),new Long(disputeIds),point,uploadsMessage,accountCodeId);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doReconcileByCurrentCreditM1 error: ", e);
			jsonResult = "{error:\"Error: "+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doReconcileByCurrentCreditM1.");
		return SUCCESS;
	}
	
	/**
	 * @author dongjian
	 * search Reconciliation
	 * */
	public String getTheInvoiceReconciliationPageData() throws Exception {
		logger.info("Enter method getTheInvoiceReconciliationPageData.");
		String result = null;
		try{
			result = invoiceDetailService.getTheInvoiceReconciliationPageData(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getTheInvoiceReconciliationPageData error: ", e);
			result = "{error:\"getTheInvoiceReconciliationPageData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTheInvoiceReconciliationPageData.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Reconciliation total no.
	 * */
	public String getTheInvoiceReconciliationPageTotalNo() throws Exception {
		logger.info("Enter method getTheInvoiceReconciliationPageTotalNo.");
		String result = null;
		try{
			result = invoiceDetailService.getTheInvoiceReconciliationPageTotalNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getTheInvoiceReconciliationPageTotalNo error: ", e);
			result = "{error:\"getTheInvoiceReconciliationPageTotalNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTheInvoiceReconciliationPageTotalNo.");
		return null;
	}
	
	
	/**
	 * @author dongjian
	 * search outstanding dispute 
	 * */
	public String getNotReconcileDisputePageData() throws Exception {
		logger.info("Enter method getNotReconcileDisputePageData.");
		String result = null;
		try{
			result = invoiceDetailService.getNotReconcileDisputePageData(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getNotReconcileDisputePageData error: ", e);
			result = "{error:\"getNotReconcileDisputePageData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getNotReconcileDisputePageData.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search outstanding dispute total no.
	 * */
	public String getNotReconcileDisputePageTotalNo() throws Exception {
		logger.info("Enter method getNotReconcileDisputePageTotalNo.");
		String result = null;
		try{
			result = invoiceDetailService.getNotReconcileDisputePageTotalNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getNotReconcileDisputePageTotalNo error: ", e);
			result = "{error:\"getNotReconcileDisputePageTotalNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getNotReconcileDisputePageTotalNo.");
		return null;
	}
	/**
	 * @author dongjian
	 * search Close As Dispute Lose
	 * */
	public String getCloseAsDisputeLosePageData() throws Exception {
		logger.info("Enter method getCloseAsDisputeLosePageData.");
		String result = null;
		try{
			result = invoiceDetailService.getCloseAsDisputeLosePageData(svo);
		}catch(Exception e){
			logger.error("getCloseAsDisputeLosePageData error: ", e);
			result = "{error:\"getCloseAsDisputeLosePageData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCloseAsDisputeLosePageData.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Close As Dispute Lose total no.
	 * */
	public String getCloseAsDisputeLosePageTotalNo() throws Exception {
		logger.info("Enter method getCloseAsDisputeLosePageTotalNo.");
		String result = null;
		try{
			result = invoiceDetailService.getCloseAsDisputeLosePageTotalNo(svo);
		}catch(Exception e){
			logger.error("getCloseAsDisputeLosePageTotalNo error: ", e);
			result = "{error:\"getCloseAsDisputeLosePageTotalNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCloseAsDisputeLosePageTotalNo.");
		return null;
	}
	
	
	/**
	 * @author wenbo
	 * search Close As Dispute Win total no.
	 * */
	public String getCloseAsDisputeWinPageTotalNo() throws Exception {
		logger.info("Enter method getCloseAsDisputeWinPageTotalNo.");
		String result = null;
		try{
			result = invoiceDetailService.getCloseAsDisputeWinPageTotalNo(svo);
		}catch(Exception e){
			logger.error("getCloseAsDisputeWinPageTotalNo error: ", e);
			result = "{error:\"getCloseAsDisputeWinPageTotalNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCloseAsDisputeWinPageTotalNo.");
		return null;
	}
	
	/**
	 * @author wenbo
	 * search Close As Dispute Win
	 * */
	public String getCloseAsDisputeWinPageData() throws Exception {
		logger.info("Enter method getCloseAsDisputeWinPageData.");
		String result = null;
		try{
			result = invoiceDetailService.getCloseAsDisputeWinPageData(svo);
		}catch(Exception e){
			logger.error("getCloseAsDisputeWinPageData error: ", e);
			result = "{error:\"getCloseAsDisputeWinPageData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCloseAsDisputeWinPageData.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * do Outstanding Credit Split Apply
	 * */
	public String doOutstandingCreditSplitApply2() throws Exception {
		logger.info("Enter method doOutstandingCreditSplitApply2.");		
		try{
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName,attachmentPointId);
			jsonResult = invoiceDetailService.updateOutstandingCreditSplitApply2(new Integer(invoiceId),new Long(inProposalIds),point,uploadsMessage,new Double(balanceAmount),accountCodeId);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doOutstandingCreditSplitApply2 error: ", e);
			jsonResult = "{error:\"doOutstandingCreditSplitApply error: "+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doOutstandingCreditSplitApply2.");
		return SUCCESS;
	}
	/**
	 * @author dongjian
	 * do Outstanding Credit Split Apply
	 * */
	public String doOutstandingCreditSplitApply1() throws Exception {
		logger.info("Enter method doOutstandingCreditSplitApply.");
		try{
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			jsonResult = invoiceDetailService.updateOutstandingCreditSplitApply1(new Integer(invoiceId),new Long(inProposalIds),point,uploadsMessage,new Double(balanceAmount),accountCodeId);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doOutstandingCreditSplitApply error: ", e);
			jsonResult = "{error:\"doOutstandingCreditSplitApply error: "+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doOutstandingCreditSplitApply.");
		return SUCCESS;
	}
	/**
	 * @author dongjian
	 * do Record Dispute And Apply To Payment
	 * */
	public String doRecordDisputeAndApplyToPayment() throws Exception {
		logger.info("Enter method doRecordDisputeAndApplyToPayment.");
		try{
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			jsonResult = invoiceDetailService.updateRecordDisputeAndApplyToPayment(new Integer(invoiceId),inProposalIds,point,uploadsMessage);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doRecordDisputeAndApplyToPayment error: ", e);
			jsonResult = "{error:\"doRecordDisputeAndApplyToPayment error: "+e.getMessage()+"\"}";
			return INPUT;
		}
		logger.info("Exit method doRecordDisputeAndApplyToPayment.");
		return SUCCESS;
	}
	
	/**
	 * @author dongjian
	 * do Apply Credit And Apply To Payment
	 * */
	public String doApplyCreditToCurrentPayment() throws Exception {
		logger.info("Enter method doApplyCreditToCurrentPayment.");
		String result = null;
		try{
			result = invoiceDetailService.updateCreditToCurrentPayment(inProposalIds);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doApplyCreditToCurrentPayment error: ", e);
			result = "{error:\"doApplyCreditToCurrentPayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doApplyCreditToCurrentPayment.");
		return null;
	}
	/**
	 * @author dongjian
	 * do Manual Reconciliation
	 * */
	public String doManualReconciliation() throws Exception {
		logger.info("Enter method doManualReconciliation.");
		String result = null;
		try{
			result = invoiceDetailService.deleteReconciliation(reconcileId);
		}catch(Exception e){
			logger.error("doManualReconciliation error: ", e);
			result = "{error:\"doManualReconciliation error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doManualReconciliation.");
		return null;
	}
	/**
	 * @author dongjian
	 * do Manual Reconciliation
	 * */
	public String doManualReconciliationMany() throws Exception {
		logger.info("Enter method doManualReconciliationMany.");
		String result = null;
		try{
			result = invoiceDetailService.deleteReconciliationMany(inIds);
		}catch(Exception e){
			logger.error("doManualReconciliationMany error: ", e);
			result = "{error:\"doManualReconciliationMany error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doManualReconciliationMany.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * Verify the BAN
	 * */
	public String doValidateBanLock() throws Exception {
		logger.info("Enter method doValidateBanLock.");
		String result = null;
		try{
			result = invoiceDetailService.validateBanLock(banId);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("doValidateBanLock error: ", e);
			result = "{error:\"doValidateBanLock error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doValidateBanLock.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * validate Missing SCOA
	 * */
	public String validateMissingSCOA() throws Exception {
		logger.info("Enter method validateMissingSCOA.");
		String result = "{}";
		try {
			Double[] ds2 = invoiceDetailService.getProposalAmount(svo.getInvoiceId1());
			if((ds2[0] != null && ds2[0] != 0) || (ds2[1] != null && ds2[1] != 0) || (ds2[2] != null && ds2[2] != 0)){
				result = "{data:true}";
			}else{
				result = "{data:false}";
			}
		} catch (Exception e) {
			logger.error(e);
			result = "{error:\"getPastThreePaymentPage error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method validateMissingSCOA.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * excute Invoice approve Workflow
	 * */
	public String handleInvoiceWorkflow() throws Exception {
		logger.info("Enter method handleInvoiceWorkflow.");				
		try {
			Invoice invoice = invoiceDetailService.getInvoiceByInvoiceId(invoiceId); 
			
			boolean isVail  = true;
			if (workflowActionId == 1){
			    isVail = valiApprove(invoice);
			}
			
			if(isVail){	
						
				AttachmentPoint point = null;
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
					point = commonLookupService.saveUploadFileAndGetPointId("invoice",filterUploads, filterUploadsFileName);
				}
				
				Invoice resultInvoice = invoiceDetailService.updateInvoiceWorkflowAndSaveMsMessage(invoice,workflowActionId,uploadsMessage,oldInvoiceStatusId,point,approveWinUserId,approveWinRoleId);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
				invoiceDetailService.updateHistoryFiles(point,resultInvoice);
				InvoiceStatus invoiceStatus = resultInvoice.getInvoiceStatus();
				int statusId = invoiceStatus.getId();
				int assignmentUserId = resultInvoice.getUserByAssignmentUserId().getId();
				if(statusId == 25 || statusId == 29 || statusId == 33){
					if (assignmentUserId != 0) {
						sendEmailService.sendApproveEmailToAssignmentUser(resultInvoice);
					}
					
				}
				if(resultInvoice.getInvoiceStatus() != null){
					InvoiceStatus is = resultInvoice.getInvoiceStatus();
					User u = resultInvoice.getUserByAssignmentUserId();
					handleWorkflowResult = "{invoiceStatusId:"+is.getId()+",invoiceStatusName:'"+is.getInvoiceStatusName()+ "',assignmentUserId:" + u.getId() + ",actionId : "+workflowActionId+",messageId : "+resultInvoice.getMessageId()+",channelId : "+resultInvoice.getChannelId()+"}";
				}
				if (workflowActionId == 1) {
					successMsg = "This invoice is approved.";
				} else if (workflowActionId == 2) {
					successMsg = "This invoice is held.";
				} else if (workflowActionId == 3) {
					successMsg = "This invoice is rejected.";
				}
			}else{
				return INPUT;
			}
		} catch (Exception e) {
			logger.error(e);
			if (e.getMessage() == null || e.getMessage().equals("")) {
				inputMsg = ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB;
			} else {
				inputMsg += e.getMessage() + "\\n";
				System.out.println(inputMsg);
			}
			return INPUT;
		}
		logger.info("Exit method handleInvoiceWorkflow.");
		return SUCCESS;
	}
	
	public void checkWorkflowPrivilege() throws Exception {
		logger.info("Enter method checkWorkflowPrivilege.");
		String result = "";
		try {
			boolean checkResult = invoiceDetailService.checkWorkflowPrivilege(Integer.valueOf(invoiceId));
			if (checkResult) {
				result = "{data:true}";
			} else {
				result = "{data:false}";
			}
		} catch (Exception e) {
			logger.error(e);
			result = "{error:\"checkWorkflowPrivilege error: "+e.getMessage()+"\"}";
//			return INPUT;
		}
		this.writeOutputStream(result);
		logger.info("Exit method checkWorkflowPrivilege.");
//		return SUCCESS;
	}
	/**
	 * @author dongjian
	 * validate before Approve 
	 * */
	private boolean valiApprove(Invoice invoice2) throws BPLException {
		boolean flag = true;
		try {
			if(invoice2.getInvoiceStatus() == null){
				inputMsg += "Invoice Status Error! \\n";
				return false;
			}
			//invoice下有没有close的dispute.并且这个dispute 下有open 的action request.
			boolean isOpen = invoiceDetailService.getInvoiceDisputeStatusCloseAndActionRequestOpenCount(invoice2);		
			if(isOpen){
				inputMsg += "Please update disputes that have open action request before approval.\\n";
				return false;
			}
			// ban 下的dispute 超过60天
			boolean isDueDate = invoiceDetailService.getBanDisputeDueDate(invoice2.getId());
			if(isDueDate){
				inputMsg += "Please update disputes that are over 60 days before approval.\\n";
				return false;
			}
			Integer isid = invoice2.getInvoiceStatus().getId();
			List<String> authorities = SystemUtil.getCurrentUserAuthorities();
			if(isid < 21){
				Long s1 = invoiceDetailService.getInactiveSCOACount(invoice2.getId());
				if(s1 != 0){
					inputMsg += "Invalid scoa(s) discovered in this invoice! \\n";
					flag = false;
				}
				Long d1 = invoiceDetailService.getInvoiceCreditBalance(invoice2.getId());
				if(d1 != 0){
					inputMsg += "Outstanding Credit! \\n";
					flag = false;
				}
				Long i1 = invoiceDetailService.getUngroupedDisputeCount(invoice2.getId());
				if(i1 != 0){
					inputMsg += "Un-grouped Dispute! \\n";
					flag = false;
				}
				Double[] ds2 = invoiceDetailService.getProposalAmount(invoice2.getId());
				if((ds2[0] != null && ds2[0] != 0) || (ds2[1] != null && ds2[1] != 0)){
					inputMsg += "Missing SCOA! \\n";
					flag = false;
				}
				if((ds2[2] != null && ds2[2] != 0)){
					inputMsg += "Reconciliation missing SCOA! \\n";
					flag = false;
				}
				
			}
			
			Double paymentAmount = invoiceDetailService.getInvoicePaymentAmount(invoice2);
			if(paymentAmount < 0){
				Double manuallyCreatedAmount = invoiceDetailService.getShortPaidDisputeAmount(invoice2);
				if(manuallyCreatedAmount != 0){
					inputMsg += "Manually Created Short-paid Dispute Caused Credit Payment! \\n";
					flag = false;
				}
			}
			Long[] levelAndNotes = invoiceDetailService.getUnLevelAndNotes(invoice2);
			long unlevel = levelAndNotes[0];
			if(unlevel != 0){
				inputMsg += "One or more claim has no confidence level! \\n";
				flag = false;
			}
			long unNotes = levelAndNotes[1];
			if(unNotes != 0){
				inputMsg += "One or more claim has invalid Notes! \\n";
				flag = false;
			}
			
			boolean subFlag = false;
			if(isid>=10 && isid<=20 && authorities.contains("FUNCTION_1")){
				subFlag = true;
			}
			if(isid>=21 && isid<=24 && authorities.contains("FUNCTION_2")){
				subFlag = true;
			}
			if(isid>=25 && isid<=28 && authorities.contains("FUNCTION_3")){
				subFlag = true;
			}
			if(isid>=29 && isid<=32 && authorities.contains("FUNCTION_4")){
				subFlag = true;
			}
			if(isid>=33 && isid<=36 && authorities.contains("FUNCTION_5")){
				subFlag = true;
			}
			if(!subFlag){
				inputMsg += "Insufficient privileges! \\n";
				flag = false;
			}
		} catch (Exception e) {
			logger.error(e);
			throw new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		return flag;
	}

	/**
	 * @author dongjian
	 * search Past Three Payment 
	 * */
	public String getPastThreePaymentPage() throws Exception {
		logger.info("Enter method getPastThreePaymentPage.");
		String result = null;
		try{
			result = invoiceDetailService.getPastThreePaymentPage(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getPastThreePaymentPage error: ", e);
			result = "{error:\"getPastThreePaymentPage error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPastThreePaymentPage.");
		return null;
	}
	/**
	 * @author dongjian
	 * get Current Invoice History
	 * */
	public String getCurrentInvoiceHistory() throws Exception {
		logger.info("Enter method getCurrentInvoiceHistory.");
		String result = null;
		try{
			result = invoiceDetailService.getCurrentInvoiceHistory(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getCurrentInvoiceHistory error: ", e);
			result = "{error:\"getCurrentInvoiceHistory error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCurrentInvoiceHistory.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Paid Dispute Payment
	 * */
	public String getPaidDisputePayment() throws Exception {
		logger.info("Enter method getPaidDisputePayment.");
		String result = null;
		try{
			result = invoiceDetailService.getPaidDisputePayment(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getPaidDisputePayment error: ", e);
			result = "{error:\"getPaidDisputePayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaidDisputePayment.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Paid Dispute Payment page no
	 * */
	public String getPaidDisputePaymentTotalPageNo() throws Exception {
		logger.info("Enter method getPaidDisputePaymentTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getPaidDisputePaymentTotalPageNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getPaidDisputePaymentTotalPageNo error: ", e);
			result = "{error:\"getPaidDisputePaymentTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaidDisputePaymentTotalPageNo.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * download Excel By Paid Dispute Payment
	 * */
	public String downloadExcelByPaidDisputePayment() throws Exception {
		logger.info("Exit method downloadExcelByPaidDisputePayment.");
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "PaidDispute - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.getExcelByPaidDisputePayment(svo,excelDirPath,titles, invoiceNumber);
			this.writeFile(fileName,new FileInputStream(excelPath));
		}catch (Throwable e) {
			logger.error("downloadExcelByPaidDisputePayment error: ", e);
		}
		logger.info("Exit method downloadExcelByPaidDisputePayment.");
		return null;
	}
	/**
	 * @author dongjian
	 * search Short Paid Dispute by Payment
	 * */
	public String getShortPaidDisputePayment() throws Exception {
		logger.info("Enter method getShortPaidDisputePayment.");
		String result = null;
		try{
			result = invoiceDetailService.getShortPaidDisputePayment(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getShortPaidDisputePayment error: ", e);
			result = "{error:\"getShortPaidDisputePayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getShortPaidDisputePayment.");
		return null;
	}
	/**
	 * @author dongjian
	 * search Short Paid Dispute by Payment page no
	 * */
	public String getShortPaidDisputePaymentTotalPageNo() throws Exception {
		logger.info("Enter method getShortPaidDisputePaymentTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getShortPaidDisputePaymentTotalPageNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getShortPaidDisputePaymentTotalPageNo error: ", e);
			result = "{error:\"getShortPaidDisputePaymentTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getShortPaidDisputePaymentTotalPageNo.");
		return null;
	}
	/**
	 * @author dongjian
	 * download Excel By Short Paid Dispute Payment
	 * */
	public String downloadExcelByShortPaidDisputePayment() throws Exception {
		logger.info("Exit method downloadExcelByShortPaidDisputePayment.");
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "ShortPaidDispute - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.getExcelByShortPaidDisputePayment(svo,excelDirPath,titles, invoiceNumber);
			this.writeFile(fileName,new FileInputStream(excelPath));
		}catch (Throwable e) {
			logger.error("downloadExcelByShortPaidDisputePayment error: ", e);
		}
		logger.info("Exit method downloadExcelByShortPaidDisputePayment.");
		return null;
	}
 
	/**
	 * @author dongjian
	 * search Current Invoice Payment
	 * */
	public String getCurrentInvoicePayment() throws Exception {
		logger.info("Enter method getCurrentInvoicePayment.");
		String result = null;
		try{
			result = invoiceDetailService.getCurrentInvoicePayment(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getCurrentInvoicePayment error: ", e);
			result = "{error:\"getCurrentInvoicePayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCurrentInvoicePayment.");
		return null;
	}
	 
	/**
	 * @author dongjian
	 * search Current Invoice Payment page no
	 * */
	public String getCurrentInvoicePaymentTotalPageNo() throws Exception {
		logger.info("Enter method getCurrentInvoicePaymentTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getCurrentInvoicePaymentTotalPageNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getCurrentInvoicePaymentTotalPageNo error: ", e);
			result = "{error:\"getCurrentInvoicePaymentTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCurrentInvoicePaymentTotalPageNo.");
		return null;
	}
	/**
	 * @author dongjian
	 * search ManuallyCreated Payment Total Page No
	 * */
	public String getManuallyCreatedPaymentTotalPageNo() throws Exception {
		logger.info("Enter method getManuallyCreatedPaymentTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getManuallyCreatedPaymentTotalPageNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getManuallyCreatedPaymentTotalPageNo error: ", e);
			result = "{error:\"getManuallyCreatedPaymentTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getManuallyCreatedPaymentTotalPageNo.");
		return null;
	}
	/**
	 * @author dongjian
	 * search ManuallyCreated Payment Total Page No
	 * */
	public String getMiscPaymentAmount() throws Exception {
		logger.info("Enter method getMiscPaymentAmount.");
		String result = null;
		try{
			result = invoiceDetailService.getMiscPaymentAmount(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getMiscPaymentAmount error: ", e);
			result = "{error:\"getMiscPaymentAmount error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getMiscPaymentAmount.");
		return null;
	}
	/**
	 * @author dongjian
	 * search ManuallyCreated Payment 
	 * */
	public String getManuallyCreatedPayment() throws Exception {
		logger.info("Enter method getManuallyCreatedPayment.");
		String result = null;
		try{
			result = invoiceDetailService.getManuallyCreatedPayment(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getManuallyCreatedPayment error: ", e);
			result = "{error:\"getManuallyCreatedPayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getManuallyCreatedPayment.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Dispute Payback Payment Page No
	 * */
	public String getDisputePaybackPaymentTotalPageNo() throws Exception {
		logger.info("Enter method getDisputePaybackPaymentTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getDisputePaybackPaymentTotalPageNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getDisputePaybackPaymentTotalPageNo error: ", e);
			result = "{error:\"getDisputePaybackPaymentTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getDisputePaybackPaymentTotalPageNo.");
		return null;
	}
	
	
	/**
	 * @author dongjian
	 * search Dispute Payback Payment
	 * */
	public String getDisputePaybackPayment() throws Exception {
		logger.info("Enter method getDisputePaybackPayment.");
		String result = null;
		try{
			result = invoiceDetailService.getDisputePaybackPayment(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getDisputePaybackPayment error: ", e);
			result = "{error:\"getDisputePaybackPayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getDisputePaybackPayment.");
		return null;
	}
	/**
	 * @author dongjian
	 * search Current Dispute PageNo
	 * */
	public String getCurrentDisputeTotalPageNo() throws Exception {
		logger.info("Enter method getCurrentDisputeTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getCurrentDisputeTotalPageNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getCurrentDisputeTotalPageNo error: ", e);
			result = "{error:\"getCurrentDisputeTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCurrentDisputeTotalPageNo.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Current Dispute
	 * */
	public String getCurrentDispute() throws Exception {
		logger.info("Enter method getCurrentDispute.");
		String result = null;
		try{
			result = invoiceDetailService.getCurrentDispute(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getCurrentDispute error: ", e);
			result = "{error:\"getCurrentDispute error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCurrentDispute.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Applying Credit Payment PageNo
	 * */
	public String getApplyingCreditPaymentTotalPageNo() throws Exception {
		logger.info("Enter method getApplyingCreditPaymentTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getApplyingCreditPaymentTotalPageNo(svo); //"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getApplyingCreditPaymentTotalPageNo error: ", e);
			result = "{error:\"getApplyingCreditPaymentTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getApplyingCreditPaymentTotalPageNo.");
		return null;
	}
	
	/**
	 * @author dongjian
	 * search Applying Credit Payment 
	 * */
	public String getApplyingCreditPayment() throws Exception {
		logger.info("Enter method getApplyingCreditPayment.");
		String result = null;
		try{
			result = invoiceDetailService.getApplyingCreditPayment(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getApplyingCreditPayment error: ", e);
			result = "{error:\"getApplyingCreditPayment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getApplyingCreditPayment.");
		return null;
	}
	
	/**
	 * The four aspects of the Payment Amount will remove the SCOA and, and then follow the SCOA summary, list displays the SCOA and Amount. 
	 * */
	public String getPaymentScoaAmountTotalPageNo() throws Exception {
		logger.info("Enter method getPaymentScoaAmountTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getPaymentScoaAmountTotalPageNo(svo);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("getPaymentScoaAmountTotalPageNo error: ", e);
			result = "{error:\"getPaymentScoaAmountTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaymentScoaAmountTotalPageNo.");
		return null;
	}
	
	/**
	 * @author mingyang.li
	 * search invoice detail by invoice number
	 * */
	public String searchInvoiceDetailByInvoiceNumber() throws Exception {
		logger.info("Enter method SearchInvoiceDetailByInvoiceNumber.");
		String result = null;
		try{
			result = invoiceDetailService.getInvoiceDetailByInvoiceNumber(searchInvoiceVO);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("SearchInvoiceDetailByInvoiceNumber error: ", e);
			result = "{error:\"SearchInvoiceDetailByInvoiceNumber error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method SearchInvoiceDetailByInvoiceNumber.");
		return null;
	}
	
	/**
	 * @author mingyang.li
	 * search invoice detail by invoice number
	 * */
	public String searchInvoiceDetailByInvoiceNumberTotalPageNo() throws Exception {
		logger.info("Enter method SearchInvoiceDetailByInvoiceNumberTotalPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getInvoiceDetailByInvoiceNumberTotalPageNo(searchInvoiceVO);//"{totalResultNo:4,totalPageNo:1}";
		}catch(Exception e){
			logger.error("SearchInvoiceDetailByInvoiceNumberTotalPageNo error: ", e);
			result = "{error:\"SearchInvoiceDetailByInvoiceNumberTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method SearchInvoiceDetailByInvoiceNumberTotalPageNo.");
		return null;
	}
	
	/**
	 * The four aspects of the Payment Amount will remove the SCOA and, and then follow the SCOA summary, list displays the SCOA and Amount. 
	 * */
	public String getPaymentScoaAmount() throws Exception {
		logger.info("Enter method getPaymentScoaAmount.");
		String result = null;
		try{
			result = invoiceDetailService.getPaymentScoaAmount(svo);//"{begin:1,end:4,data:[{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"},{scoa:1,amount:\"INV3414859\"}]}";
		}catch(Exception e){
			logger.error("getPaymentScoaAmount error: ", e);
			result = "{error:\"getPaymentScoaAmount error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaymentScoaAmount.");
		return null;
	}
	
	/**
	 * Json output amount 
	 * */
	public String getAmount() throws Exception {
		logger.info("Enter method getAmount.");
		String result = null;
		try{
			//result =  "{totalPayment:50,totalDispute:100,totalShortPaidDispute:150,totalPaidDispute:200}";
			result = invoiceDetailService.getAmount(svo);
		}catch(Exception e){
			logger.error("getAmount error: ", e);
			result = "{error:\"getAmount error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getAmount.");
		return null;
	}
	//-----------------------Payment Tab end----------------------------
	
	public String excelByAccordionInvoiceDetail() throws Exception{
		logger.info("Exit method excelByAccordionInvoiceDetail.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "invoiceDetailExcel - "+ invoiceNumber +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.createInvoiceDetailToExcel(svo,excelDirPath,titles, invoiceNumber);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Exception e) {
			logger.error("excelByAccordionInvoiceDetail error: ", e);
		}
		logger.info("Exit method excelByAccordionInvoiceDetail.");
		return null;
	}
	
	public String excelByInvoiceItemList() throws Exception{
		logger.info("Exit method excelByInvoiceItemList.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "invoiceItemListExcel - "+ invoiceNumber +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.createInvoiceItemListToExcel(svo,excelDirPath,titles, invoiceNumber);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Exception e) {
			logger.error("excelByInvoiceItemList error: ", e);
		}
		logger.info("Exit method excelByInvoiceItemList.");
		return null;
	}
	
	
	/**
	 *  recodeTaxInvoice
	 *  @author Jian.Dong
	 */
	public String recodeTaxInvoice() throws Exception{
		logger.info("Enter method recodeTaxInvoice.");
		String result = "{}";
		try{
			invoiceDetailService.updateRecodeTaxInvoice(invoiceId);
		}catch(Throwable e){
			logger.error("recodeTaxInvoice error: ", e);
			result = "{error:\"Recode tax failed\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method recodeTaxInvoice.");
		return null;
	}
	
	public String updateAutopaySwitch() throws Exception {
		String result = null;
		try {
			invoiceDetailService.updateAutopaySwitch(banId,autopaySwitch);
			result = "{success:\"success\"}";
		} catch (BPLException e) {
			logger.error("updateAutopaySwitch error: ", e);
			result = "{error:\"updateAutopaySwitch error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		return null;
	}

	/**
	 *  Obtains InvoiceItem to compile the amount  
	 */
	public String getInvoiceItemTotal() throws Exception{
		logger.info("Enter method getInvoiceItemTotal.");
		String result = null;
		try{
			result = getInvoiceItemTotalServiceCall();
			
		}catch(Exception e){
			logger.error("getInvoiceItemTotal error: ", e);
			result = "{error:\"getInvoiceItemTotal error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceItemTotal.");
		return null;
	}
	
	private String getInvoiceItemTotalServiceCall(){
		logger.info("Enter method getInvoiceItemTotalServiceCall.");
		String s = null;
		if(testWeb) s = getInvoiceItemTotalServiceCallMockup();
		else {
			try {
				s = invoiceDetailService.getInvoiceItemTotal(svo);
			} catch (BPLException e) {
				logger.error("getInvoiceItemTotal error: ", e);
				s = "{error:\"getInvoiceItemTotal error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getInvoiceItemTotalServiceCall.");
		return s;
	}

	//////Fake data///////
	private String getInvoiceItemTotalServiceCallMockup() {
		return "{data:[{amount:\"100.00000\",payment:\"19.00000\",dispute:\"20.00000\",itemName:\"Total\"}]}";
	}
	
	/**
	 *  Obtains many InvoiceItem Proposal tabulations  
	 */
	public String getItemGroupProposalList() throws Exception{
		logger.info("Enter method getItemGroupProposalList.");
		String result = null;
		try{
			result = getItemGroupProposalListServiceCall();
		}catch(Exception e){
			logger.error("getItemGroupProposalList error: ", e);
			result = "{error:\"getItemGroupProposalList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getItemGroupProposalList.");
		return null;
	}
	
	private String getItemGroupProposalListServiceCall(){
		logger.info("Enter method getItemGroupProposalListServiceCall.");
		String s = null;
		if(testWeb) s = getItemGroupProposalListServiceCallMockup();
		else {
			try {
				s = invoiceDetailService.getInvoiceItemGroupProposals(svo.getBoxInId());
			} catch (BPLException e) {
				logger.error("getItemGroupProposalList error: ", e);
				s = "{error:\"getItemGroupProposalList error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getItemGroupProposalListServiceCall.");
		return s;
	}
	
	//////Fake data///////
	private String getItemGroupProposalListServiceCallMockup() {
		return "{data:[{id:20,accountCode:\"diyige\",payment:\"0.00000\",dispute:\"1.00000\",disputeCategory:\"Duplicate Invoice\",disputeCategoryId:\"1\",accountCodeId:\"1\"}," +
				"{id:1,accountCode:\"diyige\",payment:\"7.00000\",dispute:\"7.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"1\"}," +
				"{id:10,accountCode:\"diyige\",payment:\"2.00000\",dispute:\"2.00000\",disputeCategory:\"Incorrect Rate\",disputeCategoryId:\"3\",accountCodeId:\"1\"}," +
				"{id:2,accountCode:\"dier\",payment:\"3.00000\",dispute:\"3.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"2\"}," +
				"{id:3,accountCode:\"dier\",payment:\"1.00000\",dispute:\"1.00000\",disputeCategory:\"Incorrect Rate\",disputeCategoryId:\"3\",accountCodeId:\"2\"}," +
				"{id:12,accountCode:\"san\",payment:\"1.00000\",dispute:\"1.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"3\"}," +
				"{id:6,accountCode:\"san\",payment:\"1.00000\",dispute:\"1.00000\",disputeCategory:\"Incorrect Rate\",disputeCategoryId:\"3\",accountCodeId:\"3\"}," +
				"{id:5,accountCode:\"bbb\",payment:\"4.00000\",dispute:\"4.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"5\"}]}";
	}
	
	/**
	 *  According to the data which obtains revises Proposal  
	 */
	public String updateProposalData() throws Exception{
		logger.info("Enter method updateProposalData.");
		String result = "null";
		try{
			invoiceDetailService.updateInvoiceItemProposals(svo);
		}catch(Exception e){
			logger.error("updateProposalData error: ", e);
			result = "{error:\"updateProposalData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateProposalData.");
		return null;
	}
	
	/**
	 *  According to the data which obtains revises ProposalAmount  
	 */
	public String updateProposalAmountData() throws Exception{
		logger.info("Enter method updateProposalAmountData.");
		String result = "null";
		try{
			invoiceDetailService.updateInvoiceItemProposalsAmount(svo);
		}catch(Exception e){
			logger.error("updateProposalAmountData error: ", e);
			result = "{error:\"updateProposalData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateProposalAmountData.");
		return null;
	}
	
	/**
	 *  Revises NotProposed the amount  
	 */
	public String updateNotProposedAmountData() throws Exception{
		logger.info("Enter method updateNotProposedAmountData.");
		String result = "null";
		try{
			invoiceDetailService.updateInvoiceItemNotProposedAmount(svo);
		}catch(Exception e){
			logger.error("updateNotProposedAmountData error: ", e);
			
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateNotProposedAmountData.");
		return null;
	}
	
	/**
	 *  Obtains the InvoiceItem Proposal tabulation  
	 */
	public String getItemProposalList() throws Exception{
		logger.info("Enter method getItemProposalList.");
		String result = null;
		try{
			result = getItemProposalListServiceCall();
		}catch(Exception e){
			logger.error("getItemProposalList error: ", e);
			result = "{error:\"getItemProposalList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getItemProposalList.");
		return null;
	}
	
	private String getItemProposalListServiceCall(){
		logger.info("Enter method getItemProposalListServiceCall.");
		String s = null;
		if(testWeb) s = getItemProposalListServiceCallMockup();
		else {
			try {
				s = invoiceDetailService.getInvoiceItemProposals(svo);
			} catch (BPLException e) {
				logger.error("getItemProposalList error: ", e);
				s = "{error:\"getItemProposalList error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getItemProposalListServiceCall.");
		return s;
	}
	
	//////Fake data///////
	private String getItemProposalListServiceCallMockup() {
		return "{data:[{id:20,accountCode:\"diyige\",payment:\"0.00000\",dispute:\"1.00000\",disputeCategory:\"Duplicate Invoice\",disputeCategoryId:\"1\",accountCodeId:\"1\"}," +
				"{id:1,accountCode:\"diyige\",payment:\"7.00000\",dispute:\"7.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"1\"}," +
				"{id:10,accountCode:\"diyige\",payment:\"2.00000\",dispute:\"2.00000\",disputeCategory:\"Incorrect Rate\",disputeCategoryId:\"3\",accountCodeId:\"1\"}," +
				"{id:2,accountCode:\"dier\",payment:\"3.00000\",dispute:\"3.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"2\"}," +
				"{id:3,accountCode:\"dier\",payment:\"1.00000\",dispute:\"1.00000\",disputeCategory:\"Incorrect Rate\",disputeCategoryId:\"3\",accountCodeId:\"2\"}," +
				"{id:12,accountCode:\"san\",payment:\"1.00000\",dispute:\"1.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"3\"}," +
				"{id:6,accountCode:\"san\",payment:\"1.00000\",dispute:\"1.00000\",disputeCategory:\"Incorrect Rate\",disputeCategoryId:\"3\",accountCodeId:\"3\"}," +
				"{id:5,accountCode:\"bbb\",payment:\"4.00000\",dispute:\"4.00000\",disputeCategory:\"Disconnected\",disputeCategoryId:\"2\",accountCodeId:\"5\"}]}";
	}
	
	/**
	 *  Obtains Amount which InvoiceItem compiles  
	 */
	public String getItemTotalAmount() throws Exception{
		logger.info("Enter method getItemTotalAmount.");
		String result = null;
		try{
			result = getItemTotalAmountServiceCall();
		}catch(Exception e){
			logger.error("getItemTotalAmount error: ", e);
			result = "{error:\"getItemTotalAmount error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getItemTotalAmount.");
		return null;
	}
	
	private String getItemTotalAmountServiceCall(){
		logger.info("Enter method getItemTotalAmountServiceCall.");
		String s = null;
		if(testWeb) s = getItemTotalAmountServiceCallMockup();
		else {
			try {
				s = invoiceDetailService.getItemProposalTotalAmount(invoiceItemId);
			} catch (BPLException e) {
				logger.error("getItemTotalAmount error: ", e);
				s = "{error:\"getItemTotalAmount error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getItemTotalAmountServiceCall.");
		return s;
	}
	
	//////Fake data///////
	private String getItemTotalAmountServiceCallMockup() {
		return "{data:[{totalAmount:\"100.00000\",payment:\"19.00000\",dispute:\"20.00000\"}]}";
	}
	
	/**
	 *  Obtains Amount which many InvoiceItem compile  
	 */
	public String getItemGroupTotalAmount() throws Exception{
		logger.info("Enter method getItemGroupTotalAmount.");
		String result = null;
		try{
			result = getItemGroupTotalAmountServiceCall();
		}catch(Exception e){
			logger.error("getItemGroupTotalAmount error: ", e);
			result = "{error:\"getItemGroupTotalAmount error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getItemGroupTotalAmount.");
		return null;
	}
	
	private String getItemGroupTotalAmountServiceCall(){
		logger.info("Enter method getItemGroupTotalAmountServiceCall.");
		String s = null;
		if(testWeb) s = getItemGroupTotalAmountServiceCallMockup();
		else {
			try {
				s = invoiceDetailService.getItemGroupProposalTotalAmount(svo.getBoxInId());
			} catch (BPLException e) {
				logger.error("getItemGroupTotalAmount error: ", e);
				s = "{error:\"getItemGroupTotalAmount error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getItemGroupTotalAmountServiceCall.");
		return s;
	}
	
	//////Fake data///////
	private String getItemGroupTotalAmountServiceCallMockup() {
		return "{data:[{totalAmount:\"100.00000\",payment:\"19.00000\",dispute:\"20.00000\"}]}";
	}
	
	/**
	 * So as far as itemId is the data ItemProposal 
	 */
	public String getItemProposal() throws Exception{
		logger.info("Enter method getItemProposal.");
		String result = null;
		try{
			result = getItemProposalServiceCall();
		}catch(Exception e){
			logger.error("getItemProposal error: ", e);
			result = "{error:\"getItemProposal error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getItemProposal.");
		return null;
	}
	
	private String getItemProposalServiceCall(){
		logger.info("Enter method getItemProposalServiceCall.");
		String s = null;
		if(testWeb) s = getItemProposalServiceCallMockup();
		else {
			try {
				s = invoiceDetailService.getproposal(itemId);
			} catch (BPLException e) {
				logger.error("getItemProposalServiceCall error: ", e);
				s = "{error:\"Get Invoice Hyperlink error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getItemProposalServiceCall.");
		return s;
	}
	
	//////Fake data///////
	private String getItemProposalServiceCallMockup() {
		return "{data:[{id:9,pay:\"24.00000\",dispute:\"56.00000\", itemname:\"L5\",cleanup:\"21.00000\", rate:\"54.00000\",shortPay:\"22.00000\", original:\"145.52000\",discount:\"3.00000\", quantity:\"\",itemKey:\"\", startDate:\"1899-12-30\",endDate:\"2000-12-30\", circuitNumber:\"\"}]}";
	}
	
	public String getInvoiceDetailListName() throws Exception{
		logger.info("Enter method getInvoiceDetailListName.");
		String result = null;
		try{
			result = getInvoiceDetailListNameCall();
		}catch(Exception e){
			logger.error("getInvoiceDetailListName error: ", e);
			result = "{error:\"getInvoiceDetailListName error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceDetailListName.");
		return null;
	}
	
	private String getInvoiceDetailListNameCall(){
		logger.info("Enter method getInvoiceDetailListNameCall.");
		String s = null;
		if(testWeb) s = "{data:[{typeId:2,type:\"Past Due\"}]}";
		else {
			try {
				s = invoiceDetailService.getItemDisplayListName(svo);
			} catch (BPLException e) {
				logger.error("getInvoiceDetailListName error: ", e);
				s = "{error:\"getInvoiceDetailListName error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getInvoiceDetailListNameCall.");
		return s;
	}
	
	/**
	 * Get a different type of folding section
	 */
	public String getTypeAccordion() throws Exception{
		logger.info("Enter method getTypeAccordion.");
		String result = null;
		try{
			result = getTypeAccordionCall();
		}catch(Exception e){
			logger.error("getTypeAccordion error: ", e);
			result = "{error:\"getTypeAccordion error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTypeAccordion.");
		return null;
	}
	
	private String getTypeAccordionCall(){
		logger.info("Enter method getTypeAccordionCall.");
		String s = null;
		if(testWeb) s = "{data:[{typeId:2,type:\"Past Due\"}]}";
		else {
			try {
				if(invoiceItemId != null){
					s = invoiceDetailService.getItemTypeList("invoiceItemId",invoiceItemId);
				}else {
					s = invoiceDetailService.getItemTypeList("invoiceId",invoiceId);
				}
			} catch (BPLException e) {
				logger.error("getTypeAccordion error: ", e);
				s = "{error:\"getTypeAccordion error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getTypeAccordionCall.");
		return s;
	}
	
	/**
	 * Proposal under itemId get their own list
	 */
	public String getProposalList() throws Exception{
		logger.info("Enter method getProposalList.");
		String result = null;
		try{
			result = getProposalListCall();
		}catch(Exception e){
			logger.error("getProposalList error: ", e);
			result = "{error:\"getProposalList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getProposalList.");
		return null;
	}
	
	private String getProposalListCall(){
		logger.info("Enter method getProposalListCall.");
		String s = null;
		if(testWeb) s = getProposalListCallMockup();
		else {
			try {
				s = invoiceDetailService.getItemProposalList(itemId);
			} catch (BPLException e) {
				logger.error("getProposalList error: ", e);
				s = "{error:\"getProposalList error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getProposalListCall.");
		return s;
	}
	
	//////Fake data///////
	private String getProposalListCallMockup() {
		return "{data:[{id:1,itemName:\"L2 .1\",payment:\"19.00000\",dispute:\"20.00000\",amount:\"100.00000\",proposalFlag:\"1\",countId:\"9\"}]}";
	}
	
	/**
	 * Be InvoiceItem list
	 */
	public String getInvoiceItemList() throws Exception{
		logger.info("Enter method getInvoiceItemList.");
		String result = null;
		try{
			result = getInvoiceItemListCall();
		}catch(Exception e){
			logger.error("getInvoiceItemList error: ", e);
			result = "{error:\"getInvoiceItemList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceItemList.");
		return null;
	}
	
	private String getInvoiceItemListCall(){
		logger.info("Enter method getInvoiceItemListCall.");
		String s = null;
		if(testWeb) s = getInvoiceItemListCallMockup();
		else {
			try {
				s = invoiceDetailService.getInvoiceItemLists(svo);
			} catch (BPLException e) {
				logger.error("getInvoiceItemList error: ", e);
				s = "{error:\"getInvoiceItemList error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method Call.");
		return s;
	}
	
	//////Fake data///////
	private String getInvoiceItemListCallMockup() {
		return "{begin:1,end:1,data:[{id:1,itemName:\"L2 .1\",payment:\"19.00000\",dispute:\"20.00000\",amount:\"100.00000\",proposalFlag:\"1\",countId:\"9\"}]}";
	}
	
	/**
	 * Get a list of the specified typeName
	 */
	public String getTypeOpenAcc() throws Exception{
		logger.info("Enter method getTypeOpenAcc.");
		String result = null;
		try{
			result = getTypeOpenAccCall();
		}catch(Exception e){
			logger.error("getTypeOpenAcc error: ", e);
			result = "{error:\"getTypeOpenAcc error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTypeOpenAcc.");
		return null;
	}
	
	private String getTypeOpenAccCall(){
		logger.info("Enter method getTypeOpenAccCall.");
		String s = null;
		if(testWeb) s = getTypeOpenAccCallMockup();
		else {
			try {
				s = invoiceDetailService.getTypeOpenAcc(svo);
			} catch (BPLException e) {
				logger.error("getTypeOpenAcc error: ", e);
				s = "{error:\"getTypeOpenAcc error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getTypeOpenAccCall.");
		return s;
	}
	
	//////Fake data///////
	private String getTypeOpenAccCallMockup() {
		return "{begin:1,end:1,data:[{id:1,itemname:\"L2 .1\",rate:\"21.00000\",itemAmount:\"10.00000\", original:\"323.00000\",discount:\"42.00000\", quantity:\"\",itemKey:\"\", startDate:\"1899-12-30\",endDate:\"2000-12-30\", circuitNumber:\"\"}]}";
	}
	
	/**
	 * Get InvoiceItem list of pages / total number of
	 */
	public String getInvoiceItemListTotalPageNo() throws Exception{
		logger.info("Enter method getInvoiceItemListTotalPageNo.");
		String result = null;
		try{
			result = getInvoiceItemListTotalPageNoServiceCall();
		}catch(Exception e){
			logger.error("getInvoiceItemListTotalPageNo error: ", e);
			result = "{error:\"getInvoiceItemListTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceItemListTotalPageNo.");
		return null;
	}
	
	private String getInvoiceItemListTotalPageNoServiceCall(){
		logger.info("Enter method getInvoiceItemListTotalPageNoServiceCall.");
		String s = null;
		if(testWeb) s = "{totalResultNo:4,totalPageNo:1}";
		else {
			try {
				s = invoiceDetailService.getInvoiceItemTotalPageNo(svo);
			} catch (BPLException e) {
				logger.error("getInvoiceItemListTotalPageNo error: ", e);
				s = "{error:\"getInvoiceItemListTotalPageNo error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getInvoiceItemListTotalPageNoServiceCall.");
		return s;
	}
	
	/**
	 * Get invoice item type total result number and page.
	 */
	public String getInvoiceItemTypeTotalPageNo() throws Exception{
		logger.info("Enter method getInvoiceItemTypeTotalPageNo.");
		String result = null;
		try{
			result = getInvoiceItemTypeTotalPageNoServiceCall();
		}catch(Exception e){
			logger.error("getInvoiceItemTypeTotalPageNo error: ", e);
			result = "{error:\"getInvoiceItemTypeTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceItemTypeTotalPageNo.");
		return null;
	}
	
	private String getInvoiceItemTypeTotalPageNoServiceCall(){
		logger.info("Enter method getInvoiceItemTypeTotalPageNoServiceCall.");
		String s = null;
		if(testWeb) s = "{totalResultNo:4,totalPageNo:1}";
		else {
			try {
				s = invoiceDetailService.getItemTypeTotalPageNo(svo);
			} catch (BPLException e) {
				logger.error("getInvoiceItemTypeTotalPageNo error: ", e);
				s = "{error:\"getInvoiceItemTypeTotalPageNo error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getInvoiceItemTypeTotalPageNoServiceCall.");
		return s;
	}
	
	/**
	 * Modify the label 
	 */
	public String updateLabel() throws Exception{
		logger.info("Enter method updateLabel.");
		String result = "null";
		try{
			invoiceDetailService.updateInvoiceLabel(invoiceId, labelId, bo);
		}catch(Exception e){
			logger.error("updateLabel error: ", e);
			result = "{error:\"updateLabel error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateLabel.");
		return null;
	}
	
	/**
	 * add Item Proposal
	 */
	public String addItemProposal() throws Exception{
		logger.info("Enter method addItemProposal.");
		String result = "null";
		try{
			invoice = invoiceDetailService.getInvoiceInvoiceItem(itemId);
			invoiceItem = invoiceDetailService.getInvoiceItemByInvoiceItemId(itemId);
			invoiceDetailService.addItemProposal(invoice, invoiceItem, svo);
		}catch(Exception e){
			logger.error("addItemProposal error: ", e);
			result = "{error:\"addItemProposal error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method addItemProposal.");
		return null;
	}
	
	/**
	 * update Item Proposal
	 */
	public String updateItemProposal() throws Exception{
		logger.info("Enter method updateItemProposal.");
		String result = "null";
		try{
			invoiceDetailService.updateItemProposal(proposalId, svo);
		}catch(Exception e){
			logger.error("updateItemProposal error: ", e);
			result = "{error:\"updateItemProposal error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateItemProposal.");
		return null;
	}
	
	/**
	 * ItemName level by crumbs
	 */
	public String getItemName() throws Exception{
		logger.info("Enter method getItemName.");
		try{
			String result = searchItemNameServiceCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("getItemName error: ", e);
		}
		logger.info("Exit method getItemName.");
		return null;
	}
	
	private String searchItemNameServiceCall(){
		logger.info("Enter method searchItemNameServiceCall.");
		String s = null;
		if(testWeb) s = searchItemNameServiceCallMockup();
		else {
			try {
				s = invoiceDetailService.searchItemNameList(invoiceItemId);
			} catch (BPLException e) {
				logger.error("getItemName error: ", e);
				s = "{error:\"get Item Name error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method searchItemNameServiceCall.");
		return s;
	}
	
	//////Fake data///////
	private String searchItemNameServiceCallMockup() {
		return "{data:[{invoiceId:1,parentItemId:null,invoiceItemId:1,itemname:\"L2 .1\"}]}";
	}
	
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Original performs all inquiries, conditions of the paging inquires research method.-return
	 */
	public String doGetOriginalTotalPageNos()  throws Exception {
		logger.info("Enter method doGetOriginalTotalPageNos.");
		String result;
		try{
			//svo.setInvoiceNumber(inco);
			result=invoiceDetailService.getOriginalTotalPageNo(svo);
		}catch(Exception e){
			logger.error("doGetOriginalTotalPageNos error: ", e);
			result = "{error:\"doGetOriginalTotalPageNos error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doGetOriginalTotalPageNos.");
		return null;
	}
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Originals performs all inquiries, conditions inquire method.-return
	 */
	public String getOriginals()  throws Exception {
		logger.info("Enter method getOriginals.");
		String result;
		try{
			//svo.setInvoiceNumber(inco);
			result = invoiceDetailService.getOriginalList(svo);
		}catch(Exception e){
			logger.error("getOriginals error: ", e);
			result = "{error:\"getOriginals error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getOriginals.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Tariffs performs all inquiries, conditions of the paging inquires research method.-return
	 */
	public String doGetTariffTotalPageNos()  throws Exception {
		logger.info("Enter method doGetTariffTotalPageNos.");
		String result;
		try{
			svo.setBanId(tnco);
			result = invoiceDetailService.getTariffTotalPageNo(svo);
		}catch(Exception e){
			logger.error("doGetTariffTotalPageNos error: ", e);
			result = "{error:\"doGetTariffTotalPageNos error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doGetTariffTotalPageNos.");
		return null;
	}
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Tariffs performs all inquiries, conditions inquire method.-return
	 */
	public String getTariffs()  throws Exception {
		logger.info("Enter method getTariffs.");
		String result;
		try{
			svo.setBanId(tnco);
			result = invoiceDetailService.getTariffList(svo);
		}catch(Exception e){
			logger.error("getTariffs error: ", e);
			result = "{error:\"getTariffs error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTariffs.");
		return null;
	}
	
	/**
	 * @author wei.su
	 * @return
	 * @throws Exception
	 * Get the basis and related dispute
	 */
	public String doGetDisputePageNos()  throws Exception {
		logger.info("Enter method doGetDisputePageNos.");
		try{
			//svo.setBanId(tnco);
			String result = invoiceDetailService.getDisputeTotalNOByInvoiceId(svo, Integer.parseInt(invoiceId));
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("searchDispute error: ", e);
		}
		logger.info("Exit method doGetDisputePageNos.");
		return null;
	}
	/**
	 * @author wei.su
	 * @return json
	 * @throws Exception
	 * Get the basis and related dispute and return. Set
	 */
	public String getDisputeListByInvoiceId()  throws Exception {
		logger.info("Enter method getDisputes.");
		try{
			//svo.setBanId(tnco);
			String result = disputeTabService.findByInvoiceIdAndReturnJSON(svo);
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("searchTicket error: ", e);
		}
		logger.info("Exit method getDisputes.");
		return null;
	}
	
	
	/**
	 * @author wei.su
	 * @return json
	 * @throws Exception
	 * Get the basis and related dispute and return. Set
	 */
	public String getOriginatorListAndReturnJSON()  throws Exception {
		logger.info("Enter method getOriginatorListAndReturnJSON.");
		SearchInvoiceVO searchInvoiceVO=new SearchInvoiceVO();
		try{
			String result = disputeTabService.getOriginatorListAndReturnJSON(searchInvoiceVO);
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("getOriginatorListAndReturnJSON error: ", e);
		}
		logger.info("Exit method getOriginatorListAndReturnJSON.");
		return null;
	}
	
	/**
	 * @author wei.su
	 * @return json
	 * @throws Exception
	 * Get the dispute and return. Set
	 */
	public String getDisputes()  throws Exception {
		logger.info("Enter method getDisputes.");
		try{
			//svo.setBanId(tnco);
			String result = invoiceDetailService.searchDisputesByInvoiceId(svo, Integer.parseInt(invoiceId));
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("searchTicket error: ", e);
		}
		logger.info("Exit method getDisputes.");
		return null;
	}
	
	
	/**
	 * @author Jian.Dong
	 * Get Invoice Page Credit Tab Info Totail Number
	 * @return
	 * @throws Exception
	 */
	public String getOutstandingCreditPageTotalNo()throws Exception {
		logger.info("Enter method getOutstandingCreditPageTotalNo.");
		String result = null;
		try{
			result = invoiceDetailService.getOutstandingCreditPageTotalNo(svo);
		}catch(Exception e){
			logger.error("getOutstandingCreditPageTotalNo error: ", e);
			result = "{error:\"get Outstanding Credit PageTotalNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getOutstandingCreditPageTotalNo.");
		return null;
	}
	/**
	 * @author Jian.Dong
	 * Get Invoice Page Credit Tab Info List
	 * @return
	 * @throws Exception
	 */
	public String getOutstandingCreditPageData()throws Exception {
		logger.info("Enter method getOutstandingCreditPageData.");
		String result = null;
		try{
			result = invoiceDetailService.getOutstandingCreditPageData(svo);
		}catch(Exception e){
			logger.error("getOutstandingCreditPageData error: ", e);
			result = "{error:\"getOutstandingCreditPageData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getOutstandingCreditPageData.");
		return null;
	}
 
	
	/**
	 * @author Chao.Liu
	 * DownLoad Current Invoice Payment Excel 
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByPaymentTabPCIP()throws Exception{
		logger.info("Exit method saveExcelByPaymentTabPCIP.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "CurrentInvoice - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.saveExcelByPaymentTabPCIP(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByPaymentTabPCIP error: ", e);
		}
		logger.info("Exit method saveExcelByPaymentTabPCIP.");
		return null;
	}
	
	/**
	 * @author Chao.Liu
	 * DownLoad Manually Created Payment Excel
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByPaymentTabPMCP()throws Exception{
		logger.info("Exit method saveExcelByPaymentTabPMCP.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "ManuallyCreatedAdjustments - "+ invoiceNumber +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.saveExcelByPaymentTabPMCP(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByPaymentTabPMCP error: ", e);
		}
		logger.info("Exit method saveExcelByPaymentTabPMCP.");
		return null;
	}
	/**
	 * @author Pengfei.Wang
	 * DownLoad Manually Created Payment Excel
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByPaymentTabMisc()throws Exception{
		logger.info("Exit method saveExcelByPaymentTabMisc.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();			
			String fileName = "ManuallyCreatedAdjustments - "+ invoiceNumber +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.saveExcelByPaymentTabMISC(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByPaymentTabMisc error: ", e);
		}
		logger.info("Exit method saveExcelByPaymentTabMisc.");
		return null;
	}
	
	/**
	 * @author Pengfei.Wang
	 * DownLoad Manually Created Dispute Excel
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByDisputeMisc()throws Exception{
		logger.info("Exit method saveExcelByDisputeMisc.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();			
			//svo.setBanId(mscid);
			String fileName = "DisputePayBack - "+ invoiceNumber +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.miscDisputePaybackToExcel(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByDisputeMisc error: ", e);
		}
		logger.info("Exit method saveExcelByDisputeMisc.");
		return null;
	}
 
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Paymenttabla performs all inquiries, conditions of the paging inquires research method.-return
	 */
	public String doGetPaymenttablaTotalPageNos()  throws Exception {
		logger.info("Enter method doGetPaymenttablaTotalPageNos.");
		String result;
		try{
			result = invoiceDetailService.getPaymenttablaTotalPageNo(svo);
		}catch(Exception e){
			logger.error("doGetPaymenttablaTotalPageNos error: ", e);
			result = "{error:\"doGetPaymenttablaTotalPageNos error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doGetPaymenttablaTotalPageNos.");
		return null;
	}
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Paymenttabla performs all inquiries, conditions inquire method.-return
	 */
	public String getPaymenttabla()  throws Exception {
		logger.info("Enter method getPaymenttabla.");
		String result;
		try{
			result = invoiceDetailService.getPaymenttablaList(svo);
		}catch(Exception e){
			logger.error("getPaymenttabla error: ", e);
			result = "{error:\"getPaymenttabla error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPaymenttabla.");
		return null;
	}
	


	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Paymenttabla performs all inquiries, conditions of the paging inquires research method.-return
	 */
	public String doGetMiscTotalPageNos()  throws Exception {
		logger.info("Enter method doGetMiscTotalPageNos.");
		String result;
		try{
			result = invoiceDetailService.getMiscTotalPageNo(svo);
		}catch(Exception e){
			logger.error("doGetMiscTotalPageNos error: ", e);
			result = "{error:\"doGetMiscTotalPageNos error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doGetMiscTotalPageNos.");
		return null;
	}
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to Paymenttabla performs all inquiries, conditions inquire method.-return
	 */
	public String getMisc()  throws Exception {
		logger.info("Enter method getMisc.");
		String result;
		try{
			result = invoiceDetailService.getMiscList(svo);
		}catch(Exception e){
			logger.error("getMisc error: ", e);
			result = "{error:\"getMisc error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getMisc.");
		this.writeOutputStream(result);
		return null;
	}
	public String getAddMiscProposalPayment() throws Exception{
		return null;
	}
	/**
	 * @author pengfei.wang
	 * Edit Proposal
	 */
	public String getEditMiscProposalPayment() throws Exception{
		logger.info("Enter method getAddMiscProposalPayment.");
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
		int eid=Integer.parseInt(svo.getParentItemId()) ;
		commonLookupService.saveUploadFile("proposal", eid, uploads, uploadsFileName);
		invoiceDetailService.editMiscProposalPayment(svo);
		}catch(Exception e){
			logger.error("saveMiscProposal error: ", e);
			uploadsMessage = "Please make sure no field is empty.";
			return INPUT;
		}
		logger.info("Exit method getAddMiscProposalPayment.");
		return SUCCESS;
	}
	
	/**
	 * @author pengfei.wang
	 * This Assignment will be deleted
	 * */
	public void getDeleteMiscProposalPayment() throws Exception {
		logger.info("Enter method getDeleteMiscProposalPayment.");
		invoiceDetailService.deleteMsicPayment(proPaslId);
		logger.info("Exit method getDeleteMiscProposalPayment.");	
	}
	
	public String saveMiscProposal()throws Exception {
		logger.info("Enter method saveMiscProposal.");
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
			Long rid = invoiceDetailService.addMiscProposalPayment(svo);
			commonLookupService.saveUploadFile("proposal", rid.intValue(), uploads, uploadsFileName);
		}catch(Exception e){
			logger.error("saveMiscProposal error: ", e);
			uploadsMessage = "Please make sure no field is empty.";
			return INPUT;
		}
		logger.info("Exit method saveMiscProposal.");
		return SUCCESS;
	}

	
	/**
	 * @author wei.su
	 * uploadForproposalofDisputeTabLeftSave
	 * @return
	 * @throws Exception
	 */
	public String uploadForDisputeTabLeftSave()throws Exception {
		logger.info("Enter method UploadForDispureTabLeftSave.");
		SearchInvoiceVO searchInvoiceVO=new SearchInvoiceVO();
		if("".endsWith(disputeAmount)){
			searchInvoiceVO.setDisputeAmount("0");
		}else{
			searchInvoiceVO.setDisputeAmount(disputeAmount);
		}		
		searchInvoiceVO.setNote(proposalDiscription);
		searchInvoiceVO.setAccountCodeId(sCOA);
		searchInvoiceVO.setOriginatorId(originatorId);
		searchInvoiceVO.setDisputeReason(categoryId);
		searchInvoiceVO.setCricuitNumber(cricuitNumber);
		searchInvoiceVO.setServiceType(serviceType);
		searchInvoiceVO.setInvoiceId(invoice.getId().toString());
		searchInvoiceVO.setEmailFlag(emailFlag);
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
//					uploadsMessage += " Line file must be less than 10MB.\"";
					uploadsMessage = "\"The file size should be less than 10 MB.\"";
					return INPUT;
				}
			}
				Integer Id = disputeTabService.saveProposalToInvoiceItem(searchInvoiceVO);
				commonLookupService.saveUploadFile("proposal",Id, uploads, uploadsFileName);
				uploadsMessage = "\" \"";

		}catch(Exception e){
			logger.error("UploadForDispureTabLeftSave error: ", e);
			uploadsMessage = "Upload DisputeTabLeft Is Unknown Error!";
			return INPUT;
		}
		logger.info("Exit method UploadForDispureTabLeftSave.");
		return SUCCESS;
		
	}
	
	/**
	 * @author wei.su
	 * uploadForDisputeTabRightSave
	 * @return
	 * @throws Exception
	 */
	public String uploadForDisputeTabRightSave()throws Exception {
		logger.info("Enter method uploadForDisputeTabRightSave.");
		Integer Id=Integer.valueOf(disputeIdRight);
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
				//Integer Id = disputeTabService.saveProposalToInvoiceItem(searchInvoiceVO);
				commonLookupService.saveUploadFile("dispute",Id, uploads, uploadsFileName);
				uploadsMessage = "\" \"";

		}catch(Exception e){
			logger.error("uploadForDisputeTabRightSave error: ", e);
			uploadsMessage = "Upload DisputeTabRight Is Unknown Error!";
			return INPUT;
		}
		logger.info("Exit method uploadForDisputeTabRightSave.");
		return SUCCESS;
		
	}
	/**
	 * @author wei.su
	 * uploadForProposalOfEditProposal
	 * @return
	 * @throws Exception
	 */
	public String uploadForProposalOfEditProposal()throws Exception {
		logger.info("Enter method uploadForProposalOfEditProposal.");
		Integer Id=Integer.valueOf(proposalId);
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
				//Integer Id = disputeTabService.saveProposalToInvoiceItem(searchInvoiceVO);
				commonLookupService.saveUploadFile("proposal",Id, uploads, uploadsFileName);
				uploadsMessage = "\" \"";

		}catch(Exception e){
			logger.error("uploadForProposalOfEditProposal error: ", e);
			uploadsMessage = "Upload ForProposalOfEditProposal Is Unknown Error!";
			return INPUT;
		}
		logger.info("Exit method uploadForProposalOfEditProposal.");
		return SUCCESS;
		
	}
	
	/**
	 * According to the amount of invoiceid gets dispute
	 * @author wei.su
	 * @return
	 * @throws NumberFormatException
	 * @throws BPLException
	 * @throws IOException 
	 */
	public String getDisputeTotalAmountByInvoiceId() throws NumberFormatException, BPLException, IOException
	{
		logger.info("Enter method getDisputeTotalAmountByInvoiceId.");
		String result=null;
		try {
			result=disputeTabService.getDisputeTotalAmountByInvoiceId(Integer.valueOf(invoiceDisputeTabId));

		} catch (Exception e) {
			logger.error("getDisputeTotalAmountByInvoiceId error: ", e);
			result = "{error:\"getDisputeTotalAmountByInvoiceId error: "+e.getMessage()+"\"}";
		}
		logger.info("EXIT method getDisputeTotalAmountByInvoiceId.");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info Totail Number
	 * @return
	 * @throws IOException 
	 */
	public String getDisputeTabTotalNoOfInoiceDetail() throws IOException
	{
		logger.info("Enter method getDisputeTabTotalNoOfInoiceDetail.");
		String result=null;
		svo.setInvoiceId(invoiceDisputeTabId);
		try {
			result=disputeTabService.getDisputeTabTotalNoOfInoiceDetail(svo);

		} catch (Exception e) {
			logger.error("getDisputeTabTotalNoOfInoiceDetail error: ", e);
			result = "{error:\"getDisputeTabTotalNoOfInoiceDetail error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getDisputeTabTotalNoOfInoiceDetail");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info List
	 * @return json
	 * @throws IOException 
	 */
	public String searchDisputeTabOfInoiceDetail() throws IOException
	{
		logger.info("Enter method searchDisputeTabOfInoiceDetail.");
		String result=null;
		try {
			svo.setInvoiceId(invoiceDisputeTabId);
			result=disputeTabService.searchDisputeTabOfInoiceDetail(svo);

		} catch ( Exception e) {
			logger.error("searchDisputeTabOfInoiceDetail error: ", e);
			result = "{error:\"searchDisputeTabOfInoiceDetail error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method searchDisputeTabOfInoiceDetail");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info List
	 * @return json
	 * @throws IOException 
	 */
	public String getProposalDataLeftByInvoiceId() throws IOException
	{
		logger.info("Enter method getProposalDataLeftByInvoiceId.");
		String result=null;
		SearchInvoiceVO searchInvoiceVO = new SearchInvoiceVO();
		try {
			searchInvoiceVO.setInvoiceId(invoiceDisputeTabId);
			result=disputeTabService.getProposalDataLeftByInvoiceId(searchInvoiceVO, proposalIds);

		} catch ( Exception e) {
			logger.error("getProposalDataLeftByInvoiceId error: ", e);
			result = "{error:\"getProposalDataLeftByInvoiceId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getProposalDataLeftByInvoiceId");
		this.writeOutputStream(result);
		return null;
	}
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info List
	 * @return json
	 * @throws IOException 
	 */
	public String getProposalDataRightByInvoiceIdAndDisputeId() throws IOException{
		logger.info("Enter method getProposalDataRightByInvoiceIdAndDisputeId.");
		String result=null;
		try {
			result=disputeTabService.getProposalDataRightByInvoiceIdAndDisputeId(svo);
		} catch ( Exception e) {
			logger.error("getProposalDataLeftByInvoiceId error: ", e);
			result = "{error:\"getProposalDataRightByInvoiceIdAndDisputeId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getProposalDataRightByInvoiceIdAndDisputeId");
		this.writeOutputStream(result);
		return null;
	}
	/**
	 * @author wei.su
	 * According to invoiceId get all the dispute amount
	 * @return
	 * @throws IOException 
	 */
	public String getDisputesTotalNoByInvoiceId() throws IOException
	{
		logger.info("Enter method getDisputeTotalNoByInvoiceId.");
		String result=null;
		//svo.setInvoiceId(invoiceId);
		try {
			result=(String)disputeTabService.getDisputeTotalNoByInvoiceId(svo);

		} catch ( Exception e) {
			logger.error("getDisputesTotalNoByInvoiceId error: ", e);
			result = "{error:\"getDisputesTotalNoByInvoiceId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getDisputesTotalNoByInvoiceId");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author wei.su
	 * Get  dispute List according to invoiceId
	 * @return json
	 * @throws Exception 
	 */
	public String searchDisputesByInvoiceId() throws Exception
	{
		logger.info("Enter method searchDisputesByInvoiceId.");
		String result="";
		try {
			//searchInvoiceVO.setInvoiceId(invoiceId);
			result = disputeTabService.searchDisputesByInvoiceId(svo);

		} catch ( Exception e) {
			logger.error("searchDisputesByInvoiceId error: ", e);
			result = "{error:\"searchDisputesByInvoiceId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method searchDisputesByInvoiceId");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * According to proposalId proposal "get the attachment
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getAnnexsTotalOfProposalByProposalId() throws IOException
	{
		logger.info("Enter method getAnnexsTotalOfProposalByProposalId.");
		String result=null;
		svo.setProposalId(proposalId);
		try {
			result=(String)disputeTabService.getAnnexsTotalOfProposalByProposalId(svo);

		} catch ( Exception e) {
			logger.error("getAnnexsTotalOfProposalByProposalId error: ", e);
			result = "{error:\"getAnnexsTotalOfProposalByProposalId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getAnnexsTotalOfProposalByProposalId");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author wei.su
	 * Get  dispute List according to invoiceId
	 * @return json
	 * @throws IOException 
	 * @throws Exception 
	 */
	public String searchAnnexsOfProposalByProposalId() throws BPLException, IOException
	{
		logger.info("Enter method searchAnnexsOfProposalByProposalId.");
		String result="";
		try {
			svo.setProposalId(proposalId);
			result = disputeTabService.searchAnnexsOfProposalByProposalId(svo);

		} catch ( Exception e) {
			logger.error("searchAnnexsOfProposalByProposalId error: ", e);
			result = "{error:\"searchAnnexsOfProposalByProposalId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method searchAnnexsOfProposalByProposalId");
		this.writeOutputStream(result);
		return null;
	}
	/**
	 * According to attachmentPointId delete "attachment
	 * @return
	 * @throws IOException
	 */
	public String deleteAnnexsOfProposalById() throws IOException{
		logger.info("Enter method deleteAnnexsOfProposalById.");
		String result = "";
		try {
			disputeTabService.deleteAnnexsById(Integer.valueOf(attachmentPointId));
			result = "0";
		} catch ( Exception e) {
			logger.error("deleteAnnexsOfProposalById error: ", e);
			result = "{error:\"deleteAnnexsOfProposalById error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method deleteAnnexsOfProposalById.");
		return null;
	}
	/**
	 * According to DisputeId Dispute "get the attachment totalnumber
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getAnnexsTotalOfDisputeByDisputeId() throws IOException
	{
		logger.info("Enter method getAnnexsTotalOfDisputeByDisputeId.");
		String result=null;
		svo.setDisputeId(String.valueOf(disputeId));
		try {
			result=(String)disputeTabService.getAnnexsTotalOfDisputeByDisputeId(svo);

		} catch ( Exception e) {
			logger.error("getAnnexsTotalOfDisputeByDisputeId error: ", e);
			result = "{error:\"getAnnexsTotalOfDisputeByDisputeId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getAnnexsTotalOfDisputeByDisputeId");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author wei.su
	 * Get  dispute Annexs List according to disputeId
	 * @return json
	 * @throws IOException 
	 * @throws Exception 
	 */
	public String searchAnnexsOfDisputeByDisputeId() throws BPLException, IOException
	{
		logger.info("Enter method searchAnnexsOfDisputeByDisputeId.");
		String result="";
		try {
			svo.setDisputeId(String.valueOf(disputeId));
			result = disputeTabService.searchAnnexsOfDisputeByDisputeId(svo);

		} catch ( Exception e) {
			logger.error("searchAnnexsOfDisputeByDisputeId error: ", e);
			result = "{error:\"searchAnnexsOfDisputeByDisputeId error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method searchAnnexsOfDisputeByDisputeId");
		this.writeOutputStream(result);
		return null;
	}
	/**@author wei.su
	 * According to attachmentPointId delete "attachment of dispute
	 * @return
	 * @throws IOException
	 */
	public String deleteAnnexsOfDisputeById() throws IOException{
		logger.info("Enter method deleteAnnexsOfDisputeById.");
		String result = "";
		try {
			disputeTabService.deleteAnnexsByIdOfDispute(Integer.valueOf(attachmentPointId));
			result = "0";
		} catch ( Exception e) {
			logger.error("deleteAnnexsOfProposalById error: ", e);
			result = "{error:\"deleteAnnexsOfDisputeById error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method deleteAnnexsOfDisputeById.");
		return null;
	}
	/**
	 * 
	 * @author wei.su
	 * @param searchInvoiceVO
	 * @throws IOException 
	 */
	public String saveProposalToInvoiceItem() throws IOException
	{
		logger.info("Enter method saveDisputeToInvoiceItem.");
		String result="";
		SearchInvoiceVO searchInvoiceVO=new SearchInvoiceVO();
		searchInvoiceVO.setDisputeAmount(disputeAmount);
		searchInvoiceVO.setDescription(proposalDiscription);
		searchInvoiceVO.setAccountCodeId(sCOA);
		searchInvoiceVO.setOriginatorId(originatorId);
		searchInvoiceVO.setDisputeReason(categoryId);
		searchInvoiceVO.setInvoiceId(invoiceDisputeTabId);
		try {
			disputeTabService.saveProposalToInvoiceItem(searchInvoiceVO);
			result="0";
		} catch ( Exception e) {
			logger.error("saveProposalToInvoiceItem error: ", e);
			result = "{error:\"saveProposalToInvoiceItem error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method saveProposalToInvoiceItem.");
		return null;
	}
	
	
	public Integer getWorkflowActionId() {
		return workflowActionId;
	}

	public void setWorkflowActionId(Integer workflowActionId) {
		this.workflowActionId = workflowActionId;
	}

	/**
	 * According to the selected proposalId, the record of changes to the list under disputeid selected disputeid 
	 * @author wei.su
	 * @param proposalIds
	 * @param disputeId
	 * @throws BPLException
	 */
	public String updateDisputeTabMoveSelectGroupOfInvoiceDetail() throws IOException{
		
		logger.info("Enter method updateDisputeTabMoveSelectGroupOfInvoiceDetail.");
		String result="";
		try {
			disputeTabService.updateDisputeTabMoveSelectGroupOfInvoiceDetail(proposalIds, disputeId,Double.valueOf(totalDisputeAmount),emailFlag);
			this.writeOutputStream("0");
		} catch ( Exception e) {
			logger.error("updateDisputeTabMoveSelectGroupOfInvoiceDetail error: ", e);
			result = "{error:\"updateDisputeTabMoveSelectGroupOfInvoiceDetail error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method updateDisputeTabMoveSelectGroupOfInvoiceDetail.");
		return null;
	}
	
	/**
	 * According to the selected proposalId, the record of changes to the list under disputeid selected disputeid 
	 * @author wei.su
	 * @param proposalIds
	 * @param disputeId
	 * @throws BPLException
	 */
	public String updateDisputeTabMoveSelectGroupOfInvoiceDetailRight() throws IOException{
		
		logger.info("Enter method updateDisputeTabMoveSelectGroupOfInvoiceDetail.");
		String result="";
		try {
			disputeTabService.updateDisputeTabMoveSelectGroupOfInvoiceDetailRight(proposalIds, disputeId, fromDisputeId, Double.valueOf(totalDisputeAmount),flagShortpaid);
			this.writeOutputStream("0");
		} catch ( Exception e) {
			logger.error("updateDisputeTabMoveSelectGroupOfInvoiceDetail error: ", e);
			result = "{error:\"updateDisputeTabMoveSelectGroupOfInvoiceDetail error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method updateDisputeTabMoveSelectGroupOfInvoiceDetail.");
		return null;
	}
	/**
	 * The proposal will be selected from the current dispute removed 
	 * @author wei.su
	 * @param proposalIds
	 * @throws BPLException
	 */
	public String updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail() throws IOException{
		logger.info("Enter method updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail.");
		String result = "";
		try {
			disputeTabService.updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail(proposalIds,disputeId,Double.valueOf(totalDisputeAmount));
			result = "0";
		} catch ( Exception e) {
			logger.error("updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail error: ", e);
			result = "{error:\"updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail.");
		return null;
	}
	
	
	/**
	 * Delete the current dispute and the proposal remove the dispute in the current group
	 * @author wei.su
	 * @param proposalIds
	 * @param disputeId
	 * @throws BPLException
	 */
	public String deleteDisputeOfInvoiceDetail() throws IOException{
		logger.info("Enter method deleteDisputeOfInvoiceDetail.");
		String result = "";
		try {
			disputeTabService.deleteDisputeOfInvoiceDetail(proposalIds, Integer.valueOf(disputeId),Double.valueOf(totalDisputeAmount));
			result = "0";
		} catch ( Exception e) {
			logger.error("deleteDisputeOfInvoiceDetail error: ", e);
			result = "{error:\"deleteDisputeOfInvoiceDetail error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method deleteDisputeOfInvoiceDetail.");
		return null;
	}
	
	
	/**
	 * A new record, and the disputeIndex will be selected instead of the
	 * proposal the disputeid
	 * 
	 * @author wei.su
	 * @param proposalIds
	 * @param calimNumber
	 * @throws BPLException
	 */
	public String updateDisputeTabGreateOneGroupOfInvoiceDetail() throws IOException{
		
		logger.info("Enter method updateDisputeTabGreateOneGroupOfInvoiceDetail.");
		String result = "";
		try {
			
			disputeTabService.updateDisputeTabGreateOneGroupOfInvoiceDetail(proposalIds, disputeReasonId, disputeStatusId, Integer.valueOf(invoiceId),Double.valueOf(totalDisputeAmount),emailFlag);
			result = "0";
		} catch ( Exception e) {
			logger.error("updateDisputeTabGreateOneGroupOfInvoiceDetail error: ", e);
			result = "{error:\"updateDisputeTabGreateOneGroupOfInvoiceDetail error: "+e.getMessage()+"\"}";
		
		}
		this.writeOutputStream(result);
		logger.info("exit method updateDisputeTabGreateOneGroupOfInvoiceDetail.");
		return null;
		
	}
	
	/**
	 * A new record, and the disputeIndex will be selected instead of the
	 * proposal the disputeid
	 * 
	 * @author wei.su
	 * @param proposalIds
	 * @param calimNumber
	 * @throws BPLException
	 */
	public String updateDisputeTabGreateOneGroupOfInvoiceDetailRight() throws IOException{
		
		logger.info("Enter method updateDisputeTabGreateOneGroupOfInvoiceDetail.");
		String result = "";
		try {
			
			disputeTabService.updateDisputeTabGreateOneGroupOfInvoiceDetailRight(proposalIds, disputeReasonId, disputeStatusId, Integer.valueOf(fromDisputeId), Integer.valueOf(invoiceId), Double.valueOf(totalDisputeAmount));
			result = "0";
		} catch ( Exception e) {
			logger.error("updateDisputeTabGreateOneGroupOfInvoiceDetail error: ", e);
			result = "{error:\"updateDisputeTabGreateOneGroupOfInvoiceDetail error: "+e.getMessage()+"\"}";
		
		}
		this.writeOutputStream(result);
		logger.info("exit method updateDisputeTabGreateOneGroupOfInvoiceDetail.");
		return null;
		
	}
	
	/**
	 * A new record, and the disputeIndex will be selected instead of the
	 * proposal the disputeid
	 * @param invoiceId
	 * @author wei.su
	 * @param proposalData
	 * @param calimNumber
	 * @throws BPLException
	 */
	public String updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail() throws IOException{
		
		logger.info("Enter method updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail.");
		String result = "";
		try {
			
			disputeTabService.updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail(proposalData, Integer.valueOf(invoiceId),emailFlag);
			result = "0";
		} catch ( Exception e) {
			logger.error("updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail error: ", e);
			result = "{error:\"updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail error: "+e.getMessage()+"\"}";
		
		}
		this.writeOutputStream(result);
		logger.info("exit method updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail.");
		return null;
		
	}
	
	/**
	 * delete proposal from proposallist
	 * @author wei.su
	 */
	public String deleteProposalFromProposalList() throws IOException{
		
		logger.info("Enter method deleteProposalFromProposalList.");
		String result = "";
		try {
			disputeTabService.deleteProposalFromProposalList(proposalId,invoiceItemId,disputeReasonText);
			invoiceDetailService.updateInvoiceItemAmount(svo);
			result = "0";
		} catch (Exception e) {
			logger.error("deleteProposalFromProposalList error: ", e);
			result = "{error:\"deleteProposalFromProposalList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method deleteProposalFromProposalList.");
		return null;
		
	}
	

	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info Totail Number
	 * @return
	 * @throws IOException 
	 */
	public String getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId() throws IOException
	{
		logger.info("Enter method getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.");
		String result=null;
		svo.setInvoiceId(invoiceDisputeTabId);
		svo.setDisputeId(String.valueOf(disputeId));
		try {
			result=disputeTabService.getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(svo);
		} catch (Exception e) {
			logger.error("getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId error: ", e);
			result = "{error:\"getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId");
		return null;
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info List
	 * @return json
	 * @throws IOException 
	 */
	public String searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId() throws IOException
	{
		logger.info("Enter method searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.");
		String result=null;
		try {
			svo.setInvoiceId(invoiceDisputeTabId);
			svo.setDisputeId(String.valueOf(disputeId));
			result=disputeTabService.searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(svo);
		} catch ( Exception e) {
			logger.error("searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId error: ", e);
			result = "{error:\"searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId");
		return null;
	}
	
	
	/**
	 * @author wei.su
	 * Automatically generate disputeSearchExcel.xls file server and turn it into a stream output to the client io
	 * 
	 */
	public String saveExcelByProposalListInDisputeTabByInvoiceIdLeft() throws Exception{
		logger.info("Exit method saveExcelByProposalListInDisputeTabByInvoiceIdLeft.");
		FileInputStream fis = null;
		try {
			svo.setInvoiceId(invoiceDisputeTabId);
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			String fileName = "createProposalExcel - "+ i.getInvoiceNumber() +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeTabService.saveExcelByProposalListInDisputeTabByInvoiceIdLeftToExcel(svo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByProposalListInDisputeTabByInvoiceIdLeft error: ", e);
		}
		logger.info("Exit method saveExcelByProposalListInDisputeTabByInvoiceIdLeft.");
		return null;
	}
	
	/**
	 * @author wei.su
	 * Automatically generate disputeSearchExcel.xls file server and turn it into a stream output to the client io
	 * 
	 */
	public String saveExcelByProposalListInDisputeTabByInvoiceIdAndDiputeIdRight() throws Exception{
		logger.info("Exit method saveExcelByActionHistory.");
		FileInputStream fis = null;
		try {
			svo.setInvoiceId(invoiceDisputeTabId);
			svo.setDisputeId(String.valueOf(disputeId));
			String fileName = "proposalExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeTabService.saveExcelByProposalListInDisputeTabByInvoiceIdAndDiputeIdRightToExcel(svo,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByActionHistory error: ", e);
		}
		logger.info("Exit method saveExcelByActionHistory.");
		return null;
	}
	
	/**@author wei.su
	 * Do updateProposalByProposalId action.
	 * @return
	 * @throws Exception
	 */
	public String updateProposalByProposalId() throws Exception {
		logger.info("Enter method updateProposalByProposalId.");
		SearchInvoiceVO searchInvoiceVO = new SearchInvoiceVO();
		if("".endsWith(disputeAmount)){
			searchInvoiceVO.setDisputeAmount("0");
		}else{
			searchInvoiceVO.setDisputeAmount(disputeAmount);
		}		
		
		searchInvoiceVO.setNote(proposalNote);
		searchInvoiceVO.setAccountCodeId(sCOA);
		searchInvoiceVO.setOriginatorId(originatorId);
		searchInvoiceVO.setDisputeReason(categoryId);
		searchInvoiceVO.setCricuitNumber(cricuitNumber);
		searchInvoiceVO.setServiceType(serviceType);
		searchInvoiceVO.setProposalId(proposalId);
		try{
			disputeTabService.updateProposalByProposalId(searchInvoiceVO);	
		}catch(Exception e){
			logger.error("updateProposalByProposalId error: ", e);
		}
		logger.info("Exit method updateProposalByProposalId.");
		return null;
	}
	/**@author wei.su
	 * Do getProposalByProposalId action.
	 * @return
	 * @throws Exception
	 */
	public String getProposalByProposalId() throws Exception {
		logger.info("Enter method getProposalByProposalId.");
		String result="";
		try{
			result = disputeTabService.getProposalByProposalId(Long.valueOf(proposalId));
		}catch(Exception e){
			logger.error("getProposalByProposalId error: ", e);
			result = "{error:\"getProposalByProposalId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getProposalByProposalId.");
		return null;
	}	
	/**@author wei.su
	 * Do getProposalByProposalId action.
	 * @return
	 * @throws Exception
	 */
	public String updateDisputeAmountByDiputeIdAndDisputeAmount() throws Exception {
		logger.info("Enter method updateDisputeAmountByDiputeIdAndDisputeAmount.");
		String result="";
		try{
			disputeTabService.updateDisputeAmountByDiputeIdAndDisputeAmount(Integer.valueOf(disputeIdRight), Double.valueOf(disputeAmount));
		}catch(Exception e){
			logger.error("updateDisputeAmountByDiputeIdAndDisputeAmount error: ", e);
			result = "{error:\"getProposalByProposalId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateDisputeAmountByDiputeIdAndDisputeAmount.");
		return null;
	}	
	/**
	 * @author pengfei.wang
	 * Automatically generate disputeSearchExcel.xls file server and turn it into a stream output to the client io
	 * Download current dispute.
	 */
	public String saveExcelByCurrentDispute() throws Exception{
		logger.info("Exit method saveExcelByCurrentDispute.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "CurrentDispute - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.currentDisputeToExcel(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByCurrentDispute error: ", e);
		}
		logger.info("Exit method saveExcelByCurrentDispute.");
		return null;
	}

	public String saveExcelByDisputePaybackPayments() throws Exception{
		logger.info("Exit method saveExcelByDisputePaybackPayments.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "DisputePayback - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.disputePaybackToExcel(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByDisputePaybackPayments error: ", e);
		}
		logger.info("Exit method saveExcelByDisputePaybackPayments.");
		return null;
	}
	
	/**
	 * Download applied credit.
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String saveExcelByApplyingCreditPayments() throws Exception{
		logger.info("Exit method saveExcelByApplyingCreditPayments.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "ApplyingCredit - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.applyingCreditToExcel(svo,excelDirPath,titles, invoiceNumber);
			
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByApplyingCreditPayments error: ", e);
		}
		logger.info("Exit method saveExcelByApplyingCreditPayments.");
		return null;
	}

	/**
	 * Total payments 的内容， 在 Transition Approval 标签下。 
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String saveExcelByAllPayments() throws Exception{
		logger.info("Exit method saveExcelByAllPayments.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "AllPayments - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.AllPaymentsToExcel(svo,excelDirPath,titles, invoiceNumber);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByAllPayments error: ", e);
		}
		logger.info("Exit method saveExcelByAllPayments.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * Automatically generate disputeSearchExcel.xls file server and turn it into a stream output to the client io
	 * Download invoice action history.
	 */
	public String saveExcelByActionHistory() throws Exception{
		logger.info("Exit method saveExcelByActionHistory.");
		FileInputStream fis = null;
		try {
			Integer invoiceId = svo.getInvoiceId1();
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId( (invoiceId+"") );
			String invoiceNumber = i.getInvoiceNumber();
			String fileName = "ActionHistoryExcel - " + invoiceNumber + ".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.saveExcelByActionHistory(svo,excelDirPath,titles, invoiceNumber);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByActionHistory error: ", e);
		}
		logger.info("Exit method saveExcelByActionHistory.");
		return null;
	}
	
	
	/**
	 * @author wei.su
	 * Automatically generate disputeSearchExcel.xls file server and turn it into a stream output to the client io
	 * @throws IOException 
	 * 
	 */
	public String updateDispute() throws BPLException, IOException{
		logger.info("Exit method updateDispute.");
		String result = "{}";
		try {
			disputeTabService.updateDispute(ticketNumber,claimNumber,disputeId, disputeNotes, Integer.valueOf(originatorId), flagRecurring, flagShortpaid,confidenceLevel,emailCopyTo,Double.valueOf("".equals(reservedAmount) ? "0" : reservedAmount),emailFlag);
		} catch ( Exception e) {
			logger.error("updateDispute error: ", e);
			result = "{error:\"updateDispute error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateDispute.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Get Accordion View
	 */
	public String getAccordionView() throws Exception{
		logger.info("Enter method getAccordionView.");
		String result = null;
		try{
			result = invoiceDetailService.findAccordionList(svo);
		}catch(Exception e){
			logger.error("getAccordionView error: ", e);
			result = "{error:\"getAccordionView error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getAccordionView.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * download Excel
	 */
	public String downloadSOCADisputeExcel() throws Exception{
		logger.info("Enter method downloadSOCADisputeExcel.");
		FileInputStream fis = null;
		try {
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			String fileName = "SCOA and Dispute - "+ CcmUtil.fileNameReplease(i.getInvoiceNumber()) +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.createDownloadSOCADisputeToExcel(svo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Exception e) {
			logger.error("downloadSOCADisputeExcel error: ", e);
		}
		logger.info("Exit method downloadSOCADisputeExcel.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * download Excel
	 */
	public String downloadInvoiceExcel() throws Exception{
		logger.info("Enter method downloadInvoiceExcel.");
		FileInputStream fis = null;
		try {
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			String fileName = "Invoice Excel - "+ i.getInvoiceNumber() +".xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.createDownloadInvoiceToExcel(svo,excelDirPath);
//			String excelPath = invoiceDetailService.createDownloadInvoiceDteailToExcel(svo,excelDirPath);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Exception e) {
			logger.error("downloadInvoiceExcel error: ", e);
		}
		logger.info("Exit method downloadInvoiceExcel.");
		return null;
	}
	
	/**
	 * download CSV
	 */
	public String downloadInvoiceValidationExcel() throws Exception{
		logger.info("Enter method downloadInvoiceValidationExcel.");
		FileInputStream fis = null;
		try {
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			String fileName = "Validation Result - "+i.getInvoiceNumber()+".xls";
			String csvDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.downloadInvoiceValidationExcel(i,csvDirPath);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Exception e) {
			logger.error("downloadInvoiceValidationExcel error: ", e);
		}
		logger.info("Exit method downloadInvoiceValidationExcel.");
		return null;
	}
	
	
	/**
	 * download Telephone Number Validation Excel
	 */
	public String downloadTelephoneNumberValidationExcel() throws Exception{
		logger.info("Enter method downloadTelephoneNumberValidationExcel.");
		FileInputStream fis = null;
		try {
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			String fileName = "Telephone Number Validation Result - "+i.getInvoiceNumber()+".xls";
			String csvDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceDetailService.downloadTelephoneNumberValidationExcel(i,csvDirPath);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Exception e) {
			logger.error("downloadTelephoneNumberValidationExcel error: ", e);
		}
		logger.info("Exit method downloadTelephoneNumberValidationExcel.");
		return null;
	}
	
	public String auditInvoice() throws Exception{
		logger.info("Enter method auditInvoice.");
		 String result = null; 
        try{
            invoiceDetailService.auditInvoice(svo.getInvoiceId()); 
            result = "{success:\"success\"}"; 
        }catch(Exception e){
            logger.error("searchSCOACodingTabViewStr error: ", e);
            result = "{error:\"auditInvoice error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);	
		logger.info("Exit method auditInvoice.");
		return null;
	}
	
	
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * Do search Proposal action.
	 */
	public String searchProposalList() throws Exception {
		logger.info("Enter method searchProposalList.");
		String result = null;
		try{
			result = invoiceDetailService.searchProposalList(svo);
		}catch(Exception e){
			logger.error("searchProposalList error: ", e);
			result = "{error:\"searchProposalList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchProposalList.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Do search EmptySCOA action.
	 * 
	 */
	public String searchEmptySCOAList() throws Exception {
		logger.info("Enter method searchEmptySCOAList.");
		String result = null;
		try{
			result = invoiceDetailService.searchEmptySCOAList(svo);
		}catch(Exception e){
			logger.error("searchEmptySCOAList error: ", e);
			result = "{error:\"searchEmptySCOAList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchEmptySCOAList.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Do search DisputeItems action.
	 * 
	 */
	public String searchDisputeItemsList() throws Exception {
		logger.info("Enter method searchDisputeItemsList.");
		String result = null;
		try{
			result = invoiceDetailService.searchDisputeItemsList(svo);
		}catch(Exception e){
			logger.error("searchDisputeItemsList error: ", e);
			result = "{error:\"searchDisputeItemsList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchDisputeItemsList.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Do search ItemType action.
	 */
	public String searchItemTypeList() throws Exception {
		logger.info("Enter method searchItemTypeList.");
		String result = null;
		try{
			result = invoiceDetailService.searchItemTypeList(svo);
		}catch(Exception e){
			logger.error("searchItemTypeList error: ", e);
			result = "{error:\"searchItemTypeList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchItemTypeList.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * Get Proposal search result number and page.
	 *  
	 */
	public String getProposalViewListPageNo() throws Exception{
		logger.info("Enter method getProposalViewListPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getProposalViewListPageNo(svo);
		}catch(Exception e){
			logger.error("getProposalViewListPageNo error: ", e);
			result = "{error:\"getProposalViewListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getProposalViewListPageNo.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Get EmptySCOA search result number and page.
	 *  
	 */
	public String getEmptySCOAListPageNo() throws Exception{
		logger.info("Enter method getEmptySCOAListPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getEmptySCOAViewListPageNo(svo);
		}catch(Exception e){
			logger.error("getEmptySCOAListPageNo error: ", e);
			result = "{error:\"getEmptySCOAListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getEmptySCOAListPageNo.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Get DisputeItems search result number and page.
	 *  
	 */
	public String getDisputeItemsPageNo() throws Exception{
		logger.info("Enter method getDisputeItemsPageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getDisputeItemsViewListPageNo(svo);
		}catch(Exception e){
			logger.error("getDisputeItemsPageNo error: ", e);
			result = "{error:\"getDisputeItemsPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getDisputeItemsPageNo.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * search Count List Service 
	 */
	public String searchCountList() throws Exception{
		logger.info("Enter method searchCountList.");
		String result = null;
		try{
			if("ItemLabelList".equals(svo.getAccordionName())){
				result = invoiceDetailService.searchCountListServiceByItem(svo);
			}else{
				result = invoiceDetailService.searchCountListService(svo);
			}
		}catch(Exception e){
			logger.error("searchCountList error: ", e);
			result = "{error:\"searchCountList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchCountList.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Get ItemType search result number and page.
	 *  
	 */
	public String getItemTypePageNo() throws Exception{
		logger.info("Enter method getItemTypePageNo.");
		String result = null;
		try{
			result = invoiceDetailService.getItemTypeViewListPageNo(svo);
		}catch(Exception e){
			logger.error("getItemTypePageNo error: ", e);
			result = "{error:\"getItemTypePageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getItemTypePageNo.");
		return null;
	}
	
	public String doEidtInvoiceProposalSave()throws Exception {
		logger.info("Enter method doEidtInvoiceProposalSave.");
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
//					uploadsMessage += " Line file must be less than 10MB.\"";
					uploadsMessage = "\"The file size should be less than 10 MB.\"";
					return INPUT;
				}
			}
			
			if(uploads == null) return SUCCESS;
			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("proposal",uploads,uploadsFileName);
			svo.setAttachmentPointId(point.getId());
			invoiceDetailService.updateInvoiceProposalPoint(svo,point);
		}catch(Exception e){
			logger.error("doEidtInvoiceProposalSave error: ", e);
			uploadsMessage = "Eidt Invoice Proposal Is Unknown Error!";
			return INPUT;
		}
		logger.info("Exit method doEidtInvoiceProposalSave.");
		return SUCCESS;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * update InvoiceItem Amount
	 */
	public String initializedStoredData() throws Exception{
		logger.info("Enter method initializedStoredData.");
		String result = "{}";      
		try{
			invoiceDetailService.updateInvoiceItemAmount(svo);
		}catch(Exception e){
			logger.error("initializedStoredData error: ", e);
			result = "{error:\"initializedStoredData error: "+e.getMessage()+"\"}";    
		}
		this.writeOutputStream(result);  
		logger.info("Exit method initializedStoredData.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * update Proposal to CreditAmount
	 */
	public String updateProposalToCredit() throws Exception{
		logger.info("Enter method updateProposalToCredit.");
		String result = "{}";      
		try{
			invoiceDetailService.updateProposalToCreditAmount(svo);
		}catch(Exception e){
			logger.error("updateProposalToCredit error: ", e);
			result = "{error:\"updateProposalToCredit error: "+e.getMessage()+"\"}";    
		}
		this.writeOutputStream(result);   
		logger.info("Exit method updateProposalToCredit.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * update SCOACoding And Note
	 */
	public String updateSCOACoding() throws Exception{
		logger.info("Enter method updateSCOACoding.");
		String result = "{}";       
		try{
			invoiceDetailService.updateSCOACodingAndNote(svo);
		}catch(Exception e){
			logger.error("updateSCOACoding error: ", e);
			result = "{error:\"updateSCOACoding error: "+e.getMessage()+"\"}";    
		}
		this.writeOutputStream(result);   
		logger.info("Exit method updateSCOACoding.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * A data update multiple SCOA and notes
	 */
	public String updateSCOACodingSingle() throws Exception{
		logger.info("Enter method updateSCOACodingSingle.");
		String result = "{}";       
		try{
			invoiceDetailService.updateSCOACodingSingleAndNote(svo);
		}catch(Exception e){
			logger.error("updateSCOACodingSingle error: ", e);
			result = "{error:\"updateSCOACodingSingle error: "+e.getMessage()+"\"}";    
		}
		this.writeOutputStream(result);   
		logger.info("Exit method updateSCOACodingSingle.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * get Accordion Tab
	 */
	public String getAccordionTabView() throws Exception {
        logger.info("Enter method getAccordionTabView.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.getAccordionTab(svo);                 
        }catch(Exception e){
            logger.error("getAccordionTabView error: ", e);
            result = "{error:\"getAccordionTabView error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method getAccordionTabView.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * The need to display the page list column in the database to find out
	 * 
	 */
	public String searchSCOACodingTabViewStr() throws Exception {
        logger.info("Enter method searchSCOACodingTabViewStr.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.searchSCOACodingTabView(svo);                 
        }catch(Exception e){
            logger.error("searchSCOACodingTabViewStr error: ", e);
            result = "{error:\"searchSCOACodingTabViewStr error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method searchSCOACodingTabViewStr.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query EmptySCOA list of individual data on the total number of tab
	 * 
	 */
	public String getEmptySCOAListTabPageNo() throws Exception {
        logger.info("Enter method getEmptySCOAListTabPageNo.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.getEmptySCOAListTabTotalPageNo(svo);
        }catch(Exception e){
            logger.error("getEmptySCOAListTabPageNo error: ", e);
            result = "{error:\"getEmptySCOAListTabPageNo error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method getEmptySCOAListTabPageNo.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query EmptySCOA accordion tab list data in a single
	 * 
	 */
	public String searchEmptySCOAListTab() throws Exception {
        logger.info("Enter method searchEmptySCOAListTab.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.searchEmptySCOAListTabView(svo);
        }catch(Exception e){
            logger.error("searchEmptySCOAListTab error: ", e);
            result = "{error:\"searchEmptySCOAListTab error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method searchEmptySCOAListTab.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query DisputeItems list of individual data on the total number of tab
	 * 
	 */
	public String getDisputeItemsListTabPageNo() throws Exception {
        logger.info("Enter method getDisputeItemsListTabPageNo.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.getDisputeItemsListTabTotalPageNo(svo);
        }catch(Exception e){
            logger.error("getDisputeItemsListTabPageNo error: ", e);
            result = "{error:\"getDisputeItemsListTabPageNo error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method getDisputeItemsListTabPageNo.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query DisputeItems accordion tab list data in a single
	 * 
	 */
	public String searchDisputeItemsListTab() throws Exception {
        logger.info("Enter method searchDisputeItemsListTab.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.searchDisputeItemsListTabView(svo);
        }catch(Exception e){
            logger.error("searchDisputeItemsListTab error: ", e);
            result = "{error:\"searchDisputeItemsListTab error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method searchDisputeItemsListTab.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query BrowseInvoice list of individual data on the total number of tab
	 * 
	 */
	public String getBrowseInvoiceListTabPageNo() throws Exception {
        logger.info("Enter method getBrowseInvoiceListTabPageNo.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.getBrowseInvoiceListTabTotalPageNo(svo);
        }catch(Exception e){
            logger.error("getBrowseInvoiceListTabPageNo error: ", e);
            result = "{error:\"getBrowseInvoiceListTabPageNo error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method getBrowseInvoiceListTabPageNo.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query BrowseInvoice accordion tab list data in a single
	 * 
	 */
	public String searchBrowseInvoiceListTab() throws Exception {
        logger.info("Enter method searchBrowseInvoiceListTab.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.searchBrowseInvoiceListTabView(svo);
        }catch(Exception e){
            logger.error("searchBrowseInvoiceListTab error: ", e);
            result = "{error:\"searchBrowseInvoiceListTab error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method searchBrowseInvoiceListTab.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query ItemType list of individual data on the total number of tab
	 * 
	 */
	public String getItemTypeListTabPageNo() throws Exception {
        logger.info("Enter method getItemTypeListTabPageNo.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.getItemTypeListTabTotalPageNo(svo);
        }catch(Exception e){
            logger.error("getItemTypeListTabPageNo error: ", e);
            result = "{error:\"getItemTypeListTabPageNo error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method getItemTypeListTabPageNo.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query ItemType accordion tab list data in a single
	 * 
	 */
	public String searchItemTypeListTab() throws Exception {
        logger.info("Enter method searchItemTypeListTab.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.searchItemTypeListTabView(svo);
        }catch(Exception e){
            logger.error("searchItemTypeListTab error: ", e);
            result = "{error:\"searchItemTypeListTab error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method searchItemTypeListTab.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query tab of the display data on the list
	 * 
	 */
	public String searchListTabData() throws Exception {
        logger.info("Enter method searchListTabData.");                                  
        String result = null;                                                         
        try{
            result = invoiceDetailService.searchListTabDataView(svo);
        }catch(Exception e){
            logger.error("searchListTabData error: ", e);
            result = "{error:\"searchListTabData error: "+e.getMessage()+"\"}";    
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method searchListTabData.");                                    
        return null;                                                                                                
    }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * By Invoice hyperlink address, returns a string
	 */
	public String getInvoiceHyperlink() throws Exception{
		logger.info("Enter method getInvoiceHyperlink.");
		String result = null;
		try{
			invoice = invoiceDetailService.getInvoiceByInvoiceId(invoiceId);
			result = invoiceDetailService.searchInvoiceByBanIdSame(invoice);
		}catch(Exception e){
			logger.error("getInvoiceHyperlink error: ", e);
			result = "{error:\"getInvoiceHyperlink error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceHyperlink.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * Modify the proposal data 
	 */
	public String updateProposalObject() throws Exception{
		logger.info("Enter method updateProposalObject.");
		String result = "{}";
		try{
			invoiceDetailService.updateProposalDataByProposalId(svo);
			invoiceDetailService.updateInvoiceItemAmount(svo);
			invoiceDetailService.updateDisputeAmount(svo);
		}catch(Exception e){
			logger.error("updateProposalObject error: ", e);
			result = "{error:\"updateProposalObject error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateProposalObject.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 *  Modify the gruop proposal data 
	 */
	public String updateGroupProposalObject() throws Exception{
		logger.info("Enter method updateGroupProposalObject.");
		String result = "{}";
		try{
			invoiceDetailService.updateGroupProposalDataByProposalBoxId(svo);
			invoiceDetailService.updateInvoiceItemAmount(svo);
			invoiceDetailService.updateDisputeAmount(svo);
		}catch(Exception e){
			logger.error("updateGroupProposalObject error: ", e);
			result = "{error:\"updateGroupProposalObject error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateGroupProposalObject.");
		return null;
	}
	/**
	 * The invoice notes will be updated
	 * @author xin.huang
	 * @param invoiceId, notes
	 * @throws BPLException
	 */
	public String updateInvoiceNotes() throws IOException{
		logger.info("Enter method updateInvoiceNotes.");
		String result = "";
		try {
			invoiceDetailService.updateInvoiceNotes(svo);
			result = "0";
		} catch ( Exception e) {
			logger.error("updateInvoiceNotes error: ", e);
			result = "{error:\"updateInvoiceNotes error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("exit method updateInvoiceNotes.");
		return null;
	}
	
	public void queryIsShowDueDate() throws BPLException{
		String result = "";
		if(invoice.getId() == null && invoiceId != null && !invoiceId.isEmpty()){
			invoice = invoiceDetailService.getInvoiceByInvoiceId(invoiceId);
		}
		if(invoiceDetailService.getBanDisputeDueDate(Integer.parseInt(invoiceId)) ||
		   invoiceDetailService.getInvoiceDisputeStatusCloseAndActionRequestOpenCount(invoice)){
			isShowDueDate = "true";
		}else {
			isShowDueDate = "false";
		}
		result = "{data:\""+isShowDueDate+"\"}";
		
		try {
			this.writeOutputStream(isShowDueDate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String searchInvoiceAttachment() throws IOException{
		logger.info("Enter method searchInvoiceAttachment.");
		String result = "";
		try {
			dataResult = invoiceDetailService.searchInvoiceAttachment(invoiceId);
			Invoice i = invoiceDetailService.getInvoiceByInvoiceId(invoiceId);
			User u = invoiceDetailService.searchInvoiceAnalystName(i);
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+invoiceSummaryFileName);
			String excelPath = invoiceDetailService.createInvoiceToExcel(invoiceId,excelDirPath);
			List<Map<String,Object>> originalList = invoiceDetailService.searchInvoiceOriginal(invoiceId);
			
			Map<String,String> invoiceSummaryMap = new HashMap<String,String>();
			invoiceSummaryMap.put("file_path", excelPath);
			invoiceSummaryMap.put("file_name", invoiceSummaryFileName);
			dataResult.put("invoiceSummary", invoiceSummaryMap);
			
			dataResult.put("originalList", originalList);
			
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
			DecimalFormat df = new DecimalFormat("0.00");
			
			String content = "Hi,"+
			"\n\nPlease find the attached details for invoice approval."+
			"\n\nVendor Name: "+i.getVendor().getVendorName()+
			"\nBAN: "+i.getBan().getAccountNumber().toString()+
			"\nInvoice Number: "+i.getInvoiceNumber().toString()+
			"\nInvoice Date: "+dateFormater.format(i.getInvoiceDate()).toString()+
			"\nInvoice Current Due: $"+df.format(i.getInvoiceCurrentDue()) +
			"\nAnalyst Name: "+u.getFirstName()+" "+u.getLastName()+
			"\n\nIf approval or rejection is not received by requested date the invoice may incur late payment charges (LPCs)."+
			"\nPlease respond within 10 business days of receipt by clicking the Approve or Reject link below:"+
			"\n\nThanks";
		
			dataResult.put("emailMessage", content);
			
			
			String subject = "Approval Required: " +i.getVendor().getVendorName()+"  Invoice: " +i.getInvoiceNumber().toString();
			dataResult.put("emailSubject", subject);
			
		} catch ( Exception e) {
			logger.error("searchInvoiceAttachment error: ", e);
			this.writeOutputStream(result);
		}
		logger.info("exit method searchInvoiceAttachment.");
		return SUCCESS;
	}
	
	public String sendInvoiceEmail () throws Exception {
		logger.info("Enter method sendInvoiceEmail.");
		String result = null;
		try{
			List<String[]> files = new ArrayList<String[]>();
			if(invoiceSummaryUploads!=null && invoiceSummaryUploads.size()>0){
				for(int i=0;i<invoiceSummaryUploads.size();i++){
					String filePath = invoiceSummaryUploads.get(i);
					String fileName;
					if(filePath.indexOf(invoiceSummaryFileName)>0){
						fileName = invoiceSummaryFileName;
					}else{
						fileName = filePath.substring(filePath.lastIndexOf("/")!=-1?filePath.lastIndexOf("/")+1:filePath.lastIndexOf("\\")+1);
					}
					String[] file = {fileName,filePath};					
					files.add(file);
					
				}
			}
			
			if(uploads!=null && uploads.size()>0){
				for(int j=0;j<uploads.size();j++){
					String uploadsFilePath = uploads.get(j).toString();
					String uploadsFileNameOne = uploadsFileName.get(j);
					
					String[] file = {uploadsFileNameOne,uploadsFilePath};
					
					files.add(file);
					
				}
			}

			AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("reconcile",uploads,uploadsFileName);
			Invoice invoice1 = invoiceDetailService.getInvoiceByInvoiceId(invoiceId);

			invoiceMessage.put("invoiceId", invoiceId);
			invoiceMessage.put("invoiceNumber", invoice1.getInvoiceNumber());
			
			String strEmails = invoiceMessage.get("To")+";"+invoiceMessage.get("Cc");
			String[] toEmails = strEmails.replaceAll(",", ";").split(";");
			for(String toEmail : toEmails) {
				if("".equals(toEmail)) continue;
				String[] externalEmail = invoiceDetailService.addExternalEmail(toEmail);
				invoiceMessage.put("To", toEmail);
				invoiceMessage.put("Cc", "");
				invoiceMessage.put("externalEmailId", externalEmail[0]);
				invoiceMessage.put("workflowUserName", externalEmail[1]);
				
				sendEmailService.sendInvoiceEmail(invoiceMessage,files);
			}
		    
		    invoiceDetailService.cleanExternalApprovalNotes(invoiceId);
		    
		    TransactionHistory th = new TransactionHistory();
		    invoice.setId(Integer.parseInt(invoiceId));
			th.setInvoice(invoice);
			invoiceStatus.setId(16);
			th.setInvoiceStatus(invoiceStatus);
			user.setId(SystemUtil.getCurrentUserId());
			th.setUser(user);
			th.setAttachmentPoint(point);
			invoiceDetailService.addTransactionHistory(th,"N",90,SystemUtil.getCurrentUserName(),null);
			
		}catch(Exception e){
			logger.error("sendInvoiceEmail error: ", e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			result = "{error:\"sendInvoiceEmail: "+bpe.getMessage()+"\"}";
			return INPUT;
//			this.writeOutputStream(result);
		}

		
		logger.info("Exit method sendInvoiceEmail.");
		return SUCCESS;
	}
	
	public String runReport()throws Exception{
		logger.info("Enter method runReport.");
		String result = null;
		FileInputStream fis = null;
		try{
			String fileName = "CABS Validation Report.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			fileName = invoiceDetailService.runReport(excelDirPath,Integer.valueOf(invoiceId),term);
			fis = new FileInputStream(excelDirPath);
			writeFile(fileName,fis);
		}catch(Exception e){
			logger.error("runReport error: ", e);
			result = "{error:\"runReport: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method runReport.");
		return null;
	}
	
	
	public String getProposalData() {
		return proposalData;
	}

	public void setProposalData(String proposalData) {
		this.proposalData = proposalData;
	}

	public String getFlagShortpaid() {
		return flagShortpaid;
	}

	public void setFlagShortpaid(String flagShortpaid) {
		this.flagShortpaid = flagShortpaid;
	}

	public String getFlagRecurring() {
		return flagRecurring;
	}

	public void setFlagRecurring(String flagRecurring) {
		this.flagRecurring = flagRecurring;
	}

	public String getDisputeNotes() {
		return disputeNotes;
	}

	public void setDisputeNotes(String disputeNotes) {
		this.disputeNotes = disputeNotes;
	}

	public String getProposalDiscription() {
		return proposalDiscription;
	}

	public void setProposalDiscription(String proposalDiscription) {
		this.proposalDiscription = proposalDiscription;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public IInvoiceDetailService getInvoiceDetailService() {
		return invoiceDetailService;
	}

	public void setInvoiceDetailService(IInvoiceDetailService invoiceDetailService) {
		this.invoiceDetailService = invoiceDetailService;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getChestrId() {
		return chestrId;
	}

	public void setChestrId(String chestrId) {
		this.chestrId = chestrId;
	}

	public InvoiceItem getInvoiceItem() {
		return invoiceItem;
	}

	public void setInvoiceItem(InvoiceItem invoiceItem) {
		this.invoiceItem = invoiceItem;
	}
	
	public SearchInvoiceVO getSvo() {
		return svo;
	}

	public void setSvo(SearchInvoiceVO svo) {
		this.svo = svo;
	}

	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}

//	public List<String> getLabelList() {
//		return labelList;
//	}
//
//	public void setLabelList(List<String> labelList) {
//		this.labelList = labelList;
//	}

	public List<MapEntryVO<String, String>> getAllLabelList() {
		return allLabelList;
	}

	public void setAllLabelList(List<MapEntryVO<String, String>> allLabelList) {
		this.allLabelList = allLabelList;
	}

	public boolean isBo() {
		return bo;
	}

	public void setBo(boolean bo) {
		this.bo = bo;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getInco() {
		return inco;
	}

	public void setInco(String inco) {
		this.inco = inco;
	}

	public String getTnco() {
		return tnco;
	}

	public void setTnco(String tnco) {
		this.tnco = tnco;
	}

	public String getPaynco() {
		return paynco;
	}

	public void setPaynco(String paynco) {
		this.paynco = paynco;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}
	
	public String getAuditSourceType() {
		return auditSourceType;
	}

	public void setAuditSourceType(String auditSourceType) {
		this.auditSourceType = auditSourceType;
	}

	public List<MapEntryVO<String, String>> getScoaCodeList() {
		return scoaCodeList;
	}

	public void setScoaCodeList(List<MapEntryVO<String, String>> scoaCodeList) {
		this.scoaCodeList = scoaCodeList;
	}

	public List<MapEntryVO<String, String>> getRelatedInvoicesList() {
		return relatedInvoicesList;
	}

	public void setRelatedInvoicesList(
			List<MapEntryVO<String, String>> relatedInvoicesList) {
		this.relatedInvoicesList = relatedInvoicesList;
	}

	public String getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(String invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public User getAssignedAnalys() {
		return assignedAnalys;
	}

	public void setAssignedAnalys(User assignedAnalys) {
		this.assignedAnalys = assignedAnalys;
	}

	public String getInvoiceTaxAndOtherFees() {
		return invoiceTaxAndOtherFees;
	}

	public void setInvoiceTaxAndOtherFees(String invoiceTaxAndOtherFees) {
		this.invoiceTaxAndOtherFees = invoiceTaxAndOtherFees;
	}

	public List<MapEntryVO<String, String>> getAccountCodeList() {
		return accountCodeList;
	}

	public void setAccountCodeList(List<MapEntryVO<String, String>> accountCodeList) {
		this.accountCodeList = accountCodeList;
	}

	public List<MapEntryVO<String, String>> getDisputeReasonList() {
		return disputeReasonList;
	}

	public void setDisputeReasonList(
			List<MapEntryVO<String, String>> disputeReasonList) {
		this.disputeReasonList = disputeReasonList;
	}
 
	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}
	public String getMscid() {
		return mscid;
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

	public void setMscid(String mscid) {
		this.mscid = mscid;
	}

	public String getPayVendor() {
		return payVendor;
	}

	public void setPayVendor(String payVendor) {
		this.payVendor = payVendor;
	}


	public String getMSortingDirection() {
		return mSortingDirection;
	}
	public void setMSortingDirection(String sortingDirection) {
		mSortingDirection = sortingDirection;
	}

	public String getMPaymentAmount() {
		return mPaymentAmount;
	}

	public void setMPaymentAmount(String paymentAmount) {
		mPaymentAmount = paymentAmount;
	}

	public String getMAccountCodeId() {
		return mAccountCodeId;
	}

	public void setMAccountCodeId(String accountCodeId) {
		mAccountCodeId = accountCodeId;
	}

	public String getInvoicePayIdd() {
		return invoicePayIdd;
	}

	public void setInvoicePayIdd(String invoicePayIdd) {
		this.invoicePayIdd = invoicePayIdd;
	}

	public int getProPaslId() {
		return proPaslId;
	}

	public void setProPaslId(int proPaslId) {
		this.proPaslId = proPaslId;
	}

	public List<MapEntryVO<String, String>> getOriginatorList() {
		return originatorList;
	}

	public void setOriginatorList(List<MapEntryVO<String, String>> originatorList) {
		this.originatorList = originatorList;
	}


	public List<MapEntryVO<String, String>> getDisputeList() {
		return disputeList;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setDisputeList(List<MapEntryVO<String, String>> disputeList) {
		this.disputeList = disputeList;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public String getDisputeAmount() {
		return disputeAmount;
	}

	public void setDisputeAmount(String disputeAmount) {
		this.disputeAmount = disputeAmount;
	}

	public IDisputeTabService getDisputeTabService() {
		return disputeTabService;
	}

	public void setDisputeTabService(IDisputeTabService disputeTabService) {
		this.disputeTabService = disputeTabService;
	}


	public String getInvoiceDisputeTabId() {
		return invoiceDisputeTabId;
	}

	public void setInvoiceDisputeTabId(String invoiceDisputeTabId) {
		this.invoiceDisputeTabId = invoiceDisputeTabId;
	}

	public String getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOriginatorId() {
		return originatorId;
	}

	public void setOriginatorId(String originatorId) {
		this.originatorId = originatorId;
	}

	public String getSCOA() {
		return sCOA;
	}

	public void setSCOA(String scoa) {
		sCOA = scoa;
	}

	public String[] getProposalIds() {
		return proposalIds;
	}

	public void setProposalIds(String[] proposalIds) {
		this.proposalIds = proposalIds;
	}

	public int getDisputeId() {
		return disputeId;
	}

	public void setDisputeId(int disputeId) {
		this.disputeId = disputeId;
	}

	public List<MapEntryVO<String, String>> getDisputeStatusList() {
		return disputeStatusList;
	}

	public void setDisputeStatusList(
			List<MapEntryVO<String, String>> disputeStatusList) {
		this.disputeStatusList = disputeStatusList;
	}


	public int getDisputeReasonId() {
		return disputeReasonId;
	}

	public SearchInvoiceVO getPvo() {
		return pvo;
	}

	public void setDisputeReasonId(int disputeReasonId) {
		this.disputeReasonId = disputeReasonId;
	}

	public void setPvo(SearchInvoiceVO pvo) {
		this.pvo = pvo;
	}

	public int getDisputeStatusId() {
		return disputeStatusId;
	}

	public void setDisputeStatusId(int disputeStatusId) {
		this.disputeStatusId = disputeStatusId;
	}

	public String getRecPerPage() {
		return recPerPage;
	}

	public void setRecPerPage(String recPerPage) {
		this.recPerPage = recPerPage;
	}

	public String getInputMsg() {
		return inputMsg;
	}

	public void setInputMsg(String inputMsg) {
		this.inputMsg = inputMsg;
	}


	public String getDisputeIdRight() {
		return disputeIdRight;
	}

	public void setDisputeIdRight(String disputeIdRight) {
		this.disputeIdRight = disputeIdRight;
	}

	public String getConfidenceLevel() {
		return confidenceLevel;
	}

	public void setConfidenceLevel(String confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public String getCricuitNumber() {
		return cricuitNumber;
	}

	public void setCricuitNumber(String cricuitNumber) {
		this.cricuitNumber = cricuitNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getHandleWorkflowResult() {
		return handleWorkflowResult;
	}

	public void setHandleWorkflowResult(String handleWorkflowResult) {
		this.handleWorkflowResult = handleWorkflowResult;
	}

	public String getEmailCopyTo() {
		return emailCopyTo;
	}

	public void setEmailCopyTo(String emailCopyTo) {
		this.emailCopyTo = emailCopyTo;
	}

	public String getTotalDisputeAmount() {
		return totalDisputeAmount;
	}

	public void setTotalDisputeAmount(String totalDisputeAmount) {
		this.totalDisputeAmount = totalDisputeAmount;
	}

	public int getFromDisputeId() {
		return fromDisputeId;
	}

	public void setFromDisputeId(int fromDisputeId) {
		this.fromDisputeId = fromDisputeId;
	}

	public String getAttachmentPointId() {
		return attachmentPointId;
	}

	public void setAttachmentPointId(String attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}

	public String getInProposalIds() {
		return inProposalIds;
	}

	public void setInProposalIds(String inProposalIds) {
		this.inProposalIds = inProposalIds;
	}

	public String getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(String jsonResult) {
		this.jsonResult = jsonResult;
	}
 

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Integer getAccountCodeId() {
		return accountCodeId;
	}

	public void setAccountCodeId(Integer accountCodeId) {
		this.accountCodeId = accountCodeId;
	}

	public String getDisputeIds() {
		return disputeIds;
	}

	public void setDisputeIds(String disputeIds) {
		this.disputeIds = disputeIds;
	}

	public Integer getReconcileId() {
		return reconcileId;
	}

	public void setReconcileId(Integer reconcileId) {
		this.reconcileId = reconcileId;
	}

	public SearchInvoiceVO getSearchInvoiceVO() {
		return searchInvoiceVO;
	}

	public void setSearchInvoiceVO(SearchInvoiceVO searchInvoiceVO) {
		this.searchInvoiceVO = searchInvoiceVO;
	}
	
	

	public ISendEmailService getSendEmailService() {
		return sendEmailService;
	}

	public void setSendEmailService(ISendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

	public List<HashMap<String, String>> getInvoiceLabelList() {
		return invoiceLabelList;
	}

	public void setInvoiceLabelList(List<HashMap<String, String>> invoiceLabelList) {
		this.invoiceLabelList = invoiceLabelList;
	}

	public String getProposalNote() {
		return proposalNote;
	}

	public void setProposalNote(String proposalNote) {
		this.proposalNote = proposalNote;
	}
	
	public String getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(String reservedAmount) {
		this.reservedAmount = reservedAmount;
	}

	public InvoiceDetailAction() {
	}

	public Integer getBanId() {
		return banId;
	}

	public void setBanId(Integer banId) {
		this.banId = banId;
	}
	public String getDisputeReasonText() {
		return disputeReasonText;
	}

	public void setDisputeReasonText(String disputeReasonText) {
		this.disputeReasonText = disputeReasonText;
	}

	public String getCurrentAnalystId() {
		return currentAnalystId;
	}

	public void setCurrentAnalystId(String currentAnalystId) {
		this.currentAnalystId = currentAnalystId;
	}

	public String getCurrentAssignmentId() {
		return currentAssignmentId;
	}

	public void setCurrentAssignmentId(String currentAssignmentId) {
		this.currentAssignmentId = currentAssignmentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	public String getInIds() {
		return inIds;
	}

	public void setInIds(String inIds) {
		this.inIds = inIds;
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	public String getOldInvoiceStatusId() {
		return oldInvoiceStatusId;
	}

	public void setOldInvoiceStatusId(String oldInvoiceStatusId) {
		this.oldInvoiceStatusId = oldInvoiceStatusId;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public List<String> getEffectiveFile() {
		return effectiveFile;
	}

	public void setEffectiveFile(List<String> effectiveFile) {
		this.effectiveFile = effectiveFile;
	}

	public String getApproveWinUserId() {
		return approveWinUserId;
	}

	public void setApproveWinUserId(String approveWinUserId) {
		this.approveWinUserId = approveWinUserId;
	}

	public String getApproveWinRoleId() {
		return approveWinRoleId;
	}

	public void setApproveWinRoleId(String approveWinRoleId) {
		this.approveWinRoleId = approveWinRoleId;
	}

	public List<Map<String, Object>> getValidationResultList() {
		return validationResultList;
	}

	public void setValidationResultList(
			List<Map<String, Object>> validationResultList) {
		this.validationResultList = validationResultList;
	}

	public Map<String, Object> getAuditIcon() {
		return auditIcon;
	}

	public void setAuditIcon(Map<String, Object> auditIcon) {
		this.auditIcon = auditIcon;
	}
	

	/**
	 * @return the autopayErrorNotes
	 */
	public List<HashMap<String, String>> getAutopayErrorNotes() {
		return autopayErrorNotes;
	}

	/**
	 * @param autopayErrorNotes the autopayErrorNotes to set
	 */
	public void setAutopayErrorNotes(List<HashMap<String, String>> autopayErrorNotes) {
		this.autopayErrorNotes = autopayErrorNotes;
	}

	public String getBanAuditFlag() {
		return banAuditFlag;
	}

	public void setBanAuditFlag(String banAuditFlag) {
		this.banAuditFlag = banAuditFlag;
	}
	
	public List<Map<String, String>> getToleranceRateList() {
		return toleranceRateList;
	}

	public void setToleranceRateList(List<Map<String, String>> toleranceRateList) {
		this.toleranceRateList = toleranceRateList;
	}

	public String getIsShowDueDate() {
		return isShowDueDate;
	}

	public void setIsShowDueDate(String isShowDueDate) {
		this.isShowDueDate = isShowDueDate;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getAutopaySwitch() {
		return autopaySwitch;
	}

	public void setAutopaySwitch(String autopaySwitch) {
		this.autopaySwitch = autopaySwitch;
	}

	public String getBanExemptionFlag() {
		return banExemptionFlag;
	}

	public void setBanExemptionFlag(String banExemptionFlag) {
		this.banExemptionFlag = banExemptionFlag;
	}

	public Map<String, Object> getDataResult() {
		return dataResult;
	}
	
	public List<MapEntryVO<String, String>> getProductList() {
		return productList;
	}
	
	public void setDataResult(Map<String, Object> dataResult) {
		this.dataResult = dataResult;
	}
	
	public void setProductList(List<MapEntryVO<String, String>> productList) {
		this.productList = productList;
	}
	
	public Map<String, String> getInvoiceMessage() {
		return invoiceMessage;
	}

	public void setInvoiceMessage(Map<String, String> invoiceMessage) {
		this.invoiceMessage = invoiceMessage;
	}

	public List<MapEntryVO<String, String>> getProductComponentList() {
		return productComponentList;
	}

	public void setProductComponentList(
			List<MapEntryVO<String, String>> productComponentList) {
		this.productComponentList = productComponentList;
	}

	public List<MapEntryVO<String, String>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<MapEntryVO<String, String>> provinceList) {
		this.provinceList = provinceList;
	}

	public Map<String, Object> getMemory() {
		return memory;
	}

	public void setMemory(Map<String, Object> memory) {
		this.memory = memory;
	}

	public AuditMemory getAuditMemory() {
		return auditMemory;
	}

	public void setAuditMemory(AuditMemory auditMemory) {
		this.auditMemory = auditMemory;
	}
	
	

	/**
	 * @return the uploadSCOAs
	 */
	public List<File> getUploadSCOAs() {
		return uploadSCOAs;
	}

	/**
	 * @param uploadSCOAs the uploadSCOAs to set
	 */
	public void setUploadSCOAs(List<File> uploadSCOAs) {
		this.uploadSCOAs = uploadSCOAs;
	}

	/**
	 * @return the uploadSCOAsFileName
	 */
	public List<String> getUploadSCOAsFileName() {
		return uploadSCOAsFileName;
	}

	/**
	 * @param uploadSCOAsFileName the uploadSCOAsFileName to set
	 */
	public void setUploadSCOAsFileName(List<String> uploadSCOAsFileName) {
		this.uploadSCOAsFileName = uploadSCOAsFileName;
	}
	
	

	/**
	 * @return the returnMap
	 */
	public Map<String, Object> getReturnMap() {
		return returnMap;
	}

	/**
	 * @param returnMap the returnMap to set
	 */
	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}

	public List<HashMap<String, String>> getBanExemptionDateList() {
		return banExemptionDateList;
	}

	public void setBanExemptionDateList(
			List<HashMap<String, String>> banExemptionDateList) {
		this.banExemptionDateList = banExemptionDateList;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getWorkflowUserName() {
		return workflowUserName;
	}

	public void setWorkflowUserName(String workflowUserName) {
		this.workflowUserName = workflowUserName;
	}

	public String getIsUsageBan() {
		return isUsageBan;
	}

	public void setIsUsageBan(String isUsageBan) {
		this.isUsageBan = isUsageBan;
	}

	/**
	 * @return the errorFileName
	 */
	public String getErrorFileName() {
		return errorFileName;
	}

	/**
	 * @param errorFileName the errorFileName to set
	 */
	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}

	public List<String> getInvoiceSummaryUploads() {
		return invoiceSummaryUploads;
	}

	public void setInvoiceSummaryUploads(List<String> invoiceSummaryUploads) {
		this.invoiceSummaryUploads = invoiceSummaryUploads;
	}

	public String getOpenAutoPay() {
		return openAutoPay;
	}

	public void setOpenAutoPay(String openAutoPay) {
		this.openAutoPay = openAutoPay;
	}
	
	


	
}