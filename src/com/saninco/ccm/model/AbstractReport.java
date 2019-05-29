package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractReport entity provides the base persistence definition of the Report
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractReport implements java.io.Serializable {

	// Fields

	private Integer id;
	private String reportName;
	private String frequency;
	private Integer runDay;
	private Date runTime;
	private String userRunFlag;
	private String autoRunFlag;
	private String autoRunParameterString;
	private String sendEmail;
	private String reportFormat;
	private String recActiveFlag;
	
	// Add By Chao.Liu Date: 2010/11/03 PM
	private String pbanId;
	private String fromDate;
	private String toDate;
	private String analystId;
	private String onlyUser;
	
	private Set rtagReports = new HashSet(0);
	private Set createdReports = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractReport() {
	}

	/** full constructor */
	public AbstractReport(String reportName, String frequency, Integer runDay,
			Date runTime, String userRunFlag, String autoRunFlag,
			String autoRunParameterString, String sendEmail,
			String reportFormat, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			Set rtagReports, Set createdReports) {
		this.reportName = reportName;
		this.frequency = frequency;
		this.runDay = runDay;
		this.runTime = runTime;
		this.userRunFlag = userRunFlag;
		this.autoRunFlag = autoRunFlag;
		this.autoRunParameterString = autoRunParameterString;
		this.sendEmail = sendEmail;
		this.reportFormat = reportFormat;
		this.recActiveFlag = recActiveFlag;
		this.rtagReports = rtagReports;
		this.createdReports = createdReports;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getRunDay() {
		return this.runDay;
	}

	public void setRunDay(Integer runDay) {
		this.runDay = runDay;
	}

	public Date getRunTime() {
		return this.runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	public String getUserRunFlag() {
		return this.userRunFlag;
	}

	public void setUserRunFlag(String userRunFlag) {
		this.userRunFlag = userRunFlag;
	}

	public String getAutoRunFlag() {
		return this.autoRunFlag;
	}

	public void setAutoRunFlag(String autoRunFlag) {
		this.autoRunFlag = autoRunFlag;
	}

	public String getAutoRunParameterString() {
		return this.autoRunParameterString;
	}

	public void setAutoRunParameterString(String autoRunParameterString) {
		this.autoRunParameterString = autoRunParameterString;
	}

	public String getSendEmail() {
		return this.sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getReportFormat() {
		return this.reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public Set getRtagReports() {
		return this.rtagReports;
	}

	public void setRtagReports(Set rtagReports) {
		this.rtagReports = rtagReports;
	}

	public Set getCreatedReports() {
		return this.createdReports;
	}

	public void setCreatedReports(Set createdReports) {
		this.createdReports = createdReports;
	}

	public String toString() {
		return "AbstractReport ["
				+ (autoRunFlag != null ? "autoRunFlag=" + autoRunFlag + ", "
						: "")
				+ (autoRunParameterString != null ? "autoRunParameterString="
						+ autoRunParameterString + ", " : "")
				+ (frequency != null ? "frequency=" + frequency + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (reportFormat != null ? "reportFormat=" + reportFormat + ", "
						: "")
				+ (reportName != null ? "reportName=" + reportName + ", " : "")
				+ (runDay != null ? "runDay=" + runDay + ", " : "")
				+ (runTime != null ? "runTime=" + runTime + ", " : "")
				+ (sendEmail != null ? "sendEmail=" + sendEmail + ", " : "")
				+ (userRunFlag != null ? "userRunFlag=" + userRunFlag : "")
				+ "]";
	}

	public String getPbanId() {
		return pbanId;
	}

	public void setPbanId(String pbanId) {
		this.pbanId = pbanId;
	}

	public String getAnalystId() {
		return analystId;
	}

	public void setAnalystId(String analystId) {
		this.analystId = analystId;
	}

	public String getOnlyUser() {
		return onlyUser;
	}

	public void setOnlyUser(String onlyUser) {
		this.onlyUser = onlyUser;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}