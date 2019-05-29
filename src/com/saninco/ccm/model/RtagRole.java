package com.saninco.ccm.model;

import java.util.Date;

/**
 * RtagRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class RtagRole extends AbstractRtagRole implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public RtagRole() {
	}

	/** full constructor */
	public RtagRole(Role role, Rtag rtag, String accessLevel, String sendEmail,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		super(role, rtag, accessLevel, sendEmail, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag);
	}

}
