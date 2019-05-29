package com.saninco.ccm.vo.dashboard;

import com.saninco.ccm.model.bi.BIDashboardControl;

public class DashboardControlVO {
	
	private Integer id;
	private String controlName;
	private String controlKey;
	private String globalControlName;
	
	public DashboardControlVO() {
		
	}
	public DashboardControlVO(BIDashboardControl bc) {
		this.id = bc.getId();
		this.controlName = bc.getControlName();
		this.controlKey = bc.getControlKey();
		this.globalControlName = bc.getGlobalControlName();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getControlName() {
		return controlName;
	}
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	public String getControlKey() {
		return controlKey;
	}
	public void setControlKey(String controlKey) {
		this.controlKey = controlKey;
	}
	public String getGlobalControlName() {
		return globalControlName;
	}
	public void setGlobalControlName(String globalControlName) {
		this.globalControlName = globalControlName;
	}
	
	
}
