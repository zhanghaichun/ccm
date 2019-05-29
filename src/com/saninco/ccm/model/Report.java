package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * Report entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Report extends AbstractReport implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Report() {
	}

	/** full constructor */
	public Report(String reportName, String frequency, Integer runDay,
			Date runTime, String userRunFlag, String autoRunFlag,
			String autoRunParameterString, String sendEmail,
			String reportFormat, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			Set rtagReports, Set createdReports) {
		super(reportName, frequency, runDay, runTime, userRunFlag, autoRunFlag,
				autoRunParameterString, sendEmail, reportFormat,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy,
				recActiveFlag, rtagReports, createdReports);
	}
	/**
	 * 
	 * @Auchor Chao.Liu | On Date: Nov 18, 2010
	 * @Belong To 
	 * @param id
	 */
	public Report(Integer id){
		this.setId(id);
	}

}
