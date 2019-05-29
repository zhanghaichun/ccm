package com.saninco.ccm.model;

import java.util.Date;

/**
 * PasswordHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PasswordHistory extends AbstractPasswordHistory implements
		java.io.Serializable {

	// Constructors
 
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public PasswordHistory() {
	}

	/** minimal constructor */
	public PasswordHistory(Integer id) {
		super(id);
	}

	/** full constructor */
	public PasswordHistory(Integer id, User user, String password,
			Date createdTimestamp, Integer createdBy) {
		super(id, user, password, createdTimestamp, createdBy);
	}

}
