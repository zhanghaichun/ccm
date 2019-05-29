/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.vo.AccountCodeVO;

public interface IAccountCodelDao {
	
	public List getAccountCode();

	public List getAllAccountCode();

	public List getChargeType();
	
	public List getTaxCode();
	
	public AccountCode findById(java.lang.Integer id);
	
	public String checkScoaCodeName(AccountCodeVO avo);
	
	public Object[] checkScoaCodeNameOne(AccountCodeVO avo);
	
	public void save(Object o);
	
	public void save0(AccountCode o);

	public List getAccountCodeListByDisputeProposals(Integer id);
	
	public void delete(AccountCodeVO avo);

	public List<AccountCode> findByName(String scoacode);

	public AccountCode merge(AccountCode accountCode);
	
}
