package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Credit;
import com.saninco.ccm.vo.SearchVO;
/**
 * This is CreditDetail Class DAO(Data Access Object)
 * @author Chao.Liu
 *
 */
public interface ICreditDetailDao {
	/**
	 * Get one data to Database Credit_Table 
	 * @param id
	 * @return Credit Class
	 */
	public Credit getCredit(Integer id);
	/**
	 * Search Dispute collection to Database Dispute_Table
	 * @param v
	 * @param cid
	 * @return Dispute Class toString List
	 */
	public List<String> getDisputeList(SearchVO v,Integer cid);
	/**
	 * Get Dispute data totail Number
	 * @param v
	 * @param cid
	 * @return Totail Number
	 */
	public long getNumberOfCredit(SearchVO v,Integer cid);
	/**
	 * Search Reconcile collection to database Reconcile_Table 
	 * @param v
	 * @param creditId
	 * @return Reconcile Class toString List
	 */
	public List<String> getReconcileList(SearchVO v,Integer creditId);
	/**
	 * Count Dispute Balance
	 * @param creditId
	 * @return Dispute Balance
	 */
	public Double getDisputeForReconcileTotalAmount(Integer creditId);
	/**
	 * Update one data 
	 * @param o
	 */
	public void update(Object o);
	/**
	 * 
	 * @param c
	 * @param id
	 * @return Get Object
	 */
	public Object get(Class c,Integer id);
	/**
	 * Save one data
	 * @param o
	 */
	public Integer save(Object o);
}
