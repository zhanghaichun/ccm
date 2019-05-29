package com.saninco.ccm.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.InternetAddress;

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;

import com.saninco.ccm.model.User;

public class SystemUtil {
	
	private static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	public static final SimpleDateFormat defaulDateFormater = new SimpleDateFormat("yyyy/MM/dd");
	public static Map<String, String> sysConfigMap;
	public static Authentication getCurrentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public static User getCurrentUser() {
		Authentication authentication = getCurrentAuthentication();
		if(authentication == null) return null;
		return (User) authentication.getPrincipal();
	}
	
	public static List<String> getCurrentUserAuthorities(){
		List<String> userAuthorities = new ArrayList<String>();
		GrantedAuthority[] authorities = getCurrentUser().getAuthorities();
		if (authorities!=null) {
			for (int i = 0; i < authorities.length; i++) {
				userAuthorities.add(authorities[i].getAuthority());
			}
		}
		return userAuthorities;
	}
	
	/**
	 * Generates a random 12-character password containing 4 digits, 
	 * 4 lower-case letters, and 4 upper-case letters. 
	 * @return
	 */
	public static String generatePassword() {
		
		StringBuffer password = new StringBuffer();		
		Random random = new Random(System.currentTimeMillis());
		
		// use an array to record the remaining number of items for each category
		// category index: 0 - number; 1 - lower-case letter; 2 - upper-case letter
		int[] remains = new int[] {4, 4, 4};
		
		// generate 12 random characters one by one and append them to password
		for(int i = 0; i < 12; i++) {			
			// decide category for i-th character
			int category = random.nextInt(remains.length);
			
			// check if finished for this category
			while(remains[category] == 0) {
				// move to next category
				if((++category) >= remains.length) category = 0;
			}
			
			// generate a character for the category
			if(category == 0) {
				// a number
				password.append( random.nextInt(10) );
			}
			else {
				// a letter
				int index = random.nextInt(LOWER_CASE_LETTERS.length());
				char ch = LOWER_CASE_LETTERS.charAt(index);
				
				password.append( (category == 1) ? ch : Character.toUpperCase(ch) );
			}
			
			remains[category]--;			
		}
		
		return password.toString();
	}
	
	// for testing
//	public static void main(String[] args) {
//		testValidateEmailAddress();
//	}
//	
//	private static void testValidateEmailAddress() {
//		String email = null;
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//		
//		email = "";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//		
//		email = "xyz";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "xyz@";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "@xyz";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "@xyz.com";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "xyz@xyz";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "xyz@xyz++.com";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "xy++z@xyz.com";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "xyz@xyz.com";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//
//		email = "xyz.com@xyz.com";
//		System.out.println("Validate email " + email + ": " + validateEmailAddress(email));
//	}
	
	
//	private static void testGeneratePassword() {
//		// test generatePassword
//		for(int i = 0; i < 10; i++) {
//			System.out.println("Password " + i + ": " + generatePassword());
//			try {
//				Thread.sleep(500L);
//			}
//			catch(Exception ex) {
//				// ignore
//			}
//		}
//	}
	
	/**
	 * Returns a boolean flag indicating if the given email address conforms syntax 
	 * rules of RFC 822.
	 * @param email specifies email address to be checked
	 * @return true - if given email address conforms syntax rules of RFC 822;
	 *         false - otherwise
	 */
	public static boolean validateEmailAddress(String email) {
		try {
			new InternetAddress(email).validate();
			
			return true;
		}
		catch(Exception ex) {
			// ignore
			return false;
		}
		
	}
	
	public static Integer getCurrentUserId(){
		User currentUser = getCurrentUser();
		if(currentUser == null)return null;
		return currentUser.getId();
	}
	
	public static String getCurrentUserName(){
		User currentUser = getCurrentUser();
		if(currentUser == null)return null;
		return currentUser.getFirstName()+" " +currentUser.getLastName();
	}
	
	public static String getCurrentEmail(){
		User currentUser = getCurrentUser();
		if(currentUser == null)return null;
		return currentUser.getEmail();
	}
	
	public static Integer getThemeListKey(){
		if(getCurrentUser() == null)return 1;
		if(getCurrentUser().getTheme() == null)return 1;
		return getCurrentUser().getTheme().getId();
	}
	
	public static Integer getCurrentUserThemeId(){
		if(getCurrentUser() == null)return 1;
		if(getCurrentUser().getTheme() == null)return 1;
		if(getCurrentUser().getTheme().getId() == 0){
			if(getCurrentUser().getRandomtheme() == null)return 1;
			return getCurrentUser().getRandomtheme().getId();
		}
		return getCurrentUser().getTheme().getId();
	}
	
	public static String getCurrentUserThemePath(){
		if(getCurrentUser() == null)return "Default";
		if(getCurrentUser().getTheme() == null)return "Default";
		return getCurrentUser().getTheme().getThemeName();
	}
	
	public static boolean isCurrentUserGranted(String roleName){
		for(GrantedAuthority gal:getCurrentUser().getAuthorities()){
			if(gal.getAuthority().equals(roleName)) return true;
		}
		return false;
	}
	
	public static boolean isCurrentUserADepartmentUserOrGuest() {
		if(isCurrentUserGranted(Constants.ROLE_IT_ADMIN)
				|| isCurrentUserGranted(Constants.ROLE_BIZ_ADMIN)
				|| isCurrentUserGranted(Constants.ROLE_BIZ_USER)
				|| isCurrentUserGranted(Constants.ROLE_PWGSC_USER))
		{
			return false;
		}
		
		return true;
	}
	
	public static boolean isCurrentUserADepartmentUser() {
		return isCurrentUserGranted(Constants.ROLE_DEPT_USER);
	}
	
	public static String parseString(Date date) {
		try {
			return defaulDateFormater.format(date);
		}
		catch(Exception ex) {
			// ignore
		}
		
		return "";
	}

	public static Map<String, String> getSysConfigMap() {
		return sysConfigMap;
	}

	public static void setSysConfigMap(Map<String, String> sysConfigMap) {
		SystemUtil.sysConfigMap = sysConfigMap;
	}

}
