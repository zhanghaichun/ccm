package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Function;
import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.SecurityIp;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserDelegation;
import com.saninco.ccm.model.UserPrivilege;
import com.saninco.ccm.model.UserRole;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.vo.ViewSecurityVO;

/**
 * 
 * (Optimization of complete by xinyu.Liu)
 *
 */
public interface ISecurityManagementDao {
	
	public long getNumberOfUserName(ViewSecurityVO viewSecurityVO);
	
	public long getNumberOfUserPrivilege(ViewSecurityVO viewSecurityVO);
	
	public List findByProperty(String propertyName, Object value,String propertyName2, Object value2,String propertyName3, Object value3,String propertyName4, Object value4);
	
	public User findUserById(Integer id);
	
	public Ban findBanById(Integer id);
	
	public Vendor findVendorById(Integer id);
	
	public UserPrivilege findUserPrevilegeById(Integer id);
	
	public UserPrivilege findUserPrivilegeById(Integer id);
	
	public List<String> getUserRoleId(int userId);
	
	public List<Role> findRoleAll();
	
	public List<User> findUserNotInUserId(String userId);
	
	public List<String> getVerderBanByUserIdPreviledge(ViewSecurityVO viewSecurityVO);
	
	public long getNumberOfVendorBanPreviledge(ViewSecurityVO viewSecurityVO);
	
	public void savePrevilege(UserPrivilege transientInstance);
	
	public void savePrivilege(UserPrivilege transientInstance);
	
	public List<String> getDelegationByUserId(ViewSecurityVO viewSecurityVO);
	
	public long getNumberOfDelegation(ViewSecurityVO viewSecurityVO);
	
	public void saveDelegation(UserDelegation transientInstance);
	
	public UserDelegation findDelegationById(Integer id);
	
	public void saveUserRole(UserRole transientInstance);

	public List<String> findUserRoleByUserIdAndRoleId(Integer userId, Integer roleId, boolean bo);
	
	public Role findRoleById(Integer id);
	
	public UserRole findUserRoleById(Integer id);
	
	public void saveUser(User transientInstance);
	
	public Function findFunctionById(Integer id);
	
	public UserPrivilege findUserPrivilegeByBan(Ban b) ;
	
	public UserPrivilege findUserPrivilegeByVendor(Vendor v) ;
	
	public UserPrivilege findUserPrivilege() ;
	
	public List<SecurityIp> getSecurityIPList();
	
	public String searchBBLoginFlag();

	public String verifyOperatePermission(ViewSecurityVO viewSecurityVO);
}
