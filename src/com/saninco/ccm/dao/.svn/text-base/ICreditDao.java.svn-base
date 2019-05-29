/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Credit;
import com.saninco.ccm.vo.SearchCreditVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * Spring Hibernate DAO interface for Credit based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author xinyu.Liu
 * add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 * add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 */
@SuppressWarnings("unchecked")
public interface ICreditDao {
	
	public List<Object[]> searchCreditByObject(SearchCreditVO searchCreditVO, int userId);
	
	public List<String> searchCredit(SearchCreditVO searchCreditVO, int userId);
	
	public long getNumberOfCredit(SearchCreditVO searchCreditVO, int userId);

	public long getAssignmentCount(int currentUserId);
	
	public List<String> findPendingCredits(SearchCreditVO searchCreditVO,int userId);
	
	public long getPendingNumberOfCredit(SearchCreditVO searchCreditVO, int userId);

	public List<String> searchCreditAssignment(SearchCreditVO searchCreditVO);
	
	public void updateCreditBalanceRollback(Credit credit);

	public abstract void save(Credit transientInstance);

	public abstract void delete(Credit persistentInstance);

	public abstract Credit findById(java.lang.Integer id);

	public abstract List findByExample(Credit instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByCreditAmount(Object creditAmount);

	public abstract List findByCreditBalance(Object creditBalance);

	public abstract List findByReferenceNumber(Object referenceNumber);

	public abstract List findByDisputeNumber(Object disputeNumber);

	public abstract List findByClaimNumber(Object claimNumber);

	public abstract List findByTicketNumber(Object ticketNumber);

	public abstract List findByNotes(Object notes);

	public abstract List findByFlagWorkspace(Object flagWorkspace);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();

	public abstract Credit merge(Credit detachedInstance);

	public abstract void attachDirty(Credit instance);
	public Integer getCreditTabTotalNO(SearchInvoiceVO svo);
	public List<String> searchICreditTab(SearchInvoiceVO svo);
	public Double getCreidtSumByIid(SearchInvoiceVO svo);
	public List getCreditSCOAId(SearchInvoiceVO svo);
	public String getCreditSCOAByCid(SearchInvoiceVO svo);
	public List<Integer> getCreditId(SearchInvoiceVO svo);
	/**
	 * DownLoad Excel [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List<Object[]> searchCreditTabByObject(SearchInvoiceVO svo);
	public void saveObject(Object o);
	public Object getObject(Class c, Integer id);
	public void updateObject(Object o);
	public abstract void attachClean(Credit instance);

}
