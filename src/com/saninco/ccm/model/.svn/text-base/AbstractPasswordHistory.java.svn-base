package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractPasswordHistory entity provides the base persistence definition of
 * the PasswordHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPasswordHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String password;
	private Date createdTimestamp;
	private Integer createdBy;

	// Constructors

	/** default constructor */
	public AbstractPasswordHistory() {
	}

	/** minimal constructor */
	public AbstractPasswordHistory(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractPasswordHistory(Integer id, User user, String password,
			Date createdTimestamp, Integer createdBy) {
		this.id = id;
		this.user = user;
		this.password = password;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String toString() {
		return "AbstractPasswordHistory ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (user != null ? "user=" + user : "") + "]";
	}
}