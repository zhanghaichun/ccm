package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAccountType entity provides the base persistence definition of the
 * AccountType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractAccountType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String accountTypeName;
	private Set bans = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractAccountType() {
	}

	/** full constructor */
	public AbstractAccountType(String accountTypeName, Set bans) {
		this.accountTypeName = accountTypeName;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountTypeName() {
		return this.accountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractAccountType ["
				+ (accountTypeName != null ? "accountTypeName="
						+ accountTypeName + ", " : "")
				+ (id != null ? "id=" + id : "") + "]";
	}

}