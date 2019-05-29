package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.Contract;
import com.saninco.ccm.model.Tariff;
import com.saninco.ccm.model.VendorCircuit;
import com.saninco.ccm.vo.SearchCircuitVO;

/**
 * @author xin.huang
 *
 */
public interface ICircuitDetailDao {
	
	public VendorCircuit findVendorCircuitById(Integer vendorCircuitId);
	public Integer findVendorCircuitId(String proposalId);
	public long searchSCOATotalPageNo(SearchCircuitVO searchCircuitVO);
	public List<Object[]> searchSCOAList(SearchCircuitVO searchCircuitVO);
	public long searchCostTotalPageNo(SearchCircuitVO searchCircuitVO);
	public List<Object[]> searchCostList(SearchCircuitVO searchCircuitVO);
	public long searchTariffTotalPageNo(SearchCircuitVO searchCircuitVO);
	public long doSearchMappingPageNo(SearchCircuitVO searchCircuitVO);
	public List<Object[]> searchTariffList(SearchCircuitVO searchCircuitVO);
	public List<Object[]> doSearchMappingList(SearchCircuitVO searchCircuitVO);
	public void deleteAttachmentFile(AttachmentFile attachmentFile);
	public List<Object[]> getSCOADataForExcel(SearchCircuitVO svo);
	public List<Object[]> getCostDataForExcel(SearchCircuitVO svo);
	public List<Object[]> getTariffDataForExcel(SearchCircuitVO svo);
	public List<Object[]> doSearchLogicalPathList(Integer vendorCircuitId);
	public Tariff doSearchTariff(Integer id);
	public Contract SearchContract(Integer id);
}
