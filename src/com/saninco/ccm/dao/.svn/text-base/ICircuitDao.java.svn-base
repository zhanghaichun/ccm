package com.saninco.ccm.dao;

import java.util.List;
 
import com.saninco.ccm.vo.SearchCircuitVO;

public interface ICircuitDao {
	public List<Object[]> searchCircuit(SearchCircuitVO sio, int userId);
	public long getNumberOfCircuits(SearchCircuitVO sio, int userId);
	public List<Object[]> getCircuitDataForExcel(SearchCircuitVO svo, int userId);
	public List<String> getTabs(SearchCircuitVO svo,int userId);
	public List<String> getCircuitDateHyperlink(SearchCircuitVO svo,int userId);
	public List<Object[]> searchVendorCircuit(SearchCircuitVO sio, int userId);
	public long getNumberOfVendorCircuits(SearchCircuitVO sio, int userId);
	public List<Object[]> getVendorCircuitDataForExcel(SearchCircuitVO svo);
	public List<Object[]> getSearchConditioncustomers();
	public List searchReportDownloadNumAndMaxNum(String param);
	public void updateReportDownloadNum(Boolean finishFlag);
}
