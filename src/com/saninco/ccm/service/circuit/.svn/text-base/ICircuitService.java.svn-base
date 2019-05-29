package com.saninco.ccm.service.circuit;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.SearchCircuitVO;


public interface ICircuitService {
	public String searchCircuit(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String getCircuitsSearchTotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String getDataToExcel(SearchCircuitVO svo,String sheetName, String excelDirPath, List<String> titles) throws BPLException ;
	public String getAllDataToExcel(SearchCircuitVO svo, String excelDirPath, List<String> titles) throws BPLException;
	public String getTabs(SearchCircuitVO svo)throws BPLException;
	public String getCircuitDateHyperlink(SearchCircuitVO svo)throws BPLException;
	public String searchVendorCircuit(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String getVendorCircuitSearchTotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String createVendorCircuitToExcel(SearchCircuitVO svo, String excelDirPath, List<String> titles) throws BPLException;
	public List searchReportDownloadNumAndMaxNum(String param) throws BPLException;
	public void updateReportDownloadNum(Boolean finishFlag) throws BPLException;
}
