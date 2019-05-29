package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractPaymentMethod entity provides the base persistence definition of the
 * PaymentMethod entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPaymentMethod implements java.io.Serializable {

	// Fields

	private Integer id;
	private String paymentMethodCode;
	private String paymentMethodDescription;

	private Set bans = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractPaymentMethod() {
	}

	/** full constructor */
	public AbstractPaymentMethod(String paymentMethodCode,
			String paymentMethodDescription, Set bans) {
		this.paymentMethodCode = paymentMethodCode;
		this.paymentMethodDescription = paymentMethodDescription;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentMethodCode() {
		return this.paymentMethodCode;
	}

	public void setPaymentMethodCode(String paymentMethodCode) {
		this.paymentMethodCode = paymentMethodCode;
	}

	public String getPaymentMethodDescription() {
		return this.paymentMethodDescription;
	}

	public void setPaymentMethodDescription(String paymentMethodDescription) {
		this.paymentMethodDescription = paymentMethodDescription;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractPaymentMethod ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (paymentMethodCode != null ? "paymentMethodCode="
						+ paymentMethodCode + ", " : "")
				+ (paymentMethodDescription != null ? "paymentMethodDescription="
						+ paymentMethodDescription
						: "") + "]";
	}



}