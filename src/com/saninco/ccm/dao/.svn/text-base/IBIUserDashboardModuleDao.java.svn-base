package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.model.bi.BIUserDashboardModule;
import com.saninco.ccm.vo.dashboard.DashboardChartDataVO;
import com.saninco.ccm.vo.dashboard.DashboardListbyVO;

public interface IBIUserDashboardModuleDao {
	
	public BIUserDashboardModule findById(Integer id);
	
	public double searchTotalChartData(Map<String, String[]> c, DashboardListbyVO listBy, Integer moduleId);
	
	public List<DashboardChartDataVO> searchChartData(Map<String, String[]> c, DashboardListbyVO bl, Integer moduleId, double total);
	
	public void save(BIUserDashboardModule transientInstance);
	
	public void delete(BIUserDashboardModule persistentInstance);
	
	public Integer getNextSortNo(Integer userDashboardId);
	
	public boolean isCanUseCondition(Integer userDashboardModuleId, String controlKey);
	
	public void updateSort(Integer userDashboardId, Integer sortNo);
	
	public List findAllQuoteAging();
	
	public List findAllQuoteIssuedStatus();
	
	public List findAllQuoteNetType();
	
	public List findAllQuoteProduct();
	
	public List findAllQuoteVendor();
	
	public List findAllQuoteVendorResponseStatus();
	
	public void updateSortNoByDashboardAndSortNo(Integer userDashboardId, Integer sortNo, Integer newSortNo);
	
	public Integer countSameUserDashboardModuleName(String name, Integer userDashboardId, Integer id);
	
	public Integer getNewUserDashboardModuleNumber(Integer userDashboardId, String newUserDashboardModuleName);
}
