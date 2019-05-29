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

import com.saninco.ccm.model.Originator;
import com.saninco.ccm.model.OriginatorDAO;
import com.saninco.ccm.vo.SearchInvoiceVO;

public class OriginatorDaoImpl extends HibernateDaoSupport implements
		IOriginatorDao {
	
	private static final Log log = LogFactory.getLog(OriginatorDAO.class);
	// property constants
	public static final String ORIGINATOR_NAME = "originatorName";

	public void save(Originator transientInstance) {
		log.debug("saving Originator instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Originator persistentInstance) {
		log.debug("deleting Originator instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Originator findById(java.lang.Integer id) {
		log.debug("getting Originator instance with id: " + id);
		try {
			Originator instance = (Originator) getHibernateTemplate().get(
					"com.saninco.ccm.model.Originator", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Originator instance) {
		log.debug("finding Originator instance by example");
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
		log.debug("finding Originator instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Originator as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOriginatorName(Object originatorName) {
		return findByProperty(ORIGINATOR_NAME, originatorName);
	}

	@SuppressWarnings("unchecked")
	public List findAll() {
		log.debug("finding all Originator instances");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select o.id,o.originator_name from originator o where o.rec_active_flag = 'Y' order by id ").list();
			return l;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public Originator merge(Originator detachedInstance) {
		log.debug("merging Originator instance");
		try {
			Originator result = (Originator) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Originator instance) {
		log.debug("attaching dirty Originator instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Originator instance) {
		log.debug("attaching clean Originator instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static OriginatorDAO getFromApplicationContext(ApplicationContext ctx) {
		return (OriginatorDAO) ctx.getBean("OriginatorDAO");
	}
	
	/* (non-Javadoc)
	 * @wei.su
	 * @see com.saninco.ccm.dao.IInter#findByRecActiveFlag(java.lang.Object)
	 */
	public List<String>getOriginatorAndReturnStringList(SearchInvoiceVO searchInvoiceVO) {
		logger.debug("finding all originator getOriginatorAndReturnStringList");
		try {
			final StringBuffer sb = new StringBuffer("select concat('{id:',id,',");
			sb.append("originator_name:\"',if(originator_name is null,'',originator_name),'\"");
			sb.append("}') a ");
			sb.append("from originator where rec_active_flag = 'Y' order by id ");
			HibernateTemplate template = this.getHibernateTemplate();
			List<String> l = (List<String>)template.execute(new HibernateCallback() {
			    public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	return session.createSQLQuery(sb.toString()).list();
			    }
			});
			logger.info("Exit method originatorDao.");
			return l;
		} catch (RuntimeException re) {
			logger.error("getOriginatorAndReturnStringList", re);
			throw re;
		}
	}
	
}
