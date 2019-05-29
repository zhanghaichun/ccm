package com.saninco.ccm.model.bi;

/**
 * AbstractBIVendorSpendDashboard entity provides the base persistence
 * definition of the BIVendorSpendDashboard entity. @author MyEclipse
 * Persistence Tools
 */

public abstract class AbstractBIVendorSpendDashboard implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private BIAuditReference BIAuditReference;
	private BIBudget BIBudget;
	private BIProvince BIProvince;
	private BIVendor BIVendor;
	private BIProduct BIProduct;
	private String yearAndMonth;
	private String quarter;
	private Double amount;
	private String year;

	// Constructors

	/** default constructor */
	public AbstractBIVendorSpendDashboard() {
	}

	/** full constructor */
	public AbstractBIVendorSpendDashboard(BIAuditReference BIAuditReference,
			BIBudget BIBudget, BIProvince BIProvince, BIVendor BIVendor,
			BIProduct BIProduct, String yearAndMonth, String quarter,
			Double amount, String year) {
		this.BIAuditReference = BIAuditReference;
		this.BIBudget = BIBudget;
		this.BIProvince = BIProvince;
		this.BIVendor = BIVendor;
		this.BIProduct = BIProduct;
		this.yearAndMonth = yearAndMonth;
		this.quarter = quarter;
		this.amount = amount;
		this.year = year;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BIAuditReference getBIAuditReference() {
		return this.BIAuditReference;
	}

	public void setBIAuditReference(BIAuditReference BIAuditReference) {
		this.BIAuditReference = BIAuditReference;
	}

	public BIBudget getBIBudget() {
		return this.BIBudget;
	}

	public void setBIBudget(BIBudget BIBudget) {
		this.BIBudget = BIBudget;
	}

	public BIProvince getBIProvince() {
		return this.BIProvince;
	}

	public void setBIProvince(BIProvince BIProvince) {
		this.BIProvince = BIProvince;
	}

	public BIVendor getBIVendor() {
		return this.BIVendor;
	}

	public void setBIVendor(BIVendor BIVendor) {
		this.BIVendor = BIVendor;
	}

	public BIProduct getBIProduct() {
		return this.BIProduct;
	}

	public void setBIProduct(BIProduct BIProduct) {
		this.BIProduct = BIProduct;
	}

	public String getYearAndMonth() {
		return this.yearAndMonth;
	}

	public void setYearAndMonth(String yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}

	public String getQuarter() {
		return this.quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}