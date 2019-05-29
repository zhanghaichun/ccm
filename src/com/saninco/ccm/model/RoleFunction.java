package com.saninco.ccm.model;

import java.util.Date;

/**
 * RoleFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class RoleFunction extends AbstractRoleFunction implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public RoleFunction() {
	}

	/** full constructor */
	public RoleFunction(Function function, Role role, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		super(function, role, createdTimestamp, createdBy, modifiedTimestamp,
				modifiedBy, recActiveFlag);
	}

}
