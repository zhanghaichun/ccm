package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractDisputeReason entity provides the base persistence definition of the
 * DisputeReason entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractDisputeReason implements java.io.Serializable {

	// Fields

	private Integer id;
	private String disputeReasonName;
	private String disputeReasonAcronym;
	private String disputeReasonDiscription;

	private Set disputes = new HashSet(0);
	private Set proposals = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractDisputeReason() {
	}

	/** full constructor */
	public AbstractDisputeReason(String disputeReasonName,
			String disputeReasonAcronym, String disputeReasonDiscription,
			Set disputes, Set proposals) {
		this.disputeReasonName = disputeReasonName;
		this.disputeReasonAcronym = disputeReasonAcronym;
		this.disputeReasonDiscription = disputeReasonDiscription;
		this.disputes = disputes;
		this.proposals = proposals;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisputeReasonName() {
		return this.disputeReasonName;
	}

	public void setDisputeReasonName(String disputeReasonName) {
		this.disputeReasonName = disputeReasonName;
	}

	public String getDisputeReasonAcronym() {
		return this.disputeReasonAcronym;
	}

	public void setDisputeReasonAcronym(String disputeReasonAcronym) {
		this.disputeReasonAcronym = disputeReasonAcronym;
	}

	public String getDisputeReasonDiscription() {
		return this.disputeReasonDiscription;
	}

	public void setDisputeReasonDiscription(String disputeReasonDiscription) {
		this.disputeReasonDiscription = disputeReasonDiscription;
	}

	public Set getDisputes() {
		return this.disputes;
	}

	public void setDisputes(Set disputes) {
		this.disputes = disputes;
	}

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	public String toString() {
		return "AbstractDisputeReason ["
				+ (disputeReasonAcronym != null ? "disputeReasonAcronym="
						+ disputeReasonAcronym + ", " : "")
				+ (disputeReasonDiscription != null ? "disputeReasonDiscription="
						+ disputeReasonDiscription + ", "
						: "")
				+ (disputeReasonName != null ? "disputeReasonName="
						+ disputeReasonName + ", " : "")
				+ (id != null ? "id=" + id : "") + "]";
	}

}