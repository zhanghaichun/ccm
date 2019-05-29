package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.bi.BIDashboardListby;

public interface IBIDashboardListbyDao {
	
	public List<BIDashboardListby> getListbyByMinNumber(String dashboardControlKey, Integer minNumber);
	public List<BIDashboardListby> getListbyByControlKey(String dashboardControlKey);
	public List<BIDashboardListby> getAllNoConditionListby();
}
