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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.Constants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 *
 * add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 * add searchInvoiceAssignment(); beijing 2010-4-16 Jian.Dong
 */
public class InvoiceDao4BBImpl extends HibernateDaoSupport implements IInvoiceDao4BB {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 */
	public InvoiceDao4BBImpl() {
	}

	/**
	 * InvoiceSearch SQL statements
	 * */
	private String getInvoiceSearchQueryString(SearchInvoiceVO sio, int userId){
		logger.info("Enter method getInvoiceSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',i.id,',no:\"',if(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',if(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",ban:\"',if(b.account_number is null ,'',b.account_number),'\",date:\"',if(i.invoice_date is null,'',i.invoice_date),'\",due:\"',if(i.invoice_due_date is null,'',i.invoice_due_date),'\",total:\"',");
		sb.append("if(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),'\",status:\"',s.invoice_status_name,");
		sb.append("'\",analyst:\"',if(i.assigned_analyst_id is null,'',u.user_name),");
		sb.append("'\",paymentAmount:\"',ROUND(if(i.payment_amount is null,'',i.payment_amount),2),");
		sb.append("'\",disputeAmount:\"',ROUND(if(i.dispute_amount is null,'',i.dispute_amount),2),");
		sb.append("'\",miscAdjustmentAmount:\"',ROUND(if(i.misc_adjustment_amount is null,'',i.misc_adjustment_amount),2),");
		sb.append("'\",invoiceStatus:\"',if(s.invoice_status_name is null,'',s.invoice_status_name),");
		sb.append("'\",paymentTransactionNumber:\"',if(p.payment_transaction_number is null,'',p.payment_transaction_number),");
		sb.append("'\",paymentStatus:\"',if(ps.payment_status_name is null,'',ps.payment_status_name),");
		sb.append("'\",paymentReferenceNumber:\"',if(p.payment_reference_code is null,'',p.payment_reference_code),");
		sb.append("'\",paymentDate:\"',if(p.payment_date is null,'',p.payment_date),");
		sb.append("'\",daysInStatus:\"',if(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),'\"");
		sb.append("}') a ");
		
		sb.append(" from ((((((invoice i join vendor v) join ban b) join invoice_status s) join payment_status ps) join payment p) join user u) ");
		
		sb.append(voWhereConditions(sio,userId));
		
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getInvoiceSearchQueryString.");
		return sb.toString();
	}
	
	private String getInvoiceSearchByObjectQueryString(SearchInvoiceVO sio, int userId){
		logger.info("Enter method getInvoiceSearchByObjectQueryString.");
		StringBuffer sb = new StringBuffer("select i.invoice_number,p.payment_transaction_number,p.payment_reference_code,v.vendor_name,b.account_number ");
		sb.append(",i.invoice_date,i.invoice_due_date,ROUND(if(i.invoice_total_due is null,0,i.invoice_total_due),2),u.user_name,ROUND(if(i.payment_amount is null,0,i.payment_amount),2),ROUND(if(i.dispute_amount is null,0,i.dispute_amount),2),ROUND(if(i.misc_adjustment_amount is null,0,i.misc_adjustment_amount),2)");
		sb.append(",s.invoice_status_name,datediff(now(),i.status_timestamp),ps.payment_status_name,p.payment_date");
		sb.append(" from ((((((invoice i join vendor v) join ban b) join invoice_status s) join payment_status ps) join payment p) join user u) ");
		sb.append(voWhereConditions(sio,userId));
		
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getInvoiceSearchByObjectQueryString.");
		return sb.toString();
	}
	
