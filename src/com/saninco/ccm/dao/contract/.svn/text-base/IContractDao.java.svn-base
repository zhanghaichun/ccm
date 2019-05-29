package com.saninco.ccm.dao.contract;

import java.sql.SQLException;
import java.util.List;

import com.saninco.ccm.vo.contract.SearchContractVO;

public interface IContractDao {
	public List<String> getCarrierEntityNameList();
	public List<String> getCarrierTypeList();
	public List<String> getAgreementTypeList();
	public List<String> getProductsServicesList();
	public List<String> getTermCombinedList();

	public long getContractViewListPageNo(SearchContractVO searchContractVO);
	public List<String> searchContractList(SearchContractVO searchContractVO);
	
	
	public long getTerminationPenaltyListNo(SearchContractVO searchContractVO) throws SQLException ;
	public List<String> searchTerminationPenaltyList(SearchContractVO searchContractVO) throws SQLException ;
	
	public List<Object[]> downloadContractToExcel(SearchContractVO searchContractVO);
}