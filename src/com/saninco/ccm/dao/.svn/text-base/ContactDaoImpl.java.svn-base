package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Contact;
import com.saninco.ccm.model.ContactDAO;

public class ContactDaoImpl extends HibernateDaoSupport implements IContactDao {
	private static final Log log = LogFactory.getLog(ContactDAO.class);
	// property constants
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ATTENTION_NAME = "attentionName";
	public static final String CO_NAME = "coName";
	public static final String ADDRESS1 = "address1";
	public static final String ADDRESS2 = "address2";
	public static final String CITY = "city";
	public static final String PROVINCE = "province";
	public static final String COUNTRY = "country";
	public static final String POSTAL_CODE = "postalCode";
	public static final String PRIMARY_PHONE = "primaryPhone";
	public static final String OTHER_PHONE = "otherPhone";
	public static final String OFFICE_PHONE = "officePhone";
	public static final String CELL_PHONE = "cellPhone";
	public static final String FAX_NUMBER = "faxNumber";
	public static final String EMAIL = "email";
	public static final String LOCATION_CODE = "locationCode";
	public static final String SUPERVISOR_ID = "supervisorId";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(Contact transientInstance) {
		log.debug("saving Contact instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Contact persistentInstance) {
		log.debug("deleting Contact instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Contact findById(java.lang.Integer id) {
		log.debug("getting Contact instance with id: " + id);
		try {
			Contact instance = (Contact) getHibernateTemplate().get(
					"com.saninco.ccm.model.Contact", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Contact instance) {
		log.debug("finding Contact instance by example");
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
		log.debug("finding Contact instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Contact as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFirstName(Object firstName) {
		return findByProperty(FIRST_NAME, firstName);
	}

	public List findByLastName(Object lastName) {
		return findByProperty(LAST_NAME, lastName);
	}

	public List findByAttentionName(Object attentionName) {
		return findByProperty(ATTENTION_NAME, attentionName);
	}

	public List findByCoName(Object coName) {
		return findByProperty(CO_NAME, coName);
	}

	public List findByAddress1(Object address1) {
		return findByProperty(ADDRESS1, address1);
	}

	public List findByAddress2(Object address2) {
		return findByProperty(ADDRESS2, address2);
	}

	public List findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List findByProvince(Object province) {
		return findByProperty(PROVINCE, province);
	}

	public List findByCountry(Object country) {
		return findByProperty(COUNTRY, country);
	}

	public List findByPostalCode(Object postalCode) {
		return findByProperty(POSTAL_CODE, postalCode);
	}

	public List findByPrimaryPhone(Object primaryPhone) {
		return findByProperty(PRIMARY_PHONE, primaryPhone);
	}

	public List findByOtherPhone(Object otherPhone) {
		return findByProperty(OTHER_PHONE, otherPhone);
	}

	public List findByOfficePhone(Object officePhone) {
		return findByProperty(OFFICE_PHONE, officePhone);
	}

	public List findByCellPhone(Object cellPhone) {
		return findByProperty(CELL_PHONE, cellPhone);
	}

	public List findByFaxNumber(Object faxNumber) {
		return findByProperty(FAX_NUMBER, faxNumber);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByLocationCode(Object locationCode) {
		return findByProperty(LOCATION_CODE, locationCode);
	}

	public List findBySupervisorId(Object supervisorId) {
		return findByProperty(SUPERVISOR_ID, supervisorId);
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
		log.debug("finding all Contact instances");
		try {
			String queryString = "from Contact";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Contact merge(Contact detachedInstance) {
		log.debug("merging Contact instance");
		try {
			Contact result = (Contact) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Contact instance) {
		log.debug("attaching dirty Contact instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Contact instance) {
		log.debug("attaching clean Contact instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ContactDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ContactDAO) ctx.getBean("ContactDAO");
	}
}
