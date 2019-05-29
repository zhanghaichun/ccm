/**
 * 
 */
package com.saninco.ccm.dao;

import com.saninco.ccm.model.EventJournal;


public interface IEventJournalDao {
	
	public void saveEventJournal(String eventType, String eventMessage, String eventData, Integer userId, String ipAddress);

	public void save(EventJournal ej);
	
}


