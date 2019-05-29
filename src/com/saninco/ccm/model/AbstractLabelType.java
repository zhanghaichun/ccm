package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractLabelType entity provides the base persistence definition of the
 * LabelType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractLabelType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String labelTypeName;

	private Set labels = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractLabelType() {
	}

	/** full constructor */
	public AbstractLabelType(String labelTypeName, Set labels) {
		this.labelTypeName = labelTypeName;
		this.labels = labels;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabelTypeName() {
		return this.labelTypeName;
	}

	public void setLabelTypeName(String labelTypeName) {
		this.labelTypeName = labelTypeName;
	}

	public Set getLabels() {
		return this.labels;
	}

	public void setLabels(Set labels) {
		this.labels = labels;
	}

	public String toString() {
		return "AbstractLabelType ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (labelTypeName != null ? "labelTypeName=" + labelTypeName
						: "") + "]";
	}

}