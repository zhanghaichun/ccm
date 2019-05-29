/**
 * 
 */
package com.saninco.ccm.service.security;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewSecurityVO;

/**
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 */
public interface ISecurityManagementService {
	
	public String verifyPreviledgeDataService(ViewSecurityVO viewSecurityVO) throws BPLException;
	
	public String getVerifyUserNameExists(ViewSecurityVO viewSecurityVO) throws BPLException;
	
	public void updateUserActiveFlag(ViewSecurityVO viewSecurityVO);

	public User getUser(User user, Integer id) throws BPLException;
	
	public void updateUserByLoginFailureCount(String userId);
	
	public List<Role> getRole() throws BPLException;
	
	public List<String> getUserRoleIdList(List l, String userId) throws BPLException;
	
	public String searchPreviledgeVensorBan(ViewSecurityVO viewSecurityVO) throws BPLException;
	
	public String getPreviledgeVendorBanTotalPageNo(ViewSecurityVO viewSecurityVO) throws BPLException;
	
	public void saveDelegation(ViewSecurityVO viewSecurityVO);
	
	public void updateDelegation(ViewSecurityVO viewSecurityVO,String upDelegationId) throws BPLException;
	
	public void savePreviledgeVendorBan(ViewSecurityVO viewSecurityVO);
	
	public void deleteDelegation(String userDelegationId);
	
	public void deletePreviledgeVendorBan(String userPreviledgeId);
	
	public List<MapEntryVO<String, String>> getUserNotInUserId(String userId) throws BPLException;
	
	public String searchDelegation(ViewSecurityVO viewSecurityVO) throws BPLException;
	
	public String getDelegationTotalPageNo(ViewSecurityVO viewSecurityVO) throws BPLException;
	
	public void updateUserRoleAndPassword(ViewSecurityVO viewSecurityVO);
	
	public void addUser(ViewSecurityVO viewSecurityVO);
	
	public Map checkIpSecurity(String ip,String userName);
	
	public String searchBBLoginFlag();

	public String verifyOperatePermission(ViewSecurityVO viewSecurityVO);

}
