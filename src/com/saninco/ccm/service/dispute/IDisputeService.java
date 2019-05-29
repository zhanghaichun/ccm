/**
 * 
 */
package com.saninco.ccm.service.dispute;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Contact;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.Email;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.util.SystemEmailConfig;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.OperationsVO;
import com.saninco.ccm.vo.SearchDisputeVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author xinyu.Liu
 * 
 */
public interface IDisputeService {
	
	public void updateCancelDispute(SearchDisputeVO searchDisputeVO);
	
	public void recalculateDisputeAmountAndDisputeBalance(String disputeId);

	public void updateDisputeBalance(String disputeId);
	
	public void addReconcileSCOA(SearchDisputeVO searchDisputeVO);
	
	public void addOutstandingDisputeItemsSCOA(SearchDisputeVO searchDisputeVO);
	
	public void updateDispute(SearchDisputeVO searchDisputeVO);
	
	public void upodateDisputeAttachmentPoint(Integer disputeId, AttachmentPoint attachmentPoint);
	
	public AttachmentPoint findAttachmentPoint(Integer attachmentPointId);
	
	public void updateDisputeAttachmentFile(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String searchAttachmentFile(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String getAccessoriesListPageTotalNo(SearchDisputeVO searchDisputeVO) throws BPLException;

	public void updateTransactionHistory(SearchDisputeVO searchDisputeVO,AttachmentPoint attachmentPoint);
	
	public String getExcelByTransaction(SearchDisputeVO searchDisputeVO, String excelDirPath, List<String> titles)throws BPLException;
	
	public String searchTransactionHistory(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String searchDisputeNotesList(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String getDisputeNotesListPageNo(SearchDisputeVO searchDisputeVO) throws BPLException;
	
    public String searchDisputeActionRequestList(SearchDisputeVO searchDisputeVO) throws BPLException;
    
    public String searchDisputeActionRequestListByBanId(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String getDisputeActionRequestListPageNo(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String getDisputeActionRequestListPageNoByBanId(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String searchDisputeReplyList(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public void deleteDisputeReply(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public void addReplyNote(SearchDisputeVO searchDisputeVO,String note) throws BPLException;
	
	public void updateDisputeActionRequestStatus(SearchDisputeVO searchDisputeVO,String status) throws BPLException;
	
	public void saveDisputeNote(String disputeId,String noteNotes)throws BPLException;
	
	public String getExcelByDisputeItem(SearchDisputeVO searchDisputeVO, String excelDirPath, List<String> titles)throws BPLException;
	
	public void updateDisputeSplitCloseDispute(SearchDisputeVO searchDisputeVO);
	
	public void updateDisputeCloseDisputeLose(SearchDisputeVO searchDisputeVO);
	
	public void updateDisputeCloseDisputeWin(SearchDisputeVO searchDisputeVO);
	
	public String updateReconciliation(int reconcileId,int count);
	
	public String searchDisputeReconciliation(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String getDisputeReconciliationListPageTotalNo(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String searchDisputeItem(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public List<Map<String,Object>> searchDisputeMessageByDisputeIds(String disputeId) throws BPLException;
	
	public Map<String,Object> searchContactVendorByDisputeIds(String disputeId) throws Exception;
	
	public  void contactVendorDispute(Map<String,String> disputeMessage,AttachmentPoint point) throws Exception;
	
	public String getDisputeItemViewListPageTotalNo(SearchDisputeVO searchDisputeVO) throws BPLException;
	
	public String searchDispute(SearchDisputeVO searchDisputeVO) throws BPLException;

	public String getDisputeSearchTotalPageNo(SearchDisputeVO searchDisputeVO) throws BPLException;

	public String getReconcileDetailTotalNo(SearchVO vo, Dispute dispute) throws BPLException;

	public String getReconcileDetails(Dispute dispute, SearchVO vo) throws BPLException;

	public String getCreditDetailsTotalNo(SearchVO vo, Dispute dispute) throws BPLException;

	public String getDisputeDetailTotalNo(Dispute dispute, SearchVO vo) throws BPLException;

	public String searchCreditDetails(SearchVO vo, Dispute dispute) throws BPLException;

	public String getDisputeDetail(Dispute dispute, SearchVO vo) throws BPLException;

	public String[] updateCreditForReconcile(SearchDisputeVO searchDisputeVO,Dispute dispute, Credit credit, Reconcile reconcile) throws BPLException;

	public String updateCancelForReconcile(Dispute dispute, Credit credit, Reconcile reconcile) throws BPLException;

	public Dispute findDisputeById(int disputeId) throws BPLException;
	
	public List<MapEntryVO<String, String>> getDisputeAccountCodeList( Dispute dispute) throws BPLException;

	public String getDisputeTransaction(Dispute dispute) throws BPLException;

	public String searchPendingDispute(SearchDisputeVO searchDisputeVO) throws BPLException;

	public String queryDisputeAttachmentCount(String disputeId) throws BPLException;	

	public String getPendingDisputeTotalPageNo(SearchDisputeVO searchDisputeVO) throws BPLException;

	public String searchDisputeAssignment(SearchDisputeVO searchDisputeVO) throws BPLException;

	public String getDisputeAssignmentSearchTotalPageNo( SearchDisputeVO searchDisputeVO) throws BPLException;

	public String getDisputeWorkCount(WorkspaceVO wVO) throws BPLException;

	public String searchDisputeWorkCount(WorkspaceVO wVO) throws BPLException;

	public Integer updateDisputeTiketNO(SearchDisputeVO searchDisputeVO,Dispute dispute, List<String> uploadsFileName) throws BPLException;

	public String createDisputeToExcel(SearchDisputeVO searchDisputeVO,String excelDirPath, List<String> titles) throws BPLException;

	public String createDisputeDetailToExcel(Dispute dispute, SearchVO vo,String excelDirPath, List<String> titles) throws BPLException;

	public String createDisputeTransactionToExcel(Dispute dispute, SearchVO vo,String excelDirPath, List<String> titles) throws BPLException;

	public String getValidateDisputeBanLock(String disputeId) throws BPLException;
	
	public Invoice getInvoiceObject(String invoiceId) throws BPLException;
	
	public List<Dispute> GetTheDisputesForSendEmail() throws BPLException;
	
	public Contact GetTheContactByDiputeForSendEmail(Dispute dispute)throws BPLException;
	
	public SystemEmailConfig getSystemEmailConfigForSendEmail() throws BPLException;
	
	public void CreateEmialsAddEmailTable() throws BPLException;
	
	public String getPreEmailsTotalPageNoOfDispute(OperationsVO operationsVO) throws BPLException;
	
	public String searchPreEmailsOfDispute(OperationsVO operationsVO) throws BPLException;
	
	public String getEmailByEmailId(Integer EmailId)throws Exception;
	
	public void updateEmailByEmail(Email email)throws Exception;
	
	public void updateSendCheckedEmailsAndChangeEmailStatus(String[] emailIds)throws Exception;
	
	public String getAnnexsOfDisputeTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchAnnexsOfDispute(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String updateReconciliationMany(String ids);

	public void updateRefileDisputeByProposal(String ids);

	public Double updateIncreaseAndDecrease(Dispute dispute, String reserveType,
			Integer accountCodeId, AttachmentPoint point);

	public Double getOutstandingReserveAmount(String disputeId) throws BPLException;

	public Double getDisputeAmount(String disputeId) throws BPLException;

	public void updateDisputeAmount(String disputeId);

	public String validateReconcileByDispute(String disputeId) throws BPLException;
	
	public String searchDisputeCountByDays(WorkspaceVO wvo) throws BPLException;
	
	public String searchDisputeByDays(WorkspaceVO wvo) throws BPLException;
	
	public String getDisputeExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles,List<Integer> ids)throws BPLException;

	public void saveDisputeActionRequest(String actionRequestNotes, String disputeIds,String disputeActionRequestStatus)throws BPLException;

	public void updateTimerManager(Integer id,String flag);
	
	public String findTimerManager(Integer id);
	
	public Integer findUpdateTimerManagerProcesslist();
}
