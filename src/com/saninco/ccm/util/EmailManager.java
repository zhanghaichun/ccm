package com.saninco.ccm.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.saninco.ccm.dao.IAttachmentFileDao;
import com.saninco.ccm.dao.IInvoiceDetailDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.model.Email;


public class EmailManager {
	
	private IAttachmentFileDao attachmentFileDao;
	private ISysConfigDAO sysConfigDAO;
	private IInvoiceDetailDao invoiceDetailDao;

	private EmailManager(){
	};
	
	@SuppressWarnings("unchecked")
	public void sendEmail(Email email,boolean isLoging,List<String[]> fileList,SystemEmailConfig inSec,boolean isReplyToTems) throws Exception{
		
		try{
			
			SystemEmailConfig sec = null;
			if(inSec != null) sec = inSec;
			if(sec == null ) sec = sysConfigDAO.getSystemEmailConfig();
			
			if(isLoging) invoiceDetailDao.addEmailApprove(email);
			
			Properties props = new Properties();
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.timeout", "35000");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
			Session session = Session.getInstance(props);
	
			Message msg = new MimeMessage(session);
			msg.setSubject(email.getSubject());
	
			MimeMultipart msgMultipart = new MimeMultipart("mixed");
			msg.setContent(msgMultipart);
	
			MimeBodyPart content = new MimeBodyPart();
			MimeMultipart bodyMultipart = new MimeMultipart("related");
			
			MimeBodyPart emailHTMLText = new MimeBodyPart();
			emailHTMLText.setContent(email.getContent(), "text/html;charset=utf-8");
			
			bodyMultipart.addBodyPart(emailHTMLText);
			content.setContent(bodyMultipart);
			msgMultipart.addBodyPart(content);
			
			if (fileList != null)addEmailAttch(msgMultipart, fileList); 
			
			msg.setHeader("From", sec.getSystemEmailAdress());
			
			if(!(email.getToAddress()==null || "".equals(email.getToAddress()))) {
				msg.setRecipients(Message.RecipientType.TO, new InternetAddress().parse(email.getToAddress().replace(";", ",")));
			}
			if(!(email.getBccAddress()==null || "".equals(email.getBccAddress()))) {
				
				msg.setRecipients(Message.RecipientType.BCC, new InternetAddress().parse(email.getBccAddress().replace(";", ",")));
			}
			if(!(email.getCcAddress()==null || "".equals(email.getCcAddress()))) {
				
				msg.setRecipients(Message.RecipientType.CC, new InternetAddress().parse(email.getCcAddress().replace(";", ",")));
			}
			// 收到,邮件回复时,有的邮箱支持多个,有的邮箱不支持
			if(!(email.getReplyTo()==null || "".equals(email.getReplyTo()))) {
				String[] replyEmails = email.getReplyTo().replace(";", ",").split(",");
				InternetAddress[] listAddress = null;
				if(isReplyToTems) {
					listAddress = new InternetAddress[replyEmails.length+1];
					listAddress[0] = new InternetAddress(sec.getSystemEmailUserName());
					for(int i=0 ; i < replyEmails.length ; i++){
						listAddress[i+1] = new InternetAddress(replyEmails[i]);
					}
				}else{
					listAddress = new InternetAddress[replyEmails.length];
					for(int i=0 ; i < replyEmails.length ; i++){
						listAddress[i] = new InternetAddress(replyEmails[i]);
					}
				}
				
				msg.setReplyTo(listAddress); 
			}
	
			msg.saveChanges();
			

			Transport transport = session.getTransport();
			transport.connect(sec.getSystemEmailSmtpServer(),
					          sec.getSystemEmailSmtpServerPort(), 
					          sec.getSystemEmailUserName(),
					          sec.getSystemEmailPassWord());
	
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			if(isLoging) invoiceDetailDao.updateEmailApprove(email.getId(), 2);
			
		}catch (Exception e) {
			if(isLoging) invoiceDetailDao.updateEmailApprove(email.getId(), 3);
			throw e;
		}
	}
	
	private void addEmailAttch(MimeMultipart msgMultipart,List<String[]> fileList) throws MessagingException{
		// strs[0] : File Name , strs[1] : File Path
		for(String[] strs : fileList){
			MimeBodyPart attch = new MimeBodyPart();	
			
			DataSource ds = new FileDataSource(strs[1]);
			DataHandler dh = new DataHandler(ds );
			attch.setDataHandler(dh);
			attch.setFileName(strs[0]);
			
			msgMultipart.addBodyPart(attch);
		}
	}

	public void sendEmail(Email e) throws Exception{
		this.sendEmail(e,false,null,null,true);
	}
	
	public void sendEmail(Email e,SystemEmailConfig inSec) throws Exception{
		this.sendEmail(e,false,null,inSec,true);
	}
	
	public void sendEmail(Email e,boolean isLoging) throws Exception{
		this.sendEmail(e,isLoging,null,null,true);
	}
	
	public void sendEmail(Email e,boolean isLoging,SystemEmailConfig inSec) throws Exception{
		this.sendEmail(e,isLoging,null,inSec,true);
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

	public IInvoiceDetailDao getInvoiceDetailDao() {
		return invoiceDetailDao;
	}

	public void setInvoiceDetailDao(IInvoiceDetailDao invoiceDetailDao) {
		this.invoiceDetailDao = invoiceDetailDao;
	}
	
}
