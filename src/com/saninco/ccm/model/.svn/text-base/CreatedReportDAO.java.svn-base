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
 * CreatedReport entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.CreatedReport
 * @author MyEclipse Persistence Tools
 */

public class CreatedReportDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CreatedReportDAO.class);
	// property constants
	public static final String CREATED_REPORT_NAME = "createdReportName";
	public static final String FILE_PATH = "filePath";
	public static final String FILE_NAME = "fileName";
	public static final String SHARING_FLAG = "sharingFlag";
	public static final String SEND_EMAIL = "sendEmail";
	public static final String PARAMETER_STRING = "parameterString";
	public static final String REPORT_FORMAT = "reportFormat";
	public static final String REPORT_STATUS = "reportStatus";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(CreatedReport transientInstance) {
		log.debug("saving CreatedReport instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CreatedReport persistentInstance) {
		log.debug("deleting CreatedReport instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CreatedReport findById(java.lang.Integer id) {
		log.debug("getting CreatedReport instance with id: " + id);
		try {
			CreatedReport instance = (CreatedReport) getHibernateTemplate()
					.get("com.saninco.ccm.model.CreatedReport", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CreatedReport instance) {
		log.debug("finding CreatedReport instance by example");
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
		log.debug("finding CreatedReport instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CreatedReport as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCreatedReportName(Object createdReportName) {
		return findByProperty(CREATED_REPORT_NAME, createdReportName);
	}

	public List findByFilePath(Object filePath) {
		return findByProperty(FILE_PATH, filePath);
	}

	public List findByFileName(Object fileName) {
		return findByProperty(FILE_NAME, fileName);
	}

	public List findBySharingFlag(Object sharingFlag) {
		return findByProperty(SHARING_FLAG, sharingFlag);
	}

	public List findBySendEmail(Object sendEmail) {
		return findByProperty(SEND_EMAIL, sendEmail);
	}

	public List findByParameterString(Object parameterString) {
		return findByProperty(PARAMETER_STRING, parameterString);
	}

	public List findByReportFormat(Object reportFormat) {
		return findByProperty(REPORT_FORMAT, reportFormat);
	}

	public List findByReportStatus(Object reportStatus) {
		return findByProperty(REPORT_STATUS, reportStatus);
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
		log.debug("finding all CreatedReport instances");
		try {
			String queryString = "from CreatedReport";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CreatedReport merge(CreatedReport detachedInstance) {
		log.debug("merging CreatedReport instance");
		try {
			CreatedReport result = (CreatedReport) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CreatedReport instance) {
		log.debug("attaching dirty CreatedReport instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CreatedReport instance) {
		log.debug("attaching clean CreatedReport instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CreatedReportDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (CreatedReportDAO) ctx.getBean("CreatedReportDAO");
	}
}