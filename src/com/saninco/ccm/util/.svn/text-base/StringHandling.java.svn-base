package com.saninco.ccm.util;

/**
 * 这是一个字符串处理的类
 * @author Administrator
 *
 */
public class StringHandling {
	
	/**
	 * 对传过来的字符串进行正确的替换，
	 * 以便sql中可以正确的执行查询动作
	 * 静态方法。
	 */
	public static String sqlStringReplace(String sqlString) {
		
		// %, _ 在sql的like查询中的处理比较特殊而且位于第一个
		// 位置的时候。
		if ( sqlString.indexOf("%") == 0 ) {
			sqlString = sqlString.replace("%", "\\%");
		}
		
		if ( sqlString.indexOf("_") == 0 ) {
			sqlString = sqlString.replace("_", "\\_");
		}
		
		return sqlString;
		
	}

}
