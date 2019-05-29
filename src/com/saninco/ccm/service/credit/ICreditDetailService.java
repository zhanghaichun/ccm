package com.saninco.ccm.service.credit;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.vo.SearchVO;
/**
 * This is the CreidtDetail BusinessLogic
 * @author Chao.Liu
 *
 */
public interface ICreditDetailService {
	/**
	 * This is method designed to Get Credit Object data
	 * @param id
	 * @return Credit Class
	 */
	public Credit getCredit(Integer id);
	/**
	 * This is method designed to Get Dispute_Table collection data
	 * @param v
	 * @param cid
	 * @return Json String 
	 * @throws BPLException
	 */
	public String getDisputeList(SearchVO v,Integer cid) throws BPLException;
	/**
	 * This is method designed to Get Dispute_Table data total number
	 * @param v
	 * @param cid
	 * @return Json String 
	 * @throws BPLException
	 */
	public String getCreditSearchTotalPageNo(SearchVO v,Integer cid)throws BPLException ;
	/**
	 * Search all Recocile data to database
	 * @param v
	 * @param did
	 * @return Json String 
	 * @throws BPLException
	 */
	public String getReconcileList(SearchVO v, Integer did) throws BPLException ;
	/**
	 * Clear one data in the Reconcile_Table
	 * @param creditId
	 * @param disputeId
	 * @param reconcileId
	 * @param rbalance
	 * @return Json String 
	 * @throws BPLException
	 */
	public String cancelForReconcileByCredit(
			Integer creditId,Integer disputeId,Integer reconcileId,Double rbalance) throws BPLException;
	/**
	 * Add one data in the Reconcile_Table
	 * @param notes
	 * @param creditId
	 * @param disputeId
	 * @param reconcileId
	 * @param amount
	 * @return Json String 
	 * @throws BPLException
	 */
	public String addOneReconcileData(String notes,
			Integer creditId,Integer disputeId,Integer reconcileId,Double amount) throws BPLException;
}
