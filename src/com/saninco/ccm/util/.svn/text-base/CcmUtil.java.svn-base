package com.saninco.ccm.util;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

public final class CcmUtil {

	private CcmUtil(){};
	
	private static PasswordEncoder passwordEncoder = new ShaPasswordEncoder(1);
	
	public static void main(String[] a){
		System.out.print(encoderPassword("1"));
	}
	
	public static String encoderPassword(String pwd){
		return passwordEncoder.encodePassword(pwd, null);
	}
	
	public static PasswordEncoder getPasswordEncoder(){
		return passwordEncoder;
	}
	
	/**
	 * Convert characters which are out of UTF-8 to valid characters for Javascript to evaluate.
	 * @author Joe.Yang
	 * @param s
	 * @return
	 */
	
	public static String stringToSql(final String s){
		if(s == null) return null;
	    StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			switch (c) {
			case '"':
				sb.append("\\\"");
				break;
			case '\'':
				sb.append("\\\'");
				break;
			case '{':
				sb.append("[");
				break;
			case '}':
				sb.append("]");
				break;
			case ':':
				sb.append(" ");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}  
	
	public static String fileNameReplease(final String fileName){
		if(fileName == null) return null;
		String sb = fileName;
		sb = sb.replaceAll("/", "");
		return sb;
	}  
	
	
	public static String string2Json(final String s) {
	    StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			switch (c) {
			//case '\"':
			//	sb.append("\\\"");
			//	break;
//			case '\\':
//				sb.append("\\\\");
//				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}  
	
	
	public static String string2JsonAll(String s) {
	    StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}  
	
	
	public static String wikiContent2JsonAll(String s) {
	    StringBuffer sb = new StringBuffer();
 	    if(s == null) return sb.toString();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);

			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}  
}
