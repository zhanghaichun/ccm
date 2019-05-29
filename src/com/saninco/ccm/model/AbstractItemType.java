package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractItemType entity provides the base persistence definition of the
 * ItemType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractItemType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String itemTypeName;

	private Set invoiceItemSts = new HashSet(0);
	private Set invoiceItems = new HashSet(0);
	private Set itemStructuresForParentItemTypeId = new HashSet(0);
	private Set itemDisplaies = new HashSet(0);
	private Set itemStructuresForItemTypeId = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractItemType() {
	}

	/** full constructor */
	public AbstractItemType(String itemName, Set invoiceItemSts,
			Set invoiceItems, Set itemStructuresForParentItemTypeId,
			Set itemDisplaies, Set itemStructuresForItemTypeId) {
		this.invoiceItemSts = invoiceItemSts;
		this.invoiceItems = invoiceItems;
		this.itemStructuresForParentItemTypeId = itemStructuresForParentItemTypeId;
		this.itemDisplaies = itemDisplaies;
		this.itemStructuresForItemTypeId = itemStructuresForItemTypeId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set getInvoiceItemSts() {
		return this.invoiceItemSts;
	}

	public void setInvoiceItemSts(Set invoiceItemSts) {
		this.invoiceItemSts = invoiceItemSts;
	}

	public Set getInvoiceItems() {
		return this.invoiceItems;
	}

	public void setInvoiceItems(Set invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	public Set getItemStructuresForParentItemTypeId() {
		return this.itemStructuresForParentItemTypeId;
	}

	public void setItemStructuresForParentItemTypeId(
			Set itemStructuresForParentItemTypeId) {
		this.itemStructuresForParentItemTypeId = itemStructuresForParentItemTypeId;
	}

	public Set getItemDisplaies() {
		return this.itemDisplaies;
	}

	public void setItemDisplaies(Set itemDisplaies) {
		this.itemDisplaies = itemDisplaies;
	}

	public Set getItemStructuresForItemTypeId() {
		return this.itemStructuresForItemTypeId;
	}

	public void setItemStructuresForItemTypeId(Set itemStructuresForItemTypeId) {
		this.itemStructuresForItemTypeId = itemStructuresForItemTypeId;
	}

	/**
	 * @return the itemTypeName
	 */
	public String getItemTypeName() {
		return itemTypeName;
	}

	/**
	 * @param itemTypeName
	 *            the itemTypeName to set
	 */
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String toString() {
		return "AbstractItemType [" + (id != null ? "id=" + id + ", " : "")
				+ (itemTypeName != null ? "itemTypeName=" + itemTypeName : "")
				+ "]";
	}



}