package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractDisputeStatus entity provides the base persistence definition of the
 * DisputeStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractDisputeStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String disputeStatusName;

	private Set transactionHistories = new HashSet(0);
	private Set disputes = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractDisputeStatus() {
	}

	/** full constructor */
	public AbstractDisputeStatus(String disputeStatusName,
			Set transactionHistories, Set disputes) {
		this.disputeStatusName = disputeStatusName;
		this.transactionHistories = transactionHistories;
		this.disputes = disputes;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisputeStatusName() {
		return this.disputeStatusName;
	}

	public void setDisputeStatusName(String disputeStatusName) {
		this.disputeStatusName = disputeStatusName;
	}

	public Set getTransactionHistories() {
		return this.transactionHistories;
	}

	public void setTransactionHistories(Set transactionHistories) {
		this.transactionHistories = transactionHistories;
	}

	public Set getDisputes() {
		return this.disputes;
	}

	public void setDisputes(Set disputes) {
		this.disputes = disputes;
	}

	public String toString() {
		return "AbstractDisputeStatus ["
				+ (disputeStatusName != null ? "disputeStatusName="
						+ disputeStatusName + ", " : "")
				+ (id != null ? "id=" + id : "") + "]";
	}



}