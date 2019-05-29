package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoiceFormat entity provides the base persistence definition of the
 * InvoiceFormat entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoiceFormat implements java.io.Serializable {

	// Fields

	private Integer id;
	private String invoiceFormatCode;

	private Set bans = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInvoiceFormat() {
	}

	/** full constructor */
	public AbstractInvoiceFormat(String invoiceFormatCode, Set bans) {
		this.invoiceFormatCode = invoiceFormatCode;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvoiceFormatCode() {
		return this.invoiceFormatCode;
	}

	public void setInvoiceFormatCode(String invoiceFormatCode) {
		this.invoiceFormatCode = invoiceFormatCode;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractInvoiceFormat ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceFormatCode != null ? "invoiceFormatCode="
						+ invoiceFormatCode : "") + "]";
	}
}