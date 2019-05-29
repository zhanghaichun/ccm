/**
 * 
 */
package com.saninco.ccm.service.dispute;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.dao.IAttachmentFileDao;
import com.saninco.ccm.dao.IContactDao;
import com.saninco.ccm.dao.ICreditDao;
import com.saninco.ccm.dao.IDisputeDao;
import com.saninco.ccm.dao.IEmailDao;
import com.saninco.ccm.dao.IInvoiceDao;
import com.saninco.ccm.dao.IInvoiceDetailDao;
import com.saninco.ccm.dao.IProposalDao;
import com.saninco.ccm.dao.IReconcileDao;
import com.saninco.ccm.dao.IReconcileStatusDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Contact;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.DisputeStatus;
import com.saninco.ccm.model.Email;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.model.ReconcileStatus;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.WorkflowAction;
import com.saninco.ccm.util.BlobUtil;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.FileUtil;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemEmailConfig;
import com.saninco.ccm.util.SystemUtil;
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
public class DisputeServiceImpl implements IDisputeService {
	private final Logger logger = Logger.getLogger(this.getClass());

	private IInvoiceDao invoiceDao;
	private IDisputeDao disputeDao;
	private ICreditDao creditDao;
	private IReconcileDao reconcileDao;
	private IReconcileStatusDao reconcileStatusDao;
	private IInvoiceDetailDao invoiceDetailDao;
	private IUserDao userDao;
	private IContactDao contactDao;
	private IAttachmentFileDao attachmentFileDao;
	private ISysConfigDAO sysConfigDAO;
	private IProposalDao proposalDao;
	private IEmailDao emailDao;

	private static int DOWNLOAD_EXCEL_RECPERPAGE = 10000;
	private static int DOWNLOAD_EXCEL_MAXSIZE = 60000;

