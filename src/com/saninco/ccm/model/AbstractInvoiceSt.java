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

public abstract class AbstractInvoiceSt implements java.io.Serializable {

	// Fields

	private Integer id;
	private Vendor vendor;
	private Ban ban;
	private String invoiceNumber;
	private Date invoiceDate;
	private Date invoiceStartDate;
	private Date invoiceEndDate;
	private Date invoiceDueDate;
	private Double invoicePreviousBalance;
	private Double invoiceBalanceForward;
	private Double invoiceCurrentDue;
	private Double invoiceMinDue;
	private Double invoiceTotalDue;
	private String invoiceStatus;
	private String barCode;
	private String staffName;
	private Date statusTimestamp;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String notes;
	private String recActiveFlag;

	private Set invoiceItemSts = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInvoiceSt() {
	}

	/** full constructor */
	public AbstractInvoiceSt(Vendor vendor, Ban ban, String invoiceNumber,
			Date invoiceDate, Date invoiceDueDate,
			Double invoicePreviousBalance, Double invoicePreviousPayment,
			Double invoiceBalanceForward, Double invoiceCurrentDue,
			Double invoiceMinDue, Double invoiceTotalDue, String invoiceStatus,
			String barCode, String staffName, Date statusTimestamp,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set invoiceItemSts) {
		this.vendor = vendor;
		this.ban = ban;
		this.invoiceNumber = invoiceNumber;
		this.invoiceDate = invoiceDate;
		this.invoiceDueDate = invoiceDueDate;
		this.invoicePreviousBalance = invoicePreviousBalance;
		this.invoiceBalanceForward = invoiceBalanceForward;
		this.invoiceCurrentDue = invoiceCurrentDue;
		this.invoiceMinDue = invoiceMinDue;
		this.invoiceTotalDue = invoiceTotalDue;
		this.invoiceStatus = invoiceStatus;
		this.barCode = barCode;
		this.staffName = staffName;
		this.statusTimestamp = statusTimestamp;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.invoiceItemSts = invoiceItemSts;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Ban getBan() {
		return this.ban;
	}

	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getInvoiceDueDate() {
		return this.invoiceDueDate;
	}

	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

	public Double getInvoicePreviousBalance() {
		return this.invoicePreviousBalance;
	}

	public void setInvoicePreviousBalance(Double invoicePreviousBalance) {
		this.invoicePreviousBalance = invoicePreviousBalance;
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

	public Double getInvoiceMinDue() {
		return this.invoiceMinDue;
	}

	public void setInvoiceMinDue(Double invoiceMinDue) {
		this.invoiceMinDue = invoiceMinDue;
	}

	public Double getInvoiceTotalDue() {
		return this.invoiceTotalDue;
	}

	public void setInvoiceTotalDue(Double invoiceTotalDue) {
		this.invoiceTotalDue = invoiceTotalDue;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
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

	public Set getInvoiceItemSts() {
		return this.invoiceItemSts;
	}

	public void setInvoiceItemSts(Set invoiceItemSts) {
		this.invoiceItemSts = invoiceItemSts;
	}

	/**
	 * @return the invoiceStartDate
	 */
	public Date getInvoiceStartDate() {
		return invoiceStartDate;
	}

	/**
	 * @param invoiceStartDate
	 *            the invoiceStartDate to set
	 */
	public void setInvoiceStartDate(Date invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}

	/**
	 * @return the invoiceEndDate
	 */
	public Date getInvoiceEndDate() {
		return invoiceEndDate;
	}

	/**
	 * @param invoiceEndDate
	 *            the invoiceEndDate to set
	 */
	public void setInvoiceEndDate(Date invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String toString() {
		return "AbstractInvoiceSt ["
				+ (ban != null ? "ban=" + ban + ", " : "")
				+ (barCode != null ? "barCode=" + barCode + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceBalanceForward != null ? "invoiceBalanceForward="
						+ invoiceBalanceForward + ", " : "")
				+ (invoiceCurrentDue != null ? "invoiceCurrentDue="
						+ invoiceCurrentDue + ", " : "")
				+ (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", "
						: "")
				+ (invoiceDueDate != null ? "invoiceDueDate=" + invoiceDueDate
						+ ", " : "")
				+ (invoiceEndDate != null ? "invoiceEndDate=" + invoiceEndDate
						+ ", " : "")
				+ (invoiceMinDue != null ? "invoiceMinDue=" + invoiceMinDue
						+ ", " : "")
				+ (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber
						+ ", " : "")
				+ (invoicePreviousBalance != null ? "invoicePreviousBalance="
						+ invoicePreviousBalance + ", " : "")
				+ (invoiceStartDate != null ? "invoiceStartDate="
						+ invoiceStartDate + ", " : "")
				+ (invoiceStatus != null ? "invoiceStatus=" + invoiceStatus
						+ ", " : "")
				+ (invoiceTotalDue != null ? "invoiceTotalDue="
						+ invoiceTotalDue + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (staffName != null ? "staffName=" + staffName + ", " : "")
				+ (statusTimestamp != null ? "statusTimestamp="
						+ statusTimestamp + ", " : "")
				+ (vendor != null ? "vendor=" + vendor : "") + "]";
	}

}