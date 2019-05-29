package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * InvoiceItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.InvoiceItem
 * @author MyEclipse Persistence Tools
 */

public class InvoiceItemDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(InvoiceItemDAO.class);
	// property constants
	public static final String PAYMENT_SUM = "paymentSum";
	public static final String DISPUTE_SUM = "disputeSum";
	public static final String PARENT_ITEM_ID = "parentItemId";
	public static final String ITEM_NAME = "itemName";
	public static final String RATE = "rate";
	public static final String ORIGINAL_AMOUNT = "originalAmount";
	public static final String DISCOUNT = "discount";
	public static final String QUANTITY = "quantity";
	public static final String ITEM_AMOUNT = "itemAmount";
	public static final String ITEM_KEY = "itemKey";
	public static final String CIRCUIT_NUMBER = "circuitNumber";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String PROPOSAL_FLAG = "proposalFlag";
	public static final String MRC_AMOUNT = "mrcAmount";
	public static final String ONETIME_AMOUNT = "onetimeAmount";
	public static final String MINUTES = "minutes";
	public static final String SECONDS = "seconds";
	public static final String STRIPPED_CIRCUIT_NUMBER = "strippedCircuitNumber";
	public static final String INTERNAL_CIRCUIT_NUMBER = "internalCircuitNumber";
	public static final String SERVICE_DESCRIPTION = "serviceDescription";
	public static final String DESCRIPTION = "description";
	public static final String ADDRESS = "address";
	public static final String USOC = "usoc";
	public static final String UNIT_OF_MEASURE = "unitOfMeasure";
	public static final String SERVICE_NUMBER = "serviceNumber";
	public static final String LINE_ITEM_CODE = "lineItemCode";
	public static final String LINE_ITEM_CODE_DESCRIPTION = "lineItemCodeDescription";
	public static final String SERVICE_TYPE = "serviceType";
	public static final String USAGE_TYPE = "usageType";
	public static final String USAGE_TYPE_CODE = "usageTypeCode";
	public static final String CIRCUIT_CATEGORY = "circuitCategory";
	public static final String SERVICE_ORDER_NUMBER = "serviceOrderNumber";
	public static final String PURCHASE_ORDER_NUMBER = "purchaseOrderNumber";
	public static final String RECURRING_FLAG = "recurringFlag";
	public static final String COUNTRY = "country";
	public static final String PROVINCE = "province";
	public static final String DIRECTION = "direction";

	protected void initDao() {
		// do nothing
	}

	public void save(InvoiceItem transientInstance) {
		log.debug("saving InvoiceItem instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceItem persistentInstance) {
		log.debug("deleting InvoiceItem instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceItem findById(java.lang.Long id) {
		log.debug("getting InvoiceItem instance with id: " + id);
		try {
			InvoiceItem instance = (InvoiceItem) getHibernateTemplate().get(
					"com.saninco.ccm.model.InvoiceItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceItem instance) {
		log.debug("finding InvoiceItem instance by example");
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
		log.debug("finding InvoiceItem instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from InvoiceItem as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPaymentSum(Object paymentSum) {
		return findByProperty(PAYMENT_SUM, paymentSum);
	}

	public List findByDisputeSum(Object disputeSum) {
		return findByProperty(DISPUTE_SUM, disputeSum);
	}

	public List findByParentItemId(Object parentItemId) {
		return findByProperty(PARENT_ITEM_ID, parentItemId);
	}

	public List findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List findByRate(Object rate) {
		return findByProperty(RATE, rate);
	}

	public List findByOriginalAmount(Object originalAmount) {
		return findByProperty(ORIGINAL_AMOUNT, originalAmount);
	}

	public List findByDiscount(Object discount) {
		return findByProperty(DISCOUNT, discount);
	}

	public List findByQuantity(Object quantity) {
		return findByProperty(QUANTITY, quantity);
	}

	public List findByItemAmount(Object itemAmount) {
		return findByProperty(ITEM_AMOUNT, itemAmount);
	}

	public List findByItemKey(Object itemKey) {
		return findByProperty(ITEM_KEY, itemKey);
	}

	public List findByCircuitNumber(Object circuitNumber) {
		return findByProperty(CIRCUIT_NUMBER, circuitNumber);
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

	public List findByProposalFlag(Object proposalFlag) {
		return findByProperty(PROPOSAL_FLAG, proposalFlag);
	}

	public List findByMrcAmount(Object mrcAmount) {
		return findByProperty(MRC_AMOUNT, mrcAmount);
	}

	public List findByOnetimeAmount(Object onetimeAmount) {
		return findByProperty(ONETIME_AMOUNT, onetimeAmount);
	}

	public List findByMinutes(Object minutes) {
		return findByProperty(MINUTES, minutes);
	}

	public List findBySeconds(Object seconds) {
		return findByProperty(SECONDS, seconds);
	}

	public List findByStrippedCircuitNumber(Object strippedCircuitNumber) {
		return findByProperty(STRIPPED_CIRCUIT_NUMBER, strippedCircuitNumber);
	}

	public List findByInternalCircuitNumber(Object internalCircuitNumber) {
		return findByProperty(INTERNAL_CIRCUIT_NUMBER, internalCircuitNumber);
	}

	public List findByServiceDescription(Object serviceDescription) {
		return findByProperty(SERVICE_DESCRIPTION, serviceDescription);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByUsoc(Object usoc) {
		return findByProperty(USOC, usoc);
	}

	public List findByUnitOfMeasure(Object unitOfMeasure) {
		return findByProperty(UNIT_OF_MEASURE, unitOfMeasure);
	}

	public List findByServiceNumber(Object serviceNumber) {
		return findByProperty(SERVICE_NUMBER, serviceNumber);
	}

	public List findByLineItemCode(Object lineItemCode) {
		return findByProperty(LINE_ITEM_CODE, lineItemCode);
	}

	public List findByLineItemCodeDescription(Object lineItemCodeDescription) {
		return findByProperty(LINE_ITEM_CODE_DESCRIPTION,
				lineItemCodeDescription);
	}

	public List findByServiceType(Object serviceType) {
		return findByProperty(SERVICE_TYPE, serviceType);
	}

	public List findByUsageType(Object usageType) {
		return findByProperty(USAGE_TYPE, usageType);
	}

	public List findByUsageTypeCode(Object usageTypeCode) {
		return findByProperty(USAGE_TYPE_CODE, usageTypeCode);
	}

	public List findByCircuitCategory(Object circuitCategory) {
		return findByProperty(CIRCUIT_CATEGORY, circuitCategory);
	}

	public List findByServiceOrderNumber(Object serviceOrderNumber) {
		return findByProperty(SERVICE_ORDER_NUMBER, serviceOrderNumber);
	}

	public List findByPurchaseOrderNumber(Object purchaseOrderNumber) {
		return findByProperty(PURCHASE_ORDER_NUMBER, purchaseOrderNumber);
	}

	public List findByRecurringFlag(Object recurringFlag) {
		return findByProperty(RECURRING_FLAG, recurringFlag);
	}

	public List findByCountry(Object country) {
		return findByProperty(COUNTRY, country);
	}

	public List findByProvince(Object province) {
		return findByProperty(PROVINCE, province);
	}

	public List findByDirection(Object direction) {
		return findByProperty(DIRECTION, direction);
	}

	public List findAll() {
		log.debug("finding all InvoiceItem instances");
		try {
			String queryString = "from InvoiceItem";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceItem merge(InvoiceItem detachedInstance) {
		log.debug("merging InvoiceItem instance");
		try {
			InvoiceItem result = (InvoiceItem) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceItem instance) {
		log.debug("attaching dirty InvoiceItem instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceItem instance) {
		log.debug("attaching clean InvoiceItem instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InvoiceItemDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (InvoiceItemDAO) ctx.getBean("InvoiceItemDAO");
	}
}