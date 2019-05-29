package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * PaymentBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PaymentBatch extends AbstractPaymentBatch implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public PaymentBatch() {
	}

	/** full constructor */
	public PaymentBatch(Integer fileName, String batchStatus,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set payments) {
		super(fileName, batchStatus, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag, payments);
	}

}
