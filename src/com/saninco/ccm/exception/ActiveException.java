/**
 * 
 */
package com.saninco.ccm.exception;

import org.springframework.security.AuthenticationException;

/**
 * @author Joe.Yang
 *
 */
public class ActiveException extends AuthenticationException {
	
	public ActiveException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1L;
 
}
