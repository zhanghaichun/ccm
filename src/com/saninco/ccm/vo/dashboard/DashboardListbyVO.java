package com.saninco.ccm.vo.dashboard;

import com.saninco.ccm.model.bi.BIDashboardListby;

public class DashboardListbyVO {
	
	private String key;
	private String name;
	private String listbyTable;
	private Integer limit;
	private String isSelected;
	private String chartShowName;
	
	public DashboardListbyVO() {
		
	}
	
	public DashboardListbyVO(BIDashboardListby bl) {
		this.key = bl.getListbyKey();
		this.name = bl.getListbyName();
		this.listbyTable = bl.getListbyTable();
		this.limit = bl.getListbyLimit();
		this.chartShowName = bl.getChartShowName();
	}
	
	public String getListbyTable() {
		return listbyTable;
	}
	public void setListbyTable(String listbyTable) {
		this.listbyTable = listbyTable;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getChartShowName() {
		return chartShowName;
	}
	public void setChartShowName(String chartShowName) {
		this.chartShowName = chartShowName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
