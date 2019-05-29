/**
 * 
 */
package com.saninco.ccm.service.system;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationCredentialsNotFoundException;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.providers.dao.SaltSource;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.saninco.ccm.dao.IPasswordHistoryDao;
import com.saninco.ccm.dao.ISecurityManagementDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.IUserActionLogDao;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.exception.ActiveException;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.exception.IPSecurityException;
import com.saninco.ccm.model.PasswordHistory;
import com.saninco.ccm.model.SecurityIp;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserActionLog;
import com.saninco.ccm.service.email.ISendEmailService;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.Constants;
import com.saninco.ccm.util.EhcacheUtils;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;

/**
 * Logon service implementation class.
 * 
 * @author joe.yang
 *
 */
public class LogonServiceImpl<block> extends AbstractUserDetailsAuthenticationProvider implements ILogonService {
	//logger
	private Logger logger = Logger.getLogger(LogonServiceImpl.class);
	
	private IUserDao userDao;
	private ISysConfigDAO sysConfigDAO;
	private IPasswordHistoryDao passwordHistoryDao;
	private SaltSource saltSource;
    private IUserActionLogDao userActionLogDao;
    private ISecurityManagementDao securityManagementDao;
    private ISendEmailService sendEmailService;
    private boolean bbLoginFlag;
    private HttpServletRequest request;
    
	public boolean isBbLoginFlag() {
		return bbLoginFlag;
	}

	public void setBbLoginFlag(boolean bbLoginFlag) {
		this.bbLoginFlag = bbLoginFlag;
	}

	public IUserActionLogDao getUserActionLogDao() {
		return userActionLogDao;
	}

	public void setUserActionLogDao(IUserActionLogDao userActionLogDao) {
		this.userActionLogDao = userActionLogDao;
	}

	/**
	 * 
	 */
	public LogonServiceImpl() {
	}

	/**
	 * @return the userDao
	 */
	public IUserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}	


