package com.saninco.ccm.model;

import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ReceivedEmail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.ReceivedEmail
 * @author MyEclipse Persistence Tools
 */
public class ReceivedEmailDAO extends HibernateDaoSupport  {
	private static final Log log = LogFactory.getLog(ReceivedEmailDAO.class);
	// property constants
	public static final String MESSAGE_ID = "messageId";
	public static final String FROM_ADDRESS = "fromAddress";
	public static final String TO_ADDRESS = "toAddress";
	public static final String CC_ADDRESS = "ccAddress";
	public static final String BCC_ADDRESS = "bccAddress";
	public static final String IN_REPLY_TO = "inReplyTo";
	public static final String SUBJECT = "subject";
	public static final String CONTENT_TEXT = "contentText";
	public static final String CONTENT_HTML = "contentHtml";
	public static final String REPLY_SIGN = "replySign";
	public static final String MSG_REFERENCES = "msgReferences";
	public static final String DESCRIPTION = "description";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	public void save(ReceivedEmail transientInstance) {
		log.debug("saving ReceivedEmail instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ReceivedEmail persistentInstance) {
		log.debug("deleting ReceivedEmail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReceivedEmail findById(java.lang.Integer id) {
		log.debug("getting ReceivedEmail instance with id: " + id);
		try {
			ReceivedEmail instance = (ReceivedEmail) getSession().get(
					"com.saninco.ccm.model.ReceivedEmail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ReceivedEmail instance) {
		log.debug("finding ReceivedEmail instance by example");
		try {
			List results = getSession()
					.createCriteria("com.saninco.ccm.model.ReceivedEmail")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ReceivedEmail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ReceivedEmail as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMessageId(Object messageId) {
		return findByProperty(MESSAGE_ID, messageId);
	}

	public List findByFromAddress(Object fromAddress) {
		return findByProperty(FROM_ADDRESS, fromAddress);
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

	public List findByInReplyTo(Object inReplyTo) {
		return findByProperty(IN_REPLY_TO, inReplyTo);
	}

	public List findBySubject(Object subject) {
		return findByProperty(SUBJECT, subject);
	}

	public List findByContentText(Object contentText) {
		return findByProperty(CONTENT_TEXT, contentText);
	}

	public List findByContentHtml(Object contentHtml) {
		return findByProperty(CONTENT_HTML, contentHtml);
	}

	public List findByReplySign(Object replySign) {
		return findByProperty(REPLY_SIGN, replySign);
	}

	public List findByMsgReferences(Object msgReferences) {
		return findByProperty(MSG_REFERENCES, msgReferences);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
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
		log.debug("finding all ReceivedEmail instances");
		try {
			String queryString = "from ReceivedEmail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReceivedEmail merge(ReceivedEmail detachedInstance) {
		log.debug("merging ReceivedEmail instance");
		try {
			ReceivedEmail result = (ReceivedEmail) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReceivedEmail instance) {
		log.debug("attaching dirty ReceivedEmail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReceivedEmail instance) {
		log.debug("attaching clean ReceivedEmail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}