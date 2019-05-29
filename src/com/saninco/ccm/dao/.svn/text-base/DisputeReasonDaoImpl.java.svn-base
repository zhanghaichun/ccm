package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.DisputeReason;
 

/**
 * @author xinyu.Liu
 *
 */
public class DisputeReasonDaoImpl extends HibernateDaoSupport implements IDisputeReasonDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<DisputeReason> getDisputeReason() {
		logger.info("Enter method getDisputeReason");
		List<DisputeReason> l = (List<DisputeReason>)this.getHibernateTemplate().find("from DisputeReason order by disputeReasonName asc ");
		logger.info("Exit method getDisputeReason");
		return l;
	}
	
	public String getDisputeReasonAcronymByProrosalId(int prorosalId){
		
		final StringBuffer sb=new StringBuffer();
		sb.append("select dr.dispute_reason_acronym from proposal as p left join dispute_reason as dr on p.dispute_reason_id=dr.id where p.id= ");
		sb.append(""+prorosalId+"");
		Session session = getSession();
		try {
			SQLQuery query= session.createSQLQuery(sb.toString());
			return query.uniqueResult().toString();
		} finally {
			releaseSession(session);
		}
		
	}
	
	public DisputeReason findById(java.lang.Integer id) {
		//log.debug("getting DisputeReason instance with id: " + id);
		try {
			DisputeReason instance = (DisputeReason) getHibernateTemplate()
					.get("com.saninco.ccm.model.DisputeReason", id);
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}
	
}
