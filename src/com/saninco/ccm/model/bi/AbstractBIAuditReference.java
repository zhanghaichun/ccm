package com.saninco.ccm.model.bi;


/**
 * AbstractBIAuditReference entity provides the base persistence definition of
 * the BIAuditReference entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBIAuditReference implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6493770996001421231L;
	private Integer id;
	private String name;
	private String referenceTypeName;
	private String referenceId;

	// Constructors

	/** default constructor */
	public AbstractBIAuditReference() {
	}

	/** full constructor */
	public AbstractBIAuditReference(String name, String referenceTypeName,
			String referenceId) {
		this.name = name;
		this.referenceTypeName = referenceTypeName;
		this.referenceId = referenceId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReferenceTypeName() {
		return this.referenceTypeName;
	}

	public void setReferenceTypeName(String referenceTypeName) {
		this.referenceTypeName = referenceTypeName;
	}

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

}