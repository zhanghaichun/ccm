package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.saninco.ccm.util.Constants;

/**
 * User entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class User extends AbstractUser implements UserDetails, java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GrantedAuthority[] authorities;
	private String[] assignedDepartments;
	private String roleName;
	private String departmentNameString;
	//Added by Joe.Yang
	private String ipAddress;
	
	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Integer id) {
		super(id);
	}

	/** full constructor */
	public User(Integer id, String firstName, String lastName,String initials,
			String address, String primaryPhone, String officePhone,String title,
			String cellPhone, String faxNumber, String email,
			Integer supervisorUserId, String userName, String password,
			Integer loginFailureCount, String requirePasswordChange,
			String isLockOut,Date last_login_timestamp, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			Set userCrossApps, Set userDelegationsForToUserId, Set userRoles,
			Set paymentHistories, Set userPreviledges, Set userQuicklinks,
			Set userDashlets, Set userDelegationsForFromUserId,
			Set userLoginPictures, Set banAssignments, Set passwordHistories,String approveEmailFlag) {
		super(id,  firstName, lastName, initials, address, primaryPhone,
				officePhone, cellPhone, faxNumber, email, supervisorUserId,
				userName, password, loginFailureCount, requirePasswordChange,
				isLockOut, createdTimestamp, createdBy, modifiedTimestamp,
				modifiedBy, recActiveFlag, userCrossApps,
				userDelegationsForToUserId, userRoles, paymentHistories,
				userPreviledges, userQuicklinks, userDashlets,
				userDelegationsForFromUserId, userLoginPictures,
				banAssignments, passwordHistories, approveEmailFlag);
	}

	public boolean isAccountLocked() {
		return Constants.DB_FLAG_YES.equalsIgnoreCase(this.getIsLockOut());
	}

	
	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	public GrantedAuthority[] getAuthorities() {
		return authorities;
	}

	public String getUsername() {
		return getUserName();
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return ! isAccountLocked();
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public String[] getAssignedDepartments() {
		return assignedDepartments;
	}

	public void setAssignedDepartments(String[] assignedDepartments) {
		this.assignedDepartments = assignedDepartments;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the departmentNameString
	 */
	public String getDepartmentNameString() {
		return departmentNameString;
	}

	/**
	 * @param departmentNameString the departmentNameString to set
	 */
	public void setDepartmentNameString(String departmentNameString) {
		this.departmentNameString = departmentNameString;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	
}
