package com.saninco.ccm.dao;

import com.saninco.ccm.model.InvoiceStV;

/**
 * @author xinyu.Liu
 */
public interface IInvoiceStVDao {
	
	public void save(InvoiceStV sc);
	
	public void delete(String tableName,String barCode);
	
}
