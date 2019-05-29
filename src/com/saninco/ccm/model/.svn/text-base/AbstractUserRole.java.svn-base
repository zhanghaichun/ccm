package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractUserRole entity provides the base persistence definition of the
 * UserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserRole implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Role role;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractUserRole() {
	}

	/** minimal constructor */
	public AbstractUserRole(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractUserRole(Integer id, User user, Role role,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		this.id = id;
		this.user = user;
		this.role = role;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractUserRole [");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (role != null)
			builder.append("role=").append(role).append(", ");
		if (user != null)
			builder.append("user=").append(user);
		builder.append("]");
		return builder.toString();
	}

}