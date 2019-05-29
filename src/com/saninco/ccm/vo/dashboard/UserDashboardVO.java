package com.saninco.ccm.vo.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.saninco.ccm.model.bi.BIUserDashboard;
import com.saninco.ccm.model.bi.BIUserDashboardModule;

public class UserDashboardVO {
	
	private Integer id;
	private Integer userId;
	private String dashboardName;
	private String defaultFlag;
	private String createdTimestamp;
	
	private List<UserDashboardModuleVO> userDashboardModuleList = new ArrayList<UserDashboardModuleVO>();
	
	public UserDashboardVO() {
		
	}
	
	public UserDashboardVO(BIUserDashboard ud) {
		
		this.id = ud.getId();
		this.userId = ud.getUserId();
		this.dashboardName = ud.getDashboardName();
		this.defaultFlag = ud.getDefaultFlag();
		this.userDashboardModuleList.clear();
		Iterator<BIUserDashboardModule> it = ud.getBIUserDashboardModules().iterator();
		while(it.hasNext()) {
			UserDashboardModuleVO umv = new UserDashboardModuleVO(it.next());
			this.userDashboardModuleList.add(umv);
		}
	}

	/* Id. */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	/* User id. */
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/* Dashboard name. */
	public String getDashboardName() {
		return dashboardName;
	}
	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}

	/* Default flag. */
	public String getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/* User dashboard module list. */
	public List<UserDashboardModuleVO> getUserDashboardModuleList() {
		return userDashboardModuleList;
	}
	public void setUserDashboardModuleList(
			List<UserDashboardModuleVO> userDashboardModuleList) {
		this.userDashboardModuleList = userDashboardModuleList;
	}

	/* created timestamp. */
	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}
	
}
