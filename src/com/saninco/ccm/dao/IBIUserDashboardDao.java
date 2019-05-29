package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.bi.BIUserDashboard;

public interface IBIUserDashboardDao {

	public List findByUser(Integer userId, String defaultFlag);
	
	public BIUserDashboard findById(Integer id);
	
	public void save(BIUserDashboard transientInstance);
	
	public void updateDefaultFlagById(Integer id, Integer userId);
	
	public List<Object[]> searchDashboardListInfo( Integer userId, String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords );
	
	public String deleteDashboardListItem(Integer userDashboardId);
	
	public Integer countSameDashboardName(Integer id, String dashboardName, Integer userId);
	
	public Integer getNewDashboardNumber(Integer userId);

	public Integer getTotalRecordsNo(Integer userId);

	public String searchDashboardListByConditions( Integer userId, String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords);
}
