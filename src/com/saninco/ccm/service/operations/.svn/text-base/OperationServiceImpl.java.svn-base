/**
 * 
 */
package com.saninco.ccm.service.operations;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.IBarCodeDao;
import com.saninco.ccm.dao.IInvoiceStVDao;
import com.saninco.ccm.dao.IInvoiceStDao;
import com.saninco.ccm.dao.IVendorDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.BarCode;
import com.saninco.ccm.model.InvoiceSt;
import com.saninco.ccm.model.InvoiceStV;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.OperationsVO;


public class OperationServiceImpl implements IOperationService {
	private final Logger logger = Logger.getLogger(this.getClass());

	private IInvoiceStDao invoiceStDao;
	private IInvoiceStVDao invoiceStVDao;
	private IBanDao banDao;
	private IVendorDao vendorDao;
	private IBarCodeDao barCodeDao;
	
	/**
	 * Operation of the BarCode of trying to find a list of
	 */
	public String operationServiceFindBarCode(OperationsVO operationsVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("find operation BarCode View List", operationsVO));
		List<String> list = null;
		StringBuffer sb = new StringBuffer();
		try {
			list = barCodeDao.findBarCodeToString(operationsVO.getBarCode());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list != null && list.size() > 0 ){
			sb.append("{data:[");
			for(int i = 0; i<list.size(); i++){
				Object str = list.get(i);
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
	
	public String operationServiceByBarCode(OperationsVO operationsVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("find operation BarCode View List", operationsVO));
		List<InvoiceSt> list = null;
		StringBuffer sb = new StringBuffer();
		try {
			list = invoiceStDao.findByProperty("barCode", operationsVO.getBarCode());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list != null && list.size() > 0 ){
			sb.append("{data:[{");
			for(int i = 0; i < list.size(); i++){
				InvoiceSt st = list.get(i);
				if(i != 0) sb.append(",");
				sb.append("id:");
				sb.append(st.getId());
				sb.append(",vendor:\"");
				sb.append(st.getBan() == null ? "null" : st.getBan().getVendor().getVendorName().toString());
				sb.append("\",ban:\"");
				sb.append(st.getBan() == null ? "null" : st.getBan().getAccountNumber().toString());
				sb.append("\",invoiceDate:\"");
				sb.append(st.getInvoiceDate() == null ? "null" : st.getInvoiceDate().toString());
				sb.append("\",invoiceCurrentDue:\"");
				sb.append(st.getInvoiceCurrentDue() == null ? "null" : st.getInvoiceCurrentDue().toString());
				sb.append("\",invoiceTotalDue:\"");
				sb.append(st.getInvoiceTotalDue() == null ? "null" : st.getInvoiceTotalDue().toString());
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
	 * delete all InvoiceStCheck Data
	 */
	public void deleteInvoiceStVData(OperationsVO operationsVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("delete invoice_st_v and bar_code by bar_code", operationsVO));
		StringBuffer sb = new StringBuffer();
		try {
			invoiceStVDao.delete("invoice_st_v",operationsVO.getBarCode());
			invoiceStVDao.delete("bar_code",operationsVO.getBarCode());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * add InvoiceStCheck Data
	 */
	public void addInvoiceStVData(OperationsVO ovo) {
		logger.info(CcmFormat.formatEnterLog("add InvoiceStV Data", ovo));
		InvoiceStV invoiceStV = new InvoiceStV();
		invoiceStV.setBarCode(ovo.getBarCode());
		invoiceStV.setInvoiceDueDate(new Date(ovo.getInvoiceDueDate()));
		invoiceStV.setInvoicePreviousPayment(Double.valueOf(ovo.getInvoicePreviousPayment()));
		invoiceStV.setInvoiceBalanceForward(Double.valueOf(ovo.getInvoiceBalanceForward()));
		invoiceStV.setInvoiceCurrentDue(Double.valueOf(ovo.getInvoiceCurrentDue()));
		invoiceStV.setInvoiceTotalDue(Double.valueOf(ovo.getInvoiceTotalDue()));
		invoiceStV.setCreatedBy(SystemUtil.getCurrentUserId());
		invoiceStV.setCreatedTimestamp(new Date());
		invoiceStV.setModifiedBy(SystemUtil.getCurrentUserId());
		invoiceStV.setModifiedTimestamp(new Date());
		invoiceStV.setRecActiveFlag("Y");
		invoiceStVDao.save(invoiceStV);
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Add a BarCode data
	 */
	public void saveOperations(Integer vendorId, Integer banId, String barCode, Date invoiceDate) {
		logger.info(CcmFormat.formatEnterLog("add BarCode Data", vendorId,banId,barCode,invoiceDate));
		Date now = new Date();
		BarCode bc = new BarCode();
		bc.setBan(banDao.load(banId));
		bc.setBarCode(barCode);
		bc.setInvoiceDate(invoiceDate);
		bc.setCreatedBy(SystemUtil.getCurrentUserId());
		bc.setCreatedTimestamp(now);
		bc.setModifiedBy(SystemUtil.getCurrentUserId());
		bc.setModifiedTimestamp(now);
		bc.setRecActiveFlag("Y");
		invoiceStDao.save(bc);
		logger.info(CcmFormat.formatExitLog());
	}

	public IInvoiceStDao getInvoiceStDao() {
		return invoiceStDao;
	}

	public void setInvoiceStDao(IInvoiceStDao invoiceStDao) {
		this.invoiceStDao = invoiceStDao;
	}

	public IBanDao getBanDao() {
		return banDao;
	}

	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}

	public IVendorDao getVendorDao() {
		return vendorDao;
	}

	public void setVendorDao(IVendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	public IInvoiceStVDao getInvoiceStVDao() {
		return invoiceStVDao;
	}

	public void setInvoiceStVDao(IInvoiceStVDao invoiceStVDao) {
		this.invoiceStVDao = invoiceStVDao;
	}

	public IBarCodeDao getBarCodeDao() {
		return barCodeDao;
	}

	public void setBarCodeDao(IBarCodeDao barCodeDao) {
		this.barCodeDao = barCodeDao;
	}

}
