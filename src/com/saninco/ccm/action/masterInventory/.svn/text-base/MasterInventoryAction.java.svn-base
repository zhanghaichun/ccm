/**
 * 
 */
package com.saninco.ccm.action.masterInventory;

import java.io.File;
import java.io.FileInputStream;
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
import com.saninco.ccm.service.dispute.IDisputeService;
import com.saninco.ccm.service.masterInventory.IMasterInventoryService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.quote.IQuoteService;
import com.saninco.ccm.util.ExcelFileUtil;
import com.saninco.ccm.vo.MapEntryVO;

/**
 * Invoice related UI action class.
 * 
 */
public class MasterInventoryAction extends CcmActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IDisputeService disputeService;
	private IMasterInventoryService masterInventoryService;
	private IQuicklinkService quicklinkService;
	private List<File> uploads;
	private List<String> uploadsFileName;
	private Map<String,Object> returnMap;
	private String errorFileName;
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> analystList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> lineOfBusiness = new ArrayList<MapEntryVO<String, String>>();
	public MasterInventoryAction() {
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
	 * Drop-down list, save in the list of transmission to the page
	 * */
	private void populateInvoiceSearchLookupList() throws BPLException {
		logger.info("Enter method populateInvoiceSearchLookupList.");
		logger.info("Exit method populateInvoiceSearchLookupList.");
	}
	
	public String uploadMasterInventory () throws Exception {
		logger.info("Enter method uploadMasterInventory.");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String previewFileName = df.format(new Date())+"_"+uploadsFileName.get(uploadsFileName.size()-1);
		String errorExcelDirPath = request.getSession().getServletContext().getRealPath("/")+ "previews\\" + previewFileName;
		File file = uploads.get(uploads.size()-1);
		returnMap = new HashMap<String,Object>();
		if(file.length() > 5242880){
			returnMap.put("maxSize", "Y");
		}else{
			returnMap.put("maxSize", "N");
			Map<String,String> map = masterInventoryService.uploadMasterInventory(uploads.get(uploads.size()-1),uploadsFileName.get(uploadsFileName.size()-1),errorExcelDirPath);
			if(map.get("isSuccess") == null || "".equals(map.get("isSuccess"))){
				returnMap.put("validationFailure", "Y");
			}else{
				returnMap.put("validationFailure", "N");
			}
			returnMap.put("isSuccess", map.get("isSuccess"));
		}
		returnMap.put("errorFilePath", previewFileName);
		logger.info("Exit method uploadMasterInventory.");
		return SUCCESS;
	}
	
	public String downLoadMasterInventoryErrorFile()throws Exception{
		logger.info("Exit method downLoadMasterInventoryErrorFile.");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(request.getSession().getServletContext().getRealPath("/")+ "previews\\" + errorFileName);
			String fileName = "Master Inventory Import Errors.";
			if(errorFileName.endsWith(ExcelFileUtil.EXTENSION_XLS)){
				fileName+=ExcelFileUtil.EXTENSION_XLS;
			}else{
				fileName+=ExcelFileUtil.EXTENSION_XLSX;
			}
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downLoadMasterInventoryErrorFile error: ", e);
		}
		logger.info("Exit method downLoadMasterInventoryErrorFile.");
		return null;
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
	public IDisputeService getDisputeService() {
		return disputeService;
	}
	public void setDisputeService(IDisputeService disputeService) {
		this.disputeService = disputeService;
	}
	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}
	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}
	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}
	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}
	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}
	public List<MapEntryVO<String, String>> getInvoiceStatusList() {
		return invoiceStatusList;
	}
	public void setInvoiceStatusList(
			List<MapEntryVO<String, String>> invoiceStatusList) {
		this.invoiceStatusList = invoiceStatusList;
	}
	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}
	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
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
	public List<MapEntryVO<String, String>> getLineOfBusiness() {
		return lineOfBusiness;
	}
	public void setLineOfBusiness(List<MapEntryVO<String, String>> lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}
	public Logger getLogger() {
		return logger;
	}
	public IMasterInventoryService getQuoteService() {
		return masterInventoryService;
	}
	public void setMasterInventoryService(IMasterInventoryService masterInventoryService) {
		this.masterInventoryService = masterInventoryService;
	}
	public Map<String, Object> getReturnMap() {
		return returnMap;
	}
	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}
	public IMasterInventoryService getMasterInventoryService() {
		return masterInventoryService;
	}
	public String getErrorFileName() {
		return errorFileName;
	}
	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}

}
