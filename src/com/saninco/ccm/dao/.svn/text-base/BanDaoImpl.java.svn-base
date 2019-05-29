/**
 * 
 */
package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.AccountType;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.PaymentGroup;
import com.saninco.ccm.model.PaymentTerm;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.BanVO;
import com.saninco.ccm.vo.SearchReportUserVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 *
 */
public class BanDaoImpl extends HibernateDaoSupport implements IBanDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	// property constants
	public static final String ACCOUNT_NUMBER = "accountNumber";
	public static final String BAN_PRIMIARY_CONTACT_ID = "banPrimiaryContactId";
	public static final String BAN_DEFAULT_PAYEE_ID = "banDefaultPayeeId";
	public static final String BAN_ADDRESS = "banAddress";
	public static final String BAN_DISPUTE_EMAIL = "banDisputeEmail";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	/**
	 * 
	 */
	public BanDaoImpl() {
	}
	
	protected void initDao() {
		// do nothing
	}
	
	public void updateInvoiceByBan(Integer analystId,Integer banId) {
		logger.info("Enter method updateInvoiceByBan.");
		final String queryString = updateInvoiceByBanQueryString(analystId,banId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateInvoiceByBan.");
	}
	
	public String getAnalystName(Integer analystId) {
		logger.info("Enter method getBanByVendorId.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery(" select concat(ifnull(u.first_name , ''),' ',ifnull(u.last_name , '')) from user u where u.id = "+analystId).list();
			logger.info("Exit method getBanByVendorId.");
			if(l != null && l.size()==1)
				return (String) l.get(0);
			else
				return "";
			
		} finally {
			releaseSession(session);
		}
	}
	
	private String updateInvoiceByBanQueryString(Integer analystId,Integer banId) {
		logger.info("Enter method updateInvoiceByBanQueryString.");
		StringBuffer sb = new StringBuffer("update invoice i set ");
		sb.append(" i.assigned_analyst_id = " + analystId + " ");
		sb.append(" , i.assignment_user_id = " + analystId + " ");
		sb.append(" , i.modified_timestamp = now() ");
		sb.append(" , i.modified_by = '" + SystemUtil.getCurrentUserId() + "' ");
		sb.append("  where i.invoice_status_id <=10 and i.ban_id = " + banId + " ");
		logger.info("Exit method updateInvoiceByBanQueryString.");
		return sb.toString();
	}
	
	public long getNumberOfAccountNumber(BanVO bvo) {
		logger.info("Enter method getNumberOfAccountNumber.");
		final String sql = this.getNumberOfAccountNumberQueryString(bvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfAccountNumber.");
		return count;
	}

	private String getNumberOfAccountNumberQueryString(BanVO bvo) {
		logger.info("Enter method getNumberOfAccountNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from ban b ");
		sb.append(" where b.account_number = '" + bvo.getAccountNumber() + "' ");
		if((!"".equals(bvo.getBanId())) && bvo.getBanId() != null){
			sb.append(" and b.rec_active_flag = 'Y' and b.master_ban_flag = 'Y'");
			sb.append(" and b.id != " + bvo.getBanId() + " ");
		}
		logger.info("Exit method getNumberOfAccountNumberQueryString.");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#save(com.saninco.ccm.model.Ban)
	 */
	public void save(Ban transientInstance) {
		logger.debug("saving Ban instance");
		try {
			getHibernateTemplate().save(transientInstance);
			final String queryString = updateInvoiceVonderIdByBanQueryString(transientInstance.getId());
			HibernateTemplate template = this.getHibernateTemplate();
			template.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(queryString).executeUpdate();
				}
			});
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	private String updateInvoiceVonderIdByBanQueryString(Integer banId) {
		logger.info("Enter method updateInvoiceVonderIdByBanQueryString.");
		StringBuffer sb = new StringBuffer("update invoice i , ban b set ");
		sb.append(" i.vendor_id = b.vendor_id ");
		sb.append("  where i.ban_id = b.id and i.rec_active_flag = 'Y' and i.invoice_status_id <=10 and i.ban_id = " + banId + " ");
		logger.info("Exit method updateInvoiceVonderIdByBanQueryString.");
		return sb.toString();
	}
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#delete(com.saninco.ccm.model.Ban)
	 */
	public void delete(Ban persistentInstance) {
		logger.debug("deleting Ban instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findById(java.lang.Integer)
	 */
	public Ban findById(java.lang.Integer id) {
		logger.debug("getting Ban instance with id: " + id);
		try {
			Ban instance = (Ban) getHibernateTemplate().get(
					"com.saninco.ccm.model.Ban", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findById(java.lang.Integer)
	 */
	public Ban load(java.lang.Integer id) {
		logger.debug("loading Ban instance with id: " + id);
		try {
			Ban instance = (Ban) getHibernateTemplate().load(Ban.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("load failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByExample(com.saninco.ccm.model.Ban)
	 */
	public List findByExample(Ban instance) {
		logger.debug("finding Ban instance by example");
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
	 * @see com.saninco.ccm.dao.IBanDao1#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		logger.debug("finding Ban instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Ban as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByAccountNumber(java.lang.Object)
	 */
	public List findByAccountNumber(Object accountNumber) {
		return findByProperty(ACCOUNT_NUMBER, accountNumber);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByBanPrimiaryContactId(java.lang.Object)
	 */
	public List findByBanPrimiaryContactId(Object banPrimiaryContactId) {
		return findByProperty(BAN_PRIMIARY_CONTACT_ID, banPrimiaryContactId);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByBanDefaultPayeeId(java.lang.Object)
	 */
	public List findByBanDefaultPayeeId(Object banDefaultPayeeId) {
		return findByProperty(BAN_DEFAULT_PAYEE_ID, banDefaultPayeeId);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByBanAddress(java.lang.Object)
	 */
	public List findByBanAddress(Object banAddress) {
		return findByProperty(BAN_ADDRESS, banAddress);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByBanDisputeEmail(java.lang.Object)
	 */
	public List findByBanDisputeEmail(Object banDisputeEmail) {
		return findByProperty(BAN_DISPUTE_EMAIL, banDisputeEmail);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByCreatedBy(java.lang.Object)
	 */
	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByModifiedBy(java.lang.Object)
	 */
	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findByRecActiveFlag(java.lang.Object)
	 */
	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findAll()
	 */
	public List findAll() {
		logger.debug("finding all Ban instances");
		try {
			String queryString = "from Ban";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#merge(com.saninco.ccm.model.Ban)
	 */
	public Ban merge(Ban detachedInstance) {
		logger.debug("merging Ban instance");
		try {
			Ban result = (Ban) getHibernateTemplate().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#attachDirty(com.saninco.ccm.model.Ban)
	 */
	public void attachDirty(Ban instance) {
		logger.debug("attaching dirty Ban instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#attachClean(com.saninco.ccm.model.Ban)
	 */
	public void attachClean(Ban instance) {
		logger.debug("attaching clean Ban instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao#getBanByVendorId()
	 */
	@SuppressWarnings("unchecked")
	public List getBanByVendorId(Integer vendorId) {
		logger.info("Enter method getBanByVendorId.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select b.id,b.account_number from ban b where b.vendor_id=" + vendorId + " and b.rec_active_flag='Y' order by b.account_number asc ").list();
			
			logger.info("Exit method getBanByVendorId.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @Author Chao.Liu Date: Nov 4, 2010
	 * @Belong To ReportUserServiceImpl.getVendorNameByBanId(SearchReportUserVO rvo)
	 */
	public String getVendorNameByBanId(SearchReportUserVO rvo){
		logger.info("Enter method searchBanList.");
		Session session = getSession();
		try {
			String sql = "select v.vendor_name from ban b left join vendor v on (b.vendor_id = v.id) where b.id = '"+rvo.getBanId()+"'";
			List l = (List) session.createSQLQuery(sql).list();
			logger.info("Exit method searchBanList.");
			return "{vendorName:\""+l.get(0)+"\"}";
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * Get All Ban
	 * @Author Chao.Liu Date: Nov 1, 2010
	 */
	public List<Ban> getAllBan(){
		logger.info("Enter method getAllBan.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select b.id,b.account_number from ban b where b.rec_active_flag='Y' and b.ban_status_id != 2 and b.master_ban_flag='Y' and b.account_number!='ALL' order by b.account_number").list();
			logger.info("Exit method getAllBan.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	public void saveBanExemption(Map<String, String> banExemption){
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ban_exemption(ban_id, certificate_name, file_path, term_start_date, term_end_date, notes, created_timestamp, created_by, modified_timestamp, modified_by, rec_active_flag)values");
		sb.append("('"+banExemption.get("banId")+"','"+banExemption.get("certificateName")+"','"+banExemption.get("filePath")+"','"+banExemption.get("termStartDate")+"','"+banExemption.get("termEndDate")+"','"+banExemption.get("notes")+"',now(),"+SystemUtil.getCurrentUserId()+",now(),"+SystemUtil.getCurrentUserId()+",'Y')");
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	
	public void updateBanExemption(Map<String, String> banExemption){

        final String queryString = "update ban_exemption  set term_start_date = '"+banExemption.get("termStartDate")+"', term_end_date = '"+banExemption.get("termEndDate")+"' ,modified_timestamp = now(), modified_by = "+SystemUtil.getCurrentUserId() +" where id = " + banExemption.get("banExemptionId");
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	
	
	
	/**
	 * Get Ban Status List Info
	 * @Author Chao.liu Sep 25, 2010
	 */
	public List<Object[]> getBanStatus(Integer banStatusId){
		logger.info("Enter method getBanStatus.");
		Session session = getSession();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select bs.id,bs.ban_status_name from ban_status bs");
//			if(banStatusId == null || (banStatusId != 4 && banStatusId != 5)){
//				sb.append(" where bs.id not in (4,5)");
//			}
			List<Object[]> l = (List<Object[]>) session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getBanStatus.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	/** 
	 * @Author Chao.liu Sep 25, 2010
	 */
	public List getLineofBbusiness(){
		logger.info("Enter method getLineofBbusiness.");
		Session session = getSession();
		try {
//			List l = (List) session.createSQLQuery("select t.line_of_business from (select line_of_business from ban where line_of_business != '' and line_of_business != 'RBS' and rec_active_flag = 'Y' group by line_of_business) t order by t.line_of_business asc").list();
			List l = (List) session.createSQLQuery("select t.line_of_business from (select line_of_business from ban_ref_lob_dict where line_of_business != '' and line_of_business != 'RBS' and rec_active_flag = 'Y' group by line_of_business) t order by t.line_of_business asc").list();
			logger.info("Exit method getLineofBbusiness.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	/** 
	 * @Author mingyang.Li Nov 15, 2010
	 */
	public List getBanInactiveReason(){
		logger.info("Enter method getBanInactiveReason.");
		Session session = getSession();
		try {
			List l = (List) session.createSQLQuery("select bir.id,bir.description from ban_inactive_reason bir;").list();
			logger.info("Exit method getBanInactiveReason.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	/** 
	 * @Author Chao.liu Sep 25, 2010
	 */
	public List getInvoiceFrequency(){
		logger.info("Enter method getInvoiceFrequency.");
		Session session = getSession();
		try {
			List l = (List)session.createSQLQuery("select invoice_frequency from ban where invoice_frequency != ''  and rec_active_flag = 'Y' group by invoice_frequency").list();
			logger.info("Exit method getInvoiceFrequency.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	/** 
	 * @Author Chao.liu Sep 25, 2010
	 */
	public List getAccountType(){
		logger.info("Enter method getAccountType.");
		List<AccountType> l = (List<AccountType>)this.getHibernateTemplate().find("from AccountType ");
		logger.info("Exit method getAccountType.");
		return l;
	}
	
	/** 
	 * @Author Chao.liu Sep 25, 2010
	 */
	public List getPaymentTerm(){
		logger.info("Enter method getPaymentTerm.");
		List<PaymentTerm> l = (List<PaymentTerm>)this.getHibernateTemplate().find("from PaymentTerm ");
		logger.info("Exit method getPaymentTerm.");
		return l;
	}
	
	/**
	 * @Auchor Chao.Liu | On Date: Nov 9, 2010
	 */
	public List<Object[]> getContactByVendorId(BanVO bvo){
		logger.info("Enter method getContactByVendorId.");
		String sql = this.getContactByVendorIdSqlString(bvo);
		Session session = getSession();
		try {
			List<Object[]> l = session.createSQLQuery(sql).list();
			logger.info("Exit method getContactByVendorId.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	private String getContactByVendorIdSqlString(BanVO bvo){
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,concat(if(c.id IS NULL, '', c.id), ' , ',if(c.first_name is null,'',c.first_name),if(c.last_name is null,'',if(c.first_name is null or c.first_name='',c.last_name,concat(' ',c.last_name))),if(c.first_name is null or c.first_name='',if(c.last_name is null or c.first_name='','',','),','),if(c.email IS NULL, '', c.email),' , ',if(c.primary_phone IS NULL, '', c.primary_phone),' , ',if(c.address_1 IS NULL, '', c.address_1)) from contact c");
		sb.append(" where ( c.vendor_id=");
		sb.append(bvo.getVendorId() + " or c.id = (select b.ban_primiary_contact_id from ban b where b.id ="+bvo.getBanId()+" ) or  c.id = (select b.ban_dispute_contact_id from ban b where b.id ="+bvo.getBanId()+" ))");
		sb.append(" and c.rec_active_flag='Y' and c.email is not null order by concat(if(concat(c.first_name,' ',c.last_name) is null,'',concat(c.first_name,' ',c.last_name)),if(concat(c.first_name,' ',c.last_name) is null,'',','),c.email) asc");
		return sb.toString();
	}
	/** 
	 * @Author Chao.liu Sep 25, 2010
	 */
	public List getPaymentGroup(){
		logger.info("Enter method getPaymentGroup.");
		List<PaymentGroup> l = (List<PaymentGroup>)this.getHibernateTemplate().find("from PaymentGroup ");
		logger.info("Exit method getPaymentGroup.");
		return l;
	}
	
	/**
	 * @Auchor Chao.Liu | On Date: Nov 9, 2010
	 */
	public Integer getBanCountByBanName(Ban b){
		logger.info("Enter method getBanCountByBanName.");
		String hql = this.getBanCountByBanNameSqlString(b);
		List l = (List)this.getHibernateTemplate().find(hql);
		logger.info("Exit method getBanCountByBanName.");
		return l != null?l.size() : 0;
	}
	
	private String getBanCountByBanNameSqlString(Ban b){
		StringBuffer sb = new StringBuffer();
		sb.append("from Ban b ");
		sb.append("where 1=1 ");
		if(b.getId() != null){
			sb.append("and b.recActiveFlag = 'Y' and b.masterBanFlag = 'Y' ");
			sb.append("and b.id != '" + b.getId() + "' ");
		}
		sb.append("and b.accountNumber = '" + b.getAccountNumber() + "' ");
		sb.append("and b.vendor.id = '" + b.getVendor().getId() + "' ");
		return sb.toString();
	}
	
	/**
	 * @Author Chao.liu Sep 26, 2010
	 */
	public long getBanTotalNO(BanVO bvo){
		logger.info("Enter method getBanTotalNO.");
		String sql = this.getBanTotalNOSqlString(bvo);
		Session session = getSession();
		try {
			List l = (List)session.createSQLQuery(sql).list();
			logger.info("Exit method getBanTotalNO.");
			return new Long(l.get(0).toString());
		} finally {
			releaseSession(session);
		}
	}
	
	private String getBanTotalNOSqlString(BanVO bvo){
		StringBuffer sb = new StringBuffer("select count(0)");
		sb.append(this.getBanWhereSqlString(bvo));
		return sb.toString();
	}
	
	/**
	 * @Author Chao.liu Sep 26, 2010
	 */
	public List searchBanList(BanVO bvo){
		logger.info("Enter method searchBanList.");
		String sql = this.searchBanListSqlString(bvo);
		Session session = getSession();
		try {
			List l = (List) session.createSQLQuery(sql).list();
			logger.info("Exit method searchBanList.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	private String searchBanListSqlString(BanVO bvo){
		String uid = (bvo.getUid() == null ? -1 : bvo.getUid())+"";
		StringBuffer sb = new StringBuffer("select concat('{");
		sb.append("banId:\"',b.id,'\",");
		sb.append("uid:\"',"+uid+",'\",");
		sb.append("accountNumber:\"',toJSON(b.account_number is null,'',b.account_number),'\",");
		sb.append("originalAccountNumber:\"',toJSON(b.original_account_number is null,'',b.original_account_number),'\",");
		sb.append("apSupplierNumber:\"',toJSON(b.ap_supplier_number is null,'',b.ap_supplier_number),'\",");
		sb.append("apSupplierName:\"',toJSON(b.ap_supplier_name is null,'',b.ap_supplier_name),'\",");
		sb.append("apSupplierSite:\"',toJSON(b.ap_supplier_site is null,'',b.ap_supplier_site),'\",");
		sb.append("apCustomerName:\"',toJSON(b.ap_customer_name is null,'',b.ap_customer_name),'\",");
		sb.append("banCreateDate:\"',toJSON(b.ban_create_date is null,'',b.ban_create_date),'\",");
		sb.append("banCloseDate:\"',toJSON(b.ban_close_date is null,'',b.ban_close_date),'\",");
		sb.append("usageBackbillMonths:\"',toJSON(b.usage_backbill_months is null,'',b.usage_backbill_months),'\",");
		sb.append("billDay:\"',toJSON(b.bill_day is null,'',b.bill_day),'\",");
		sb.append("invoiceFrequency:\"',toJSON(b.invoice_frequency is null,'',b.invoice_frequency),'\",");
		sb.append("company:\"',toJSON(b.company is null,'',b.company),'\",");
		sb.append("location:\"',toJSON(b.location is null,'',b.location),'\",");
		sb.append("department:\"',toJSON(b.department is null,'',b.department),'\",");
		sb.append("channel:\"',toJSON(b.channel is null,'',b.channel),'\",");
		sb.append("lineOfBusiness:\"',toJSON(b.line_of_business is null,'',b.line_of_business),'\",");
		sb.append("description:\"',toJSON(bir.description is null,'',bir.description),'\",");
		sb.append("statusTimestamp:\"',toJSON(b.status_timestamp is null,'',b.status_timestamp),'\",");
		sb.append("notes:\"',toJSON(b.notes is null,'',b.notes),'\",");
		sb.append("createdTimestamp:\"',toJSON(b.created_timestamp is null,'',b.created_timestamp),'\",");
		sb.append("modifiedTimestamp:\"',toJSON(b.modified_timestamp is null,'',b.modified_timestamp),'\",");
		sb.append("createdBy:\"',toJSON(uc.first_name is null,'',uc.first_name),' ',toJSON(uc.last_name is null,'',uc.last_name),'\",");
		sb.append("modifiedBy:\"',toJSON(um.first_name is null,'',um.first_name),' ',toJSON(um.last_name is null,'',um.last_name),'\",");
		sb.append("vendorName:\"',toJSON(v.vendor_name is null,'',v.vendor_name),'\",");
		sb.append("banStatus:\"',toJSON(bs.ban_status_name is null,'',bs.ban_status_name),'\",");
		sb.append("accountType:\"',toJSON(a_t.account_type_name is null,'',a_t.account_type_name),'\",");
		sb.append("invoiceChannel:\"',toJSON(ic.invoice_channel_name is null,'',ic.invoice_channel_name),'\",");
		sb.append("invoiceFormat:\"',toJSON(i_f.invoice_format_code is null,'',i_f.invoice_format_code),'\",");
		sb.append("missingInvoiceEmail:\"',toJSON(b.Missing_Email_flag is null,'',b.Missing_Email_flag),'\",");
		sb.append("paymentMethod:\"',toJSON(pm.payment_method_code is null,'',pm.payment_method_code),'\",");
		sb.append("paymentTerm:\"',toJSON(pt.payment_term_code is null,'',pt.payment_term_code),'\",");
		sb.append("paymentGroup:\"',toJSON(pg.payment_group_code is null,'',pg.payment_group_code),'\",");
		sb.append("currency:\"',toJSON(c.currency_name is null,'',c.currency_name),'\",");
		sb.append("banPrimiaryContact:\"',toJSON(cpric.first_name is null,'',cpric.first_name),' ',toJSON(cpric.last_name is null,'',cpric.last_name),' ',toJSON(cpric.email is null,'',cpric.email),'\",");
		sb.append("banPaymentContact:\"',toJSON(cpayc.first_name is null,'',cpayc.first_name),' ',toJSON(cpayc.last_name is null,'',cpayc.last_name),' ',toJSON(cpayc.email is null,'',cpayc.email),'\",");
		sb.append("banDisputeContact:\"',toJSON(cdisc.first_name is null,'',cdisc.first_name),' ',toJSON(cdisc.last_name is null,'',cdisc.last_name),' ',toJSON(cdisc.email is null,'',cdisc.email),'\",");
		sb.append("banTariffContact:\"',toJSON(ctarc.first_name is null,'',ctarc.first_name),' ',toJSON(ctarc.last_name is null,'',ctarc.last_name),' ',toJSON(ctarc.email is null,'',ctarc.email),'\",");
		sb.append("banBillingContact:\"',toJSON(cbilc.first_name is null,'',cbilc.first_name),' ',toJSON(cbilc.last_name is null,'',cbilc.last_name),' ',toJSON(cbilc.email is null,'',cbilc.email),'\",");
		sb.append("analyst:\"',toJSON(ua.first_name is null,'',ua.first_name),' ',toJSON(ua.last_name is null,'',ua.last_name),'\",");
		sb.append("approver1:\"',toJSON(ua1.first_name is null,'',ua1.first_name),' ',toJSON(ua1.last_name is null,'',ua1.last_name),'\",");
		sb.append("approver2:\"',toJSON(ua2.first_name is null,'',ua2.first_name),' ',toJSON(ua2.last_name is null,'',ua2.last_name),'\",");
		sb.append("approver3:\"',toJSON(ua3.first_name is null,'',ua3.first_name),' ',toJSON(ua3.last_name is null,'',ua3.last_name),'\"");
		sb.append("}') ");
		sb.append(this.getBanWhereSqlString(bvo));
		sb.append(bvo.getOrderByCause(null));
        sb.append(bvo.getLimitCause());
		return sb.toString();
	}
	
	/**
	 * @Author Chao.liu Sep 28, 2010
	 */
	public List searchBanListExcel(BanVO bvo){
		logger.info("Enter method searchBanListExcel.");
		String sql = this.searchBanListExcelSqlString(bvo);
		Session session = getSession();
		try {
			List l = (List) session.createSQLQuery(sql).list();
			logger.info("Exit method searchBanListExcel.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author dongjian
	 * */
	public List getInvoiceChannelJsonByLineOsBussiness(String lineOfBussiness) {
		StringBuffer sb = new StringBuffer("select DISTINCT ic.id,concat('{");
		sb.append("id:\"',ic.id,'\",");
		sb.append("name:\"',toJSON(ic.invoice_channel_name is null,'',ic.invoice_channel_name),'\"");
		sb.append("}') ");
		sb.append("from ban_ref_lobcf_dict o,invoice_channel ic ");
		sb.append("where o.invoice_channel_id=ic.id ");
		//sb.append("and o.line_of_business='"+lineOfBussiness+"' ");
		sb.append("and o.rec_active_flag!='N' ");
		sb.append("and o.invoice_channel_id is not null ");
		Session session = getSession();
		try {
			List r = session.createSQLQuery(sb.toString()).list();
			return r;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author dongjian
	 * */
	public List getInvoiceFormatJsonByLineOsBussinessAndChannel(
			String lineOfBussiness, String invoiceChannel) {
		StringBuffer sb = new StringBuffer("select DISTINCT if_.id,concat('{");
		sb.append("id:\"',if_.id,'\",");
		sb.append("name:\"',toJSON(if_.invoice_format_code is null,'',if_.invoice_format_code),'\"");
		sb.append("}') ");
		sb.append("from ban_ref_lobcf_dict o,invoice_format if_ ");
		sb.append("where o.invoice_format_id=if_.id ");
		//sb.append("and o.line_of_business='"+lineOfBussiness+"' ");
//		sb.append("and o.invoice_channel_id="+invoiceChannel+" ");
		sb.append("and o.rec_active_flag!='N' ");
		sb.append("and o.invoice_format_id is not null ");
		Session session = getSession();
		try {
			List r = session.createSQLQuery(sb.toString()).list();
			return r;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author dongjian
	 * */
	public List getScoaCodeTypeJsonByLineOsBussiness(String lineOfBussiness) {
		StringBuffer sb = new StringBuffer("select DISTINCT o.scoa_code_type,concat('{");
		sb.append("id:\"',toJSON(o.scoa_code_type is null,'',o.scoa_code_type),'\",");
		sb.append("name:\"',toJSON(o.scoa_code_type is null,'',o.scoa_code_type),'\"");
		sb.append("}') ");
		sb.append("from ban_ref_lob_dict o ");
		sb.append("where o.line_of_business='"+lineOfBussiness+"' ");
		sb.append("and o.rec_active_flag!='N' ");
		sb.append("and o.scoa_code_type is not null ");
		Session session = getSession();
		try {
			List r = session.createSQLQuery(sb.toString()).list();
			return r;
		} finally {
			releaseSession(session);
		}
	}

	
	private String searchBanListExcelSqlString(BanVO bvo){
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" concat(toJSON(b.account_number is null,'',b.account_number)),");
		sb.append(" concat(toJSON(v.vendor_name is null,'',v.vendor_name)),");
		sb.append(" concat(toJSON(b.original_account_number is null,'',b.original_account_number)),");
		sb.append(" concat(toJSON(b.ap_supplier_number is null,'',b.ap_supplier_number)),");
		sb.append(" concat(toJSON(b.ap_supplier_name is null,'',b.ap_supplier_name)),");
		sb.append(" concat(toJSON(b.ap_supplier_site is null,'',b.ap_supplier_site)),");
		sb.append(" concat(toJSON(b.ap_customer_name is null,'',b.ap_customer_name)),");
		sb.append(" concat(toJSON(b.ban_create_date is null,'',b.ban_create_date)),");
		sb.append(" concat(toJSON(b.ban_close_date is null,'',b.ban_close_date)),");
		sb.append(" concat(toJSON(b.usage_backbill_months is null,'',b.usage_backbill_months)),");
		sb.append(" concat(toJSON(b.bill_day is null,'',b.bill_day)),");
		sb.append(" concat(toJSON(bs.ban_status_name is null,'',bs.ban_status_name)),");
		sb.append(" concat(toJSON(a_t.account_type_name is null,'',a_t.account_type_name)),");
		sb.append(" concat(toJSON(b.invoice_frequency is null,'',b.invoice_frequency)),");
		sb.append(" concat(toJSON(ic.invoice_channel_name is null,'',ic.invoice_channel_name)),");
		sb.append(" concat(toJSON(i_f.invoice_format_code is null,'',i_f.invoice_format_code)),");
		sb.append(" concat(toJSON(b.Missing_Email_flag IS NULL, '', b.Missing_Email_flag)),");
		sb.append(" concat(toJSON(pm.payment_method_code is null,'',pm.payment_method_code)),");
		sb.append(" concat(toJSON(pt.payment_term_code is null,'',pt.payment_term_code)),");
		sb.append(" concat(toJSON(pg.payment_group_code is null,'',pg.payment_group_code)),");
		sb.append(" concat(toJSON(b.company is null,'',b.company)),");
		sb.append(" concat(toJSON(b.location is null,'',b.location)),");
		sb.append(" concat(toJSON(b.department is null,'',b.department)),");
		sb.append(" concat(toJSON(b.channel is null,'',b.channel)),");
		sb.append(" concat(toJSON(ua.first_name is null,'',ua.first_name),' ',toJSON(ua.last_name is null,'',ua.last_name)),");
		sb.append(" concat(toJSON(ua1.first_name is null,'',ua1.first_name),' ',toJSON(ua1.last_name is null,'',ua1.last_name)),");
		sb.append(" concat(toJSON(ua2.first_name is null,'',ua2.first_name),' ',toJSON(ua2.last_name is null,'',ua2.last_name)),");
		sb.append(" concat(toJSON(ua3.first_name is null,'',ua3.first_name),' ',toJSON(ua3.last_name is null,'',ua3.last_name)), ");
		sb.append(" concat(toJSON(b.line_of_business is null,'',b.line_of_business)),");
		sb.append(" concat(toJSON(bir.description is null,'',bir.description)),");
		sb.append(" concat(toJSON(b.notes is null,'',b.notes)),");
		sb.append(" concat(toJSON(c.currency_name is null,'',c.currency_name)),");
		sb.append(" concat(toJSON(b.status_timestamp is null,'',b.status_timestamp)),");
		sb.append(" concat(toJSON(cpric.first_name is null,'',cpric.first_name),' ',toJSON(cpric.last_name is null,'',cpric.last_name),' ',toJSON(cpric.email is null,'',cpric.email)),");
		sb.append(" concat(toJSON(cpayc.first_name is null,'',cpayc.first_name),' ',toJSON(cpayc.last_name is null,'',cpayc.last_name),' ',toJSON(cpayc.email is null,'',cpayc.email)),");
		sb.append(" concat(toJSON(cdisc.first_name is null,'',cdisc.first_name),' ',toJSON(cdisc.last_name is null,'',cdisc.last_name),' ',toJSON(cdisc.email is null,'',cdisc.email)),");
		sb.append(" concat(toJSON(ctarc.first_name is null,'',ctarc.first_name),' ',toJSON(ctarc.last_name is null,'',ctarc.last_name),' ',toJSON(ctarc.email is null,'',ctarc.email)),");
		sb.append(" concat(toJSON(cbilc.first_name is null,'',cbilc.first_name),' ',toJSON(cbilc.last_name is null,'',cbilc.last_name),' ',toJSON(cbilc.email is null,'',cbilc.email)),");
		sb.append(" concat(toJSON(uc.first_name is null,'',uc.first_name),' ',toJSON(uc.last_name is null,'',uc.last_name)),");
		sb.append(" concat(toJSON(b.created_timestamp is null,'',b.created_timestamp)),");
		sb.append(" concat(toJSON(um.first_name is null,'',um.first_name),' ',toJSON(um.last_name is null,'',um.last_name)),");
		sb.append(" concat(toJSON(b.modified_timestamp is null,'',b.modified_timestamp))");
		sb.append(this.getBanWhereSqlString(bvo));
        sb.append(bvo.getLimitCause());
		return sb.toString();
	}
	
	private String getBanWhereSqlString(BanVO bvo){
		StringBuffer sb = new StringBuffer(" ");
		sb.append(this.getBanWhereSqlStringFrom());
		sb.append(this.getBanWhereSqlStringWhere(bvo));
		return sb.toString();
	}
	
	private String getBanWhereSqlStringFrom(){
		StringBuffer sb = new StringBuffer(" ");
		sb.append("from (((((((((((((((((((((`ban` b ");
		sb.append("left join `user` uc on (b.created_by = uc.id )) ");
		sb.append("left join `user` um on (b.modified_by = um.id)) ");
		sb.append("left join `vendor` v on (b.vendor_id = v.id)) ");
		sb.append("left join `ban_status` bs on (b.ban_status_id = bs.id)) ");
		sb.append("left join `account_type` a_t on (b.account_type_id = a_t.id)) ");
		sb.append("left join `invoice_channel` ic on (b.invoice_channel_id = ic.id)) ");
		sb.append("left join `invoice_format` i_f on (b.invoice_format_id = i_f.id)) ");
		sb.append("left join `payment_method` pm on (b.payment_method_id = pm.id)) ");
		sb.append("left join `payment_term` pt on (b.payment_term_id = pt.id)) ");
		sb.append("left join `payment_group` pg on (b.payment_group_id = pg.id)) ");
		sb.append("left join `currency` c on (b.currency_id = c.id)) ");
		sb.append("left join `contact` cpric on (b.ban_primiary_contact_id = cpric.id)) ");
		sb.append("left join `contact` cpayc on(b.ban_payment_contact_id = cpayc.id)) ");
		sb.append("left join `contact` cdisc on(b.ban_dispute_contact_id = cdisc.id)) ");
		sb.append("left join `contact` ctarc on(b.ban_tariff_contact_id = ctarc.id)) ");
		sb.append("left join `contact` cbilc on(b.ban_billing_contact_id = cbilc.id)) ");
		sb.append("left join `user` ua on (b.analyst_id = ua.id )) ");
		sb.append("left join `user` ua1 on (b.approver1_id = ua1.id )) ");
		sb.append("left join `user` ua2 on (b.approver2_id = ua2.id )) ");
		sb.append("left join `user` ua3 on (b.approver3_id = ua3.id )) ");
		sb.append("left join `ban_inactive_reason` bir on (b.ban_inactive_reason_id = bir.id )) ");
		sb.append("where b.master_ban_flag = 'Y' ");
		sb.append("and b.rec_active_flag = 'Y' ");
		return sb.toString();
	}
	
	private String getBanWhereSqlStringWhere(BanVO bvo){
		StringBuffer sb = new StringBuffer(" ");
		if(bvo.getFilter()!=null){
			sb.append("and " + bvo.getFilter() + " ");
		}	
		if(bvo.getVendorId() != null){
			sb.append("and v.id = " + bvo.getVendorId() + " ");
		}
		if(bvo.getAccountNumber() != null){
			sb.append("and b.account_number like '%" + bvo.getAccountNumber() + "%' ");
		}
		if(bvo.getAPSupplierNumber() != null){
			sb.append("and b.ap_supplier_number like '%" + bvo.getAPSupplierNumber() + "%' ");
		}
		if(bvo.getAPSupplierName() != null){
			sb.append("and b.ap_supplier_name like '%" + bvo.getAPSupplierName() + "%' ");
		}
		if(bvo.getAPSupplierSite() != null){
			sb.append("and b.ap_supplier_site like '%" + bvo.getAPSupplierSite() + "%' ");
		}
		if(bvo.getCompany() != null){
			sb.append("and b.company like '%" + bvo.getCompany() + "%' ");
		}
		if(bvo.getLocation() != null){
			sb.append("and b.location like '%" + bvo.getLocation() + "%' ");
		}
		if(bvo.getChannel() != null){
			sb.append("and b.channel like '%" + bvo.getChannel() + "%' ");
		}
		if(bvo.getBanStatusId() != null){
			sb.append("and bs.id = " + bvo.getBanStatusId() + " ");
		}
		if(bvo.getInvoiceChannelId() != null){
			sb.append("and ic.id = " + bvo.getInvoiceChannelId() + " ");
		}
		if(bvo.getInvoiceFormatId() != null){
			sb.append("and i_f.id = " + bvo.getInvoiceFormatId() + " ");
		}
		if(bvo.getPaymentMethodId() != null){
			sb.append("and pm.id = " + bvo.getPaymentMethodId() + " ");
		}
		if(bvo.getAnalystId() != null){
			sb.append("and ua.id = " + bvo.getAnalystId() + " ");
		}
		if(bvo.getLineofBbusiness() != null){
			sb.append("and b.line_of_business = '" + bvo.getLineofBbusiness() + "' ");
		}
		if(bvo.getBanInactiveReasonId() != null){
			sb.append("and b.ban_inactive_reason_id = '" + bvo.getBanInactiveReasonId() + "' ");
		}
		if(bvo.getUid() != null){
			Integer uid;
//			if(bvo.getUid() > 0){
//				sb.append("and b.created_by = '" + bvo.getUid() + "' ");
//			}else{
//				sb.append("and b.created_by = '" + SystemUtil.getCurrentUserId() + "' ");
//			}
			if(bvo.getUid() > 0){
				uid = bvo.getUid();
			}else {
				uid = SystemUtil.getCurrentUserId();
			}
			if(bvo.getBanStatusId() == 4){
				sb.append(" AND (SELECT CASE (SELECT COUNT(1) FROM user_role ur " +
						"LEFT JOIN role r ON ur.role_id = r.id " +
						"LEFT JOIN role_function rf ON rf.role_id = r.id " +
						"LEFT JOIN function f ON f.id = rf.function_id " +
						"WHERE ur.user_id = '"+uid+"' AND f.id IN (4910)" +
						" AND r.rec_active_flag = 'Y' AND rf.rec_active_flag = 'Y' AND f.function_active_flag = 'Y' AND ur.rec_active_flag = 'Y') " +
						"WHEN 0 THEN b.created_by = '"+uid+"' ELSE 1 = 1 END)");
			}else{
				sb.append(" AND b.created_by = '"+uid+"'");
			}
		}else{
			sb.append(" AND (SELECT CASE (SELECT COUNT(1) FROM user_role ur " +
					"LEFT JOIN role r ON ur.role_id = r.id " +
					"LEFT JOIN role_function rf ON rf.role_id = r.id " +
					"LEFT JOIN function f ON f.id = rf.function_id " +
					"WHERE ur.user_id = '"+SystemUtil.getCurrentUserId()+"' AND f.id IN (4900)" +
					" AND r.rec_active_flag = 'Y' AND rf.rec_active_flag = 'Y' AND f.function_active_flag = 'Y' AND ur.rec_active_flag = 'Y') " +
					"WHEN 0 THEN b.created_by = '"+SystemUtil.getCurrentUserId()+"' ELSE 1 = 1 END)");
//			sb.append(" and bs.id not in (4,5)");
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List getUserPreviledgedBanByVendorId(final Integer userId, final Integer vendorId) {
		logger.info("Enter method getUserPreviledgedBanByVendorId with userId "+userId+" and vendorId "+vendorId+".");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select b.id,b.account_number from ban b where b.rec_active_flag = 'Y' and b.ban_status_id != 2 and b.master_ban_flag='Y' and b.vendor_id = " + vendorId + " order by b.account_number asc ").list();
			logger.info("Exit method getUserPreviledgedBanByVendorId.");
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public String getValidateBanLock(Integer banId) {
		Session session = getSession();
		try {
			return (String)session.createSQLQuery("select b.process_status from ban b where b.id="+banId).uniqueResult();
		} finally {
			releaseSession(session);
		}
	}

	public Integer findMaxId() {
		Session session = getSession();
		try {
			return (Integer)session.createSQLQuery("select max(b.id) from ban b").uniqueResult();
		} finally {
			releaseSession(session);
		}
	}

	public Long getAccCountByName(String accNoName) {
		Session session = getSession();
		try {
			BigInteger b = (BigInteger)session.createSQLQuery("select count(1) from ban b where b.account_number='" + accNoName + "'").uniqueResult();
			return b.longValue();
		} finally {
			releaseSession(session);
		}
	}

	public String getVendorNameByVendorId(Integer id) {
		logger.info("Enter method getVendorNameByVendorId.");
		String sql = "select v.vendor_name from vendor v where v.id="+id;
		Session session = getSession();
		try {
			String vname = (String) session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method getVendorNameByVendorId.");
			return vname;
		} finally {
			releaseSession(session);
		}
	}
//add By donghao.guo for search condition
	public List<Ban> getSearchConditionBan() {
		logger.info("Enter method getSearchConditionBan.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select b.id,b.account_number from ban b where b.rec_active_flag='Y' and b.master_ban_flag='Y' and b.account_number!='ALL' order by length(b.account_number)").list();
			logger.info("Exit method getSearchConditionBan.");
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public void updateInvoiceAndEmail(Ban ban) {
		logger.info("Enter method updateInvoiceAndEmail.");
		StringBuilder sb = new StringBuilder();
		sb.append("update ban b,invoice i,email e set");
		sb.append(" i.rec_active_flag='" + ban.getMissingEmailflag() + "',");
	    sb.append(" e.rec_active_flag='" + ban.getMissingEmailflag() + "'");
	    sb.append(" where b.id = i.ban_id");
        sb.append(" and e.reference_id = i.id");
        sb.append(" and e.email_type='I'");
        sb.append(" and i.invoice_status_id=1");
        sb.append(" and i.account_number='" + ban.getAccountNumber() + "'");
        sb.append(" and b.id=" + ban.getId());
        sb.append(" and b.rec_active_flag='Y'");
        sb.append(" and b.master_ban_flag='Y'");
        sb.append(" and i.modified_by=" + SystemUtil.getCurrentUserId());
        sb.append(" and i.modified_timestamp = now()");
        sb.append(" and e.modified_by=" + SystemUtil.getCurrentUserId());
        sb.append(" and e.modified_timestamp = now()");
    	Session session = getSession();
    	try {
    		session.createSQLQuery(sb.toString()).executeUpdate();
    	} finally {
    		releaseSession(session);
    	}
		logger.info("Exit method updateInvoiceAndEmail.");
	}
	/**
	 * ??????? Ban ? invoice ?????
	 * @param {Ban} ?? ban?
	 */
	public void updateInvoicesForBan(Ban ban) {
		logger.info("Enter method updateInvoicesForBan.");
		StringBuilder sb = new StringBuilder();
		
		// ?? ??? external approve flag
		this.updateExternalApproveFlagOfAllInvoiceForTheBan(ban.getId(), ban.getExternalApproveFlag());
		
		sb.append("update ban b,invoice i set");
		sb.append(" i.vendor_id = '" + ban.getVendor().getId() + "' ");
		sb.append(" where b.id = i.ban_id");
		sb.append(" and b.id = " + ban.getId());
		sb.append(" and i.invoice_status_id < 21");
		sb.append(" and i.rec_active_flag='Y'");
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateInvoicesForBan.");
	}
	
	/**
	 * ????ban???? external approve flag
	 * @param {Integer} banId
	 * @param {String} externalApproveFlag [Y | N]
	 */
	private void updateExternalApproveFlagOfAllInvoiceForTheBan(Integer banId, String externalApproveFlag) {
		logger.info("Enter method updateExternalApproveFlagOfInvoice");
		
		StringBuffer updateStringBuffer = new StringBuffer();
		updateStringBuffer.append(" UPDATE invoice i");
		updateStringBuffer.append(" SET i.external_approve_flag = '"+ externalApproveFlag +"' ");
		updateStringBuffer.append(" WHERE i.ban_id = " + banId);
		updateStringBuffer.append(" AND i.rec_active_flag = 'Y'");
		if ("Y".equals(externalApproveFlag)) {
			updateStringBuffer.append(" AND i.invoice_status_id >= 9");
			updateStringBuffer.append(" AND i.invoice_status_id <= 10");
			updateStringBuffer.append(" AND NOT EXISTS (SELECT t.* FROM transaction_history t WHERE t.invoice_id = i.id AND t.rec_active_flag = 'Y' AND t.workflow_action_id = 91); ");
		} else {
			updateStringBuffer.append(" AND 1 = 1");
		}
		
		Session session = getSession();
		try {
			session.createSQLQuery(updateStringBuffer.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateExternalApproveFlagOfInvoice");
	}
	
	public Integer getInvoiceByBan(Ban b){
		logger.info("Enter method getInvoiceByBan.");
		final String sql = this.getInvoiceByBanSqlString(b);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getInvoiceByBan.");
		return count;
	}
	
	private String getInvoiceByBanSqlString(Ban b){
		StringBuffer sb = new StringBuffer("select count(1) from invoice i where i.rec_active_flag = 'Y' and i.invoice_status_id between 3 and 40 and i.ban_id='" + b.getId() + "'");
		return sb.toString();
	}

	public String getApprovalLevel() {
		Session session=getSession();
		try {
			String sql="select sc.value from sys_config sc where sc.parameter='max_approval_level'";
			List<?> l = session.createSQLQuery(sql).list();
			if(l!=null && l.size()>0){
				String result=l.get(0).toString();
				return result;
			}
		} finally{
			releaseSession(session);
		}
		return null;
	}
	
	public String getAutoPayMaxCount() {
		Session session=getSession();
		try {
			String sql="select sc.value from sys_config sc where sc.parameter='Auto_Pay_Max_Count'";
			List<?> l = session.createSQLQuery(sql).list();
			if(l!=null && l.size()>0){
				String result=l.get(0).toString();
				return result;
			}
		} finally{
			releaseSession(session);
		}
		return null;
	}
	

	public long getBanWorkCountNOPaymentInException(WorkspaceVO wVO,Integer banStatusId) {
		logger.info("Enter method getBanWorkCountNOPaymentInException.");
		Integer uid;
		final StringBuffer sb = new StringBuffer("SELECT COUNT(1) FROM ban WHERE rec_active_flag = 'Y'");
		if(wVO.getUid()==-1){
			uid = SystemUtil.getCurrentUserId();
		}else {
			uid = wVO.getUid();
		}
		if(banStatusId == 4){
			sb.append(" AND (SELECT CASE (SELECT COUNT(1) FROM user_role ur " +
					"LEFT JOIN role r ON ur.role_id = r.id " +
					"LEFT JOIN role_function rf ON rf.role_id = r.id " +
					"LEFT JOIN function f ON f.id = rf.function_id " +
					"WHERE ur.user_id = '"+uid+"' AND f.id IN (4910) " +
					" AND r.rec_active_flag = 'Y' AND rf.rec_active_flag = 'Y' AND f.function_active_flag = 'Y' AND ur.rec_active_flag = 'Y') " +
					"WHEN 0 THEN created_by = '"+uid+"' ELSE 1 = 1 END)");
		}else{
			sb.append(" AND created_by = '"+uid+"'");
		}
		sb.append(" and ban_status_id = " + banStatusId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sb.toString()).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getBanWorkCountNOPaymentInException.");
		return count;
	}
	
	public void updateBanStatusId(Integer banId,Integer banStatusId,String rejectNotes){
		logger.info("Enter method updateBanStatusId.");
		StringBuilder sb = new StringBuilder();
		sb.append("update ban b set");
		if(banStatusId == 5 && rejectNotes!=null && !"".equals(rejectNotes)){
			sb.append(" b.reject_notes = '" + CcmUtil.stringToSql(rejectNotes) + "',");
		}
		sb.append(" b.ban_status_id = '" + banStatusId + "',modified_timestamp = now(),modified_by ="+SystemUtil.getCurrentUserId());
		sb.append(" where b.id = " + banId);
		sb.append(" and b.rec_active_flag='Y'");
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	
	public void deleteBanById(Integer banId){
		logger.info("Enter method deleteBanById.");
		StringBuilder sb = new StringBuilder();
		sb.append("update ban b set");
		sb.append(" b.rec_active_flag = 'N',modified_timestamp = now(),modified_by ="+SystemUtil.getCurrentUserId());
		sb.append(" where b.id = " + banId);
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
}
