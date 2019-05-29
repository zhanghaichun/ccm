package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.vo.SearchVendorInventoryVO;

public interface IVendorInventoryDao {
	public List<Object[]> getConditionProductList();
	public List<Object[]> getConditionAccessList();
	public List<Object[]> getConditionBandwidthList();
	public List<Object[]> getConditionQosList();
	public List<Object[]> getConditionCountryList();	
	public List<Object[]> getConditionProvinceList(int cId);
	public List<Object[]> getListByCityId(int cId);
	public List<Object[]> getConditionTermList();
	public List<Object[]> searchVendorInventory(SearchVendorInventoryVO sv);
	public long getNumberOfVendorInventory(SearchVendorInventoryVO sv);
	public List<Object[]> getVendorInventoryDataForExcel(SearchVendorInventoryVO sv);
	public List<Map> getVendorInventoryDataForLetter(SearchVendorInventoryVO sv);
}
