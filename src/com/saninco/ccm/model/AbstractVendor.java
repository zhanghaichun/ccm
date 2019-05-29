package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractVendor entity provides the base persistence definition of the Vendor
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractVendor implements java.io.Serializable {

	// Fields

	private Integer id;
	private VendorStatus vendorStatus;
	private String vendorName;
	private String channel;
	private Double lpcRate;
	private Integer vendorPrimaryContactId;
	private Integer vendorDisputeContactId;
	private Integer vendorPaymentContactId;
	private Integer vendorTariffContactId;
	private Integer vendorBillingContactId;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String summaryVendorName;
	private String vendorAcronym;
	private Date statusTimestamp;

	private Set userPreviledges = new HashSet(0);
	private Set invoices = new HashSet(0);
	private Set contacts = new HashSet(0);
	private Set invoiceSts = new HashSet(0);
	private Set inventories = new HashSet(0);
	private Set credits = new HashSet(0);
	private Set receivedPayments = new HashSet(0);
	private Set bans = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractVendor() {
	}

	/** full constructor */
	public AbstractVendor(VendorStatus vendorStatus, String channel, Double lpcRate , String vendorName,
			Integer vendorPrimaryContactId, Integer vendorDisputeContactId,
			Integer vendorPaymentContactId, Integer vendorTariffContactId,
			Integer vendorBillingContactId, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, String summaryVendorName,
			String vendorAcronym, Date statusTimestamp, String vendorHistId,
			Integer histVendorId, Set userPreviledges, 
			Set invoices, Set contacts, Set invoiceSts, Set inventories,
			Set credits, Set receivedPayments, Set bans) {
		this.vendorStatus = vendorStatus;
		this.channel = channel;
		this.lpcRate = lpcRate;
		this.vendorName = vendorName;
		this.vendorPrimaryContactId = vendorPrimaryContactId;
		this.vendorDisputeContactId = vendorDisputeContactId;
		this.vendorPaymentContactId = vendorPaymentContactId;
		this.vendorTariffContactId = vendorTariffContactId;
		this.vendorBillingContactId = vendorBillingContactId;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.summaryVendorName = summaryVendorName;
		this.vendorAcronym = vendorAcronym;
		this.statusTimestamp = statusTimestamp;
		this.userPreviledges = userPreviledges; 
		this.invoices = invoices;
		this.contacts = contacts;
		this.invoiceSts = invoiceSts;
		this.inventories = inventories;
		this.credits = credits;
		this.receivedPayments = receivedPayments;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VendorStatus getVendorStatus() {
		return this.vendorStatus;
	}

	public void setVendorStatus(VendorStatus vendorStatus) {
		this.vendorStatus = vendorStatus;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getVendorPrimaryContactId() {
		return this.vendorPrimaryContactId;
	}

	public void setVendorPrimaryContactId(Integer vendorPrimaryContactId) {
		this.vendorPrimaryContactId = vendorPrimaryContactId;
	}

	public Integer getVendorDisputeContactId() {
		return this.vendorDisputeContactId;
	}

	public void setVendorDisputeContactId(Integer vendorDisputeContactId) {
		this.vendorDisputeContactId = vendorDisputeContactId;
	}

	public Integer getVendorPaymentContactId() {
		return this.vendorPaymentContactId;
	}

	public void setVendorPaymentContactId(Integer vendorPaymentContactId) {
		this.vendorPaymentContactId = vendorPaymentContactId;
	}

	public Integer getVendorTariffContactId() {
		return this.vendorTariffContactId;
	}

	public void setVendorTariffContactId(Integer vendorTariffContactId) {
		this.vendorTariffContactId = vendorTariffContactId;
	}

	public Integer getVendorBillingContactId() {
		return this.vendorBillingContactId;
	}

	public void setVendorBillingContactId(Integer vendorBillingContactId) {
		this.vendorBillingContactId = vendorBillingContactId;
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

	public String getSummaryVendorName() {
		return this.summaryVendorName;
	}

	public void setSummaryVendorName(String summaryVendorName) {
		this.summaryVendorName = summaryVendorName;
	}

	public String getVendorAcronym() {
		return this.vendorAcronym;
	}

	public void setVendorAcronym(String vendorAcronym) {
		this.vendorAcronym = vendorAcronym;
	}

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public Set getUserPreviledges() {
		return this.userPreviledges;
	}

	public void setUserPreviledges(Set userPreviledges) {
		this.userPreviledges = userPreviledges;
	}

	public Set getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set invoices) {
		this.invoices = invoices;
	}

	public Set getContacts() {
		return this.contacts;
	}

	public void setContacts(Set contacts) {
		this.contacts = contacts;
	}

	public Set getInvoiceSts() {
		return this.invoiceSts;
	}

	public void setInvoiceSts(Set invoiceSts) {
		this.invoiceSts = invoiceSts;
	}

	public Set getInventories() {
		return this.inventories;
	}

	public void setInventories(Set inventories) {
		this.inventories = inventories;
	}

	public Set getCredits() {
		return this.credits;
	}

	public void setCredits(Set credits) {
		this.credits = credits;
	}

	public Set getReceivedPayments() {
		return this.receivedPayments;
	}

	public void setReceivedPayments(Set receivedPayments) {
		this.receivedPayments = receivedPayments;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractVendor [");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (statusTimestamp != null)
			builder.append("statusTimestamp=").append(statusTimestamp).append(
					", ");
		if (summaryVendorName != null)
			builder.append("summaryVendorName=").append(summaryVendorName)
					.append(", ");
		if (vendorAcronym != null)
			builder.append("vendorAcronym=").append(vendorAcronym).append(", ");
		if (vendorBillingContactId != null)
			builder.append("vendorBillingContactId=").append(
					vendorBillingContactId).append(", ");
		if (vendorDisputeContactId != null)
			builder.append("vendorDisputeContactId=").append(
					vendorDisputeContactId).append(", ");
		if (vendorName != null)
			builder.append("vendorName=").append(vendorName).append(", ");
		if (vendorPaymentContactId != null)
			builder.append("vendorPaymentContactId=").append(
					vendorPaymentContactId).append(", ");
		if (vendorPrimaryContactId != null)
			builder.append("vendorPrimaryContactId=").append(
					vendorPrimaryContactId).append(", ");
		if (vendorStatus != null)
			builder.append("vendorStatus=").append(vendorStatus).append(", ");
		if (vendorTariffContactId != null)
			builder.append("vendorTariffContactId=").append(
					vendorTariffContactId);
		builder.append("]");
		return builder.toString();
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Double getLpcRate() {
		return lpcRate;
	}

	public void setLpcRate(Double lpcRate) {
		this.lpcRate = lpcRate;
	}
	

}