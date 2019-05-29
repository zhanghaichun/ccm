package com.saninco.ccm.action.circuit;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.circuit.ICircuitService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchCircuitVO;
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
	private List<MapEntryVO<String, String>> customerList = new ArrayList<MapEntryVO<String, String>>();
	private ICommonLookupService commonLookupService;
	private SearchCircuitVO searchCircuitVO;
	private IQuicklinkService quicklinkService;
	private ICircuitService circuitService;
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
		this.customerList = commonLookupService.getSearchConditioncustomers();
		logger.info("Exit method exec");
		return SUCCESS;
	}
	public String saveCircuitSearch() throws Exception {
		logger.info("Enter method saveCircuitSearch.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.CIRCUIT);
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
			result = circuitService.searchCircuit(searchCircuitVO);
		}catch(Exception e){
			logger.error("searchCircuit error: ", e);
			result = "{error:\"searchCircuit: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchCircuit.");
		return null;
	}
	public String getCircuitSearchTotalPageNo() throws Exception{
		logger.info("Enter method getCircuitSearchTotalPageNo.");
		String result = null;
		try{
			result = circuitService.getCircuitsSearchTotalPageNo(searchCircuitVO);
		}catch(Exception e){
			logger.error("getInvoiceSearchTotalPageNo error: ", e);
			result = "{error:\"getCircuitSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getCircuitSearchTotalPageNo.");
		return null;
	}
	public String searchVendorCircuit()throws Exception {
		logger.info("Enter method searchVendorCircuit.");
		String result = null;
		try{
			result = circuitService.searchVendorCircuit(searchCircuitVO);
		}catch(Exception e){
			logger.error("searchVendorCircuit error: ", e);
			result = "{error:\"searchVendorCircuit: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchVendorCircuit.");
		return null;
	}
	public String getVendorCircuitSearchTotalPageNo() throws Exception{
		logger.info("Enter method getVendorCircuitSearchTotalPageNo.");
		String result = null;
		try{
			result = circuitService.getVendorCircuitSearchTotalPageNo(searchCircuitVO);
		}catch(Exception e){
			logger.error("getVendorCircuitSearchTotalPageNo error: ", e);
			result = "{error:\"getVendorCircuitSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getVendorCircuitSearchTotalPageNo.");
		return null;
	}
	public String saveAllToExcel()throws Exception{
		logger.info("Enter method saveAllToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "CircuitCharges.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = circuitService.getAllDataToExcel(searchCircuitVO,excelDirPath,titles);
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
			excelPath = circuitService.getDataToExcel(searchCircuitVO,"CircuitCharges_"+searchCircuitVO.getItemType(),excelDirPath,titles);
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
	            result = circuitService.getTabs(searchCircuitVO);                 
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
	            result = circuitService.getCircuitDateHyperlink(searchCircuitVO);                 
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
			String excelPath = circuitService.getDataToExcel(searchCircuitVO,"All",excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveHistoricalDataToExcel error: ", e);
		}
		logger.info("Exit method saveHistoricalDataToExcel.");
		return null;
	}
	
	
	public String checkVendorCircuitCsv(){
		try {
			if(!checkDownloadMaxnum()){
				writeOutputStream("maxnum");
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String doDownloadVendorCircuitCsv()throws Exception{
		logger.info("Enter method doDownloadVendorCircuitCsv.");
		FileInputStream fis = null;
		try {
			String fileName = "CircuitDetails.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = circuitService.createVendorCircuitToExcel(searchCircuitVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
			circuitService.updateReportDownloadNum(true);
		}catch (Throwable e) {
			circuitService.updateReportDownloadNum(true);
			logger.error("doDownloadVendorCircuitCsv error: ", e);
		}
		logger.info("Exit method doDownloadVendorCircuitCsv.");
		return null;
	}
	
	private boolean checkDownloadMaxnum()throws Exception{
		int reportDownloadMaxnum = 0;
		int reportDownloadNum = 0;
		List l;
		try {
			l = circuitService.searchReportDownloadNumAndMaxNum("report_download_maxnum");
			if(l.size()>0){
				reportDownloadMaxnum = Integer.valueOf(l.get(0).toString());
			}
			l = new ArrayList();
			l = circuitService.searchReportDownloadNumAndMaxNum("report_download_num");
			if(l.size()>0){
				reportDownloadNum = Integer.valueOf(l.get(0).toString());
			}
			if(reportDownloadMaxnum > reportDownloadNum){
				circuitService.updateReportDownloadNum(false);
				return true;
			}
		}catch (Throwable e) {
			circuitService.updateReportDownloadNum(true);
			logger.error("checkDownloadMaxnum error: ", e);
		}
		
		return false;
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
	public ICircuitService getCircuitService() {
		return circuitService;
	}
	public void setCircuitService(ICircuitService circuitService) {
		this.circuitService = circuitService;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
	public List<MapEntryVO<String, String>> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<MapEntryVO<String, String>> customerList) {
		this.customerList = customerList;
	}
	
}
