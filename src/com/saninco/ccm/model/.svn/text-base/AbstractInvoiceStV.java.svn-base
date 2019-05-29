package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoiceSt entity provides the base persistence definition of the
 * InvoiceSt entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoiceStV implements java.io.Serializable {

	// Fields

	private Integer id;
	private String barCode;
	private Double invoiceBalanceForward;
	private Double invoicePreviousBalance;
	private Double invoicePreviousPayment;
	private Double invoiceCurrentDue;
	private Double invoiceTotalDue;
	private Date invoiceDueDate;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	/** default constructor */
	public AbstractInvoiceStV() {
	}

	/** full constructor */
	public AbstractInvoiceStV(String barCode, Double invoicePreviousPayment,Double invoiceBalanceForward,
			Double invoiceCurrentDue, Double invoiceTotalDue,
			Date invoiceDueDate,Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		this.barCode = barCode;
		this.invoiceBalanceForward = invoiceBalanceForward;
		this.invoicePreviousPayment = invoicePreviousPayment;
		this.invoiceDueDate = invoiceDueDate;
		this.invoiceCurrentDue = invoiceCurrentDue;
		this.invoiceTotalDue = invoiceTotalDue;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getInvoiceBalanceForward() {
		return this.invoiceBalanceForward;
	}

	public void setInvoiceBalanceForward(Double invoiceBalanceForward) {
		this.invoiceBalanceForward = invoiceBalanceForward;
	}

	public Double getInvoiceCurrentDue() {
		return this.invoiceCurrentDue;
	}

	public void setInvoiceCurrentDue(Double invoiceCurrentDue) {
		this.invoiceCurrentDue = invoiceCurrentDue;
	}

	public Double getInvoiceTotalDue() {
		return this.invoiceTotalDue;
	}

	public void setInvoiceTotalDue(Double invoiceTotalDue) {
		this.invoiceTotalDue = invoiceTotalDue;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return this.modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String toString() {
		return "AbstractInvoiceSt ["
				+ (barCode != null ? "barCode=" + barCode + ", " : "")
				+ (invoiceDueDate != null ? "invoiceDueDate=" + invoiceDueDate + ", " : "")
				+ (invoiceBalanceForward != null ? "invoiceBalanceForward=" + invoiceBalanceForward + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceBalanceForward != null ? "invoiceBalanceForward="
						+ invoiceBalanceForward + ", " : "")
				+ (invoiceCurrentDue != null ? "invoiceCurrentDue="
						+ invoiceCurrentDue + ", " : "")
				+ (invoiceTotalDue != null ? "invoiceTotalDue="
						+ invoiceTotalDue + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "") + "]";
	}

	public Double getInvoicePreviousBalance() {
		return invoicePreviousBalance;
	}

	public void setInvoicePreviousBalance(Double invoicePreviousBalance) {
		this.invoicePreviousBalance = invoicePreviousBalance;
	}

	public Double getInvoicePreviousPayment() {
		return invoicePreviousPayment;
	}

	public void setInvoicePreviousPayment(Double invoicePreviousPayment) {
		this.invoicePreviousPayment = invoicePreviousPayment;
	}

	public Date getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

}