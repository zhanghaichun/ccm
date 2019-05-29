package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBanStatus entity provides the base persistence definition of the
 * BanStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractBanStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String banStatusName;
	private Set bans = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractBanStatus() {
	}

	/** full constructor */
	public AbstractBanStatus(String banStatusName, Set bans) {
		this.banStatusName = banStatusName;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBanStatusName() {
		return this.banStatusName;
	}

	public void setBanStatusName(String banStatusName) {
		this.banStatusName = banStatusName;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractBanStatus ["
				+ (banStatusName != null ? "banStatusName=" + banStatusName
						+ ", " : "") + (id != null ? "id=" + id : "") + "]";
	}

}