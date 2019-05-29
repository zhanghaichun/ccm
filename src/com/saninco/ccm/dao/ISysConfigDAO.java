package com.saninco.ccm.dao;

import java.util.Map;

import com.saninco.ccm.util.SystemEmailConfig;

public interface ISysConfigDAO {
	
	public SystemEmailConfig getSystemEmailConfig();
	
	public SystemEmailConfig getSystemDisputeEmailConfig();
	
	public String getSystemSmtpServer();
	
	public String getSystemSmtpServerPort();
	
	public String getSystemEmailUserName();
	
	public String getSystemEmailPassWord();
	
	public String getSystemEmailAdress();
	
	public String getUploadFilePath();
	
	public String getTariffFilePath();
	
	public String getReportFilePath();

	public String getSystemUrlAdress();

	public Map<String, String> findSysConfig();
	
	public String getsystemConfigForwordDisputeBccEmail();
	
}
