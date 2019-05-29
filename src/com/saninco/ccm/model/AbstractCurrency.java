package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractCurrency entity provides the base persistence definition of the
 * Currency entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractCurrency implements java.io.Serializable {

	// Fields

	private Integer id;
	private String currencyName;
	private String currencyDescription;

	private Set bans = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractCurrency() {
	}

	/** full constructor */
	public AbstractCurrency(String currencyName, String currencyDescription,
			Set bans) {
		this.currencyName = currencyName;
		this.currencyDescription = currencyDescription;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurrencyName() {
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCurrencyDescription() {
		return this.currencyDescription;
	}

	public void setCurrencyDescription(String currencyDescription) {
		this.currencyDescription = currencyDescription;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractCurrency ["
				+ (currencyDescription != null ? "currencyDescription="
						+ currencyDescription + ", " : "")
				+ (currencyName != null ? "currencyName=" + currencyName + ", "
						: "") + (id != null ? "id=" + id : "") + "]";
	}

 

}