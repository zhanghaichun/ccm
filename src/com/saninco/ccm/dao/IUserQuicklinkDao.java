package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.UserQuicklink;

public interface IUserQuicklinkDao {

	public abstract void save(UserQuicklink transientInstance);

	public abstract void delete(UserQuicklink persistentInstance);

	public abstract UserQuicklink findById(java.lang.Integer id);

	public abstract List findByExample(UserQuicklink instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByQuicklinkName(Object quicklinkName,String quicklinkType);

	public abstract List findByRequestString(Object requestString);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();

	public abstract UserQuicklink merge(UserQuicklink detachedInstance);

	public abstract void attachDirty(UserQuicklink instance);

	public abstract void attachClean(UserQuicklink instance);
	
	public abstract List findQuicklinks(String quicklinkType, Integer userId);

	public List<Object[]> findQuicklinks(Integer userId);

	public String getPageName(int quicklinkId);

	public void delete();

	public UserQuicklink findByQuicklinkName(Object quicklinkName);

	public UserQuicklink find();
}