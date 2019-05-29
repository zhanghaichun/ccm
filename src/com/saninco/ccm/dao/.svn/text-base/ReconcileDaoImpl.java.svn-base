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

import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.model.UserDelegation;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchReconcileVO;

/**
 * Spring Hibernate DAO class for Reconcile and Dispute based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author Wei.Su
 *
 */
public class ReconcileDaoImpl extends HibernateDaoSupport implements IReconcileDao {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	public static final String RECONCILE_AMOUNT = "reconcileAmount";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	public ReconcileDaoImpl(){}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#save(com.saninco.ccm.model.Reconcile)
	 */
	public void save(Reconcile transientInstance) {
		logger.debug("saving Reconcile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#delete(com.saninco.ccm.model.Reconcile)
	 */
	public void delete(Reconcile persistentInstance) {
		logger.debug("deleting Reconcile instance");
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
	public Reconcile findById(java.lang.Integer id) {
		logger.debug("getting Reconcile instance with id: " + id);
		try {
			Reconcile instance = (Reconcile) getHibernateTemplate().get(
					"com.saninco.ccm.model.Reconcile", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByExample(com.saninco.ccm.model.Reconcile)
	 */
	public List findByExample(Reconcile instance) {
		logger.debug("finding Reconcile instance by example");
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
		logger.debug("finding Reconcile instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Reconcile as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByReconcileAmount(java.lang.Object)
	 */
	public List findByReconcileAmount(Object reconcileAmount) {
		return findByProperty(RECONCILE_AMOUNT, reconcileAmount);
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
		logger.debug("finding all Reconcile instances");
		try {
			String queryString = "from Reconcile";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#merge(com.saninco.ccm.model.Reconcile)
	 */
	public Reconcile merge(Reconcile detachedInstance) {
		logger.debug("merging Reconcile instance");
		try {
			Reconcile result = (Reconcile) getHibernateTemplate().merge(
					detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#attachDirty(com.saninco.ccm.model.Reconcile)
	 */
	public void attachDirty(Reconcile instance) {
		logger.debug("attaching dirty Reconcile instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#attachClean(com.saninco.ccm.model.Reconcile)
	 */
	public void attachClean(Reconcile instance) {
		logger.debug("attaching clean Reconcile instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * Get the query string
	 * @param sro
	 * @param userId
	 * @return
	 */
	private String getReconcileSearchQueryString(SearchReconcileVO sro, int userId){
		logger.info("Enter method getReconcileSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',id,',creditid:',credit_id,',disputeid:',dispute_id,',dispute:\"',if(dispute_number is null,'',dispute_number),'\",vendor:\"',vendor_name,'\",analyst:\"',user_name,");
		sb.append("'\",ban:\"',account_number,'\",date:\"',if(reconcile_date is null,'',reconcile_date),'\",amount:\"',");
		sb.append("if(reconcile_amount is null,'',concat('$',format(reconcile_amount, 2))),'\",ticketNumber:\"',if(ticket_number is null,'',ticket_number),'\",claimNumber:\"',if(claim_number is null,'',claim_number),'\",invoiceNumber:\"',if(invoice_number is null,'',invoice_number),");
		sb.append("'\"}') a ");
		sb.append("from search_reconcile_view r ");
		sb.append("where ");
		if(sro.getFilter()!=null){
			sb.append(sro.getFilter());
			sb.append(" and ");
		}	
		if(sro.getVendorId()!=null) {
			sb.append("r.vendor_id="+sro.getVendorId()+" ");
			if(sro.getBanId()!=null)
				sb.append(" and r.ban_id="+sro.getBanId()+" ");
			else{
				sb.append(" and r.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		else{
			if(sro.getBanId()!=null)
				sb.append(" r.ban_id="+sro.getBanId()+" ");
			else{
				sb.append(" r.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		if(sro.getInvoiceNumber()!=null)
			sb.append(" and upper(r.invoice_number) like '%"+sro.getInvoiceNumber()+"%' ");
			//sb.append(" and upper(c.invoice_number) like '"+sro.getInvoiceNumber().replace('*', '%').toUpperCase()+"' ");
		if(sro.getDisputeNumber()!=null)
			sb.append(" and upper(r.dispute_number) like '%"+sro.getDisputeNumber()+"%' ");
			//sb.append(" and upper(c.dispute_number) like '"+sro.getDisputeNumber().replace('*', '%').toUpperCase()+"' ");
		if(sro.getCreditNumber()!=null)
			sb.append(" and upper(r.credit_number) like '%"+sro.getCreditNumber()+"%' ");
		//sb.append(" and upper(c.credit_number) like '"+sro.getCreditNumber().replace('*', '%').toUpperCase()+"' ");
		if(sro.getClaimNumber()!=null)
			sb.append(" and upper(r.claim_number) like '%"+sro.getClaimNumber()+"%' ");
		if(sro.getTicketNumber()!=null)
			sb.append(" and upper(r.ticket_number) like '%"+sro.getTicketNumber()+"%' ");
		
		if(sro.getLeastAmount()!=null) 
			sb.append(" and r.reconcile_amount>="+sro.getLeastAmount()+" ");
		if(sro.getGreatestAmount()!=null)
			sb.append(" and r.reconcile_amount<="+sro.getGreatestAmount()+" ");
		if(sro.getAnalystId()!=null) 
			sb.append(" and r.created_by="+sro.getAnalystId()+" ");
		if(sro.calReconcileDueStartsOn()!=null)
			sb.append(" and datediff(r.reconcile_date, "+sro.calReconcileDueStartsOn()+")>=0 "); 
		if(sro.calReconcileDueEndsOn()!=null)
			sb.append(" and datediff(r.reconcile_date, "+sro.calReconcileDueEndsOn()+")<=0 "); 
		
		if(sro.getSortField()!=null)
			sb.append("order by "+sro.getSortField()+" ");
		if(sro.getSortingDirection()!=null) 
			sb.append(sro.getSortingDirection()+" ");
		sb.append("limit " + sro.getStartIndex());
		sb.append(","+sro.getRecPerPage()); 
		logger.info("Exit method getReconcileSearchNumberQueryString.");
		return sb.toString();
	}
	
	/**
	 * Get used to check the number of records to the query string
	 * @param sro
	 * @param userId
	 * @return
	 */
	private String getReconcileSearchNumberQueryString(SearchReconcileVO sro, int userId){
		logger.info("Enter method getCreditSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");//select count(1) a 
		sb.append("from search_reconcile_view r ");
		sb.append("where ");
		if(sro.getFilter()!=null){
			sb.append(sro.getFilter());
			sb.append(" and ");
		}	
		if(sro.getVendorId()!=null) {
			sb.append("r.vendor_id="+sro.getVendorId()+" ");
			if(sro.getBanId()!=null)
				sb.append(" and r.ban_id="+sro.getBanId()+" ");
			else{
				sb.append(" and r.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		else{
			if(sro.getBanId()!=null)
				sb.append(" r.ban_id="+sro.getBanId()+" ");
			else{
				sb.append(" r.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		if(sro.getInvoiceNumber()!=null)
			sb.append(" and upper(r.invoice_number) like '%"+sro.getInvoiceNumber()+"%' ");
			//sb.append(" and upper(c.invoice_number) like '"+sro.getInvoiceNumber().replace('*', '%').toUpperCase()+"' ");
		if(sro.getDisputeNumber()!=null)
			sb.append(" and upper(r.dispute_number) like '%"+sro.getDisputeNumber()+"%' ");
			//sb.append(" and upper(c.dispute_number) like '"+sro.getDisputeNumber().replace('*', '%').toUpperCase()+"' ");
		//sb.append(" and upper(c.credit_number) like '"+sro.getCreditNumber().replace('*', '%').toUpperCase()+"' ");
		if(sro.getClaimNumber()!=null)
			sb.append(" and upper(r.claim_number) like '%"+sro.getClaimNumber()+"%' ");
		if(sro.getTicketNumber()!=null)
			sb.append(" and upper(r.ticket_number) like '%"+sro.getTicketNumber()+"%' ");
		
		if(sro.getLeastAmount()!=null) 
			sb.append(" and r.reconcile_amount>="+sro.getLeastAmount()+" ");
		if(sro.getGreatestAmount()!=null)
			sb.append(" and r.reconcile_amount<="+sro.getGreatestAmount()+" ");
		if(sro.getAnalystId()!=null) 
			sb.append(" and r.created_by="+sro.getAnalystId()+" ");
		if(sro.calReconcileDueStartsOn()!=null)
			sb.append(" and datediff(r.reconcile_date, "+sro.calReconcileDueStartsOn()+")>=0 "); 
		if(sro.calReconcileDueEndsOn()!=null)
			sb.append(" and datediff(r.reconcile_date, "+sro.calReconcileDueEndsOn()+")<=0 "); 
		logger.info("Exit method getReconcileSearchNumberQueryString.");
		
		
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getAssignmentCount(java.lang.String)
	 */
	public long getAssignmentCount(String banStr) {
		logger.info("Enter method getAssignmentCount.");
		String sql = this.getAssignmentCountQueryString(banStr);
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql);
			BigInteger count = (BigInteger)query.uniqueResult();
			logger.info("Exit method getAssignmentCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getNumberOfReconcile(com.saninco.ccm.vo.SearchReconcileVO, int)
	 * Get total number
	 */
	public long getNumberOfReconcile(SearchReconcileVO searchReconcileVO,
			int userId) {
		logger.info("Enter method getNumberOfReconcile.");
		final String sql = this.getReconcileSearchNumberQueryString(searchReconcileVO, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List list = session.createSQLQuery(sql).list();
		    	return new Integer(list.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfReconcile.");
		return count;
	}
	
	/**
	 * Get Invoice Page Reconciliation Tab Info Totail Number [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public Integer getIReconciliationTabTotalNO(SearchInvoiceVO svo){
		logger.info("Enter method getIReconciliationTabTotalNO.");
		final String sql = this.getIReconciliationTabTotalNOString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getIReconciliationTabTotalNO.");
		return Count;
	}
	private String getIReconciliationTabTotalNOString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select count(1) ");

		sb.append("from ((((`reconcile` as r left join `credit` as c on ( r.credit_id = c.id )) left join `dispute` as d on(r.dispute_id = d.id  )) left join `reconcile_status` as rs on (r.reconcile_status_id = rs.id )) left join `user` as u on ( r.created_by = u.id )) ");
		sb.append(this.iReconciliationTabWhere(svo));
		return sb.toString();
	}
	/**
	 * Get Invoice Page Reconciliation Tab Info List [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List<String> searchIReconciliationTab(SearchInvoiceVO svo){
		logger.info("Enter method searchIReconciliationTab.");
		final String sql = this.searchIReconciliationTabString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchIReconciliationTab.");
		return list;
	}
	private String searchIReconciliationTabString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		
		sb.append("rid:\"',r.id,'\", ");
		sb.append("cid:\"',c.id,'\",  ");
		sb.append("did:\"',if(d.id is null,'',d.id),'\",      ");
		sb.append("disputeClaimNumber:\"',if(d.id is null,'',d.id),'\", ");
		sb.append("reconcileDate:\"',if(r.reconcile_date is null,'',r.reconcile_date),'\", ");
		sb.append("reconcileAmount:\"',if(r.reconcile_amount is null,'', format(r.reconcile_amount, 2)),'\", ");
		sb.append("recondileStatus:\"',if(rs.reconcile_status_name is null,'',rs.reconcile_status_name),'\", ");
		//sb.append("recondileStatus:\"',if(rs.id is null,'',rs.id),'\", ");
		sb.append("createdBy:\"',if(u.user_name is null,'',u.user_name),'\", ");
		sb.append("files:\"',if(r.attachment_point_id is null,'',r.attachment_point_id),'\", ");
		sb.append("notes:\"',if(r.notes is null,'',r.notes),'\" ");
		sb.append("}') from ((((`reconcile` as r left join `credit` as c on ( r.credit_id = c.id )) left join `dispute` as d on(r.dispute_id = d.id  )) left join `reconcile_status` as rs on (r.reconcile_status_id = rs.id )) left join `user` as u on ( r.created_by = u.id )) ");
		sb.append(this.iReconciliationTabWhere(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		return sb.toString();
	}
	public List searchIReconciliationTabObject(SearchInvoiceVO svo){
		logger.info("Enter method searchIReconciliationTabObject.");
		final String sql = this.searchIReconciliationTabObjectString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchIReconciliationTabObject.");
		return list;
	}
	private String searchIReconciliationTabObjectString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" d.id , ");
		sb.append(" r.reconcile_date , ");
		sb.append(" r.reconcile_amount , ");
		sb.append(" rs.reconcile_status_name , ");
		sb.append(" u.user_name , ");
		sb.append(" r.notes ");
		sb.append(" from ((((`reconcile` as r left join `credit` as c on ( r.credit_id = c.id )) left join `dispute` as d on(r.dispute_id = d.id  )) left join `reconcile_status` as rs on (r.reconcile_status_id = rs.id )) left join `user` as u on ( r.created_by = u.id )) ");
		sb.append(this.iReconciliationTabWhere(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		return sb.toString();
	}
	private String iReconciliationTabWhere(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer();
		sb.append("where c.invoice_id = "+svo.getInvoiceId()+" ");
		sb.append("and c.credit_balance >= 0 ");
		sb.append("and r.rec_active_flag = 'Y' ");
		return sb.toString();
	}
	
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#searchReconcile(com.saninco.ccm.vo.SearchReconcileVO, int)
	 * Query qualified record
	 */
	public List<String> searchReconcile(SearchReconcileVO searchReconcileVO,
			int userId) {
		logger.info("Enter method SearchReconcile.");
		final String sql = this.getReconcileSearchQueryString(searchReconcileVO, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method SearchReconcile.");
		return list;
	}
	
	
	private String getAssignmentCountQueryString(String banStr) {
		logger.info("Enter method getAssignmentCountQueryString.");
		StringBuilder sb = new StringBuilder("select count(1) assignmentCount from reconcile r");
		sb.append(" where r.ban_id in (" + banStr + ")");
		sb.append(" and r.rec_active_flag='Y'");
		sb.append(" and r.flag_workspace='Y'");
		logger.info("Exit method getAssignmentCountQueryString.");
		return sb.toString();
	}
	
	
	
}
