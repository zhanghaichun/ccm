package com.saninco.ccm.service.rateSearch;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.rateSearch.SearchRateSearchVO;


public interface IRateSearchService {


    
    public List<MapEntryVO<String, String>> listSubProduct() throws BPLException;
    
    
    public List<MapEntryVO<String, String>> listSummaryVendorName() throws BPLException;
    public List<MapEntryVO<String, String>> listVendorName() throws BPLException;
    public List<MapEntryVO<String, String>> listContractOrTariffName(String referenceType) throws BPLException;

    public String countRateSearchPageNo(SearchRateSearchVO searchRateSearchVO) throws BPLException;

    public String countContractSummaryPageNo(SearchRateSearchVO searchRateSearchVO) throws BPLException;


    public String listRateSearch(SearchRateSearchVO searchRateSearchVO) throws BPLException;
    public String listContractSummary(SearchRateSearchVO searchRateSearchVO) throws BPLException;
    public String downloadRateSearch(SearchRateSearchVO searchRateSearchVO,String excelDirPath, List<String> titles) throws BPLException;
    public String downloadContractSummary(SearchRateSearchVO searchRateSearchVO,String excelDirPath, List<String> titles) throws BPLException;
    public Map<String,String> uploadRateInventory(File quoteFile,String fileName,String errorExcelDirPath);
    public String downloadRateTemplate(SearchRateSearchVO searchRateSearchVO,String excelDirPath) throws BPLException;
    public String downloadRateBlankTemplate(String referenceType,String excelDirPath) throws BPLException;

}