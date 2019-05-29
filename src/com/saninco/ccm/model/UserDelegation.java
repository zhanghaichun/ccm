package com.saninco.ccm.model;

import java.util.Date;

/**
 * UserDelegation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class UserDelegation extends AbstractUserDelegation implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public UserDelegation() {
	}

	/** minimal constructor */
	public UserDelegation(Integer id) {
		super(id);
	}

	/** full constructor */
	public UserDelegation(Integer id, User userByFromUserId,
			User userByToUserId, Date startDate, Date endDate,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		super(id, userByFromUserId, userByToUserId, startDate, endDate,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy,
				recActiveFlag);
	}

}
