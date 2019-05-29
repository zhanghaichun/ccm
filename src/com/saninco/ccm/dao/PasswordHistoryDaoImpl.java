package com.saninco.ccm.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.PasswordHistory;

public class PasswordHistoryDaoImpl extends HibernateDaoSupport implements
		IPasswordHistoryDao {

	@SuppressWarnings("unchecked")
	public List<String> findUsedPasswords(int userId) {
		StringBuffer sql = new StringBuffer("select ph.password from PasswordHistory ph");
		sql.append(" where ph.user.id=? order by id.createdTimestamp desc ");
		
		return (List<String>) getHibernateTemplate().find(sql.toString(), userId);
	}

	public void insertPasswordHistory(PasswordHistory passwordHistory) {
		getHibernateTemplate().persist(passwordHistory);		
	}

}
