package com.saninco.ccm.action.contractTariffPriceList;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.contractTariffPriceList.IContractTariffPriceListService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contractTariffPriceList.SearchContractTariffPriceListVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;

/**
 * Quote Inventory 模块。
 */
public class ContractTariffPriceListAction extends CcmActionSupport {

	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	
	private IContractTariffPriceListService contractTariffPriceListService;
	private ICommonLookupService commonLookupService;
	private IQuicklinkService quicklinkService;
	private SearchContractTariffPriceListVO searchContractTariffPriceListVO;
	private SearchQuoteVO searchQuoteVO;
	
	private List<String> titles;
	
	

	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	
	private List<MapEntryVO<String, String>> productList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> productComponentList = new ArrayList<MapEntryVO<String, String>>();
	private long expiryDateContractCount;
	private Integer contractId;
	private String quicklinkName;
	private String queryString;



	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();

	

	/**
	 * @return the quicklinkService
	 */
	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	/**
	 * @param quicklinkService the quicklinkService to set
	 */
	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	/**
	 * @return the quicklinkList
	 */
	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	/**
	 * @param quicklinkList the quicklinkList to set
	 */
	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}
	
	/**
	 * @return the titles
	 */
	public List<String> getTitles() {
		return titles;
	}

	/**
	 * @param titles the titles to set
	 */
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
	
	/**
	 * @return the commonLookupService
	 */
	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	/**
	 * @param commonLookupService the commonLookupService to set
	 */
	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}

	/**
	 * @return the vendorList
	 */
	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	/**
	 * @param vendorList the vendorList to set
	 */
	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	/**
	 * @return the banList
	 */
	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	/**
	 * @param banList the banList to set
	 */
	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}
	
	
	/**
	 * @return the contractTariffPriceListService
	 */
	public IContractTariffPriceListService getContractTariffPriceListService() {
		return contractTariffPriceListService;
	}

	/**
	 * @param contractTariffPriceListService the contractTariffPriceListService to set
	 */
	public void setContractTariffPriceListService(
			IContractTariffPriceListService contractTariffPriceListService) {
		this.contractTariffPriceListService = contractTariffPriceListService;
	}
	

	/**
	 * @return the searchContractTariffPriceListVO
	 */
	public SearchContractTariffPriceListVO getSearchContractTariffPriceListVO() {
		return searchContractTariffPriceListVO;
	}

	/**
	 * @param searchContractTariffPriceListVO the searchContractTariffPriceListVO to set
	 */
	public void setSearchContractTariffPriceListVO(
			SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		this.searchContractTariffPriceListVO = searchContractTariffPriceListVO;
	}

	/**
	 * @return the searchQuoteVO
	 */
	public SearchQuoteVO getSearchQuoteVO() {
		return searchQuoteVO;
	}

	/**
	 * @param searchQuoteVO the searchQuoteVO to set
	 */
	public void setSearchQuoteVO(SearchQuoteVO searchQuoteVO) {
		this.searchQuoteVO = searchQuoteVO;
	}

	/**
	 * @return the expiryDateContractCount
	 */
	public long getExpiryDateContractCount() {
		return expiryDateContractCount;
	}

	/**
	 * @param expiryDateContractCount the expiryDateContractCount to set
	 */
	public void setExpiryDateContractCount(long expiryDateContractCount) {
		this.expiryDateContractCount = expiryDateContractCount;
	}
	
	/**
	 * @return the contractId
	 */
	public Integer getContractId() {
		return contractId;
	}

	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return the productList
	 */
	public List<MapEntryVO<String, String>> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<MapEntryVO<String, String>> productList) {
		this.productList = productList;
	}

	/**
	 * @return the productComponentList
	 */
	public List<MapEntryVO<String, String>> getProductComponentList() {
		return productComponentList;
	}

	/**
	 * @param productComponentList the productComponentList to set
	 */
	public void setProductComponentList(
			List<MapEntryVO<String, String>> productComponentList) {
		this.productComponentList = productComponentList;
	}

	/**
	 * @return the quicklinkName
	 */
	public String getQuicklinkName() {
		return quicklinkName;
	}

	/**
	 * @param quicklinkName the quicklinkName to set
	 */
	public void setQuicklinkName(String quicklinkName) {
		this.quicklinkName = quicklinkName;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * @param queryString the queryString to set
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * 页面加载的时候获取信息，主要是 vendor select list 和 ban select list.
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		this.vendorList = commonLookupService.getSearchConditionVendors();
		this.banList = commonLookupService.getSearchConditionBans();
		// this.expiryDateContractCount = contractTariffPriceListService.getContractCountsByExpiryDate(searchContractTariffPriceListVO);
		// Product list.
		this.productList = contractTariffPriceListService.getProductMapList();
		
		// Product Component list.
		this.productComponentList = contractTariffPriceListService.getProductComponentMapList();
		
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	/**
	 * 查询Contract 列表信息。
	 */
	public String getContractList() throws Exception {
		logger.info("Enter method getContractList.");
		String result = null;
		try{
			result = contractTariffPriceListService.getContractList(searchContractTariffPriceListVO, searchQuoteVO);
		}catch(Exception e){
			logger.error("getContractList error: ", e);
			result = "{error:\"getContractList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getContractList.");
		return null;
	}

	/**
	 * 根据 expiry date 条件来查询 contracts 的个数。
	 * @returns
	 * @throws Exception
	 */
	public String getContractCountsByExpiryDate() throws Exception {
		logger.info("Enter method getContractCountsByExpiryDate.");
		String resultCount = null;
		try{
			long count = contractTariffPriceListService.getContractCountsByExpiryDate(searchContractTariffPriceListVO, searchQuoteVO);
			// 保证返回的数据是JSON格式的。
			resultCount = "{\"expiryDateContractCount\": "+ count +"}";
		}catch(Exception e){
			logger.error("getContractCountsByExpiryDate error: ", e);
			resultCount = "{\"error\":\"getContractCountsByExpiryDate error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(resultCount); // 直接向前台返回一个JSON类型的数据。
		logger.info("Exit method getContractCountsByExpiryDate.");
		return null;
	}
	
	/**
	 * 查询Contract 列表的分页信息.
	 */
	public String getContractListPageNo() throws Exception {
		
		logger.info("Enter method getContractListPageNo.");
		String result = null;
		try{
			result = contractTariffPriceListService.getContractListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
		}catch(Exception e){
			logger.error("getContractListPageNo error: ", e);
			result = "{error:\"getContractListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getContractListPageNo.");
		return null;
	}


	/**
	 * 将 Contract 列表内容下载成excel文件
	 */
	public String downloadContractList() throws Exception {
		
		logger.info("Enter method downloadContractList.");
		FileInputStream fis = null;
		String fileName = "";
		try {
			
			if (searchContractTariffPriceListVO.getIsExpiryDateContract() != null && searchContractTariffPriceListVO.getIsExpiryDateContract() == true) {
				fileName = "ExpiryDateContract.xls";
			} else {
				fileName = "Contract.xls";
			}
			
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = contractTariffPriceListService.downloadContractList(searchContractTariffPriceListVO, searchQuoteVO, excelDirPath, titles);
		
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downloadContractList error: ", e);
		}
		logger.info("Exit method downloadContractList.");
		
		return null;
	}
	

	/**
	 * 查询Tariff 列表的分页信息。
	 */
	public String getTariffListPageNo() throws Exception {
		
		logger.info("Enter method getTariffListPageNo.");
		String result = null;
		try{
			result = contractTariffPriceListService.getTariffListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
		}catch(Exception e){
			logger.error("getTariffListPageNo error: ", e);
			result = "{error:\"getTariffListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTariffListPageNo.");
		return null;
	}

	/**
	 * 查询Tariff 列表。
	 */
	public String getTariffList() throws Exception {
		logger.info("Enter method getTariffList.");
		String result = null;
		try{
			result = contractTariffPriceListService.getTariffList(searchContractTariffPriceListVO, searchQuoteVO);
		}catch(Exception e){
			logger.error("getTariffList error: ", e);
			result = "{error:\"getTariffList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTariffList.");
		return null;
	}

	/**
	 * 下载 Tariff List 列表到Excel文件中.
	 */
	public String downloadTariffList() throws Exception {
		
		logger.info("Enter method downloadTariffList.");
		FileInputStream fis = null;
		try {
			String fileName = "Tariff.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = contractTariffPriceListService.downloadTariffList(searchContractTariffPriceListVO, searchQuoteVO, excelDirPath, titles);
		
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downloadTariffList error: ", e);
		}
		logger.info("Exit method downloadTariffList.");
		
		return null;
	}
	
	/**
	 * 查询Price List 列表的分页信息。
	 */
	public String getPriceListListPageNo() throws Exception {
		
		logger.info("Enter method getPriceListListPageNo.");
		String result = null;
		try{
			result = contractTariffPriceListService.getPriceListListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
		}catch(Exception e){
			logger.error("getPriceListListPageNo error: ", e);
			result = "{error:\"getPriceListListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPriceListListPageNo.");
		return null;
	}
	
	/**
	 * 查询Price List 列表。
	 */
	public String getPriceListList() throws Exception {
		logger.info("Enter method getPriceListList.");
		String result = null;
		try{
			result = contractTariffPriceListService.getPriceListList(searchContractTariffPriceListVO, searchQuoteVO);
		}catch(Exception e){
			logger.error("getPriceListList error: ", e);
			result = "{error:\"getPriceListList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPriceListList.");
		return null;
	}
	
	/**
	 * 下载 Price List 列表到Excel文件中.
	 */
	public String downloadPriceListList() throws Exception {
		
		logger.info("Enter method downloadPriceListList.");
		FileInputStream fis = null;
		try {
			String fileName = "Price List.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = contractTariffPriceListService.downloadPriceListList(searchContractTariffPriceListVO,searchQuoteVO, excelDirPath, titles);
		
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downloadPriceListList error: ", e);
		}
		logger.info("Exit method downloadPriceListList.");
		
		return null;
	}
	
	/**
	 * 保存 快捷查询连接 的数据
	 * @return
	 */
	public String saveContractTariffPriceListSearch() throws Exception {
		logger.info("Enter method saveContractTariffPriceListSearch.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.CONTRACT_TARIFF_PRICELIST);
		}catch(Exception e){
			logger.error("saveContractTariffPriceListSearch error: ", e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			result = "{error:\"saveContractTariffPriceListSearch: "+bpe.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveContractTariffPriceListSearch.");
		return null;
	}
	
	/**
	 *  查询Rate Discrepancy 列表的信息.
	 * @return
	 */
	public String getRateDiscrepancyList() throws Exception {
		logger.info("Enter method getRateDiscrepancyList.");
		String result = null;
		try{
			result = contractTariffPriceListService.getRateDiscrepancyList(searchContractTariffPriceListVO);
		}catch(Exception e){
			logger.error("getRateDiscrepancyList error: ", e);
			result = "{error:\"getRateDiscrepancyList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getRateDiscrepancyList.");
		return null;
	}
	
	/**
	 * 查询Rate Discrepancy 列表的分页信息.
	 */
	public String getRateDiscrepancyListPageNo() throws Exception {
		
		logger.info("Enter method getRateDiscrepancyListPageNo.");
		String result = null;
		try{
			result = contractTariffPriceListService.getRateDiscrepancyListPageNo(searchContractTariffPriceListVO);
		}catch(Exception e){
			logger.error("getRateDiscrepancyListPageNo error: ", e);
			result = "{error:\"getRateDiscrepancyListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getRateDiscrepancyListPageNo.");
		return null;
	}
	
	/**
	 * Excel 导出
	 * @return
	 * @throws Exception
	 */
	public String saveExcelBySearchRateDiscrepancy() throws Exception{
		logger.info("Exit method saveExcelBySearchRateDiscrepancy.");
		FileInputStream fis = null;
		try {
			String fileName = "rateDiscrepancyExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = contractTariffPriceListService.createRateDiscrepancyToExcel(searchContractTariffPriceListVO,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchRateDiscrepancy error: ", e);
		}
		logger.info("Exit method saveExcelBySearchRateDiscrepancy.");
		return null;
	}
}