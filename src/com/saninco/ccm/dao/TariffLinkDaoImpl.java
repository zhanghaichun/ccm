package com.saninco.ccm.dao;

import java.io.File;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.TariffLink;

public class TariffLinkDaoImpl extends HibernateDaoSupport implements
		ITariffLinkDao {

	public TariffLinkDaoImpl() {
	}

	public void save(TariffLink tariffLink) {
		getHibernateTemplate().save(tariffLink);
	}
	
	public void deleteTariffLinkAndFile(TariffLink tariffLink) {
		tariffLink =  (TariffLink)getHibernateTemplate().get(TariffLink.class, tariffLink.getId());
		
		String filePath = tariffLink.getTariffPath();
		
		if (filePath != null && !filePath.equals("")) {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
		}
		
		getHibernateTemplate().delete(tariffLink);
	}

}
