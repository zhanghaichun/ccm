/**
 * 
 */
package com.saninco.ccm.service.invoice;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.dao.IAccountCodelDao;
import com.saninco.ccm.dao.IAttachmentFileDao;
import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.IInvoiceDao;
import com.saninco.ccm.dao.IIvoiceNoteDao;
import com.saninco.ccm.dao.IPaymentDao;
import com.saninco.ccm.dao.IPaymentDetailDao;
import com.saninco.ccm.dao.IProposalDao;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceNotes;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.model.PaymentDetail;
import com.saninco.ccm.model.PaymentStatus;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.BlobUtil;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.ExcelFileUtil;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author xinyu.Liu
 * 
 */
public class InvoiceServiceImpl implements IInvoiceService {
	private final Logger logger = Logger.getLogger(this.getClass());

	private IInvoiceDao invoiceDao;
	private IIvoiceNoteDao invoiceNoteDao;
	private IPaymentDao paymentDao;
	private IBanDao banDao;
	private IUserDao userDao;
	private IPaymentDetailDao paymentDetailDao;
	private IAccountCodelDao accountCodelDao;
	private IProposalDao proposalDao;
	private IAttachmentFileDao attachmentFileDao;
	
	public IBanDao getBanDao() {
		return banDao;
	}

	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}

	public InvoiceServiceImpl() {
	}
	
	/**
	 * create Invoice To Excel
	 */
	public String createInvoiceToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath,List<String> titles) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("create Invoice To Excel", searchInvoiceVO,excelDirPath,titles));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDao.getNumberOfInvoices(searchInvoiceVO, SystemUtil.getCurrentUserId());
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"invoice0"); 
			jExcelUtil.writeTitle(0, titles);
			if(count <= recPerPage){
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int)count);
				list = invoiceDao.searchInvoiceByObject(searchInvoiceVO,SystemUtil.getCurrentUserId());
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for(int j = 0 ; j < totlePage; j++){
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = invoiceDao.searchInvoiceByObject(searchInvoiceVO,SystemUtil.getCurrentUserId());
					for(int i = 0; i < list.size(); i++){
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i)); 
					}
				}
			}
			jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
			//jExcelUtil.protect();
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
	 * Search invoices by SearchInvoiceVO.
	 * Return JSON string.
	 */
	public String searchInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDao.searchInvoice(searchInvoiceVO, SystemUtil.getCurrentUserId());
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
	 * @author Chao.Liu
	 * @param searchInvoiceVO
	 * @return
	 * @throws BPLException
	 */
	public String searchInvoiceByIName(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice By Name", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = invoiceDao.searchInvoiceByIName(searchInvoiceVO);
			if(l.size() == 0){
//				sb.append("{error:\"Invoice Not Found.\"}");
				sb.append("{error:\"Not valid invoice.\"}");
			}else{
				Object[] str = l.get(0);
				Integer isid = new Integer(str[1].toString());
				if(isid >= 40){
					sb.append("{error:\"Transaction Process Started, Can Not Reject Invoice.\"}");
				}else{
					if (str[0] instanceof Blob) {
						str[0] = this.getBlobContent((Blob) str[0]);
					}
					sb.append(str[0].toString());
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author mingyang.Li
	 * @param searchInvoiceVO
	 * @return
	 * @throws BPLException
	 */
	public String searchInvoiceByInvoiceNumber(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice By Name", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = invoiceDao.searchInvoiceByInvoiceNumber(searchInvoiceVO);
			if(l.size() == 0){
				sb.append("{error:\"Invoice Not Found.\"}");
			}else{
				Object[] str = l.get(0);
				String invoiceStatus = str[1].toString();
				if("9".equals(invoiceStatus) || "10".equals(invoiceStatus) ||
				   "11".equals(invoiceStatus) || "12".equals(invoiceStatus) ||
				   "13".equals(invoiceStatus) || "14".equals(invoiceStatus) ||
				   "15".equals(invoiceStatus)){
					sb.append(str[0].toString());
				}else{
					sb.append("{error:\"This invoice can't be modified!\"}");
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String searchInvoiceForPRJ(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice By Name", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = invoiceDao.findByInvoiceNumber(searchInvoiceVO);
			Object[] str = l.get(0);
			if("0".equals(str[0].toString())){
				sb.append("{error:\"Not valid invoice.\"}");
			}else{
				Integer invoiceStatus = new Integer(str[2].toString());
				Integer paymentStatus = new Integer(str[3].toString());
				if("Y".equals(str[4].toString()) && 41 == invoiceStatus && 22 == paymentStatus){
					Payment p = paymentDao.findById(Integer.parseInt(str[1].toString()));
					PaymentStatus ps = paymentDao.findPaymentStatusById(24);
					
					p.setPaymentStatus(ps);
					p.setStatusTimestamp(new Date());
					p.setModifiedTimestamp(new Date());
					p.setModifiedBy(SystemUtil.getCurrentUserId());
					paymentDao.merge(p);
					sb.append("{payment:true}");
				}else{
					sb.append("{error:\"Not valid invoice.\"}");
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	

	public String searchInvoiceForPRS(SearchInvoiceVO searchInvoiceVO)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice By Name", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = invoiceDao.findByInvoiceNumber(searchInvoiceVO);
			
			Object[] str = l.get(0);
//			if("0".equals(str[0].toString())){
//				sb.append("{error:\"Invoice Not Found.\"}");
//				return sb.toString();
//			}
			
			int invoiceId = new Integer(str[0].toString());
			int invoices = new Integer(str[6].toString());
			int payments = new Integer(str[7].toString());
			
			if(invoices != 1){
//				sb.append("{error:\"query invoice table return "+ invoices +" invoices of the same invoice number,should be only one.\"}");
				sb.append("{error:\"Not valid invoice!\"}");
			}else if(payments != 1){
//				sb.append("{error:\"query payment table return "+ payments +" invoices of the same invoice number,should be only one.\"}");
				sb.append("{error:\"Not valid invoice!\"}");
			}
			else{
				Integer paymentStatus = new Integer(str[3].toString());
				if(24 == paymentStatus){
					if(searchInvoiceVO.getSite() != null && !"".equals(str[5].toString()) 
					   && str[5].toString().equals(searchInvoiceVO.getSite())){
						sb.append("{equals:true}");
					}else{
						if(searchInvoiceVO.getSite() != null && !"".equals(searchInvoiceVO.getSite())){
							Invoice invoice = invoiceDao.findById(invoiceId);
							Ban ban = invoice.getBan();
							ban.setApSupplierSite(searchInvoiceVO.getSite());
							ban.setModifiedBy(SystemUtil.getCurrentUserId());
							ban.setModifiedTimestamp(new Date());
							banDao.merge(ban);
						}
						sb.append(updatePaymentStatusTo20(searchInvoiceVO));
					}
				}else{
					sb.append("{error:\"Not valid invoice.\"}");
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String updateSearchForScoa(SearchInvoiceVO searchInvoiceVO)
		throws BPLException {
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
//		try {
			l = invoiceDao.findByInvoiceNumber(searchInvoiceVO);
			Object[] str = l.get(0);
			Integer invoiceId = 0;
			Integer paymentId = 0;
			Integer invoices = 0;
			Integer payments = 0;
			Integer paymentStatus = 0;
			Integer oldAccountCodeId = 0;
//			if(str[0] != null && str[3] != null){
			invoiceId = new Integer(str[0].toString());
			invoices = new Integer(str[6].toString());
			payments = new Integer(str[7].toString());
			paymentStatus = new Integer(str[3].toString());
			paymentId = new Integer(str[1].toString());
//			}
			
			List<AccountCode> accountCodelist = accountCodelDao.findByName(searchInvoiceVO.getScoacode());
			List<PaymentDetail> paymentDetailList = paymentDetailDao.findByPaymentIdAndLine(paymentId.toString(),searchInvoiceVO.getLineOfBusiness());
			if(paymentDetailList.size() > 0){
				oldAccountCodeId = paymentDetailList.get(0).getAccountCode().getId();
			}
			if(str[0] == null){
//				sb.append("{error:\"Invoice Not Found.\"}");
				sb.append("{error:\"Not valid invoice.\"}");
			}else if(Integer.parseInt(searchInvoiceVO.getLineOfBusiness()) > 3){
				sb.append("{error:\"Not valid Line Number!\"}");
			}else if(invoices != 1){
//				sb.append("{error:\"query invoice table return "+ invoices +" invoices of the same invoice number,should be only one.\"}");
				sb.append("{error:\"Not valid invoice!\"}");
			}else if(payments != 1){
				sb.append("{error:\"query payment table return "+ payments +" payments of the same invoice number,should be only one.\"}");
			}else if(24 != paymentStatus){
				sb.append("{error:\"Not valid invoice.\"}");
			}else if (42 != searchInvoiceVO.getScoacode().length()){
				sb.append("{error:\"scoa code length must be 42.\"}");
			}else if (paymentDetailList.size() != 1 ){
//				sb.append("{error:\"query payment_detail table return "+ paymentDetailList.size() +" payment_details of the same invoice number,should be only one.\"}");
				sb.append("{error:\"Not valid invoice!\"}");
			}else if (accountCodelist.size() > 1){
//				sb.append("{error:\"query account_code table return "+ accountCodelist.size() +" invoices of the same invoice number,should be only one.\"}");
				sb.append("{error:\"Not valid invoice!\"}");
			}else if (accountCodelist.size() == 1 && "N".equals(accountCodelist.get(0).getRecActiveFlag()) && "N".equals(accountCodelist.get(0).getActiveFlag())){
				sb.append("{error:\"This  SCOA is inactive !\"}");
			}else if (accountCodelist.size() == 1 && accountCodelist.get(0).getAccountCodeName().equals(paymentDetailList.get(0).getAccountCode().getAccountCodeName()) ){
				sb.append("{equals:true}");
			}else if (accountCodelist.size() == 0 && !searchInvoiceVO.isInsertConfirmed()){
				sb.append("{insert:true}");
			}else if ((accountCodelist.size() == 0 && searchInvoiceVO.isInsertConfirmed())|| accountCodelist.size() == 1){
//				AccountCode newac = new AccountCode();
				AccountCode ac = new AccountCode();
				if(accountCodelist.size() == 0){
					ac.setCreatedBy(SystemUtil.getCurrentUserId());
					ac.setCreatedTimestamp(new Date());
					ac.setModifiedBy(SystemUtil.getCurrentUserId());
					ac.setModifiedTimestamp(new Date());
					ac.setAccountCodeName(searchInvoiceVO.getScoacode());
					ac.setRecActiveFlag("Y");
					ac.setActiveFlag("Y");
					accountCodelDao.save(ac);
				}else{
					ac = accountCodelist.get(0);
				}
				
				PaymentDetail p = paymentDetailList.get(0);
				p.setAccountCode(ac);
				p.setModifiedBy(SystemUtil.getCurrentUserId());
				paymentDetailDao.merge(p);
				updatePaymentStatusTo20(searchInvoiceVO);
				
				Double paymentDetailAmount = paymentDetailList.get(0).getAmount();
				Double proposalAmount = proposalDao.getAountByInvoiceAndAccount(invoiceId,oldAccountCodeId);
				
				if(paymentDetailAmount.doubleValue() == proposalAmount.doubleValue()){
					proposalDao.updateAccountByInvoiceAndAccount(invoiceId,oldAccountCodeId,ac.getId());
					sb.append("{up:\"update proposal account_code_id Success!\"}");
				}else{
//					sb.append("{up:\"update proposal account_code_id Failed!\"}");
					RuntimeException bpe = new RuntimeException("update proposal account_code_id Failed!");
					throw bpe;
					
				}

			}
//		} catch (RuntimeException e) {
//			logger.error(CcmFormat.formatErrorLog(e));
//			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
//			throw bpe;
//		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public String pRSConfirmed(SearchInvoiceVO searchInvoiceVO)
			throws BPLException {		
		return updatePaymentStatusTo20(searchInvoiceVO);
	}
	
	public String updatePaymentStatusTo20(SearchInvoiceVO searchInvoiceVO){
		List<Object[]> l = null;
		l = invoiceDao.findByInvoiceNumber(searchInvoiceVO);
		Object[] str = l.get(0);
		int paymentid = new Integer(str[1].toString());
		Payment payment = paymentDao.findById(paymentid);
		if(22 == payment.getPaymentStatus().getId() || 24 == payment.getPaymentStatus().getId()){
			PaymentStatus ps = paymentDao.findPaymentStatusById(20);
			payment.setPaymentStatus(ps);
			payment.setStatusTimestamp(new Date());
			payment.setFlagWorkspace("Y");
			payment.setPaymentDate(new Date());
			payment.setModifiedTimestamp(new Date());
			payment.setModifiedBy(SystemUtil.getCurrentUserId());
			
			paymentDao.merge(payment);
			return "{payment:true}";
		}else{
			return "{error:\"Not valid invoice.\"}";
		}
		
	}

	/**
	 * Blob compiler
	 * */
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
	 * Get total page number and result number.
	 * Return JSON string.
	 */
	public String getInvoiceSearchTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Search Total Page No", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getNumberOfInvoices(searchInvoiceVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchInvoiceVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * InvoiceSearch pagination method
	 * */
	public String getInvoiceAssignmentSearchTotalPageNo(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Assignment Search Total Page No", svo));
		StringBuffer sb = new StringBuffer();
		long totalResult = 0;
		try {
			totalResult = invoiceDao.getAssignmentCount(svo.getUserId());
			sb.append(svo.getTotalPageNoJson(totalResult));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString(); 
	}
	
	/**
	 * InvoiceSearch query method, InvoiceDao calls
	 * */
	public String searchInvoiceAssignment(SearchInvoiceVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice Assignment View List", svo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDao.searchInvoiceAssignment(svo);
			sb.append(svo.getListJson(list));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString(); 
	}
	
	/**
	 * @author pengfei.wang
	 * Will the quantity statistical basis
	 * */
	public String getInvoiceWorkCount(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Work Count total No", wVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getInvoiceWorkCount(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) wVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/*
	 * External Approve Invoice Page No
	 */
	public String countExternalApproveInvoicePageNo(WorkspaceVO workspaceVO) throws BPLException {
		
		logger.info("Enter method countExternalApproveInvoicePageNo");
		StringBuffer sb = new StringBuffer();
		
		long count = 0;
		try {
			count = invoiceDao.getWorkspaceExternalApproveInvoiceCount(workspaceVO);
			
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		sb.append( workspaceVO.getTotalPageNoJson(count) );
		
		logger.info("Exit method countExternalApproveInvoicePageNo");
		return sb.toString();
	}
	
	/*
	 * External Approve Invoice list items
	 */
	public String listExternalApproveInvoiceItems(WorkspaceVO workspaceVO) throws BPLException {
		
		logger.info("Enter method listExternalApproveInvoiceItems");

		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		
		try {
			
			list = invoiceDao.listExternalApproveInvoiceItems(workspaceVO);
			
			sb.append( workspaceVO.getListJsonCompatible(list) );
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
			
		}
		
		
		

		logger.info("Exit method listExternalApproveInvoiceItems");
		
		return sb.toString();
	}
	
	/*
	 * service: 将从数据库中返回的 external approve 账单数据
	 * 生成一张 excel 工作表。
	 * 
	 */
	public String downloadExternalApproveInvoices(WorkspaceVO wvo, String excelDirPath, List<String> titles) throws BPLException {
		
		logger.info("Enter method downloadExternalApproveInvoices");
		
		try {
			
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDao.getWorkspaceExternalApproveInvoiceCount(wvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "ExternalApproveInvoices");
			jExcelUtil.writeTitle(0, titles);
			
			if (count <= recPerPage) {
				wvo.setPageNo(1);
				wvo.setRecPerPage((int) count);
				list = invoiceDao.getExternalApproveInvoiceData(wvo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					wvo.setPageNo(j + 1);
					wvo.setRecPerPage(recPerPage);
					list = invoiceDao.getExternalApproveInvoiceData(wvo);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			
			jExcelUtil.setColumnView(new int[] { 30,30,30,30,30,30,30,30,30,30,30,30,30, 30,30,30,30,30,30, 30 });
			jExcelUtil.write();
			jExcelUtil.close();
			
		} catch (Throwable e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
			
		}
		
		logger.info("Exit method downloadExternalApproveInvoices");
		return excelDirPath;
	}
	
	/**
	 * search PastDuePageNoService
	 * from hongtao.pang
	 */
	public String getPastDueCount(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get PastDue Count total No", wVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getPastDueCount(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) wVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * The statistical basis will query
	 * */
	public String searchInvoiceWorkCount(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice Work Count View List", wVO));

		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDao.searchInvoiceWorkCount(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(wVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(wVO.getStartIndex() + size);
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
	 * Will be paged data cycle into closure data
	 * */
	public String getUnknownWorkCount(WorkspaceVO wvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Unknown Work Count total No", wvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getUnknownInvoicesCount(wvo);
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
	 * @author pengfei.wang
	 * Will be paged data cycle into closure data
	 * */
	public String getMissingWorkCount(WorkspaceVO wvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Missing Work Count total No", wvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getWorkspaseCount(wvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
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
	 * Will be paged data cycle into closure data
	 * */
	public String getProcessWorkCount(WorkspaceVO wvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Process Work Count total No", wvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			if(wvo.getDueDay() == -1){
				count = invoiceDao.getWorkspasePaymentInProcessCount(wvo);
			}else if(wvo.getDueDay() == -2){
				count = invoiceDao.getWorkspaseDisputesInProcess(wvo);
			}else if(wvo.getDueDay() == -3){
				count = invoiceDao.getWorkspasePaymentInException(wvo);
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
		int tp = (int) Math.ceil((double) count / (double) wvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	public String searchUnknownInvoices(WorkspaceVO wvo) throws BPLException {
		logger.info("Enter method searchUnknownInvoices.");
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDao.searchUnknownInvoices(wvo);
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
		logger.info("Exit method searchUnknownInvoices.");
		return sb.toString();
	}
	
	/**
	 * @author Jian.Dong
	 * 
	 * get MissingInvoices Excel
	 * */
	public String getMissingInvoicesExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDao.getWorkspaseCount(wvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "MissingInvoices");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				wvo.setPageNo(1);
				wvo.setRecPerPage((int) count);
				list = invoiceDao.getMissingInvoicesData(wvo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					wvo.setPageNo(j + 1);
					wvo.setRecPerPage(recPerPage);
					list = invoiceDao.getMissingInvoicesData(wvo);
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
	 * @author Donghao.Guo
	 * 
	 * get PreloadInvoices Count
	 * */
	public String getPreloadInvoiceWorkCount(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Preload Invoice Work Count total No", wVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getPreloadInvoiceWorkCount(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) wVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author mingyang.li
	 * 
	 * get Invoice Reject Count
	 * */
	public String getInvoiceRejectWorkCount(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Reject Work Count total No", wVO));
			
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getInvoiceRejectWorkCount(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) wVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
		
	}
	
	/**
	 * @author Donghao.Guo
	 * 
	 * search PreloadInvoices Count
	 * */
	public String searchPreloadInvoiceWorkCount(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Preload Invoice Work Count View List", wVO));

		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDao.searchWorkspasePreloadInvoice(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(wVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(wVO.getStartIndex() + size);
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
	 * @author mingyang.li
	 * 
	 * search Invoice Reject Count
	 * */
	public String searchInvoiceRejectWorkCount(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice Reject Work Count View List", wVO));
		
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDao.searchInvoiceRejectWorkCount(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(wVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(wVO.getStartIndex() + size);
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
	 * @hongtao.pang
	 * SearchPastDue query
	 */
	
	public String searchPastDue(WorkspaceVO wVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search PastDue", wVO));
		
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = invoiceDao.searchPastDue(wVO);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(wVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(wVO.getStartIndex() + size);
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
	 * @author Donghao.Guo
	 * 
	 * get PreloadInvoices Excel
	 * */
	public String getPreloadInvoicesExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDao.getPreloadInvoiceWorkCount(wvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "PreloadInvoices");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				wvo.setPageNo(1);
				wvo.setRecPerPage((int) count);
				list = invoiceDao.getPreloadInvoiceData(wvo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					wvo.setPageNo(j + 1);
					wvo.setRecPerPage(recPerPage);
					list = invoiceDao.getPreloadInvoiceData(wvo);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30,30,30,30,30,30,30,30,30,30,30,30,30});
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
	//Add by wenbo.zhang 2015-3-18
	public String getDueDaysInvoicesExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDao.getInvoiceWorkCount(wvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "PreloadInvoices");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				wvo.setPageNo(1);
				wvo.setRecPerPage((int) count);
				list = invoiceDao.getDueDaysInvoiceData(wvo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					wvo.setPageNo(j + 1);
					wvo.setRecPerPage(recPerPage);
					list = invoiceDao.getDueDaysInvoiceData(wvo);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30,30,30,30,30,30,30,30,30,30,30,30,30});
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
	public String removeMissingInvoice(Invoice invoice,String missingEmailflag) throws BPLException {
		logger.info("Enter method removeMissingInvoice.");
		String result = "";
		try {
			result = invoiceDao.removeMissingInvoiceByInvoiceId(invoice,missingEmailflag);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:true}";
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;// TODO: handle exception
		}
		logger.info("Exit method removeMissingInvoice.");
		return result;
	}

	public String removeUnknownInvoice(Invoice invoice) throws BPLException {
		logger.info("Enter method removeUnknownInvoice.");
		String result = "";
		try {
			result = invoiceDao.removeUnknownInvoiceByInvoiceId(invoice);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:true}";
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method removeUnknownInvoice.");
		return result;
	}

	public String getUnknownInvoicesExcel(WorkspaceVO wvo, String excelDirPath,
			List<String> titles,List<Integer> ids) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = invoiceDao.getUnknownInvoicesCount(wvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "UnknownInvoices");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				wvo.setPageNo(1);
				wvo.setRecPerPage((int) count);
				list = invoiceDao.getUnknownInvoicesData(wvo, ids);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					wvo.setPageNo(j + 1);
					wvo.setRecPerPage(recPerPage);
					list = invoiceDao.getUnknownInvoicesData(wvo, ids);
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

	public String getInvoiceByNumberSearchTotalPageNo(
			SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Invoice Search Total Page No", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceDao.getNumberOfInvoicesByNumber(searchInvoiceVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchInvoiceVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public String searchInvoiceByINumber(SearchInvoiceVO searchInvoiceVO)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice View List", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDao.searchInvoiceByINumber(searchInvoiceVO, SystemUtil.getCurrentUserId());
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

	public String closeInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info("Enter method closeInvoice.");
		String result = "";
		try {
			invoiceDao.closeInvoice(searchInvoiceVO);
			result = "{'info':'Close Success!'}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{'info':'Close Error!'}";
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method closeInvoice.");
		return result;
	}
	
	public String cancelInvoice(SearchInvoiceVO searchInvoiceVO)
	throws BPLException {
		logger.info("Enter method cancelInvoice.");
		String result = "";
		try {
			invoiceDao.cancelInvoice(searchInvoiceVO);
			result = "{'info':'cancel Success!'}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{'info':'cancel Error!'}";
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method cancelInvoice.");
		return result;
	}

	
	public String removeInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info("Enter method removeInvoice.");
		String result = "";
		try {
			invoiceDao.removeInvoice(searchInvoiceVO);
			result = "{'info':'Remove Success!'}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{'info':'Remove Error!'}";
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method removeInvoice.");
		return result;
	}

	public String updateInvoiceOrfindInvoiceStatus(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findInvoiceStatus.");
		String result = "";
		result = invoiceDao.updateInvoiceOrGetStatusIdAndDisputeAmountByINumber(searchInvoiceVO);
		logger.info("Exit method findInvoiceStatus.");
		return result;
	}

	public String tRegularCloseInvoice(SearchInvoiceVO searchInvoiceVO)
			throws BPLException {
		logger.info("Enter method tRegularCloseInvoice.");
		String result = "",success = "{'info':'Close Success!'}",error = "{'info':'Close Error!'}";
		try {
			result = "Success".equals(invoiceDao.tRegularClosedInvoice(searchInvoiceVO)) ? success : error;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = error;
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method tRegularCloseInvoice.");
		return result;
	}

	public String throughAPPaymentCloseInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		logger.info("Enter method throughAPPaymentCloseInvoice.");
		String result = "",success = "{'info':'Close Success!'}",error = "{'info':'Close Error!'}";
		try {
			result = "Success".equals(invoiceDao.throughAPPaymentClosedInvoice(searchInvoiceVO)) ? success : error;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = error;
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method throughAPPaymentCloseInvoice.");
		return result;
	}
	
	public String searchInvoiceLabel(String labelIds) throws BPLException {
		logger.info("Enter method searchInvoiceLabel.");
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceDao.searchInvoiceLabelsByLabelId(labelIds);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			int size = l.size();
			sb.append("{data:[");
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
	 * update invoice by SearchInvoiceVO.
	 * Return JSON string.
	 * @throws SQLException 
	 */
	public String saveModifyPreviousAdjustment(SearchInvoiceVO searchInvoiceVO) throws BPLException, SQLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice View List", searchInvoiceVO));
		String message  = isInvoiceStatusIn9Or10(searchInvoiceVO);
//		int count = invoiceDao.searchInvoiceItemProposalFlagIs2(searchInvoiceVO);
		if(message.indexOf("data")>=0){
			// 1. 更新 invoice 的adjustment
			invoiceDao.modifyInvoicePreviousAdjustment(searchInvoiceVO);
			// 2. invoice_item表和proposal表插入 tax 和 adjustment 更新 invoice.invoice_tax
			Double taxTotal = invoiceDao.modifyInvoiceItemPreviousAdjustment(searchInvoiceVO);
			// 3.判断invoice_item是否存在proposal_flag = 2的 tax 和 adjustment 存在则update 不存在则insert into
			invoiceDao.modifyInvoiceItemandProposalProposalFlagIs2Record(searchInvoiceVO,taxTotal);
		}
		logger.info(CcmFormat.formatExitLog());
		return message;
	}
	
	/**
	 * update invoice by SearchInvoiceVO.
	 * Return JSON string.
	 * @throws SQLException 
	 */
	public String saveMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO) throws BPLException, SQLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice View List", searchInvoiceVO));
		String message  = isInvoiceStatusIn9Or10(searchInvoiceVO);
		if(message.indexOf("data")>=0){
			//1.更新 invoice表金额
			invoiceDao.modifyInvoiceMoveCurrentToAdjustment(searchInvoiceVO);
			//2.更新 invoice_item表金额与invoice_item表合计金额
			invoiceDao.modifyInvoiceItemMoveCurrentToAdjustment(searchInvoiceVO);
			//2.更新 proposal表金额与proposal表合计金额
			invoiceDao.modifyProposalMoveCurrentToAdjustment(searchInvoiceVO);
		}
		logger.info(CcmFormat.formatExitLog());
		return message;
	}
	
	private String isInvoiceStatusIn9Or10(SearchInvoiceVO searchInvoiceVO){
		List<Object[]> l = null;
		l = invoiceDao.searchInvoiceByInvoiceNumber(searchInvoiceVO);
		if(l.size() == 0){
			return "{error:\"Invoice Not Found.\"}";
		}else{
			Object[] str = l.get(0);
			String invoiceStatus = str[1].toString();
			if ("9".equals(invoiceStatus) || "10".equals(invoiceStatus)
					|| "11".equals(invoiceStatus) || "12".equals(invoiceStatus)
					|| "13".equals(invoiceStatus) || "14".equals(invoiceStatus)
					|| "15".equals(invoiceStatus)) {
				return "{data:\"Save Success!\"}";
			} else {
				return "{error:\"This invoice can't be modified!\"}";
			}
		}
	}
	
	public String downloadUsageReport(Integer invoiceId,String filePath){
		Invoice invoice = invoiceDao.findById(invoiceId);
		String accountNumber = invoice.getBan().getAccountNumber();
		String fileName = "";
		List<Object[]> list = null;
		JExcelUtil jExcelUtil;
		try {
			jExcelUtil = new JExcelUtil();
			if(accountNumber.substring(3,4).equals("C")){
				fileName = "Daily Network Report Telarix - "+invoice.getInvoiceNumber()+".xls";
				list = invoiceDao.queryCBanUsageReportList(invoiceId+"");
					jExcelUtil.createWritableWorkbook(filePath+fileName);
					jExcelUtil.createWritableSheet(0, "Report");
					jExcelUtil.writeTitle(0, getCBanColumnList());
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(i + 1, list.get(i));
					}
					jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40});
					jExcelUtil.write();
					jExcelUtil.close();
				
			}else if(accountNumber.substring(3,4).equals("D")){
				fileName = "Daily ASR Report Telarix - "+invoice.getInvoiceNumber()+".xls";
				
				List<Object[]> originatingList = invoiceDao.queryDBanUsageReportList(invoiceId+"","Originating");
				
				jExcelUtil.createWritableWorkbook(filePath+fileName);
				jExcelUtil.createWritableSheet(0, "Originating");
				jExcelUtil.writeTitle(0, getDBanColumnList("Originating"));
				for (int i = 0; i < originatingList.size(); i++) {
					jExcelUtil.writeLine(i + 1, originatingList.get(i));
				}
				jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40});
				
				List<Object[]> terminatingList = invoiceDao.queryDBanUsageReportList(invoiceId+"","Terminating");
				
				jExcelUtil.createWritableSheet(1, "Terminating");
				jExcelUtil.setWritableSheet(1);
				jExcelUtil.writeTitle(0, getDBanColumnList("Terminating"));
				for (int i = 0; i < terminatingList.size(); i++) {
					jExcelUtil.writeLine(i + 1, terminatingList.get(i));
				}
				jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40});
				
				jExcelUtil.write();
				jExcelUtil.close();
			}else if(accountNumber.equals("28321") || accountNumber.equals("68242527") || accountNumber.equals("000080000071") 
					|| accountNumber.equals("8191106257") || accountNumber.equals("418FD84802") || accountNumber.equals("M059")){
				int sheetNum = 0;
				list = invoiceDao.queryCBanUsageReportList(invoiceId+"");
				if(list.size()>0){
					fileName = "Daily Network Report Telarix - "+invoice.getInvoiceNumber()+".xls";
					jExcelUtil.createWritableWorkbook(filePath+fileName);
					jExcelUtil.createWritableSheet(0, "Report");
					jExcelUtil.writeTitle(0, getCBanColumnList());
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(i + 1, list.get(i));
					}
					jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40});
					sheetNum+=1;
				}
				
				List<Object[]> originatingList = invoiceDao.queryDBanUsageReportList(invoiceId+"","Originating");
				List<Object[]> terminatingList = invoiceDao.queryDBanUsageReportList(invoiceId+"","Terminating");
				if(originatingList.size()>0 || terminatingList.size()>0){
					if(fileName == null || "".equals(fileName)){
						fileName = "Daily ASR Report Telarix - "+invoice.getInvoiceNumber()+".xls";
						jExcelUtil.createWritableWorkbook(filePath+fileName);
					}
					jExcelUtil.createWritableSheet(sheetNum, "Originating");
					jExcelUtil.setWritableSheet(sheetNum);
					jExcelUtil.writeTitle(0, getDBanColumnList("Originating"));
					for (int i = 0; i < originatingList.size(); i++) {
						jExcelUtil.writeLine(i + 1, originatingList.get(i));
					}
					jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40});
					
					jExcelUtil.createWritableSheet(sheetNum+1, "Terminating");
					jExcelUtil.setWritableSheet(sheetNum+1);
					jExcelUtil.writeTitle(0, getDBanColumnList("Terminating"));
					for (int i = 0; i < terminatingList.size(); i++) {
						jExcelUtil.writeLine(i + 1, terminatingList.get(i));
					}
					jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40});
				}
				jExcelUtil.write();
				jExcelUtil.close();
				if(fileName == null || "".equals(fileName)){
					fileName = "Daily Network Report Telarix - "+invoice.getInvoiceNumber()+".xls";
					jExcelUtil.createWritableWorkbook(filePath+fileName);
					jExcelUtil.createWritableSheet(0, "Report");
					jExcelUtil.writeTitle(0, getCBanColumnList());
					jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40});
					jExcelUtil.write();
					jExcelUtil.close();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return fileName;
	}
	
	private List<String> getCBanColumnList(){
		List<String> columnList = new ArrayList<String>();
		columnList.add("Call Date");
		columnList.add("Trunk / CLLI");
		columnList.add("NONE");
		columnList.add("Hold Min.");
		columnList.add("Usage Min.");
		columnList.add("Count");
		columnList.add("UPC");
		return columnList;
	}
	
	private List<String> getDBanColumnList(String type){
		List<String> columnList = new ArrayList<String>();
		columnList.add("Call Date");
		if(type.equals("Originating")){
			columnList.add("InAccount");
			columnList.add("InTrunkName");
		}else{
			columnList.add("OutAccount");
			columnList.add("OutTrunkName");
		}
		columnList.add("Minutes");
		columnList.add("Average Duration");
		columnList.add("Seized");
		columnList.add("Answered");
		columnList.add("ASR");
		return columnList;
	}
	
	public void createUsageInvoiceNote(String filePath){
		List<Integer> list = invoiceDao.queryAllUsageInvoice();
		for(int i=0;i<list.size();i++){
			Integer invoiceId = list.get(i);
			long isSystemUpload = invoiceDao.querySystemUploadByInvoiceId(invoiceId);
			if(isSystemUpload <= 0){
				String fileName = downloadUsageReport(invoiceId,filePath);
				if(fileName!=null && !"".equals(fileName)){
					Invoice invoice = invoiceDao.findById(invoiceId);
					Integer apid = 0;
					
					AttachmentPoint ap = new AttachmentPoint();
					ap.setTableName("invoice_notes");
					ap.setTableIdValue(list.get(i));
					ap.setCreatedBy(0);
					ap.setCreatedTimestamp(new Date());
					ap.setModifiedBy(0);
					ap.setModifiedTimestamp(new Date());
					ap.setRecActiveFlag("Y");
					Integer newId = attachmentFileDao.createPoint(ap);
					apid = newId;
					
					AttachmentFile attachmentFile = new AttachmentFile();
					attachmentFile.setAttachmentPoint(attachmentFileDao.loadPoint(apid));
					attachmentFile.setFilePath(filePath+fileName);
					attachmentFile.setFileName(fileName);
					attachmentFile.setNotes(fileName);
					attachmentFile.setCreatedBy(0);
					attachmentFile.setCreatedTimestamp(new Date());
					attachmentFile.setModifiedBy(0);
					attachmentFile.setModifiedTimestamp(new Date());
					attachmentFile.setRecActiveFlag("Y");
					attachmentFile.setSystemUploadFlag("Y");
					attachmentFile.setTelarixReportFlag("Y");
					attachmentFileDao.save(attachmentFile);
					
					InvoiceNotes invoiceNote =new InvoiceNotes();
					invoiceNote.setCreatedTimestamp(new Date());
					invoiceNote.setInvoice(invoice);
					invoiceNote.setNotes(fileName);
					User user = userDao.findById(0);
					invoiceNote.setUser(user);
					invoiceNote.setPrivateFlag("N");
					invoiceNote.setCreatedBy(0);
					invoiceNote.setModifiedBy(0);
					invoiceNote.setModifiedTimestamp(new Date());
					invoiceNote.setRec_active_flag("Y");
					invoiceNote.setAttachment_point_id(apid);
					invoiceNoteDao.saveInvoiceNote(invoiceNote);
				}
			}
		}
	}
	
	public void deleteInvoiceNote(String attachmentFileId){
		AttachmentFile af = attachmentFileDao.findById(Integer.valueOf(attachmentFileId));
		Integer attachmentPointId = af.getAttachmentPoint().getId();
		attachmentFileDao.delete(af);
		List<AttachmentFile> afList = attachmentFileDao.findAllByAttchmentPiontIdPiontId(attachmentPointId);
		if(afList.size() <= 0 ){
			attachmentFileDao.deleteAttchmentPiont(attachmentPointId);
		}
	}
	
	public IPaymentDao getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(IPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	public IPaymentDetailDao getPaymentDetailDao() {
		return paymentDetailDao;
	}

	public void setPaymentDetailDao(IPaymentDetailDao paymentDetailDao) {
		this.paymentDetailDao = paymentDetailDao;
	}

	public IAccountCodelDao getAccountCodelDao() {
		return accountCodelDao;
	}

	public void setAccountCodelDao(IAccountCodelDao accountCodelDao) {
		this.accountCodelDao = accountCodelDao;
	}

	public IProposalDao getProposalDao() {
		return proposalDao;
	}

	public void setProposalDao(IProposalDao proposalDao) {
		this.proposalDao = proposalDao;
	}

	public IIvoiceNoteDao getInvoiceNoteDao() {
		return invoiceNoteDao;
	}

	public void setInvoiceNoteDao(IIvoiceNoteDao invoiceNoteDao) {
		this.invoiceNoteDao = invoiceNoteDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IAttachmentFileDao getAttachmentFileDao() {
		return attachmentFileDao;
	}

	public void setAttachmentFileDao(IAttachmentFileDao attachmentFileDao) {
		this.attachmentFileDao = attachmentFileDao;
	}


	

}
