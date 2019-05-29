package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractLabel entity provides the base persistence definition of the Label
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractLabel implements java.io.Serializable {

	// Fields

	private Integer id;
	private LabelType labelType;
	private String labelName;
	private String iconFilePath;

	private Set invoiceLabels = new HashSet(0);
	private Set itemLabels = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractLabel() {
	}

	/** full constructor */
	public AbstractLabel(LabelType labelType, String labelName,
			String iconFilePath, Set invoiceLabels, Set itemLabels) {
		this.labelType = labelType;
		this.labelName = labelName;
		this.iconFilePath = iconFilePath;
		this.invoiceLabels = invoiceLabels;
		this.itemLabels = itemLabels;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LabelType getLabelType() {
		return this.labelType;
	}

	public void setLabelType(LabelType labelType) {
		this.labelType = labelType;
	}

	public String getLabelName() {
		return this.labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getIconFilePath() {
		return this.iconFilePath;
	}

	public void setIconFilePath(String iconFilePath) {
		this.iconFilePath = iconFilePath;
	}

	public Set getInvoiceLabels() {
		return this.invoiceLabels;
	}

	public void setInvoiceLabels(Set invoiceLabels) {
		this.invoiceLabels = invoiceLabels;
	}

	public Set getItemLabels() {
		return this.itemLabels;
	}

	public void setItemLabels(Set itemLabels) {
		this.itemLabels = itemLabels;
	}

	public String toString() {
		return "AbstractLabel ["
				+ (iconFilePath != null ? "iconFilePath=" + iconFilePath + ", "
						: "") + (id != null ? "id=" + id + ", " : "")
				+ (labelName != null ? "labelName=" + labelName + ", " : "")
				+ (labelType != null ? "labelType=" + labelType : "") + "]";
	}



}