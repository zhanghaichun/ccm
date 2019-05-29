package com.saninco.ccm.model.bi;

/**
 * AbstractBITimePeriod entity provides the base persistence definition of the
 * BITimePeriod entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBITimePeriod implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7817235291492390881L;
	private Integer id;
	private String lastPeriodType;
	private Integer lastPeriodNumber;
	private String listFlag;
	private String name;

	// Constructors

	/** default constructor */
	public AbstractBITimePeriod() {
	}

	/** full constructor */
	public AbstractBITimePeriod(String lastPeriodType,
			Integer lastPeriodNumber, String listFlag, String name) {
		this.lastPeriodType = lastPeriodType;
		this.lastPeriodNumber = lastPeriodNumber;
		this.listFlag = listFlag;
		this.name = name;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastPeriodType() {
		return this.lastPeriodType;
	}

	public void setLastPeriodType(String lastPeriodType) {
		this.lastPeriodType = lastPeriodType;
	}

	public Integer getLastPeriodNumber() {
		return this.lastPeriodNumber;
	}

	public void setLastPeriodNumber(Integer lastPeriodNumber) {
		this.lastPeriodNumber = lastPeriodNumber;
	}

	public String getListFlag() {
		return this.listFlag;
	}

	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}