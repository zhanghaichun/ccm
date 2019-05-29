/**
 * 
 */
package com.saninco.ccm.dao;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.ReconcileStatus;

/**
 * @author Joe.Yang
 * 
 */
public class ReconcileStatusDaoImpl extends HibernateDaoSupport implements
		IReconcileStatusDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	public ReconcileStatusDaoImpl() {
	}

	public ReconcileStatus findById(java.lang.Integer id) {
		logger.debug("getting ReconcileStatus instance with id: " + id);
		try {
			ReconcileStatus instance = (ReconcileStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.ReconcileStatus", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public ReconcileStatus load(int id) {
		ReconcileStatus instance = (ReconcileStatus) getHibernateTemplate().load(ReconcileStatus.class, id);
		return instance;
	}
}
