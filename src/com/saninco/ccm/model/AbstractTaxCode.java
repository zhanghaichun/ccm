package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractTaxCode entity provides the base persistence definition of the
 * TaxCode entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTaxCode implements java.io.Serializable {

	// Fields

	private Integer id;
	private String taxCode;
	private String taxCodeDescription;
	private Double taxRate;

	private Set paymentDetails = new HashSet(0);
	private Set invoiceItemSts = new HashSet(0);
	private Set invoiceItems = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractTaxCode() {
	}

	/** full constructor */
	public AbstractTaxCode(String taxCode, String taxCodeDescription,
			Double taxRate, Set paymentDetails, Set invoiceItemSts,
			Set invoiceItems) {
		this.taxCode = taxCode;
		this.taxCodeDescription = taxCodeDescription;
		this.taxRate = taxRate;
		this.paymentDetails = paymentDetails;
		this.invoiceItemSts = invoiceItemSts;
		this.invoiceItems = invoiceItems;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaxCode() {
		return this.taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getTaxCodeDescription() {
		return this.taxCodeDescription;
	}

	public void setTaxCodeDescription(String taxCodeDescription) {
		this.taxCodeDescription = taxCodeDescription;
	}

	public Double getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Set getPaymentDetails() {
		return this.paymentDetails;
	}

	public void setPaymentDetails(Set paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public Set getInvoiceItemSts() {
		return this.invoiceItemSts;
	}

	public void setInvoiceItemSts(Set invoiceItemSts) {
		this.invoiceItemSts = invoiceItemSts;
	}

	public Set getInvoiceItems() {
		return this.invoiceItems;
	}

	public void setInvoiceItems(Set invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	public String toString() {
		return "AbstractTaxCode ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (taxCode != null ? "taxCode=" + taxCode + ", " : "")
				+ (taxCodeDescription != null ? "taxCodeDescription="
						+ taxCodeDescription + ", " : "")
				+ (taxRate != null ? "taxRate=" + taxRate : "") + "]";
	}

}