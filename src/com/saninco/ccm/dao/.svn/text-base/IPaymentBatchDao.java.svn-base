/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;
import com.saninco.ccm.model.PaymentBatch;


/**
 * @author Qiao
 *
 */
public interface IPaymentBatchDao {
	public void save(PaymentBatch transientInstance);

	public abstract void delete(PaymentBatch persistentInstance);

	public PaymentBatch findById(java.lang.Integer id);

	public abstract List findByExample(PaymentBatch instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByFileName(Object fileName);
	
	public abstract List findByBatchStatus(Object batchStatus);
	
	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);
	
	public abstract List findAll();

	public abstract PaymentBatch merge(PaymentBatch detachedInstance);

	public abstract void attachDirty(PaymentBatch instance);

	public abstract void attachClean(PaymentBatch instance);
	
	public void update(PaymentBatch persistentInstance);

}
