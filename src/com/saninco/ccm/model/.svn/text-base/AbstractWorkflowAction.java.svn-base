package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractWorkflowAction entity provides the base persistence definition of the
 * WorkflowAction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractWorkflowAction implements java.io.Serializable {

	// Fields

	private Integer id;
	private String workflowActionName;

	private Set disputes = new HashSet(0);
	private Set invoices = new HashSet(0);
	private Set transactionHistories = new HashSet(0);
	private Set payments = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractWorkflowAction() {
	}

	/** full constructor */
	public AbstractWorkflowAction(String workflowActionName, Set disputes,
			Set invoices, Set transactionHistories, Set payments) {
		this.workflowActionName = workflowActionName;
		this.disputes = disputes;
		this.invoices = invoices;
		this.transactionHistories = transactionHistories;
		this.payments = payments;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkflowActionName() {
		return this.workflowActionName;
	}

	public void setWorkflowActionName(String workflowActionName) {
		this.workflowActionName = workflowActionName;
	}

	public Set getDisputes() {
		return this.disputes;
	}

	public void setDisputes(Set disputes) {
		this.disputes = disputes;
	}

	public Set getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set invoices) {
		this.invoices = invoices;
	}

	public Set getTransactionHistories() {
		return this.transactionHistories;
	}

	public void setTransactionHistories(Set transactionHistories) {
		this.transactionHistories = transactionHistories;
	}

	public Set getPayments() {
		return this.payments;
	}

	public void setPayments(Set payments) {
		this.payments = payments;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractWorkflowAction [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (workflowActionName != null)
			builder.append("workflowActionName=").append(workflowActionName);
		builder.append("]");
		return builder.toString();
	}



}