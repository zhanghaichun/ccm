package com.saninco.ccm.action.vendor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.vendor.IVendorInventoryService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchVendorInventoryVO;

public class VendorInventoryAction extends CcmActionSupport { /**
	 * 
	 */
	private static final long serialVersionUID = 5113333404510903522L;
	private IVendorInventoryService vendorInventoryService;
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> productList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> accessList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> bandwidthList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> qodList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> countryList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> termList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private SearchVendorInventoryVO searchVendorInventoryVO = new SearchVendorInventoryVO();
	private IQuicklinkService quicklinkService;
	private String quicklinkName;
	private String queryString;
	private List<String> titles;
	private String countryId;
	private String provinceId;
	@Override
	public String exec() throws Exception {
		this.vendorList = commonLookupService.getSearchConditionVendors();
		this.productList = vendorInventoryService.getConditionProductList();
		this.accessList = vendorInventoryService.getConditionAccessList();
		this.bandwidthList = vendorInventoryService.getConditionBandwidthList();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		this.qodList = vendorInventoryService.getConditionQosList();
		this.termList = vendorInventoryService.getConditionTermList();
		this.countryList = vendorInventoryService.getConditionCountryList();
		return "success";
	}
	public String getListByProvinceId() throws Exception{
		logger.info("Enter method getListByProvinceId.");
		String result = null;
		try{
			result = this.vendorInventoryService.getListByProvinceId(new Integer(this.countryId));
		}catch(Exception e){
			logger.error("getListByProvinceId error: ", e);
			result = "{error:\"getListByProvinceId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getListByProvinceId.");
		return null;
	}
	public String getListByCityId() throws Exception{
		logger.info("Enter method getListByCityId.");
		String result = null;
		try{
			result = this.vendorInventoryService.getListByCityId(new Integer(this.provinceId));
		}catch(Exception e){
			logger.error("getListByCityId error: ", e);
			result = "{error:\"getListByCityId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getListByCityId.");
		return null;
	}
	public String getVendorInventorySearchTotalPageNo() throws Exception{
		logger.info("Enter method getVendorInventorySearchTotalPageNo.");
		String result = null;
		try{
			result = vendorInventoryService.getInventorySearchTotalPageNo(searchVendorInventoryVO);
		}catch(Exception e){
			logger.error("getInvoiceSearchTotalPageNo error: ", e);
			result = "{error:\"getVendorInventorySearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getVendorInventorySearchTotalPageNo.");
		return null;
	}
	public String searchVendorInventory()throws Exception {
		logger.info("Enter method searchVendorInventory.");
		String result = null;
		try{
			result = vendorInventoryService.searchVendorInventory(searchVendorInventoryVO);
		}catch(Exception e){
			logger.error("searchVendorInventory error: ", e);
			result = "{error:\"searchVendorInventory: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchVendorInventory.");
		return null;
	}
	
	public String doDownloadVendorInventoryCsv()throws Exception{
		logger.info("Enter method doDownloadVendorInventoryCsv.");
		FileInputStream fis = null;
		try {
			String fileName = "VendoryInventory.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = vendorInventoryService.createVendorInventoryToExcel(searchVendorInventoryVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("doDownloadVendorInventoryCsv error: ", e);
		}
		logger.info("Exit method doDownloadVendorInventoryCsv.");
		return null;
	}
	
	public String doDownloadVendorInventoryPDF()throws Exception{
		logger.info("Enter method doDownloadVendorInventoryPDF.");
		FileInputStream fis = null;
		try {
			String fileName = "VendoryInventory.pdf";
			String pdfDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String jasperPath = request.getSession().getServletContext().getRealPath(File.separator + "jasper") + File.separator;
			List<String> tem = new ArrayList<String>();
			tem.add("VendoryInventoryReport.jasper");
			String excelPath = vendorInventoryService.createVendorInventoryToLetter(searchVendorInventoryVO, pdfDirPath, jasperPath, tem);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("doDownloadVendorInventoryPDF error: ", e);
		}
		logger.info("Exit method doDownloadVendorInventoryPDF.");
		return null;
	}
	
	public String saveVendorInventorySearch() throws Exception {
		logger.info("Enter method saveVendorInventorySearch.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.VENDOR_INVENTORY);
		}catch(Exception e){
			logger.error("saveVendorInventorySearch error: ", e);
			result = "{error:\""+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveVendorInventorySearch.");
		return null;
	}

	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public IVendorInventoryService getVendorInventoryService() {
		return vendorInventoryService;
	}

	public List<MapEntryVO<String, String>> getProductList() {
		return productList;
	}

	public List<MapEntryVO<String, String>> getAccessList() {
		return accessList;
	}

	public List<MapEntryVO<String, String>> getBandwidthList() {
		return bandwidthList;
	}

	public List<MapEntryVO<String, String>> getQodList() {
		return qodList;
	}

	public List<MapEntryVO<String, String>> getTermList() {
		return termList;
	}

	public void setVendorInventoryService(
			IVendorInventoryService vendorInventoryService) {
		this.vendorInventoryService = vendorInventoryService;
	}

	public SearchVendorInventoryVO getSearchVendorInventoryVO() {
		return searchVendorInventoryVO;
	}

	public void setSearchVendorInventoryVO(
			SearchVendorInventoryVO searchVendorInventoryVO) {
		this.searchVendorInventoryVO = searchVendorInventoryVO;
	}
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
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
	public List<MapEntryVO<String, String>> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<MapEntryVO<String, String>> countryList) {
		this.countryList = countryList;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
}


