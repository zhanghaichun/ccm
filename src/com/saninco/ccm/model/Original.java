package com.saninco.ccm.model;

import java.util.Date;

/**
 * Original entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Original extends AbstractOriginal implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Original() {
	}

	/** full constructor */
	public Original(Invoice invoice, String fileName, String filePath,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		super(invoice, fileName, filePath, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag);
	}

}
