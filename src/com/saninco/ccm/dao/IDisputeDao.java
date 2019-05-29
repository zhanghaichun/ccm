package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.DisputeStatus;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.WorkflowAction;
import com.saninco.ccm.vo.SearchDisputeVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * Spring Hibernate DAO interface for Dispute based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author xinyu.Liu
 * 
 */
@SuppressWarnings("unchecked")
public interface IDisputeDao{
	
	public void recalculateDisputeAmountAndDisputeBalance(String disputeId);

	public void updateDisputeBalanceByProposal(String disputeId);
	
	public void updateSCOACoingByProposal(SearchDisputeVO sdo ,Integer userId );
	
	public Reconcile findReconcileById(Integer id);
	
	public AccountCode findAccountCodeById(Integer id);
	
	public AttachmentPoint findAttachmentPointById(Integer id);
	
	public AttachmentFile findAttachmentFileById(Integer id);
	
	public long getNumberAccessories(String attachmentPointId);
	
	public void saveTransactionHistory(TransactionHistory transientInstance);
	
	public Dispute findDisputeById(Integer id);
	
	
	public void updateDisputeTimestamp(Integer id);
	
	public void updateDisputeTimestampByDisputeNumber (String disputeNumber);
	
	public WorkflowAction findWorkflowActionById(Integer id);
	
	public User findUserById(Integer id);
	
	public Invoice findInvoiceById(Integer id);
	
	public List<Object[]> searchTransactionByObject(SearchDisputeVO searchDisputeVO);
	
	public List<String> searchTransactionHistorys(SearchDisputeVO searchDisputeVO);
	
	public List<Map<String,Object>> searchDisputeNotesList(SearchDisputeVO searchDisputeVO);
	
	public long getDisputeNotesListPageNo(SearchDisputeVO searchDisputeVO);
	
	public long getDisputeActionRequestListPageNo(SearchDisputeVO searchDisputeVO);	
	
	public long getDisputeActionRequestListPageNoByBanId(SearchDisputeVO searchDisputeVO);	

	public long getDisputeActionRequestStatusById(String actionRequestStatusId);	
	
	public List<Map<String,Object>> searchDisputeActionRequestList(SearchDisputeVO searchDisputeVO);
	
	public List<Map<String,Object>> searchDisputeActionRequestListByBanId(SearchDisputeVO searchDisputeVO);
	
	public void deleteDisputeReply(String disputeActionRequestReplyId);
	
	public void addReplyNote(String disputeActionRequestReplyId,String note);
	
	public void updateDisputeActionRequestStatus(String disputeActionRequestId,String status);
	
	public void saveDisputeNote(String disputeId,String noteNotes);

	public void saveDisputeActionRequest(String actionRequestNotes, String disputeId,String disputeActionRequestStatus);
	
	public List<Map<String,Object>> searchDisputeReplyList(String disputeActionRequestId);
		
	public void updateSplitDispute(SearchDisputeVO sdo ,Integer userId );
	
	public void insertReconcileBySplit(SearchDisputeVO sdo ,Integer userId,String s1,String s2);
	
	public void insertReconcileByWin(SearchDisputeVO searchDisputeVO ,Integer userId );
	
	public void insertReconcileByLose(SearchDisputeVO searchDisputeVO ,Integer userId );
	
	public void updateDisputeCloseDisputeWins(SearchDisputeVO searchDisputeVO ,Integer userId );
	
	public Proposal findProposalById(Long id);
	
	public List<String> searchAttachmentFile(SearchDisputeVO searchDisputeVO);
	
	public List<String> searchDisputeReconciliation(SearchDisputeVO searchDisputeVO);
	
	public long getNumberOfReconciliations(SearchDisputeVO searchDisputeVO);
	
	public List<Object[]> searchDisputeItemByObject(SearchDisputeVO searchDisputeVO);
	
	public List<String> searchDisputeItem(SearchDisputeVO searchDisputeVO);
	
	public Dispute searchDisputeById(String disputeId);
	
	public List<Map<String,Object>> searchDisputeAttachmentFile(String attachmentPointId);
	
	public String searchDisputeEmailAddress(Integer attachmentPointId);
	
	public void contactVendorSaveDisputeMessage(Map<String,String> disputeMessage,Integer attachmentPointId);

	public List<Map<String,Object>> searchDisputeMessage(String disputeId);
	
	public long getNumberOfDisputeItems(SearchDisputeVO searchDisputeVO);
	
	
	public long searchDisputeCountByDays(WorkspaceVO wVO);
	
	public List<String> searchDisputeByDays(WorkspaceVO wVO);
	
	public List<Object[]> searchDisputeByExcelDays(WorkspaceVO wVO,List<Integer> ids);

	public List<String> searchDispute(SearchDisputeVO searchDisputeVO, int userId);
	public List<String> findDisputeDetail(int disputeId, int userId);	
	public long getNumberOfDispute(SearchDisputeVO searchDisputeVO, int userId);
	public List<Object[]> searchDisputeByObject(SearchDisputeVO sio, int userId);
	public List<Object[]> searchDisputeDetailByObject(Dispute dispute,SearchVO sio, int userId);
	public List<Object[]> searchDisputeTransctionByObject(Dispute dispute);
	public long getAssignmentCount(int currentUserId);
	
