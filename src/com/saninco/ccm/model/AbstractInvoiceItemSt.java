package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoiceItemSt entity provides the base persistence definition of the
 * InvoiceItemSt entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoiceItemSt implements java.io.Serializable {

	// Fields

	private Long id;
	private ItemType itemType;
	private TaxCode taxCode;
	private InvoiceSt invoiceSt;
	private InvoiceItemSt invoiceItemSt;
	private String disqualifiedFlag;
	private Integer sequence;
	private String itemName;
	private String barCode;
	private String invoiceNumber;
	private Double itemAmount;
	private Double rate;
	private Double originalAmount;
	private Double discount;
	private Double quantity;
	private Date startDate;
	private Date endDate;
	private Date date;
	private String circuitNumber;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	private Set invoiceItemSts = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInvoiceItemSt() {
	}

	/** full constructor */
	public AbstractInvoiceItemSt(ItemType itemType, TaxCode taxCode,
			InvoiceSt invoiceSt, InvoiceItemSt invoiceItemSt,
			String disqualifiedFlag, Integer sequence, String itemName,
			Double itemAmount, Double rate, Double originalAmount,
			Double discount, Double quantity, Date startDate, Date endDate,
			Date date, String circuitNumber, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, Set invoiceItemSts) {
		this.itemType = itemType;
		this.taxCode = taxCode;
		this.invoiceSt = invoiceSt;
		this.invoiceItemSt = invoiceItemSt;
		this.disqualifiedFlag = disqualifiedFlag;
		this.sequence = sequence;
		this.itemName = itemName;
		this.itemAmount = itemAmount;
		this.rate = rate;
		this.originalAmount = originalAmount;
		this.discount = discount;
		this.quantity = quantity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.date = date;
		this.circuitNumber = circuitNumber;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.invoiceItemSts = invoiceItemSts;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public TaxCode getTaxCode() {
		return this.taxCode;
	}

	public void setTaxCode(TaxCode taxCode) {
		this.taxCode = taxCode;
	}

	public InvoiceSt getInvoiceSt() {
		return this.invoiceSt;
	}

	public void setInvoiceSt(InvoiceSt invoiceSt) {
		this.invoiceSt = invoiceSt;
	}

	public InvoiceItemSt getInvoiceItemSt() {
		return this.invoiceItemSt;
	}

	public void setInvoiceItemSt(InvoiceItemSt invoiceItemSt) {
		this.invoiceItemSt = invoiceItemSt;
	}

	public String getDisqualifiedFlag() {
		return this.disqualifiedFlag;
	}

	public void setDisqualifiedFlag(String disqualifiedFlag) {
		this.disqualifiedFlag = disqualifiedFlag;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemAmount() {
		return this.itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getOriginalAmount() {
		return this.originalAmount;
	}

	public void setOriginalAmount(Double originalAmount) {
		this.originalAmount = originalAmount;
	}

	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCircuitNumber() {
		return this.circuitNumber;
	}

	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
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
	 * @return the barCode
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * @param barCode
	 *            the barCode to set
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String toString() {
		return "AbstractInvoiceItemSt ["
				+ (barCode != null ? "barCode=" + barCode + ", " : "")
				+ (circuitNumber != null ? "circuitNumber=" + circuitNumber
						+ ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (date != null ? "date=" + date + ", " : "")
				+ (discount != null ? "discount=" + discount + ", " : "")
				+ (disqualifiedFlag != null ? "disqualifiedFlag="
						+ disqualifiedFlag + ", " : "")
				+ (endDate != null ? "endDate=" + endDate + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceItemSt != null ? "invoiceItemSt=" + invoiceItemSt
						+ ", " : "")
				+ (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber
						+ ", " : "")
				+ (invoiceSt != null ? "invoiceSt=" + invoiceSt + ", " : "")
				+ (itemAmount != null ? "itemAmount=" + itemAmount + ", " : "")
				+ (itemName != null ? "itemName=" + itemName + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (originalAmount != null ? "originalAmount=" + originalAmount
						+ ", " : "")
				+ (quantity != null ? "quantity=" + quantity + ", " : "")
				+ (rate != null ? "rate=" + rate + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (sequence != null ? "sequence=" + sequence + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (taxCode != null ? "taxCode=" + taxCode : "") + "]";
	}

}