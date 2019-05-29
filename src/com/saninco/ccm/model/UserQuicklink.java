package com.saninco.ccm.model;

import java.util.Date;

/**
 * UserQuicklink entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class UserQuicklink extends AbstractUserQuicklink implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserQuicklink() {
	}

	/** minimal constructor */
	public UserQuicklink(Integer id) {
		super(id);
	}

	/** full constructor */
	public UserQuicklink(Integer id, User user, String quicklinkName,
			String requestString, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		super(id, user, quicklinkName, requestString, createdTimestamp,
				createdBy, modifiedTimestamp, modifiedBy, recActiveFlag);
	}

}
