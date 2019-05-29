/**
 * 
 */
package com.saninco.ccm.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.AccountCodeVO;

/**
 * @author xinyu.Liu
 */
public class AccountCodeDaoImpl extends HibernateDaoSupport implements IAccountCodelDao { 
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public List getAccountCode() {
		logger.info("Enter method getAccountCode");
		Session session = getSession();
		try {
			List list = session.createSQLQuery("select ac.id,ac.account_code_name from account_code ac where ac.rec_active_flag='Y' and ac.active_flag='Y' order by ac.account_code_name asc").list();
			logger.info("Exit method getAccountCode");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	
	public List getAllAccountCode() {
		logger.info("Enter method getAllAccountCode");
		Session session = getSession();
		try {
			List list = session.createSQLQuery("select ac.id,ac.account_code_name from account_code ac where length(ac.account_code_name) = 42 and length(replace(ac.account_code_name,'.','')) = 34 order by ac.account_code_name asc;").list();
			logger.info("Exit method getAllAccountCode");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	
	public List getChargeType() {
		logger.info("Enter method getChargeType");
		Session session = getSession();
		try {
			List list = session.createSQLQuery("select f_get_root_item_type(id), item_type_summary from item_type where f_get_root_item_type(id) != 0 group by item_type_summary;").list();
			logger.info("Exit method getChargeType");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	
	public List getTaxCode() {
		logger.info("Enter method getTaxCode");
		Session session = getSession();
		try {
			List list = session.createSQLQuery("select tc.id,tc.tax_code from tax_code tc order by tc.tax_code asc;").list();
			logger.info("Exit method getTaxCode");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	
	public AccountCode findById(java.lang.Integer id) {
		//log.debug("getting AccountCode instance with id: " + id);
		try {
			AccountCode instance = (AccountCode) getHibernateTemplate().get(
					"com.saninco.ccm.model.AccountCode", id);
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}
	
	public List<AccountCode> findByName(String scoacode) {
		try {
			String queryString = "from AccountCode as model where model.accountCodeName=?";
			return (List<AccountCode>)getHibernateTemplate().find(queryString, scoacode);
		} catch (RuntimeException re) {
			logger.error("find by findByName failed", re);
			throw re;
		}
	}
	/**
	 * Check name is repeate
	 * @author Chao.Liu Date:Sep 9, 2010
	 * @param avo
	 * @return
	 */
	public String checkScoaCodeName(AccountCodeVO avo){
		String result = "";
		String sql = "select rec_active_flag,active_flag from `account_code` where account_code_name = '"+avo.getScoaName()+"'";
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sql).list();
			if (l.size() > 0) {
				result = l.get(0).toString();
			}
			return result;
		} finally {
			releaseSession(session);
		}
	}
	
	public Object[] checkScoaCodeNameOne(AccountCodeVO avo){
		Object result[] = null;
		String sql = "select rec_active_flag,active_flag from `account_code` where account_code_name = '"+avo.getScoaName()+"'";
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sql).list();
			if (l.size() > 0) {
				result =  (Object[]) l.get(0);
			}
			return result;
		} finally {
			releaseSession(session);
		}
	}
	
	public void save(Object o){
		this.getHibernateTemplate().save(o);
	}
	public void save0(AccountCode o){
		Session session=getSession();
		try {
			Query query = session.createSQLQuery("update account_code ac set ac.rec_active_flag='Y'" +
					",ac.active_flag='Y',ac.modified_timestamp=now(),ac.modified_by="+SystemUtil.getCurrentUserId() +
					" where ac.account_code_name ='"+o.getAccountCodeName()+"';");  
			query.executeUpdate();  
		} finally{
			releaseSession(session);
		}
	}
	
	public List getAccountCodeListByDisputeProposals(Integer disputeId) {
		logger.info("Enter method getAccountCodeListByDisputeProposals");
		Session session = getSession();
		try {
			List list = session.createSQLQuery("select DISTINCT ac.id,ac.account_code_name from proposal p,account_code ac where p.account_code_id=ac.id and ac.rec_active_flag='Y' and p.rec_active_flag='Y' and p.dispute_id="+disputeId+" order by ac.account_code_name asc").list();
			logger.info("Exit method getAccountCodeListByDisputeProposals");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	public void delete(AccountCodeVO avo) {
		logger.info("Enter method delete account code");
		Session session = getSession();
		try {
			session.createQuery("update AccountCode ac set ac.activeFlag='N',ac.recActiveFlag='Y',ac.modifiedTimestamp='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',ac.modifiedBy=" + SystemUtil.getCurrentUserId() + " where ac.recActiveFlag='Y' and ac.accountCodeName='" + avo.getScoaName() + "'").executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method delete account code");
	}
	
	public AccountCode merge(AccountCode accountCode) {
		logger.debug("merging AccountCode instance");
		try {
			AccountCode result = (AccountCode) getHibernateTemplate()
					.merge(accountCode);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	
}
