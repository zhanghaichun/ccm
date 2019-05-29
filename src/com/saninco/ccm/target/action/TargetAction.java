package com.saninco.ccm.target.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Target;
import com.saninco.ccm.target.model.SearchTargetVO;
import com.saninco.ccm.target.service.ITargetService;
import com.saninco.ccm.vo.MapEntryVO;

public class TargetAction extends CcmActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MapEntryVO<String, String>> vendorList;
	private List<MapEntryVO<String, String>> banList;
	private List<MapEntryVO<String, String>> paymentStatusList;
	private List<MapEntryVO<String, String>> analystList;
	private List<MapEntryVO<String, String>> invoiceStatusList;
	private List<MapEntryVO<String, String>> lineOfBusiness;
	private ITargetService targetService;
	private SearchTargetVO searchTargetVO = new SearchTargetVO();
	private String banId;
	private List<String> titles;
	private List<Integer> targetIds;
 	private List<MapEntryVO<String, String>> chargeTypeList;
	private List<Target>  targetList = new ArrayList<Target>();
	private File file;
	
	private File excelFile;
	private String excelFileContentType;
	private String excelFileFileName;
	//
	private List<Target> targetList2=new ArrayList<Target>();

	public List<Target> getTargetList2() {
		return targetList2;
	}

	public void setTargetList2(List<Target> targetList2) {
		this.targetList2 = targetList2;
	}

	@Override
	public String exec() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}
	
	public String showTarget () throws BPLException{
		this.vendorList = commonLookupService.getSearchConditionVendors();
		//update by donghao.guo 2011/10/25
		this.banList = commonLookupService.getSearchConditionBans();
		this.paymentStatusList = commonLookupService.getPaymentStatus();
		this.analystList = commonLookupService.getAnalysts();
		this.invoiceStatusList = commonLookupService.getInvoiceStatus();
		this.lineOfBusiness = commonLookupService.getLineofBbusiness();
		this.chargeTypeList = targetService.chargeTypeList();
		return SUCCESS;
	}
	
	public String searchTarget() throws Exception {
		logger.info("Enter method searchTarget.");
		String result = null;
		try{
			result = targetService.searchTarget(searchTargetVO);
		}catch(Exception e){
			logger.error("searchInvoice error: ", e);
			result = "{error:\"searchInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchTarget.");
		return null;
	}
	
	public String getTragetSearchTotalPageNo() throws Exception {
		logger.info("Enter method getTragetSearchTotalPageNo.");
		String result = null;
		try{
			result = targetService.getTragetSearchTotalPageNo(searchTargetVO);
		}catch(Exception e){
			logger.error("SearchTotalPageNo error: ", e);
			result = "{error:\"SearchTotalPageNo: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTragetSearchTotalPageNo.");
		return null;
	}
	
	public String getCircuitList()throws Exception{
		logger.info("Enter method getCircuitList.");
		String result = null;
		try{
			result = this.targetService.getCircuitList(new Integer(this.banId));
		}catch(Exception e){
			logger.error("getCircuitList error: ", e);
			result = "{error:\"getCircuitList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getCircuitList.");
		return null;
	}
	
	public String saveExcelBySearchTarget() throws Exception{
		logger.info("Exit method saveExcelBySearchInvoice.");
		FileInputStream fis = null;
		try {
			String fileName = "targetSearchExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = targetService.createTargetToExcel(searchTargetVO,excelDirPath,titles,targetIds);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchInvoice error: ", e);
		}
		logger.info("Exit method saveExcelBySearchInvoice.");
		return null;
	}
	
	public String saveTarget() throws Exception {
		logger.info("Enter method saveTarget.");
		String result = "";
		try{
			//targetService.saveTarget(targetList2,1);
			targetService.saveTG(targetList2, 3);
			result = "{s:true}";
		}catch(Exception e){
			result = "";
			logger.error("saveTG error: ", e);
			result = "{error:\"saveTG: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveTarget.");
		return null;
	}
	
	public String queryCopyTargetList()throws Exception {
		String result = null;
		try{
			result = targetService.queryCopyTargetList(targetList);
		}catch(Exception e){
			logger.error("queryTargetList error: ", e);
			result = "{error:\"queryTargetList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method queryTargetList.");
		return null;
	}
	
	public String verificationTargets()throws Exception{
		logger.info("Enter method verificationTargets.");
		String result = "";
		try{
			result = targetService.commonResult(targetIds, 1);
	
		}catch(Exception e){
			
			logger.error("verificationTargets error: ", e);
			result = "{error:\"verificationTargets: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method verificationTargets.");
		return null;
	}
	//verificationNoSave yongzhen.li modify 2013-10-24
	public String verificationNoSave()throws Exception{
		logger.info("Enter method verificationNoSave.");
		String result = "";
		try{
			
			result = targetService.common(targetList2,3);
	
		}catch(Exception e){
			
			logger.error("verificationNoSave error: ", e);
			result = "{error:\"verificationNoSave: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method verificationNoSave.");
		return null;
	}
	//procedure����verification
	public String verificationNoSaveProcedure()throws Exception{
		logger.info("Enter method verificationNoSaveProcedure.");
		String result = "";
		try{
			
			result = targetService.ProcedureProcessVerification(targetList2);
	
		}catch(Exception e){
			
			logger.error("verificationNoSaveProcedure error: ", e);
			result = "{error:\"verificationNoSaveProcedure: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method verificationNoSaveProcedure.");
		return null;
	}

	public String downLoadResultToExcel() throws Exception{
		logger.info("Exit method downLoadResultToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "targetResultExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = targetService.downLoadResultToExcel(targetIds,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downLoadResultToExcel error: ", e);
		}
		logger.info("Exit method downLoadResultToExcel.");
		return null;
	}
	
	
	public String verificationOldTargets()throws Exception{
		logger.info("Enter method verificationOldTargets.");
		String result = "";
		try{
			result = targetService.commonResult(targetIds, 1);
	
		}catch(Exception e){
			logger.error("verificationOldTargets error: ", e);
			result = "{error:\"verificationOldTargets: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method verificationOldTargets.");
		return null;
	}
	
	public String actualResults()throws Exception{
		logger.info("Enter method actualResults.");
		String result = "";
		try{
			result = targetService.commonResult(targetIds, 2);
	
		}catch(Exception e){
			logger.error("actualResults error: ", e);
			result = "{error:\"actualResults: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method actualResults.");
		return null;
	}
	
	
	public String excelUp()throws Exception{
		logger.info("Enter method excelUp.");
		String result = "";
		try{
			result =targetService.read2007Excel(excelFile,excelFileFileName);
			String aSpilt[]=result.split("!");
			HttpServletRequest request=ServletActionContext.getRequest();
	        request.setAttribute("CeateTarget",aSpilt[0]);
	        request.setAttribute("BanListByvendor", aSpilt[1]);
	        request.setAttribute("curcuitByban", aSpilt[2]);
	        request.setAttribute("curcuitBybanvendor", aSpilt[3]);
		}catch (BPLException ex) {
			request.setAttribute("errorMessage",ex.getMessage());
			return INPUT;
		}
		catch(Exception e){
			logger.error("excelUp error: ", e);
			request.setAttribute("errorMessage","");
			result = "{error:\"excelUp: "+e.getMessage()+"\"}";
			return INPUT;
		}
//		this.writeOutputStream(result);
		//response.getWriter().write(result);
		logger.info("Exit method actualResults.");
		return SUCCESS;
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

	public List<MapEntryVO<String, String>> getPaymentStatusList() {
		return paymentStatusList;
	}

	public void setPaymentStatusList(
			List<MapEntryVO<String, String>> paymentStatusList) {
		this.paymentStatusList = paymentStatusList;
	}

	public List<MapEntryVO<String, String>> getAnalystList() {
		return analystList;
	}

	public void setAnalystList(List<MapEntryVO<String, String>> analystList) {
		this.analystList = analystList;
	}

	public List<MapEntryVO<String, String>> getInvoiceStatusList() {
		return invoiceStatusList;
	}

	public void setInvoiceStatusList(
			List<MapEntryVO<String, String>> invoiceStatusList) {
		this.invoiceStatusList = invoiceStatusList;
	}

	public List<MapEntryVO<String, String>> getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(List<MapEntryVO<String, String>> lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public ITargetService getTargetService() {
		return targetService;
	}

	public void setTargetService(ITargetService targetService) {
		this.targetService = targetService;
	}

	public SearchTargetVO getSearchTargetVO() {
		return searchTargetVO;
	}

	public void setSearchTargetVO(SearchTargetVO searchTargetVO) {
		this.searchTargetVO = searchTargetVO;
	}

	public String getBanId() {
		return banId;
	}

	public void setBanId(String banId) {
		this.banId = banId;
	}

	public List<MapEntryVO<String, String>> getChargeTypeList() {
		return chargeTypeList;
	}

	public void setChargeTypeList(List<MapEntryVO<String, String>> chargeTypeList) {
		this.chargeTypeList = chargeTypeList;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<Target> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<Target> targetList) {
		this.targetList = targetList;
	}

	public List<Integer> getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(List<Integer> targetIds) {
		this.targetIds = targetIds;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public String getExcelFileContentType() {
		return excelFileContentType;
	}

	public void setExcelFileContentType(String excelFileContentType) {
		this.excelFileContentType = excelFileContentType;
	}

	public String getExcelFileFileName() {
		return excelFileFileName;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	
	
	
}
