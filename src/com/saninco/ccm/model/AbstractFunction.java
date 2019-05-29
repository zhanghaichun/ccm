package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractFunction entity provides the base persistence definition of the
 * Function entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractFunction implements java.io.Serializable {

	// Fields

	private Integer id;
	private String functionName;
	private String functionActiveFlag;

	private Set roleFunctions = new HashSet(0);
	private Set userPreviledges = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractFunction() {
	}

	/** minimal constructor */
	public AbstractFunction(String functionName, String functionActiveFlag) {
		this.functionName = functionName;
		this.functionActiveFlag = functionActiveFlag;
	}

	/** full constructor */
	public AbstractFunction(String functionName, String functionActiveFlag,
			Set roleFunctions, Set userPreviledges) {
		this.functionName = functionName;
		this.functionActiveFlag = functionActiveFlag;
		this.roleFunctions = roleFunctions;
		this.userPreviledges = userPreviledges;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionActiveFlag() {
		return this.functionActiveFlag;
	}

	public void setFunctionActiveFlag(String functionActiveFlag) {
		this.functionActiveFlag = functionActiveFlag;
	}

	public Set getRoleFunctions() {
		return this.roleFunctions;
	}

	public void setRoleFunctions(Set roleFunctions) {
		this.roleFunctions = roleFunctions;
	}

	public Set getUserPreviledges() {
		return this.userPreviledges;
	}

	public void setUserPreviledges(Set userPreviledges) {
		this.userPreviledges = userPreviledges;
	}

}