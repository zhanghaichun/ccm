package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractCreditStatus entity provides the base persistence definition of the
 * CreditStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractCreditStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String creditStatusName;

	private Set credits = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractCreditStatus() {
	}

	/** full constructor */
	public AbstractCreditStatus(String creditStatusName, Set credits) {
		this.creditStatusName = creditStatusName;
		this.credits = credits;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreditStatusName() {
		return this.creditStatusName;
	}

	public void setCreditStatusName(String creditStatusName) {
		this.creditStatusName = creditStatusName;
	}

	public Set getCredits() {
		return this.credits;
	}

	public void setCredits(Set credits) {
		this.credits = credits;
	}

	public String toString() {
		return "AbstractCreditStatus ["
				+ (creditStatusName != null ? "creditStatusName="
						+ creditStatusName + ", " : "")
				+ (id != null ? "id=" + id : "") + "]";
	}



}