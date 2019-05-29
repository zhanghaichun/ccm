package com.saninco.ccm.model.bi;

import java.util.Date;
import java.util.Set;

/**
 * BIUserDashboard entity. @author MyEclipse Persistence Tools
 */
public class BIUserDashboard extends AbstractBIUserDashboard implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public BIUserDashboard() {
	}
	/** full constructor */
	public BIUserDashboard(Integer userId, String dashboardName,
			String defaultFlag, Date createdTimestamp, Integer createdBy, Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag, Set BIUserDashboardModules) {
		super(userId, dashboardName, defaultFlag, createdTimestamp, createdBy, modifiedTimestamp, modifiedBy, recActiveFlag, BIUserDashboardModules);
	}

}
