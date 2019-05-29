package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.CreditStatus;
import com.saninco.ccm.vo.SearchCreditVO;


/**
 * @author xinyu.Liu
 */
public interface ICreditStatusDao {
	
	public List<CreditStatus> getCreditStatus();
	
}