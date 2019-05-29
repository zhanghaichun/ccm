package com.saninco.ccm.model;

import java.util.Set;

/**
 * CreditStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CreditStatus extends AbstractCreditStatus implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CreditStatus() {
	}

	/** full constructor */
	public CreditStatus(String creditStatusName, Set credits) {
		super(creditStatusName, credits);
	}

}
