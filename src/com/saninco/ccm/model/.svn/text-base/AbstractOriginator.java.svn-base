package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractOriginator entity provides the base persistence definition of the
 * Originator entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractOriginator implements java.io.Serializable {

	// Fields

	private Integer id;
	private String originatorName;

	private Set proposals = new HashSet(0);
	private Set disputes = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractOriginator() {
	}

	/** full constructor */
	public AbstractOriginator(String originatorName, Set proposals, Set disputes) {
		this.originatorName = originatorName;
		this.proposals = proposals;
		this.disputes = disputes;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOriginatorName() {
		return this.originatorName;
	}

	public void setOriginatorName(String originatorName) {
		this.originatorName = originatorName;
	}

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	public Set getDisputes() {
		return this.disputes;
	}

	public void setDisputes(Set disputes) {
		this.disputes = disputes;
	}

	public String toString() {
		return "AbstractOriginator ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (originatorName != null ? "originatorName=" + originatorName
						: "") + "]";
	}

}