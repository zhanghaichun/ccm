package com.saninco.ccm.dao.rateSearch;

import java.util.List;

import com.saninco.ccm.vo.rateSearch.SearchRateSearchVO;



public interface IRateSearchDao {
    

   public List<String> listSubProduct();
   
   public List<String> listSummaryVendorName();
   public List<String> listVendorName();
   public List<String> listContractOrTariffName(String referenceType);

   public long countRateSearchPageNo(SearchRateSearchVO searchRateSearchVO);
   public long countContractSummaryPageNo(SearchRateSearchVO searchRateSearchVO);

   public List<String> listRateSearch(SearchRateSearchVO searchRateSearchVO);
   public List<String> listContractSummary(SearchRateSearchVO searchRateSearchVO);

   public List<Object[]> downloadRateSearch(SearchRateSearchVO searchRateSearchVO);
   public List<Object[]> downloadContractSummary(SearchRateSearchVO searchRateSearchVO);
   public List<Object[]> downloadRateTemplate(SearchRateSearchVO searchRateSearchVO);
   public List<Object[]> queryKeyFieldListByReferenceType(Integer referenceTypeId);



}