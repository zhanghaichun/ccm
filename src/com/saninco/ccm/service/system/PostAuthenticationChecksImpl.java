package com.saninco.ccm.service.system;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsChecker;

/**
 * Implements the UserDetailsChecker for post authentication check to prevent throwing
 * CredentialExpired exception.
 * 
 * @author Joe.Yang
 *
 */
public class PostAuthenticationChecksImpl implements UserDetailsChecker {

	public void check(UserDetails arg0) {
		// do nothing 
	}

}
