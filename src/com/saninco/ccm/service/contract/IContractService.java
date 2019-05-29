package com.saninco.ccm.service.contract;

import java.sql.SQLException;
import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contract.SearchContractVO;

public interface IContractService {

	public List<MapEntryVO<String, String>> getCarrierEntityNameListMap() throws BPLException;

	public List<MapEntryVO<String, String>> getCarrierTypeListMap() throws BPLException;
	public List<MapEntryVO<String, String>> getAgreementTypeListMap() throws BPLException;
	public List<MapEntryVO<String, String>> getProductsServicesListMap() throws BPLException;
	public List<MapEntryVO<String, String>> getTermCombinedListMap() throws BPLException;

	public String getContractViewListPageNo(SearchContractVO searchContractVO) throws BPLException;
	public String searchContractList(SearchContractVO searchContractVO) throws BPLException;
	
	public String getTerminationPenaltyListNo(SearchContractVO searchContractVO) throws BPLException;
	public String searchTerminationPenaltyList(SearchContractVO searchContractVO) throws BPLException;

	public String downloadContractToExcel(SearchContractVO searchContractVO,String excelDirPath, List<String> titles) throws BPLException;
}