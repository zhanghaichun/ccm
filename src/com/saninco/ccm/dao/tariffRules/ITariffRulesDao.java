package com.saninco.ccm.dao.tariffRules;

import java.util.List;

import com.saninco.ccm.vo.tariffRules.SearchTariffRulesVO;

public interface ITariffRulesDao {
    
   public List<String> listChargeType();
   public List<String> listKeyField();
   public List<String> listSubProduct();
   
   public List<String> listSummaryVendorName();
   public List<Object[]> listVendorName();
   public List<String> listTariffName();

   public long countTariffRulesViewListPageNo(SearchTariffRulesVO searchTariffRulesVO);

   public List<String> listTariffRules(SearchTariffRulesVO searchTariffRulesVO);

   public List<Object[]> downloadTariffRulesToExcel(SearchTariffRulesVO searchTariffRulesVO);

}