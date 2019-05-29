package com.saninco.ccm.service.tariffRules;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.tariffRules.SearchTariffRulesVO;

public interface ITariffRulesService {

    public List<MapEntryVO<String, String>> listChargeType() throws BPLException;

    public List<MapEntryVO<String, String>> listKeyField() throws BPLException;
    
    public List<MapEntryVO<String, String>> listSubProduct() throws BPLException;
    
    
    public List<MapEntryVO<String, String>> listSummaryVendorName() throws BPLException;
    public List<MapEntryVO<String, String>> listVendorName() throws BPLException;
    public List<MapEntryVO<String, String>> listTariffName() throws BPLException;

    public String countTariffRulesViewListPageNo(SearchTariffRulesVO searchTariffRulesVO) throws BPLException;

    public String listTariffRules(SearchTariffRulesVO searchTariffRulesVO) throws BPLException;

    public String downloadTariffRulesToExcel(SearchTariffRulesVO searchTariffRulesVO,String excelDirPath, List<String> titles) throws BPLException;
}