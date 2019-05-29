package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIUserDashboard;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIUserDashboard entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIUserDashboard
 * @author MyEclipse Persistence Tools
 */
public class BIUserDashboardDaoImpl extends HibernateDaoSupport implements IBIUserDashboardDao {
	private static final Log log = LogFactory.getLog(BIUserDashboardDaoImpl.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String DASHBOARD_NAME = "dashboardName";
	public static final String DEFAULT_FLAG = "defaultFlag";

	/**
	 * 查询 Dashboard List 中的信息。
	 */
	public List<Object[]> searchDashboardListInfo( Integer userId, String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords ) {

		if (currentPage == null || currentPage != 1) {
			currentPage = 1;
		}

		if (pageRecords == null || pageRecords != 5) {
			pageRecords = 5;
		}

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ud.id, ");
		sb.append("IF(ud.dashboard_name IS NULL, null, ud.dashboard_name) AS dashboard_name, ");
		sb.append("IF(ud.created_timestamp IS NULL, null, ud.created_timestamp) AS created_timestamp");
		
		sb.append( this.fromAndWhereConditions(userId) );
		
		sb.append( this.limitCauseAndSortCause(dashboardNameSort, dateCreatedSort, currentPage, pageRecords) );
		
		
		// 这里必须使用final 关键字进行修饰。
		final String queryString = sb.toString();

		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> dashboardListInfo = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});

