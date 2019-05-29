package com.saninco.ccm.service.credit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ICreditDetailDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchVO;

/**
 * It implementa ICreditDetailService It main designed to BusinessLogic On the
 * CreditDeatail Class
 * 
 * @author Chao.Liu
 * Modified Logger by Chao.liu 10/08/23 AM
 */
public class CreditDetailServiceImpl implements ICreditDetailService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ICreditDetailDao creditDetailDao;
	
	public CreditDetailServiceImpl() {
	}
	
	/**
	 * This is method designed to Get Credit Object data
	 * 
	 * @param id
	 * @return Credit Class
	 */
	public Credit getCredit(Integer id) {
		Credit c = creditDetailDao.getCredit(id);
		logger.info("Exit method getCredit.");
		return c;
	}

	/**
	 * This is method designed to Get Dispute_Table collection data
	 * 
	 * @param v
	 * @param cid
	 * @return Json String
	 * @throws BPLException
	 */
	public String getDisputeList(SearchVO v, Integer cid) throws BPLException {
		StringBuffer sb = new StringBuffer();
		List<String> list = null;

		try {
			list = creditDetailDao.getDisputeList(v, cid);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(v.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(v.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				if (i != 0)
					sb.append(",");
				sb.append(list.get(i).toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"No Records.\"");
		}
		sb.append("}");
		logger.info("Exit method getDisputeList.");
		return sb.toString();
	}

	/**
	 * Get total page number and result number. Return JSON string.
	 */
	public String getCreditSearchTotalPageNo(SearchVO v, Integer cid)
			throws BPLException {
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = creditDetailDao.getNumberOfCredit(v, cid);
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
		int tp = (int) Math.ceil((double) count / (double) v.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info("Exit method getCreditSearchTotalPageNo.");
		return sb.toString();
	}

	/**
	 * Search all Recocile data to database
	 * 
	 * @param v
	 * @param did
	 * @return Json String
	 * @throws BPLException
	 */
	public String getReconcileList(SearchVO v, Integer creditId)
			throws BPLException {

		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		Double totalAmount;
		try {
			totalAmount = creditDetailDao
					.getDisputeForReconcileTotalAmount(creditId);
			list = creditDetailDao.getReconcileList(v, creditId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		if (list != null && list.size() > 0) {
			sb.append("{totalAmount:");
			sb.append(totalAmount);
			sb.append(",data:[");
			for (int i = 0; i < list.size(); i++) {
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
			sb.append("{error:\"No Records.\"");
		}
		sb.append("}");

		logger.info("Exit method getReconcileList.");
		return sb.toString();
	}

	/**
	 * Clear one data to Reconcile_Table Return Json String
	 */
	public String cancelForReconcileByCredit(Integer creditId,
			Integer disputeId, Integer reconcileId, Double balance)
			throws BPLException {

		StringBuffer sb = new StringBuffer();
		try {
			Credit c = (Credit) creditDetailDao.get(Credit.class, creditId);
			c.setCreditBalance(c.getCreditBalance() + balance);
			creditDetailDao.update(c);

			sb.append("{creditBalance:" + c.getCreditBalance().toString()+ "}");

			Dispute d = (Dispute) creditDetailDao.get(Dispute.class, disputeId);
			d.setDisputeBalance(d.getDisputeBalance() + balance);
			if(d.getDisputeNumber() == null){
				d.setRecActiveFlag("N");
			}
			
			
			creditDetailDao.update(d);
			Reconcile r = (Reconcile) creditDetailDao.get(Reconcile.class,
					reconcileId);
			r.setRecActiveFlag("N");
			creditDetailDao.update(r);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method cancelForReconcileByCredit.");
		return sb.toString();
	}

	/**
	 * Add one data to Reconcile_Table Return Json String
	 */
	public String addOneReconcileData(String notes, Integer creditId,
			Integer disputeId, Integer reconcileId, Double amount)
			throws BPLException {

		StringBuffer sb = new StringBuffer();
		Credit c = null;
		Dispute d = null;
		Reconcile r = null;

		// Anytime must want to use Credit Object
		try {
			c = (Credit) creditDetailDao.get(Credit.class, creditId);
			c.setCreditBalance(c.getCreditBalance() + amount);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Judgment could it be Dispute Object
		if (disputeId != -1) {
			try {
				d = (Dispute) creditDetailDao.get(Dispute.class, disputeId);
				d.setDisputeBalance(d.getDisputeBalance() - amount);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(
						ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}

		} else {
			d = new Dispute();
			d.setInvoice(c.getInvoice());
			d.setDisputeBalance(amount);
			d.setCreatedBy(SystemUtil.getCurrentUserId());
			d.setModifiedBy(SystemUtil.getCurrentUserId());
			d.setCreatedTimestamp(new Date());
			d.setModifiedTimestamp(new Date());
			d.setRecActiveFlag("Y");
			d.setOutstandingReserveAmount(d.getReserveAmount());
		}

		d.setNotes(notes);

		// If Credit Balance less than 0,it return.
		if (c.getCreditBalance() < 0) {
			sb.append("{error:\"creditBalance not enough.\"}");
			return sb.toString();
		}
		// If Dispute Balance less than 0,it return.
		if (d != null && d.getDisputeBalance() < 0) {
			sb.append("{error:\"disputeBalance not enough.\"}");
			return sb.toString();
		}

		r = new Reconcile();
		r.setReconcileAmount(amount);
//		r.setCredit(c);
		r.setCreatedTimestamp(new Date());
		r.setModifiedTimestamp(new Date());
		r.setReconcileDate(new Date());
		r.setCreatedBy(SystemUtil.getCurrentUserId());
		r.setModifiedBy(SystemUtil.getCurrentUserId());
		r.setRecActiveFlag("Y");

		try {
			if (disputeId == -1) {
				int did = creditDetailDao.save(d);
				d = (Dispute) creditDetailDao.get(Dispute.class, did);
			} else {
				creditDetailDao.update(d);
			}

			r.setDispute(d);

			creditDetailDao.update(c);
			sb.append("{creditBalance:" + c.getCreditBalance().toString()
							+ "}");
			creditDetailDao.save(r);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info("Exit method cancelForReconcileByCredit.");
		return sb.toString();
	}

	/**
	 * This is method designed to handle SQLDB Blob question String
	 */
	private String getBlobContent(Blob b) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedInputStream bis = new BufferedInputStream(b
					.getBinaryStream());
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

	// Getter And Setter
	public ICreditDetailDao getCreditDetailDao() {
		return creditDetailDao;
	}

	public void setCreditDetailDao(ICreditDetailDao creditDetailDao) {
		this.creditDetailDao = creditDetailDao;
	}
}
