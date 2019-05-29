/**
 * 
 */
package com.saninco.ccm.service.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.saninco.ccm.dao.IPaymentDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.util.BlobUtil;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewPaymentDetailVO;

/**
 * @author xinyu.Liu
 *
 */
public class PaymentDetailServiceImpl implements IPaymentDetailService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IPaymentDao paymentDao;
	
	public PaymentDetailServiceImpl() {
	} 
	
	/**
	 * Split Payment Action and Status List ### Get Status Map(action_id , status_id)
	 */
	public Map<String, String> getPaymentStatus(String paymentId)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get PaymentStatus List", paymentId));
		List<?> list = null;
		try {
			list = getPaymentActionAndStatus(paymentId);
		} catch (BPLException e) {
			e.printStackTrace();
		}
		Map<String, String> map = new HashMap<String, String>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object str = list.get(i);
				String str1 = str.toString();

				// {id:2,action:"Decline",status:"5"}
				String str2 = str1.replace("{id:", "");
				String str3 = str2.replace("action:\"", "");
				String str4 = str3.replace("\",status:\"", ",");
				String str5 = str4.replace("\"}", "");
				// 2,Decline,5
				String[] st = str5.split(",");
				// 2,5
				map.put(st[0], st[2]);
			}
		} else {
			logger.info(CcmFormat.formatExitLog());
			return null;
		}
		logger.info(CcmFormat.formatExitLog());
		return map;
	}
	
	/**
	 * Split Payment Action and Status List ### Get Action List(action_id ,action_name)
	 */
	public List<MapEntryVO<String, String>> getPaymentAction(String paymentId)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get PaymentAction List", paymentId));
		List<?> list = null;
		try {
			list = getPaymentActionAndStatus(paymentId);
		} catch (BPLException e) {
			e.printStackTrace();
		}
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object str = list.get(i);
				String str1 = str.toString();

				// {id:2,action:"AAA",status:"5"}
				String str2 = str1.replace("{id:", "");
				String str3 = str2.replace("action:\"", "");
				String str4 = str3.replace("\",status:\"", ",");
				String str5 = str4.replace("\"}", "");
				// 2,Decline,5
				String[] st = str5.split(",");
				// 2,Decline
				MapEntryVO<String, String> m = new MapEntryVO<String, String>(
						st[0], st[1]);
				l.add(m);
			}
		} else {
			logger.info(CcmFormat.formatExitLog());
			return new ArrayList<MapEntryVO<String, String>>();
		}
		logger.info(CcmFormat.formatExitLog());
		return l;
	}
	
	/**
	 * Get Payment Action and Status List
	 */
	public List<?> getPaymentActionAndStatus(String paymentId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get PaymentActionAndStatus List", paymentId));
		List<?> list = null;
		try {
			//{id:2,action:"AAA",status:"5"}
			list = paymentDao.getPaymentAction(paymentId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return list;
	}
	
	/**
	 * Payment Details Data List
	 */
	public String searchPaymentDetails(ViewPaymentDetailVO viewPaymentDetailVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Payment List", viewPaymentDetailVO));
		StringBuffer sb = new StringBuffer();
		List<?> list = null;
		try {
			list = paymentDao.searchPaymentDetails(viewPaymentDetailVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(viewPaymentDetailVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(viewPaymentDetailVO.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = list.get(i);
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
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * Get total page number and result number. ### Payment Detail 
	 * Return JSON string.
	 */
	public String getPaymentDetailsTotalPageNo(ViewPaymentDetailVO viewPaymentDetailVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get PaymentDetails Total Page No", viewPaymentDetailVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = paymentDao.getNumberOfPaymentDetails(viewPaymentDetailVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:2,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count
				/ (double) viewPaymentDetailVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * find Payment by paymentId
	 */
	public Payment findPayment(String paymentId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("find Payment Object", paymentId));
		if (paymentId != null) {
			int id = Integer.parseInt(paymentId);
			Payment payment = paymentDao.findById(id);
			logger.info(CcmFormat.formatExitLog());
			return payment;
		} else {
			logger.info(CcmFormat.formatExitLog());
			return null;
		}
	}

	public IPaymentDao getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(IPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}


}
