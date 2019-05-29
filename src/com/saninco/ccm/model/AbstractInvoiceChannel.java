package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoiceChannel entity provides the base persistence definition of the
 * InvoiceChannel entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoiceChannel implements java.io.Serializable {

	// Fields

	private Integer id;
	private String invoiceChannelName;

	private Set bans = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractInvoiceChannel() {
	}

	/** full constructor */
	public AbstractInvoiceChannel(String invoiceChannelName, Set bans) {
		this.invoiceChannelName = invoiceChannelName;
		this.bans = bans;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvoiceChannelName() {
		return this.invoiceChannelName;
	}

	public void setInvoiceChannelName(String invoiceChannelName) {
		this.invoiceChannelName = invoiceChannelName;
	}

	public Set getBans() {
		return this.bans;
	}

	public void setBans(Set bans) {
		this.bans = bans;
	}

	public String toString() {
		return "AbstractInvoiceChannel ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceChannelName != null ? "invoiceChannelName="
						+ invoiceChannelName : "") + "]";
	}

}