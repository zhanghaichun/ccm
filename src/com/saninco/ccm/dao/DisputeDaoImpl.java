package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.DisputeStatus;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.ProposalDAO;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.WorkflowAction;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchDisputeVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * Spring Hibernate DAO class for Dispute based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author li,zhiguo
 *   return count of dispute
 *   @see pendingDispute is below --pengfei.wang
 *   add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 */
@SuppressWarnings("unchecked")
public class DisputeDaoImpl extends HibernateDaoSupport implements IDisputeDao {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static final Log log = LogFactory.getLog(ProposalDAO.class);

	public static final String DISPUTE_BALANCE = "disputeBalance";
	public static final String DISPUTE_AMOUNT = "disputeAmount";
	public static final String DISPUTE_NUMBER = "disputeNumber";
	public static final String CLAIM_NUMBER = "claimNumber";
	public static final String TICKET_NUMBER = "ticketNumber";
	public static final String NOTES = "notes";
	public static final String FLAG_WORKSPACE = "flagWorkspace";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	
	public DisputeDaoImpl() {
	}
	
	public void recalculateDisputeAmountAndDisputeBalance(String disputeId){
		logger.info("Enter method updateDisputeBalanceByProposal.");
		final StringBuffer dbSql = new StringBuffer("update dispute d set ");
		dbSql.append(" d.dispute_balance = (select sum(if(p.balance_amount is null,0,p.balance_amount)) ");
		dbSql.append(" from proposal p where p.dispute_id = " + disputeId + " and p.rec_active_flag = 'Y') ");
		dbSql.append(" where d.id = " + disputeId + " ");
		
		final StringBuffer daSql = new StringBuffer("update dispute d set ");
		daSql.append(" d.dispute_amount = (select sum(if(p.dispute_amount is null,0,p.dispute_amount)) ");
		daSql.append(" from proposal p where p.dispute_id = " + disputeId + " and p.rec_active_flag = 'Y') ");
		daSql.append(" where d.id = " + disputeId + " ");
		
		Session session = getSession();
		try {
			session.createSQLQuery(dbSql.toString()).executeUpdate();
			session.createSQLQuery(daSql.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateDisputeBalanceByProposal.");
	}
	
	public void updateDisputeBalanceByProposal(String disputeId){
		logger.info("Enter method updateDisputeBalanceByProposal.");
		final StringBuffer sb = new StringBuffer("update dispute d set ");
		sb.append(" d.modified_timestamp = now() ");
		sb.append(" ,d.modified_by = '" + SystemUtil.getCurrentUserId() + "' ");
		sb.append(" ,d.dispute_balance = (select sum(if(p.balance_amount is null,0,p.balance_amount)) ");
		sb.append(" from proposal p where p.dispute_id = " + disputeId + " and p.rec_active_flag = 'Y') ");
		sb.append(" where d.id = " + disputeId + " ");
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateDisputeBalanceByProposal.");
	}
	
	public void insertReconcileBySplit(SearchDisputeVO sdo ,Integer userId,String s1,String s2){
		logger.info("Enter method insertReconcileBySplit.");
		final String queryString = insertReconcileBySplitQueryString(sdo,userId,s1,s2);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method insertReconcileBySplit.");
	}
	
	private String insertReconcileBySplitQueryString(SearchDisputeVO sdo, Integer userId,String s1,String s2 ) {
		logger.info("Enter method insertReconcileBySplitQueryString.");
		StringBuffer sb = new StringBuffer("insert into reconcile ( dispute_proposal_id,dispute_id,reconcile_amount,");
		sb.append(" account_code_id,notes,reconcile_status_id,created_timestamp, ");
		sb.append(" modified_timestamp,created_by,modified_by,rec_active_flag,reconcile_date ");
		if(sdo.getAttachmentPointId() != null){
			sb.append(",attachment_point_id");
		}
		sb.append(" ) ");
		sb.append(" select p.id,p.dispute_id,'"+sdo.getBalanceAmount()+"', ");
		if(!"".equals(sdo.getAccountCodeId())){
			sb.append("'"+sdo.getAccountCodeId()+"', ");
		}else{
			sb.append("p.account_code_id, ");
		}
		sb.append(" '"+sdo.getNotes()+"',if(d.flag_shortpaid = 'Y','"+s1+"','"+s2+"'),now(),now(), ");
		sb.append(" '"+userId+"','"+userId+"','Y',now() ");
		if(sdo.getAttachmentPointId() != null){
			sb.append(",'"+sdo.getAttachmentPointId()+"'");
		}
		sb.append(" from proposal p ,dispute d ");
		sb.append(" where p.dispute_id = d.id ");
		sb.append(" and p.id in (" + sdo.getBoxId() + ") ");
		logger.info("Exit method insertReconcileBySplitQueryString.");
		return sb.toString();
	}
	
	public void updateSplitDispute(SearchDisputeVO sdo ,Integer userId ) {
		logger.info("Enter method updateSplitDispute.");
		final String queryString = updateSplitDisputeQueryString(sdo,userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateSplitDispute.");
	}
	
	private String updateSplitDisputeQueryString(SearchDisputeVO sdo, Integer userId ) {
		logger.info("Enter method updateSplitDisputeQueryString.");
		StringBuffer sb = new StringBuffer("update proposal set ");
		sb.append(" balance_amount = balance_amount - "+sdo.getBalanceAmount()+" ");
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + userId + "' ");
		sb.append(" where id in (" + sdo.getBoxId() + ") ");
		logger.info("Exit method updateSplitDisputeQueryString.");
		return sb.toString();
	}
	
	public void insertReconcileByLose(SearchDisputeVO sdo ,Integer userId ) {
		logger.info("Enter method insertReconcileByLose.");
		final String queryString = insertReconcileByLoseQueryString(sdo,userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method insertReconcileByLose.");
	}
	
	private String insertReconcileByLoseQueryString(SearchDisputeVO sdo, Integer userId ) {
		logger.info("Enter method insertReconcileByLoseQueryString.");
		StringBuffer sb = new StringBuffer("insert into reconcile ( dispute_proposal_id,dispute_id,reconcile_amount,");
		sb.append(" account_code_id,notes,reconcile_status_id,created_timestamp, ");
		sb.append(" modified_timestamp,created_by,modified_by,rec_active_flag,reconcile_date ");
		if(sdo.getAttachmentPointId() != null){
			sb.append(", attachment_point_id");
		}
		sb.append(" ) ");
		sb.append(" select p.id,p.dispute_id,p.balance_amount,p.account_code_id, ");
		sb.append(" '"+sdo.getNotes()+"',if(d.flag_shortpaid = 'Y','7','5'),now(),now(), ");
		sb.append(" '"+userId+"','"+userId+"','Y',now() ");
		if(sdo.getAttachmentPointId() != null){
			sb.append(",'"+sdo.getAttachmentPointId()+"'");
		}
		sb.append(" from proposal p ,dispute d ");
		sb.append(" where p.dispute_id = d.id ");
		sb.append(" and p.id in (" + sdo.getBoxId() + ") ");
		logger.info("Exit method insertReconcileByLoseQueryString.");
		return sb.toString();
	}
	
	public void insertReconcileByWin(SearchDisputeVO sdo ,Integer userId ) {
		logger.info("Enter method insertReconcileByWin.");
		final String queryString = insertReconcileByWinQueryString(sdo,userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method insertReconcileByWin.");
	}
	
	private String insertReconcileByWinQueryString(SearchDisputeVO sdo, Integer userId ) {
		logger.info("Enter method insertReconcileByWinQueryString.");
		StringBuffer sb = new StringBuffer("insert into reconcile ( dispute_proposal_id,dispute_id,reconcile_amount,");
		sb.append(" account_code_id,notes,reconcile_status_id,created_timestamp, ");
		sb.append(" modified_timestamp,created_by,modified_by,rec_active_flag,reconcile_date");
		if(sdo.getAttachmentPointId() != null){
			sb.append(", attachment_point_id");
		}
		sb.append(" ) ");
		sb.append(" select p.id,p.dispute_id,p.balance_amount,p.account_code_id, ");
		sb.append(" '"+sdo.getNotes()+"',if(d.flag_shortpaid = 'Y','8','6'),now(),now(), ");
		sb.append(" '"+userId+"','"+userId+"','Y',now() ");
		if(sdo.getAttachmentPointId() != null){
			sb.append(",'"+sdo.getAttachmentPointId()+"'");
		}
		sb.append(" from proposal p ,dispute d ");
		sb.append(" where p.dispute_id = d.id ");
		sb.append(" and p.id in (" + sdo.getBoxId() + ") ");
		logger.info("Exit method insertReconcileByWinQueryString.");
		return sb.toString();
	}
	
	public void updateSCOACoingByProposal(SearchDisputeVO sdo ,Integer userId ) {
		logger.info("Enter method updateSCOACoingByProposal.");
		final String queryString = updateSCOACoingByProposalQueryString(sdo,userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateSCOACoingByProposal.");
	}
	
	private String updateSCOACoingByProposalQueryString(SearchDisputeVO sdo, Integer userId ) {
		logger.info("Enter method updateSCOACoingByProposalQueryString.");
		StringBuffer sb = new StringBuffer("update proposal set ");
		sb.append(" account_code_id = '" + sdo.getAccountCodeId() + "' ");
		sb.append(" , scoa_source_id = 710101 ");
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + userId + "' ");
		sb.append(" where id in (" + sdo.getBoxId() + ") and rec_active_flag = 'Y'");
		logger.info("Exit method updateSCOACoingByProposalQueryString.");
		return sb.toString();
	}
	
	public void updateDisputeTimestamp (Integer id) {
		logger.info("Enter method updateDisputeTimestamp.");
		final String queryString = "update dispute set modified_timestamp = now(),modified_by = 0 where id = " + id;
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateDisputeTimestamp.");
	}
	
	public void updateDisputeTimestampByDisputeNumber (String disputeNumber) {
		logger.info("Enter method updateDisputeTimestamp.");
		final String queryString = "update dispute set modified_timestamp = now(),modified_by = 0 where dispute_number = '" + disputeNumber + "';";
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateDisputeTimestamp.");
	}
	
	public void updateDisputeCloseDisputeWins(SearchDisputeVO sdo ,Integer userId ) {
		logger.info("Enter method updateDisputeCloseDisputeWins.");
		final String queryString = updateDisputeCloseDisputeWinsQueryString(sdo,userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateDisputeCloseDisputeWins.");
	}
	
	private String updateDisputeCloseDisputeWinsQueryString(SearchDisputeVO sdo, Integer userId ) {
		logger.info("Enter method updateDisputeCloseDisputeWinsQueryString.");
		StringBuffer sb = new StringBuffer("update proposal set ");
		sb.append(" balance_amount = 0 ");
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + userId + "' ");
		sb.append(" where id in (" + sdo.getBoxId() + ") ");
		logger.info("Exit method updateDisputeCloseDisputeWinsQueryString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> searchTransactionHistorys(SearchDisputeVO sdo){
		logger.info("Enter method searchTransactionHistorys.");
		final String sql = this.searchTransactionHistorysQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchTransactionHistorys.");
		return l;
	}
	
	private String searchTransactionHistorysQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchTransactionHistorysQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',th.id,',");
		sb.append("createdTimestamp:\"',if(th.created_timestamp is null,'',th.created_timestamp),'\",");
		sb.append("notes:\"',toJSON(th.notes is null,'',th.notes),'\",");
		sb.append("disputeAmount:\"',format(toJSON(th.dispute_amount is null,'',th.dispute_amount),5),'\",");
		sb.append("attachmentPoint:\"',toJSON(th.attachment_point_id is null,'',th.attachment_point_id),'\",");
		sb.append("invoiceStatus:\"',toJSON(th.invoice_status_id is null,'',s.invoice_status_name),'\",");
		sb.append("disputeStatus:\"',toJSON(th.dispute_status_id is null,'',ds.dispute_status_name),'\",");
		sb.append("workflowUser:\"',toJSON(th.workflow_user_id is null,'',CONCAT(if(u.first_name is null,'',u.first_name),' ',if(u.last_name is null,'',u.last_name))),'\",");
		sb.append("workflowAction:\"',toJSON(th.workflow_action_id is null,'',wa.workflow_action_name),'\"");
		sb.append("}') a ");
		sb.append(" from ((((transaction_history th left join invoice_status s on (th.invoice_status_id = s.id)) ");
		sb.append(" left join dispute_status ds on (th.dispute_status_id = ds.id)) ");
		sb.append(" left join workflow_action wa on (th.workflow_action_id = wa.id)) ");
		sb.append(" left join user u on (th.workflow_user_id = u.id)) ");
		sb.append(" where th.rec_active_flag = 'Y' ");
		sb.append(" and (th.dispute_id is NULL or th.dispute_id = " + sdo.getDisputeId()+ ") ");
		sb.append(" and th.payment_id is NULL ");
		sb.append(" and th.invoice_id = " + sdo.getInvoiceId()+ " ");
		sb.append(" and th.created_by != 0 ");
		sb.append(" ORDER BY th.created_timestamp DESC ");
		logger.info("Exit method searchTransactionHistorysQueryString.");
		return sb.toString();
	}
	public long getDisputeActionRequestListPageNo(SearchDisputeVO sdo){
		final String sql = " select count(1) from ("+this.searchDisputeActionRequestQueryString(sdo)+ ") dn";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getDisputeNotesListPageNo.");
		return count;
	}
	
	public long getDisputeActionRequestListPageNoByBanId(SearchDisputeVO sdo){
		final String sql = " select count(1) from ("+this.searchDisputeActionRequestQueryStringByBanId(sdo)+ ") dn";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getDisputeActionRequestListPageNoByBanId.");
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchDisputeActionRequestList(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeActionRequestList.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.searchDisputeActionRequestQueryString(sdo));
		sql.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		Session session = getSession();
		List list ;
		try {
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method searchDisputeActionRequestList.");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchDisputeActionRequestListByBanId(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeActionRequestList.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.searchDisputeActionRequestQueryStringByBanId(sdo));
		sql.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		List list;
		Session session = getSession();
		try {
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method searchDisputeActionRequestList.");
		return list;
	}
	
	/**
	 * 删除 dispute reply.
	 * @param disputeActionRequestReplyId [description]
	 */
	public void deleteDisputeReply(String disputeActionRequestReplyId){
		logger.info("Enter method deleteDisputeReply.");
		Session session=getSession();
		try{
			session.createSQLQuery("delete From dispute_action_request_reply where id = "+ disputeActionRequestReplyId).executeUpdate();
		}finally{
			releaseSession(session);
		}
				
		logger.info("Exit method deleteDisputeReply.");
	}

	/**
	 * 添加 reply item 到后台数据库中。
	 * @param disputeActionRequestReplyId [description]
	 * @param note                        [description]
	 */
	public void addReplyNote(String disputeActionRequestReplyId,String note){
		logger.info("Enter method addReplyNote.");
		Session session=getSession();
		try{
			StringBuffer addRequestSQL = new StringBuffer("insert into dispute_action_request_reply (dispute_action_request_id,notes,created_by,created_timestamp,modified_by,modified_timestamp,rec_active_flag) VALUES (");
			addRequestSQL.append(disputeActionRequestReplyId+",'");
			addRequestSQL.append(note+"',");
			addRequestSQL.append(SystemUtil.getCurrentUserId()+",");
			addRequestSQL.append("now(),");
			addRequestSQL.append(SystemUtil.getCurrentUserId()+",");
			addRequestSQL.append("now(),'Y')");
			session.createSQLQuery(addRequestSQL.toString()).executeUpdate();
		}finally{
			releaseSession(session);
		}
				
		logger.info("Exit method addReplyNote.");
	}
	
	public void updateDisputeActionRequestStatus(String disputeActionRequestId,String status){
		logger.debug("updateDisputeActionRequestStatus");
		Integer count=0;
		Session session = getSession();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" update  dispute_action_request SET  dispute_action_request_status_id ="+ status +"  ,modified_timestamp = now(), modified_by = "+ SystemUtil.getCurrentUserId()+" WHERE id = "+disputeActionRequestId+";");
			count= session.createSQLQuery(sb.toString()).executeUpdate();
			
		} catch (RuntimeException re) {
			logger.error("updateDisputeActionRequestStatus error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchDisputeNotesList(SearchDisputeVO sdo){
		logger.info("Enter method searchTransactionHistorys.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.searchDisputeNotesQueryString(sdo));
		sql.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		Session session = getSession();
		List list ;
		try {
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
		} catch (RuntimeException re) {
			logger.error("searchTransactionHistorys error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method searchTransactionHistorys.");
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchDisputeReplyList(String disputeActionRequestId) {
		logger.info("Enter method searchDisputeReplyList.");
		StringBuilder sql = new StringBuilder();
		sql.append("select darr.id,darr.dispute_action_request_id, CONCAT(if(u.first_name IS NULL, '', u.first_name),' ',if(u.last_name IS NULL, '', u.last_name)) AS name,ifnull( date_format(darr.created_timestamp, '%Y-%m-%d'),'') AS date,darr.notes from dispute_action_request_reply darr,user u ");
		sql.append(" where darr.created_by = u.id and darr.dispute_action_request_id = "+disputeActionRequestId+" and darr.rec_active_flag = 'Y' order by darr.created_timestamp desc;");
		Session session = getSession();
		List list;
		try {
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
		} catch (RuntimeException re) {
			logger.error("searchDisputeReplyList error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method searchDisputeReplyList.");
		return list;
	}
	
	public long getDisputeNotesListPageNo(SearchDisputeVO sdo){
		final String sql = " select count(1) from ("+this.searchDisputeNotesQueryString(sdo)+ ") dn";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getDisputeNotesListPageNo.");
		return count;
	}
	
	public long getDisputeActionRequestStatusById(String actionRequestStatusId){
		final String sql = "select d.dispute_action_request_status_id from dispute_action_request d where d.id = " + actionRequestStatusId + "; ";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getDisputeNotesListPageNo.");
		return count;
	}

	/**
	 * 保存Dispute Notes 信息到数据库中.
	 * @param invoiceId [description]
	 * @param disputeId [description]
	 * @param noteNotes [description]
	 */
	public void saveDisputeNote(String disputeId,String noteNotes){
		logger.info("Enter method saveDisputeNote.");
		final String sql = this.saveDisputeNoteQueryString(disputeId,noteNotes);
		Session session = getSession();
		
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setParameter("notes", noteNotes);
		    sqlQuery.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("saveDisputeNote error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method saveDisputeNote.");
	}
	
	/**
	 * 保存Dispute Notes 信息到数据库中的Sql 语句。
	 * @param  invoiceId [description]
	 * @param  disputeId [description]
	 * @param  noteNotes [description]
	 * @return           [description]
	 */
	private String saveDisputeNoteQueryString(String disputeId,String noteNotes){
		
		String notes = DaoUtil.ccmEscape4(noteNotes);
		logger.info("Enter method saveDisputeNoteQueryString.");
		StringBuffer sb = new StringBuffer(" insert into dispute_notes (invoice_id,dispute_id,notes,analyst_id,created_by,modified_by,created_timestamp,modified_timestamp,rec_active_flag) VALUES ( ");
		sb.append("(select invoice_id from dispute where id = "+disputeId+"),"+disputeId+",:notes,"+SystemUtil.getCurrentUserId()+","+SystemUtil.getCurrentUserId()+","+SystemUtil.getCurrentUserId()+",now(),now(),\"Y\")");
		logger.info("Exit method saveDisputeNoteQueryString.");
		return sb.toString();
	}
	
	
	private String searchDisputeNotesQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeNotesQueryString.");
		StringBuffer sb = new StringBuffer(" SELECT id,date,note,name  FROM");
		sb.append(" ((SELECT th.id, ifnull( date_format(th.created_timestamp, '%Y-%m-%d %H:%i:%s'),'') AS date,");
		sb.append(" toJSON(th.notes IS NULL, '', th.notes) AS note,");
		sb.append(" toJSON( th.workflow_user_id IS NULL,'',CONCAT(if(u.first_name IS NULL, '', u.first_name),' ',if(u.last_name IS NULL, '', u.last_name))) AS name");
		sb.append(" FROM  transaction_history th LEFT JOIN user u ON (th.workflow_user_id = u.id)");
		sb.append(" WHERE     th.rec_active_flag = 'Y' AND (th.dispute_id IS NULL OR th.dispute_id = " + sdo.getDisputeId()+ ") ");
		sb.append(" AND th.payment_id IS NULL AND th.invoice_id = " + sdo.getInvoiceId()+ " AND th.created_by != 0)");
		sb.append(" UNION ALL");
		sb.append(" (SELECT t.id, ifnull(date_format(t.created_timestamp, '%Y-%m-%d %H:%i:%s'), '') AS date,");
		sb.append(" notes AS note, concat(u.first_name, ' ', u.last_name) AS name FROM dispute_notes t LEFT JOIN user u ON u.id = t.analyst_id ");
		sb.append(" WHERE     t.rec_active_flag = 'Y' AND (t.dispute_id IS NULL OR t.dispute_id = " + sdo.getDisputeId()+ ") ");
		sb.append(" AND t.invoice_id = " + sdo.getInvoiceId()+ ")) aa  ");
		if(sdo.getFilter()!=null){
			sb.append(" where " + sdo.getFilter());
		}	
		if(sdo.getSortField()!=null){
			sb.append(sdo.getOrderByCause(null));
		}else{
			sb.append("order by date desc");
		}
		
		
		
		logger.info("Exit method searchDisputeNotesQueryString.");
		return sb.toString();
	}

	/**
	 * 将 Dispute Action Request 保存到数据库中。
	 */
	public void saveDisputeActionRequest(String actionRequestNotes, String disputeId ,String disputeActionRequestStatus) {
		logger.info("Enter method saveDisputeActionRequest.");
		
		if (disputeActionRequestStatus == null || "".equals(disputeActionRequestStatus)){
			disputeActionRequestStatus = "1";
		}

		StringBuffer sb = new StringBuffer();
		sb.append(" insert into dispute_action_request ");
		sb.append(" (dispute_id, dispute_action_request_status_id, notes, created_by,modified_by, ");
		sb.append(" created_timestamp,modified_timestamp, rec_active_flag ) values (");

		sb.append( disputeId + ", "+disputeActionRequestStatus+",:notes," + SystemUtil.getCurrentUserId() + ","+SystemUtil.getCurrentUserId()+", now(), now(), \"Y\")");


		final String sql = sb.toString();

        Session session = getSession();
		
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.setParameter("notes", actionRequestNotes);
		    sqlQuery.executeUpdate();
		} catch (RuntimeException re) {
			logger.error("saveDisputeActionRequest error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method saveDisputeActionRequest.");
	}
	
	/**
	 * 查询 Dispute Action Request 的SQL语句。
	 * @param  sdo [description]
	 * @return     [description]
	 */
	private String searchDisputeActionRequestQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeActionRequestQueryString.");
		StringBuffer sb = new StringBuffer(" SELECT dar.id as id ,concat(ifnull(u.first_name,''),' ',ifnull(u.last_name,'')) AS name,");
		sb.append(" IFNULL( date_format(dar.created_timestamp, '%Y-%m-%d %H:%i:%s'),'') AS createdTimestamp,");
		sb.append(" dars.dispute_action_request_status AS status,");
		sb.append(" IFNULL(dar.notes,'') AS notes,");
		sb.append(" (SELECT COUNT(1) FROM dispute_action_request_reply darr WHERE darr.dispute_action_request_id = dar.id AND darr.rec_active_flag = 'Y') AS replyCount,ifnull(dar.created_by,'') AS userId");

		sb.append(" FROM dispute_action_request dar LEFT JOIN user u ON u.id = dar.created_by");
		sb.append(" LEFT JOIN dispute_action_request_status dars ON dar.dispute_action_request_status_id = dars.id ");

		sb.append(" WHERE  dar.rec_active_flag = 'Y' AND dar.dispute_id = " + sdo.getDisputeId());
		if(sdo.getFilter()!=null){
			sb.append(" and " + sdo.getFilter());
		}	
		
		sb.append(sdo.getOrderByCause(null));
		
		
		logger.info("Exit method searchDisputeActionRequestQueryString.");
		return sb.toString();
	}
	
	/**
	 * 根据 Ban id 查询 Dispute Action Request 的SQL语句。
	 * @param  sdo [description]
	 * @return     [description]
	 */
	private String searchDisputeActionRequestQueryStringByBanId(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeActionRequestQueryStringByBanId.");
		StringBuffer sb = new StringBuffer(" SELECT dar.id as id ,concat(ifnull(u.first_name,''),' ',ifnull(u.last_name,'')) AS name,");
		sb.append(" IFNULL( date_format(dar.created_timestamp, '%Y-%m-%d %H:%i:%s'),'') AS createdTimestamp,");
		sb.append(" dars.dispute_action_request_status AS status,");
		sb.append(" IFNULL(dar.notes,'') AS notes,");
		sb.append(" IFNULL(d.notes,'') AS disputeNotes,");
		sb.append(" d.id AS disputeId,");
		sb.append(" d.dispute_number AS disputeNumber,");
		sb.append(" (SELECT COUNT(1) FROM dispute_action_request_reply darr WHERE darr.dispute_action_request_id = dar.id AND darr.rec_active_flag = 'Y') AS replyCount,ifnull(dar.created_by,'') AS userId");

		sb.append(" FROM invoice i, ban b, dispute d, dispute_action_request dar LEFT JOIN user u ON u.id = dar.created_by");
		sb.append(" LEFT JOIN dispute_action_request_status dars ON dar.dispute_action_request_status_id = dars.id ");

		sb.append(" WHERE  dar.dispute_id = d.id AND d.invoice_id = i.id AND i.ban_id = b.id AND dar.rec_active_flag = 'Y' AND b.id = " + sdo.getBanId());
		if(sdo.getFilter()!=null){
			sb.append(" and " + sdo.getFilter());
		}	
		
		sb.append(sdo.getOrderByCause(null));
		
		
		logger.info("Exit method searchDisputeActionRequestQueryStringByBanId.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> searchDisputeReconciliation(SearchDisputeVO sdo) {
		logger.info("Enter method searchDisputeReconciliation.");
		final String sql = this.searchDisputeReconciliationQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchDisputeReconciliation.");
		return l;
	}
	
	private String searchDisputeReconciliationQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeReconciliationQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',r.id,',pid:\"',if(r.dispute_proposal_id is null,'',p.id),'\",");
		sb.append("itemName:\"',toJSON(p.item_name is null,'',p.item_name),'\",");	
		sb.append("SCOAId:\"',toJSON(r.account_code_id is null,'',r.account_code_id),'\",");	
		sb.append("SCOA:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\",");	
		sb.append("circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),'\",");	
		sb.append("attachmentPointId:\"',toJSON(r.attachment_point_id is null,'',r.attachment_point_id),'\",");
		sb.append("reconcileDate:\"',toJSON(r.reconcile_date is null,'',r.reconcile_date),'\",");
		sb.append("releaseReserveAmount:\"',format(if(r.released_reserve_amount is null,'',r.released_reserve_amount),5),'\",");
		sb.append("reconcileAmount:\"',format(if(r.reconcile_amount is null,'',r.reconcile_amount),5),'\",");
		sb.append("invoiceNumber:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\",");
		sb.append("reconcileStatus:\"',toJSON(r.reconcile_status_id is null,'',rs.reconcile_status_name),'\",");
		sb.append("createdBy:\"',toJSON(r.created_by is null,'',CONCAT(if(u.first_name is null,'',u.first_name),' ',if(u.last_name is null,'',u.last_name))),'\",");
		sb.append("notes:\"',toJSON(r.notes is null,'',r.notes),'\"");
		
		sb.append("}') a ");
		
		sb.append(disputeReconciliationWhereSqlString(sdo));
		
		sb.append(sdo.getOrderByCause(null));
		//sb.append(" order by p.item_name asc, p.id asc ");
		sb.append(sdo.getLimitCause());
		
		logger.info("Exit method searchDisputeReconciliationQueryString.");
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> searchAttachmentFile(SearchDisputeVO sdo){
		logger.info("Enter method searchAttachmentFile.");
		final String sql = this.searchAttachmentFileQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchAttachmentFile.");
		return l;
	}
	
	private String searchAttachmentFileQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchAttachmentFileQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',af.id,',");
		sb.append("fileName:\"',if(af.file_name is null,'',af.file_name),'\",");	
		sb.append("filePath:\"',if(af.file_path is null,'',af.file_path),'\"");
		sb.append("}') a ");
		
		sb.append(disputeAttachmentPointWhereSqlString(sdo.getAttachmentPointId()));
		sb.append(sdo.getOrderByCause(null));
		sb.append(sdo.getLimitCause());
		logger.info("Exit method searchAttachmentFileQueryString.");
		return sb.toString();
	}
	
	public long getNumberAccessories(String attachmentPointId){
		logger.info("Enter method getNumberAccessories.");
		final String sql = this.getNumberAccessoriesQueryString(attachmentPointId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberAccessories.");
		return count;
	}
	
	private String getNumberAccessoriesQueryString(String attachmentPointId){
		logger.info("Enter method getNumberAccessoriesQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(disputeAttachmentPointWhereSqlString(attachmentPointId));
		logger.info("Exit method getNumberAccessoriesQueryString.");
		return sb.toString();
	}
	
	private String disputeAttachmentPointWhereSqlString(String attachmentPointId){
		logger.info("Exit method disputeAttachmentPointWhereSqlString.");
		StringBuffer sb = new StringBuffer("");
		sb.append(" from attachment_file af ");
		sb.append(" where af.rec_active_flag = 'Y' ");
		if(attachmentPointId != null && (!"".equals(attachmentPointId))) {
			sb.append(" and af.attachment_point_id = " + attachmentPointId + " ");
		}
		logger.info("Exit method disputeAttachmentPointWhereSqlString.");
		return sb.toString();
	}
	
	public long getNumberOfReconciliations(SearchDisputeVO sdo) {
		logger.info("Enter method getNumberOfReconciliations.");
		final String sql = this.getNumberOfReconciliationsQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfReconciliations.");
		return count;
	}
	
	private String getNumberOfReconciliationsQueryString(SearchDisputeVO sdo){
		logger.info("Enter method getNumberOfReconciliationsQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(disputeReconciliationWhereSqlString(sdo));
		logger.info("Exit method getNumberOfReconciliationsQueryString.");
		return sb.toString();
	}
	
	private String disputeReconciliationWhereSqlString(SearchDisputeVO sdo){
		logger.info("Exit method disputeReconciliationWhereSqlString.");
		StringBuffer sb = new StringBuffer("");
		sb.append(" from ((((reconcile r left join proposal p on (r.dispute_proposal_id = p.id and p.rec_active_flag = 'Y') ");
		sb.append(" left join invoice i on (r.credit_invoice_id = i.id)) ");
		sb.append(" left join reconcile_status rs on (r.reconcile_status_id = rs.id)) ");
		sb.append(" left join user u on (r.created_by = u.id)) ");
		sb.append(" left join account_code ac on (ac.rec_active_flag = 'Y' and r.account_code_id = ac.id)) ");
		sb.append(" where r.rec_active_flag = 'Y' ");
		sb.append(" and r.dispute_id = " + sdo.getDisputeId()+ " ");
		
		logger.info("Exit method disputeReconciliationWhereSqlString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchTransactionByObject(SearchDisputeVO sdo){
		logger.info("Enter method searchTransactionByObject.");
		final String sql = this.searchTransactionByObjectQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchTransactionByObject.");
		return l;
	}
	
	private String searchTransactionByObjectQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchTransactionByObjectQueryString.");
		StringBuffer sb = new StringBuffer("select if(th.workflow_action_id is null,'',wa.workflow_action_name),");
		sb.append("if(th.workflow_user_id is null,'',CONCAT(if(u.first_name is null,'',u.first_name),' ',if(u.last_name is null,'',u.last_name))),");
		sb.append("if(th.dispute_status_id is null,'',ds.dispute_status_name),");	
		sb.append("if(th.invoice_status_id is null,'',s.invoice_status_name),");
		sb.append("format(if(th.dispute_amount is null,'',th.dispute_amount),2),");
		sb.append("if(th.notes is null,'',th.notes),");
		sb.append("if(th.created_timestamp is null,'',th.created_timestamp) ");
		sb.append(" from ((((transaction_history th left join invoice_status s on (th.invoice_status_id = s.id)) ");
		sb.append(" left join dispute_status ds on (th.dispute_status_id = ds.id)) ");
		sb.append(" left join workflow_action wa on (th.workflow_action_id = wa.id)) ");
		sb.append(" left join user u on (th.workflow_user_id = u.id)) ");
		sb.append(" where th.rec_active_flag = 'Y' ");
		sb.append(" and (th.dispute_id is NULL or th.dispute_id = " + sdo.getDisputeId()+ ") ");
		sb.append(" and th.payment_id is NULL ");
		sb.append(" and th.invoice_id = " + sdo.getInvoiceId()+ " ");
		sb.append(" and th.created_by != 0 ");
		sb.append(" ORDER BY th.created_timestamp DESC ");
		logger.info("Exit method searchTransactionByObjectQueryString.");
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchDisputeItemByObject(SearchDisputeVO sdo) {
		logger.info("Enter method searchDisputeItemByObject.");
		final String sql = this.searchDisputeItemByObjectQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchDisputeItemByObject.");
		return l;
	}
	
	private String searchDisputeItemByObjectQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeItemByObjectQueryString.");
		StringBuffer sb = new StringBuffer("select format(if(p.dispute_amount is null,0,p.dispute_amount),5),");
		sb.append("format(if(p.balance_amount is null,0,p.balance_amount),5),");
		sb.append("if(p.item_name is null,'',p.item_name),");
		sb.append("if(p.account_code_id is null,'',ac.account_code_name),");	
		sb.append("if(p.circuit_number is null,'',p.circuit_number),");
		sb.append("if(p.billing_number is null,'',p.billing_number),");
		sb.append("if(p.description is null,'',p.description),");
		sb.append("if(p.address is null,'',p.address),");
		sb.append("if(p.service_type is null,'',p.service_type),");
		sb.append("if(p.charge_type is null,'',p.charge_type),");
		sb.append("if(p.usoc is null,'',p.usoc),");
		sb.append("if(p.usoc_description is null,'',p.usoc_description),");
		sb.append("if(p.quantity is null,'',p.quantity),");
		sb.append("if(p.rate is null,'',p.rate),");
		sb.append("if(p.minutes is null,'',p.minutes),");
		sb.append("if(p.number_of_calls is null,'',p.number_of_calls),");
		sb.append("if(p.cellular_indicator is null,'',p.cellular_indicator),");
		sb.append("if(p.country is null,'',p.country),");
		sb.append("if(p.mileage is null,'',p.mileage),");
		sb.append("if(p.asg is null,'',p.asg),");
		sb.append("if(p.jurisdiction is null,'',p.jurisdiction),");
		sb.append("if(p.direction is null,'',p.direction) ");
		
		sb.append(disputeItemWhereSqlString(sdo));
		
		sb.append(sdo.getOrderByCause(null));
		//sb.append(" order by p.item_name asc, p.id asc ");
		logger.info("Exit method searchDisputeItemByObjectQueryString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> searchDisputeItem(SearchDisputeVO sdo) {
		logger.info("Enter method searchDisputeItem.");
		final String sql = this.searchDisputeItemQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchDisputeItem.");
		return l;
	}
	
	private String searchDisputeItemQueryString(SearchDisputeVO sdo){
		logger.info("Enter method searchDisputeItemQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,',");
		sb.append("disputeAmount:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),'\",");
		sb.append("balanceAmount:\"',format(if(p.balance_amount is null,0,p.balance_amount),2),'\",");
		sb.append("itemName:\"',toJSON(p.item_name is null,'',p.item_name),'\",");	
		sb.append("SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),'\",");	
		sb.append("SCOA:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\",");	
		sb.append("circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),'\",");
		sb.append("billingNumber:\"',toJSON(p.billing_number is null,'',p.billing_number),'\",");
		sb.append("description:\"',toJSON(p.description is null,'',p.description),'\",");
		sb.append("address:\"',toJSON(p.address is null,'',p.address),'\",");
		sb.append("serviceType:\"',toJSON(p.service_type is null,'',p.service_type),'\",");
		sb.append("chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),'\",");
		sb.append("usoc:\"',toJSON(p.usoc is null,'',p.usoc),'\",");
		sb.append("usocDescription:\"',toJSON(p.usoc_description is null,'',p.usoc_description),'\",");
		sb.append("quantity:\"',toJSON(p.quantity is null,'',p.quantity),'\",");
		sb.append("rate:\"',toJSON(p.rate is null,'',p.rate),'\",");
		sb.append("minutes:\"',toJSON(p.minutes is null,'',p.minutes),'\",");
		sb.append("numberCalls:\"',toJSON(p.number_of_calls is null,'',p.number_of_calls),'\",");
		sb.append("cellularIndicator:\"',toJSON(p.cellular_indicator is null,'',p.cellular_indicator),'\",");
		sb.append("country:\"',toJSON(p.country is null,'',p.country),'\",");
		sb.append("mileage:\"',toJSON(p.mileage is null,'',p.mileage),'\",");
		sb.append("asg:\"',toJSON(p.asg is null,'',p.asg),'\",");
		sb.append("accountCodeId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),'\",");
		sb.append("jurisdiction:\"',toJSON(p.jurisdiction is null,'',p.jurisdiction),'\",");
		sb.append("direction:\"',toJSON(p.direction is null,'',p.direction),'\"");
		sb.append("}') a ");
		
		sb.append(disputeItemWhereSqlString(sdo));
		
		sb.append(sdo.getOrderByCause(null));
		//sb.append(" order by p.item_name asc, p.id asc ");
		sb.append(sdo.getLimitCause());
		
		logger.info("Exit method searchDisputeItemQueryString.");
		return sb.toString();
	}
	
	public long getNumberOfDisputeItems(SearchDisputeVO sdo) {
		logger.info("Enter method getNumberOfDisputeItems.");
		final String sql = this.getNumberOfDisputeItemsQueryString(sdo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfDisputeItems.");
		return count;
	}
	
	private String getNumberOfDisputeItemsQueryString(SearchDisputeVO sdo){
		logger.info("Enter method getNumberOfDisputeItemsQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(disputeItemWhereSqlString(sdo));
		logger.info("Exit method getNumberOfDisputeItemsQueryString.");
		return sb.toString();
	}
	
	private String disputeItemWhereSqlString(SearchDisputeVO sdo){
		logger.info("Exit method disputeItemWhereSqlString.");
		StringBuffer sb = new StringBuffer("");
		sb.append(" from dispute d, proposal p left join account_code ac ");
//		sb.append(" on (ac.rec_active_flag = 'Y' and ac.active_flag = 'Y' and p.account_code_id = ac.id) ");
		sb.append(" on (	p.account_code_id = ac.id " +
				"		and (case when (select count(1) from invoice where id = p.invoice_id and invoice_status_id < 21) = 1 " +
								 "then ac.rec_active_flag = 'Y' and ac.active_flag = 'Y' " +
								 "else 1 = 1 end))");
		sb.append(" where p.rec_active_flag = 'Y' ");
		sb.append(" and d.id = p.dispute_id ");
		sb.append(" and p.dispute_id = " + sdo.getDisputeId()+ " ");
		sb.append(" and p.dispute_amount <> 0 ");
		sb.append(" and p.dispute_amount <> 0 ");
		sb.append(" and d.dispute_status_id = 30 ");
		sb.append(" and p.balance_amount <> 0 ");
		sb.append(" and p.proposal_flag = 1 ");
		logger.info("Exit method disputeItemWhereSqlString.");
		return sb.toString();
	}
	
	public String searchDisputeEmailAddress(Integer contactId) {
		logger.info("Enter method searchDisputeMessage.");
		Session session = getSession();
		String sql = "select email from contact where id = " + contactId;
		List list;
		try {
			list = session.createSQLQuery(sql).list();
		} catch (RuntimeException re) {
			logger.error("searchDisputeMessage error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method searchDisputeMessage.");
		return (list != null && list.size() > 0) ? list.get(0).toString() : "";
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchDisputeMessage(String disputeId) {
		logger.info("Enter method searchDisputeMessage.");
		Session session = getSession();
		String sql = "select d.dispute_id" +
				",if((SELECT count(1) " +
				"       FROM sys_config s " +
				"      WHERE s.parameter = 'forword_dispute_bcc_email' " +
				"        AND s.value = trim(subString_index(subString_index(d.from_address,'(', -1),')',1))) = 1 " +
				"     , null " +
				"     , d.attachment_point_id) attachment_point_id" +
				",d.from_address" +
				",d.to_address" +
				",d.cc_address" +
				",d.bcc_address" +
				",d.reply_to" +
				",d.subject" +
				",d.content" +
				",replace(d.content,'\"','\\\\\\\"')  rp_content" +
				",date_format(d.sent_datetime, '%Y-%m-%d %H:%i:%s') sent_datetime " +
				"from dispute_message d " +
				"where d.rec_active_flag = 'Y' and d.dispute_id = " + disputeId+ " order by sent_datetime desc";
		List list;
		try {
			list = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} catch (RuntimeException re) {
			logger.error("searchDisputeMessage error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method searchDisputeMessage.");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchDisputeAttachmentFile(String attachmentPointId) {
		logger.info("Enter method searchDisputeAttachmentFile.");
		Session session = getSession();
		String sql = "SELECT a.id, a.file_name, a.file_path, a.created_timestamp FROM attachment_file a,"+
		"(SELECT file_name, max(created_timestamp) created_timestamp FROM attachment_file WHERE rec_active_flag = 'Y' AND attachment_point_id IN ("+attachmentPointId+")  GROUP BY file_name) b" +
		" WHERE a.rec_active_flag = 'Y' AND a.attachment_point_id IN ("+attachmentPointId+") AND a.file_name = b.file_name AND a.created_timestamp = b.created_timestamp";
		List list;
		try {
			list = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} catch (RuntimeException re) {
			logger.error("searchDisputeMessage error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method searchDisputeAttachmentFile.");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public void contactVendorSaveDisputeMessage(Map<String,String> disputeMessage,Integer attachmentPointId) {
		logger.info("Enter method contactVendorSaveDisputeMessage.");
		final StringBuffer sql = new StringBuffer("insert into dispute_message " );
		sql.append("(dispute_id,attachment_point_id,from_address,to_address,cc_address,subject,content,sent_datetime,created_timestamp,created_by,rec_active_flag)");
		sql.append("VALUES ("+disputeMessage.get("disputeId")+","+ attachmentPointId + ",");
		sql.append("'"+CcmUtil.stringToSql(disputeMessage.get("From"))+"',"+"'"+CcmUtil.stringToSql(disputeMessage.get("To"))+"',"+"'"+CcmUtil.stringToSql(disputeMessage.get("CC"))+"',"+"'disputeEmail',"+"'"+CcmUtil.stringToSql(disputeMessage.get("message"))+"',");
		sql.append("now(),now()," + SystemUtil.getCurrentUserId()+",");
		sql.append("'Y'");
		sql.append(")");
		
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql.toString()).executeUpdate();
			}
		});
		logger.info("Exit method contactVendorSaveDisputeMessage.");
	}
	
	@SuppressWarnings("unchecked")
	public Dispute searchDisputeById(String disputeId) {
		logger.info("Enter method searchDisputeMessage.");
		Dispute dispute = (Dispute) getHibernateTemplate().get(
				"com.saninco.ccm.model.Dispute", Integer.valueOf(disputeId.trim()));
		return dispute;
	}
	
	private String searchDisputeMessageQueryString(String disputeId){
		logger.info("Exit method searchDisputeMessageQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{dispute_id:',d.dispute_id,',");
		sb.append("attachment_point_id:\"',d.attachment_point_id,'\",");
		sb.append("from_address:\"',toJSON(d.from_address is null,'',d.from_address),'\",");
		sb.append("to_address:\"',toJSON(d.to_address is null,'',d.to_address),'\",");
		sb.append("cc_address:\"',toJSON(d.cc_address is null,'',d.cc_address),'\",");
		sb.append("bcc_address:\"',toJSON(d.bcc_address is null,'',d.bcc_address),'\",");
		sb.append("reply_to:\"',toJSON(d.reply_to is null,'',d.reply_to),'\",");
		sb.append("subject:\"',toJSON(d.subject is null,'',d.subject),'\",");
		sb.append("content:\"',toJSON(d.content is null,'',d.content),'\",");
		sb.append("sent_datetime:\"',toJSON(d.sent_datetime is null,'',d.sent_datetime),");
		sb.append("'\"}') a ");
		sb.append(" from dispute_message d ");
		sb.append(" where d.rec_active_flag = 'Y' ");
		sb.append(" and d.dispute_id = " + disputeId+ " ");
		sb.append(" order by sent_datetime desc; ");
		logger.info("Exit method searchDisputeMessageQueryString.");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#save(com.saninco.ccm.model.Dispute)
	 */
	public void save(Dispute transientInstance) {
		logger.debug("saving Dispute instance");
		try {
			getHibernateTemplate().save(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#delete(com.saninco.ccm.model.Dispute)
	 */
	public void delete(Dispute persistentInstance) {
		logger.debug("deleting Dispute instance");
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
	public Dispute findById(java.lang.Integer id) {
		logger.debug("getting Dispute instance with id: " + id);
		try {
			Dispute instance = (Dispute) getHibernateTemplate().get(
					"com.saninco.ccm.model.Dispute", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findById(java.lang.Integer)
	 */
	public Dispute findByInvoiceId(java.lang.Integer invoiceId) {
		logger.debug("getting Dispute instance with invoice_id: " + invoiceId);
		try {
			Dispute instance = (Dispute) getHibernateTemplate().get(
					"com.saninco.ccm.model.Dispute", invoiceId);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByExample(com.saninco.ccm.model.Dispute)
	 */
	public List findByExample(Dispute instance) {
		logger.debug("finding Dispute instance by example");
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
		logger.debug("finding Dispute instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Dispute as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByDisputeBalance(java.lang.Object)
	 */
	public List findByDisputeBalance(Object disputeBalance) {
		return findByProperty(DISPUTE_BALANCE, disputeBalance);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findByDisputeAmount(java.lang.Object)
	 */
	public List findByDisputeAmount(Object disputeAmount) {
		return findByProperty(DISPUTE_AMOUNT, disputeAmount);
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
	 * @see com.saninco.ccm.dao.IInter#findDisputeStatusById(java.lang.Integer)
	 */
/*	public DisputeAction findDisputeActionById(java.lang.Integer id) {
		try {
			DisputeAction instance = (DisputeAction) getHibernateTemplate()
					.get("com.saninco.ccm.model.DisputeAction", id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}*/
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findDisputeStatusById(java.lang.Integer)
	 */
	public DisputeStatus findDisputeStatusById(java.lang.Integer id) {
		try {
			DisputeStatus instance = (DisputeStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.DisputeStatus", id);
			return instance;
		} catch (RuntimeException re) {		
			throw re;
		}
	}

	/*
	 * save  Dispute History
	 */
//	public void saveDisputeHistory(DisputeHistory transientInstance) {
//		try {
//			getHibernateTemplate().save(transientInstance);		
//		} catch (RuntimeException re) {		
//			throw re;
//		}
//	}
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
	 * @wei.su
	 * @see com.saninco.ccm.dao.IInter#findByRecActiveFlag(java.lang.Object)
	 */
	public List findByInvoiceId(int invoiceId) {
		logger.debug("finding all Dispute findByInvoiceId");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select d.id,d.claim_number from dispute d where d.invoice_id="+ invoiceId+" and d.rec_active_flag="+"'Y'").list();
			return l;
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}
	
	/* (non-Javadoc)
	 * @wei.su
	 * @see com.saninco.ccm.dao.IInter#findByRecActiveFlag(java.lang.Object)
	 */
	public List<String> findByInvoiceIdAndReturnStringList(SearchInvoiceVO searchInvoiceVO) {
		logger.debug("finding all Dispute findByInvoiceIdAndReturnStringList");
		try {
			final StringBuffer sb = new StringBuffer("select concat('{id:',id,',");
			sb.append("claim_number:\"',if(claim_number is null,'',claim_number),'\",");
			sb.append("dispute_reason_id:\"',if(dispute_reason_id is null,'',dispute_reason_id),'\"");
			sb.append("}') a ");
			sb.append("from dispute  ");
			sb.append("where  dispute.invoice_id = "+Integer.valueOf(searchInvoiceVO.getInvoiceId())+" ");
			sb.append("and  dispute.rec_active_flag = 'Y' order by dispute.created_timestamp desc ");
			HibernateTemplate template = this.getHibernateTemplate();
			List<String> l = (List<String>)template.execute(new HibernateCallback() {
			    public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	return session.createSQLQuery(sb.toString()).list();
			    }
			});
			logger.info("Exit method searchInvoice.");
			return l;
		} catch (RuntimeException re) {
			logger.error("findByInvoiceIdAndReturnStringList", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findAll()
	 */
	public List findAll() {
		logger.debug("finding all Dispute instances");
		try {
			String queryString = "from Dispute";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#merge(com.saninco.ccm.model.Dispute)
	 */
	public Dispute merge(Dispute detachedInstance) {
		logger.debug("merging Dispute instance");
		try {
			Dispute result = (Dispute) getHibernateTemplate().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#attachDirty(com.saninco.ccm.model.Dispute)
	 */
	public void attachDirty(Dispute instance) {
		logger.debug("attaching dirty Dispute instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#attachClean(com.saninco.ccm.model.Dispute)
	 */
	public void attachClean(Dispute instance) {
		logger.debug("attaching clean Dispute instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}
	public List<Object[]> searchDisputeByObject(SearchDisputeVO sio, int userId) {
		logger.info("Enter method searchDisputeByObject.");
		final String sql = this.getDisputeSearchByObjectQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchDisputeByObject.");
		return l;
	}
	private String getDisputeSearchByObjectQueryString(SearchDisputeVO sio, int userId){
		StringBuffer sb = new StringBuffer("select ");
		sb.append("dispute_number,");
		sb.append("invoice_number,");
		sb.append("claim_number,");
		sb.append("vendor_name,");	
		sb.append("account_number,");
		sb.append("toJSON(dispute_amount is null,'0.00',format(dispute_amount,2)),");
		sb.append("toJSON(dispute_balance is null,'',format(dispute_balance,2)),");
		sb.append("toJSON(created_timestamp is null,'0.00',date_format(created_timestamp, '%Y-%m-%d')),");
		sb.append("dispute_status_name,");
		sb.append("dispute_reason_name,");
		sb.append("analyst_user,");
		sb.append("ticket_number,");
		sb.append("flag_shortpaid,");
		sb.append("flag_recurring ");
		sb.append("from search_dispute_view i ");
		sb.append("where ");
		
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
		if(sio.getIsRecurring()!=null) 
			sb.append(" and i.flag_recurring ="+sio.getIsRecurring()+" ");
		if(sio.getIsShortPaid()!=null)
			sb.append(" and i.flag_shortpaid="+sio.getIsShortPaid()+" ");
		if(sio.getDisputeReasonId()!=null)
			sb.append(" and i.dispute_reason_id="+sio.getDisputeReasonId()+" ");
		if(sio.getDisputeStatusId()!=null)
			sb.append(" and i.dispute_status_id="+sio.getDisputeStatusId()+" ");
		if(sio.getClaimNumber()!=null)
			sb.append(" and upper(i.claim_number) like '%"+sio.getClaimNumber().toUpperCase()+"%' ");
		if(sio.getTicketNumber()!=null)
			sb.append(" and upper(i.ticket_number) like '%"+sio.getTicketNumber().toUpperCase()+"%' ");
		if(sio.getAnalyst()!=null)
			sb.append(" and i.assigned_analyst_id = "+sio.getAnalyst()+" ");
		if(sio.calInvoiceDueStartsOn()!=null)
			sb.append(" and datediff(i.created_timestamp, "+sio.calInvoiceDueStartsOn()+")>=0 "); 
		if(sio.calInvoiceDueEndsOn()!=null)
			sb.append(" and datediff(i.created_timestamp, "+sio.calInvoiceDueEndsOn()+")<=0 "); 
		//add by mingyang.li 2012.03.16
		if(sio.getDisputeNumber()!=null)
			sb.append(" and upper(dispute_number) like '%"+sio.getDisputeNumber().toUpperCase()+"%' ");
		
		if(sio.getFilter()!=null){
			sb.append(" and ");
			sb.append(sio.getFilter());
		}	
		
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getDisputeSearchByObjectQueryString.");
		return sb.toString();
	}
	
	/* Code Review By Chao.Liu
	 * (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getNumberOfDispute(com.saninco.ccm.vo.SearchDisputeVO, int)
	 */
	public long getNumberOfDispute(SearchDisputeVO sio, int userId) {
		logger.info("Enter method getNumberOfDispute.");
		final String sql = this.getDisputeSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfDispute.");
		return count;
	}
	/* Code Review By Chao.Liu
	 * (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#searchDispute(com.saninco.ccm.vo.SearchDisputeVO, int)
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchDispute(SearchDisputeVO sio, int userId) {
		logger.info("Enter method searchInvoice.");
		final String sql = this.getDisputeSearchQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchInvoice.");
		return l;
	}
	private String getDisputeSearchNumberQueryString(SearchDisputeVO sio, int userId){
		logger.info("Enter method getDisputeSearchNumberQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from search_dispute_view i ");
		disputeWhereSqlString(sio, userId, sb);
		logger.info("Exit method getDisputeSearchNumberQueryString.");
		return sb.toString();
	}
	private String getDisputeSearchQueryString(SearchDisputeVO sio, int userId){
		logger.info("Enter method getDisputeSearchQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select concat('{id:',i.id,',");
		sb.append("iid:\"',toJSON(i.iid is null,'',i.iid),'\",");
		sb.append("dispute_amount:\"',toJSON(i.dispute_amount is null,'0.00',format(i.dispute_amount,2)),'\",");
		sb.append("invoice_number:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\",");
		sb.append("outstanding_dispute_amount:\"',toJSON(i.dispute_balance is null,'0.00',format(i.dispute_balance,2)),'\",");
		sb.append("ticket_number:\"',toJSON(i.ticket_number is null,'',i.ticket_number),'\",");
		sb.append("claim_number:\"',toJSON(i.claim_number is null,'',i.claim_number),'\",");
		sb.append("vendor_name:\"',toJSON(i.vendor_name is null,'',i.vendor_name),'\",");	
		sb.append("account_number:\"',toJSON(i.account_number is null,'',i.account_number),'\",");
		sb.append("dispute_number:\"',toJSON(i.dispute_number is null,'',i.dispute_number),'\",");
		sb.append("created_timestamp:\"',toJSON(i.created_timestamp is null,'',date_format(i.created_timestamp, '%Y-%m-%d')),'\",");
		sb.append("dispute_status_name:\"',toJSON(i.dispute_status_name is null,'',i.dispute_status_name),'\",");
		sb.append("dispute_reason_name:\"',toJSON(i.dispute_reason_name is null,'',i.dispute_reason_name),'\",");
		sb.append("analyst:\"',i.analyst_user,'\",");
		sb.append("flag_shortpaid:\"',toJSON(i.flag_shortpaid is null,'',i.flag_shortpaid),'\",");
		sb.append("flag_recurring:\"',toJSON(i.flag_recurring is null,'',i.flag_recurring),'\"");
		sb.append("}') a ");
		sb.append("from search_dispute_view i left join ban b on b.id=i.ban_id ");
		disputeWhereSqlString(sio, userId, sb);
		
		sb.append(sio.getOrderByCause(null));
		sb.append(sio.getLimitCause());
		logger.info("Exit method getDisputeSearchQueryString.");
		return sb.toString();
	}
	private void disputeWhereSqlString(SearchDisputeVO sio, int userId, StringBuffer sb){
		sb.append("where 1=1 ");
		if(sio.getFilter()!=null){
			sb.append(" and ");
			sb.append(sio.getFilter());
		}	
		if(sio.getVendorId()!=null) {
			sb.append(" and ");
			sb.append("i.vendor_id ="+sio.getVendorId()+" ");
		}
		if(sio.getBanId()!=null){
			sb.append(" and i.ban_id in ("+sio.getBanId()+") ");
		}else{
			sb.append(" and i.ban_id in ("+DaoUtil.getUserPreviledgedBanIdSqlString(userId)+") ");
		}
		if(sio.getIsRecurring()!=null) 
			sb.append(" and i.flag_recurring="+sio.getIsRecurring()+" ");
		if(sio.getIsShortPaid()!=null)
			sb.append(" and i.flag_shortpaid="+sio.getIsShortPaid()+" ");
		if(sio.getDisputeReasonId()!=null)
			sb.append(" and i.dispute_reason_id="+sio.getDisputeReasonId()+" ");
		if(sio.getDisputeStatusId()!=null)
			sb.append(" and i.dispute_status_id="+sio.getDisputeStatusId()+" ");
		if(sio.getClaimNumber()!=null)
			sb.append(" and upper(i.claim_number) like '%"+sio.getClaimNumber().toUpperCase()+"%' ");
		if(sio.getDisputeNumber()!=null)
			sb.append(" and upper(i.dispute_number) like '%"+sio.getDisputeNumber().toUpperCase()+"%' ");
		if(sio.getTicketNumber()!=null)
			sb.append(" and upper(i.ticket_number) like '%"+sio.getTicketNumber().toUpperCase()+"%' ");
		if(sio.getAnalyst()!=null)
			sb.append(" and i.assigned_analyst_id = "+sio.getAnalyst()+" ");
		if(sio.calInvoiceDueStartsOn()!=null)
			sb.append(" and datediff(i.created_timestamp, "+sio.calInvoiceDueStartsOn()+")>=0 "); 
		if(sio.calInvoiceDueEndsOn()!=null)
			sb.append(" and datediff(i.created_timestamp, "+sio.calInvoiceDueEndsOn()+")<=0 "); 
		
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findDisputeDetail(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<String> findDisputeDetail(int disputeId, int userId) {
		logger.info("Enter method viewDisputeDetail.");
		final String sql = this.getDisputeDetailQueryString(disputeId, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method viewDisputeDetail.");
		return l;
	}
	
	private String getDisputeDetailQueryString(int disputeId, int userId){
		logger.info("Enter method getDisputeSearchQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select concat('{id:',id,', ");
		sb.append("invoice_number:\"',if(invoice_number is null,'',invoice_number),'\",");
		sb.append("dispute_balance:\"',if(dispute_balance is null,'',concat(format(dispute_balance,2))),'\",");
		sb.append("dispute_amount:\"',if(dispute_amount is null,'',dispute_amount),'\",");
		sb.append("dispute_number:\"',if(dispute_number is null,'',dispute_number),'\",");
		sb.append("vendor_name:\"',if(vendor_name is null,'',vendor_name),'\",");	
		sb.append("account_number:\"',if(account_number is null,'',account_number),'\",");
		sb.append("created_timestamp:\"',if(created_timestamp is null,'',created_timestamp),'\",");
		sb.append("ticket_number:\"',if(ticket_number is null,'',ticket_number),'\",");
		sb.append("claim_number:\"',if(claim_number is null,'',claim_number),'\"");
		sb.append("}') a ");
		sb.append("from dispute_detail_view i ");		
		sb.append("where i.id="+disputeId);
		logger.info("Exit method getDisputeSearchQueryString.");
		return sb.toString();
	}
	
	
	public long getNumberOfReconcil(Dispute dispute, SearchVO vo) {
		logger.info("Enter method getNumberOfReconcil.");
		final String sql = this.getReconcilNumberQueryString(dispute,vo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfReconcil.");
		return count;
	}
	private String getReconcilNumberQueryString(Dispute dispute,SearchVO vo){
		logger.info("Enter method getReconcilNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from  user u join reconcile r left join credit c on r.credit_id = c.id join reconcile_status rs join account_code ac ");		
		sb.append("where  r.created_by = u.id  and rs.id = reconcile_status_id and ac.id = r.account_code_id ");
		if(vo !=null){		
			if(vo.getFilter()!=null){
				sb.append(" and ");
				sb.append(vo.getFilter());
			}	 
		}
		sb.append(" and u.system_user_flag='N' ");
		sb.append(" and r.dispute_id="+dispute.getId());
		sb.append(" and r.rec_active_flag = 'Y'");
		logger.info("Exit method getDisputeSearchQueryString.");
		return sb.toString();
	}
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findReconcileDetails(int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<String> findReconcileDetails(int disputeId, int userId,SearchVO vo) {
		logger.info("Enter method searchInvoice.");
		final String sql = this.getReconcileDetailQueryString(disputeId, userId,vo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoice.");
		return l;
	}
 

	private String getReconcileDetailQueryString(int disputeId, int userId,SearchVO vo){
		logger.info("Enter method getDisputeSearchQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select concat('{id:',r.id,',");
		sb.append("credit_number:\"',if(c.id is null,'',c.id),'\",");
		sb.append("reconcile_amount:\"',if(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),'\",");
		sb.append("r_notes:\"',if(r.notes is null,'',r.notes),'\",");		
		sb.append("c_notes:\"',if(c.notes is null,'',c.notes),'\",");		
		sb.append("reconcile_status_name:\"',if(reconcile_status_name is null,'',reconcile_status_name),'\",");		
		sb.append("created_timestamp:\"',if(r.created_timestamp is null,'',r.created_timestamp),'\",");
		sb.append("account_code_name:\"',if(account_code_name is null,'',account_code_name),'\",");
		sb.append("attachment_point_id:\"',if(r.attachment_point_id is null,'',r.attachment_point_id),'\",");
		sb.append("user_name:\"',if(u.user_name is null,'',u.user_name),'\"");	
		sb.append("}') a ");
		sb.append("from  user u join reconcile r left join credit c on r.credit_id = c.id join reconcile_status rs join account_code ac ");		
		sb.append("where  r.created_by = u.id  and rs.id = reconcile_status_id and ac.id = r.account_code_id ");
		if(vo !=null){		
			if(vo.getFilter()!=null){
				sb.append(" and ");
				sb.append(vo.getFilter());
			}	 
		}
		sb.append(" and u.system_user_flag='N' ");
		sb.append(" and r.dispute_id="+disputeId);
		sb.append(" and r.rec_active_flag = 'Y'");
		if(vo.getSortField()!=null)
			sb.append("order by "+vo.getSortField()+" ");
		if(vo.getSortingDirection()!=null) 
			sb.append(vo.getSortingDirection()+" ");
		sb.append("limit " + vo.getStartIndex());
		sb.append(","+vo.getRecPerPage()); 
		logger.info("Exit method getDisputeSearchQueryString.");
		return sb.toString();
	}
	
	public List<Object[]> searchDisputeDetailByObject(Dispute dispute,SearchVO sio, int userId) {
		logger.info("Enter method searchDisputeDetailByObject.");
		final String sql = this.getDisputeDetailByObjectQueryString(dispute,sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchDisputeDetailByObject.");
		return l;
	}
	
	public List<Object[]> searchDisputeTransctionByObject(Dispute dispute) {
		logger.info("Enter method searchDisputeDetailByObject.");
		final String sql = this.getDisputeTransactionByObjectQueryString(dispute);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchDisputeDetailByObject.");
		return l;
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getCreditForReconcileTotalAmount(com.saninco.ccm.model.Dispute)
	 */
	public Double getCreditForReconcileTotalAmount(Dispute dispute,SearchVO vo) {
		logger.info("Enter method creditForReconcileTotalAmount.");		
		StringBuffer sb = new StringBuffer("select sum(reconcile_amount) from reconcile_detail_view where ");
//		if(vo !=null){		
//			if(vo.getFilter()!=null){
//				sb.append(vo.getFilter());
//				sb.append(" and ");
//			}	
//		}
		sb.append("dispute_id="+dispute.getId());
		sb.append(" and rec_active_flag = 'Y'");
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sb.toString());
			Double totalAmount = (Double)query.uniqueResult();
			logger.info("Exit method creditForReconcileTotalAmount.");
			return totalAmount;
		} finally {
			releaseSession(session);
		}
	}	

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getNumberOfCredit()
	 */
	@SuppressWarnings("unchecked")
	public long getNumberOfCredit(Dispute dispute,SearchVO vo) {
		logger.info("Enter method getNumberOfCredit.");
		final String sql = this.getCredithNumberQueryString(dispute,vo);
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
	
	private String getCredithNumberQueryString(Dispute dispute,SearchVO vo){
		logger.info("Enter method getCredithNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from credit c where ");
		if(vo.getFilter()!=null){
				sb.append(vo.getFilter());
				sb.append(" and ");
			}	
		sb.append(" credit_balance > 0 and credit_status_id < 3");
		if(dispute.getInvoice() == null){
			sb.append(" and c.ban_id is null ");
		}else{
			sb.append(" and c.ban_id = ");
			sb.append(dispute.getInvoice().getBan().getId());
		}
		sb.append(" and rec_active_flag = 'Y' ");
		logger.info("Exit method getCredithNumberQueryString.");
		return sb.toString();
	}
	
	

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findCreditDetails(com.saninco.ccm.vo.SearchVO, int)
	 */
	@SuppressWarnings("unchecked")
	public List<String> findCreditDetails(Dispute dispute, SearchVO vo,int userId) {
		logger.info("Enter method findCreditDetails.");
		final String sql = this.getCreditDetailQueryString(dispute,vo, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method findCreditDetails.");
		return l;
	}
	
	private String getCreditDetailQueryString(Dispute dispute, SearchVO vo,int userId){
		logger.info("Enter method getCreditDetailQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select concat('{id:',id,',");
		sb.append("credit_balance:\"',if(credit_balance is null,'',format(credit_balance,2)),'\",");
		sb.append("credit_amount:\"',if(credit_amount is null,'',format(credit_amount,2)),'\",");
		sb.append("claim_number:\"',if(claim_number is null,'',claim_number),'\",");
		sb.append("notes:\"',if(notes is null,'',notes),'\",");
		sb.append("reference_number:\"',if(reference_number is null,'',reference_number),'\",");
		sb.append("circuit_number:\"',if(circuit_number is null,'',circuit_number),'\",");
		sb.append("dispute_number:\"',if(dispute_number is null,'',dispute_number),'\",");
		sb.append("dispute_category:\"',if(dispute_category is null,'',dispute_category),'\",");
		sb.append("ticket_number:\"',if(ticket_number is null,'',ticket_number),'\"");
		sb.append("}') a ");
		sb.append("from credit c where ");
		if(vo.getFilter()!=null){
				sb.append(vo.getFilter());
				sb.append(" and ");
			}	
		sb.append("credit_balance > 0 and credit_status_id < 3 and rec_active_flag = 'Y' ");	
		sb.append(" and c.ban_id = ");
		sb.append(dispute.getInvoice().getBan().getId());
		if(vo.getSortField()!=null)
			sb.append(" order by "+vo.getSortField()+" ");
		if(vo.getSortingDirection()!=null) 
			sb.append(vo.getSortingDirection()+" ");
		sb.append(" limit " + vo.getStartIndex());
		sb.append(","+vo.getRecPerPage()); 
		logger.info("Exit method getCreditDetailQueryString.");
		return sb.toString();
	}
	private String getDisputeDetailByObjectQueryString(Dispute dispute, SearchVO vo,int userId){
		logger.info("Enter method getDisputeDetailByObjectQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select ");
		sb.append("item_name,");
		sb.append("format(item_amount,2),");
		sb.append("account_code_name,");
		sb.append("dispute_amount,");
		sb.append("p.description,");
		sb.append("table_name,");
		sb.append("dispute_reason_name,");
		sb.append("originator_name ");
		sb.append("from proposal p left join attachment_point ap on p.attachment_point_id = ap.id,invoice_item i ,account_code ac , originator o ,dispute_reason dr where ");
		if(vo.getFilter()!=null){
			sb.append(vo.getFilter());
			sb.append(" and ");
		}	
		sb.append("p.invoice_item_id = i.id and p.account_code_id = ac.id and p.originator_id = o.id and p.dispute_reason_id = dr.id and p.dispute_amount != 0 and dispute_id="+dispute.getId());			
		if(vo.getSortField()!=null)
			sb.append(" order by "+vo.getSortField()+" ");
		if(vo.getSortingDirection()!=null) 
			sb.append(vo.getSortingDirection()+" ");
		logger.info("Exit method getDisputeDetailByObjectQueryString.");
		return sb.toString();
	}
	private String getDisputeTransactionByObjectQueryString(Dispute dispute){
		logger.info("Enter method getDisputeTransactionByObjectQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select ");
		sb.append("th.created_timestamp,");
		sb.append("invoice_status_name,");
		sb.append("dispute_status_name,");
		sb.append("workflow_action_name,");
		sb.append("user_name,");
		sb.append("table_name,");
		sb.append("notes ");
		sb.append("	from transaction_history th left join attachment_point ap on th.attachment_point_id = ap.id ,invoice_status ins,dispute_status ds, workflow_action wa, user u  where ");
		sb.append("	th.invoice_status_id = ins.id and th.dispute_status_id = ds.id and th.workflow_action_id = wa.id and th.workflow_user_id = u.id and");
		sb.append("(th.dispute_id is NULL or th.dispute_id="+dispute.getId()+")");				
		sb.append("	AND th.payment_id is NULL ");
		sb.append("	AND th.invoice_id = " + dispute.getInvoice().getId());
		sb.append(" and u.system_user_flag='N' ");
		sb.append("	ORDER BY th.created_timestamp DESC ");

		logger.info("Exit method getDisputeTransactionByObjectQueryString.");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getNumberOfDisputeHistory()
	 */
	@SuppressWarnings("unchecked")
	public long getNumberOfDisputeDetail(Dispute dispute,SearchVO vo) {
		logger.info("Enter method getNumberOfDisputeDetail.");
		final String sql = this.getNumberOfDisputeDetailQueryString(dispute,vo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfDisputeDetail.");
		return count;
	}
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findDisputeHistoryDetails(com.saninco.ccm.vo.SearchVO, int)
	 */
	@SuppressWarnings("unchecked")
	public List<String> findDisputeDetail(SearchVO vo ,Dispute dispute) {
		logger.info("Enter method findDisputeDetail.");
		final String sql = this.findDisputeDetailQueryString(vo, dispute);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method findDisputeDetail.");
		return l;
	}
	
	public List<String> findDisputeTransaction(Dispute dispute) {
		logger.info("Enter method findDisputeTransaction.");
		final String sql = this.findDisputeTransactionQueryString(dispute);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method findDisputeTransaction.");
		return l;
	}
	
	private String getNumberOfDisputeDetailQueryString(Dispute dispute,SearchVO vo){
		logger.info("Enter method getNumberOfDisputeDetailQueryString.");
		
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from proposal p left join attachment_point ap on p.attachment_point_id = ap.id,invoice_item i ,account_code ac , originator o ,dispute_reason dr where ");
		if(vo.getFilter()!=null){
			sb.append(vo.getFilter());
			sb.append(" and ");
		}	
		sb.append("p.invoice_item_id = i.id and p.account_code_id = ac.id and p.originator_id = o.id and p.dispute_reason_id = dr.id and p.dispute_amount != 0 and dispute_id="+dispute.getId());			
		sb.append("");
		logger.info("Exit method getNumberOfDisputeDetailQueryString.");
		return sb.toString();
	}
	private String findDisputeDetailQueryString(SearchVO vo ,Dispute dispute){
		logger.info("Enter method findDisputeDetailQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,',");
		sb.append("item_name:\"',if(item_name is null,'',item_name),'\",");
		sb.append("item_amount:\"',if(item_amount is null,'',format(item_amount,2)),'\",");
		sb.append("account_code_name:\"',if(account_code_name is null,'',account_code_name),'\",");
		sb.append("dispute_amount:\"',if(dispute_amount is null,'',format(dispute_amount,2)),'\",");
		sb.append("description :\"',if(p.description  is null,'',p.description),'\",");
		sb.append("table_name:\"',if(table_name is null,'',table_name),'\",");
		sb.append("attachment_point_id:\"',if(attachment_point_id is null,'',attachment_point_id),'\",");
		sb.append("originator_name:\"',if(originator_name is null,'',originator_name),'\",");
		sb.append("dispute_reason_name:\"',if(dr.dispute_reason_name is null,'',dr.dispute_reason_name),'\"");
		sb.append("}') a ");
		
		sb.append("from proposal p left join attachment_point ap on p.attachment_point_id = ap.id,invoice_item i ,account_code ac , originator o ,dispute_reason dr where ");
		if(vo.getFilter()!=null){
			sb.append(vo.getFilter());
			sb.append(" and ");
		}	
		sb.append("p.invoice_item_id = i.id and p.account_code_id = ac.id and p.originator_id = o.id and p.dispute_reason_id = dr.id and p.dispute_amount != 0 and dispute_id="+dispute.getId());			
		if(vo.getSortField()!=null)
			sb.append(" order by "+vo.getSortField()+" ");
		if(vo.getSortingDirection()!=null) 
			sb.append(vo.getSortingDirection()+" ");
		sb.append("limit " + vo.getStartIndex());
		sb.append(","+vo.getRecPerPage()); 
		logger.info("Exit method findDisputeDetailQueryString.");
		return sb.toString();
	}
	
	private String findDisputeTransactionQueryString(Dispute dispute){
		logger.info("Enter method findDisputeTransactionQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select concat('{id:',th.id,',");
		sb.append("created_timestamp:\"',if(th.created_timestamp is null,'',th.created_timestamp),'\",");
		sb.append("invoice_status_name:\"',if(invoice_status_name is null,'',invoice_status_name),'\",");
		sb.append("dispute_status_name:\"',if(dispute_status_name is null,'',dispute_status_name),'\",");
		sb.append("workflow_action_name:\"',if(workflow_action_name is null,'',workflow_action_name),'\",");
		sb.append("user_name :\"',if(user_name is null,'',user_name),'\",");
		sb.append("table_name:\"',if(table_name is null,'',table_name),'\",");
		sb.append("attachment_point_id:\"',if(th.attachment_point_id is null,'',th.attachment_point_id),'\",");
		sb.append("notes:\"',if(notes is null,'',notes),'\"");
		sb.append("}') a ");
		sb.append("	from transaction_history th left join attachment_point ap on th.attachment_point_id = ap.id ,invoice_status ins,dispute_status ds, workflow_action wa, user u  where ");
		sb.append("	th.invoice_status_id = ins.id and th.dispute_status_id = ds.id and th.workflow_action_id = wa.id and th.workflow_user_id = u.id and");
		sb.append("(th.dispute_id is NULL or th.dispute_id="+dispute.getId()+")");				
		sb.append("	AND th.payment_id is NULL ");
		sb.append(" and u.system_user_flag='N' ");
		if(dispute.getInvoice() == null){
			sb.append("	AND th.invoice_id is null ");
		}else{
			sb.append("	AND th.invoice_id = " + dispute.getInvoice().getId());
		}
		sb.append("	ORDER BY th.created_timestamp DESC ");
		logger.info("Exit method findDisputeTransactionQueryString.");
		return sb.toString();
	}
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
	public List<Object[]> getDisputeAccountCodeList(Dispute dispute) {
		logger.info("Enter method getDisputeAccountCodeList.");
		String sql = this.getDisputeAccountCodeListQueryString(dispute);
		System.out.println(sql);
		Session session = getSession();
		try {
			List<Object[]> l = (List<Object[]>)session.createSQLQuery(sql).list();
			logger.info("Exit method getDisputeAccountCodeList.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
 
	private String getDisputeAccountCodeListQueryString(Dispute dispute){
		logger.info("Enter method getDisputeAccountCodeListQueryString.");
		/* Example of getPaymentActionQueryString (Mock-up)
		"{id:2,action:\"22\"," +"status:\"11\"}*/
		StringBuffer sb = new StringBuffer("select distinct(ac.id),ac.account_code_name ");
		sb.append("from account_code ac, proposal p ");
		sb.append("where ac.id = p.account_code_id ");
		sb.append("and p.dispute_id="+(dispute.getId())+" ");
		sb.append("and p.rec_active_flag = 'Y'");
		
		logger.info("Exit method getDisputeAccountCodeListQueryString.");
		return sb.toString();
	}
	
	private String assignmentSearchAlias = "d";
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
//		query.setInteger(0, userId);
			BigInteger count = (BigInteger)query.uniqueResult();
			logger.info("Exit method getAssignmentCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#searchDisputeAssignment(com.saninco.ccm.vo.SearchDisputeVO)
	 */
	public List<String> searchDisputeAssignment(SearchDisputeVO svo) {
		logger.info("Enter method searchDisputeAssignment.");
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
			logger.info("Exit method searchDisputeAssignment.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	private String getAssignmentCountSelectCause() {
		return "select count(1) assignmentCount from search_dispute_view " + assignmentSearchAlias;
	}
	
	private String getAssignmentSearchSelectCause(){
		logger.info("Enter method initDisputeAssignmentSearchQueryString.");
		StringBuilder sb = new StringBuilder("select concat('{id:',id,', ");
		sb.append("invoice_number:\"',if(invoice_number is null,'',invoice_number),'\",");
		sb.append("dispute_balance:\"',if(dispute_balance is null,'',dispute_balance),'\",");
		sb.append("dispute_amount:\"',if(dispute_amount is null,'',dispute_amount),'\",");
		sb.append("dispute_number:\"',if(dispute_number is null,'',dispute_number),'\",");
		sb.append("ticket_number:\"',if(ticket_number is null,'',ticket_number),'\",");
		sb.append("vendor_name:\"',if(vendor_name is null,'',vendor_name),'\",");	
		sb.append("account_number:\"',if(account_number is null,'',account_number),'\",");
		sb.append("created_timestamp:\"',if(created_timestamp is null,'',created_timestamp),'\",");
		sb.append("dispute_status_name:\"',if(dispute_status_name is null,'',dispute_status_name),'\",");
		sb.append("dispute_reason_name:\"',if(dispute_reason_name is null,'',dispute_reason_name),'\",");
		sb.append("dispute_number:\"',if(dispute_number is null,'',dispute_number),'\",");
		sb.append("claim_number:\"',if(claim_number is null,'',claim_number),'\",");
		sb.append("user_name:\"',if(user_name is null,'',user_name),'\"");
		sb.append("}') jsonObj ");
		sb.append("from search_dispute_view ");
		sb.append(assignmentSearchAlias);
		logger.info("Exit method initDisputeAssignmentSearchQueryString.");
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
	 * @see com.saninco.ccm.dao.IInter#updateDisputeBalanceRollback(com.saninco.ccm.model.Dispute)
	 */
	public void updateDisputeBalanceRollback(Dispute dispute)
	{
		getHibernateTemplate().merge(dispute);
	}

	public String findTimerManager(Integer id){
		log.debug("finding TimerManager flag: ");
		try {
			String queryString = "select run_flag from timer_manager where id = "+id+"";
			Query queryObject = getSession().createSQLQuery(queryString);
			return queryObject.uniqueResult().toString();
		} catch (RuntimeException re) {
			log.error("find TimerManager flag failed", re);
			throw re;
		}	
	}
	
	public Integer findUpdateTimerManagerProcesslist(){
		log.debug("finding TimerManager flag: ");
		try {
			String queryString = "SELECT count(1) FROM information_schema.`PROCESSLIST` WHERE info LIKE 'update timer_manager%';";
			Query queryObject = getSession().createSQLQuery(queryString);
			return Integer.valueOf(queryObject.uniqueResult().toString());
		} catch (RuntimeException re) {
			log.error("find TimerManager flag failed", re);
			throw re;
		}	
	}
	
	public void updateTimerManager(Integer id,String flag){
		log.debug("update TimerManager flag: ");
		Session session = getSession();
		try {
			
			String queryString = "update timer_manager set run_flag = '"+flag+"' ,modified_timestamp = now() where id = "+id+" and run_flag != '"+flag+"'";
			session.createSQLQuery(queryString).executeUpdate();
		} catch (RuntimeException re) {
			log.error("update TimerManager flag failed", re);
			throw re;
		}finally {
			releaseSession(session);
		}
	}
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#findPendingDisputes(com.saninco.ccm.vo.SearchDisputeVO, int)
	 */
	//DisputeService.java connection here
	@SuppressWarnings("unchecked")
	public List<String> findPendingDisputes(SearchDisputeVO sio, int userId) {
		logger.info("Enter method searchPendingDispute.");
		final String sql = this.pendingDisputeQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoice.");
		return c;
	}
	//pendingDispute connection here
	private String pendingDisputeQueryString(SearchDisputeVO sio, int userId){
		logger.info("Enter method pendingDispute.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select concat('{id:',id,', ");
		sb.append("invoice_number:\"',if(invoice_number is null,'',invoice_number),'\",");
		sb.append("dispute_balance:\"',if(dispute_balance is null,'',dispute_balance),'\",");
		sb.append("dispute_amount:\"',if(dispute_amount is null,'',dispute_amount),'\",");
		sb.append("dispute_number:\"',if(dispute_number is null,'',dispute_number),'\",");
		sb.append("ticket_number:\"',if(ticket_number is null,'',ticket_number),'\",");
		sb.append("vendor_name:\"',if(vendor_name is null,'',vendor_name),'\",");	
		sb.append("account_number:\"',if(account_number is null,'',account_number),'\",");
		sb.append("created_timestamp:\"',if(created_timestamp is null,'',created_timestamp),'\",");
		sb.append("dispute_status_name:\"',if(dispute_status_name is null,'',dispute_status_name),'\",");
		sb.append("dispute_reason_name:\"',if(dispute_reason_name is null,'',dispute_reason_name),'\",");
		sb.append("dispute_number:\"',if(dispute_number is null,'',dispute_number),'\",");
		sb.append("claim_number:\"',if(claim_number is null,'',claim_number),'\",");
		sb.append("user_name:\"',if(user_name is null,'',user_name),'\"");
		sb.append("}') a ");
		sb.append("from pending_dispute_view i ");	
		if(sio.getFilter()!=null){
			sb.append("where ");
			sb.append(sio.getFilter());
		}	
		//modified by xinyu.Liu on 2010.5.19 for CCM-217
		if(sio.getSortField()!=null)
			sb.append("order by "+sio.getSortField()+" ");
		if(sio.getSortingDirection()!=null) 
			sb.append(sio.getSortingDirection()+" ");
		
		sb.append("limit " + sio.getStartIndex());
		sb.append(","+sio.getRecPerPage()); 
		logger.info("Exit method getDisputeSearchQueryString.");
		return sb.toString();
	}
	

	//DisputeService.java connection here
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IInter#getPendingDisputesNumber(com.saninco.ccm.vo.SearchDisputeVO, int)
	 */
	public long getPendingDisputesNumber(SearchDisputeVO sio,
			int userId) {
		logger.info("Enter method getNumberOfDispute.");
		final String sql = this.getPendingDisputeSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfDispute.");
		return count;
	}
	//getPendingNumberOfDispute connection here
	private String getPendingDisputeSearchNumberQueryString(SearchDisputeVO sio, int userId){
		logger.info("Enter method getDisputeSearchNumberQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select count(1) a ");		
		
		sb.append("from pending_dispute_view i");	
		if(sio.getFilter()!=null){
			sb.append(" where ");
			sb.append(sio.getFilter());
		}	
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * */
	public long getDisputeWorkCount(WorkspaceVO wvo) {
		logger.info("Enter method getInvoiceWorkCount.");
		final String sql = getDisputeWorkCountNOString(wvo);
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
	private String getDisputeWorkCountNOString(WorkspaceVO wVO) {
		logger.info("Enter method getDisputeWorkCountNOString.");
		StringBuffer sb = new StringBuffer("select count(1) from invoice,dispute  ");
		sb.append("where dispute.flag_workspace='Y' ");
		sb.append(" and  dispute.invoice_id=invoice.id  ");
		if(wVO.getUid()==-1){
			sb.append(" and dispute.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and dispute.assignment_user_id="+wVO.getUid()+" ");
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
		logger.info("Exit method getDisputeWorkCountNOString.");
		return sb.toString();
	}
	
	/**
	 * @author pengfei.wang
	 * */
	public List<String> searchDisputeWorkCount(WorkspaceVO wvo) {
		logger.info("Enter method searchInvoiceWorkCount.");
		final String queryString = getDisputeWorkSqlString(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchInvoiceWorkCount.");
		return list;
	}
	
	public Invoice findInvoiceById(java.lang.Integer id) {
		log.debug("getting Invoice instance with id: " + id);
		try {
			Invoice instance = (Invoice) getHibernateTemplate().get(
					"com.saninco.ccm.model.Invoice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Dispute findDisputeById(java.lang.Integer id) {
		log.debug("getting Dispute instance with id: " + id);
		try {
			Dispute instance = (Dispute) getHibernateTemplate().get(
					"com.saninco.ccm.model.Dispute", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public WorkflowAction findWorkflowActionById(java.lang.Integer id) {
		log.debug("getting WorkflowAction instance with id: " + id);
		try {
			WorkflowAction workflowAction = (WorkflowAction) getHibernateTemplate().get(
					"com.saninco.ccm.model.WorkflowAction", id);
			return workflowAction;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public AttachmentPoint findAttachmentPointById(Integer id) {
		log.debug("getting AttachmentPoint instance with id: " + id);
		try {
			AttachmentPoint instance = (AttachmentPoint) getHibernateTemplate()
					.get("com.saninco.ccm.model.AttachmentPoint", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public AttachmentFile findAttachmentFileById(Integer id) {
		log.debug("getting AttachmentFile instance with id: " + id);
		try {
			AttachmentFile instance = (AttachmentFile) getHibernateTemplate()
					.get("com.saninco.ccm.model.AttachmentFile", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public User findUserById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"com.saninco.ccm.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public void saveTransactionHistory(TransactionHistory transientInstance) {
		log.debug("saving TransactionHistory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public AccountCode findAccountCodeById(Integer id) {
		log.debug("getting AccountCode instance with id: " + id);
		try {
			AccountCode instance = (AccountCode) getHibernateTemplate().get(
					"com.saninco.ccm.model.AccountCode", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Reconcile findReconcileById(Integer id) {
		log.debug("getting Reconcile instance with id: " + id);
		try {
			Reconcile instance = (Reconcile) getHibernateTemplate().get(
					"com.saninco.ccm.model.Reconcile", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	

	/**
	 * @author pengfei.wang
	 * DisputeWorkCount queryString
	 * */
	private String getDisputeWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',invoice.id,'\", ");
		sb.append("invoice_number:\"',toJSON(invoice.invoice_number is null,'',invoice.invoice_number),'\", ");
		sb.append("dispute_balance:\"',toJSON(dispute.dispute_balance is null,'',dispute.dispute_balance ),'\", ");
		sb.append("dispute_amount:\"',toJSON(dispute.dispute_amount is null,'',dispute.dispute_amount),'\", ");
		sb.append("dispute_number:\"',toJSON(dispute.dispute_number is null,'',dispute.dispute_number),'\", ");
		sb.append("dispute_status_name:\"',toJSON(dispute.dispute_status_name is null,'',dispute.dispute_status_name),'\" ");
		sb.append("}')  from invoice,dispute  ");
		sb.append("where dispute.flag_workspace='Y' ");
		sb.append(" and  dispute.invoice_id=invoice.id  ");
		if(wVO.getUid()==-1){
			sb.append(" and dispute.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and dispute.assignment_user_id="+wVO.getUid()+" ");
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
		logger.info("Exit method getInvoiceWorkSqlString.");
		return sb.toString();
	}
	
	/**
	 * Get Invoice Page Dispute Tab Info Totail Number [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public Integer getIDisputeTabTotalNO(SearchInvoiceVO svo){
		logger.info("Enter method getIDisputeTabTotalNO.");
		final String sql = this.getIDisputeTabTotalNOSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getIDisputeTabTotalNO.");
		return Count;
	}
	private String getIDisputeTabTotalNOSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select count(1) ");
		
		sb.append(this.searchIDisputeTabWhereString(svo));
		return sb.toString();
	}
	/**
	 * Get Invoice Page Dispute Tab Info List [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List<String> searchIDisputeTab(SearchInvoiceVO svo){
		logger.info("Enter method searchIDisputeTab.");
		final String sql = this.searchIDisputeTabSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchIDisputeTab.");
		return list;
	}
	private String searchIDisputeTabSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		
		sb.append("did:\"',d.id,'\", ");
		sb.append("description:\"', ");
		sb.append("if(d.notes is null,'',d.notes), ");
		sb.append("if(d.dispute_reason_id is null,'',d.dispute_reason_id), ");
		sb.append("if(d.dispute_number is null,'',d.dispute_number), ");
		sb.append("if(d.claim_number is null,'',d.claim_number), ");
		sb.append("if(d.ticket_number is null,'',d.ticket_number), ");
		sb.append("if(d.originator_id is null,'',d.originator_id), ");
		sb.append("if(d.flag_shortpaid is null,'',d.flag_shortpaid), ");
		sb.append("if(d.flag_recurring is null,'',d.flag_recurring), ");
		sb.append("'\", ");
		sb.append("originalAmount:\"',if(d.dispute_amount is null,'',format(d.dispute_amount, 2)),'\", ");
		sb.append("outstandingAmount:\"',if(d.dispute_balance is null,'',format(d.dispute_balance, 2)),'\" ");
		sb.append("}') ");
		sb.append(this.searchIDisputeTabWhereString(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		
		return sb.toString();
	}
	public List<String> searchIDisputeTabObject(SearchInvoiceVO svo){
		logger.info("Enter method searchIDisputeTabObject.");
		final String sql = this.searchIDisputeTabObjectSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchIDisputeTabObject.");
		return list;
	}
	private String searchIDisputeTabObjectSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select CONCAT(");
		sb.append("if(d.notes is null,'',d.notes), ");
		sb.append("if(d.dispute_reason_id is null,'',d.dispute_reason_id), ");
		sb.append("if(d.dispute_number is null,'',d.dispute_number), ");
		sb.append("if(d.claim_number is null,'',d.claim_number), ");
		sb.append("if(d.ticket_number is null,'',d.ticket_number), ");
		sb.append("if(d.originator_id is null,'',d.originator_id), ");
		sb.append("if(d.flag_shortpaid is null,'',d.flag_shortpaid), ");
		sb.append("if(d.flag_recurring is null,'',d.flag_recurring) ");
		sb.append("),  ");
		sb.append(" d.dispute_amount , ");
		sb.append(" d.dispute_balance ");
		sb.append(this.searchIDisputeTabWhereString(svo));
		
		return sb.toString();
	}
	private String searchIDisputeTabWhereString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("from `dispute` as d , `invoice` as i ");
		sb.append("where i.ban_id = "+svo.getBanId()+" ");
		sb.append("and d.invoice_id = i.id ");
		sb.append("and d.dispute_balance <>0 ");
		sb.append("and d.dispute_status_id > 22 ");
		sb.append("and d.rec_active_flag = 'Y' ");
		return sb.toString();
	}
	/**
	 * Get Dispute SCOA 
	 * @param svo
	 * @return
	 */
	public Integer getDisputeCount(SearchInvoiceVO svo){
		logger.info("Enter method getDisputeCount.");
		final String sql = this.getDisputeCountSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getDisputeCount.");
		return Count;
	}
	private String getDisputeCountSqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select count(1) from `reconcile` r , `credit` c ");
		
		sb.append("where r.dispute_id = "+svo.getDisputeId()+" ");
		sb.append("and r.credit_id = c.id ");
		sb.append("and c.account_code_id != 0 ");
		sb.append("and r.rec_active_flag = 'Y' ");
		         
		return sb.toString();
	}
	/**
	 * Get Dispute SCOA By Credit SCOA
	 * @param svo
	 * @return
	 */
	public Integer getDisputeSCOA(SearchInvoiceVO svo){
		logger.info("Enter method getDisputeSCOA.");
		final String sql = this.getDisputeSCOASqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getDisputeSCOA.");
		return Count;
	}
	private String getDisputeSCOASqlString(SearchInvoiceVO svo){
		StringBuffer sb = new StringBuffer("select c.account_code_id from `reconcile` r , `credit` c ");
		
		sb.append("where r.dispute_id = "+svo.getDisputeId()+" ");
		sb.append("and r.credit_id = c.id ");
		sb.append("and c.account_code_id != 0 ");
		sb.append("and r.rec_active_flag = 'Y' ");
		         
		return sb.toString();
	}
	
	/**
	 * @author wei.su
	 * @param searchInvoiceVO
	 * @return
	 */
	public List getDisputeTotalAmountByInvoiceId(int invoiceId)
	{
		final StringBuffer sb=new StringBuffer("SELECT format(Sum(proposal.dispute_amount),2) AS amount FROM proposal WHERE proposal.invoice_id = ");
		sb.append(""+invoiceId+" and proposal.rec_active_flag = 'Y' and proposal.proposal_flag = '1'" );
		//ArrayList<Object> l=new ArrayList<Object>();
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		return c;
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
	
	public Proposal findProposalById(java.lang.Long id) {
		log.debug("getting Proposal instance with id: " + id);
		try {
			Proposal instance = (Proposal) getHibernateTemplate().get(
					"com.saninco.ccm.model.Proposal", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/** (non-Javadoc)
	 * @author wei.su
	 */
	public int saveAndReturnDisputeId(Dispute transientInstance) {
		logger.debug("saving Dispute instance");
		int disputeId=0;
		try {
			disputeId = (Integer)getHibernateTemplate().save(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
		return disputeId;
	}
	
	/** (non-Javadoc)
	 * @author wei.su
	 */
	public int getDisputeTotalNoByInvoiceId(SearchInvoiceVO searchInvoiceVO) {
		logger.debug("saving Dispute instance");
		final StringBuffer sb = new StringBuffer(
				"select count(1) from (dispute AS d LEFT JOIN dispute_reason AS dr ON d.dispute_reason_id = dr.id) LEFT JOIN originator AS o ON d.originator_id = o.id ");
		sb.append("where d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " ");
		sb.append("and d.rec_active_flag = 'Y' ");

		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sb.toString()).list();
			}
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getDisputeTotalNoByInvoiceId.");
		return Count;
	
	}
	/** (non-Javadoc)
	 * @author wei.su
	 */
	public List<String> searchDisputesByInvoiceId(SearchInvoiceVO searchInvoiceVO)
	{
		final StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',toJSON(d.id is null,'',d.id),'\", ");
		sb.append("claim_number:\"',toJSON(d.claim_number is null,'',d.claim_number),'\", ");
		sb.append("dispute_status:\"',toJSON(d.dispute_status_id is null,'',d.dispute_status_id),'\", ");
		sb.append("dispute_number:\"',toJSON(d.dispute_number is null,'',d.dispute_number),'\", ");
		// by hongtao 2011-09-19
		sb.append("ticket_number:\"',toJSON(d.ticket_number is null,'',d.ticket_number),'\", ");
		sb.append("notes:\"',toJSON(d.notes is null,'',d.notes ),'\", ");
		sb.append("reserve_amount:\"',toJSON(d.reserve_amount is null,'',format(d.reserve_amount,2) ),'\", ");
		sb.append("confidence_level:\"',toJSON(d.confidence_level is null,'',d.confidence_level ),'\", ");
		sb.append("dispute_amount:\"',toJSON(d.dispute_amount is null,'',format(d.dispute_amount,2)),'\", ");
		sb.append("flag_shortpaid:\"',toJSON(d.flag_shortpaid is null,'',d.flag_shortpaid),'\", ");
		sb.append("flag_recurring:\"',toJSON(d.flag_recurring is null,'',d.flag_recurring),'\", ");
		sb.append("email_copy_address:\"',toJSON(d.email_copy_address is null,'',d.email_copy_address),'\", ");
		sb.append("dispute_reason_name:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),'\", ");
		sb.append("originator_id:\"',toJSON(o.id is null,'',o.id),'\", ");
		sb.append("originator_name:\"',toJSON(o.originator_name is null,'',o.originator_name),'\", ");
		sb.append("email_flag:\"',toJSON(d.email_flag is null,'',d.email_flag),'\" ");
		sb.append("}')  from (dispute AS d LEFT JOIN dispute_reason AS dr ON d.dispute_reason_id = dr.id) LEFT JOIN originator AS o ON d.originator_id = o.id   ");
		sb.append("where d.rec_active_flag='Y' ");
		sb.append(" and  d.invoice_id="+searchInvoiceVO.getInvoiceId()+"  ");
		sb.append(" order by " + searchInvoiceVO.getSortField() + " " + searchInvoiceVO.getSortingDirection());
		sb.append(" LIMIT " + searchInvoiceVO.getStartIndex() + "," + searchInvoiceVO.getRecPerPage());
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method searchDisputesByInvoiceId.");
		return list;
		
	}
	/***
	 * @author wei.su
	 */
	public List<Dispute> GetTheDisputesInFoToSendEmail(){
		logger.debug("GetTheDisputesInFoToSendEmail");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from Dispute as d where (d.disputeStatus.id=20 or d.disputeStatus.id=24) and d.recActiveFlag='Y' and d.emailFlag='Y'");
			List<Dispute> dl = (List<Dispute>)getHibernateTemplate().find(sb.toString());
			return dl;
		} catch (RuntimeException re) {
			logger.error("find GetTheDisputesInFoToSendEmail error", re);
			throw re;
		}
	}

	/***
	 * @author wei.su
	 */
	public Integer AfterSendEmailChangeDisputeStatus(){
		logger.debug("AfterSendEmailChangeDisputeStatus");
		Integer count=0;
		Session session = getSession();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("update dispute d set d.dispute_status_id=if(d.dispute_status_id=20,21,25),d.modified_timestamp = now(), d.modified_by = "+ SystemUtil.getCurrentUserId()+" where d.rec_active_flag='Y' and (d.dispute_status_id=20 or d.dispute_status_id=24)");
			count= session.createSQLQuery(sb.toString()).executeUpdate();
			
		} catch (RuntimeException re) {
			logger.error("find AfterSendEmailChangeDisputeStatus error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
		return count;
	}
	
	public WorkflowAction loadWorkflowActionById(int id) {
		return (WorkflowAction) getHibernateTemplate().load(WorkflowAction.class, id);
	}
	
	public Integer getAnnexsTotalOfDisputeByDisputeId(SearchInvoiceVO svo)
	{
		logger.info("Enter method getAnnexsTotalOfDisputeByDisputeId.");
		final StringBuffer sb = new StringBuffer("select count(*)");
		sb.append(" from attachment_file ,dispute ");
		sb.append("where  attachment_file.attachment_point_id = dispute.attachment_point_id and dispute.id = ");
		sb.append(""+("".equals(svo.getDisputeId())?"''":svo.getDisputeId())+" and attachment_file.rec_active_flag = 'Y' " );
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getAnnexsTotalOfDisputeByDisputeId.");
		return Count;	
	}
	@SuppressWarnings("unchecked")
	public List searchAnnexsOfDisputeByDisputeId(SearchInvoiceVO svo)
	{
		logger.info("Enter method searchAnnexsOfDisputeByDisputeId.");
		final StringBuffer sb = new StringBuffer("select concat('{file_path:\"',if(attachment_file.file_path is null,'',attachment_file.file_path),'\",file_name:\"',if(attachment_file.file_name is null,'',attachment_file.file_name),'\",id:\"',if(attachment_file.id is null,'',attachment_file.id),'\",attachment_point_id:\"',if(attachment_file.attachment_point_id is null,'',attachment_file.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(" from attachment_file ,dispute ");
		sb.append("where   attachment_file.attachment_point_id = dispute.attachment_point_id and dispute.id=");
		sb.append(""+svo.getDisputeId()+" and attachment_file.rec_active_flag = 'Y' ");
		if(svo.getFilter()!=null){
			sb.append(" and ");
			sb.append(svo.getFilter());
		}	
		sb.append(" order by " + svo.getSortField() + " " + svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method searchAnnexsOfDisputeByDisputeId.");
		return list;	
	}
// ************************************************************************************************************************

	public DisputeStatus loadDisputeStatusById(int id) {
		return (DisputeStatus) getHibernateTemplate().load(DisputeStatus.class, id);
	}

	public void updateRefileDisputeByProposal(String ids) {
		logger.info("Enter method updateRefileDisputeByProposal");
		String d = CcmFormat.formatDateTime(new Date());
		Session session = getSession();
		try {
			session.createSQLQuery("update dispute set dispute_status_id=34,status_timestamp='"+d+"',modified_timestamp='"+d+"',modified_by="+SystemUtil.getCurrentUserId()+" where dispute_status_id=30 and id in(select p.dispute_id from proposal p where p.id in("+ids+"))").executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateRefileDisputeByProposal.");
	}

	public double getBalanceAmountByProposal(Integer id) {
		Session session = getSession();
		try {
			Query q = session.createSQLQuery("select SUM(p.balance_amount) from proposal p where p.rec_active_flag='Y' and p.dispute_id="+id);
			return (Double)q.uniqueResult();
		} finally {
			releaseSession(session);
		}
	}

	public Double getOutstandingReserveAmount(String disputeId) {
		Session session = getSession();
		try {
			Query q = session.createSQLQuery("select d.outstanding_reserve_amount from dispute d where d.id="+disputeId);
			Double r = (Double)q.uniqueResult();
			return r;
		} finally {
			releaseSession(session);
		}
	}

	public void updateDisputeAmountByProposal(String disputeId) {
		logger.info("Enter method updateDisputeAmountByProposal.");
		final StringBuffer sb = new StringBuffer("update dispute d set ");
		sb.append(" d.modified_timestamp = now() ");
		sb.append(" ,d.modified_by = '" + SystemUtil.getCurrentUserId() + "' ");
		sb.append(" ,d.dispute_amount = (select sum(if(p.dispute_amount is null,0,p.dispute_amount)) ");
		sb.append(" from proposal p where p.dispute_id = " + disputeId + " and p.rec_active_flag = 'Y') ");
		sb.append(" where d.id = " + disputeId + " ");
		Session session = getSession();
		
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateDisputeAmountByProposal.");
	}

	public Double getDisputeAmountByProposal(int disputeId) {
		logger.info("Enter method getDisputeAmountByProposal.");
		final StringBuffer sb = new StringBuffer();
		sb.append("select sum(if(p.dispute_amount is null,0,p.dispute_amount)) ");
		sb.append(" from proposal p where p.dispute_id = " + disputeId + " and p.rec_active_flag = 'Y' ");
		Session session = getSession();
		try {
			Double r = (Double)session.createSQLQuery(sb.toString()).uniqueResult();
			logger.info("Exit method getDisputeAmountByProposal.");
			return r;
		} finally {
			releaseSession(session);
		}
	}

	public Double getReconcileByDispute1(String disputeId) {
		Session session = getSession();
		try {
			Query q = session.createSQLQuery("select sum(r.reconcile_amount) from reconcile r,invoice i " +
			" where r.credit_invoice_id=i.id and i.invoice_status_id>40 and i.rec_active_flag = 'Y' and r.rec_active_flag = 'Y'");
			Double r = (Double)q.uniqueResult();
			return r;
		} finally {
			releaseSession(session);
		}
	}

	public Double getReconcileByDispute2(String disputeId) {
		Session session = getSession();
		try {
			Query q = session.createSQLQuery("select sum(r.reconcile_amount) from reconcile r,payment p " +
			" where r.payment_id = p.id and p.rec_active_flag = 'Y' and p.payment_status_id >= 22 and r.rec_active_flag = 'Y'");
			Double r = (Double)q.uniqueResult();
			return r;
		} finally {
			releaseSession(session);
		}
	}

	public Double getReconcileByDispute3(String disputeId) {
		Session session = getSession();
		try {
			Query q = session.createSQLQuery("select sum(r.reconcile_amount) from reconcile r where r.reconcile_status_id in (5,6,8,9) and r.rec_active_flag = 'Y'");
			Double r = (Double)q.uniqueResult();
			return r;
		} finally {
			releaseSession(session);
		}
	}

	public long getReconcileByDisputeFor3(String disputeId) {
		Session session = getSession();
		try {
			Query q = session.createSQLQuery("select count(1) from reconcile r where r.id not in (select r.id from reconcile r,payment p " +
					" where r.payment_id = p.id and r.dispute_id="+disputeId+" and p.rec_active_flag = 'Y' and p.payment_status_id >= 22 and r.rec_active_flag = 'Y')" +
					" and r.id not in (select r.id from reconcile r,invoice i " +
					" where r.credit_invoice_id=i.id and r.dispute_id="+disputeId+" and i.invoice_status_id>40 and i.rec_active_flag = 'Y' and r.rec_active_flag = 'Y')" +
					" and r.id not in (select r.id from reconcile r where r.dispute_id="+disputeId+" and r.reconcile_status_id in (5,6,8,9,10) and r.rec_active_flag = 'Y')" +
					" and r.rec_active_flag = 'Y' and r.dispute_id="+disputeId);
			BigInteger bi = (BigInteger)q.uniqueResult();
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author mingyang.li 2017-05-22
	 * Dispute Work Space quantity statistics
	 * */
	public long searchDisputeCountByDays(WorkspaceVO wVO){
		
		logger.info("Enter method searchDisputeCountByDays.");
		final String sql = searchOpenDisputeCountSql(wVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method searchDisputeCountByDays.");
		return count;
		
	}
	
	/**
	 * @author mingyang.li 2017-05-22
	 * */
	private String searchOpenDisputeCountSql(WorkspaceVO wVO) {
		logger.info("Enter method searchOpenDisputeCountSql.");
		
		StringBuffer sb = new StringBuffer("select count(1) a from ("+searchOpenDisputeSql(wVO)+") a");

		logger.info("Exit method searchOpenDisputeCountSql.");
		return sb.toString();
	}
	
	/**
	 * @author mingyang.li 2017-05-22
	 * Dispute Work Space quantity statistics
	 * */
	public List<String> searchDisputeByDays(WorkspaceVO wVO){
		
		logger.info("Enter method searchDisputeByDays.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer(searchOpenDisputeSql(wVO));
		sb.append(wVO.getLimitCause());
		try {
			List<String> r = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method searchDisputeByDays.");
			return r;
		} finally {
			releaseSession(session);
		}
		
	}
	
	public List<Object[]> searchDisputeByExcelDays(WorkspaceVO wVO,List<Integer> ids){
		
		logger.info("Enter method searchDisputeByExcelDays.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer(searchOpenDisputeSqlExcel(wVO,ids));
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		try {
			List<Object[]> r = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method searchDisputeByExcelDays.");
			return r;
		} finally {
			releaseSession(session);
		}
		
	}
	
	private int searchDsiputeLevel(Integer uid){
		Session session = getSession();
		Integer disputeLevel;
		try {
			disputeLevel = Integer.valueOf(((String)session.createSQLQuery("select ifnull(min(dispute_level),'0') from dispute_level where rec_active_flag = 'Y' and assignment_user_id = "+uid).uniqueResult())).intValue();
		} finally {
			releaseSession(session);
		}
		return disputeLevel;
	}
	private String searchOpenDisputeSqlString() {
		String sql = "select concat('{id:\"',d.id,'\",dispute_number:\"',toJSON(d.dispute_number is null,'',d.dispute_number),'\",claim_number:\"',toJSON(d.claim_number is null,'',d.claim_number),'\","+
        "vendor_name:\"',toJSON(v.vendor_name is null,'',v.vendor_name),'\","+
        "account_number:\"',toJSON(i.account_number is null,'',i.account_number),'\","+
        "invoice_number:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\","+
        "dispute_amount:\"',toJSON(d.dispute_amount is null,'0.00',format(d.dispute_amount,2)),'\","+
        "dispute_balance:\"',toJSON(d.dispute_balance is null,'0.00',format(d.dispute_balance,2)),'\","+
        "dispute_date:\"',Date(d.created_timestamp),'\","+
        "dispute_category:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),'\","+
        "dispute_flag:\"',toJSON(FN_GET_DISPUTE_MESSAGE_STATUS(d.id) IS NULL, '', FN_GET_DISPUTE_MESSAGE_STATUS(d.id)),'\","+
        "action_flag:\"',if(count(dar.id) = 0, 'N', 'Y'),'\","+
        "days_in_status:\"',IFNULL(datediff(now(), d.status_timestamp),0),'\","+
        "dispute_owner:\"',concat(u.first_name, ' ', u.last_name),'\","+
        "dispute_created_by:\"',IFNULL((SELECT concat(first_name, ' ', last_name) FROM user WHERE id = d.created_by), ''),'\","+
        "assigned_to:\"',case when datediff(now(), d.status_timestamp) < 60 then IFNULL((concat(u.first_name, ' ', u.last_name)),'')  when datediff(now(), d.status_timestamp) > 90 then IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 2 and dl.rec_active_flag = 'Y' limit 1),'') else IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 1 and dl.rec_active_flag = 'Y' limit 1),'') end   ,'\","+
        "short_paid_dispute:\"',toJSON(d.flag_shortpaid is null,'',d.flag_shortpaid),'\","+
        "recurring_dispute:\"',toJSON(d.flag_recurring is null,'',d.flag_recurring),'\"}') b ";
		
		return sql;
	}
	
	private String searchOpenDisputeSqlExcelString() {
        String sql = "select toJSON(d.dispute_number is null,'',d.dispute_number),toJSON(d.claim_number is null,'',d.claim_number),"+
		"toJSON(v.vendor_name is null,'',v.vendor_name),toJSON(i.account_number is null,'',i.account_number),toJSON(i.invoice_number is null,'',i.invoice_number),"+
		"toJSON(d.dispute_amount is null,'0.00',d.dispute_amount),toJSON(d.dispute_balance is null,'0.00',format(d.dispute_balance,2)),"+
		"toJSON(d.created_timestamp is null,'',Date(d.created_timestamp)),toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),"+
		"toJSON(datediff(now(), d.status_timestamp) is null,'',datediff(now(), d.status_timestamp)),concat(u.first_name, ' ', u.last_name),"+
		"IFNULL((SELECT concat(first_name, ' ', last_name) FROM user WHERE id = d.created_by), ''),"+
		"case when datediff(now(), d.status_timestamp) < 60 then IFNULL((concat(u.first_name, ' ', u.last_name)),'')  when datediff(now(), d.status_timestamp) > 90 then IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 2 and dl.rec_active_flag = 'Y' limit 1),'') else IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 1 and dl.rec_active_flag = 'Y' limit 1),'') end ,"+
		"toJSON(d.flag_shortpaid is null,'',d.flag_shortpaid),toJSON(d.flag_recurring is null,'',d.flag_recurring) ";
		
		return sql;
	}
	
	/**
	 * @author mingyang.li 2017-05-22
	 * */
	private String searchOpenDisputeSql(WorkspaceVO wvo) {
		logger.info("Enter method searchOpenDisputeSql.");
		
		String sql = searchOpenDisputeSqlString();
		
		String disputeList = searchDisputeSql(wvo,sql,null);
		
		logger.info("Exit method searchOpenDisputeSql.");
		return disputeList;
	}
	
	private String searchOpenDisputeSqlExcel(WorkspaceVO wvo,List<Integer> ids) {
		logger.info("Enter method searchOpenDisputeSqlExcel.");
		
		String sql = searchOpenDisputeSqlExcelString();
		
		String disputeList = searchDisputeSql(wvo,sql,ids);
		
		logger.info("Exit method searchOpenDisputeSqlExcel.");
		return disputeList;
	}
	

	private String searchDisputeSql(WorkspaceVO wvo,String sql,List<Integer> ids) {
		logger.info("Enter method searchDisputeSql.");
		
		
		StringBuffer sb = new StringBuffer(sql);                      
		sb.append(" FROM (dispute d, invoice i,ban b , vendor v, user u) LEFT JOIN dispute_reason dr ON d.dispute_reason_id = dr.id  LEFT JOIN "+
                                            " dispute_action_request dar ON  dar.dispute_id = d.id  AND dar.dispute_action_request_status_id = 1 " +
											"WHERE d.invoice_id = i.id AND b.id = i.ban_id AND b.analyst_id = u.id AND b.rec_active_flag = 'Y' " +
											"AND b.master_ban_flag = 'Y' AND b.ban_status_id = 1 AND v.id = i.vendor_id AND d.rec_active_flag = 'Y' " +
											"AND i.rec_active_flag = 'Y' AND d.dispute_status_id in (30,31,34,35,36)");
		
		if(wvo != null){
			Integer uid = SystemUtil.getCurrentUserId();
			if(wvo.getUid()!=null && wvo.getUid() > 0){
				uid = wvo.getUid();
			}
			
			int disputeLevel = 0;
			
			disputeLevel = searchDsiputeLevel(uid);
			
			// 查询所有的dispute信息。
			if (wvo.getDisputeDay() == -1){
				if(disputeLevel == 1){
					sb.append("AND ((datediff(now(), d.status_timestamp) BETWEEN 0 AND 60 AND u.id = "+uid+" ) " +
							"or datediff(now(), d.status_timestamp) > 60) ");
				}else if(disputeLevel == 2){
					sb.append("AND ((datediff(now(), d.status_timestamp) BETWEEN 0 AND 90 AND u.id = "+uid+" ) " +
					"or datediff(now(), d.status_timestamp) > 90) ");
				}else{
					sb.append("AND u.id = "+uid);
				}
			}
			
			// 查询前30天的dispute信息。
			if (wvo.getDisputeDay() == 30){
				sb.append("AND datediff(now(), d.status_timestamp) BETWEEN 0 AND 30 ");
				sb.append("AND u.id = "+uid);
			}

			// 查询31-60天的dispute信息。
			if (wvo.getDisputeDay() == 60){
				sb.append("AND datediff(now(), d.status_timestamp) BETWEEN 31 AND 60 ");
				sb.append("AND u.id = "+uid);
			}

			// 查询前61-90天的dispute信息。
			if (wvo.getDisputeDay() == 90){
				sb.append("AND datediff(now(), d.status_timestamp) BETWEEN 61 AND 90 ");
				
				if(disputeLevel == 0 || disputeLevel == 2 ){
					sb.append("AND u.id = "+uid);
				}
			}

			// 查询前91-120天的dispute信息。
			if (wvo.getDisputeDay() == 120){
				sb.append("AND datediff(now(), d.status_timestamp) BETWEEN 91 AND 120 ");
				if(disputeLevel == 0){
					sb.append("AND u.id = "+uid);
				}
			}

			// 查询前121-180天的dispute信息。
			if (wvo.getDisputeDay() == 180){
				sb.append("AND datediff(now(), d.status_timestamp) BETWEEN 121 AND 180 ");
				if(disputeLevel == 0){
					sb.append("AND u.id = "+uid);
				}
			}
			if (wvo.getDisputeDay() == 0){
				sb.append("AND datediff(now(), d.status_timestamp) > 180 ");	
				if(disputeLevel == 0){
					sb.append("AND u.id = "+uid);
				}
			}
			
			if(wvo.getFilter()!=null){
				
				if(wvo.getFilter().indexOf("assigned_to")!=-1){
					sb.append(" and ");
					sb.append(wvo.getFilter().replaceAll("assigned_to", "(case when datediff(now(), d.status_timestamp) < 60 then IFNULL((concat(u.first_name, ' ', u.last_name)),'')  when datediff(now(), d.status_timestamp) > 90 then IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 2 and dl.rec_active_flag = 'Y' limit 1),'') else IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 1 and dl.rec_active_flag = 'Y' limit 1),'') end)"));
				}else if(wvo.getFilter().indexOf("dispute_created_by")!=-1){
					
					sb.append(" and ");
					sb.append(wvo.getFilter().replaceAll("dispute_created_by", "(SELECT concat(first_name, ' ', last_name) FROM user WHERE id = d.created_by)"));
				}else{
					sb.append(" and ");
					sb.append(wvo.getFilter());
				}
				
			}
			if (ids !=null) {
				sb.append("  AND d.id in (" + ids.toString().substring(1,ids.toString().length()-1) + ") ");
			}
			if("assigned_to".equals(wvo.getSortField())) {
				sb.append(" GROUP BY d.id, d.dispute_number  ORDER BY (case when datediff(now(), d.status_timestamp) < 60 then IFNULL((concat(u.first_name, ' ', u.last_name)),'')  when datediff(now(), d.status_timestamp) > 90 then IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 2 and dl.rec_active_flag = 'Y' limit 1),'') else IFNULL(( select concat(u.first_name, ' ', u.last_name) from dispute_level dl,user u where u.id = dl.assignment_user_id and dl.dispute_level = 1 and dl.rec_active_flag = 'Y' limit 1),'') end) " +wvo.getSortingDirection()+" ");
			}else if("dispute_created_by".equals(wvo.getSortField())) {
				sb.append( " GROUP BY d.id, d.dispute_number  ORDER BY (SELECT concat(first_name, ' ', last_name) FROM user WHERE id = d.created_by) "  +wvo.getSortingDirection()+" ");
			}else{
				sb.append(" GROUP BY d.id, d.dispute_number " + wvo.getOrderByCause(null));
			}
			
		}else{
			sb.append("AND 1 = 2 ");
		}

		logger.info("Exit method searchDisputeSql.");
		return sb.toString();
	}
	
	public void updateDisputeFlagAndReplayDateByDisputeNumber(String disputeNumber){
		logger.info("Enter method updateDisputeBalanceByProposal.");
		final StringBuffer sb = new StringBuffer("update dispute d set ");
		sb.append(" d.replay_email_datetime = now() ");
		sb.append(" ,d.modified_timestamp = now() ");
		sb.append(" ,d.modified_by = 0 ");
		sb.append(" where d.dispute_number = '" + disputeNumber + "' ");
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateDisputeBalanceByProposal.");
	}
	
	public String getDisputeAccountNumber(String disputeNumber){
		logger.info("Enter method getDisputeAccountNumber.");
		final StringBuffer sb = new StringBuffer("SELECT i.account_number ");
		sb.append(" FROM dispute d, invoice i");
		sb.append(" WHERE d.invoice_id = i.id AND d.rec_active_flag = 'Y' AND d.dispute_number = '" + disputeNumber + "' ");
		Session session = getSession();
		List l;
		try {
			l = session.createSQLQuery(sb.toString()).list();
			
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method getDisputeAccountNumber.");
		return l.get(0).toString();
	}	
	
	public String getDisputeVendorName(String disputeNumber){
		logger.info("Enter method getDisputeVendorName.");
		final StringBuffer sb = new StringBuffer("SELECT v.vendor_name ");
		sb.append(" FROM dispute d, invoice i, vendor v");
		sb.append(" WHERE d.invoice_id = i.id AND v.id = i.vendor_id AND d.rec_active_flag = 'Y' AND d.dispute_number = '" + disputeNumber + "' ");
		Session session = getSession();
		List l;
		try {
			l = session.createSQLQuery(sb.toString()).list();
			
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method getDisputeVendorName.");
		return l.get(0).toString();
	}	
	
	public String getDisputeClaimNumber(String disputeNumber){
		logger.info("Enter method getDisputeClaimNumber.");
		final StringBuffer sb = new StringBuffer("SELECT d.claim_number ");
		sb.append(" FROM dispute d");
		sb.append(" WHERE d.dispute_number = '" + disputeNumber + "' ");
		Session session = getSession();
		List l;
		try {
			l = session.createSQLQuery(sb.toString()).list();
			
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method getDisputeClaimNumber.");
		return l.get(0).toString();
	}	
	
}