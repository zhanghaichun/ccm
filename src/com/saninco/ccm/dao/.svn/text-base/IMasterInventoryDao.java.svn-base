package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.vo.SearchInventoryDashboardVO;
import com.saninco.ccm.vo.UpdateMasterInventoryVO;

public interface IMasterInventoryDao {
	
	public List<Object[]> getSearchConditionProduct();
	public List<Object[]> searchMasterInventoryComplete();
	
	// Get Master inventory cost type list
	public List<Object[]> searchMasterInventoryCostType();
	
	public List<Map<String,Object>> searchMasterInventoryCompleteByFilter(SearchInventoryDashboardVO searchInventoryDashboardVO);
	
	public long getMasterInventoryListPageNo(SearchInventoryDashboardVO sdo);
	public List<Map<String,Object>> searchMasterInventoryList(SearchInventoryDashboardVO sdo);
	
	public long getInventoryChangeHistoryListPageNo(SearchInventoryDashboardVO sdo);
	public List<Map<String,Object>> getInventoryChangeHistoryList(SearchInventoryDashboardVO sdo);
	
	public List<Object[]> getInventoryChangeHistoryDataForExcel(SearchInventoryDashboardVO sdo);
	public long getInventoryChangeHistoryDataForExcelPageNo(SearchInventoryDashboardVO sdo);

	
	public void callMasterInventoryVerification(String batchNo) throws SQLException;
	public List<Map<String,Object>> queryTmpMasterInventoryError();
	public void insertMasterInventoryTemporary(List<Map<String,String>> dataList,List<String> columnList,String batchNo) throws SQLException;


	public List<Object[]> getMasterInventoryDataForExcel(SearchInventoryDashboardVO sdo);
	public long getMasterInventoryDataForExcelPageNo(SearchInventoryDashboardVO sdo);
	
	public void updateMasterInventory(UpdateMasterInventoryVO mivo) throws SQLException;
	
	public long getMasterInventoryRateListPageNo(SearchInventoryDashboardVO sdo);
	public List<Map<String,Object>> searchMasterInventoryRateList(SearchInventoryDashboardVO sdo);
	public List<Object[]> searchMasterInventoryRateComplete();
	
	public long getMasterInventoryRateDataForExcelPageNo(SearchInventoryDashboardVO sdo);
	public List<Object[]> getMasterInventoryRateDataForExcel(SearchInventoryDashboardVO sdo);

	public long getInventoryRateChangeHistoryListPageNo(SearchInventoryDashboardVO sdo);
	public List<Map<String,Object>> getInventoryRateChangeHistoryList(SearchInventoryDashboardVO sdo);
	

	public List<Object[]> getInventoryRateChangeHistoryDataForExcel(SearchInventoryDashboardVO sdo);
	public long getInventoryRateChangeHistoryDataForExcelPageNo(SearchInventoryDashboardVO sdo);
}
