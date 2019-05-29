package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchReconcileVO;
/**
 * Spring Hibernate DAO interface for Reconcile and Dispute based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author Wei.Su
 *
 */
@SuppressWarnings("unchecked")
public interface IReconcileDao {

    public List<String> searchReconcile(SearchReconcileVO searchReconcileVO, int userId);
	
	public long getNumberOfReconcile(SearchReconcileVO searchReconcileVO, int userId);

	public long getAssignmentCount(String banStr);
	
	public abstract void save(Reconcile transientInstance);

	public abstract void delete(Reconcile persistentInstance);

	public abstract Reconcile findById(java.lang.Integer id);

	public abstract List findByExample(Reconcile instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByReconcileAmount(Object reconcileAmount);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();
	
	/**
	 * Get Invoice Page Reconciliation Tab Info Totail Number [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public Integer getIReconciliationTabTotalNO(SearchInvoiceVO svo);
	/**
	 * Get Invoice Page Reconciliation Tab Info List [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List<String> searchIReconciliationTab(SearchInvoiceVO svo);
	public List searchIReconciliationTabObject(SearchInvoiceVO svo);
	
	public abstract Reconcile merge(Reconcile detachedInstance);

	public abstract void attachDirty(Reconcile instance);

	public abstract void attachClean(Reconcile instance);

}
