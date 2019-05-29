package com.saninco.ccm.service.quoteInventory;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;

public interface IQuoteInventoryService {

	public List<MapEntryVO<String, String>> getProviderListMap() throws BPLException;

	public List<MapEntryVO<String, String>> getProductListMap() throws BPLException;

	public String searchQuoteInventoryList(SearchQuoteVO searchQuoteVO) throws BPLException;

	public String getQuoteInventoryViewListPageNo(SearchQuoteVO searchQuoteVO) throws BPLException;

	public String downloadQuoteInventoryToExcel(SearchQuoteVO searchQuoteVO,
			String excelDirPath, List<String> titles, List<Integer> ids) throws BPLException;

	public String downloadQuoteInventoryTemplateToExcel(String excelDirPath, List<String> titles) throws BPLException;

	
}