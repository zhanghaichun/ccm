package com.saninco.ccm.dao;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.SystemEmailConfig;

public class SysConfigDAOImpl extends HibernateDaoSupport implements ISysConfigDAO {
	
	public SystemEmailConfig getSystemEmailConfig(){
		SystemEmailConfig systemEmailConfig = new SystemEmailConfig();
		try {
			systemEmailConfig.setSystemEmailAdress(getSystemEmailAdress());
			systemEmailConfig.setSystemEmailPassWord(getSystemEmailPassWord());
			systemEmailConfig.setSystemEmailSmtpServer(getSystemSmtpServer());
			systemEmailConfig.setSystemEmailSmtpServerPort(Integer.valueOf(getSystemSmtpServerPort()));
			systemEmailConfig.setSystemEmailUserName(getSystemEmailUserName());
			systemEmailConfig.setSystemUrlAdress(getSystemUrlAdress());
		} catch (NumberFormatException e) {
			logger.error( e); 
		}
		return systemEmailConfig;
	}
	
	public SystemEmailConfig getSystemDisputeEmailConfig(){
		SystemEmailConfig systemEmailConfig = new SystemEmailConfig();
		try {
			systemEmailConfig.setSystemEmailPassWord(getSysConfigInfo("system_dispute_email_password"));
			systemEmailConfig.setSystemEmailSmtpServer(getSysConfigInfo("system_dispute_email_smtp_server"));
			systemEmailConfig.setSystemEmailSmtpServerPort(Integer.valueOf(getSysConfigInfo("system_dispute_email_smtp_server_port")));
			systemEmailConfig.setSystemEmailUserName(getSysConfigInfo("system_dispute_email_username"));
			systemEmailConfig.setSystemEmailImapServerPort(getSysConfigInfo("system_dispute_email_imap_server_port"));
			systemEmailConfig.setSystemEmailImapServer(getSysConfigInfo("system_dispute_email_imap_server"));
			systemEmailConfig.setSystemDisputeEmailCopyToServer(getSysConfigInfo("dispute_email_copy_to"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return systemEmailConfig;
	}
	
	public String getSysConfigInfo(String parameter) {
		logger.info("Enter method getSystemDisputeEmailInfo.");
		String sql = "select t.value from sys_config t where t.parameter='"+parameter+"';";
		Session session = getSession();
		try {
			String systemEmailAdress = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getSystemDisputeEmailInfo.");
			return systemEmailAdress;
		} finally {
			releaseSession(session);
		}
	}
	
	public String getSystemSmtpServer() {
		logger.info("Enter method getSystemSmtpServer.");
		String sql = "select t.value from sys_config t where t.parameter='system_email_smtp_server'";
		Session session = getSession();
		try {
			String server = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getSystemSmtpServer.");
			return server;
		} finally {
			releaseSession(session);
		}
	}	
	
	public String getSystemSmtpServerPort() {
		logger.info("Enter method getSystemSmtpServerPort.");
		String sql = "select t.value from sys_config t where t.parameter='system_email_smtp_server_port'";
		Session session = getSession();
		try {
			String serverPort = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getSystemSmtpServerPort.");
			return serverPort;
		} finally {
			releaseSession(session);
		}
	}	
	
	public String getSystemEmailUserName() {
		logger.info("Enter method getSystemEmailUserName.");
		String sql = "select t.value from sys_config t where t.parameter='system_email_username'";
		Session session = getSession();
		try {
			String emailUserName = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getSystemEmailUserName.");
			return emailUserName;
		} finally {
			releaseSession(session);
		}
	}	
	
	public String getSystemEmailPassWord() {
		logger.info("Enter method getSystemEmailPassWord.");
		String sql = "select t.value from sys_config t where t.parameter='system_email_password'";
		Session session = getSession();
		try {
			String emailPassWord = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getSystemEmailPassWord.");
			return emailPassWord;
		} finally {
			releaseSession(session);
		}
	}
	
	public String getSystemEmailAdress() {
		logger.info("Enter method getSystemEmailAdress.");
		String sql = "select t.value from sys_config t where t.parameter='system_email_from_address'";
		Session session = getSession();
		try {
			String systemEmailAdress = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getSystemEmailAdress.");
			return systemEmailAdress;
		} finally {
			releaseSession(session);
		}
	}
	
	public String getUploadFilePath() {
		logger.info("Enter method getUploadFilePath.");
		String sql = "select t.value from sys_config t where t.parameter='upload_file_path'";
		Session session = getSession();
		try {
			String path = (String)session.createSQLQuery(sql).uniqueResult();			
			logger.info("Exit method getUploadFilePath.");

			return path;
		} finally {
			releaseSession(session);
		}
	}
	
	public String getTariffFilePath() {
		logger.info("Enter method getUploadFilePath.");
		String sql = "select t.value from sys_config t where t.parameter='tariff_file_path'";
		Session session = getSession();
		try {
			String path = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getUploadFilePath.");
			return path;
		} finally {
			releaseSession(session);
		}
	}
	
	public String getReportFilePath(){
		logger.info("Enter method getReportFilePath.");
		String sql = "select t.value from sys_config t where t.parameter='report_file_path'";
		Session session = getSession();
		try {
			String path = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getReportFilePath.");
			return path;
		} finally {
			releaseSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> findSysConfig() {
		Session session = getSession();
		String sql = "select * from sys_config";
		try {
			List<Map<String, String>> sysConfigList = session.createSQLQuery(sql).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			Map<String,String> sysConfigMap = new HashMap<String, String>();
			for (Map<String, String> map : sysConfigList) {
				sysConfigMap.put(map.get("parameter"), map.get("value"));
			}
			
			return sysConfigMap;
		} finally {
			releaseSession(session);
		}
	}
	
	public String getSystemUrlAdress() {
		logger.info("Enter method getsystemUrlAdress.");
		String sql = "select t.value from sys_config t where t.parameter='system_Url_from_address'";
		Session session = getSession();
		try {
			String systemUrlAdress = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getsystemUrlAdress.");
			return systemUrlAdress;
		} finally {
			releaseSession(session);
		}
	}
	
	public String getsystemConfigForwordDisputeBccEmail() {
		logger.info("Enter method getsystemConfigForwordDisputeBccEmail.");
		String sql = "select t.value from sys_config t where t.parameter='forword_dispute_bcc_email'";
		Session session = getSession();
		try {
			String BccEmail = (String)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getsystemConfigForwordDisputeBccEmail.");
			return BccEmail;
		} finally {
			releaseSession(session);
		}
	}
	
}
