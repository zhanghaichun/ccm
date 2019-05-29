package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Originator;
import com.saninco.ccm.vo.SearchInvoiceVO;

public interface IOriginatorDao {

	public void save(Originator transientInstance) ;
	public void delete(Originator persistentInstance);
	public Originator findById(java.lang.Integer id);
	public List findAll();
	public List findByExample(Originator instance);
	public List findByProperty(String propertyName, Object value);
	public List findByOriginatorName(Object originatorName);
	public Originator merge(Originator detachedInstance);
	public void attachDirty(Originator instance);
	public void attachClean(Originator instance);
	public List<String>getOriginatorAndReturnStringList(SearchInvoiceVO searchInvoiceVO);
}
