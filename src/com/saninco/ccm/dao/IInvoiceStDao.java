package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.BarCode;
import com.saninco.ccm.model.InvoiceSt;

/**
 * @author Jian.Dong
 */
public interface IInvoiceStDao {
	public void save(BarCode bc);
	
	public List<InvoiceSt> getInvoiceSt();	
	
	public List findByProperty(String propertyName, Object value);
}
