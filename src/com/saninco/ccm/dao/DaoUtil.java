/**
 * 
 */
package com.saninco.ccm.dao;

/**
 * @author joe.yang
 *
 */
public class DaoUtil {
	public static String getUserPreviledgedBanIdSqlString(int userId){
		return "select b.id " +
				"from ban b ";
//		+
//				"where 0=(select count(up1.user_id) " +
//				"from user_privilege up1 " +
//				"where up1.user_id="+userId+" and up1.vendor_id is null and up1.ban_id is null) " +
//				"union " +
//				"select b.id " +
//				"from ban b, vendor v, user_privilege up " +
//				"where up.user_id="+userId+" and up.vendor_id is not null and up.ban_id is null and up.vendor_id=b.vendor_id " +
//				"union " +
//				"select b.id " +
//				"from ban b, user_privilege up " +
//				"where up.user_id="+userId+" and up.vendor_id is not null and up.ban_id is not null and up.vendor_id=b.vendor_id and up.ban_id=b.id " +
//				"union " +
//				"select b.id " +
//				"from ban b, user_privilege up " +
//				"where up.user_id="+userId+" and up.vendor_id is null and up.ban_id is not null and up.ban_id=b.id";
	}
	public static String ccmEscape(String param){
		return param.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_").replace("'", "\\'").replace("\"","\\\"");
	}
	public static String ccmEscape2(String param){
		return param.replace("\\", "\\\\").replace("'", "\\'").replace("\"","\\\"");
	}
	public static String ccmEscape3(String param){
		return param.replace("\\", "\\\\\\\\").replace("%", "\\%").replace("_", "\\_").replace("'", "\\'").replace("\"","\\\"");
	}
	public static String ccmEscape4(String param){
		return param.replace("\\", "\\\\").replace("\"","\\\"");
	}
	public static void main(String args []){
		System.out.println(getUserPreviledgedBanIdSqlString(1));
		
	}
}
