package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractPaymentGroup entity provides the base persistence definition of the
 * PaymentGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPaymentGroup implements java.io.Serializable {

	// Fields

	private Integer id;
	private String paymentGroupCode;
	private String paymentGroupDescription;

	private Set bans = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractPaymentGroup() {
	}

	/** full constructor */
	public AbstractPaymentGroup(String paymentGroupCode,
			String paymentGroupDescription, Set bans) {
		this.paymentGroupCode = paymentGroupCode;
		this.paymentGroupDescription = paymentGroupDescription;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentGroupCode() {
		return this.paymentGroupCode;
	}

	public void setPaymentGroupCode(String paymentGroupCode) {
		this.paymentGroupCode = paymentGroupCode;
	}

	public String getPaymentGroupDescription() {
		return this.paymentGroupDescription;
	}

	public void setPaymentGroupDescription(String paymentGroupDescription) {
		this.paymentGroupDescription = paymentGroupDescription;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractPaymentGroup ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (paymentGroupCode != null ? "paymentGroupCode="
						+ paymentGroupCode + ", " : "")
				+ (paymentGroupDescription != null ? "paymentGroupDescription="
						+ paymentGroupDescription : "") + "]";
	}



}