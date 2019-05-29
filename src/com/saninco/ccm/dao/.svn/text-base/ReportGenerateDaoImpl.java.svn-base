package com.saninco.ccm.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.CreatedReport;

public class ReportGenerateDaoImpl extends HibernateDaoSupport implements
		IReportGenerateDao
{

	@SuppressWarnings("unchecked")
	public List<CreatedReport> getGenerateReport()
	{
		logger.info("Enter method ReportGenerateDaoImpl.getGenerateReport");

		String sql = " select a from CreatedReport a where a.reportStatus = 1 or a.reportStatus = 2 ";
		
		List<CreatedReport> list = (List<CreatedReport>) this.getHibernateTemplate().find( sql );
		
		logger.info("Exit method ReportGenerateDaoImpl.getGenerateReport");
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public void updateCreatedReportStatus(Integer id)
	{
	}

	
}
