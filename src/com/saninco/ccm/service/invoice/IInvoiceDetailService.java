package com.saninco.ccm.service.invoice;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.AuthenticationFailedException;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.AuditMemory;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceItem;
import com.saninco.ccm.model.InvoiceStatus;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * xinyu.liu
 */
public interface IInvoiceDetailService {
	
	public String searchListTabDataView(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getItemTypeListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchItemTypeListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchBrowseInvoiceListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getBrowseInvoiceListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchDisputeItemsListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getDisputeItemsListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchEmptySCOAListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getEmptySCOAListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchSCOACodingTabView(SearchInvoiceVO sio) throws BPLException;
	
	public String getAccordionTab(SearchInvoiceVO sio) throws BPLException;
	
	public void updateDisputeAmount(SearchInvoiceVO sio) throws BPLException;
	
	public String searchCountListServiceByItem(SearchInvoiceVO sio) throws BPLException;
	
	public String searchCountListService(SearchInvoiceVO svo) throws BPLException;
	
	public void updateProposalByAttachmentPoint(SearchInvoiceVO searchInvoiceVO,AttachmentPoint attachmentPoint);
	
	public AttachmentPoint findAttachmentPoint(SearchInvoiceVO searchInvoiceVO);
	
	public void updateInvoiceProposalPoint(SearchInvoiceVO searchInvoiceVO,AttachmentPoint attachmentPoint);
	
	public String saveInvoiceReject(SearchInvoiceVO searchInvoiceVO);
	
	public void updateSCOACodingAndNote(SearchInvoiceVO sio);
	
	public void updateSCOACodingSingleAndNote(SearchInvoiceVO sio);
	
	public void updateProposalToCreditAmount(SearchInvoiceVO sio);
	
	public void updateInvoiceItemAmount(SearchInvoiceVO sio) throws BPLException;
	
	public String createDownloadSOCADisputeToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath,List<String> titles) throws BPLException ;
	
//	public String createDownloadInvoiceDteailToExcel(SearchInvoiceVO sio,String excelDirPath) throws BPLException ;
	
