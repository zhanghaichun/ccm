package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.ProposalDAO;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;

public class ProposalDAOImpl extends HibernateDaoSupport implements
		IProposalDao {
	
	private static final Log log = LogFactory.getLog(ProposalDAO.class);
	// property constants
	public static final String PAYMENT_AMOUNT = "paymentAmount";
	public static final String DISPUTE_AMOUNT = "disputeAmount";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String DESCRIPTION = "description";
	
	public void save(Proposal transientInstance) {
		log.debug("saving Proposal instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * save Proposal
	 */
	public Integer saveProposal(Proposal transientInstance) {
		log.debug("saving Proposal instance");
		Integer id  = null;
		try {
			Long preid = (Long)getHibernateTemplate().save(transientInstance);
			id=preid.intValue();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return id;
	}
	
	public void delete(Proposal persistentInstance) {
		log.debug("deleting Proposal instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Proposal findById(java.lang.Long id) {
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

	public List findByExample(Proposal instance) {
		log.debug("finding Proposal instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Proposal instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Proposal as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPaymentAmount(Object paymentAmount) {
		return findByProperty(PAYMENT_AMOUNT, paymentAmount);
	}

	public List findByDisputeAmount(Object disputeAmount) {
		return findByProperty(DISPUTE_AMOUNT, disputeAmount);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findAll() {
		log.debug("finding all Proposal instances");
		try {
			String queryString = "from Proposal";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Proposal merge(Proposal detachedInstance) {
		log.debug("merging Proposal instance");
		try {
			Proposal result = (Proposal) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Proposal instance) {
		log.debug("attaching dirty Proposal instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Proposal instance) {
		log.debug("attaching clean Proposal instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	public Integer getDisputeTabTotalNoOfInoiceDetail(SearchInvoiceVO svo){
		logger.info("Enter method getDisputeTabTotalNoOfInoiceDetail.");
		final StringBuffer sb = new StringBuffer("select count(1) from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id   LEFT JOIN attachment_point AS ap ON ap.id = p.attachment_point_id LEFT JOIN account_code as ac ON p.account_code_id=ac.id");
		//wenbo.zhang
		//2016-07-08
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId());
		if (invoiceStatusId < 21) {
					sb.append(" and ac.active_flag = 'Y'");
					sb.append(" and ac.rec_active_flag = 'Y'");
		}
		
		sb.append(" where p.invoice_id = "+svo.getInvoiceId() );
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and p.dispute_id is null ");
		sb.append(" and p.dispute_amount != 0 ");
		sb.append(" and p.dispute_amount is not null and p.proposal_flag = '1' ");
		if(svo.getFilter()!=null){
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		sb.append(svo.getOrderByCause(null));
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getDisputeTabTotalNoOfInoiceDetail.");
		return Count;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> searchDisputeTabOfInoiceDetail(SearchInvoiceVO svo)
	{
	
		logger.info("Enter method searchDisputeTabOfInoiceDetail.");
		final StringBuffer sb = new StringBuffer("select concat('{id:',toJSON(p.id is null,'',p.id),',disputeAmount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\",description:\"',toJSON(p.description is null,'',p.description),'\",notes:\"',toJSON(p.notes is null,'',p.notes),'\",invoiceitemid:\"',toJSON(p.invoice_item_id is null,'',p.invoice_item_id),");
		
		sb.append("'\",amount:\"',toJSON(FALSE, '', ROUND((if(p.dispute_amount IS NULL, 0, p.dispute_amount) + if(p.payment_amount IS NULL, 0, p.payment_amount)), 2)),");
		sb.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb.append("'\",creditAmount:\"',toJSON(FALSE, '', ROUND(if(p.credit_amount IS NULL, 0, p.credit_amount), 2)),");
		
		sb.append("'\",SCOAId:\"',toJSON(p.account_code_id IS NULL, '', p.account_code_id),");
		sb.append("'\",disputeCategoryId:\"', toJSON(p.dispute_reason_id IS NULL, '', p.dispute_reason_id),");
		
		
		sb.append("'\",category:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),");
		sb.append("'\",categoryId:\"',toJSON(dr.id is null,'',dr.id),");
		sb.append("'\",circuit_number:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb.append("'\",service_type:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb.append("'\",originator_id:\"',toJSON(o.id is null,'',o.id),");
		sb.append("'\",account_code_name:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\",attachmentPoint:\"', toJSON((SELECT COUNT(1) FROM attachment_file WHERE rec_active_flag = 'Y' AND attachment_point_id = p.attachment_point_id) = 0, '', p.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id");
		sb.append(" LEFT JOIN account_code as ac ON p.account_code_id=ac.id  LEFT JOIN attachment_point AS ap ON ap.id = p.attachment_point_id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo.getInvoiceId());
		if(invoiceStatusId < 21){
			sb.append(" and ac.rec_active_flag = 'Y' and ac.active_flag = 'Y'");
		}
		sb.append(" where  p.invoice_id=");
		sb.append(""+svo.getInvoiceId()+" " );
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and p.dispute_id is null ");
		sb.append(" and p.dispute_amount != 0 ");
		sb.append(" and p.dispute_amount != 0.00 and p.proposal_flag = '1' ");
		
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
		logger.info("Exit method searchDisputeTabOfInoiceDetail.");
		return list;	
	}
	@SuppressWarnings("unchecked")
	public List<String> getProposalDataLeftByInvoiceId(SearchInvoiceVO svo,String sProposalIds)
	{
		logger.info("Enter method getProposalDataLeftOfInoiceDetailByInvoiceId.");
		final StringBuffer sb = new StringBuffer("select concat('{id:',toJSON(p.id is null,'',p.id),',amount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\",description:\"',toJSON(p.description is null,'',p.description),'\",invoiceitemid:\"',toJSON(p.invoice_item_id is null,'',p.invoice_item_id),");
		sb.append("'\",category:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),");
		sb.append("'\",categoryId:\"',toJSON(dr.id is null,'',dr.id),");
		sb.append("'\",circuit_number:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb.append("'\",service_type:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb.append("'\",originator_id:\"',toJSON(o.id is null,'',o.id),");
		sb.append("'\",account_code_name:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\"}') a ");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id LEFT JOIN account_code as ac ON p.account_code_id=ac.id ");
		sb.append("where  p.invoice_id=");
		sb.append(""+svo.getInvoiceId()+" " );
		sb.append(" and p.id in ( "+sProposalIds+" )");
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and p.dispute_id is null ");
		sb.append(" and p.dispute_amount != 0 ");
		sb.append(" and p.dispute_amount != 0.00 and p.proposal_flag = '1' ");
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method getProposalDataLeftOfInoiceDetailByInvoiceId.");
		return list;	
	}
	@SuppressWarnings("unchecked")
	public List<String> getProposalDataRightByInvoiceIdAndDisputeId(SearchInvoiceVO svo,String proposalIds){
		logger.info("Enter method getProposalDataRightByInvoiceId.");
		final StringBuffer sb = new StringBuffer("select concat('{id:',toJSON(p.id is null,'',p.id),',amount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\",description:\"',toJSON(p.description is null,'',p.description),'\",invoiceitemid:\"',toJSON(p.invoice_item_id is null,'',p.invoice_item_id),");
		sb.append("'\",category:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),");
		sb.append("'\",categoryId:\"',toJSON(dr.id is null,'',dr.id),");
		sb.append("'\",circuit_number:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb.append("'\",service_type:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb.append("'\",originator_id:\"',toJSON(o.id is null,'',o.id),");
		sb.append("'\",dispute_id:\"',toJSON(d.id is null,'',d.id),");
		sb.append("'\",account_code_name:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\"}') a ");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id LEFT JOIN account_code as ac ON p.account_code_id=ac.id ");
		sb.append(" where p.invoice_id = " + svo.getInvoiceId() + " ");
		if(proposalIds != null && !"".equals(proposalIds)){
			sb.append(" and p.id in ( " + proposalIds + " ) ");
		}
		sb.append(" and p.dispute_id = " + svo.getDisputeId() + " ");
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and p.dispute_amount != 0 ");
		sb.append(" and p.dispute_amount != 0.00 and p.proposal_flag = '1' ");
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method getProposalDataRightByInvoiceId.");
		return list;	
	}
	public void updateDisputeTabOfInvoiceDetail(String proposalIds,int disputeId)
	{
		logger.info("Enter methed updateDisputeTabMoveSelectGroupOfInvoiceDetail");
		final StringBuffer sb=new StringBuffer();
		sb.append("update proposal set dispute_id=");
		sb.append(""+disputeId+" where id in (");
		sb.append(""+proposalIds+"");
		sb.append(")");
		Session session = getSession();
		try {
			SQLQuery query= session.createSQLQuery(sb.toString());
			query.executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	
	public void updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail(String proposalIds)
	{
		logger.info("Enter methed updateDisputeTabMoveSelectGroupOfInvoiceDetail");
		final StringBuffer sb=new StringBuffer();
		sb.append("update proposal set dispute_id=");
		sb.append("null where id in (");
		sb.append(""+proposalIds+"");
		sb.append(")");
		Session session = getSession();
		try {
			SQLQuery query= session.createSQLQuery(sb.toString());
			query.executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	
	public void updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetailByDisputeId(Integer disputeId)
	{
		logger.info("Enter methed updateDisputeTabMoveSelectGroupOfInvoiceDetail");
		final StringBuffer sb=new StringBuffer();
		sb.append("update proposal set dispute_id=");
		sb.append("null where dispute_id= ");
		sb.append(""+disputeId+"");
		Session session = getSession();
		try {
			SQLQuery query=session.createSQLQuery(sb.toString());
			query.executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	
	public Integer getDisputesTotalNoOfInoiceDetailByInvoiceId(SearchInvoiceVO svo){
		logger.info("Enter method getDisputesTotalNoOfInoiceDetailByInvoiceId.");
		final StringBuffer sb = new StringBuffer("select count(1) FROM ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id WHERE p.dispute_id IS NOT NULL");
		sb.append("and p.invoice_id = "+svo.getInvoiceId()+" ");
		sb.append("and p.rec_active_flag = 'Y' ");
		sb.append(" and p.dispute_id is null ");
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getDisputesTotalNoOfInoiceDetailByInvoiceId.");
		return Count;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> searchDisputesOfInoiceDetailByInvoiceId(SearchInvoiceVO svo)
	{
	
		logger.info("Enter method searchDisputesOfInoiceDetailByInvoiceId.");
		final StringBuffer sb = new StringBuffer("select concat('{id:',p.id,',amount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\",description:\"',toJSON(p.description is null,'',p.description),'\",disputeNumber:\"',toJSON(d.dispute_number is null,'',DISTINCT(d.dispute_number)),");
		sb.append("'\",category:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),'\",claimNumber:\"',toJSON(d.claim_number is null,'',d.claim_number),'\",originator:\"',toJSON(o.originator_name is null,'',o.originator_name),'\",originatorId:\"',toJSON(o.id is null,'',o.id),'\",email_copy_address:\"',toJSON(p.email_copy_address is null,'',p.email_copy_address),");
		sb.append("'\"}') a ");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id");
		sb.append("where p.dispute_id IS NOT NULL and  p.invoice_id=");
		sb.append(""+svo.getInvoiceId()+"" );
		sb.append("and p.rec_active_flag = 'Y' ");
		if(svo.getSortField()!=null)
			sb.append("order by "+svo.getSortField()+" ");
		if(svo.getSortingDirection()!=null) 
			sb.append(svo.getSortingDirection()+" ");
		sb.append("limit " + svo.getStartIndex());
		sb.append(","+svo.getRecPerPage()); 
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method searchDisputesOfInoiceDetailByInvoiceId.");
		return list;	
	}
	
	
	public Integer getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId(int vendorId,int disputeReasonId){
		logger.info("Enter method getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId.");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMDD");
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		String date=sdfInput.format(new Date());
		final StringBuffer sb = new StringBuffer("SELECT count(1) FROM dispute as d left join invoice AS i on d.invoice_id = i.id LEFT JOIN vendor AS v ON i.vendor_id = v.id WHERE ");
		sb.append("d.created_timestamp >= '"+date+" 00:00:00'  and  d.created_timestamp <= '"+date+" 23:59:59' ");
		sb.append(" and v.id = "+vendorId+" ");
		sb.append(" and d.dispute_reason_id = "+disputeReasonId+" ");
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId.");
		return Count;
	} 
	
	public Integer getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId(String claimNumber){
		logger.info("Enter method getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId.");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMDD");
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
		String date=sdfInput.format(new Date());
		final StringBuffer sb = new StringBuffer("select count(1) + 1 from dispute d where d.claim_number like '"+claimNumber+"%';");
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sb.toString()).list();
			}
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId.");
		return Count;
	} 
	
	
	public Integer getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO svo){
		logger.info("Enter method getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.");
		final StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON d.originator_id=o.id LEFT JOIN account_code as ac ON p.account_code_id=ac.id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.active_flag = 'Y'");
			sb.append(" and ac.rec_active_flag = 'Y'");
        }
		sb.append(" LEFT JOIN attachment_point AS ap ON ap.id = p.attachment_point_id  ");
		sb.append(" where p.invoice_id = "+svo.getInvoiceId()+" ");
/*		sb.append(" and ac.active_flag = 'Y'");
		sb.append(" and ac.rec_active_flag = 'Y'");*/
		sb.append("and  d.id = "+svo.getDisputeId());
		sb.append(" and p.rec_active_flag = 'Y' and p.proposal_flag = '1' and p.dispute_amount != 0.00 and p.dispute_amount != 0 ");
		if(svo.getFilter()!=null){
			sb.append(" and ");
			sb.append(svo.getFilter());
		}	
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.");
		return Count;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO svo)
	{
	
		logger.info("Enter method searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.");
		final StringBuffer sb = new StringBuffer("select concat('{id:',toJSON(p.id is null,'',p.id),',amount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\",dispute_status:\"',toJSON(d.dispute_status_id is null,'',d.dispute_status_id),'\",description:\"',toJSON(p.description is null,'',p.description),'\",notes:\"',toJSON(p.notes is null,'',p.notes),'\",invoiceitemid:\"',toJSON(p.invoice_item_id is null,'',p.invoice_item_id),");
		sb.append("'\",category:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),");
		sb.append("'\",categoryId:\"',toJSON(dr.id is null,'',dr.id),");
		sb.append("'\",circuit_number:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb.append("'\",service_type:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb.append("'\",originator_id:\"',toJSON(o.id is null,'',o.id),");
		sb.append("'\",account_code_name:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\",attachmentPoint:\"',  toJSON(p.attachment_point_id IS NULL, '', p.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON d.originator_id=o.id LEFT JOIN account_code as ac ON p.account_code_id=ac.id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.active_flag = 'Y'");
			sb.append(" and ac.rec_active_flag = 'Y'");
		}
		sb.append(" LEFT JOIN attachment_point AS ap ON ap.id = p.attachment_point_id ");
		sb.append("where  p.invoice_id=");
		sb.append(""+svo.getInvoiceId()+" " );
		sb.append(" and p.rec_active_flag = 'Y' and p.proposal_flag = '1' and p.dispute_amount != 0.00 and p.dispute_amount != 0 ");
		if(svo.getFilter()!=null){
			sb.append(" and ");
			sb.append(svo.getFilter());
		}	
		sb.append(" and d.id = "+svo.getDisputeId());
		sb.append(" order by " + svo.getSortField() + " " + svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.");
		return list;	
	}
	
	
	@SuppressWarnings("unchecked")
	public List getProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeIdDownExcel(SearchInvoiceVO svo)
	{
	
		logger.info("Enter method getProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeIdDownExcel.");
		final StringBuffer sb = new StringBuffer("select format(p.dispute_amount,2),dr.dispute_reason_name,p.service_type,p.circuit_number,ac.account_code_name,p.notes,p.description");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id LEFT JOIN account_code as ac ON p.account_code_id=ac.id ");
		sb.append("where  p.invoice_id=");
		sb.append(""+svo.getInvoiceId()+" " );
		sb.append(" and p.rec_active_flag = 'Y' and p.proposal_flag = '1' ");
		sb.append(" and p.dispute_amount != 0 ");
		sb.append(" and d.id = "+svo.getDisputeId());
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		HibernateTemplate template = this.getHibernateTemplate();
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeIdDownExcel.");
			return re;	
		} finally {
			releaseSession(session);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List getProposalsOfInoiceDetailDisputeTabByInvoiceIdDownExcel(SearchInvoiceVO svo){
		logger.info("Enter method getProposalsOfInoiceDetailDisputeTabByInvoiceIdDownExcel.");
		final StringBuffer sb = new StringBuffer("select format(p.dispute_amount,2),dr.dispute_reason_name ,p.service_type,ac.account_code_name,p.circuit_number,p.notes,p.description");
		sb.append(" from ((proposal AS p LEFT JOIN dispute AS d ON  p.dispute_id=d.id) LEFT JOIN dispute_reason AS dr ON p.dispute_reason_id=dr.id) LEFT JOIN originator AS o ON p.originator_id=o.id LEFT JOIN account_code as ac ON p.account_code_id=ac.id ");
		sb.append("where  p.invoice_id=");
		sb.append(""+svo.getInvoiceId()+" " );
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and p.dispute_id is null ");
		sb.append(" and p.dispute_amount != 0 ");
		sb.append(" and p.dispute_amount != 0.00 and p.proposal_flag = '1' ");
		if(svo.getFilter()!=null){
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		sb.append(svo.getOrderByCause(null));
		HibernateTemplate template = this.getHibernateTemplate();
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getProposalsOfInoiceDetailDisputeTabByInvoiceIdDownExcel.");
			return re;	
		} finally {
			releaseSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Integer getAnnexsTotalOfProposalByProposalId(SearchInvoiceVO svo)
	{
		logger.info("Enter method getAnnexsTotalOfProposalByProposalId.");
		final StringBuffer sb = new StringBuffer("select count(*)");
		sb.append(" from attachment_file ,proposal ");
		sb.append("where  attachment_file.attachment_point_id = proposal.attachment_point_id and proposal.id = ");
		sb.append(""+("".equals(svo.getProposalId())?"''":svo.getProposalId())+" and attachment_file.rec_active_flag = 'Y' " );
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		return Count;	
	}
	
	@SuppressWarnings("unchecked")
	public List searchAnnexsOfProposalByProposalId(SearchInvoiceVO svo)
	{
		logger.info("Enter method searchAnnexsOfProposalByProposalId.");
		final StringBuffer sb = new StringBuffer("select concat('{file_path:\"',if(attachment_file.file_path is null,'',attachment_file.file_path),'\",id:\"',if(attachment_file.id is null,'',attachment_file.id),'\",file_name:\"',if(attachment_file.file_name is null,'',attachment_file.file_name),'\",attachment_point_id:\"',if(attachment_file.attachment_point_id is null,'',attachment_file.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(" from attachment_file ,proposal ");
		sb.append("where   attachment_file.attachment_point_id = proposal.attachment_point_id and proposal.id=");
		sb.append(""+svo.getProposalId()+" and attachment_file.rec_active_flag = 'Y' ");
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
		logger.info("Exit method searchAnnexsOfProposalByProposalId.");
		return list;	
	}
	public Integer getProposalsNumberOfDisputeToExcelForSendEmailByDisputeId(Dispute dispute){
		logger.info("Enter method getAnnexsTotalOfProposalByProposalId.");
		final StringBuffer sb = new StringBuffer("select count(*)");
		sb.append(" from proposal as p ");
		sb.append("where p.proposal_flag='1' and p.rec_active_flag='Y' and p.dispute_id = ");
		sb.append(""+dispute.getId()+" " );
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		return Count;
	}
	/** (non-Javadoc)
	 * @author wei.su
	 */
	@SuppressWarnings("unchecked")
	public List searchProposalsOfDisputeToExcelForSendEmailByDisputeId(Dispute dispute)
	{
		logger.info("Enter method searchProposalsOfDisputeToExcelForSendEmailByDisputeId.");
		final StringBuffer sb = new StringBuffer("select ii.province,i.invoice_date, dr.dispute_reason_name,p.dispute_amount,p.circuit_number ,p.address ,p.description ,p.service_type ,p.charge_type ,p.rate ,p.quantity ,p.usoc ,p.usoc_description ,p.number_of_calls ,p.country ,p.direction ,p.billing_number ,p.asg ,p.jurisdiction ,p.mileage ,p.minutes ,if(d.flag_shortpaid='Y','Yes','No')");
		sb.append("  from  proposal as p left join invoice_item as ii on p.invoice_item_id = ii.id left join invoice as i on p.invoice_id = i.id left join dispute as d on p.dispute_id = d.id left join dispute_reason as dr on p.dispute_reason_id = dr.id");
		sb.append(" where p.proposal_flag='1' ");
		sb.append(" and  p.rec_active_flag='Y' ");
		sb.append(" and  p.dispute_id="+dispute.getId()+"  ");
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(final Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method searchProposalsOfDisputeToExcelForSendEmailByDisputeId.");
		return list;	
	}

	private Integer getInvoiceStatusIdByInvoiceId(String invoiceId) {
		Session session = getSession();
		try {
			Integer id = Integer.valueOf(invoiceId);
			return (Integer)session.createSQLQuery("select i.invoice_status_id from invoice i where i.id="+id).uniqueResult();
		} finally {
			releaseSession(session);
		}
	}
	public void commit() {
		try {
			getSession().flush();
//			getSession().clear();
		} catch (Exception e) {}
	}

	public Double getAountByInvoiceAndAccount(int invoiceId, Integer id) {
		
		final StringBuffer sb = new StringBuffer("select ifnull(sum(ifnull(p.payment_amount,0)),0)");
		sb.append(" from proposal p");
		sb.append(" where p.invoice_id = "+invoiceId);
		sb.append(" and p.rec_active_flag='Y'");
		sb.append(" and p.proposal_flag=1");
		sb.append(" and p.account_code_id="+id);
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Double amount = new Double(l.get(0).toString());
		logger.info("Exit method getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.");
		return amount;
	}

	public void updateAccountByInvoiceAndAccount(int invoiceId, Integer id,Integer nid) {
		logger.info("Enter methed updateAccountByInvoiceAndAccount");
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=sdfInput.format(new Date());
		final StringBuffer sb=new StringBuffer();
		sb.append("update proposal p");
		sb.append(" set p.account_code_id="+nid+",p.scoa_source_id=750101,p.modified_by="+SystemUtil.getCurrentUserId()+",p.modified_timestamp='"+date+"'");
		sb.append(" where p.invoice_id = "+invoiceId);
		sb.append(" and p.rec_active_flag='Y'");
		sb.append(" and p.proposal_flag=1");
		sb.append(" and p.account_code_id="+id);
		Session session = getSession();
		try {
			SQLQuery query= session.createSQLQuery(sb.toString());
			query.executeUpdate();
		} finally {
			releaseSession(session);
		}
		
	}
}
