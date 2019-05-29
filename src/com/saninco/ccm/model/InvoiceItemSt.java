package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * InvoiceItemSt entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class InvoiceItemSt extends AbstractInvoiceItemSt implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public InvoiceItemSt() {
	}

	/** full constructor */
	public InvoiceItemSt(ItemType itemType, TaxCode taxCode,
			InvoiceSt invoiceSt, InvoiceItemSt invoiceItemSt,
			String disqualifiedFlag, Integer sequence, String itemName,
			Double itemAmount, Double rate, Double originalAmount,
			Double discount, Double quantity, Date startDate, Date endDate,
			Date date, String circuitNumber, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, Set invoiceItemSts) {
		super(itemType, taxCode, invoiceSt, invoiceItemSt, disqualifiedFlag,
				sequence, itemName, itemAmount, rate, originalAmount, discount,
				quantity, startDate, endDate, date, circuitNumber,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy,
				recActiveFlag, invoiceItemSts);
	}

}
