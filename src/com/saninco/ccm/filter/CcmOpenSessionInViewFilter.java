/**
 * 
 */
package com.saninco.ccm.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * @author Joe.Yang
 *
 */
public class CcmOpenSessionInViewFilter extends OpenSessionInViewFilter {
	@Override
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		session.flush();
		super.closeSession(session, sessionFactory);
	}

	@Override
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session =  super.getSession(sessionFactory);
		session.setFlushMode(FlushMode.AUTO);
		
		return session;
	}
}
