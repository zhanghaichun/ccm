package com.saninco.ccm.action.quoteInventory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.quoteInventory.IQuoteInventoryService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;
/**
 * Quote Inventory 模块。
 */
public class QuoteInventoryAction extends CcmActionSupport {

	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private SearchQuoteVO searchQuoteVO;
	private String circuitNumber;
	private String quicklinkName;
	private String queryString;
	
	


	private IQuoteInventoryService quoteInventoryService;
	private IQuicklinkService quicklinkService;
	
	private List<String> titles;
	private List<Integer> ids;
	
	

	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> providerList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> productList = new ArrayList<MapEntryVO<String, String>>();
	
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
	 * @return the providerList
	 */
	public List<MapEntryVO<String, String>> getProviderList() {
		return providerList;
	}

	/**
	 * @param providerList the providerList to set
	 */
	public void setProviderList(List<MapEntryVO<String, String>> providerList) {
		this.providerList = providerList;
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
	 * @return the quoteInventoryService
	 */
	public IQuoteInventoryService getQuoteInventoryService() {
		return quoteInventoryService;
	}

	/**
	 * @param quoteInventoryService the quoteInventoryService to set
	 */
	public void setQuoteInventoryService(IQuoteInventoryService quoteInventoryService) {
		this.quoteInventoryService = quoteInventoryService;
	}

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
	 * @return the ids
	 */
	public List<Integer> getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	
	/**
	 * @return the circuitNumber
	 */
	public String getCircuitNumber() {
		return circuitNumber;
	}

	/**
	 * @param circuitNumber the circuitNumber to set
	 */
	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
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
	 * 页面加载的时候获取信息，主要是 provider select list 和 product select list.
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		
		// 获取 provider list.
		this.providerList = quoteInventoryService.getProviderListMap();
		
		// 获取 product list.
		this.productList = quoteInventoryService.getProductListMap();
		
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	/**
	 * 查询 Quote Inventory 列表信息。
	 */
	public String searchQuoteInventoryList() throws Exception {
		logger.info("Enter method searchQuoteInventoryList.");
		String result = null;
		try{
			result = quoteInventoryService.searchQuoteInventoryList(searchQuoteVO);
		}catch(Exception e){
			logger.error("searchQuoteInventoryList error: ", e);
			result = "{error:\"searchQuoteInventoryList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchQuoteInventoryList.");
		return null;
	}
	
	/**
	 * 查询 Quote Inventory 列表分页信息。
	 */
	public String getQuoteInventoryViewListPageNo() throws Exception {
		logger.info("Enter method getQuoteInventoryViewListPageNo.");
		String result = null;
		try{
			result = quoteInventoryService.getQuoteInventoryViewListPageNo(searchQuoteVO);
		}catch(Exception e){
			logger.error("getQuoteInventoryViewListPageNo error: ", e);
			result = "{error:\"getQuoteInventoryViewListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getQuoteInventoryViewListPageNo.");
		return null;
	}
	
	/**
	 * 将 quote inventory 列表内容下载成excel文件
	 */
	public String downloadQuoteInventoryToExcel() throws Exception {
		
		logger.info("Enter method downloadQuoteInventoryToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "Quote Inventory.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = quoteInventoryService.downloadQuoteInventoryToExcel(searchQuoteVO, excelDirPath, titles, ids);
		
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downloadQuoteInventoryToExcel error: ", e);
		}
		logger.info("Exit method downloadQuoteInventoryToExcel.");
		
		return null;
	}

	/**
	 * 将Quote Inventory 表中信息的title信息当做Excel模板下载下来
	 */
	public String downloadQuoteInventoryTemplateToExcel() throws Exception {
		
		logger.info("Enter method downloadQuoteInventoryTemplateToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "Quote Inventory Template.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = quoteInventoryService.downloadQuoteInventoryTemplateToExcel(excelDirPath, titles);
		
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downloadQuoteInventoryTemplateToExcel error: ", e);
		}
		logger.info("Exit method downloadQuoteInventoryTemplateToExcel.");
		
		return null;
	}

	
	/**
	 * 保存 快捷查询连接 的数据
	 * @return
	 */
	public String saveAllQuoteInventory() throws Exception {
		logger.info("Enter method saveAllQuoteInventory.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.QUOTE_INVENTORY);
		}catch(Exception e){
			logger.error("saveAllQuoteInventory error: ", e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			result = "{error:\"saveAllQuoteInventory: "+bpe.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveAllQuoteInventory.");
		return null;
	}
	
	
}