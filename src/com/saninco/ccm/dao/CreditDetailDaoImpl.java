package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Credit;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.vo.SearchTicketVO;
import com.saninco.ccm.vo.SearchVO;

public class CreditDetailDaoImpl extends HibernateDaoSupport implements ICreditDetailDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Select Credit Class By creditId;
	 * Return Credit Class
	 */
	public Credit getCredit(Integer id) {
		logger.info("Enter method getCredit.");
		
		HibernateTemplate template = this.getHibernateTemplate();
		Credit c = (Credit) template.get(Credit.class, id);
		
		logger.info("Exit method getCredit.");
		return c;
	}
	
	/**
	 * Search Dispute collection to database Dispute_Table
	 */
	public List<String> getDisputeList(SearchVO v,Integer cid) {
		logger.info("Enter method getDisputeList.");
		
		final String sql = this.getSearchQueryString(v,cid);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		
		logger.info("Exit method getDisputeList.");
		return list;
	}
	
	/**
	 * Search Credit data totail number
	 * Return Credit data totail number
	 */
	public long getNumberOfCredit(SearchVO v,Integer cid) {
		logger.info("Enter method getNumberOfCredit.");
		
		final String sql = this.getDisputeSearchNumberQueryString(v, cid);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		
		logger.info("Exit method getNumberOfCredit.");
		return count;
	}
	
	/**
	 * Edit Search_Dispute_Data_Collection String 
	 * @param v
	 * @param cid
	 * @return HQL String
	 */
	private String getDisputeSearchNumberQueryString(SearchVO v,Integer cid){
		logger.info("Enter method getDisputeSearchNumberQueryString.");
		
		StringBuffer sb = new StringBuffer("select count(1) from search_creditDetail_view ");
		
		logger.info("Exit method getDisputeSearchNumberQueryString.");
		return sb.toString();
	}
	/**
	 * Edit Search_Dispute_Collection_TotailNumber String 
	 * @param v
	 * @param cid
	 * @return HQL String
	 */
	private String getSearchQueryString(SearchVO v,Integer cid){
		logger.info("Enter method getSearchQueryString.");
		
		StringBuffer sb = new StringBuffer("select concat('{ ");
		sb.append("id:\"',if(did is null,'',did),'\", ");
		sb.append("disputeNumber:\"',if(disputeNumber is null,'',disputeNumber),'\", ");
		sb.append("disputeBalance:\"',if(disputeBalance is null,'',disputeBalance),'\", ");
		sb.append("claimNnmber:\"',if(claimNnmber is null,'',claimNnmber),'\", ");
		sb.append("ticketNumber:\"',if(ticketNumber is null,'',ticketNumber),'\" ");
		sb.append("}')from search_creditDetail_view ");
	
		if(v.getFilter()!=null){
			sb.append(" where ");
			sb.append(v.getFilter());
		}	
		sb.append(v.getLimitCause());
		
		logger.info("Exit method getSearchQueryString.");
		return sb.toString();
	}
	
	/**
	 * Edit Search_Dispute_Balance String
	 * @param v
	 * @param creditId
	 * @return HQL String
	 */
	private String getReconcileSearchNumberQueryString(SearchVO v,Integer creditId){
		logger.info("Enter method getReconcileSearchNumberQueryString.");
		
		StringBuffer sb = new StringBuffer("select count(1) from search_creditDetailReconcile_view ");
		sb.append("where cid = " + creditId);
		if(v.getFilter()!=null){
			sb.append(" and ");
			sb.append(v.getFilter());
			
		}	
		logger.info("Exit method getReconcileSearchNumberQueryString.");
		return sb.toString();
	}
	/**
	 * Edit Search_Reconcile_Collection String
	 * @param v
	 * @param creditId
	 * @return HQL String 
	 */
	private String getSearchReconcileQueryString(SearchVO v,Integer creditId){
		logger.info("Enter method getSearchReconcileQueryString.");
		
		StringBuffer sb = new StringBuffer("select concat('{");
		sb.append("rid:\"',rid,'\", ");
		sb.append("did:\"',if(did is null,'',did),'\", ");
		sb.append("cid:\"',if(cid is null,'',cid),'\", ");
		sb.append("dnumber:\"',if(dnumber is null,'',dnumber),'\", ");
		sb.append("ramout:\"',if(ramout is null,'',ramout),'\", ");
		sb.append("rdate:\"',if(rdate is null,'',rdate),'\", ");
		sb.append("dnotes:\"',if(dnotes is null,'',dnotes),'\", ");
		sb.append("uname:\"',if(uname is null,'',uname),'\" ");
		sb.append("}')from search_creditDetailReconcile_view  ");
		sb.append("where cid = " + creditId + " ");
		if(v.getFilter()!=null){
			sb.append(" and ");
			sb.append(v.getFilter());
			
		}	
		sb.append(" ;");
		logger.info("Exit method getSearchReconcileQueryString.");
		return sb.toString();
	}
	
	/**
	 * Search Reconcile data Collection to database Reconcile_Table
	 * Return Reconcile Class toString List
	 */
	public List<String> getReconcileList(SearchVO v,Integer did) {
		logger.info("Enter method getReconcileList.");
		
		final String sql = this.getSearchReconcileQueryString(v,did);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		
		logger.info("Exit method getReconcileList.");
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getCreditForReconcileTotalAmount(com.saninco.ccm.model.Dispute)
	 */
	public Double getDisputeForReconcileTotalAmount(Integer creditId) {
		logger.info("Enter method getDisputeForReconcileTotalAmount.");
		Session session = getSession();
		try {
			String sql = "select sum(reconcile_amount) from reconcile where credit_id="+creditId+" and rec_active_flag = 'Y'";
			Query query = session.createSQLQuery(sql);
			Double totalAmount = (Double)query.uniqueResult();
			
			logger.info("Exit method getDisputeForReconcileTotalAmount.");
			return totalAmount;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * Uptate Object Class
	 */
	public void update(Object o){
		logger.info("Enter method updateDispute.");
		Session session = getSession();
		try {
			session.update(o);
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateDispute.");
	}
	/**
	 * Get Object Method
	 */
	public Object get(Class c,Integer id){
		logger.info("Enter method get.");
		Session session = getSession();
		try {
			Object o = session.get(c, id);
			
			logger.info("Exit method get.");
			return o;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * Save one Reconcile data
	 */
	public Integer save(Object o){
		logger.info("Enter method updateDispute.");
		Session session = getSession();
		try {
			Integer i = (Integer) session.save(o);
			
			logger.info("Exit method updateDispute.");
			return i;
		} finally {
			releaseSession(session);
		}
	}
	
}
