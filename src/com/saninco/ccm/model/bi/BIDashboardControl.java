package com.saninco.ccm.model.bi;

import java.util.Set;

/**
 * BIDashboardControl entity. @author MyEclipse Persistence Tools
 */
public class BIDashboardControl extends AbstractBIDashboardControl implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public BIDashboardControl() {
	}

	/** full constructor */
	public BIDashboardControl(String controlName, String controlKey,String globalControlName, String globalFlag,
			Set BIDashboardModuleControls) {
		super(controlName, controlKey, globalControlName, globalFlag, BIDashboardModuleControls);
	}

}
