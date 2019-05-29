package com.saninco.ccm.action.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.service.ban.IBanService;
import com.saninco.ccm.service.dispute.IDisputeService;
import com.saninco.ccm.service.invoice.IInvoiceService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.vo.BanVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * Common action class.
 * 
 * @author Jian.Dong
 * @see Modified By Chao.Liu to 2010/06/08 PM
 * @see I'm add some method about the Left Page.
 * @see Modified By Chao.Liu On 2010/09/11 AM [Add Or Update Logger]
 */
public class CommonLookupAction extends CcmActionSupport {
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IInvoiceService invoiceService;
	private IDisputeService disputeService;
	private IBanService banService;
	private WorkspaceVO wvo = new WorkspaceVO();
	private SearchInvoiceVO svo;
	private BanVO bvo = new BanVO();
	private boolean isMockup = false;
	private String filePath;
	private String fileName;
	private String attachmentPointId;
	private Integer themeId;
	private List<String> titles;
	private static final int BUFFER = 2048;
	private Invoice invoice;
	private String missingEmailflag;
	private List<Integer> ids;
	private String disputeId;
	
	
	
	public String getDisputeId() {
		return disputeId;
	}


	public void setDisputeId(String disputeId) {
		this.disputeId = disputeId;
	}


	public String getAttachmentPointId() {
		return attachmentPointId;
	}


