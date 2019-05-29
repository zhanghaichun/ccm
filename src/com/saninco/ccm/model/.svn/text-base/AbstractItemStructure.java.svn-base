package com.saninco.ccm.model;

/**
 * AbstractItemStructure entity provides the base persistence definition of the
 * ItemStructure entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractItemStructure implements java.io.Serializable {

	// Fields

	private Integer id;
	private ItemType itemTypeByItemTypeId;
	private ItemType itemTypeByParentItemTypeId;
	private InvoiceStructure invoiceStructure;
	private String multilineFlag;
	private String recActiveFlag;
	private String proposalFlag;

	// Constructors

	/** default constructor */
	public AbstractItemStructure() {
	}

	/** full constructor */
	public AbstractItemStructure(ItemType itemTypeByItemTypeId,
			ItemType itemTypeByParentItemTypeId,
			InvoiceStructure invoiceStructure, String multilineFlag,
			String recActiveFlag, String proposalFlag) {
		this.itemTypeByItemTypeId = itemTypeByItemTypeId;
		this.itemTypeByParentItemTypeId = itemTypeByParentItemTypeId;
		this.invoiceStructure = invoiceStructure;
		this.multilineFlag = multilineFlag;
		this.recActiveFlag = recActiveFlag;
		this.proposalFlag = proposalFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ItemType getItemTypeByItemTypeId() {
		return this.itemTypeByItemTypeId;
	}

	public void setItemTypeByItemTypeId(ItemType itemTypeByItemTypeId) {
		this.itemTypeByItemTypeId = itemTypeByItemTypeId;
	}

	public ItemType getItemTypeByParentItemTypeId() {
		return this.itemTypeByParentItemTypeId;
	}

	public void setItemTypeByParentItemTypeId(
			ItemType itemTypeByParentItemTypeId) {
		this.itemTypeByParentItemTypeId = itemTypeByParentItemTypeId;
	}

	public InvoiceStructure getInvoiceStructure() {
		return this.invoiceStructure;
	}

	public void setInvoiceStructure(InvoiceStructure invoiceStructure) {
		this.invoiceStructure = invoiceStructure;
	}

	public String getMultilineFlag() {
		return this.multilineFlag;
	}

	public void setMultilineFlag(String multilineFlag) {
		this.multilineFlag = multilineFlag;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String getProposalFlag() {
		return this.proposalFlag;
	}

	public void setProposalFlag(String proposalFlag) {
		this.proposalFlag = proposalFlag;
	}

	public String toString() {
		return "AbstractItemStructure ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceStructure != null ? "invoiceStructure="
						+ invoiceStructure + ", " : "")
				+ (itemTypeByItemTypeId != null ? "itemTypeByItemTypeId="
						+ itemTypeByItemTypeId + ", " : "")
				+ (itemTypeByParentItemTypeId != null ? "itemTypeByParentItemTypeId="
						+ itemTypeByParentItemTypeId + ", "
						: "")
				+ (multilineFlag != null ? "multilineFlag=" + multilineFlag
						+ ", " : "")
				+ (proposalFlag != null ? "proposalFlag=" + proposalFlag + ", "
						: "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						: "") + "]";
	}

}