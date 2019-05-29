/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.PaymentDetail;

/**
 * @author Qiao
 *
 */
public interface IPaymentDetailDao {

	public void save(PaymentDetail transientInstance);

	public abstract void delete(PaymentDetail persistentInstance);

	public PaymentDetail findById(java.lang.Integer id);

	public abstract List findByExample(PaymentDetail instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByAmount(Object amount);

	public abstract List findByLineTypeLookupCode(Object lineTypeLookupCode);
	
	public abstract List findByLineNumber(Object lineNumber);

	public abstract List findByDescription(Object description);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();

	public abstract PaymentDetail merge(PaymentDetail detachedInstance);

	public abstract void attachDirty(PaymentDetail instance);

	public abstract void attachClean(PaymentDetail instance);

	public void update(PaymentDetail persistentInstance);

	public List<PaymentDetail> findByPaymentIdAndLine(String string,
			String lineOfBusiness);
}
