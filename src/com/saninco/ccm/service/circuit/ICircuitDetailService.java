package com.saninco.ccm.service.circuit;

import java.util.HashMap;
import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Contract;
import com.saninco.ccm.model.Tariff;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.model.VendorCircuit;
import com.saninco.ccm.vo.SearchCircuitVO;


public interface ICircuitDetailService {
	public VendorCircuit findVendorCircuitById(Integer vendorCircuitId) throws BPLException;
	public VendorCircuit findCircuitDetail(String proposalId) throws BPLException;
	public String searchSCOATotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String searchSCOAList(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String searchCostTotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String searchCostList(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String searchTariffTotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String doSearchMappingPageNo(SearchCircuitVO searchCircuitVO) throws BPLException;	
	public String searchTariffList(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String doSearchMappingList(SearchCircuitVO searchCircuitVO) throws BPLException;
	public String createScoaToExcel(SearchCircuitVO svo,String excelDirPath, List<String> titles) throws BPLException;
	public String createCostToExcel(SearchCircuitVO svo,String excelDirPath, List<String> titles) throws BPLException;
	public String createTariffToExcel(SearchCircuitVO svo,String excelDirPath, List<String> titles) throws BPLException;
	public void deleteTariffOfVendorCircuit(TariffLink tariffLink)throws BPLException;
	public List<HashMap<String, String>> doSearchLogicalPathList(Integer vendorCircuitId) throws BPLException;
	public Tariff doSearchTariff(Integer id)throws BPLException;
	public Contract SearchContract(Integer id)throws BPLException;
	
}
