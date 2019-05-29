package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.ReceivedEmail;

public interface IReceivedEmailDao {
	
	public void saveDisputeMessageByReceivedEmail();
	
	public void save(ReceivedEmail receivedEmail);
	
	public List findAll();
	
	public List findByProperty(String propertyName, Object value);
	
	public List findByMessageId(Object messageId);
}
