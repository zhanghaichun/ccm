package com.saninco.ccm.circuitold.service;

import java.util.List;

import com.saninco.ccm.circuitold.model.SearchCircuitVO;
import com.saninco.ccm.exception.BPLException;


public interface ICircuitService {
	public String searchCircuit(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String getCircuitsSearchTotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String getDataToExcel(SearchCircuitVO svo,String sheetName, String excelDirPath, List<String> titles) throws BPLException ;
	public String getAllDataToExcel(SearchCircuitVO svo, String excelDirPath, List<String> titles) throws BPLException;
	public String getTabs(SearchCircuitVO svo)throws BPLException;
	public String getCircuitDateHyperlink(SearchCircuitVO svo)throws BPLException;
}
