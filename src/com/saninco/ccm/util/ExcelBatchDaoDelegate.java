package com.saninco.ccm.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ExcelBatchDaoDelegate {

	public void callInventoryVerification(String batchNo) throws SQLException;
	public List<Map<String,Object>> queryTmpInventoryError(String batchNo);
	public void insertInventoryTemporary(List<Map<String, String>> dataList,List<String> columnList,String batchNo) throws SQLException;
}
