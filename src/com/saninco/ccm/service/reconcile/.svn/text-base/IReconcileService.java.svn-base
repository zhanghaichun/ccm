package com.saninco.ccm.service.reconcile;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.SearchReconcileVO;


public interface IReconcileService {

	 public String searchReconcile(SearchReconcileVO searchReconcileVO) throws BPLException;
	 //public String getReconcileSearchTotalPageNo(SearchReconcileVO searchReconcileVO) throws BPLException;
	 public String getReconcileSearchTotalPageNo(SearchReconcileVO searchReconcileVO)throws BPLException;
	 public void updateCreditOrDisputebalanceRollback(int reconcileId,String disputeNumber,String creditNumber,int disputeId,int creditId,double amount)throws BPLException;
}
