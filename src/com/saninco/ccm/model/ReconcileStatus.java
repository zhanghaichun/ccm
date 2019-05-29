package com.saninco.ccm.model;

import java.util.Set;

/**
 * ReconcileStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ReconcileStatus extends AbstractReconcileStatus implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public ReconcileStatus() {
	}

	/** full constructor */
	public ReconcileStatus(String reconcileStatusName, Set reconciles) {
		super(reconcileStatusName, reconciles);
	}

}
