package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * AccountCode entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.AccountCode
 * @author MyEclipse Persistence Tools
 */

public class AccountCodeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(AccountCodeDAO.class);
	// property constants
	public static final String ACCOUNT_CODE_NAME = "accountCodeName";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String ACCOUNT_CODE_DESCRIPTION = "accountCodeDescription";
	public static final String COMPANY = "company";
	public static final String LOCATION = "location";
	public static final String DEPARTMENT = "department";
	public static final String PRODUCT = "product";
	public static final String ACCOUNT = "account";
	public static final String CHANNEL = "channel";
	public static final String SEGMENT7 = "segment7";
	public static final String SEGMENT8 = "segment8";
	public static final String INTERCO = "interco";

	protected void initDao() {
		// do nothing
	}

	public void save(AccountCode transientInstance) {
		log.debug("saving AccountCode instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AccountCode persistentInstance) {
		log.debug("deleting AccountCode instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AccountCode findById(java.lang.Integer id) {
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

	public List findByExample(AccountCode instance) {
		log.debug("finding AccountCode instance by example");
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
		log.debug("finding AccountCode instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AccountCode as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccountCodeName(Object accountCodeName) {
		return findByProperty(ACCOUNT_CODE_NAME, accountCodeName);
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

	public List findByAccountCodeDescription(Object accountCodeDescription) {
		return findByProperty(ACCOUNT_CODE_DESCRIPTION, accountCodeDescription);
	}

	public List findByCompany(Object company) {
		return findByProperty(COMPANY, company);
	}

	public List findByLocation(Object location) {
		return findByProperty(LOCATION, location);
	}

	public List findByDepartment(Object department) {
		return findByProperty(DEPARTMENT, department);
	}

	public List findByProduct(Object product) {
		return findByProperty(PRODUCT, product);
	}

	public List findByAccount(Object account) {
		return findByProperty(ACCOUNT, account);
	}

	public List findByChannel(Object channel) {
		return findByProperty(CHANNEL, channel);
	}

	public List findBySegment7(Object segment7) {
		return findByProperty(SEGMENT7, segment7);
	}

	public List findBySegment8(Object segment8) {
		return findByProperty(SEGMENT8, segment8);
	}

	public List findByInterco(Object interco) {
		return findByProperty(INTERCO, interco);
	}

	public List findAll() {
		log.debug("finding all AccountCode instances");
		try {
			String queryString = "from AccountCode";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AccountCode merge(AccountCode detachedInstance) {
		log.debug("merging AccountCode instance");
		try {
			AccountCode result = (AccountCode) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AccountCode instance) {
		log.debug("attaching dirty AccountCode instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AccountCode instance) {
		log.debug("attaching clean AccountCode instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static AccountCodeDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (AccountCodeDAO) ctx.getBean("AccountCodeDAO");
	}
}