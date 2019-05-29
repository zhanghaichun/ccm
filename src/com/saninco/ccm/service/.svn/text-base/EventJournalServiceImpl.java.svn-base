/**
 * 
 */
package com.saninco.ccm.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.saninco.ccm.dao.IEventJournalDao;
import com.saninco.ccm.util.SystemUtil;

/**
 * AOP advice class to write event journal to database.
 * 
 * @author Joe.Yang
 *
 */
public class EventJournalServiceImpl implements IEventJournalService {
	Logger logger = Logger.getLogger(this.getClass());
	private IEventJournalDao eventJournalDao;
	/**
	 * 
	 */
	public EventJournalServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.service.IEventJournalService#saveEventJournal(org.aspectj.lang.ProceedingJoinPoint)
	 */
	public Object saveEventJournal(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("Enter method saveEventJournal.");
		//get target class name as event type
		String eventType = pjp.getTarget().getClass().getName(); 
        String eventMessage = "Executed " + pjp.getSignature().getName();
        Object[] arguments = pjp.getArgs(); 
        String eventData = arguments.length + " arguments.";
		eventJournalDao.saveEventJournal(eventType, eventMessage, eventData, SystemUtil.getCurrentUserId(), SystemUtil.getCurrentUser().getIpAddress());
		logger.info("Exit method saveEventJournal.");
		return pjp.proceed();
	}

	/**
	 * @return the eventJournalDao
	 */
	public IEventJournalDao getEventJournalDao() {
		return eventJournalDao;
	}

	/**
	 * @param eventJournalDao the eventJournalDao to set
	 */
	public void setEventJournalDao(IEventJournalDao eventJournalDao) {
		this.eventJournalDao = eventJournalDao;
	}

}
