package com.saninco.ccm.service.contractRules;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contractRules.SearchContractRulesVO;

public interface IContractRulesService {

    public List<MapEntryVO<String, String>> listSummaryVendorName() throws BPLException;

    public List<MapEntryVO<String, String>> listKeyField() throws BPLException;
    public List<MapEntryVO<String, String>> listSubProduct() throws BPLException;
    public List<MapEntryVO<String, String>> listContractName() throws BPLException;

    public String countContractRulesViewListPageNo(SearchContractRulesVO searchContractRulesVO) throws BPLException;

    public String listContractRules(SearchContractRulesVO searchContractRulesVO) throws BPLException;

    public String downloadContractRulesToExcel(SearchContractRulesVO searchContractRulesVO,String excelDirPath, List<String> titles) throws BPLException;
}