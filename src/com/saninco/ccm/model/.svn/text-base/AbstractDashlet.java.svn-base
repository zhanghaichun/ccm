package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractDashlet entity provides the base persistence definition of the
 * Dashlet entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractDashlet implements java.io.Serializable {

	// Fields

	private Integer id;
	private String dashletName;

	private Set userDashlets = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractDashlet() {
	}

	/** full constructor */
	public AbstractDashlet(String dashletName, Set userDashlets) {
		this.dashletName = dashletName;
		this.userDashlets = userDashlets;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDashletName() {
		return this.dashletName;
	}

	public void setDashletName(String dashletName) {
		this.dashletName = dashletName;
	}

	public Set getUserDashlets() {
		return this.userDashlets;
	}

	public void setUserDashlets(Set userDashlets) {
		this.userDashlets = userDashlets;
	}

	public String toString() {
		return "AbstractDashlet ["
				+ (dashletName != null ? "dashletName=" + dashletName + ", "
						: "") + (id != null ? "id=" + id : "") + "]";
	}



}