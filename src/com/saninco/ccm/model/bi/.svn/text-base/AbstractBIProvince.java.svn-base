package com.saninco.ccm.model.bi;


/**
 * AbstractBIProvince entity provides the base persistence definition of the
 * BIProvince entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBIProvince implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2553298853713316433L;
	private Integer id;
	private String provinceName;
	private String provinceAcronym;

	// Constructors

	/** default constructor */
	public AbstractBIProvince() {
	}

	/** minimal constructor */
	public AbstractBIProvince(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractBIProvince(Integer id, String provinceName,
			String provinceAcronym) {
		this.id = id;
		this.provinceName = provinceName;
		this.provinceAcronym = provinceAcronym;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceAcronym() {
		return this.provinceAcronym;
	}

	public void setProvinceAcronym(String provinceAcronym) {
		this.provinceAcronym = provinceAcronym;
	}

}