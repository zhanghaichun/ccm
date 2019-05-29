package com.saninco.ccm.model;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * ItemStructure entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.ItemStructure
 * @author MyEclipse Persistence Tools
 */

public class ItemStructureDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ItemStructureDAO.class);
	// property constants
	public static final String MULTILINE_FLAG = "multilineFlag";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String PROPOSAL_FLAG = "proposalFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(ItemStructure transientInstance) {
		log.debug("saving ItemStructure instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ItemStructure persistentInstance) {
		log.debug("deleting ItemStructure instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ItemStructure findById(java.lang.Integer id) {
		log.debug("getting ItemStructure instance with id: " + id);
		try {
			ItemStructure instance = (ItemStructure) getHibernateTemplate()
					.get("com.saninco.ccm.model.ItemStructure", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ItemStructure instance) {
		log.debug("finding ItemStructure instance by example");
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
		log.debug("finding ItemStructure instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ItemStructure as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMultilineFlag(Object multilineFlag) {
		return findByProperty(MULTILINE_FLAG, multilineFlag);
	}

	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	public List findByProposalFlag(Object proposalFlag) {
		return findByProperty(PROPOSAL_FLAG, proposalFlag);
	}

	public List findAll() {
		log.debug("finding all ItemStructure instances");
		try {
			String queryString = "from ItemStructure";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ItemStructure merge(ItemStructure detachedInstance) {
		log.debug("merging ItemStructure instance");
		try {
			ItemStructure result = (ItemStructure) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ItemStructure instance) {
		log.debug("attaching dirty ItemStructure instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ItemStructure instance) {
		log.debug("attaching clean ItemStructure instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ItemStructureDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ItemStructureDAO) ctx.getBean("ItemStructureDAO");
	}
}