	public Integer getThemeId() {
		return themeId;
	}


	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}


	public void setAttachmentPointId(String attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	//
    public String download() {
    	logger.info("enter method download.");
    	try {
    		
    		logger.info("-----------------------"+filePath+"---------------------------");
    		
    		File of = new File(filePath);
    		File f = null;
    		fileName = fileName == null ? of.getName() : fileName;
    		String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
    		if("TIF".equalsIgnoreCase(prefix)){
    			f = new File(tifToPdf(filePath));
    			fileName = fileName.toUpperCase().replace("TIF", "PDF");
    		}else{
    			f = new File(filePath);
    		}
			this.writeFile(fileName,new FileInputStream(f));
		} catch (Exception e) {
			logger.error("download error: ", e);
			return INPUT;
		}
    	logger.info("Exit method download.");
		return null;
    }
    
    public String preview() {
    	logger.info("enter method preview.");
    	try {
    		String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
    		String outputFilePath = "";
    		if("TIF".equals(prefix.toUpperCase())){
    			outputFilePath = this.tifToPdf(filePath);
    		}else{
    			outputFilePath = this.previewFile(filePath);
    		}
    		if(!"".equals(outputFilePath) && outputFilePath != null){
    			this.writeOutputStream("{success:\""+outputFilePath+"\"}");
    		}else{
    			this.writeOutputStream("{error: \"File does not exist !\"}");
    		}
    	} catch (Exception e) {
    		logger.error("preview error: ", e);
    		return INPUT;
    	}
    	logger.info("Exit method preview.");
    	return null;
    }
    
    public String downloadReport() throws IOException {
    	logger.info("enter method downloadReport.");
//    	String result ="";
    	try {
    		File f = new File(filePath);
    		fileName = fileName == null ? f.getName() : fileName;
    		String savePath = "/var/report";
//    		String filePath = request.getSession().getServletContext().getRealPath(File.separator + savePath+"\\"+fileName);
    		//�����ZIP ֱ������
    		FileInputStream fis = new FileInputStream(f);
			writeFile(fileName,fis);
			//���ZIP������
//    		if (createZip(fileName,filePath,savePath)) {
//    			FileInputStream fis = new FileInputStream(savePath);
//    			writeFile(fileName,fis);
//    		}
//    		if (createZip(fileName,filePath,savePath)) {
//    			result = "{info:'success'}";
//    		} else {
//    			result = "{error:'Create ZIP Error!'}";
//    		}
		} catch (Exception e) {
			logger.error("download error: ", e);
//			result = "{error:true}";
		}
//		this.writeOutputStream(result);
    	logger.info("Exit method downloadReport.");
		return null;
    }
    
    public boolean createZip(String fileName,String filePath,String savePath) {
    	logger.info("enter method createZip.");
    	boolean b = true;
    	String splitFileName = "";
    	BufferedInputStream origin = null;
    	FileInputStream fi = null;
    	try {
    		if (fileName.split("\\.").length > 2) {
    			for (int i = 0; i < fileName.split("\\.").length-1; i++) {
    				splitFileName+= fileName.split("\\.")[i] + ".";
				}
    			splitFileName = splitFileName.substring(0,splitFileName.length()-1);
    		} else {
    			splitFileName= fileName.split("\\.")[0];
    		}
           
            File f = new File(filePath);
            fi = new FileInputStream(f);
           
        } catch (Exception e) {
        	b = false;
            e.printStackTrace();
        }
        try {
        	if (b) {
	        	FileOutputStream dest = new FileOutputStream(savePath + "/" + splitFileName + ".zip");
	        	ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
	        			dest));
	        	byte data[] = new byte[BUFFER];
	        	origin = new BufferedInputStream(fi, BUFFER);
	        	ZipEntry entry = new ZipEntry(fileName);
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
				out.close();
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Exit method createZip.");
        return b;
    }
	
	public CommonLookupAction() { }
	
	
	public String exec() throws Exception {
		return SUCCESS;
	}

	/**
	 * Search dispute,credit and pyment count in the My Workspace 
	 * @return Json String
	 * @throws Exception
	 */
	public String getMyWorkspace()throws Exception {
		logger.info("Enter method getMyWorkspace.");
		String result = null;
		try {
			if(isMockup){
				result = getMyWorkspaceMockup();
			}else{
				result = commonLookupService.getMyWorkspaceAllCountArray();
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"My Workspace Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getMyWorkspace.");
		return null;
	}
 
	public String changeUserTheme()throws Exception {
		logger.info("Enter method changeUserTheme.");
		String result = "1";
		try {
			try {
				result = commonLookupService.changeUserTheme(themeId);
			} catch (RuntimeException e) {
				logger.error("changeUserTheme error: ", e);
				result = "{error:\"changeUserTheme error: " + e.getMessage() + "\"}";
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Change User Theme!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method changeUserTheme.");
		return null;
	}
	/**
	 * Search Other User Name List 
	 * @return Json String
	 * @throws Exception
	 */
	public String getBackupAction()throws Exception {
		logger.info("Enter method getBackupAction.");
		String result = null;
		try {
			if(isMockup){
				result = getMyWorkspaceOtherMockup();
			}else{
				result = commonLookupService.getMyWorkspaceBackMockup(); // This is false
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Backup Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getBackupAction.");
		return null;
	}
	/**
	 * Search Delegation User Name List 
	 * @return Json String
	 * @throws Exception
	 */
	public String getDelegation()throws Exception {
		logger.info("Enter method getDelegation.");
		String result = null;
		try {
			if(isMockup){
				result = getMyWorkspaceOtherMockup();
			}else{
				result = commonLookupService.getUserDelegationList(wvo); // This is false
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Delegation Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getDelegation.");
		return null;
	}
	/**
	 * Search My Team User Name List 
	 * @return Json String
	 * @throws Exception
	 */
	public String getMyTeam()throws Exception {
		logger.info("Enter method getMyTeam.");
		String result = null;
		try {
			if(isMockup){
				result = getMyWorkspaceOtherMockup();
			}else{
				result = commonLookupService.getMyWorkspaceSupervisor(); // This is false
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search My Team Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getMyTeam.");
		return null;
	}
	public String getMyTeamUser()throws Exception {
		logger.info("Enter method getMyTeamUser.");
		String result = null;
		try {
			
			result = commonLookupService.getMyWorkspaceMyTeamUserCounts(wvo); // This is false
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search My Team getMyTeamUser Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getMyTeamUser.");
		return null;
	}
	
	
	/////////////////[o0 Search Talbe List 0o]////////////////////
	/**
	 * Search Invoice Total Page No.
	 * @return Json String
	 * @throws Exception
	 */
	public String getInvoiceTotalPageNoInCommonLookup()throws Exception {
		logger.info("Enter method getInvoiceTotalPageNoInCommonLookup.");
		String result = null;
		try {
			if(isMockup){
				result = getInvoiceTotalPageNoInCommonLookupMockup();
			}else{
				result = invoiceService.getInvoiceWorkCount(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Invoice[Workspace] Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getInvoiceTotalPageNoInCommonLookup.");
		return null;
	}
	
	/*
	 * External Approve Invoice Page Number.
	 */
	public String countExternalApproveInvoicePageNo()throws Exception {
		
		logger.info("Enter method countExternalApproveInvoicePageNo.");
		
		String result = null;
		
		try {
			if(isMockup){
				// result = getInvoiceTotalPageNoInCommonLookupMockup();
			}else{
				result = invoiceService.countExternalApproveInvoicePageNo(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"count external approve invoice page number. Error!\"}";
		}
		
		this.writeOutputStream(result);
		logger.info("Exit method countExternalApproveInvoicePageNo.");
		return null;
		
	}
	
	/*
	 * External Approve Invoice list items.
	 */
	public String listExternalApproveInvoiceItems()throws Exception {
		
		logger.info("Enter method listExternalApproveInvoiceItems.");
		
		String result = null;
		
		try {
			
			if(isMockup){
//				result = searchInvoiceInCommonLookupMockup();
			}else{
				result = invoiceService.listExternalApproveInvoiceItems(wvo);
			}
		} catch (BPLException e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"list external approve invoice items Error!\"}";
		}
		
		this.writeOutputStream(result);
		logger.info("Exit method listExternalApproveInvoiceItems.");
		return null;
	}
	
	/*
	 * Download External Approve Invoices item.
	 */
	public String downloadExternalApproveInvoices()throws  Exception {
		
		logger.info("Enter method downloadExternalApproveInvoices.");
		
		FileInputStream fis = null;
		
		try {
			
			String fileName = "ExternalApproveInvoices.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceService.downloadExternalApproveInvoices(wvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
			
		}catch (Throwable e) {
			logger.error("downloadExternalApproveInvoices error: ", e);
		}
		
		logger.info("Exit method downloadExternalApproveInvoices.");
		return null;
	}
	
	
	/**
	 * search PastDuePageNo
	 * @return from hongtao.pang
	 * @throws Exception
	 */
	public String getInvoiceTotalPageNoInPastDue()throws Exception {
		logger.info("Enter method getInvoiceTotalPageNoInPastDue.");
		String result = null;
		try {
			if(isMockup){
				result = getInvoiceTotalPageNoInCommonLookupMockup();
			}else{
				result = invoiceService.getPastDueCount(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Invoice[Workspace] Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getInvoiceTotalPageNoInPastDue.");
		return null;
	}
	
	/**
	 * Delete By Chao.Liu to 10/06/11 AM
	 * Search Payment Total Page No.
	 * public String doGetPaymentTotalPageNoInCommonLookup()throws Exception{...}
	 * Search Dispute Total Page No.
	 * public String doGetDisputeTotalPageNoInCommonLookup()throws Exception{...}
	 */
	/**
	 * Search Invoice Total Page No.
	 * @return Json String
	 * @throws Exception
	 */
	public String getMissingInvoicesTotalPageNoInCommonLookup()throws Exception {
		logger.info("Enter method getInvoiceTotalPageNoInCommonLookup.");
		String result = null;
		try {
			if(isMockup){
				result = getMissingInvoicesTotalPageNoMockup();
			}else{
				result = invoiceService.getMissingWorkCount(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Missing Invoices Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getInvoiceTotalPageNoInCommonLookup.");
		return null;
	}
	/**
	 * Delete By Chao.Liu to 10/06/11 AM
	 * Search Payment Total Page No.
	 * public String doGetPaymentTotalPageNoInCommonLookup()throws Exception{...}
	 * Search Dispute Total Page No.
	 * public String doGetDisputeTotalPageNoInCommonLookup()throws Exception{...}
	 */
	/**
	 * Search Invoice Total Page No.
	 * @return Json String
	 * @throws Exception
	 */
	public String doGetUnknownInvoicesTotalPageNoInCommonLookup()throws Exception {
		logger.info("Enter method doGetUnknownInvoicesTotalPageNoInCommonLookup.");
		String result = null;
		try {
			result = invoiceService.getUnknownWorkCount(wvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Unknown Invoices Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method doGetUnknownInvoicesTotalPageNoInCommonLookup.");
		return null;
	}
	/**
	 * Delete By Chao.Liu to 10/06/11 AM
	 * Search Payment List Info 
	 * public String doSearchPaymentInCommonLookup()throws Exception{...}
	 * Search Dispute List Info 
	 * public String doSearchDisputeInCommonLookup()throws Exception{...}
	 */
	public String searchUnknownInvoices()throws  Exception {
		logger.info("Enter method searchUnknownInvoices.");
		String result = null;
		try {
			result = invoiceService.searchUnknownInvoices(wvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Unknown Invoices Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchUnknownInvoices.");
		return null;
	}
	/**
	 * Search Invoice Total Page No.
	 * @return Json String
	 * @throws Exception
	 */
	public String getInvoiceInProcessTotalPageNoInCommonLookup()throws Exception {
		logger.info("Enter method getInvoiceTotalPageNoInCommonLookup.");
		String result = null;
		try {
			if(isMockup){
				result = getInvoiceInProcessTotalPageNoMockup();
			}else{
				result = invoiceService.getProcessWorkCount(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Invoice In Process Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getInvoiceTotalPageNoInCommonLookup.");
		return null;
	}
	/**
	 * Search Invoice List Info 
	 * @return Json String 
	 * @throws Exception
	 */
	public String searchInvoiceInCommonLookup()throws Exception {
		logger.info("Enter method searchInvoiceInCommonLookup.");
		String result = null;
		try {
			if(isMockup){
				result = searchInvoiceInCommonLookupMockup();
			}else{
				result = invoiceService.searchInvoiceWorkCount(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Invoice In Common Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceInCommonLookup.");
		return null;
	}
	/**
	 * @author Donghao.Guo
	 * get Preload Invoice TotalPageNo
	 *  @throws Exception
	 * */
	public String getPreloadInvoicesTotalPageNoInCommonLookup()throws Exception {
		logger.info("Enter method getPreloadInvoicesTotalPageNoInCommonLookup.");
		String result = null;
		try {
			result = invoiceService.getPreloadInvoiceWorkCount(wvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Missing Invoices Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getPreloadInvoicesTotalPageNoInCommonLookup.");
		return null;
	}
	/**
	 * @author mingyang.li
	 * get Invoice Reject TotalPageNo
	 *  @throws Exception
	 * */
	public String getInvoiceRejectTotalPageNoInCommonLookup()throws Exception {
		logger.info("Enter method getInvoiceRejectTotalPageNoInCommonLookup.");
		String result = null;
		try {
			if(isMockup){
				result = getInvoiceRejectTotalPageNoMockup();
			}else{
				result = invoiceService.getInvoiceRejectWorkCount(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Invoice In Process Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getInvoiceTotalPageNoInCommonLookup.");
		return null;
	}
	/**
	 * @author Donghao.Guo
	 * Search Preload Invoice List Info
	 *  @throws Exception
	 * */
	public String searchPreloadInvoices()throws Exception{
		logger.info("Enter method searchPreloadInvoices.");
		String result = null;
		try{
			result = invoiceService.searchPreloadInvoiceWorkCount(wvo);
		}catch(BPLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Preload Invoices Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchPreloadInvoices.");
		return null;
	}
	/**
	 * @author mingyang.li
	 * Search Invoice Reject List Info
	 *  @throws Exception
	 * */
	public String SearchInvoiceReject()throws Exception{
		logger.info("Enter method SearchInvoiceReject.");
		String result = null;
		try{
			result = invoiceService.searchInvoiceRejectWorkCount(wvo);
		}catch(BPLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Preload Invoices Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method SearchInvoiceReject.");
		return null;
	}
	
	/**
	 * @author hongtao.pang
	 *  Search Pass Dud
	 * @throws Exception
	 * 
	 */
	
	public String SearchPastDue()throws Exception{
		logger.info("Enter method SearchPastDue.");
		String result = null;
		try{
			result = invoiceService.searchPastDue(wvo);
		}catch(BPLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search PastDue Invoices Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method SearchPastDue.");
		return null;
	}
	/**
	 * @author Donghao.Guo
	 * Save Preload Excel Invoice List Info
	 *  @throws Exception
	 * */
	public String savePreloadInvoicesExcel()throws  Exception {
		logger.info("Enter method savePreloadInvoicesExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "PreloadInvoices.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceService.getPreloadInvoicesExcel(wvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("savePreloadInvoicesExcel error: ", e);
		}
		logger.info("Exit method savePreloadInvoicesExcel.");
		return null;
	}
/**
 * 
 * due days Add by wenbo.zhang 2015-3-18
 * */
	public String saveDueDaysInvoicesExcel()throws  Exception {
		logger.info("Enter method saveDueDaysInvoicesExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "saveDueDaysInvoices.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceService.getDueDaysInvoicesExcel(wvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveDueDaysInvoicesExcel error: ", e);
		}
		logger.info("Exit method saveDueDaysInvoicesExcel.");
		return null;
	}
	
	/**
	 * @author Jian.Dong
	 * Search Missing Invoice List Info 
	 * @throws Exception
	 */
	public String searchMissingInvoices()throws  Exception {
		logger.info("Enter method searchInvoiceInCommonLookup.");
		String result = null;
		try {
			if(isMockup){
				result = searchMissingInvoicesMockup();
			}else{
				result = commonLookupService.searchWorkspaceMissing(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Missing Invoices Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceInCommonLookup.");
		return null;
	}

	/**
	 * @author Jian.Dong
	 * Search Missing Invoice List Info 
	 * @throws Exception
	 */
	public String saveMissingInvoicesExcel()throws  Exception {
		logger.info("Exit method saveMissingInvoicesExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "MissingInvoices.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceService.getMissingInvoicesExcel(wvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveMissingInvoicesExcel error: ", e);
		}
		logger.info("Exit method saveMissingInvoicesExcel.");
		return null;
	}
	/**
	 * Search Invoice List Info 
	 * @return Json String 
	 * @throws Exception
	 */
	public String searchInvoiceInProcess()throws  Exception {
		logger.info("Enter method searchInvoiceInCommonLookup.");
		String result = null;
		try {
			if(isMockup){
				result = searchInvoiceInProcessMockup();
			}else{
				result = commonLookupService.searchWorkspaceProsing(wvo);
			}
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Invoice In Process Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceInCommonLookup.");
		return null;
	}
	
	/**
	 * Search dispute,credit and pyment count in the My Workspace 
	 * @return Json String
	 * @throws Exception
	 */
	public String getDisputesBucketCount()throws Exception {
		logger.info("Enter method getDisputesBucketCount.");
		String result = null;
		try {
			result = commonLookupService.getDisputesBucketCount();
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Disputes Bucket Count Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getDisputesBucketCount.");
		return null;
	}
	
	/**
	 * Search Open Dispute Count Info 
	 */
	public String searchDisputeCountByDays()throws  Exception {
		logger.info("Enter method searchDisputeCountByDays.");
		String result = null;
		try {
			result = disputeService.searchDisputeCountByDays(wvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Open Dispute Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchDisputeCountByDays.");
		return null;
	}
	
	/**
	 * Search Open Dispute List Info 
	 */
	public String searchDisputeByDays()throws  Exception {
		logger.info("Enter method searchDisputeByDays.");
		String result = null;
		try {
			result = disputeService.searchDisputeByDays(wvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Open Dispute Error!\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchDisputeByDays.");
		return null;
	}
	
	/////////////////[o0 Mockup 0o]///////////////////
	/**
	 * Modified By Chao.Liu to 10/06/11 AM
	 */
	private String getMyWorkspaceMockup(){
		logger.info("Enter method getMyWorkspace.");
		StringBuffer sb = new StringBuffer("{");
		sb.append("redInvoiceCount:2,yellowInvoiceCount:1,greenInvoiceCount:12,missingInvoicesCount:2,invoiceInProcessCount:1}");
		logger.info("Exit method getMyWorkspace.");
		return sb.toString();
	}
	/**
	 * Modified By Chao.Liu to 10/06/11 AM
	 */
	private String getMyWorkspaceOtherMockup(){
		logger.info("Enter method getBackupMockup.");
		StringBuffer sb = new StringBuffer("[");
		sb.append("{uid:1,uname:\"lcc\",");
		sb.append("redInvoiceCount:2,yellowInvoiceCount:1,greenInvoiceCount:12,missingInvoicesCount:2,invoiceInProcessCount:1},");
		
		sb.append("{uid:2,uname:\"test\",");
		sb.append("redInvoiceCount:2,yellowInvoiceCount:1,greenInvoiceCount:12,missingInvoicesCount:2,invoiceInProcessCount:1}");
		
		sb.append("]");
		logger.info("Exit method getBackupMockup.");
		return sb.toString();
	}
	private String getInvoiceTotalPageNoInCommonLookupMockup(){
		return "{totalResultNo:4,totalPageNo:1}";
	}
	/**
	 * Delete By Chao.Liu to 10/06/11 AM
	 * private String doGetPaymentTotalPageNoInCommonLookupMockup(){...}
	 * private String doGetDisputeTotalPageNoInCommonLookupMockup(){...}
	 */
	private String searchInvoiceInCommonLookupMockup(){
		return "{begin:1,end:4," +
			"data:[{id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/06/18\",due:\"2009/07/18\",total:\"$1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}," +
			"{id:2,no:\"INV3411085\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/03/18\",due:\"2009/04/18\",total:\"$482.58\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}," +
			"{id:3,no:\"INV3412591\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/04/18\",due:\"2009/05/18\",total:\"$715.99\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}," +
			"{id:4,no:\"INV3413728\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/05/18\",due:\"2009/06/18\",total:\"$951.73\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}]}";
	}
	/**
	 * Delete By Chao.Liu to 10/06/11 AM
	 * private String doSearchPaymentInCommonLookupMockup(){...}
	 * private String doSearchDisputeInCommonLookupMockup(){...}
	 */
	
	private String getMissingInvoicesTotalPageNoMockup(){
		return "{totalResultNo:2,totalPageNo:1}";
	}
	private String getInvoiceInProcessTotalPageNoMockup(){
		return "{totalResultNo:1,totalPageNo:1}";
	}
	private String getInvoiceRejectTotalPageNoMockup(){
		return "{totalResultNo:1,totalPageNo:1}";
	}
	private String searchMissingInvoicesMockup(){
		return "{begin:1,end:4," +
		"data:[{id:1,vendor:\"vendor\",ban:\"ban\",expectingDate:\"expectingDate\",lastMonthTotalDue:\"lastMonthTotalDue\",lastMonthPayment:\"lastMonthPayment\",lastMonthDispute:\"lastMonthDispute\",analystName:\"analystName\"}," +
		"{id:2,vendor:\"vendor\",ban:\"ban\",expectingDate:\"expectingDate\",lastMonthTotalDue:\"lastMonthTotalDue\",lastMonthPayment:\"lastMonthPayment\",lastMonthDispute:\"lastMonthDispute\",analystName:\"analystName\"}]}";
	}
	private String searchInvoiceInProcessMockup(){
		return "{begin:1,end:4," +
		"data:[{id:1,bendor:\"bendor\",ban:\"ban\",totalDue:\"totalDue\",payment:\"payment\",dispute:\"dispute\",numberOfDaysInProcess:\"numberOfDaysInProcess\",analystName:\"analystName\"}]}";
	}
	
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to getAttachmentPointIdCountService performs all inquiries, conditions of the paging inquires research method.-return
	 */
	public String getAttachmentPointIdCountService()  throws Exception {
		logger.info("Enter method getAttachmentPointIdCountService.");
		String result = "";
		try{
			svo.setBoxInId(attachmentPointId);
			result = commonLookupService.getAttachmentPointIdCountService(svo);
		}catch(Exception e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Attachment Point Id Count Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getAttachmentPointIdCountService.");
		return null;
	}
	//wenbo.zhang 15-5-4
	public String getDisputeAttachmentPointIdCountService() throws Exception {
		logger.info("Enter method getDisputeAttachmentPointIdCountService.");
		String result = "";
		try{
			svo.setDisputeId(disputeId);
			result = commonLookupService.getDisputeAttachmentPointIdCountService(svo);
		}catch(Exception e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search disputeId Count Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getDisputeAttachmentPointIdCountService.");
		return null;
		
	}
	public String getDisputeSearchAttachmentPointIdService()  throws Exception {
		logger.info("Enter method getDisputeSearchAttachmentPointIdService.");
		String result = "";
		try{
			svo.setDisputeId(disputeId);
			result = commonLookupService.getDisputeSearchAttachmentPointIdService(svo);
		}catch(Exception e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Role Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getDisputeSearchAttachmentPointIdService.");
		return null;
	}
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to getSearchAttachmentPointIdService performs all inquiries, conditions inquire method.-return
	 */
	public String getSearchAttachmentPointIdService()  throws Exception {
		logger.info("Enter method getSearchAttachmentPointIdService.");
		String result = "";
		try{
			svo.setBoxInId(attachmentPointId);
			result = commonLookupService.searchAttachmentPointIdService(svo);
		}catch(Exception e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Role Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getSearchAttachmentPointIdService.");
		return null;
	}
	
	public String removeMissingInvoice() throws Exception{
		logger.info("Enter method removeMissingInvoice.");
		String result = "";
		try {
			result = invoiceService.removeMissingInvoice(invoice,missingEmailflag);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Remove Missing Invoice. Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method removeMissingInvoice.");
		return null;
	}
	
	public String removeUnknownInvoice() throws Exception{
		logger.info("Enter method removeUnknownInvoice.");
		String result = "";
		try {
			result = invoiceService.removeUnknownInvoice(invoice);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Remove Unknown Invoice. Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method removeUnknownInvoice.");
		return null;
	}
	
	public String saveUnknownInvoicesExcel() throws Exception{
		logger.info("Enter method saveUnknownInvoicesExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "UnknownInvoices.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = invoiceService.getUnknownInvoicesExcel(wvo,excelDirPath,titles,ids);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveMissingInvoicesExcel error: ", e);
		}
		logger.info("Exit method saveUnknownInvoicesExcel.");
		return null;
	}
	
	public String saveDisputeExcel() throws Exception{
		logger.info("Enter method saveDisputeExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "Dispute Result.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = disputeService.getDisputeExcel(wvo,excelDirPath,titles,ids);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveDisputeExcel error: ", e);
		}
		logger.info("Exit method saveDisputeExcel.");
		return null;
	}
	
	public String doSearchBanApproveTotalPageNo()throws Exception {
		logger.info("Enter method doSearchBanApproveTotalPageNo.");
		String result = null;
		try {
			bvo.setBanStatusId(4);
			result = banService.getBanTotalNO(bvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		this.writeOutputStream(result);
		logger.info("Exit method doSearchBanApproveTotalPageNo.");
		return null;
	}
	
	public String doSearchBanRejectTotalPageNo()throws Exception {
		logger.info("Enter method doSearchBanRejectTotalPageNo.");
		String result = null;
		try {
			bvo.setBanStatusId(5);
			result = banService.getBanTotalNO(bvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		this.writeOutputStream(result);
		logger.info("Exit method doSearchBanRejectTotalPageNo.");
		return null;
	}
    
	public String searchBanApproveList()throws  Exception {
		logger.info("Enter method searchBanApproveList.");
		String result = null;
		try {
			bvo.setBanStatusId(4);
			result = banService.searchBanList(bvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchBanApproveList.");
		return null;
	}
	
	public String searchBanRejectList()throws  Exception {
		logger.info("Enter method searchBanRejectList.");
		String result = null;
		try {
			bvo.setBanStatusId(5);
			result = banService.searchBanList(bvo);
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchBanRejectList.");
		return null;
	}
	
	public WorkspaceVO getWvo() {
		return wvo;
	}

	public void setWvo(WorkspaceVO wvo) {
		this.wvo = wvo;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public SearchInvoiceVO getSvo() {
		return svo;
	}

	public void setSvo(SearchInvoiceVO svo) {
		this.svo = svo;
	}
 
	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}


	public List<String> getTitles() {
		return titles;
	}


	public void setTitles(List<String> titles) {
		this.titles = titles;
	}


	public Invoice getInvoice() {
		return invoice;
	}


	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}


	public String getMissingEmailflag() {
		return missingEmailflag;
	}


	public void setMissingEmailflag(String missingEmailflag) {
		this.missingEmailflag = missingEmailflag;
	}


	public List<Integer> getIds() {
		return ids;
	}


	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}


	public IDisputeService getDisputeService() {
		return disputeService;
	}


	public void setDisputeService(IDisputeService disputeService) {
		this.disputeService = disputeService;
	}


	public IBanService getBanService() {
		return banService;
	}


	public void setBanService(IBanService banService) {
		this.banService = banService;
	}


	public BanVO getBvo() {
		return bvo;
	}


	public void setBvo(BanVO bvo) {
		this.bvo = bvo;
	}
	
	
}
