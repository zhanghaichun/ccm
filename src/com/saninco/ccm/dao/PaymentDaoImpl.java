/**
 * 
 */
package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

//import com.saninco.ccm.action.payment.PaymentAction;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceChannel;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.model.PaymentDAO;
import com.saninco.ccm.model.PaymentMethod;
import com.saninco.ccm.model.PaymentStatus;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchPaymentVO;
import com.saninco.ccm.vo.ViewPaymentDetailVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 */
public class PaymentDaoImpl extends HibernateDaoSupport implements IPaymentDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final Log log = LogFactory.getLog(PaymentDAO.class);
	
	private String assignmentSearchAlias = "p";
	
	public PaymentDaoImpl() {
	}
	
	/**
	 * get Payment Search
	 * @return
	 */
	private String getPaymentSearchQueryString(SearchPaymentVO sio, int userId){
		logger.info("Enter method getPaymentSearchQueryString.");
		
		/* Example of PaymentSearchQueryString (Mock-up)
		"{id:2,no:\"INV3414859\"," + "due:\"2009/07/18\"," + "vendor:\"Bell Aliant\"," +
		"ban:\"30092944\"," + "status:\"Canceled\"," + "amount:\"$1,369.00\"," +
		"pdate:\"2009/07/18\"," + "pidate:\"2009/07/18\"," + "ref:\"No\"," + "tx:\"20091116015601261\"}*/
		
		/*Modified by Qiao.Yang. Added pid and iid to the payment search query string*/ 
		
		StringBuffer sb = new StringBuffer("select concat('{id:',id,',iid:',iid,',no:\"',if(invoice_number is null,'',invoice_number),'\", due:\"',if(invoice_due_date is null,'',invoice_due_date),'\", vendor:\"',vendor_name,");
		sb.append("'\",ban:\"',account_number,'\",status:\"',payment_status_name,'\",amount:\"',");
		sb.append("if(payment_amount is null,'',concat('$',format(payment_amount, 2))),'\", pdate:\"',if(payment_date is null,'',payment_date),'\", pidate:\"',if(paid_date is null,'',paid_date),'\",ref:\"',if(payment_reference_code is null,'',payment_reference_code),'\", tx:\"',if(payment_transaction_number is null,'',payment_transaction_number),'\"}') a ");
		sb.append("from search_payment_view i ");
		sb.append("where ");
		if(sio.getFilter()!=null){
			sb.append(sio.getFilter());
			sb.append(" and ");
		}	
		if(sio.getVendorId()!=null) {
			sb.append("i.vendor_id="+sio.getVendorId()+" ");
			if(sio.getBanId()!=null)
				sb.append(" and i.ban_id="+sio.getBanId()+" ");
			else{
				sb.append(" and i.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		else{
			if(sio.getBanId()!=null)
				sb.append(" i.ban_id="+sio.getBanId()+" ");
			else{
				sb.append(" i.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		if(sio.getLeastAmount()!=null) 
			sb.append(" and i.payment_amount>="+sio.getLeastAmount()+" ");
		if(sio.getGreatestAmount()!=null)
			sb.append(" and i.payment_amount<="+sio.getGreatestAmount()+" ");
		if(sio.getCurrencyId()!=null)
			sb.append(" and i.currency_id="+sio.getCurrencyId()+" ");
		if(sio.getPaymentTransactionNumber()!=null)
			sb.append(" and upper(i.payment_transaction_number) like '"+sio.getPaymentTransactionNumber().replace('*', '%').toUpperCase()+"' ");
		if(sio.getReferenceNumber()!=null)
			sb.append(" and upper(i.payment_reference_code) like '"+sio.getReferenceNumber().replace('*', '%').toUpperCase()+"' ");
		if(sio.getStatusId()!=null)
			sb.append(" and i.payment_status_id="+sio.getStatusId()+" ");
		if(sio.getPaymentApproverId()!=null)
			sb.append(" and i.created_by="+sio.getPaymentApproverId()+" ");
		if(sio.calPaidDateStartsOn()!=null)
			sb.append(" and datediff(i.paid_date, "+sio.calPaidDateStartsOn()+")>=0 "); 
		if(sio.calPaidDateEndsOn()!=null)
			sb.append(" and datediff(i.paid_date, "+sio.calPaidDateEndsOn()+")<=0 "); 
		if(sio.calPaymentDateStartsOn()!=null)
			sb.append(" and datediff(i.payment_date, "+sio.calPaymentDateStartsOn()+")>=0 "); 
		if(sio.calPaymentDateEndsOn()!=null)
			sb.append(" and datediff(i.payment_date, "+sio.calPaymentDateEndsOn()+")<=0 "); 
		if(sio.getSortField()!=null)
			sb.append("order by "+sio.getSortField()+" ");
		if(sio.getSortingDirection()!=null) 
			sb.append(sio.getSortingDirection()+" ");
		sb.append("limit " + sio.getStartIndex());
		sb.append(","+sio.getRecPerPage()); 
		logger.info("Exit method getPaymentSearchQueryString.");
		return sb.toString();
	}

	private String getPaymentSearchNumberQueryString(SearchPaymentVO sio, int userId){
		logger.info("Enter method getPaymentSearchNumberQueryString.");
		
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from search_payment_view i ");
		sb.append("where ");
		if(sio.getFilter()!=null){
			sb.append(sio.getFilter());
			sb.append(" and ");
		}	
		if(sio.getVendorId()!=null) {
			sb.append("i.vendor_id="+sio.getVendorId()+" ");
			if(sio.getBanId()!=null)
				sb.append(" and i.ban_id="+sio.getBanId()+" ");
			else{
				sb.append(" and i.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		else{
			if(sio.getBanId()!=null)
				sb.append(" i.ban_id="+sio.getBanId()+" ");
			else{
				sb.append(" i.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		if(sio.getLeastAmount()!=null) 
			sb.append(" and i.payment_amount>="+sio.getLeastAmount()+" ");
		if(sio.getGreatestAmount()!=null)
			sb.append(" and i.payment_amount<="+sio.getGreatestAmount()+" ");
		if(sio.getCurrencyId()!=null)
			sb.append(" and i.currency_id="+sio.getCurrencyId()+" ");
		if(sio.getPaymentTransactionNumber()!=null)
			sb.append(" and upper(i.payment_transaction_number) like '"+sio.getPaymentTransactionNumber().replace('*', '%').toUpperCase()+"' ");
		if(sio.getReferenceNumber()!=null)
			sb.append(" and upper(i.payment_reference_code) like '"+sio.getReferenceNumber().replace('*', '%').toUpperCase()+"' ");
		if(sio.getStatusId()!=null)
			sb.append(" and i.payment_status_id="+sio.getStatusId()+" ");
		if(sio.getPaymentApproverId()!=null)
			sb.append(" and i.created_by="+sio.getPaymentApproverId()+" ");
		if(sio.calPaidDateStartsOn()!=null)
			sb.append(" and datediff(i.paid_date, "+sio.calPaidDateStartsOn()+")>=0 "); 
		if(sio.calPaidDateEndsOn()!=null)
			sb.append(" and datediff(i.paid_date, "+sio.calPaidDateEndsOn()+")<=0 "); 
		if(sio.calPaymentDateStartsOn()!=null)
			sb.append(" and datediff(i.payment_date, "+sio.calPaymentDateStartsOn()+")>=0 "); 
		if(sio.calPaymentDateEndsOn()!=null)
			sb.append(" and datediff(i.payment_date, "+sio.calPaymentDateEndsOn()+")<=0 "); 
		logger.info("Exit method getPaymentSearchNumberQueryString.");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IPaymentDao#getNumberOfPayments(com.saninco.ccm.vo.SearchPaymentVO)
	 */
	public long getNumberOfPayments(SearchPaymentVO sio, int userId) {
		logger.info("Enter method getNumberOfPayments.");
		final String sql = this.getPaymentSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfPayments.");
		return count;
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IPaymentDao#searchPayment(com.saninco.ccm.vo.SearchPaymentVO)
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchPayment(SearchPaymentVO sio, int userId) {
		logger.info("Enter method searchPayment.");
		final String sql = this.getPaymentSearchQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchPayment.");
		return l;
	}
	
	/**
	 * Payment Detail
	 */
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IPaymentDao#getNumberOfPaymentDetails(com.saninco.ccm.vo.ViewPaymentDetailVO)
	 */
	public long getNumberOfPaymentDetails(ViewPaymentDetailVO vpd) {
		logger.info("Enter method getNumberOfPaymentDetails.");
		final String sql = this.getPaymentDetailsSearchNumberQueryString(vpd);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfPaymentDetails.");
		return count;
	}
	
	private String getPaymentDetailsSearchNumberQueryString(ViewPaymentDetailVO vpd){
		logger.info("Enter method getPaymentDetailsSearchNumberQueryString.");
		
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from payment_detail p , account_code a ");
		sb.append("where ");
		if(vpd.getFilter()!=null){
			sb.append(vpd.getFilter());
			sb.append(" and ");
		}	
		sb.append("p.account_code_id = a.id and p.rec_active_flag=\"Y\" ");
		String paymentId = vpd.getPaymentId();
		sb.append(" and p.payment_id="+Integer.parseInt(paymentId)+" ");

		logger.info("Exit method getPaymentDetailsSearchNumberQueryString.");
		return sb.toString();
	}
	
	/**
	 * get Number Payment Details 
	 */
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IPaymentDao#searchPaymentDetails(com.saninco.ccm.vo.ViewPaymentDetailVO)
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchPaymentDetails(ViewPaymentDetailVO vpd) {
		logger.info("Enter method searchPaymentDetails.");
		final String sql = this.getPaymentDetailsSearchQueryString(vpd);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchPaymentDetails.");
		return l;
	}
	
	private String getPaymentDetailsSearchQueryString(ViewPaymentDetailVO vpd){
		logger.info("Enter method getPaymentDetailsSearchQueryString.");
		
		/* Example of PaymentDetailsSearchQueryString (Mock-up)
		"{id:2,no:\"1\"," + "description:\"hdt345erye\"," +
		"accountCode:\"gsss"," +" amount:\"$1,369.00\"}*/
		
		StringBuffer sb = new StringBuffer("select distinct concat('{id:',p.id,',no:\"',if(p.payment_id is null,'',p.payment_id),'\", description:\"',if(description is null,'',description),");
		sb.append("'\",accountCode:\"',account_code_name,'\",amount:\"',if(amount is null,'',amount),'\"}') a ");
		sb.append("from payment_detail p , account_code a ");
		sb.append("where ");
		if(vpd.getFilter()!=null){
			sb.append(vpd.getFilter());
			sb.append(" and ");
		}	
		sb.append("p.account_code_id = a.id and p.rec_active_flag=\"Y\" ");
		String paymentId = vpd.getPaymentId();
		sb.append(" and p.payment_id="+Integer.parseInt(paymentId)+" ");
		
		if(vpd.getSortField()!=null)
			sb.append("order by "+vpd.getSortField()+" ");
		if(vpd.getSortingDirection()!=null) 
			sb.append(vpd.getSortingDirection()+" ");
		sb.append("limit " + vpd.getStartIndex());
		sb.append(","+vpd.getRecPerPage()); 
		logger.info("Exit method getPaymentDetailsSearchQueryString.");
		return sb.toString();
	}

	/**
	 * get Number Payment History 
	 */
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IPaymentDao#getNumberOfPaymentHistory(com.saninco.ccm.vo.ViewPaymentDetailVO)
	 */
//	public long getNumberOfPaymentHistory(ViewPaymentDetailVO vpd) {
//		logger.info("Enter method getNumberOfPaymentHistory.");
//		final String sql = this.getPaymentHistorySearchNumberQueryString(vpd);
//		HibernateTemplate template = this.getHibernateTemplate();
//		Integer count = (Integer)template.execute(new HibernateCallback() {
//		    @SuppressWarnings("unchecked")
//			public Object doInHibernate(Session session) throws HibernateException, SQLException {
//		    	List l = session.createSQLQuery(sql).list();
//		    	return new Integer(l.get(0).toString());
//		    }
//		});
//		logger.info("Exit method getNumberOfPaymentHistory.");
//		return count;
//	}
//	
//	private String getPaymentHistorySearchNumberQueryString(ViewPaymentDetailVO vpd){
//		logger.info("Enter method getPaymentHistorySearchNumberQueryString.");
//		
//		StringBuffer sb = new StringBuffer("select count(1) a ");
//		sb.append("from payment_history ph , user u , payment_action pa , payment_status ps1 , payment_status ps2 ");
//		sb.append("where ");
//		if(vpd.getFilter()!=null){
//			sb.append(vpd.getFilter());
//			sb.append(" and ");
//		}	
//		sb.append("ph.created_by = u.id and ph.payment_action_id = pa.id and ph.previous_status_id = ps1.id and ph.new_status_id = ps2.id and ph.rec_active_flag=\"Y\" ");
//		
//		String paymentId = vpd.getPaymentId();
//		sb.append(" and ph.payment_id="+Integer.parseInt(paymentId)+" ");
//
//		logger.info("Exit method getPaymentHistorySearchNumberQueryString.");
//		return sb.toString();
//	}
//	
//	/**
//	 * get Payment History Detailed
//	 */
//	/* (non-Javadoc)
//	 * @see com.saninco.ccm.dao.IPaymentDao#searchPaymentHistory(com.saninco.ccm.vo.ViewPaymentDetailVO)
//	 */
//	@SuppressWarnings("unchecked")
//	public List<String> searchPaymentHistory(ViewPaymentDetailVO vpd) {
//		logger.info("Enter method searchPaymentHistory.");
//		final String sql = this.getPaymentHistorySearchQueryString(vpd);
//		HibernateTemplate template = this.getHibernateTemplate();
//		List<String> l = (List<String>)template.execute(new HibernateCallback() {
//		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
//		    	return session.createSQLQuery(sql).list();
//		    }
//		});
//		logger.info("Exit method searchPaymentHistory.");
//		return l;
//	}
//	
//	private String getPaymentHistorySearchQueryString(ViewPaymentDetailVO vpd){
//		logger.info("Enter method getPaymentHistorySearchQueryString.");
//		/* Example of PaymentHistorySearchQueryString (Mock-up)
//		"{id:2,no:\"1\"," + "user:\"ddd\"," + "action:\"gsass"," +
//		"previousStatus:\"gsass"," + "newStatus:\"gsass"," + "notes:\"gsass"," + "time:\"2010.01.21\"}*/
//		
//		StringBuffer sb = new StringBuffer("select concat('{id:',ph.id,',no:\"',if(payment_id is null,'',payment_id),'\", user:\"',if(user_name is null,'',user_name),");
//		sb.append("'\",action:\"',if(payment_action_name is null,'',payment_action_name),'\",previousStatus:\"',if(ps1.payment_status_name is null,'',ps1.payment_status_name),");
//		sb.append("'\",notes:\"',if(notes is null,'',notes),'\",time:\"',ph.created_timestamp,'\",newStatus:\"',if(ps2.payment_status_name is null,'',ps2.payment_status_name),'\"}') a ");
//		sb.append("from payment_history ph , user u , payment_action pa , payment_status ps1 , payment_status ps2 ");
//		sb.append("where ");
//		if(vpd.getFilter()!=null){
//			sb.append(vpd.getFilter());
//			sb.append(" and ");
//		}	
//		sb.append("ph.created_by = u.id and ph.payment_action_id = pa.id and ph.previous_status_id = ps1.id and ph.new_status_id = ps2.id and ph.rec_active_flag=\"Y\" ");
//		
//		String paymentId = vpd.getPaymentId();
//		sb.append(" and ph.payment_id="+Integer.parseInt(paymentId)+" ");
//		if(vpd.getSortField()!=null)
//			sb.append("order by "+vpd.getSortFieldThree()+" ");
//		if(vpd.getSortingDirection()!=null) 
//			sb.append(vpd.getSortingDirectionThree()+" ");
//		sb.append("limit " + vpd.getStartIndexThree());
//		sb.append(","+vpd.getRecPerPageThree()); 
//		logger.info("Exit method getPaymentHistorySearchQueryString.");
//		return sb.toString();
//	}
	
	/**
	 * Action be eligible 
	 * Drop-down box to get content from payment_operation table: 
	 * According to the current user has all the role, together with the Payment Transaction line previous_status_id 
	 * the current status, and access to allow all the action. Note Do not return repeated SQL action.
	 */
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IPaymentDao#getPaymentAction(String paymentId)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPaymentAction(String paymentId) {
		logger.info("Enter method getPaymentAction.");
		final String sql = this.getPaymentActionQueryString(paymentId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getPaymentAction.");
		return l;
	}
	
	private String getPaymentActionQueryString(String paymentId){
		logger.info("Enter method getPaymentActionQueryString.");
		/* Example of getPaymentActionQueryString (Mock-up)
		"{id:2,action:\"22\"," +"status:\"11\"}*/
		StringBuffer sb = new StringBuffer("select distinct concat('{id:',pa.id,',action:\"',pa.payment_action_name,'\",status:\"',po.new_status_id,'\"}') a ");
		sb.append("from user_role u, payment p, payment_operation po, payment_action pa ");
		sb.append("where po.role_id = u.role_id and po.payment_action_id = pa.id and po.previous_status_id = p.payment_status_id and p.rec_active_flag=\"Y\" ");
		sb.append("and p.id="+Integer.parseInt(paymentId)+" ");
		sb.append("and u.user_id="+SystemUtil.getCurrentUserId()+" ");
		
		logger.info("Exit method getPaymentActionQueryString.");
		return sb.toString();
	}
	
	/**
	 * find Payment History By id
	 */
//	public PaymentHistory findPaymentHistoryById(Integer id) {
//		log.debug("getting PaymentHistory instance with id: " + id);
//		try {
//			PaymentHistory instance = (PaymentHistory) getHibernateTemplate()
//					.get("com.saninco.ccm.model.PaymentHistory", id);
//			return instance;
//		} catch (RuntimeException re) {
//			log.error("get failed", re);
//			throw re;
//		}
//	}
	
	/**
	 * save Payment History
	 */
//	public void savePaymentHistory(PaymentHistory transientInstance) {
//		log.debug("saving PaymentHistory instance");
//		try {
//			getHibernateTemplate().save(transientInstance);
//			log.debug("save successful");
//		} catch (RuntimeException re) {
//			log.error("save failed", re);
//			throw re;
//		}
//	}
	
	/**
	 * find Payment Action By id
	 */
/*	public PaymentAction findPaymentActionById(Integer id) {
		log.debug("getting PaymentAction instance with id: " + id);
		try {
			PaymentAction instance = (PaymentAction) getHibernateTemplate()
					.get("com.saninco.ccm.model.PaymentAction", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}*/
	
	/**
	 * find Payment Status By id
	 */
	public PaymentStatus findPaymentStatusById(Integer id) {
		log.debug("getting PaymentStatus instance with id: " + id);
		try {
			PaymentStatus instance = (PaymentStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.PaymentStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * find Payment By id
	 */
	public Payment findById(Integer id) {
		log.debug("getting Payment instance with id: " + id);
		try {
			Payment instance = (Payment) getHibernateTemplate().get(
					"com.saninco.ccm.model.Payment", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	//jian.Dong
	public List<String> searchPaymentAssignment(SearchPaymentVO svo) {
		logger.info("Enter method searchPaymentAssignment.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.getAssignmentSearchSelectCause());
		sql.append(this.getAssignmentWhereCause());
		sql.append(svo.getOrderByCause(assignmentSearchAlias));
		sql.append(svo.getLimitCause());
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql.toString());
			query.setInteger(0, svo.getUserId());
			List<String> result = (List<String>)query.list();
			logger.info("Exit method searchPaymentAssignment.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	
	//jian.Dong
	private String getAssignmentSearchSelectCause(){
		StringBuilder sb = new StringBuilder("select concat('{id:',id,',iid:',iid,',no:\"',if(invoice_number is null,'',invoice_number),'\", due:\"',if(invoice_due_date is null,'',invoice_due_date),'\", vendor:\"',vendor_name,");
		sb.append("'\",ban:\"',account_number,'\",status:\"',payment_status_name,'\",amount:\"',");
		sb.append("if(payment_amount is null,'',concat('$',format(payment_amount, 2))),'\", pdate:\"',if(payment_date is null,'',payment_date),'\", pidate:\"',if(paid_date is null,'',paid_date),'\",ref:\"',if(payment_reference_code is null,'',payment_reference_code),'\", tx:\"',if(payment_transaction_number is null,'',payment_transaction_number),'\"}') jsonObj ");
		sb.append("from search_payment_view ");
		sb.append(assignmentSearchAlias);
		return sb.toString();
	} 
	
	public long getAssignmentCount(int userId) {
		logger.info("Enter method getAssignmentCount.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.getAssignmentCountSelectCause());
		sql.append(this.getAssignmentWhereCause());
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql.toString());
//		query.setInteger(0, userId);
			BigInteger count = (BigInteger)query.uniqueResult();
			logger.info("Exit method getAssignmentCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

	private String getAssignmentCountSelectCause() {
		return "select count(1) assignmentCount from search_payment_view " + assignmentSearchAlias;
	}
	
	private String getAssignmentWhereCause() {
		StringBuilder sb = new StringBuilder();
		sb.append(" where " + assignmentSearchAlias + ".ban_id in");
		sb.append(" (select b.id from ban b ) ");
		sb.append(" and " + assignmentSearchAlias + ".flag_workspace='Y'");
		return sb.toString();
	}

	public long getPaymentWorkCount(WorkspaceVO wvo) {
		logger.info("Enter method getInvoiceWorkCount.");
		final String sql = getPaymentWorkCountNOString(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getInvoiceWorkCount.");
		return count;
	}
	/**
	 * @author Chao.Liu
	 * @param name
	 * @return
	 */
	public List getPaymentByName(String name){
		logger.info("Enter method getPaymentByName.");
		final String sql = this.getPaymentByNameSqlString(name);
		Session session = getSession();
		try {
			List list = session.createSQLQuery(sql).list();
			logger.info("Exit method getPaymentByName.");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	private String getPaymentByNameSqlString(String name){
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("pid:\"',p.id,'\", ");
		sb.append("iid:\"',if(i.id is null,'',i.id),'\", ");
		sb.append("vid:\"',if(v.id is null,'',v.id),'\", ");
		sb.append("bid:\"',if(b.id is null,'',b.id),'\", ");
		sb.append("vendorName:\"',if(v.vendor_name is null,'',v.vendor_name),'\", ");
		sb.append("ban:\"',if(b.account_number is null,'',b.account_number),'\", ");
		sb.append("paymentTransactionNumber:\"',if(p.payment_transaction_number is null,'',p.payment_transaction_number),'\", ");
		sb.append("paymentDate:\"',if(p.payment_date is null,'',p.payment_date),'\", ");
		sb.append("paymentAmount:\"',if(p.payment_amount is null,'',format(p.payment_amount,2)),'\", ");
		sb.append("paymentStatus:\"',if(ps.payment_status_name is null,'',ps.payment_status_name),'\", ");
		sb.append("paidDate:\"',if(p.paid_date is null,'',p.paid_date),'\", ");
		sb.append("paymentReferenceCode:\"',if(p.payment_reference_code is null,'',p.payment_reference_code),'\", ");
		sb.append("description:\"',if(p.description is null,'',p.description),'\", ");
		sb.append("attachmentPoint:\"',if(p.attachment_point_id is null,'',p.attachment_point_id),'\" ");
		sb.append("}') ");
		sb.append(" from payment p left join invoice i on (p.invoice_id = i.id and i.rec_active_flag = 'Y') ");
		sb.append(" left join vendor v on (v.id = i.vendor_id and v.rec_active_flag = 'Y') ");
		sb.append(" left join ban b on (b.id = i.ban_id and b.rec_active_flag = 'Y') ");
		sb.append(" left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append(" where p.rec_active_flag = 'Y' ");
		sb.append(" and p.payment_transaction_number = '"+name+"' ");
//		sb.append(" and i.vendor_id is not null ");
//		sb.append(" and i.ban_id is not null ");
		return sb.toString();
	}

	/**
	 * @Author Chao.liu Sep 25, 2010
	 * @return
	 */
	public List<PaymentMethod> getPaymentMethod(){
		logger.info("Enter method getPaymentMethod.");
		List<PaymentMethod> l = (List<PaymentMethod>)this.getHibernateTemplate().find("from PaymentMethod ");
		logger.info("Exit method getPaymentMethod.");
		return l;
	}

	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 */
	public long getPaymentFileTotalNO(SearchPaymentVO svo){
		logger.info("Enter method getPaymentFileTotalNO.");
		String sql = this.getPaymentFileTotalNOSqlString(svo);
		Session session = getSession();
		try {
			List l = (List) session.createSQLQuery(sql).list();
			logger.info("Exit method getPaymentFileTotalNO.");
			return new Long(l.get(0).toString());
		} finally {
			releaseSession(session);
		}
	}
	private String getPaymentFileTotalNOSqlString(SearchPaymentVO svo){
		StringBuffer sb = new StringBuffer("select count(0)");
		sb.append(this.getPaymentFileWhereSqlString(svo));
		return sb.toString();
	}
	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 */
	public List searchPaymentFileList(SearchPaymentVO svo){
		logger.info("Enter method searchPaymentFileList.");
		String sql = this.searchPaymentListExcelSqlString(svo);
		Session session = getSession();
		try {
			List l = (List) session.createSQLQuery(sql).list();
			logger.info("Exit method searchPaymentFileList.");
			return l;
		} finally {
			releaseSession(session);
		}
	} 
	private String searchPaymentListExcelSqlString(SearchPaymentVO svo){
		StringBuffer sb = new StringBuffer("select concat('{ ");
		sb.append("id:\"',id,'\", ");
		sb.append("fileName:\"',file_name,'\", ");
		sb.append("filePath:\"',file_path,'\" ");
		sb.append("}') ");
		sb.append(this.getPaymentFileWhereSqlString(svo));
		sb.append(svo.getOrderByCause(null));
        sb.append(svo.getLimitCause());
		return sb.toString();
	}
	private String getPaymentFileWhereSqlString(SearchPaymentVO svo){
		StringBuffer sb = new StringBuffer(" ");
		sb.append("from `attachment_file` ");
		sb.append("where rec_active_flag = 'Y' ");
		if(svo.getAttachmentPointId() != null){
			sb.append("and attachment_point_id = "+svo.getAttachmentPointId()+" ");
		}
		return sb.toString();
	}
	// Created By Chao.Liu
	public void save(Object o){
		this.getHibernateTemplate().save(o);
	} 
	public Object get(Class c,Integer id){
		return this.getHibernateTemplate().get(c, id);
	}
	
	@Transactional(readOnly = false)
	public void update(Object o){
		this.getHibernateTemplate().setFlushMode(HibernateAccessor.FLUSH_AUTO);
		this.getHibernateTemplate().update(o);
	}
	
	/**
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String getPaymentWorkCountNOString(WorkspaceVO wVO) {
		logger.info("Enter method getPaymentWorkCountNOString.");
		StringBuffer sb = new StringBuffer("select count(1) from invoice,payment  ");
		sb.append("where payment.flag_workspace='Y' ");
		sb.append(" and payment.invoice_id=invoice.id ");
		if(wVO.getUid()==-1){
			sb.append(" and payment.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and payment.assignment_user_id="+wVO.getUid()+" ");
		}
		if(wVO.getDueDay()==5){
			sb.append(" and Datediff(invoice.invoice_due_date,now()) <5 ");	
		}else if(wVO.getDueDay()==10)
		{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >5 and Datediff(invoice.invoice_due_date,now())<10  ");	
		}
		else{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >10 ");
		}
		logger.info("Exit method getPaymentWorkCountNOString.");
		return sb.toString();
	}

	public List<String> searchPaymentWorkCount(WorkspaceVO wvo) {
		logger.info("Enter method searchPaymentWorkCount.");
		final String queryString = getPaymentWorkSqlString(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchPaymentWorkCount.");
		return list;
	}
	
	/**
	 * @author pengfei.wang
	 * DisputeWorkCount queryString
	 * */
	private String getPaymentWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getPaymentWorkSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',invoice.id,'\", ");
		sb.append("invoice_number:\"',toJSON(invoice.invoice_number is null,'',invoice.invoice_number),'\", ");
		sb.append("payment_transaction_number:\"',toJSON(payment.payment_transaction_number is null,'',payment.payment_transaction_number ),'\", ");
		sb.append("payment_amount:\"',toJSON(payment.payment_amount is null,'',payment.payment_amount),'\", ");
		sb.append("payment_date:\"',toJSON(payment.payment_date is null,'',payment.payment_date),'\", ");
		sb.append("payment_reference_code:\"',toJSON(payment.payment_reference_code is null,'',payment.payment_reference_code),'\" ");
		sb.append("}')  from invoice,payment  ");
		sb.append("where payment.flag_workspace='Y' ");
		sb.append(" and payment.invoice_id=invoice.id ");
		if(wVO.getUid()==-1){
			sb.append(" and payment.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and payment.assignment_user_id="+wVO.getUid()+" ");
		}
		if(wVO.getDueDay()==5){
			sb.append(" and Datediff(invoice.invoice_due_date,now()) <5 ");	
		}else if(wVO.getDueDay()==10)
		{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >5 and Datediff(invoice.invoice_due_date,now())<10  ");	
		}
		else{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >10 ");
		}
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getPaymentWorkSqlString.");
		return sb.toString();
	}
	
	public void merge(Payment payment) {
		getHibernateTemplate().merge(payment);
	}
}
