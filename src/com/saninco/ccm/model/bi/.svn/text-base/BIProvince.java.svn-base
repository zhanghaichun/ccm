package com.saninco.ccm.model.bi;

import java.util.Set;

/**
 * BIProvince entity. @author MyEclipse Persistence Tools
 */
public class BIProvince extends AbstractBIProvince implements
		java.io.Serializable {

	// Constructors
	private String labelName;

	/** default constructor */
	public BIProvince() {
	}

	/** minimal constructor */
	public BIProvince(Integer id) {
		super(id);
	}

	/** full constructor */
	public BIProvince(Integer id, String provinceName, String provinceAcronym) {
		super(id, provinceName, provinceAcronym);
	}

	public String getLabelName() {
		return labelName == null ? this.getProvinceName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
