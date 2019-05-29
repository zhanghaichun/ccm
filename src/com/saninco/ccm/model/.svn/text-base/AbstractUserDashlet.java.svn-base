package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractUserDashlet entity provides the base persistence definition of the
 * UserDashlet entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserDashlet implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Dashlet dashlet;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractUserDashlet() {
	}

	/** minimal constructor */
	public AbstractUserDashlet(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractUserDashlet(Integer id, User user, Dashlet dashlet,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		this.id = id;
		this.user = user;
		this.dashlet = dashlet;
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

	public Dashlet getDashlet() {
		return this.dashlet;
	}

	public void setDashlet(Dashlet dashlet) {
		this.dashlet = dashlet;
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
		builder.append("AbstractUserDashlet [");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (dashlet != null)
			builder.append("dashlet=").append(dashlet).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (user != null)
			builder.append("user=").append(user);
		builder.append("]");
		return builder.toString();
	}

}