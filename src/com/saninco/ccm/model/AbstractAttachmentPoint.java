package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAttachmentPoint entity provides the base persistence definition of
 * the AttachmentPoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractAttachmentPoint implements java.io.Serializable {

	// Fields

	private Integer id;
	private String tableName;
	private Integer tableIdValue;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	private Set proposals = new HashSet(0);
	private Set disputes = new HashSet(0);
	private Set reconciles = new HashSet(0);
	private Set payments = new HashSet(0);
	private Set transactionHistories = new HashSet(0);
	private Set invoices = new HashSet(0);
	private Set credits = new HashSet(0);
	private Set attachmentFiles = new HashSet(0);
	private Set receivedPayments = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractAttachmentPoint() {
	}

	/** full constructor */
	public AbstractAttachmentPoint(String tableName, Integer tableIdValue,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set proposals,
			Set disputes, Set reconciles, Set payments,
			Set transactionHistories, Set invoices, Set credits,
			Set attachmentFiles, Set receivedPayments) {
		this.tableName = tableName;
		this.tableIdValue = tableIdValue;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.proposals = proposals;
		this.disputes = disputes;
		this.reconciles = reconciles;
		this.payments = payments;
		this.transactionHistories = transactionHistories;
		this.invoices = invoices;
		this.credits = credits;
		this.attachmentFiles = attachmentFiles;
		this.receivedPayments = receivedPayments;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getTableIdValue() {
		return this.tableIdValue;
	}

	public void setTableIdValue(Integer tableIdValue) {
		this.tableIdValue = tableIdValue;
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

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	public Set getDisputes() {
		return this.disputes;
	}

	public void setDisputes(Set disputes) {
		this.disputes = disputes;
	}

	public Set getReconciles() {
		return this.reconciles;
	}

	public void setReconciles(Set reconciles) {
		this.reconciles = reconciles;
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

	public Set getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set invoices) {
		this.invoices = invoices;
	}

	public Set getCredits() {
		return this.credits;
	}

	public void setCredits(Set credits) {
		this.credits = credits;
	}

	public Set getAttachmentFiles() {
		return this.attachmentFiles;
	}

	public void setAttachmentFiles(Set attachmentFiles) {
		this.attachmentFiles = attachmentFiles;
	}

	public Set getReceivedPayments() {
		return this.receivedPayments;
	}

	public void setReceivedPayments(Set receivedPayments) {
		this.receivedPayments = receivedPayments;
	}

	public String toString() {
		return "AbstractAttachmentPoint ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (tableIdValue != null ? "tableIdValue=" + tableIdValue + ", "
						: "")
				+ (tableName != null ? "tableName=" + tableName : "") + "]";
	}



}