package com.saninco.ccm.model.bi;

/**
 * BIUserDashboardModule entity. @author MyEclipse Persistence Tools
 */
public class BIUserDashboardModule extends AbstractBIUserDashboardModule
		implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5122943788662164394L;
	// Constructors
	
	/** default constructor */
	public BIUserDashboardModule() {
	}

	/** full constructor */
	public BIUserDashboardModule(BIUserDashboard BIUserDashboard,
			BIDashboardModule BIDashboardModule, String controlData,
			Integer sortNo, String name, String showPercentFlag) {
		super(BIUserDashboard, BIDashboardModule, controlData, sortNo, name, showPercentFlag);
	}

}