		return dashboardListInfo;

	}
	
	/**
	 * 查询 dashboard list 时的from 和 where 条件的限制。
	 * @param userId
	 * @return
	 */
	private Object fromAndWhereConditions(Integer userId) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM bi_db.user_dashboard AS ud ");

		sb.append(" WHERE ud.user_id = " + userId + " AND ud.rec_active_flag = 'Y' ");
		
		return sb.toString();
	}

	/**
	 * Dashboard list 列表中的分页和排序条件的查询。
	 * @param dashboardNameSort
	 * @param dateCreatedSort
	 * @param currentPage
	 * @param pageRecords
	 * @return
	 */
	private String limitCauseAndSortCause(String dashboardNameSort,String dateCreatedSort, Integer currentPage, Integer pageRecords) {
		StringBuffer sb = new StringBuffer();
		
		
		if ( dashboardNameSort != null && !dashboardNameSort.equals("")) { // 按照 dashboard name 排序。
			
			sb.append(" ORDER BY ud.dashboard_name "+ dashboardNameSort);
		} else if (dateCreatedSort != null && !dateCreatedSort.equals("")) { // 按照 date created 排序。
			
			sb.append(" ORDER BY ud.created_timestamp "+ dateCreatedSort);
		} else {
			sb.append(" ORDER BY ud.created_timestamp DESC");
		}
		
		if ( currentPage == 1 ) {
			sb.append(" LIMIT 0," + pageRecords);
		} else {
			sb.append(" LIMIT " + (currentPage - 1) * pageRecords + ", " + pageRecords);
		}
		
		return sb.toString();
	}

	/**
	 * 根据特殊的排序和分页的条件来查询 dashboard list item.
	 */
	public String searchDashboardListByConditions( Integer userId, String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords) {

		String listString = null;
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT CONCAT('{\"id\":' , toJSON(ud.id IS NULL, '', ud.id), ");
		sb.append(" ',\"dashboard_name\": \"', toJSON(ud.dashboard_name IS NULL, '', ud.dashboard_name), ");
		sb.append(" '\",\"created_timestamp\": \"', toJSON(ud.created_timestamp IS NULL, '', DATE_FORMAT(ud.created_timestamp,'%Y/%m/%d') ), ");
		
		sb.append(" '\"}') ");
		
		sb.append( this.fromAndWhereConditions(userId) );
		
		sb.append( this.limitCauseAndSortCause(dashboardNameSort, dateCreatedSort, currentPage, pageRecords) );
		
		final String queryString = sb.toString();

		HibernateTemplate template = this.getHibernateTemplate();
		listString = (String)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l =  session.createSQLQuery(queryString).list();
				return new String( l.toString() );
			}
		});

		return listString;
	}

	/**
	 * 删除 dashboard 列表项。
	 * @param  userDashboardId [description]
	 * @return                 [description]
	 */
	public String deleteDashboardListItem(Integer userDashboardId) {

		StringBuffer sb = new StringBuffer();
		sb.append("DELETE ");

		sb.append(" FROM bi_db.user_dashboard ");

		sb.append(" WHERE id = " + userDashboardId);

		// 这里必须使用final 关键字进行修饰。
		final String queryString = sb.toString();

		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});

		return null;
	}
	
	/**
	 * 获取 dashboard list 列表中项目的总条数。
	 */
	public Integer getTotalRecordsNo(Integer userId) {
		Integer count = null;
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT COUNT(*) ");

		sb.append(" FROM bi_db.user_dashboard AS ud ");

		sb.append(" WHERE ud.user_id = " + userId + " AND ud.rec_active_flag = 'Y' ");
		
		final String queryString = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		count = (Integer) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(queryString).list();
				return new Integer(l.get(0).toString());
			}
		});
		return count;
	}
	
	public List findByUser(Integer userId, String defaultFlag) {
		
		log.debug("finding BIUserDashboard instance with user: "
				+ userId + ", defaultFlag: " + defaultFlag);
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from BIUserDashboard as model where model.userId = ? ");
			if (defaultFlag != null)
				sb.append(" and model.defaultFlag = ?");
			Query queryObject = getSession().createQuery(sb.toString());
			queryObject.setParameter(0, userId);
			if (defaultFlag != null) 
				queryObject.setParameter(1, defaultFlag);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by user failed", re);
			throw re;
		}
	}
	
	public void save(BIUserDashboard transientInstance) {
		log.debug("saving BIUserDashboard instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIUserDashboard persistentInstance) {
		log.debug("deleting BIUserDashboard instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public void updateDefaultFlagById(Integer id, Integer userId) {
		
		logger.info("Enter method updateDefaultFlagById.");
		try {
			
			String queryString = "UPDATE bi_db.user_dashboard model SET default_flag = if(model.id = "+id+", 'Y', 'N') WHERE model.user_id = "+userId;
			getSession().createSQLQuery(queryString).executeUpdate();
			log.debug("updateDefaultFlagById successful");
		} catch (RuntimeException re) {
			log.error("updateDefaultFlagById failed", re);
			throw re;
		}
		logger.info("Exit method updateDefaultFlagById.");
	}
	
	public Integer countSameDashboardName(Integer id, String dashboardName, Integer userId) {
		logger.info("Enter method countSameDashboardName.");
		Integer result = null;
		
		try {
			String r = getSession().createSQLQuery("SELECT count(*) FROM bi_db.user_dashboard u WHERE u.dashboard_name = '"+dashboardName+"' AND u.user_id = "+userId+" AND u.id != "+id+" AND u.rec_active_flag = 'Y'").list().get(0).toString();
			result = Integer.valueOf(r);
			log.debug("countSameDashboardName successful");
		} catch (RuntimeException re) {
			log.error("countSameDashboardName failed", re);
			throw re;
		}
		logger.info("Exit method countSameDashboardName.");
		return result;
	}
	
	public Integer getNewDashboardNumber(Integer userId) {
		logger.info("Enter method getNewDashboardNumber.");
		Integer result = null;
		
		try {
			List list = getSession().createSQLQuery("SELECT substring_index(u.dashboard_name, ' ', -1) FROM bi_db.user_dashboard u WHERE u.user_id = "+userId+" AND u.rec_active_flag = 'Y' AND u.dashboard_name REGEXP '^New Dashboard [0-9]{0,}$' ORDER BY u.created_timestamp DESC LIMIT 1").list();
			if (list.size() > 0) {
				String r = list.get(0).toString();
				result = Integer.valueOf(r);
			}
			
			log.debug("getNewDashboardNumber successful");
		} catch (RuntimeException re) {
			log.error("getNewDashboardNumber failed", re);
			throw re;
		}
		logger.info("Exit method getNewDashboardNumber.");
		return result;
		
	}
	
	public BIUserDashboard findById(java.lang.Integer id) {
		log.debug("getting BIUserDashboard instance with id: " + id);
		try {
			BIUserDashboard instance = (BIUserDashboard) getSession().get(
					"com.saninco.ccm.model.bi.BIUserDashboard", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIUserDashboard instance) {
		log.debug("finding BIUserDashboard instance by example");
		try {
			List results = getSession()
					.createCriteria("com.saninco.ccm.model.bi.BIUserDashboard")
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
		log.debug("finding BIUserDashboard instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BIUserDashboard as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByDashboardName(Object dashboardName) {
		return findByProperty(DASHBOARD_NAME, dashboardName);
	}

	public List findByDefaultFlag(Object defaultFlag) {
		return findByProperty(DEFAULT_FLAG, defaultFlag);
	}

	public List findAll() {
		log.debug("finding all BIUserDashboard instances");
		try {
			String queryString = "from BIUserDashboard";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BIUserDashboard merge(BIUserDashboard detachedInstance) {
		log.debug("merging BIUserDashboard instance");
		try {
			BIUserDashboard result = (BIUserDashboard) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIUserDashboard instance) {
		log.debug("attaching dirty BIUserDashboard instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIUserDashboard instance) {
		log.debug("attaching clean BIUserDashboard instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}