package com.saninco.ccm.model.bi;

import java.util.Date;

/**
 * BIBudget entity. @author MyEclipse Persistence Tools
 */
public class BIBudget extends AbstractBIBudget implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4285467437414864463L;
	// Constructors
	private String labelName;
	
	/** default constructor */
	public BIBudget() {
	}

	/** full constructor */
	public BIBudget(String budgetName, String budgetOwner, Date createdTimestamp, Integer createdBy, Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		super(budgetName, budgetOwner, createdTimestamp, createdBy, modifiedTimestamp, modifiedBy, recActiveFlag);
	}

	public String getLabelName() {
		
		String result = this.labelName;
		
		if (result == null) {
			result = this.getBudgetName();
			if (this.getBudgetOwner() != null) {
				result = result + "("+this.getBudgetOwner()+")";
			}
		} 
		return result;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
