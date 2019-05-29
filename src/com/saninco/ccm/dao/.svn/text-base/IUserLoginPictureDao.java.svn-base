package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserLoginPicture;

public interface IUserLoginPictureDao {

	public abstract void save(UserLoginPicture transientInstance);

	public abstract void delete(UserLoginPicture persistentInstance);

	public abstract void delete(Integer id);
	
	public abstract UserLoginPicture findById(java.lang.Integer id);

	public abstract List findByExample(UserLoginPicture instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByFilePath(Object filePath);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();

	public abstract UserLoginPicture merge(UserLoginPicture detachedInstance);

	public abstract void attachDirty(UserLoginPicture instance);

	public abstract void attachClean(UserLoginPicture instance);

	public abstract Long findByCountProperty(String propertyName, Object value);
	
	/**
	 * Get User Login Picture Id List By UserId [Chao.Liu]
	 * @param id
	 * @return
	 */
	public List<String> getPicIdByUid(Integer uid);
	
}