package com.saninco.ccm.model.bi;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBIBudget entity provides the base persistence definition of the
 * BIBudget entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBIBudget implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -283468724897462128L;
	private Integer id;
	private String budgetName;
	private String budgetOwner;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractBIBudget() {
	}

	/** full constructor */
	public AbstractBIBudget(String budgetName, String budgetOwner, Date createdTimestamp, Integer createdBy, Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		this.budgetName = budgetName;
		this.budgetOwner = budgetOwner;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	public String getBudgetOwner() {
		return budgetOwner;
	}

	public void setBudgetOwner(String budgetOwner) {
		this.budgetOwner = budgetOwner;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

}