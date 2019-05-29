package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.model.Email;
import com.saninco.ccm.vo.OperationsVO;

public interface IEmailDao {
	public void save(Email transientInstance);
	public void delete(Email persistentInstance);
	public Email findById(java.lang.Integer id);
	public List findByExample(Email instance);
	public List findByProperty(String propertyName, Object value);
	public List findBySubject(Object subject);
	public List findByToAddress(Object toAddress);
	public List findByCcAddress(Object ccAddress);
	public List findByBccAddress(Object bccAddress);
	public List findByContent(Object content);
	public List findByEmailStatus(Object emailStatus);
	public List findBySystemMessage(Object systemMessage);
	public List findByCreatedBy(Object createdBy);
	public List findByModifiedBy(Object modifiedBy);
	public List findByRecActiveFlag(Object recActiveFlag);
	public List findAll();
	public Email merge(Email detachedInstance);
	public void attachDirty(Email instance);
	public void attachClean(Email instance);
	public Integer getPreEmailsTotalPageNoOfDispute(OperationsVO operationsVO);
	public List<String> searchPreEmailsOfDispute(OperationsVO operationsVO);
	public List findCheckedEmailsToSend(String emailIds);
	public List<Map<String,String>> getDisputeReminderEmail();
}
