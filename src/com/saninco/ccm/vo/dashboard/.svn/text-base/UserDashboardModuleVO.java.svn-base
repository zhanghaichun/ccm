package com.saninco.ccm.vo.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.saninco.ccm.model.bi.BIDashboardModuleControl;
import com.saninco.ccm.model.bi.BIUserDashboardModule;


public class UserDashboardModuleVO {
	
	private Integer id;
	private String name;
	private String controlData;
	private String series;
	private Integer sortNo;
	private Integer dashboardModuleId;
	private boolean isShowPercent;
	private List<DashboardControlVO> canUseControlList = new ArrayList<DashboardControlVO>();
	
	public UserDashboardModuleVO() {
		
	}
	
	public UserDashboardModuleVO(BIUserDashboardModule dm) {
		this.id = dm.getId();
		this.controlData = dm.getControlData();
		this.sortNo = dm.getSortNo();
		this.name = dm.getName();
		this.isShowPercent = "Y".equals(dm.getShowPercentFlag());
		this.dashboardModuleId = dm.getBIDashboardModule().getId();
		canUseControlList.clear();
		Iterator<BIDashboardModuleControl> it = dm.getBIDashboardModule().getBIDashboardModuleControls().iterator();
		while(it.hasNext()) {
			DashboardControlVO dv = new DashboardControlVO(it.next().getBIDashboardControl());
			this.canUseControlList.add(dv);
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getControlData() {
		return controlData;
	}
	public void setControlData(String controlData) {
		this.controlData = controlData;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	
	public List<DashboardControlVO> getCanUseControlList() {
		return canUseControlList;
	}

	public void setCanUseControlList(List<DashboardControlVO> canUseControlList) {
		this.canUseControlList = canUseControlList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isShowPercent() {
		return isShowPercent;
	}

	public void setShowPercent(boolean isShowPercent) {
		this.isShowPercent = isShowPercent;
	}

	public Integer getDashboardModuleId() {
		return dashboardModuleId;
	}

	public void setDashboardModuleId(Integer dashboardModuleId) {
		this.dashboardModuleId = dashboardModuleId;
	}
	
	

}
