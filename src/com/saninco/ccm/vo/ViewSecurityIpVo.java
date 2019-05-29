package com.saninco.ccm.vo;

import java.io.Serializable;

public class ViewSecurityIpVo extends SearchVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String securityId;
	private String securityIpA;
	private String securityIpB;
	private String securityStartDate;
	private String securityEndDate;
	private String securityUserName;
	private String securityUserId;
	private String activeFlag;
	private String ipRangeFlag;
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getSecurityIpA() {
		return securityIpA;
	}
	public void setSecurityIpA(String securityIpA) {
		this.securityIpA = securityIpA;
	}
	public String getSecurityIpB() {
		return securityIpB;
	}
	public void setSecurityIpB(String securityIpB) {
		this.securityIpB = securityIpB;
	}
	public String getSecurityStartDate() {
		return securityStartDate;
	}
	public void setSecurityStartDate(String securityStartDate) {
		this.securityStartDate = securityStartDate;
	}
	public String getSecurityEndDate() {
		return securityEndDate;
	}
	public void setSecurityEndDate(String securityEndDate) {
		this.securityEndDate = securityEndDate;
	}
	public String getSecurityUserId() {
		return securityUserId;
	}
	public void setSecurityUserId(String securityUserId) {
		this.securityUserId = securityUserId;
	}
	public String getSecurityUserName() {
		return securityUserName;
	}
	public void setSecurityUserName(String securityUserName) {
		this.securityUserName = securityUserName;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getIpRangeFlag() {
		return ipRangeFlag;
	}
	public void setIpRangeFlag(String ipRangeFlag) {
		this.ipRangeFlag = ipRangeFlag;
	}
	
}
