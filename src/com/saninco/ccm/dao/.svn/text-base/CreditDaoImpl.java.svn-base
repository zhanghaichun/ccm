/**
 * 
 */
package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


import com.saninco.ccm.model.Credit;
import com.saninco.ccm.vo.SearchCreditVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * Spring Hibernate DAO class for Credit based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author xinyu.Liu
 * @see pendingCredit is below --pengfei.wang
 * add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 * add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 */
/**
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class CreditDaoImpl extends HibernateDaoSupport implements ICreditDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public CreditDaoImpl() {
	}
	// property constants
	public static final String CREDIT_AMOUNT = "creditAmount";
	public static final String CREDIT_BALANCE = "creditBalance";
	public static final String REFERENCE_NUMBER = "referenceNumber";
	public static final String DISPUTE_NUMBER = "disputeNumber";
	public static final String CLAIM_NUMBER = "claimNumber";
	public static final String TICKET_NUMBER = "ticketNumber";
	public static final String NOTES = "notes";
	public static final String FLAG_WORKSPACE = "flagWorkspace";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#save(com.saninco.ccm.model.Credit)
	 */
	public void save(Credit transientInstance) {
		logger.debug("saving Credit instance");
		try {
			getHibernateTemplate().save(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#delete(com.saninco.ccm.model.Credit)
	 */
	public void delete(Credit persistentInstance) {
		logger.debug("deleting Credit instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findById(java.lang.Integer)
	 */
	public Credit findById(java.lang.Integer id) {
		logger.debug("getting Credit instance with id: " + id);
		try {
			Credit instance = (Credit) getHibernateTemplate().get(
					"com.saninco.ccm.model.Credit", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByExample(com.saninco.ccm.model.Credit)
	 */
	public List findByExample(Credit instance) {
		logger.debug("finding Credit instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			logger.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		logger.debug("finding Credit instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Credit as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByCreditAmount(java.lang.Object)
	 */
	public List findByCreditAmount(Object creditAmount) {
		return findByProperty(CREDIT_AMOUNT, creditAmount);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByCreditBalance(java.lang.Object)
	 */
	public List findByCreditBalance(Object creditBalance) {
		return findByProperty(CREDIT_BALANCE, creditBalance);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByReferenceNumber(java.lang.Object)
	 */
	public List findByReferenceNumber(Object referenceNumber) {
		return findByProperty(REFERENCE_NUMBER, referenceNumber);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByDisputeNumber(java.lang.Object)
	 */
	public List findByDisputeNumber(Object disputeNumber) {
		return findByProperty(DISPUTE_NUMBER, disputeNumber);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByClaimNumber(java.lang.Object)
	 */
	public List findByClaimNumber(Object claimNumber) {
		return findByProperty(CLAIM_NUMBER, claimNumber);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByTicketNumber(java.lang.Object)
	 */
	public List findByTicketNumber(Object ticketNumber) {
		return findByProperty(TICKET_NUMBER, ticketNumber);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByNotes(java.lang.Object)
	 */
	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByFlagWorkspace(java.lang.Object)
	 */
	public List findByFlagWorkspace(Object flagWorkspace) {
		return findByProperty(FLAG_WORKSPACE, flagWorkspace);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByCreatedBy(java.lang.Object)
	 */
	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByModifiedBy(java.lang.Object)
	 */
	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByRecActiveFlag(java.lang.Object)
	 */
	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findAll()
	 */
	public List findAll() {
		logger.debug("finding all Credit instances");
		try {
			String queryString = "from Credit";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#merge(com.saninco.ccm.model.Credit)
	 */
	public Credit merge(Credit detachedInstance) {
		logger.debug("merging Credit instance");
		try {
			Credit result = (Credit) getHibernateTemplate().merge(
					detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#attachDirty(com.saninco.ccm.model.Credit)
	 */
	public void attachDirty(Credit instance) {
		logger.debug("attaching dirty Credit instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#attachClean(com.saninco.ccm.model.Credit)
	 */
	public void attachClean(Credit instance) {
		logger.debug("attaching clean Credit instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	//modified by xinyu.Liu on 2010.514 for CCM-180
	private String getCreditSearchQueryString(SearchCreditVO sco, int userId){
		logger.info("Enter method getCreditSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',c.id,',vendor:\"',if(v.vendor_name is null,'',v.vendor_name),");
		sb.append("'\",ban:\"',if(account_number is null,'',account_number),'\",date:\"',if(credit_date is null,'',credit_date),'\",amount:\"',");
		sb.append("if(credit_amount is null,'',concat('$',format(credit_amount, 2))),'\",status:\"',if(credit_status_name is null,'',credit_status_name),'\",referenceNumber:\"',if(reference_number is null,'',reference_number),");
		sb.append("'\"}') a ");
		sb.append("from credit c, invoice i, vendor v, ban b, credit_status cs ");
		
		sb.append(voWhereConditions(sco,userId));
		
		sb.append(sco.getOrderByCause(null) + " ");
		sb.append(sco.getLimitCause() + " ");
		logger.info("Exit method getCreditSearchQueryString.");
		return sb.toString();
	}

	//modified by xinyu.Liu on 2010.514 for CCM-180
	private String getCreditSearchNumberQueryString(SearchCreditVO sio, int userId){
		logger.info("Enter method getCreditSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from credit c, invoice i, vendor v, ban b, credit_status cs ");
		sb.append("where ");
		
		if(sio.getFilter()!=null){
			sb.append(sio.getFilter());
			sb.append(" and ");
		}		
		sb.append("c.rec_active_flag=\"Y\" and c.vendor_id=v.id and c.ban_id=b.id and c.invoice_id=i.id ");
		sb.append("and c.credit_status_id=cs.id ");
		
		if(sio.getBanId()!=null){
			sb.append(" and c.vendor_id="+sio.getVendorId()+" ");
			sb.append(" and c.ban_id="+sio.getBanId()+" ");
		}else {
			if(sio.getVendorId()!=null){
				sb.append(" and c.vendor_id="+sio.getVendorId()+" ");
			}
		}
		
		if(sio.getInvoiceNumber()!=null)
			//modified by xinyu.Liu on 2010.5.11 for CCM-109
			sb.append(" and upper(i.invoice_number) like '%"+sio.getInvoiceNumber().trim()+"%' ");
		if(sio.getDisputeNumber()!=null)
			sb.append(" and upper(c.dispute_number) like '%"+sio.getDisputeNumber().trim()+"%' ");
		if(sio.getCreditLeastAmount()!=null) 
			sb.append(" and c.credit_amount>="+sio.getCreditLeastAmount().trim()+" ");
		if(sio.getCreditGreatestAmount()!=null)
			sb.append(" and c.credit_amount<="+sio.getCreditGreatestAmount().trim()+" ");
		if(sio.getCreditStatusId()!=null)
			sb.append(" and c.credit_status_id="+sio.getCreditStatusId()+" ");
		if(sio.calCreditDateStartsOn()!=null)
			sb.append(" and datediff(c.credit_date, "+sio.calCreditDateStartsOn()+")>=0 "); 
		if(sio.calCreditDateEndsOn()!=null)
			sb.append(" and datediff(c.credit_date, "+sio.calCreditDateEndsOn()+")<=0 "); 
		
		logger.info("Exit method getCreditSearchNumberQueryString.");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ICreditDao#getNumberOfCredit(com.saninco.ccm.vo.SearchCreditVO)
	 */
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getNumberOfCredit(com.saninco.ccm.vo.SearchCreditVO, int)
	 */
	public long getNumberOfCredit(SearchCreditVO sio, int userId) {
		logger.info("Enter method getNumberOfCredit.");
		final String sql = this.getCreditSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List list = session.createSQLQuery(sql).list();
		    	return new Integer(list.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfCredit.");
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getPendingNumberOfCredit(com.saninco.ccm.vo.SearchCreditVO, int)
	 */
	//CreditService.java connection here
	public long getPendingNumberOfCredit(SearchCreditVO sio,
			int userId) {
		logger.info("Enter method getPendingNumberOfCredit.");
		final String sql = this.getPendingCreditSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getPendingNumberOfCredit.");
		return count;
	}
	
	//getPendingNumberOfCredit connection here
	private String getPendingCreditSearchNumberQueryString(SearchCreditVO sio, int userId){
		logger.info("Enter method getDisputeSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");		
		
		sb.append("from pending_credit_view i");
		if(sio.getFilter()!=null){
			sb.append(" where ");
			sb.append(sio.getFilter());
		}	
		return sb.toString();
	}

	//CreditService.java connection here
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findPendingCredits(com.saninco.ccm.vo.SearchCreditVO, int)
	 */
	public List<String> findPendingCredits(SearchCreditVO sio, int userId) {
		logger.info("Enter method pendingCredit.");
		final String sql = this.pendingCreditQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method pendingCredit.");
		return c;
	}
	
	//pendingCredit connection here
	private String pendingCreditQueryString(SearchCreditVO sio, int userId){
		logger.info("Enter method pendingCreditQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',id,',vendor:\"',if(vendor_name is null,'',vendor_name),");
		sb.append("'\",ban:\"',if(account_number is null,'',account_number),'\",date:\"',if(credit_date is null,'',credit_date),'\",amount:\"',");
		sb.append("if(credit_amount is null,'',concat('$',format(credit_amount, 2))),'\",invoice_number:\"',invoice_number,'\",credit_balance:\"',credit_balance,'\",claim_number:\"',claim_number,'\",ticket_number:\"',if(ticket_number is null,'',ticket_number),");
		sb.append("'\"}') a ");
		sb.append("from pending_credit_view c ");	
		if(sio.getFilter()!=null){
			sb.append(" where ");
			sb.append(sio.getFilter());
		}	
		//modified by xinyu.Liu on 2010.5.19 for CCM-217
		if(sio.getSortField()!=null)
			sb.append("order by "+sio.getSortField()+" ");
		if(sio.getSortingDirection()!=null) 
			sb.append(sio.getSortingDirection()+" ");
		
		sb.append("limit " + sio.getStartIndex());
		sb.append(","+sio.getRecPerPage()); 
		logger.info("Exit method pendingCreditQueryString.");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#searchCredit(com.saninco.ccm.vo.SearchCreditVO, int)
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchCredit(SearchCreditVO sio, int userId) {
		logger.info("Enter method SearchCredit.");
		final String sql = this.getCreditSearchQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method SearchCredit.");
		return list;
	}
	
	public List<Object[]> searchCreditByObject(SearchCreditVO sco, int userId) {
		logger.info("Enter method searchCreditByObject.");
		final String sql = this.getCreditSearchByObjectQueryString(sco, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchCreditByObject.");
		return list;
	}
	
	public String getCreditSearchByObjectQueryString(SearchCreditVO sco, int userId) {
		logger.info("Enter method getCreditSearchByObjectQueryString.");
		StringBuffer sb = new StringBuffer("select c.id,v.vendor_name,account_number,format(credit_amount, 2),credit_date,credit_status_name,reference_number");
		sb.append(" from credit c, invoice i, vendor v, ban b, credit_status cs ");
		
		sb.append(voWhereConditions(sco,userId));
		
		sb.append(sco.getOrderByCause(null) + " ");
		sb.append(sco.getLimitCause() + " ");
		logger.info("Exit method getCreditSearchByObjectQueryString.");
		return sb.toString();
	}
	
	private String voWhereConditions(SearchCreditVO sco, int userId) {
		logger.info("Enter method voWhereConditions.");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");
		if(sco.getFilter()!=null){
			sb.append(sco.getFilter());
			sb.append(" and ");
		}	
		sb.append("c.rec_active_flag=\"Y\" and c.vendor_id=v.id and c.ban_id=b.id and c.invoice_id=i.id ");
		sb.append("and c.credit_status_id=cs.id ");
		
		if(sco.getBanId()!=null){
			sb.append(" and c.vendor_id="+sco.getVendorId()+" ");
			sb.append(" and c.ban_id="+sco.getBanId()+" ");
		}else {
			if(sco.getVendorId()!=null){
				sb.append(" and c.vendor_id="+sco.getVendorId()+" ");
			}
		}
		
		if(sco.getInvoiceNumber()!=null)
			//modified by xinyu.Liu on 2010.5.11 for CCM-109
			sb.append(" and upper(i.invoice_number) like '%"+sco.getInvoiceNumber().trim()+"%' ");
		if(sco.getDisputeNumber()!=null)
			sb.append(" and upper(c.dispute_number) like '%"+sco.getDisputeNumber().trim()+"%' ");
		if(sco.getCreditLeastAmount()!=null) 
			sb.append(" and c.credit_amount>="+sco.getCreditLeastAmount().trim()+" ");
		if(sco.getCreditGreatestAmount()!=null)
			sb.append(" and c.credit_amount<="+sco.getCreditGreatestAmount().trim()+" ");
		if(sco.getCreditStatusId()!=null)
			sb.append(" and c.credit_status_id="+sco.getCreditStatusId()+" ");
		if(sco.calCreditDateStartsOn()!=null)
			sb.append(" and datediff(c.credit_date, "+sco.calCreditDateStartsOn()+")>=0 "); 
		if(sco.calCreditDateEndsOn()!=null)
			sb.append(" and datediff(c.credit_date, "+sco.calCreditDateEndsOn()+")<=0 "); 

		logger.info("Enter method voWhereConditions.");
		return sb.toString();
	}

	private String assignmentSearchAlias = "c";
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getAssignmentCount(int)
	 */
	public long getAssignmentCount(int userId) {
		logger.info("Enter method getAssignmentCount.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.getAssignmentCountSelectCause());
		sql.append(this.getAssignmentWhereCause());
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql.toString());
			//	query.setInteger(0, userId);
			BigInteger count = (BigInteger)query.uniqueResult();
			logger.info("Exit method getAssignmentCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#searchCreditAssignment(com.saninco.ccm.vo.SearchCreditVO)
	 */
	public List<String> searchCreditAssignment(SearchCreditVO svo) {
		logger.info("Enter method searchCreditAssignment.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.getCreditAssignmentSearchSelectCause());
		sql.append(this.getAssignmentWhereCause());
		sql.append(svo.getOrderByCause(assignmentSearchAlias));
		sql.append(svo.getLimitCause());
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql.toString());
			query.setInteger(0, svo.getUserId());
			List<String> result = (List<String>)query.list();
			logger.info("Exit method searchCreditAssignment.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	private String getAssignmentCountSelectCause() {
		String selectCause = "select count(1) assignmentCount from search_credit_view " + assignmentSearchAlias;
		return selectCause;
	}
	private String getCreditAssignmentSearchSelectCause(){
		StringBuilder sb = new StringBuilder("select concat('{id:',id,',',vendor:\"',if(vendor_name is null,'',vendor_name),");
		sb.append("'\",ban:\"',if(account_number is null,'',account_number),'\",date:\"',if(credit_date is null,'',credit_date),'\",amount:\"',");
		sb.append("if(credit_amount is null,'',concat('$',format(credit_amount, 2))),'\",status:\"',if(credit_status_name is null,'',credit_status_name),'\",referenceNumber:\"',if(reference_number is null,'',reference_number),");
		sb.append("'\"}') jsonObj");
		sb.append(" from search_credit_view ");
		sb.append(assignmentSearchAlias);
		return sb.toString();
	}
	private String getAssignmentWhereCause() {
		StringBuilder sb = new StringBuilder();
		sb.append(" where " + assignmentSearchAlias + ".ban_id in");
		sb.append(" (select b.id from ban b )");
		sb.append(" and " + assignmentSearchAlias + ".flag_workspace='Y'");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#updateCreditBalanceRollback(com.saninco.ccm.model.Credit)
	 */
	public void updateCreditBalanceRollback(Credit credit) {
	
			getHibernateTemplate().merge(credit);
		
	}
	/**
	 * Get Invoice Page Credit Tab Info Totail Number [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public Integer getCreditTabTotalNO(SearchInvoiceVO svo){
		logger.info("Enter method getCreditTabTotalNO.");
		final String sql = this.getCreditTabTotalNOSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getCreditTabTotalNO.");
		return Count;
	}
	private String getCreditTabTotalNOSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select count(1) from `credit` as c ");
		this.getCreditTabWhereSqlString(svo, sb);
		//sb.append("");
		return sb.toString();
	}
	/**
	 * Get Invoice Page Credit Tab Info List [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List<String> searchICreditTab(SearchInvoiceVO svo){
		logger.info("Enter method searchICreditTab.");
		final String sql = this.searchICreditTabSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchICreditTab.");
		return list;
	}
	private String searchICreditTabSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("cid:\"',c.id,'\", ");
		sb.append("description:\"', ");
		sb.append("if(c.notes is null,'',c.notes), ");
		sb.append("if(c.reference_number is null,'',c.reference_number), ");
		sb.append("if(c.circuit_number is null,'',c.circuit_number), ");
		sb.append("if(c.dispute_number is null,'',c.dispute_number), ");
		sb.append("if(c.claim_number is null,'',c.claim_number), ");
		sb.append("if(c.dispute_category is null,'',c.dispute_category), ");
		sb.append("if(c.ticket_number is null,'',c.ticket_number), ");
		sb.append(" '\", ");
		sb.append("originalAmount:\"',if(credit_amount is null,'',format(credit_amount,2)),'\", ");
		sb.append("outstandingAmount:\"',if(credit_balance is null,'',format(credit_balance,2)),'\" ");
		sb.append("}') from `credit` as c ");
		this.getCreditTabWhereSqlString(svo, sb);
//		sb.append("");
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		
		return sb.toString();
	}
	/**
	 * DownLoad Excel [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List<Object[]> searchCreditTabByObject(SearchInvoiceVO svo){
		logger.info("Enter method searchCreditTabByObject.");
		final String sql = this.searchCreditTabByObjectSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchCreditTabByObject.");
		return list;
	}
	private String searchCreditTabByObjectSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select CONCAT( ");
		sb.append("if(c.notes is null,'',c.notes), ");
		sb.append("if(c.reference_number is null,'',c.reference_number), ");
		sb.append("if(c.circuit_number is null,'',c.circuit_number), ");
		sb.append("if(c.dispute_number is null,'',c.dispute_number), ");
		sb.append("if(c.claim_number is null,'',c.claim_number), ");
		sb.append("if(c.dispute_category is null,'',c.dispute_category), ");
		sb.append("if(c.ticket_number is null,'',c.ticket_number) ");
		sb.append(" ) description, ");
		sb.append(" credit_amount, ");
		sb.append(" credit_balance ");
		sb.append(" from `credit` as c ");
		this.getCreditTabWhereSqlString(svo, sb);
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		
		return sb.toString();
	}
	private void getCreditTabWhereSqlString(SearchInvoiceVO svo,StringBuffer sb){
		sb.append("where c.invoice_id = "+svo.getInvoiceId()+" ");
		sb.append("and c.credit_balance <>0 ");
		sb.append("and c.rec_active_flag = 'Y' ");
	}
	
	/**
	 * Get Credit Sum By Invoic Id [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public Double getCreidtSumByIid(SearchInvoiceVO svo){
		logger.info("Enter method getCreidtCountByIid.");
		final String sql = this.getCreidtSumByIidSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		Double sum = (Double) l.get(0);
		logger.info("Exit method getCreidtCountByIid.");
		return sum;
	}
	private String getCreidtSumByIidSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select sum(credit_balance) from `credit` ");
		sb.append("where rec_active_flag = 'Y' ");
		sb.append("and invoice_id = "+svo.getInvoiceId()+" ");
		return sb.toString();
	}
	/**
	 * Get Credit SCOA Id By Invoice Id [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List getCreditSCOAId(SearchInvoiceVO svo){
		logger.info("Enter method getCreditSCOAId.");
		final String sql = this.getCreditSCOAIdSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getCreditSCOAId.");
		return l;
	}
	private String getCreditSCOAIdSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select account_code_id from `credit` ");
		sb.append("where invoice_id = "+svo.getInvoiceId()+" ");
		sb.append("and credit_balance <> 0 ");
		sb.append("and account_code_id != 0 and rec_active_flag = 'Y' ");
		return sb.toString();
	} 
	
	/**
	 * Get Credit SCOA Id By Credit Id [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public String getCreditSCOAByCid(SearchInvoiceVO svo){
		logger.info("Enter method getCreditSCOAByCid.");
		final String sql = this.getCreditSCOAByCidSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getCreditSCOAByCid.");
		return l.get(0);
	}
	private String getCreditSCOAByCidSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("scoa:\"',if(account_code_id is null,'choose',account_code_id),'\" ");
		sb.append("}') from `credit` ");
		sb.append("where id = "+svo.getCreditId()+" ");
		sb.append("and rec_active_flag = 'Y' ");
		return sb.toString();
	} 
	/**
	 * Search Credit Id List Info [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List getCreditId(SearchInvoiceVO svo){
		logger.info("Enter method getCreditId.");
		String queryString = "select c.id from Credit c where c.invoice.id = "+svo.getInvoiceId()+" and c.creditBalance <> 0  and c.recActiveFlag = 'Y'";
		List l = this.getHibernateTemplate().find(queryString);
		logger.info("Exit method getCreditId.");
		return l;
	}
	public void saveObject(Object o){
		this.getHibernateTemplate().save(o);
	}
	public Object getObject(Class c, Integer id){
		return this.getHibernateTemplate().get(c, id);
	}
	public void updateObject(Object o){
		this.getHibernateTemplate().update(o);
	}
}
