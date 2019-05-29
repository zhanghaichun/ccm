package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoiceStatus entity provides the base persistence definition of the
 * InvoiceStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoiceStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String invoiceStatusName;

	private Set invoices = new HashSet(0);
	private Set transactionHistories = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInvoiceStatus() {
	}

	/** full constructor */
	public AbstractInvoiceStatus(String invoiceStatusName, Set invoices,
			Set transactionHistories) {
		this.invoiceStatusName = invoiceStatusName;
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

	public String getInvoiceStatusName() {
		return this.invoiceStatusName;
	}

	public void setInvoiceStatusName(String invoiceStatusName) {
		this.invoiceStatusName = invoiceStatusName;
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
		return "AbstractInvoiceStatus ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceStatusName != null ? "invoiceStatusName="
						+ invoiceStatusName : "") + "]";
	}

}