	public long getNumberOfReconcil(Dispute dispute,SearchVO vo);
	public List<String> findReconcileDetails(int disputeId, int userId,SearchVO vo);
	public long getNumberOfCredit(Dispute dispute,SearchVO vo);
	public List<String> findCreditDetails(Dispute dispute, SearchVO vo, int userId);
	public Double getCreditForReconcileTotalAmount(Dispute dispute,SearchVO vo);
	public long getNumberOfDisputeDetail(Dispute dispute,SearchVO vo);
	public List<String> findDisputeDetail(SearchVO vo ,Dispute dispute);
	public List<Object[]> getDisputeAccountCodeList(Dispute dispute);
//	public DisputeAction findDisputeActionById(java.lang.Integer id);
	public DisputeStatus findDisputeStatusById(java.lang.Integer id);
	public List<String> findDisputeTransaction(Dispute dispute);
	
	
	public String findTimerManager(Integer id);
	
	public Integer findUpdateTimerManagerProcesslist();
	
	public void updateTimerManager(Integer id,String flag);
	
	//create pendingDispute by pengfei.wang
	public List<String> findPendingDisputes(SearchDisputeVO searchDisputeVO,int userId);
	public long getPendingDisputesNumber(SearchDisputeVO searchDisputeVO, int userId);
	public List<String> searchDisputeAssignment(SearchDisputeVO svo);
	
	//create UpdateDisputebalanceRollback by suwei
	public void updateDisputeBalanceRollback(Dispute dispute);

	public abstract void save(Dispute transientInstance);

	public abstract void delete(Dispute persistentInstance);

	public abstract Dispute findById(java.lang.Integer id);

	public abstract List findByExample(Dispute instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByDisputeBalance(Object disputeBalance);

	public abstract List findByDisputeAmount(Object disputeAmount);

	public abstract List findByDisputeNumber(Object disputeNumber);

	public abstract List findByClaimNumber(Object claimNumber);

	public abstract List findByTicketNumber(Object ticketNumber);

	public abstract List findByNotes(Object notes);

	public abstract List findByFlagWorkspace(Object flagWorkspace);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();

	public abstract Dispute merge(Dispute detachedInstance);

	public abstract void attachDirty(Dispute instance);

	public abstract void attachClean(Dispute instance);
	
	public List findByInvoiceId(int invoiceId);
	/**
	 * Get Invoice Page Dispute Tab Info Totail Number [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public Integer getIDisputeTabTotalNO(SearchInvoiceVO svo);
	/**
	 * Get Invoice Page Dispute Tab Info List [Chao.Liu]
	 * @param svo
	 * @return
	 */
	public List<String> searchIDisputeTab(SearchInvoiceVO svo);
	public List searchIDisputeTabObject(SearchInvoiceVO svo);
	/**
	 * Get Dispute SCOA 
	 * @param svo
	 * @return
	 */
	public Integer getDisputeCount(SearchInvoiceVO svo);
	/**
	 * Get Dispute SCOA By Credit SCOA
	 * @param svo
	 * @return
	 */
	public Integer getDisputeSCOA(SearchInvoiceVO svo);
	public long getDisputeWorkCount(WorkspaceVO wVO);
	public List<String> searchDisputeWorkCount(WorkspaceVO wVO);
	
	public List getDisputeTotalAmountByInvoiceId(int invoiceId);

	
	public int saveAndReturnDisputeId(Dispute transientInstance);

	public void saveObject(Object o);
	public Object getObject(Class c, Integer id);
	public void updateObject(Object o);
	public int getDisputeTotalNoByInvoiceId(SearchInvoiceVO searchInvoiceVO);
	public List<String> searchDisputesByInvoiceId(SearchInvoiceVO searchInvoiceVO);
	public List<String> findByInvoiceIdAndReturnStringList(SearchInvoiceVO searchInvoiceVO);

	public List<Dispute> GetTheDisputesInFoToSendEmail();

	public WorkflowAction loadWorkflowActionById(int i);
	
	public Integer getAnnexsTotalOfDisputeByDisputeId(SearchInvoiceVO svo);
	
	public List searchAnnexsOfDisputeByDisputeId(SearchInvoiceVO svo);
	
	public Integer AfterSendEmailChangeDisputeStatus();

	public DisputeStatus loadDisputeStatusById(int i);

	public void updateRefileDisputeByProposal(String ids);

	public double getBalanceAmountByProposal(Integer id);

	public Double getOutstandingReserveAmount(String disputeId);

	public void updateDisputeAmountByProposal(String disputeId);

	public Double getDisputeAmountByProposal(int parseInt);

	public Double getReconcileByDispute1(String disputeId);

	public Double getReconcileByDispute2(String disputeId);

	public Double getReconcileByDispute3(String disputeId);

	public long getReconcileByDisputeFor3(String disputeId);
	
	public void updateDisputeFlagAndReplayDateByDisputeNumber(String disputeNumber);
	
	public String getDisputeAccountNumber(String disputeNumber);

	public String getDisputeVendorName(String disputeNumber);
	
	public String getDisputeClaimNumber(String disputeNumber);

}