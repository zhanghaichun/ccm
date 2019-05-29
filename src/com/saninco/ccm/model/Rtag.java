package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * Rtag entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Rtag extends AbstractRtag implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Rtag() {
	}

	/** full constructor */
	public Rtag(String rtagName, String rtagColor, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, Set rtagRoles, Set rtagReports) {
		super(rtagName, rtagColor, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag, rtagRoles,
				rtagReports);
	}
	/**
	 * 
	 * @Auchor Chao.Liu | On Date: Nov 18, 2010
	 * @Belong To 
	 * @param id
	 */
	public Rtag(Integer id){
		this.setId(id);
	}
}
