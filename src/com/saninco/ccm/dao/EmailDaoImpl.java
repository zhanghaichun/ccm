package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Email;
import com.saninco.ccm.model.EmailDAO;
import com.saninco.ccm.vo.OperationsVO;

public class EmailDaoImpl extends HibernateDaoSupport implements IEmailDao {
	private static final Log log = LogFactory.getLog(EmailDAO.class);
	// property constants
	public static final String SUBJECT = "subject";
	public static final String TO_ADDRESS = "toAddress";
	public static final String CC_ADDRESS = "ccAddress";
	public static final String BCC_ADDRESS = "bccAddress";
	public static final String CONTENT = "content";
	public static final String EMAIL_STATUS = "emailStatus";
	public static final String SYSTEM_MESSAGE = "systemMessage";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	
	public List<Map<String,String>> getDisputeReminderEmail(){
		
		logger.info("Enter method getTheInvoiceReconciliationPageData.");
		StringBuffer sb = new StringBuffer("SELECT d.dispute_number, dm.attachment_point_id, date_format(d.created_timestamp, '%Y-%m-%d %H:%i:%s') as created_timestamp,");
		sb.append(" trim(subString_index(subString_index(dm.from_address, '(', -1), ')', 1)) from_address,");
		sb.append(" trim(subString_index(subString_index(dm.to_address, '(', -1), ')', 1)) to_address,");
		sb.append(" trim(subString_index(subString_index(dm.cc_address, '(', -1), ')', 1)) cc_address,");
		sb.append(" trim(subString_index(subString_index(dm.reply_to, '(', -1), ')', 1)) reply_to");
		sb.append(" FROM dispute_message dm, dispute d WHERE d.id = dm.dispute_id AND d.rec_active_flag = 'Y'");
		sb.append(" AND dm.rec_active_flag = 'Y' AND d.replay_email_datetime IS NULL");
		sb.append(" AND d.dispute_status_id in (30,31,34,35,36)");
		sb.append(" AND FN_GET_DISPUTE_MESSAGE_STATUS(d.id) =  CONVERT('A' USING utf8) COLLATE utf8_unicode_ci ");
		sb.append(" GROUP BY d.id ");
		Session session = getSession();
		try {
			List<Map<String,String>> re = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			logger.info("Exit method getTheInvoiceReconciliationPageData.");
			return re;
		} finally {
			releaseSession(session);
		}
		
	}
	
	public void save(Email transientInstance) {
		log.debug("saving Email instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Email persistentInstance) {
		log.debug("deleting Email instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Email findById(java.lang.Integer id) {
		log.debug("getting Email instance with id: " + id);
		try {
			Email instance = (Email) getHibernateTemplate().get(
					"com.saninco.ccm.model.Email", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Email instance) {
		log.debug("finding Email instance by example");
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
		log.debug("finding Email instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Email as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySubject(Object subject) {
		return findByProperty(SUBJECT, subject);
	}

	public List findByToAddress(Object toAddress) {
		return findByProperty(TO_ADDRESS, toAddress);
	}

	public List findByCcAddress(Object ccAddress) {
		return findByProperty(CC_ADDRESS, ccAddress);
	}

	public List findByBccAddress(Object bccAddress) {
		return findByProperty(BCC_ADDRESS, bccAddress);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByEmailStatus(Object emailStatus) {
		return findByProperty(EMAIL_STATUS, emailStatus);
	}

	public List findBySystemMessage(Object systemMessage) {
		return findByProperty(SYSTEM_MESSAGE, systemMessage);
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

	public List findAll() {
		log.debug("finding all Email instances");
		try {
			String queryString = "from Email";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	/***
	 * @author wei.su
	 * @param emailId
	 * @return
	 */
	public List findCheckedEmailsToSend(String emailIds) {
		log.debug("find Checked Emails To Send");
		try {
			String queryString = "from Email e where e.id in ("+emailIds+")";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find Checked Emails To Send failed", re);
			throw re;
		}
	}
	public Email merge(Email detachedInstance) {
		log.debug("merging Email instance");
		try {
			Email result = (Email) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Email instance) {
		log.debug("attaching dirty Email instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	public void attachClean(Email instance) {
		log.debug("attaching clean Email instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EmailDAO getFromApplicationContext(ApplicationContext ctx) {
		return (EmailDAO) ctx.getBean("EmailDAO");
	}
	/**
	 * @author wei.su
	 */
	public Integer getPreEmailsTotalPageNoOfDispute(OperationsVO operationsVO){
		logger.info("Enter method getPreEmailsOfDispute.");
		final StringBuffer sb = new StringBuffer("select count(1) from email AS e  ");
		sb.append(" where e.rec_active_flag = 'Y' ");
		//sb.append("and e.email_status = '1' ");
		if(operationsVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(operationsVO.getFilter());
		}	
		HibernateTemplate template = this.getHibernateTemplate();
		List l = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		Integer Count = new Integer(l.get(0).toString());
		logger.info("Exit method getPreEmailsOfDispute.");
		return Count;
	}
	@SuppressWarnings("unchecked")
	/***
	 * @author wei.su
	 */
	public List<String> searchPreEmailsOfDispute(OperationsVO operationsVO)
	{
		logger.info("Enter method searchPreEmailsOfDispute.");
		final StringBuffer sb = new StringBuffer("select concat('{id:',toJSON(e.id is null,'',e.id),',email_type:\"',toJSON(e.email_type is null,'',e.email_type),'\",email_status:\"',toJSON(e.email_status is null,'',e.email_status),'\",subject:\"',toJSON(e.subject is null,'',e.subject),'\",to_address:\"',toJSON(e.to_address is null,'',e.to_address),");
		sb.append("'\",sent_datetime:\"',toJSON(e.sent_datetime is null,'',e.sent_datetime),");
		sb.append("'\",attachment_point_id:\"',toJSON(e.attachment_point_id is null,'',e.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(" from email AS e ");
		sb.append("where ");
		//sb.append("e.email_status = '1' ");
		sb.append(" e.rec_active_flag = 'Y' ");
		if(operationsVO.getFilter()!=null){
			sb.append(" and ");
			sb.append(operationsVO.getFilter());
		}	
		sb.append(" order by " + operationsVO.getSortField() + " " + operationsVO.getSortingDirection());
		sb.append(" LIMIT " + operationsVO.getStartIndex() + "," + operationsVO.getRecPerPage());
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sb.toString()).list();
		    }
		});
		logger.info("Exit method searchPreEmailsOfDispute.");
		return list;	
	}
}
