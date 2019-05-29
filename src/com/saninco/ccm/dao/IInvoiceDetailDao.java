package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.AuditMemory;
import com.saninco.ccm.model.DisputeReason;
import com.saninco.ccm.model.Email;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceItem;
import com.saninco.ccm.model.InvoiceLabel;
import com.saninco.ccm.model.InvoiceStatus;
import com.saninco.ccm.model.Label;
import com.saninco.ccm.model.Originator;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author xinyu.Liu
 * 
 */
public interface IInvoiceDetailDao {
	
	public List<Object[]> searchListTabDataByItemType(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> searchListTabDataByDisputed(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> searchListTabDataBySCOA(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> searchListTabDataByBrowse(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> findTypeOpenAccByItemType(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> getSOCADisputeForBrowseInvoiceListOfObject(StringBuffer querySql,String invoiceId,String itemTypeId);
	
	public List<Object[]> getSOCADisputeListOfObjectByitemTypeId(SearchInvoiceVO sio);
	
	public List<Object[]> findTypeOpenAccByBrowseInvoice(SearchInvoiceVO searchInvoiceVO);
	
	public long getItemTypeListTabNumber(SearchInvoiceVO searchInvoiceVO);
	
	public long getBrowseInvoiceListTabNumber(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> findTypeOpenAccByDisputeItems(SearchInvoiceVO searchInvoiceVO);
	
	public long getDisputeItemsListTabNumber(SearchInvoiceVO searchInvoiceVO);
	
	public List<String> findSCOACodingTabLists(SearchInvoiceVO sio);
	
	public long getEmptySCOAListTabNumber(SearchInvoiceVO searchInvoiceVO);
	
	public List<String> getAccordionTabsByBrowse(SearchInvoiceVO sio);
	
	public List<Object> getInvoiceItemTypeIds(SearchInvoiceVO sio) ;
		
	public List<String> getAccordionTabsBySCOA(SearchInvoiceVO sio);
	
	public List<String> getAccordionTabsByDisputed(SearchInvoiceVO sio);
	
	public List<String> getAccordionTabsByItemType(SearchInvoiceVO sio);
	
	public void updateDisputeAmount(String invoiceId);
	
	public List<Object[]> searchProposalCountList(SearchInvoiceVO sio,String name);
	
	public void updateSCOACodingAndNoteData(SearchInvoiceVO sio, Integer userId);
	
	public void updateSCOACodingSingleAndNoteData(SearchInvoiceVO sio,String accountCodeId,String amount);
	
	public void UpdateInvoiceItemTable(Integer invoiceId) throws SQLException;
	
	public List<Object[]> getSOCADisputeListOfObject(SearchInvoiceVO sio);
	
	public long getNumberOfSOCADisputeList(SearchInvoiceVO sio);
	
	public void updateMoveToCreditProposal(SearchInvoiceVO sio,Integer userId);
	
	/**
	 * Insert scoa data into temporary table.
	 */
	public void insertSCOADataTemporary(List<Map<String,String>> dataList, List<String> columnList, String batchNo, String invoiceId) throws SQLException;
	
	
	/**
	 * Query error data list from error temporary table
	 * so that creating error data excel file.
	 * @return
	 */
	public List<Map<String,Object>> queryTmpSCOAError();
	
	public void updateGroupDisputeProposalByDispute(SearchInvoiceVO sio,Integer userId);
	
	public void updateGroupDisputeProposalByPayment(SearchInvoiceVO sio,Integer userId);
	
	public List<String> searchItemTypeList(SearchInvoiceVO sio);
	
	public long getNumberOfItemType(SearchInvoiceVO sio);
	
	public List<String> searchDisputeItemsList(SearchInvoiceVO sio);
	
	public long getNumberOfDisputeItems(SearchInvoiceVO sio);
	
	public long getTransactionHistoryCount(Integer invoiceId);
	
	public long getExternalEmailCount(String externalEmailId);
	
	public long doViewOriginalList(Integer invoiceId);
	
	public List<String> searchEmptySCOAList(SearchInvoiceVO sio);
	
	public long getNumberOfEmptySCOAs(SearchInvoiceVO sio);
	
	public List<Object[]> findItemLabelListByInvoiceId(SearchInvoiceVO searchInvoiceVO);
	
	public List<String> searchProposalList(SearchInvoiceVO searchInvoiceVO);
	
	public long getNumberOfProposals(SearchInvoiceVO svo);
	
	public List<String> findInvoiceItemTypeListNameDefult(String itemTypeId, String invoiceStructureCodeId);
	
	public void UpdateProposal(Integer invoiceItemId,Integer accountCodeId,Integer disputeReasonId,Integer nowAccountCodeId,Integer nowDisputeReasonId,String action,Integer userId,String description) throws SQLException ;
	
	public InvoiceStatus loadInvoiceStatusById(Integer id);
	
	public List<String> findInvoiceItemTypeListName(SearchInvoiceVO searchInvoiceVO); 
	
	public Object[] findInvoiceItemByInvoiceItemIdObject(String invoiceItemId,String invoiceItemName,Integer invoiceId);
	
	public List<String> findInvoiceItemByInvoiceItemId(String invoiceItemId,String invoiceItemName,Integer invoiceId);
	
	public Object[] findInvoiceItemByInvoiceIdObject(Integer invoiceId);
	
	public List<Object[]> findInvoiceItemListByObject(SearchInvoiceVO sio);
	
	public List<Object[]> findTypeOpenAccByObject(SearchInvoiceVO searchInvoiceVO);
	
	public DisputeReason findDisputeReasonById(Integer id);
	
	public List<Object[]> findNotProposedAmount(String invoiceId,String invoiceItemInId);
	
	public void updateProposalsAmountZero(SearchInvoiceVO searchInvoiceVO,String invoiceItemIdStrings,String proposalName);
	
	public void updateProposalsAmount(SearchInvoiceVO searchInvoiceVO,String invoiceItemIdStrings,String type);
	
	public void updateProposals(SearchInvoiceVO searchInvoiceVO,String invoiceItemIdStrings);
	
	public List<String> searchInvoiceItemProposals(String invoiceItemId); 
	
	public List<Object[]> searchInvoiceItemGroupProposals(String invoiceItemId); 
	
	public List<String> searchItemGroupProposalTotalAmount(String boxInId,Integer invoiceId); 
	
	public List<String> searchItemProposalTotalAmount(String invoiceItemId,String invoiceItemInId); 
	
	public long getInvoiceItemListOfInvoices(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> getRelatedInvoicesByInvoiceId(Integer invoiceId);
	
	public List<Object[]> getRelatedInvoicesByRelativeInvoiceId(Integer invoiceId);
	
	public Invoice findInvoiceById(Integer id);
	
	public Object[] findbudgetOwnerEmail(Integer id);
	
	public void auditInvoice(Integer id) throws SQLException;
	
	public void recodeInvoiceSCOA(Integer id) throws SQLException;
	
	public List<String> findInvoiceIdBybanId(Integer banId);
	
	public String findItemLowerMoneySum(Integer itemId) throws SQLException ;
	
	public List<BigInteger> findTypeOpenAccItemId(SearchInvoiceVO searchInvoiceVO);
	
	public List<String> findItemTypeListByItemId(String itemId,String invoiceId);
	
	public List<String> findItemProposalByItemId(Integer itemId);
	
	public InvoiceItem findInvoiceItemById(Long id);
	
	public List<BigInteger> findParentItemIdByinvoiceId(int invoiceId);
	
	public List<String> findProposalByitemId(String itemId);
	
	public long getOriginalTotalNO(SearchInvoiceVO svo);
	
	public List<String> searchOriginal(SearchInvoiceVO svo); 
	
	public long getTariffTotalNO(SearchInvoiceVO svo);
	
	public List<String> searchTariff(SearchInvoiceVO svo); 
	
	public List<String> searchInvoiceLabel(Integer invoiceId); 
	
	public InvoiceLabel findInvoiceLabelById(Integer id);
	
	public List<String> findInvoiceLabelId(String invoiceId, String labelId, String recActiveFlag);
	
	//Do not use
	//public List<String> findInvoiceItemList(String invoiceId); 
	
	public List<String> findInvoiceItemList(SearchInvoiceVO sio); 
	
	public List<String> findItemTypeList(String invoiceId); 
	
	public Label findLabelById(Integer id);
	
	public void saveInvoiceLabel(InvoiceLabel transientInstance);
	
	public long getInvoiceDetailNumberOfInvoices(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> findInvoiceSummary(String invoiceId);
	
	public long findInvoiceItemListNumber(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> findInvoiceItemListObject(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> findItemDisplayTitleName(SearchInvoiceVO searchInvoiceVO);
	
	public List<Object[]> findItemDisplayTitleNameDefult(String itemTypeId,String invoiceStructureId);
	
	public List<Object[]> findItemTypeAndInvoiceStructure(String invoiceId,String banId);
	
	public List<Object[]> findTypeOpenAcc(SearchInvoiceVO searchInvoiceVO);
	
	public List<String> findItemProposal(String itemId);
	
	public long getDisputeTotalNOByInvoiceId(SearchInvoiceVO svo,int invoiceId);
	
	public List<String> searchDisputesByInvoiceId(SearchInvoiceVO svo,int invoiceId);

	public long getPaymenttablaTotalNO(SearchInvoiceVO svo);
	
	public List<String> searchPaymenttabla(SearchInvoiceVO svo); 
	
	public Long saveProposal(Proposal transientInstance);
	
	public void addTransactionHistory(TransactionHistory th,String openInterface,int workflowActionId,String workflowUserName,String notes);
	
	public String[] addExternalEmail(String toEmail);
	
	public String externalEmailToUser(String userId,String budgetId ,String toEmail);
	
	public void updateExternalEmail(String id);
	
	public void updateBanAutoPay(Integer id);
	
	public Proposal findProposalById(Long id);
	
	public AccountCode findAccountCodeById(Integer id);
	
	public Originator findOriginatorById(Integer id);
	
	public List<Object[]> getInvoiceExcel(String invoiceId); 
	
	public User findUserById(Integer id);
	
	public List<Object[]> searchValidationResult(String proposalId, String invoiceId, String auditSourceType, String circuitNumber);
	
	public List<Object[]> findInvoiceItemTotalToInvoiceItem(Integer invoiceId);
	
	public List<String> findInvoiceItemByInvoiceId(Integer invoiceId,String inId);
	
	public long getMiscTotalNO(SearchInvoiceVO svo);
	
	public List<String> searchMisc(SearchInvoiceVO svo);

	public String getTotalPayment(Integer invoiceId,Integer banId);

	public String getTotalDispute(Integer invoiceId);
	
	public String getTotalShortPaidDispute(Integer invoiceId);
	
	public String getTotalPaidDispute(Integer invoiceId);
	
	public String getshortPaidforPrevious(Integer invoiceId1);

	public String getPaidforPrevious(Integer invoiceId1);
	
	public String getTotalDisputePayment(Integer invoiceId);

	public String getTotalShortPaidDisputePayment(Integer invoiceId);

	public String getTotalPaidDisputePayment(Integer invoiceId);
	
	public long getPaymentScoaAmountTotalPageNo(SearchInvoiceVO svo);
	
	public long getInvoiceDetailByInvoiceNumberTotalPageNo(SearchInvoiceVO svo);
	
	public long autopaySwitch(String invoiceId);
	
	public void updateAutopaySwitch(Integer banId,String autopaySwitch);

	public List getPaymentScoaAmount(SearchInvoiceVO svo);

	public long getApplyingCreditPaymentTotalPageNo(SearchInvoiceVO svo);

	public List getApplyingCreditPayment(SearchInvoiceVO svo);
	
	public List getInvoiceDetailByInvoiceNumber(SearchInvoiceVO svo);

	public long getCurrentInvoicePaymentTotalPageNo(SearchInvoiceVO svo);

	public List getCurrentInvoicePayment(SearchInvoiceVO svo);

	public long getManuallyCreatedPaymentTotalPageNo(SearchInvoiceVO svo);

	public List getManuallyCreatedPayment(SearchInvoiceVO svo);

	public List getDisputePaybackPayment(SearchInvoiceVO svo);

	public long getDisputePaybackPaymentTotalPageNo(SearchInvoiceVO svo);

	public List getCurrentDispute(SearchInvoiceVO svo);

	public long getCurrentDisputeTotalPageNo(SearchInvoiceVO svo);

	public List previous3PaymentAmount(SearchInvoiceVO svo);

	public List previous3DisputeAmount(SearchInvoiceVO svo);

	public String getMinPaymentStatus(SearchInvoiceVO svo);

	public List getCurrentInvoiceHistory(SearchInvoiceVO svo);

	public List getPastThreePaymentPage(SearchInvoiceVO svo);

//	public void handleInvoiceWorkflor(SearchInvoiceVO svo);
	
	public List getCurrentDisputeDownExcel(SearchInvoiceVO svo);
	
	/**
	 * @author Chao.Liu
	 * DownLoad Current Invoice Payment Excel 
	 * @return
	 * @throws Exception
	 */
	public List getManuallyCreatedPaymentObject(SearchInvoiceVO svo);
	public List getMiscPaymentObject(SearchInvoiceVO svo);
	/**
	 * @author Chao.Liu
	 * DownLoad Current Invoice Payment Excel 
	 * @return
	 * @throws Exception
	 */
	public List getCurrentInvoicePaymentObject(SearchInvoiceVO svo);
//	public long getPastThreePaymentPageTotalPageNo(SearchInvoiceVO svo); 

	public String getCurrentInvoice(SearchInvoiceVO svo);

	public String getManuallyCreated(SearchInvoiceVO svo);

	public String getDisputePayback(SearchInvoiceVO svo);

	public String getApplyingCredit(SearchInvoiceVO svo);

	public List<Object[]> getCurrentInvoiceHistoryToExcel(SearchInvoiceVO svo);

	public List<Object[]> getApplyingCreditToExcel(SearchInvoiceVO searchInvoiceVO);

	public List<Object[]> getDisputePaybackToExcel(SearchInvoiceVO searchInvoiceVO);

	public List<Object[]> getPaymentScoaAmountToExcel(
			SearchInvoiceVO searchInvoiceVO);

	public Long getInvoiceCreditBalance(Integer id);

	public Double[] getProposalAmount(Integer id);

	public Integer getAssignmentUserId(Invoice invoice);

	public void updateInvoice(Invoice Id,Integer newStatusId, Integer assignmentUserId,Integer workflowActionId,String message);

	public void setBanProcessStatus(Integer id, String string);
	
	public void updateExternalApproveFlagForCertainInvoice(Integer invoiceId);

//	public void saveHistory(Invoice invoice,Integer invoiceStatusId);

	public List getPaidDisputePayment(SearchInvoiceVO svo);

	public long getPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo);

	public List getShortPaidDisputePayment(SearchInvoiceVO svo);

	public long getShortPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo);

	public List<Object[]> getExcelByPaidDisputePayment(
			SearchInvoiceVO searchInvoiceVO);

	public List<Object[]> getExcelByShortPaidDisputePayment(
			SearchInvoiceVO searchInvoiceVO);

	public Long getUngroupedDisputeCount(Integer id);

	public Long getOutstandingCreditPageTotalNo(SearchInvoiceVO svo);

	public List<String> getOutstandingCreditPageData(SearchInvoiceVO svo);

	public void updateCreditToCurrentPayment(String inProposalIds);

	public List<Proposal> findCredits(String inProposalIds);

	public void cleanBlanceAmount(String inProposalIds);

	public Proposal findCredit(Long proposalId);

	public AccountCode loadAccountCode(Integer accountCodeId);

	public void addBlanceAmount(double d,Integer proposalId);

	public List getNotReconcileDisputePageData(SearchInvoiceVO svo);

	public long getNotReconcileDisputePageTotalNo(SearchInvoiceVO svo);

	public List getTheInvoiceReconciliationPageData(SearchInvoiceVO svo);

	public long getTheInvoiceReconciliationPageTotalNo(SearchInvoiceVO svo);
	
	public Double getInvoicePaymentAmount(Invoice invoice);
	
	public long getBanDisputeDueDate(Integer invoiceId);
	
	public long getInvoiceDisputeStatusCloseAndActionRequestOpenCount(Invoice invoice);

	public Long[] getUnLevelAndNotes(Invoice invoice2);

	public List<Object[]> saveOutstandingCreditPageExcel(
			SearchInvoiceVO searchInvoiceVO);

	public List<Object[]> getNotReconcileDisputePageExcel(
			SearchInvoiceVO searchInvoiceVO);

	public List<Object[]> getTheInvoiceReconciliationPageExcel(
			SearchInvoiceVO searchInvoiceVO);
	public List<Object[]> getMiscDisputePaybackToExcel(SearchInvoiceVO svo);

	public void addOutstandingDisputeSCOA(String inProposalIds, String scoa , String note);
	
	public Integer addEmailApprove(Email email);
	
	public List<Email> searchEmailApprove();
	
	public void updateEmailApprove(Integer newId,Integer emailStatusId);

	public void addOutstandingDisputeSCOAAll(String inProposalId, String scoa, String note);

	public void updateOutstandingCreditSplitApply2(Integer invoiceId,
			Long proposalId, AttachmentPoint point, String notes,
			Double balanceAmount, Integer accountCodeId);

	public void setReconcilePaymentInvoiceId(Invoice invoice);

	public void clearReconcilePaymentInvoiceId(Invoice invoice);

	public void recodeTaxInvoice(String invoiceId);

	public long getCloseAsDisputeLosePageTotalNo(SearchInvoiceVO svo);

	public List getCloseAsDisputeLosePageData(SearchInvoiceVO svo);
	
	public long getCloseAsDisputeWinPageTotalNo(SearchInvoiceVO svo);

	public List getCloseAsDisputeWinPageData(SearchInvoiceVO svo);

	public List<Object[]> getCloseAsDisputeLosePageExcel(SearchInvoiceVO svo);
	
	public List<Object[]> getCloseAsDisputeWinPageExcel(SearchInvoiceVO svo);

	public AttachmentPoint loadAttachmentPoint(String attachmentPointId);
	
	public void updateInvoiceNotes(SearchInvoiceVO svo);
	
	public Integer getInvoiceStatusIdByInvoiceId(String invoiceId);
	
	public Map<String, Object> checkApprovePrivilege(Integer invoiceId) throws SQLException ;
	
	public String showMessages(String messageIds);
	
	public void updateActionRequest(Integer invoiceId);
	
	public void callSpApproveInvoices(Integer invoiceId, Integer workflowActionId, String notes,String oldInvoiceStatusId) throws SQLException;
	
	/**
	 * 查询 validation reference 信息。
	 * @param auditReferenceId
	 * @return
	 */
	public List<Object[]> queryValidationReferenceInfo(Object auditReferenceType,Object auditReferenceId, String auditResultId);
	
	public List<Object[]> getInvoiceValidationToExcel(String invoiceId);

	public List<Object[]> getTelephoneNumberValidationToExcel(String invoiceId);
	
	public Long selectInvoiceValidationByInvoiceIdCount(String invoiceId);
	
	public List<Map<String,Object>> searchInvoiceOriginal(String invoiceId);

	public Long selectTelephoneNumberValidationByInvoiceIdCount(String invoiceId);
	
	public List<Object[]> getAuditStatusDetailList(String invoiceId);
	
	/**
	 * Get autopay error notes.
	 * @param invoiceId
	 * @return
	 */
	public List<Object[]> searchAutopayErrorNotesDBList(String invoiceId);
	
	public Object[] searchAuditMemory(String proposalId);
	
	public Object[] searchAuditMemoryDynamic(Object[] obj);
	
	public String saveMemory(AuditMemory auditMemory);
	
	public String getExternalEmailUser(String id);
		
	public void updateMemory(AuditMemory auditMemory);
	
	public List<Object[]> getSearchProductList(String proposalId);
	
	public List<Object[]> getSearchProductComponentList();
	
	public List<Object[]> getSearchProvinceList();
	
	/**
	 * 检查 当前invoice_id 所属的ban 在 bill_keep_ban 表中 是否存在
	 */
	public Boolean isBillKeepBan(String invoiceId);
	/**
	 * 检查 当前invoice_id 所属的ban 是否为cabs ban
	 */
	public Boolean isCabsCDBan(String invoiceId);
	/**
	 * 根据proposal id 查询item_type_id
	 */
	public String getItemTypeByProposalId(String proposalId);
	
	public List<Object[]> searchBanExemptionDate(String invoiceId);
	
	public Map<String, Object> queryTariffValidationDetail(String referenceId, String referenceTypeId, String auditResultId);
	
	public List<Map<String, Object>> searchOriginalByInvoiceId(Invoice invoice);
	
	public long searchIsUasgeBan(String invoice);

	public List<Object[]> runReportSummary(int invoiceId,int term);
	
	public List<Object[]> runReportDetail(int invoiceId,int term);
	
	public Object[] queryInvoiceDate(int invoiceId);
}
