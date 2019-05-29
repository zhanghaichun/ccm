package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * InvoiceItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class InvoiceItem extends AbstractInvoiceItem implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public InvoiceItem() {
	}

	/** full constructor */
	public InvoiceItem(ItemType itemType, TaxCode taxCode, Invoice invoice,
			Double paymentSum, Double disputeSum, Long parentItemId,
			String itemName, Double rate, Double originalAmount,
			Double discount, Double quantity, Double itemAmount,
			String itemKey, Date startDate, Date endDate, String circuitNumber,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, String proposalFlag,
			Date date, Double mrcAmount, Double onetimeAmount, Integer minutes,
			Integer seconds, Date startDatetime, Date endDatetime,
			Date completionDate, String strippedCircuitNumber,
			String internalCircuitNumber, String serviceDescription,
			String description, String address, String usoc,
			String unitOfMeasure, String serviceNumber, String lineItemCode,
			String lineItemCodeDescription, String serviceType,
			String usageType, String usageTypeCode, String circuitCategory,
			String serviceOrderNumber, String purchaseOrderNumber,
			String recurringFlag, String country, String province,
			String direction, Set proposals, Set itemLabels) {
		super(itemType, taxCode, invoice, paymentSum, disputeSum, parentItemId,
				itemName, rate, originalAmount, discount, quantity, itemAmount,
				itemKey, startDate, endDate, circuitNumber, createdTimestamp,
				createdBy, modifiedTimestamp, modifiedBy, recActiveFlag,
				proposalFlag, date, mrcAmount, onetimeAmount, minutes, seconds,
				startDatetime, endDatetime, completionDate,
				strippedCircuitNumber, internalCircuitNumber,
				serviceDescription, description, address, usoc, unitOfMeasure,
				serviceNumber, lineItemCode, lineItemCodeDescription,
				serviceType, usageType, usageTypeCode, circuitCategory,
				serviceOrderNumber, purchaseOrderNumber, recurringFlag,
				country, province, direction, proposals, itemLabels);
	}

}
