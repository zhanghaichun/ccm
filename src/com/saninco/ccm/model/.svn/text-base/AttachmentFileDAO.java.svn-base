package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * AttachmentFile entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.AttachmentFile
 * @author MyEclipse Persistence Tools
 */

public class AttachmentFileDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(AttachmentFileDAO.class);
	// property constants
	public static final String FILE_PATH = "filePath";
	public static final String FILE_NAME = "fileName";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(AttachmentFile transientInstance) {
		log.debug("saving AttachmentFile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AttachmentFile persistentInstance) {
		log.debug("deleting AttachmentFile instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AttachmentFile findById(java.lang.Integer id) {
		log.debug("getting AttachmentFile instance with id: " + id);
		try {
			AttachmentFile instance = (AttachmentFile) getHibernateTemplate()
					.get("com.saninco.ccm.model.AttachmentFile", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AttachmentFile instance) {
		log.debug("finding AttachmentFile instance by example");
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
		log.debug("finding AttachmentFile instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AttachmentFile as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFilePath(Object filePath) {
		return findByProperty(FILE_PATH, filePath);
	}

	public List findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
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
		log.debug("finding all AttachmentFile instances");
		try {
			String queryString = "from AttachmentFile";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AttachmentFile merge(AttachmentFile detachedInstance) {
		log.debug("merging AttachmentFile instance");
		try {
			AttachmentFile result = (AttachmentFile) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AttachmentFile instance) {
		log.debug("attaching dirty AttachmentFile instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AttachmentFile instance) {
		log.debug("attaching clean AttachmentFile instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static AttachmentFileDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (AttachmentFileDAO) ctx.getBean("AttachmentFileDAO");
	}
}