	private String voWhereConditions(SearchInvoiceVO sio, int userId) {
		logger.info("Enter method voWhereConditions.");
		StringBuffer sb = new StringBuffer();
		
		sb.append(" where (i.vendor_id = v.id) and (i.ban_id = b.id) ");
		sb.append(" and i.id = p.invoice_id  ");
		sb.append(" and ps.id = p.payment_status_id ");
		sb.append(" and i.invoice_status_id = s.id ");
		sb.append(" and u.id = i.assigned_analyst_id ");
		sb.append(" and i.rec_active_flag = 'Y' ");
		sb.append(" and p.rec_active_flag = 'Y' ");
		
		if(sio.getFilter()!=null){
			sb.append(" and ");
			sb.append(sio.getFilter());
		}	
		
		if(sio.getVendorId()!=null) {
			sb.append(" and i.vendor_id="+sio.getVendorId()+" ");
			if(sio.getBanId()!=null)
				sb.append(" and i.ban_id="+sio.getBanId()+" ");
			else{
				sb.append(" and i.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		}
		else{
			if(sio.getBanId()!=null)
				sb.append(" and i.ban_id="+sio.getBanId()+" ");
			else{
				sb.append(" and i.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
			}
		} 

		if(sio.getDaysInStatus()!=null)
			sb.append(" and datediff(now(),if(i.status_timestamp is null,'',i.status_timestamp)) > "+sio.getDaysInStatus()+" ");
		
		
		if(sio.getInvoiceNumber()!=null)
			sb.append(" and upper(i.invoice_number) like '%"+sio.getInvoiceNumber()+"%' ");
		if(sio.getPaymentReference()!=null)
			sb.append(" and upper(p.payment_reference_code) like '%"+sio.getPaymentReference()+"%' ");
		if(sio.getStatusId()!=null)
			sb.append(" and i.invoice_status_id="+sio.getStatusId()+" ");
		
		if(sio.getPaymentStatusId()!=null)
			sb.append(" and p.payment_status_id="+sio.getPaymentStatusId()+" ");
		
		if(sio.getAnalystId()!=null)
			sb.append(" and i.assigned_analyst_id="+sio.getAnalystId()+" ");
		
		if(sio.getInvoiceDateWiPastNumOfDays() == null){
			if(sio.calInvoiceDateStartsOn()!=null)
				sb.append(" and datediff(i.invoice_date, "+sio.calInvoiceDateStartsOn()+")>=0 "); 
			if(sio.calInvoiceDateEndsOn()!=null)
				sb.append(" and datediff(i.invoice_date, "+sio.calInvoiceDateEndsOn()+")<=0 "); 
		}else{
			sb.append(" and datediff(i.invoice_date, "+sio.calInvoiceDateStartsOn()+")>=0 "); 
			sb.append(" and datediff(i.invoice_date, "+sio.calInvoiceDateStartsOn()+")<=" + sio.getInvoiceDateWiPastNumOfDays() + " "); 
		}
		
		if(sio.getInvoiceDueWiPastNumOfDays() == null){
			if(sio.calInvoiceDueStartsOn()!=null)
				sb.append(" and datediff(i.invoice_due_date, "+sio.calInvoiceDueStartsOn()+")>=0 "); 
			if(sio.calInvoiceDueEndsOn()!=null)
				sb.append(" and datediff(i.invoice_due_date, "+sio.calInvoiceDueEndsOn()+")<=0 "); 
		}else{
			sb.append(" and datediff(i.invoice_due_date, "+sio.calInvoiceDueStartsOn()+")>=0 ");
			sb.append(" and datediff(i.invoice_due_date, "+sio.calInvoiceDueStartsOn()+")<=" + sio.getInvoiceDueWiPastNumOfDays() + " ");
		}
		
		if(sio.getPaymentDateWiPastNumOfDays() == null){
			if(sio.calPaymentDateStartsOn()!=null)
				sb.append(" and datediff(p.payment_date, "+sio.calPaymentDateStartsOn()+")>=0 "); 
			if(sio.calPaymentDateEndsOn()!=null)
				sb.append(" and datediff(p.payment_date, "+sio.calPaymentDateEndsOn()+")<=0 "); 
		}else{
			sb.append(" and datediff(p.payment_date, "+sio.calPaymentDateStartsOn()+")>=0 "); 
			sb.append(" and datediff(p.payment_date, "+sio.calPaymentDateStartsOn()+")<=" + sio.getPaymentDateWiPastNumOfDays() + " "); 
		}
		
		logger.info("Enter method voWhereConditions.");
		return sb.toString();
	}
	
	/**
	 * InvoiceSearch pagination SQL statements
	 * */
	private String getInvoiceSearchNumberQueryString(SearchInvoiceVO sio, int userId){
		logger.info("Enter method getInvoiceSearchNumberQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"$1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from ((((((invoice i join vendor v) join ban b) join invoice_status s) join payment_status ps) join payment p) join user u)");
		
		sb.append(voWhereConditions(sio,userId));
		logger.info("Exit method getInvoiceSearchNumberQueryString.");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInvoiceDao#getNumberOfInvoices(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getNumberOfInvoices(SearchInvoiceVO sio, int userId) {
		logger.info("Enter method getNumberOfInvoices.");
		final String sql = this.getInvoiceSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfInvoices.");
		return count;
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInvoiceDao#searchInvoice(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchInvoice(SearchInvoiceVO sio, int userId) {
		logger.info("Enter method searchInvoice.");
		final String sql = this.getInvoiceSearchQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoice.");
		return l;
	}
	
	public List<Object[]> searchInvoiceByObject(SearchInvoiceVO sio, int userId) {
		logger.info("Enter method searchInvoiceByObject.");
		final String sql = this.getInvoiceSearchByObjectQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoiceByObject.");
		return l;
	}
	private String assignmentSearchAlias = "i";
	/**
	 * To InvoiceAction aggregation condition UserID data
	 * */
	public long getAssignmentCount(int userId) {
		logger.info("Enter method getAssignmentCount.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.getAssignmentCountSelectCause());
		sql.append(this.getAssignmentWhereCause());
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql.toString());
			query.setInteger(0, userId);
			BigInteger count = (BigInteger)query.uniqueResult();
			
			logger.info("Exit method getAssignmentCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * InvoiceAction returns to the data
	 * */
	public List<String> searchInvoiceAssignment(SearchInvoiceVO svo) {
		logger.info("Enter method searchInvoiceAssignment.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.getAssignmentSearchSelectCause());
		sql.append(this.getAssignmentWhereCause());
		sql.append(svo.getOrderByCause(assignmentSearchAlias));
		sql.append(svo.getLimitCause());
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql.toString());
//		query.setInteger(0, svo.getUserId());
			List<String> result = (List<String>)query.list();
			logger.info("Exit method searchInvoiceAssignment.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
 
	private String getAssignmentCountSelectCause() {
		return "select count(*) assignmentCount from search_invoice_view " + assignmentSearchAlias;
	}
	/**
	 * View of statements
	 * */
	private String getAssignmentSearchSelectCause() {
		StringBuilder sb = new StringBuilder("select concat('{id:',id,',no:\"',if(invoice_number is null,'',invoice_number),'\",vendor:\"',vendor_name,");
		sb.append("'\",ban:\"',account_number,'\",date:\"',if(invoice_date is null,'',invoice_date),'\",due:\"',if(invoice_due_date is null,'',invoice_due_date),'\",total:\"',");
		sb.append("if(invoice_total_due is null,'',concat('$',format(invoice_total_due, 2))),'\",status:\"',invoice_status_name,'\",n:\"',if(flag_new_ban is null,'',flag_new_ban),'\",c:\"',if(flag_new_circuit is null,'',flag_new_circuit),");
		sb.append("'\",d:\"',if(flag_dispute is null,'',flag_dispute),'\",o:\"',if(flag_large is null,'',flag_large),'\"}') jsonObj ");
		sb.append("from search_invoice_view ");
		sb.append(assignmentSearchAlias);
		return sb.toString();
	}
	/**
	 * View of statements
	 * */
	private String getAssignmentWhereCause() {
		StringBuilder sb = new StringBuilder();
		sb.append(" where " + assignmentSearchAlias + ".ban_id in");
		sb.append(" (select b.id from ban b )");
		sb.append(" and " + assignmentSearchAlias + ".flag_workspace='Y'");
		return sb.toString();
	}
	
	/**
	 * According to introduction data batch sqlstring
	 * @author wei.su
	 */
	public void  saveDataByXml(String sqlStr){
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sqlStr);
			query.executeUpdate();
		} finally {
			releaseSession(session);
		}
		
	}
	/**
	 * @author pengfei.wang
	 * InvoiceWorkSpace quantity statistics
	 * */
	public long getInvoiceWorkCount(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCount.");
		final String sql = getInvoiceWorkCountNOString(wVO);
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
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String getInvoiceWorkCountNOString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCountNOString.");
		StringBuffer sb = new StringBuffer("select count(distinct invoice.id) from invoice,invoice_status ,vendor,ban,payment,dispute  ");
		sb.append("where invoice.invoice_status_id =  invoice_status.id ");
		sb.append(" and  invoice.ban_id =  ban.id ");
		sb.append(" and  invoice.id=payment.invoice_id ");
		sb.append(" and  invoice.id=dispute.invoice_id ");
		sb.append(" and  invoice.vendor_id =  vendor.id ");
		sb.append(" and  invoice.rec_active_flag='Y' ");
		if(wVO.getUid()==-1){
			sb.append(" and ((invoice.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
			sb.append(" and  invoice.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("  (payment.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
			sb.append(" and   payment.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("   (dispute.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
			sb.append(" and  dispute.flag_workspace='Y' ))");
		}else{
			sb.append(" and ((invoice.assignment_user_id="+wVO.getUid()+" ");
			sb.append(" and  invoice.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("  (payment.assignment_user_id="+wVO.getUid()+" ");
			sb.append(" and   payment.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("   (dispute.assignment_user_id="+wVO.getUid()+" ");
			sb.append(" and  dispute.flag_workspace='Y')) ");
		}
		if(wVO.getDueDay()==5){
			sb.append(" and Datediff(invoice.invoice_due_date,now()) <5 ");	
		}else if(wVO.getDueDay()==10)
		{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >=5 and Datediff(invoice.invoice_due_date,now())<=10  ");	
		}
		else{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >10 ");
		}
		logger.info("Exit method getInvoiceWorkCountNOString.");
		return sb.toString();
	}
	
	/**
	 * @author pengfei.wang
	 * InvoiceWorkSpace query
	 * */
	public List<String> searchInvoiceWorkCount(WorkspaceVO wVO) {
		logger.info("Enter method searchInvoiceWorkCount.");
		final String queryString = getInvoiceWorkSqlString(wVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchInvoiceWorkCount.");
		return list;
	}
	
	
	/**
	 * @author pengfei.wang
	 * InvoiceWorkCount queryString
	 * */
	private String getInvoiceWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',invoice.id,'\", ");
		sb.append("no:\"',toJSON(invoice.invoice_number is null,'',invoice.invoice_number),'\", ");
		sb.append("date:\"',toJSON(invoice.invoice_date is null,'',invoice.invoice_date ),'\", ");
		sb.append("payment_amount:\"',toJSON(sum(payment.payment_amount) is null,'',sum(payment.payment_amount) ),'\", ");
		sb.append("dispute_amount:\"',toJSON(sum(dispute.dispute_amount) is null,'',sum(dispute.dispute_amount) ),'\", ");
		sb.append("due:\"',toJSON(invoice.invoice_due_date is null,'',invoice.invoice_due_date),'\", ");
		sb.append("total:\"',toJSON(invoice.invoice_total_due is null,'',invoice.invoice_total_due),'\", ");
		sb.append("vendor:\"',toJSON(vendor.vendor_name is null,'',vendor.vendor_name),'\", ");
		sb.append("ban:\"',toJSON(ban.account_number is null,'',ban.account_number),'\", ");
		sb.append("status:\"',toJSON(invoice_status.invoice_status_name is null,'',invoice_status.invoice_status_name),'\" ");
		sb.append("}')  from invoice,invoice_status ,vendor,ban,payment,dispute ");
		sb.append("where invoice.invoice_status_id =  invoice_status.id ");
		sb.append(" and  invoice.ban_id =  ban.id ");
		sb.append(" and  invoice.id=payment.invoice_id ");
		sb.append(" and  invoice.id=dispute.invoice_id ");
		sb.append(" and  invoice.vendor_id =  vendor.id ");
		sb.append(" and  invoice.rec_active_flag='Y' ");
		if(wVO.getUid()==-1){
			sb.append(" and ((invoice.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
			sb.append(" and  invoice.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("  (payment.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
			sb.append(" and   payment.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("   (dispute.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
			sb.append(" and  dispute.flag_workspace='Y' ))");
		}else{
			sb.append(" and ((invoice.assignment_user_id="+wVO.getUid()+" ");
			sb.append(" and  invoice.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("  (payment.assignment_user_id="+wVO.getUid()+" ");
			sb.append(" and   payment.flag_workspace='Y' )");
			sb.append(" or ");
			sb.append("   (dispute.assignment_user_id="+wVO.getUid()+" ");
			sb.append(" and  dispute.flag_workspace='Y')) ");
		}
		if(wVO.getDueDay()==5){
			sb.append(" and Datediff(invoice.invoice_due_date,now()) <5 ");	
		}else if(wVO.getDueDay()==10)
		{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >=5 and Datediff(invoice.invoice_due_date,now())<=10  ");	
		}
		else{
			sb.append(" and Datediff(invoice.invoice_due_date,now()) >10 ");
		}
		sb.append("    group by invoice.invoice_number ");
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getInvoiceWorkSqlString.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Missing Invoices
	 * */
	public List<String> searchWorkspase(WorkspaceVO wvo) {
		logger.info("Enter method searchWorkspase.");
		final String queryString = getWorkSqlString(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchWorkspase.");
		return list;
	}
	
	/**
	 * @author pengfei.wang
	 * searchWorkspase queryString
	 * */
	private String getWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',invoice.id,'\", ");
		sb.append("vendor:\"',toJSON(vendor.vendor_name is null,'',vendor.vendor_name),'\", ");
		sb.append("ban:\"',toJSON(ban.account_number is null,'',ban.account_number ),'\", ");
		sb.append("expectingDate:\"',toJSON(invoice.invoice_expecting_date is null,'',invoice.invoice_expecting_date ),'\", ");
		sb.append("lastMonthTotalDue:\"',toJSON(invoice.last_total_due is null,'',invoice.last_total_due ),'\", ");
		sb.append("lastMonthPayment:\"',toJSON(invoice.last_payment is null,'',invoice.last_payment),'\", ");
		sb.append("analystName:\"',toJSON(invoice.assigned_analyst_id is null,'',invoice.assigned_analyst_id),'\", ");
		sb.append("lastMonthDispute:\"',toJSON(invoice.last_dispute is null,'',invoice.last_dispute),'\" ");
		sb.append("}')  from invoice ,ban ,vendor ,invoice_status ");
		sb.append("where invoice.ban_id =  ban.id ");
		sb.append(" and  invoice.vendor_id =  vendor.id ");
		sb.append(" and  invoice_status.invoice_status_name =  'Waiting'  ");
		sb.append(" and  invoice.invoice_status_id =  invoice_status.id ");
		sb.append(" and  invoice.rec_active_flag='Y' ");
		if(wVO.getUid()==-1){
			sb.append(" and invoice.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}
		else {
			sb.append(" and invoice.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getInvoiceWorkSqlString.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Missing Invoices Count
	 * */
	public long getWorkspaseCount(WorkspaceVO wvo) {
		logger.info("Enter method getWorkspaseCount.");
		final String sql = getInvoiceWorkCountNO(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getWorkspaseCount.");
		return count;
	}
	
	/**
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String getInvoiceWorkCountNO(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCountNO.");
		StringBuffer sb = new StringBuffer("select count(*) from invoice ,ban ,vendor ,invoice_status  ");
		sb.append("where invoice.ban_id =  ban.id ");
		sb.append(" and  invoice.vendor_id =  vendor.id ");
		sb.append(" and  invoice_status.invoice_status_name =  'Waiting'  ");
		sb.append(" and  invoice.invoice_status_id =  invoice_status.id ");
		sb.append(" and  invoice.rec_active_flag='Y' ");
		if(wVO.getUid()==-1){
			sb.append(" and invoice.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}
		else {
			sb.append(" and invoice.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		logger.info("Exit method getInvoiceWorkCountNO.");
		return sb.toString();
	}

	public long getWorkspaseProcessCount(WorkspaceVO wvo) {
		logger.info("Enter method getWorkspaseProcessCount.");
		final String sql = getInvoiceWorkCountNOProcess(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getWorkspaseProcessCount.");
		return count;
	}
	
	public User findUserById(Integer id) {
		logger.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"com.saninco.ccm.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/**
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String getInvoiceWorkCountNOProcess(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCountNO.");
		StringBuffer sb = new StringBuffer("select count(*) from invoice ,ban ,vendor,invoice_status  ");
		sb.append("where invoice.ban_id =  ban.id ");
		sb.append(" and  invoice.vendor_id =  vendor.id ");
		sb.append(" and  invoice_status.invoice_status_name =  'Transaction in Process'  ");
		sb.append(" and  invoice.invoice_status_id =  invoice_status.id ");
		sb.append(" and  invoice.rec_active_flag='Y' ");
		if(wVO.getUid()==-1){
			sb.append(" and invoice.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}
		else {
			sb.append(" and invoice.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		logger.info("Exit method getInvoiceWorkCountNO.");
		return sb.toString();
	}

	public List<String> searchWorkspaseProcess(WorkspaceVO wvo) {
		logger.info("Enter method searchWorkspaseProcess.");
		final String queryString = getWorkSqlStringProcess(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchWorkspaseProcess.");
		return list;
	}
	
	/**
	 * @author pengfei.wang
	 * searchWorkspase queryString
	 * */
	private String getWorkSqlStringProcess(WorkspaceVO wVO) {
		logger.info("Enter method getWorkSqlStringProcess.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',invoice.id,'\", ");
		sb.append("vendor:\"',toJSON(vendor.vendor_name is null,'',vendor.vendor_name),'\", ");
		sb.append("ban:\"',toJSON(ban.account_number is null,'',ban.account_number ),'\", ");
		sb.append("totalDue:\"',toJSON(invoice.invoice_total_due is null,'',invoice.invoice_total_due ),'\", ");
		sb.append("payment:\"',toJSON(invoice.payment_amount is null,'',invoice.payment_amount ),'\", ");
		sb.append("dispute:\"',toJSON(invoice.dispute_amount is null,'',invoice.dispute_amount),'\", ");
		sb.append("numberOfDaysInProcess:\"',toJSON((Datediff(now(),invoice.status_timestamp)) is null, '' ,(Datediff(now(),invoice.status_timestamp))),'\", ");
		sb.append("analystName:\"',toJSON(invoice.assigned_analyst_id is null,'',invoice.assigned_analyst_id),'\" ");
		sb.append("}')  from invoice ,ban ,vendor ,invoice_status ");
		sb.append("where  invoice.ban_id =  ban.id ");
		sb.append(" and  invoice.vendor_id =  vendor.id ");
		sb.append(" and  invoice_status.invoice_status_name =  'Transaction in Process'  ");
		sb.append(" and  invoice.invoice_status_id =  invoice_status.id ");
		sb.append(" and  invoice.rec_active_flag='Y' ");
		if(wVO.getUid()==-1){
			sb.append(" and invoice.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}
		else {
			sb.append(" and invoice.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getWorkSqlStringProcess.");
		return sb.toString();
	}
	
	public Invoice findById(java.lang.Integer id) {

		Invoice instance = (Invoice) getHibernateTemplate().get(
				"com.saninco.ccm.model.Invoice", id);
		return instance;
	}
	
	public String getVendorAcronymByInvoiceId(int invoiceId){
		
		final StringBuffer sb=new StringBuffer();
		sb.append("select v.vendor_acronym from invoice as i left join vendor as v on i.vendor_id=v.id where i.id= ");
		sb.append(""+invoiceId+"");
		Session session = getSession();
		try {
			SQLQuery query= session.createSQLQuery(sb.toString());
			return query.uniqueResult().toString();
		} finally {
			releaseSession(session);
		}
		
	}
	
	public String getVendorIdByInvoiceId(int invoiceId){
		
		final StringBuffer sb=new StringBuffer();
		sb.append("select i.vendor_id from invoice as i where i.id= ");
		sb.append(""+invoiceId+"");
		Session session = getSession();
		try {
			SQLQuery query=session.createSQLQuery(sb.toString());
			return query.uniqueResult().toString();
		} finally {
			releaseSession(session);
		}
		
	}
	
	/**
	 * Search all invoices by current user for approval.
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> searchInvoicesForApproval(final SearchInvoiceVO svo) {
		logger.info("Enter method searchInvoicesForApproval.");
		final String sql = "select i.id,i.invoice_number, v.vendor_name, b.account_number, i.payment_amount, i.dispute_amount, i.invoice_status_id " +
				"from invoice i, vendor v, ban b where i.assignment_user_id="+svo.getUserId()+" and " +
				"i.invoice_status_id>="+Constants.MINIMAL_STATUS_ID_FOR_INVOICE_APPROVAL+" and " +
				"i.invoice_status_id<="+Constants.MAXIMIUM_STATUS_ID_FOR_INVOICE_APPROVAL+" and " +
				"i.vendor_id=v.id and i.ban_id=b.id " +
				"and v.rec_active_flag='Y' and b.rec_active_flag='Y' and b.ban_status_id=1 and b.master_ban_flag='Y' " + 
				svo.getOrderByCause(null) + svo.getLimitCause();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoicesForApproval.");
		return l;
	}
	
	/**
	 * Get summary for approve all invoices.
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getSummaryForApproveAll(String invoiceIds){
		logger.info("Enter method getSummaryForApproveAll.");
		final String sql = "select count(i.id), sum(i.payment_amount), sum(i.dispute_amount) " +
				"from invoice i where i.id in ("+invoiceIds+")";
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getSummaryForApproveAll.");
		return l;
	}

	/**
	 * Search all invoice objects for approval.
	 */
	@SuppressWarnings("unchecked")
	public List<Invoice> searchInvoiceObjectsForApproval(String invoiceIds) {
		logger.info("Enter method searchInvoiceObjectsForApproval.");
		final String sql = "select i.* " +
				"from invoice i where i.id in ("+invoiceIds+")";
		HibernateTemplate template = this.getHibernateTemplate();
		List<Invoice> l = (List<Invoice>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).addEntity(Invoice.class).list();
		    }
		});
		logger.info("Exit method searchInvoiceObjectsForApproval.");
		return l;
	}
	
	/**
	 * Get approval invoice and last three referenced invoices.
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getLast4InvoicesForReferenceApproval(final SearchInvoiceVO svo) {
		logger.info("Enter method getLast4InvoicesForReferenceApproval.");
		final String sql = "select i.invoice_number, v.vendor_name, " +
				"b.account_number, i.invoice_date, i.payment_amount, i.dispute_amount,i.invoice_status_id,i.id " +
				"from invoice i, vendor v, ban b where " +
				"datediff(i.invoice_date, (select ii.invoice_date from invoice ii where ii.id="+svo.getInvoiceId()+"))<=0 " +
				"and i.vendor_id=(select ii0.vendor_id from invoice ii0 where ii0.id="+svo.getInvoiceId()+") " + 
				"and i.ban_id=(select ii0.ban_id from invoice ii0 where ii0.id="+svo.getInvoiceId()+") " + 
				"and i.vendor_id=v.id and i.ban_id=b.id order by i.invoice_date desc limit " + 
				(Constants.NUMBER_OF_INVOICES_FOR_APPROVAL_REFERENCE+1);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getLast4InvoicesForReferenceApproval.");
		return l;
	}

	/**
	 * Get count of invoics for approval.
	 */
	@SuppressWarnings("unchecked")
	public int getCountOfInvoicesForApproval(final SearchInvoiceVO svo) {
		logger.info("Enter method searchInvoicesForApproval.");
		final String sql = "select count(1) " +
				"from invoice where assignment_user_id="+svo.getUserId()+" and " +
				"invoice_status_id>="+Constants.MINIMAL_STATUS_ID_FOR_INVOICE_APPROVAL + 
				" and invoice_status_id<="+Constants.MAXIMIUM_STATUS_ID_FOR_INVOICE_APPROVAL;
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		return count;
	}
	
	public Invoice merge(Invoice detachedInstance) {
		logger.debug("merging Invoice instance");
		try {
			Invoice result = (Invoice) getHibernateTemplate().merge(
					detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Invoice instance) {
		logger.debug("attaching dirty Invoice instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Invoice instance) {
		logger.debug("attaching clean Invoice instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

}
