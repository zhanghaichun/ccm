package com.saninco.ccm.service.inventory;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInventoryDashboardVO;
import com.saninco.ccm.vo.UpdateMasterInventoryVO;

public interface IInventoryDashboardService {
	public String getMasterInventoryListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public String searchMasterInventoryList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public List<MapEntryVO<String, String>> searchMasterInventoryComplete()  throws BPLException;
	
	// Get Master inventory cost type list
	public List<MapEntryVO<String, String>> searchMasterInventoryCostType()  throws BPLException;
	
	public String createMasterInventoryToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException;
	public String createInventoryChangeHistoryToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException;
	public String getInventoryChangeHistoryListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public String getInventoryChangeHistoryList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public String searchMasterInventoryCompleteByFilter(SearchInventoryDashboardVO searchInventoryDashboardVO)throws BPLException;
	public void updateMasterInventory(UpdateMasterInventoryVO mivo) throws BPLException;
	
	public String getMasterInventoryRateListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public String searchMasterInventoryRateList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public List<MapEntryVO<String, String>> searchMasterInventoryRateComplete()  throws BPLException;
	public String createMasterInventoryRateToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException;
	
	public String getInventoryRateChangeHistoryListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public String getInventoryRateChangeHistoryList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException;
	public String createInventoryRateChangeHistoryToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException;
}
