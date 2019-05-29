/**
 * 
 */
package com.saninco.ccm.util;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author joe.yang
 *
 */
public class EnumUserSearchField implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String code;
	private static String CODE_USER_NAME="user_name";
	private static String CODE_FIRST_NAME="first_name";
	private static String CODE_LAST_NAME="last_name";
	private static String CODE_EMAIL="email";

	public static final EnumUserSearchField USER_NAME=new EnumUserSearchField(CODE_USER_NAME, "User Name");
	public static final EnumUserSearchField FIRST_NAME=new EnumUserSearchField(CODE_FIRST_NAME, "First Name");
	public static final EnumUserSearchField LAST_NAME=new EnumUserSearchField(CODE_LAST_NAME, "Last Name");
	public static final EnumUserSearchField EMAIL=new EnumUserSearchField(CODE_EMAIL, "Email");
	private static final EnumUserSearchField[] userSearchFieldCodes = { USER_NAME, FIRST_NAME, LAST_NAME, EMAIL };
	/**
	 * Allows user to iterate over all elements of the enumeration.
	 */
	public static final List USERSEARCHFIELDLIST = Collections.unmodifiableList(Arrays.asList(userSearchFieldCodes));


	private EnumUserSearchField (String code, String name){
		this.code=code;
		this.name=name;
	}

	public String getCode(){
		return this.code;
	}

	public String getName(){
		return this.name;
	}

	public String toString(){
		return "code=" +  code + "; name="+ name;
	}

	public static EnumUserSearchField valueOf(String code){
		Iterator iter = USERSEARCHFIELDLIST.iterator();
		while (iter.hasNext()) {
			EnumUserSearchField userSearchFieldCode = (EnumUserSearchField)iter.next();
			if ( code.equals(userSearchFieldCode.getCode() ) ){
				return userSearchFieldCode;
			}
		}
		return null;
	}
	
	public static EnumUserSearchField nameOf(String name){
		Iterator iter = USERSEARCHFIELDLIST.iterator();
		while (iter.hasNext()) {
			EnumUserSearchField  userSearchFieldCode = (EnumUserSearchField)iter.next();
			if ( name.equals(userSearchFieldCode.getName() ) ){
				return userSearchFieldCode;
			}
		}
		return null;
	}

	private Object readResolve() throws ObjectStreamException{
		if (code.equalsIgnoreCase(CODE_USER_NAME)) return USER_NAME;
		if (code.equalsIgnoreCase(CODE_FIRST_NAME)) return FIRST_NAME;
		if (code.equalsIgnoreCase(CODE_LAST_NAME)) return LAST_NAME;
		if (code.equalsIgnoreCase(CODE_EMAIL)) return EMAIL;
		return null;
	}
}
