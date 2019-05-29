package com.saninco.ccm.service.vendor;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchVendorInventoryVO;

public interface IVendorInventoryService {
	public List<MapEntryVO<String, String>> getConditionProductList() throws BPLException;
	public List<MapEntryVO<String, String>> getConditionAccessList() throws BPLException;
	public List<MapEntryVO<String, String>> getConditionBandwidthList() throws BPLException;
	public List<MapEntryVO<String, String>> getConditionQosList() throws BPLException;
	public List<MapEntryVO<String, String>> getConditionCountryList() throws BPLException;
	public List<MapEntryVO<String, String>> getConditionTermList() throws BPLException;
	public String getInventorySearchTotalPageNo(SearchVendorInventoryVO sv) throws BPLException;
	public String searchVendorInventory(SearchVendorInventoryVO sv) throws BPLException;
	public String createVendorInventoryToExcel(SearchVendorInventoryVO sv,String excelDirPath, List<String> titles) throws BPLException;
	public String getListByProvinceId(int cId) throws BPLException;
	public String getListByCityId(int pId) throws BPLException;
	public String createVendorInventoryToLetter(SearchVendorInventoryVO sv,String pdfDirPath, String jasperPath, List<String> tem) throws BPLException;
}
