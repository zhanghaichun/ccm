package com.saninco.ccm.service.pricelist;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.pricelist.SearchPricelistVO;

public interface IPricelistService {
	
	public List<MapEntryVO<String, String>> getServiceTypeListMap() throws BPLException;

	public List<MapEntryVO<String, String>> getBandListMap() throws BPLException;

	public String getPricelistViewListPageNo(SearchPricelistVO searchPricelistVO) throws BPLException;
	
	public String searchPricelistList(SearchPricelistVO searchPricelistVO) throws BPLException;

	public String downloadPricelistToExcel(SearchPricelistVO searchPricelistVO,String excelDirPath, List<String> titles) throws BPLException;
}