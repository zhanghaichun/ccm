package com.saninco.ccm.model;

public class AbstractTargetChargeType implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 876235001552295979L;
	private Integer id;
	private String chargeTypeName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public AbstractTargetChargeType(Integer id, String chargeTypeName) {
		this.id = id;
		this.chargeTypeName = chargeTypeName;
	}
	public AbstractTargetChargeType(){
		
	}
}
