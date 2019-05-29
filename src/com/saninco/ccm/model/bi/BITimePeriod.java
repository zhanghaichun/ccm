package com.saninco.ccm.model.bi;

/**
 * BITimePeriod entity. @author MyEclipse Persistence Tools
 */
public class BITimePeriod extends AbstractBITimePeriod implements
		java.io.Serializable {

	// Constructors
	private String labelName;

	/** default constructor */
	public BITimePeriod() {
	}

	/** full constructor */
	public BITimePeriod(String lastPeriodType, Integer lastPeriodNumber,
			String listFlag, String name) {
		super(lastPeriodType, lastPeriodNumber, listFlag, name);
	}

	public String getLabelName() {
		return labelName == null ? this.getName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
