/**
 * 
 */
package com.saninco.ccm.action.invoice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.invoice.IInvoiceService;
import com.saninco.ccm.service.invoice.IIvoiceNoteService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.FileUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.InvoiceNoteVO;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * Invoice related UI action class.
 * 
 * @author xinyu.Liu
 * 
 */
public class InvoiceAction extends CcmActionSupport {

	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ICommonLookupService commonLookupService;
	private IInvoiceService invoiceService;
	private IQuicklinkService quicklinkService;
	private SearchInvoiceVO searchInvoiceVO;
	
	private IIvoiceNoteService invoiceNoteService;
	private InvoiceNoteVO invoiceNoteVO;
	
	
	private String quicklinkName;
	private String queryString;
	private String selVendorId;
	
	private List<String> titles;
	private List<String> effectiveFile;
	private String labelIds;
	
	private String noteInvoiceId;
	private String privateFlag;
	private String starFlag;
	private String noteUserId;
	private String noteRoleId;
	private String noteBanId;
	private String noteNotes;
	private String messageId;
	private String channelId;
	private String attachmentFileId;
	private Boolean addBanNoteFlag;
	
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> analystList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> lineOfBusiness = new ArrayList<MapEntryVO<String, String>>();
	
	
	
	private List<File> uploads;
	private List<String> uploadsFileName;
	private String uploadsMessage;
	/**
	 * 
	 */
	public InvoiceAction() {
	}

	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@AllGranted(value="FUNCTION_1000")
	public String exec() throws Exception {
		logger.info("Enter method exec");
		populateInvoiceSearchLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	/**
	 * Drop-down list, save in the list of transmission to the page
	 * */
	private void populateInvoiceSearchLookupList() throws BPLException {
		logger.info("Enter method populateInvoiceSearchLookupList.");
		//update by donghao.guo 2011/10/25
		this.vendorList = commonLookupService.getSearchConditionVendors();
		//update by donghao.guo 2011/10/25
		this.banList = commonLookupService.getSearchConditionBans();
		this.paymentStatusList = commonLookupService.getPaymentStatus();
		this.analystList = commonLookupService.getAnalysts();
		this.invoiceStatusList = commonLookupService.getInvoiceStatus();
		this.lineOfBusiness = commonLookupService.getLineofBbusiness();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method populateInvoiceSearchLookupList.");
	}

	/**
	 * @author xinyu.Liu (searchInvoice.js)
	 * Do search invoice action.
	 * 
	 */
	public String searchInvoice() throws Exception {
		logger.info("Enter method searchInvoice.");
		String result = null;
		try{
			result = invoiceService.searchInvoice(searchInvoiceVO);
		}catch(Exception e){
			logger.error("searchInvoice error: ", e);
			result = "{error:\"searchInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchInvoice.");
		return null;
	}
	
	/**
	 * @author
	 * Get invoice search total result number and page.
	 * 
	 */
	public String getInvoiceAssignmentSearchTotalPageNo() throws Exception{
		logger.info("Enter method getInvoiceAssignmentSearchTotalPageNo.");
		String result = null;
		try {
			result = invoiceService.getInvoiceAssignmentSearchTotalPageNo(searchInvoiceVO);
		} catch (BPLException e) {
			logger.error("getInvoiceAssignmentSearchTotalPageNo error: ", e);
			result = "{error:\"getInvoiceAssignmentSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getInvoiceAssignmentSearchTotalPageNo.");
		return null;
	}
	
	/**
	 * @author
	 * Do search invoice action.
	 * 
	 */
	public String searchInvoiceAssignment() throws Exception {
		logger.info("Enter method searchInvoiceAssignment.");
		String result = null;
		try {
			result = invoiceService.searchInvoiceAssignment(searchInvoiceVO);
		} catch (BPLException e) {
			logger.error("searchInvoiceAssignment error: ", e);
			result = "{error:\"searchInvoiceAssignment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceAssignment.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (searchInvoice.js)
	 * Get invoice search total result number and page.
	 * 
	 */
	public String getInvoiceSearchTotalPageNo() throws Exception{
		logger.info("Enter method getInvoiceSearchTotalPageNo.");
		String result = null;
		try{
			result = invoiceService.getInvoiceSearchTotalPageNo(searchInvoiceVO);
		}catch(Exception e){
			logger.error("getInvoiceSearchTotalPageNo error: ", e);
			result = "{error:\"getInvoiceSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceSearchTotalPageNo.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (searchInvoice.js)
	 * Do save query and return the saved query name displaying on the quick link column 
	 * in the left side panel.
	 * 
	 */
	public String saveInvoiceSearch() throws Exception {
		logger.info("Enter method saveSearchQuery.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.INVOICE);
		}catch(Exception e){
			logger.error("saveSearchQuery error: ", e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			result = "{error:\"saveSearchQuery: "+bpe.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveSearchQuery.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (searchInvoice.js)
	 * Get user previledged vendor list.
	 * 
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
	 * @author xinyu.Liu (searchInvoice.js)
	 * Get Ban list by vendor id.
	 * 
	 */
	public String getBanListByVendorId() throws Exception {
		logger.info("Enter method getBanListByVendorId.");
		String result = null;
		try{
			result = this.commonLookupService.getUserPreviledgedBansJsonStrByVendorId(new Integer(this.selVendorId));
		}catch(Exception e){
			logger.error("getBanListByVendorId error: ", e);
			result = "{error:\"getBanListByVendorId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getBanListByVendorId.");
		return null;
	}
	/**
	 * yongzhen.li
	 * @return
	 * @throws Exception
	 */
	public String getBanListByVendorId0() throws Exception {
		logger.info("Enter method getBanListByVendorId.");
		String result = null;
		try{
			result = this.commonLookupService.getUserPreviledgedBansJsonStrByVendorId0(new Integer(this.selVendorId));
		}catch(Exception e){
			logger.error("getBanListByVendorId error: ", e);
			result = "{error:\"getBanListByVendorId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getBanListByVendorId.");
		return null;
	}
	
	
	/**
	 * @author Chao.Liu 
	 * This is Admin Page Tab Two
	 * 
	 */
	public String searchInvoiceByIName()throws Exception {
		logger.info("Enter method searchInvoiceByIName.");
		String result = null;
		try{
			result = invoiceService.searchInvoiceByIName(searchInvoiceVO);
		}catch(Exception e){
			logger.error("searchInvoiceByIName error: ", e);
			result = "{error:\"searchInvoiceByIName error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceByIName.");
		return null;
	}
	
	public String searchInvoiceForPRJ()throws Exception {
		logger.info("Enter method searchInvoiceForPRJ.");
		String result = null;
		try{
			result = invoiceService.searchInvoiceForPRJ(searchInvoiceVO);
		}catch(Exception e){
			logger.error("searchInvoiceForPRJ error: ", e);
			result = "{error:\"searchInvoiceForPRJ error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceForPRJ.");
		return null;
	}
	
	public String searchInvoiceForPRS()throws Exception {
		logger.info("Enter method searchInvoiceForPRS.");
		String result = null;
		try{
			result = invoiceService.searchInvoiceForPRS(searchInvoiceVO);
		}catch(Exception e){
			logger.error("searchInvoiceForPRS error: ", e);
			result = "{error:\"searchInvoiceForPRS error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceForPRS.");
		return null;
	}
	
	public String pRSConfirmed()throws Exception {
		logger.info("Enter method pRSConfirm.");
		String result = null;
		try{
			result = invoiceService.pRSConfirmed(searchInvoiceVO);
		}catch(Exception e){
			logger.error("pRSConfirm error: ", e);
			result = "{error:\"pRSConfirm error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method pRSConfirm.");
		return null;
	}
	
	public String searchForScoa()throws Exception {
		logger.info("Enter method searchForScoa.");
		String result = null;
		try{
			result = invoiceService.updateSearchForScoa(searchInvoiceVO);
		}catch(Exception e){
			logger.error("searchForScoa error: ", e);
			result = "{error:\"searchForScoa error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchForScoa.");
		return null;
	}
	/**
	 * @author xinyu.Liu (searchInvoice.js)
	 * Automatically generate invoiceSearchExcel.xls file server and turn it into a stream output to the client io
	 * 
	 */
	public String saveExcelBySearchInvoice() throws Exception{
		logger.info("Exit method saveExcelBySearchInvoice.");
		FileInputStream fis = null;
		try {
			String fileName = "invoiceSearchExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceService.createInvoiceToExcel(searchInvoiceVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchInvoice error: ", e);
		}
		logger.info("Exit method saveExcelBySearchInvoice.");
		return null;
	}
	
	public String getInvoiceByNumberSearchTotalPageNo() throws Exception{
		logger.info("Enter method getInvoiceByNumberSearchTotalPageNo.");
		String result = null;
		try{
			result = invoiceService.getInvoiceByNumberSearchTotalPageNo(searchInvoiceVO);
		}catch(Exception e){
			logger.error("getInvoiceByNumberSearchTotalPageNo error: ", e);
			result = "{error:\"getInvoiceByNumberSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceByNumberSearchTotalPageNo.");
		return null;
	}
	
	public String searchPTOWInvoiceByINumber() throws Exception {
		logger.info("Enter method searchPTOWInvoiceByINumber.");
		String result = null;
		try{
			result = invoiceService.searchInvoiceByINumber(searchInvoiceVO);
		}catch(Exception e){
			logger.error("searchInvoice error: ", e);
			result = "{error:\"searchInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchPTOWInvoiceByINumber.");
		return null;
	}
	
	public String findInvoiceStatus() throws Exception {
		logger.info("Enter method findInvoiceStatus.");
		String result = null;
		try{
			result = invoiceService.updateInvoiceOrfindInvoiceStatus(searchInvoiceVO);
		}catch(Exception e){
			logger.error("findInvoiceStatus error: ", e);
			result = "{error:\"findInvoiceStatus: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method findInvoiceStatus.");
		return null;
	}
	
	public String closeInvoice() throws Exception {
		logger.info("Enter method closeInvoice.");
		String result = null;
		try{
			result = invoiceService.closeInvoice(searchInvoiceVO);
		}catch(Exception e){
			logger.error("closeInvoice error: ", e);
			result = "{error:\"closeInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method closeInvoice.");
		return null;
	}
	
	public String removeInvoice() throws Exception {
		logger.info("Enter method removeInvoice.");
		String result = null;
		try{
			result = invoiceService.removeInvoice(searchInvoiceVO);
		}catch(Exception e){
			logger.error("removeInvoice error: ", e);
			result = "{error:\"removeInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method removeInvoice.");
		return null;
	}
	
	public String cancelInvoice() throws Exception {
		logger.info("Enter method cancelInvoice.");
		String result = null;
		try{
			result = invoiceService.cancelInvoice(searchInvoiceVO);
		}catch(Exception e){
			logger.error("cancelInvoice error: ", e);
			result = "{error:\"cancelInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method cancelInvoice.");
		return null;
	}
	
	public String throughAPPaymentCloseInvoice() throws Exception {
		logger.info("Enter method throughAPPaymentCloseInvoice.");
		String result = null;
		try{
			result = invoiceService.throughAPPaymentCloseInvoice(searchInvoiceVO);
		}catch(Exception e){
			logger.error("throughAPPaymentCloseInvoice error: ", e);
			result = "{error:\"throughAPPaymentCloseInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method throughAPPaymentCloseInvoice.");
		return null;
	}
	
	public String tRegularCloseInvoice() throws Exception {
		logger.info("Enter method tRegularCloseInvoice.");
		String result = null;
		try{
			result = invoiceService.tRegularCloseInvoice(searchInvoiceVO);
		}catch(Exception e){
			logger.error("tRegularCloseInvoice error: ", e);
			result = "{error:\"tRegularCloseInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method tRegularCloseInvoice.");
		return null;
	}
	
	public String searchInvoiceLabel() throws Exception {
		logger.info("Enter method searchInvoiceLabel.");
		String result = null;
		try{
			result = invoiceService.searchInvoiceLabel(labelIds);
		}catch(Exception e){
			logger.error("tRegularCloseInvoice error: ", e);
			result = "{error:\"tRegularCloseInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchInvoiceLabel.");
		return null;
	}

	/**
	 * @author mingyang.Li
	 * This is Admin Page Tab 
	 * 
	 */
	public String searchInvoiceByInvoiceNumber()throws Exception {
		logger.info("Enter method searchInvoiceByInvoiceNumber.");
		String result = null;
		try{
			result = invoiceService.searchInvoiceByInvoiceNumber(searchInvoiceVO);
		}catch(Exception e){
			logger.error("searchInvoiceByInvoiceNumber error: ", e);
			result = "{error:\"searchInvoiceByInvoiceNumber error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceByInvoiceNumber.");
		return null;
	}
	
	/**
	 * @author mingyang.Li
	 * Move Previous Adjustment to Current Invoice Adjustment
	 * @return
	 * @throws Exception
	 */
	public String saveModifyPreviousAdjustment()throws Exception{
		logger.info("Enter method saveModifyPreviousAdjustment.");
		String result = "{}";       
		try{
			result = invoiceService.saveModifyPreviousAdjustment(searchInvoiceVO);
		}catch(Exception e){
			logger.error("saveModifyPreviousAdjustment error: ", e);
			result = "{error:\"saveModifyPreviousAdjustment error: "+e.getMessage()+"\"}";    
		}
		this.writeOutputStream(result);   
		logger.info("Exit method saveModifyPreviousAdjustment.");
		return null;
	}
	
	/**
	 * @author mingyang.Li
	 * Move Previous Adjustment to Current Invoice Adjustment
	 * @return
	 * @throws Exception
	 */
	public String saveMoveCurrentToAdjustment()throws Exception{
		logger.info("Enter method saveMoveCurrentToAdjustment.");
		String result = "{}";       
		try{
			result = invoiceService.saveMoveCurrentToAdjustment(searchInvoiceVO);
		}catch(Exception e){
			logger.error("saveMoveCurrentToAdjustment error: ", e);
			result = "{error:\"saveMoveCurrentToAdjustment error: "+e.getMessage()+"\"}";    
		}
		this.writeOutputStream(result);   
		logger.info("Exit method saveMoveCurrentToAdjustment.");
		return null;
	}
	
	
	/**
	 * @author yongzhen
	 * @param searchInvoiceVO
	 */
	
	public String doGetInvoiceNoteTotalPageNoUri()throws Exception{
		logger.info("Enter method getTragetSearchTotalPageNo.");
		String result = null;
		try{
    		result = invoiceNoteService.getInvoiceNoteTotalPageNo(invoiceNoteVO);
		}catch(Exception e){
			logger.error("SearchTotalPageNo error: ", e);
			result = "{error:\"SearchTotalPageNo: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTragetSearchTotalPageNo.");
		return null;
	}
	
	public String doGetUserAndRoleUri()throws Exception{
		logger.info("Enter method doGetUserAndRoleUri.");
		String result = null;
		try{
			result = invoiceNoteService.doGetUserAndRoleUri();
		}catch(Exception e){
			logger.error("doGetUserAndRoleUri error: ", e);
			result = "{error:\"doGetUserAndRoleUri: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doGetUserAndRoleUri.");
		return null;
	}
	
	
	public String doGetInvoiceNoteUri()throws Exception{
		logger.info("Enter method doGetInvoiceNoteUri.");
		String result = null;
		try{
			result = invoiceNoteService.searchInvoiceNote(invoiceNoteVO);
		}catch(Exception e){
			logger.error("searchInvoice error: ", e);
			result = "{error:\"searchInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doGetInvoiceNoteUri.");
		return null;
	}
	
	public String doSaveInvoiceNotes()throws Exception{
		logger.info("Enter method doSaveInvoiceNotes.");
		String result = null;
		try{
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
				point = commonLookupService.saveUploadFileAndGetPointId("invoice_notes",filterUploads, filterUploadsFileName);
			}
			result = invoiceNoteService.saveInvoiceNoteAndMsMessage(noteInvoiceId,noteBanId,noteNotes,point,addBanNoteFlag,
		    											   privateFlag,starFlag,noteUserId,noteRoleId);
			messageId = "";
			channelId = "";
			if(!"".equals(result)){
				String s[] = result.split(",");
				if(s.length == 2){
					messageId = s[0];
					channelId =	s[1];
				}
			}
			
			logger.info("Exit method doSaveInvoiceNotes.");
			return SUCCESS;
		}catch(Exception e){
			logger.error("doSaveInvoiceNotes error: ", e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			result = "{error:\"doSaveInvoiceNotes: "+bpe.getMessage()+"\"}";
			this.writeOutputStream(result);
		}
			
		logger.info("Exit method doSaveInvoiceNotes.");
		return SUCCESS;
	}
	
	public String downloadUsageReport()throws Exception{
		logger.info("Enter method downloadUsageReport.");
		String result = null;
		FileInputStream fis = null;
		try{
//			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator);
			String excelDirPath = SystemUtil.getSysConfigMap().get("invoice_file_path");
			result = invoiceService.downloadUsageReport(Integer.valueOf(searchInvoiceVO.getInvoiceId()),excelDirPath+File.separator);
			fis = new FileInputStream(excelDirPath+File.separator+result);
			writeFile(result,fis);
		}catch(Exception e){
			logger.error("searchInvoice error: ", e);
			result = "{error:\"searchInvoice: "+e.getMessage()+"\"}";
		}
		return null;
	}
	
	public void deleteInvoiceNote()throws Exception{
		invoiceService.deleteInvoiceNote(attachmentFileId);
	}
	
	public String doSaveInvoiceNotesSucess()throws Exception{
		return SUCCESS;
	}
	
	public void setSearchInvoiceVO(SearchInvoiceVO searchInvoiceVO) {
		this.searchInvoiceVO = searchInvoiceVO;
	}

	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public List<MapEntryVO<String, String>> getInvoiceStatusList() {
		return invoiceStatusList;
	}

	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
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

	public void setSelVendorId(String selVendorId) {
		this.selVendorId = selVendorId;
	}

	public SearchInvoiceVO getSearchInvoiceVO() {
		return searchInvoiceVO;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public List<MapEntryVO<String, String>> getAnalystList() {
		return analystList;
	}

	public void setAnalystList(List<MapEntryVO<String, String>> analystList) {
		this.analystList = analystList;
	}

	public List<MapEntryVO<String, String>> getPaymentStatusList() {
		return paymentStatusList;
	}

	public void setPaymentStatusList(
			List<MapEntryVO<String, String>> paymentStatusList) {
		this.paymentStatusList = paymentStatusList;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public Logger getLogger() {
		return logger;
	}

	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public String getQuicklinkName() {
		return quicklinkName;
	}

	public String getSelVendorId() {
		return selVendorId;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public void setInvoiceStatusList(
			List<MapEntryVO<String, String>> invoiceStatusList) {
		this.invoiceStatusList = invoiceStatusList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

	public List<MapEntryVO<String, String>> getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(List<MapEntryVO<String, String>> lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public IIvoiceNoteService getInvoiceNoteService() {
		return invoiceNoteService;
	}

	public void setInvoiceNoteService(IIvoiceNoteService invoiceNoteService) {
		this.invoiceNoteService = invoiceNoteService;
	}

	public InvoiceNoteVO getInvoiceNoteVO() {
		return invoiceNoteVO;
	}

	public void setInvoiceNoteVO(InvoiceNoteVO invoiceNoteVO) {
		this.invoiceNoteVO = invoiceNoteVO;
	}

	//----

	public String getNoteBanId() {
		return noteBanId;
	}

	public void setNoteBanId(String noteBanId) {
		this.noteBanId = noteBanId;
	}

	public String getNoteNotes() {
		return noteNotes;
	}

	public void setNoteNotes(String noteNotes) {
		this.noteNotes = noteNotes;
	}
	

	public Boolean getAddBanNoteFlag() {
		return addBanNoteFlag;
	}

	public void setAddBanNoteFlag(Boolean addBanNoteFlag) {
		this.addBanNoteFlag = addBanNoteFlag;
	}

	public String getNoteInvoiceId() {
		return noteInvoiceId;
	}

	public void setNoteInvoiceId(String noteInvoiceId) {
		this.noteInvoiceId = noteInvoiceId;
	}
	
	public String getPrivateFlag() {
		return privateFlag;
	}

	public void setPrivateFlag(String privateFlag) {
		this.privateFlag = privateFlag;
	}

	public List<String> getEffectiveFile() {
		return effectiveFile;
	}

	public void setEffectiveFile(List<String> effectiveFile) {
		this.effectiveFile = effectiveFile;
	}

	public List<File> getUploads() {
		return uploads;
	}

	public String getStarFlag() {
		return starFlag;
	}

	public void setStarFlag(String starFlag) {
		this.starFlag = starFlag;
	}

	public String getNoteUserId() {
		return noteUserId;
	}

	public void setNoteUserId(String noteUserId) {
		this.noteUserId = noteUserId;
	}

	public String getNoteRoleId() {
		return noteRoleId;
	}

	public void setNoteRoleId(String noteRoleId) {
		this.noteRoleId = noteRoleId;
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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getAttachmentFileId() {
		return attachmentFileId;
	}

	public void setAttachmentFileId(String attachmentFileId) {
		this.attachmentFileId = attachmentFileId;
	}
	
}
