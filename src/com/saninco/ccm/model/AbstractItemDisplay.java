package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractItemDisplay entity provides the base persistence definition of the
 * ItemDisplay entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractItemDisplay implements java.io.Serializable {

	// Fields

	private Integer id;
	private ItemType itemType;
	private InvoiceStructure invoiceStructure;
	private Date tableField;
	private String displayTitle;
	private String displayFormat;
	private Integer displayOrder;
	private Integer displayWidth;
	private String recActiveFlag;
	private String defaultValue;

	// Constructors

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue
	 *            the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/** default constructor */
	public AbstractItemDisplay() {
	}

	/** full constructor */
	public AbstractItemDisplay(ItemType itemType,
			InvoiceStructure invoiceStructure, Date tableField,
			String displayTitle, String displayFormat, Integer displayOrder,
			Integer displayWidth, String recActiveFlag, String defaultValue) {
		this.itemType = itemType;
		this.invoiceStructure = invoiceStructure;
		this.tableField = tableField;
		this.displayTitle = displayTitle;
		this.displayFormat = displayFormat;
		this.displayOrder = displayOrder;
		this.displayWidth = displayWidth;
		this.recActiveFlag = recActiveFlag;
		this.defaultValue = defaultValue;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public InvoiceStructure getInvoiceStructure() {
		return this.invoiceStructure;
	}

	public void setInvoiceStructure(InvoiceStructure invoiceStructure) {
		this.invoiceStructure = invoiceStructure;
	}

	public Date getTableField() {
		return this.tableField;
	}

	public void setTableField(Date tableField) {
		this.tableField = tableField;
	}

	public String getDisplayTitle() {
		return this.displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}

	public String getDisplayFormat() {
		return this.displayFormat;
	}

	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}

	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Integer getDisplayWidth() {
		return this.displayWidth;
	}

	public void setDisplayWidth(Integer displayWidth) {
		this.displayWidth = displayWidth;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String toString() {
		return "AbstractItemDisplay ["
				+ (defaultValue != null ? "defaultValue=" + defaultValue + ", "
						: "")
				+ (displayFormat != null ? "displayFormat=" + displayFormat
						+ ", " : "")
				+ (displayOrder != null ? "displayOrder=" + displayOrder + ", "
						: "")
				+ (displayTitle != null ? "displayTitle=" + displayTitle + ", "
						: "")
				+ (displayWidth != null ? "displayWidth=" + displayWidth + ", "
						: "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceStructure != null ? "invoiceStructure="
						+ invoiceStructure + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (tableField != null ? "tableField=" + tableField : "") + "]";
	}


}