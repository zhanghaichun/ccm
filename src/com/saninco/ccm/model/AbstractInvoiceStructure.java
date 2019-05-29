package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoiceStructure entity provides the base persistence definition of
 * the InvoiceStructure entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoiceStructure implements java.io.Serializable {

	// Fields

	private Integer id;
	private String invoiceStructureCode;

	private Set itemDisplaies = new HashSet(0);
	private Set itemStructures = new HashSet(0);
	private Set bans = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInvoiceStructure() {
		
	}
	
	/** default constructor */
	public AbstractInvoiceStructure(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractInvoiceStructure(String invoiceStructureCode,
			Set itemDisplaies, Set itemStructures, Set bans) {
		this.invoiceStructureCode = invoiceStructureCode;
		this.itemDisplaies = itemDisplaies;
		this.itemStructures = itemStructures;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvoiceStructureCode() {
		return this.invoiceStructureCode;
	}

	public void setInvoiceStructureCode(String invoiceStructureCode) {
		this.invoiceStructureCode = invoiceStructureCode;
	}

	public Set getItemDisplaies() {
		return this.itemDisplaies;
	}

	public void setItemDisplaies(Set itemDisplaies) {
		this.itemDisplaies = itemDisplaies;
	}

	public Set getItemStructures() {
		return this.itemStructures;
	}

	public void setItemStructures(Set itemStructures) {
		this.itemStructures = itemStructures;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractInvoiceStructure ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceStructureCode != null ? "invoiceStructureCode="
						+ invoiceStructureCode : "") + "]";
	}

}