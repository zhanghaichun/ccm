package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractReconcileStatus entity provides the base persistence definition of
 * the ReconcileStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractReconcileStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String reconcileStatusName;
	
	private Set reconciles = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractReconcileStatus() {
	}

	/** full constructor */
	public AbstractReconcileStatus(String reconcileStatusName, Set reconciles) {
		this.reconcileStatusName = reconcileStatusName;
		this.reconciles = reconciles;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReconcileStatusName() {
		return this.reconcileStatusName;
	}

	public void setReconcileStatusName(String reconcileStatusName) {
		this.reconcileStatusName = reconcileStatusName;
	}

	public Set getReconciles() {
		return this.reconciles;
	}

	public void setReconciles(Set reconciles) {
		this.reconciles = reconciles;
	}

	public String toString() {
		return "AbstractReconcileStatus ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (reconcileStatusName != null ? "reconcileStatusName="
						+ reconcileStatusName : "") + "]";
	}

}