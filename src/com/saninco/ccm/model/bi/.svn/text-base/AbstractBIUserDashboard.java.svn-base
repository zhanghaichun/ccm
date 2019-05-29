package com.saninco.ccm.model.bi;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBIUserDashboard entity provides the base persistence definition of
 * the BIUserDashboard entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBIUserDashboard implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String dashboardName;
	private String defaultFlag;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private Set BIUserDashboardModules = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractBIUserDashboard() {
	}

	/** full constructor */
	public AbstractBIUserDashboard(Integer userId, String dashboardName,
			String defaultFlag, Date createdTimestamp, Integer createdBy, Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag, Set BIUserDashboardModules) {
		this.userId = userId;
		this.dashboardName = dashboardName;
		this.defaultFlag = defaultFlag;
		this.BIUserDashboardModules = BIUserDashboardModules;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getDashboardName() {
		return this.dashboardName;
	}

	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName;
	}

	public String getDefaultFlag() {
		return this.defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public Set getBIUserDashboardModules() {
		return this.BIUserDashboardModules;
	}

	public void setBIUserDashboardModules(Set BIUserDashboardModules) {
		this.BIUserDashboardModules = BIUserDashboardModules;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

}