package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractPaymentStatus entity provides the base persistence definition of the
 * PaymentStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPaymentStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String paymentStatusName;

	private Set payments = new HashSet(0);
	private Set transactionHistories = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractPaymentStatus() {
	}

	/** full constructor */
	public AbstractPaymentStatus(String paymentStatusName, Set payments,
			Set transactionHistories) {
		this.paymentStatusName = paymentStatusName;
		this.payments = payments;
		this.transactionHistories = transactionHistories;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaymentStatusName() {
		return this.paymentStatusName;
	}

	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}

	public Set getPayments() {
		return this.payments;
	}

	public void setPayments(Set payments) {
		this.payments = payments;
	}

	public Set getTransactionHistories() {
		return this.transactionHistories;
	}

	public void setTransactionHistories(Set transactionHistories) {
		this.transactionHistories = transactionHistories;
	}


	public String toString() {
		return "AbstractPaymentStatus ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (paymentStatusName != null ? "paymentStatusName="
						+ paymentStatusName : "") + "]";
	}



}