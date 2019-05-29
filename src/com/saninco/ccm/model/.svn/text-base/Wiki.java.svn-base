package com.saninco.ccm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Wiki extends AbstractWiki {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1160845182790461978L;
	private String publishUser;

	public Wiki() {
		super();
	}

	public Wiki(Integer id, String title, String content, String contentText, Integer lististop, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		super(id, title, content, contentText, lististop, createdTimestamp, createdBy, modifiedTimestamp,
				modifiedBy, recActiveFlag);
		// TODO Auto-generated constructor stub
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	
	public String getPublishTime() {
		String publishTime = "";
		if (super.getModifiedTimestamp() != null) {
			publishTime = new SimpleDateFormat("yyyy/MM/dd").format(super.getModifiedTimestamp());
		}
		return publishTime;
	}

}
