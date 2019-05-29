package com.saninco.ccm.service;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.saninco.ccm.model.User;

/**
 * 
 * @author Joe.Yang
 *
 */
public class SendPasswordResetEmailAdvice implements AfterReturningAdvice, InitializingBean {

	private static final String DEFAULT_MAIL_FROM = "joe.yang@rci.rogers.com";

	private static final String DEFAULT_SUBJECT = "Password rest for Rogers Toll Free Portal";

	private final Logger logger = Logger.getLogger(getClass());

	private MailSender mailSender;

	private String mailFrom = DEFAULT_MAIL_FROM;

	private String subject = DEFAULT_SUBJECT;
	
	private String messageContent = "";

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void afterPropertiesSet() throws Exception {
		if (this.mailSender == null) {
			throw new IllegalStateException("mailSender is required");
		}
	}
	
	public void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable {
		if(returnValue == null || (! (returnValue instanceof List<?>))) {
			return;
		}
		
		List<?> valueList = (List<?>) returnValue;
		if(valueList.size() != 2 
				|| valueList.get(0) == null || (! (valueList.get(0) instanceof User))
				|| valueList.get(1) == null || (! (valueList.get(1) instanceof String))) 
		{
			return;
		}		
		
		User user = (User) valueList.get(0);
		String password = (String) valueList.get(1); // non-encrypted password

		// don't do anything if email address is not set
		if (user.getEmail() == null || user.getEmail().length() == 0) {
			return;
		}

		String text = messageContent;
		text = text.replaceAll("\\{1\\}", user.getFirstName()+ " " + user.getLastName());
		text = text.replaceAll("\\{2\\}", password);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom(this.mailFrom);
		mailMessage.setSubject(this.subject);
		mailMessage.setText(text);
		try {
			this.mailSender.send(mailMessage);
		}
		catch (MailException ex) {
			// just log it and go on
			logger.warn("An exception occured when trying to send email", ex);
		}
	}

	/**
	 * @return the messageContent
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}


}
