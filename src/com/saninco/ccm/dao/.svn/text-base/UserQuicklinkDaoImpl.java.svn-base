package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.UserQuicklink;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.SystemUtil;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserQuicklink entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.UserQuicklink
 * @author MyEclipse Persistence Tools
 */

public class UserQuicklinkDaoImpl extends HibernateDaoSupport implements IUserQuicklinkDao {
	private static final Log log = LogFactory.getLog(UserQuicklinkDaoImpl.class);
	// property constants
	public static final String QUICKLINK_NAME = "quicklinkName";
	public static final String REQUEST_STRING = "requestString";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#save(com.saninco.ccm.model.UserQuicklink)
	 */
	public void save(UserQuicklink transientInstance) {
		log.debug("saving UserQuicklink instance");
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
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#delete(com.saninco.ccm.model.UserQuicklink)
	 */
	public void delete(UserQuicklink persistentInstance) {
		log.debug("deleting UserQuicklink instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	/**
	 * Add By Chao.Liu
	 */
	public void delete(){
		Session session = getSession();
		try {
			String sql = "delete uq from `user_quicklink` uq where uq.rec_active_flag = 'N' and uq.quicklink_type = 'mark_delete' ";
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findById(java.lang.Integer)
	 */
	public UserQuicklink findById(java.lang.Integer id) {
		log.debug("getting UserQuicklink instance with id: " + id);
		try {
			UserQuicklink instance = (UserQuicklink) getHibernateTemplate().get("com.saninco.ccm.model.UserQuicklink", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findByExample(com.saninco.ccm.model.UserQuicklink)
	 */
	public List findByExample(UserQuicklink instance) {
		log.debug("finding UserQuicklink instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findByProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserQuicklink instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from UserQuicklink as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findByQuicklinkName(java.lang.Object)
	 */
	public List findByQuicklinkName(Object quicklinkName, String quicklinkType) {
		String queryString = "from UserQuicklink as model where model.quicklinkName=? and model.quicklinkType=? and model.user.id = "+SystemUtil.getCurrentUserId()+" ";
		return getHibernateTemplate().find(queryString,new Object[]{quicklinkName,quicklinkType});
	}
	/**
	 * 
	 * @author Chao.Liu Date:Aug 29, 2010
	 * @param quicklinkName
	 * @return
	 */
	public UserQuicklink findByQuicklinkName(Object quicklinkName) {
		String queryString = "from UserQuicklink as model where model.quicklinkName=? and model.recActiveFlag = 'N' ";
		List l =  (List) getHibernateTemplate().find(queryString,new Object[]{quicklinkName});
		if(l != null && l.size() > 0){
			return (UserQuicklink) l.get(0);
		}
		return null;
	}
	/**
	 * 
	 * @author Chao.Liu Date:Aug 29, 2010
	 * @return
	 */
	public UserQuicklink find(){
		String queryString = "from UserQuicklink as model where model.recActiveFlag = 'N' ";
		List l =  (List) getHibernateTemplate().find(queryString);
		if(l != null && l.size() > 0){
			return (UserQuicklink) l.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findByRequestString(java.lang.Object)
	 */
	public List findByRequestString(Object requestString) {
		return findByProperty(REQUEST_STRING, requestString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findByCreatedBy(java.lang.Object)
	 */
	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findByModifiedBy(java.lang.Object)
	 */
	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findByRecActiveFlag(java.lang.Object)
	 */
	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#findAll()
	 */
	public List findAll() {
		log.debug("finding all UserQuicklink instances");
		try {
			String queryString = "from UserQuicklink";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#merge(com.saninco.ccm.model.UserQuicklink)
	 */
	public UserQuicklink merge(UserQuicklink detachedInstance) {
		log.debug("merging UserQuicklink instance");
		try {
			UserQuicklink result = (UserQuicklink) getHibernateTemplate().merge(detachedInstance);
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
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#attachDirty(com.saninco.ccm.model.UserQuicklink)
	 */
	public void attachDirty(UserQuicklink instance) {
		log.debug("attaching dirty UserQuicklink instance");
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
	 * @see com.saninco.ccm.dao.IUserQuicklinkDao#attachClean(com.saninco.ccm.model.UserQuicklink)
	 */
	public void attachClean(UserQuicklink instance) {
		log.debug("attaching clean UserQuicklink instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IUserQuicklinkDao getFromApplicationContext(ApplicationContext ctx) {
		return (IUserQuicklinkDao) ctx.getBean("UserQuicklinkDAO");
	}

	public List findQuicklinks(String quicklinkType, Integer userId) {
		log.debug("finding UserQuicklink instance with quicklinkType: " + quicklinkType + ", userId" + userId);
		try {
			final String sql = "select * from user_quicklink u where upper(u.quicklink_type)='" + quicklinkType.toUpperCase()
					+ "' and u.user_id=" + userId;
			HibernateTemplate template = this.getHibernateTemplate();
			List l = (List) template.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(sql).addEntity(UserQuicklink.class).list();
				}
			});
			return l;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	/**
	 * @author Chao.Liu
	 */
	public List<Object[]> findQuicklinks(Integer userId) {
		log.debug("finding UserQuicklink instance with userId" + userId);
		Session session = getSession();
		try {
			final String sql = "select u.id,u.quicklink_name from user_quicklink u where u.user_id=" + userId + " and u.rec_active_flag != 'N' order by u.quicklink_name asc ";
			List l = session.createSQLQuery(sql).list();
			return l;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author Chao.Liu
	 */
	public String getPageName(int quicklinkId){
		try {
			final String sql = "select u.quicklink_type from user_quicklink u where u.id=" + quicklinkId;
			HibernateTemplate template = this.getHibernateTemplate();
			String s = (String) template.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(sql).list().get(0);
				}
			});
			return s;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}