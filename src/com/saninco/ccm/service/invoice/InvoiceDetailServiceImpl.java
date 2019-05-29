/**
 * 
 */
package com.saninco.ccm.service.invoice;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.AuthenticationFailedException;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.IDisputeDao;
import com.saninco.ccm.dao.IInvoiceDao;
import com.saninco.ccm.dao.IInvoiceDetailDao;
import com.saninco.ccm.dao.IInvoiceStatusDao;
import com.saninco.ccm.dao.IIvoiceNoteDao;
import com.saninco.ccm.dao.IProposalDao;
import com.saninco.ccm.dao.IReconcileDao;
import com.saninco.ccm.dao.IReconcileStatusDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.dao.IWorkflowActionDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.AuditMemory;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.DisputeReason;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceItem;
import com.saninco.ccm.model.InvoiceLabel;
import com.saninco.ccm.model.InvoiceStatus;
import com.saninco.ccm.model.Label;
import com.saninco.ccm.model.Originator;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.model.ScoaSource;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.WorkflowAction;
import com.saninco.ccm.service.email.ISendEmailService;
import com.saninco.ccm.util.BlobUtil;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.ExcelFileUtil;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemEmailConfig;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author xinyu.Liu
 */
public class InvoiceDetailServiceImpl extends MsMessageServiceImpl implements IInvoiceDetailService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ISysConfigDAO sysConfigDAO;
	private IInvoiceDetailDao invoiceDetailDao; 
	private IIvoiceNoteDao invoiceNoteDao;
	private IInvoiceStatusDao invoiceStatusDao;
	private IReconcileStatusDao reconcileStatusDao;
	private IInvoiceDao invoiceDao;
	private IDisputeDao disputeDao;
	private IUserDao userDao;
	private IReconcileDao reconcileDao;
	private IProposalDao proposalDao;
	private IBanDao banDao;
	private IWorkflowActionDao workflowActionDao;
