/**
 * 
 */
package com.saninco.ccm.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.InvoiceStV;
import com.saninco.ccm.util.SystemUtil;

/**
 * @author xinyu.Liu
 */
public class InvoiceStVDaoImpl extends HibernateDaoSupport implements IInvoiceStVDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	

	public void save(InvoiceStV sc) {
		logger.info("Enter method save.");
		getHibernateTemplate().save(sc);
		logger.info("Exit method save.");
	}
	
	public void delete(String tableName,String barCode){
		logger.info("Enter method delete.");
		StringBuffer sb = new StringBuffer("update " + tableName + " i set i.rec_active_flag='N' ");
		sb.append(",i.modified_timestamp= now() ");
		sb.append(",i.modified_by=");
		sb.append(SystemUtil.getCurrentUserId());
		sb.append(" where i.rec_active_flag='Y' and i.bar_code='");
		sb.append(barCode);
		sb.append("'");
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method delete.");
	}
	
}
