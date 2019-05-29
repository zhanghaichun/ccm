package com.saninco.ccm.model;

import java.util.Date;

/**
 * PaymentDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PaymentDetail extends AbstractPaymentDetail implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public PaymentDetail() {
	}

	/** full constructor */
	public PaymentDetail(AccountCode accountCode, TaxCode taxCode,
			Payment payment, Double amount, String lineTypeLookupCode,
			Integer lineNumber, String description, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		super(accountCode, taxCode, payment, amount, lineTypeLookupCode,
				lineNumber, description, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag);
	}

}
