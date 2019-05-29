package com.saninco.ccm.model;

import java.util.Set;

/**
 * Theme entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Theme extends AbstractTheme implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Theme() {
	}

	/** full constructor */
	public Theme(String themeName, String themePath, Set users) {
		super(themeName, themePath, users);
	}

}
