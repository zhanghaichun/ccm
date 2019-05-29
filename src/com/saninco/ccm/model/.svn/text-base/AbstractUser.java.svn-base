package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractUser entity provides the base persistence definition of the User
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractUser implements java.io.Serializable {

	// Fields

	private Integer id;
	private Theme theme;
	private Theme randomtheme;
	private String firstName;
	private String lastName;
	private String title;
	private String address;
	private String primaryPhone;
	private String officePhone;
	private String cellPhone;
	private String faxNumber;
	private String email;
	private Integer supervisorUserId;
	private String userName;
	private String password;
	private Integer backupUserId;
	private String initials;
	private String systemUserFlag;
	private Integer loginFailureCount;
	private String requirePasswordChange;
	private String isLockOut;
	private String activeFlag;
	private Date lastLoginTimestamp;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String findPasswordFlag;
//	private String approveEmailFlag;
	
	private Set userCrossApps = new HashSet(0);
	private Set userDelegationsForToUserId = new HashSet(0);
	private Set userRoles = new HashSet(0);
	private Set userPreviledges = new HashSet(0);
	private Set userQuicklinks = new HashSet(0);
	private Set userDashlets = new HashSet(0);
	private Set userDelegationsForFromUserId = new HashSet(0);
	private Set userLoginPictures = new HashSet(0);
	private Set banAssignments = new HashSet(0);
	private Set passwordHistories = new HashSet(0);
	
	// Constructors

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @param initials
	 *            the initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/** default constructor */
	public AbstractUser() {
	}

	/** minimal constructor */
	public AbstractUser(Integer id) {
		this.id = id;
	}

	public String getFindPasswordFlag() {
		return findPasswordFlag;
	}

	public void setFindPasswordFlag(String findPasswordFlag) {
		this.findPasswordFlag = findPasswordFlag;
	}

	/** full constructor */
	public AbstractUser(Integer id, String firstName, String lastName,
			String initials, String address, String primaryPhone,
			String officePhone, String cellPhone, String faxNumber,
			String email, Integer supervisorUserId, String userName,
			String password, Integer loginFailureCount,
			String requirePasswordChange, String isLockOut,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set userCrossApps,
			Set userDelegationsForToUserId, Set userRoles,
			Set paymentHistories, Set userPreviledges, Set userQuicklinks,
			Set userDashlets, Set userDelegationsForFromUserId,
			Set userLoginPictures, Set banAssignments, Set passwordHistories,String approveEmailFlag
			) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.initials = initials;
		this.address = address;
		this.primaryPhone = primaryPhone;
		this.officePhone = officePhone;
		this.cellPhone = cellPhone;
		this.faxNumber = faxNumber;
		this.email = email;
		this.supervisorUserId = supervisorUserId;
		this.userName = userName;
		this.password = password;
		this.loginFailureCount = loginFailureCount;
		this.requirePasswordChange = requirePasswordChange;
		this.isLockOut = isLockOut;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.userCrossApps = userCrossApps;
		this.userDelegationsForToUserId = userDelegationsForToUserId;
		this.userRoles = userRoles;
		this.userPreviledges = userPreviledges;
		this.userQuicklinks = userQuicklinks;
		this.userDashlets = userDashlets;
		this.userDelegationsForFromUserId = userDelegationsForFromUserId;
		this.userLoginPictures = userLoginPictures;
		this.banAssignments = banAssignments;
		this.passwordHistories = passwordHistories;
//		this.approveEmailFlag = approveEmailFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the theme
	 */
	public com.saninco.ccm.model.Theme getTheme() {
		return theme;
	}

	/**
	 * @param theme
	 *            the theme to set
	 */
	public void setTheme(com.saninco.ccm.model.Theme theme) {
		this.theme = theme;
	}

	public Theme getRandomtheme() {
		return randomtheme;
	}

	public void setRandomtheme(Theme randomtheme) {
		this.randomtheme = randomtheme;
	}

	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @return the backupUserId
	 */
	public Integer getBackupUserId() {
		return backupUserId;
	}

	/**
	 * @param backupUserId
	 *            the backupUserId to set
	 */
	public void setBackupUserId(Integer backupUserId) {
		this.backupUserId = backupUserId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrimaryPhone() {
		return this.primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSupervisorUserId() {
		return this.supervisorUserId;
	}

	public void setSupervisorUserId(Integer supervisorUserId) {
		this.supervisorUserId = supervisorUserId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLoginFailureCount() {
		return this.loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public String getRequirePasswordChange() {
		return this.requirePasswordChange;
	}

	public void setRequirePasswordChange(String requirePasswordChange) {
		this.requirePasswordChange = requirePasswordChange;
	}

	public String getIsLockOut() {
		return this.isLockOut;
	}

	public void setIsLockOut(String isLockOut) {
		this.isLockOut = isLockOut;
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

	public Set getUserCrossApps() {
		return this.userCrossApps;
	}

	public void setUserCrossApps(Set userCrossApps) {
		this.userCrossApps = userCrossApps;
	}

	public Set getUserDelegationsForToUserId() {
		return this.userDelegationsForToUserId;
	}

	public void setUserDelegationsForToUserId(Set userDelegationsForToUserId) {
		this.userDelegationsForToUserId = userDelegationsForToUserId;
	}

	public Set getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set userRoles) {
		this.userRoles = userRoles;
	}

	public Set getUserPreviledges() {
		return this.userPreviledges;
	}

	public void setUserPreviledges(Set userPreviledges) {
		this.userPreviledges = userPreviledges;
	}

	public Set getUserQuicklinks() {
		return this.userQuicklinks;
	}

	public void setUserQuicklinks(Set userQuicklinks) {
		this.userQuicklinks = userQuicklinks;
	}

	public Set getUserDashlets() {
		return this.userDashlets;
	}

	public void setUserDashlets(Set userDashlets) {
		this.userDashlets = userDashlets;
	}

	public Set getUserDelegationsForFromUserId() {
		return this.userDelegationsForFromUserId;
	}

	public void setUserDelegationsForFromUserId(Set userDelegationsForFromUserId) {
		this.userDelegationsForFromUserId = userDelegationsForFromUserId;
	}

	public Set getUserLoginPictures() {
		return this.userLoginPictures;
	}

	public void setUserLoginPictures(Set userLoginPictures) {
		this.userLoginPictures = userLoginPictures;
	}

	/**
	 * @return the banAssignments
	 */
	public Set getBanAssignments() {
		return banAssignments;
	}

	/**
	 * @param banAssignments
	 *            the banAssignments to set
	 */
	public void setBanAssignments(Set banAssignments) {
		this.banAssignments = banAssignments;
	}

	public Set getPasswordHistories() {
		return this.passwordHistories;
	}

	public void setPasswordHistories(Set passwordHistories) {
		this.passwordHistories = passwordHistories;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the lastLoginTimestamp
	 */
	public Date getLastLoginTimestamp() {
		return lastLoginTimestamp;
	}

	/**
	 * @param lastLoginTimestamp
	 *            the lastLoginTimestamp to set
	 */
	public void setLastLoginTimestamp(Date lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	/**
	 * @return the activeFlag
	 */
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag
	 *            the activeFlag to set
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return the systemUserFlag
	 */
	public String getSystemUserFlag() {
		return systemUserFlag;
	}

	/**
	 * @param systemUserFlag
	 *            the systemUserFlag to set
	 */
	public void setSystemUserFlag(String systemUserFlag) {
		this.systemUserFlag = systemUserFlag;
	}
	

	public String toString() {
		return "AbstractUser ["
				+ (activeFlag != null ? "activeFlag=" + activeFlag + ", " : "")
				+ (address != null ? "address=" + address + ", " : "")
				+ (backupUserId != null ? "backupUserId=" + backupUserId + ", "
						: "")
				+ (cellPhone != null ? "cellPhone=" + cellPhone + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (faxNumber != null ? "faxNumber=" + faxNumber + ", " : "")
				+ (firstName != null ? "firstName=" + firstName + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (initials != null ? "initials=" + initials + ", " : "")
				+ (isLockOut != null ? "isLockOut=" + isLockOut + ", " : "")
				+ (lastLoginTimestamp != null ? "lastLoginTimestamp="
						+ lastLoginTimestamp + ", " : "")
				+ (lastName != null ? "lastName=" + lastName + ", " : "")
				+ (loginFailureCount != null ? "loginFailureCount="
						+ loginFailureCount + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (officePhone != null ? "officePhone=" + officePhone + ", "
						: "")
				+ (password != null ? "password=" + password + ", " : "")
				+ (primaryPhone != null ? "primaryPhone=" + primaryPhone + ", "
						: "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (requirePasswordChange != null ? "requirePasswordChange="
						+ requirePasswordChange + ", " : "")
				+ (supervisorUserId != null ? "supervisorUserId="
						+ supervisorUserId + ", " : "")
				+ (systemUserFlag != null ? "systemUserFlag=" + systemUserFlag
						+ ", " : "")
				+ (theme != null ? "theme=" + theme + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (userName != null ? "userName=" + userName : "") + "]";
	}

//	public String getApproveEmailFlag() {
//		return approveEmailFlag;
//	}
//
//	public void setApproveEmailFlag(String approveEmailFlag) {
//		this.approveEmailFlag = approveEmailFlag;
//	}

	

}