package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIProduct;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIProduct entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIProduct
 * @author MyEclipse Persistence Tools
 */
public class BIProductDaoImpl extends HibernateDaoSupport implements IBIProductDao{
	private static final Log log = LogFactory.getLog(BIProductDaoImpl.class);
	// property constants
	public static final String PRODUCT_NAME = "productName";
	public static final String MEDIA_LABEL = "mediaLabel";
	public static final String FUNCTION_LABEL = "functionLabel";
	public static final String GENERATION_LABEL = "generationLabel";
	
	public List<BIProduct> findByLabel(String productLabel) {
		log.debug("findByLabel instance with label: " + productLabel);
		try {
			String queryString = "from BIProduct as model where model.mediaLabel= ? or model.functionLabel= ? or model.generationLabel= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, productLabel);
			queryObject.setParameter(1, productLabel);
			queryObject.setParameter(2, productLabel);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findByLabel failed", re);
			throw re;
		}
	}
	
	public void save(BIProduct transientInstance) {
		log.debug("saving BIProduct instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIProduct persistentInstance) {
		log.debug("deleting BIProduct instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIProduct findById(java.lang.Integer id) {
		log.debug("getting BIProduct instance with id: " + id);
		try {
			BIProduct instance = (BIProduct) getSession().get(
					"com.saninco.ccm.model.bi.BIProduct", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIProduct instance) {
		log.debug("finding BIProduct instance by example");
		try {
			List results = getSession()
					.createCriteria("com.saninco.ccm.model.bi.BIProduct")
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
		log.debug("finding BIProduct instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BIProduct as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProductName(Object productName) {
		return findByProperty(PRODUCT_NAME, productName);
	}

	public List findByMediaLabel(Object mediaLabel) {
		return findByProperty(MEDIA_LABEL, mediaLabel);
	}

	public List findByFunctionLabel(Object functionLabel) {
		return findByProperty(FUNCTION_LABEL, functionLabel);
	}

	public List findByGenerationLabel(Object generationLabel) {
		return findByProperty(GENERATION_LABEL, generationLabel);
	}

	public List findAll() {
		log.debug("finding all BIProduct instances");
		try {
			String queryString = "from BIProduct";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BIProduct merge(BIProduct detachedInstance) {
		log.debug("merging BIProduct instance");
		try {
			BIProduct result = (BIProduct) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIProduct instance) {
		log.debug("attaching dirty BIProduct instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIProduct instance) {
		log.debug("attaching clean BIProduct instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}