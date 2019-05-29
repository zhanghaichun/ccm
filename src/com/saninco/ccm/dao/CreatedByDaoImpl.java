package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class CreatedByDaoImpl extends HibernateDaoSupport implements  ICreatedByDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List getCreatedBy() {
		logger.info("Enter method getCreatedBy");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select u.id id,concat(ifnull(u.first_name,''),' ',ifnull(u.last_name,'')) uname from user u where u.system_user_flag!='Y' and u.rec_active_flag = 'Y' and u.active_flag = 'Y' order by concat(ifnull(u.first_name, ''), ' ', ifnull(u.last_name, '')) asc").list();
			logger.info("Exit method getCreatedBy");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	public List getAllUserList() {
		logger.info("Enter method getCreatedBy");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select u.id id,concat(ifnull(u.first_name,''),' ',ifnull(u.last_name,'')) uname from user u where u.rec_active_flag = 'Y' and u.active_flag = 'Y' order by concat(u.first_name,' ',u.last_name) asc").list();
			logger.info("Exit method getCreatedBy");
			return l;
		} finally {
			releaseSession(session);
		}
	}

}
