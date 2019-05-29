/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.EventJournal;

/**
 * @author Xinyu.Liu
 */
public class EventJournalDaoImpl extends HibernateDaoSupport implements IEventJournalDao {
	
	public EventJournalDaoImpl() {
	}

	/**
	 * @param eventType 
	 * */
	public void saveEventJournal(String eventType, String eventMessage, String eventData, Integer userId, String ipAddress) {
		EventJournal ej = new EventJournal();
		ej.setCreatedBy(userId);
		ej.setCreatedTimestamp(new Date());
		ej.setEventData(eventData);
		ej.setEventMessage(eventMessage);
		ej.setEventType(eventType);
		ej.setIpAddress(ipAddress);
		getHibernateTemplate().save(ej);
	}

	public void save(EventJournal ej) {
		try {
			getSession().connection().setReadOnly(false);
			getHibernateTemplate().save(ej);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
