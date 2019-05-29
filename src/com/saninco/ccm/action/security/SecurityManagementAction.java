package com.saninco.ccm.action.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.model.User;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.security.ISecurityManagementService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewSecurityVO;

/**
 * 
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 *
 */
public class SecurityManagementAction extends CcmActionSupport {

	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());

	private ICommonLookupService commonLookupService;
	private IQuicklinkService quicklinkService;
	private ISecurityManagementService securityManagementService;

	private String userId;
	private String selVendorId;
	private String upDelegationId;
	private String userPreviledgeId;
	private String userDelegationId;
	private ViewSecurityVO viewSecurityVO;

	private User user = new User();
	private User createdUser = new User();
	private User modifiedUser = new User();

	private List<MapEntryVO<String, String>> roleList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> userSupervisList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> userList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> functionList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> userAllList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<String> userRoleList = new ArrayList<String>();

	/**
	 * 
	 */
	public SecurityManagementAction() {
	}

	/*
	 * Go to search page.
	 * 
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@AllGranted(value="FUNCTION_4100")
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populateSecurityLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}

	/**
	 * Initialization of data capture
	 */
	private void populateSecurityLookupList() throws BPLException {
		logger.info("Enter method populateSecurityManagementLookupList.");
		if(userId != null){
			user = securityManagementService.getUser(user, Integer.parseInt(userId));
			createdUser = securityManagementService.getUser(createdUser, user.getCreatedBy());
			modifiedUser = securityManagementService.getUser(modifiedUser, user.getModifiedBy());
			userRoleList = securityManagementService.getUserRoleIdList(userRoleList, userId);
			this.userList = securityManagementService.getUserNotInUserId(userId);
		}
		this.userAllList = commonLookupService.getAnalysts();
		this.roleList = commonLookupService.getRole();
		this.vendorList = commonLookupService.getUserPreviledgedVendors();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method populateSecurityManagementLookupList.");
	}

	/**
	 * Get user previledged vendor list.
	 */
	public String getUserPreviledgedVendorList() throws Exception {
		logger.info("Enter method getUserPreviledgedVendorList.");
		String result = null;
		try {
			result = getUserPreviledgedVendorListCall();
		} catch (Exception e) {
			logger.error("getUserPreviledgedVendorList error: ", e);
		}
		this.writeOutputStream(result);
		logger.info("Exit method getUserPreviledgedVendorList.");
		return null;
	}

	/**
	 * get User Previledged Vendor List Call
	 */
	private String getUserPreviledgedVendorListCall() {
		logger.info("Enter method getUserPreviledgedVendorListCall.");
		String s = null;
		if (testWeb)
			s = "[{id:1,name:\"Bell Maliant\"},{id:2,name:\"Bell Ontario\"},{id:3,name:\"Metafore\"}]";
		else {
			try {
				s = this.commonLookupService.getUserPreviledgedVendorsJson();
			} catch (BPLException e) {
				logger.error("getUserPreviledgedVendorListCall error: ", e);
				s = "{error:\"getUserPreviledgedVendorListCall error: " + e.getMessage() + "\"}";
			}
		}
		logger.info("Exit method getUserPreviledgedVendorListCall.");
		return s;
	}

	/**
	 * Get the Delegation data 
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String searchDelegation() throws Exception {
		logger.info("Enter method searchDelegation.");
		String result = null;
		try {
			result = securityManagementService.searchDelegation(viewSecurityVO);
		} catch (Exception e) {
			logger.error("searchDelegation error: ", e);
			result = "{error:\"SearchDelegation error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchDelegation.");
		return null;
	}

	/**
	 * Get the data of VendorBan Previledge 
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String searchPreviledgeVendorBan() throws Exception {
		logger.info("Enter method searchPreviledgeVendorBan.");
		String result = null;
		try {
			result = securityManagementService.searchPreviledgeVensorBan(viewSecurityVO);
		} catch (Exception e) {
			logger.error("searchPreviledgeVendorBan error: ", e);
			result = "{error:\"searchPreviledgeVendorBan error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchPreviledgeVendorBan.");
		return null;
	}

	/**
	 * Get the number of pages in the VendorBan Delegation 
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String getDelegationTotalPageNo() throws Exception {
		logger.info("Enter method getDelegationTotalPageNo.");
		String result = null;
		try {
			result = securityManagementService.getDelegationTotalPageNo(viewSecurityVO);
		} catch (Exception e) {
			logger.error("getDelegationTotalPageNo error: ", e);
			result = "{error:\"getDelegationTotalPageNo error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getDelegationTotalPageNo.");
		return null;
	}
	
	/**
	 * verify UserName Exists
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String verifyUserNameExists() throws Exception {
		logger.info("Enter method verifyUserNameExists.");
		String result = null;
		try {
			result = securityManagementService.getVerifyUserNameExists(viewSecurityVO);
		} catch (Exception e) {
			logger.error("verifyUserNameExists error: ", e);
			result = "{error:\"verifyUserNameExistsServiceCall error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method verifyUserNameExists.");
		return null;
	}

	/**
	 * Get the number of pages in the VendorBan Previledge 
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String getPreviledgeVendorBanTotalPageNo() throws Exception {
		logger.info("Enter method getPreviledgeVendorBanTotalPageNo.");
		String result = null;
		try {
			result = securityManagementService.getPreviledgeVendorBanTotalPageNo(viewSecurityVO);
		} catch (Exception e) {
			logger.error("getPreviledgeVendorBanTotalPageNo error: ", e);
			result = "{error:\"getPreviledgeVendorBanTotalPageNo error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getPreviledgeVendorBanTotalPageNo.");
		return null;
	}
	
	/**
	 * update Radio button by user active_flag
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String updateRadio() throws Exception {
		logger.info("Enter method updateRadio.");
		String result = "{}";
		try {
			securityManagementService.updateUserActiveFlag(viewSecurityVO);
		} catch (RuntimeException e) {
			logger.error("updateRadio error: ", e);
			result = "{error:\"updateRadio error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateRadio.");
		return null;
	}

	/**
	 * Add Previledge Vend and Ban  ### viewSecurityVO(previledgeVendorId;previledgeBanId)
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String savePreviledgeVendorBan() throws Exception {
		logger.info("Enter method saveAssignmentVendorBan.");
		String result = "{}";
		try {
			securityManagementService.savePreviledgeVendorBan(viewSecurityVO);
		} catch (RuntimeException e) {
			logger.error("saveAssignmentVendorBan error: ", e);
			result = "{error:\"saveAssignmentVendorBan error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method saveAssignmentVendorBan.");
		return null;
	}

	/**
	 * Add a Delegation  ### viewSecurityVO(Start Date;End Date;To User Name)
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String saveDelegation() throws Exception {
		logger.info("Enter method saveDelegation.");
		String result = "{}";
		try {
			securityManagementService.saveDelegation(viewSecurityVO);
		} catch (RuntimeException e) {
			logger.error("saveDelegation error: ", e);
			result = "{error:\"saveDelegation error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method saveDelegation.");
		return null;
	}

	/**
	 * Remove Previledge Vend and Ban under userPreviledgeId
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String daletePreviledgeVendorBan() throws Exception {
		logger.info("Enter method daletePreviledgeVendorBan.");
		String result = "{}";
		try {
			securityManagementService.deletePreviledgeVendorBan(userPreviledgeId);
		} catch (RuntimeException e) {
			logger.error("daletePreviledgeVendorBan error: ", e);
			result = "{error:\"daletePreviledgeVendorBan error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method daletePreviledgeVendorBan.");
		return null;
	}

	/**
	 * Remove Delegation under upDelegationId
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String daleteDelegation() throws Exception {
		logger.info("Enter method daleteDelegation.");
		String result = "{}";
		try {
			securityManagementService.deleteDelegation(userDelegationId);
		} catch (RuntimeException e) {
			logger.error("daleteDelegation error: ", e);
			result = "{error:\"daleteDelegation error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method daleteDelegation.");
		return null;
	}

	/**
	 * Modify a user's password and role ### viewSecurityVO(roleString;password)
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String updateUserRoleAndPassword() throws Exception {
		logger.info("Enter method updateUserRoleAndPassword.");
		String result = "{}";
		try {
			securityManagementService.updateUserRoleAndPassword(viewSecurityVO);
		} catch (RuntimeException e) {
			logger.error("updateUserRoleAndPassword error: ", e);
			result = "{error:\"updateUserRoleAndPassword error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateUserRoleAndPassword.");
		return null;
	}
	
	/**
	 * Reset user table login_failure_count
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String resetFailureCount() throws Exception {
		logger.info("Enter method resetFailureCount.");
		String result = "{}";
		try {
			securityManagementService.updateUserByLoginFailureCount(userId);
		} catch (RuntimeException e) {
			logger.error("resetFailureCount error: ", e);
			result = "{error:\"resetFailureCount error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method resetFailureCount.");
		return null;
	}

	/**
	 * According to modify the Delegation upDelegationId ### viewSecurityVO(Start Date;End Date;To User Name)
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String upSaveDelegation() throws Exception {
		logger.info("Enter method upSaveDelegation.");
		String result = "{}";
		try {
			securityManagementService.updateDelegation(viewSecurityVO,upDelegationId);
		} catch (RuntimeException e) {
			logger.error("upSaveDelegation error: ", e);
			result = "{error:\"upSaveDelegation error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method upSaveDelegation.");
		return null;
	}
	
	/**
	 * Add Users
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String addUserRoleAndPassword() throws Exception {
		logger.info("Enter method addUserRoleAndPassword.");
		String result = "{}";
		try {
			securityManagementService.addUser(viewSecurityVO);
		} catch (RuntimeException e) {
			logger.error("addUserRoleAndPassword error: ", e);
			result = "{error:\"addUserRoleAndPassword error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method addUserRoleAndPassword.");
		return null;
	}
	
	/**
	 * The same data already exists.
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	 public String verifyPreviledgeData() throws Exception {
		logger.info("Enter method verifyPreviledgeData.");
		String result = null;
		try {
			result = securityManagementService.verifyPreviledgeDataService(viewSecurityVO);
		} catch (Exception e) {
			logger.error("verifyPreviledgeData error: ", e);
			result = "{error:\"verifyPreviledgeData error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method verifyPreviledgeData.");
		return null;
	}
	 
	 public String verifyOperatePermission() throws Exception {
		 logger.info("Enter method verifyOperatePermission.");
		 String result = null;
		 try {
			 result = securityManagementService.verifyOperatePermission(viewSecurityVO);
		 } catch (Exception e) {
			 logger.error("verifyOperatePermission error: ", e);
			 result = "{error:\"verifyPreviledgeData error: " + e.getMessage() + "\"}";
		 }
		 this.writeOutputStream(result);
		 logger.info("Exit method verifyOperatePermission.");
		 return null;
	 }

	public ISecurityManagementService getSecurityManagementService() {
		return securityManagementService;
	}

	public void setSecurityManagementService(ISecurityManagementService securityManagementService) {
		this.securityManagementService = securityManagementService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public User getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(User modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}

	public List<MapEntryVO<String, String>> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<MapEntryVO<String, String>> roleList) {
		this.roleList = roleList;
	}

	public List<MapEntryVO<String, String>> getUserList() {
		return userList;
	}

	public void setUserList(List<MapEntryVO<String, String>> userList) {
		this.userList = userList;
	}

	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public String getSelVendorId() {
		return selVendorId;
	}

	public void setSelVendorId(String selVendorId) {
		this.selVendorId = selVendorId;
	}

	public ViewSecurityVO getViewSecurityVO() {
		return viewSecurityVO;
	}

	public void setViewSecurityVO(ViewSecurityVO viewSecurityVO) {
		this.viewSecurityVO = viewSecurityVO;
	}

	public List<String> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<String> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public String getUserPreviledgeId() {
		return userPreviledgeId;
	}

	public void setUserPreviledgeId(String userPreviledgeId) {
		this.userPreviledgeId = userPreviledgeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserDelegationId() {
		return userDelegationId;
	}

	public void setUserDelegationId(String userDelegationId) {
		this.userDelegationId = userDelegationId;
	}

	public String getUpDelegationId() {
		return upDelegationId;
	}

	public void setUpDelegationId(String upDelegationId) {
		this.upDelegationId = upDelegationId;
	}

	public List<MapEntryVO<String, String>> getUserSupervisList() {
		return userSupervisList;
	}

	public void setUserSupervisList(
			List<MapEntryVO<String, String>> userSupervisList) {
		this.userSupervisList = userSupervisList;
	}

	public List<MapEntryVO<String, String>> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<MapEntryVO<String, String>> functionList) {
		this.functionList = functionList;
	}

	public List<MapEntryVO<String, String>> getUserAllList() {
		return userAllList;
	}

	public void setUserAllList(List<MapEntryVO<String, String>> userAllList) {
		this.userAllList = userAllList;
	}
	
	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}
	
}
