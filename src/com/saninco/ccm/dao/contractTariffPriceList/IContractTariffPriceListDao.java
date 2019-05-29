package com.saninco.ccm.dao.contractTariffPriceList;

import java.util.List;

import com.saninco.ccm.vo.contractTariffPriceList.SearchContractTariffPriceListVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;


public interface IContractTariffPriceListDao {

	public long getContractListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);
	public long getContractCountsByExpiryDate(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public List<String> getContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public List<Object[]> downloadContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public long getTariffListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public List<String> getTariffList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public List<Object[]> downloadTariffList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public long getPriceListListPageNo( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public List<String> getPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public List<Object[]> downloadPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO);

	public List<String> getProductComponentList();

	public List<String> getProductList();
	
	public List<String> getRateDiscrepancyList( SearchContractTariffPriceListVO searchContractTariffPriceListVO);
	
	public long getRateDiscrepancyListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO);
	
	public List<Object[]> getRateDiscrepancyDataForExcel( SearchContractTariffPriceListVO searchContractTariffPriceListVO);
	
	public long getRateDiscrepancyDataForExcelPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO);

	
}