package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBanStatus entity provides the base persistence definition of the
 * BanStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractBanInactiveReason implements java.io.Serializable {

	// Fields

	private Integer id;
	private String description;
	private Set bans = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractBanInactiveReason() {
	}

	/** full constructor */
	public AbstractBanInactiveReason(String description, Set bans) {
		this.description = description;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractBanStatus ["
				+ (description != null ? "description=" + description
						+ ", " : "") + (id != null ? "id=" + id : "") + "]";
	}

}