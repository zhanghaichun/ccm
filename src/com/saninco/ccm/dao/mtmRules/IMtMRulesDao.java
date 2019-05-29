package com.saninco.ccm.dao.mtmRules;

import java.util.List;

import com.saninco.ccm.vo.mtmRules.SearchMtMRulesVO;

public interface IMtMRulesDao {
    
   public List<String> listChargeType();
   public List<String> listKeyField();
   public List<String> listSubProduct();
   
   public List<String> listSummaryVendorName();
   public List<Object[]> listVendorName();

   public long countMtMRulesViewListPageNo(SearchMtMRulesVO searchMtMRulesVO);

   public List<String> listMtMRules(SearchMtMRulesVO searchMtMRulesVO);

   public List<Object[]> downloadMtMRulesToExcel(SearchMtMRulesVO searchMtMRulesVO);

}