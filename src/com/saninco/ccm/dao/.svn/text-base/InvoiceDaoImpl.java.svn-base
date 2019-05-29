/**
 * 
 */
package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceChannel;
import com.saninco.ccm.model.InvoiceFormat;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.ProposalDAO;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 *
 * add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 * add searchInvoiceAssignment(); beijing 2010-4-16 Jian.Dong
 */
public class InvoiceDaoImpl extends HibernateDaoSupport implements IInvoiceDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final Log log = LogFactory.getLog(ProposalDAO.class);
	
	/**
	 * 
	 */
	public InvoiceDaoImpl() {
	}
	
	public void updateInvoiceProposalPointId(SearchInvoiceVO searchInvoiceVO, String inId){
		logger.info("Enter method updateInvoiceProposalPointId.");
		final String sql = this.updateInvoiceProposalPointIdQueryString(searchInvoiceVO,inId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).executeUpdate();
		    }
		});
		logger.info("Exit method updateInvoiceProposalPointId.");
	}
	
	private String updateInvoiceProposalPointIdQueryString(SearchInvoiceVO sio, String inId){
		logger.info("Enter method updateInvoiceProposalPointIdQueryString.");
		StringBuffer sb = new StringBuffer(" update proposal p set p.attachment_point_id = '" + sio.getAttachmentPointId()+ "' ");
		sb.append(" ,p.modified_timestamp = now(),p.modified_by = '" + SystemUtil.getCurrentUserId() + "'  ");
		sb.append(" where p.id in ( " + inId + " ) ");
		logger.info("Exit method updateInvoiceProposalPointIdQueryString.");
		return sb.toString();
	}
	
	public void copyInvoiceProposalPoint(String pastId, String nowId){
		logger.info("Enter method copyInvoiceProposalPoint.");
		final String sql = this.copyInvoiceProposalPointQueryString(pastId,nowId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).executeUpdate();
		    }
		});
		logger.info("Exit method copyInvoiceProposalPoint.");
	}
	
	private String copyInvoiceProposalPointQueryString(String pastId, String nowId){
		logger.info("Enter method copyInvoiceProposalPointQueryString.");
		StringBuffer sb = new StringBuffer("insert into attachment_file(attachment_point_id,file_name,file_path,created_timestamp,created_by,modified_timestamp,modified_by,rec_active_flag) ");
		sb.append(" (SELECT " + nowId + "  ");
		sb.append(" attachment_point_id,af.file_name,af.file_path,af.created_timestamp,af.created_by,af.modified_timestamp,af.modified_by,af.rec_active_flag ");
		sb.append(" from attachment_file af where af.attachment_point_id= " + pastId + " ) ");
		logger.info("Exit method copyInvoiceProposalPointQueryString.");
		return sb.toString();
	}
	
	public List<Object[]> searchInvoiceProposalPointByObject(SearchInvoiceVO sio) {
		logger.info("Enter method searchInvoiceProposalPointByObject.");
		final String sql = this.searchInvoiceProposalPointByObjectQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoiceProposalPointByObject.");
		return l;
	}
	
	private String searchInvoiceProposalPointByObjectQueryString(SearchInvoiceVO sio){
		logger.info("Enter method searchInvoiceProposalPointByObjectQueryString.");
		StringBuffer sb = new StringBuffer("select p.id,if(p.attachment_point_id is null,'',p.attachment_point_id) ");
		sb.append(" from proposal p ");
		sb.append(" where p.id in (" + sio.getBoxInId() + ") ");
		logger.info("Exit method searchInvoiceProposalPointByObjectQueryString.");
		return sb.toString();
	}

	/**
	 * Modified By Chao.Liu to 10/08/11 PM
	 * InvoiceSearch SQL statements
	 * */
	private String getInvoiceSearchQueryString(SearchInvoiceVO sio, int userId){
		logger.info("Enter method getInvoiceSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',i.id,',no:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",invoice_label:\"',ifnull((select group_concat(label_id SEPARATOR ',') from invoice_label il,label l, label_type lt WHERE il.label_id = l.id and l.label_type_id = lt.id and lt.label_type_name = 'Invoice' and il.invoice_id = i.id and il.rec_active_flag = 'Y' and l.id <> '6'),''),");
		sb.append("'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),'\",date:\"',if(i.invoice_date is null,'',i.invoice_date),'\",due:\"',if(i.invoice_due_date is null,'',i.invoice_due_date),'\",total:\"',");
		sb.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),'\",status:\"',toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
		sb.append("'\",currentDue:\"',format(if(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sb.append("'\",analyst:\"',toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("'\",originalFlag:\"',toJSON(i.original_flag is null,'N',i.original_flag),");
		sb.append("'\",paymentAmount:\"',format(if(i.payment_amount is null,'',i.payment_amount),2),");
		sb.append("'\",disputeAmount:\"',format(if(i.dispute_amount is null,'',i.dispute_amount),2),");
		sb.append("'\",miscAdjustmentAmount:\"',format(if(i.misc_adjustment_amount is null,'',i.misc_adjustment_amount),2),");
		sb.append("'\",invoiceStatus:\"',toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
		sb.append("'\",paymentTransactionNumber:\"',toJSON(p.payment_transaction_number is null,'',p.payment_transaction_number),");
		sb.append("'\",paymentStatus:\"',toJSON(ps.payment_status_name is null,'',ps.payment_status_name),");
		sb.append("'\",paymentReferenceNumber:\"',toJSON(p.payment_reference_code is null,'',p.payment_reference_code),");
		sb.append("'\",paymentDate:\"',if(p.payment_date is null,'',p.payment_date),");
		sb.append("'\",lineOfBusiness:\"',if(b.line_of_business is null,'',b.line_of_business),");
		sb.append("'\",daysInStatus:\"',if(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),'\"");
		sb.append("}') a ");
		
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.payment_status_id <>98 and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		
		sb.append(voWhereConditions(sio,userId));
		
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getInvoiceSearchQueryString.");
		return sb.toString();
	}
	
	private String getInvoiceSearchByObjectQueryString(SearchInvoiceVO sio, int userId){
		logger.info("Enter method getInvoiceSearchByObjectQueryString.");
		StringBuffer sb = new StringBuffer("select i.invoice_number,b.account_number,if(b.line_of_business is null,'',b.line_of_business),v.vendor_name,date_format(i.invoice_date,'%Y-%m-%d')," +
				"format(if(i.invoice_current_due is null,0,i.invoice_current_due),2),i.original_flag,concat(u.first_name,' ',u.last_name) ");
		sb.append(",date_format(i.invoice_due_date,'%Y-%m-%d'),format(if(i.invoice_total_due is null,0,i.invoice_total_due),2)," +
				"format(if(i.payment_amount is null,0,i.payment_amount),2),format(if(i.dispute_amount is null,0,i.dispute_amount),2)," +
				"format(if(i.misc_adjustment_amount is null,0,i.misc_adjustment_amount),2)");
		sb.append(",s.invoice_status_name,datediff(now(),i.status_timestamp),ps.payment_status_name,date_format(p.payment_date,'%Y-%m-%d')," +
				"p.payment_transaction_number,p.payment_reference_code ");
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
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
		sb.append(" and i.invoice_status_id = s.id ");
		sb.append(" and u.id = i.assigned_analyst_id ");
		sb.append(" and i.invoice_status_id >= 9 ");
		sb.append(" and i.rec_active_flag = 'Y' ");
		
		if(sio.getFilter()!=null){
			sb.append(" and ");
			sb.append(sio.getFilter());
		}	
		
		if(!sio.isHistoricalInvoice())
			sb.append(" and i.invoice_status_id != 80 ");
		if(sio.getLineOfBusiness()!=null){
			sb.append(" and b.line_of_business = '");
			sb.append(sio.getLineOfBusiness() + "'");
		}
		
		if(sio.getVendorId()!=null) {
			sb.append(" and i.vendor_id="+sio.getVendorId()+" ");
			if(sio.getBanId()!=null) sb.append(" and i.ban_id in ("+sio.getBanId()+") ");
		}else{
			if(sio.getBanId()!=null) sb.append(" and i.ban_id in ("+sio.getBanId()+") ");
		}

		if(sio.getDaysInStatus()!=null)
			sb.append(" and datediff(now(),if(i.status_timestamp is null,'',i.status_timestamp)) > "+sio.getDaysInStatus()+" ");
		
		if(sio.getInvoiceNumber()!=null)
			sb.append(" and i.invoice_number like '%"+sio.getInvoiceNumber()+"%' ");

		if(sio.getPaymentReference()!=null)
			sb.append(" and p.payment_reference_code like '%"+sio.getPaymentReference()+"%' ");
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
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.payment_status_id <>98 and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		
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
	/**
	 * @author Chao.Liu
	 * @param searchInvoiceVO
	 * @return
	 * @throws BPLException
	 */
	public List<Object[]> searchInvoiceByIName(SearchInvoiceVO sio) {
		logger.info("Enter method searchInvoiceByIName.");
		final String sql = this.searchInvoiceByINameQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoiceByIName.");
		return l;
	}
	private String searchInvoiceByINameQueryString(SearchInvoiceVO sio){
		StringBuilder sb = new StringBuilder("select CONCAT('{ ");
		sb.append("iid:\"',i.id,'\", ");
		sb.append("vid:\"',v.id,'\", ");
		sb.append("bid:\"',b.id,'\", ");
		sb.append("vendorName:\"',if(v.vendor_name is null,'',v.vendor_name),'\", ");
		sb.append("ban:\"',if(b.account_number is null,'',b.account_number),'\", ");
		sb.append("invoiceNumber:\"',if(i.invoice_number is null,'',i.invoice_number),'\", ");
		sb.append("status:\"',if(s.invoice_status_name is null,'',s.invoice_status_name),'\", ");
		sb.append("invoiceDate:\"',if(i.invoice_date is null,'',i.invoice_date),'\" ");
		sb.append("}'), ");
		sb.append("s.id ");
		sb.append("from `invoice` i, ban b, vendor v, `invoice_status` s ");
		sb.append("where i.invoice_number = '"+sio.getInvoiceNumber()+"' ");
		sb.append("and s.id = i.invoice_status_id ");
		sb.append("and b.id = i.ban_id ");
		sb.append("and v.id = i.vendor_id ");
		sb.append("and i.rec_active_flag = 'Y' ");
		sb.append("and i.invoice_status_id not in (80,98) ");
		sb.append("and v.rec_active_flag = 'Y' ");
		sb.append("and b.rec_active_flag = 'Y' ");
		sb.append("order by i.invoice_date desc;");
		return sb.toString();
	}
	
	/**
	 * @author mingyang.Li
	 * @param searchInvoiceVO
	 * @return
	 * @throws BPLException
	 */
	public List<Object[]> searchInvoiceByInvoiceNumber(SearchInvoiceVO sio) {
		logger.info("Enter method searchInvoiceByInvoiceNumber.");
		final String sql = this.searchInvoiceByInvoiceNumberQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoiceByInvoiceNumber.");
		return l;
	}
	private String searchInvoiceByInvoiceNumberQueryString(SearchInvoiceVO sio){
		StringBuilder sb = new StringBuilder("select CONCAT('{ ");
		sb.append("iid:\"',i.id,'\", ");
		sb.append("vid:\"',v.id,'\", ");
		sb.append("bid:\"',b.id,'\", ");
		sb.append("vendorName:\"',if(v.vendor_name is null,'',v.vendor_name),'\", ");
		sb.append("ban:\"',if(b.account_number is null,'',b.account_number),'\", ");
		sb.append("invoiceNumber:\"',if(i.invoice_number is null,'',i.invoice_number),'\", ");
		sb.append("invoiceDate:\"',if(i.invoice_date is null,'',i.invoice_date),'\", ");
		sb.append("invoicePreviousBalance:\"',if(i.invoice_previous_balance is null,'',format(i.invoice_previous_balance, 2)),'\", ");
		sb.append("invoicePreviousPayment:\"',if(i.invoice_previous_payment is null,'',format(i.invoice_previous_payment, 2)),'\", ");
		sb.append("invoicePreviousAdjustment:\"',if(i.invoice_previous_adjustment is null,'',format(i.invoice_previous_adjustment, 2)),'\", ");
		sb.append("invoiceBalanceForward:\"',if(i.invoice_balance_forward is null,'',format(i.invoice_balance_forward, 2)),'\", ");
		sb.append("invoiceAdjustment:\"',if(i.invoice_adjustment is null,'',format(i.invoice_adjustment, 2)),'\", ");
		sb.append("invoiceLPC:\"',if(i.invoice_late_payment_charge is null,'',format(i.invoice_late_payment_charge, 2)),'\", ");
		sb.append("invoiceCredit:\"',if(i.invoice_credit is null,'',format(i.invoice_credit, 2)),'\", ");
		sb.append("invoiceMrc:\"',if(i.invoice_mrc is null,'',format(i.invoice_mrc, 2)),'\", ");
		sb.append("invoiceOcc:\"',if(i.invoice_occ is null,'',format(i.invoice_occ, 2)),'\", ");
		sb.append("invoiceUsage:\"',if(i.invoice_usage is null,'',format(i.invoice_usage, 2)),'\", ");
		sb.append("invoiceTax:\"',if(i.invoice_tax is null,'',format(i.invoice_tax, 2)),'\", ");
		sb.append("invoiceCurrentDue:\"',if(i.invoice_current_due is null,'',format(i.invoice_current_due, 2)),'\", ");
		sb.append("invoiceTotalDue:\"',if(i.invoice_total_due is null,'',format(i.invoice_total_due, 2)),'\" ");
		sb.append("}'),i.invoice_status_id ");
		sb.append("from `invoice` i, ban b, vendor v ");
		sb.append("where i.invoice_number = '"+sio.getInvoiceNumber()+"' ");
		sb.append("and i.rec_active_flag = 'Y' ");
		sb.append("and v.rec_active_flag = 'Y' ");
		sb.append("and b.rec_active_flag = 'Y' ");
		sb.append("and b.id = i.ban_id ");
		sb.append("and v.id = i.vendor_id ");
		return sb.toString();
	}

	public List<Object[]>  findByInvoiceNumber(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findByInvoiceNumber.");
		final String sql = this.findByInvoiceNumberString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method findByInvoiceNumber.");
		return l;
	}
	
	
	
	private String findByInvoiceNumberString(SearchInvoiceVO searchInvoiceVO) {
		StringBuilder sb = new StringBuilder("SELECT ifnull(i.id,0),");
		sb.append("ifnull(p.id,0 ) AS pid, ");
		sb.append("ifnull(i.invoice_status_id,0), ");
		sb.append("ifnull(p.payment_status_id,0 ), ");
		sb.append("ifnull(i.rec_active_flag,0 ), ");
		sb.append("ifnull(b.ap_supplier_site,''), ");
		sb.append("count(DISTINCT i.id), ");
		sb.append("count(DISTINCT p.id) ");
		sb.append("FROM   `invoice` i LEFT JOIN payment p ON i.id = p.invoice_id AND p.payment_status_id not in (98 , 99) ,ban b ");
		sb.append("WHERE i.invoice_number = '"+searchInvoiceVO.getInvoiceNumber()+"' ");
		sb.append("and i.ban_id=b.id and i.rec_active_flag='Y' and p.rec_active_flag='Y'");
		if(!searchInvoiceVO.isReject()){
			sb.append("and p.payment_status_id=24");
		}
		
		return sb.toString();
	}
	
	public void paymentResend(String invoiceNumber, String site) {
		final String sql = " call sp_rejection_fix_supplier('"+invoiceNumber+"','"+site+"')";
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).executeUpdate();
		    }
		});
	}



	// Created By Chao.Liu
	public void save(Object o){
		this.getHibernateTemplate().save(o);
	} 
	public Object get(Class c,Integer id){
		return this.getHibernateTemplate().get(c, id);
	}
	public void update(Object o){
		this.getHibernateTemplate().update(o);
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
		return "select count(1) assignmentCount from search_invoice_view " + assignmentSearchAlias;
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
	 * search PastDuePageNo
	 * @param wVO
	 * @return from hongtao.pang
	 */
	public long getPastDueCount(WorkspaceVO wVO) {
		logger.info("Enter method getPastDueCount.");
		final String sql = getPastDueCountNOString(wVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getPastDueCount.");
		return count;
	}
	/**
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String getInvoiceWorkCountNOString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCountNOString.");
		StringBuffer sb = new StringBuffer("select count(1) a from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id)  ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append(" and i.invoice_status_id >=9 ");
		sb.append(" and i.invoice_status_id <>98 ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()== -1){
			sb.append(" and (i.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and (i.assignment_user_id="+wVO.getUid()+" ");
		}
		sb.append(" and i.flag_workspace='Y' )");
//		sb.append(" and i.invoice_due_date > now()");
		sb.append(" and b.rec_active_flag = 'Y' and b.master_ban_flag = 'Y' and b.ban_status_id = 1 ");
		if (wVO.getDueDay()!=null) {
			if(wVO.getDueDay()==97){
				sb.append(" and i.invoice_due_date <= adddate(date_format(now(), '%Y/%m/%d'),interval 7 day) and i.invoice_due_date >= date(date_format(now(), '%Y/%m/%d')) ");
			}else if(wVO.getDueDay()==915){
				sb.append(" and i.invoice_due_date <= adddate(date_format(now(), '%Y/%m/%d'),interval 15 day) and i.invoice_due_date >= date(date_format(now(), '%Y/%m/%d')) ");				
			}else if(wVO.getDueDay()==90){
				sb.append(" and i.invoice_due_date = date_format(now(), '%Y/%m/%d')");				
			}
			else if(wVO.getDueDay()>0 && wVO.getDueDay()<16){
				sb.append(" and i.invoice_due_date =adddate(date_format(now(), '%Y/%m/%d'),interval "+wVO.getDueDay()+" day)");			
			}
			else{	
				sb.append(" ");
            }
		}	//Add by wenbo.zhang 2015-3-18
//		if (wVO.getDueDay() == 7) sb.append(" and i.invoice_due_date < adddate(now(),interval 7 day) and i.invoice_due_date >= date(now()) ");
//		if (wVO.getDueDay() == 15) sb.append(" and i.invoice_due_date < adddate(now(),interval 15 day) and i.invoice_due_date >= date(now()) ");
//		if (wVO.getDueDay() == 7) sb.append(" and Datediff(i.invoice_due_date,now()) <7 ");
//		if (wVO.getDueDay() == 15) sb.append(" and Datediff(i.invoice_due_date,now()) <15  ");
		sb.append(" and  (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		logger.info("Exit method getInvoiceWorkCountNOString.");
		return sb.toString();
	}
	/**
	 * search PastDuePageNoString
	 * @param wVO
	 * @return from hongtao.pang
	 */
	private String getPastDueCountNOString(WorkspaceVO wVO) {
		logger.info("Enter method getPastDueCountNOString.");
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1)");
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u)  ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append(" and i.invoice_status_id >= 9 and i.invoice_status_id < 21 ");
		sb.append(" and i.invoice_due_date < date(now())");
		if(wVO.getUid()== -1){
			sb.append(" and i.assignment_user_id="+SystemUtil.getCurrentUserId());
		}else{
			sb.append(" and i.assignment_user_id="+wVO.getUid());
		}
//		sb.append(" and i.assigned_analyst_id="+wVO.getUid());
		sb.append(" and i.flag_workspace='Y' ");
		sb.append(" and b.rec_active_flag = 'Y' ");
		sb.append(" and b.ban_status_id = 1 ");
		sb.append(" and b.master_ban_flag = 'Y' ");
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		logger.info("Exit method getPastDueCountNOString.");
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
	 * @author mingyang.li
	 * InvoiceRejectWorkSpace query
	 * */
	public List<String> searchInvoiceRejectWorkCount(WorkspaceVO wVO) {
		logger.info("Enter method searchInvoiceWorkCount.");
		final String queryString = getInvoiceRejectWorkSqlString(wVO);
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
	 * @author hongtao.pang
	 * searchPastDue query
	 */
	
	public List<String> searchPastDue(WorkspaceVO wVO) {
		logger.info("Enter method searchPastDue.");
		final String queryString = getPastDueSqlString(wVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchPastDue.");
		return list;
	}
		
	/**
	 * @author pengfei.wang
	 * InvoiceWorkCount queryString
	 * */
	private String getInvoiceWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append(WorkString());
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append(" and i.invoice_status_id >=9 ");
		sb.append(" and i.invoice_status_id <> 98 ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and (i.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and (i.assignment_user_id="+wVO.getUid()+" ");
		}
		sb.append(" and i.flag_workspace='Y' )");
//		sb.append(" and i.invoice_due_date > now()");
		sb.append(" and b.rec_active_flag = 'Y' and b.master_ban_flag = 'Y' and b.ban_status_id = 1 ");
		if(wVO.getDueDay()==97){
			sb.append(" and i.invoice_due_date <= adddate(date_format(now(), '%Y/%m/%d'),interval 7 day) and i.invoice_due_date >= date(date_format(now(), '%Y/%m/%d')) ");
		}else if(wVO.getDueDay()==915){
			sb.append(" and i.invoice_due_date <= adddate(date_format(now(), '%Y/%m/%d'),interval 15 day) and i.invoice_due_date >= date(date_format(now(), '%Y/%m/%d')) ");				
		}else if(wVO.getDueDay()==90){
			sb.append(" and i.invoice_due_date = date_format(now(), '%Y/%m/%d')");				
		}
		else if(wVO.getDueDay()>0 && wVO.getDueDay()<16){
			sb.append(" and i.invoice_due_date =adddate(date_format(now(), '%Y/%m/%d'),interval "+wVO.getDueDay()+" day)");			
		}
		else{	
			sb.append(" ");
        }
//		if (wVO.getDueDay() == 7) sb.append(" and i.invoice_due_date < adddate(now(),interval 7 day) and i.invoice_due_date >= date(now()) ");
//		if (wVO.getDueDay() == 15) sb.append(" and i.invoice_due_date < adddate(now(),interval 15 day) and i.invoice_due_date >= date(now()) ");
//		if(wVO.getDueDay()==7) sb.append(" and Datediff(i.invoice_due_date,now()) <7 ");
//		if(wVO.getDueDay()==15) sb.append(" and Datediff(i.invoice_due_date,now()) <15 ");	
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
//		sb.append("  group by i.invoice_number ");
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getInvoiceWorkSqlString.");
		return sb.toString();
	}
	
	/**
	 * 
	 * @author pengfei.wang
	 * getPastDueSqlString queryString
	 */
	
	private String getPastDueSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getPastDueSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append("select concat('{id:',i.id,',no:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",lob:\"',toJSON(b.line_of_business is null ,'',b.line_of_business),'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),'\",date:\"',toJSON(i.invoice_date is null,'',i.invoice_date),'\",due:\"',toJSON(i.invoice_due_date is null,'',i.invoice_due_date),'\",total:\"',");
		sb.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),'\",status:\"',s.invoice_status_name,");
		sb.append("'\",currentDue:\"',format(toJSON(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sb.append("'\",originalFlag:\"',toJSON(i.original_flag is null,'N',i.original_flag),");
		sb.append("'\",analyst:\"',toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("'\",paymentAmount:\"',format(toJSON(i.payment_amount is null,'',i.payment_amount),2),");
		sb.append("'\",disputeAmount:\"',format(toJSON(i.dispute_amount is null,'',i.dispute_amount),2),");
		sb.append("'\",miscAdjustmentAmount:\"',format(toJSON(i.misc_adjustment_amount is null,'',i.misc_adjustment_amount),2),");
		sb.append("'\",invoiceStatus:\"',toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");

		sb.append("'\",invoiceExpectingDate:\"',if(i.invoice_expecting_date is null,'',i.invoice_expecting_date),");
		sb.append("'\",lastDispute:\"',format(i.last_dispute is null,2),");
		sb.append("'\",lastPayment:\"',format(i.last_payment is null,2),");
		sb.append("'\",lastTotalDue:\"',format(i.last_total_due is null,2),");
		sb.append("'\",missingEmailFlag:\"',toJSON(b.Missing_Email_flag is null,'Y',b.Missing_Email_flag),");
		sb.append("'\",daysInStatus:\"',toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),'\"");
		sb.append("}') a ");
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u)  ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append(" and i.invoice_status_id >= 9 and i.invoice_status_id < 21 ");
		sb.append(" and i.invoice_due_date < date(now())");
		if(wVO.getUid()== -1){
			sb.append(" and i.assignment_user_id="+SystemUtil.getCurrentUserId());
		}else{
			sb.append(" and i.assignment_user_id="+wVO.getUid());
		}
		sb.append(" and i.flag_workspace='Y'");
		sb.append(" and b.rec_active_flag = 'Y' ");
		sb.append(" and b.ban_status_id = 1 ");
		sb.append(" and b.master_ban_flag = 'Y' ");
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
//		sb.append("  group by i.invoice_number ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getPastDueSqlString.");
		return sb.toString();
	}
	
	/**
	 * @author mingyang.li
	 * InvoiceRejectWorkCount queryString
	 * */
	private String getInvoiceRejectWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceRejectWorkSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append(WorkString());
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append(" and i.invoice_status_id >10 ");
		sb.append(" and i.invoice_status_id < 16 ");
		sb.append(" and b.rec_active_flag = 'Y' ");
		sb.append(" and b.master_ban_flag = 'Y' ");
		sb.append(" and b.ban_status_id = 1 ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and (i.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and (i.assignment_user_id="+wVO.getUid()+" ");
		}
		sb.append(" and  i.flag_workspace='Y' )");
		sb.append("and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
//		sb.append("  group by i.invoice_number ");
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getInvoiceRejectWorkSqlString.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Missing Invoices
	 * */
	public List<String> searchWorkspase(WorkspaceVO wvo) {
		logger.info("Enter method searchWorkspase.");
		List<String> list = null;
		String queryString = getWorkSqlString(wvo);
		Session session = getSession();
		try {
			list = session.createSQLQuery(queryString).list();
			logger.info("Exit method searchWorkspase.");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author Donghao.Guo
	 * Preload Invoices
	 * */
	public long getPreloadInvoiceWorkCount(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCount.");
		final String sql = getPreloadInvoiceWorkCountNOString(wVO);
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
	 * @author mingyang.li
	 * Invoice Reject
	 * */
	public long getInvoiceRejectWorkCount(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceRejectWorkCount.");
		final String sql = getInvoiceRejectWorkCountNOString(wVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getInvoiceRejectWorkCount.");
		return count;
	}
	/**
	 * @author mingyang.li
	 * get Preload Invoices countNo.
	 * */
	private String getInvoiceRejectWorkCountNOString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceRejectWorkCountNOString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append(" and i.invoice_status_id >10 ");
		sb.append(" and i.invoice_status_id < 16 ");
		sb.append(" and b.rec_active_flag = 'Y' ");
		sb.append(" and b.master_ban_flag = 'Y' ");
		sb.append(" and b.ban_status_id = 1 ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and (i.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and (i.assignment_user_id="+wVO.getUid()+" ");
		}
		sb.append(" and  i.flag_workspace='Y' )");
		sb.append("and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		logger.info("Exit method getInvoiceRejectWorkCountNOString.");
		return sb.toString();
	}
	/**
	 * @author Donghao.Guo
	 * get Preload Invoices countNo.
	 * */
	private String getPreloadInvoiceWorkCountNOString(WorkspaceVO wVO) {
		logger.info("Enter method getPreloadInvoiceWorkCountNOString.");
		StringBuffer sb = new StringBuffer("select count(1) a from invoice i inner join vendor v on i.vendor_id = v.id inner join ban b on i.ban_id = b.id inner join invoice_status s on i.invoice_status_id = s.id inner join user u on u.id = i.assigned_analyst_id ");
		sb.append("where i.invoice_status_id >2 ");
		sb.append(" and i.invoice_status_id <9 and i.rec_active_flag='Y'  ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()== -1){
			sb.append(" and i.assigned_analyst_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and i.assigned_analyst_id="+wVO.getUid()+" ");
		}
		logger.info("Exit method getPreloadInvoiceWorkCountNOString.");
		System.out.println(sb.toString());
		return sb.toString();
	}
	/**
	 * @author Donghao.Guo
	 * search Preload Invoices
	 * */
	public List<String> searchWorkspasePreloadInvoice(WorkspaceVO wvo) {
		logger.info("Enter method searchWorkspasePreloadInvoice.");
		List<String> list = null;
		String queryString = getPreloadInvoiceWorkSqlString(wvo);
		Session session = getSession();
		try {
			list = session.createSQLQuery(queryString).list();
			logger.info("Exit method searchWorkspasePreloadInvoice.");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author Donghao.Guo
	 * get Preload Invoices sql
	 * */
	private String getPreloadInvoiceWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append(PreloadInvoiceWorkString());
		sb.append(" from invoice i inner join vendor v on i.vendor_id = v.id inner join ban b on i.ban_id = b.id inner join invoice_status s on i.invoice_status_id = s.id inner join user u on u.id = i.assigned_analyst_id ");
		sb.append("where i.invoice_status_id>2 ");
		sb.append(" and i.invoice_status_id <9 and i.rec_active_flag='Y' ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and i.assigned_analyst_id="+wVO.getUid()+" ");
		}
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getInvoiceWorkSqlString.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * searchWorkspase queryString
	 * */
	private String getWorkSqlString(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append(WorkString());
		sb.append(getWorkSqlStringWhereCause(wVO));
		logger.info("Exit method getInvoiceWorkSqlString.");
		return sb.toString();
	}
	private String getWorkSqlStringWhereCause(WorkspaceVO wVO) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}
		else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append("and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and i.invoice_status_id = 1 ");
		sb.append(" and b.ban_status_id != 2 ");
		if (wVO.getSortField() != null && wVO.getSortField().length() > 0) {
			sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		}
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
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
		StringBuffer sb = new StringBuffer("select count(1) a from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}
		else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append("and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and i.invoice_status_id = 1 ");
		sb.append(" and b.ban_status_id != 2 ");
		logger.info("Exit method getInvoiceWorkCountNO.");
		return sb.toString();
	}	
	
	/*
	 * ?? External Approve bucket ?, ???? external approve
	 * ? ban ?? ??? external approve ???????
	 */
	public long getWorkspaceExternalApproveInvoiceCount(WorkspaceVO workspaceVO) {
		
		logger.info("Enter method getWorkspaceExternalApproveInvoiceCount.");
		
		StringBuffer sqlStringBuffer = new StringBuffer(" SELECT COUNT(1) ");
		sqlStringBuffer.append( getExternalApproveInvoiceSegmentStatements(workspaceVO) );
		
		final String sql = sqlStringBuffer.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		
		logger.info("Exit method getWorkspaceExternalApproveInvoiceCount.");
		
		return count;
	}
	
	/*
	 * ?? External Approve Bucket ??
	 * ???????
	 */
	public List<String> listExternalApproveInvoiceItems(WorkspaceVO workspaceVO) {
		
		logger.info("Enter method listExternalApproveInvoiceItems.");
		
		StringBuffer sqlStringBuffer = new StringBuffer();
		
		// WorkString ?????????? invoice
		// ???
		// @this.searchInvoiceWorkCount
//		sqlStringBuffer.append( WorkString() );
		sqlStringBuffer.append("select concat('{id:',i.id,',no:\"',toJSON(i.invoice_number is null,'',i.invoice_number),");
		sqlStringBuffer.append(" '\",workflowAction:\"',toJSON(w.workflow_action_name is null ,'',w.workflow_action_name), ");
		sqlStringBuffer.append(" '\",notes:\"',toJSON(t.notes is null ,'',t.notes), ");
		sqlStringBuffer.append(" '\",vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name), ");
		sqlStringBuffer.append("'\",lob:\"',toJSON(b.line_of_business is null ,'',b.line_of_business),'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),'\",date:\"',toJSON(i.invoice_date is null,'',i.invoice_date),'\",due:\"',toJSON(i.invoice_due_date is null,'',i.invoice_due_date),'\",total:\"',");
		sqlStringBuffer.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),'\",status:\"',s.invoice_status_name,");
		sqlStringBuffer.append("'\",currentDue:\"',format(toJSON(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sqlStringBuffer.append("'\",originalFlag:\"',toJSON(i.original_flag is null,'N',i.original_flag),");
		sqlStringBuffer.append("'\",analyst:\"',toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sqlStringBuffer.append("'\",paymentAmount:\"',format(toJSON(i.payment_amount is null,'',i.payment_amount),2),");
		sqlStringBuffer.append("'\",disputeAmount:\"',format(toJSON(i.dispute_amount is null,'',i.dispute_amount),2),");
		sqlStringBuffer.append("'\",miscAdjustmentAmount:\"',format(toJSON(i.misc_adjustment_amount is null,'',i.misc_adjustment_amount),2),");
		sqlStringBuffer.append("'\",invoiceStatus:\"',toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
		sqlStringBuffer.append("'\",paymentTransactionNumber:\"',toJSON(p.payment_transaction_number is null,'',p.payment_transaction_number),");
		sqlStringBuffer.append("'\",paymentStatus:\"',toJSON(ps.payment_status_name is null,'',ps.payment_status_name),");
		sqlStringBuffer.append("'\",paymentReferenceNumber:\"',toJSON(p.payment_reference_code is null,'',p.payment_reference_code),");
		sqlStringBuffer.append("'\",paymentDate:\"',toJSON(p.payment_date is null,'',p.payment_date),");
		sqlStringBuffer.append("'\",invoiceExpectingDate:\"',if(i.invoice_expecting_date is null,'',i.invoice_expecting_date),");
		sqlStringBuffer.append("'\",lastDispute:\"',format(i.last_dispute is null,2),");
		sqlStringBuffer.append("'\",lastPayment:\"',format(i.last_payment is null,2),");
		sqlStringBuffer.append("'\",lastTotalDue:\"',format(i.last_total_due is null,2),");
		sqlStringBuffer.append("'\",missingEmailFlag:\"',toJSON(b.Missing_Email_flag is null,'Y',b.Missing_Email_flag),");
		sqlStringBuffer.append("'\",daysInStatus:\"',toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),'\"");
		sqlStringBuffer.append("}') a ");
		sqlStringBuffer.append( getExternalApproveInvoiceSegmentStatements(workspaceVO) );
		
		sqlStringBuffer.append(" ORDER BY " + workspaceVO.getSortField() + " " + workspaceVO.getSortingDirection());
		sqlStringBuffer.append(" LIMIT " + workspaceVO.getStartIndex() + "," + workspaceVO.getRecPerPage());
		
		final String queryString = sqlStringBuffer.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method listExternalApproveInvoiceItems.");
		return list;
	}
	
	/*
	 * ?? external approve invoice ???????
	 * 
	 */
	public List<Object[]> getExternalApproveInvoiceData(WorkspaceVO wvo){
		
		logger.info("Enter method getExternalApproveInvoiceData.");
		
		StringBuffer sb = new StringBuffer("SELECT ");
		sb.append("toJSON(i.invoice_number IS NULL,'',i.invoice_number),");
		sb.append("toJSON(w.workflow_action_name IS NULL,'',w.workflow_action_name),");
		sb.append("toJSON(t.notes IS NULL,'',t.notes),");
		sb.append("toJSON(b.account_number IS NULL ,'',b.account_number),");
		sb.append("toJSON(v.vendor_name IS NULL ,'',v.vendor_name),");
		sb.append("toJSON(i.invoice_date IS NULL,'',i.invoice_date),");
		sb.append("FORMAT(toJSON(i.invoice_current_due IS NULL,0,i.invoice_current_due),2),");
		sb.append("toJSON(i.original_flag IS NULL,'N',i.original_flag),");
		sb.append("toJSON(i.assigned_analyst_id IS NULL,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("toJSON(i.invoice_due_date IS NULL,'',i.invoice_due_date),");
		sb.append("toJSON(i.invoice_total_due IS NULL,'',CONCAT('',format(i.invoice_total_due, 2))),");		
		sb.append("FORMAT(toJSON(i.payment_amount IS NULL,'',i.payment_amount),2),");
		sb.append("FORMAT(toJSON(i.dispute_amount IS NULL,'',i.dispute_amount),2),");
		sb.append("FORMAT(toJSON(i.misc_adjustment_amount IS NULL,'',i.misc_adjustment_amount),2),");
		sb.append("toJSON(s.invoice_status_name IS NULL,'',s.invoice_status_name),");
		sb.append("toJSON(i.status_timestamp IS NULL,'',datediff(now(),i.status_timestamp)), ");
		sb.append("toJSON(ps.payment_status_name IS NULL,'',ps.payment_status_name),");
		sb.append("toJSON(p.payment_date IS NULL,'',p.payment_date),");
		sb.append("toJSON(p.payment_transaction_number IS NULL,'',p.payment_transaction_number),");
		sb.append("toJSON(p.payment_reference_code IS NULL,'',p.payment_reference_code)");
		sb.append( getExternalApproveInvoiceSegmentStatements(wvo) );
		
		sb.append("order by i.id asc LIMIT " + wvo.getStartIndex() + "," + wvo.getRecPerPage());
		
		
		Session session = getSession();
		
		try {
			
			List<Object[]> list = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getExternalApproveInvoiceData.");
			return list;
			
		} finally {
			releaseSession(session);
		}
	}
	
	/*
	 * ?? External Approve Invoice ? from ... where
	 * ???
	 */
	private String getExternalApproveInvoiceSegmentStatements(WorkspaceVO workspaceVO) {
		
		StringBuffer sqlStringBuffer = new StringBuffer();

		sqlStringBuffer.append(" FROM invoice i  ");
		sqlStringBuffer.append(" 	INNER JOIN ban b ON i.ban_id = b.id  ");
		sqlStringBuffer.append(" 	LEFT JOIN vendor v ON i.vendor_id = v.id  ");
		sqlStringBuffer.append(" 	LEFT JOIN transaction_history t ON t.invoice_id = i.id  ");
		sqlStringBuffer.append(" 	LEFT JOIN workflow_action w ON t.workflow_action_id = w.id  ");
		sqlStringBuffer.append(" 	LEFT JOIN invoice_status s ON i.invoice_status_id = s.id  ");
		sqlStringBuffer.append(" 	LEFT JOIN user u ON i.assigned_analyst_id = u.id  ");
		sqlStringBuffer.append(" 	LEFT JOIN payment p ON i.id = p.invoice_id AND p.rec_active_flag = 'Y'  ");
		sqlStringBuffer.append(" 	LEFT JOIN payment_status ps ON ps.id = p.payment_status_id  ");
		sqlStringBuffer.append(" WHERE i.rec_active_flag = 'Y'  ");
		sqlStringBuffer.append(" 	AND b.external_approve_flag = 'Y'  ");
		sqlStringBuffer.append(" 	AND i.invoice_status_id >= 9  ");
		sqlStringBuffer.append(" 	AND b.ban_status_id = 1  ");
		sqlStringBuffer.append(" 	AND b.rec_active_flag = 'Y'  ");
		sqlStringBuffer.append(" 	AND b.master_ban_flag = 'Y'  ");
		sqlStringBuffer.append(" 	AND i.external_approve_flag = 'Y'  ");
		if(workspaceVO.getUid() == -1){
			sqlStringBuffer.append(" AND i.assignment_user_id = "+ SystemUtil.getCurrentUserId()+" ");
		}else{
			sqlStringBuffer.append(" AND i.assignment_user_id = "+ workspaceVO.getUid()+" ");
		}
		
		if(workspaceVO.getFilter()!=null){
			
			sqlStringBuffer.append(" AND ");
			// ??? filters ???? bucket ???? filters.
			sqlStringBuffer.append(workspaceVO.getFilter());
		}
		sqlStringBuffer.append(" 	AND t.id IN (  ");
		sqlStringBuffer.append(" 		SELECT MAX(t.id) FROM transaction_history t ");
		sqlStringBuffer.append(" 		LEFT JOIN invoice di ON di.id = t.invoice_id ");
		sqlStringBuffer.append(" 		WHERE i.external_approve_flag = 'Y' ");
		sqlStringBuffer.append(" 		AND i.rec_active_flag = 'Y' ");
		// 
		if(workspaceVO.getUid() == -1){
			sqlStringBuffer.append(" 	AND i.assignment_user_id = "+ SystemUtil.getCurrentUserId()+" ");
		}else{
			sqlStringBuffer.append(" 	AND i.assignment_user_id = "+ workspaceVO.getUid()+" ");
		}
		sqlStringBuffer.append(" 		AND i.invoice_status_id >= 9 ");
		sqlStringBuffer.append(" 		AND di.id = i.id ");
		sqlStringBuffer.append(" 		GROUP BY i.id ASC ");
		sqlStringBuffer.append(" 	)  ");
		
		
		
		
		
		return sqlStringBuffer.toString();
	}
	
	public Invoice findById(java.lang.Integer id) {

		Invoice instance = (Invoice) getHibernateTemplate().get(
				"com.saninco.ccm.model.Invoice", id);
		return instance;
	}
	
	public Invoice refreshInvoiceNoCache(Invoice invoice){
		Session session = getSession();
		try {
			session.evict(invoice);
			return (Invoice) getHibernateTemplate().get("com.saninco.ccm.model.Invoice", invoice.getId());
		} finally {
			releaseSession(session);
		}
	}
	
	public String getVendorAcronymByInvoiceId(int invoiceId){
		
		final StringBuffer sb=new StringBuffer();
		sb.append("select v.vendor_acronym from invoice as i left join vendor as v on i.vendor_id=v.id where i.id= ");
		sb.append(""+invoiceId+"");
		Session session = getSession();
		try {
			SQLQuery query=session.createSQLQuery(sb.toString());
			//update by mingyang.li 2012.03.19
			return query.uniqueResult() != null ? query.uniqueResult().toString() : "";
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
	 * System Time
	 */
	public List getInvoiceAtTransaction() {
		logger.info("Enter method getInvoiceAtTransaction.");
		StringBuffer sb = new StringBuffer("select i.status_timestamp from invoice i");
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_status_id = 41 ");
		sb.append(" and i.status_timestamp is not null "); 
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getInvoiceAtTransaction.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * User Time
	 */
	public List getUserInvoiceAtTransaction() {
		logger.info("Enter method getUserInvoiceAtTransaction.");
		StringBuffer sb = new StringBuffer("select i.status_timestamp ");
		sb.append("from invoice i ");
		sb.append("where i.rec_active_flag = 'Y' ");
		sb.append("and i.invoice_status_id = 41 ");
		sb.append("and i.status_timestamp is not null "); 
		sb.append("and i.assigned_analyst_id = "+SystemUtil.getCurrentUserId()+" ");
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getUserInvoiceAtTransaction.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * System Time Scale
	 * OK
	 */
	public List getInvoiceAtDashlet() {
		logger.info("Enter method getInvoiceAtDashlet.");
		StringBuffer sb = new StringBuffer("select i.invoice_due_date from invoice i");
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_status_id >= 9 ");
		sb.append(" and i.invoice_status_id <= 40 ");
		sb.append(" and i.invoice_due_date is not null"); 
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getInvoiceAtDashlet.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * Dashboard
	 * @Author Chao.Liu Date: Oct 11, 2010
	 * @param isSystem
	 * @param day
	 * @return
	 */
	public Integer getTimeScaleCount(boolean isSystem, int day){
		logger.info("Enter method getTimeScaleCount.");
		StringBuffer sb = new StringBuffer("select count(*) from (invoice i, vendor v, ban b, invoice_status s,user u) ");
		sb.append(" LEFT JOIN payment p ON (i.id = p.invoice_id AND p.rec_active_flag = 'Y') ");
		sb.append(" LEFT JOIN payment_status ps ON (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag = 'Y' ");
		sb.append(" and i.flag_workspace = 'Y'");
		sb.append(" and i.invoice_status_id >= 9 ");
		sb.append(" and i.invoice_status_id <> 98 ");
		sb.append(" and b.rec_active_flag = 'Y' ");
		sb.append(" and b.master_ban_flag = 'Y' ");
		sb.append(" and b.ban_status_id = 1 ");
		sb.append(" and i.vendor_id = v.id ");
		sb.append(" and i.ban_id = b.id ");
		sb.append(" and i.invoice_status_id = s.id ");
		sb.append(" and u.id = i.assigned_analyst_id ");
		if(day > -1){
			sb.append(" and i.invoice_due_date < adddate(now(), INTERVAL "+day+" DAY) ");
			sb.append(" and i.invoice_due_date >= date(now()) ");
		} 
//		if(!isSystem) sb.append(" and i.assigned_analyst_id = " + SystemUtil.getCurrentUserId());
		if(!isSystem) sb.append(" and i.assignment_user_id = " + SystemUtil.getCurrentUserId());
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getTimeScaleCount.");
			return new Integer(l.get(0).toString());
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * User Time Scale
	 * OK
	 */
	public List getUserInvoiceAtDashlet() {
		logger.info("Enter method getUserInvoiceAtDashlet.");
		StringBuffer sb = new StringBuffer(" select i.invoice_due_date ");
		sb.append(" from invoice i ");
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_status_id >= 9 ");
		sb.append(" and i.invoice_status_id <= 40 ");
		sb.append(" and i.assigned_analyst_id = " + SystemUtil.getCurrentUserId());
		sb.append(" and i.invoice_due_date is not null"); 
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getUserInvoiceAtDashlet.");
			return l;
		} finally {
			releaseSession(session);
		}
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
	
	public List<AttachmentFile> findAttachmentFileByProperty(String propertyName, Object value) {
		log.debug("finding AttachmentFile instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AttachmentFile as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public void saveAttachmentFile(AttachmentFile transientInstance) {
		log.debug("saving AttachmentFile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void saveAttachmentPoint(AttachmentPoint transientInstance) {
		log.debug("saving AttachmentPoint instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	/**
	 * @pengfei.wang
	 * */
	public long getWorkspasePaymentInProcessCount(WorkspaceVO wvo) {
		logger.info("Enter method getWorkspasePaymentInProcessCount.");
		final String sql = getInvoiceWorkCountNOPaymentInProcessCount(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getWorkspasePaymentInProcessCount.");
		return count;
	}
	
	/**
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String getInvoiceWorkCountNOPaymentInProcessCount(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCountNOPaymentInProcessCount.");
		StringBuffer sb = new StringBuffer("select count(1) a from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id)   ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append("and i.invoice_status_id not in (99,98) ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and p.payment_status_id >= 20 and p.payment_status_id < 99  ");
		sb.append(" and p.payment_status_id not in (98,23,26,27)  ");
		sb.append(" and p.flag_workspace!='N' ");
		logger.info("Exit method getInvoiceWorkCountNOPaymentInProcessCount.");
		return sb.toString();
	}
	
	/**
	 * @pengfei.wang
	 * */
	public List<String> searchWorkspasePaymentInProcess(WorkspaceVO wvo) {
		logger.info("Enter method searchWorkspasePaymentInProcess.");
		final String queryString = getWorkSqlStringPaymentInProcess(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchWorkspasePaymentInProcess.");
		return list;
	}
	
	private String getWorkSqlStringPaymentInProcess(WorkspaceVO wVO) {
		logger.info("Enter method getWorkSqlStringPaymentInProcess.");
		StringBuffer sb = new StringBuffer();
		sb.append(WorkString());
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append("and i.invoice_status_id not in (99,98) ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and  p.payment_status_id >= 20 and p.payment_status_id < 99  ");
		sb.append(" and  p.payment_status_id not in (98,23,26,27)  ");
		sb.append(" and p.flag_workspace!='N' ");
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getWorkSqlStringPaymentInProcess.");
		return sb.toString();
	}
	
	public long getWorkspaseDisputesInProcess(WorkspaceVO wvo) {
		logger.info("Enter method getWorkspaseDisputesInProcess.");
		final String sql = getInvoiceWorkCountNODisputesInProcess(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getWorkspaseDisputesInProcess.");
		return count;
	}
	private String getInvoiceWorkCountNODisputesInProcess(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCountNODisputesInProcess.");
		StringBuffer sb = new StringBuffer("select count(1) from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and (select count(1) from dispute d where d.invoice_id=i.id and d.dispute_status_id >= 20 and d.dispute_status_id < 98  and d.rec_active_flag='Y')>0 ");
		logger.info("Exit method getInvoiceWorkCountNODisputesInProcess.");
		return sb.toString();
	}

	public List<String> searchWorkspaseDisputesInProcess(WorkspaceVO wvo) {
		logger.info("Enter method searchWorkspaseDisputesInProcess.");
		final String queryString = getWorkSqlStringDisputesInProcess(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchWorkspaseDisputesInProcess.");
		return list;
	}
	
	private String getWorkSqlStringDisputesInProcess(WorkspaceVO wVO) {
		logger.info("Enter method getWorkSqlStringDisputesInProcess.");
		StringBuffer sb = new StringBuffer();
		sb.append(WorkString());
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and (select count(1) from dispute d where d.invoice_id=i.id and d.dispute_status_id >= 20 and d.dispute_status_id < 98 and d.rec_active_flag='Y')>0 ");
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getWorkSqlStringDisputesInProcess.");
		return sb.toString();
	}
	
	public void modifyInvoicePreviousAdjustment(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method modifyInvoicePreviousAdjustment.");
		String sb = getModifyInvoicePreviousAdjustmentSQL(searchInvoiceVO);
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
			sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
			sqlQuery.executeUpdate();
			
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method modifyInvoicePreviousAdjustment.");
	}
	
	private String getModifyInvoicePreviousAdjustmentSQL(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method getModifyInvoicePreviousAdjustmentSQL.");
		StringBuffer sb = new StringBuffer();
		sb.append(" update invoice i set i.invoice_previous_adjustment = ifnull(i.invoice_previous_adjustment,0) - "+searchInvoiceVO.getInvoiceAdjustment()+", ");
		sb.append(" i.invoice_balance_forward = ifnull(i.invoice_balance_forward,0) - "+searchInvoiceVO.getInvoiceAdjustment()+", ");
		sb.append(" i.invoice_adjustment = ifnull(i.invoice_adjustment,0) + "+searchInvoiceVO.getInvoiceAdjustment()+", ");
		sb.append(" i.invoice_current_due = ifnull(i.invoice_current_due,0) + "+searchInvoiceVO.getInvoiceAdjustment()+", ");
		sb.append(" i.calculated_current_due = ifnull(i.calculated_current_due,0) + "+searchInvoiceVO.getInvoiceAdjustment()+", ");
		sb.append(" i.notes = :notes, ");	// put into parameter
		sb.append(" i.modified_timestamp = now() ,i.modified_by = "+SystemUtil.getCurrentUserId()+" ");
		sb.append(" where i.id = "+searchInvoiceVO.getInvoiceId()+" and i.invoice_status_id in (9,10,11,12,13,14,15) and i.rec_active_flag = 'Y' ");
		logger.info("Exit method getModifyInvoicePreviousAdjustmentSQL.");
		return sb.toString();
	}
	
	public void modifyInvoiceMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method modifyInvoiceMoveCurrentToAdjustment.");
		String sb = getModifyInvoiceMoveCurrentToAdjustmentSQL(searchInvoiceVO);
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
			sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
			sqlQuery.executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method modifyInvoiceMoveCurrentToAdjustment.");
	}
	
	private String getModifyInvoiceMoveCurrentToAdjustmentSQL(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method getModifyInvoiceMoveCurrentToAdjustmentSQL.");
		StringBuffer sb = new StringBuffer();
		sb.append(" update invoice i set i.modified_timestamp = now(), i.invoice_balance_forward = ifnull(i.invoice_balance_forward,0) + "+searchInvoiceVO.getAmount()+", ");
		sb.append(" i.invoice_previous_adjustment = ifnull(i.invoice_previous_adjustment,0) + "+searchInvoiceVO.getAmount()+",");
		if("16".equals(searchInvoiceVO.getItemTypeId()) || "6".equals(searchInvoiceVO.getItemTypeId().substring(0, 1)))
			sb.append(" i.invoice_adjustment = ifnull(i.invoice_adjustment,0) - "+searchInvoiceVO.getAmount()+",");
		else
			sb.append(" i.invoice_tax = ifnull(i.invoice_tax,0) - "+searchInvoiceVO.getAmount()+",");
		sb.append(" i.invoice_current_due = ifnull(i.invoice_current_due,0) - "+searchInvoiceVO.getAmount()+",");
		sb.append(" i.calculated_current_due = ifnull(i.calculated_current_due,0) - "+searchInvoiceVO.getAmount()+",");
		sb.append(" i.notes = :notes,");	// put into parameter
		sb.append(" i.modified_by = "+SystemUtil.getCurrentUserId()+" ");
		sb.append(" where i.id = "+searchInvoiceVO.getInvoiceId()+" and i.invoice_status_id in (9,10,11,12,13,14,15) and i.rec_active_flag = 'Y' ");
		logger.info("Exit method getModifyInvoiceMoveCurrentToAdjustmentSQL.");
		return sb.toString();
	}
	
	public void modifyInvoiceItemMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method modifyInvoiceItemMoveCurrentToAdjustment.");
		String sb = getModifyInvoiceItemMoveCurrentToAdjustmentSQL(searchInvoiceVO,1);
		String sbTotal = getModifyInvoiceItemMoveCurrentToAdjustmentSQL(searchInvoiceVO,2);
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
			sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
			sqlQuery.executeUpdate();
			
			session.createSQLQuery(sbTotal.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method modifyInvoiceItemMoveCurrentToAdjustment.");
	}
	
	private String getModifyInvoiceItemMoveCurrentToAdjustmentSQL(SearchInvoiceVO searchInvoiceVO,int proposalFlag) {
		logger.info("Enter method getModifyInvoiceItemMoveCurrentToAdjustmentSQL.");
		StringBuffer sb = new StringBuffer();
		sb.append(" update invoice_item ii set ii.modified_timestamp = now() , ii.item_amount = ii.item_amount - "+searchInvoiceVO.getAmount()+", ");
		if(proposalFlag == 1){
			sb.append(" ii.notes = :notes,");	// put into parameter
		}
		sb.append(" ii.modified_by = "+SystemUtil.getCurrentUserId()+" ");
		sb.append(" where ii.rec_active_flag = 'Y' and proposal_flag="+proposalFlag+" ");
		sb.append(" and f_get_root_item_type(ii.item_type_id)= f_get_root_item_type("+searchInvoiceVO.getItemTypeId()+") "); // o?=o?=R*o?=P6o?=o?=o?=6 o?=o?=o?=o?=4
		if(proposalFlag == 1){
			sb.append(" and ii.id = "+searchInvoiceVO.getInvoiceItemId()+" "); 
		}else{
			sb.append(" and ii.invoice_id = "+searchInvoiceVO.getInvoiceId()+" "); 
		}
		logger.info("Exit method getModifyInvoiceItemMoveCurrentToAdjustmentSQL.");
		return sb.toString();
	}
	
	public void modifyProposalMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method modifyProposalMoveCurrentToAdjustment.");
		String sb = getModifyProposalMoveCurrentToAdjustmentSQL(searchInvoiceVO,1);
		String sbTotal = getModifyProposalMoveCurrentToAdjustmentSQL(searchInvoiceVO,2);
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
			sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
			sqlQuery.executeUpdate();
			
			session.createSQLQuery(sbTotal.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method modifyProposalMoveCurrentToAdjustment.");
	}
	
	private String getModifyProposalMoveCurrentToAdjustmentSQL(SearchInvoiceVO searchInvoiceVO,int proposalFlag) {
		logger.info("Enter method getModifyProposalMoveCurrentToAdjustmentSQL.");
		StringBuffer sb = new StringBuffer();
		sb.append(" update proposal p set p.modified_timestamp = now() , p.payment_amount = p.payment_amount - "+searchInvoiceVO.getAmount()+", ");
		if(proposalFlag == 1){
			sb.append(" p.notes = :notes,");	// put into parameter
		}
		sb.append(" p.modified_by = "+SystemUtil.getCurrentUserId()+" ");
		sb.append(" where p.rec_active_flag = 'Y' and p.proposal_flag="+proposalFlag+" ");
		sb.append(" and f_get_root_item_type(p.item_type_id)= f_get_root_item_type("+searchInvoiceVO.getItemTypeId()+") "); // o?=o?=R*o?=P6o?=o?=o?=6 o?=o?=o?=o?=4
		if(proposalFlag == 1){
			sb.append("  and p.id = "+searchInvoiceVO.getProposalId()+" ");
		}else{
			sb.append(" and p.invoice_id = " + searchInvoiceVO.getInvoiceId()+" ");
		}
		logger.info("Exit method getModifyProposalMoveCurrentToAdjustmentSQL.");
		return sb.toString();
	}
	
	public Double modifyInvoiceItemPreviousAdjustment(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method modifyInvoiceItemPreviousAdjustment.");
		
		String[] single = {};
		if(!"".equals(searchInvoiceVO.getSingle())){
			single = searchInvoiceVO.getSingle().split(",");
		}
		Double taxAmount = 0.00; 
		Session session = getSession();
		SQLQuery sqlQuery = null;
		try {
			if(single != null && single.length > 0){
				for(int i = 0; i < single.length; i+=4){
					taxAmount += Double.valueOf(single[i]);
					String sb = getModifyInvoiceItemPreviousAdjustmentSQL(single,searchInvoiceVO.getInvoiceId(),i,"0");
					sqlQuery = session.createSQLQuery(sb.toString());		//o?=o?=o?=o?=invoice_itemo?=o?=tax
					sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
					sqlQuery.executeUpdate();
					String invoiceItemId = searchInvoiceItemId(searchInvoiceVO);	//o?=C5o?=C?o?=N2o?=o?=o?=invoice_item_ido?=o?=id
					String addProposalSQL = getModifyProposalSQL(single,searchInvoiceVO,invoiceItemId,i);
					sqlQuery = session.createSQLQuery(addProposalSQL);
					sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
					sqlQuery.executeUpdate();		//o?=o?=o?=o?=proposalo?=o?=tax
				}
				String updateInvoice = "update invoice i set i.invoice_previous_adjustment = ifnull(i.invoice_previous_adjustment,0) - "+taxAmount+" ,i.invoice_balance_forward = ifnull(i.invoice_balance_forward,0) - "+taxAmount+" ,i.calculated_current_due = ifnull(i.calculated_current_due,0) + "+taxAmount+" ,i.invoice_current_due = ifnull(i.invoice_current_due,0) + "+taxAmount+" ,i.invoice_tax = ifnull(i.invoice_tax,0) + "+taxAmount+" where i.id = "+searchInvoiceVO.getInvoiceId();	// o?=o?=o?=o?=o?=K0,o?=o?=C4o?=o?=o?=o?=o?=o?=o?=o?=o?=invoiceo?=o?=o?=K0o?=V6o?=
				session.createSQLQuery(updateInvoice.toString()).executeUpdate();
				
			}
			String addAdjustmentSQL = getModifyInvoiceItemPreviousAdjustmentSQL(null,searchInvoiceVO.getInvoiceId(),0,searchInvoiceVO.getInvoiceAdjustment());
			sqlQuery = session.createSQLQuery(addAdjustmentSQL.toString());
			sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
			sqlQuery.executeUpdate();	//o?=o?=o?=o?=invoice_itemo?=o?= adjustment
			String invoiceItemId = searchInvoiceItemId(searchInvoiceVO);
			String addProposalSQL = getModifyProposalSQL(null,searchInvoiceVO,invoiceItemId,0);
			sqlQuery = session.createSQLQuery(addProposalSQL);
			sqlQuery.setParameter("notes", searchInvoiceVO.getNote());
			sqlQuery.executeUpdate();			//o?=o?=o?=o?=proposalo?=o?=adjustment
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method modifyInvoiceItemPreviousAdjustment.");
		
		return taxAmount;
	}
	
	public void modifyInvoiceItemandProposalProposalFlagIs2Record(SearchInvoiceVO searchInvoiceVO,Double taxTotal) {
		logger.info("Enter method modifyInvoiceItemandProposalProposalFlagIs2Record.");
		
		String modifyInvoiceItem = "";
		String modifyProposal = "";
		Session session = getSession();
		try {
			if(searchInvoiceItemProposalFlagIs2Record(searchInvoiceVO,true) > 0){
				modifyInvoiceItem = updateInvoiceItemProposalFlagIs2Record(searchInvoiceVO,null);
				modifyProposal = updateProposalProposalFlagIs2Record(searchInvoiceVO,null);
				session.createSQLQuery(modifyInvoiceItem.toString()).executeUpdate();
				session.createSQLQuery(modifyProposal.toString()).executeUpdate();
			}else{
				modifyInvoiceItem = insertInvoiceItemProposalFlagIs2Record(searchInvoiceVO,null);
				session.createSQLQuery(modifyInvoiceItem.toString()).executeUpdate();
				String invoiceItemId = searchInvoiceItemId(searchInvoiceVO);
				modifyProposal = insertProposalProposalFlagIs2Record(searchInvoiceVO,null,invoiceItemId);
				session.createSQLQuery(modifyProposal.toString()).executeUpdate();
			}
			
			
			if(searchInvoiceItemProposalFlagIs2Record(searchInvoiceVO,false) > 0){
				modifyInvoiceItem = updateInvoiceItemProposalFlagIs2Record(searchInvoiceVO,taxTotal);
				modifyProposal = updateProposalProposalFlagIs2Record(searchInvoiceVO,taxTotal);
				session.createSQLQuery(modifyInvoiceItem.toString()).executeUpdate();
				session.createSQLQuery(modifyProposal.toString()).executeUpdate();
			}else{
				modifyInvoiceItem = insertInvoiceItemProposalFlagIs2Record(searchInvoiceVO,taxTotal);
				session.createSQLQuery(modifyInvoiceItem.toString()).executeUpdate();
				String invoiceItemId = searchInvoiceItemId(searchInvoiceVO);
				modifyProposal = insertProposalProposalFlagIs2Record(searchInvoiceVO,taxTotal,invoiceItemId);
				session.createSQLQuery(modifyProposal.toString()).executeUpdate();
			}
			
			// o?=o?=o?=o?=o?=B2o?=o?=o?=o?=B<o?=o?=parent
			String modifyInvoiceItemParentId = "update invoice_item a,invoice_item b set a.parent_item_id = b.id" +
					" where b.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and b.proposal_flag = 2 and b.item_type_id = 6 and b.rec_active_flag = 'Y' and b.invoice_id = a.invoice_id " +
					" and b.rec_active_flag = a.rec_active_flag and f_get_root_item_type(a.item_type_id) = 6 and a.proposal_flag = 1 ";
			session.createSQLQuery(modifyInvoiceItemParentId.toString()).executeUpdate();
			
			modifyInvoiceItemParentId = "update invoice_item a,invoice_item b set a.parent_item_id = b.id" +
					" where b.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and b.proposal_flag = 2 and b.item_type_id = 8 and b.rec_active_flag = 'Y' and b.invoice_id = a.invoice_id " +
					" and b.rec_active_flag = a.rec_active_flag and f_get_root_item_type(a.item_type_id) = 8 and a.proposal_flag = 1 ";
			session.createSQLQuery(modifyInvoiceItemParentId.toString()).executeUpdate();
			
			String modifyProposalParentId = "update proposal a,proposal b set a.parent_proposal_id =  b.id" +
				    " where b.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and b.proposal_flag = 2 and b.item_type_id = 6 and b.rec_active_flag = 'Y' and b.invoice_id = a.invoice_id" +
				    " and b.rec_active_flag = a.rec_active_flag and f_get_root_item_type(a.item_type_id) = 6 and a.proposal_flag = 1";
			session.createSQLQuery(modifyProposalParentId.toString()).executeUpdate();	   
			
			modifyProposalParentId = "update proposal a,proposal b set a.parent_proposal_id =  b.id" +
				    " where b.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and b.proposal_flag = 2 and b.item_type_id = 8 and b.rec_active_flag = 'Y' and b.invoice_id = a.invoice_id" +
				    " and b.rec_active_flag = a.rec_active_flag and f_get_root_item_type(a.item_type_id) = 8 and a.proposal_flag = 1";
			session.createSQLQuery(modifyProposalParentId.toString()).executeUpdate();	   
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method modifyInvoiceItemandProposalProposalFlagIs2Record.");
	}
	
	private String updateInvoiceItemProposalFlagIs2Record(SearchInvoiceVO searchInvoiceVO,Double taxTotal) {
		logger.info("Enter method updateInvoiceItemProposalFlagIs2Record.");
		StringBuffer invoiceItemSql = new StringBuffer(" update invoice_item ii set ii.item_amount= ii.item_amount + ");
		if(taxTotal == null)
			invoiceItemSql.append(searchInvoiceVO.getInvoiceAdjustment()+", ");
		else
			invoiceItemSql.append(taxTotal+", ");
		invoiceItemSql.append(" ii.modified_timestamp = now() , ii.modified_by = "+ SystemUtil.getCurrentUserId());
		invoiceItemSql.append(" where ii.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and ii.proposal_flag = 2 ");
		if(taxTotal == null)
			invoiceItemSql.append(" and ii.item_type_id = 6 and ii.rec_active_flag = 'Y' ");
		else
			invoiceItemSql.append(" and ii.item_type_id = 8 and ii.rec_active_flag = 'Y' ");
		logger.info("Exit method updateInvoiceItemProposalFlagIs2Record.");
		return invoiceItemSql.toString();
	}
	
	private String updateProposalProposalFlagIs2Record(SearchInvoiceVO searchInvoiceVO,Double taxTotal) {
		logger.info("Enter method updateProposalProposalFlagIs2Record.");
		StringBuffer invoiceItemSql = new StringBuffer(" update proposal p set p.payment_amount = p.payment_amount + ");
		if(taxTotal == null)
			invoiceItemSql.append(searchInvoiceVO.getInvoiceAdjustment()+", ");
		else
			invoiceItemSql.append(taxTotal+", ");
		invoiceItemSql.append(" p.modified_timestamp = now() , p.modified_by = "+ SystemUtil.getCurrentUserId());
		invoiceItemSql.append(" where p.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and p.proposal_flag = 2 ");
		if(taxTotal == null)
			invoiceItemSql.append(" and p.item_type_id = 6 and p.rec_active_flag = 'Y' ");
		else
			invoiceItemSql.append(" and p.item_type_id = 8 and p.rec_active_flag = 'Y' ");
		logger.info("Exit method updateProposalProposalFlagIs2Record.");
		return invoiceItemSql.toString();
	}
	
	private String insertProposalProposalFlagIs2Record(SearchInvoiceVO searchInvoiceVO,Double taxTotal,String invoiceItemId) {
		logger.info("Enter method insertProposalProposalFlagIs2Record.");
		StringBuffer addProposalSQL = new StringBuffer("insert into proposal (invoice_id,invoice_item_id,item_type_id,account_code_id," +
		"proposal_flag,item_name,payment_amount,created_timestamp,created_by,modified_timestamp,modified_by,rec_active_flag) values ( ");
		addProposalSQL.append(searchInvoiceVO.getInvoiceId()+","+invoiceItemId+", ");
		if(taxTotal != null)
			addProposalSQL.append(" 8, ");
		else
			addProposalSQL.append(" 6, ");
		addProposalSQL.append(" "+searchInvoiceVO.getScoaCodeId()+",'2', ");
		if(taxTotal != null)
			addProposalSQL.append(" 'Tax(Manual)', "+taxTotal+", ");
		else
			addProposalSQL.append(" 'Adjustment(Manual)', "+searchInvoiceVO.getInvoiceAdjustment()+", ");
		addProposalSQL.append(" now(),"+SystemUtil.getCurrentUserId()+",now(),"+SystemUtil.getCurrentUserId()+",'Y') ");
		logger.info("Exit method insertProposalProposalFlagIs2Record.");
		return addProposalSQL.toString();
	}
	
	private String insertInvoiceItemProposalFlagIs2Record(SearchInvoiceVO searchInvoiceVO,Double taxTotal) {
		logger.info("Enter method insertInvoiceItemProposalFlagIs2Record.");
		StringBuffer invoiceItemSql = new StringBuffer("insert into invoice_item (invoice_id,bar_code,invoice_number,item_type_id,proposal_flag,item_name,item_amount");
		if(taxTotal != null)
			invoiceItemSql.append(",tax_code_id");
		invoiceItemSql.append(",created_timestamp,created_by,modified_timestamp,modified_by,rec_active_flag )");
		invoiceItemSql.append("select id,bar_code,invoice_number,");
		if(taxTotal != null)
			invoiceItemSql.append(" 8, ");
		else
			invoiceItemSql.append(" 6, ");
		invoiceItemSql.append(" 2, ");
		if(taxTotal != null)
			invoiceItemSql.append(" 'Tax(Manual)',"+taxTotal+",null, ");
		else
			invoiceItemSql.append(" 'Adjustment(Manual)',"+searchInvoiceVO.getInvoiceAdjustment()+",");	
		invoiceItemSql.append(" now(),"+SystemUtil.getCurrentUserId()+",now(),"+SystemUtil.getCurrentUserId()+",'Y' from invoice where id = "+searchInvoiceVO.getInvoiceId());
		logger.info("Exit method insertInvoiceItemProposalFlagIs2Record.");
		return invoiceItemSql.toString();
	}
	
	private String getModifyInvoiceItemPreviousAdjustmentSQL(String[] single,String invoiceId,int index,String adjustment) {
		logger.info("Enter method getModifyInvoiceItemPreviousAdjustmentSQL.");
		StringBuffer invoiceItemSql = new StringBuffer("insert into invoice_item (invoice_id,bar_code,invoice_number,item_type_id,proposal_flag,item_name,item_amount");
		if(single != null && single.length > 0)
			invoiceItemSql.append(",tax_code_id");
		invoiceItemSql.append(",created_timestamp,created_by,modified_timestamp,modified_by,rec_active_flag,notes )");
	
		invoiceItemSql.append("select id,bar_code,invoice_number,");
		invoiceItemSql.append("(select ii.id from item_structure ist,item_type ii where ii.id > 10 ");
		if(single != null && single.length > 0)
			invoiceItemSql.append(" and (ii.id = 18 or ii.id like '8%') ");
		else
			invoiceItemSql.append(" and (ii.id = 16 or ii.id like '6%') ");
		invoiceItemSql.append(" and ist.item_type_id = ii.id and ist.invoice_structure_id = invoice_structure_id order by ii.id limit 1),1, ");
		if(single != null && single.length > 0)
			invoiceItemSql.append(" '"+single[index+3]+"',"+single[index]+","+single[index+2]+", ");
		else
			invoiceItemSql.append(" 'Adjustment(Manual)',"+adjustment+",");	
		invoiceItemSql.append(" now(),"+SystemUtil.getCurrentUserId()+",now(),"+SystemUtil.getCurrentUserId()+",'Y', :notes from invoice where id = "+invoiceId);
		
		logger.info("Exit method getModifyInvoiceItemPreviousAdjustmentSQL.");
		return invoiceItemSql.toString();
	}
	
	private String getModifyProposalSQL(String[] single,SearchInvoiceVO searchInvoiceVO,String invoiceItemId,int index) {
		
		logger.info("Enter method getModifyProposalSQL.");
		
		StringBuffer addProposalSQL = new StringBuffer("insert into proposal (invoice_id,invoice_item_id,item_type_id,account_code_id," +
		"proposal_flag,item_name,payment_amount,created_timestamp,created_by,modified_timestamp,modified_by,rec_active_flag,notes) values ( ");
		addProposalSQL.append(searchInvoiceVO.getInvoiceId()+","+invoiceItemId+", ");
		addProposalSQL.append("(select ii.id from item_structure ist,item_type ii where ii.id > 10 ");
		if(single != null && single.length > 0)
			addProposalSQL.append(" and (ii.id = 18 or ii.id like '8%') ");
		else
			addProposalSQL.append(" and (ii.id = 16 or ii.id like '6%') ");
		addProposalSQL.append(" and ist.item_type_id = ii.id and ist.invoice_structure_id = invoice_structure_id order by ii.id limit 1), ");
		addProposalSQL.append(" "+searchInvoiceVO.getScoaCodeId()+",'1', ");
		if(single != null && single.length > 0)
//			addProposalSQL.append(" 'Tax(Manual)', ");
			addProposalSQL.append(" '"+single[index+3]+"',"+single[index]+",");
		else
			addProposalSQL.append(" 'Adjustment(Manual)', "+searchInvoiceVO.getInvoiceAdjustment()+", ");
		addProposalSQL.append(" now(),"+SystemUtil.getCurrentUserId()+",now(),"+SystemUtil.getCurrentUserId()+",'Y',:notes) ");

		logger.info("Exit method getModifyProposalSQL.");
		return addProposalSQL.toString();
	}
	
	public String searchInvoiceItemId(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method searchInvoiceItemId.");
		final String sql = "select MAX(id) from invoice_item ii where ii.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and ii.rec_active_flag = 'Y'";
		HibernateTemplate template = this.getHibernateTemplate();
		String invoiceItemId =  (String) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return l.get(0).toString();
			}
		});
		logger.info("Exit method searchInvoiceItemId.");
		return invoiceItemId;
	}
	
	public int searchInvoiceItemProposalFlagIs2Record(SearchInvoiceVO searchInvoiceVO,Boolean adjustmentOrTax) {
		logger.info("Enter method searchInvoiceItemProposalFlagIs2Record.");
		
		StringBuffer sb = new StringBuffer("select count(1) from invoice_item ii where ii.invoice_id = "+searchInvoiceVO.getInvoiceId()+" and ii.proposal_flag = '2' and ii.rec_active_flag = 'Y' ");
		
		if(adjustmentOrTax)
			sb.append(" and ii.item_type_id = 6");	// adjustment
		else
			sb.append(" and ii.item_type_id = 8");	//tax
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		String count =  (String) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return l.get(0).toString();
			}
		});
		logger.info("Exit method searchInvoiceItemProposalFlagIs2Record.");
		return Integer.parseInt(count);
	}
	
	public List<String> searchInvoiceBalanceError(String event_journal_id) {
		logger.info("Enter method searchInvoiceBalanceError.");
		final String e_id = event_journal_id;
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select event_message from event_journal where id > "+e_id+" and message_type = 'ERROR' and key_data is not null").list();
			}
		});
		logger.info("Exit method searchInvoiceBalanceError.");
		return list;
	}
	
	public long getWorkspasePaymentInException(WorkspaceVO wvo) {
		logger.info("Enter method getWorkspasePaymentInException.");
		final String sql = getInvoiceWorkCountNOPaymentInException(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getWorkspasePaymentInException.");
		return count;
	}
	
	/**
	 * @Author Chao.liu Sep 25, 2010
	 * @return
	 */
	public List<InvoiceChannel> getInvoiceChannel(){
		logger.info("Enter method getInvoiceChannel.");
		List<InvoiceChannel> l = (List<InvoiceChannel>)this.getHibernateTemplate().find("from InvoiceChannel ");
		logger.info("Exit method getInvoiceChannel.");
		return l;
	}
	/**
	 * @Author Chao.liu Sep 25, 2010
	 * @return
	 */
	public List<InvoiceFormat> getInvoiceFormat(){
		logger.info("Enter method getInvoiceFormat.");
		List<InvoiceFormat> l = (List<InvoiceFormat>)this.getHibernateTemplate().find("from InvoiceFormat ");
		logger.info("Exit method getInvoiceFormat.");
		return l;
	}
	
	private String getInvoiceWorkCountNOPaymentInException(WorkspaceVO wVO) {
		logger.info("Enter method getInvoiceWorkCountNOPaymentInException.");
		StringBuffer sb = new StringBuffer("select count(1) a from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id)  ");
		sb.append("where i.rec_active_flag='Y' ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and  p.payment_status_id in (24, 25) ");
		logger.info("Exit method getInvoiceWorkCountNOPaymentInException.");
		return sb.toString();
	}

	public List<String> searchWorkspasePaymentInException(WorkspaceVO wvo) {
		logger.info("Enter method searchWorkspasePaymentInException.");
		final String queryString = getWorkSqlStringPaymentInException(wvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchWorkspasePaymentInException.");
		return list;
	}
	
	private String getWorkSqlStringPaymentInException(WorkspaceVO wVO) {
		logger.info("Enter method getWorkSqlStringPaymentInException.");
		StringBuffer sb = new StringBuffer();
		sb.append(WorkString());
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		
		sb.append("where i.rec_active_flag='Y' ");
		if(wVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(wVO.getFilter());
		}
		if(wVO.getUid()==-1){
			sb.append(" and i.assigned_analyst_id =  '"+SystemUtil.getCurrentUserId()+"' ");
		}else {
			sb.append(" and i.assigned_analyst_id =  '"+wVO.getUid()+"' ");
		}
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		sb.append(" and  p.payment_status_id in (24, 25) ");
		sb.append(" order by " + wVO.getSortField() + " " + wVO.getSortingDirection());
		sb.append(" LIMIT " + wVO.getStartIndex() + "," + wVO.getRecPerPage());
		logger.info("Exit method getWorkSqlStringPaymentInException.");
		return sb.toString();
	}
	
	private String WorkString(){
		StringBuffer sb = new StringBuffer();
		sb.append("select concat('{id:',i.id,',no:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",lob:\"',toJSON(b.line_of_business is null ,'',b.line_of_business),'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),'\",date:\"',toJSON(i.invoice_date is null,'',i.invoice_date),'\",due:\"',toJSON(i.invoice_due_date is null,'',i.invoice_due_date),'\",total:\"',");
		sb.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),'\",status:\"',s.invoice_status_name,");
		sb.append("'\",currentDue:\"',format(toJSON(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sb.append("'\",originalFlag:\"',toJSON(i.original_flag is null,'N',i.original_flag),");
		sb.append("'\",analyst:\"',toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("'\",paymentAmount:\"',format(toJSON(i.payment_amount is null,'',i.payment_amount),2),");
		sb.append("'\",disputeAmount:\"',format(toJSON(i.dispute_amount is null,'',i.dispute_amount),2),");
		sb.append("'\",miscAdjustmentAmount:\"',format(toJSON(i.misc_adjustment_amount is null,'',i.misc_adjustment_amount),2),");
		sb.append("'\",invoiceStatus:\"',toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
		sb.append("'\",paymentTransactionNumber:\"',toJSON(p.payment_transaction_number is null,'',p.payment_transaction_number),");
		sb.append("'\",paymentStatus:\"',toJSON(ps.payment_status_name is null,'',ps.payment_status_name),");
		sb.append("'\",paymentReferenceNumber:\"',toJSON(p.payment_reference_code is null,'',p.payment_reference_code),");
		sb.append("'\",paymentDate:\"',toJSON(p.payment_date is null,'',p.payment_date),");
		sb.append("'\",invoiceExpectingDate:\"',if(i.invoice_expecting_date is null,'',i.invoice_expecting_date),");
		sb.append("'\",lastDispute:\"',format(i.last_dispute is null,2),");
		sb.append("'\",lastPayment:\"',format(i.last_payment is null,2),");
		sb.append("'\",lastTotalDue:\"',format(i.last_total_due is null,2),");
		sb.append("'\",missingEmailFlag:\"',toJSON(b.Missing_Email_flag is null,'Y',b.Missing_Email_flag),");
		sb.append("'\",daysInStatus:\"',toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),'\"");
		sb.append("}') a ");
		return sb.toString();	
	}
	

	public Invoice load(Integer invoiceId) {
		return (Invoice)getHibernateTemplate().load(Invoice.class, invoiceId);
	}

	public Proposal loadProposal(Long disputeId) {
		return (Proposal)getHibernateTemplate().load(Proposal.class, disputeId);
	}

	public Proposal getProposal(Long disputeId) {
		return (Proposal)getHibernateTemplate().get(Proposal.class, disputeId);
	}

	public void merge(Invoice invoice) {
		getHibernateTemplate().merge(invoice);
	}

	public void updateHistoryFiles(AttachmentPoint point, Invoice resultInvoice) {
		Session session = getSession();
		try {
//			Query query = session.createSQLQuery("update transaction_history th set th.attachment_point_id="+(point == null ? "null" : point.getId()) +" where th.created_timestamp='"+CcmFormat.formatDateTime(modifiedTimestamp)+"'");
			Query query = session.createSQLQuery("update transaction_history th set th.attachment_point_id="+(point == null ? "null" : point.getId()) +" where invoice_id = "+ resultInvoice.getId() + " order by id desc limit 1");
			query.executeUpdate();
		} finally {
			releaseSession(session);
		}
//		
//		Query query = getSession().createQuery("from com.saninco.ccm.model.TransactionHistory th where th.createdTimestamp=?");
//		query.setDate(0, modifiedTimestamp);
//		List l = query.list();
//		if (l != null && l.size() > 0) {
//			TransactionHistory th = (TransactionHistory) l.get(0);
//			th.setAttachmentPoint(point);
//			getSession().merge(th);
//		}
	}
	//update by mingyang.li 2012.03.19
	public long getUnknownInvoicesCount(WorkspaceVO rvo) {
		logger.info("Enter method getUnknownInvoicesCount.");
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(1)");
		sb.append(" from (((((invoice i join vendor v) join ban b) join invoice_status s) join user u) join original o)");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append("and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id and o.invoice_id = i.id and o.created_timestamp = (select max(created_timestamp) as created_timestamp from original where bar_code = o.bar_code and rec_active_flag = 'Y')");
		if(rvo.getUid()==-1){
			sb.append(" and i.assigned_analyst_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and i.assigned_analyst_id="+rvo.getUid()+" ");
		}
		sb.append(" and b.ban_status_id=3 and b.master_ban_flag='Y' and b.rec_active_flag='Y'  ");
		if(rvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(rvo.getFilter());
		}

//		sb.append(" where i.ban_id=b.id");
//		sb.append(" and b.ban_status_id=3 and i.rec_active_flag='Y' and b.rec_active_flag='Y'");
//		if(rvo.getUid()==-1){
//			sb.append(" and i.assigned_analyst_id="+SystemUtil.getCurrentUserId()+" ");
//		}else{
//			sb.append(" and i.assigned_analyst_id="+rvo.getUid()+" ");
//		}
//		if(rvo.getFilter()!=null){
//			sb.append(" and " + rvo.getFilter());
//		}
		Session session = getSession();
		try {
			BigInteger b = (BigInteger)session.createSQLQuery(sb.toString()).uniqueResult();
			logger.info("Exit method getUnknownInvoicesCount.");
			return b.longValue();
		} finally {
			releaseSession(session);
		}
	}

	// Modified by Qiao Yang on 2011-08-29
	// Please further modify the query to complete all requirements
	// To do:
	public List<String> searchUnknownInvoices(WorkspaceVO wvo) {
		logger.info("Enter method searchUnknownInvoices.");
		StringBuffer sb = new StringBuffer();
		// Modified by Xin Huang on 2011-08-30 Start
		sb.append("select concat('{id:',i.id,',barCode:\"',toJSON(i.bar_code is null,'',i.bar_code),'\",vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
//		sb.append("select concat('{id:',i.id,',no:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
		// Modified by Xin Huang on 2011-08-30 End
		sb.append("'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),'\",date:\"',toJSON(i.invoice_date is null,'',i.invoice_date),'\",due:\"',toJSON(i.invoice_due_date is null,'',i.invoice_due_date),'\",total:\"',");
		sb.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),'\",status:\"',s.invoice_status_name,");
		sb.append("'\",currentDue:\"',format(toJSON(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sb.append("'\",originalFlag:\"',toJSON(i.original_flag is null,'N',i.original_flag),");
		sb.append("'\",analyst:\"',toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
//		sb.append("'\",paymentAmount:\"',format(toJSON(i.payment_amount is null,'',i.payment_amount),2),");
//		sb.append("'\",disputeAmount:\"',format(toJSON(i.dispute_amount is null,'',i.dispute_amount),2),");
//		sb.append("'\",miscAdjustmentAmount:\"',format(toJSON(i.misc_adjustment_amount is null,'',i.misc_adjustment_amount),2),");
		sb.append("'\",invoiceStatus:\"',toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
//		sb.append("'\",paymentTransactionNumber:\"',toJSON(p.payment_transaction_number is null,'',p.payment_transaction_number),");
//		sb.append("'\",paymentStatus:\"',toJSON(ps.payment_status_name is null,'',ps.payment_status_name),");
//		sb.append("'\",paymentReferenceNumber:\"',toJSON(p.payment_reference_code is null,'',p.payment_reference_code),");
//		sb.append("'\",paymentDate:\"',toJSON(p.payment_date is null,'',p.payment_date),");
		sb.append("'\",receiveDate:\"',toJSON(i.invoice_receive_date is null,'',i.invoice_receive_date),");
//		sb.append("'\",invoiceExpectingDate:\"',if(i.invoice_expecting_date is null,'',i.invoice_expecting_date),");
//		sb.append("'\",lastDispute:\"',format(i.last_dispute is null,2),");
//		sb.append("'\",lastPayment:\"',format(i.last_payment is null,2),");
//		sb.append("'\",lastTotalDue:\"',format(i.last_total_due is null,2),");
		// Add by Xin Huang on 2011-08-30 Start
		sb.append("'\",notes:\"',toJSON(i.notes is null,'',i.notes),");
		sb.append("'\",originalFlg:\"',toJSON(i.original_flag is null,'N',i.original_flag),");
		// Add by Xin Huang on 2011-08-30 End
		sb.append("'\",originalFileName:\"',toJSON(o.file_name is null,'',o.file_name),");
		sb.append("'\",originalFilePath:\"',toJSON(o.file_path is null,'',o.file_path),");
		sb.append("'\",daysInStatus:\"',toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),'\"");
		sb.append("}') a ");
		sb.append(" from (((((invoice i join vendor v) join ban b) join invoice_status s) join user u) join original o)");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append("and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id and o.invoice_id = i.id and o.created_timestamp = (select max(created_timestamp) as created_timestamp from original where bar_code = o.bar_code and rec_active_flag = 'Y')");
		if(wvo.getUid()==-1){
			sb.append(" and i.assigned_analyst_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and i.assigned_analyst_id="+wvo.getUid()+" ");
		}
		//update by mingyang.li 2012.03.19
		sb.append(" and b.ban_status_id=3 and b.master_ban_flag='Y' and b.rec_active_flag='Y' ");
		if(wvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(wvo.getFilter());
		}
		sb.append(wvo.getOrderByCause(null));
		sb.append(wvo.getLimitCause());
		Session session = getSession();
		try {
			List<String> r = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method searchUnknownInvoices.");
			return r;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getMissingInvoicesData(WorkspaceVO wvo) {
		logger.info("Enter method getMissingInvoicesData.");
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("toJSON(b.account_number is null ,'',b.account_number),");
		sb.append("toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("toJSON(i.original_flag is null,'N',i.original_flag),");
		sb.append("toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
		sb.append("toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),");
		sb.append("if(i.invoice_expecting_date is null,'',i.invoice_expecting_date),");
		sb.append("format(i.last_dispute is null,2),");
		sb.append("format(i.last_payment is null,2),");
		sb.append("format(i.last_total_due is null,2) ");
		sb.append(getWorkSqlStringWhereCause(wvo));
		Session session = getSession();
		try {
			List<Object[]> list = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getMissingInvoicesData.");
			return list;
		} finally {
			releaseSession(session);
		}
	}

	public AccountCode loadAccountCode(Integer accountCodeId) {
		// TODO Auto-generated method stub
		return (AccountCode)getHibernateTemplate().load(AccountCode.class, accountCodeId);
	}
	/**
	 * @author Donghao.Guo
	 * get Preload Invoices Data
	 * */
	public List<Object[]> getPreloadInvoiceData(WorkspaceVO wvo){
		logger.info("Enter method getPreloadInvoiceData.");
		StringBuffer sb = new StringBuffer();
		sb.append(" select ");
		sb.append("toJSON(i.invoice_number is null,'',i.invoice_number),");
		sb.append("toJSON(i.bar_code is null,'',i.bar_code),");
		sb.append("toJSON(b.account_number is null ,'',b.account_number),");
		sb.append("toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("toJSON(i.invoice_date is null,'',i.invoice_date),");
		sb.append("toJSON(i.invoice_due_date is null,'',i.invoice_due_date),");
		sb.append("toJSON(i.invoice_receive_date is null,'',i.invoice_receive_date),");
		sb.append("format(toJSON(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sb.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),");
		sb.append("toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
		sb.append("toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp))");
		sb.append(" from invoice i inner join vendor v on i.vendor_id = v.id inner join ban b on i.ban_id = b.id inner join invoice_status s on i.invoice_status_id = s.id inner join user u on u.id = i.assigned_analyst_id ");
		sb.append("where i.invoice_status_id>2 ");
		sb.append(" and i.invoice_status_id <9 and i.rec_active_flag='Y' ");
		if(wvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(wvo.getFilter());
		}
		if(wvo.getUid()==-1){
			sb.append(" and i.assigned_analyst_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and i.assigned_analyst_id="+wvo.getUid()+" ");
		}
		sb.append(" order by " + wvo.getSortField() + " " + wvo.getSortingDirection());
		sb.append(" LIMIT " + wvo.getStartIndex() + "," + wvo.getRecPerPage());
		logger.info("Exit method getInvoiceWorkSqlString.");
		Session session = getSession();
		try {
			List<Object[]> list = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getPreloadInvoiceData.");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	public List<Object[]> getDueDaysInvoiceData(WorkspaceVO wvo){
		logger.info("Enter method getDueDaysInvoiceData.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("toJSON(i.invoice_number is null,'',i.invoice_number),");
		sb.append("toJSON(b.account_number is null ,'',b.account_number),");
		sb.append("toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("toJSON(i.invoice_date is null,'',i.invoice_date),");
		sb.append("format(toJSON(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sb.append("toJSON(i.original_flag is null,'N',i.original_flag),");
		sb.append("toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("toJSON(i.invoice_due_date is null,'',i.invoice_due_date),");
		sb.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),");		
		sb.append("format(toJSON(i.payment_amount is null,'',i.payment_amount),2),");
		sb.append("format(toJSON(i.dispute_amount is null,'',i.dispute_amount),2),");
		sb.append("format(toJSON(i.misc_adjustment_amount is null,'',i.misc_adjustment_amount),2),");
		sb.append("toJSON(s.invoice_status_name is null,'',s.invoice_status_name),");
		sb.append("toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)), ");
		sb.append("toJSON(ps.payment_status_name is null,'',ps.payment_status_name),");
		sb.append("toJSON(p.payment_date is null,'',p.payment_date),");
		sb.append("toJSON(p.payment_transaction_number is null,'',p.payment_transaction_number),");
		sb.append("toJSON(p.payment_reference_code is null,'',p.payment_reference_code)");


/*        sb.append("if(i.invoice_expecting_date is null,'',i.invoice_expecting_date),");
		sb.append("format(i.last_dispute is null,2),");
		sb.append("format(i.last_payment is null,2),");
		sb.append("format(i.last_total_due is null,2),");
		sb.append("toJSON(b.Missing_Email_flag is null,'Y',b.Missing_Email_flag)");*/
		
		sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append("where i.rec_active_flag='Y' ");
		sb.append(" and i.invoice_status_id >=9 ");
		sb.append(" and i.invoice_status_id <>98 ");
		if(wvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(wvo.getFilter());
		}
		if(wvo.getUid()== -1){
			sb.append(" and (i.assignment_user_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and (i.assignment_user_id="+wvo.getUid()+" ");
		}
		sb.append(" and i.flag_workspace='Y' )");
		sb.append(" and b.rec_active_flag = 'Y' and b.master_ban_flag = 'Y' and b.ban_status_id = 1 ");
		if (wvo.getDueDay()!=null) {
			if(wvo.getDueDay()==97){
				sb.append(" and i.invoice_due_date <= adddate(date_format(now(), '%Y/%m/%d'),interval 7 day) and i.invoice_due_date >= date(date_format(now(), '%Y/%m/%d')) ");
			}else if(wvo.getDueDay()==915){
				sb.append(" and i.invoice_due_date <= adddate(date_format(now(), '%Y/%m/%d'),interval 15 day) and i.invoice_due_date >= date(date_format(now(), '%Y/%m/%d')) ");				
			}else if(wvo.getDueDay()==90){
				sb.append(" and i.invoice_due_date = date_format(now(), '%Y/%m/%d')");				
			}
			else if(wvo.getDueDay()>0 && wvo.getDueDay()<16){
				sb.append(" and i.invoice_due_date =adddate(date_format(now(), '%Y/%m/%d'),interval "+wvo.getDueDay()+" day)");			
			}
			else{	
				sb.append(" ");
            }
		}
        sb.append(" and  (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id ");
		
		sb.append("order by i.id asc LIMIT " + wvo.getStartIndex() + "," + wvo.getRecPerPage());
		logger.info("Exit method getInvoiceWorkSqlString.");
		Session session = getSession();
		try {
			List<Object[]> list = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getDueDaysInvoiceData.");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author Donghao.Guo
	 *  Preload Invoices 
	 * */
	private String PreloadInvoiceWorkString(){
		StringBuffer sb = new StringBuffer();
		sb.append("select concat('{id:',i.id,',barCode:\"',toJSON(i.bar_code is null,'',i.bar_code),'\",no:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",lob:\"',toJSON(b.line_of_business is null ,'',b.line_of_business),'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),'\",date:\"',toJSON(i.invoice_date is null,'',i.invoice_date),'\",due:\"',toJSON(i.invoice_due_date is null,'',i.invoice_due_date),'\",total:\"',");
		sb.append("toJSON(i.invoice_total_due is null,'',concat('',format(i.invoice_total_due, 2))),'\",status:\"',s.invoice_status_name,");
		sb.append("'\",receiveDate:\"',toJSON(i.invoice_receive_date is null,'',i.invoice_receive_date),");
		sb.append("'\",currentDue:\"',format(toJSON(i.invoice_current_due is null,0,i.invoice_current_due),2),");
		sb.append("'\",analyst:\"',toJSON(i.assigned_analyst_id is null,'',concat(u.first_name,' ',u.last_name)),");
		sb.append("'\",daysInStatus:\"',toJSON(i.status_timestamp is null,'',datediff(now(),i.status_timestamp)),'\"");
		sb.append("}') a ");
		return sb.toString();	
	}

	public String removeMissingInvoiceByInvoiceId(Invoice invoice,String missingEmailflag) {
		logger.info("Enter method removeMissingInvoiceByInvoiceId.");
		StringBuilder sb = new StringBuilder();
		Integer banId = findById(invoice.getId()).getBan().getId();
		Session session = getSession();
		try {
			session.createSQLQuery("update ban b set b.Missing_Email_flag = '" + missingEmailflag + "',b.system_modified_mif_flag = 'N',b.modified_timestamp='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',b.modified_by=" + SystemUtil.getCurrentUserId() 
					+ " where b.id=" + banId + " AND b.Missing_Email_flag != '" + missingEmailflag + "'").executeUpdate();
			session.createSQLQuery("update invoice i set i.notes='" + CcmUtil.stringToSql(invoice.getNotes()) + "',i.rec_active_flag='N',i.modified_timestamp='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',i.modified_by=" + SystemUtil.getCurrentUserId() + " where i.id=" + invoice.getId()).executeUpdate();
//		session.createSQLQuery("update email e set e.rec_active_flag='N',e.modified_timestamp='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',e.modified_by=" + SystemUtil.getCurrentUserId() + " where e.email_type='I' and e.email_status<>2 and e.reference_id=" + invoice.getId() + " and e.rec_active_flag='Y'").executeUpdate();
			sb.append("{info:'success'}");
			logger.info("Exit method removeMissingInvoiceByInvoiceId.");
			return sb.toString();
		} finally {
			releaseSession(session);
		}
	}

	public String removeUnknownInvoiceByInvoiceId(Invoice invoice) {
		logger.info("Enter method removeUnknownInvoiceByInvoiceId.");
		StringBuilder sb = new StringBuilder();
		Integer banId = findById(invoice.getId()).getBan().getId();
		Session session = getSession();
		try {
			session.createSQLQuery("update ban b set b.notes='" + invoice.getNotes() + "',b.ban_status_id=2,b.modified_timestamp='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',b.modified_by=" + SystemUtil.getCurrentUserId() 
					+ " where b.id=" + banId).executeUpdate();
			session.createSQLQuery("update invoice i set i.rec_active_flag='N',i.modified_timestamp='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',i.modified_by=" + SystemUtil.getCurrentUserId() + " where i.id=" + invoice.getId()).executeUpdate();
			sb.append("{info:'success'}");
			logger.info("Exit method removeUnknownInvoiceByInvoiceId.");
			return sb.toString();
		} finally {
			releaseSession(session);
		}
	}
	
	public List<Object[]> getUnknownInvoicesData(WorkspaceVO wvo,List<Integer> ids) {
		logger.info("Enter method getUnknownInvoicesData.");
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ifnull(i.bar_code, '') as bar_code,ifnull(b.account_number, '') as account_number,ifnull(v.vendor_name, '') as vendor_name,");
        sb.append("ifnull(i.invoice_date, '') as invoice_date,ifnull(i.invoice_due_date, '') as invoice_due_date,format(ifnull(i.invoice_current_due, 0),2) as invoice_current_due,");
        sb.append("ifnull(concat('', format(i.invoice_total_due, 2)),'') as invoice_total_due,ifnull(concat(u.first_name, ' ', u.last_name),'') as analyst,ifnull(i.invoice_receive_date, '') as invoice_receive_date,");
        sb.append("ifnull(s.invoice_status_name, '') as invoice_status_name,ifnull(datediff(now(), i.status_timestamp),'') as status_timestamp,ifnull(o.file_path, '') as file_path,");
        sb.append("ifnull(i.notes, '') as notes ");
        sb.append("FROM (((((invoice i JOIN vendor v) JOIN ban b) JOIN invoice_status s) JOIN user u) JOIN original o) ");
        sb.append("where i.rec_active_flag='Y' ");
		sb.append("and (i.vendor_id = v.id) and (i.ban_id = b.id)  and i.invoice_status_id = s.id  and u.id = i.assigned_analyst_id and o.invoice_id = i.id and o.created_timestamp = (select max(created_timestamp) as created_timestamp from original where bar_code = o.bar_code and rec_active_flag = 'Y')");
		if(wvo.getUid()==-1){
			sb.append(" and i.assigned_analyst_id="+SystemUtil.getCurrentUserId()+" ");
		}else{
			sb.append(" and i.assigned_analyst_id="+wvo.getUid()+" ");
		}
		sb.append(" and b.ban_status_id=3 and b.master_ban_flag='Y' and b.rec_active_flag='Y' ");
		if(wvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(wvo.getFilter());
		}
		sb.append(" AND i.id in (" + ids.toString().substring(1,ids.toString().length()-1) + ") ");
		sb.append(wvo.getOrderByCause(null));
		sb.append(wvo.getLimitCause());
		Session session = getSession();
		try {
			List<Object[]> list = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getUnknownInvoicesData.");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	public String updateInvoiceOrGetStatusIdAndDisputeAmountByINumber(
			SearchInvoiceVO searchInvoiceVO) {
		Session session = getSession();
		try {
			String result = "";
//		List invoiceList = session.createSQLQuery("select i.invoice_status_id,(select if(sum(d.dispute_amount)>0,1,0)  from dispute d where d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_amount,(select count(1)  from reconcile r where r.credit_invoice_id = " + searchInvoiceVO.getInvoiceId() + " and r.rec_active_flag='Y') as is_reconcile,(select count(1)  from dispute d where d.dispute_status_id not in (98,99) and d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_status_count,(select count(1)  from dispute d where d.dispute_status_id in (98,99) and d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_status_count2 from invoice i where i.id=" + searchInvoiceVO.getInvoiceId() + " and i.rec_active_flag='Y'").list();
			List invoiceList = session.createSQLQuery("select i.invoice_status_id,(select count(1)  from dispute d where d.dispute_status_id not in (98,99) and d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_status_count from invoice i where i.id=" + searchInvoiceVO.getInvoiceId() + " and i.rec_active_flag='Y'").list();
			if (invoiceList.size() > 0) {
				Object[] objects = (Object[])invoiceList.get(0);
				Integer status = (Integer)objects[0];
//			Integer disputeAmountCount = ((BigInteger)objects[1]).intValue();
//			Integer isReconcile = ((BigInteger)objects[2]).intValue();
				Integer disputeStatusNotIn9899Count = ((BigInteger)objects[1]).intValue();
//			Integer disputeStatusNotIn9899Count = ((BigInteger)objects[3]).intValue();
//			Integer disputeStatusIn9899Count = ((BigInteger)objects[4]).intValue();
				StringBuffer sb = new StringBuffer("select concat('{id:',i.id,',no:\"',if(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',if(v.vendor_name is null ,'',v.vendor_name),");
				sb.append("'\",ban:\"',if(b.account_number is null ,'',b.account_number),'\",date:\"',if(i.invoice_date is null,'',i.invoice_date),'\",invoiceCurrentDue:\"',if(i.invoice_current_due is null,0,i.invoice_current_due),");
				sb.append("'\",invoiceStatus:\"',if(s.invoice_status_name is null,'',s.invoice_status_name),'\"");
				sb.append("}') a ");
				sb.append(" from (((invoice i join vendor v) join ban b) join invoice_status s ) ");
				sb.append(" where i.rec_active_flag = 'Y' and i.id=" + searchInvoiceVO.getInvoiceId() + " and (i.vendor_id = v.id) and (i.ban_id = b.id) and (i.invoice_status_id = s.id) ");
				String searchResult = session.createSQLQuery(sb.toString()).list().get(0).toString();
				if(status == 7){			
					result = searchResult.substring(0,searchResult.length()-1) + ",'cancel':true}";
				}else {
					if (disputeStatusNotIn9899Count > 0) {
						result = "{'info':'Can\\'t close the  invoice with open dispute!'}";
					}else{			
						if (status >= 9 && status < 41) {
							result = searchResult.substring(0,searchResult.length()-1) + ",'cancelOrClose':true}";
						}
						if (status == 41) {
							result = searchResult.substring(0,searchResult.length()-1) + ",'cancelOrCloseAP':true}";
						}	
					}
				}
			}
			
			result = "".equals(result) ? "{'info':'Not valid invoice!'}" : result;
			
			return result;
		} finally {
			releaseSession(session);
		}
	}

	public long getNumberOfInvoicesByNumber(SearchInvoiceVO searchInvoiceVO,
			int userId) {
		logger.info("Enter method getNumberOfInvoicesByNumber.");
		final String sql = this.getInvoiceSearchNumberQueryStringByNumber(searchInvoiceVO, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfInvoicesByNumber.");
		return count;
	}	
	
	private String getInvoiceSearchNumberQueryStringByNumber(SearchInvoiceVO sio, int userId){
		logger.info("Enter method getInvoiceSearchNumberQueryStringByNumber.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"$1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select count(1) a ");
		//sb.append(" from ((((invoice i join vendor v) join ban b) join invoice_status s) join user u) left join payment p on (i.id = p.invoice_id and p.rec_active_flag = 'Y') left join payment_status ps on (ps.id = p.payment_status_id) ");
		sb.append(" from (((invoice i join vendor v) join ban b) join invoice_status s ) ");
		sb.append(voPTOWWhereConditions(sio,userId));
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id) and (i.invoice_status_id = s.id) ");
		logger.info("Exit method getInvoiceSearchNumberQueryStringByNumber.");
		return sb.toString();
	}
	
	private String voPTOWWhereConditions(SearchInvoiceVO sio, int userId) {
		logger.info("Enter method voPTOWWhereConditions.");
		StringBuffer sb = new StringBuffer();
		
		sb.append(" where 1=1 ");
		sb.append(" and i.rec_active_flag = 'Y' and i.invoice_status_id not in (80,98,99) ");
		
		if(sio.getFilter()!=null){
			sb.append(" and ");
			sb.append(sio.getFilter());
		}	
		if(sio.getInvoiceNumber()!=null) {
			sb.append(" and i.invoice_number = '"+sio.getInvoiceNumber()+"' ");
		}
		logger.info("Enter method voPTOWWhereConditions.");
	
		return sb.toString();
	}

	public List<String> searchInvoiceByINumber(SearchInvoiceVO searchInvoiceVO,
			int userId) {
		logger.info("Enter method searchInvoiceByINumber.");
		final String sql = this.getInvoiceSearchQueryStringByINumber(searchInvoiceVO, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoiceByINumber.");
		return l;
	}
	
	private String getInvoiceSearchQueryStringByINumber(SearchInvoiceVO sio, int userId){
		logger.info("Enter method getInvoiceSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',i.id,',no:\"',if(i.invoice_number is null,'',i.invoice_number),'\",vendor:\"',if(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",ban:\"',if(b.account_number is null ,'',b.account_number),'\",date:\"',if(i.invoice_date is null,'',i.invoice_date),'\",invoiceCurrentDue:\"',if(i.invoice_current_due is null,'',i.invoice_current_due),");
		sb.append("'\",invoiceStatus:\"',if(s.invoice_status_name is null,'',s.invoice_status_name),'\",invoiceStatusId:\"',if(i.invoice_status_id is null,'',i.invoice_status_id),'\"");
		sb.append("}') a ");
		
		sb.append(" from (((invoice i join vendor v) join ban b) join invoice_status s ) ");
		
		sb.append(voPTOWWhereConditions(sio,userId));
		sb.append(" and (i.vendor_id = v.id) and (i.ban_id = b.id) and (i.invoice_status_id = s.id) ");
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getInvoiceSearchQueryString.");
		return sb.toString();
	}

	public void closeInvoice(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method closeInvoice.");
		String result = "";
//		List invoiceList = getSession().createSQLQuery("select (select if(sum(d.dispute_amount)>0,1,0)  from dispute d where d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_amount,(select count(1)  from reconcile r where r.credit_invoice_id = " + searchInvoiceVO.getInvoiceId() + " and r.rec_active_flag='Y' and r.reconcile_amount>0) as is_reconcile from invoice i where i.id=" + searchInvoiceVO.getInvoiceId() + " and i.rec_active_flag='Y'").list();
//		if (invoiceList.size() > 0) {
//			Object[] objects = (Object[])invoiceList.get(0);
//			Integer disputeAmount = ((BigInteger)objects[0]).intValue();
//			Integer countReconcile = ((BigInteger)objects[1]).intValue();
//			if (disputeAmount > 0 || countReconcile > 0) {
//				try {
//					Connection con = getSession().connection();
//					con.setReadOnly(false);
//					CallableStatement proc = null;
//					proc = con.prepareCall("call SP_CANCEL_PAYMENT_AND_REJECT_INVOICE_PROCESS(?,?,?)");
//					proc.setInt(1, SystemUtil.getCurrentUserId());
//					proc.setInt(2, Integer.parseInt(searchInvoiceVO.getInvoiceId()));
//					proc.setString(3, result);
//					proc.execute();
//					result = proc.getString(3);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					result = "{'info':'Error!'}";
//					e.printStackTrace();
//				}
//			}
//		}
		Session session = getSession();
		try {
			session.createSQLQuery("update invoice i set i.invoice_status_id = 99,i.modified_by = 0,i.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',i.workflow_action_id = 5,i.workflow_user_id = 0,i.assignment_user_id = 0,i.flag_workspace = 'N',i.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',i.notes='" + searchInvoiceVO.getNote() + "' where i.rec_active_flag='Y' and i.id=" + searchInvoiceVO.getInvoiceId()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		if(searchInvoiceVO.isUpPayment()){
			this.closePayment(searchInvoiceVO);
		}
		logger.info("Exit method closeInvoice.");
	}
	
	
	public void cleanExternalApprovalNotes(String invoiceId) {
		logger.info("Enter method cleanExternalApprovalNotes.");

		Session session = getSession();
		try {
			session.createSQLQuery("update transaction_history set rec_active_flag = 'N' where invoice_status_id in (16,17,18) and invoice_id = " + invoiceId).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method cleanExternalApprovalNotes.");
	}
	
	public void closePayment (SearchInvoiceVO searchInvoiceVO){
		StringBuffer sb = new StringBuffer("");
		sb.append(" update payment p");
		sb.append(" set p.payment_status_id = 99,");
		if(searchInvoiceVO.getPaymentReferenceCode() !=null && !"".equals(searchInvoiceVO.getPaymentReferenceCode())){
			sb.append("p.payment_reference_code = '" + searchInvoiceVO.getPaymentReferenceCode() + "',");
		}else{
			sb.append("p.payment_reference_code = null,");
		}
		if(searchInvoiceVO.getPaidDate() !=null && !"".equals(searchInvoiceVO.getPaidDate())){
			sb.append("p.paid_date = '" + searchInvoiceVO.getPaidDate() + "',");
		}else{
			sb.append("p.paid_date = null,");
		}
		sb.append(" p.modified_by = 0,");
		sb.append(" p.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"',");
		sb.append(" p.workflow_action_id = 4,");
		sb.append(" p.workflow_user_id = 0,");
		sb.append(" p.flag_workspace = 'N',");
		sb.append(" p.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'");
		sb.append(" where p.payment_status_id not in (98,99)");
		sb.append(" and p.invoice_id="+ searchInvoiceVO.getInvoiceId());
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	
	public void cancelPayment (SearchInvoiceVO searchInvoiceVO){
		StringBuffer sb = new StringBuffer("");
		sb.append(" update payment p");
		sb.append(" set p.payment_status_id = 98,");
		if(searchInvoiceVO.getPaymentReferenceCode() !=null && !"".equals(searchInvoiceVO.getPaymentReferenceCode())){
			sb.append("p.payment_reference_code = '" + searchInvoiceVO.getPaymentReferenceCode() + "',");
		}else{
			sb.append("p.payment_reference_code = null,");
		}
		if(searchInvoiceVO.getPaidDate() !=null && !"".equals(searchInvoiceVO.getPaidDate())){
			sb.append("p.paid_date = '" + searchInvoiceVO.getPaidDate() + "',");
		}else{
			sb.append("p.paid_date = null,");
		}
		sb.append(" p.modified_by = 0,");
		sb.append(" p.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"',");
		sb.append(" p.workflow_action_id = 4,");
		sb.append(" p.workflow_user_id = 0,");
		sb.append(" p.flag_workspace = 'N',");
		sb.append(" p.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'");
		sb.append(" where p.payment_status_id not in (98,99)");
		sb.append(" and p.invoice_id="+ searchInvoiceVO.getInvoiceId());
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	
	public void cancelInvoice(SearchInvoiceVO searchInvoiceVO) {
		String result = "";
//		List invoiceList = getSession().createSQLQuery("select (select if(sum(d.dispute_amount)>0,1,0)  from dispute d where d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_amount,(select count(1)  from reconcile r where r.credit_invoice_id = " + searchInvoiceVO.getInvoiceId() + " and r.rec_active_flag='Y' and r.reconcile_amount>0) as is_reconcile from invoice i where i.id=" + searchInvoiceVO.getInvoiceId() + " and i.rec_active_flag='Y'").list();
//		if (invoiceList.size() > 0) {
//			Object[] objects = (Object[])invoiceList.get(0);
//			Integer disputeAmount = ((BigInteger)objects[0]).intValue();
//			Integer countReconcile = ((BigInteger)objects[1]).intValue();
//			if (disputeAmount > 0 || countReconcile > 0) {
//				try {
//					Connection con = getSession().connection();
//					con.setReadOnly(false);
//					CallableStatement proc = null;
//					proc = con.prepareCall("call SP_CANCEL_PAYMENT_AND_REJECT_INVOICE_PROCESS(?,?,?)");
//					proc.setInt(1, SystemUtil.getCurrentUserId());
//					proc.setInt(2, Integer.parseInt(searchInvoiceVO.getInvoiceId()));
//					proc.setString(3, result);
//					proc.execute();
//					result = proc.getString(3);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					result = "{'info':'Error!'}";
//					e.printStackTrace();
//				}
//			}
//		}
		Session session = getSession();
		try {
			session.createSQLQuery("update invoice i set i.invoice_status_id = 98 , i.rec_active_flag = 'Y',i.modified_by = 0,i.modified_timestamp = '" 
					+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
					+ "',i.workflow_action_id = 5,i.workflow_user_id = 0,i.assignment_user_id = 0,i.flag_workspace = 'N',i.status_timestamp = '" 
					+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) 
					+ "',i.notes='" + searchInvoiceVO.getNote() + "' where i.rec_active_flag='Y' and i.id=" 
					+ searchInvoiceVO.getInvoiceId()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		if(searchInvoiceVO.isUpPayment()){
			this.cancelPayment(searchInvoiceVO);
		}
	}
	
	public void removeInvoice(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method removeInvoice.");
		String result = "";
//		List invoiceList = getSession().createSQLQuery("select (select if(sum(d.dispute_amount)>0,1,0)  from dispute d where d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_amount,(select count(1)  from reconcile r where r.credit_invoice_id = " + searchInvoiceVO.getInvoiceId() + " and r.rec_active_flag='Y' and r.reconcile_amount>0) as is_reconcile from invoice i where i.id=" + searchInvoiceVO.getInvoiceId() + " and i.rec_active_flag='Y'").list();
//		if (invoiceList.size() > 0) {
//			Object[] objects = (Object[])invoiceList.get(0);
//			Integer disputeAmount = ((BigInteger)objects[0]).intValue();
//			Integer countReconcile = ((BigInteger)objects[1]).intValue();
//			if (disputeAmount > 0 || countReconcile > 0) {
//				try {
//					Connection con = getSession().connection();
//					con.setReadOnly(false);
//					CallableStatement proc = null;
//					proc = con.prepareCall("call SP_CANCEL_PAYMENT_AND_REJECT_INVOICE_PROCESS(?,?,?)");
//					proc.setInt(1, SystemUtil.getCurrentUserId());
//					proc.setInt(2, Integer.parseInt(searchInvoiceVO.getInvoiceId()));
//					proc.setString(3, result);
//					proc.execute();
//					result = proc.getString(3);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					result = "{'info':'Error!'}";
//					e.printStackTrace();
//				}
//			}
//		}
		Session session = getSession();
		try {
			session.createSQLQuery("update invoice i set i.rec_active_flag = 'N',i.modified_by = 0,i.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',i.workflow_action_id = 5,i.workflow_user_id = 0,i.assignment_user_id = 0,i.flag_workspace = 'N',i.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',i.notes='" + searchInvoiceVO.getNote() + "' where i.rec_active_flag='Y' and i.id=" + searchInvoiceVO.getInvoiceId()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method removeInvoice.");
	}

	public String tRegularClosedInvoice(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method tRegularClosedInvoice.");
		StringBuilder sb = new StringBuilder();
		String result = "Success";
//		List invoiceList = getSession().createSQLQuery("select (select if(sum(d.dispute_amount)>0,1,0)  from dispute d where d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_amount,(select count(1)  from reconcile r where r.credit_invoice_id = " + searchInvoiceVO.getInvoiceId() + " and r.rec_active_flag='Y' and r.reconcile_amount>0) as is_reconcile from invoice i where i.id=" + searchInvoiceVO.getInvoiceId() + " and i.rec_active_flag='Y'").list();
//		if (invoiceList.size() > 0) {
//			Object[] objects = (Object[])invoiceList.get(0);
//			Integer disputeAmount = ((BigInteger)objects[0]).intValue();
//			Integer countReconcile = ((BigInteger)objects[1]).intValue();
//			if (disputeAmount > 0 || countReconcile > 0) {
//				try {
//					Connection con = getSession().connection();
//					con.setReadOnly(false);
//					CallableStatement proc = null;
//					proc = con.prepareCall("call SP_CANCEL_PAYMENT_AND_REJECT_INVOICE_PROCESS(?,?,?)");
//					proc.setInt(1, SystemUtil.getCurrentUserId());
//					proc.setInt(2, Integer.parseInt(searchInvoiceVO.getInvoiceId()));
//					proc.setString(3, result);
//					proc.execute();
//					result = proc.getString(3);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					result = "Error";
//					e.printStackTrace();
//				}
//			}
//		}
		sb.append("update payment p,invoice i set ");
		sb.append("i.invoice_status_id = 99,");
		sb.append("i.modified_by = 0,");
		sb.append("i.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',");
		sb.append("i.workflow_action_id = 5,");
		sb.append("i.workflow_user_id = 0,");
		sb.append("i.assignment_user_id = 0,");
		sb.append("i.flag_workspace = 'N',");
		sb.append("i.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',");
		sb.append("i.notes='" + searchInvoiceVO.getNote() + "',");
		sb.append("p.payment_status_id = 99,");
		sb.append("p.modified_by = 0,");
		sb.append("p.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',");
		sb.append("p.workflow_action_id = 4,");
		sb.append("p.workflow_user_id = 0,");
		sb.append("p.flag_workspace = 'N',");
		sb.append("p.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' where p.invoice_id = i.id and p.rec_active_flag='Y' and i.rec_active_flag='Y' and i.id=" + searchInvoiceVO.getInvoiceId());
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method tRegularClosedInvoice.");
		return "Success".equals(result) ? "Success" : "Error";
	}

	public String throughAPPaymentClosedInvoice(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method throughAPPaymentClosedInvoice.");
		StringBuilder sb = new StringBuilder();
		String result = "Success";
//		List invoiceList = getSession().createSQLQuery("select (select if(sum(d.dispute_amount)>0,1,0)  from dispute d where d.invoice_id = " + searchInvoiceVO.getInvoiceId() + " and d.rec_active_flag='Y') as dispute_amount,(select count(1)  from reconcile r where r.credit_invoice_id = " + searchInvoiceVO.getInvoiceId() + " and r.rec_active_flag='Y' and r.reconcile_amount>0) as is_reconcile from invoice i where i.id=" + searchInvoiceVO.getInvoiceId() + " and i.rec_active_flag='Y'").list();
//		if (invoiceList.size() > 0) {
//			Object[] objects = (Object[])invoiceList.get(0);
//			Integer disputeAmount = ((BigInteger)objects[0]).intValue();
//			Integer countReconcile = ((BigInteger)objects[1]).intValue();
//			if (disputeAmount > 0 || countReconcile > 0) {
//				try {
//					Connection con = getSession().connection();
//					con.setReadOnly(false);
//					CallableStatement proc = null;
//					proc = con.prepareCall("call SP_CANCEL_PAYMENT_AND_REJECT_INVOICE_PROCESS(?,?,?)");
//					proc.setInt(1, SystemUtil.getCurrentUserId());
//					proc.setInt(2, Integer.parseInt(searchInvoiceVO.getInvoiceId()));
//					proc.setString(3, result);
//					proc.execute();
//					result = proc.getString(3);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					result = "Error";
//					e.printStackTrace();
//				}
//			}
//		}
		sb.append("update invoice i set ");
		sb.append("i.invoice_status_id = 99,");
		sb.append("i.modified_by = 0,");
		sb.append("i.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',");
		sb.append("i.workflow_action_id = 5,");
		sb.append("i.workflow_user_id = 0,");
		sb.append("i.assignment_user_id = 0,");
		sb.append("i.flag_workspace = 'N',");
		sb.append("i.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',");
		sb.append("i.notes='" + searchInvoiceVO.getNote()+"' ");
		sb.append("where i.rec_active_flag='Y' and i.id=" + searchInvoiceVO.getInvoiceId());
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		
			sb = new StringBuilder();
			sb.append("update payment p set ");
			if(searchInvoiceVO.getPaymentReferenceCode() !=null && !"".equals(searchInvoiceVO.getPaymentReferenceCode())){
				sb.append("p.payment_reference_code = '" + searchInvoiceVO.getPaymentReferenceCode() + "',");
			}else{
				sb.append("p.payment_reference_code = null,");
			}
			
			if(searchInvoiceVO.getPaidDate() !=null && !"".equals(searchInvoiceVO.getPaidDate())){
				sb.append("p.paid_date = '" + searchInvoiceVO.getPaidDate() + "',");
			}else{
				sb.append("p.paid_date = null,");
			}
			
			sb.append("p.payment_status_id = 26,");
			sb.append("p.modified_by = 0,");
			sb.append("p.modified_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',");
			sb.append("p.workflow_action_id = 4,");
			sb.append("p.workflow_user_id = 0,");
			sb.append("p.flag_workspace = 'N',");
			sb.append("p.status_timestamp = '" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' ");
			sb.append("where p.rec_active_flag='Y' and p.payment_status_id not in (98,99) and p.invoice_id=" + searchInvoiceVO.getInvoiceId());
			session.createSQLQuery(sb.toString()).executeUpdate();
			logger.info("Exit method throughAPPaymentClosedInvoice.");
			return "Success".equals(result) ? "Success" : "Error";
		} finally {
			releaseSession(session);
		}
	}
	
	public List<String> searchInvoiceLabelsByLabelId(String labelIds){
		
		logger.info("Enter method searchInvoiceLabel.");
		final String sql = this.getsearchInvoiceLabelsByLabelId(labelIds);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoiceLabel.");
		return l;
	}
	
	private String getsearchInvoiceLabelsByLabelId(String labelIds){
		logger.info("Enter method getsearchInvoiceLabel.");
		String[] labelList = labelIds.split(",");
		StringBuffer sb = new StringBuffer("select concat('{labelId:',l.id,',labelName:\"',toJSON(l.label_name is null,'',l.label_name),'\",labelIcon:\"',toJSON(l.icon_file_path is null,'',l.icon_file_path),'\"}') a  from label l where id in ( ");
		for(String item : labelList){
			sb.append(Integer.parseInt(item) + ",");
		}
		int len = sb.length();
		if(len >0){
			sb.deleteCharAt(len-1);
		}
		sb.append(" )");
		logger.info("Exit method getsearchInvoiceLabel.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchInvoiceLabelsByInvoiceId(String invoiceId){
		
		logger.info("Enter method searchInvoiceLabelsByInvoiceId.");
		final String sql = this.getsearchInvoiceLabelsByInvoiceId(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchInvoiceLabelsByInvoiceId.");
		return l;
	}
	
	private String getsearchInvoiceLabelsByInvoiceId(String invoiceId){
		logger.info("Enter method getsearchInvoiceLabelsByInvoiceId.");
		StringBuffer sb = new StringBuffer("select i.id,i.description,l.label_name,l.icon_file_path from invoice_label i, label l, label_type lt " +
				  						   "where i.label_id = l.id and l.label_type_id = lt.id and lt.label_type_name = 'Invoice' and i.rec_active_flag = 'Y' and i.invoice_id = "+invoiceId+" " +
				  						   "and l.icon_file_path IS NOT NULL and l.icon_file_path <> '' and l.id <> '6' order by l.label_type_id, i.id");
		logger.info("Exit method getsearchInvoiceLabelsByInvoiceId.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getOriginalListByInvoiceId(int invoiceId){
		
		logger.info("Enter method getOriginalListByInvoiceId.");
		final String sql = this.getOriginalListSearchQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<HashMap<String, String>> l = (List<HashMap<String, String>>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			}
		});
		logger.info("Exit method getOriginalListByInvoiceId.");
		return l;
	}
	
	
	private String getOriginalListSearchQueryString(int invoiceId){
		logger.info("Enter method getsearchInvoiceLabelsByInvoiceId.");
		StringBuffer sb = new StringBuffer("select o.id original_id , o.file_name , o.invoice_id from original o where o.rec_active_flag = 'Y' and o.invoice_id =" + invoiceId);
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getOriginalDetail(int originalId){
		
		logger.info("Enter method getOriginalDetail.");
		final String sql = "select file_path,file_name from original where id = "+originalId;
		HibernateTemplate template = this.getHibernateTemplate();
		List<HashMap<String, String>> list = (List<HashMap<String, String>>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			}
		});
		logger.info("Exit method getOriginalDetail.");
		return list.size() == 1 ? list.get(0) : null;
	}

	public Object[] searchInvoiceAuditStatus (String invoiceId){
		logger.info("Enter method searchInvoiceAuditStatus.");
		Session session = getSession();
		try {
			Object[] obj = (Object[]) session.createSQLQuery( "select  id,payment_audit_status_id, tax_audit_status_id, mrc_audit_status_id, occ_audit_status_id, usage_audit_status_id, adjustment_audit_status_id ,lpc_audit_status_id, current_due_audit_status_id from invoice_audit_status where invoice_id ="+invoiceId)
			.uniqueResult();
			logger.info("Exit method searchInvoiceAuditStatus.");
			return obj;
		} finally {
			releaseSession(session);
		}
	}
	
	public String searchInvoiceAuditForSourceBeginning(String invoiceId,String sourceBeginning){
		Session session = getSession();
		logger.info("Enter method searchInvoiceAuditForSourceId.");
		String sql = "select count(1) from audit_result where invoice_id = "+invoiceId+" and audit_source_id like '"+sourceBeginning+"%'";
		List l = session.createSQLQuery(sql).list();
		logger.info("Exit method searchInvoiceAuditForSourceId.");
		return l.get(0).toString();
	}
	
	public String searchBanAuditFlag(String invoiceId){
		logger.info("Enter method searchBanAuditFlag.");
		Session session = getSession();
		try {
			String auditFlag = null;
			String sql = "select CONVERT(b.audit_flag USING utf8) as auditFlag from ban b where b.id = (select i.ban_id from invoice i where i.id = "+invoiceId+")"; 
			auditFlag = (String)session.createSQLQuery(sql).uniqueResult();			
			logger.info("Exit method searchBanAuditFlag.");
			return auditFlag;
		} finally {
			releaseSession(session);
		}
	}
	
	public Long searchBanExemption(String invoiceId){
		logger.info("Enter method searchBanExemption.");
		String sql = "select count(1) from ban_exemption be,invoice i where i.ban_id = be.ban_id and i.rec_active_flag = 'Y' and be.rec_active_flag = 'Y' and  i.invoice_date <= be.term_end_date and i.id = " + invoiceId;
		Session session = getSession();
		try {
			BigInteger b = (BigInteger)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method searchBanExemption.");
			return b.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	public Long searchBanOpenAutoPay(String invoiceId){
		logger.info("Enter method searchBanOpenAutoPay.");
		String sql = "select count(1) from  ban b ,invoice i where i.ban_id = b.id and i.rec_active_flag = 'Y' and b.rec_active_flag = 'Y' and  b.autopay_count = b.autopay_max_count  AND b.autopay_flag = 'Y' AND b.mib_flag = 'N' and i.id = " + invoiceId;
		Session session = getSession();
		try {
			BigInteger b = (BigInteger)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method searchBanOpenAutoPay.");
			return b.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> queryCBanUsageReportList(String invoiceId){
		
		logger.info("Enter method queryCBanUsageReportList.");
		final String sql = this.getsearchCBanUsageList(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method queryCBanUsageReportList.");
		return l;
	}
	
	private String getsearchCBanUsageList(String invoiceId){
		logger.info("Enter method getsearchCBanUsageList.");
		StringBuffer sb = new StringBuffer("SELECT DATE_FORMAT(call_date,'%Y/%m/%d') AS 'Call Date',CONCAT(trunk,'/',clli) AS 'Trunk / CLLI',none AS 'NONE',CONCAT(hold_min) AS 'Hold Min.',CONCAT(usage_min) AS 'Usage Min.',CONCAT(ROUND(usage_count,0)) AS 'Count',CONCAT(upc) AS 'UPC'" +
				" FROM audit_cabs_c_usage WHERE invoice_id = "+invoiceId+" order by call_date");
		logger.info("Exit method getsearchCBanUsageList.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> queryDBanUsageReportList(String invoiceId,String type){
		
		logger.info("Enter method queryDBanUsageReportList.");
		final String sql = this.getsearchDBanUsageList(invoiceId,type);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method queryDBanUsageReportList.");
		return l;
	}
	
	private String getsearchDBanUsageList(String invoiceId,String type){
		logger.info("Enter method getsearchDBanUsageList.");
		StringBuffer sb = null;
		if(type.equals("Originating")){
			sb = new StringBuffer("SELECT DATE_FORMAT(call_date,'%Y/%m/%d') AS 'Call Date',vendor AS 'InAccount',CONCAT(trunk,'/',clli) AS 'InTrunkName',minutes AS 'Minutes',CONCAT(average_duration) AS 'Average Duration',CONCAT(ROUND(seized,0)) AS 'Seized',CONCAT(ROUND(answered,0)) AS 'Answered',CONCAT(asr) AS 'ASR' " +
					"FROM audit_cabs_d_usage WHERE type = '"+type+"' and invoice_id = "+invoiceId +" order by call_date");
		}else{
			sb = new StringBuffer("SELECT DATE_FORMAT(call_date,'%Y/%m/%d') AS 'Call Date',vendor AS 'OutAccount',CONCAT(trunk,'/',clli) AS 'OutTrunkName',minutes AS 'Minutes',CONCAT(average_duration) AS 'Average Duration',CONCAT(ROUND(seized,0)) AS 'Seized',CONCAT(ROUND(answered,0)) AS 'Answered',CONCAT(asr) AS 'ASR' " +
					"FROM audit_cabs_d_usage WHERE type = '"+type+"' and invoice_id = "+invoiceId +" order by call_date");
		}
		logger.info("Exit method getsearchDBanUsageList.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> queryAllUsageInvoice(){
		logger.info("Enter method getAllUsageInvoice.");
		StringBuffer sb = new StringBuffer("SELECT i.id FROM invoice i LEFT JOIN ban b ON i.ban_id = b.id " +
			"WHERE     b.account_number IN (SELECT ts_variable_ban FROM audit_cabs_clli GROUP BY ts_variable_ban) " +
			"AND i.invoice_status_id IN (9, 10) AND i.rec_active_flag = 'Y' " +
			"AND DATE_FORMAT(invoice_date, '%Y-%m') >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 5 MONTH), '%Y-%m');");
		final String sql = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getAllUsageInvoice.");
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public long querySystemUploadByInvoiceId(Integer invoiceId){
		logger.info("Enter method querySystemUploadByInvoiceId.");
		StringBuffer sb = new StringBuffer("SELECT COUNT(1) " +
				"FROM attachment_point ap LEFT JOIN attachment_file af ON ap.id = af.attachment_point_id " +
				"WHERE     ap.table_id_value = "+invoiceId+" AND af.system_upload_flag = 'Y' AND ap.table_name = 'invoice_notes' " +
				"AND ap.rec_active_flag = 'Y' AND af.rec_active_flag = 'Y';");
		
		final String sql = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method querySystemUploadByInvoiceId.");
		return count;
	}
}
