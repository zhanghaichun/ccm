/**
 * 
 */
package com.saninco.ccm.action.openInterface;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.action.common.CommonLookupAction;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.ThreadObject;
import com.saninco.ccm.filter.UrlSecurityCheckFilter;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.invoice.IInvoiceDetailService;
import com.saninco.ccm.service.invoice.IInvoiceService;
import com.saninco.ccm.util.SystemUtil;



public class OpenInterfaceAction extends CommonLookupAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5671288712837572230L;

	/**
	 * 
	 */
	public String exec() throws Exception {
		return INPUT;
	}
	public OpenInterfaceAction() {
	}
	private final Logger logger = Logger.getLogger(this.getClass());
	private IInvoiceDetailService invoiceDetailService;
	private ICommonLookupService commonLookupService; 
	private IInvoiceService invoiceService;
	private int invoiceId;
	private int originalId;
	private String workflowUserName;
	private int userId;
	private String userName;
	private String invoiceNumber;
	private List<HashMap<String, String>> list;
	private String systemUrlAdress;
	private String externalEmailId;
	private String notes;
	private String typeAction;
	private int fileDownloadId;
	private String token;
	private String effectiveHour;
	private String CRdownloadTimes;
	
	public String externalAproveFromEmail() throws Exception {
		logger.info("Enter method externalAproveFromEmail.");	
		try{
			
			Long count = invoiceDetailService.getExternalEmailCount(externalEmailId);
			if(count>0) {
				return "OUT";
			}else{
				invoiceDetailService.externalAproveBack("Approve",invoiceId,workflowUserName,userName,userId,"Y",externalEmailId,null);
			}
			
		}catch(Exception e){
			logger.error("externalAproveFromEmail error: ", e);

			return INPUT;
		}
		
		logger.info("Exit method externalAproveFromEmail.");
		return SUCCESS;
	}
	
	
	public String externalRejectFromEmail() throws Exception {
		logger.info("Enter method externalRejectFromEmail.");
		
        try{     	
        	Long count = invoiceDetailService.getExternalEmailCount(externalEmailId);
			if(count>0) {
				return "OUT";
			}else{
				invoiceDetailService.externalAproveBack("Reject",invoiceId,workflowUserName,userName,userId,"Y",externalEmailId,null);
			}
		}catch(Exception e){
			logger.error("externalRejectFromEmail error: ", e);

			return INPUT;
		}
		logger.info("Exit method externalRejectFromEmail.");
		return SUCCESS;
		
	}
	
	public String externalCommentsFromEmail () throws Exception {
		logger.info("Enter method externalCommentsFromEmail.");
		try {
			Long count = invoiceDetailService.getExternalEmailCount(externalEmailId);
			if (count > 0) {
				return "OUT";
			}
		} catch (Exception e) {
			logger.error("externalCommentsFromEmail error: ", e);

			return INPUT;
		}
		logger.info("Exit method externalCommentsFromEmail.");
		return SUCCESS;
	}
	
	public String externalCommentsNotes () throws IOException {
        logger.info("Enter method externalCommentsNotes.");
        String result;
        try{     	
        	Long count = invoiceDetailService.getExternalEmailCount(externalEmailId);
			if (count > 0) {
				this.writeOutputStream("{OUT:\"out\"}");
				return null;
			}
			
        	if ("Approve".equals(typeAction)) {
        		invoiceDetailService.externalAproveBack("Approve",invoiceId,workflowUserName,userName,userId,"Y",externalEmailId,notes);
        		this.writeOutputStream("{success:\"External invoice approval is successful.\"}");
        	}
        	else {
        		invoiceDetailService.externalAproveBack("Reject",invoiceId,workflowUserName,userName,userId,"Y",externalEmailId,notes);
        		this.writeOutputStream("{success:\"External invoice rejection is successful.\"}");
        	}     		

		}catch(Exception e){
			logger.error("externalCommentsNotes error: ", e);

			result = "{error:\"externalCommentsNotes error: "+e.getMessage()+"\"}";;
			if ("Approve".equals(typeAction)) {

				result = "{error:\"External invoice approval is fail.\"}";;
        	}
        	else {
        		result = "{error:\"External invoice rejection is fail.\"}";;
        	}   
			this.writeOutputStream(result);
		}
		logger.info("Exit method externalCommentsNotes.");
		return null;
	}
	
	
	
	public String doViewOriginalList() throws Exception {
		logger.info("Enter method doViewOriginalList.");
		
		try{     	
			Long count = invoiceDetailService.doViewOriginalList(invoiceId);
			list = new ArrayList<HashMap<String, String>>();
			if(count==0) {
				return INPUT;
			}else{
				systemUrlAdress = invoiceDetailService.getSystemUrlAdress();
				list = invoiceDetailService.getOriginalListByInvoiceId(invoiceId);
				logger.info("Exit method doViewOriginalList.");
				return SUCCESS;
			}
		}catch(Exception e){
			logger.error("doViewOriginalList error: ", e);
			return INPUT;
		}
		
	}
	
	public String downloadInvoiceOriginalFile() throws Exception {
		logger.info("Enter method downloadInvoiceOriginalFile.");
		Map<String, String> map = new HashMap<String, String>();
		
		map = invoiceDetailService.getOriginalDetail(originalId);
		
		if(map == null){
			return INPUT;
		}else{
			setFilePath(map.get("file_path").toString());
			setFileName(map.get("file_name").toString());
			download();
			logger.info("Exit method downloadInvoiceOriginalFile.");
			return null;
		}
	}
	
	public String securityDownloadReportFile() throws Exception {
		return this.downloadReportFile();
	}

	public String downloadReportFile() throws Exception {
		logger.info("Enter method downloadReportFile.");

		try{   
			Integer  count = null;
			String value = commonLookupService.isFileDownLoad(fileDownloadId,token);
			Map <String,String> map = commonLookupService.selectFileDownLoad(fileDownloadId);
			
			String downloadTimes = map.get("download_times");			
			if (downloadTimes != null && !downloadTimes.equals("")) {
				count = commonLookupService.findfileDownloadJournal(fileDownloadId);
			}
			if ("Download".equals(value)) {
				
				if (downloadTimes == null || downloadTimes.equals("") || Integer.parseInt(downloadTimes) > count) {
					setFilePath(map.get("file_path").toString()+map.get("file_name").toString());
					setFileName(map.get("file_name").toString());
					String download = download();
					//添加下载日志
					String  ip = request.getRemoteAddr();
					commonLookupService.savefileDownloadJournal(map.get("login_flag").toString(),fileDownloadId,ip);	
					
					return download;
				}
				else {
					CRdownloadTimes = downloadTimes;
					return "AgainDownload";
				}
			}
			else if ("Login".equals(value)) {
				return "Login";
			}
			else if ("Timeout".equals(value)) {
				effectiveHour = String.valueOf(map.get("effective_hour"));
				return "Timeout";
			}
			else if ("TokenError".equals(value)) {
				return "TokenError";
			}
			
				
			
			
			
			
		}catch(Exception e){
			return INPUT;
		}
	
		return null;
		
	}

	public void createUsageInvoiceNote() throws Exception {
//		String filePath = request.getSession().getServletContext().getRealPath(File.separator+SystemUtil.getSysConfigMap().get("invoice_file_path"));
		String filePath = SystemUtil.getSysConfigMap().get("invoice_file_path");
		invoiceService.createUsageInvoiceNote(filePath+File.separator);
	}
	public IInvoiceDetailService getInvoiceDetailService() {
		return invoiceDetailService;
	}

	public void setInvoiceDetailService(IInvoiceDetailService invoiceDetailService) {
		this.invoiceDetailService = invoiceDetailService;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}


	public String getWorkflowUserName() {
		return workflowUserName;
	}

	public void setWorkflowUserName(String workflowUserName) {
		this.workflowUserName = workflowUserName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public List<HashMap<String, String>> getList() {
		return list;
	}
	public void setList(List<HashMap<String, String>> list) {
		this.list = list;
	}
	
	public int getOriginalId() {
		return originalId;
	}
	
	public void setOriginalId(int originalId) {
		this.originalId = originalId;
	}
	public String getSystemUrlAdress() {
		return systemUrlAdress;
	}
	public void setSystemUrlAdress(String systemUrlAdress) {
		this.systemUrlAdress = systemUrlAdress;
	}
	public String getExternalEmailId() {
		return externalEmailId;
	}
	public void setExternalEmailId(String externalEmailId) {
		this.externalEmailId = externalEmailId;
	}
	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}
	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTypeAction() {
		return typeAction;
	}
	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	public int getFileDownloadId() {
		return fileDownloadId;
	}
	public void setFileDownloadId(int fileDownloadId) {
		this.fileDownloadId = fileDownloadId;
	}
	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}
	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEffectiveHour() {
		return effectiveHour;
	}
	public void setEffectiveHour(String effectiveHour) {
		this.effectiveHour = effectiveHour;
	}
	public String getCRdownloadTimes() {
		return CRdownloadTimes;
	}
	public void setCRdownloadTimes(String cRdownloadTimes) {
		CRdownloadTimes = cRdownloadTimes;
	}

	
	
}