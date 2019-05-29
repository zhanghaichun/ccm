package com.saninco.ccm.action.system;


import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.system.ILogonService;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;

/**
 * Handles password reset page.
 * 
 * @ Author: Feiwei.Ma
 *
 */
public class PwdReset extends CcmActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5024323091720740658L;

	private static final String SEARCH_BY_USERNAME = "username";
	private static final String SEARCH_BY_EMAIL = "email";
	
	private ILogonService logonService;
	
	private String searchBy;
	private String username;
	private String email;
	
	
	public ILogonService getLogonService() {
		return logonService;
	}

	public void setLogonService(ILogonService logonService) {
		this.logonService = logonService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	@Override
	public String exec() throws Exception {
		// display initial page			
		return INPUT;
	}
	
	public String doPwdReset() {
		// validate input
		if(! validateInput()) {
			return INPUT;
		}
		
		try {
			if(SEARCH_BY_USERNAME.equals(searchBy)) {
				logonService.resetPasswordByUsername(username);
			}
			else {
				logonService.resetPasswordByEmail(email);
			}
		}
		catch(BPLException be) {
			if(ErrorCodeConstants.EC_NOT_EXIST.equals(be.getErrorCode())) {
				if(SEARCH_BY_USERNAME.equals(searchBy)) {
					addActionError(getText("page.passwordreminder.error.username",
							"Username does not exist in the system!"));
				}
				else {
					addActionError(getText("page.passwordreminder.error.email",
							"Email does not exist in the system!"));
				}
			}
			else if(ErrorCodeConstants.EC_DUPLICATE.equals(be.getErrorCode())) {
				if(SEARCH_BY_USERNAME.equals(searchBy)) {
					addActionError(getText("page.passwordreminder.error.duplicate.account",
						"Cannot reset password since multiple accounts are found in the system for the username!"));
				}
				else {
					addActionError(getText("page.passwordreminder.error.duplicate.email",
						"Multiple accounts are found for the email! Please use username to reset password for the account."));
				}
			}
			else if(ErrorCodeConstants.EC_USER_LOCKED_OUT.equals(be.getErrorCode())) {
				addActionError(getText("page.passwordreminder.error.account.locked", 
						"Your account has been locked out, please call system administrator to resolve. Thanks."));
			}
			else {
				addActionError(getText("page.passwordreminder.error.system",
						"Could not reset your password due to system error!"));
			}
			
			return INPUT;
		}
		
		return SUCCESS;
	}
	
	private boolean validateInput() {
		if(SEARCH_BY_USERNAME.equals(searchBy)) {
			if(username == null || username.trim().length() == 0) {
				addActionError(getText("page.passwordreminder.error.input.username",
						"Please enter Username."));
				return false;
			}
		}
		else if(SEARCH_BY_EMAIL.equals(searchBy)) {
			if(email == null || email.trim().length() == 0) {
				addActionError(getText("page.passwordreminder.error.input.email",
						"Please enter Email."));
				return false;
			}
			else if(! SystemUtil.validateEmailAddress(email)) {
				addActionError(getText("page.passwordreminder.error.input.invalidEmail",
						"Invalid Email."));
				return false;
			}
		}
		else {
			// invalid searchBy value
			addActionError(getText("page.passwordreminder.error.input.choose",
					"Please choose either Username or Email!"));
			return false;
		}
		
		return true;
	}
}