	public String searchItemTypeList(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getItemTypeViewListPageNo(SearchInvoiceVO svo) throws BPLException;
	
	public String searchDisputeItemsList(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getDisputeItemsViewListPageNo(SearchInvoiceVO svo) throws BPLException;
	
	public String searchEmptySCOAList(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getEmptySCOAViewListPageNo(SearchInvoiceVO svo) throws BPLException;
	
	public String findAccordionList(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public void updateGroupProposalDataByProposalBoxId(SearchInvoiceVO searchInvoiceVO);
	
	public void updateProposalDataByProposalId(SearchInvoiceVO searchInvoiceVO);
	
	public String searchProposalList(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getProposalViewListPageNo(SearchInvoiceVO svo) throws BPLException;
	
	public void updateInvoiceStatusId(Invoice invoice) throws BPLException ;
	
	public String createDownloadInvoiceToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath) throws BPLException ;

	public String downloadInvoiceValidationExcel(Invoice i,String excelDirPath) throws BPLException ;

	public String downloadTelephoneNumberValidationExcel(Invoice i,String excelDirPath) throws BPLException ;
	
	public String createInvoiceToExcel(String invoiceId,String excelDirPath) throws BPLException ;
	
	public List<Map<String,Object>> searchInvoiceOriginal(String invoiceId) throws BPLException ;
	
	public String createSOCADisputeToExcel(String invoiceId,String excelDirPath) throws BPLException ;
	
	public String getItemDisplayListName(SearchInvoiceVO searchInvoiceVO) throws BPLException ;
	
	public String createInvoiceItemListToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException ;
	
	public String createInvoiceDetailToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException ;
	
	public void updateInvoiceItemNotProposedAmount(SearchInvoiceVO searchInvoiceVO) throws BPLException ;
	
	public void updateInvoiceItemProposalsAmount(SearchInvoiceVO searchInvoiceVO) throws BPLException ;
	
	public void updateInvoiceItemProposals(SearchInvoiceVO searchInvoiceVO) throws BPLException ;
	
	public String getInvoiceItemGroupProposals(String boxInId) throws BPLException;
	
	public String getInvoiceItemProposals(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getItemGroupProposalTotalAmount(String boxInId) throws BPLException;
	
	public String getItemProposalTotalAmount(String invoiceItemId) throws BPLException;
	
	public String getInvoiceItemTotal(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public Double addDouble(Double d1, Double d2, Double d3);
	
	public List<Map<String,Object>> searchValidationResult(String proposalId, String invoiceId, String auditSourceType, String circuitNumber) throws BPLException;
	
	public User getAssignedAnalys(User user,Integer assignedAnalysId) throws BPLException;
	
	public String getInvoiceItemTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getInvoiceItemLists(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public List<MapEntryVO<String, String>> getRelatedInvoices(String invoiceId) throws BPLException;

	public Invoice getInvoiceByInvoiceId(String invoiceId) throws BPLException;
	
	public User searchInvoiceAnalystName(Invoice invoice) throws BPLException;
	
	public void addTransactionHistory(TransactionHistory th,String openInterface, int workflowActionId,String workflowUserName,String notes) throws BPLException;
	
	public String[] addExternalEmail(String toEmail) throws BPLException;
	
	public void cleanExternalApprovalNotes(String invoiceId) throws BPLException ;
	
	public void auditInvoice (String invoiceId) throws BPLException;

	public Invoice getInvoiceInvoiceItem(String itemId) throws BPLException;

	public InvoiceItem getInvoiceItemByInvoiceItemId(String itemId) throws BPLException;

	public String searchInvoiceByBanIdSame(Invoice invoice) throws BPLException;

	public void addItemProposal(Invoice invoice, InvoiceItem invoiceItem, SearchInvoiceVO sio);

	public void updateItemProposal(String proposalId, SearchInvoiceVO sio);
	
	public void  updateBanAutoPay(Integer banId) throws BPLException;

	// Do not use
	// public String searchInvoiceItemName(String invoiceId) throws BPLException;

	// Do not use
	// public String searchInvoiceItemNameList(String invoiceId) throws BPLException;

	public String getTypeOpenAcc(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public List<String> searchInvoiceLabelList(Invoice invoice) throws BPLException;

	public String getproposal(String itemId) throws BPLException;

	public void updateInvoiceLabel(String invoiceId, String labelId, boolean bo) throws BPLException;

	public String searchItemNameList(String invoiceItemId) throws BPLException;
	
	public String getItemTypeList(String idName, String invoiceOritemId) throws BPLException;

	public String getItemTypeTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String getItemProposalList(String itemId) throws BPLException;

	// Original pagination
	public String getOriginalTotalPageNo(SearchInvoiceVO svo) throws BPLException;

	// Original query
	public String getOriginalList(SearchInvoiceVO svo) throws BPLException;

	// Tariff pagination
	public String getTariffTotalPageNo(SearchInvoiceVO svo) throws BPLException;

	// Tariff query
	public String getTariffList(SearchInvoiceVO svo) throws BPLException;

	public String getDisputeTotalNOByInvoiceId(SearchInvoiceVO svo, int invoiceId) throws BPLException;

	public String searchDisputesByInvoiceId(SearchInvoiceVO svo, int invoiceId) throws BPLException;

	public String getPaymenttablaTotalPageNo(SearchInvoiceVO svo) throws BPLException;

	public String getPaymenttablaList(SearchInvoiceVO svo) throws BPLException;
	
	public String getMiscTotalPageNo(SearchInvoiceVO svo) throws BPLException;

	public String getMiscList(SearchInvoiceVO svo) throws BPLException;
	
	public Long addMiscProposalPayment(SearchInvoiceVO sio)throws BPLException;
	
	public void editMiscProposalPayment(SearchInvoiceVO sio)throws BPLException;
	
	public void deleteMsicPayment(int id);

	public String getAmount(SearchInvoiceVO svo)throws BPLException;

	public String getPaymentScoaAmount(SearchInvoiceVO svo)throws BPLException;

	public String getPaymentScoaAmountTotalPageNo(SearchInvoiceVO svo)throws BPLException;
	
	public String getInvoiceDetailByInvoiceNumberTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String getApplyingCreditPaymentTotalPageNo(SearchInvoiceVO svo)throws BPLException;
	
	public String getApplyingCreditPayment(SearchInvoiceVO svo)throws BPLException;
	
	public String getInvoiceDetailByInvoiceNumber(SearchInvoiceVO svo)throws BPLException;

	public String getCurrentInvoicePayment(SearchInvoiceVO svo)throws BPLException;

	public String getCurrentInvoicePaymentTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String getManuallyCreatedPayment(SearchInvoiceVO svo)throws BPLException;

	public String getManuallyCreatedPaymentTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String getDisputePaybackPayment(SearchInvoiceVO svo)throws BPLException;

	public String getDisputePaybackPaymentTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String getCurrentDispute(SearchInvoiceVO svo)throws BPLException;

	public String getCurrentDisputeTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String getCurrentInvoiceHistory(SearchInvoiceVO svo)throws BPLException;

	public String getPastThreePaymentPage(SearchInvoiceVO svo)throws BPLException; 
	
	public String currentDisputeToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException;
	
	/**
	 * @author Chao.Liu
	 * DownLoad Current Invoice Payment Excel 
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByPaymentTabPCIP(SearchInvoiceVO svo,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException;
	/**
	 * @author Chao.Liu
	 * DownLoad Manually Created Payment Excel
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByPaymentTabPMCP(SearchInvoiceVO svo,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException;
//	public String getPastThreePaymentPageTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String saveExcelByActionHistory(SearchInvoiceVO svo, String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException;

	public String disputePaybackToExcel(SearchInvoiceVO svo, String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException;

	public String applyingCreditToExcel(SearchInvoiceVO svo, String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException;

	public String AllPaymentsToExcel(SearchInvoiceVO svo, String excelDirPath,
			List<String> titles, String invoiceNumber)throws BPLException;

	public Invoice updateInvoiceWorkflowAndSaveMsMessage(Invoice invoice, Integer workflowActionId, String uploadsMessage,String oldInvoiceStatusId,AttachmentPoint point,String approveWinUserId,String approveWinRoleId)throws BPLException;
	
	public Invoice updateInvoiceWorkflow(Invoice invoice, Integer workflowActionId, String uploadsMessage,String oldInvoiceStatusId)throws BPLException, AuthenticationFailedException;

	public Long getInvoiceCreditBalance(Integer id)throws BPLException;

	public Double[] getProposalAmount(Integer id)throws BPLException;

	public InvoiceStatus getInvoiceStatus(Integer invoiceStatusId)throws BPLException;

	public String getPaidDisputePayment(SearchInvoiceVO svo)throws BPLException;

	public String getPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String getShortPaidDisputePayment(SearchInvoiceVO svo)throws BPLException;

	public String getShortPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo)throws BPLException;

	public String getExcelByPaidDisputePayment(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber)throws BPLException;

	public String getExcelByShortPaidDisputePayment(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber)throws BPLException;

	public Long getUngroupedDisputeCount(Integer id)throws BPLException;

	public String validateBanLock(Integer banId)throws BPLException;

	public String getOutstandingCreditPageTotalNo(SearchInvoiceVO svo)throws BPLException;

	public String getOutstandingCreditPageData(SearchInvoiceVO svo)throws BPLException;

	public String updateCreditToCurrentPayment(String inProposalIds)throws BPLException;

	public String updateRecordDisputeAndApplyToPayment(Integer invoiceId,String inProposalIds,
			AttachmentPoint point, String uploadsMessage);

	public String updateOutstandingCreditSplitApply1(Integer invoiceId,
			Long proposalId, AttachmentPoint point, String uploadsMessage,
			Double balanceAmount,Integer accountCodeId);

	public String updateOutstandingCreditSplitApply2(Integer invoiceId, Long proposalId,
			AttachmentPoint point, String uploadsMessage, Double balanceAmount,
			Integer accountCodeId);

	public String getNotReconcileDisputePageData(SearchInvoiceVO svo)throws BPLException;

	public String getNotReconcileDisputePageTotalNo(SearchInvoiceVO svo)throws BPLException;

	public String createReconcileByCurrentCreditM1(String inProposalIds,
			Integer invoiceId, Long disputeId, AttachmentPoint point,
			String notes,Integer accountCodeId);

	public String createReconcileByCurrentCredit1M(Long creditProposalId, Integer invoiceId,
			String disputeProposalIds, AttachmentPoint point, String uploadsMessage,Integer accountCodeId);

	public String createReconcileByCurrentCredit11(Long creditProposalId,
			Integer invoiceId, Long disputeProposalId, AttachmentPoint point,
			String notes, Double balanceAmount,Integer accountCodeId);

	public String getTheInvoiceReconciliationPageData(SearchInvoiceVO svo)throws BPLException;

	public String getTheInvoiceReconciliationPageTotalNo(SearchInvoiceVO svo)throws BPLException;
	
	public String deleteReconciliation(Integer reconcileId);
	
	public Double getInvoicePaymentAmount(Invoice invoice) throws BPLException;
	
	public boolean getInvoiceDisputeStatusCloseAndActionRequestOpenCount(Invoice invoice);
	
	public boolean getBanDisputeDueDate (Integer invoiceId);

	public Double getShortPaidDisputeAmount(Invoice invoice2)throws BPLException;

	public Long[] getUnLevelAndNotes(Invoice invoice2)throws BPLException;
	
	public String saveExcelByPaymentTabMISC(SearchInvoiceVO svo,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException;
	
	public String miscDisputePaybackToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException;

	public String getOutstandingCreditPageExcel(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber)throws BPLException;

	public String getNotReconcileDisputePageExcel(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber)throws BPLException;

	public String getTheInvoiceReconciliationPageExcel(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber)throws BPLException;
	
	public String saveCloseAsDisputeLosePageExcel(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException;
	
	public String saveCloseAsDisputeWinPageExcel(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException;

	public void updateHistoryFiles(AttachmentPoint point, Invoice resultInvoice);
	
	public void addOutstandingDisputeSCOA(String inProposalIds, String scoa, String note);

	public void addOutstandingDisputeSCOAAll(String inProposalIds, String scoa, String note);

	public String getMiscPaymentAmount(SearchInvoiceVO svo)throws BPLException;

	public void updateRecodeTaxInvoice(String invoiceId);

	public String deleteReconciliationMany(String inIds);

	public String getCloseAsDisputeLosePageData(SearchInvoiceVO svo) throws BPLException;

	public String getCloseAsDisputeLosePageTotalNo(SearchInvoiceVO svo) throws BPLException;	
	
	public String getCloseAsDisputeWinPageData(SearchInvoiceVO svo) throws BPLException;

	public String getCloseAsDisputeWinPageTotalNo(SearchInvoiceVO svo) throws BPLException;
	
	public String searchBanAuditFlag(String invoiceId);
	
	public String autopaySwitch(String disputeId) throws BPLException;
	
	public void updateAutopaySwitch(Integer banId,String autopaySwitch)throws BPLException;

	public void updateInvoiceNotes(SearchInvoiceVO svo) throws BPLException;
	
	public boolean searchInvoiceStatus(String invoiceId) throws BPLException;
	
	public void externalAproveBack(String backValue,Integer invoiceId,String workflowUserName,String userName,int userId,String OpenInterface,String externalEmailId,String notes) throws Exception;
	
	public Long getTransactionHistoryCount(Integer invoiceId) throws BPLException;
	
	public Long getExternalEmailCount(String externalEmailId) throws BPLException;

	public Long doViewOriginalList(Integer invoiceId) throws BPLException;
	
	public Long getInactiveSCOACount(Integer invoiceId);

	public List<HashMap<String, String>> getInvoiceLabelsByInvoiceId(String invoiceId) throws BPLException;
	
	public List<HashMap<String, String>> getOriginalListByInvoiceId(int invoiceId) throws BPLException;
	
	public HashMap<String, String> getOriginalDetail(int originalId) throws BPLException;
	
	public Map<String, Object> searchInvoiceAuditStatus(String invoiceId);
	
	/**
	 * Get autopay error notes.
	 */
	public List<HashMap<String, String>> searchAutopayErrorNotes(String invoiceId) throws BPLException;
	
	public Map<String, Object> searchAuditMemory(String proposalId);
	
	public String saveMemory(AuditMemory auditMemory) throws BPLException;
	
	public void updateMemory(AuditMemory auditMemory) throws BPLException;
	
	public boolean checkWorkflowPrivilege(Integer invoiceId);
	
	public boolean checkWorkflowPrivilegeAndShowMessage(Integer invoiceId);
	
	public List<Map<String, String>> getToleranceRateList(String invoiceId);
	
	public String searchBanExemption(String invoiceId);
	
	public String searchBanOpenAutoPay(String invoiceId);

	public String getSystemUrlAdress();
	
	public List<HashMap<String, String>> searchBanExemptionDate(String invoiceId) throws BPLException;
	
	public String getItemTypeByProposalId(String proposalId);
	
	public Map<String, Object> searchInvoiceAttachment(String invoiceId);
	
	public List<MapEntryVO<String, String>> getSearchProductList(String proposalId)  throws BPLException;
	public List<MapEntryVO<String, String>> getSearchProductComponentList()  throws BPLException;
	public List<MapEntryVO<String, String>> getSearchProvinceList()  throws BPLException;
	
	/**
	 * Dispose uploaded scoa data
	 */
	public Map<String,String> uploadSCOAData( File uploadFile, String fileName, String errorExcelDirPath, String invoiceId );
	
	public String creatExcelByTemplate(String excelDirPath, List<String> titles) throws BPLException;

	public String searchIsUasgeBan(String invoiceId);
	
	public String runReport(String filePath,int invoiceId,int term) throws BPLException;
}
