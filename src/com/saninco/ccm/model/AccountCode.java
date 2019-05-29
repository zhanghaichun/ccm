package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * AccountCode entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AccountCode extends AbstractAccountCode implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AccountCode() {
	}

	/** full constructor */
	public AccountCode(String accountCodeName, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, String accountCodeDescription,
			String company, String location, String department, String product,
			String account, String channel, String segment7, String segment8,
			String interco, Set proposals, Set credits, Set reconciles,
			Set paymentDetails,String activeFlag) {
		super(accountCodeName, createdTimestamp, createdBy, modifiedTimestamp,
				modifiedBy, recActiveFlag, accountCodeDescription, company,
				location, department, product, account, channel, segment7,
				segment8, interco, proposals, credits, reconciles,
				paymentDetails,activeFlag);
	}

}
