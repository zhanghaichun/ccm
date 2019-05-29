package com.saninco.ccm.model.bi;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBIDashboardControl entity provides the base persistence definition of
 * the BIDashboardControl entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBIDashboardControl implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private String controlName;
	private String controlKey;
	private String globalControlName;
	private String globalFlag;
	private Set BIDashboardModuleControls = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractBIDashboardControl() {
	}

	/** full constructor */
	public AbstractBIDashboardControl(String controlName, String controlKey, String globalControlName, String globalFlag,
			Set BIDashboardModuleControls) {
		this.controlName = controlName;
		this.controlKey = controlKey;
		this.globalControlName = globalControlName;
		this.globalFlag = globalFlag;
		this.BIDashboardModuleControls = BIDashboardModuleControls;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getControlName() {
		return this.controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public String getControlKey() {
		return this.controlKey;
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

	public String getGlobalFlag() {
		return globalFlag;
	}

	public void setGlobalFlag(String globalFlag) {
		this.globalFlag = globalFlag;
	}

	public Set getBIDashboardModuleControls() {
		return this.BIDashboardModuleControls;
	}

	public void setBIDashboardModuleControls(Set BIDashboardModuleControls) {
		this.BIDashboardModuleControls = BIDashboardModuleControls;
	}

}