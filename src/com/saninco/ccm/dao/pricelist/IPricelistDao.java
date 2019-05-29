package com.saninco.ccm.dao.pricelist;

import java.util.List;

import com.saninco.ccm.vo.pricelist.SearchPricelistVO;

public interface IPricelistDao {
	
	public List<String> getServiceTypeList();
	public List<String> getBandList();
	
	public long getPricelistViewListPageNo(SearchPricelistVO searchPricelistVO);
	
	public List<String> searchPricelistList(SearchPricelistVO searchPricelistVO);

	public List<Object[]> downloadPricelistToExcel(SearchPricelistVO searchPricelistVO);
}