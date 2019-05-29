package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Email
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.saninco.ccm.model.Email
 * @author MyEclipse Persistence Tools
 */

public class EmailDAO extends HibernateDaoSupport {
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
}