package com.saninco.ccm.service.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IAttachmentFileDao;
import com.saninco.ccm.dao.IReceivedEmailDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.ReceivedEmail;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.ReceivedEmailUtil;
import com.saninco.ccm.util.SystemEmailConfig;
import com.saninco.ccm.util.SystemUtil;

public class ReceivedEmailServiceImpl implements IReceivedEmailService{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IAttachmentFileDao attachmentFileDao;
	private ISysConfigDAO sysConfigDAO;
	private IReceivedEmailDao receivedEmailDao;
	
	
	public void createReceivedEmailCronJob() {
		logger.info(CcmFormat.formatEnterLog("createReceivedEmailCronJob"));
		
		SystemEmailConfig sec = sysConfigDAO.getSystemDisputeEmailConfig();
		
		Properties props = new Properties();
		props.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
		props.setProperty("mail.imap.socketFactory.port",sec.getSystemEmailImapServerPort());  
		props.setProperty("mail.store.protocol","imaps");    
		props.setProperty("mail.imap.port", sec.getSystemEmailImapServerPort());    
		Session session = Session.getDefaultInstance(props,null);  
		session.setDebug(false);
        Store store = null;
        Folder folder = null;
    	Folder dfolder = null;
		try {
			
			store = session.getStore("imaps");
			store.connect(sec.getSystemEmailImapServer(), sec.getSystemEmailUserName(), sec.getSystemEmailPassWord());
			
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			dfolder = store.getFolder("Download");//读取后的文件夹
			dfolder.open(Folder.READ_WRITE);  
			Message[] messages = folder.getMessages();
			
			logger.info("Messages's length: " + messages.length);
			for (int i = 0; i < messages.length; i++) {
				ReceivedEmail receivedEmail = new ReceivedEmail();
				ReceivedEmailUtil reu = new ReceivedEmailUtil((MimeMessage)messages[i]);
				receivedEmail.setToAddress(reu.getMailAddress(reu.EMAIL_ADDRESS_TO));
				receivedEmail.setBccAddress(reu.getMailAddress(reu.EMAIL_ADDRESS_BCC));
			    receivedEmail.setCcAddress(reu.getMailAddress(reu.EMAIL_ADDRESS_CC));
			    receivedEmail.setReplyTo(reu.getReplyTo());
			    receivedEmail.setContentHtml(reu.getContentHtml());
			    receivedEmail.setContentText(reu.getContentText());
			    receivedEmail.setSubject(reu.getSubject());
			    logger.info("Messages's Subject: " + reu.getSubject());
			    receivedEmail.setSentDatetime(reu.getSentDate());
			    receivedEmail.setReplySign(reu.getReplySign() ? "Y" : "N");
			    receivedEmail.setAutoReplyFlag(reu.getAutoReplySign() ? "Y" : "N");
				receivedEmail.setMessageId(reu.getMessageId());
				receivedEmail.setFromAddress(reu.getFrom());
//					receivedEmail.setDescription(reu.getDescription());
				receivedEmail.setInReplyTo(reu.getInReplyTo());
				receivedEmail.setMsgReferences(reu.getReferences());
				receivedEmail.setCreatedBy(SystemUtil.getCurrentUserId());
				receivedEmail.setCreatedTimestamp(new Timestamp(new Date().getTime()));
				receivedEmail.setModifiedBy(SystemUtil.getCurrentUserId());
				receivedEmail.setModifiedTimestamp(new Timestamp(new Date().getTime()));
				receivedEmail.setRecActiveFlag("Y");
			    saveReceivedEmail(receivedEmail, reu.getAttachmentList());
			}
			
			receivedEmailDao.saveDisputeMessageByReceivedEmail();
			
			folder.copyMessages(messages, dfolder);//复制到新文件夹  
			folder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);//删除源文件夹下的邮件  
			//		        
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				// 释放资源     
				if (dfolder != null && dfolder.isOpen())
					dfolder.close(true);
				if (folder != null && folder.isOpen())
				    folder.close(true);
				if (store != null)
				    store.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
       
		
        logger.info(CcmFormat.formatExitLog());
	}
	
	public void saveReceivedEmail(ReceivedEmail receivedEmail, List<Map<String , Object>> attachmentList) {
		
		logger.info(CcmFormat.formatEnterLog("Save received email"));
		try{
			List existReceivedEmails =  receivedEmailDao.findByMessageId(receivedEmail.getMessageId());
			if (existReceivedEmails == null || existReceivedEmails.size() == 0) {
				AttachmentPoint ap = null;
				if(attachmentList != null && attachmentList.size()>0){
					ap = new AttachmentPoint();
					ap.setTableName("received_email");
					ap.setCreatedBy(SystemUtil.getCurrentUserId());
					ap.setCreatedTimestamp(new Date());
					ap.setModifiedBy(SystemUtil.getCurrentUserId());
					ap.setModifiedTimestamp(new Date());
					ap.setRecActiveFlag("Y");
					attachmentFileDao.createPoint(ap);
					String dirPath = getFilePath();
					File dir = new File(dirPath);
					if(dir.exists() && !dir.isDirectory()){
						dir.delete();
					}
					if(!dir.exists()){
						dir.mkdirs();
					}
					
					for(int i = 0; i < attachmentList.size(); i++){
						
						Map<String,Object> attachmentMap = attachmentList.get(i);
						InputStream in = (InputStream)attachmentMap.get("inputStream");
						String fname = (String)attachmentMap.get("fileName");
						String activeFlag = (String)attachmentMap.get("activeFlag");
						
						String fileType = "";
						if (fname != null && fname.indexOf(".") > 0) {
							fileType = fname.substring(fname.lastIndexOf("."),fname.length());
						}
						
						String fileName = UUID.randomUUID().toString() + fileType;
						File out = null;
						String outPath = null;
						BufferedOutputStream bos = null;  
				        BufferedInputStream bis = null;
						outPath = dirPath + "/" + fileName;
						out = new File(outPath);
						
						try {  
				            bos = new BufferedOutputStream(new FileOutputStream(out));  
				            bis = new BufferedInputStream(in);
				            
				            int c;  
				            while ((c = bis.read()) != -1) {  
				                bos.write(c);  
				                bos.flush();  
				            }  
				            AttachmentFile af = new AttachmentFile();
							af.setFilePath(outPath);
							af.setFileName(fname);
							af.setAttachmentPoint(ap);
							af.setCreatedBy(0);
							af.setCreatedTimestamp(new Date());
							af.setModifiedBy(0);
							af.setModifiedTimestamp(new Date());
							af.setRecActiveFlag(activeFlag);
							
							attachmentFileDao.save(af);
				        } catch (Exception exception) { 
				        	logger.error(CcmFormat.formatErrorLog(exception));
							throw new RuntimeException("file save error!");
				        } finally {  
				            bos.close();  
				            bis.close();  
				        }
					}
				}
				receivedEmail.setAttachmentPoint(ap);
				receivedEmailDao.save(receivedEmail);
			} 
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	public String getFilePath(){
		return sysConfigDAO.getUploadFilePath();
	}

	public IAttachmentFileDao getAttachmentFileDao() {
		return attachmentFileDao;
	}

	public void setAttachmentFileDao(IAttachmentFileDao attachmentFileDao) {
		this.attachmentFileDao = attachmentFileDao;
	}

	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public IReceivedEmailDao getReceivedEmailDao() {
		return receivedEmailDao;
	}

	public void setReceivedEmailDao(IReceivedEmailDao receivedEmailDao) {
		this.receivedEmailDao = receivedEmailDao;
	}
	
	
}