//	private NResultSetHelperService nresultSetHelper;
	private ISendEmailService sendEmailService;
	private IInvoiceService4BB invoiceService4BB;
	private SystemEmailConfig sec ;
	
	
	private static String INVOICE_ITEM_PROPOSAL_PAYMENT_AMOUNT = "Payment";
	private static String INVOICE_ITEM_PROPOSAL_DISPUTE_AMOUNT = "Dispute";
	private static String INVOICE_ITEM_PROPOSAL_NOT_AMOUNT = "Proposed";
	
	private static int DOWNLOAD_EXCEL_RECPERPAGE = 10000;
	private static int DOWNLOAD_EXCEL_MAXSIZE = 60000;

	public InvoiceDetailServiceImpl() {
	}
	
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query tab of the display data on the list
	 */
	public String searchListTabDataView(SearchInvoiceVO sio) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Query tab of the display data on the list", sio));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			if("Browse".equals(sio.getAccordionName())) l = invoiceDetailDao.searchListTabDataByBrowse(sio);
			if("SCOA".equals(sio.getAccordionName())) l = invoiceDetailDao.searchListTabDataBySCOA(sio);
			if("Disputed".equals(sio.getAccordionName())) l = invoiceDetailDao.searchListTabDataByDisputed(sio);
			if("ItemType".equals(sio.getAccordionName())){
				sio.setInvoiceItemId(getBoxInId(sio.getInvoiceItemId()));
				l = invoiceDetailDao.searchListTabDataByItemType(sio);
			} 
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{data:[");
			for(int i = 0; i < l.size(); i++){
				if(i != 0) sb.append(",");
				sb.append("{itemTypeName:\"");
				sb.append(l.get(0)[0]);
				sb.append("\",itemTypeId:\"");
				sb.append(l.get(0)[1]);
				sb.append("\",count:\"");
				sb.append(l.get(0)[2]);
				sb.append("\",totalAmount:\"");
				sb.append(l.get(0)[3]);
				sb.append("\",accordionName:\"");
				sb.append(sio.getAccordionName());
				sb.append("\"}");
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query ItemType accordion tab list data in a single
	 */
	public String searchItemTypeListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Query ItemType accordion tab list data in a single", searchInvoiceVO));
		Invoice invoice = new Invoice();
		StringBuffer sb = new StringBuffer();
		StringBuffer tableField = new StringBuffer();
		List<Object[]> l = null;
		List<Object[]> itemDisplayList = null;
		try {
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(searchInvoiceVO.getInvoiceId()));
			String invoiceStructureId = invoice.getInvoiceStructure() == null ? null : invoice.getInvoiceStructure().getId().toString();
			searchInvoiceVO.setInvoiceStructureCodeId(invoiceStructureId);
			
			//Need to query the database to obtain the field
			itemDisplayList = invoiceDetailDao.findItemDisplayTitleName(searchInvoiceVO);
			
			if(itemDisplayList != null && itemDisplayList.size() > 0){
				for(int i = 0; i < itemDisplayList.size(); i++){
					Object[] obj = itemDisplayList.get(i); 
					//String displayTitleName = obj[0].toString();
					String tableFieldName = obj[1].toString();
					String displayFormat =obj[2].toString();
					
					if(displayFormat.equals("amount")){
						tableField.append(""+tableFieldName+":\"',format(if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),2),");
					}else if(displayFormat.equals("number")){
						tableField.append(""+tableFieldName+":\"',if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),");
					}else {
						tableField.append(""+tableFieldName+":\"',toJSON(ii."+tableFieldName+" is null,'',ii."+tableFieldName+"),");
					}
					if(i+1 < itemDisplayList.size()) tableField.append("'\",");
				}
			}
			
			searchInvoiceVO.setTableField(tableField.toString());
			searchInvoiceVO.setInvoiceItemId(getBoxInId(searchInvoiceVO.getInvoiceItemId()));
			l = invoiceDetailDao.findTypeOpenAccByItemType(searchInvoiceVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInvoiceVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchInvoiceVO.getStartIndex()+size);
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
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query ItemType list of individual data on the total number of tab
	 */
	public String getItemTypeListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Accordion query ItemType list of individual data on the total number of tab", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			searchInvoiceVO.setInvoiceItemId(getBoxInId(searchInvoiceVO.getInvoiceItemId()));
			count = invoiceDetailDao.getItemTypeListTabNumber(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * Dispose uploaded scoa data
	 */
	public Map<String,String> uploadSCOAData( File uploadFile, String fileName, String errorExcelDirPath, String invoiceId ) {
		
		String batchNo = UUID.randomUUID().toString();
		List<Map<String,String>> dataList = null;
		List<String> columnList = null;
		Map<String,String> returnMap = new HashMap<String,String>();
		
		int columnLen = 0;
		
		String proposalIDColumnName = "";
		String scoaColumnName = "";
		
		try {   
			
			// Get all data from excel file
			Map map = ExcelFileUtil.getExcelDataList(uploadFile, fileName);
			
			if (map!=null) {
				
				// Template columns: [Proposal ID, SCOA]
				columnList = (List<String>) map.get("columnList");
				dataList = (List<Map<String, String>>) map.get("dataList");
				
				// Get length of excel column.
				columnLen = columnList.size();
				
				
				
				// The length of excel data list needs to be correct.
				if ( columnLen == 2 ) {
					
					/* Validating uploaded scoa data list
					   the uploaded file only can include two columns 'proposal id' and 'scoa'*/
					proposalIDColumnName = columnList.get(0);
					scoaColumnName = columnList.get(1);
					
					if ( proposalIDColumnName.equalsIgnoreCase("Proposal ID") && 
							scoaColumnName.equalsIgnoreCase("SCOA")) {
						
						// Insert all data into a temporary table.
						invoiceDetailDao.insertSCOADataTemporary(dataList, columnList, batchNo, invoiceId);
						
						
						// Query error data table.
						List<Map<String,Object>> errorList = invoiceDetailDao.queryTmpSCOAError();
						
						
						// Create error scoa data excel file.
						if(errorList!=null && errorList.size()>0){
							
							ExcelFileUtil.createErrorFile(uploadFile, fileName,columnList,errorList,errorExcelDirPath);
							returnMap.put("isSuccess", "N");
							returnMap.put("errorFilePath", errorExcelDirPath);
							
						}else{
							
							returnMap.put("isSuccess", "Y");
							
						} // /.[IF] scoa error list.
						
					} else {
						
						returnMap.put("isSuccess", null);
					}
					
					
					
					
				} else {
					
					returnMap.put("isSuccess", null);
					
				} // /.[IF] Excel scoa data list is valid or invalid.
				
				
				
				
				
				
			} // /. [IF] map is NULL or not.
			
        } catch (Exception e) {
        	
        	e.printStackTrace();
        	
        } // /. [try ... catch]
        
        return returnMap;
        
	}
	public String creatExcelByTemplate(String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "SCOA Template");
			jExcelUtil.writeTitle(0, titles);
			jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40 });
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
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query BrowseInvoice accordion tab list data in a single
	 */
	public String searchBrowseInvoiceListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Query BrowseInvoice accordion tab list data in a single", searchInvoiceVO));
		Invoice invoice = new Invoice();
		StringBuffer tableField = new StringBuffer();
		List<Object[]> l = null;
		List<Object[]> itemDisplayList = null;
		try {
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(searchInvoiceVO.getInvoiceId()));
			String invoiceStructureId = invoice.getInvoiceStructure() == null ? null : invoice.getInvoiceStructure().getId().toString();
			searchInvoiceVO.setInvoiceStructureCodeId(invoiceStructureId);
			
			//Need to query the database to obtain the field
			itemDisplayList = invoiceDetailDao.findItemDisplayTitleName(searchInvoiceVO);
			
			if(itemDisplayList != null && itemDisplayList.size() > 0){
				for(int i = 0; i < itemDisplayList.size(); i++){
					Object[] obj = itemDisplayList.get(i); 
					//String displayTitleName = obj[0].toString();
					String tableFieldName = obj[1].toString();
					String displayFormat =obj[2].toString();
					
					if(displayFormat.equals("amount")){
						tableField.append(""+tableFieldName+":\"',format(if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),2),");
					}else if(displayFormat.equals("number")){
						tableField.append(""+tableFieldName+":\"',if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),");
					}else {
						tableField.append(""+tableFieldName+":\"',toJSON(ii."+tableFieldName+" is null,'',ii."+tableFieldName+"),");
					}
					if(i+1 < itemDisplayList.size()) tableField.append("'\",");
				}
			}
			
			searchInvoiceVO.setTableField(tableField.toString());
			
			// 主要的查询语句。
			l = invoiceDetailDao.findTypeOpenAccByBrowseInvoice(searchInvoiceVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		StringBuffer sb = new StringBuffer();
		int j = 0;
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInvoiceVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchInvoiceVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = l.get(i);
				if(str != null){
					if (str instanceof Blob) {
						str = this.getBlobContent((Blob) str);
					}
					if(j++!=0) sb.append(",");
					sb.append(str.toString());
				}
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query BrowseInvoice list of individual data on the total number of tab
	 */
	public String getBrowseInvoiceListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Accordion query BrowseInvoice list of individual data on the total number of tab", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getBrowseInvoiceListTabNumber(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query DisputeItems accordion tab list data in a single
	 */
	public String searchDisputeItemsListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Query DisputeItems accordion tab list data in a single", searchInvoiceVO));
		Invoice invoice = new Invoice();
		StringBuffer sb = new StringBuffer();
		StringBuffer tableField = new StringBuffer();
		List<Object[]> l = null;
		List<Object[]> itemDisplayList = null;
		try {
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(searchInvoiceVO.getInvoiceId()));
			String invoiceStructureId = invoice.getInvoiceStructure() == null ? null : invoice.getInvoiceStructure().getId().toString();
			searchInvoiceVO.setInvoiceStructureCodeId(invoiceStructureId);
			
			//Need to query the database to obtain the field
			itemDisplayList = invoiceDetailDao.findItemDisplayTitleName(searchInvoiceVO);
			
			if(itemDisplayList != null && itemDisplayList.size() > 0){
				for(int i = 0; i < itemDisplayList.size(); i++){
					Object[] obj = itemDisplayList.get(i); 
					//String displayTitleName = obj[0].toString();
					String tableFieldName = obj[1].toString();
					String displayFormat =obj[2].toString();
					
					if(displayFormat.equals("amount")){
						tableField.append(""+tableFieldName+":\"',format(if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),2),");
					}else if(displayFormat.equals("number")){
						tableField.append(""+tableFieldName+":\"',if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),");
					}else {
						tableField.append(""+tableFieldName+":\"',toJSON(ii."+tableFieldName+" is null,'',ii."+tableFieldName+"),");
					}
					if(i+1 < itemDisplayList.size()) tableField.append("'\",");
				}
			}
			
			searchInvoiceVO.setTableField(tableField.toString());
			
			l = invoiceDetailDao.findTypeOpenAccByDisputeItems(searchInvoiceVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInvoiceVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchInvoiceVO.getStartIndex()+size);
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
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query DisputeItems list of individual data on the total number of tab
	 */
	public String getDisputeItemsListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Accordion query DisputeItems list of individual data on the total number of tab", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getDisputeItemsListTabNumber(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Query EmptySCOA accordion tab list data in a single
	 */
	public String searchEmptySCOAListTabView(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Query EmptySCOA accordion tab list data in a single", searchInvoiceVO));
		Invoice invoice = new Invoice();
		StringBuffer sb = new StringBuffer();
		StringBuffer tableField = new StringBuffer();
		List<Object[]> l = null;
		List<Object[]> itemDisplayList = null;
		try {
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(searchInvoiceVO.getInvoiceId()));
			String invoiceStructureId = invoice.getInvoiceStructure() == null ? null : invoice.getInvoiceStructure().getId().toString();
			searchInvoiceVO.setInvoiceStructureCodeId(invoiceStructureId);
			
			//Need to query the database to obtain the field
			itemDisplayList = invoiceDetailDao.findItemDisplayTitleName(searchInvoiceVO);
			
			if(itemDisplayList != null && itemDisplayList.size() > 0){
				for(int i = 0; i < itemDisplayList.size(); i++){
					Object[] obj = itemDisplayList.get(i); 
					//String displayTitleName = obj[0].toString();
					String tableFieldName = obj[1].toString();
					String displayFormat =obj[2].toString();
					
					if(displayFormat.equals("amount")){
						tableField.append(""+tableFieldName+":\"',format(if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),2),");
					}else if(displayFormat.equals("number")){
						tableField.append(""+tableFieldName+":\"',if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),");
					}else {
						tableField.append(""+tableFieldName+":\"',toJSON(ii."+tableFieldName+" is null,'',ii."+tableFieldName+"),");
					}
					if(i+1 < itemDisplayList.size()) tableField.append("'\",");
				}
			}
			
			searchInvoiceVO.setTableField(tableField.toString());
			
			l = invoiceDetailDao.findTypeOpenAcc(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(l));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Accordion query EmptySCOA list of individual data on the total number of tab
	 */
	public String getEmptySCOAListTabTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Accordion query EmptySCOA list of individual data on the total number of tab", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getEmptySCOAListTabNumber(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public Invoice getInvoiceObject(String invoiceId) throws BPLException{
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
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * update dispute_amount by proposal SUM(dispute_amount)
	 */
	public void updateDisputeAmount(SearchInvoiceVO sio) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("update dispute_amount by proposal SUM(dispute_amount)", sio));
		try {
			invoiceDetailDao.updateDisputeAmount(sio.getInvoiceId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Check the list of total number and amount of the total articles
	 */
	public String searchCountListService(SearchInvoiceVO sio) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Check the list of total number and amount of the total articles", sio));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = invoiceDetailDao.searchProposalCountList(sio,sio.getAccordionName());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{count:\"");
			sb.append(l.get(0)[0]);
			sb.append("\",paymentSum:\"");
			sb.append(l.get(0)[1]);
			sb.append("\",disputeSum:\"");
			sb.append(l.get(0)[2]);
			sb.append("\",creditSum:\"");
			sb.append(l.get(0)[3]);
			sb.append("\"");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * The need to display the page list column in the database to find out
	 */
	public String searchSCOACodingTabView(SearchInvoiceVO sio) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("The need to display the page list column in the database to find out", sio));
		Invoice invoice = new Invoice();
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(sio.getInvoiceId()));
			String invoiceStructureId = invoice.getInvoiceStructure() == null ? null : invoice.getInvoiceStructure().getId().toString();
			sio.setInvoiceStructureCodeId(invoiceStructureId);
	
			list = invoiceDetailDao.findSCOACodingTabLists(sio);
			sb.append("{cols:[");
			if(list!=null && list.size()>0){
				for(int i = 0; i<list.size(); i++){
					if(i!=0) sb.append(",");
					sb.append(list.get(i).toString());
				}
			}
			sb.append("]}");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Be classified in the accordion tab in the number and data
	 */
	public String getAccordionTab(SearchInvoiceVO sio) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Be classified in the accordion tab in the number and data", sio));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			if("Browse".equals(sio.getAccordionName())) l = invoiceDetailDao.getAccordionTabsByBrowse(sio);
			if("SCOA".equals(sio.getAccordionName())) {
				Integer invoiceStatusId = invoiceDetailDao.getInvoiceStatusIdByInvoiceId(sio.getInvoiceId());
				if (invoiceStatusId < 21) {
					l = invoiceDetailDao.getAccordionTabsBySCOA(sio);
				}
			}
			if("Disputed".equals(sio.getAccordionName())) l = invoiceDetailDao.getAccordionTabsByDisputed(sio);
			if("ItemType".equals(sio.getAccordionName())){
				sio.setInvoiceItemId(getBoxInId(sio.getInvoiceItemId()));
				l = invoiceDetailDao.getAccordionTabsByItemType(sio);
			} 
			sb.append(getListJson(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * According to Item_label generate accordion, check the list of total number and amount of the total articles
	 */
	public String searchCountListServiceByItem(SearchInvoiceVO sio) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Check the list of total number and amount of the total articles by ItemLabel", sio));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			sio.setInvoiceItemId(getBoxInId(sio.getInvoiceItemId()));
			l = invoiceDetailDao.searchProposalCountList(sio,sio.getAccordionName());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{count:\"");
			sb.append(l.get(0)[0]);
			sb.append("\",paymentSum:\"");
			sb.append(l.get(0)[1]);
			sb.append("\",disputeSum:\"");
			sb.append(l.get(0)[2]);
			sb.append("\",creditSum:\"");
			sb.append(l.get(0)[3]);
			sb.append("\",labelName:\"");
			sb.append(sio.getLabelName());
			sb.append("\"");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * Update the proposal of AttachmentPoint
	 */
	public void updateProposalByAttachmentPoint(SearchInvoiceVO sio,AttachmentPoint attachmentPoint){
		logger.info(CcmFormat.formatEnterLog("Update the proposal of AttachmentPoint", sio,attachmentPoint));
		Proposal proposal = new Proposal();
		proposal = invoiceDetailDao.findProposalById(Long.valueOf(sio.getProposalId()));
		proposal.setAttachmentPoint(attachmentPoint);
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Find the proposal of AttachmentPoint
	 */
	public AttachmentPoint findAttachmentPoint(SearchInvoiceVO sio){
		logger.info(CcmFormat.formatEnterLog("Find the proposal of AttachmentPoint", sio));
		AttachmentPoint attachmentPoint = new AttachmentPoint();
		Proposal proposal = new Proposal();
		proposal = invoiceDetailDao.findProposalById(Long.valueOf(sio.getProposalId()));
		attachmentPoint = proposal.getAttachmentPoint();
		logger.info(CcmFormat.formatExitLog());
		return attachmentPoint;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * For the proposal to add or create the object AttachmentPoint
	 */
	public void updateInvoiceProposalPoint(SearchInvoiceVO sio,AttachmentPoint attachmentPoint){
		logger.info(CcmFormat.formatEnterLog("For the proposal to add or create the object AttachmentPoint", sio,attachmentPoint));
		List<Object[]> list = null;
		StringBuffer sbNo = new StringBuffer();
		StringBuffer sbYes = new StringBuffer();
		try {
			if ("".equals(sio.getBoxInId())) {
				Proposal proposal = new Proposal();
				proposal = invoiceDao.findProposalById(Long.valueOf(sio.getProposalId()));
				sio.setBoxInId(sio.getProposalId());
				list = invoiceDao.searchInvoiceProposalPointByObject(sio);
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = list.get(i);
					if ("".equals(obj[1])) {
						proposal.setAttachmentPoint(attachmentPoint);
					} else {
						invoiceDao.copyInvoiceProposalPoint(attachmentPoint.getId().toString(), obj[1].toString());
					}
				}
			}else{
				sio.setBoxInId(getBoxInId(sio.getBoxInId()));
				list = invoiceDao.searchInvoiceProposalPointByObject(sio);
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = list.get(i);
					if ("".equals(obj[1])) sbNo.append(obj[0] + ",");
					if (!"".equals(obj[1])) sbYes.append(obj[1] + ",");
				}
				if(!"".equals(sbNo.toString())){
					
					String proposalInId =  getBoxInId(sbNo.toString());
					String[] inId = proposalInId.split(",");
					for(int i = 0; i < inId.length; i++){
						AttachmentPoint ap = new AttachmentPoint();
						ap.setTableName(attachmentPoint.getTableName());
						ap.setTableIdValue(attachmentPoint.getTableIdValue());
						ap.setCreatedBy(attachmentPoint.getCreatedBy());
						ap.setCreatedTimestamp(attachmentPoint.getCreatedTimestamp());
						ap.setModifiedBy(attachmentPoint.getModifiedBy());
						ap.setModifiedTimestamp(attachmentPoint.getModifiedTimestamp());
						ap.setRecActiveFlag("Y");
						invoiceDao.saveAttachmentPoint(ap);
						
						List<AttachmentFile> l = invoiceDao.findAttachmentFileByProperty("attachmentPoint", attachmentPoint);
						for(int j = 0; j < l.size(); j++){
							AttachmentFile attachmentFile = new AttachmentFile();
							attachmentFile = l.get(j);
							AttachmentFile af = new AttachmentFile();
							af.setAttachmentPoint(ap);
							af.setFileName(attachmentFile.getFileName());
							af.setFilePath(attachmentFile.getFilePath());
							af.setCreatedBy(attachmentFile.getCreatedBy());
							af.setCreatedTimestamp(attachmentFile.getCreatedTimestamp());
							af.setModifiedBy(attachmentFile.getModifiedBy());
							af.setModifiedTimestamp(attachmentFile.getModifiedTimestamp());
							af.setRecActiveFlag("Y");
							
							invoiceDao.saveAttachmentFile(af);
						}
						sio.setAttachmentPointId(ap.getId());
						invoiceDao.updateInvoiceProposalPointId(sio,inId[i]);
					}
				}
				if(!"".equals(sbYes.toString())){
					String[] proposalPoint = sbYes.toString().split(",");
					for (int i = 0; i < proposalPoint.length; i++) {
						invoiceDao.copyInvoiceProposalPoint(attachmentPoint.getId().toString(), proposalPoint[i].toString());
					}
				}
				
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	

	/**
	 * Traverse the list
	 * 
	 */
	private List<String> traverseRemovalList(List<String> titles){
		for(int i = 0; i < titles.size(); i++){
			if("Edit".equals(titles.get(i)) || "Download".equals(titles.get(i))){
				titles.remove(i);
			}
		}
		return titles;
	}
	
	/**
	 * Create List
	 * 
	 */
	private List<String> createList(String title){
		String[] split = title.split("&titles=");
		List<String> titles = new ArrayList<String>();
		for(int i = 0; i < split.length; i++){
			if((!"".equals(split[i])) && (!"Edit".equals(split[i])) && (!"Download".equals(split[i]))){
				titles.add(split[i]);
			}
		}
		return titles;
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * create SOCA and Dispute To Excel
	 */
	public String createDownloadSOCADisputeToExcel(SearchInvoiceVO sio,String excelDirPath,List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Download SOCA Dispute To Excel", sio,excelDirPath,titles));
		List<Object[]> itemDisplayList = null;
		
		
		String invoiceId = sio.getInvoiceId();
		Invoice invoiveModel = this.getInvoiceByInvoiceId(invoiceId);
		
		// Invoice Number.
		String invoiceNumber = invoiveModel.getInvoiceNumber();
		String sheetName = invoiceNumber;
		
		String itemTypeId = sio.getItemTypeId()!=null?sio.getItemTypeId():"";
		if(itemTypeId.length() > 2){
			itemTypeId = itemTypeId.substring(0, 1);
		}
		
		if("1".equals(itemTypeId) || "11".equals(itemTypeId)){
			sheetName = sheetName+"-Payment";
		}else if("2".equals(itemTypeId) || "12".equals(itemTypeId)){
			sheetName = sheetName+"-LPC";
		}else if("3".equals(itemTypeId) || "13".equals(itemTypeId)){
			sheetName = sheetName+"-MRC";
		}else if("4".equals(itemTypeId) || "14".equals(itemTypeId)){
			sheetName = sheetName+"-Usage";
		}else if("5".equals(itemTypeId) || "15".equals(itemTypeId)){
			sheetName = sheetName+"-OCC";
		}else if("6".equals(itemTypeId) || "16".equals(itemTypeId)){
			sheetName = sheetName+"-Adjustment";
		}else if("7".equals(itemTypeId) || "17".equals(itemTypeId)){
			sheetName = sheetName+"-Credit";
		}else if("8".equals(itemTypeId) || "18".equals(itemTypeId)){
			sheetName = sheetName+"-Tax";
		}
		
		StringBuffer tableField = new StringBuffer();
		if(sio.getInvoiceItemId() != null){
			sio.setInvoiceItemId(getBoxInId(sio.getInvoiceItemId()));
		}
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int pageNumber = 0;
			if((sio.getItemTypeId() == null) || ("null".equals(sio.getItemTypeId()))) count = invoiceDetailDao.getNumberOfSOCADisputeList(sio);
			if((sio.getItemTypeId() != null) && (!"null".equals(sio.getItemTypeId()))) count = invoiceDetailDao.getEmptySCOAListTabNumber(sio);

			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(pageNumber,sheetName);
			jExcelUtil.writeTitle(0, traverseRemovalList(titles));
			//jExcelUtil.writeTitle(0, createList(title));

			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				sio.setPageNo(1);
				sio.setRecPerPage((int)count);
				if((sio.getItemTypeId() == null) || ("null".equals(sio.getItemTypeId()))) {
					// 主查询方法 （下载文件中的列表信息）。
					// 这个是查找 allTab 下的明细信息。
					list = invoiceDetailDao.getSOCADisputeListOfObject(sio);
				}else{
					Invoice invoice = new Invoice();
					invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(sio.getInvoiceId()));
					String invoiceStructureId = invoice.getInvoiceStructure() == null ? null : invoice.getInvoiceStructure().getId().toString();
					sio.setInvoiceStructureCodeId(invoiceStructureId);
					
					//Need to query the database to obtain the field
					itemDisplayList = invoiceDetailDao.findItemDisplayTitleName(sio);
					
					if(itemDisplayList != null && itemDisplayList.size() > 0){
						for(int i = 0; i < itemDisplayList.size(); i++){
							Object[] obj = itemDisplayList.get(i); 
							String tableFieldName = obj[1].toString();
							String displayFormat =obj[2].toString();
							
							if(displayFormat.equals("amount")){
								tableField.append(",format(if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),2)");
							}else if(displayFormat.equals("number")){
								tableField.append(",if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+")");
							}else {
								tableField.append(",if(ii."+tableFieldName+" is null,'',ii."+tableFieldName+")");
							}
						}
					}
					sio.setTableField(tableField.toString());
					// 除了 allTab和USAGE(usage多了Terminating Province列)以外的其他标签页下的明细列表信息
					// 因为 MRC 和其他的audit source 
					// 如： ACC， Tax， OCC，的itemTypeId都是不相同的。
					list = invoiceDetailDao.getSOCADisputeListOfObjectByitemTypeId(sio);
				}
				
				// 向 excel 的表格中写入数据，
				// 一行一行的写入。
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}

			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for(int j = 0 ; j < totlePage; j++){
					sio.setPageNo(j + 1);
					sio.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					if((sio.getItemTypeId() == null) || ("null".equals(sio.getItemTypeId()))) list = invoiceDetailDao.getSOCADisputeListOfObject(sio);
					if((sio.getItemTypeId() != null) && (!"null".equals(sio.getItemTypeId()))) list = invoiceDetailDao.getSOCADisputeListOfObjectByitemTypeId(sio);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{30,30,30,30,40,40,30,30,30,30,50,30,30,30,30,30,30,30,30,30,30,30,30,30});
							jExcelUtil.createWritableSheet(pageNumber, sheetName);
							jExcelUtil.setWritableSheet(pageNumber);
						}
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + i) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), list.get(i)); 
						}
					}else{
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1, list.get(i)); 
						}
					}
				}
			}

			jExcelUtil.setColumnView(new int[]{30,30,30,30,40,40,30,30,30,30,50,30,30,30,30,30,30,30,30,30,30,30,30,30,
					30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,
					30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	/**
	 * @author mingyang.Li (viewSocaDispute.js) 
	 * 
	 * createDownloadSOCADisputeForBrowseInvoiceToExcel
	 */
//	public String createDownloadInvoiceDteailToExcel(SearchInvoiceVO sio,String excelDirPath) throws BPLException {
//		logger.info(CcmFormat.formatEnterLog("createDownloadSOCADisputeForBrowseInvoiceToExcel", sio,excelDirPath));
//		try {
//			JExcelUtil jExcelUtil = new JExcelUtil();
//			jExcelUtil.createWritableWorkbook(excelDirPath);
//			if(sio.getItemTypeId() == null){
//				List<Object> itemTypes = invoiceDetailDao.getInvoiceItemTypeIds(sio);
//				if( itemTypes != null && itemTypes.size() != 0){
//					for(int i=0;i<itemTypes.size();i++){
//						List<String> titles = new ArrayList<String>();
//						int[] columnView = new int[100];
//						StringBuffer sb = new StringBuffer("select ");
////						StringBuffer orderBySql = new StringBuffer(" order by ");
//						Object[] obj = (Object[]) itemTypes.get(i); 
//						String itemTypeId = obj[0].toString();
//						String invoiceStructureId =obj[1].toString();
//						String sheetName =obj[2].toString();
//						List<Object[]> excelColumns = invoiceDetailDao.findItemDisplayTitleNameDefult(itemTypeId,invoiceStructureId);
//						for(int j = 0; j < excelColumns.size(); j++){
//							Object[] excelColumn = excelColumns.get(j); 
//							
//							String displayTitleName = excelColumn[0].toString();
//							String tableFieldName = excelColumn[1].toString();
////							String displayFormat = excelColumn[2].toString();
////							String displayOrder = excelColumn[3].toString();
//							Integer displayWidth = (Integer)excelColumn[4];
//							titles.add(displayTitleName);
//							columnView[j] = displayWidth;
//							if(j+1 != excelColumns.size()){
//								sb.append(tableFieldName+",");
//							}else{
//								sb.append(tableFieldName);
//							}
//				
//						}
//						jExcelUtil = createJexcelUtilSheet(jExcelUtil,sio,excelDirPath,titles,columnView,i,sb,sheetName,itemTypeId);
//					}
//				}
//			}
////			if((sio.getItemTypeId() == null) || ("null".equals(sio.getItemTypeId()))) count = invoiceDetailDao.getNumberOfSOCADisputeList(sio);
////			if((sio.getItemTypeId() != null) && (!"null".equals(sio.getItemTypeId()))) count = invoiceDetailDao.getEmptySCOAListTabNumber(sio);
//			jExcelUtil.write();
//			jExcelUtil.close();
//		} catch (Throwable  e) {
//			logger.error(CcmFormat.formatErrorLog(e));
//			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
//			throw bpe;
//		}
//		logger.info(CcmFormat.formatExitLog());
//		return excelDirPath;
//	}
//	private JExcelUtil createJexcelUtilSheet(JExcelUtil jExcelUtil,SearchInvoiceVO sio,String excelDirPath,List<String> titles,int[] displayWidths,int sheetNumber,StringBuffer sql,String sheetName,String itemTypeId) throws RowsExceededException, WriteException, IOException{
//		jExcelUtil.createWritableSheet(sheetNumber,sheetName);
//		jExcelUtil.setWritableSheet(sheetNumber);
//		jExcelUtil.writeTitle(0, traverseRemovalList(titles));
//		List<Object[]> list = invoiceDetailDao.getSOCADisputeForBrowseInvoiceListOfObject(sql,sio.getInvoiceId(),itemTypeId);
//		for(int i=0;i<list.size();i++){
//			jExcelUtil.writeLine(i+1, list.get(i));
//		}
//		jExcelUtil.setColumnView(displayWidths);
//		return jExcelUtil;
//	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * According to automatically generate multiple accordion ItemLabel
	 */
	public String findAccordionList(SearchInvoiceVO sio) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("According to automatically generate multiple accordion ItemLabel", sio));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		String labelId = "";
		String labelName = "";
		Integer count = 0;
		double paymentSum = 0;
		double disputeSum = 0;
		double creditSum = 0;
		StringBuffer invoiceItemId = new StringBuffer();
		try {
			l = invoiceDetailDao.findItemLabelListByInvoiceId(sio);
			if (l != null && l.size() > 0 ) {
				sb.append("{data:[");
				for (int i = 0; i < l.size(); i++) {
					Object[] str = l.get(i);
					if(l.size() == 1) {
						sb.append("{label:\""+str[0]+"\",labelName:\""+str[2]+"\",invoiceItemId:\""+str[1]+"\",count:\""+str[3]+"\",paymentSum:\""+str[4]+"\",disputeSum:\""+str[5]+"\",creditSum:\""+str[6]+"\"}");
					}else{
						if(i == 0){
							labelId = str[0].toString();
							labelName = str[2].toString();
							count = Integer.parseInt(str[3].toString());
							paymentSum = Double.valueOf(str[4].toString());
							disputeSum = Double.valueOf(str[5].toString());
							creditSum = Double.valueOf(str[6].toString());
							invoiceItemId.append(str[1].toString());
							invoiceItemId.append(",");
						}else{
							if(labelId.equals(str[0].toString())){
								count += Integer.parseInt(str[3].toString());
								paymentSum += Double.valueOf(str[4].toString());
								disputeSum += Double.valueOf(str[5].toString());
								creditSum += Double.valueOf(str[6].toString());
								invoiceItemId.append(str[1].toString());
								invoiceItemId.append(",");
								if(i + 1 >= l.size()){
									sb.append("{label:\""+labelId+"\",labelName:\""+labelName+"\",invoiceItemId:\""+invoiceItemId.toString()+"\",count:\""+count+"\",paymentSum:\""+paymentSum+"\",disputeSum:\""+disputeSum+"\",creditSum:\""+creditSum+"\"}");
								}
							}else{
								sb.append("{label:\""+labelId+"\",labelName:\""+labelName+"\",invoiceItemId:\""+invoiceItemId.toString()+"\",count:\""+count+"\",paymentSum:\""+paymentSum+"\",disputeSum:\""+disputeSum+"\",creditSum:\""+creditSum+"\"},");
								if(i + 1 < l.size()){
									labelId = str[0].toString();
									labelName = str[2].toString();
									count = Integer.parseInt(str[3].toString());
									paymentSum = Double.valueOf(str[4].toString());
									disputeSum = Double.valueOf(str[5].toString());
									creditSum = Double.valueOf(str[6].toString());
									invoiceItemId = new StringBuffer();
									invoiceItemId.append(str[1].toString());
									invoiceItemId.append(",");
								}else{
									sb.append("{label:\""+str[0]+"\",labelName:\""+str[2]+"\",invoiceItemId:\""+str[1]+"\",count:\""+str[3]+"\",paymentSum:\""+str[4]+"\",disputeSum:\""+str[5]+"\",creditSum:\""+str[6]+"\"}");
								}
							}
						}
					}
				}
				sb.append("]");
			} else {
				sb.append("{error:\"failed to query.\"");
			}
			sb.append("}");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	 }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * update group Proposal Data By ProposaBoxlId
	 */
	public void updateGroupProposalDataByProposalBoxId(SearchInvoiceVO sio){
		logger.info(CcmFormat.formatEnterLog("update group Proposal Data By ProposaBoxlId", sio));
		try {
			sio.setBoxInId(getBoxInId(sio.getBoxInId()));
			if("payment".equals(sio.getRadio())){
				invoiceDetailDao.updateGroupDisputeProposalByPayment(sio, SystemUtil.getCurrentUserId());
			}
			if("dispute".equals(sio.getRadio())){
				invoiceDetailDao.updateGroupDisputeProposalByDispute(sio, SystemUtil.getCurrentUserId());
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * update SCOACoding And Note
	 */
	public void updateSCOACodingAndNote(SearchInvoiceVO sio){
		logger.info(CcmFormat.formatEnterLog("update SCOACoding And Note", sio));
		try {
			sio.setBoxInId(getBoxInId(sio.getBoxInId()));
			invoiceDetailDao.updateSCOACodingAndNoteData(sio, SystemUtil.getCurrentUserId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * A data update multiple SCOA and notes
	 */
	public void updateSCOACodingSingleAndNote(SearchInvoiceVO sio){
		logger.info(CcmFormat.formatEnterLog("A data update multiple SCOA and notes", sio));
		Proposal proposal = new Proposal();
		double amount = 0.0;
		try {

			sio.setBoxInId(getBoxInId(sio.getBoxInId()));
			String[] single = sio.getSingle().split(",");
			
			for(int i = 0; i < single.length; i+=2){
				invoiceDetailDao.updateSCOACodingSingleAndNoteData(sio,single[i].toString(),single[i+1].toString());
				amount += Double.valueOf(single[i+1].toString());
			}
			
			proposal = proposalDao.findById(Long.valueOf(sio.getBoxInId()));
			proposal.setPaymentAmount(proposal.getPaymentAmount() - amount);
			proposal.setModifiedBy(SystemUtil.getCurrentUserId());
			proposal.setModifiedTimestamp(new Date());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Updated proposal of credirAmount
	 */
	public void updateProposalToCreditAmount(SearchInvoiceVO sio){
		logger.info(CcmFormat.formatEnterLog("update Proposal of CreditAmount", sio));
		try {
			sio.setBoxInId(getBoxInId(sio.getBoxInId()));
			invoiceDetailDao.updateMoveToCreditProposal(sio, SystemUtil.getCurrentUserId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * update Proposal Data By ProposalId
	 */
	public void updateProposalDataByProposalId(SearchInvoiceVO sio){
		logger.info(CcmFormat.formatEnterLog("update Proposal Data By ProposalId", sio));
		Proposal proposal = new Proposal();
		ScoaSource scoaSource=new ScoaSource();
		AccountCode accountCode = new AccountCode();
		Originator originator = new Originator();
		try {
			proposal = invoiceDetailDao.findProposalById(Long.valueOf(sio.getProposalId()));
			//update ScoaCode
			if(!"".equals(sio.getScoaCodeId())){
				accountCode = invoiceDetailDao.findAccountCodeById(Integer.parseInt(sio.getScoaCodeId()));
				proposal.setAccountCode(accountCode);
				scoaSource.setId(740101);
				proposal.setScoaSource(scoaSource);
			}
			//update Originator
			if(!"".equals(sio.getOriginatorId())){
				originator = invoiceDetailDao.findOriginatorById(Integer.parseInt(sio.getOriginatorId()));
				proposal.setOriginator(originator);
			}else{
				proposal.setOriginator(null);
			}
			
			if(sio.getDisputeReasonId() != null){
				if(proposal.getDisputeReason() != null){
					if(!sio.getDisputeReasonId().equals(proposal.getDisputeReason().getId().toString())){
						DisputeReason disputeReason = new DisputeReason();
						disputeReason = invoiceDetailDao.findDisputeReasonById(Integer.parseInt(sio.getDisputeReasonId()));
						proposal.setDisputeReason(disputeReason);
						proposal.setDispute(null);
					}
				}else{
					DisputeReason disputeReason = new DisputeReason();
					disputeReason = invoiceDetailDao.findDisputeReasonById(Integer.parseInt(sio.getDisputeReasonId()));
					proposal.setDisputeReason(disputeReason);
					proposal.setDispute(null);
				}
			}
			Double newAmount = Double.valueOf(sio.getDisputeAmount());
			if(newAmount == 0.0) proposal.setDisputeReason(null);
			proposal.setPaymentAmount(Double.valueOf(sio.getPaymentAmount()));
			Double amountChang = newAmount - (proposal.getDisputeAmount() == null ? 0 : proposal.getDisputeAmount());
			proposal.setDisputeAmount(newAmount);
			proposal.setNotes(sio.getDescription());
			proposal.setModifiedBy(SystemUtil.getCurrentUserId());
			proposal.setModifiedTimestamp(new Date());
			Dispute d = proposal.getDispute();
			if(d != null){
				d.setDisputeAmount((d.getDisputeAmount() == null ? 0d : d.getDisputeAmount()) + amountChang);
				d.setReserveAmount(getReserveAmount(d.getConfidenceLevel(),d.getDisputeAmount()));
				d.setOutstandingReserveAmount(d.getReserveAmount());
				disputeDao.merge(d);
			}
			proposalDao.merge(proposal);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	private Double getReserveAmount(Integer confidenceLevel,Double disputeAmount) {
        confidenceLevel = (confidenceLevel == null ? 0 : confidenceLevel);
		if(confidenceLevel > 0 && confidenceLevel < 100){
			if(confidenceLevel.intValue() >= 2){
				return disputeAmount*0.5;
			}else{
				return disputeAmount;
			}
		}else{
			return 0d;
		}
	}
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * update Invoice of ItemAmount
	 */
	public void updateInvoiceItemAmount(SearchInvoiceVO sio) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update Invoice of ItemAmount", sio));
		try {
			invoiceDetailDao.UpdateInvoiceItemTable(Integer.parseInt(sio.getInvoiceId()));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * search Proposal View List
	 */
	public String searchProposalList(SearchInvoiceVO searchInvoiceVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("search Proposal View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDetailDao.searchProposalList(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(l));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * search EmptySCOA View List
	 */
	public String searchEmptySCOAList(SearchInvoiceVO searchInvoiceVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("search EmptySCOA View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDetailDao.searchEmptySCOAList(searchInvoiceVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchInvoiceVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchInvoiceVO.getStartIndex()+size);
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
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * search DisputeItems View List
	 */
	public String searchDisputeItemsList(SearchInvoiceVO searchInvoiceVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("search DisputeItems View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDetailDao.searchDisputeItemsList(searchInvoiceVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchInvoiceVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchInvoiceVO.getStartIndex()+size);
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
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * search ItemType View List
	 */
	public String searchItemTypeList(SearchInvoiceVO searchInvoiceVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("search ItemType View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			searchInvoiceVO.setInvoiceItemId(getBoxInId(searchInvoiceVO.getInvoiceItemId()));
			l = invoiceDetailDao.searchItemTypeList(searchInvoiceVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchInvoiceVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchInvoiceVO.getStartIndex()+size);
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
	
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * get Proposal View List Page No
	 */
	public String getProposalViewListPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Proposal View List Page No", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getNumberOfProposals(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * get EmptySCOA View List Page No
	 */
	public String getEmptySCOAViewListPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get EmptySCOA View List Page No", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			Integer invoiceStatusId = invoiceDetailDao.getInvoiceStatusIdByInvoiceId(svo.getInvoiceId());
			if (invoiceStatusId < 21) {
				count = invoiceDetailDao.getNumberOfEmptySCOAs(svo);
			} else {
				count = 0;
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public boolean searchInvoiceStatus(String invoiceId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice Status", invoiceId));

		try {
			Integer invoiceStatusId = invoiceDetailDao.getInvoiceStatusIdByInvoiceId(invoiceId);
			if (invoiceStatusId == 9 || invoiceStatusId == 10) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	/**
	 * 对 external approve 回来的 Ban 进行处理。
	 * @param {String} BackValue [Approve | Reject]
	 * @param {Integer} invoiceId
	 */
	public void externalAproveBack(String backValue,Integer invoiceId,String workflowUserName,String userName,int userId,String openInterface,String externalEmailId,String notes) throws Exception {
		logger.info(CcmFormat.formatEnterLog("external Aprove Back"));
		
	    try{
	    	
			boolean status = searchInvoiceStatus(invoiceId+"");
			TransactionHistory th = new TransactionHistory();
			Map<String,String> invoiceMessage  = new HashMap<String, String>();
			sec = sysConfigDAO.getSystemEmailConfig();
			int workflowActionId ;
			Invoice invoice = getInvoiceByInvoiceId(invoiceId+"");
			User user = this.invoiceService4BB.findUserById(userId);	
			th.setInvoice(invoice);
			th.setWorkflowUserName(invoiceDetailDao.getExternalEmailUser(externalEmailId));
			invoiceMessage.put("To", user.getEmail());
			

			if("Approve".equals(backValue)) {
				workflowActionId = 91;
				// 更新某一张账单的 external approve flag
				invoiceDetailDao.updateExternalApproveFlagForCertainInvoice(invoiceId);
				invoiceMessage.put("Subject", "External Invoice Approval Notice - Invoice# "+invoice.getInvoiceNumber());
				invoiceMessage.put("message", "Hi " +userName+", <br/> Please be advised that invoice <a href = \""+sec.getSystemUrlAdress()+"viewInvoiceDetails.action?invoiceId="+invoiceId+"#NAME_pagePaymentScoaAmount\">"+invoice.getInvoiceNumber()+"</a> is now externally approved. <br/>You can log into TEMS for further action.<br/>Thanks");
			}else {
				workflowActionId = 92;
				// 更新某一张账单的 external approve flag
//				invoiceDetailDao.updateExternalApproveFlagForCertainInvoice(invoiceId);
    			invoiceMessage.put("Subject", "External Invoice Rejection Notice - Invoice# "+invoice.getInvoiceNumber());
    			invoiceMessage.put("message", "Hi " +userName+", <br/> Please be advised that invoice <a href = \""+sec.getSystemUrlAdress()+"viewInvoiceDetails.action?invoiceId="+invoiceId+"#NAME_pagePaymentScoaAmount\">"+invoice.getInvoiceNumber()+"</a> is rejected by external approver.<br/>Please log into TEMS for further action.<br/>Thanks");
    			
			}
			if (notes == null) {
				notes = "";
			}else {

				notes = new String(notes.getBytes("ISO-8859-1"),"UTF-8").replace("&lt;","<");
			}
			
			addTransactionHistory(th,openInterface , workflowActionId,workflowUserName,notes);
			updateExternalEmail(externalEmailId);
			sendEmailService.sendInvoiceEmailBack(invoiceMessage);
				

			
		}catch(Exception e){
			 logger.error(CcmFormat.formatErrorLog(e));
			 throw e;
		}
	}
	public Long getTransactionHistoryCount(Integer invoiceId) throws BPLException{
		
		logger.info(CcmFormat.formatEnterLog("get Transaction History No", invoiceId));
		long count = 0;
		try {
			count = invoiceDetailDao.getTransactionHistoryCount(invoiceId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return count;
	}
	
	public Long getExternalEmailCount(String externalEmailId) throws BPLException{
		
		logger.info(CcmFormat.formatEnterLog("get Transaction History No", externalEmailId));
		long count = 0;
		try {
			count = invoiceDetailDao.getExternalEmailCount(externalEmailId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return count;
	}
	
	public Long doViewOriginalList(Integer invoiceId) throws BPLException{
		
		logger.info(CcmFormat.formatEnterLog("get Transaction History No", invoiceId));
		long count = 0;
		try {
			count = invoiceDetailDao.doViewOriginalList(invoiceId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return count;
	}
	
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * get DisputeItems View List Page No
	 */
	public String getDisputeItemsViewListPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get DisputeItems View List Page No", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getNumberOfDisputeItems(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * get ItemType View List Page No
	 */
	public String getItemTypeViewListPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get ItemType View List Page No", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			svo.setInvoiceItemId(getBoxInId(svo.getInvoiceItemId()));
			count = invoiceDetailDao.getNumberOfItemType(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * get Invoice Total or Invoice Item Total
	 * 
	 */
	public String getInvoiceItemTotal(SearchInvoiceVO sio) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get InvoiceItem Total Page", sio));
		String str = "null";
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		List<Object[]> list = null;
		try {
			if(sio.getInvoiceItemId() != null){
				InvoiceItem invoiceItem = new InvoiceItem();
				invoiceItem = invoiceDetailDao.findInvoiceItemById(Long.valueOf(sio.getInvoiceItemId()));
				l = invoiceDetailDao.findInvoiceItemByInvoiceItemId(sio.getInvoiceItemId(),invoiceItem.getItemName(),invoiceItem.getInvoice().getId());
				str = getListJson(l);
			}else{
				list = invoiceDetailDao.findInvoiceItemTotalToInvoiceItem(Integer.parseInt(sio.getInvoiceId()));
				if(list.size() != 0 && list != null){
					for(int i = 0; i < list.size(); i++){
						Object[] obj = list.get(i);
						if(i != 0) sb.append(",");
						sb.append(obj[0]);
					}
				}
				l = invoiceDetailDao.findInvoiceItemByInvoiceId(Integer.parseInt(sio.getInvoiceId()),sb.toString());
				str = sio.getListJsonCompatibleByAll(l);
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return str;
	}
	
	/**
	 * Invoice Status changes for the invoice
	 * 
	 */
	public void updateInvoiceStatusId(Invoice invoice) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update InvoiceStatus", invoice));
		InvoiceStatus invoiceStatus = invoiceDetailDao.loadInvoiceStatusById(10);
		WorkflowAction wa = (WorkflowAction) invoiceDao.get(WorkflowAction.class, 3);
		invoice.setInvoiceStatus(invoiceStatus);
		invoice.setUserByWorkflowUserId(SystemUtil.getCurrentUser());
		invoice.setModifiedTimestamp(new Date());
		invoice.setStatusTimestamp(new Date());
		invoice.setModifiedBy(SystemUtil.getCurrentUserId());
		logger.info(CcmFormat.formatExitLog());
	}
	/**
	 * @author Chao.Liu
	 * This is Admin Page Tab Two
	 * @return
	 * @throws Exception
	 */
	public String saveInvoiceReject(SearchInvoiceVO searchInvoiceVO)   {
		logger.info(CcmFormat.formatEnterLog("This is Admin Page Tab Two", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		try {
			Invoice i = (Invoice) invoiceDao.get(Invoice.class, new Integer(searchInvoiceVO.getInvoiceId()));
			
			InvoiceStatus is = (InvoiceStatus) invoiceDao.get(InvoiceStatus.class, 15);
			User au = (User) invoiceDao.get(User.class, i.getAssignedAnalystId());
			WorkflowAction wa = (WorkflowAction) invoiceDao.get(WorkflowAction.class, 3);
			
			i.setNotes(searchInvoiceVO.getNote());
			i.setInvoiceStatus(is);
			i.setWorkflowAction(wa);
			i.setUserByAssignmentUserId(au);
			i.setUserByWorkflowUserId(SystemUtil.getCurrentUser());
			i.setModifiedBy(SystemUtil.getCurrentUserId());
			i.setModifiedTimestamp(new Date());
			i.getBan().setProcessStatus(null);
			i.setStatusTimestamp(new Date());
			i.setPaymentAmount(0d);
			i.setDisputeAmount(0d);
			i.setMiscAdjustmentAmount(0d);
			invoiceDetailDao.clearReconcilePaymentInvoiceId(i);
			
			invoiceDao.update(i);
			
			sb.append("{error:false}");
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method saveInvoiceReject.");
		return sb.toString();
	}
	/**
	 * LIST is written to the default data directly, the prevention of database Reed default data does not exist
	 * 
	 */
	private List itemDisplayTitleNameDefult(List l, String listName){
		logger.info("Enter method itemDisplayTitleNameDefult.");
		if(l ==null || l.size() <= 0){
			if("List<String>".equals(listName)){
				String s1 = "{id:0,filed:\"item_name\",title:\"Item\",format:\"string\",width:\"200\"}";
				String s2 = "{id:0,filed:\"item_amount\",title:\"Amount\",format:\"amount\",width:\"100\"}";
				l.add(s1);l.add(s2);
			}
			if("List<Object[]>".equals(listName)){
				Object[] o1 = new Object[]{"Item", "item_name", "string"};
				Object[] o2 = new Object[]{"Amount", "item_amount", "amount"};
				l.add(o1);l.add(o2);
			}
		}
		logger.info("Enter method itemDisplayTitleNameDefult.");
		return l;
	}
	
	/**
	 * Be displayed in the accordion in a list of names and other data
	 * 
	 */
	public String getItemDisplayListName(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info("Enter method getItemDisplayListName.");
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDetailDao.findInvoiceItemTypeListName(searchInvoiceVO);
			if(list == null || list.size() == 0){
				list = invoiceDetailDao.findInvoiceItemTypeListNameDefult("0","0");
				//If the database is not the default display data, then manually add the show
				list = itemDisplayTitleNameDefult(list,"List<String>");
			}
			sb.append("{typeName:\""+searchInvoiceVO.getTypeName()+"\",");
			sb.append("inst:\""+searchInvoiceVO.getInst()+"\",cols:[");
			if(list!=null && list.size()>0){
				for(int i = 0; i<list.size(); i++){
					if(i!=0) sb.append(",");
					sb.append(list.get(i).toString());
				}
			}
			sb.append("]}");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info("Exit method getItemDisplayListName.");
		return sb.toString();
	}
	
	/**
	 * The current invoice download all data into excel
	 * 
	 */
	public String createDownloadInvoiceToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("The current invoice download all data into excel", searchInvoiceVO,excelDirPath));

		// 获取 Invoice Number.
		String invoiceId = searchInvoiceVO.getInvoiceId();
		Invoice invoiveModel = this.getInvoiceByInvoiceId(invoiceId);

		// Invoice Number.
		String invoiceNumber = invoiveModel.getInvoiceNumber();
		
		
		try {
			List<Object[]> invoiceList = null;
			List<Object[]> itemTypeList = null;
			List<String> titles = new ArrayList<String>();
			JExcelUtil jExcelUtil = new JExcelUtil();
			Invoice invoice = new Invoice();
			long count = 0;
			int pageNumber = 0;
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			titles.add("Invoice Number");titles.add("Vendor");titles.add("Ban");titles.add("Line of Business");
			titles.add("Invoice Date");titles.add("Due Date");titles.add("Received Date");
			titles.add("From Date");titles.add("To Date");titles.add("Currency");
			titles.add("Status");titles.add("Analyst Name");
			titles.add("Usage");titles.add("Tax and Other Fees");titles.add("LPC");
			titles.add("MRC");titles.add("OCC");titles.add("Adjustment");
			titles.add("Credit");titles.add("Current Due");titles.add("Face Page Difference");
			titles.add("Prievious Balance");
			titles.add("Previous Payment");
			titles.add("Balance Forward");
			titles.add("Total Due");
			titles.add("Payment Amount");
			titles.add("Dispute Amount");
			titles.add("Misc Adjustment");
			
			
			jExcelUtil.writeTitle(0, titles);
			invoiceList = invoiceDetailDao.findInvoiceSummary(searchInvoiceVO.getInvoiceId());
			for(int i=0;i<invoiceList.size();i++){
				jExcelUtil.writeLine(i+1, invoiceList.get(i)); 
			}
			
			jExcelUtil.setColumnView(new int[]{23,48,21,21,23,22,22,22,22,22,22,22,33,20,20,20,23,23,23,32,30,30,28,26,26,26,28});
			
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(searchInvoiceVO.getInvoiceId()));
			itemTypeList = invoiceDetailDao.findItemTypeAndInvoiceStructure(searchInvoiceVO.getInvoiceId(),invoice.getBan().getId().toString());
			
			for(int i=0;i<itemTypeList.size();i++){
				Object[] obj = itemTypeList.get(i);
				String itemTypeId = obj[2].toString();
				String itemTypeName = obj[3].toString();
				String invoiceStructureId = obj[0].toString();
				String invoiceStructureName = obj[1].toString();
				
				searchInvoiceVO.setItemTypeId(itemTypeId);
				searchInvoiceVO.setInvoiceStructureCodeId(invoiceStructureId);
				
				// 在一个excel文件中，如果有多个sheet, sheet的名字就是
				// Invoice Number 的值后面加上Item Type.
				// 在这里使用了i变量是为了防止excel文件中的sheet重名。
				String str = invoiceNumber + " " + i + "-" +itemTypeName + " " + invoiceStructureName; // Sheets 名。
				
				titles = new ArrayList<String>();
				splitString(searchInvoiceVO);
				titles = searchInvoiceVO.getDisplayTitle();
				
				jExcelUtil.setWritableSheet(i+2);
				jExcelUtil.createWritableSheet(i+2, str);
				jExcelUtil.writeTitle(0, titles);
				
				count = invoiceDetailDao.findInvoiceItemListNumber(searchInvoiceVO);
				List<Object[]> invoiceItemList = null;
				if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
					searchInvoiceVO.setPageNo(1);
					searchInvoiceVO.setRecPerPage((int)count);
					invoiceItemList = invoiceDetailDao.findInvoiceItemListObject(searchInvoiceVO);
					for(int j2 = 0; j2 < invoiceItemList.size(); j2++){
						jExcelUtil.writeLine(j2 + 1, invoiceItemList.get(j2)); 
					}
				}else{

					long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
					for(int j = 0 ; j < totlePage; j++){
						searchInvoiceVO.setPageNo(j + 1);
						searchInvoiceVO.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
						invoiceItemList = invoiceDetailDao.findInvoiceItemListObject(searchInvoiceVO);
						pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
						if(pageNumber > 0){
							if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
								jExcelUtil.setColumnView(new int[]{40,40,40,40,40,40,40,40,40});
								jExcelUtil.createWritableSheet(pageNumber + i + 2, str + pageNumber);
								jExcelUtil.setWritableSheet(pageNumber + i + 2);
							}
							for(int j2 = 0; j2 < invoiceItemList.size(); j2++){
								jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + j2) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), invoiceItemList.get(j2)); 
							}
						}else{
							for(int j2 = 0; j2 < invoiceItemList.size(); j2++){
								jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + j2 + 1, invoiceItemList.get(j2)); 
							}
						}
					}
				}
				jExcelUtil.setColumnView(new int[]{40,40,40,40,40,40,40,40,40});
			}
			jExcelUtil.write();			
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	public String createInvoiceToExcel(String invoiceId,String excelDirPath) throws BPLException  {
		// Invoice Number.
		try {
			List<Object[]> invoiceList = null;
			List<String> titles = new ArrayList<String>();
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Invoice Summary");
			titles.add("Invoice Number");titles.add("Vendor");titles.add("Ban");titles.add("Line of Business");
			titles.add("Invoice Date");titles.add("Due Date");titles.add("Received Date");
			titles.add("From Date");titles.add("To Date");titles.add("Currency");
			titles.add("Status");titles.add("Analyst Name");
			titles.add("Usage");titles.add("Tax and Other Fees");titles.add("LPC");
			titles.add("MRC");titles.add("OCC");titles.add("Adjustment");
			titles.add("Credit");titles.add("Current Due");titles.add("Face Page Difference");
			titles.add("Prievious Balance");
			titles.add("Previous Payment");
			titles.add("Balance Forward");
			titles.add("Total Due");
			titles.add("Payment Amount");
			titles.add("Dispute Amount");
			titles.add("Misc Adjustment");
			jExcelUtil.writeTitle(0, titles);
			invoiceList = invoiceDetailDao.findInvoiceSummary(invoiceId);
			for(int i=0;i<invoiceList.size();i++){
				jExcelUtil.writeLine(i+1, invoiceList.get(i)); 
			}
			jExcelUtil.setColumnView(new int[]{23,48,21,21,23,22,22,22,22,22,22,22,33,20,20,20,23,23,23,32,30,30,28,26,26,26,28});
			
			// 创建明细 sheet
			List<Object[]> list = null;
			SearchInvoiceVO sio = new SearchInvoiceVO();
			sio.setAccordionName("Browse Invoice");
			sio.setInvoiceId(invoiceId);
			
			jExcelUtil.createWritableSheet(1,"SCOA and Dispute");
			jExcelUtil.setWritableSheet(1);
			
			titles = new ArrayList<String>();
			titles.add("Proposal Id");
			titles.add("Item");
			titles.add("Payment");
			titles.add("Dispute");
			titles.add("Credit");
			titles.add("PON");
		//	titles.add("Province Source");
			titles.add("Procinve");
			titles.add("Circuit number");
			titles.add("Stripped Circuit Number");
			titles.add("Product");
			titles.add("Component");
			titles.add("Billing number");
			titles.add("SCOA");
			titles.add("Dispute category");
			titles.add("Notes");
			titles.add("Description");
			titles.add("Address");
			titles.add("Service Type");
			titles.add("Charge Type");
			titles.add("USOC");
			titles.add("USOC Description");
			titles.add("Eo Direct Quantity");
			titles.add("Quantity");
			titles.add("Rate");
			titles.add("Minutes");
			titles.add("Number of Calls");
			titles.add("Cellular");
			titles.add("Country");
			titles.add("Mileage");
			titles.add("ASG");
			titles.add("Jurisdiction");
			titles.add("direction");
			titles.add("From Date");
			titles.add("End Date");
			titles.add("Dispute Number");
			titles.add("Dispute Notes");
			jExcelUtil.writeTitle(0, titles);

			// 主查询方法 （下载文件中的列表信息）。
			// 这个是查找 allTab 下的明细信息。
			list = invoiceDetailDao.getSOCADisputeListOfObject(sio);
			// 向 excel 的表格中写入数据，
			// 一行一行的写入。
			for(int i=0;i<list.size();i++){
				jExcelUtil.writeLine(i+1, list.get(i)); 
			}
			
			jExcelUtil.setColumnView(new int[]{30,30,30,30,40,40,30,30,30,30,50,30,30,30,30,30,30,30,30,30,30,30,30,30,
					30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,
					30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30});
			
			jExcelUtil.write();			
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	public List<Map<String,Object>> searchInvoiceOriginal(String invoiceId) throws BPLException {
		
		logger.info("Enter method searchInvoiceOriginal.");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = invoiceDetailDao.searchInvoiceOriginal(invoiceId);
		}
		catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method searchInvoiceOriginal.");
		return list;
	}
	public String downloadInvoiceValidationExcel(Invoice invoice,String excelDirPath) throws BPLException {
		logger.info("Enter method downloadInvoiceValidationExcel.");

		// 获取 Invoice Number.
		String invoiceId = invoice.getId().toString();
		String invoiceNumber = invoice.getInvoiceNumber();
		List<String> titles = new ArrayList<String>();
		List<Object[]> list = null;
			titles.add("Vendor Name");
			titles.add("Account Number");
			titles.add("Invoice Number");
			titles.add("Invoice Date");
			titles.add("Product");
			titles.add("Component");
			titles.add("Charge Type");
			titles.add("Circuit");
			titles.add("USOC");
			titles.add("USOC Description");
			titles.add("Audit Status");
			titles.add("Audit Method");
			titles.add("Audit Notes");
			titles.add("Actual Amount");
			titles.add("Expect Amount");
			titles.add("Quantity");
			titles.add("Actual Rate");
			titles.add("Expect Rate");
			titles.add("Effective Date");
			titles.add("Reference Type");
			titles.add("Reference Name");
			titles.add("Item Name");
			titles.add("Proposal Id");
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.selectInvoiceValidationByInvoiceIdCount(invoiceId);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				list = invoiceDetailDao.getInvoiceValidationToExcel(invoiceId);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = 1; //(count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					list = invoiceDetailDao.getInvoiceValidationToExcel(invoiceId);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
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
	
	public String downloadTelephoneNumberValidationExcel(Invoice invoice,String excelDirPath) throws BPLException {
		logger.info("Enter method downloadInvoiceValidationExcel.");
		
		// 获取 Invoice Number.
		String invoiceId = invoice.getId().toString();
		String invoiceNumber = invoice.getInvoiceNumber();
		List<String> titles = new ArrayList<String>();
		List<Object[]> list = null;
		titles.add("Line");
		titles.add("Telephone Number");
		titles.add("Action");
		titles.add("S\\E Indicator");
		titles.add("Collect Indicator");
		titles.add("Bill to Third Indicator");
		titles.add("Time Stamp");
		titles.add("Complete");
		titles.add("Validation Result");
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
//			long count = 0;
//			int recPerPage = 100;
//			count = invoiceDetailDao.selectTelephoneNumberValidationByInvoiceIdCount(invoiceId);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			
			list = invoiceDetailDao.getTelephoneNumberValidationToExcel(invoiceId);
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}
//			if (count <= recPerPage) {
//				list = invoiceDetailDao.getTelephoneNumberValidationToExcel(invoiceId);
//				for (int i = 0; i < list.size(); i++) {
//					jExcelUtil.writeLine(i + 1, list.get(i));
//				}
//			} else {
//				long totlePage = (count + recPerPage - 1) / recPerPage;
//				for (int j = 0; j < totlePage; j++) {
//					list = invoiceDetailDao.getTelephoneNumberValidationToExcel(invoiceId);
//					for (int i = 0; i < list.size(); i++) {
//						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
//					}
//				}
//			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
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
	
	
	/**
	 * create Invoice Detail To Excel
	 * 
	 */
	public String createInvoiceItemListToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("create InvoiceItem List To Excel", searchInvoiceVO,excelDirPath,titles));
		try {
			InvoiceItem invoiceItem = new InvoiceItem();
			List<Object[]> list = null;
			Object[] obj = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int pageNumber = 0;
			count = invoiceDetailDao.getInvoiceItemListOfInvoices(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(pageNumber,invoiceNumber);
			jExcelUtil.writeTitle(0, titles);

			if(searchInvoiceVO.getInvoiceItemId() != null){
				String id = searchInvoiceVO.getInvoiceItemId();
				invoiceItem = invoiceDetailDao.findInvoiceItemById(Long.valueOf(id));
				obj = invoiceDetailDao.findInvoiceItemByInvoiceItemIdObject(id,invoiceItem.getItemName(),invoiceItem.getInvoice().getId());
				jExcelUtil.writeLine(1, obj); 
			}else{
				obj = invoiceDetailDao.findInvoiceItemByInvoiceIdObject(Integer.parseInt(searchInvoiceVO.getInvoiceId()));
				jExcelUtil.writeLine(1, obj); 
			}
			
			jExcelUtil.writeTitle(3, titles);
			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int)count);
				list = invoiceDetailDao.findInvoiceItemListByObject(searchInvoiceVO);
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+4, list.get(i)); 
				}
			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for(int j = 0 ; j < totlePage; j++){
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					list = invoiceDetailDao.findInvoiceItemListByObject(searchInvoiceVO);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{20,20,20,20});
							jExcelUtil.createWritableSheet(pageNumber,invoiceNumber);
							jExcelUtil.setWritableSheet(pageNumber);
						}
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + i) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), list.get(i)); 
						}
					}else{
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + i + 4, list.get(i)); 
						}
					}
				}
			}
			jExcelUtil.setColumnView(new int[]{20,20,20,20});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	/**
	 * Split a string array 
	 * 
	 */
	private void splitString(SearchInvoiceVO searchInvoiceVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Split a string array ", searchInvoiceVO));
		List<Object[]> itemDisplayList = null;
		List<String> titles = new ArrayList<String>();
		StringBuffer tableField = new StringBuffer();
		itemDisplayList = invoiceDetailDao.findItemDisplayTitleName(searchInvoiceVO);
		if(itemDisplayList ==null || itemDisplayList.size() <= 0){
			itemDisplayList = invoiceDetailDao.findItemDisplayTitleNameDefult("0", "0");
			itemDisplayList = itemDisplayTitleNameDefult(itemDisplayList,"List<Object[]>");
		}
		for(int i = 0; i < itemDisplayList.size(); i++){
			Object[] obj = itemDisplayList.get(i); 
			String displayTitleName = obj[0].toString();
			String tableFieldName = obj[1].toString();
			String displayFormat =obj[2].toString();
			titles.add(displayTitleName);
			if(i != 0) tableField.append(",");
			if(displayFormat.equals("amount")){
			 	//modified by xinyu.Liu on 2010.7.28 for CCMUAT-67
				tableField.append("format(if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),2)");
			}else if(displayFormat.equals("number")){
				tableField.append("if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+")");
			}else{
				tableField.append("if(ii."+tableFieldName+" is null,'',ii."+tableFieldName+")");
			}
		}
		searchInvoiceVO.setTableField(tableField.toString());
		searchInvoiceVO.setDisplayTitle(titles);
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * create Invoice Detail To Excel
	 * 
	 */
	public String createInvoiceDetailToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("create Invoice Detail To Excel", searchInvoiceVO,excelDirPath,titles));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int pageNumber = 0;
			count = invoiceDetailDao.getInvoiceDetailNumberOfInvoices(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			splitString(searchInvoiceVO);
			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int)count);
				list = invoiceDetailDao.findTypeOpenAccByObject(searchInvoiceVO);
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for(int j = 0 ; j < totlePage; j++){
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					list = invoiceDetailDao.findTypeOpenAccByObject(searchInvoiceVO);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{20,20,20,20});
							jExcelUtil.createWritableSheet(pageNumber,invoiceNumber);
							jExcelUtil.setWritableSheet(pageNumber);
						}
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + i) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), list.get(i)); 
						}
					}else{
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1, list.get(i)); 
						}
					}
				}
			}
			jExcelUtil.setColumnView(new int[]{40,40,40,40,40,40,40,40,40,40,40,40,40,40,40});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	/**
	 * According to multiple id be InvoiceItem the Proposal list
	 * 
	 */
	public String getInvoiceItemGroupProposals(String boxInId) throws BPLException {
		logger.info("Enter method getInvoiceItemGroupProposals.");
		StringBuffer sb = new StringBuffer();
		List<Object[]> list = new ArrayList<Object[]>();
		List<String> listString = new ArrayList<String>();
		String[] boxId = boxInId.split(",");
		try {
			for(int i = 0; i < boxId.length; i++){
				String invoiceItemId = boxId[i];
				List<Object[]> l = invoiceDetailDao.searchInvoiceItemGroupProposals(invoiceItemId);
				for(int j = 0; j < l.size(); j++){
					//[{accountCode:"",payment:"382,046.30",dispute:"0.00",disputeCategory:"",disputeCategoryId:"",accountCodeId:""}]
					Object[] obj = l.get(j);
					boolean bo = true;
					if(i == 0){
						list.add(obj);
					}else{
						String disputeCategoryId = obj[1].toString();
						String accountCodeId = obj[0].toString();
						Double payment = (Double)obj[2];
						Double dispute = (Double)obj[3];
						String disputeCategoryName = obj[5].toString();
						String accountCodeName = obj[4].toString();
						for(int a = 0; a < list.size(); a++){
							Object[] objList = list.get(a);
							if(disputeCategoryId.equals(objList[1].toString()) && accountCodeId.equals(objList[0].toString())){
								payment += (Double)objList[2];
								dispute += (Double)objList[3];
								Object[] o = new Object[]{accountCodeId,disputeCategoryId,payment,dispute,accountCodeName,disputeCategoryName};
								list.set(a, o);
								bo = false;
								break;
							}
						}
						if(bo){
							list.add(obj);
						}
					}
				}
			}
			for(int i = 0; i < list.size(); i++){
				Object[] obj = list.get(i);
				String disputeAmount = CcmFormat.formatCurrency(Double.valueOf(obj[3].toString()));
				String paymentAmount = CcmFormat.formatCurrency(Double.valueOf(obj[2].toString()));
				String str = "{accountCode:\"" + obj[4].toString() + "\",payment:\"" + (paymentAmount == null ? "0.00" : paymentAmount) 
				+ "\",dispute:\"" + (disputeAmount == null ? "0.00" : disputeAmount)  + "\",disputeCategory:\"" + obj[5].toString() 
				+ "\",disputeCategoryId:\"" + obj[1].toString() + "\",accountCodeId:\"" + obj[0].toString() + "\"}";
				listString.add(str);
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String str = getListJson(listString);
		sb.append(str);
		logger.info("Exit method getInvoiceItemGroupProposals.");
		return sb.toString();
	}
	
	/**
	 * ccording to the Proposal list invoiceItemId be InvoiceItem
	 * 
	 */
	public String getInvoiceItemProposals(SearchInvoiceVO sio) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Item Proposals View List", sio));
		List<String> l = null;
		String str = "null";
		try {
			l = invoiceDetailDao.searchInvoiceItemProposals(sio.getInvoiceItemId());
			str = sio.getListJsonCompatibleByAll(l);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return str;
	}
	
	/**
	 * According to multiple id's Proposal by invoiceItem summary Amount
	 * 
	 */
	public String getItemGroupProposalTotalAmount(String boxInId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("According to multiple id's Proposal by invoiceItem summary Amount", boxInId));
		StringBuffer sb = new StringBuffer();
		StringBuffer inIdString = new StringBuffer();
		List<String> l = null;
		InvoiceItem invoiceItem = new InvoiceItem();
		try {
			inIdString.append(getBoxInId(boxInId));
			invoiceItem = invoiceDetailDao.findInvoiceItemById(Long.valueOf(boxInId.split(",")[0]));
			l = invoiceDetailDao.searchItemGroupProposalTotalAmount(inIdString.toString(),invoiceItem.getInvoice().getId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String str = getListJson(l);
		sb.append(str);
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * Remove the extra comma
	 * 
	 */
	private String getBoxInId(String boxInId) throws BPLException{
		StringBuffer inIdString = new StringBuffer();
		String[] inId = boxInId.split(",");
		try {
			for(int i = 0; i < inId.length; i++){
				inIdString.append(inId[i]);
				if(i + 1 < inId.length){
					inIdString.append(",");
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
	 * According to the Proposal invoiceItemId be invoiceItem summary Amount
	 * 
	 */
	public String getItemProposalTotalAmount(String invoiceItemId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("According to the Proposal invoiceItemId be invoiceItem summary Amount", invoiceItemId));
		StringBuffer sb = new StringBuffer();
		StringBuffer inIdString = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDetailDao.searchItemProposalTotalAmount(invoiceItemId,inIdString.toString());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String str = getListJson(l);
		sb.append(str);
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 *  double type variable adding together  
	 *  
	 */
    public Double addDouble(Double d1, Double d2, Double d3){
      	if(d1 == null) d1 = 0.0;
    	if(d2 == null) d2 = 0.0;
    	if(d3 == null) d3 = 0.0;

        BigDecimal b1 = new BigDecimal(d1.toString());
        BigDecimal b2 = new BigDecimal(d2.toString());
        BigDecimal b3 = new BigDecimal(d3.toString());
        return (b1.add(b2)).add(b3).doubleValue();
    }
    
    
    /**
     * 返回从数据库中查询的validation_result list 集合。
     * @param  proposalId      [description]
     * @param  invoiceId       [description]
     * @param  auditSourceType [description]
     * @return                 [description]
     * @throws BPLException    [description]
     * @see InvoiceDetailAction#searchValidationResult.
     */
    public List<Map<String,Object>> searchValidationResult(String proposalId, String invoiceId, String auditSourceType, String circuitNumber) throws BPLException {   	
    	
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		// 此方法获得的信息用于显示 validation result overlay。
		List<Object[]> validationResult = invoiceDetailDao.searchValidationResult(proposalId, invoiceId, auditSourceType, circuitNumber);

		// 遍历返回的数据，存储到list集合中。
		for (Object[] vdr : validationResult) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			
			map.put("audit_status_name", vdr[0]);
			map.put("product_name", vdr[1]);
			map.put("actual_amount", vdr[2]);
			map.put("expect_amount", vdr[3]);
			map.put("audit_reference_type_name", vdr[4]);
			map.put("audit_source_name", vdr[5]);
			
			// 获取描述信息的时候需要去掉前后的空格。
			if ( vdr[6] != null ) {
				map.put("audit_source_description", ((String) vdr[6]).trim());
			} else {
				map.put("audit_source_description",vdr[6]);
			}
			
			
			if ( vdr[7] != null ) {
				String notes = (String) vdr[7];
				String trim_notes = notes.trim();
				String notesValue = "{\"message\":\" \" ,\"list\":[]}"; // 代表不存在所需结构的数据。
				// 将notes中的换行符号替换成前台的 <br> 换行符。
				String break_line_notes = trim_notes.replaceAll("\n | \r | \r\n", "<br />");
				if(break_line_notes.equals(notesValue)){
					map.put("notes_data", null);
				}else{
					map.put("notes_data", break_line_notes);
				}
				
			} else {
				
				map.put("notes_data",vdr[7]);
			}
			
			map.put("notes_notes", vdr[8]);
			
			map.put("audit_reference_id", vdr[9]);
			map.put("audit_status_id", vdr[10]);
			map.put("audit_reference_type_id", vdr[11]);
			map.put("component_name", vdr[12]);
			map.put("quantity", vdr[13]);
			map.put("audit_source_id", vdr[14]);
			map.put("audit_result_id", vdr[15]);
			
			// 获取 validation_result 的信息时， 当 reference_type 为 Contract, Tariff, Price List 时，
			// 查询额外的信息。
			if ( (Integer)vdr[11] == 2 || (Integer)vdr[11] == 3 || (Integer)vdr[11] == 5) {
				
				map.put("ctp_data",invoiceDetailDao.queryTariffValidationDetail(vdr[9].toString(),vdr[11].toString(), vdr[15].toString()));
				
				List<Object[]> validationReferenceInfo = invoiceDetailDao.queryValidationReferenceInfo( vdr[4], vdr[9] , vdr[15].toString());
				Object[] referenceInfos = validationReferenceInfo.get(0);
				
				map.put("reference_type_file_id", referenceInfos[0]);
				map.put("reference_type_id", referenceInfos[1]);
				map.put("reference_type_name", referenceInfos[2]);
				map.put("attachment_point_id", referenceInfos[3]);
			}

			list.add(map);
		}
		
		return list;
	}
	
	/**
	 * Get User by assignedAnalysId (invoice)
	 * @author xinyu.Liu
	 */
	public User getAssignedAnalys(User user,Integer assignedAnalysId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get User by assignedAnalysId (invoice)", user,assignedAnalysId));
		if(assignedAnalysId != null){
			user = invoiceDetailDao.findUserById(assignedAnalysId);
		}else{
			user = null;
		}
		logger.info(CcmFormat.formatExitLog());
		return user;
	}
	
	/**
	 * Get all Related Invoices list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getRelatedInvoices(String invoiceId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get all Related Invoices list", invoiceId));
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		List<Object[]> cList = null;
		try {
			dList = invoiceDetailDao.getRelatedInvoicesByInvoiceId(Integer.parseInt(invoiceId));
			cList = invoiceDetailDao.getRelatedInvoicesByRelativeInvoiceId(Integer.parseInt(invoiceId));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(int a = 0 ; a < 2; a++){
			if(a == 1){
				dList = cList;
			}
			// Data which searches, after elimination redundant, increases in turn to list in 
			for(Object[] r : dList){
				// Increases the data to list in  
				for(int i = 0; i < r.length; i=i+2){
					// Removes repeatedly  
					boolean bo = true;
					for(int j = 0; j < list.size(); j++){
						MapEntryVO<String, String> mm = list.get(j);
						String keyId = mm.getKey();
						if(r[i].toString().equals(keyId)){
							bo = false;
							break;
						}
					}
					// Increases the data to list in  
					if(bo){
						MapEntryVO<String, String> m = new MapEntryVO<String, String>(r[i].toString(), r[i+1].toString());
						list.add(m);
					}
				}
			}
		}
		logger.info(CcmFormat.formatExitLog());
		return list;
	}
	
	/**
	 * Get total page number and result number.
	 * Return JSON string.
	 * 
	 */
	public String getInvoiceItemTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(" Get total page number and result number", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getInvoiceItemListOfInvoices(searchInvoiceVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		String str = searchInvoiceVO.getTotalPageNoJson(count);
		sb.append(str);
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * List by InvoiceItem
	 * 
	 */
	public String getInvoiceItemLists(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Item Lists View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDetailDao.findInvoiceItemList(searchInvoiceVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		String str = searchInvoiceVO.getListJson(l);
		sb.append(str);
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * Get total page number and result number.
	 * Return JSON string.
	 * 
	 */
	public String getItemTypeTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get ItemType Total Page No", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getInvoiceDetailNumberOfInvoices(searchInvoiceVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		String str = searchInvoiceVO.getTotalPageNoJson(count);
		sb.append(str);
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * Modify the label 
	 * If bo is true, then add
	 * Is false, then delete 
	 */
	public void updateInvoiceLabel(String invoiceId, String labelId, boolean bo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Modify the label (If bo is true, then add,Is false, then delete)", invoiceId,labelId,bo));
		Integer invoiceLabelId = null;
		List<String> l = null;
		if(bo){
			//find rec_active_flag="N"
			try {
				l = invoiceDetailDao.findInvoiceLabelId(invoiceId, labelId, "N");
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			if (l != null && l.size() > 0) {
				Object str = l.get(0);
				invoiceLabelId = (Integer)str;
				updateInvoiceLabelId(invoiceLabelId,"Y");
			}else{
				//add
				addInvoiceLabel(invoiceId,labelId);
			}
		}else{
			//update
			try {
				l = invoiceDetailDao.findInvoiceLabelId(invoiceId, labelId, "Y");
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			if (l != null && l.size() > 0) {
				Object str = l.get(0);
				invoiceLabelId = (Integer)str;
				updateInvoiceLabelId(invoiceLabelId,"N");
			}
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Add a InvoiceLabel records 
	 */
	private void addInvoiceLabel(String invoiceId,String labelId){
		logger.info(CcmFormat.formatEnterLog("Add a InvoiceLabel records ", invoiceId,labelId));
		InvoiceLabel invoiceLabel = new InvoiceLabel();
		Invoice invoice = new Invoice();
		Label label = new Label();
		invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(invoiceId));
		label = invoiceDetailDao.findLabelById(Integer.parseInt(labelId));
		
		invoiceLabel.setInvoice(invoice);
		invoiceLabel.setLabel(label);
		invoiceLabel.setCreatedBy(SystemUtil.getCurrentUserId());
		invoiceLabel.setCreatedTimestamp(new Date());
		invoiceLabel.setModifiedBy(SystemUtil.getCurrentUserId());
		invoiceLabel.setModifiedTimestamp(new Date());
		invoiceLabel.setRecActiveFlag("Y");
		
		invoiceDetailDao.saveInvoiceLabel(invoiceLabel);
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * update InvoiceLabel ### setRecActiveFlag("N") and setRecActiveFlag("Y")
	 */
	private void updateInvoiceLabelId(Integer invoiceLabelId,String recActiveFlag){
		logger.info(CcmFormat.formatEnterLog("update InvoiceLabel", invoiceLabelId,recActiveFlag));
		InvoiceLabel invoiceLabel = new InvoiceLabel();
		invoiceLabel = invoiceDetailDao.findInvoiceLabelById(invoiceLabelId);
		invoiceLabel.setModifiedBy(SystemUtil.getCurrentUserId());
		invoiceLabel.setModifiedTimestamp(new Date());
		if("Y".equals(recActiveFlag)){
			invoiceLabel.setRecActiveFlag("Y");
		}
		if("N".equals(recActiveFlag)){
			invoiceLabel.setRecActiveFlag("N");
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * According to the itemId get Invoice object 
	 */
	public Invoice getInvoiceInvoiceItem(String invoiceItemId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("According to the itemId get Invoice object", invoiceItemId));
		InvoiceItem invoiceItem = new InvoiceItem();
		try {
			invoiceItem = getInvoiceItemByInvoiceItemId(invoiceItemId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return invoiceItem.getInvoice();
	}
	
	/**
	 * According to the itemId get InvoiceItem object 
	 */
	public InvoiceItem getInvoiceItemByInvoiceItemId(String invoiceItemId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("According to the itemId get InvoiceItem object", invoiceItemId));
		InvoiceItem invoiceItem = new InvoiceItem();
		try {
			invoiceItem = invoiceDetailDao.findInvoiceItemById(Long.valueOf(invoiceItemId));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return invoiceItem;
	}
	
	public String getItemTypeList(String idName, String id) throws BPLException {
		logger.info("Enter method getItemTypeList.");
		StringBuffer sb = new StringBuffer();
		Invoice invoice = new Invoice();
		InvoiceItem invoiceItem = new InvoiceItem();
		List<String> l = null;
		//benid  
		try {
			if("invoiceId".equals(idName)){
				//invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(id));
				//String banId = invoice.getBan().getId().toString();
				l = invoiceDetailDao.findItemTypeList(id);
			}
			if("invoiceItemId".equals(idName)){
				invoiceItem = invoiceDetailDao.findInvoiceItemById(Long.valueOf(id));
				//String banId = invoiceItem.getInvoice().getBan().getId().toString();
				String invoiceId = invoiceItem.getInvoice().getId().toString();
				l = invoiceDetailDao.findItemTypeListByItemId(id,invoiceId);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String str = getListJson(l);
		sb.append(str);
		logger.info("Exit method getItemTypeList.");
		return sb.toString();
	 }
	
	private String getListJson(List l){
		StringBuilder sb = new StringBuilder();
		if (l != null && l.size() > 0 ) {
			int size = l.size();
			sb.append("{data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str == null) {
					break;
				} else {
					if (str instanceof SerializableBlob) {
						str = BlobUtil.getBlobString((SerializableBlob) str);
					}
					if (i != 0)
						sb.append(",");
					sb.append(str.toString());
				}
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * According to the itemId get it all Proposal 
	 */
	public String getItemProposalList(String itemId) throws BPLException {
		logger.info("Enter method getItemProposalList.");
		StringBuffer sb = new StringBuffer();
		String objString;
		List<String> l = null;
		try {
			//Their data by itemId
			l = invoiceDetailDao.findItemProposalByItemId(Integer.parseInt(itemId));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		String str = getListJson(l);
		sb.append(str);
		logger.info("Exit method getItemProposalList.");
		return sb.toString();
	 }
	
	/**
	 * Their data by itemId
	 */
	public String getproposal(String itemId) throws BPLException {
		logger.info("Enter method getproposal.");
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			/*
			 * {id:1",pay:"25411.9261",dispute:"32065.4141",cleanup:"11074.0632",shortPay:"51811.1791,
			 * itemname:"L2 ",rate:"21.00000",itemAmount:"56756.00000", original:"323.00000",discount:"42.00000", 
			 * quantity:"",itemKey:"", startDate:"1899-12-30",endDate:"2000-12-30", circuitNumber:""}
			 */
			//Their data by itemId
			l = invoiceDetailDao.findProposalByitemId(itemId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String str = getListJson(l);
		sb.append(str);
		logger.info("Exit method getproposal.");
		return sb.toString();
	}
	
	/**
	 * Find the current invoice objects owned by all Label
	 */
	public List<String> searchInvoiceLabelList(Invoice invoice) throws BPLException {
		logger.info("Enter method searchInvoiceLabelList.");
		Integer invoiceId = invoice.getId();
		List<String> l = null;
		try {
			l = invoiceDetailDao.searchInvoiceLabel(invoiceId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			logger.info("Exit method searchInvoiceLabelList.");
			return l;
		} 
		logger.info("Exit method searchInvoiceLabelList.");
		return null;
	 }
	
	/**
	 * According to the itemId, get all the parent names and IDs 
	 */
	public String searchItemNameList(String invoiceItemId) throws BPLException {
		logger.info("Enter method searchItemNameList.");
		String str = new String();
		try {
			InvoiceItem invoiceItem = invoiceDetailDao.findInvoiceItemById(Long.valueOf(invoiceItemId));
			str = getInvoiceItemString(invoiceItem);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Enter method searchItemNameList.");
		return str;
	}
	
	//Data processing
	private String getInvoiceItemString(InvoiceItem invoiceItem) throws BPLException {
		logger.info("Enter method getInvoiceItemString.");
		StringBuffer sb = new StringBuffer();
		Long parentItemId;
		sb.append("{data:[");
		do {
			parentItemId = invoiceItem.getParentItemId();
			//{data:[{invoiceId:1,parentItemId:5,itemname:"aaaaaaa"},{invoiceId:5,parentItemId:null,itemname:"fffff"}]
			sb.append("{invoiceId:");
			sb.append(invoiceItem.getInvoice().getId());
			sb.append(",parentItemId:");
			sb.append(invoiceItem.getParentItemId());
			sb.append(",invoiceItemId:");
			sb.append(invoiceItem.getId());
			sb.append(",itemname:\"");
			sb.append(invoiceItem.getItemName());
			sb.append("\"}");
			if(parentItemId != null && !("".equals(parentItemId))){
				sb.append(",");
				invoiceItem = invoiceDetailDao.findInvoiceItemById(parentItemId);
			}
		}while(parentItemId != null && !("".equals(parentItemId)));
		
		sb.append("]}");
		logger.info("Enter method getInvoiceItemString.");
		return sb.toString();
	}
	
	/**
	 * According to get any of the itemId invoiceId 
	 */
	private String getParentItemId(String invoiceId) throws BPLException {
		logger.info("Enter method getParentItemId.");
		List<BigInteger> l = null;
		try {
			l = invoiceDetailDao.findParentItemIdByinvoiceId(Integer.parseInt(invoiceId));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			logger.info("Enter method getParentItemId.");
			return l.get(0).toString();
		} else {
			logger.info("Enter method getParentItemId.");
			return null;
		}
	}
	
	public String getTypeOpenAcc(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info("Enter method getTypeOpenAcc.");
		StringBuffer sb = new StringBuffer();
		StringBuffer tableField = new StringBuffer();
		List<Object[]> l = null;
		List<Object[]> itemDisplayList = null;
		try {
			Invoice invoice = new Invoice();
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(searchInvoiceVO.getInvoiceId()));
			searchInvoiceVO.setInvoiceStructureCodeId(invoice.getInvoiceStructure().getId().toString());
			itemDisplayList = invoiceDetailDao.findItemDisplayTitleName(searchInvoiceVO);
			if(itemDisplayList ==null || itemDisplayList.size() <= 0){
				itemDisplayList = invoiceDetailDao.findItemDisplayTitleNameDefult("0", "0");
				itemDisplayList = itemDisplayTitleNameDefult(itemDisplayList,"List<Object[]>");
			}
			for(int i = 0; i < itemDisplayList.size(); i++){
				Object[] obj = itemDisplayList.get(i); 
				//String displayTitleName = obj[0].toString();
				String tableFieldName = obj[1].toString();
				String displayFormat =obj[2].toString();
				
				if(displayFormat.equals("amount")){
					tableField.append(""+obj[1]+":\"',format(if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),2),");
				}else if(displayFormat.equals("number")){
					tableField.append(""+obj[1]+":\"',if(ii."+tableFieldName+" is null,0,ii."+tableFieldName+"),");
				}else {
					tableField.append(""+obj[1]+":\"',toJSON(ii."+tableFieldName+" is null,'',ii."+tableFieldName+"),");
				}
				if(i+1 < itemDisplayList.size()) tableField.append("'\",");
			}
			searchInvoiceVO.setTableField(tableField.toString());
			l = invoiceDetailDao.findTypeOpenAcc(searchInvoiceVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInvoiceVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchInvoiceVO.getStartIndex()+size);
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
		logger.info("Exit method searchInvoice.");
		return sb.toString();
	}
	public void  updateBanAutoPay(Integer banId) throws BPLException {
		logger.info("Enter method updateBanAutoPay.");
		try {
		invoiceDetailDao.updateBanAutoPay(banId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updateBanAutoPay.");
	}
	
	/**
	 * According to proposalId, to modify the specified data 
	 */
	public void updateItemProposal(String proposalId, SearchInvoiceVO sio) {
		logger.info("Enter method updateItemProposal.");
		Proposal proposal = new Proposal();
		ScoaSource scoaSource=new ScoaSource();
		AccountCode accountCode = new AccountCode();
		proposal = invoiceDetailDao.findProposalById(Long.valueOf(proposalId));

		//There will not be able to modify the Payment and ScoaCode
			if(sio.getScoaCodeId() != null){
				accountCode = invoiceDetailDao.findAccountCodeById(Integer.parseInt(sio.getScoaCodeId()));
				proposal.setAccountCode(accountCode);
				scoaSource.setId(740201);
				proposal.setScoaSource(scoaSource);
			}
			if(sio.getPaymentAmount() != null && !"".equals(sio.getPaymentAmount())){
				proposal.setPaymentAmount(Double.valueOf(sio.getPaymentAmount()));
			}
		//There will not be able to modify the Dispute 
		if(proposal.getDispute() == null && sio.getDisputeAmount() != null && !"".equals(sio.getDisputeAmount())){
			proposal.setDisputeAmount(Double.valueOf(sio.getDisputeAmount()));
		}
		
		if(sio.getCleanupAmount() != null && !"".equals(sio.getCleanupAmount())){
		}
		proposal.setModifiedTimestamp(new Date());
		proposal.setModifiedBy(SystemUtil.getCurrentUserId());
		invoiceDetailDao.saveProposal(proposal);
		logger.info("Enter method updateItemProposal.");
	}
	
	/**
	 * Add a Proposal records 
	 */
	public void addItemProposal(Invoice invoice, InvoiceItem invoiceItem, SearchInvoiceVO sio) {
		logger.info("Enter method addItemProposal.");
		Proposal proposal = new Proposal();
		ScoaSource scoaSource=new ScoaSource();
		AccountCode accountCode = new AccountCode();
		
		proposal.setInvoice(invoice);
		proposal.setInvoiceItem(invoiceItem);
		
		if(sio.getScoaCodeId() != null){
			accountCode = invoiceDetailDao.findAccountCodeById(Integer.parseInt(sio.getScoaCodeId()));
			proposal.setAccountCode(accountCode);
			scoaSource.setId(740301);
			proposal.setScoaSource(scoaSource);
		}
		if(sio.getPaymentAmount() != null && !"".equals(sio.getPaymentAmount())){
			proposal.setPaymentAmount(Double.valueOf(sio.getPaymentAmount()));
		}
		if(sio.getDisputeAmount() != null && !"".equals(sio.getDisputeAmount())){
			proposal.setDisputeAmount(Double.valueOf(sio.getDisputeAmount()));
		}
		if(sio.getCleanupAmount() != null && !"".equals(sio.getCleanupAmount())){
		}
		
		proposal.setCreatedTimestamp(new Date());
		proposal.setCreatedBy(SystemUtil.getCurrentUserId());
		proposal.setModifiedTimestamp(new Date());
		proposal.setModifiedBy(SystemUtil.getCurrentUserId());
		proposal.setRecActiveFlag("Y");
		invoiceDetailDao.saveProposal(proposal);
		logger.info("Enter method addItemProposal.");
	}
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js) 
	 * 
	 * Find all the invoice in the same data as the id banid 
	 */
	public String searchInvoiceByBanIdSame(Invoice invoice) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Find all the invoice in the same data as the id banid ", invoice));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		Integer banId = invoice.getBan() == null ? null : invoice.getBan().getId();
		try {
			l = invoiceDetailDao.findInvoiceIdBybanId(banId);
			sb.append(getListJson(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	 }
	
	/**
	 * @author xinyu.Liu (viewSocaDispute.js)
	 * 
	 * get Invoice By invoiceId
	 */
	public Invoice getInvoiceByInvoiceId(String invoiceId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice By invoiceId", invoiceId));
		Invoice invoice = new Invoice();
		try {
			invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(invoiceId));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return invoice;
	}
		
	public User searchInvoiceAnalystName(Invoice invoice)throws BPLException{
		
		logger.info(CcmFormat.formatEnterLog("search Invoice AnalystName", invoice));
		User analysUser = new User();
		try {
			analysUser = userDao.findById(invoice.getAssignedAnalystId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		
		return analysUser;
	}
	
	/**
	 * @author wenbo.zhang (viewSocaDispute.js)
	 * 
	 * get Invoice By invoiceId
	 */
	public void auditInvoice (String invoiceId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice By invoiceId", invoiceId));
		try {
			invoiceDetailDao.recodeInvoiceSCOA(Integer.parseInt(invoiceId));
			invoiceDetailDao.auditInvoice(Integer.parseInt(invoiceId));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author mingyang.Li (viewInvoiceDetailMainPanel.jsp)
	 * 
	 * get Invoice Label By invoiceId
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getInvoiceLabelsByInvoiceId(String invoiceId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Label By invoiceId", invoiceId));
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		List<Object[]> l = null;
		try {
			l = invoiceDao.searchInvoiceLabelsByInvoiceId(invoiceId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(int i=0 ; i<l.size();i++){
			Object[] o = l.get(i);
			HashMap<String,String> m = new HashMap<String,String>();
			m.put("id", o[0].toString());
			m.put("description", o[1] == null ? "" : o[1].toString());
			m.put("labelName", o[2] == null ? "" : o[2].toString());
			m.put("iconFilePath", o[3] == null ? "" : o[3].toString());
			list.add(m);
		}
		logger.info(CcmFormat.formatExitLog());
		return list;
	}
	
	
	/**
	 * @author mingyang.Li (viewInvoiceDetailMainPanel.jsp)
	 * 
	 * get Invoice Label By invoiceId
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getOriginalListByInvoiceId(int invoiceId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Label By invoiceId", invoiceId));
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			list = invoiceDao.getOriginalListByInvoiceId(invoiceId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return list;
	}
	
	/**
	 * @author mingyang.Li (viewInvoiceDetailMainPanel.jsp)
	 * 
	 * get Invoice Label By invoiceId
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getOriginalDetail(int originalId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Label By originalId", originalId));
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			map = invoiceDao.getOriginalDetail(originalId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return map;
	}
	
	public String searchBanAuditFlag(String invoiceId){
		logger.info(CcmFormat.formatEnterLog("search BanAudit Flag", invoiceId));
		String banAuditFlag = null;
		banAuditFlag = invoiceDao.searchBanAuditFlag(invoiceId);
		return banAuditFlag;
	}
	public String searchBanExemption(String invoiceId){
		logger.info(CcmFormat.formatEnterLog("search BanExemption", invoiceId));
		String banExemptionFlag = "isHide";
		Long count = invoiceDao.searchBanExemption(invoiceId);
		if (count>0) {
			banExemptionFlag = "isShow";
		}
		return banExemptionFlag;
	}
	public String searchBanOpenAutoPay(String invoiceId){
		logger.info(CcmFormat.formatEnterLog("search OpenAutoPay", invoiceId));
		String isOPen = "false";
		Long count = invoiceDao.searchBanOpenAutoPay(invoiceId);
		if (count>0) {
			isOPen = "true";
		}
		return isOPen;
	}
	
	public String autopaySwitch(String invoiceId) throws BPLException{
		String autopaySwitch = null;
		try {
			long count  = invoiceDetailDao.autopaySwitch(invoiceId);
			 
			 if (count == 1){
				 autopaySwitch = "on";
			 }else if(count<1){
				 autopaySwitch = "off";
			 }

		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return autopaySwitch;
	}
	
	public void updateAutopaySwitch(Integer banId,String autopaySwitch)throws BPLException{
		try {
			invoiceDetailDao.updateAutopaySwitch(banId,autopaySwitch);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public Map<String, Object> searchInvoiceAuditStatus(String invoiceId) {
		logger.info(CcmFormat.formatEnterLog("get Invoice Audit Status", invoiceId));
		Map<String, Object> auditStatus = new HashMap<String, Object>(); 
		
		// TODO Auto-generated method stub
		List<Object[]> list = invoiceDetailDao.getAuditStatusDetailList(invoiceId);
		
		String mrcNotes = null;
		String usageNotes = null;
		String occNotes = null;
		String adjustmentNotes = null;
		
		for(Object[] obj : list) {
		  
		  // MRC	
		  if("3".equals(obj[0].toString())){
			  mrcNotes = this.resultTipInfo(obj[1].toString(), obj[2].toString(), obj[3].toString());
		  // Usage	  
		  } else if("4".equals(obj[0].toString())){
			  usageNotes = this.resultTipInfo(obj[1].toString(), obj[2].toString(), obj[3].toString());
		  // OCC	
		  } else if("5".equals(obj[0].toString())){
			  occNotes = this.resultTipInfo(obj[1].toString(), obj[2].toString(), obj[3].toString());
		  // Adjustment  
		  } else if("6".equals(obj[0].toString())){
			  adjustmentNotes = this.resultTipInfo(obj[1].toString(), obj[2].toString(), obj[3].toString());
		  }
		}
		
		
		Object[] status = null;
		status = invoiceDao.searchInvoiceAuditStatus(invoiceId);
		if (status == null) {
			auditStatus.put("payment_audit_status_id",0);
			auditStatus.put("tax_audit_status_id", 0);
			auditStatus.put("mrc_audit_status_id",0);
			auditStatus.put("occ_audit_status_id",0);
			auditStatus.put("usage_audit_status_id",0);
			auditStatus.put("adjustment_audit_status_id",0);
			auditStatus.put("lpc_audit_status_id",0);
			auditStatus.put("current_due_audit_status_id",0);
		}else {			
			auditStatus.put("payment_audit_status_id",status[1] == null ? 0 :status[1]);
			auditStatus.put("tax_audit_status_id", status[2] == null ? 0 :status[2]);
			auditStatus.put("mrc_audit_status_id", status[3] == null ? 0 :status[3]);
			auditStatus.put("mrc_audit_notes", mrcNotes);
			auditStatus.put("occ_audit_status_id", status[4] == null ? 0 :status[4]);
			auditStatus.put("occ_audit_notes", occNotes);
			auditStatus.put("usage_audit_status_id", status[5] == null ? 0 :status[5]);
			auditStatus.put("usage_audit_notes", usageNotes);
			auditStatus.put("adjustment_audit_status_id", status[6] == null ? 0 :status[6]);
			auditStatus.put("adjustment_audit_notes", adjustmentNotes);
			auditStatus.put("lpc_audit_status_id", status[7] == null ? 0 :status[7]);
			auditStatus.put("current_due_audit_status_id", status[8] == null ? 0 :status[8]);
			auditStatus.put("usage_bill_keep_status_id", 0);
			String usageCount = invoiceDao.searchInvoiceAuditForSourceBeginning(invoiceId,"80");
			if(Integer.valueOf(usageCount)<=0){
				auditStatus.put("usage_bill_keep_status_id", 1);
			}
		}
		return auditStatus;
	}
	
	/**
	 * Get autopay error notes.
	 * @throws BPLException 
	 */
	public List<HashMap<String, String>> searchAutopayErrorNotes(String invoiceId) throws BPLException {
		
		// Declare error notes variable.
		List<HashMap<String, String>> autopayErrorNotesList = new ArrayList<HashMap<String, String>>();
		
		// Database list variable.
		List<Object[]> autopayErrorNotesDBList = null;
		
		// try ... catch
		try {
			
			autopayErrorNotesDBList = invoiceDetailDao.searchAutopayErrorNotesDBList(invoiceId);
			
		} catch (RuntimeException e) {

			logger.error(CcmFormat.formatErrorLog(e));
	        BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
	        throw bpe;
			
		}
		
		// Iterate the database object.
		for(int i = 0, len = autopayErrorNotesDBList.size() ; i < len; i ++){
			
			// Get the special one.
	        Object[] o = autopayErrorNotesDBList.get(i);
	        
	        // List item hashmap .
	        HashMap<String,String> autopayErrorNotesListItemMap = new HashMap<String,String>();
	        
	        // Add item to hashmap.
	        autopayErrorNotesListItemMap.put("invoiceId", o[0].toString());
	        autopayErrorNotesListItemMap.put("errorNote", o[1].toString());
	        
	        // Add hashmap item to list.
	        autopayErrorNotesList.add(autopayErrorNotesListItemMap);
	    }
		
	    logger.info(CcmFormat.formatExitLog());
		
		// Return list.
		return autopayErrorNotesList;
	}
	
	public Map<String, Object> searchAuditMemory(String proposalId){
		logger.info(CcmFormat.formatEnterLog("Search Audit Memory", proposalId));
		Map<String, Object> auditMemory = new HashMap<String, Object>(); 

		// TODO Auto-generated method stub
		Object[] obj = invoiceDetailDao.searchAuditMemory(proposalId);
		Object[] obj2 = invoiceDetailDao.searchAuditMemoryDynamic(obj);
		auditMemory.put("ban_id",obj[0]);
		auditMemory.put("account_number",obj[1]);
		auditMemory.put("charge_type",obj[2]);
		auditMemory.put("circuit_number",obj[3]);
		auditMemory.put("usoc",obj[4]);
		auditMemory.put("item_name",obj[5]);
		auditMemory.put("line_item_code",obj[6]);
		auditMemory.put("description",obj[7]);
		
		if (obj2 == null) {
			auditMemory.put("rate",null);
			auditMemory.put("product_id",null);
			auditMemory.put("product_component_id",null);
			auditMemory.put("province_id",null);
			auditMemory.put("type","add");
		}else{
			auditMemory.put("memory_id",obj2[0]);
			auditMemory.put("rate",obj2[1]);
			auditMemory.put("product_id",obj2[2]);
			auditMemory.put("product_component_id",obj2[3]);
			auditMemory.put("province_id",obj2[4]);
			auditMemory.put("type","update");
		}
		
		return auditMemory;
	}
	
    public String saveMemory(AuditMemory auditMemory) throws BPLException {
    	logger.info(CcmFormat.formatEnterLog("save memory"));
    	String auditMemoryId =null;
		try {
			auditMemoryId = invoiceDetailDao.saveMemory(auditMemory);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return auditMemoryId;
    }
	
	public void updateMemory(AuditMemory auditMemory) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("update memory"));
		try {
			invoiceDetailDao.updateMemory(auditMemory);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author pengfei.wang
	 * To meet the conditions of the data into List
	 * */
	public String getOriginalList(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Original By SearchInvoiceVo", svo));

		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDetailDao.searchOriginal(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svo.getStartIndex() + size);
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
	/**
	 * @author pengfei.wang
	 * Statistics for the number of requirements with inquires
	 * */
	public String getOriginalTotalPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Original No", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getOriginalTotalNO(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * To meet the conditions of the data into List
	 * */
	public String getTariffList(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Tariff by SearchInvoiceVO", svo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDetailDao.searchTariff(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svo.getStartIndex() + size);
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
	/**
	 * @author pengfei.wang
	 * Statistics for the number of requirements with inquires
	 * */
	public String getTariffTotalPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Tariff No", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getTariffTotalNO(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count
				/ (double) svo.getRecPerPage());
		sb.append(tp);
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
	 * @author wei.su
	 * According to the records of invoiceId all dispute
	 * */
	 public String getDisputeTotalNOByInvoiceId(SearchInvoiceVO svo,int invoiceId)throws BPLException
	 {
		 logger.info(CcmFormat.formatEnterLog("get DisputeTotalNO By InvoiceId", svo));
			StringBuffer sb = new StringBuffer();
			long count = 0;
			try {
				count = invoiceDetailDao.getDisputeTotalNOByInvoiceId(svo, invoiceId);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(
						ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			sb.append("{totalResultNo:");
			sb.append(count);
			sb.append(",totalPageNo:");
			int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
			sb.append(tp);
			sb.append("}");
			logger.info(CcmFormat.formatExitLog());
			return sb.toString();
	 }

	/**
	 * @author pengfei.wang
	 * The Payment search.com inquires, stored in list
	 * */
	public String getPaymenttablaList(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Payment by SearchInvoiceVO", svo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDetailDao.searchPaymenttabla(svo);
			sb.append(svo.getListJsonCompatible(list));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @author pengfei.wang
	 * Payment pagination inquiry statistics
	 * */
	public String getPaymenttablaTotalPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Payment NO", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getPaymenttablaTotalNO(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	 /**
	  * @author wei.su 
	  * According to invoiceId get all the dispute to set back and
	  */
	 public String searchDisputesByInvoiceId(SearchInvoiceVO svo,int invoiceId)throws BPLException
	 {
		 
		 logger.info("Enter method searchDisputesByInvoiceId.");

			StringBuffer sb = new StringBuffer();
			List<String> list = null;
			try {
				list = invoiceDetailDao.searchDisputesByInvoiceId(svo, invoiceId);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(
						ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			if (list != null && list.size() > 0) {
				sb.append("{begin:");
				sb.append(svo.getStartIndex() + 1);
				sb.append(",end:");
				int size = list.size();
				sb.append(svo.getStartIndex() + size);
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

			logger.info("Exit method searchDisputesByInvoiceId.");
			return sb.toString();

		 
	 }

	 /**
	  * @author pengfei.wang
	  * The data that accords with a condition inquires
	  * */
	public String getMiscList(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search MiscList ", svo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDetailDao.searchMisc(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svo.getStartIndex() + size);
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
	/**
	 * @author pengfei.wang
	 * The number of statistics that accords with a condition
	 * */
	public String getMiscTotalPageNo(SearchInvoiceVO svo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get getMiscTotalPageNo ", svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDetailDao.getMiscTotalNO(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * add proposal
	 * */
	public Long addMiscProposalPayment(SearchInvoiceVO sio) {
		logger.info(CcmFormat.formatEnterLog("save Proposal ", sio));
		Proposal proposal = new Proposal();
		ScoaSource scoaSource=new ScoaSource();
		AccountCode accountCode = new AccountCode();
		Invoice invoicePay=new Invoice();
		
		if(sio.getInvoiceId() != null){
			invoicePay = invoiceDetailDao.findInvoiceById(Integer.parseInt(sio.getInvoiceId()));
			proposal.setInvoice(invoicePay);
		}
		if(sio.getAccountCodeId() != null){
			accountCode = invoiceDetailDao.findAccountCodeById(Integer.parseInt(sio.getAccountCodeId()));
			proposal.setAccountCode(accountCode);
			scoaSource.setId(740401);
			proposal.setScoaSource(scoaSource);
		}
		if(sio.getPaymentAmount() != null && !"".equals(sio.getPaymentAmount())){
			proposal.setPaymentAmount(Double.valueOf(sio.getPaymentAmount()));
		}
		if(sio.getSortingDirection() != null && !"".equals(sio.getSortingDirection())){
			proposal.setNotes(sio.getSortingDirection());
		}
		proposal.setCreatedTimestamp(new Date());
		proposal.setCreatedBy(SystemUtil.getCurrentUserId());
		proposal.setModifiedTimestamp(new Date());
		proposal.setModifiedBy(SystemUtil.getCurrentUserId());
		proposal.setRecActiveFlag("Y");
		proposal.setProposalFlag("1");
		Long id = invoiceDetailDao.saveProposal(proposal);
		logger.info(CcmFormat.formatExitLog());
		return id;
	}
	
	public void cleanExternalApprovalNotes(String invoiceId) throws BPLException {
		logger.info("Enter method cleanExternalApprovalNotes.");
		
		try {
			invoiceDao.cleanExternalApprovalNotes(invoiceId);
		} catch (Exception e) {
			logger.error(e);
			throw new BPLException(e.getMessage());
		}
		logger.info("Exit method cleanExternalApprovalNotes.");
		
	}
	
	
	public void addTransactionHistory(TransactionHistory th,String openInterface , int workflowActionId,String workflowUserName,String notes) throws BPLException {
		logger.info("Enter method addTransactionHistory.");
		
		try {
			invoiceDetailDao.addTransactionHistory(th,openInterface,workflowActionId,workflowUserName,notes);
		} catch (Exception e) {
			logger.error(e);
			throw new BPLException(e.getMessage());
		}
		logger.info("Exit method addTransactionHistory.");
		
	}
	public String[] addExternalEmail(String toEmail) throws BPLException {
		
		logger.info("Enter method addExternalEmail.");
		String[] strings;
		String user = "";
		try {
			strings = invoiceDetailDao.addExternalEmail(toEmail);
			
		} catch (Exception e) {
			logger.error(e);
			throw new BPLException(e.getMessage());
		}
		
		logger.info("Exit method addExternalEmail.");
		return strings;
	}
	
    public void updateExternalEmail(String id) throws BPLException {
		
		logger.info("Enter method updateExternalEmail.");
		try {
			invoiceDetailDao.updateExternalEmail(id);
		} catch (Exception e) {
			logger.error(e);
			throw new BPLException(e.getMessage());
		}
		
		logger.info("Exit method updateExternalEmail.");
	}
	/**
	 * @author pengfei.wang
	 * edit proposal
	 * */
	public void editMiscProposalPayment(SearchInvoiceVO sio)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Edit Proposal ", sio));
		AccountCode accountCode = new AccountCode();
		ScoaSource scoaSource=new ScoaSource();
		Proposal proposal=invoiceDetailDao.findProposalById(Long.valueOf(sio.getParentItemId()));
		if(sio.getAccountCodeId() != null){
			accountCode = invoiceDetailDao.findAccountCodeById(Integer.parseInt(sio.getAccountCodeId()));
			proposal.setAccountCode(accountCode);
			scoaSource.setId(740501);
			proposal.setScoaSource(scoaSource);
		}
		if(sio.getPaymentAmount() != null && !"".equals(sio.getPaymentAmount())){
			proposal.setPaymentAmount(Double.valueOf(sio.getPaymentAmount()));
		}
		if(sio.getSortingDirection() != null && !"".equals(sio.getSortingDirection())){
			proposal.setNotes(sio.getSortingDirection());
		}
		proposal.setModifiedTimestamp(new Date());
		proposal.setModifiedBy(SystemUtil.getCurrentUserId());
		proposal.setRecActiveFlag("Y");
		invoiceDetailDao.saveProposal(proposal);
		logger.info(CcmFormat.formatExitLog());
	}
	/**
	 * @author pengfei.wang
	 * delete proposal
	 * */
	public void deleteMsicPayment(int id) {
		logger.info(CcmFormat.formatEnterLog("Delete Proposal by id", id));
		Proposal proposal=invoiceDetailDao.findProposalById(Long.valueOf(id));
		proposal.setRecActiveFlag("N");
		invoiceDetailDao.saveProposal(proposal);
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author dongjian
	 * get Invoice Payment Amount
	 * */
	public Double getInvoicePaymentAmount(Invoice invoice) throws BPLException{
		return invoiceDetailDao.getInvoicePaymentAmount(invoice);
	};
	
	public boolean  getInvoiceDisputeStatusCloseAndActionRequestOpenCount(Invoice invoice){

		long count = invoiceDetailDao.getInvoiceDisputeStatusCloseAndActionRequestOpenCount(invoice);

		if(count>0){
			return  true;
		}else{
			return  false;
		}       
	}
	
	public boolean getBanDisputeDueDate(Integer invoiceId){
		
		long count = invoiceDetailDao.getBanDisputeDueDate(invoiceId);
		if(count>0){
			return  true;
		}else{
			return  false;
		}    
	}
	/**
	 * @author dongjian
	 * get more amount json
	 * */
	public String getAmount(SearchInvoiceVO svo) throws BPLException {
		logger.info("Enter method getAmount.");
		StringBuilder sb = null;
		try {
			sb = new StringBuilder("{");
			String totalShortPaidDispute = invoiceDetailDao.getTotalShortPaidDispute(svo.getInvoiceId1());//invoiceDetailDao.getTotalShortPaidDispute(svo.getInvoiceId1());
			sb.append("totalShortPaidDispute:'"+totalShortPaidDispute);
			String totalPaidDispute = invoiceDetailDao.getTotalPaidDispute(svo.getInvoiceId1());//invoiceDetailDao.getTotalPaidDispute(svo.getInvoiceId1());
			sb.append("',totalPaidDispute:'"+totalPaidDispute);
			String shortPaidforPrevious = invoiceDetailDao.getshortPaidforPrevious(svo.getInvoiceId1());
			sb.append("',shortPaidforPrevious:'"+shortPaidforPrevious);
			String paidforPrevious = invoiceDetailDao.getPaidforPrevious(svo.getInvoiceId1());
			sb.append("',paidforPrevious:'"+paidforPrevious);
			String totalDispute = null;
			try {
				totalDispute = CcmFormat.formatCurrency((
						CcmFormat.paseCurrency(totalShortPaidDispute).doubleValue()
						+ CcmFormat.paseCurrency(totalPaidDispute).doubleValue()
						+ CcmFormat.paseCurrency(shortPaidforPrevious).doubleValue()
						+ CcmFormat.paseCurrency(paidforPrevious).doubleValue()
					)) ;
			} catch (ParseException e) {
				totalDispute = invoiceDetailDao.getTotalDispute(svo.getInvoiceId1());
			}
			sb.append("',totalDispute:'"+totalDispute);
			//////////////////////////
			String totalShortPaidDisputePayment = invoiceDetailDao.getTotalShortPaidDisputePayment(svo.getInvoiceId1());
			sb.append("',totalShortPaidDisputePayment:'"+totalShortPaidDisputePayment);
			String totalPaidDisputePayment = invoiceDetailDao.getTotalPaidDisputePayment(svo.getInvoiceId1());
			sb.append("',totalPaidDisputePayment:'"+totalPaidDisputePayment);
			String currentInvoice = invoiceDetailDao.getCurrentInvoice(svo);
			sb.append("',currentInvoice:'"+currentInvoice);
			String manuallyCreated = invoiceDetailDao.getManuallyCreated(svo);
			sb.append("',manuallyCreated:'"+manuallyCreated);
			String disputePayback = invoiceDetailDao.getDisputePayback(svo);
			sb.append("',disputePayback:'"+disputePayback);
			String applyingCredit = invoiceDetailDao.getApplyingCredit(svo);
			sb.append("',applyingCredit:'"+applyingCredit);
			
			String totalPayment = null;
			try {
				totalPayment = CcmFormat.formatCurrency((
						CcmFormat.paseCurrency(totalPaidDisputePayment).doubleValue() - 
						CcmFormat.paseCurrency(totalShortPaidDisputePayment).doubleValue() + 
						CcmFormat.paseCurrency(currentInvoice).doubleValue() + 
						CcmFormat.paseCurrency(manuallyCreated).doubleValue() + 
						CcmFormat.paseCurrency(disputePayback).doubleValue() - 
						CcmFormat.paseCurrency(applyingCredit).doubleValue()
					)) ;
			} catch (ParseException e) {
				totalPayment = invoiceDetailDao.getTotalPayment(svo.getInvoiceId1(),svo.getBanId1());
			}
			sb.append("',totalPayment:'"+totalPayment);
			sb.append("'}");
		} catch (RuntimeException e) {
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getAmount.");
		return sb.toString();
	}
	
	/**
	 * @author dongjian 
	 * update invoice amount 
	 * */
	private void setInvoiceAmounts(Invoice invoice) {
		logger.info("Enter method setInvoiceAmounts.");
		SearchInvoiceVO svo = new SearchInvoiceVO();
		svo.setInvoiceId1(invoice.getId());
		svo.setBanId1((invoice.getBan()==null ? null : invoice.getBan().getId()));
		try {
			String totalShortPaidDisputePayment = invoiceDetailDao.getTotalShortPaidDisputePayment(svo.getInvoiceId1());
			String totalPaidDisputePayment = invoiceDetailDao.getTotalPaidDisputePayment(svo.getInvoiceId1());
			String currentInvoice = invoiceDetailDao.getCurrentInvoice(svo);
			String manuallyCreated = invoiceDetailDao.getManuallyCreated(svo);
			String disputePayback = invoiceDetailDao.getDisputePayback(svo);
			String applyingCredit = invoiceDetailDao.getApplyingCredit(svo);
			Double totalPayment = CcmFormat.paseCurrency(totalPaidDisputePayment).doubleValue() - 
				CcmFormat.paseCurrency(totalShortPaidDisputePayment).doubleValue() + 
				CcmFormat.paseCurrency(currentInvoice).doubleValue() + 
				CcmFormat.paseCurrency(manuallyCreated).doubleValue() + 
				CcmFormat.paseCurrency(disputePayback).doubleValue() - 
				CcmFormat.paseCurrency(applyingCredit).doubleValue();

			String totalShortPaidDispute = invoiceDetailDao.getTotalShortPaidDispute(svo.getInvoiceId1());
			String totalPaidDispute = invoiceDetailDao.getTotalPaidDispute(svo.getInvoiceId1());
			String shortPaidforPrevious = invoiceDetailDao.getshortPaidforPrevious(svo.getInvoiceId1());
			String paidforPrevious = invoiceDetailDao.getPaidforPrevious(svo.getInvoiceId1());
			Double totalDispute = CcmFormat.paseCurrency(totalShortPaidDispute).doubleValue() 
					+ CcmFormat.paseCurrency(totalPaidDispute).doubleValue()
					+ CcmFormat.paseCurrency(shortPaidforPrevious).doubleValue()
					+ CcmFormat.paseCurrency(paidforPrevious).doubleValue() ;

			Double misc = CcmFormat.paseCurrency(manuallyCreated).doubleValue() + 
			CcmFormat.paseCurrency(disputePayback).doubleValue();
			
			invoice.setPaymentAmount(totalPayment);
			invoice.setDisputeAmount(totalDispute);
			invoice.setMiscAdjustmentAmount(misc);
		} catch (ParseException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		logger.info("Exit method setInvoiceAmounts.");
	}
	


	public String getMiscPaymentAmount(SearchInvoiceVO svo) throws BPLException {
		logger.info("Enter method getMiscPaymentAmount.");
		String result = "{result:\"0.00\"}";
		try {
			String manuallyCreated = invoiceDetailDao.getManuallyCreated(svo);
			String disputePayback = invoiceDetailDao.getDisputePayback(svo);
			String misc = CcmFormat.formatCurrency((
				CcmFormat.paseCurrency(manuallyCreated).doubleValue() + 
				CcmFormat.paseCurrency(disputePayback).doubleValue() 
			)) ;
			result = "{result:\""+misc+"\"}";
		} catch (Exception e) {
			logger.error(e);
			throw new BPLException(e.getMessage());
		}
		logger.info("Exit method getMiscPaymentAmount.");
		return result;
	}
	
	/**
	 * @author dongjian 
	 * get invoice Amount group by scoa
	 * */
	public String getPaymentScoaAmount(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getPaymentScoaAmount.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getPaymentScoaAmount(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getPaymentScoaAmount.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get invoice Amount group by scoa page no
	 * */
	public String getPaymentScoaAmountTotalPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info("Enter method getPaymentScoaAmountTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getPaymentScoaAmountTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getPaymentScoaAmountTotalPageNo.");
		return s;
	}
	
	/**
	 * @author dongjian 
	 * get invoice Amount group by scoa page no
	 * */
	public String getInvoiceDetailByInvoiceNumberTotalPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info("Enter method getInvoiceDetailByInvoiceNumberTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getInvoiceDetailByInvoiceNumberTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getInvoiceDetailByInvoiceNumberTotalPageNo.");
		return s;
	}
	
	/**
	 * @author dongjian 
	 * get Applying Credit Payment
	 * */
	public String getInvoiceDetailByInvoiceNumber(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getInvoiceDetailByInvoiceNumber.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getInvoiceDetailByInvoiceNumber(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getInvoiceDetailByInvoiceNumber.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Applying Credit Payment
	 * */
	public String getApplyingCreditPayment(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getApplyingCreditPayment.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getApplyingCreditPayment(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getApplyingCreditPayment.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Applying Credit Payment page no 
	 * */
	public String getApplyingCreditPaymentTotalPageNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getApplyingCreditPaymentTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getApplyingCreditPaymentTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getApplyingCreditPaymentTotalPageNo.");
			return s;
		}
 
		/**
	 * @author dongjian 
	 * get Current Invoice Payment
	 * */
	public String getCurrentInvoicePayment(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getCurrentInvoicePayment.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getCurrentInvoicePayment(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getCurrentInvoicePayment.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Current Invoice Payment page no
	 * */
	public String getCurrentInvoicePaymentTotalPageNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getCurrentInvoicePaymentTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getCurrentInvoicePaymentTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getCurrentInvoicePaymentTotalPageNo.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Manually Created Payment
	 * */
	public String getManuallyCreatedPayment(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getManuallyCreatedPayment.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getManuallyCreatedPayment(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getManuallyCreatedPayment.");
		return s;
	}
	/**
	 * @author Chao.Liu
	 * DownLoad Current Invoice Payment Excel 
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByPaymentTabPCIP(SearchInvoiceVO svo,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException  {
		logger.info("Exit method saveExcelByPaymentTabPCIP.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getCurrentInvoicePaymentTotalPageNo(svo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,invoiceNumber);
			
			jExcelUtil.writeTitle(0, titles);
			if(count <= recPerPage){
				svo.setPageNo(1);
				svo.setRecPerPage((int)count);
				list = invoiceDetailDao.getCurrentInvoicePaymentObject(svo);
				for(int i=0;i<list.size();i++){
					Object[] o = list.get(i); 
//						o[1] = CcmFormat.formatCurrency(new Double(o[1].toString()));
//						o[3] = "";
					jExcelUtil.writeLine(i+1, o); 
				}
			}else{
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for(int j = 0 ; j < totlePage; j++){
					svo.setPageNo(j + 1);
					svo.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getCurrentInvoicePaymentObject(svo);
					for(int i = 0; i < list.size(); i++){
						Object[] o = list.get(i);
//							o[1] = CcmFormat.formatCurrency(new Double(o[1].toString()));
//							o[3] = "";
						jExcelUtil.writeLine(j * recPerPage + i + 1, o); 
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 20, 52, 20, 25, 25});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method saveExcelByPaymentTabPCIP.");
		return excelDirPath;
	}
	
	/**
	 * @author dongjian 
	 * get Manually Created Payment page no
	 * */
	public String getManuallyCreatedPaymentTotalPageNo(SearchInvoiceVO svo)
				throws BPLException {
		logger.info("Enter method getManuallyCreatedPaymentTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getManuallyCreatedPaymentTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getManuallyCreatedPaymentTotalPageNo.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Dispute Payback Payment
	 * */
	public String getDisputePaybackPayment(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getDisputePaybackPayment.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getDisputePaybackPayment(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getDisputePaybackPayment.");
		return s;
	}
	/**
	 * @author Chao.Liu
	 * DownLoad Manually Created Payment Excel
	 * @return
	 * @throws Exception
	 */
	public String saveExcelByPaymentTabPMCP(SearchInvoiceVO svo,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException  {
		logger.info("Exit method saveExcelByPaymentTabPMCP.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getManuallyCreatedPaymentTotalPageNo(svo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,invoiceNumber);
			
			jExcelUtil.writeTitle(0, titles);
			if(count <= recPerPage){
				svo.setPageNo(1);
				svo.setRecPerPage((int)count);
				list = invoiceDetailDao.getManuallyCreatedPaymentObject(svo);
				for(int i=0;i<list.size();i++){
					Object[] o = list.get(i);
//						o[1] = CcmFormat.formatCurrency(new Double(o[1].toString()));
//						o[3] = "";
					jExcelUtil.writeLine(i+1, o); 
				}
			}else{
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for(int j = 0 ; j < totlePage; j++){
					svo.setPageNo(j + 1);
					svo.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getManuallyCreatedPaymentObject(svo);
					for(int i = 0; i < list.size(); i++){
						Object[] o = list.get(i);
//							o[1] = CcmFormat.formatCurrency(new Double(o[1].toString()));
//							o[3] = "";
						jExcelUtil.writeLine(j * recPerPage + i + 1, o); 
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 60, 25, 25});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method saveExcelByPaymentTabPMCP.");
		return excelDirPath;
	}
	/**
	 * @author pengfei.wang
	 * download Excel to payment
	 * */
	public String saveExcelByPaymentTabMISC(SearchInvoiceVO svo,String excelDirPath,List<String> titles, String invoiceNumber) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("Down Excel by Misc Adjustment ", svo));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getPaymenttablaTotalNO(svo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,invoiceNumber);
			jExcelUtil.removeFieldName(titles);
			jExcelUtil.writeTitle(0, titles);
			if(count <= recPerPage){
				svo.setPageNo(1);
				svo.setRecPerPage((int)count);
				list = (List)invoiceDetailDao.getMiscPaymentObject(svo);
				for(int i=0;i<list.size();i++){
					Object[] o = list.get(i);
					jExcelUtil.writeLine(i+1, o); 
				}
			}else{
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for(int j = 0 ; j < totlePage; j++){
					svo.setPageNo(j + 1);
					svo.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getMiscPaymentObject(svo);
					for(int i = 0; i < list.size(); i++){
						Object[] o = list.get(i);
						jExcelUtil.writeLine(j * recPerPage + i + 1, o); 
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 55, 25, 65, 30, 30});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Exception  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	/**
	 * @author dongjian 
	 * get Dispute Payback Payment PageNo
	 * */
	public String getDisputePaybackPaymentTotalPageNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getDisputePaybackPaymentTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getDisputePaybackPaymentTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getDisputePaybackPaymentTotalPageNo.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Current Dispute
	 * */
	public String getCurrentDispute(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getCurrentDispute.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getCurrentDispute(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getCurrentDispute.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Current Dispute PageNo
	 * */
	public String getCurrentDisputeTotalPageNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getCurrentDisputeTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getCurrentDisputeTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getCurrentDisputeTotalPageNo.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Current Invoice History page
	 * */
	public String getCurrentInvoiceHistory(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getCurrentInvoiceHistory.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getCurrentInvoiceHistory(svo);
			s = svo.getListJsonCompatibleByAll(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getCurrentInvoiceHistory.");
		return s;
	}

	/**
	 * @author dongjian 
	 * get Current Invoice Past Three Payment page
	 * */
	public String getPastThreePaymentPage(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getPastThreePaymentPage.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getPastThreePaymentPage(svo);
			s = svo.getListJsonCompatibleByAll(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getPastThreePaymentPage.");
		return s;
	}

//		public String handleInvoiceWorkflor(SearchInvoiceVO svo) {
//			logger.info("Enter method handleInvoiceWorkflor.");
//			String s = "1";
//			try {
//				invoiceDetailDao.handleInvoiceWorkflor(svo);
//			} catch (RuntimeException e) {
//				logger.equals(e);
//				throw e;
//			}
//			logger.info("Exit method handleInvoiceWorkflor.");
//			return s;
//		}
	/**
	 * @author pengfei.wang
	 * Down Excel
	 * */
	public String currentDisputeToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Down Excel by CurrentDisput ", searchInvoiceVO));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getCurrentDisputeTotalPageNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getCurrentDisputeDownExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getCurrentDisputeDownExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 25, 25, 25, 25, 25, 25, 25,
					25, 25, 20, 20 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}

	
	public String saveExcelByActionHistory(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info("Exit method saveExcelByActionHistory.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			list = invoiceDetailDao.getCurrentInvoiceHistoryToExcel(svo);
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}
			jExcelUtil.setColumnView(new int[] { 25, 25, 25, 25, 25, 25, 25,25, 25, 20, 20 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method saveExcelByActionHistory.");
		return excelDirPath;
	}

	public String applyingCreditToExcel(SearchInvoiceVO searchInvoiceVO,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info("Exit method applyingCreditToExcel.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getApplyingCreditPaymentTotalPageNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getApplyingCreditToExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getApplyingCreditToExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 60, 25, 25});
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method applyingCreditToExcel.");
		return excelDirPath;
	}

	public String disputePaybackToExcel(SearchInvoiceVO searchInvoiceVO,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info("Exit method disputePaybackToExcel.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getDisputePaybackPaymentTotalPageNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getDisputePaybackToExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getDisputePaybackToExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 60, 25, 25});
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method disputePaybackToExcel.");
		return excelDirPath;
	}
	
	public String miscDisputePaybackToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info("Exit method miscDisputePaybackToExcel.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getMiscTotalNO(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.removeFieldName(titles);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getMiscDisputePaybackToExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getMiscDisputePaybackToExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 60, 25, 25});
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method miscDisputePaybackToExcel.");
		return excelDirPath;
	}

	public String AllPaymentsToExcel(SearchInvoiceVO searchInvoiceVO, String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info("Exit method AllPaymentsToExcel.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getPaymentScoaAmountTotalPageNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getPaymentScoaAmountToExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getPaymentScoaAmountToExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 60, 25, 25});
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method AllPaymentsToExcel.");
		return excelDirPath;
	}

	/**
	 * @author dongjian
	 * Through the workflow user basis, to approve or on project or hold
	 * @throws BPLException 
	 * @throws AuthenticationFailedException 
	 * */
	public Invoice updateInvoiceWorkflow(Invoice invoice, Integer workflowActionId, String uploadsMessage, String oldInvoiceStatusId) throws BPLException, AuthenticationFailedException {
		logger.info("Enter method updateInvoiceWorkflow.");
		
		if (oldInvoiceStatusId != null && invoice.getInvoiceStatus() != null) {
			if (!oldInvoiceStatusId.equals(String.valueOf(invoice.getInvoiceStatus().getId()))) {
				String messageKey = "8"; //Failed to update invoice because its status had been changed by someone else!
				String errorMessage = invoiceDetailDao.showMessages(messageKey);
				BPLException bpe = new BPLException(errorMessage);
				throw bpe;
			}
		}
		
		Invoice i = null;
		if(workflowActionId == 1){
			i = updateInvoiceApprove(invoice,uploadsMessage,oldInvoiceStatusId);
		}
		if(workflowActionId == 2){
			i = updateInvoiceHold(invoice,uploadsMessage);
		}
		if(workflowActionId == 3){
			i = updateInvoiceReject(invoice,uploadsMessage);
		}
		
		invoiceDetailDao.updateActionRequest(invoice.getId());
		
		logger.info("Exit method updateInvoiceWorkflow.");
		return i;
	}
	
	/**
	 * @author dongjian
	 * Through the workflow user basis, to approve or on project or hold
	 * @throws BPLException 
	 * */
	public Invoice updateInvoiceWorkflowAndSaveMsMessage(Invoice invoice, Integer workflowActionId, String uploadsMessage, String oldInvoiceStatusId,AttachmentPoint point,String approveWinUserId,String approveWinRoleId) throws BPLException {
		logger.info("Enter method updateInvoiceWorkflowAndSaveMsMessage.");
		
		if (oldInvoiceStatusId != null && invoice.getInvoiceStatus() != null) {
			if (!oldInvoiceStatusId.equals(String.valueOf(invoice.getInvoiceStatus().getId()))) {
				String messageKey = "8"; //Failed to update invoice because its status had been changed by someone else!
				String errorMessage = invoiceDetailDao.showMessages(messageKey);
				BPLException bpe = new BPLException(errorMessage);
				throw bpe;
			}
		}
		Invoice i = null;
		if(workflowActionId == 1){
			i = updateInvoiceApprove(invoice,uploadsMessage,oldInvoiceStatusId);
		}
		if(workflowActionId == 2){
			i = updateInvoiceHold(invoice,uploadsMessage);
		}
		if(workflowActionId == 3){
			i = updateInvoiceReject(invoice,uploadsMessage);
		}
		Integer pointId = point != null ? point.getId() : null;
		String result = super.saveMsMessage(invoiceNoteDao,"", "", approveWinUserId, approveWinRoleId, uploadsMessage, pointId, invoice.getId().toString(), false);
		
		if(!"".equals(result)){
			String s[] = result.split(",");
			if(s.length == 2){
				i.setMessageId(s[0]);
				i.setChannelId(s[1]);
			}
		}
		invoiceDetailDao.updateActionRequest(invoice.getId());
		
		logger.info("Exit method updateInvoiceWorkflowAndSaveMsMessage.");
		return i;
	}

	/**
	 * @author dongjian
	 * Through the workflow user basis, to project 
	 * */
	private Invoice updateInvoiceReject(Invoice invoice, String message) {
//			invoiceDetailDao.setBanProcessStatus(invoice.getBan().getId(),null);
		invoiceDetailDao.clearReconcilePaymentInvoiceId(invoice);
		int oldStatusId = invoice.getInvoiceStatus().getId();
		Integer newStatusId = null;
		Integer assignmentUserId = SystemUtil.getCurrentUserId();
		if(oldStatusId>=10 && oldStatusId<=20){
			newStatusId = 10;
		}
		if(oldStatusId>=21 && oldStatusId<=24){
			newStatusId = 11;
		}
		if(oldStatusId>=25 && oldStatusId<=28){
			newStatusId = 12;
		}
		if(oldStatusId>=29 && oldStatusId<=32){
			newStatusId = 13;
		}
		if(oldStatusId>=33 && oldStatusId<=36){
			newStatusId = 14;
		}
		invoice.setDisputeAmount(0d);
		invoice.setPaymentAmount(0d);
		invoice.setMiscAdjustmentAmount(0d);
//			invoice.setInvoiceStatus(invoiceStatusDao.load(newStatusId));
		assignmentUserId = invoice.getAssignedAnalystId();
		return updateInvoice(invoice,newStatusId,assignmentUserId,3,message);
	}

	/**
	 * @author dongjian
	 * Through the workflow user basis, to  hold
	 * */
	private Invoice updateInvoiceHold(Invoice invoice, String message) {
//			invoiceDetailDao.setBanProcessStatus(invoice.getBan().getId(),null);
		Integer oldStatusId = null;
		Integer newStatusId = null;
		Integer assignmentUserId = SystemUtil.getCurrentUserId();
		if(oldStatusId>=10 && oldStatusId<=20){
			newStatusId = 20;
		}
		if(oldStatusId>=21 && oldStatusId<=24){
			newStatusId = 24;
		}
		if(oldStatusId>=25 && oldStatusId<=28){
			newStatusId = 28;
		}
		if(oldStatusId>=29 && oldStatusId<=32){
			newStatusId = 32;
		}
		if(oldStatusId>=33 && oldStatusId<=36){
			newStatusId = 36;
		}
		assignmentUserId = invoiceDetailDao.getAssignmentUserId(invoice);
		return updateInvoice(invoice,newStatusId,assignmentUserId,2,message);
	}

	/**
	 * @author dongjian
	 * Through the workflow user basis, to approve 
	 * @throws BPLException 
	 * */
	private Invoice updateInvoiceApprove(Invoice invoice, String message, String oldInvoiceStatusId) throws BPLException {
//			invoiceDetailDao.setBanProcessStatus(invoice.getBan().getId(),"L");
		try {
			invoiceDetailDao.setReconcilePaymentInvoiceId(invoice);
			invoiceDetailDao.callSpApproveInvoices(invoice.getId(), 1, message, oldInvoiceStatusId);
//			int oldStatusId = invoice.getInvoiceStatus().getId();
//			Integer newStatusId = null;
//			Integer assignmentUserId = null;
//			if(oldStatusId>=10 && oldStatusId<=20){
//				newStatusId = 21;
//			}
//			if(oldStatusId>=21 && oldStatusId<=24){
//				newStatusId = 25;
//			}
//			if(oldStatusId>=25 && oldStatusId<=28){
//				newStatusId = 29;
//			}
//			if(oldStatusId>=29 && oldStatusId<=32){
//				newStatusId = 33;
//			}
//			if(oldStatusId>=33 && oldStatusId<=36){
//				newStatusId = 37;
//			}
//			this.setInvoiceAmounts(invoice);
//			assignmentUserId = invoiceDetailDao.getAssignmentUserId(invoice);
//			return updateInvoice(invoice,newStatusId,assignmentUserId,1,message);
			return invoiceDao.refreshInvoiceNoCache(invoice);
		
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	/**
	 * @author dongjian
	 * update invoice
	 * */
	private Invoice updateInvoice(Invoice invoice,Integer newStatusId,Integer assignmentUserId, int workflowActionId, String message) {
		invoice.setInvoiceStatus(invoiceStatusDao.load(newStatusId));
		invoice.setUserByAssignmentUserId(userDao.loadUser(assignmentUserId));
		invoice.setWorkflowAction(workflowActionDao.load(workflowActionId));
		invoice.setUserByWorkflowUserId(SystemUtil.getCurrentUser());
		invoice.setModifiedTimestamp(new Date());
		invoice.setModifiedBy(SystemUtil.getCurrentUserId());
		invoice.setStatusTimestamp(new Date());
		invoice.setNotes(message);
		invoiceDao.merge(invoice);
		return invoice;
	}

	/**
	 * @author dongjian
	 * get Invoice Credit Balance
	 * */
	public Long getInvoiceCreditBalance(Integer id) throws BPLException {
		Long d = invoiceDetailDao.getInvoiceCreditBalance(id);
		return d;
	}

	/**
	 * @author dongjian
	 * get Proposal Amount
	 * */
	public Double[] getProposalAmount(Integer id) throws BPLException {
		Double[] ds = invoiceDetailDao.getProposalAmount(id);
		return ds;
	}
	public IProposalDao getProposalDao() {
		return proposalDao;
	}

	public void setProposalDao(IProposalDao proposalDao) {
		this.proposalDao = proposalDao;
	}
	public InvoiceStatus getInvoiceStatus(Integer invoiceStatusId) throws BPLException {
		return invoiceStatusDao.find(invoiceStatusId);
	}

	public IInvoiceStatusDao getInvoiceStatusDao() {
		return invoiceStatusDao;
	}

	public void setInvoiceStatusDao(IInvoiceStatusDao invoiceStatusDao) {
		this.invoiceStatusDao = invoiceStatusDao;
	}

	/**
	 * @author dongjian
	 * search Paid Dispute Payment
	 * */
	public String getPaidDisputePayment(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getPaidDisputePayment.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getPaidDisputePayment(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getPaidDisputePayment.");
		return s;
	}

	/**
	 * @author dongjian
	 * search Paid Dispute Payment page no
	 * */
	public String getPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getPaidDisputePaymentTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getPaidDisputePaymentTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getPaidDisputePaymentTotalPageNo.");
		return s;
	}

	/**
	 * @author dongjian
	 * search Short Paid Dispute by Payment
	 * */
	public String getShortPaidDisputePayment(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getShortPaidDisputePayment.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getShortPaidDisputePayment(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getShortPaidDisputePayment.");
		return s;
	}

	/**
	 * @author dongjian
	 * search Short Paid Dispute by Payment page no
	 * */
	public String getShortPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getShortPaidDisputePaymentTotalPageNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getShortPaidDisputePaymentTotalPageNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getShortPaidDisputePaymentTotalPageNo.");
		return s;
	}
//		public String getPastThreePaymentPageTotalPageNo(SearchInvoiceVO svo)
//				throws BPLException {
//			logger.info("Enter method getPastThreePaymentPageTotalPageNo.");
//			long count = 0;
//			try {
//				count = invoiceDetailDao.getPastThreePaymentPageTotalPageNo(svo);
//			} catch (RuntimeException e) {
//				logger.error(CcmFormat.formatErrorLog(e));
//				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
//				throw bpe;
//			}
//			String s = svo.getTotalPageNoJson(count);
//			logger.info("Exit method getPastThreePaymentPageTotalPageNo.");
//			return s;
//		}

	/**
	 * @author dongjian
	 * download Excel By Paid Dispute Payment
	 * */
	public String getExcelByPaidDisputePayment(SearchInvoiceVO searchInvoiceVO,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info("Exit method getExcelByPaidDisputePayment.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getPaidDisputePaymentTotalPageNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getExcelByPaidDisputePayment(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getExcelByPaidDisputePayment(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 60, 25, 25});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getExcelByPaidDisputePayment.");
		return excelDirPath;
	}

	/**
	 * @author dongjian
	 * download Excel By Short Paid Dispute Payment
	 * */
	public String getExcelByShortPaidDisputePayment(SearchInvoiceVO searchInvoiceVO,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info("Exit method getExcelByShortPaidDisputePayment.");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getShortPaidDisputePaymentTotalPageNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getExcelByShortPaidDisputePayment(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getExcelByShortPaidDisputePayment(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 60, 25, 25});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getExcelByShortPaidDisputePayment.");
		return excelDirPath;
	}
	
	/**
	 * @author dongjian
	 * ge tUngrouped Dispute Count
	 * */
	public Long getUngroupedDisputeCount(Integer id) throws BPLException {
		return invoiceDetailDao.getUngroupedDisputeCount(id);
	}

	/**
	 * @author dongjian
	 * Verify the BAN
	 * */
	public String validateBanLock(Integer invoiceId) throws BPLException {
		String result = null;
		try {
			Invoice invoice = invoiceDetailDao.findInvoiceById(invoiceId);
			String invoiceStatusId = ((invoice.getInvoiceStatus() == null) ? "null" : invoice.getInvoiceStatus().getId()+"");
			String banProcess = (invoice.getBan() == null || invoice.getBan().getProcessStatus() == null) ? "null" : "'"+invoice.getBan().getProcessStatus()+"'";
			result = "{invoiceStautsId:"+invoiceStatusId+", banProcess:"+banProcess+"}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return result;
	}
	/**
	 * @author Jian.Dong
	 * Get Invoice Page Credit Tab Info Totail Number
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getOutstandingCreditPageTotalNo(SearchInvoiceVO svo)throws BPLException {
		logger.info("Enter method getOutstandingCreditPageTotalNo.");
		String result = null;
		try {
			Long totalResult = invoiceDetailDao.getOutstandingCreditPageTotalNo(svo);
			result = svo.getTotalPageNoJson(totalResult);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getOutstandingCreditPageTotalNo.");
		return result;
	}
	/**
	 * @author Jian.Dong
	 * Get Invoice Page Credit Tab Info List
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getOutstandingCreditPageData(SearchInvoiceVO svo)throws BPLException {
		logger.info("Enter method getOutstandingCreditPageData.");
		List<String> list = null;
		String result = null;
		try {
			list = invoiceDetailDao.getOutstandingCreditPageData(svo);
			result = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info("Exit method getOutstandingCreditPageData.");
		return result;
	}
	
	/**
	 * @author dongjian
	 * do Apply Credit And Apply To Payment
	 * */
	public String updateCreditToCurrentPayment(String inProposalIds) {
		logger.info("Enter method updateCreditToCurrentPayment.");
		String result = null;
		try {
			invoiceDetailDao.updateCreditToCurrentPayment(inProposalIds);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info("Exit method updateCreditToCurrentPayment.");
		return result;
	}

	/**
	 * @author dongjian
	 * do Manual deleteReconciliationMany
	 * */
	public String deleteReconciliationMany(String inIds) {
		logger.info("Enter method deleteReconciliationMany.");
		String result = null;
		int closeError = 0;
		try {
			String[] ids = inIds.split(",");
			for(String id : ids){
				if(id != null && !"".equals(id)){
					result =deleteReconciliation(Integer.parseInt(id));
					if("error".equals(result)){
						closeError = closeError+1;
					}
				}
			}
			result = "{message:\""+(ids.length - closeError)+" disputes deleted successful!\"}";
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method deleteReconciliationMany.");
		return result;
	}
	
	/**
	 * @author dongjian
	 * do Manual Reconciliation
	 * */
	public String deleteReconciliation(Integer reconcileId)  {
		//logger.info("Enter method deleteReconciliation.");
		String result = null;
		try {
			Reconcile r = reconcileDao.findById(reconcileId);
			Integer disputeStatusId = r.getDispute().getDisputeStatus().getId();
			if(disputeStatusId.intValue() == 99){
					return "error";			
			}
			r.setRecActiveFlag("N");
			r.setModifiedBy(SystemUtil.getCurrentUserId());
			r.setModifiedTimestamp(new Date());
			
			Double amount = r.getReconcileAmount();
			Proposal creditProposal = r.getCreditProposal();
			Proposal disputeProposal =  r.getDisputeProposal();
			Dispute d = (disputeProposal == null || disputeProposal.getDispute() == null) ? r.getDispute() : disputeProposal.getDispute();
			double disputeBalance = disputeDao.getBalanceAmountByProposal(d.getId());
			if(d != null){
				Double outstandingReserveAmount = d.getOutstandingReserveAmount() == null ? 0 : d.getOutstandingReserveAmount();
				Double releasedReserveAmount = r.getReleasedReserveAmount() == null ? 0 : r.getReleasedReserveAmount();
				d.setOutstandingReserveAmount(outstandingReserveAmount+releasedReserveAmount);
				d.setDisputeBalance(disputeBalance+amount);
				d.setModifiedBy(SystemUtil.getCurrentUserId());
				d.setModifiedTimestamp(new Date());
				disputeDao.merge(d);
			}
			if(creditProposal != null){
				creditProposal.setBalanceAmount((creditProposal.getBalanceAmount()==null)? 0 - amount : creditProposal.getBalanceAmount() - amount); 
				creditProposal.setModifiedBy(r.getModifiedBy());
				creditProposal.setModifiedTimestamp(r.getModifiedTimestamp());
				proposalDao.merge(creditProposal);
			}
			if(disputeProposal != null){
				disputeProposal.setBalanceAmount((disputeProposal.getBalanceAmount()==null)? 0 + amount : disputeProposal.getBalanceAmount() + amount);
				disputeProposal.setModifiedBy(r.getModifiedBy());
				disputeProposal.setModifiedTimestamp(r.getModifiedTimestamp());
				proposalDao.merge(disputeProposal);
			}
			reconcileDao.merge(r);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		//logger.info("Exit method deleteReconciliation.");
		return result;
	}

	/**
	 * @author dongjian
	 * do Record Dispute And Apply To Payment
	 * */
	public String updateRecordDisputeAndApplyToPayment(Integer invoiceId,String inProposalIds, AttachmentPoint point, String notes) {
		logger.info("Enter method updateRecordDisputeAndApplyToPayment.");
		String result = null;
		try {
			List<Proposal> ps = invoiceDetailDao.findCredits(inProposalIds);
			Date dateTime = new Date();
			for(Proposal p : ps){
				Reconcile r = new Reconcile();
				r.setCreditProposal(p);
				r.setCreditInvoice(invoiceDao.load(invoiceId));
				r.setReconcileAmount(p.getBalanceAmount()*-1);
				r.setAccountCode(p.getAccountCode());
				r.setNotes(notes);
				r.setAttachmentPoint(point);
				r.setReconcileStatus(reconcileStatusDao.load(4));
				r.setCreatedBy(SystemUtil.getCurrentUserId());
				r.setCreatedTimestamp(dateTime);
				r.setModifiedBy(SystemUtil.getCurrentUserId());
				r.setModifiedTimestamp(dateTime);
				r.setReconcileDate(dateTime);
				r.setRecActiveFlag("Y");
				reconcileDao.save(r);
			}
			invoiceDetailDao.cleanBlanceAmount(inProposalIds);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info("Exit method updateRecordDisputeAndApplyToPayment.");
		return result;
	}

	/**
	 * @author dongjian
	 * do more Outstanding Credit Split Apply
	 * */
	public String updateOutstandingCreditSplitApply1(Integer invoiceId, Long proposalId, AttachmentPoint point, String notes,
			Double balanceAmount,Integer accountCodeId) {
		logger.info("Enter method updateRecordDisputeAndApplyToPayment.");
		String result = null;
		try {
			Date dateTime = new Date();
			Proposal p = invoiceDetailDao.findCredit(proposalId);
			Reconcile r = new Reconcile();
			r.setCreditProposal(p);
			r.setCreditInvoice(invoiceDao.load(invoiceId));
			r.setReconcileAmount(balanceAmount*-1);
			r.setAccountCode( invoiceDetailDao.loadAccountCode(accountCodeId) );
			r.setNotes(notes);
			r.setAttachmentPoint(point);
			r.setReconcileStatus(reconcileStatusDao.load(4));
			r.setCreatedBy(SystemUtil.getCurrentUserId());
			r.setCreatedTimestamp(dateTime);
			r.setModifiedBy(SystemUtil.getCurrentUserId());
			r.setModifiedTimestamp(dateTime);
			r.setReconcileDate(dateTime);
			r.setRecActiveFlag("Y");
			reconcileDao.save(r);
			p.setBalanceAmount(p.getBalanceAmount()-balanceAmount);
			p.setModifiedTimestamp(r.getModifiedTimestamp());
			p.setModifiedBy(r.getModifiedBy());
			proposalDao.merge(p);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info("Exit method updateRecordDisputeAndApplyToPayment.");
		return result;
	}

	/**
	 * @author dongjian
	 * do Outstanding Credit Split Apply
	 * */
	public String updateOutstandingCreditSplitApply2(Integer invoiceId,
			Long proposalId, AttachmentPoint point, String notes,
			Double balanceAmount, Integer accountCodeId) {
		logger.info("Enter method doOutstandingCreditSplitApply2.");
		String result = null;
		try {
			invoiceDetailDao.updateOutstandingCreditSplitApply2(invoiceId, proposalId, point, notes,balanceAmount, accountCodeId);
//			Date dateTime = new Date();
//			Proposal p = invoiceDetailDao.findCredit(proposalId);
//			Reconcile r = new Reconcile();
//			r.setCreditProposal(p);
//			r.setCreditInvoice(invoiceDao.load(invoiceId));
//			r.setReconcileAmount(balanceAmount*-1);
//			r.setAccountCode( invoiceDetailDao.loadAccountCode(accountCodeId) );
//			r.setNotes(notes);
//			r.setAttachmentPoint(point);
//			r.setReconcileStatus(reconcileStatusDao.load(3));
//			r.setCreatedBy(SystemUtil.getCurrentUserId());
//			r.setCreatedTimestamp(dateTime);
//			r.setModifiedBy(SystemUtil.getCurrentUserId());
//			r.setModifiedTimestamp(dateTime);
//			r.setRecActiveFlag("Y");
//			r.setReconcileDate(dateTime);
//			reconcileDao.save(r);
//			p.setBalanceAmount(p.getBalanceAmount()-balanceAmount);
//			p.setModifiedTimestamp(r.getModifiedTimestamp());
//			p.setModifiedBy(r.getModifiedBy());
//			proposalDao.merge(p);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info("Exit method doOutstandingCreditSplitApply2.");
		return result;
	}

	public String getCloseAsDisputeLosePageData(SearchInvoiceVO svo) 
			throws BPLException {
		logger.info("Enter method getCloseAsDisputeLosePageData.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getCloseAsDisputeLosePageData(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getCloseAsDisputeLosePageData.");
		return s;
	}
	
	
	public String getCloseAsDisputeLosePageTotalNo(SearchInvoiceVO svo) 
			throws BPLException {
		logger.info("Enter method getCloseAsDisputeLosePageTotalNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getCloseAsDisputeLosePageTotalNo(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getCloseAsDisputeLosePageTotalNo.");
		return s;
	}
	public String getCloseAsDisputeWinPageData(SearchInvoiceVO svo) 
	throws BPLException {
        logger.info("Enter method getCloseAsDisputeWinPageData.");
        String s = null;
        List list = null;
        try {
             list = invoiceDetailDao.getCloseAsDisputeWinPageData(svo);
             s = svo.getListJsonCompatible(list);
        } catch (Exception e) {
             logger.error(CcmFormat.formatErrorLog(e));
             BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
             throw bpe;
         }
         logger.info("Exit method getCloseAsDisputeWinPageData.");
         return s;
      }


    public String getCloseAsDisputeWinPageTotalNo(SearchInvoiceVO svo) 
    throws BPLException {
        logger.info("Enter method getCloseAsDisputeWinPageTotalNo.");
        long count = 0;
        try {
            	count = invoiceDetailDao.getCloseAsDisputeWinPageTotalNo(svo);
             } catch (Exception e) {
                logger.error(CcmFormat.formatErrorLog(e));
                BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
                throw bpe;
             }
         String s = svo.getTotalPageNoJson(count);
         logger.info("Exit method getCloseAsDisputeWinPageTotalNo.");
         return s;
     }
	
	
	/**
	 * @author dongjian
	 * search outstanding dispute 
	 * */
	public String getNotReconcileDisputePageData(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getNotReconcileDisputePageData.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getNotReconcileDisputePageData(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getNotReconcileDisputePageData.");
		return s;
	}

	/**
	 * @author dongjian
	 * search outstanding dispute total no.
	 * */
	public String getNotReconcileDisputePageTotalNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getNotReconcileDisputePageTotalNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getNotReconcileDisputePageTotalNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info("Exit method getNotReconcileDisputePageTotalNo.");
		return s;
	}

	/**
	 * @author dongjian
	 * Through a dispute, at more specified amount, create the designated reconcile
	 * */
	public String createReconcileByCurrentCreditM1(String inProposalIds,
			Integer invoiceId, Long disputeId, AttachmentPoint point,
			String notes,Integer accountCodeId) {
		logger.info("Enter method createReconcileByCurrentCreditM1.");
		String result = null;
		boolean out  = false;
		try {
			List<Proposal> ps = invoiceDetailDao.findCredits(inProposalIds);
			Proposal disputeProposal = invoiceDao.getProposal(disputeId);
			Dispute d = disputeProposal.getDispute();
			AccountCode ac = accountCodeId == null ? null : invoiceDetailDao.loadAccountCode(accountCodeId);
//			Double balanceAmount = 0d;
			Date dateTime = new Date();
			for(Proposal creditProposal : ps){
				Reconcile r = new Reconcile();
				r.setCreditProposal(creditProposal);
				r.setCreditInvoice(invoiceDao.load(invoiceId));
				r.setDisputeProposal(disputeProposal);
				r.setDispute(d);
			//	r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(creditProposal.getBalanceAmount(),d,false));
			//	r.setReconcileAmount(creditProposal.getBalanceAmount()*-1);
				AccountCode ac_ = (ac == null ? (disputeProposal.getAccountCode() == null ? creditProposal.getAccountCode() : disputeProposal.getAccountCode()) : ac);
				r.setAccountCode(ac_);
//				balanceAmount += creditProposal.getBalanceAmount();
				r.setNotes(notes);
				r.setAttachmentPoint(point);
				r.setReconcileStatus(( "Y".equals(d.getFlagShortpaid()) ? reconcileStatusDao.load(2) : reconcileStatusDao.load(1)));
				r.setCreatedBy(SystemUtil.getCurrentUserId());
				r.setCreatedTimestamp(dateTime);
				r.setModifiedBy(SystemUtil.getCurrentUserId());
				r.setModifiedTimestamp(dateTime);
				r.setReconcileDate(dateTime);
				r.setRecActiveFlag("Y");

			    if (Math.abs(disputeProposal.getBalanceAmount()) > Math.abs(creditProposal.getBalanceAmount())){
					r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(creditProposal.getBalanceAmount(),d,false));
					r.setReconcileAmount(creditProposal.getBalanceAmount()*-1);
					disputeProposal.setBalanceAmount(disputeProposal.getBalanceAmount() + creditProposal.getBalanceAmount());
					creditProposal.setBalanceAmount(0d);
				}else {// dispute  小于单个credit
					r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(disputeProposal.getBalanceAmount()*-1,d,false));
					r.setReconcileAmount(disputeProposal.getBalanceAmount());
					creditProposal.setBalanceAmount(disputeProposal.getBalanceAmount() + creditProposal.getBalanceAmount());
					disputeProposal.setBalanceAmount(0d);
					out = true;
				}
				
				
				proposalDao.merge(disputeProposal);
				
				reconcileDao.save(r);
				disputeDao.merge(d);
				proposalDao.merge(creditProposal);
				proposalDao.commit();
				if(out){
					break;
				}
				
			}
//			invoiceDetailDao.cleanBlanceAmount(inProposalIds);
//			disputeProposal.setBalanceAmount(disputeProposal.getBalanceAmount() + balanceAmount);
//			proposalDao.merge(disputeProposal);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(e.getMessage());
			throw bpe;
		} 
		logger.info("Exit method createReconcileByCurrentCreditM1.");
		return result;
	}

	/**
	 * @author dongjian
	 * Through more dispute, at a specified amount, create the designated reconcile
	 * */
	public String createReconcileByCurrentCredit1M(Long creditProposalId,
			Integer invoiceId, String disputeProposalIds,
			AttachmentPoint point, String notes,Integer accountCodeId) {
		logger.info("Enter method createReconcileByCurrentCredit1M.");
		String result = null;
		try {
			List<Proposal> ps = invoiceDetailDao.findCredits(disputeProposalIds);
			Proposal creditProposal = invoiceDao.getProposal(creditProposalId);
//			AccountCode ac = accountCodeId == null ? null : invoiceDetailDao.loadAccountCode(accountCodeId);
//			Double balanceAmount = 0d;
//			Date dateTime = new Date();
			for(Proposal disputeProposal : ps){
				Double cBAmount = creditProposal.getBalanceAmount();
				Double dBAmount = disputeProposal.getBalanceAmount();
				Double disputeBalanceAmount = ((Math.abs(cBAmount) - Math.abs(dBAmount)) > 0 ?dBAmount:cBAmount*-1);
				if(creditProposal.getBalanceAmount() != 0 ){
					createReconcileByCurrentCredit11(creditProposalId,invoiceId,disputeProposal.getId(),point,notes,disputeBalanceAmount*-1,accountCodeId);
				}else{
					break;
				}
//				Dispute d = disputeProposal.getDispute();
//				Reconcile r = new Reconcile();
//				r.setCreditProposal(creditProposal);
//				r.setCreditInvoice(invoiceDao.load(invoiceId));
//				r.setDisputeProposal(disputeProposal);
//				AccountCode ac_ = (ac == null ? (disputeProposal.getAccountCode() == null ? creditProposal.getAccountCode() : disputeProposal.getAccountCode()) : ac);
//				r.setAccountCode(ac_);
//				r.setDispute(d);
//				r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(disputeProposal.getBalanceAmount()*-1,d,false));
//				r.setReconcileAmount(disputeProposal.getBalanceAmount());
//				balanceAmount += disputeProposal.getBalanceAmount();
//				r.setNotes(notes);
//				r.setAttachmentPoint(point);
//				r.setReconcileStatus(( "Y".equals(d.getFlagShortpaid()) ? reconcileStatusDao.load(2) : reconcileStatusDao.load(1)));
//				r.setCreatedBy(SystemUtil.getCurrentUserId());
//				r.setCreatedTimestamp(dateTime);
//				r.setModifiedBy(SystemUtil.getCurrentUserId());
//				r.setModifiedTimestamp(dateTime);
//				r.setRecActiveFlag("Y");
//				r.setReconcileDate(dateTime);
//				//d.setDisputeBalance((d.getDisputeBalance() == null ? 0 : d.getDisputeBalance()) - disputeProposal.getBalanceAmount());
//				disputeProposal.setBalanceAmount(0d);
//				
//				
//				reconcileDao.save(r);
//				disputeDao.merge(d);
//				proposalDao.merge(disputeProposal);
//				proposalDao.commit();
			}
//			invoiceDetailDao.cleanBlanceAmount(disputeProposalIds);
//				d.setDisputeAmount(a);
//				disputeDao.merge(d);
//			creditProposal.setBalanceAmount(creditProposal.getBalanceAmount() + balanceAmount);
//			creditProposal.setModifiedTimestamp(dateTime);
//			creditProposal.setModifiedBy(SystemUtil.getCurrentUserId());
//			proposalDao.merge(creditProposal);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(e.getMessage());
			throw bpe;
		} 
		logger.info("Exit method createReconcileByCurrentCredit1M.");
		return result;
	}
	/**
	 * @author dongjian
	 * Through a dispute, at a specified amount, create the designated reconcile
	 * */
	public String createReconcileByCurrentCredit11(Long creditProposalId,
			Integer invoiceId, Long disputeProposalId, AttachmentPoint point,
			String notes, Double balanceAmount,Integer accountCodeId) {
		logger.info("Enter method createReconcileByCurrentCredit11.");
//			balanceAmount = balanceAmount*-1;
		String result = null;
		try {
			Date dateTime = new Date();
			Proposal creditProposal = invoiceDao.getProposal(creditProposalId);
			Proposal disputeProposal = invoiceDao.getProposal(disputeProposalId);
			Dispute d = disputeProposal.getDispute();
			AccountCode ac = accountCodeId == null ? null : invoiceDetailDao.loadAccountCode(accountCodeId);
			Reconcile r = new Reconcile();
			r.setCreditProposal(creditProposal);
			r.setCreditInvoice(invoiceDao.load(invoiceId));
			r.setDisputeProposal(disputeProposal);
			r.setDispute(d);
			r.setReleasedReserveAmount(getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(balanceAmount,d,false)*-1);
			r.setReconcileAmount(balanceAmount*-1);
			r.setNotes(notes);
			r.setAttachmentPoint(point);
			AccountCode ac_ = (ac == null ? (disputeProposal.getAccountCode() == null ? creditProposal.getAccountCode() : disputeProposal.getAccountCode()) : ac);
			r.setAccountCode(ac_);
			r.setReconcileStatus(( "Y".equals(d.getFlagShortpaid()) ? reconcileStatusDao.load(2) : reconcileStatusDao.load(1)));
			r.setCreatedBy(SystemUtil.getCurrentUserId());
			r.setCreatedTimestamp(dateTime);
			r.setModifiedBy(SystemUtil.getCurrentUserId());
			r.setModifiedTimestamp(dateTime);
			r.setRecActiveFlag("Y");
			r.setReconcileDate(dateTime);
			reconcileDao.save(r);
			
			d.setModifiedBy(SystemUtil.getCurrentUserId());
			d.setModifiedTimestamp(dateTime);
			
			disputeDao.merge(d);
			creditProposal.setBalanceAmount(creditProposal.getBalanceAmount() - balanceAmount);
			creditProposal.setModifiedTimestamp(dateTime);
			creditProposal.setModifiedBy(SystemUtil.getCurrentUserId());
			disputeProposal.setBalanceAmount(disputeProposal.getBalanceAmount() + balanceAmount);
			disputeProposal.setModifiedTimestamp(dateTime);
			disputeProposal.setModifiedBy(SystemUtil.getCurrentUserId());
			proposalDao.merge(creditProposal);
			proposalDao.merge(disputeProposal);
			result = "{}";
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(e.getMessage());
			throw bpe;
		} 
		logger.info("Exit method createReconcileByCurrentCredit11.");
		return result;
	}
	/**
	 * @author jian.Dong
	 * xxxxxxxxxxxxxxxxxxxx
	 * @param disputeAmount 
	 * */
	private Double getReconcileReleaseReserveAmountAndSetDisputeOutstandingReserveAmount(Double disputeAmount, Dispute d , Boolean isDisputeFlag){
		if(d == null){
			return 0d;
		}else{
			double beforeOutstandingReserveAmount = (d.getOutstandingReserveAmount() == null ? 0.0D : d.getOutstandingReserveAmount());
			double beforeDisputeBalance = disputeDao.getBalanceAmountByProposal(d.getId());
			double disputeBalance = disputeDao.getBalanceAmountByProposal(d.getId());
			double outstandingReserveAmount = new BigDecimal((d.getOutstandingReserveAmount() == null ? 0 : d.getOutstandingReserveAmount())).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			double result = new BigDecimal(disputeAmount * (outstandingReserveAmount/disputeBalance)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			double outResult = 0;
			
			if(isDisputeFlag){
				d.setDisputeBalance(disputeBalance - disputeAmount);
				outResult = outstandingReserveAmount - result;
			}else{
				d.setDisputeBalance(disputeBalance + disputeAmount);
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
	 * @author dongjian
	 * search Reconciliation
	 * */
	public String getTheInvoiceReconciliationPageData(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getTheInvoiceReconciliationPageData.");
		String s = null;
		List list = null;
		try {
			list = invoiceDetailDao.getTheInvoiceReconciliationPageData(svo);
			s = svo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getTheInvoiceReconciliationPageData.");
		return s;
	}

	/**
	 * @author dongjian
	 * search Reconciliation total no.
	 * */
	public String getTheInvoiceReconciliationPageTotalNo(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method getTheInvoiceReconciliationPageTotalNo.");
		long count = 0;
		try {
			count = invoiceDetailDao.getTheInvoiceReconciliationPageTotalNo(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(count);
		logger.info(CcmFormat.formatExitLog());
		return s;
	}

	public IWorkflowActionDao getWorkflowActionDao() {
		return workflowActionDao;
	}

	public void setWorkflowActionDao(IWorkflowActionDao workflowActionDao) {
		this.workflowActionDao = workflowActionDao;
	}
	
	public IReconcileStatusDao getReconcileStatusDao() {
		return reconcileStatusDao;
	}

	public void setReconcileStatusDao(IReconcileStatusDao reconcileStatusDao) {
		this.reconcileStatusDao = reconcileStatusDao;
	}

	public IReconcileDao getReconcileDao() {
		return reconcileDao;
	}

	public IDisputeDao getDisputeDao() {
		return disputeDao;
	}

	public void setDisputeDao(IDisputeDao disputeDao) {
		this.disputeDao = disputeDao;
	} 
	public void setReconcileDao(IReconcileDao reconcileDao) {
		this.reconcileDao = reconcileDao;
	}

	public IInvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	public IBanDao getBanDao() {
		return banDao;
	}

	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}


	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}
	
	public Double getShortPaidDisputeAmount(Invoice invoice2)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get ShortPaid Dispute Amount", invoice2));
		Double r = null;
		try {
			String result = invoiceDetailDao.getTotalShortPaidDisputePayment(invoice2.getId());
			r = CcmFormat.paseCurrency(result).doubleValue();
		} catch (ParseException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return r;
	}

	/**
	 * @author dongjian
	 * get unlevel and unnotes proposal
	 * */
	public Long[] getUnLevelAndNotes(Invoice invoice2) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("getUnLevel And UnNotes", invoice2));
		Long[] r = null;
		try {
			r = invoiceDetailDao.getUnLevelAndNotes(invoice2);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return r;
	}
	
	/**
	 * Not Proposed amount to move to the payment or dispute the
	 * 
	 */
	public void updateInvoiceItemNotProposedAmount(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info("Enter method updateInvoiceItemNotProposedAmount.");
		try{
			String action = "";
			if(searchInvoiceVO.getNowProposalName().equals(INVOICE_ITEM_PROPOSAL_PAYMENT_AMOUNT)) action = "NP";
			if(searchInvoiceVO.getNowProposalName().equals(INVOICE_ITEM_PROPOSAL_DISPUTE_AMOUNT)) action = "ND";
			
			if(searchInvoiceVO.getInvoiceItemId() != null){
				//A invoice_item_id
				invoiceDetailDao.UpdateProposal(Integer.parseInt(searchInvoiceVO.getInvoiceItemId()),
						Integer.parseInt((searchInvoiceVO.getPastAccountCodeId() == null ? "-1" : searchInvoiceVO.getPastAccountCodeId())),
						Integer.parseInt((searchInvoiceVO.getPastDisputeCategoryId() == null ? "-1" : searchInvoiceVO.getPastDisputeCategoryId())),
						Integer.parseInt(searchInvoiceVO.getNowAccountCodeId()),
						Integer.parseInt(searchInvoiceVO.getNowDisputeCategoryId()),
						action,SystemUtil.getCurrentUserId(),
						(searchInvoiceVO.getNote() == null ? "" : searchInvoiceVO.getNote()));
			}else{
				//More invoice_item_id
				if(searchInvoiceVO.getBoxInId() != null){
					String[] boxId = searchInvoiceVO.getBoxInId().split(",");
					for(int i = 0; i < boxId.length; i++){
						invoiceDetailDao.UpdateProposal(Integer.parseInt(boxId[i]),
								Integer.parseInt((searchInvoiceVO.getPastAccountCodeId() == null ? "-1" : searchInvoiceVO.getPastAccountCodeId())),
								Integer.parseInt((searchInvoiceVO.getPastDisputeCategoryId() == null ? "-1" : searchInvoiceVO.getPastDisputeCategoryId())),
								Integer.parseInt(searchInvoiceVO.getNowAccountCodeId()),
								Integer.parseInt(searchInvoiceVO.getNowDisputeCategoryId()),
								action,SystemUtil.getCurrentUserId(),
								(searchInvoiceVO.getNote() == null ? "" : searchInvoiceVO.getNote()));
					}
				}
			}
		}catch(Exception e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * According to amend the development conditions of the Proposals of the Amount InvoiceItem
	 * 
	 */
	public void updateInvoiceItemProposalsAmount(SearchInvoiceVO sio) throws BPLException {
		logger.info("Enter method updateInvoiceItemProposalsAmount.");
		int size = 1;
		try {
			if(sio.getBoxInId() != null){
				String[] boxId = sio.getBoxInId().split(",");
				size = boxId.length;
			}
			
			for(int i = 0 ; i < size; i++){
				if(sio.getBoxInId() != null){
					String[] id = sio.getBoxInId().split(",");
					sio.setInvoiceItemId(id[i]);
				}
				
				if(sio.getPastProposalName().equals(sio.getNowProposalName())){
					//Proposal equal to the SCOA Code and the Dispute Category not equal
					invoiceDetailDao.UpdateProposal(Integer.parseInt(sio.getInvoiceItemId()),
							Integer.parseInt((sio.getPastAccountCodeId() == null ? "-1" : sio.getPastAccountCodeId())),
							Integer.parseInt((sio.getPastDisputeCategoryId() == null ? "-1" : sio.getPastDisputeCategoryId())),
							Integer.parseInt(sio.getNowAccountCodeId()),
							Integer.parseInt(sio.getNowDisputeCategoryId()),
							"NOT",SystemUtil.getCurrentUserId(),
							(sio.getNote() == null ? "" : sio.getNote()));
				}else{
					//Proposal does not equal
					if(sio.getNowProposalName().equals(INVOICE_ITEM_PROPOSAL_NOT_AMOUNT)){
						//Payment into 0
						if(sio.getPastProposalName().equals(INVOICE_ITEM_PROPOSAL_PAYMENT_AMOUNT)){
							invoiceDetailDao.UpdateProposal(Integer.parseInt(sio.getInvoiceItemId()),
									Integer.parseInt((sio.getPastAccountCodeId() == null ? "-1" : sio.getPastAccountCodeId())),
									Integer.parseInt((sio.getPastDisputeCategoryId() == null ? "-1" : sio.getPastDisputeCategoryId())),
									Integer.parseInt(sio.getNowAccountCodeId()),
									Integer.parseInt(sio.getNowDisputeCategoryId()),
									"P0",SystemUtil.getCurrentUserId(),
									(sio.getNote() == null ? "" : sio.getNote()));
						}
						//Dispute into 0
						if(sio.getPastProposalName().equals(INVOICE_ITEM_PROPOSAL_DISPUTE_AMOUNT)){
							invoiceDetailDao.UpdateProposal(Integer.parseInt(sio.getInvoiceItemId()),
									Integer.parseInt((sio.getPastAccountCodeId() == null ? "-1" : sio.getPastAccountCodeId())),
									Integer.parseInt((sio.getPastDisputeCategoryId() == null ? "-1" : sio.getPastDisputeCategoryId())),
									Integer.parseInt(sio.getNowAccountCodeId()),
									Integer.parseInt(sio.getNowDisputeCategoryId()),
									"D0",SystemUtil.getCurrentUserId(),
									(sio.getNote() == null ? "" : sio.getNote()));
						}
					}else{
						//Payment add, Dispute becomes 0
						if(sio.getNowProposalName().equals(INVOICE_ITEM_PROPOSAL_PAYMENT_AMOUNT)){
							invoiceDetailDao.UpdateProposal(Integer.parseInt(sio.getInvoiceItemId()),
									Integer.parseInt((sio.getPastAccountCodeId() == null ? "-1" : sio.getPastAccountCodeId())),
									Integer.parseInt((sio.getPastDisputeCategoryId() == null ? "-1" : sio.getPastDisputeCategoryId())),
									Integer.parseInt(sio.getNowAccountCodeId()),
									Integer.parseInt(sio.getNowDisputeCategoryId()),
									"DP",SystemUtil.getCurrentUserId(),
									(sio.getNote() == null ? "" : sio.getNote()));
						}
						//Dispute add, Payment becomes 0
						if(sio.getNowProposalName().equals(INVOICE_ITEM_PROPOSAL_DISPUTE_AMOUNT)){
							invoiceDetailDao.UpdateProposal(Integer.parseInt(sio.getInvoiceItemId()),
									Integer.parseInt((sio.getPastAccountCodeId() == null ? "-1" : sio.getPastAccountCodeId())),
									Integer.parseInt((sio.getPastDisputeCategoryId() == null ? "-1" : sio.getPastDisputeCategoryId())),
									Integer.parseInt(sio.getNowAccountCodeId()),
									Integer.parseInt(sio.getNowDisputeCategoryId()),
									"PD",SystemUtil.getCurrentUserId(),
									(sio.getNote() == null ? "" : sio.getNote()));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * But the change does not move the amount of SCOA Code and the combination of Dispute Category
	 * 
	 */
	public void updateInvoiceItemProposals(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info("Enter method updateInvoiceItemProposals.");
		try {
			String action = "NOT";
			if(searchInvoiceVO.getInvoiceItemId() != null){
				//A invoice_item_id
				invoiceDetailDao.UpdateProposal(Integer.parseInt(searchInvoiceVO.getInvoiceItemId()),
						Integer.parseInt((searchInvoiceVO.getPastAccountCodeId() == null ? "-1" : searchInvoiceVO.getPastAccountCodeId())),
						Integer.parseInt((searchInvoiceVO.getPastDisputeCategoryId() == null ? "-1" : searchInvoiceVO.getPastDisputeCategoryId())),
						Integer.parseInt(searchInvoiceVO.getNowAccountCodeId()),
						Integer.parseInt(searchInvoiceVO.getNowDisputeCategoryId()),
						action,SystemUtil.getCurrentUserId(),
						(searchInvoiceVO.getNote() == null ? "" : searchInvoiceVO.getNote()));
			}else{
				//More invoice_item_id
				if(searchInvoiceVO.getBoxInId() != null){
					String[] boxId = searchInvoiceVO.getBoxInId().split(",");
					for(int i = 0; i < boxId.length; i++){
						invoiceDetailDao.UpdateProposal(Integer.parseInt(boxId[i]),
								Integer.parseInt((searchInvoiceVO.getPastAccountCodeId() == null ? "-1" : searchInvoiceVO.getPastAccountCodeId())),
								Integer.parseInt((searchInvoiceVO.getPastDisputeCategoryId() == null ? "-1" : searchInvoiceVO.getPastDisputeCategoryId())),
								Integer.parseInt(searchInvoiceVO.getNowAccountCodeId()),
								Integer.parseInt(searchInvoiceVO.getNowDisputeCategoryId()),
								action,SystemUtil.getCurrentUserId(),
								(searchInvoiceVO.getNote() == null ? "" : searchInvoiceVO.getNote()));
					}
				}
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * @author dongjian
	 * Download the Outstanding Credit Excel
	 */
	public String getOutstandingCreditPageExcel(SearchInvoiceVO searchInvoiceVO,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get OutstandingCredit Excel", searchInvoiceVO));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getOutstandingCreditPageTotalNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.saveOutstandingCreditPageExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.saveOutstandingCreditPageExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 60, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25});
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

	/**
	 * @author dongjian
	 * get the Outstanding Dispute Excel
	 */
	public String getNotReconcileDisputePageExcel(SearchInvoiceVO searchInvoiceVO,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get ReconcileDispute Excel", searchInvoiceVO));

		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getNotReconcileDisputePageTotalNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getNotReconcileDisputePageExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getNotReconcileDisputePageExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 60, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25});
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

	/**
	 * @author dongjian
	 * 
	 * get the Reconciliation Excel
	 * */
	public String getTheInvoiceReconciliationPageExcel(SearchInvoiceVO searchInvoiceVO,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get the Reconciliation Excel", searchInvoiceVO));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getTheInvoiceReconciliationPageTotalNo(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = invoiceDetailDao.getTheInvoiceReconciliationPageExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getTheInvoiceReconciliationPageExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
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
	

	/**
	 * @author dongjian
	 * 
	 * get the CloseAsDisputeLose Excel
	 * */
	public String saveCloseAsDisputeLosePageExcel(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get the Reconciliation Excel", svo));

		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getCloseAsDisputeLosePageTotalNo(svo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				svo.setPageNo(1);
				svo.setRecPerPage((int) count);
				list = invoiceDetailDao.getCloseAsDisputeLosePageExcel(svo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					svo.setPageNo(j + 1);
					svo.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getCloseAsDisputeLosePageExcel(svo);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
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
	
	/**
	 * @author dongjian
	 * 
	 * get the CloseAsDisputeLose Excel
	 * */
	public String saveCloseAsDisputeWinPageExcel(SearchInvoiceVO svo,
			String excelDirPath, List<String> titles, String invoiceNumber) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get the Reconciliation Excel", svo));

		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDetailDao.getCloseAsDisputeWinPageTotalNo(svo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				svo.setPageNo(1);
				svo.setRecPerPage((int) count);
				list = invoiceDetailDao.getCloseAsDisputeWinPageExcel(svo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					svo.setPageNo(j + 1);
					svo.setRecPerPage(recPerPage);
					list = invoiceDetailDao.getCloseAsDisputeWinPageExcel(svo);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
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
	
	public void updateHistoryFiles(AttachmentPoint point, Invoice resultInvoice) {
		logger.info(CcmFormat.formatEnterLog("Update the History AttachmentPoint", point,resultInvoice));
		if(point != null)invoiceDao.updateHistoryFiles(point,resultInvoice);
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author dongjian
	 * Update the SCOA dispute one
	 * */
	public void addOutstandingDisputeSCOA(String inProposalIds, String scoa , String note) {
		logger.info(CcmFormat.formatEnterLog("add OutstandingDispute SCOA", inProposalIds,scoa));
		try {
			String notes = DaoUtil.ccmEscape2(note);
			invoiceDetailDao.addOutstandingDisputeSCOA(inProposalIds,scoa,notes);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * @author dongjian
	 * Update the SCOA dispute more
	 * */
	public void addOutstandingDisputeSCOAAll(String inProposalIds, String scoa , String note) {
		logger.info(CcmFormat.formatEnterLog("add manay OutstandingDispute SCOA", inProposalIds,scoa));
		try {
			String notes = DaoUtil.ccmEscape2(note);
			String[] proposalId = inProposalIds.split(",");
			for (String pId : proposalId) {
				invoiceDetailDao.addOutstandingDisputeSCOAAll(pId,scoa,notes);
			}
			
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	

	public IInvoiceDetailDao getInvoiceDetailDao() {
		return invoiceDetailDao;
	}

	public void setInvoiceDetailDao(IInvoiceDetailDao invoiceDetailDao) {
		this.invoiceDetailDao = invoiceDetailDao;
	}

	public IIvoiceNoteDao getInvoiceNoteDao() {
		return invoiceNoteDao;
	}


	public void setInvoiceNoteDao(IIvoiceNoteDao invoiceNoteDao) {
		this.invoiceNoteDao = invoiceNoteDao;
	}


	public void updateRecodeTaxInvoice(String invoiceId) {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			invoiceDetailDao.recodeTaxInvoice(invoiceId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * Update invoice notes
	 * Add by Xin Huang on 2011-08-31
	 */
	public void updateInvoiceNotes(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update invoice notes"));
		try {
			invoiceDetailDao.updateInvoiceNotes(svo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Add by Xin Huang on 2011-09-29
	 */
	public Long getInactiveSCOACount(Integer invoiceId) {
		logger.info(CcmFormat.formatEnterLog("getInactiveSCOACount"));
		Long result;
		try {
			SearchInvoiceVO sio = new SearchInvoiceVO();
			sio.setInvoiceId(invoiceId.toString());
			result = invoiceDetailDao.getNumberOfEmptySCOAs(sio);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return result;
	}
	
	/**
	 * Add by Xin Huang on 2013-01-29
	 */
	public boolean checkWorkflowPrivilege(Integer invoiceId) {
		logger.info(CcmFormat.formatEnterLog("checkWorkflowPrivilege"));
	
		try {
			Map<String, Object> result = invoiceDetailDao.checkApprovePrivilege(invoiceId);
			String returnCode = (String)result.get("returnCode");
			if ("SUCCESS".equals(returnCode)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public boolean checkWorkflowPrivilegeAndShowMessage(Integer invoiceId) {
		logger.info(CcmFormat.formatEnterLog("checkWorkflowPrivilegeAndShowMessage"));
		try {
			Map<String, Object> result = invoiceDetailDao.checkApprovePrivilege(invoiceId);
			
			String returnCode = (String)result.get("returnCode");
			String messageIds = (String)result.get("messageIds");
//			Integer nextStatusId = (Integer)result.get("nextStatusId");
			
			if ("SUCCESS".equals(returnCode)) {
				return true;
			} else {
				String message = invoiceDetailDao.showMessages(messageIds);
				RuntimeException bpe = new RuntimeException(message);
				throw bpe;
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public List<Map<String, String>> getToleranceRateList(String invoiceId) {
		// TODO Auto-generated method stub
		List<Map<String,String>> toleranceRateList = new ArrayList<Map<String,String>>();
		Map <String,String> toleranceRateMap = sysConfigDAO.findSysConfig();
		Boolean isBillKeepBan = invoiceDetailDao.isBillKeepBan(invoiceId);
		Boolean isCabsCDBan = invoiceDetailDao.isCabsCDBan(invoiceId);
		Map<String,String> sysConfigMap = new HashMap<String, String>();
		
		Map<String, Object> auditStatus = this.searchInvoiceAuditStatus(invoiceId);
		
		if(isBillKeepBan){
			sysConfigMap.put("key","Usage Tolerance Rate");
			sysConfigMap.put("value",formattedDecimalToPercentage(toleranceRateMap.get("audit_bill_keep_amount")));
		} else if(isCabsCDBan) {
			sysConfigMap.put("key","Usage Tolerance Rate");
			sysConfigMap.put("value",formattedDecimalToPercentage(toleranceRateMap.get("audit_tolerance_rate_usage_cabs")));
		} else if((Integer)auditStatus.get("usage_audit_status_id") > 0){
			sysConfigMap.put("key","Usage Tolerance Rate");
			sysConfigMap.put("value",formattedDecimalToPercentage(toleranceRateMap.get("audit_tolerance_rate_usage_report")));
		}
		
		toleranceRateList.add(sysConfigMap);
		return toleranceRateList;
	}
	public List<HashMap<String, String>> searchBanExemptionDate(String invoiceId)throws BPLException{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		List<Object[]> l = null;
		try {
			l = invoiceDetailDao.searchBanExemptionDate(invoiceId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(int i=0 ; i<l.size();i++){
			Object[] o = l.get(i);
			HashMap<String,String> m = new HashMap<String,String>();
			m.put("name", o[0].toString());
			m.put("term_start_date", o[1].toString());
			m.put("term_end_date", o[2].toString());
			list.add(m);
		}
		logger.info(CcmFormat.formatExitLog());
		return list;
		
	}
	 @SuppressWarnings("unused")
	private static String formattedDecimalToPercentage(String strDecimal)  
	    {  
		 	Double decimal = Double.parseDouble(strDecimal);	
	        //获取格式化对象  
	        NumberFormat nt = NumberFormat.getPercentInstance();  
	        //设置百分数精确度2即保留两位小数  
	        nt.setMinimumFractionDigits(0);  
	        return nt.format(decimal);  
	    }

	private String resultTipInfo(String passCount, String failedCount, String cannotValidateCount){
		
		StringBuffer resultNotes = new StringBuffer();
		resultNotes.append("Passed: ");
		resultNotes.append(passCount);
		if(Integer.parseInt(passCount) > 1){
			resultNotes.append(" items,");
		} else {
			resultNotes.append(" item,");
		}
		resultNotes.append(" Failed: ");
		resultNotes.append(failedCount);
		if(Integer.parseInt(failedCount) > 1){
			resultNotes.append(" items,");
		} else {
			resultNotes.append(" item,");
		}
		resultNotes.append(" Cannot Validate: ");
		resultNotes.append(cannotValidateCount);
		if(Integer.parseInt(cannotValidateCount) > 1){
			resultNotes.append(" items.");
		} else {
			resultNotes.append(" item.");
		}
		resultNotes.append(" For more information, see the invoice item tab below.");
		
		return resultNotes.toString();
	}
	public String getItemTypeByProposalId(String proposalId){
		return invoiceDetailDao.getItemTypeByProposalId(proposalId);
	}
	
	public Map<String, Object> searchInvoiceAttachment(String invoiceId){
		Invoice invoice = invoiceDetailDao.findInvoiceById(Integer.parseInt(invoiceId));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("invoice", invoice);
		map.put("originalList", null);
//		map.put("originalList", invoiceDetailDao.searchOriginalByInvoiceId(invoice));
		map.put("emailFrom", sysConfigDAO.getSystemEmailConfig().getSystemEmailUserName());
		Object[] obj = invoiceDetailDao.findbudgetOwnerEmail(Integer.parseInt(invoiceId));
		map.put("emailTo",obj[1].toString());
		map.put("workflowUserName",obj[0].toString());
		return map;
	}
	
	public List<MapEntryVO<String, String>> getSearchProductList(String proposalId)  throws BPLException{
		
		logger.info("Enter method getSearchProductList.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List productList = null;
		try {
			productList = invoiceDetailDao.getSearchProductList(proposalId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : productList){
			Object[] ban = (Object[])b; 
			String key = ((Integer)ban[0]).toString();
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(key, (String)ban[1]);
			l.add(m);
		}
		logger.info("Exit method getSearchProductList.");
		return l;
		
	}
	public List<MapEntryVO<String, String>> getSearchProductComponentList()  throws BPLException{
		logger.info("Enter method getSearchProductComponentList.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List productComponentList = null;
		try {
			productComponentList = invoiceDetailDao.getSearchProductComponentList();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : productComponentList){
			Object[] ban = (Object[])b; 
			String key = ((Integer)ban[0]).toString();
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(key, (String)ban[1]);
			l.add(m);
		}
		logger.info("Exit method getSearchProductComponentList.");
		return l;
	}
	public List<MapEntryVO<String, String>> getSearchProvinceList()  throws BPLException{
		logger.info("Enter method getSearchProvinceList.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List banList = null;
		try {
			banList = invoiceDetailDao.getSearchProvinceList();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : banList){
			Object[] ban = (Object[])b; 
			String key = ((Integer)ban[0]).toString();
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(key, (String)ban[1]);
			l.add(m);
		}
		logger.info("Exit method getSearchProvinceList.");
		return l;
	}
	
	public String createSOCADisputeToExcel(String invoiceId,String excelDirPath) throws BPLException {
		List<Object[]> itemDisplayList = null;
		Invoice invoiveModel = this.getInvoiceByInvoiceId(invoiceId);
		SearchInvoiceVO sio = new SearchInvoiceVO();
		sio.setAccordionName("Browse Invoice");
		sio.setInvoiceId(invoiceId);
		// Invoice Number.
		String invoiceNumber = invoiveModel.getInvoiceNumber();
		String sheetName = "SCOA and Dispute";
		
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int pageNumber = 0;

			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(pageNumber,sheetName);
			
			List<String> titles = new ArrayList<String>();
			titles.add("Proposal Id");
			titles.add("Item");
			titles.add("Payment");
			titles.add("Dispute");
			titles.add("Credit");
			titles.add("PON");
		//	titles.add("Province Source");
			titles.add("Procinve");
			titles.add("Circuit number");
			titles.add("Stripped Circuit Number");
			titles.add("Product");
			titles.add("Component");
			titles.add("Billing number");
			titles.add("SCOA");
			titles.add("Dispute category");
			titles.add("Notes");
			titles.add("Description");
			titles.add("Address");
			titles.add("Service Type");
			titles.add("Charge Type");
			titles.add("USOC");
			titles.add("USOC Description");
			titles.add("Eo Direct Quantity");
			titles.add("Quantity");
			titles.add("Rate");
			titles.add("Minutes");
			titles.add("Number of Calls");
			titles.add("Cellular");
			titles.add("Country");
			titles.add("Mileage");
			titles.add("ASG");
			titles.add("Jurisdiction");
			titles.add("direction");
			titles.add("From Date");
			titles.add("End Date");
			jExcelUtil.writeTitle(0, titles);
			//jExcelUtil.writeTitle(0, createList(title));

			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				// 主查询方法 （下载文件中的列表信息）。
				// 这个是查找 allTab 下的明细信息。
				list = invoiceDetailDao.getSOCADisputeListOfObject(sio);
				// 向 excel 的表格中写入数据，
				// 一行一行的写入。
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for(int j = 0 ; j < totlePage; j++){
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{30,30,30,30,40,40,30,30,30,30,50,30,30,30,30,30,30,30,30,30,30,30,30,30});
							jExcelUtil.createWritableSheet(pageNumber, sheetName);
							jExcelUtil.setWritableSheet(pageNumber);
						}
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + i) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), list.get(i)); 
						}
					}else{
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1, list.get(i)); 
						}
					}
				}
			}

			jExcelUtil.setColumnView(new int[]{30,30,30,30,40,40,30,30,30,30,50,30,30,30,30,30,30,30,30,30,30,30,30,30,
					30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,
					30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30,30});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}

	public String searchIsUasgeBan(String invoiceId){
		long isUasge = invoiceDetailDao.searchIsUasgeBan(invoiceId);
		return isUasge > 0 ? "Y" : "N";
	}
	
	
	public String runReport(String excelDirPath,int invoiceId,int term) throws BPLException{
		List<Object[]> summaryList = null;
		List<Object[]> detailList = null;
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			summaryList = invoiceDetailDao.runReportSummary(invoiceId,term);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "CABS_C_D_Summary");
			List<String> summaryTitles = new ArrayList<String>();
			summaryTitles.add("Vendor Name");
			summaryTitles.add("Audit Method");
			summaryTitles.add("Traffic Type");
			summaryTitles.add("Invoice Month");
			summaryTitles.add("Sum of Actual");
			summaryTitles.add("Sum of Expect");
			jExcelUtil.writeTitle(0, summaryTitles);
			for (int i = 0; i < summaryList.size(); i++) {
				jExcelUtil.writeLine(i + 1, summaryList.get(i));
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30});
			
			detailList = invoiceDetailDao.runReportDetail(invoiceId,term);
			jExcelUtil.createWritableSheet(1, "CABS_C_D_Detail");
			jExcelUtil.setWritableSheet(1);
			List<String> detailTitles = new ArrayList<String>();
			detailTitles.add("Vendor Name");
			detailTitles.add("Account Number");
			detailTitles.add("Invoice Number");
			detailTitles.add("Audit Method");
			detailTitles.add("Audit Status");
			detailTitles.add("Invoice Date");
			detailTitles.add("Traffic Type");
			detailTitles.add("Audit Notes");
			detailTitles.add("Actual");
			detailTitles.add("Expect");
			detailTitles.add("Reference Type");
			jExcelUtil.writeTitle(0, detailTitles);
			for (int i = 0; i < detailList.size(); i++) {
				jExcelUtil.writeLine(i + 1, detailList.get(i));
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
			
			
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info(CcmFormat.formatExitLog());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Object[] invoice = invoiceDetailDao.queryInvoiceDate(invoiceId);
		Date endDate = (Date) invoice[0];
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(endDate);
		startDate.add(Calendar.MONTH, -(term-1));
		String vendorName = invoice[1].toString();
		excelDirPath = "CABS Validation Report - "+vendorName+" - "+sdf.format(startDate.getTime()) +" - "+ sdf.format(endDate)+".xls";
		return excelDirPath;
	}
	
	public String getSystemUrlAdress(){
		return sysConfigDAO.getSystemUrlAdress();
	}
	
	public ISendEmailService getSendEmailService() {
		return sendEmailService;
	}


	public void setSendEmailService(ISendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	}


	public IInvoiceService4BB getInvoiceService4BB() {
		return invoiceService4BB;
	}


	public void setInvoiceService4BB(IInvoiceService4BB invoiceService4BB) {
		this.invoiceService4BB = invoiceService4BB;
	}


	public SystemEmailConfig getSec() {
		return sec;
	}


	public void setSec(SystemEmailConfig sec) {
		this.sec = sec;
	}
	
	

}
