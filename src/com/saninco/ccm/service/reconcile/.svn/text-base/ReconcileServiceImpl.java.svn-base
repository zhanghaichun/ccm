package com.saninco.ccm.service.reconcile;

import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ICreditDao;
import com.saninco.ccm.dao.IDisputeDao;
import com.saninco.ccm.dao.IReconcileDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.Reconcile;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchReconcileVO;

public class ReconcileServiceImpl implements IReconcileService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IDisputeDao disputeDao;
	private ICreditDao creditDao;
	private IReconcileDao reconcileDao;


	public String getPaymentSearchTotalPageNo(
			SearchReconcileVO searchReconcileVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getCreditSearchTotalPageNo.", searchReconcileVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = reconcileDao.getNumberOfReconcile(searchReconcileVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchReconcileVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public String searchReconcile(SearchReconcileVO searchReconcileVO)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method searchCreditVO.", searchReconcileVO));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = reconcileDao.searchReconcile(searchReconcileVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list!=null&&list.size()>0){
			sb.append("{begin:");
			sb.append(searchReconcileVO.getStartIndex()+1);
			sb.append(",end:");
			int size = list.size();
			sb.append(searchReconcileVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				if(i!=0) sb.append(",");
				sb.append(list.get(i).toString());
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
	 * Get total page number and result number.
	 * Return JSON string.
	 */
	public String getReconcileSearchTotalPageNo(SearchReconcileVO searchReconcileVO)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getReconcileSearchTotalPageNo.", searchReconcileVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = reconcileDao.getNumberOfReconcile(searchReconcileVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchReconcileVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public IReconcileDao getReconcileDao() {
		return reconcileDao;
	}

	public void setReconcileDao(IReconcileDao reconcileDao) {
		this.reconcileDao = reconcileDao;
	}

	/**
	 * @return the disputeDao
	 */
	public IDisputeDao getDisputeDao() {
		return disputeDao;
	}

	/**
	 * @param disputeDao the disputeDao to set
	 */
	public void setDisputeDao(IDisputeDao disputeDao) {
		this.disputeDao = disputeDao;
	}

	/**
	 * @return the creditDao
	 */
	public ICreditDao getCreditDao() {
		return creditDao;
	}

	/**
	 * @param creditDao the creditDao to set
	 */
	public void setCreditDao(ICreditDao creditDao) {
		this.creditDao = creditDao;
	}
	
	/**
	 * 
	 */
	public void updateCreditOrDisputebalanceRollback(int reconcileId,String disputeNumber,String creditNumber,int disputeId,int creditId,double amount)throws BPLException 
	{
		Credit c;
		Dispute d;
		Reconcile r;
		logger.info("Enter method updateCreditOrDisputebalanceRollback.");
			
		if((("").equals(creditNumber))&&(!(("").equals(disputeNumber))))
		{	
			r=reconcileDao.findById(reconcileId);
			r.setRecActiveFlag("N");
			d=disputeDao.findById(disputeId);
			d.setDisputeBalance(amount);
			try {
				disputeDao.updateDisputeBalanceRollback(d);
				reconcileDao.merge(r);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
		}
		else if((("").equals(disputeNumber))&&(!(("").equals(creditNumber)))){
			r=reconcileDao.findById(reconcileId);
			r.setRecActiveFlag("N");
			c=creditDao.findById(creditId);
			c.setCreditBalance(amount);
			try {
				creditDao.updateCreditBalanceRollback(c);
				reconcileDao.merge(r);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
		
		}	
		else{
			r=reconcileDao.findById(reconcileId);
			r.setRecActiveFlag("N");
			d = disputeDao.findById(disputeId);
			d.setDisputeBalance(amount);
			c = creditDao.findById(creditId);
			c.setCreditBalance(amount);
			try {
				disputeDao.updateDisputeBalanceRollback(d);
				creditDao.updateCreditBalanceRollback(c);
				reconcileDao.merge(r);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			
		
			}
		
		logger.info("Exit method updateCreditOrDisputebalanceRollback.");

		}
	
	
}
