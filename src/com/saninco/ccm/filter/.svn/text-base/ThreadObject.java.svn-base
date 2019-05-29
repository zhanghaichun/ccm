package com.saninco.ccm.filter;


public class ThreadObject {

	public static ThreadLocal<String> ipAddress = new ThreadLocal<String>();
	
	public static String getClientIpAddress(){
		return ipAddress.get();
	}
	
	public static void setClientIpAddress(String ip){
		ipAddress.set(ip);
	}
}
