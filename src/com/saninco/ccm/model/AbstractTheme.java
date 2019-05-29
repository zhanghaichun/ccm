package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractTheme entity provides the base persistence definition of the Theme
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTheme implements java.io.Serializable {

	// Fields

	private Integer id;
	private String themeName;
	private String themePath;

	private Set users = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractTheme() {
	}

	/** full constructor */
	public AbstractTheme(String themeName, String themePath, Set users) {
		this.themeName = themeName;
		this.themePath = themePath;
		this.users = users;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getThemeName() {
		return this.themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getThemePath() {
		return this.themePath;
	}

	public void setThemePath(String themePath) {
		this.themePath = themePath;
	}

	public Set getUsers() {
		return this.users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

	public String toString() {
		return "AbstractTheme [" + (id != null ? "id=" + id + ", " : "")
				+ (themeName != null ? "themeName=" + themeName + ", " : "")
				+ (themePath != null ? "themePath=" + themePath : "") + "]";
	}

}