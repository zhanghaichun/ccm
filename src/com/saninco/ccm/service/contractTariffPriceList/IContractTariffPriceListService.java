package com.saninco.ccm.service.contractTariffPriceList;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contractTariffPriceList.SearchContractTariffPriceListVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;


public interface IContractTariffPriceListService {

	public String getContractListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException;

	public String getContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException;

	public String downloadContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO,SearchQuoteVO searchQuoteVO, String excelDirPath, List<String> titles) throws BPLException;

	public Long getContractCountsByExpiryDate(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException;

	public String getTariffListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException;

	public String getTariffList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException;

	public String downloadTariffList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO, String excelDirPath, List<String> titles) throws BPLException;

	public String getPriceListListPageNo( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException;

	public String getPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException;

	public String downloadPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO, String excelDirPath, List<String> titles) throws BPLException;

	public List<MapEntryVO<String, String>> getProductMapList() throws BPLException;

	public List<MapEntryVO<String, String>> getProductComponentMapList() throws BPLException;
	
	public String getRateDiscrepancyList(SearchContractTariffPriceListVO searchContractTariffPriceListVO) throws BPLException;
	
	public String getRateDiscrepancyListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO) throws BPLException;
	
	public String createRateDiscrepancyToExcel(SearchContractTariffPriceListVO searchContractTariffPriceListVO,String excelDirPath, List<String> titles) throws BPLException;


	
}