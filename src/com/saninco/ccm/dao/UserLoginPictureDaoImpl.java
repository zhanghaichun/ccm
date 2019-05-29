package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.UserLoginPicture;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserLoginPicture entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.UserLoginPicture
 * @author MyEclipse Persistence Tools
 */

public class UserLoginPictureDaoImpl extends HibernateDaoSupport implements
		IUserLoginPictureDao {
	private static final Log log = LogFactory
			.getLog(UserLoginPictureDaoImpl.class);
	// property constants
	public static final String FILE_PATH = "filePath";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#save(com.saninco.ccm.model.UserLoginPicture)
	 */
	public void save(UserLoginPicture transientInstance) {
		log.debug("saving UserLoginPicture instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#delete(com.saninco.ccm.model.UserLoginPicture)
	 */
	public void delete(Integer id) {
		log.debug("deleting UserLoginPicture instance");
		try {
			getHibernateTemplate().delete(
					getHibernateTemplate().load(UserLoginPicture.class, id));
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#delete(com.saninco.ccm.model.UserLoginPicture)
	 */
	public void delete(UserLoginPicture persistentInstance) {
		log.debug("deleting UserLoginPicture instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findById(java.lang.Integer)
	 */
	public UserLoginPicture findById(java.lang.Integer id) {
		log.debug("getting UserLoginPicture instance with id: " + id);
		try {
			UserLoginPicture instance = (UserLoginPicture) getHibernateTemplate()
					.get("com.saninco.ccm.model.UserLoginPicture", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findByExample(com.saninco.ccm.model.UserLoginPicture)
	 */
	public List findByExample(UserLoginPicture instance) {
		log.debug("finding UserLoginPicture instance by example");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserLoginPicture instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from UserLoginPicture as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findByFilePath(java.lang.Object)
	 */
	public List findByFilePath(Object filePath) {
		return findByProperty(FILE_PATH, filePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findByCreatedBy(java.lang.Object)
	 */
	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findByModifiedBy(java.lang.Object)
	 */
	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findByRecActiveFlag(java.lang.Object)
	 */
	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all UserLoginPicture instances");
		try {
			String queryString = "from UserLoginPicture";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#merge(com.saninco.ccm.model.UserLoginPicture)
	 */
	public UserLoginPicture merge(UserLoginPicture detachedInstance) {
		log.debug("merging UserLoginPicture instance");
		try {
			UserLoginPicture result = (UserLoginPicture) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#attachDirty(com.saninco.ccm.model.UserLoginPicture)
	 */
	public void attachDirty(UserLoginPicture instance) {
		log.debug("attaching dirty UserLoginPicture instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserLoginPictureDao#attachClean(com.saninco.ccm.model.UserLoginPicture)
	 */
	public void attachClean(UserLoginPicture instance) {
		log.debug("attaching clean UserLoginPicture instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IUserLoginPictureDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (IUserLoginPictureDao) ctx.getBean("UserLoginPictureDAO");
	}

	public Long findByCountProperty(String propertyName, Object value) {
		log.debug("finding UserLoginPicture count with property: "
				+ propertyName + ", value: " + value);
		Session session = getSession();
		try {
			String queryString = "select count(model) from UserLoginPicture as model where model."
					+ propertyName + "=?";
			Query query = session.createQuery(queryString);
			query.setParameter(0, value);
			Number bi = (Number) query.uniqueResult();
			return bi.longValue();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * Get User Login Picture Id List By UserId [Chao.Liu]
	 * 
	 * @param id
	 * @return
	 */
	public List<String> getPicIdByUid(Integer uid) {
		logger.info("Enter method getPicIdByUid.");

		final String queryString = this.getPicIdByUidSqlString(uid);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});

		logger.info("Exit method getPicIdByUid.");
		return list;
	}

	private String getPicIdByUidSqlString(Integer uid) {
		logger.info("Enter method getPicIdByUidSqlString.");

		StringBuffer sb = new StringBuffer(
				"select CONCAT(ulp.id) from user_login_picture ulp ");
		sb.append("where ulp.user_id = " + uid);

		logger.info("Exit method getPicIdByUidSqlString.");
		return sb.toString();
	}
}