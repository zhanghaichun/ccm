/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Remittance;

/**
 * @author Qiao
 *
 */
public interface IRemittance {

	public void save(Remittance transientInstance);

	public abstract void delete(Remittance persistentInstance);
	
	public void update(Remittance persistentInstance);

	public Remittance findById(java.lang.Integer id);

	public abstract List findByExample(Remittance instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByInvoiceNumber(Object invoiceNumber);

	public abstract List findByApSupplierNumber(Object apSupplierNumber);
	
	public abstract List findByPaymentAmount(Object paymentAmount);

	public abstract List findByRemittanceStatus(Object remittanceStatus);

	public abstract List findByProcessDescription(Object processDescription);

	public abstract List findByPaymentReferenceCode(Object paymentReferenceCode);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);
	
	public abstract List findAll();

	public abstract Remittance merge(Remittance detachedInstance);

	public void attachDirty(Remittance instance);

	public void attachClean(Remittance instance);
	
}
