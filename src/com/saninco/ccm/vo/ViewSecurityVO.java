/**
 * 
 */
package com.saninco.ccm.vo;

import java.io.Serializable;

/**
 * @author xinyu.Liu
 */
public class ViewSecurityVO extends SearchVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891774932870661027L;

	private String password;
	private String lockOut;
	private String requirePasswordChange;

	// Basic information data users
	private String firstName;
	private String lastName;
	private String address;
	private String primaryPhone;
	private String officePhone;
	private String cellPlon;
	private String faxNumber;
	private String email;
	private String supervisId;
	private String userName;
	private String backupUserId;
	private String initials;
	private String activeFlag;
//	private String approveEmailFlag;
	private String functionId;

	// role list
	private String roleString;

	private String userId;
	// Delegation to UserId
	private String toUserId;

	// Previledge Vendor and Ban
	private String previledgeVendorId;
	private String previledgeBanId;

	// Delegation Date
	private String delegationDateStartsOn;
	private String delegationDateEndsOn;

	public String calDelegationDateStartsOn() {
		String r = null;
		if (this.delegationDateStartsOn != null)
			r = "'" + this.delegationDateStartsOn + "'";
		return r;
	}

	public String calDelegationDateEndsOn() {
		String r = null;
		if (this.delegationDateEndsOn != null)
			r = "'" + this.delegationDateEndsOn + "'";
		return r;
	}

	public String getPreviledgeVendorId() {
		return previledgeVendorId;
	}

	public void setPreviledgeVendorId(String previledgeVendorId) {
		this.previledgeVendorId = previledgeVendorId;
	}

	public String getPreviledgeBanId() {
		return previledgeBanId;
	}

	public void setPreviledgeBanId(String previledgeBanId) {
		this.previledgeBanId = previledgeBanId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDelegationDateStartsOn() {
		return delegationDateStartsOn;
	}

	public void setDelegationDateStartsOn(String delegationDateStartsOn) {
		this.delegationDateStartsOn = delegationDateStartsOn;
	}

	public String getDelegationDateEndsOn() {
		return delegationDateEndsOn;
	}

	public void setDelegationDateEndsOn(String delegationDateEndsOn) {
		this.delegationDateEndsOn = delegationDateEndsOn;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLockOut() {
		return lockOut;
	}

	public void setLockOut(String lockOut) {
		this.lockOut = lockOut;
	}

	public String getRequirePasswordChange() {
		return requirePasswordChange;
	}

	public void setRequirePasswordChange(String requirePasswordChange) {
		this.requirePasswordChange = requirePasswordChange;
	}

	public String getRoleString() {
		return roleString;
	}

	public void setRoleString(String roleString) {
		this.roleString = roleString;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getCellPlon() {
		return cellPlon;
	}

	public void setCellPlon(String cellPlon) {
		this.cellPlon = cellPlon;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSupervisId() {
		return supervisId;
	}

	public void setSupervisId(String supervisId) {
		this.supervisId = supervisId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBackupUserId() {
		return backupUserId;
	}

	public void setBackupUserId(String backupUserId) {
		this.backupUserId = backupUserId;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String toString() {
		return "ViewSecurityVO [activeFlag=" + activeFlag + ", address="
				+ address + ", backupUserId=" + backupUserId + ", cellPlon="
				+ cellPlon + ", delegationDateEndsOn=" + delegationDateEndsOn
				+ ", delegationDateStartsOn=" + delegationDateStartsOn
				+ ", email=" + email + ", faxNumber=" + faxNumber
				+ ", firstName=" + firstName + ", functionId=" + functionId
				+ ", initials=" + initials + ", lastName=" + lastName
				+ ", lockOut=" + lockOut + ", officePhone=" + officePhone
				+ ", password=" + password + ", previledgeBanId="
				+ previledgeBanId + ", previledgeVendorId="
				+ previledgeVendorId + ", primaryPhone=" + primaryPhone
				+ ", requirePasswordChange=" + requirePasswordChange
				+ ", roleString=" + roleString + ", supervisId=" + supervisId
				+ ", toUserId=" + toUserId + ", userId=" + userId
				+ ", userName=" + userName + "]";
	}

//	public String getApproveEmailFlag() {
//		return approveEmailFlag;
//	}
//
//	public void setApproveEmailFlag(String approveEmailFlag) {
//		this.approveEmailFlag = approveEmailFlag;
//	}

}
