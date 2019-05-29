/**
 * 
 */
package com.saninco.ccm.action.email;

import org.apache.log4j.Logger;

import com.saninco.ccm.service.dispute.IDisputeService;
import com.saninco.ccm.service.email.IReceivedEmailService;
import com.saninco.ccm.service.email.ISendEmailService;

/**
 * Invoice related UI action class.
 * 
 */
public class EmailAction {
	/**
	 * 
	 */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IReceivedEmailService receivedEmailService;
	private ISendEmailService sendEmailService;
	private IDisputeService disputeService;
	
	
	public void createReceivedEmailCronJob() {
		logger.info("Enter createReceivedEmailCronJob method.");
		String flag = disputeService.findTimerManager(1);
		Integer processlistNum = disputeService.findUpdateTimerManagerProcesslist();
		
		if ("N".equals(flag) && processlistNum == 0 ){
			
			disputeService.updateTimerManager(1,"Y");
			
			try {
			    receivedEmailService.createReceivedEmailCronJob();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				disputeService.updateTimerManager(1, "N");
			}
			
		}
		logger.info("Exit createReceivedEmailCronJob method.");
	}
	
	public void sendDisputeReminderEmailCronJob() {
		logger.info("Enter sendDisputeReminderEmailCronJob method.");
		String flag = disputeService.findTimerManager(2);
		Integer processlistNum = disputeService.findUpdateTimerManagerProcesslist();
		
		if ("N".equals(flag) && processlistNum == 0 ){
			
			disputeService.updateTimerManager(2,"Y");
			
			try {
				sendEmailService.sendDisputeReminderEmailCronJob();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				disputeService.updateTimerManager(2, "N");
			}
		}
		logger.info("Exit sendDisputeReminderEmailCronJob method.");
	}
	
	
	public IReceivedEmailService getReceivedEmailService() {
		return receivedEmailService;
	}
	public void setReceivedEmailService(IReceivedEmailService receivedEmailService) {
		this.receivedEmailService = receivedEmailService;
	}
	public ISendEmailService getSendEmailService() {
		return sendEmailService;
	}
	public void setSendEmailService(ISendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	}

	public IDisputeService getDisputeService() {
		return disputeService;
	}

	public void setDisputeService(IDisputeService disputeService) {
		this.disputeService = disputeService;
	}
	

}
