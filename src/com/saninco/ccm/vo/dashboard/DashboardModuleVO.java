package com.saninco.ccm.vo.dashboard;

import com.saninco.ccm.model.bi.BIDashboardModule;

public class DashboardModuleVO {
	
	private Integer id;
	private String moduleName;
	
	public DashboardModuleVO() {
		
	}
	public DashboardModuleVO(BIDashboardModule b) {

		this.id = b.getId();
		this.moduleName = b.getModuleName();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
}
