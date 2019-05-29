package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.CreatedReport;

public interface IReportGenerateDao {
	
	public List<CreatedReport> getGenerateReport();
	
	public void updateCreatedReportStatus( Integer id );

}