//	public IRoleDao getRoleDao() {
//		return roleDao;
//	}
//
//	public void setRoleDao(IRoleDao roleDao) {
//		this.roleDao = roleDao;
//	}
//
//	public IControlTableDao getControlTableDao() {
//		return controlTableDao;
//	}
//
//	public void setControlTableDao(IControlTableDao controlTableDao) {
//		this.controlTableDao = controlTableDao;
//	}
	
	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public IPasswordHistoryDao getPasswordHistoryDao() {
		return passwordHistoryDao;
	}

	public void setPasswordHistoryDao(IPasswordHistoryDao passwordHistoryDao) {
		this.passwordHistoryDao = passwordHistoryDao;
	}

	public SaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	private Object getSalt(UserDetails userDetails) {
		Object salt = null;
		if (this.saltSource != null) {
			salt = this.saltSource.getSalt(userDetails);
		}		

		return salt;
	}
	// Modified By Chao.Liu to 10/07/27 PM
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
	throws AuthenticationException 
	{
		User user = (User) userDetails;
		
		try {	
			
			if("N".equals(user.getRecActiveFlag()) || "N".equals(user.getActiveFlag())) {
				throw new ActiveException("The user has De-Active,");
			}		
			
			if (authentication.getCredentials() == null) {
				throw new BadCredentialsException("Missing Password.");
			}

			String presentedPassword = authentication.getCredentials().toString();

			if (!isSamePassword(userDetails, presentedPassword)) {
				throw new BadCredentialsException("Bad credentials.");
			}
			
			if (!checkIPSecurity(userDetails, authentication) && !isMobile()) {
				throw new IPSecurityException("Account is expired. Please contact our support team.");
			}
			
//			Temporarily unavailable [Modified By Chao.Liu]
			user.setLoginFailureCount(0);
			user.setLastLoginTimestamp(new Date());
			userDao.updateUser(user);
			recordLoginAuditLog(user, authentication, Constants.EVENT_LOGIN_SUCCESS);
			
			UserActionLog userActionLog = new UserActionLog();
			userActionLog.setActivityType("Log In");
			userActionLog.setCreatedBy(0);
			userActionLog.setCreatedTimestamp(new Date());
			userActionLog.setRecActiveFlag("Y");
//			userActionLog.setMessage(ae.getMessage());
//			userActionLog.setUrl("j_spring_security_check");
			userActionLog.setUserId(user.getId());
			userActionLogDao.save(userActionLog);
		} catch(AuthenticationException ae) {
/*			// retrieve latest login audit log			
			EventLog lastAuditLog = auditLogDao.getLatestLoginAuditLog(user.getId());
			
			// retrieve number of consective login failure
			int failedCount = 0;
			if(lastAuditLog != null) {
				try {
					failedCount = Integer.parseInt(lastAuditLog.getActivityDetail());
				}
				catch(NumberFormatException ne) {
					//ignore
				}
			}
			
			// retrieve number of allowed login try
			int num = getControlTableParamValueAsInt(Constants.PARAM_NAME_LOGIN_TRY);

			failedCount++;
			if(num >0 && failedCount >= num) {
				// lock user
				user.setIsLocked(Constants.DB_FLAG_YES);
				user.setModifiedBy(user.getFirstName() + " " + user.getLastName());
				user.setUpdatedOn(new Date());
				
				userDao.updateUser(user);
				
				recordLoginAuditLog(user, authentication, Constants.EVENT_LOGIN_LOCKED_OUT);
			}
			else {
				recordLoginAuditLog(user, authentication, Integer.toString(failedCount));
			}
*/
//			Temporarily unavailable [Modified By Chao.Liu]
			
			
			UserActionLog userActionLog = new UserActionLog();
			userActionLog.setActivityType("Log In");
			userActionLog.setCreatedBy(0);
			userActionLog.setCreatedTimestamp(new Date());
			userActionLog.setRecActiveFlag("Y");
			userActionLog.setMessage(ae.getMessage());
//			userActionLog.setUrl("j_spring_security_check");
			userActionLog.setUserId(user.getId());
			userActionLogDao.save(userActionLog);
			
			Map<String, String> m = sysConfigDAO.findSysConfig();
			int maxLoginFailure = Integer.valueOf(m.get("max_login_failure")).intValue();
			
			if(user.getLoginFailureCount() == null){
				user.setLoginFailureCount(1);
			}else{
				user.setLoginFailureCount(user.getLoginFailureCount()+1);
			}
			user.setLastLoginTimestamp(new Date());
			if(user.getLoginFailureCount()>= maxLoginFailure) {
				// lock user
				user.setIsLockOut(Constants.DB_FLAG_YES);
				user.setModifiedBy(user.getId());
				user.setModifiedTimestamp(new Date());
				recordLoginAuditLog(user, authentication, Constants.EVENT_LOGIN_LOCKED_OUT);
			}
			else {
				recordLoginAuditLog(user, authentication, Integer.toString(user.getLoginFailureCount()));
			}
			userDao.updateUser(user);
			throw ae;
		}
	}
	
	private void recordLoginAuditLog(
			User user, 
			UsernamePasswordAuthenticationToken authentication, 
			String activityDetails) 
	{		
		String ipAddress = null;
		Object authDetails = authentication.getDetails();
		if(authDetails != null && authDetails instanceof WebAuthenticationDetails) {
			ipAddress = ((WebAuthenticationDetails) authDetails).getRemoteAddress();
		}
		user.setIpAddress(ipAddress);
	}

	
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
	throws AuthenticationException {

		// load user
		User user = userDao.getUser(username);
		
		// this method cannot return null
		if(user == null) {
			UserActionLog userActionLog = new UserActionLog();
			userActionLog.setActivityType("Log In");
			userActionLog.setCreatedBy(0);
			userActionLog.setCreatedTimestamp(new Date());
			userActionLog.setRecActiveFlag("Y");
			userActionLog.setMessage("The user Not Found!");
//			userActionLog.setUrl("j_spring_security_check");
//			userActionLog.setUserId(user.getId());
			userActionLogDao.save(userActionLog);
			throw new AuthenticationCredentialsNotFoundException(
					"Username " + username + " Not Found!");
		}

		if(user.isAccountLocked()) {
			
			UserActionLog userActionLog = new UserActionLog();
			userActionLog.setActivityType("Log In");
			userActionLog.setCreatedBy(0);
			userActionLog.setCreatedTimestamp(new Date());
			userActionLog.setRecActiveFlag("Y");
			userActionLog.setMessage("The user has bean locked.");
//			userActionLog.setUrl("j_spring_security_check");
			userActionLog.setUserId(user.getId());
			userActionLogDao.save(userActionLog);
			
			recordLoginAuditLog(user, authentication, Constants.EVENT_LOGIN_LOCKED_OUT);
		}
		
		return user;
	}

	
	protected Authentication createSuccessAuthentication(
			Object principal, Authentication authentication, UserDetails user) 
	{
		
		// get user role and authorities
		User theUser = (User) user;
//		Role role = roleDao.findRoleById(theUser.getRoleId());
//		List<GrantedAuthority> authorityList = role.getAuthorities();
//		if(authorityList == null) authorityList = new ArrayList<GrantedAuthority>();
//		
//		// populate assigned departments if needed
//		if(Constants.ROLE_DEPT_USER.equals(role.getName())) {
//			List<?> userDeptList = userDao.findDepartmentCodesByUserId(theUser.getUserId());
//			
//			if(userDeptList != null && !userDeptList.isEmpty()) {
//				ArrayList<String> deptCodeList = new ArrayList<String>();
//				
// 				for(int i = 0; i < userDeptList.size(); i++) {
//					//UserDept userDept = (UserDept) userDeptList.get(i);
//					//String deptCode = userDept.getId().getDepartment().getDeptCd();
// 					String deptCode = (String) userDeptList.get(i);
//					deptCodeList.add(deptCode);
//					
//					// add department code to authority list as well
//	 				authorityList.add(new GrantedAuthorityImpl(deptCode));
//				}
// 				
// 				theUser.setAssignedDepartments(deptCodeList.toArray(new String[0]));
//			}
//		}
//		
//		// populate authorities
//		GrantedAuthority[] authorities = authorityList.toArray(new GrantedAuthorityImpl[0]);
//		theUser.setAuthorities(authorities);
		
		// construct the AuthenticationToken object required by Spring Security framework
		// start create random theme add by mingyang.li
		Random rd1 = new Random();
		Integer randowThemeId = (rd1.nextInt(19)+1);
		if( theUser.getTheme() == null || theUser.getTheme().getId().intValue() == 0){
			userDao.updateUserRandomTheme(randowThemeId,theUser.getId());
			theUser.setRandomtheme(userDao.findThemesById(randowThemeId));
		}
		// end 
		List functions = userDao.getUserFunctions(theUser.getId());
		if(functions.contains(0)){
			functions = userDao.getAllFunctions();
		}
		GrantedAuthority[] authorities = new GrantedAuthority[functions.size()];
		for(int i = 0; i<functions.size(); i++){
			Object fun = functions.get(i);
			if(fun!=null)authorities[i] = new GrantedAuthorityImpl("FUNCTION_"+fun);
		}
		
		UsernamePasswordAuthenticationToken result =  new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authorities);
		result.setDetails(authentication.getDetails());
		theUser.setAuthorities(authorities);
		return result;
	}

	private int getControlTableParamValueAsInt(String paramName) {
//		String numStr = controlTableDao.findParamValue(paramName);
		int num = 3;		
//		try {			
//			num = Integer.parseInt(numStr);
//		}
//		catch(NumberFormatException ex) {
//			logger.error("Number of unrepeat password is not a number: " + numStr);
//			num = 0;
//		}
		
		return num;
	}
	
	public void changePassword(User user, String password) throws BPLException {
		// check if the new password is the same with the old password
		if (isSamePassword(user, password)) {
			// new password is the same with old password
			throw new BPLException(ErrorCodeConstants.EC_DUPLICATE, 
					"new password is the same with old password.");			
		}

		// retrieve number of recent passwords that the new password cannot be the same with
		int num = getControlTableParamValueAsInt(Constants.PARAM_NAME_UNREPEAT_PASSWORD);
		if(num == 0) {		
			logger.error("Number of unrepeat password is 0.");
			throw new BPLException(ErrorCodeConstants.EC_SYSTEM_ERROR, 
					"Number of unrepeat password is 0.");
		}
		
		String originalPassword = user.getPassword();
		
		// retrieve history password in recent used order
		List<String> historyPasswords = passwordHistoryDao.findUsedPasswords(user.getId());
//		if(historyPasswords != null && !historyPasswords.isEmpty()) {
		if(historyPasswords != null && historyPasswords.size() != 0) {			
			// check with history passwords one by one
			for(int i = 0; i < num && i < historyPasswords.size(); i++) {
				
				// set user's password to a history password for check only
				user.setPassword(historyPasswords.get(i));
				
				if(isSamePassword(user, password)) {
					// recover the original password for the user
					user.setPassword(originalPassword);
					throw new BPLException(ErrorCodeConstants.EC_DUPLICATE, "User try to use a previous used password.");
				}
			}
			
			// pass the validation, recover the original password first
			user.setPassword(originalPassword);
		}
		
		// record password history
		recordPasswordHistory(user);
		
		// update password
		user.setPassword(encodePassword(password));
		user.setRecActiveFlag("Y");
		user.setModifiedBy(user.getId());
		user.setModifiedTimestamp(new Date());
		
		try {
			userDao.updateUser(user);
			
			// set a new authentication object for changing password
			Authentication existingAuth = SystemUtil.getCurrentAuthentication();
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, password, existingAuth.getAuthorities());
			auth.setDetails(existingAuth.getDetails());
			SecurityContextHolder.getContext().setAuthentication(auth);			
		}
		catch(Exception ex) {
			//recover original user password
			user.setPassword(originalPassword);
			
			logger.error("Got exception when trying to update User: " + ex.getMessage());
			throw new BPLException(ErrorCodeConstants.EC_SYSTEM_ERROR, 
					ex.getLocalizedMessage());
		}
	}
	
	private String encodePassword(String password) {
//		Object salt = getSalt(user);
		return CcmUtil.encoderPassword(password);
	}
	
	private void recordPasswordHistory(User user) throws BPLException {
		try {
			PasswordHistory history = new PasswordHistory(null,user,user.getPassword(),new Date(),user.getId());

			passwordHistoryDao.insertPasswordHistory(history);
		}
		catch(Exception ex) {
			logger.error("Got exception: " + ex.getMessage());
			throw new BPLException(ErrorCodeConstants.EC_SYSTEM_ERROR, 
					ex.getLocalizedMessage());
		}
	}
	
	private boolean isSamePassword(UserDetails user, String newPassword) {
		return CcmUtil.getPasswordEncoder().isPasswordValid(user.getPassword(), newPassword, null) || user.getPassword().equals(newPassword);
	}

	public List<Object> resetPasswordByEmail(String email) throws BPLException {
		// find user
		List<User> userList = userDao.findUsersByEmail(email);
		String newPassword=SystemUtil.generatePassword();
//		if(userList == null || userList.isEmpty()) {
		if(userList == null || userList.size() == 0) {
			throw new BPLException(ErrorCodeConstants.EC_NOT_EXIST, 
					"user does not exist for the given email.");
		}
		
		if(userList.size() > 1) {
			throw new BPLException(ErrorCodeConstants.EC_DUPLICATE, 
			                "multiple user accounts are found for the given email.");
		}
		try {
			CreateTheEmailByUserNameAndPassword(userList.get(0),newPassword);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return resetPassword( userList.get(0), newPassword );
	}

	private List<Object> resetPassword(User user, String newPassword) throws BPLException {
		
		if(! user.isCredentialsNonExpired()) {
			throw new BPLException(ErrorCodeConstants.EC_NOT_EXIST, 
			"user account has expired.");
		}
		
		if(user.isAccountLocked()) {
			throw new BPLException(ErrorCodeConstants.EC_USER_LOCKED_OUT, 
			"user account has been locked out.");
		}
		
		try {			
			// record password history only when this password is not auto-generated
			if(! Constants.MODIFIED_BY_PASSWORD_RESET.equals(user.getModifiedBy())) {
				recordPasswordHistory(user);
			}

			// reset password
			user.setPassword(encodePassword(newPassword));

			user.setRecActiveFlag("Y");
			user.setModifiedTimestamp(new Date());
			user.setModifiedBy(user.getId());
			user.setRequirePasswordChange("Y");

			userDao.updateUser(user);			
			
			if(logger.isInfoEnabled()) {
				logger.debug("Reset password for user " + user.getUsername() + " to " + newPassword);
			}
			
			List<Object> retValues = new ArrayList<Object>();
			retValues.add(user);
			retValues.add(newPassword);
			
			return retValues;
		}
		catch(Exception ex) {
			logger.error("Got exception: " + ex.getMessage());
			throw new BPLException(ErrorCodeConstants.EC_SYSTEM_ERROR,
					ex.getLocalizedMessage());			
		}
	}
	
	public List<Object> resetPasswordByUsername(String username) throws BPLException {
		User user = null;
		String newPassword=SystemUtil.generatePassword();
		try {
			user = userDao.getUser(username);	
		}
		catch(Exception ex) {
			logger.error("Got exception: " + ex.getMessage());
			throw new BPLException(ErrorCodeConstants.EC_SYSTEM_ERROR,
					ex.getLocalizedMessage());

		}
		
		if(user == null) {
			throw new BPLException(ErrorCodeConstants.EC_NOT_EXIST,
					"Could not find user for username: " + username);
		}
		try {
			CreateTheEmailByUserNameAndPassword(user,newPassword);
		} catch (AddressException e) {
		
			e.printStackTrace();
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		return resetPassword(user,newPassword );
	}
	/**
	 * According to the clipboard so and password generate mail
	 * @param username
	 * @param password
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	@SuppressWarnings("unused")
	private void CreateTheEmailByUserNameAndPassword(User user,String password) throws AddressException, MessagingException{
		//2017-07-29		
		sendEmailService.createTheEmailByUserNameAndPassword(user,password);
	
	}
	
	private boolean checkIPSecurity(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
		User user = (User) userDetails;
		String ipAddress = null;
		Object authDetails = authentication.getDetails();
		if(authDetails != null && authDetails instanceof WebAuthenticationDetails) {
			ipAddress = ((WebAuthenticationDetails) authDetails).getRemoteAddress();
		}
		
		if(isBbLoginFlag() && "Y".equals(securityManagementDao.searchBBLoginFlag())){
			return true;
		}
		
		List<SecurityIp> list = obtainSecurityIpList();
		
		for (SecurityIp securityIp : list) {
			
			if("N".equals(securityIp.getIpRangeFlag())){
				
				if (securityIp.getIpA().equals(ipAddress)) {
					
					boolean isOfficeIp = (securityIp.getUserId() == null);
					boolean isCurrentUserIp = (user.getId().equals(securityIp.getUserId()));
					boolean startDateValid = (securityIp.getStartDate() == null || compareDateToNow(securityIp.getStartDate()) <= 0);
					boolean endDateValid = (securityIp.getEndDate() == null || compareDateToNow(securityIp.getEndDate()) >= 0);
					
					if (isOfficeIp || isCurrentUserIp) {
						
						if("N".equals(securityIp.getActiveFlag())){
							return false;
						}
						
						if (startDateValid && endDateValid) {
							return true;
						}
						
					} 
				}
			}else{
				
				String ipA = securityIp.getIpA() == null ? "" :securityIp.getIpA();
				String ipB = securityIp.getIpB() == null ? "" :securityIp.getIpB();
				if("".equals(ipA) || "".equals(ipB)){
					throw new IPSecurityException("DB IP Data Error !");
				}
				String[] listIpA = ipA.split("\\.");
				String[] listIpB = ipB.split("\\.");
				String[] listUserIp = ipAddress.split("\\."); 
				if("0:0:0:0:0:0:0:1".equals(ipAddress)){
					listUserIp = ipAddress.split(":"); 
				}
				String ipFrom = ipStructure(listIpA);
				String ipTo = ipStructure(listIpB);
				String userIp = ipStructure(listUserIp);
				
				if(userIp.compareTo(ipFrom) >= 0 && userIp.compareTo(ipTo) <=0){
					if("N".equals(securityIp.getActiveFlag())){
						return false;
					}
					boolean isOfficeIp = (securityIp.getUserId() == null);
					boolean isCurrentUserIp = (user.getId().equals(securityIp.getUserId()));
					boolean startDateValid = (securityIp.getStartDate() == null || compareDateToNow(securityIp.getStartDate()) <= 0);
					boolean endDateValid = (securityIp.getEndDate() == null || compareDateToNow(securityIp.getEndDate()) >= 0);
					
					if (isOfficeIp || isCurrentUserIp) {
						if (startDateValid && endDateValid) {
							return true;
						}
					} 
				}
			}
			
		}
		
		return false;
		
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
	
	private List<SecurityIp> obtainSecurityIpList() {
		Object ipList = EhcacheUtils.get("CCM_CACHE", "ipList");
		
		if (ipList == null) {
			ipList = securityManagementDao.getSecurityIPList();
			EhcacheUtils.put("CCM_CACHE", "ipList", ipList);
			// note: when security_ip table data changed , must to call EhcacheUtils.clear("CCM_CACHE");
		}
		
		List<SecurityIp> list = (List<SecurityIp>) ipList;
		return list;
	}
	
	public boolean isMobile(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String authorization = request.getHeader("Authorization");
		if(authorization!=null && !"".equals(authorization)){
			try{
				String token = new String(Base64.decodeBase64(new String(authorization).getBytes()));
				String[] ss = null;
				if(token.indexOf("isMobile") >= 0){
					ss = token.split("isMobile");
					token = ss[ss.length-1];
				}
				long nowTimeStamp = System.currentTimeMillis();
				long timeStamp = 0;
				if (token!=null && !"".equals(token)){
					timeStamp = nowTimeStamp - Long.parseLong(token);
				}
				long hour = (timeStamp / (60 * 60 * 1000));
				if (hour <= 2) {
					request.getSession().setAttribute("isMobile", "Y");
					return true;
				}else{
					return false;
				}
			}catch (Exception re) {
				return false;
			}
		}else{
			String isMobile = request.getSession().getAttribute("isMobile")+"";
			if(isMobile!=null && "Y".equals(isMobile)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public ISecurityManagementDao getSecurityManagementDao() {
		return securityManagementDao;
	}

	public void setSecurityManagementDao(
			ISecurityManagementDao securityManagementDao) {
		this.securityManagementDao = securityManagementDao;
	}

	public ISendEmailService getSendEmailService() {
		return sendEmailService;
	}

	public void setSendEmailService(ISendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	}
	
	
}
