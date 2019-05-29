package com.saninco.ccm.model;

import java.util.Date;
public class CreatedReportParameter extends AbstractCreatedReportParameter implements java.io.Serializable
{

	/** default constructor */
	public CreatedReportParameter() {
	}

	/** full constructor */
	public CreatedReportParameter(Integer report, String tableFiled,
			String value, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		super(report, tableFiled , value,  createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag);
	}
}
