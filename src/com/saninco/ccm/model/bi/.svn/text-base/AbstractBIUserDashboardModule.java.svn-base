package com.saninco.ccm.model.bi;

/**
 * AbstractBIUserDashboardModule entity provides the base persistence definition
 * of the BIUserDashboardModule entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractBIUserDashboardModule implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 704574895346840423L;
	private Integer id;
	private String name;
	private BIUserDashboard BIUserDashboard;
	private BIDashboardModule BIDashboardModule;
	private String controlData;
	private String showPercentFlag;
	private Integer sortNo;

	// Constructors

	/** default constructor */
	public AbstractBIUserDashboardModule() {
	}

	/** full constructor */
	public AbstractBIUserDashboardModule(BIUserDashboard BIUserDashboard,
			BIDashboardModule BIDashboardModule, String controlData,
			Integer sortNo, String name, String showPercentFlag) {
		this.BIUserDashboard = BIUserDashboard;
		this.BIDashboardModule = BIDashboardModule;
		this.controlData = controlData;
		this.sortNo = sortNo;
		this.name = name;
		this.showPercentFlag = showPercentFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BIUserDashboard getBIUserDashboard() {
		return this.BIUserDashboard;
	}

	public void setBIUserDashboard(BIUserDashboard BIUserDashboard) {
		this.BIUserDashboard = BIUserDashboard;
	}

	public BIDashboardModule getBIDashboardModule() {
		return this.BIDashboardModule;
	}

	public void setBIDashboardModule(BIDashboardModule BIDashboardModule) {
		this.BIDashboardModule = BIDashboardModule;
	}

	public String getControlData() {
		return this.controlData;
	}

	public void setControlData(String controlData) {
		this.controlData = controlData;
	}

	public Integer getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowPercentFlag() {
		return showPercentFlag;
	}

	public void setShowPercentFlag(String showPercentFlag) {
		this.showPercentFlag = showPercentFlag;
	}

}