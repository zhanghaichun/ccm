package com.saninco.ccm.dao.quoteInventory;

import java.util.List;

import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;

public interface IQuoteInventoryDao {

	public List<String> getProviderList();

	public List<String> getProductList();

	public List<String> searchQuoteInventoryList(SearchQuoteVO searchQuoteVO);

	public long getQuoteInventoryViewListPageNo(SearchQuoteVO searchQuoteVO);

	public List<Object[]> downloadQuoteInventoryToExcel(
			SearchQuoteVO searchQuoteVO, List<Integer> ids);
	
	
}