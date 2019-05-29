package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.DisputeReason;


/**
 * @author xinyu.Liu
 */
public interface IDisputeReasonDao {
	
	public List<DisputeReason> getDisputeReason();
	public String getDisputeReasonAcronymByProrosalId(int prorosalId);
	public DisputeReason findById(java.lang.Integer id);
}