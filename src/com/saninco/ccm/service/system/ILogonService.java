/**
 * 
 */
package com.saninco.ccm.service.system;

import java.util.List;

import org.springframework.security.providers.AuthenticationProvider;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.User;

/**
 * @author joe.yang
 *
 */
public interface ILogonService extends AuthenticationProvider {
	
	public void changePassword(User user, String password) throws BPLException;
	
	public List<Object> resetPasswordByUsername(String username) throws BPLException;
	
	public List<Object> resetPasswordByEmail(String email) throws BPLException;
}
