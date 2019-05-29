package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * Role entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Role extends AbstractRole implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** full constructor */
	public Role(String roleName, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			Set rtagRoles, Set userRoles, Set roleFunctions) {
		super(roleName, createdTimestamp, createdBy, modifiedTimestamp,
				modifiedBy, recActiveFlag, rtagRoles, userRoles, roleFunctions);
	}

}