	public void recalculateDisputeAmountAndDisputeBalance(String disputeId){
		logger.info(CcmFormat.formatEnterLog("Update dispute table Balance Amount And dispute Amount", disputeId));
		try{
			disputeDao.recalculateDisputeAmountAndDisputeBalance(disputeId);
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewDisputeDetails.js)
	 * 
	 * According to disputeId query table summary proposal Balance Amount, and updates to the dispute table
	 * 
	 */
	public void updateDisputeBalance(String disputeId){
		logger.info(CcmFormat.formatEnterLog("Update dispute table Balance Amount", disputeId));
		try{
			disputeDao.updateDisputeBalanceByProposal(disputeId);
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Add to reconcile accountCode
	 */
	public void addReconcileSCOA(SearchDisputeVO searchDisputeVO){
		logger.info(CcmFormat.formatEnterLog("add Reconcile SCOA", searchDisputeVO));
		Reconcile reconcile = new Reconcile();
		AccountCode accountCode = new AccountCode();
		try{
			reconcile = disputeDao.findReconcileById(searchDisputeVO.getReconcileId());
			accountCode = disputeDao.findAccountCodeById(Integer.parseInt(searchDisputeVO.getAccountCodeId()));
			reconcile.setAccountCode(accountCode);
			reconcile.setModifiedBy(SystemUtil.getCurrentUserId());
			reconcile.setModifiedTimestamp(new Date());
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * add OutstandingDisputeItems SCOA
	 */
	public void addOutstandingDisputeItemsSCOA(SearchDisputeVO searchDisputeVO) {
		logger.info(CcmFormat.formatEnterLog("add OutstandingDisputeItems SCOA", searchDisputeVO));
		try {
			searchDisputeVO.setBoxId(getBoxInId(searchDisputeVO.getBoxId()));
			disputeDao.updateSCOACoingByProposal(searchDisputeVO,SystemUtil.getCurrentUserId());
			disputeDao.updateDisputeTimestamp(searchDisputeVO.getDisputeId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * search Transaction History View List (viewDisputeDetails.js)
	 * @author xinyu.Liu
	 *  
	 */
	public String searchTransactionHistory(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Transaction History View List", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = disputeDao.searchTransactionHistorys(searchDisputeVO);
			sb.append(searchDisputeVO.getListJsonCompatibleByAll(l));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * search Dispute Notes View List (viewDisputeDetails.js)
	 *  
	 */
	public String searchDisputeNotesList(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute Notes List", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> l = null;
		try {
			l = disputeDao.searchDisputeNotesList(searchDisputeVO);
			sb.append(getListJsonCompatibleByDisputeNotes(l,searchDisputeVO));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	
	private String getListJsonCompatibleByDisputeNotes(List<Map<String,Object>> list,SearchDisputeVO searchDisputeVO) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(searchDisputeVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(searchDisputeVO.getStartIndex() + size);
			sb.append(" ,data:[");
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> ll = list.get(i);
				String date = DaoUtil.ccmEscape4((String)ll.get("date"));
				String note = DaoUtil.ccmEscape4((String)ll.get("note"));
				String name = DaoUtil.ccmEscape4((String)ll.get("name"));
							
				Object str2 = "{id:"+ ll.get("id")+",date:\""+date+"\",note:\""+note+"\",name:\""+name+"\"}";
				if (str2 instanceof Blob) {
					str2 = searchDisputeVO.getBlobString((Blob) str2);
				} else if (str2 instanceof String) {
					// String is defult
				} else {
					throw new Exception("This type is not compatible");
				}
				if (i != 0)
					sb.append(",");
				sb.append(str2.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}
	private String getListJsonCompatibleByDisputeActionRequest(List<Map<String,Object>> list,SearchDisputeVO searchDisputeVO) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(searchDisputeVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(searchDisputeVO.getStartIndex() + size);
			sb.append(" ,data:[");
			String disputeId = "";
			String disputeNotes = "";
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> ll = list.get(i);
				String disputeNumber = (String)ll.get("disputeNumber");
				String notes = (String)ll.get("notes");
				if(ll.get("disputeId") != null) {
					 disputeId = ll.get("disputeId").toString();
				}			
				String name = DaoUtil.ccmEscape4((String)ll.get("name"));
				String createdTimestamp = (String)ll.get("createdTimestamp");
				String status = (String)ll.get("status");
				
				if(ll.get("disputeNotes") != null) {
					disputeNotes = DaoUtil.ccmEscape4((String)ll.get("disputeNotes").toString());
				}
				String replyCount = ll.get("replyCount").toString();
				String userId = ll.get("userId").toString();
							
				Object str2 = "{id:"+ ll.get("id")+",disputeNumber:\""+disputeNumber+"\",disputeId:\""+disputeId+"\",disputeNotes:\""+disputeNotes+"\",name:\""+name+"\",createdTimestamp:\""+createdTimestamp+"\",status:\""+status+"\",notes:\""+notes+"\",replyCount:\""+replyCount+"\",userId:\""+userId+"\"}";
				if (str2 instanceof Blob) {
					str2 = searchDisputeVO.getBlobString((Blob) str2);
				} else if (str2 instanceof String) {
					// String is defult
				} else {
					throw new Exception("This type is not compatible");
				}
				if (i != 0)
					sb.append(",");
				sb.append(str2.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}
	
	
	
	public String getDisputeNotesListPageNo(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute Notes List Page No", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getDisputeNotesListPageNo(searchDisputeVO);
			searchDisputeVO.getTotalPageNoJson(count);
			sb.append(searchDisputeVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public void saveDisputeNote(String disputeId,String noteNotes) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("save Dispute Note"));
		try {
			disputeDao.saveDisputeNote(disputeId,noteNotes);
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}

	/**
	 * 保存 Dispute Action Request.
	 * @param actionRequestNotes [description]
	 * @param disputeId          [description]
	 */
	public void saveDisputeActionRequest(String actionRequestNotes, String disputeIds,String disputeActionRequestStatus) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Save Dispute Action Request."));
		
		try {
			String [] disputeId = disputeIds.split("&");
			for(int i = 0; i< disputeId.length; i++){
				disputeDao.saveDisputeActionRequest(actionRequestNotes, disputeId[i],disputeActionRequestStatus);
			}
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
	
		}
	}
	
	public String getDisputeActionRequestListPageNo(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List Page No", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getDisputeActionRequestListPageNo(searchDisputeVO);
			searchDisputeVO.getTotalPageNoJson(count);
			sb.append(searchDisputeVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getDisputeActionRequestListPageNoByBanId(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List Page No", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getDisputeActionRequestListPageNoByBanId(searchDisputeVO);
			searchDisputeVO.getTotalPageNoJson(count);
			sb.append(searchDisputeVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String searchDisputeReplyList(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute Reply List", searchDisputeVO));
		List<Map<String,Object>> list = null;
		StringBuilder sb = new StringBuilder();
		try {
			list = disputeDao.searchDisputeReplyList(searchDisputeVO.getDisputeActionRequestId());

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == null) {
					list.remove(i);
					i--;
				}
			}

			if (list != null && list.size() > 0) {
				sb.append("{data:[");
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> ll = list.get(i);
					String name = DaoUtil.ccmEscape4((String)ll.get("name"));
					String date = (String)ll.get("date").toString();
					String notes = null;
					if(ll.get("notes")!= null) {
						 notes = DaoUtil.ccmEscape4((String)ll.get("notes").toString());
					}
					
								
					Object str2 = "{id:"+ ll.get("id")+",disputeActionRequestId:\""+ll.get("dispute_action_request_id")+"\",name:\""+name+"\",date:\""+date+"\",notes:\""+notes+"\"}";

					if (i != 0)
						sb.append(",");
					sb.append(str2.toString());
				}
				sb.append("]");
			} else {
				sb.append("{count:0");
			}
			sb.append("}");

		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public void deleteDisputeReply(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("delete Dispute Reply", searchDisputeVO));
		try {
			disputeDao.deleteDisputeReply(searchDisputeVO.getDisputeActionRequestReplyId());

		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	public void addReplyNote(SearchDisputeVO searchDisputeVO,String note) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("add Reply Note", searchDisputeVO));
		try {
			disputeDao.addReplyNote(searchDisputeVO.getDisputeActionRequestId(), DaoUtil.ccmEscape2(note));

		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	public void updateDisputeActionRequestStatus(SearchDisputeVO searchDisputeVO,String status) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update DisputeActionRequest Status", searchDisputeVO));
		try {
			Long disputeActionRequestStatusId = disputeDao.getDisputeActionRequestStatusById(searchDisputeVO.getDisputeActionRequestId());
			if(disputeActionRequestStatusId != 3){
				disputeDao.updateDisputeActionRequestStatus(searchDisputeVO.getDisputeActionRequestId(), status);
			}else{
				BPLException bpe = new BPLException("Action Request is now completed.");
				throw bpe;
			}

		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	
	public String searchDisputeActionRequestList(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> l = null;
		try {
			l = disputeDao.searchDisputeActionRequestList(searchDisputeVO);
			sb.append(getListJsonCompatibleByDisputeActionRequest(l,searchDisputeVO));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String searchDisputeActionRequestListByBanId(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> l = null;
		try {
			l = disputeDao.searchDisputeActionRequestListByBanId(searchDisputeVO);
			sb.append(getListJsonCompatibleByDisputeActionRequest(l,searchDisputeVO));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	
	/**
	 * Download DisputeItem To Excel 
	 */
	public String getExcelByDisputeItem(SearchDisputeVO sdo, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Download DisputeItem To Excel", sdo, excelDirPath, titles));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int pageNumber = 0;
			count = disputeDao.getNumberOfDisputeItems(sdo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(pageNumber, "DisputeItem" + pageNumber);
			jExcelUtil.writeTitle(0, traverseRemovalList(titles));

			if (count <= DOWNLOAD_EXCEL_RECPERPAGE) {
				sdo.setPageNo(1);
				sdo.setRecPerPage((int) count);
				list = disputeDao.searchDisputeItemByObject(sdo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					sdo.setPageNo(j + 1);
					sdo.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					list = disputeDao.searchDisputeItemByObject(sdo);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					if (pageNumber > 0) {
						if (((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0) {
							jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 40, 40, 30, 30, 30, 30, 50, 30, 30, 30, 30, 30, 30, 30,
									30, 30, 30, 30, 30, 30 });
							jExcelUtil.createWritableSheet(pageNumber, "DisputeItem" + pageNumber);
							jExcelUtil.setWritableSheet(pageNumber);
						}
						for (int i = 0; i < list.size(); i++) {
							jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + i) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), list.get(i));
						}
					} else {
						for (int i = 0; i < list.size(); i++) {
							jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1, list.get(i));
						}
					}
				}
			}

			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 53, 30, 40, 40, 30, 30, 30, 30, 50, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
					30, 30 });
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}

	/**
	 * Traverse the list
	 * 
	 */
	private List<String> traverseRemovalList(List<String> titles) {
		for (int i = 0; i < titles.size(); i++) {
			if ("Edit".equals(titles.get(i)) || "Download".equals(titles.get(i))) {
				titles.remove(i);
			}
		}
		return titles;
	}

	/**
	 * update Dispute Attachment File
	 */
	public void updateDisputeAttachmentFile(SearchDisputeVO searchDisputeVO){
		logger.info(CcmFormat.formatEnterLog("update Dispute Attachment File",searchDisputeVO));
		AttachmentFile attachmentFile = new AttachmentFile();
		try {
			attachmentFile = disputeDao.findAttachmentFileById(Integer.parseInt(searchDisputeVO.getAttachmentFileId()));
			attachmentFile.setModifiedBy(SystemUtil.getCurrentUserId());
			attachmentFile.setModifiedTimestamp(new Date());
			attachmentFile.setRecActiveFlag("N");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * update Cancel Refile Dispute and add Transaction History (viewDisputeReconciliation.js)
	 * 
	 * @author xinyu.Liu
	 */
	public void updateCancelDispute(SearchDisputeVO searchDisputeVO){
		logger.info(CcmFormat.formatEnterLog("update Cancel Refile Dispute and add Transaction History ",searchDisputeVO));
		Dispute dispute = new Dispute();
		DisputeStatus disputeStatus = new DisputeStatus();
		WorkflowAction workflowAction = new WorkflowAction();
		TransactionHistory transactionHistory = new TransactionHistory();
		User user = new User();
		try {
			dispute = disputeDao.findDisputeById(searchDisputeVO.getDisputeId());
			disputeStatus = disputeDao.findDisputeStatusById(30);
			workflowAction = disputeDao.findWorkflowActionById(4);//Update
			user = disputeDao.findUserById(SystemUtil.getCurrentUserId());
				
			dispute.setDisputeStatus(disputeStatus);
			dispute.setNotes(searchDisputeVO.getNotes());
			dispute.setModifiedBy(SystemUtil.getCurrentUserId());
			dispute.setModifiedTimestamp(new Date());
			
			//add transaction History
			transactionHistory.setInvoice(dispute.getInvoice());
			transactionHistory.setDispute(dispute);
			transactionHistory.setInvoiceStatus(dispute.getInvoice().getInvoiceStatus());
			transactionHistory.setDisputeStatus(disputeStatus);
			transactionHistory.setUser(user);
			transactionHistory.setWorkflowAction(workflowAction);
			transactionHistory.setNotes(searchDisputeVO.getNotes());
			transactionHistory.setAttachmentPoint(dispute.getAttachmentPoint());
			transactionHistory.setDisputeAmount(dispute.getDisputeBalance());
			transactionHistory.setRecActiveFlag("Y");
			transactionHistory.setCreatedBy(SystemUtil.getCurrentUserId());
			transactionHistory.setCreatedTimestamp(new Date());
			disputeDao.saveTransactionHistory(transactionHistory);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * update Dispute and add Transaction History (viewDisputeReconciliation.js)
	 * 
	 * @author xinyu.Liu
	 */
	public void updateDispute(SearchDisputeVO searchDisputeVO){
		logger.info(CcmFormat.formatEnterLog("update Dispute And add Transation History ",searchDisputeVO));
		try {
			Dispute dispute = disputeDao.findDisputeById(searchDisputeVO.getDisputeId());
			
			TransactionHistory transactionHistory = new TransactionHistory();
			transactionHistory.setInvoice(dispute.getInvoice());
			transactionHistory.setDispute(dispute);
			transactionHistory.setInvoiceStatus(dispute.getInvoice().getInvoiceStatus());
			transactionHistory.setDisputeStatus(dispute.getDisputeStatus());
			transactionHistory.setUser(SystemUtil.getCurrentUser());
			transactionHistory.setWorkflowAction(disputeDao.loadWorkflowActionById(6));
			transactionHistory.setNotes(dispute.getNotes());
			transactionHistory.setAttachmentPoint(dispute.getAttachmentPoint());
			transactionHistory.setDisputeAmount(dispute.getDisputeBalance());
			transactionHistory.setRecActiveFlag("Y");
			transactionHistory.setCreatedBy(SystemUtil.getCurrentUserId());
			transactionHistory.setCreatedTimestamp(new Date());
			disputeDao.saveTransactionHistory(transactionHistory);
			
			dispute.setDisputeStatus(disputeDao.loadDisputeStatusById(34));
			dispute.setStatusTimestamp(new Date());
			if(searchDisputeVO.getNotes() != null && !"".equals(searchDisputeVO.getNotes().trim())){
				dispute.setNotes(searchDisputeVO.getNotes());
			}
			dispute.setModifiedBy(SystemUtil.getCurrentUserId());
			dispute.setModifiedTimestamp(new Date());
			disputeDao.merge(dispute);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * 
	 * @author dongjian 
	 */
	public void updateRefileDisputeByProposal(String ids) {
		logger.info("Enter method updateRefileDisputeByProposal");
		try {
			disputeDao.updateRefileDisputeByProposal(ids);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		logger.info("Exit method updateRefileDisputeByProposal.");
	}

	/**
	 * upodate Dispute Attachment Point
	 */
	public void upodateDisputeAttachmentPoint(Integer disputeId, AttachmentPoint attachmentPoint){
		logger.info(CcmFormat.formatEnterLog("upodate Dispute Attachment Point", attachmentPoint));
		Dispute dispute = new Dispute();
		try {
			dispute = disputeDao.findDisputeById(disputeId);
			dispute.setAttachmentPoint(attachmentPoint);
			dispute.setModifiedBy(SystemUtil.getCurrentUserId());
			dispute.setModifiedTimestamp(new Date());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Find AttachmentPoint Object 
	 */
	public AttachmentPoint findAttachmentPoint(Integer attachmentPointId){
		logger.info(CcmFormat.formatEnterLog("Find AttachmentPoint Object", attachmentPointId));
		AttachmentPoint attachmentPoint = new AttachmentPoint();
		try {
			attachmentPoint = disputeDao.findAttachmentPointById(attachmentPointId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return attachmentPoint;
	}
	
	/**
	 * @author jian.Dong
	 * xxxxxxxxxxxxxxxxxxxx
	 * @param disputeAmount 
	 * */
	private Double getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(Proposal p , Double reconcilemount, Dispute d , Boolean isDisputeFlag){
		if(d == null){
			return 0d;
		}else{
			
			double proposalBalanceAmount = p.getBalanceAmount();
			
			if(proposalBalanceAmount*reconcilemount < 0 || Math.abs(proposalBalanceAmount) < Math.abs(reconcilemount)){
				throw new RuntimeException("Outstanding balance amount calculation error.");
			}
			
//			double beforeOutstandingReserveAmount = (d.getOutstandingReserveAmount() == null ? 0.0D : d.getOutstandingReserveAmount());
			double disputeBalance = disputeDao.getBalanceAmountByProposal(d.getId());
//			double beforeDisputeBalance = disputeDao.getBalanceAmountByProposal(d.getId());
			double outstandingReserveAmount = (d.getOutstandingReserveAmount() == null ? 0.0D : d.getOutstandingReserveAmount());
			BigDecimal b1 = new BigDecimal(outstandingReserveAmount);
			outstandingReserveAmount = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double result = ((disputeBalance==0 && outstandingReserveAmount == 0) ? reconcilemount : reconcilemount * (outstandingReserveAmount/disputeBalance));
			BigDecimal b = new BigDecimal(result);
			result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
			
			double outResult = 0;
			
			if(isDisputeFlag){
				d.setDisputeBalance(disputeBalance - reconcilemount);
				outResult = outstandingReserveAmount - result;
				
//				if(Math.abs(disputeBalance) < Math.abs(reconcilemount)){
//					throw new RuntimeException("Outstanding balance amount calculation error.");
//				}
				
			}else{
				d.setDisputeBalance(disputeBalance + reconcilemount);
				outResult = outstandingReserveAmount + result;
			}
			
			d.setOutstandingReserveAmount(outResult);
			
//			if(Math.abs(beforeDisputeBalance) > 0 && Math.abs(beforeDisputeBalance) < Math.abs(d.getDisputeBalance())){
//				throw new RuntimeException("Outstanding balance amount calculation error.");
//			}
//			
//			if(Math.abs(beforeOutstandingReserveAmount) > 0 && Math.abs(beforeOutstandingReserveAmount) < Math.abs(d.getOutstandingReserveAmount())){
//				throw new RuntimeException("Outstanding reserve amount calculation error.");
//			}
			return result;
		}
		
	}
	/**
	 * update Dispute Split Close Dispute
	 */
	public void updateDisputeSplitCloseDispute(SearchDisputeVO sdo){
		logger.info(CcmFormat.formatEnterLog("update Dispute Split Close Dispute", sdo));
		try {
			sdo.setBoxId(getBoxInId(sdo.getBoxId()));
			Double ra = new Double((sdo.getBalanceAmount() == null || "".equals(sdo.getBalanceAmount())) ? "0" : sdo.getBalanceAmount());
			List<Proposal> ps = invoiceDetailDao.findCredits(sdo.getBoxId());
			for(Proposal p : ps){
				Dispute d = p.getDispute();
				Reconcile r = new Reconcile();
				if(sdo.getAccountCodeId()!=null && !"".equals(sdo.getAccountCodeId())){
					r.setAccountCode(invoiceDetailDao.loadAccountCode(Integer.parseInt(sdo.getAccountCodeId())));
				}else{
					r.setAccountCode(p.getAccountCode());
				}
				r.setDisputeProposal(p);
				r.setDispute(d);
				r.setReconcileAmount(ra);
				r.setAttachmentPoint(invoiceDetailDao.loadAttachmentPoint(sdo.getAttachmentPointId()));
				r.setCreatedBy(SystemUtil.getCurrentUserId());
				r.setCreatedTimestamp(new Date());
				r.setModifiedBy(r.getCreatedBy());
				r.setModifiedTimestamp(r.getCreatedTimestamp());
				r.setNotes(sdo.getNotes());
				r.setRecActiveFlag("Y");
				r.setReconcileDate(new Date());
				r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(p,ra, d,true));
				
				Integer reconcileStatusId = 0;
				if ("Win".equals(sdo.getBo())){
					if("Y".equals(d == null ? "N" : d.getFlagShortpaid())){
						reconcileStatusId = 8;
					}else{
						reconcileStatusId = 6;
					}
				}else{
					if("Y".equals(d == null ? "N" : d.getFlagShortpaid())){
						reconcileStatusId = 7;
					}else{
						reconcileStatusId = 5;
					}
				}
				r.setReconcileStatus(reconcileStatusDao.load(reconcileStatusId));
				
				p.setBalanceAmount((p.getBalanceAmount() == null ? 0.0D : p.getBalanceAmount()) - ra);
				p.setModifiedBy(SystemUtil.getCurrentUserId());
				p.setModifiedTimestamp(new Date());
				
				d.setModifiedBy(SystemUtil.getCurrentUserId());
				d.setModifiedTimestamp(new Date());
				
				reconcileDao.save(r);
				proposalDao.merge(p);
				disputeDao.merge(d);
				proposalDao.commit();
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(e.getMessage());
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	public void updateTransactionHistory(SearchDisputeVO searchDisputeVO, AttachmentPoint attachmentPoint){
		logger.info(CcmFormat.formatEnterLog("update Transaction History", searchDisputeVO, attachmentPoint));
		Invoice invoice = new Invoice();
		Dispute dispute = new Dispute();
		User user = new User();
		WorkflowAction workflowAction = new WorkflowAction();
		TransactionHistory transactionHistory = new TransactionHistory();

		try {
			invoice = disputeDao.findInvoiceById(Integer.parseInt(searchDisputeVO.getInvoiceId()));
			dispute = disputeDao.findDisputeById(searchDisputeVO.getDisputeId());
			workflowAction = disputeDao.loadWorkflowActionById(4);
			user = disputeDao.findUserById(SystemUtil.getCurrentUserId());
	
			//add transaction History
			transactionHistory.setInvoice(invoice);
			transactionHistory.setDispute(dispute);
			transactionHistory.setInvoiceStatus(invoice.getInvoiceStatus());
			transactionHistory.setInternalInvoiceStatus(invoice.getInternalInvoiceStatus());
			transactionHistory.setDisputeStatus(dispute.getDisputeStatus());
			transactionHistory.setWorkflowAction(workflowAction);
			transactionHistory.setUser(user);
			transactionHistory.setDisputeAmount(dispute.getDisputeAmount());
			transactionHistory.setNotes(searchDisputeVO.getNotes());
			transactionHistory.setAttachmentPoint(attachmentPoint);
			transactionHistory.setCreatedBy(SystemUtil.getCurrentUserId());
			transactionHistory.setCreatedTimestamp(new Date());
			transactionHistory.setRecActiveFlag("Y");
			dispute.setModifiedBy(0);
			dispute.setModifiedTimestamp(new Date());
			disputeDao.saveTransactionHistory(transactionHistory);

			if (searchDisputeVO.getTicketNumber() != null && !"".equals(searchDisputeVO.getTicketNumber())) {
				dispute.setTicketNumber(searchDisputeVO.getTicketNumber());
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * update Dispute close Dispute Lose
	 * 
	 */
	public void updateDisputeCloseDisputeLose(SearchDisputeVO sdo){
		logger.info(CcmFormat.formatEnterLog("update Dispute Close Dispute Lose", sdo));
		try {
			sdo.setBoxId(getBoxInId(sdo.getBoxId()));
			List<Proposal> ps = invoiceDetailDao.findCredits(sdo.getBoxId());
			for(Proposal p : ps){
				Dispute d = p.getDispute();
				Reconcile r = new Reconcile();
				r.setDisputeProposal(p);
				r.setDispute(d);
				r.setReconcileAmount(p.getBalanceAmount());
				if(sdo.getAccountCodeId()!=null && !"".equals(sdo.getAccountCodeId())){
					r.setAccountCode(invoiceDetailDao.loadAccountCode(Integer.parseInt(sdo.getAccountCodeId())));
				}else{
					r.setAccountCode(p.getAccountCode());
				}
				r.setNotes(sdo.getNotes());
				r.setAttachmentPoint(invoiceDetailDao.loadAttachmentPoint(sdo.getAttachmentPointId()));
				r.setCreatedBy(SystemUtil.getCurrentUserId());
				r.setCreatedTimestamp(new Date());
				r.setModifiedBy(r.getCreatedBy());
				r.setModifiedTimestamp(r.getCreatedTimestamp());
				r.setRecActiveFlag("Y");
				r.setReconcileDate(new Date());
				r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(p,p.getBalanceAmount() == null ? 0.0D : p.getBalanceAmount(), d,true));
				
				Integer reconcileStatusId = 0;
				if("Y".equals(d == null ? "N" : d.getFlagShortpaid())){
					reconcileStatusId = 7;
				}else{
					reconcileStatusId = 5;
				}
				r.setReconcileStatus(reconcileStatusDao.load(reconcileStatusId));
				
				p.setBalanceAmount(0d);
				p.setModifiedBy(SystemUtil.getCurrentUserId());
				p.setModifiedTimestamp(new Date());
				
				d.setModifiedBy(SystemUtil.getCurrentUserId());
				d.setModifiedTimestamp(new Date());
				
				reconcileDao.save(r);
				proposalDao.merge(p);
				disputeDao.merge(d);
				proposalDao.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(e.getMessage());
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * update Dispute close Dispute Win
	 * 
	 */
	public void updateDisputeCloseDisputeWin(SearchDisputeVO sdo){
		logger.info(CcmFormat.formatEnterLog("update Dispute Close Dispute Win", sdo));
		try {
			sdo.setBoxId(getBoxInId(sdo.getBoxId()));
			List<Proposal> ps = invoiceDetailDao.findCredits(sdo.getBoxId());
			for(Proposal p : ps){
				Dispute d = p.getDispute();
				Reconcile r = new Reconcile();
				r.setDisputeProposal(p);
				r.setDispute(d);
				r.setReconcileAmount(p.getBalanceAmount());
				if(sdo.getAccountCodeId()!=null && !"".equals(sdo.getAccountCodeId())){
					r.setAccountCode(invoiceDetailDao.loadAccountCode(Integer.parseInt(sdo.getAccountCodeId())));
				}else{
					r.setAccountCode(p.getAccountCode());
				}
				r.setNotes(sdo.getNotes());
				r.setAttachmentPoint(invoiceDetailDao.loadAttachmentPoint(sdo.getAttachmentPointId()));
				r.setCreatedBy(SystemUtil.getCurrentUserId());
				r.setCreatedTimestamp(new Date());
				r.setModifiedBy(r.getCreatedBy());
				r.setModifiedTimestamp(r.getCreatedTimestamp());
				r.setRecActiveFlag("Y");
				r.setReconcileDate(new Date());
				r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(p,p.getBalanceAmount() == null ? 0.0D : p.getBalanceAmount(), d,true));
				
				Integer reconcileStatusId = 0;
				if("Y".equals(d == null ? "N" : d.getFlagShortpaid())){
					reconcileStatusId = 8;
				}else{
					reconcileStatusId = 6;
				}
				r.setReconcileStatus(reconcileStatusDao.load(reconcileStatusId));
				
				p.setBalanceAmount(0d);
				p.setModifiedBy(SystemUtil.getCurrentUserId());
				p.setModifiedTimestamp(new Date());
				
				d.setModifiedBy(SystemUtil.getCurrentUserId());
				d.setModifiedTimestamp(new Date());
				
				reconcileDao.save(r);
				proposalDao.merge(p);
				disputeDao.merge(d);
				proposalDao.commit();
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * Remove the extra comma
	 * 
	 */
	private String getBoxInId(String boxInId) throws BPLException {
		StringBuffer inIdString = new StringBuffer();
		String[] inId = boxInId.split(",");
		try {
			for (int i = 0; i < inId.length; i++) {
				if (inId[i] != null && !"".equals(inId[i])) {
					inIdString.append(inId[i]);
					if (i + 1 < inId.length) {
						inIdString.append(",");
					}
				}
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return inIdString.toString();
	}

	/**
	 * update Reconciliation
	 * 
	 */
	public String updateReconciliationMany(String ids){
		logger.info("Enter Method updateReconciliationMany.");
		String[] ids_ = ids.split(",");
		String result = "";
		int closeError = 0;
		for(String id : ids_){
			result = updateReconciliation(Integer.parseInt(id),-1);
			if("error".equals(result)){
				closeError = closeError+1;
			}
			
		}
		result = "{message:\""+(ids_.length - closeError)+" disputes deleted successful!\"}";
		//result = "{message:\""+(ids_.length - closeError)+" disputes deleted successful, "+closeError+" disputes close failure!\"}";
		logger.info("Exit Method updateReconciliationMany.");
		return result;
	}

	/**
	 * update Reconciliation
	 * 
	 */
	public String updateReconciliation(int reconcileId,int count){
		Reconcile reconcile = null;
		Proposal creditProposal = null;
		Proposal disputeProposal = null;
		try {
			
			reconcile = reconcileDao.findById(reconcileId);
			Integer disputeStatusId = reconcile.getDispute().getDisputeStatus().getId();
			if(disputeStatusId.intValue() == 99){
				if(count==1){
					return "{validError:\"This dispute is closed, can't be deleted.\"}";
				}else{
					return "error";
				}
			}
			Double reconcileAmount = (reconcile.getReconcileAmount() == null ? 0.0D : reconcile.getReconcileAmount());

			creditProposal = reconcile.getCreditProposal();
			if (creditProposal != null) {
				creditProposal.setBalanceAmount((creditProposal.getBalanceAmount() == null ? 0.0D : creditProposal.getBalanceAmount()) - reconcileAmount);
				proposalDao.merge(creditProposal);
			}
			disputeProposal = reconcile.getDisputeProposal();
			if(disputeProposal != null){
				disputeProposal.setBalanceAmount((disputeProposal.getBalanceAmount() == null ? 0.0D : disputeProposal.getBalanceAmount()) + reconcileAmount);
				proposalDao.merge(disputeProposal);
			}
			
			Dispute d = (disputeProposal == null || disputeProposal.getDispute() == null) ? reconcile.getDispute() : disputeProposal.getDispute();
			double disputeBalance = disputeDao.getBalanceAmountByProposal(d.getId());
			if(d != null){
				Double outstandingReserveAmount = d.getOutstandingReserveAmount() == null ? 0.0D : d.getOutstandingReserveAmount();
				Double releasedReserveAmount = reconcile.getReleasedReserveAmount() == null ? 0.0D : reconcile.getReleasedReserveAmount();
				d.setOutstandingReserveAmount(outstandingReserveAmount+releasedReserveAmount);
				d.setDisputeBalance(disputeBalance+reconcileAmount);
				d.setModifiedBy(SystemUtil.getCurrentUserId());
				d.setModifiedTimestamp(new Date());
				disputeDao.merge(d);
			}
			
			reconcile.setRecActiveFlag("N");
			reconcileDao.merge(reconcile);
			if(count==1){
				return "{success:\"\"}";
			}else {
				return "success";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}

	/**
	 * search Dispute Reconciliation View List
	 */
	public String searchDisputeReconciliation(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute Reconciliation View List", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = disputeDao.searchDisputeReconciliation(searchDisputeVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchDisputeVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchDisputeVO.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append((str != null ? str.toString() : ""));
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * get Dispute Reconciliation List Page Total No
	 */
	public String getDisputeReconciliationListPageTotalNo(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Reconciliation List Page Total No", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getNumberOfReconciliations(searchDisputeVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) searchDisputeVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * search Dispute Item View List
	 */
	public String searchDisputeItem(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute Item View List", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = disputeDao.searchDisputeItem(searchDisputeVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchDisputeVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchDisputeVO.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * search Dispute Message View List
	 */
	public List<Map<String,Object>> searchDisputeMessageByDisputeIds(String disputeIds) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute Message View List", disputeIds));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String[] sList; 
		String attachmentPointId = null;
		try {
			
			sList = disputeIds.split(",");
			if (sList != null && sList.length > 0) {
				for(int i = 0 ; i < sList.length ; i ++ ){
					String disputeId = sList[i];
					Map<String,Object> map = new HashMap<String,Object>();
					Dispute dispute = this.disputeDao.searchDisputeById(disputeId);
					map.put("dispute", dispute);
					map.put("user", userDao.findById(SystemUtil.getCurrentUserId()));
					map.put("defaultEmailTo", disputeDao.searchDisputeEmailAddress(dispute.getInvoice().getBan().getBanDisputeContactId()));
					map.put("defaultEmailMessage", this.getDefaultEmailMessage(dispute));
					List<Map<String,Object>> disputeMsgList = this.disputeDao.searchDisputeMessage(disputeId);
					if(dispute.getAttachmentPoint() != null) {
						attachmentPointId = dispute.getAttachmentPoint().getId().toString();
					}
					
					for (int j = 0 ; j < disputeMsgList.size() ; j ++ ){		
						if(disputeMsgList.get(j).get("attachment_point_id") != null) {
							attachmentPointId = attachmentPointId + ","+disputeMsgList.get(j).get("attachment_point_id");
						}
						
					}
					map.put("disputeMsgList", disputeMsgList);
					if(attachmentPointId != null) {
						map.put("attachmentFileList", FileUtil.deduplicationFile(this.disputeDao.searchDisputeAttachmentFile(attachmentPointId)));
					}
					
					list.add(map);
					
				}
			}
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info(CcmFormat.formatExitLog());
		return list;
	}
	
	public Map<String,Object> searchContactVendorByDisputeIds(String disputeId) throws Exception{
		logger.info(CcmFormat.formatEnterLog("search Dispute Contact Vendor", disputeId));
		Map<String,Object> map = new HashMap<String,Object>();
		try {
					Dispute dispute = this.disputeDao.searchDisputeById(disputeId);
					map.put("dispute", dispute);
					map.put("emailFrom", sysConfigDAO.getSystemDisputeEmailConfig().getSystemEmailUserName());
					map.put("defaultEmailTo", disputeDao.searchDisputeEmailAddress(dispute.getInvoice().getBan().getBanDisputeContactId()));
					map.put("user", userDao.findById(SystemUtil.getCurrentUserId()));
					map.put("disputeMsgList", this.disputeDao.searchDisputeMessage(disputeId));
					map.put("defaultEmailMessage", this.getDefaultEmailMessage(dispute));
					if(dispute.getAttachmentPoint() != null) {
						map.put("attachmentFileList", this.disputeDao.searchDisputeAttachmentFile(dispute.getAttachmentPoint().getId().toString()));
					}
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info(CcmFormat.formatExitLog());
		return map;
	}

	public  void contactVendorDispute(Map<String,String> disputeMessage,AttachmentPoint point) throws Exception {
		logger.info(CcmFormat.formatEnterLog("contact Vendor Dispute", disputeMessage,point));
		try {
			Integer id = null;
			if(point != null){
				id = point.getId();	
			}
		//保存disputeMessage
		disputeDao.contactVendorSaveDisputeMessage(disputeMessage,id);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	/**
	 * get Dispute Item View List Page Total No
	 */
	public String getDisputeItemViewListPageTotalNo(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Item View List Page Total No", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getNumberOfDisputeItems(searchDisputeVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) searchDisputeVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @return String DisputeSearchTotalPageNo
	 */
	public String getDisputeSearchTotalPageNo(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Search Total Page No", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getNumberOfDispute(searchDisputeVO, SystemUtil.getCurrentUserId());
			sb.append(searchDisputeVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @return string searchDispute for dispute page table
	 */
	public String searchDispute(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute View List", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = disputeDao.searchDispute(searchDisputeVO, SystemUtil.getCurrentUserId());
			sb.append(searchDisputeVO.getListJsonCompatible(l));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * get Reconcile Detail Total No
	 */
	public String getReconcileDetailTotalNo(SearchVO vo, Dispute dispute) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Reconcile Detail Total No", vo, dispute));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			dispute = disputeDao.findById(dispute.getId());
			count = disputeDao.getNumberOfReconcil(dispute, vo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) vo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * get Accessories Total No
	 */
	public String getAccessoriesListPageTotalNo(SearchDisputeVO searchDisputeVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("get Accessories Total No",searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			Dispute dispute = new Dispute();
			dispute = disputeDao.findDisputeById(searchDisputeVO.getDisputeId());
			String attachmentPointId = dispute.getAttachmentPoint() == null ? "" : dispute.getAttachmentPoint().getId().toString();
			if(!"".equals(attachmentPointId)) count = disputeDao.getNumberAccessories(attachmentPointId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) searchDisputeVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @return string Reconcile Details for dispute_detail page table
	 */
	public String getReconcileDetails(Dispute dispute, SearchVO vo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Reconcile Details View List", vo, dispute));
		StringBuffer sb = new StringBuffer();
		Double totalAmount;
		List<?> l = null;
		try {
			totalAmount = disputeDao.getCreditForReconcileTotalAmount(dispute, vo);
			l = disputeDao.findReconcileDetails(dispute.getId(), SystemUtil.getCurrentUserId(), vo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(vo.getStartIndex() + 1);
			sb.append(",end:");
			int size = l.size();
			sb.append(vo.getStartIndex() + size);
			sb.append(",totalAmount:");
			sb.append(totalAmount);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"No Recorders.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	private String getBlobContent(Blob b) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedInputStream bis = new BufferedInputStream(b.getBinaryStream());
			int len = (int) b.length();
			byte[] bt = new byte[len];
			int readLen = 0;
			while ((readLen = bis.read(bt)) != -1) {
				sb.append(new String(bt, 0, readLen));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * @return CreditDetailsTotalNo for dispute_detail page table
	 */
	public String getCreditDetailsTotalNo(SearchVO vo, Dispute dispute) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Credit Details Total No", vo, dispute));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			dispute = disputeDao.findById(dispute.getId());
			count = disputeDao.getNumberOfCredit(dispute, vo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) vo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @return DisputeHistoryTotalNo for dispute_detail page table
	 */
	public String getDisputeDetailTotalNo(Dispute dispute, SearchVO vo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Detail Tota lNo", dispute, vo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getNumberOfDisputeDetail(dispute, vo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) vo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @return Credit Details for dispute_detail page table
	 */
	public String searchCreditDetails(SearchVO vo, Dispute dispute) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Credit Details View List", vo, dispute));
		StringBuffer sb = new StringBuffer();
		List<?> l = null;
		try {
			dispute = disputeDao.findById(dispute.getId());
			l = disputeDao.findCreditDetails(dispute, vo, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(vo.getStartIndex() + 1);
			sb.append(",end:");
			int size = l.size();
			sb.append(vo.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"No Records.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @return CreditDetails for dispute_detail page table
	 */
	public String getDisputeDetail(Dispute dispute, SearchVO vo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Detail View List", dispute, vo));
		StringBuffer sb = new StringBuffer();
		List<?> l = null;
		try {
			l = disputeDao.findDisputeDetail(vo, dispute);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(vo.getStartIndex() + 1);
			sb.append(",end:");
			int size = l.size();
			sb.append(vo.getStartIndex() + size);
			sb.append(",data:[");

			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * get Dispute Account Code List
	 */
	public List<MapEntryVO<String, String>> getDisputeAccountCodeList(Dispute dispute) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Account Code List", dispute));
		List<MapEntryVO<String, String>> list = null;

		list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = disputeDao.getDisputeAccountCodeList(dispute);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for (int i = 0; i < dList.size(); i++) {
			Object[] rs = dList.get(i);
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer) rs[0] + ""), (String) rs[1]);
			list.add(m);
		}

		logger.info(CcmFormat.formatExitLog());
		return list;

	}
	
//	/**
//	 * @author jian.Dong
//	 * */
//	private Double getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(Dispute d){
//		if(d == null){
//			return 0d;
//		}else{
//			double disputeAmount = (d.getDisputeAmount() == null ? 0 : d.getDisputeAmount());
//			double disputeBalance = (d.getDisputeBalance() == null ? 0 : d.getDisputeBalance());
//			double outstandingReserveAmount = (d.getOutstandingReserveAmount() == null ? 0 : d.getOutstandingReserveAmount());
//			double result = disputeAmount * (outstandingReserveAmount/disputeBalance);
//			d.setOutstandingReserveAmount(outstandingReserveAmount - result);
//			return result;
//		}
//		
//	}

	/**
	 * @return Credit For Reconcile
	 */
	public String[] updateCreditForReconcile(SearchDisputeVO searchDisputeVO, Dispute disputeNew, Credit creditNew, Reconcile reconcileNew)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update Credit For Reconcile", searchDisputeVO, disputeNew, creditNew, reconcileNew));

		String[] strs = new String[3];

		StringBuffer sb = new StringBuffer();
		Double disputeBalance;
		Double creditBalance;
		Dispute dispute;
		Credit credit;
		Reconcile reconcile = new Reconcile();
		ReconcileStatus reconcileStatus;
		// two types reconcile and manual reconcile
		// reconcile
		if (creditNew.getId() > 0) {
			try {
				dispute = disputeDao.findById(disputeNew.getId());
				credit = creditDao.findById(creditNew.getId());
				if (dispute.getFlagShortpaid().equals("N"))
					reconcileStatus = reconcileStatusDao.findById(1);
				else
					reconcileStatus = reconcileStatusDao.findById(2);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			// generate judge condition
			disputeBalance = dispute.getDisputeBalance() - disputeNew.getDisputeBalance();
			creditBalance = credit.getCreditBalance() - creditNew.getCreditBalance();

			if (disputeBalance >= 0 && creditBalance >= 0) {
				// update dispute data
				dispute.setDisputeBalance(disputeBalance);
				// update credit data
				credit.setCreditBalance(creditBalance);
				// set new reconcile data
				Double aaa = null;
				try {
					aaa = CcmFormat.paseCurrency(searchDisputeVO.getReconcileAmount()).doubleValue();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				reconcile.setReconcileAmount(aaa);
				reconcile.setDispute(dispute);
//				reconcile.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(dispute));
				reconcile.setCreatedTimestamp(new Date());
				reconcile.setModifiedTimestamp(new Date());
				reconcile.setReconcileDate(new Date());
				reconcile.setNotes(creditNew.getNotes());
				reconcile.setCreatedBy(SystemUtil.getCurrentUserId());
				reconcile.setModifiedBy(SystemUtil.getCurrentUserId());
				reconcile.setRecActiveFlag("Y");
				reconcile.setReconcileStatus(reconcileStatus);
				reconcile.setAccountCode(reconcileNew.getAccountCode());
				// update data
				try {
					disputeDao.updateDisputeBalanceRollback(dispute);
					creditDao.updateCreditBalanceRollback(credit);
					reconcileDao.save(reconcile);
					strs[0] = reconcile.getId().toString();
				} catch (RuntimeException e) {
					logger.error(CcmFormat.formatErrorLog(e));
					BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
					throw bpe;
				}
			}
			// condition not enough
			else if (disputeBalance < 0) {
				sb.append("{error:\"dispute Balance not enough.\"}");
				strs[2] = sb.toString();
				return strs;
			} else if (creditBalance < 0) {
				sb.append("{error:\"credit Balance not enough.\"}");
				strs[2] = sb.toString();
				return strs;
			}
			// manual reconcile
		} else if (creditNew.getId() == 0) {
			try {
				dispute = disputeDao.findById(disputeNew.getId());
				if (dispute.getFlagShortpaid().equals("N"))
					reconcileStatus = reconcileStatusDao.findById(6);
				else
					reconcileStatus = reconcileStatusDao.findById(8);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			// generate judge condition

			// update dispute data
			dispute.setDisputeBalance(0.0);

			// set new reconcile data
			Double aaa = null;
			try {
				aaa = CcmFormat.paseCurrency(searchDisputeVO.getReconcileAmount()).doubleValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			reconcile.setReconcileAmount(aaa);
			reconcile.setDispute(dispute);
			reconcile.setCreatedTimestamp(new Date());
			reconcile.setModifiedTimestamp(new Date());
			reconcile.setReconcileDate(new Date());
			reconcile.setNotes(creditNew.getNotes());
			reconcile.setCreatedBy(SystemUtil.getCurrentUserId());
			reconcile.setModifiedBy(SystemUtil.getCurrentUserId());
			reconcile.setRecActiveFlag("Y");
			reconcile.setReconcileStatus(reconcileStatus);
			reconcile.setAccountCode(reconcileNew.getAccountCode());
			// update data
			try {
				disputeDao.updateDisputeBalanceRollback(dispute);

				reconcileDao.save(reconcile);
				strs[0] = reconcile.getId().toString();
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}

		} else {
			try {
				dispute = disputeDao.findById(disputeNew.getId());
				if (dispute.getFlagShortpaid().equals("N"))
					reconcileStatus = reconcileStatusDao.findById(5);
				else
					reconcileStatus = reconcileStatusDao.findById(7);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			// generate judge condition

			// update dispute data
			dispute.setDisputeBalance(0.0);

			// set new reconcile data
			Double aaa = null;
			try {
				aaa = CcmFormat.paseCurrency(searchDisputeVO.getReconcileAmount()).doubleValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			reconcile.setReconcileAmount(aaa);
			reconcile.setDispute(dispute);
			reconcile.setCreatedTimestamp(new Date());
			reconcile.setModifiedTimestamp(new Date());
			reconcile.setReconcileDate(new Date());
			reconcile.setNotes(creditNew.getNotes());
			reconcile.setCreatedBy(SystemUtil.getCurrentUserId());
			reconcile.setModifiedBy(SystemUtil.getCurrentUserId());
			reconcile.setRecActiveFlag("Y");
			reconcile.setReconcileStatus(reconcileStatus);
			reconcile.setAccountCode(reconcileNew.getAccountCode());
			// update data
			try {
				disputeDao.updateDisputeBalanceRollback(dispute);

				reconcileDao.save(reconcile);
				strs[0] = reconcile.getId().toString();
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}

		}
		// get disputeBalance and disputeId
		sb.append("{disputeBalance:'");
		sb.append(CcmFormat.formatCurrency(dispute.getDisputeBalance()));
		sb.append("',disputeId:");
		sb.append(dispute.getId());
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		strs[1] = sb.toString();
		return strs;
	}

	/**
	 * @return Cancel For Reconcile
	 */
	public String updateCancelForReconcile(Dispute disputeNew, Credit creditNew, Reconcile reconcileNew) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update Cancel For Reconcile", disputeNew, creditNew, reconcileNew));
		StringBuffer sb = new StringBuffer();
		Double disputeBalance;
		Double creditBalance;
		Dispute dispute;
		Credit credit;
		Reconcile reconcile;
		boolean falg;
		// initial instance
		try {
			dispute = disputeDao.findById(disputeNew.getId());

			reconcile = reconcileDao.findById(reconcileNew.getId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// judge manual or auto generate creditNumber
		falg = false;
		// auto generate creditNumber
		if (falg) {
			try {
				credit = creditDao.findById(creditNew.getId());
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			disputeBalance = dispute.getDisputeBalance() + reconcileNew.getReconcileAmount();
			// alter dispute
			dispute.setDisputeBalance(disputeBalance);
			disputeDao.updateDisputeBalanceRollback(dispute);
			// alter credit
			creditBalance = credit.getCreditBalance() + reconcileNew.getReconcileAmount();
			credit.setCreditBalance(creditBalance);
			creditDao.updateCreditBalanceRollback(credit);
			// alter reconcile
			reconcile.setRecActiveFlag("N");
			reconcileDao.merge(reconcile);
		}
		// manual generate creditNumber
		else {
			disputeBalance = dispute.getDisputeBalance() + reconcileNew.getReconcileAmount();
			// alter dispute
			dispute.setDisputeBalance(disputeBalance);
			disputeDao.updateDisputeBalanceRollback(dispute);
			// alter reconcile
			reconcile.setRecActiveFlag("N");
			reconcileDao.merge(reconcile);
		}
		sb.append("{disputeBalance:'");
		sb.append(CcmFormat.formatCurrency(dispute.getDisputeBalance()));
		sb.append("',disputeId:");
		sb.append(dispute.getId());
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * return dispute instance by one id Modified By Chao.liu
	 */
	public Dispute findDisputeById(int id) throws BPLException {
		Dispute dispute;
		try {
			dispute = (Dispute) disputeDao.getObject(Dispute.class, id);
			// dispute.setInvoice(dispute.getInvoice());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return dispute;
	}

	/**
	 * get Dispute Transaction View List
	 */
	public String getDisputeTransaction(Dispute dispute) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Transaction View List", dispute));
		StringBuffer sb = new StringBuffer();
		List<?> l = null;
		try {
			l = disputeDao.findDisputeTransaction(dispute);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			int size = l.size();
			sb.append("{data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	// DisputeAction.java connection here
	// paging
	public String queryDisputeAttachmentCount(String disputeId) throws BPLException {
		SearchInvoiceVO svo = new SearchInvoiceVO();
		svo.setDisputeId(disputeId);
		try {
			long attachmentCount  = attachmentFileDao.getDisputeAttachmentPointIdCountDao(svo);
			if((int)attachmentCount > 0){
				return "Y";
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return "N";
	}

	/**
	 * **************************************************************
	 * 
	 * @author pengfei.wang create pendingDispute
	 *         ***************************************************************
	 */
	// DisputeAction.java connection here
	// paging
	public String getPendingDisputeTotalPageNo(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Pending Dispute Total PageNo", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getPendingDisputesNumber(searchDisputeVO, SystemUtil.getCurrentUserId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) searchDisputeVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	// CreditAction.java connection here
	// search Credit table
	public String searchPendingDispute(SearchDisputeVO searchDisputeVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Pending Dispute", searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<?> l = null;
		try {
			l = disputeDao.findPendingDisputes(searchDisputeVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchDisputeVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchDisputeVO.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				if (i != 0)
					sb.append(",");
				sb.append(l.get(i).toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @author dongjian
	 */
	public String getDisputeAssignmentSearchTotalPageNo(SearchDisputeVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get DisputeAssignment Search Total PageNo", svo));
		long count = 0;
		try {
			count = disputeDao.getAssignmentCount(svo.getUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = buildTotalPageNoJson(count, svo.getRecPerPage());
		logger.info(CcmFormat.formatExitLog());
		return s;
	}

	/**
	 * search DisputeAssignment View List
	 */
	public String searchDisputeAssignment(SearchDisputeVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search DisputeAssignment View List", svo));
		String s;
		List<String> list = null;
		try {
			list = disputeDao.searchDisputeAssignment(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		s = buildDataJson(list, svo.getStartIndex());
		logger.info(CcmFormat.formatExitLog());
		return s;
	}

	private String buildTotalPageNoJson(long count, int recPerPage) {
		StringBuilder sb = new StringBuilder();
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) recPerPage);
		sb.append(tp);
		sb.append("}");
		return sb.toString();
	}

	private String buildDataJson(List<String> list, int startIndex) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(startIndex + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(startIndex + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				if (i != 0)
					sb.append(",");
				sb.append(list.get(i).toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * @author pengfei.wang Will the quantity statistical basis
	 */
	public String getDisputeWorkCount(WorkspaceVO wvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get DisputeWorkCount", wvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.getDisputeWorkCount(wvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) wvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @author Pengfei.wang The statistical basis will query
	 * 
	 * 
	 * Get transaction_history id
	 * @param searchDisputeVO
	 * @return
	 * @throws BPLException
	 */
	public Integer updateDisputeTiketNO(SearchDisputeVO searchDisputeVO, Dispute d, List<String> uploadsFileName) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update DisputeTiket NOt", searchDisputeVO, d, uploadsFileName));
		TransactionHistory th = null;
		User user = null;
		if (uploadsFileName != null || !d.getTicketNumber().equals(searchDisputeVO.getTid())) {

			try {
				if (!d.getTicketNumber().equals(searchDisputeVO.getTid())) {
					d.setTicketNumber(searchDisputeVO.getTid());
				}
				user = userDao.findById(SystemUtil.getCurrentUserId());
				th = new TransactionHistory();
				th.setDispute(d);
				th.setInvoice(d.getInvoice());
				th.setInvoiceStatus(d.getInvoice().getInvoiceStatus());
				th.setInternalInvoiceStatus(d.getInvoice().getInternalInvoiceStatus());
				th.setDisputeStatus(d.getDisputeStatus());
				th.setWorkflowAction(d.getInvoice().getWorkflowAction());
				th.setUser(user);
				th.setDisputeAmount(d.getDisputeAmount());
				th.setNotes(searchDisputeVO.getNotes());
				th.setCreatedBy(SystemUtil.getCurrentUserId());
				th.setCreatedTimestamp(new Date());
				th.setRecActiveFlag("Y");

				disputeDao.saveObject(th);
			} catch (Exception e) {
				logger.error(CcmFormat.formatErrorLog(e));
				RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			logger.info(CcmFormat.formatExitLog());
			return th.getId();
		} else {
			logger.info(CcmFormat.formatExitLog());
			return null;
		}

	}

	/**
	 * @author pengfei.wang The statistical basis will query
	 */
	public String searchDisputeWorkCount(WorkspaceVO wvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search DisputeWorkCount View List", wvo));

		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = disputeDao.searchDisputeWorkCount(wvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(wvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(wvo.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = list.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"Not found info to DB.\"");
		}
		sb.append("}");

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();

	}
	/***
	 * @author wei.su
	 * @return excel
	 * create Dispute To Excel
	 */
	/**
	 * Download createDispute To Excel
	 */
	public String createDisputeToExcel(SearchDisputeVO searchDisputeVO, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Download createDispute To Excel", searchDisputeVO, excelDirPath, titles));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = disputeDao.getNumberOfDispute(searchDisputeVO, SystemUtil.getCurrentUserId());
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "dispute0");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchDisputeVO.setPageNo(1);
				searchDisputeVO.setRecPerPage((int) count);
				list = disputeDao.searchDisputeByObject(searchDisputeVO, SystemUtil.getCurrentUserId());
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchDisputeVO.setPageNo(j + 1);
					searchDisputeVO.setRecPerPage(recPerPage);
					list = disputeDao.searchDisputeByObject(searchDisputeVO, SystemUtil.getCurrentUserId());
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 28, 30, 28, 28, 25, 25, 25, 25, 25, 20, 20 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	/**
	 * @author wei.su
	 * @return excel
	 * create Dispute Detail To Excel
	 */
	/**
	 * Download createDisputeDetail To Excel
	 */
	public String createDisputeDetailToExcel(Dispute dispute, SearchVO vo, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Download createDisputeDetail To Excel", dispute, vo, excelDirPath, titles));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = disputeDao.getNumberOfDisputeDetail(dispute, vo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "dispute1");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				vo.setPageNo(1);
				vo.setRecPerPage((int) count);
				list = disputeDao.searchDisputeDetailByObject(dispute, vo, SystemUtil.getCurrentUserId());
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					vo.setPageNo(j + 1);
					vo.setRecPerPage(recPerPage);
					list = disputeDao.searchDisputeDetailByObject(dispute, vo, SystemUtil.getCurrentUserId());
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 20, 20, 20, 20, 40, 25, 25, 25 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	/***
	 * @author wei.su
	 * @return excel
	 * create Dispute Transaction To Excel
	 */
	/**
	 * Download createDisputeTransaction To Excel
	 */
	public String createDisputeTransactionToExcel(Dispute dispute, SearchVO vo, String excelDirPath, List<String> titles)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Download createDisputeTransaction To Excel", dispute, vo, excelDirPath, titles));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "dispute1");
			jExcelUtil.writeTitle(0, titles);

			list = disputeDao.searchDisputeTransctionByObject(dispute);
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}

			jExcelUtil.setColumnView(new int[] { 25, 25, 25, 25, 20, 30, 40 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}

	public String getValidateDisputeBanLock(String disputeId) throws BPLException {
		logger.info("Enter method getValidateBanLock.");
		String result = null;
		try {
			Dispute d = disputeDao.findById(new Integer(disputeId));
			String disputeStatusId = ((d.getDisputeStatus() == null) ? "null" : d.getDisputeStatus().getId() + "");
			String banProcess = (d.getInvoice().getBan().getProcessStatus() == null) ? "null" : "'"
					+ d.getInvoice().getBan().getProcessStatus() + "'";
			result = "{disputeStatusId:" + disputeStatusId + ", banProcess:" + banProcess + "}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getValidateBanLock.");
		return result;
	}

	public Invoice getInvoiceObject(String invoiceId) throws BPLException {
		logger.info("Enter method getInvoiceObject.");
		Invoice invoice = new Invoice();
		try {
			invoice = invoiceDao.findById(Integer.parseInt(invoiceId));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Enter method getInvoiceObject.");
		return invoice;
	}

	/**
	 * @author wei.su Get to the dispute email messages
	 */
	public List<Dispute> GetTheDisputesForSendEmail() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method GetTheDisputesInFoToSendEmail.", ""));
		List<Dispute> dl = null;
		try {
			dl = disputeDao.GetTheDisputesInFoToSendEmail();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return dl;
	}

	/***************************************************************************
	 * @author wei.su get the sendtoemail from contact by dispute
	 * @param dispute
	 * @return
	 * @throws BPLException
	 */
	public Contact GetTheContactByDiputeForSendEmail(Dispute dispute) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method GetTheSendToEmailAdress.", dispute));
		Contact contact = null;
		try {
			contact = contactDao.findById(dispute.getInvoice().getBan().getBanDisputeContactId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return contact;
	}

	/***************************************************************************
	 * @author wei.su
	 *         getEmailCopyAddresOfDisputeAndConvertInToStringListForSendEmail
	 * @param dispute
	 * @return
	 * @throws BPLException
	 */
	public String[] getEmailCopyAddresOfDisputeAndConvertInToStringListForSendEmail(Dispute dispute) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getEmailCopyAddresOfDisputeAndConvertInToStringListForSendEmail.", dispute));
		String[] copyToEmails = null;
		try {
			copyToEmails = dispute.getEmailCopyAddress().split("\\;");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return copyToEmails;
	}

	/***************************************************************************
	 * @author wei.su
	 */
	public SystemEmailConfig getSystemEmailConfigForSendEmail() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getSystemEmailConfigForSendEmail.", ""));
		SystemEmailConfig systemEmailConfig = null;
		try {
			systemEmailConfig = sysConfigDAO.getSystemEmailConfig();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return systemEmailConfig;
	}

	public synchronized void getEmailAndInsertInToDataBaseForValedateCheck() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter getEmailAndInsertInToDataBaseForValedateCheck.", ""));
		try {
			List<Dispute> dl = GetTheDisputesForSendEmail();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/***************************************************************************
	 * @author wei.su
	 */
	public String createProposalsOfDisputeToExcelForSendEmail(Dispute dispute, SearchVO vo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Exit method createProposalsDisputeDetailToExcelForSendEmail.", vo));
		String filePath = "" + sysConfigDAO.getUploadFilePath() + "dispute-" + dispute.getDisputeNumber() + "-"
				+ CcmFormat.format(new Date(), "yyyy-MM-dd") + ".xls";
		File excel_ = new File(filePath);
		File dir = new File(excel_.getParent());
		if (dir.exists() && !dir.isDirectory()) {
			dir.delete();
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try {
			List<String> titles = new ArrayList<String>();
			titles.add("Province");
			titles.add("Invoice Date");
			titles.add("Dispute Reason");
			titles.add("Dispute Amount");
			titles.add("Circuit Number");
			titles.add("Address");
			titles.add("Description");
			titles.add("Service Type");
			titles.add("Rate");
			titles.add("Quantity");
			titles.add("Usoc");
			titles.add("Usoc Description");
			titles.add("Number Of Calls");
			titles.add("Country");
			titles.add("Direction");
			titles.add("Billing Number");
			titles.add("Asg");
			titles.add("Jurisdiction");
			titles.add("Mileage");
			titles.add("Minutes");
			titles.add("Short Paid");
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = proposalDao.getProposalsNumberOfDisputeToExcelForSendEmailByDisputeId(dispute);
			jExcelUtil.createWritableWorkbook(filePath);
			jExcelUtil.createWritableSheet(0, "dispute1");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				vo.setPageNo(1);
				vo.setRecPerPage((int) count);
				list = proposalDao.searchProposalsOfDisputeToExcelForSendEmailByDisputeId(dispute);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					vo.setPageNo(j + 1);
					vo.setRecPerPage(recPerPage);
					list = proposalDao.searchProposalsOfDisputeToExcelForSendEmailByDisputeId(dispute);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 20, 20, 20, 20, 40, 25, 25, 25 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return filePath;
	}

	/***************************************************************************
	 * @author wei.su
	 * @throws BPLException
	 */
	public synchronized void CreateEmialsAddEmailTable() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search PreEmails Of Dispute"));
		List<Dispute> dl = null;
		try {
			dl = GetTheDisputesForSendEmail();
			for (int i = 0; i < dl.size(); i++) {
				List afl = new ArrayList<AttachmentFile>();
				Email email = new Email();
				AttachmentFile attachmentFile = new AttachmentFile();
				SearchVO searchVO = new SearchVO();
				Dispute dispute = dl.get(i);
				Integer apid = 0;
				if (dispute.getAttachmentPoint() == null) {
					apid = null;
				} else {
					apid = dispute.getAttachmentPoint().getId();
				}

				StringBuffer content = new StringBuffer();
				// Will the new XLS binding to the dispute
				String filePath = createProposalsOfDisputeToExcelForSendEmail(dispute, searchVO);
				String fileName = new File(filePath).getName();
				if ("".endsWith(filePath)) {
					return;
				}
				if (dispute.getAttachmentPoint() == null) {
					AttachmentPoint ap = new AttachmentPoint();
					ap.setTableName("dispute");
					ap.setTableIdValue(dispute.getId());
					ap.setCreatedBy(SystemUtil.getCurrentUserId());
					ap.setCreatedTimestamp(new Date());
					ap.setModifiedBy(SystemUtil.getCurrentUserId());
					ap.setModifiedTimestamp(new Date());
					ap.setRecActiveFlag("Y");
					Integer newId = attachmentFileDao.createPoint(ap);
					userDao.updateAttachmentPointId("dispute", dispute.getId(), newId);
					apid = newId;
					attachmentFile.setAttachmentPoint(attachmentFileDao.loadPoint(apid));
					attachmentFile.setFilePath(filePath);
					attachmentFile.setFileName(fileName);
					attachmentFile.setCreatedBy(SystemUtil.getCurrentUserId());
					attachmentFile.setCreatedTimestamp(new Date());
					attachmentFile.setModifiedBy(SystemUtil.getCurrentUserId());
					attachmentFile.setModifiedTimestamp(new Date());
					attachmentFile.setRecActiveFlag("Y");
					attachmentFileDao.save(attachmentFile);
				}

				email.setAttachmentPoint(dispute.getAttachmentPoint());
				User user = userDao.findById(dispute.getInvoice().getAssignedAnalystId());
				email.setSubject("Rogers " + dispute.getClaimNumber() + "");
				email.setCcAddress(user.getEmail());
				content.append("Dear "
						+ (dispute.getInvoice().getVendor().getVendorName() == null ? " " : dispute.getInvoice().getVendor()
								.getVendorName()) + ",<br/><br/>");
				content.append("Please accept this email as formal notification of a dispute being file on account # "
						+ (dispute.getInvoice().getBan().getAccountNumber() == null ? " " : dispute.getInvoice().getBan()
								.getAccountNumber()) + " ");
				content.append("Invoice # " + (dispute.getInvoice().getInvoiceNumber()) + ".<br/><br/>");
				content.append("All related back up documentation is attached. Please respond within 30 days of the receipt of this email.<br/><br/>");
				content.append("If you should have any questions concerning this dispute, please do not hesitate to contact me.<br/><br/>");
				content.append("Regards,<br/><br/>");
				content.append(user.getFirstName() + "&nbsp;" + user.getLastName());
				content.append("<br/><br/>Company:Rogers<br/><br/>");
				content.append("Phone:" + (user.getCellPhone() == null ? " " : user.getCellPhone()) + "<br/><br/>");
				content.append("Fax:" + (user.getFaxNumber() == null ? " " : user.getFaxNumber()) + "<br/><br/>");
				email.setContent(content.toString());
				email.setEmailType("D");
				email.setReferenceId(dispute.getId());
				email.setCreatedBy(SystemUtil.getCurrentUserId());
				email.setCreatedTimestamp(new Date());
				email.setEmailStatus("0");
				email.setModifiedBy(SystemUtil.getCurrentUserId());
				email.setModifiedTimestamp(email.getCreatedTimestamp());
				email.setRecActiveFlag("Y");
				email.setSentDatetime(null);
				email.setSystemMessage("");
				email.setReplyTo(user.getEmail());
				email.setCcAddress(user.getEmail());

				email.setToAddress(GetTheContactByDiputeForSendEmail(dispute).getEmail());
				emailDao.save(email);
			}
			disputeDao.AfterSendEmailChangeDisputeStatus();
		} catch (BPLException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * @author wei.su
	 * @return String get PreEmails Total PageNo OfDispute
	 */
	public String getPreEmailsTotalPageNoOfDispute(OperationsVO operationsVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get PreEmails Total Page No Of Dispute", operationsVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = emailDao.getPreEmailsTotalPageNoOfDispute(operationsVO);
			sb.append(operationsVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @author wei.su
	 * @return string search PreEmails Of Dispute
	 */
	public String searchPreEmailsOfDispute(OperationsVO operationsVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search PreEmails Of Dispute", operationsVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = emailDao.searchPreEmailsOfDispute(operationsVO);
			sb.append(operationsVO.getListJson(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * get email by emailId
	 * 
	 * @author wei.su
	 * @throws Exception
	 */
	public String getEmailByEmailId(Integer emailId) throws Exception {
		logger.info(CcmFormat.formatEnterLog("Enter method getEmailByEmailId.", emailId));
		String proposalJson = "";
		Email email = new Email();
		try {
			email = emailDao.findById(emailId);
			proposalJson = "{bccAddress:\"" + (email.getBccAddress() == null ? "" : email.getBccAddress()) + "\",ccAddress:\""
					+ (email.getCcAddress() == null ? "" : email.getCcAddress()) + "\",content:\"" + email.getContent()
					+ "\",emailStatus:\"" + (email.getEmailStatus() == null ? "" : email.getEmailStatus()) + "\",recActiveFlag:\""
					+ (email.getRecActiveFlag() == null ? "" : email.getRecActiveFlag()) + "\",subject:\""
					+ (email.getSubject() == null ? "" : email.getSubject()) + "\",systemMessage:\""
					+ (email.getSystemMessage() == null ? "" : email.getSystemMessage()) + "\",toAddress:\""
					+ (email.getToAddress() == null ? "" : email.getToAddress()) + "\",attachmentPoint:\""
					+ (email.getAttachmentPoint() == null ? "" : email.getAttachmentPoint().getId()) + "\",createdBy:\""
					+ (email.getCreatedBy() == null ? "" : email.getCreatedBy()) + "\",createdTime:\""
					+ (email.getCreatedTimestamp() == null ? "" : email.getCreatedTimestamp()) + "\",sendTime:\""
					+ (email.getSentDatetime() == null ? "" : CcmFormat.formatDateTime(email.getSentDatetime())) + "\",modifiedBy:\""
					+ (email.getModifiedBy() == null ? "" : email.getModifiedBy()) + "\",modifiedTime:\""
					+ (email.getModifiedTimestamp() == null ? "" : email.getModifiedTimestamp()) + "\",referenceId:\""
					+ (email.getReferenceId() == null ? "" : email.getReferenceId()) + "\",emailType:\""
					+ (email.getEmailType() == null ? "" : email.getEmailType()) + "\",notes:\""
					+ (email.getNotes() == null ? "" : email.getNotes()) + "\",recActiveFlag:\""
					+ (email.getRecActiveFlag() == null ? "" : email.getRecActiveFlag()) + "\",id:\""
					+ (email.getId() == null ? "" : email.getId()) + "\"}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			e.printStackTrace();
			throw new BPLException(e.getMessage());
		}
		logger.info(CcmFormat.formatExitLog());
		return proposalJson;
	}
	/**
	 * @author wei.su
	 * updateEmailByEmail
	 */
	public void updateEmailByEmail(Email email) throws Exception {
		logger.info(CcmFormat.formatEnterLog("Enter method updateEmailByEmail.", email));
		try {
			email.setEmailStatus("1");
			emailDao.merge(email);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new BPLException(e.getMessage());
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/***************************************************************************
	 * 
	 * @author wei.su
	 * @param emailIds
	 * @throws Exception
	 */
	public void updateSendCheckedEmailsAndChangeEmailStatus(String[] emailIds) throws Exception {
		logger.info(CcmFormat.formatEnterLog("Enter method updateEmailByEmail."));
		List<Email> el = new ArrayList<Email>();
		String pemaiIds[] = emailIds[0].toString().split(",");
		String sEmailIdsForGetSendEmails = "";
		for (int i = 0; i < pemaiIds.length; i++) {
			if (i == (emailIds.length - 1)) {

				sEmailIdsForGetSendEmails = sEmailIdsForGetSendEmails + emailIds[i];
				break;
			}
			sEmailIdsForGetSendEmails = sEmailIdsForGetSendEmails + pemaiIds[i] + ",";

		}
		if (pemaiIds.length < 1) {
			sEmailIdsForGetSendEmails = pemaiIds.toString();
		}
		try {
			el = emailDao.findCheckedEmailsToSend(sEmailIdsForGetSendEmails);
			for (int i = 0; i < el.size(); i++) {
				Email email = el.get(i);
				List<String> fileList = new ArrayList<String>();
				if (email.getAttachmentPoint() != null && email.getAttachmentPoint().getAttachmentFiles() != null) {
					Iterator j = email.getAttachmentPoint().getAttachmentFiles().iterator();
					while (j.hasNext()) {
						int k = 0;
						Object o = j.next();
						AttachmentFile attachmentFile = (AttachmentFile) o;
						if (attachmentFile != null && new File(attachmentFile.getFilePath()).exists()) {
							fileList.add(k, attachmentFile.getFilePath());
							k = k + 1;
						}
					}
				}
				try {
					createAndSendEmail(email, fileList);
					email.setEmailStatus("2");
					email.setSentDatetime(new Date());
				} catch (RuntimeException e) {
					throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
			throw new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * @author wei.su Send Email
	 * @param subject
	 * @param text
	 * @param toEmail
	 * @param filePath
	 * @throws MessagingException
	 */
	@SuppressWarnings("unused")
	private void createAndSendEmail(Email email, List<String> fileList) throws MessagingException {
		SystemEmailConfig sec = sysConfigDAO.getSystemEmailConfig();

		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(props);

//		session.setDebug(true);

		Message msg = new MimeMessage(session);
		msg.setSubject(email.getSubject());
		MimeMultipart msgMultipart = new MimeMultipart();
		msg.setContent(msgMultipart);

		MimeBodyPart content = new MimeBodyPart();
		content.setContent(email.getContent(), "text/html;charset=utf-8");
		msgMultipart.addBodyPart(content);
		this.addEmailAttch(msgMultipart, fileList); 
		msg.setHeader("From", sec.getSystemEmailAdress());
		msg.setHeader("To", email.getToAddress());
		if(!(email.getReplyTo()==null || "".equals(email.getReplyTo())))msg.setHeader("Reply-to", email.getReplyTo());
		if(!(email.getCcAddress()==null || "".equals(email.getCcAddress()))) msg.setHeader("Cc", email.getCcAddress());
		if(!(email.getBccAddress()==null || "".equals(email.getBccAddress())))msg.setHeader("Bcc", email.getBccAddress());
		msg.saveChanges();
		List<InternetAddress> address = new ArrayList<InternetAddress>();
		address.add(new InternetAddress(email.getToAddress()));
		if(email.getCcAddress()!=null && !"".equals(email.getCcAddress())){
			address.add(new InternetAddress(email.getCcAddress()));
		}
		if(email.getBccAddress()!=null && !"".equals(email.getBccAddress())){
			address.add(new InternetAddress(email.getBccAddress()));
		}
		Address[] a = new Address[address.size()];
		for(int i=0;i<address.size();i++){
			a[i] = address.get(i);
		}
		Transport transport = session.getTransport();

		transport.connect(sec.getSystemEmailSmtpServer(), sec.getSystemEmailSmtpServerPort(), sec.getSystemEmailUserName(), sec.getSystemEmailPassWord());
		transport.sendMessage(msg, a);
		transport.close();

	}
	/***
	 * @author wei.su
	 * @param msgMultipart
	 * @param fileList
	 * @throws MessagingException
	 */
	private void addEmailAttch(MimeMultipart msgMultipart, List<String> fileList) throws MessagingException {
		for (String strs : fileList) {
			File attachment = new File(strs);
			if (attachment.exists()) {
				MimeBodyPart attch = new MimeBodyPart();
				DataSource ds = new FileDataSource(strs);
				DataHandler dh = new DataHandler(ds);
				attch.setDataHandler(dh);
				String filename = attachment.getName();
				attch.setFileName(filename);
				msgMultipart.addBodyPart(attch);
			}
		}
	}

	/**@author wei.su
	 * @return String DisputeSearchTotalPageNo
	 */
	public String getAnnexsOfDisputeTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Dispute Search Total Page No", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = attachmentFileDao.getAttachmentPointIdCount(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**@author wei.su
	 * @return string searchDispute for dispute page table
	 */
	public String searchAnnexsOfDispute(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Dispute Annexs View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = attachmentFileDao.searchAttachmentPointId(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(l));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public IAttachmentFileDao getAttachmentFileDao() {
		return attachmentFileDao;
	}

	public void setAttachmentFileDao(IAttachmentFileDao attachmentFileDao) {
		this.attachmentFileDao = attachmentFileDao;
	}

	public IContactDao getContactDao() {
		return contactDao;
	}

	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}

	/**
	 * @return the userDao
	 */
	public IUserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao
	 *            the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * initial
	 */
	public DisputeServiceImpl() {
	}

	/**
	 * 
	 * @return disputeDao
	 */
	public IDisputeDao getDisputeDao() {
		return disputeDao;
	}

	/**
	 * @return the creditDao
	 */
	public ICreditDao getCreditDao() {
		return creditDao;
	}

	/**
	 * @param creditDao
	 *            the creditDao to set
	 */
	public void setCreditDao(ICreditDao creditDao) {
		this.creditDao = creditDao;
	}

	/**
	 * @return the reconcileDao
	 */
	public IReconcileDao getReconcileDao() {
		return reconcileDao;
	}

	/**
	 * @param reconcileDao
	 *            the reconcileDao to set
	 */
	public void setReconcileDao(IReconcileDao reconcileDao) {
		this.reconcileDao = reconcileDao;
	}

	/**
	 * 
	 * @param disputeDao
	 */
	public void setDisputeDao(IDisputeDao disputeDao) {
		this.disputeDao = disputeDao;
	}

	public IProposalDao getProposalDao() {
		return proposalDao;
	}

	public void setProposalDao(IProposalDao proposalDao) {
		this.proposalDao = proposalDao;
	}

	public IEmailDao getEmailDao() {
		return emailDao;
	}

	public void setEmailDao(IEmailDao emailDao) {
		this.emailDao = emailDao;
	}

	public IInvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	/**
	 * Download Transaction To Excel (viewDisputeDetails.js)
	 * @author xinyu.Liu
	 * 
	 */
	public String getExcelByTransaction(SearchDisputeVO sdo, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Download Transaction To Excel", sdo, excelDirPath, titles));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			int pageNumber = 0;
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(pageNumber, "DisputeItem" + pageNumber);
			jExcelUtil.writeTitle(0, traverseRemovalList(titles));

			list = disputeDao.searchTransactionByObject(sdo);
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 });
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}

	public String searchAttachmentFile(SearchDisputeVO searchDisputeVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("search Dispute Attachment File View List",searchDisputeVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			Dispute dispute = new Dispute();
			dispute = disputeDao.findDisputeById(searchDisputeVO.getDisputeId());
			searchDisputeVO.setAttachmentPointId(dispute.getAttachmentPoint() == null ? "" : dispute.getAttachmentPoint().getId().toString());
			l = disputeDao.searchAttachmentFile(searchDisputeVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchDisputeVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchDisputeVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if(i!=0) sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	

	public IReconcileStatusDao getReconcileStatusDao() {
		return reconcileStatusDao;
	}

	public void setReconcileStatusDao(IReconcileStatusDao reconcileStatusDao) {
		this.reconcileStatusDao = reconcileStatusDao;
	}

	public Double updateIncreaseAndDecrease(Dispute dispute, String reserveType,Integer accountCodeId,
			AttachmentPoint point) {
		logger.info("Enter method updateIncreaseAndDecrease.");
		Dispute d = null;
		try {
			d = disputeDao.findById(dispute.getId());
			Reconcile r = new Reconcile();
			if(accountCodeId != null){
				r.setAccountCode(invoiceDao.loadAccountCode(accountCodeId));
			}
			r.setAttachmentPoint(point);
			r.setCreatedBy(SystemUtil.getCurrentUserId());
			r.setCreatedTimestamp(new Date());
			r.setDispute(d);
			r.setModifiedBy(r.getCreatedBy());
			r.setModifiedTimestamp(r.getCreatedTimestamp());
			r.setRecActiveFlag("Y");
			r.setReconcileDate(new Date());
			r.setNotes(dispute.getNotes());
			r.setReconcileStatus(reconcileStatusDao.load(10));
			double v = (dispute.getOutstandingReserveAmount() == null ? 0 : dispute.getOutstandingReserveAmount());
			if("Increase".equals(reserveType)){
				r.setReleasedReserveAmount(v*-1);
				d.setOutstandingReserveAmount(((d.getOutstandingReserveAmount() == null ? 0 : d.getOutstandingReserveAmount())+v));
			}else{
				r.setReleasedReserveAmount(v);
				d.setOutstandingReserveAmount(((d.getOutstandingReserveAmount() == null ? 0 : d.getOutstandingReserveAmount())-v));
			}
			d.setModifiedBy(SystemUtil.getCurrentUserId());
			d.setModifiedTimestamp(new Date());
			reconcileDao.save(r);
			disputeDao.merge(d);
		} catch (Exception e) {
			logger.error("updateIncreaseAndDecrease error: ", e);
			throw new RuntimeException(e);
		}
		logger.info("Exit method updateIncreaseAndDecrease.");
		return d.getOutstandingReserveAmount();
	}

	public IInvoiceDetailDao getInvoiceDetailDao() {
		return invoiceDetailDao;
	}

	public void setInvoiceDetailDao(IInvoiceDetailDao invoiceDetailDao) {
		this.invoiceDetailDao = invoiceDetailDao;
	}

	public Double getOutstandingReserveAmount(String disputeId) throws BPLException {
		logger.info("Enter method getOutstandingReserveAmount.");
		Double result = 0d;
		try {
			result = disputeDao.getOutstandingReserveAmount(disputeId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BPLException(e.getMessage());
		}
		logger.info("Exit method getOutstandingReserveAmount.");
		return result;
	}

	public Double getDisputeAmount(String disputeId)
			throws BPLException {
		logger.info("Enter method getDisputeAmount.");
		Double result = 0d;
		try {
			result = disputeDao.getDisputeAmountByProposal(Integer.parseInt(disputeId == null ? "0" : disputeId));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BPLException(e.getMessage());
		}
		logger.info("Exit method getDisputeAmount.");
		return result;
	}

	public void updateDisputeAmount(String disputeId) {
		logger.info(CcmFormat.formatEnterLog("Update dispute Amount", disputeId));
		try{
			disputeDao.updateDisputeAmountByProposal(disputeId);
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	public String validateReconcileByDispute(String disputeId) throws BPLException {
		logger.info("Enter method validateReconcileByDispute.");
		String result = "{}";
		try{
			boolean b = true;
//			if (!b) {
//				Double amount1 = disputeDao.getReconcileByDispute1(disputeId);
//				if (amount1 > 0) {
//					b = true;
//				}
//			}
//			if (!b) {
//				Double amount2 = disputeDao.getReconcileByDispute2(disputeId);
//				if (amount2 > 0) {
//					b = true;
//				}
//			}
//			if (!b) {
//				Double amount3 = disputeDao.getReconcileByDispute3(disputeId);
//				if (amount3 > 0) {
//					b = true;
//				}
//			}
			long amount = disputeDao.getReconcileByDisputeFor3(disputeId);
			if (amount > 0) {
				b = false;
			}
			result = "{result:"+b+"}";
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return result;
	}
	
	public String searchDisputeCountByDays(WorkspaceVO wvo) throws BPLException{
		
		logger.info(CcmFormat.formatEnterLog("get Dispute Work Count total No", wvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = disputeDao.searchDisputeCountByDays(wvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) wvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
		
	}
	
	public String searchDisputeByDays(WorkspaceVO wvo) throws BPLException{
		
		logger.info("Enter method searchDisputeByDays.");
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = disputeDao.searchDisputeByDays(wvo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list!=null&&list.size()>0){
			sb.append("{begin:");
			sb.append(wvo.getStartIndex()+1);
			sb.append(",end:");
			int size = list.size();
			sb.append(wvo.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = list.get(i);
				if(str == null){
					break;
				}else {
					if(str instanceof SerializableBlob){
						str = BlobUtil.getBlobString((SerializableBlob)str);
					}
					if(i!=0) sb.append(",");
					sb.append(str.toString());
				}
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info("Exit method searchDisputeByDays.");
		return sb.toString();
		
	}
	
	public String getDisputeExcel(WorkspaceVO wvo, String excelDirPath,
			List<String> titles,List<Integer> ids) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = disputeDao.searchDisputeCountByDays(wvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "disputes");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				wvo.setPageNo(1);
				wvo.setRecPerPage((int) count);
				list = disputeDao.searchDisputeByExcelDays(wvo, ids);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					wvo.setPageNo(j + 1);
					wvo.setRecPerPage(recPerPage);
					list = disputeDao.searchDisputeByExcelDays(wvo, ids);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	private String getDefaultEmailMessage(Dispute dispute){
		StringBuffer sb = new StringBuffer("Dear "+dispute.getInvoice().getVendor().getVendorName()+",\n\n");
		sb.append("This is a follow up to dispute "+dispute.getDisputeNumber()+", claim "+dispute.getClaimNumber()+" filed on BAN "+dispute.getInvoice().getBan().getAccountNumber()+" Invoice "+dispute.getInvoice().getInvoiceNumber()+".");
		sb.append("\n\n\n\n");
		sb.append("Please reply to tems.dispute@saninco.com");
		return sb.toString();
	}

    public void updateTimerManager(Integer id,String flag){
    	disputeDao.updateTimerManager(id, flag);
    }
	
	public String findTimerManager(Integer id){
		return disputeDao.findTimerManager(id);
	}
	
	public Integer findUpdateTimerManagerProcesslist(){
		return disputeDao.findUpdateTimerManagerProcesslist();
	}
}
