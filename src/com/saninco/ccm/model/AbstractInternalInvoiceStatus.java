package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInternalInvoiceStatus entity provides the base persistence definition
 * of the InternalInvoiceStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInternalInvoiceStatus implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private String statusName;

	private Set invoices = new HashSet(0);
	private Set transactionHistories = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInternalInvoiceStatus() {
	}

	/** full constructor */
	public AbstractInternalInvoiceStatus(String statusName, Set invoices,
			Set transactionHistories) {
		this.statusName = statusName;
		this.invoices = invoices;
		this.transactionHistories = transactionHistories;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Set getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set invoices) {
		this.invoices = invoices;
	}

	public Set getTransactionHistories() {
		return this.transactionHistories;
	}

	public void setTransactionHistories(Set transactionHistories) {
		this.transactionHistories = transactionHistories;
	}

	public String toString() {
		return "AbstractInternalInvoiceStatus ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (statusName != null ? "statusName=" + statusName : "") + "]";
	}



}