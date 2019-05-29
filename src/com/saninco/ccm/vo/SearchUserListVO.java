package com.saninco.ccm.vo;

import java.io.Serializable;

import com.saninco.ccm.util.SystemUtil;

public class SearchUserListVO extends SearchVO implements Serializable {
	private static final long serialVersionUID = -4891774932870661027L;

	private Integer uid;
	private String name;
	private String userName;
	private String initials;
	private String phone;
	private String email;

	private Integer backupUserId;
	private Integer supervisorId;

	private String createBeginDate;
	private String createdEndDate;
	private String userCreateWiPastNumOfDays;

	private String modifiedBeginDate;
	private String modifiedEndDate;
	private String userModifiedWiPastNumOfDays;

	private Integer currentUserId;
	private Integer suId;

	public SearchUserListVO() {
	}

	/*
	 * Getter And Setter
	 */

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	public Integer getBackupUserId() {
		return backupUserId;
	}

	public void setBackupUserId(Integer backupUserId) {
		this.backupUserId = backupUserId;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getCreateBeginDate() {
		return createBeginDate;
	}

	public void setCreateBeginDate(String createBeginDate) {
		this.createBeginDate = createBeginDate.trim();
	}

	public String getModifiedBeginDate() {
		return modifiedBeginDate;
	}

	public void setModifiedBeginDate(String modifiedBeginDate) {
		this.modifiedBeginDate = modifiedBeginDate.trim();
	}

	public String getUserCreateWiPastNumOfDays() {
		return userCreateWiPastNumOfDays;
	}

	public void setUserCreateWiPastNumOfDays(String userCreateWiPastNumOfDays) {
		this.userCreateWiPastNumOfDays = userCreateWiPastNumOfDays.trim();
	}

	public String getCreatedEndDate() {
		return createdEndDate;
	}

	public void setCreatedEndDate(String createdEndDate) {
		this.createdEndDate = createdEndDate.trim();
	}

	public String getModifiedEndDate() {
		return modifiedEndDate;
	}

	public void setModifiedEndDate(String modifiedEndDate) {
		this.modifiedEndDate = modifiedEndDate.trim();
	}

	public String getUserModifiedWiPastNumOfDays() {
		return userModifiedWiPastNumOfDays;
	}

	public void setUserModifiedWiPastNumOfDays(
			String userModifiedWiPastNumOfDays) {
		this.userModifiedWiPastNumOfDays = userModifiedWiPastNumOfDays.trim();
	}

	public Integer getCurrentUserId() {
		return SystemUtil.getCurrentUserId();
	}

	public void setCurrentUserId(Integer currentUserId) {
		this.currentUserId = currentUserId;
	}

	public Integer getSuId() {
		return suId;
	}

	public void setSuId(Integer suId) {
		this.suId = suId;
	}

	public String toString() {
		return "SearchUserListVO [backupUserId=" + backupUserId
				+ ", createBeginDate=" + createBeginDate + ", createdEndDate="
				+ createdEndDate + ", currentUserId=" + currentUserId
				+ ", email=" + email + ", initials=" + initials
				+ ", modifiedBeginDate=" + modifiedBeginDate
				+ ", modifiedEndDate=" + modifiedEndDate + ", name=" + name
				+ ", phone=" + phone + ", suId=" + suId + ", supervisorId="
				+ supervisorId + ", uid=" + uid
				+ ", userCreateWiPastNumOfDays=" + userCreateWiPastNumOfDays
				+ ", userModifiedWiPastNumOfDays="
				+ userModifiedWiPastNumOfDays + ", userName=" + userName + "]";
	}

}
