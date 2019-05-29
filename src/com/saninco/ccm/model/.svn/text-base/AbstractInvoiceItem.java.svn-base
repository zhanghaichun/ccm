package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoiceItem entity provides the base persistence definition of the
 * InvoiceItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoiceItem implements java.io.Serializable {

	// Fields

	private Long id;
	private ItemType itemType;
	private TaxCode taxCode;
	private Invoice invoice;
	private Double paymentSum;
	private Double disputeSum;
	private Long parentItemId;
	private String itemName;
	private Double rate;
	private Double originalAmount;
	private Double discount;
	private Double quantity;
	private Double itemAmount;
	private String itemKey;
	private Date startDate;
	private Date endDate;
	private String barCode;
	private String invoiceNumber;
	private String circuitNumber;
	private Integer days;
	private Double originalQuantity;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String proposalFlag;
	private Date date;
	private Double mrcAmount;
	private Double onetimeAmount;
	private Integer minutes;
	private Integer seconds;
	private Date startDatetime;
	private Date endDatetime;
	private Date completionDate;
	private String strippedCircuitNumber;
	private String internalCircuitNumber;
	private String serviceDescription;
	private String description;
	private String address;
	private String usoc;
	private String unitOfMeasure;
	private String serviceNumber;
	private String lineItemCode;
	private String lineItemCodeDescription;
	private String serviceType;
	private String usageType;
	private String usageTypeCode;
	private String circuitCategory;
	private String serviceOrderNumber;
	private String purchaseOrderNumber;
	private String recurringFlag;
	private String country;
	private String taxNumber;
	private String reference;
	private String notes;
	private String province;
	private String direction;

	private Set proposals = new HashSet(0);
	private Set itemLabels = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInvoiceItem() {
	}

	/** full constructor */
	public AbstractInvoiceItem(ItemType itemType, TaxCode taxCode,
			Invoice invoice, Double paymentSum, Double disputeSum,
			Long parentItemId, String itemName, Double rate,
			Double originalAmount, Double discount, Double quantity,
			Double itemAmount, String itemKey, Date startDate, Date endDate,
			String circuitNumber, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			String proposalFlag, Date date, Double mrcAmount,
			Double onetimeAmount, Integer minutes, Integer seconds,
			Date startDatetime, Date endDatetime, Date completionDate,
			String strippedCircuitNumber, String internalCircuitNumber,
			String serviceDescription, String description, String address,
			String usoc, String unitOfMeasure, String serviceNumber,
			String lineItemCode, String lineItemCodeDescription,
			String serviceType, String usageType, String usageTypeCode,
			String circuitCategory, String serviceOrderNumber,
			String purchaseOrderNumber, String recurringFlag, String country,
			String province, String direction, Set proposals, Set itemLabels) {
		this.itemType = itemType;
		this.taxCode = taxCode;
		this.invoice = invoice;
		this.paymentSum = paymentSum;
		this.disputeSum = disputeSum;
		this.parentItemId = parentItemId;
		this.itemName = itemName;
		this.rate = rate;
		this.originalAmount = originalAmount;
		this.discount = discount;
		this.quantity = quantity;
		this.itemAmount = itemAmount;
		this.itemKey = itemKey;
		this.startDate = startDate;
		this.endDate = endDate;
		this.circuitNumber = circuitNumber;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.proposalFlag = proposalFlag;
		this.date = date;
		this.mrcAmount = mrcAmount;
		this.onetimeAmount = onetimeAmount;
		this.minutes = minutes;
		this.seconds = seconds;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.completionDate = completionDate;
		this.strippedCircuitNumber = strippedCircuitNumber;
		this.internalCircuitNumber = internalCircuitNumber;
		this.serviceDescription = serviceDescription;
		this.description = description;
		this.address = address;
		this.usoc = usoc;
		this.unitOfMeasure = unitOfMeasure;
		this.serviceNumber = serviceNumber;
		this.lineItemCode = lineItemCode;
		this.lineItemCodeDescription = lineItemCodeDescription;
		this.serviceType = serviceType;
		this.usageType = usageType;
		this.usageTypeCode = usageTypeCode;
		this.circuitCategory = circuitCategory;
		this.serviceOrderNumber = serviceOrderNumber;
		this.purchaseOrderNumber = purchaseOrderNumber;
		this.recurringFlag = recurringFlag;
		this.country = country;
		this.province = province;
		this.direction = direction;
		this.proposals = proposals;
		this.itemLabels = itemLabels;
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

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Double getPaymentSum() {
		return this.paymentSum;
	}

	public void setPaymentSum(Double paymentSum) {
		this.paymentSum = paymentSum;
	}

	public Double getDisputeSum() {
		return this.disputeSum;
	}

	public void setDisputeSum(Double disputeSum) {
		this.disputeSum = disputeSum;
	}

	public Long getParentItemId() {
		return this.parentItemId;
	}

	public void setParentItemId(Long parentItemId) {
		this.parentItemId = parentItemId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public Double getItemAmount() {
		return this.itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}

	public String getItemKey() {
		return this.itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
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

	public String getProposalFlag() {
		return this.proposalFlag;
	}

	public void setProposalFlag(String proposalFlag) {
		this.proposalFlag = proposalFlag;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getMrcAmount() {
		return this.mrcAmount;
	}

	public void setMrcAmount(Double mrcAmount) {
		this.mrcAmount = mrcAmount;
	}

	public Double getOnetimeAmount() {
		return this.onetimeAmount;
	}

	public void setOnetimeAmount(Double onetimeAmount) {
		this.onetimeAmount = onetimeAmount;
	}

	public Integer getMinutes() {
		return this.minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer getSeconds() {
		return this.seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public Date getStartDatetime() {
		return this.startDatetime;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	public Date getEndDatetime() {
		return this.endDatetime;
	}

	public void setEndDatetime(Date endDatetime) {
		this.endDatetime = endDatetime;
	}

	public Date getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getStrippedCircuitNumber() {
		return this.strippedCircuitNumber;
	}

	public void setStrippedCircuitNumber(String strippedCircuitNumber) {
		this.strippedCircuitNumber = strippedCircuitNumber;
	}

	public String getInternalCircuitNumber() {
		return this.internalCircuitNumber;
	}

	public void setInternalCircuitNumber(String internalCircuitNumber) {
		this.internalCircuitNumber = internalCircuitNumber;
	}

	public String getServiceDescription() {
		return this.serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsoc() {
		return this.usoc;
	}

	public void setUsoc(String usoc) {
		this.usoc = usoc;
	}

	public String getUnitOfMeasure() {
		return this.unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getServiceNumber() {
		return this.serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getLineItemCode() {
		return this.lineItemCode;
	}

	public void setLineItemCode(String lineItemCode) {
		this.lineItemCode = lineItemCode;
	}

	public String getLineItemCodeDescription() {
		return this.lineItemCodeDescription;
	}

	public void setLineItemCodeDescription(String lineItemCodeDescription) {
		this.lineItemCodeDescription = lineItemCodeDescription;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getUsageType() {
		return this.usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public String getUsageTypeCode() {
		return this.usageTypeCode;
	}

	public void setUsageTypeCode(String usageTypeCode) {
		this.usageTypeCode = usageTypeCode;
	}

	public String getCircuitCategory() {
		return this.circuitCategory;
	}

	public void setCircuitCategory(String circuitCategory) {
		this.circuitCategory = circuitCategory;
	}

	public String getServiceOrderNumber() {
		return this.serviceOrderNumber;
	}

	public void setServiceOrderNumber(String serviceOrderNumber) {
		this.serviceOrderNumber = serviceOrderNumber;
	}

	public String getPurchaseOrderNumber() {
		return this.purchaseOrderNumber;
	}

	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	public String getRecurringFlag() {
		return this.recurringFlag;
	}

	public void setRecurringFlag(String recurringFlag) {
		this.recurringFlag = recurringFlag;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	public Set getItemLabels() {
		return this.itemLabels;
	}

	public void setItemLabels(Set itemLabels) {
		this.itemLabels = itemLabels;
	}

	/**
	 * @return the taxNumber
	 */
	public String getTaxNumber() {
		return taxNumber;
	}

	/**
	 * @param taxNumber
	 *            the taxNumber to set
	 */
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
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

	/**
	 * @return the days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
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

	/**
	 * @return the originalQuantity
	 */
	public Double getOriginalQuantity() {
		return originalQuantity;
	}

	/**
	 * @param originalQuantity
	 *            the originalQuantity to set
	 */
	public void setOriginalQuantity(Double originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	@Override
	public String toString() {
		return "AbstractInvoiceItem ["
				+ (address != null ? "address=" + address + ", " : "")
				+ (barCode != null ? "barCode=" + barCode + ", " : "")
				+ (circuitCategory != null ? "circuitCategory="
						+ circuitCategory + ", " : "")
				+ (circuitNumber != null ? "circuitNumber=" + circuitNumber
						+ ", " : "")
				+ (completionDate != null ? "completionDate=" + completionDate
						+ ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (date != null ? "date=" + date + ", " : "")
				+ (days != null ? "days=" + days + ", " : "")
				+ (description != null ? "description=" + description + ", "
						: "")
				+ (direction != null ? "direction=" + direction + ", " : "")
				+ (discount != null ? "discount=" + discount + ", " : "")
				+ (disputeSum != null ? "disputeSum=" + disputeSum + ", " : "")
				+ (endDate != null ? "endDate=" + endDate + ", " : "")
				+ (endDatetime != null ? "endDatetime=" + endDatetime + ", "
						: "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (internalCircuitNumber != null ? "internalCircuitNumber="
						+ internalCircuitNumber + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber
						+ ", " : "")
				+ (itemAmount != null ? "itemAmount=" + itemAmount + ", " : "")
				+ (itemKey != null ? "itemKey=" + itemKey + ", " : "")
				+ (itemName != null ? "itemName=" + itemName + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "")
				+ (lineItemCode != null ? "lineItemCode=" + lineItemCode + ", "
						: "")
				+ (lineItemCodeDescription != null ? "lineItemCodeDescription="
						+ lineItemCodeDescription + ", " : "")
				+ (minutes != null ? "minutes=" + minutes + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (mrcAmount != null ? "mrcAmount=" + mrcAmount + ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (onetimeAmount != null ? "onetimeAmount=" + onetimeAmount
						+ ", " : "")
				+ (originalAmount != null ? "originalAmount=" + originalAmount
						+ ", " : "")
				+ (originalQuantity != null ? "originalQuantity="
						+ originalQuantity + ", " : "")
				+ (parentItemId != null ? "parentItemId=" + parentItemId + ", "
						: "")
				+ (paymentSum != null ? "paymentSum=" + paymentSum + ", " : "")
				+ (proposalFlag != null ? "proposalFlag=" + proposalFlag + ", "
						: "")
				+ (province != null ? "province=" + province + ", " : "")
				+ (purchaseOrderNumber != null ? "purchaseOrderNumber="
						+ purchaseOrderNumber + ", " : "")
				+ (quantity != null ? "quantity=" + quantity + ", " : "")
				+ (rate != null ? "rate=" + rate + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (recurringFlag != null ? "recurringFlag=" + recurringFlag
						+ ", " : "")
				+ (reference != null ? "reference=" + reference + ", " : "")
				+ (seconds != null ? "seconds=" + seconds + ", " : "")
				+ (serviceDescription != null ? "serviceDescription="
						+ serviceDescription + ", " : "")
				+ (serviceNumber != null ? "serviceNumber=" + serviceNumber
						+ ", " : "")
				+ (serviceOrderNumber != null ? "serviceOrderNumber="
						+ serviceOrderNumber + ", " : "")
				+ (serviceType != null ? "serviceType=" + serviceType + ", "
						: "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (startDatetime != null ? "startDatetime=" + startDatetime
						+ ", " : "")
				+ (strippedCircuitNumber != null ? "strippedCircuitNumber="
						+ strippedCircuitNumber + ", " : "")
				+ (taxCode != null ? "taxCode=" + taxCode + ", " : "")
				+ (taxNumber != null ? "taxNumber=" + taxNumber + ", " : "")
				+ (unitOfMeasure != null ? "unitOfMeasure=" + unitOfMeasure
						+ ", " : "")
				+ (usageType != null ? "usageType=" + usageType + ", " : "")
				+ (usageTypeCode != null ? "usageTypeCode=" + usageTypeCode
						+ ", " : "") + (usoc != null ? "usoc=" + usoc : "")
				+ "]";
	}

}