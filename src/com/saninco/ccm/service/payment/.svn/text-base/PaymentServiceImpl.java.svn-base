/**
 * 
 */
package com.saninco.ccm.service.payment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IPaymentDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.WorkflowAction;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchPaymentVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 * add getCreditAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 * add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 */
public class PaymentServiceImpl implements IPaymentService {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private IPaymentDao paymentDao;
	
	public PaymentServiceImpl() {
	}
	
	/**
	 * Search payments by SearchPaymentVO.
	 * Return JSON string.
	 */
	public String searchPayment(SearchPaymentVO searchPaymentVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search payments by SearchPaymentVO.",searchPaymentVO));
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = paymentDao.searchPayment(searchPaymentVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchPaymentVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchPaymentVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				if(i!=0) sb.append(",");
				sb.append(l.get(i).toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info("Exit method searchPayment.");
		return sb.toString();
	}
	
	/**
	 * @param paymentDao the paymentDao to set
	 */
	public void setPaymentDao(IPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}
	
	/**
	 * Get total page number and result number.
	 * Return JSON string.
	 */
	public String getPaymentSearchTotalPageNo(SearchPaymentVO searchPaymentVO)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.",searchPaymentVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = paymentDao.getNumberOfPayments(searchPaymentVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:2,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchPaymentVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info("Exit method getPaymentSearchTotalPageNo.");
		return sb.toString();
	}

	public String getPaymentAssignmentSearchTotalPageNo(SearchPaymentVO svo) throws BPLException {
		logger.info("Enter method getPaymentAssignmentSearchTotalPageNo.");
		long totalResult = 0;
		try {
			totalResult = paymentDao.getAssignmentCount(svo.getUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(totalResult);
		logger.info("Exit method getPaymentAssignmentSearchTotalPageNo.");
		return s;
	}
	/**
	 * @author Chao.Liu
	 * Search Payment By Payment Number
	 * @return
	 * @throws Exception
	 */
	public String searchPaymentByPName(SearchPaymentVO svo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Payment By Payment Number",svo));
		StringBuffer sb = new StringBuffer();
		try {
			List l = paymentDao.getPaymentByName(svo.getPaymentTransactionNumber());
			
			if(l.size() == 0){
				sb.append("{error:\"Transaction Not Found!\"}");
			}else {
				Object str = l.get(0);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				sb.append(str);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			sb.append("{error:\""+e.getMessage()+"!\"}");
			throw bpe;
		}
		logger.info("Exit method searchPaymentByPName.");
		return sb.toString();
	}
	/**
	 * @author Chao.Liu
	 * Edit Payment
	 * @return
	 * @throws Exception
	 */
	public String paymentPTConfirm(SearchPaymentVO svo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Edit Payment",svo));
		StringBuffer sb = new StringBuffer();
		try {
			Payment p = (Payment) paymentDao.get(Payment.class, svo.getPid());
			
			TransactionHistory th = new TransactionHistory();
			th.setInvoice(p.getInvoice());
			th.setPayment(p);
			th.setInvoiceStatus(p.getInvoice().getInvoiceStatus());//
			th.setInternalInvoiceStatus(p.getInvoice().getInternalInvoiceStatus());//
			th.setPaymentStatus(p.getPaymentStatus());
			th.setWorkflowAction(p.getWorkflowAction());
			th.setUser(p.getUserByWorkflowUserId());
			th.setNotes(svo.getNotes());
			th.setAttachmentPoint(p.getAttachmentPoint());
			
			th.setCreatedBy(SystemUtil.getCurrentUserId());
			th.setCreatedTimestamp(new Date());
			th.setRecActiveFlag("Y");
			
			paymentDao.save(th);

			WorkflowAction wa = (WorkflowAction) paymentDao.get(WorkflowAction.class, svo.getWorkflowUserId());
			User wu = (User) paymentDao.get(User.class, SystemUtil.getCurrentUserId());
			User au = (User) paymentDao.get(User.class, 0);
			
			p.setPaymentReferenceCode(svo.getPaymentReferenceCode());
			p.setPaidDate(CcmFormat.pase(svo.getPaidDate(),"yyyy/MM/dd"));
			p.setWorkflowAction(wa);
			p.setDescription(svo.getNotes());
			p.setUserByWorkflowUserId(wu);
			p.setUserByAssignmentUserId(au);
			
			p.setModifiedBy(SystemUtil.getCurrentUserId());
			p.setModifiedTimestamp(new Date());
			
			paymentDao.update(p);

			sb.append("\"It is OK!\"");
		} catch (RuntimeException e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			sb.append("{error:\""+e.getMessage()+"!\"}");
			throw bpe;
		}
		logger.info("Exit method paymentPTConfirm.");
		return sb.toString();
	}
	/**
	 * Get Payment File Number
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getPaymentFileTotalNO(SearchPaymentVO svo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Payment File Number.",svo));
		String json = svo.getTotalPageNoJson(paymentDao.getPaymentFileTotalNO(svo));
		logger.info(CcmFormat.formatExitLog());
		return json;
	}
	/**
	 * Search Payment File List Info
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String searchPaymentFileList(SearchPaymentVO svo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Payment File List Info.",svo));
		String json = "";
		try {
			json = svo.getListJsonCompatible(paymentDao.searchPaymentFileList(svo));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return json;
	}
	/**
	 * Delete Payment File List Info
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String deletePaymentFileByFId(SearchPaymentVO svo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Delete Payment File List Info.",svo));
		try {
			AttachmentFile af = (AttachmentFile) paymentDao.get(AttachmentFile.class,svo.getAttachmentFileId());
			af.setRecActiveFlag("N");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return "{error:false}";
	}
	
	public String searchPaymentAssignment(SearchPaymentVO svo) throws BPLException {
		logger.info("Enter method searchPaymentAssignment.");
		String s;
		List<String> list = null;
		try {
			list = paymentDao.searchPaymentAssignment(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		s = svo.getListJson(list);
		logger.info("Exit method searchPaymentAssignment.");
		return s;
	}
	/**
	 * pengfei.wang
	 * */
	public String getPaymentWorkCount(WorkspaceVO wvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getInvoiceWorkCount.",wvo ));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = paymentDao.getPaymentWorkCount(wvo);
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
	/**
	 * pengfei.wang
	 * */
	public String searchPaymentWorkCount(WorkspaceVO wvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getOriginalList.", wvo));

		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = paymentDao.searchPaymentWorkCount(wvo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
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
}
