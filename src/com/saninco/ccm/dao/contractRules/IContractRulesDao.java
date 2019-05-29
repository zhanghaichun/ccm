package com.saninco.ccm.dao.contractRules;

import java.util.List;

import com.saninco.ccm.vo.contractRules.SearchContractRulesVO;

public interface IContractRulesDao {
	
    public List<String> listSummaryVendorName();
    public List<String> listKeyField();
    public List<String> listSubProduct();
    public List<String> listContractName();

    public long countContractRulesViewListPageNo(SearchContractRulesVO searchContractRulesVO);

    public List<String> listContractRules(SearchContractRulesVO searchContractRulesVO);

    public List<Object[]> downloadContractRulesToExcel(SearchContractRulesVO searchContractRulesVO);

}