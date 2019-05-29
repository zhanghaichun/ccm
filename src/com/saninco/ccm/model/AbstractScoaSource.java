package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;


public abstract class AbstractScoaSource implements java.io.Serializable {
	
	private Integer id;
	private String scoaSourceName;
	
	private Set proposals = new HashSet(0);
	/** default constructor */
	public AbstractScoaSource() {
	}
	/** full constructor */
	public AbstractScoaSource(Integer id,String scoaSourceName, Set proposals) {
		this.id = id;
		this.scoaSourceName = scoaSourceName;
		this.proposals = proposals;
	}
	
	public String toString() {
		return "AbstractPaymentTerm ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (scoaSourceName != null ? "scoaSourceName="
						+ scoaSourceName : "") + "]";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getScoaSourceName() {
		return scoaSourceName;
	}
	public void setScoaSourceName(String scoaSourceName) {
		this.scoaSourceName = scoaSourceName;
	}
	public Set getProposals() {
		return proposals;
	}
	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}
	
}
