/**
 * 
 */
package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceChannel;
import com.saninco.ccm.model.InvoiceFormat;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 *
 * add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 * add searchInvoiceAssignment(); beijing 2010-4-16 Jian.Dong
 */
public interface IInvoiceDao {
	
	public Proposal findProposalById(Long id);
	
	public List<AttachmentFile> findAttachmentFileByProperty(String propertyName, Object value);
	
	public void saveAttachmentPoint(AttachmentPoint transientInstance);
	public long getPastDueCount(WorkspaceVO wVO);
	public void saveAttachmentFile(AttachmentFile transientInstance);
	public List<String> searchPastDue(WorkspaceVO wVO);
	public void updateInvoiceProposalPointId(SearchInvoiceVO searchInvoiceVO,String inId);
	public void copyInvoiceProposalPoint(String pastId, String nowId);
	public List<Object[]> searchInvoiceProposalPointByObject(SearchInvoiceVO sio);
	public List<String> searchInvoice(SearchInvoiceVO searchInvoiceVO, int userId);
	public List<Object[]> searchInvoiceByObject(SearchInvoiceVO sio, int userId);
	public long getNumberOfInvoices(SearchInvoiceVO searchInvoiceVO, int userId);
	public long getAssignmentCount(int currentUserId);
	public List<String> searchInvoiceAssignment(SearchInvoiceVO svo);
	public long getInvoiceWorkCount(WorkspaceVO wVO);
	public List<String> searchInvoiceWorkCount(WorkspaceVO wVO);
	public List<String> searchInvoiceRejectWorkCount(WorkspaceVO wVO);
	public void  saveDataByXml(String sqlStr);
	public void  modifyInvoicePreviousAdjustment(SearchInvoiceVO searchInvoiceVO);
	public void  modifyInvoiceMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO);
	public Double  modifyInvoiceItemPreviousAdjustment(SearchInvoiceVO searchInvoiceVO);
	public void  modifyInvoiceItemandProposalProposalFlagIs2Record(SearchInvoiceVO searchInvoiceVO,Double taxTotal);
	public void  modifyInvoiceItemMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO);
	public void  modifyProposalMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO);
	public List<String> searchWorkspase(WorkspaceVO wvo);
	public long getWorkspaseCount(WorkspaceVO wvo);
	/**
	 * @author Chao.Liu
	 * @param searchInvoiceVO
	 * @return
	 * @throws BPLException
	 */
	public List<Object[]> searchInvoiceByIName(SearchInvoiceVO sio);
	
	public List<Object[]> searchInvoiceByInvoiceNumber(SearchInvoiceVO sio);
	/**
	 * Dashboard
	 * @Author Chao.Liu Date: Oct 11, 2010
	 * @param isSystem
	 * @param day
	 * @return
	 */
	public Integer getTimeScaleCount(boolean isSystem, int day);
	// Created By Chao.Liu
	public void save(Object o);
	public Object get(Class c,Integer id);
	public void update(Object o);
	public Invoice findById(java.lang.Integer id);
	public Invoice refreshInvoiceNoCache(Invoice invoice);
	public String getVendorAcronymByInvoiceId(int invoiceId);
	public String getVendorIdByInvoiceId(int invoiceId);
	public List getInvoiceAtTransaction();
	public List getInvoiceAtDashlet();
	public List getUserInvoiceAtDashlet();
	public List getUserInvoiceAtTransaction();
	public Invoice load(Integer invoiceId);
	public Proposal loadProposal(Long disputeId);
	public Proposal getProposal(Long disputeId);
	public void merge(Invoice invoice);
	public List<String> searchWorkspasePaymentInProcess(WorkspaceVO wvo);
	public long getWorkspasePaymentInProcessCount(WorkspaceVO wvo);
	public List<String> searchWorkspaseDisputesInProcess(WorkspaceVO wvo);
	public long getWorkspaseDisputesInProcess(WorkspaceVO wvo);
	public List<String> searchWorkspasePaymentInException(WorkspaceVO wvo);
	public long getWorkspasePaymentInException(WorkspaceVO wvo);
	
	// 检索 External Approve Ban 的账单数
	public long getWorkspaceExternalApproveInvoiceCount(WorkspaceVO wvo);
	
	// 检索 External Approve Ban 中账单的详细信息列表。
	public List<String> listExternalApproveInvoiceItems(WorkspaceVO wvo);
	
	/*
	 * 检索 external approve invoice 相关信息用于生成
	 * Excel 工作表
	 */
	public List<Object[]> getExternalApproveInvoiceData(WorkspaceVO wvo);
	
	
	/**
	 * @Author Chao.liu Sep 25, 2010
	 * @return
	 */
	public List<InvoiceChannel> getInvoiceChannel();
	/**
	 * @Author Chao.liu Sep 25, 2010
	 * @return
	 */
	public List<InvoiceFormat> getInvoiceFormat();
	public void updateHistoryFiles(AttachmentPoint point, Invoice resultInvoice);

	public long getUnknownInvoicesCount(WorkspaceVO rvo);

	public List<String> searchUnknownInvoices(WorkspaceVO wvo);

	public List<Object[]> getMissingInvoicesData(WorkspaceVO wvo);

	public AccountCode loadAccountCode(Integer accountCodeId);
	/**
	 * @Author Donghao.Guo Sep 16, 2011
	 * @return
	 */
	public long getPreloadInvoiceWorkCount(WorkspaceVO wVO);
	
	public long getInvoiceRejectWorkCount(WorkspaceVO wVO);
	/**
	 * @Author Donghao.Guo Sep 16, 2011
	 * @return
	 */
	public List<String> searchWorkspasePreloadInvoice(WorkspaceVO wvo);
	
	public List<Object[]> getPreloadInvoiceData(WorkspaceVO wvo);
	
	public List<Object[]> getDueDaysInvoiceData(WorkspaceVO wvo);
	
	public String removeMissingInvoiceByInvoiceId(Invoice invoice,String missingEmailflag);
	
	public String removeUnknownInvoiceByInvoiceId(Invoice invoice);
	
	public List<Object[]> getUnknownInvoicesData(WorkspaceVO wvo,List<Integer> ids);

	public List<Object[]> findByInvoiceNumber(SearchInvoiceVO id);

	public void paymentResend(String invoiceNumber, String site);
	
	public String updateInvoiceOrGetStatusIdAndDisputeAmountByINumber(SearchInvoiceVO searchInvoiceVO);
	
	public long getNumberOfInvoicesByNumber(SearchInvoiceVO searchInvoiceVO, int userId);
	
	public List<String> searchInvoiceByINumber(SearchInvoiceVO searchInvoiceVO, int userId);
	
	public void closeInvoice(SearchInvoiceVO searchInvoiceVO);

	public void cleanExternalApprovalNotes(String invoiceId);
	
	public void removeInvoice(SearchInvoiceVO searchInvoiceVO);
	
	public String throughAPPaymentClosedInvoice(SearchInvoiceVO searchInvoiceVO);
	
	public String tRegularClosedInvoice(SearchInvoiceVO searchInvoiceVO);

	public void cancelInvoice(SearchInvoiceVO searchInvoiceVO);
	
	public List<String> searchInvoiceLabelsByLabelId(String labelIds);
	
	public List<Object[]> searchInvoiceLabelsByInvoiceId(String invoiceId);

	public List<HashMap<String, String>> getOriginalListByInvoiceId(int invoiceId);

	public HashMap<String, String> getOriginalDetail(int originalId);
	
	public Object[] searchInvoiceAuditStatus(String invoiceId);
	
	public String searchInvoiceAuditForSourceBeginning(String invoiceId,String sourceBeginning);
	
	public String searchBanAuditFlag(String invoiceId);
	
	public Long searchBanExemption(String invoiceId);
	
	public Long searchBanOpenAutoPay(String invoiceId);
	
	public List<Object[]> queryCBanUsageReportList(String invoiceId);
	
	public List<Object[]> queryDBanUsageReportList(String invoiceId,String type);
	
	public List<Integer> queryAllUsageInvoice();
	
	public long querySystemUploadByInvoiceId(Integer invoiceId);
	
}
