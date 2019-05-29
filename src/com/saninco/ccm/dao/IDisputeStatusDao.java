package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.DisputeStatus;


/**
 * @author Joe.Yang
 */
public interface IDisputeStatusDao {
	
	public List<DisputeStatus> getDisputeStatus();
	public DisputeStatus findById(java.lang.Integer id);
}