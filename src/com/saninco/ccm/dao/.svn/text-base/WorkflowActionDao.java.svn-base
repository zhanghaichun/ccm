package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.WorkflowAction;

/**
 * A data access object (DAO) providing persistence and search support for
 * WorkflowAction entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.WorkflowAction
 * @author MyEclipse Persistence Tools
 */

public class WorkflowActionDao extends HibernateDaoSupport implements IWorkflowActionDao {
	private final Logger log = Logger.getLogger(this.getClass());
	// property constants
	public static final String WORKFLOW_ACTION_NAME = "workflowActionName";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#save(com.saninco.ccm.model.WorkflowAction)
	 */
	public void save(WorkflowAction transientInstance) {
		log.debug("saving WorkflowAction instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#delete(com.saninco.ccm.model.WorkflowAction)
	 */
	public void delete(WorkflowAction persistentInstance) {
		log.debug("deleting WorkflowAction instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#findById(java.lang.Integer)
	 */
	public WorkflowAction findById(java.lang.Integer id) {
		log.debug("getting WorkflowAction instance with id: " + id);
		try {
			WorkflowAction instance = (WorkflowAction) getHibernateTemplate()
					.get("com.saninco.ccm.model.WorkflowAction", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#findByExample(com.saninco.ccm.model.WorkflowAction)
	 */
	public List findByExample(WorkflowAction instance) {
		log.debug("finding WorkflowAction instance by example");
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding WorkflowAction instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from WorkflowAction as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#findByWorkflowActionName(java.lang.Object)
	 */
	public List findByWorkflowActionName(Object workflowActionName) {
		return findByProperty(WORKFLOW_ACTION_NAME, workflowActionName);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all WorkflowAction instances");
		try {
			String queryString = "from WorkflowAction";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#merge(com.saninco.ccm.model.WorkflowAction)
	 */
	public WorkflowAction merge(WorkflowAction detachedInstance) {
		log.debug("merging WorkflowAction instance");
		try {
			WorkflowAction result = (WorkflowAction) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#attachDirty(com.saninco.ccm.model.WorkflowAction)
	 */
	public void attachDirty(WorkflowAction instance) {
		log.debug("attaching dirty WorkflowAction instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IWorkflowActionDao#attachClean(com.saninco.ccm.model.WorkflowAction)
	 */
	public void attachClean(WorkflowAction instance) {
		log.debug("attaching clean WorkflowAction instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IWorkflowActionDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (IWorkflowActionDao) ctx.getBean("WorkflowActionDAO");
	}

	public WorkflowAction load(int id) {
		log.debug("loading WorkflowAction instance with id: " + id);
		try {
			WorkflowAction instance = (WorkflowAction) getHibernateTemplate().load(WorkflowAction.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("load failed", re);
			throw re;
		}
	}
}