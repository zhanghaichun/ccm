package com.saninco.ccm.action.circuitpage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.inventory.IInventoryDashboardService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInventoryDashboardVO;
import com.saninco.ccm.vo.UpdateMasterInventoryVO;

public class CircuitPageAction extends CcmActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3944410906730927602L;
	private IInventoryDashboardService inventoryDashboardService;
	private IQuicklinkService quicklinkService;
	private SearchInventoryDashboardVO searchInventoryDashboardVO;
	private UpdateMasterInventoryVO updateMasterInventoryVO;
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> summaryVendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> productList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> scoaCodeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> completeList = new ArrayList<MapEntryVO<String, String>>();
	
	// Master Inventory cost type list.
	private List<MapEntryVO<String, String>> costTypeList = new ArrayList<MapEntryVO<String, String>>();
	
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private String quicklinkName;
	private String queryString;
	private List<String> titles;
	private Map<String, Object> miDetail = new HashMap<String, Object>();
	
	/**
	 * 快捷查询连接的 查询
	 */
	@Override
	public String exec() throws Exception {
		// TODO Auto-generated method stub
		logger.info("Enter CircuitPageAction method exec");
		
		return SUCCESS;
	}
	
	/**
	 * 页面初始化信息查询
	 * @return
	 * @throws Exception
	 */
	public String inventoryDashboard() throws Exception {
		logger.info("Enter CircuitPageAction method inventoryDashboard");
		this.vendorList = commonLookupService.getSearchConditionVendors();
		this.summaryVendorList = commonLookupService.getSearchConditionSummaryVendors();
		this.productList = commonLookupService.getSearchConditionProduct();
		this.banList = commonLookupService.getSearchConditionBans();
		this.completeList = inventoryDashboardService.searchMasterInventoryComplete();
		
		// Get Master inventory cost type list
		this.costTypeList = inventoryDashboardService.searchMasterInventoryCostType();
		
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		this.scoaCodeList = commonLookupService.getAccountCode();
		return SUCCESS;
	}
	
	/**
	 * 根据检索条件查询Dashboard 饼图
	 * @return
	 * @throws Exception
	 */
	public String searchMasterInvenrotyCompleteByFilter() throws BPLException, IOException{
		String result = null;
		try {
			result = inventoryDashboardService.searchMasterInventoryCompleteByFilter(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("searchMasterInvenrotyComplete error: ", e);
			result = "{error:\"searchMasterInvenrotyComplete error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method searchMasterInvenrotyComplete.");
		return null;
	}
	
	/**
	 * 根据查询条件 查询 master inventory 数据列表的行数信息
	 * @return
	 * @throws Exception
	 */
	public String getMasterInventoryListPageNo() throws Exception {
			
		logger.info("Enter method getMasterInventoryListPageNo.");
		String result = null;
		try {
			result = inventoryDashboardService.getMasterInventoryListPageNo(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("getMasterInventoryListPageNo error: ", e);
			result = "{error:\"getMasterInventoryListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getMasterInventoryListPageNo.");
		return null;
	}
	/**
	 * 根据查询条件 查询 master inventory 数据列表
	 * @return
	 * @throws Exception
	 */
	public String searchMasterInventoryList() throws Exception {
		logger.info("Enter method searchMasterInventoryList.");

		String result = null;
		try {
			result = inventoryDashboardService.searchMasterInventoryList(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("searchMasterInventoryList error: ", e);
			result = "{error:\"searchMasterInventoryList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchMasterInventoryList.");
		return null;
	}
	
	/**
	 * Excel 导出
	 * @return
	 * @throws Exception
	 */
	public String saveExcelBySearchMasterInventory() throws Exception{
		logger.info("Exit method saveExcelBySearchMasterInventory.");
		FileInputStream fis = null;
		try {
			String fileName = "Master Circuit Inventory.xlsx";
			if(searchInventoryDashboardVO.getInventoryId() != null &&  !"".equals(searchInventoryDashboardVO.getInventoryId())) {
				fileName = "Master Inventory Template.xlsx";
			}
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = inventoryDashboardService.createMasterInventoryToExcel(searchInventoryDashboardVO,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchMasterInventory error: ", e);
		}
		logger.info("Exit method saveExcelBySearchMasterInventory.");
		return null;
	}
	
	/**
	 * Excel 导出 master_inventory 表中，某条数据的 修改记录
	 * @return
	 * @throws Exception
	 */
	public String saveExcelBySearchInventoryChangeHistory() throws Exception{
		logger.info("Exit method saveExcelBySearchInventoryChangeHistory.");
		FileInputStream fis = null;
		try {
			String fileName = "Master Circuit Inventory - Update History.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = inventoryDashboardService.createInventoryChangeHistoryToExcel(searchInventoryDashboardVO,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchInventoryChangeHistory error: ", e);
		}
		logger.info("Exit method saveExcelBySearchInventoryChangeHistory.");
		return null;
	}
	
	
	
	/**
	 * 保存 快捷查询连接 的数据
	 * @return
	 */
	public String saveMasterInventorySearch() throws Exception {
		logger.info("Enter method saveMasterInventorySearch.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.MASTER_INVENTORY);
		}catch(Exception e){
			logger.error("saveCircuitSearch error: ", e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			result = "{error:\"saveMasterInventorySearch: "+bpe.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveMasterInventorySearch.");
		return null;
	}
	
	/**
	 * 根据 ccm_db.master_inventory.id 查询 history_db.inventory_change_history 表中对该条记录修改的历史记录列表（条数）
	 * @return
	 */
	public String getInventoryChangeHistoryListPageNo() throws Exception {
		logger.info("Enter method getInventoryChangeHistoryListPageNo.");
		String result = null;
		try {
			result = inventoryDashboardService.getInventoryChangeHistoryListPageNo(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("getInventoryChangeHistoryListPageNo error: ", e);
			result = "{error:\"getInventoryChangeHistoryListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getInventoryChangeHistoryListPageNo.");
		return null;
	}
	/**
	 * 根据 ccm_db.master_inventory.id 查询 history_db.inventory_change_history 表中对该条记录修改的历史记录列表（列表）
	 * @return
	 */
	public String getInventoryChangeHistoryList() throws Exception {
		logger.info("Enter method getInventoryChangeHistoryList.");

		String result = null;
		try {
			result = inventoryDashboardService.getInventoryChangeHistoryList(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("getInventoryChangeHistoryList error: ", e);
			result = "{error:\"getInventoryChangeHistoryList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInventoryChangeHistoryList.");
		return null;
	}
	
	/**
	 * 获得一条MI的详细信息
	 * @return
	 */
	public String getMIDetail() throws Exception {
	
		searchInventoryDashboardVO.setPageNo(1);
		searchInventoryDashboardVO.setRecPerPage(1);
		
		logger.info("Enter method searchMasterInventoryList.");

		String result = null;
		try {
			result = inventoryDashboardService.searchMasterInventoryList(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("searchMasterInventoryList error: ", e);
			result = "{error:\"searchMasterInventoryList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchMasterInventoryList.");
		
		
		return null;
	}
	
	/**
	 * 更新一条MI的详细信息
	 * @return
	 */
	public String updateMIDetail() throws Exception {
		
		logger.info("Enter method updateMIDetail.");

		String result = "success";
		try {
			inventoryDashboardService.updateMasterInventory(updateMasterInventoryVO);
		} catch (BPLException e) {
			logger.error("updateMIDetail error: ", e);
			result = "{error:\"updateMIDetail: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method updateMIDetail.");
		return null;
	}
	
	
	
	
	/**
	 * 根据检索条件查询Dashboard 饼图
	 * @return
	 * @throws Exception
	 */
	public String searchMasterInvenrotyRateCompleteByFilter() throws BPLException, IOException{
		String result = null;
		try {
			result = inventoryDashboardService.searchMasterInventoryCompleteByFilter(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("searchMasterInvenrotyComplete error: ", e);
			result = "{error:\"searchMasterInvenrotyComplete error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method searchMasterInvenrotyComplete.");
		return null;
	}
	
	/**
	 * 根据查询条件 查询 master inventory 数据列表的行数信息
	 * @return
	 * @throws Exception
	 */
	public String getMasterInventoryRateListPageNo() throws Exception {
			
		logger.info("Enter method getMasterInventoryListPageNo.");
		String result = null;
		try {
			result = inventoryDashboardService.getMasterInventoryRateListPageNo(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("getMasterInventoryListPageNo error: ", e);
			result = "{error:\"getMasterInventoryListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getMasterInventoryListPageNo.");
		return null;
	}
	/**
	 * 根据查询条件 查询 master inventory 数据列表
	 * @return
	 * @throws Exception
	 */
	public String searchMasterInventoryRateList() throws Exception {
		logger.info("Enter method searchMasterInventoryList.");

		String result = null;
		try {
			result = inventoryDashboardService.searchMasterInventoryRateList(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("searchMasterInventoryList error: ", e);
			result = "{error:\"searchMasterInventoryList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchMasterInventoryList.");
		return null;
	}
	
	/**
	 * Excel 导出
	 * @return
	 * @throws Exception
	 */
	public String saveExcelBySearchMasterInventoryRate() throws Exception{
		logger.info("Exit method saveExcelBySearchMasterInventoryRate.");
		FileInputStream fis = null;
		try {
			String fileName = "Master Inventory Rate.xlsx";
			if(searchInventoryDashboardVO.getInventoryId() != null &&  !"".equals(searchInventoryDashboardVO.getInventoryId())) {
				fileName = "Master Inventory Rate Template.xlsx";
			}
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = inventoryDashboardService.createMasterInventoryRateToExcel(searchInventoryDashboardVO,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchMasterInventoryRate error: ", e);
		}
		logger.info("Exit method saveExcelBySearchMasterInventoryRate.");
		return null;
	}
	
	public String getInventoryRateChangeHistoryListPageNo() throws Exception {
		logger.info("Enter method getInventoryRateChangeHistoryListPageNo.");
		String result = null;
		try {
			result = inventoryDashboardService.getInventoryRateChangeHistoryListPageNo(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("getInventoryRateChangeHistoryListPageNo error: ", e);
			result = "{error:\"getInventoryRateChangeHistoryListPageNo error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);	
		logger.info("Exit method getInventoryRateChangeHistoryListPageNo.");
		return null;
	}
	public String getInventoryRateChangeHistoryList() throws Exception {
		logger.info("Enter method getInventoryRateChangeHistoryList.");

		String result = null;
		try {
			result = inventoryDashboardService.getInventoryRateChangeHistoryList(searchInventoryDashboardVO);
		} catch (BPLException e) {
			logger.error("getInventoryRateChangeHistoryList error: ", e);
			result = "{error:\"getInventoryRateChangeHistoryList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInventoryRateChangeHistoryList.");
		return null;
	}
	
	public String saveExcelBySearchInventoryRateChangeHistory() throws Exception{
		logger.info("Exit method saveExcelBySearchInventoryRateChangeHistory.");
		FileInputStream fis = null;
		try {
			String fileName = "Master Circuit Inventory Rate - Update History.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = inventoryDashboardService.createInventoryRateChangeHistoryToExcel(searchInventoryDashboardVO,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchInventoryRateChangeHistory error: ", e);
		}
		logger.info("Exit method saveExcelBySearchInventoryRateChangeHistory.");
		return null;
	}
	
	// Getters and Setters
	
	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public List<MapEntryVO<String, String>> getScoaCodeList() {
		return scoaCodeList;
	}

	public void setScoaCodeList(List<MapEntryVO<String, String>> scoaCodeList) {
		this.scoaCodeList = scoaCodeList;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}
	
	public List<MapEntryVO<String, String>> getProductList() {
		return productList;
	}

	public void setProductList(List<MapEntryVO<String, String>> productList) {
		this.productList = productList;
	}
	
	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}


	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}
	
	public IInventoryDashboardService getInventoryDashboardService() {
		return inventoryDashboardService;
	}


	public void setInventoryDashboardService(
			IInventoryDashboardService inventoryDashboardService) {
		this.inventoryDashboardService = inventoryDashboardService;
	}


	public SearchInventoryDashboardVO getSearchInventoryDashboardVO() {
		return searchInventoryDashboardVO;
	}


	public void setSearchInventoryDashboardVO(
			SearchInventoryDashboardVO searchInventoryDashboardVO) {
		this.searchInventoryDashboardVO = searchInventoryDashboardVO;
	}
	

	public List<MapEntryVO<String, String>> getCompleteList() {
		return completeList;
	}


	public void setCompleteList(List<MapEntryVO<String, String>> completeList) {
		this.completeList = completeList;
	}
	
	
	/**
	 * @return the costTypeList
	 */
	public List<MapEntryVO<String, String>> getCostTypeList() {
		return costTypeList;
	}

	/**
	 * @param costTypeList the costTypeList to set
	 */
	public void setCostTypeList(List<MapEntryVO<String, String>> costTypeList) {
		this.costTypeList = costTypeList;
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
	
	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}


	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
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

	public List<MapEntryVO<String, String>> getSummaryVendorList() {
		return summaryVendorList;
	}

	public void setSummaryVendorList(
			List<MapEntryVO<String, String>> summaryVendorList) {
		this.summaryVendorList = summaryVendorList;
	}

	public Map<String, Object> getMiDetail() {
		return miDetail;
	}

	public void setMiDetail(Map<String, Object> miDetail) {
		this.miDetail = miDetail;
	}

	public UpdateMasterInventoryVO getUpdateMasterInventoryVO() {
		return updateMasterInventoryVO;
	}

	public void setUpdateMasterInventoryVO(
			UpdateMasterInventoryVO updateMasterInventoryVO) {
		this.updateMasterInventoryVO = updateMasterInventoryVO;
	}
	
	
	


}
