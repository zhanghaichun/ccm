package com.saninco.ccm.action.system;


import java.util.HashSet;

import org.springframework.security.Authentication;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.User;
import com.saninco.ccm.service.system.ILogonService;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;

/**
 * User Change Password action.
 * 
 * @ Author: Feiwei.Ma
 *
 */
public class ChangePassword extends CcmActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5951412725791711321L;
	
	private ILogonService logonService;
	
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	@Override
	public String exec() throws Exception {
		// display initial page			
		return INPUT;
	}
	
	/**
	 * @return the logonService
	 */
	public ILogonService getLogonService() {
		return logonService;
	}
	
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * @param logonService the logonService to set
	 */
	public void setLogonService(ILogonService logonService) {
		this.logonService = logonService;
	}

	public String  doPwdChange() {
		
		// validate user input first
		if(!validatePasswordEntered() || !validatePasswordMatch() || !validatePasswordStrong()){// || ) {
			return INPUT;
		}
		
		User user = SystemUtil.getCurrentUser();
		
		try {
			logonService.changePassword(user, newPassword);
		}
		catch(BPLException be) {
			if(be.getErrorCode().equals(ErrorCodeConstants.EC_SYSTEM_ERROR)) {
				addActionError(getText("page.change.password.error.system",
						"Unable change your password due to system error!"));
			}
			else if(be.getErrorCode().equals(ErrorCodeConstants.EC_DUPLICATE)) {
				addActionError(getText("page.change.password.error.password.repeat",
				"You must use a new password that is different with your recent 5 passwords."));
			}
			else {
				addActionError(be.getMessage());
			}
			
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	
	private boolean validatePasswordEntered() {
		boolean valid = true;
		
		// check fields not empty
		if(oldPassword == null || oldPassword.trim().length() == 0) {
			addActionError(getText("page.change.password.error.input.oldPwd",
					"Old password is mandatory."));
			valid = false;
		}
		
		if(newPassword == null || newPassword.trim().length() == 0) {
			addActionError(getText("page.change.password.error.input.newPwd",
					"New password is mandatory."));
			valid = false;
		}
		
		if(confirmPassword == null || confirmPassword.trim().length() == 0) {
			addActionError(getText("page.change.password.error.input.confirmPwd",
					"Confirm password is mandatory."));
			valid = false;
		}
		
		return valid;
	}
	
	private boolean validatePasswordMatch() {
		// old password must be correct
		// get current user's authentication which is stored in this thread
		Authentication user = SystemUtil.getCurrentAuthentication();
		if(! oldPassword.equals(user.getCredentials())) {
			addActionError(getText("page.change.password.error.input.incorrectOldPwd",
					"Old password is not correct."));
			return false;
		}
		
		// new password and confirm password must match
		if(! newPassword.equals(confirmPassword)) {
			addActionError(getText("page.change.password.error.input.notMatch",
					"New password and confirm password do not match."));
			return false;
		}
		
		if(oldPassword.equals(newPassword)) {
			addActionError(getText("page.change.password.error.input.repeat",
					"New password must be different with old password."));
			return false;
		}

		return true;
	}
	
	private void addPasswordNotStrongErrors() {
		addActionError(getText("page.change.password.error.password.length","Password must be at least 8 characters length."));
		
		addActionError(getText("page.change.password.error.password.unique.char", "Password must contain 2 unique characters."));
		
		addActionError(getText("page.change.password.error.password.symbols", "Password must contain 2 digits or symbols."));
		
//		addActionError(getText("page.change.password.error.password.upper.lower.case", "Password must contain letters in upper and lower case."));		
	}
	
	/**
	 * Password must be at least 8 characters in length, 
	 * must contain at least 4 unique characters,
	 * must contain at least 2 digits or symbols, 
	 * @return
	 */
	private boolean validatePasswordStrong() {
		//String errorMsg = "Password must be strong.";
		
		if(newPassword.length() < 8) {
			addPasswordNotStrongErrors();
			return false;
		}
		
		HashSet<Character> charSet = new HashSet<Character>();
		int nonLetterCount = 0;
//		int lowerLetterCount = 0;
//		int upperLetterCount = 0;
		for(int i = 0; i < newPassword.length(); i++) {
			char ch = newPassword.charAt(i);
			charSet.add(ch);
			if(Character.isLetter(ch)) {
//				if(Character.isUpperCase(ch)) {
//					upperLetterCount++;
//				}
//				else {
//					lowerLetterCount++;
//				}
			} else {
				nonLetterCount++;
			}
		}
		
		if(charSet.size() < 2 || nonLetterCount < 2 )
		{
			addPasswordNotStrongErrors();
			return false;
		}
		
		return true;
	}
}