package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractCreatedReport entity provides the base persistence definition of the
 * CreatedReport entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractCreatedReport implements java.io.Serializable {

	// Fields

	private Integer id;
	private Report report;
	private String createdReportName;
	private String filePath;
	private String fileName;
	private String sharingFlag;
	private String sendEmail;
	private String email;
	private String parameterString;
	private String reportFormat;
	private String reportStatus;
	private Date statusTimestamp;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String keyWord;
	
	private Date fromDate;
	private Date toDate;
	private Integer analystId;
	private String onlyUser;
	
	private String downloadTimes;
	private Double effectiveHour;
	private String isLogin;
	
	// Add By Chao.Liu Date: 2010/11/03 PM
	private Integer pbanId;
	
	// Constructors

	/** default constructor */
	public AbstractCreatedReport() {
	}

	/** full constructor */
	public AbstractCreatedReport(Report report, String createdReportName, String keyWord,
			String filePath, String fileName, String sharingFlag,
			String sendEmail,String email, String parameterString, String reportFormat,
			String reportStatus, Date statusTimestamp, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag,String downloadTimes,Double effectiveHour,String isLogin) {
		this.report = report;
		this.createdReportName = createdReportName;
		this.keyWord = keyWord;
		this.filePath = filePath;
		this.fileName = fileName;
		this.sharingFlag = sharingFlag;
		this.sendEmail = sendEmail;
		this.email = email;
		this.parameterString = parameterString;
		this.reportFormat = reportFormat;
		this.reportStatus = reportStatus;
		this.statusTimestamp = statusTimestamp;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.downloadTimes = downloadTimes;
		this.effectiveHour = effectiveHour;
		this.isLogin = isLogin;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getCreatedReportName() {
		return this.createdReportName;
	}

	public void setCreatedReportName(String createdReportName) {
		this.createdReportName = createdReportName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSharingFlag() {
		return this.sharingFlag;
	}

	public void setSharingFlag(String sharingFlag) {
		this.sharingFlag = sharingFlag;
	}

	public String getSendEmail() {
		return this.sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getParameterString() {
		return this.parameterString;
	}

	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
	}

	public String getReportFormat() {
		return this.reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String getReportStatus() {
		return this.reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return this.modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String toString() {
		return "AbstractCreatedReport ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdReportName != null ? "createdReportName="
						+ createdReportName + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (filePath != null ? "filePath=" + filePath + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (parameterString != null ? "parameterString="
						+ parameterString + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (report != null ? "report=" + report + ", " : "")
				+ (reportFormat != null ? "reportFormat=" + reportFormat + ", "
						: "")
				+ (reportStatus != null ? "reportStatus=" + reportStatus + ", "
						: "")
				+ (sendEmail != null ? "sendEmail=" + sendEmail + ", " : "")
				+ (sharingFlag != null ? "sharingFlag=" + sharingFlag + ", "
						: "")
				+ (statusTimestamp != null ? "statusTimestamp="
						+ statusTimestamp : "") + "]";
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getAnalystId() {
		return analystId;
	}

	public void setAnalystId(Integer analystId) {
		this.analystId = analystId;
	}

	public String getOnlyUser() {
		return onlyUser;
	}

	public void setOnlyUser(String onlyUser) {
		this.onlyUser = onlyUser;
	}

	public Integer getPbanId() {
		return pbanId;
	}

	public void setPbanId(Integer pbanId) {
		this.pbanId = pbanId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDownloadTimes() {
		return downloadTimes;
	}

	public void setDownloadTimes(String downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	public Double getEffectiveHour() {
		return effectiveHour;
	}

	public void setEffectiveHour(Double effectiveHour) {
		this.effectiveHour = effectiveHour;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}
    
	
}