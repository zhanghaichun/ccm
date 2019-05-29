package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.DisputeStatus;
 

/**
 * @author Jian.Dong
 *
 */
public class DisputeStatusDaoImpl extends HibernateDaoSupport implements IDisputeStatusDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<DisputeStatus> getDisputeStatus() {
		logger.info("Enter method getDisputeStatus");
		List<DisputeStatus> l = (List<DisputeStatus>)this.getHibernateTemplate().find("from DisputeStatus d order by d.disputeStatusName asc ");
		logger.info("Exit method getDisputeStatus");
		return l;
	}
	
	public DisputeStatus findById(java.lang.Integer id) {
		//log.debug("getting DisputeStatus instance with id: " + id);
		try {
			DisputeStatus instance = (DisputeStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.DisputeStatus", id);
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}

}
