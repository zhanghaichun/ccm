package com.saninco.ccm.model.bi;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBIDashboardModule entity provides the base persistence definition of
 * the BIDashboardModule entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBIDashboardModule implements java.io.Serializable {

	// Fields

	private Integer id;
	private String moduleName;
	private Set BIDashboardModuleControls = new HashSet(0);
	private Set BIUserDashboardModules = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractBIDashboardModule() {
	}

	/** full constructor */
	public AbstractBIDashboardModule(String moduleName,
			Set BIDashboardModuleControls, Set BIUserDashboardModules) {
		this.moduleName = moduleName;
		this.BIDashboardModuleControls = BIDashboardModuleControls;
		this.BIUserDashboardModules = BIUserDashboardModules;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Set getBIDashboardModuleControls() {
		return this.BIDashboardModuleControls;
	}

	public void setBIDashboardModuleControls(Set BIDashboardModuleControls) {
		this.BIDashboardModuleControls = BIDashboardModuleControls;
	}

	public Set getBIUserDashboardModules() {
		return this.BIUserDashboardModules;
	}

	public void setBIUserDashboardModules(Set BIUserDashboardModules) {
		this.BIUserDashboardModules = BIUserDashboardModules;
	}

}