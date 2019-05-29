package com.saninco.ccm.model;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccountType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.AccountType
 * @author MyEclipse Persistence Tools
 */

public class AccountTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(AccountTypeDAO.class);
	// property constants
	public static final String ACCOUNT_TYPE_NAME = "accountTypeName";

	protected void initDao() {
		// do nothing
	}

	public void save(AccountType transientInstance) {
		log.debug("saving AccountType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AccountType persistentInstance) {
		log.debug("deleting AccountType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AccountType findById(java.lang.Integer id) {
		log.debug("getting AccountType instance with id: " + id);
		try {
			AccountType instance = (AccountType) getHibernateTemplate().get(
					"com.saninco.ccm.model.AccountType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AccountType instance) {
		log.debug("finding AccountType instance by example");
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
		log.debug("finding AccountType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AccountType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccountTypeName(Object accountTypeName) {
		return findByProperty(ACCOUNT_TYPE_NAME, accountTypeName);
	}

	public List findAll() {
		log.debug("finding all AccountType instances");
		try {
			String queryString = "from AccountType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AccountType merge(AccountType detachedInstance) {
		log.debug("merging AccountType instance");
		try {
			AccountType result = (AccountType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AccountType instance) {
		log.debug("attaching dirty AccountType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AccountType instance) {
		log.debug("attaching clean AccountType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static AccountTypeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (AccountTypeDAO) ctx.getBean("AccountTypeDAO");
	}
}