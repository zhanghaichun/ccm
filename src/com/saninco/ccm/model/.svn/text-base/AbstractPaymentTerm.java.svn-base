package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractPaymentTerm entity provides the base persistence definition of the
 * PaymentTerm entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPaymentTerm implements java.io.Serializable {

	// Fields

	private Integer id;
	private String paymentTermCode;
	private String paymentTermDescription;

	private Set bans = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractPaymentTerm() {
	}

	/** full constructor */
	public AbstractPaymentTerm(String paymentTermCode,
			String paymentTermDescription, Set bans) {
		this.paymentTermCode = paymentTermCode;
		this.paymentTermDescription = paymentTermDescription;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentTermCode() {
		return this.paymentTermCode;
	}

	public void setPaymentTermCode(String paymentTermCode) {
		this.paymentTermCode = paymentTermCode;
	}

	public String getPaymentTermDescription() {
		return this.paymentTermDescription;
	}

	public void setPaymentTermDescription(String paymentTermDescription) {
		this.paymentTermDescription = paymentTermDescription;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractPaymentTerm ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (paymentTermCode != null ? "paymentTermCode="
						+ paymentTermCode + ", " : "")
				+ (paymentTermDescription != null ? "paymentTermDescription="
						+ paymentTermDescription : "") + "]";
	}

}