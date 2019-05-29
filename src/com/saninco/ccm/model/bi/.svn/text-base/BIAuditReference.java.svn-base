package com.saninco.ccm.model.bi;

import java.util.Set;

/**
 * BIAuditReference entity. @author MyEclipse Persistence Tools
 */
public class BIAuditReference extends AbstractBIAuditReference implements
		java.io.Serializable {

	// Constructors
	private String labelName;

	/** default constructor */
	public BIAuditReference() {
	}

	/** full constructor */
	public BIAuditReference(String name, String referenceTypeName,
			String referenceId) {
		super(name, referenceTypeName, referenceId);
	}

	public String getLabelName() {
		return labelName == null ? this.getName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
