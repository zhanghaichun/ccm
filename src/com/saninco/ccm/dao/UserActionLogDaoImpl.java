/**
 * 
 */
package com.saninco.ccm.dao;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.UserActionLog;

/**
 * @author jian.dong
 */
public class UserActionLogDaoImpl extends HibernateDaoSupport implements IUserActionLogDao {
//	private final Logger logger = Logger.getLogger(this.getClass());

	public void save(UserActionLog userActionLog) {
		Session session = getSession();
		try {
			session.save(userActionLog);
		} finally {
			releaseSession(session);
		}
	}
	
}
