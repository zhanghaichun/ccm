package com.saninco.ccm.service.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IAttachmentFileDao;
import com.saninco.ccm.dao.IDisputeDao;
import com.saninco.ccm.dao.IEmailDao;
import com.saninco.ccm.dao.IInvoiceDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Email;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.EmailManager;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemEmailConfig;
import com.saninco.ccm.util.SystemUtil;

public class SendEmailServiceImpl implements ISendEmailService{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ISysConfigDAO sysConfigDAO;
	private EmailManager emailManager;
	private IAttachmentFileDao attachmentFileDao;
	private IEmailDao emailDao;
	private IDisputeDao disputeDao;
	private IInvoiceDao invoiceDao;
	private SystemEmailConfig sec ;
	private IUserDao userDao;
	
	public SendEmailServiceImpl(){
	}
	
	
	public void sendDisputeReminderEmailCronJob() {
		
		List<Map<String,String>> reminderEmails = emailDao.getDisputeReminderEmail();

		for(int i = 0 ; i < reminderEmails.size() ; i ++ ){
			
			Map<String,String> item = reminderEmails.get(i);
			String vendorName = disputeDao.getDisputeVendorName(item.get("dispute_number"));
			String claimNumber = disputeDao.getDisputeClaimNumber(item.get("dispute_number"));
			String accountNumber = disputeDao.getDisputeAccountNumber(item.get("dispute_number"));
			StringBuffer contact = new StringBuffer();
			contact.append("Dear "+vendorName+",<br/><br/>");
			contact.append("This is a friendly reminder that we didn't get reply on the following dispute over 30 days:<br/>");
			contact.append("Dispute Date:  "+item.get("created_timestamp")+"<br/>");
			contact.append("Ban:  "+accountNumber+"<br/>");
			contact.append("Claim No:  "+claimNumber+"<br/>");
			contact.append("Dispute No:  "+item.get("dispute_number")+"<br/><br/>");
			contact.append("Thanks in advance for your cooperation.");
			
			Email email = new Email();
			email.setSubject("Dispute "+accountNumber + " - " +claimNumber + " - " + item.get("dispute_number")+" Reply Reminder - IMPORTANT");
			email.setContent(contact.toString());
			email.setToAddress(item.get("to_address"));
			email.setCcAddress(item.get("cc_address"));
			email.setReplyTo(item.get("reply_to"));
			email.setBccAddress(sysConfigDAO.getsystemConfigForwordDisputeBccEmail());
			
			disputeDao.updateDisputeFlagAndReplayDateByDisputeNumber(item.get("dispute_number"));
			
			try {
				emailManager.sendEmail(email,true,sysConfigDAO.getSystemDisputeEmailConfig());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void createTheEmailByUserNameAndPassword(User user,String password){
		Email email = new Email();
		email.setSubject("Your temporary TEMS password!");
		email.setContent("username: "+user.getUserName()+";	password: "+password+"");
		email.setToAddress(user.getEmail());
		
		try {
			emailManager.sendEmail(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	public void sendTicketEmail(String subject,String text,String toEmail){
		Email email = new Email();
		email.setSubject(subject);
		email.setContent(text);
		email.setToAddress(toEmail);
		try {
			emailManager.sendEmail(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	public void sendApproveEmailToAssignmentUser(Invoice i) {
		logger.info(CcmFormat
				.formatEnterLog("Send Approve Email", i));
		User user = i.getUserByAssignmentUserId();
		if(sec == null) sec = sysConfigDAO.getSystemEmailConfig();
		
	    String text = "Hi "+user.getFirstName()+",<br/><br/>" + "Invoice Number: <a href = \""+sec.getSystemUrlAdress()+"viewInvoiceDetails.action?invoiceId="+i.getId()+"#NAME_invoiceHistoryPage\">"+ i.getInvoiceNumber()+"</a> is waiting for you to approve. Please log into TEMS and approve the invoice.<br/><br/> Thanks,";
		
		 Email email = new Email();
		       email.setContent(text);
			   email.setEmailType("A");
			   email.setCreatedBy(SystemUtil.getCurrentUserId());
			   email.setCreatedTimestamp(new Date());
			   email.setEmailStatus("1");
			   email.setModifiedBy(SystemUtil.getCurrentUserId());
			   email.setModifiedTimestamp(email.getCreatedTimestamp());
			   email.setRecActiveFlag("Y");
			   email.setSentDatetime(null);
			   email.setSystemMessage("");
			   email.setSubject(" Approve Reminder: Invoice #"+ i.getInvoiceNumber());
			   email.setToAddress(user.getEmail());
			   
	    try {
			emailManager.sendEmail(email,true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendForwardDisputeEmail(Map<String,String> msgMap) throws Exception{
		logger.info(CcmFormat
				.formatEnterLog("Send Forward Dispute Email", msgMap));
		
		String[] attachmentFileIds = null;
		List<String[]> files = new ArrayList();
		Email email = new Email();
		String attachmentFiles = msgMap.get("attachment");
		String content = CcmFormat.formatEmailContent(msgMap.get("message"));
		String claimNumber = disputeDao.getDisputeClaimNumber(msgMap.get("disputeNumber"));
		String accountNumber = disputeDao.getDisputeAccountNumber(msgMap.get("disputeNumber"));
		
		if(attachmentFiles != null && !"".equals(attachmentFiles) && attachmentFiles.split(",").length>0) {
			AttachmentPoint ap = new AttachmentPoint();
			attachmentFileIds = attachmentFiles.split(",");
			ap.setTableName("dispute");
			ap.setCreatedBy(SystemUtil.getCurrentUserId());
			ap.setCreatedTimestamp(new Date());
			ap.setModifiedBy(SystemUtil.getCurrentUserId());
			ap.setModifiedTimestamp(new Date());
			ap.setRecActiveFlag("Y");
			Integer newId = attachmentFileDao.createPoint(ap);
			int i = 0;
			for (String strs : attachmentFileIds) {
				
				AttachmentFile originalAttachmentFile = attachmentFileDao.findById(Integer.valueOf(strs.trim()));
				AttachmentFile attachmentFile = new AttachmentFile();
				attachmentFile.setAttachmentPoint(attachmentFileDao.loadPoint(newId));
				attachmentFile.setFilePath(originalAttachmentFile.getFilePath());
				attachmentFile.setFileName(originalAttachmentFile.getFileName());
				attachmentFile.setCreatedBy(SystemUtil.getCurrentUserId());
				attachmentFile.setCreatedTimestamp(new Date());
				attachmentFile.setModifiedBy(SystemUtil.getCurrentUserId());
				attachmentFile.setModifiedTimestamp(new Date());
				attachmentFile.setRecActiveFlag("Y");
				attachmentFileDao.save(attachmentFile);
				
				String[] file = {originalAttachmentFile.getFileName(),originalAttachmentFile.getFilePath()};
				files.add(file);
				i++;
				
			}
			
			email.setAttachmentPoint(ap);
			
        }

		email.setSubject(" Rogers : "+ accountNumber +" - "+ claimNumber +" - "+msgMap.get("disputeNumber"));
		email.setContent(content);
		email.setToAddress(msgMap.get("forwardTo"));
		email.setBccAddress(sysConfigDAO.getsystemConfigForwordDisputeBccEmail());
		email.setReplyTo(msgMap.get("replyTo"));
		
		
		disputeDao.updateDisputeTimestampByDisputeNumber(msgMap.get("disputeNumber").toString());
		emailManager.sendEmail(email,true,files,sysConfigDAO.getSystemDisputeEmailConfig(),true);
		
		
	}
	public void sendContactVendorDisputeEmail(Map<String,String> disputeMessage,AttachmentPoint point) throws Exception {
		logger.info(CcmFormat
				.formatEnterLog("Send Contact Vendor Dispute Email", disputeMessage));
		
		String content = CcmFormat.formatEmailContent(disputeMessage.get("message"));
		String claimNumber = disputeDao.getDisputeClaimNumber(disputeMessage.get("disputeNumber"));
		String accountNumber = disputeDao.getDisputeAccountNumber(disputeMessage.get("disputeNumber"));
		
		Email email = new Email();
		List<String[]> files = new ArrayList();
		email.setSubject(" Rogers : "+ accountNumber +" - "+ claimNumber +" - "+disputeMessage.get("disputeNumber"));
		email.setContent(content);
		email.setAttachmentPoint(point);
		email.setToAddress(disputeMessage.get("To"));
		email.setCcAddress(disputeMessage.get("CC"));
		email.setBccAddress(sysConfigDAO.getsystemConfigForwordDisputeBccEmail());

		if(point !=null){
			files = getAttachmentFileList(point.getId());
		}
		disputeDao.updateDisputeTimestamp(Integer.valueOf(disputeMessage.get("disputeId")));
		emailManager.sendEmail(email,true,files,sysConfigDAO.getSystemDisputeEmailConfig(),true);
	}
	
	public List<String[]> getAttachmentFileList(Integer pointId){
		
		List<String[]> files = new ArrayList();
		List<AttachmentFile> list= attachmentFileDao.findAllByAttchmentPiontIdPiontId(pointId);
		for (int i = 0; i<list.size();i++){
			String[] file = {list.get(i).getFileName().toString(),list.get(i).getFilePath().toString()};
			files.add(file);
		}
		
		return files;
	}
	
	public void sendInvoiceEmail(Map<String,String> disputeMessage,List<String[]> files ) throws Exception {
		logger.info(CcmFormat
				.formatEnterLog("Send Contact Invoice Email", disputeMessage));
		sec = sysConfigDAO.getSystemEmailConfig();
		Email email = new Email();
	
		String message = CcmFormat.formatEmailContent(disputeMessage.get("message"));
		String[] sList = message.split("<br/>");
		String hrefInvoiceNumber = "";
		String invoiceNumber = disputeMessage.get("invoiceNumber");
		
		for(String item : sList){
			if(item.indexOf("Invoice Number:") >= 0){
				hrefInvoiceNumber = "Invoice Number: <a href = \""+sec.getSystemUrlAdress()+"doViewOriginalList.action?invoiceId="+disputeMessage.get("invoiceId")+"\">"+invoiceNumber+"</a>";
				message = message.replace(item, hrefInvoiceNumber);
				break;
			}
		}

		StringBuffer button = new StringBuffer("<br/><br/><a href = \""+sec.getSystemUrlAdress()+"doExternalCommentsFromEmail.action?invoiceId="+disputeMessage.get("invoiceId")+"&workflowUserName="+disputeMessage.get("workflowUserName")+"&userId="+SystemUtil.getCurrentUserId()+"&userName="+SystemUtil.getCurrentUser().getFirstName()+"&invoiceNumber="+invoiceNumber+"&externalEmailId="+disputeMessage.get("externalEmailId")+"&typeAction=Approve"+"\"><input type=\"button\" value=\"Approve\" ></a>");
		button.append(" &nbsp;&nbsp;&nbsp;&nbsp;<a href = \""+sec.getSystemUrlAdress()+"doExternalCommentsFromEmail.action?invoiceId="+disputeMessage.get("invoiceId")+"&workflowUserName="+disputeMessage.get("workflowUserName")+"&userId="+SystemUtil.getCurrentUserId()+"&userName="+SystemUtil.getCurrentUser().getFirstName()+"&invoiceNumber="+invoiceNumber+"&externalEmailId="+disputeMessage.get("externalEmailId")+"&typeAction=Reject"+"\"><input type=\"button\" value=\"Reject\" ></a>");
		//button.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href = \""+sec.getSystemUrlAdress()+"doExternalCommentsFromEmail.action?invoiceId="+disputeMessage.get("invoiceId")+"&workflowUserName="+disputeMessage.get("workflowUserName")+"&userId="+SystemUtil.getCurrentUserId()+"&userName="+SystemUtil.getCurrentUser().getFirstName()+"&invoiceNumber="+invoiceNumber+"&externalEmailId="+disputeMessage.get("externalEmailId")+"\"><input type=\"button\" value=\"Comments\" ></a>");
		email.setContent(message + button.toString());
		
		email.setToAddress(disputeMessage.get("To"));
		email.setCcAddress(disputeMessage.get("Cc"));
		email.setBccAddress(sysConfigDAO.getsystemConfigForwordDisputeBccEmail());
		email.setSubject(disputeMessage.get("Subject"));
		email.setReplyTo(SystemUtil.getCurrentUser().getEmail());
		
		try {
			 emailManager.sendEmail(email,true,files,sysConfigDAO.getSystemEmailConfig(),false);
				 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public void sendInvoiceEmailBack(Map<String,String> disputeMessage) throws Exception {
		logger.info(CcmFormat
				.formatEnterLog("Send External Aprove Email Back", disputeMessage));
		
		Email email = new Email();
		email.setContent(CcmFormat.formatEmailContent(disputeMessage.get("message")));
		email.setToAddress(disputeMessage.get("To"));
		email.setBccAddress(sysConfigDAO.getsystemConfigForwordDisputeBccEmail());
		email.setSubject(disputeMessage.get("Subject"));
		
		try {
			emailManager.sendEmail(email,true,null,sysConfigDAO.getSystemEmailConfig(),false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
	}
	

	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public EmailManager getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}

	public IAttachmentFileDao getAttachmentFileDao() {
		return attachmentFileDao;
	}

	public void setAttachmentFileDao(IAttachmentFileDao attachmentFileDao) {
		this.attachmentFileDao = attachmentFileDao;
	}

	public IEmailDao getEmailDao() {
		return emailDao;
	}

	public void setEmailDao(IEmailDao emailDao) {
		this.emailDao = emailDao;
	}


	public IDisputeDao getDisputeDao() {
		return disputeDao;
	}

	public void setDisputeDao(IDisputeDao disputeDao) {
		this.disputeDao = disputeDao;
	}


	public IUserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}


	public IInvoiceDao getInvoiceDao() {
		return invoiceDao;
	}


	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

}
