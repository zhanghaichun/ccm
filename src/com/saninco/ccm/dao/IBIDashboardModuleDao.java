package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.bi.BIDashboardModule;

public interface IBIDashboardModuleDao {
	
	public BIDashboardModule findById(java.lang.Integer id);
	
	public List findAll();
}
