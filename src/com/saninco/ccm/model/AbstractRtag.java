package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractRtag entity provides the base persistence definition of the Rtag
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractRtag implements java.io.Serializable {

	// Fields

	private Integer id;
	private String rtagName;
	private String rtagColor;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	private Set rtagRoles = new HashSet(0);
	private Set rtagReports = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractRtag() {
	}

	/** full constructor */
	public AbstractRtag(String rtagName, String rtagColor,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set rtagRoles,
			Set rtagReports) {
		this.rtagName = rtagName;
		this.rtagColor = rtagColor;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.rtagRoles = rtagRoles;
		this.rtagReports = rtagReports;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRtagName() {
		return this.rtagName;
	}

	public void setRtagName(String rtagName) {
		this.rtagName = rtagName;
	}

	public String getRtagColor() {
		return this.rtagColor;
	}

	public void setRtagColor(String rtagColor) {
		this.rtagColor = rtagColor;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return this.modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public Set getRtagRoles() {
		return this.rtagRoles;
	}

	public void setRtagRoles(Set rtagRoles) {
		this.rtagRoles = rtagRoles;
	}

	public Set getRtagReports() {
		return this.rtagReports;
	}

	public void setRtagReports(Set rtagReports) {
		this.rtagReports = rtagReports;
	}

	public String toString() {
		return "AbstractRtag ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (rtagColor != null ? "rtagColor=" + rtagColor + ", " : "")
				+ (rtagName != null ? "rtagName=" + rtagName : "") + "]";
	}

}