package com.saninco.ccm.circuitold.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.circuitold.model.SearchCircuitVO;
import com.saninco.ccm.circuitold.service.ICircuitService;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
/**
 * create by donghao.guo 2011/09/20 14:00
 * for Search circuit charges and SCOAs
 */
public class CircuitAction extends CcmActionSupport{
	
	private static final long serialVersionUID = -4144327007345549784L;

	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> lineOfBusiness = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private ICommonLookupService commonLookupService;
	private SearchCircuitVO searchCircuitVO;
	private IQuicklinkService quicklinkService;
	private ICircuitService circuitOldService;
	private List<String> titles;
	
	private String quicklinkName;
	private String queryString;
	@AllGranted(value="FUNCTION_5000")
	public String exec() throws Exception {
		// TODO Auto-generated method stub
		logger.info("Enter method exec");
		this.vendorList = commonLookupService.getSearchConditionVendors();
		this.banList = commonLookupService.getSearchConditionBans();
		this.lineOfBusiness = commonLookupService.getLineofBbusiness();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method exec");
		return SUCCESS;
	}
	public String saveCircuitSearch() throws Exception {
		logger.info("Enter method saveCircuitSearch.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.OldCIRCUIT);
		}catch(Exception e){
			logger.error("saveCircuitSearch error: ", e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			result = "{error:\"saveCircuitSearch: "+bpe.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveCircuitSearch.");
		return null;
	}
	public String searchCircuit()throws Exception {
		logger.info("Enter method searchCircuit.");
		String result = null;
		try{
			result = circuitOldService.searchCircuit(searchCircuitVO);
		}catch(Exception e){
			logger.error("searchInvoice error: ", e);
			result = "{error:\"searchInvoice: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchCircuit.");
		return null;
	}
	public String getCircuitSearchTotalPageNo() throws Exception{
		logger.info("Enter method getCircuitSearchTotalPageNo.");
		String result = null;
		try{
			result = circuitOldService.getCircuitsSearchTotalPageNo(searchCircuitVO);
		}catch(Exception e){
			logger.error("getInvoiceSearchTotalPageNo error: ", e);
			result = "{error:\"getCircuitSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCircuitSearchTotalPageNo.");
		return null;
	}
	public String saveAllToExcel()throws Exception{
		logger.info("Enter method saveAllToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "CircuitCharges.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = circuitOldService.getAllDataToExcel(searchCircuitVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveMrcToExcel error: ", e);
		}
		logger.info("Exit method saveMrcToExcel.");
		return null;
	}
	public String saveExcel()throws  Exception{
		logger.info("Enter method saveMrcToExcel.");
		FileInputStream fis = null;
		String fileName = "";
		String excelPath ="";
		try {
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			fileName = "CircuitCharges_"+searchCircuitVO.getItemType()+".xls";
			excelPath = circuitOldService.getDataToExcel(searchCircuitVO,"CircuitCharges_"+searchCircuitVO.getItemType(),excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveMrcToExcel error: ", e);
		}
		logger.info("Exit method saveMrcToExcel.");
		return null;
	}
	public String getTabView() throws Exception {
		 logger.info("Enter method getAccordionTabView.");                                  
	        String result = null;                                                         
	        try{
	            result = circuitOldService.getTabs(searchCircuitVO);                 
	        }catch(Exception e){
	            logger.error("getAccordionTabView error: ", e);
	            result = "{error:\"getAccordionTabView error: "+e.getMessage()+"\"}";    
	        }
	        this.writeOutputStream(result);   
	        logger.info("Exit method getAccordionTabView.");                                    
	        return null;                                                                                               
    }
	public String getCircuitDateHyperlink() throws Exception{
			logger.info("Enter method getCircuitDateHyperlink.");                                  
	        String result = null;                                                         
	        try{
	            result = circuitOldService.getCircuitDateHyperlink(searchCircuitVO);                 
	        }catch(Exception e){
	            logger.error("getCircuitDateHyperlink error: ", e);
	            result = "{error:\"getCircuitDateHyperlink error: "+e.getMessage()+"\"}";    
	        }
	        this.writeOutputStream(result);   
	        logger.info("Exit method getCircuitDateHyperlink.");                                    
	        return null; 
	}
//	@AllGranted(value="FUNCTION_5100")
	public String saveHistoricalDataToExcel()throws Exception{
		logger.info("Enter method saveHistoricalDataToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "HistoricalCircuitCharges.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = circuitOldService.getDataToExcel(searchCircuitVO,"All",excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveHistoricalDataToExcel error: ", e);
		}
		logger.info("Exit method saveHistoricalDataToExcel.");
		return null;
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

	

	public List<MapEntryVO<String, String>> getLineOfBusiness() {
		return lineOfBusiness;
	}


	public void setLineOfBusiness(List<MapEntryVO<String, String>> lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}


	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}


	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}


	public SearchCircuitVO getSearchCircuitVO() {
		return searchCircuitVO;
	}


	public void setSearchCircuitVO(SearchCircuitVO searchCircuitVO) {
		this.searchCircuitVO = searchCircuitVO;
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
	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}
	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}
	public void setCircuitOldService(ICircuitService circuitOldService) {
		this.circuitOldService = circuitOldService;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	
}
