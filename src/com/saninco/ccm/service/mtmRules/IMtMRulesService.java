package com.saninco.ccm.service.mtmRules;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.mtmRules.SearchMtMRulesVO;

public interface IMtMRulesService {

    public List<MapEntryVO<String, String>> listChargeType() throws BPLException;

    public List<MapEntryVO<String, String>> listKeyField() throws BPLException;
    
    public List<MapEntryVO<String, String>> listSubProduct() throws BPLException;
    
    
    public List<MapEntryVO<String, String>> listSummaryVendorName() throws BPLException;
    public List<MapEntryVO<String, String>> listVendorName() throws BPLException;

    public String countMtMRulesViewListPageNo(SearchMtMRulesVO searchMtMRulesVO) throws BPLException;

    public String listMtMRules(SearchMtMRulesVO searchMtMRulesVO) throws BPLException;

    public String downloadMtMRulesToExcel(SearchMtMRulesVO searchMtMRulesVO,String excelDirPath, List<String> titles) throws BPLException;
}