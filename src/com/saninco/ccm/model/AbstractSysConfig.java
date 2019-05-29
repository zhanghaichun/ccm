package com.saninco.ccm.model;

/**
 * AbstractSysConfig entity provides the base persistence definition of the
 * SysConfig entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractSysConfig implements java.io.Serializable {

	// Fields

	private Integer id;
	private String parameter;
	private String value;

	// Constructors

	/** default constructor */
	public AbstractSysConfig() {
	}

	/** minimal constructor */
	public AbstractSysConfig(String parameter) {
		this.parameter = parameter;
	}

	/** full constructor */
	public AbstractSysConfig(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return "AbstractSysConfig [" + (id != null ? "id=" + id + ", " : "")
				+ (parameter != null ? "parameter=" + parameter + ", " : "")
				+ (value != null ? "value=" + value : "") + "]";
	}

}