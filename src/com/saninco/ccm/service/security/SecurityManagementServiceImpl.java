/**
 * 
 */
package com.saninco.ccm.service.security;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ISecurityManagementDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.SecurityIp;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserDelegation;
import com.saninco.ccm.model.UserPrivilege;
import com.saninco.ccm.model.UserRole;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.EhcacheUtils;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewSecurityVO;

/**
 * 
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class SecurityManagementServiceImpl implements ISecurityManagementService {

	private final Logger logger = Logger.getLogger(this.getClass());

	private ISecurityManagementDao securityManagementDao;

	public SecurityManagementServiceImpl() {
	}
	
	/**
	 * Verify the user name exists
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String getVerifyUserNameExists(ViewSecurityVO viewSecurityVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Verify the user name exists",viewSecurityVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = securityManagementDao.getNumberOfUserName(viewSecurityVO);
			sb.append("{count:");
			sb.append(count);
			sb.append("}");
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString(); 
	}
	
	/**
	 * update User active_flag
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void updateUserActiveFlag(ViewSecurityVO viewSecurityVO){
		logger.info(CcmFormat.formatEnterLog("update User ActiveFlag",viewSecurityVO));
		User user = new User();
		try {
			user = securityManagementDao.findUserById(Integer.parseInt(viewSecurityVO.getUserId()));
			user.setActiveFlag(viewSecurityVO.getActiveFlag());
			user.setModifiedBy(SystemUtil.getCurrentUserId());
			user.setModifiedTimestamp(new Date());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Reset user table login_failure_count = 0
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void updateUserByLoginFailureCount(String userId){
		logger.info(CcmFormat.formatEnterLog("update User LoginFailureCount = 0",userId));
		User user = new User();
		try {
			user = securityManagementDao.findUserById(Integer.parseInt(userId));
			user.setLoginFailureCount(0);
			user.setModifiedBy(SystemUtil.getCurrentUserId());
			user.setModifiedTimestamp(new Date());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Add Users
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void addUser(ViewSecurityVO vvo) {
		logger.info(CcmFormat.formatEnterLog("Add User Data",vvo));
		User user = new User();
		Role role = new Role();
		try {
			//add user Data 
			user.setFirstName(vvo.getFirstName());
			user.setLastName(vvo.getLastName());
			user.setAddress(vvo.getAddress());
			user.setPrimaryPhone(vvo.getPrimaryPhone());
			user.setOfficePhone(vvo.getOfficePhone());
			user.setCellPhone(vvo.getCellPlon());
			user.setFaxNumber(vvo.getFaxNumber());
			user.setEmail(vvo.getEmail());
			user.setInitials(vvo.getInitials());
			user.setUserName(vvo.getUserName());
			
			if(vvo.getSupervisId() != null) user.setSupervisorUserId(Integer.parseInt(vvo.getSupervisId()));
			//modified by xinyu.Liu on 2010.7.16 for CCM-308
			if(vvo.getBackupUserId() != null) user.setBackupUserId(Integer.parseInt(vvo.getBackupUserId()));
			
			user.setPassword(CcmUtil.encoderPassword(vvo.getPassword()));
			user.setIsLockOut(vvo.getLockOut());
			user.setRequirePasswordChange(vvo.getRequirePasswordChange());
			user.setActiveFlag("Y");
			
			user.setCreatedBy(SystemUtil.getCurrentUserId());
			user.setCreatedTimestamp(new Date());
			user.setModifiedBy(SystemUtil.getCurrentUserId());
			user.setModifiedTimestamp(new Date());
			user.setRecActiveFlag("Y");
			user.setSystemUserFlag("N");
			securityManagementDao.saveUser(user);
			
			//Add user Role
			if(vvo.getRoleString() != null){
				String roleString = vvo.getRoleString();
				String[] ro = roleString.split(",");
				
				//Loop through the preservation userRole
				for(int i = 0; i < ro.length; i++){
					UserRole userRole = new UserRole();
					role = securityManagementDao.findRoleById(Integer.parseInt(ro[i]));
					userRole.setUser(user);
					userRole.setRole(role);
					userRole.setCreatedBy(SystemUtil.getCurrentUserId());
					userRole.setCreatedTimestamp(new Date());
					userRole.setModifiedBy(SystemUtil.getCurrentUserId());
					userRole.setModifiedTimestamp(new Date());
					userRole.setRecActiveFlag("Y");
					securityManagementDao.saveUserRole(userRole);
				}
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * Array of strings into a list
	 */
	private List<String> getListByString(String[] str) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length; i++) {
			list.add(str[i]);
		}
		logger.info(CcmFormat.formatExitLog());
		return list;
	}

	/**
	 * Remove the second list of duplicate
	 */
	private List<String> removeDuplicate(List<String> pastList, List<String> nowList) {
		List<String> l1 = new ArrayList<String>(pastList);
		//1,3
		List<String> l2 = new ArrayList<String>(nowList);
			
		int b = l2.size();
		for (int i = 0; i < l1.size(); i++) {
			for (int j = 0; j < b;) {
				if ((l1.get(i)).equals(l2.get(j))) {
					l2.remove(j);
					b--;
					j = 0;
				} else {
					j++;
				}
			}
		}
		return l2;
	}
	
	/**
	 * Incoming User object that you want to modify, modify the user's basic data 
	 */
	private void updateUserData(ViewSecurityVO vvo) {
		logger.info(CcmFormat.formatEnterLog("update User Data",vvo));
		User user = new User();
		user = securityManagementDao.findUserById(Integer.parseInt(vvo.getUserId()));
		try {
			user.setFirstName(vvo.getFirstName());
			user.setLastName(vvo.getLastName());
			user.setAddress(vvo.getAddress());
			user.setPrimaryPhone(vvo.getPrimaryPhone());
			user.setOfficePhone(vvo.getOfficePhone());
			user.setCellPhone(vvo.getCellPlon());
			user.setFaxNumber(vvo.getFaxNumber());
			user.setEmail(vvo.getEmail());
//			user.setApproveEmailFlag(vvo.getApproveEmailFlag());
			if(vvo.getSupervisId() != null) {
				user.setSupervisorUserId(Integer.parseInt(vvo.getSupervisId()));
			}else{
				user.setSupervisorUserId(null);
			}
			if(vvo.getBackupUserId() != null) {
				user.setBackupUserId(Integer.parseInt(vvo.getBackupUserId()));
			}else{
				user.setBackupUserId(null);
			}
			user.setUserName(vvo.getUserName());
			user.setInitials(vvo.getInitials());
			user.setModifiedBy(SystemUtil.getCurrentUserId());
			user.setModifiedTimestamp(new Date());
			user.setRecActiveFlag("Y");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * Modify the user's basic data and Role List and password 
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void updateUserRoleAndPassword(ViewSecurityVO vvo){
		logger.info(CcmFormat.formatEnterLog("update User Information(password,UserRole)",vvo));
		User user = new User();
		Role role = new Role();
		
		List<String> nowList = new ArrayList<String>();
		List<String> nowAdd = new ArrayList<String>();
		List<String> pastList = new ArrayList<String>();
		List<String> pastdelete = new ArrayList<String>();
		List noUserRoleList = null;
		
		try {
			updateUserData(vvo);
			
			user = securityManagementDao.findUserById(Integer.parseInt(vvo.getUserId()));
	
			//roleString = 1,2,3;
			String roleString = vvo.getRoleString();
			
			if(roleString != null){
				//ro[] = [1,2,3]
				String[] ro = roleString.split(",");
				
				//Get the page to modify the userRoleList 
				nowList = getListByString(ro);
				
				//Get the original userRoleList 
				pastList = getUserRoleIdList(pastList, vvo.getUserId());
				
				//Elimination of duplicated each other, are now just add the userRoleList
				nowAdd = removeDuplicate(pastList, nowList);
				
				//After eliminating heavy, get used to delete userRoleList 
				pastdelete = removeDuplicate(nowList, pastList);
			}else{
				nowAdd = null;
				pastdelete = getUserRoleIdList(pastList, vvo.getUserId());
			}
	
			
			if(nowAdd != null && nowAdd.size() != 0){
				for (int i = 0; i < nowAdd.size(); i++) {
					//modified by xinyu.Liu on 2010.7.29 for CCMUAT-145
					UserRole userRole = new UserRole();
					//Look for the database has been previously created, but RecActiveFlag is "N" of the database, such as long as the RecActiveFlag that is "Y" can 
					noUserRoleList = securityManagementDao.findUserRoleByUserIdAndRoleId(Integer.parseInt(vvo.getUserId()), Integer.parseInt(nowAdd.get(i)),false);
					if (noUserRoleList != null && noUserRoleList.size() > 0) {
						for (int j = 0; j < noUserRoleList.size(); j++) {
							String str;
							Object obj = noUserRoleList.get(j);
							if (obj == null) {
								break;
							} else {
								str = obj.toString();
							}
							userRole = securityManagementDao.findUserRoleById(Integer.parseInt(str));
							userRole.setRecActiveFlag("Y");
						}
					} else {
						//Create new userRole
						role = securityManagementDao.findRoleById(Integer.parseInt(nowAdd.get(i)));
						userRole.setUser(user);
						userRole.setRole(role);
						userRole.setCreatedBy(SystemUtil.getCurrentUserId());
						userRole.setCreatedTimestamp(new Date());
						userRole.setModifiedBy(SystemUtil.getCurrentUserId());
						userRole.setModifiedTimestamp(new Date());
						userRole.setRecActiveFlag("Y");
						securityManagementDao.saveUserRole(userRole);
					}
				}
			}
			
			if(pastdelete != null && pastdelete.size() != 0){
				//Former Yes, now you want to delete to delete the userRole 
				for (int i = 0; i < pastdelete.size(); i++) {
					UserRole userRole = new UserRole();
					List userRoleList = securityManagementDao.findUserRoleByUserIdAndRoleId(Integer.parseInt(vvo.getUserId()), Integer.parseInt(pastdelete.get(i)),true);
					for (int j = 0; j < userRoleList.size(); j++) {
						String str;
						Object obj = userRoleList.get(j);
						if (obj == null) {
							break;
						} else {
							str = obj.toString();
						}
						userRole = securityManagementDao.findUserRoleById(Integer.parseInt(str));
						userRole.setRecActiveFlag("N");
					}
				}
			}
	
			//Change Password
			if (!"".equals(vvo.getPassword())) {
				user.setPassword(CcmUtil.encoderPassword(vvo.getPassword()));
			}
			
			String isLockOut = vvo.getLockOut();
			user.setIsLockOut(isLockOut);
			if("N".equals(isLockOut)) updateUserByLoginFailureCount(vvo.getUserId());
			user.setRequirePasswordChange(vvo.getRequirePasswordChange());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * Search Delegation by ViewSecurityVO.
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 */
	public String searchDelegation(ViewSecurityVO viewSecurityVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Delegation List",viewSecurityVO));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = securityManagementDao.getDelegationByUserId(viewSecurityVO);
			sb.append(viewSecurityVO.getListJsonCompatible(list));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * Get total page number and result number.
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String getDelegationTotalPageNo(ViewSecurityVO viewSecurityVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Delegation Total Page No",viewSecurityVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = securityManagementDao.getNumberOfDelegation(viewSecurityVO);
			sb.append(viewSecurityVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * Get does not include the userid and the system of the user's login ID userRoleList 
	 */
	public List<MapEntryVO<String, String>> getUserNotInUserId(String userId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get does not include your User List",userId));
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<User> iList = null;
		try {
			iList = securityManagementDao.findUserNotInUserId(userId);
			for (User i : iList) {
				MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getFirstName() + " " + i.getLastName());
				l.add(m);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return l;
	}

	/**
	 * Pass in a new userId of the list and, in the database to find the appropriate userRoleList 
	 */
	public List<String> getUserRoleIdList(List l, String userId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("find UserRole List",l,userId));
		List<String> list = null;
		try {
			list = securityManagementDao.getUserRoleId(Integer.parseInt(userId));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Add the appropriate data into the list. 
		for (int i = 0; i < list.size(); i++) {
			Object str = list.get(i);
			l.add(str.toString());
		}
		logger.info(CcmFormat.formatExitLog());
		return l;
	}

	/**
	 * According to delete Delegation userDelegationId ### setRecActiveFlag("N");
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void deleteDelegation(String userDelegationId) {
		logger.info(CcmFormat.formatEnterLog("Delete UserDelegation Data",userDelegationId));
		UserDelegation userDelegation = new UserDelegation();
		try {
			userDelegation = securityManagementDao.findDelegationById(Integer.parseInt(userDelegationId));
			userDelegation.setRecActiveFlag("N");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * According to delete Previledge the VendorBan userPreviledgeId ### setRecActiveFlag("N");
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void deletePreviledgeVendorBan(String userPreviledgeId){
		logger.info(CcmFormat.formatEnterLog("Delete UserPreviledge Data",userPreviledgeId));
		UserPrivilege userPrivilege = new UserPrivilege();
		try {
			userPrivilege = securityManagementDao.findUserPrevilegeById(Integer.parseInt(userPreviledgeId));
			userPrivilege.setModifiedBy(SystemUtil.getCurrentUserId());
			userPrivilege.setModifiedTimestamp(new Date());
			userPrivilege.setRecActiveFlag("N");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * Add a Delegation to the database data by ViewSecurityVO
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void saveDelegation(ViewSecurityVO viewSecurityVO){
		logger.info(CcmFormat.formatEnterLog("Add UserDelegation Data",viewSecurityVO));
		UserDelegation userDelegation = new UserDelegation();
		User fromUser = new User();
		User toUser = new User();
		Date dateStarts = new Date();
		Date dateEnds = new Date();
		try {
			//Locate the table needs a data object 
			fromUser = securityManagementDao.findUserById(Integer.parseInt(viewSecurityVO.getUserId()));
			toUser = securityManagementDao.findUserById(Integer.parseInt(viewSecurityVO.getToUserId()));
			
			dateStarts = formatDate(viewSecurityVO.getDelegationDateStartsOn());
			dateEnds = formatDate(viewSecurityVO.getDelegationDateEndsOn());
			
			//Add a userDelegation
			userDelegation.setUserByFromUserId(fromUser);
			userDelegation.setUserByToUserId(toUser);
			userDelegation.setStartDate(dateStarts);
			userDelegation.setEndDate(dateEnds);
			userDelegation.setCreatedBy(SystemUtil.getCurrentUserId());
			userDelegation.setCreatedTimestamp(new Date());
			userDelegation.setModifiedBy(SystemUtil.getCurrentUserId());
			userDelegation.setModifiedTimestamp(new Date());
			userDelegation.setRecActiveFlag("Y");
			
			securityManagementDao.saveDelegation(userDelegation);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Date format
	 * @param dateString  To format the date
	 * @return The formatted date 
	 */
	private Date formatDate(String dateString) throws BPLException {
		Date date = new Date();
		//Format a date (yyyy-MM-dd)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//Get the date and the format of the page 
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return date;
	}

	/**
	 * update userDelegation by ViewSecurityVO
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void updateDelegation(ViewSecurityVO viewSecurityVO,String upDelegationId){
		logger.info(CcmFormat.formatEnterLog("update UserDelegation Data",viewSecurityVO));
		UserDelegation userDelegation = new UserDelegation();
		User toUser = new User();
		Date dateStarts = new Date();
		Date dateEnds = new Date();

		try {
			//According to modify the object id found
			userDelegation = securityManagementDao.findDelegationById(Integer.parseInt(upDelegationId));
			toUser = securityManagementDao.findUserById(Integer.parseInt(viewSecurityVO.getToUserId()));
			
			dateStarts = formatDate(viewSecurityVO.getDelegationDateStartsOn());
			dateEnds = formatDate(viewSecurityVO.getDelegationDateEndsOn());
	
			//update userDelegation
			userDelegation.setUserByToUserId(toUser);		
			userDelegation.setStartDate(dateStarts);
			userDelegation.setEndDate(dateEnds);
			userDelegation.setModifiedBy(SystemUtil.getCurrentUserId());
			userDelegation.setModifiedTimestamp(new Date());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * The same data already exists.
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 */
	public String verifyPreviledgeDataService(ViewSecurityVO viewSecurityVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("The same data already exists.", viewSecurityVO));
		String str = "{data:true}";
		try {
			long count = securityManagementDao.getNumberOfUserPrivilege(viewSecurityVO);
			if(count != 0) str = "{data:false}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return str;
	}

	/**
	 * To add a database of VendorBan Previledge by ViewSecurityVO
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public void savePreviledgeVendorBan(ViewSecurityVO viewSecurityVO){
		logger.info(CcmFormat.formatEnterLog("Add UserPreviledge Data",viewSecurityVO));
		UserPrivilege userPrivilege = new UserPrivilege();
		User user = new User();
		Vendor vendor = new Vendor();
		Ban ban = new Ban();
		try {
			//get vendorId and banId
			Integer vendorId = Integer.parseInt(viewSecurityVO.getPreviledgeVendorId());
			Integer banId = Integer.parseInt(viewSecurityVO.getPreviledgeBanId());
			
			user = securityManagementDao.findUserById(Integer.parseInt(viewSecurityVO.getUserId()));
			vendor = securityManagementDao.findVendorById(vendorId);
			ban = securityManagementDao.findBanById(banId);
			
			userPrivilege.setUser(user);
			userPrivilege.setVendor(vendor);
			userPrivilege.setBan(ban);
			userPrivilege.setCreatedBy(SystemUtil.getCurrentUserId());
			userPrivilege.setCreatedTimestamp(new Date());
			userPrivilege.setModifiedBy(SystemUtil.getCurrentUserId());
			userPrivilege.setModifiedTimestamp(new Date());
			userPrivilege.setRecActiveFlag("Y");
			
			securityManagementDao.savePrevilege(userPrivilege);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * Get total page number and result number.
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String getPreviledgeVendorBanTotalPageNo(ViewSecurityVO viewSecurityVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get PreviledgeVendorBan Total Page No",viewSecurityVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = securityManagementDao.getNumberOfVendorBanPreviledge(viewSecurityVO);
			sb.append(viewSecurityVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * Search Previledge VensorBan by ViewSecurityVO.
	 * @author xinyu.Liu (viewSecurityManagement.js)
	 * 
	 */
	public String searchPreviledgeVensorBan(ViewSecurityVO viewSecurityVO)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search PreviledgeVensorBan List",viewSecurityVO));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = securityManagementDao.getVerderBanByUserIdPreviledge(viewSecurityVO);
			sb.append(viewSecurityVO.getListJsonCompatible(list));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * get Role All return List
	 */
	public List<Role> getRole() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get RoleAll List"));
		List<Role> list = new ArrayList<Role>();
		try {
			list = securityManagementDao.findRoleAll();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return list;
	}

	/**
	 * Introduction of a new User and are User Data UserId
	 */
	public User getUser(User user, Integer id) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("find User Object", user,id));
		try {
			user = securityManagementDao.findUserById(id);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return user;
	}

	public Map checkIpSecurity(String ip,String userName) {
		Map mapResult = new HashMap();
		mapResult.put("result", true);
		mapResult.put("startDateValid", true);
		mapResult.put("endDateValid", true);
		Object ipList = EhcacheUtils.get("CCM_CACHE", "ipList");
		if (ipList == null) {
			ipList = securityManagementDao.getSecurityIPList();
			EhcacheUtils.put("CCM_CACHE", "ipList", ipList);
			// note: when security_ip table data changed , must to call EhcacheUtils.clear("CCM_CACHE");
		}
		
		List<SecurityIp> list = (List<SecurityIp>) ipList;
		if(userName == null){
			return mapResult;
		}
		for (SecurityIp securityIp : list) {
			
			if("N".equals(securityIp.getIpRangeFlag())){
				if (securityIp.getIpA().equals(ip)) {
					
					boolean isOfficeIp = (securityIp.getUserId() == null);
					boolean isCurrentUserIp = (userName.equals(securityIp.getUserName()));
					boolean startDateValid = (securityIp.getStartDate() == null || compareDateToNow(securityIp.getStartDate()) <= 0);
					boolean endDateValid = (securityIp.getEndDate() == null || compareDateToNow(securityIp.getEndDate()) >= 0);
					mapResult.put("startDateValid", startDateValid);
					mapResult.put("endDateValid", endDateValid);
					
					if( isOfficeIp || isCurrentUserIp ){
						if("N".equals(securityIp.getActiveFlag())){
							mapResult.put("result", false);
							return mapResult;
						}
						
						if ( startDateValid && endDateValid ) {
							mapResult.put("result", true);
							return mapResult;
						}
						
					}
				}
			}else{
				String ipA = securityIp.getIpA() == null ? "" :securityIp.getIpA();
				String ipB = securityIp.getIpB() == null ? "" :securityIp.getIpB();
				if("".equals(ipA) || "".equals(ipB)){
					mapResult.put("result", false);
					return mapResult;
				}
				String[] listIpA = ipA.split("\\.");
				String[] listIpB = ipB.split("\\.");
				String[] listUserIp = ip.split("\\."); 
				if("0:0:0:0:0:0:0:1".equals(ip)){
					listUserIp = ip.split(":"); 
				}
				String ipFrom = ipStructure(listIpA);
				String ipTo = ipStructure(listIpB);
				String userIp = ipStructure(listUserIp);
				
				if(userIp.compareTo(ipFrom) >= 0 && userIp.compareTo(ipTo) <=0){
					if("N".equals(securityIp.getActiveFlag())){
						mapResult.put("result", false);
						return mapResult;
					}
					boolean isOfficeIp = (securityIp.getUserId() == null);
					boolean isCurrentUserIp = (userName.equals(securityIp.getUserName()));
					boolean startDateValid = (securityIp.getStartDate() == null || compareDateToNow(securityIp.getStartDate()) <= 0);
					boolean endDateValid = (securityIp.getEndDate() == null || compareDateToNow(securityIp.getEndDate()) >= 0);
					mapResult.put("startDateValid", startDateValid);
					mapResult.put("endDateValid", endDateValid);
					
					if (isOfficeIp || isCurrentUserIp) {
						if (startDateValid && endDateValid) {
							mapResult.put("result", true);
							return mapResult;
						}
					} 
				}
			}
			
		}
		
		mapResult.put("result", false);
		return mapResult;
		
	}
	
	private String ipStructure(String[] ipList){
		StringBuffer ip = new StringBuffer("");
		for (int i = 0 ; i < 4; i++) {
			int len = 3-ipList[i].length();
			switch(len){
				case 1: 
					ip.append("0");
					break;
				case 2: 
					ip.append("00");
					break;
			}
			ip.append(ipList[i]);
			
		}
		return ip.toString();
	}
	
	private int compareDateToNow(Date date) {
		Calendar cDate = Calendar.getInstance();
		cDate.setTime(date);
        
		Calendar today = Calendar.getInstance();
		
		// only compare date not include time 
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
		String sToday = fmt.format(new Date());
		try {
			today.setTime(fmt.parse(sToday));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return cDate.compareTo(today);
	}
	
	public String verifyOperatePermission(ViewSecurityVO viewSecurityVO) {
		return securityManagementDao.verifyOperatePermission(viewSecurityVO);
	}

	public String searchBBLoginFlag(){
		return securityManagementDao.searchBBLoginFlag();
	}
	
	public ISecurityManagementDao getSecurityManagementDao() {
		return securityManagementDao;
	}

	public void setSecurityManagementDao(ISecurityManagementDao securityManagementDao) {
		this.securityManagementDao = securityManagementDao;
	}

}
