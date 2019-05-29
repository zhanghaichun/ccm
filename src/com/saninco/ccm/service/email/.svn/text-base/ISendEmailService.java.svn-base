package com.saninco.ccm.service.email;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.User;

public interface ISendEmailService {
	
	public void sendApproveEmailToAssignmentUser(Invoice i);
	
	public void createTheEmailByUserNameAndPassword(User user,String password);

	public void sendTicketEmail(String subject,String text,String toEmail);
	
	public void sendForwardDisputeEmail(Map<String,String> msgMap) throws Exception;
	
	public void sendContactVendorDisputeEmail(Map<String,String> disputeMessage,AttachmentPoint point) throws Exception;
	
	public void sendInvoiceEmail(Map<String,String> invoiceMessage,List<String[]> files) throws Exception;
	
	public void sendInvoiceEmailBack(Map<String,String> invoiceMessage) throws Exception;
	
	
	public void sendDisputeReminderEmailCronJob();
	
}
