package com.saninco.ccm.model.bi;

public abstract class AbstractBIQuoteVendorResponseStatus implements java.io.Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3412969916143419974L;
	private Integer id;
	private String quoteStatusName;
	
	/** default constructor */
	public AbstractBIQuoteVendorResponseStatus() {
	}

	/** full constructor */
	public AbstractBIQuoteVendorResponseStatus(String quoteStatusName) {
		
		this.quoteStatusName = quoteStatusName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuoteStatusName() {
		return quoteStatusName;
	}

	public void setQuoteStatusName(String quoteStatusName) {
		this.quoteStatusName = quoteStatusName;
	}
}
