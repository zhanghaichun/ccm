package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractRole entity provides the base persistence definition of the Role
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private String roleName;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	private Set rtagRoles = new HashSet(0);
	private Set userRoles = new HashSet(0);
	private Set roleFunctions = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractRole() {
	}

	/** full constructor */
	public AbstractRole(String roleName, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, Set rtagRoles, Set userRoles,
			Set roleFunctions) {
		this.roleName = roleName;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.rtagRoles = rtagRoles;
		this.userRoles = userRoles;
		this.roleFunctions = roleFunctions;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Set getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set userRoles) {
		this.userRoles = userRoles;
	}

	public Set getRoleFunctions() {
		return this.roleFunctions;
	}

	public void setRoleFunctions(Set roleFunctions) {
		this.roleFunctions = roleFunctions;
	}

	public String toString() {
		return "AbstractRole ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (roleName != null ? "roleName=" + roleName : "") + "]";
	}

}