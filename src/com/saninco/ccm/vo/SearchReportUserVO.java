package com.saninco.ccm.vo;

import java.io.Serializable;

import com.saninco.ccm.util.SystemUtil;

public class SearchReportUserVO extends SearchVO implements Serializable {
	private static final long serialVersionUID = -4891774932870661027L;

	private Integer reportId;
	private String createdReportNameNew;
	private String reportFormatNew;
	private String sendEmailNew;
	private String sharingNew;
	private String parameterString;
	private String onlyUser;
	private String fdate;
	private String tdate;
	private String keyWord;
	private String email;
	private Integer curUid;
	private Integer analystId;
	
	private Integer banId;
	private Integer pBanId;

	private String strJson;
	
	private String isLogin;
	private String effectiveHour;
	private String downloadTimes;
	

	public SearchReportUserVO() {
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getCurUid() {
		return SystemUtil.getCurrentUserId();
	}

	public String getCreatedReportNameNew() {
		String s = createdReportNameNew;
		if(s == null || s.equals("")){
			return s;
		}else{
			s = s.replaceAll("\\\\", "\\\\\\\\");
			s = s.replaceAll("'", "\\\\'");
			if(s.indexOf(".csv") == -1){
				s = s+".csv";
			}
			return s;
		}
	}

	public void setCreatedReportNameNew(String createdReportNameNew) {
		this.createdReportNameNew = createdReportNameNew.trim();
	}

	public String getReportFormatNew() {
		return reportFormatNew;
	}

	public void setReportFormatNew(String reportFormatNew) {
		this.reportFormatNew = reportFormatNew;
	}

	public String getSendEmailNew() {
		return sendEmailNew;
	}

	public void setSendEmailNew(String sendEmailNew) {
		this.sendEmailNew = sendEmailNew;
	}

	public String getSharingNew() {
		return sharingNew;
	}

	public void setSharingNew(String sharingNew) {
		this.sharingNew = sharingNew;
	}

	public String getParameterString() {
		return parameterString;
	}

	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
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

	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public Integer getBanId() {
		return banId;
	}

	public void setBanId(Integer banId) {
		this.banId = banId;
	}

	public Integer getPBanId() {
		return pBanId;
	}

	public void setPBanId(Integer banId) {
		pBanId = banId;
	}

	public String toString() {
		return "SearchReportUserVO [createdReportNameNew="
				+ createdReportNameNew + ", isLogin=" + isLogin+ ", effectiveHour=" + effectiveHour+ ", downloadTimes=" + downloadTimes+ ", curUid=" + curUid
				+ ", parameterString=" + parameterString + ", reportFormatNew="
				+ reportFormatNew + ", reportId=" + reportId
				+ ", sendEmailNew=" + sendEmailNew + ", email=" + email + ", sharingNew="
				+ sharingNew + "]";
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public String getStrJson() {
		return strJson;
	}

	public void setStrJson(String strJson) {
		this.strJson = strJson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getEffectiveHour() {
		return effectiveHour;
	}

	public void setEffectiveHour(String effectiveHour) {
		this.effectiveHour = effectiveHour;
	}

	public String getDownloadTimes() {
		return downloadTimes;
	}

	public void setDownloadTimes(String downloadTimes) {
		this.downloadTimes = downloadTimes;
	}
	

}
