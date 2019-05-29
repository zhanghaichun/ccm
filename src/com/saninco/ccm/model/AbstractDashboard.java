package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractDashboard entity provides the base persistence definition of the
 * Dashboard entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractDashboard implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer row;
	private String title;
	private String idStrCenter;
	private String imgSrc;
	private String isOpen;
	private Integer sort;
	private Date createdTimestamp;
	private Date modifiedTimestamp;

	// Constructors

	/** default constructor */
	public AbstractDashboard() {
	}

	/** minimal constructor */
	public AbstractDashboard(Integer userId, String isOpen) {
		this.userId = userId;
		this.isOpen = isOpen;
	}

	/** full constructor */
	public AbstractDashboard(Integer userId, String title, String idStrCenter,
			String imgSrc, String isOpen, Date createdTimestamp,
			Date modifiedTimestamp) {
		this.userId = userId;
		this.title = title;
		this.idStrCenter = idStrCenter;
		this.imgSrc = imgSrc;
		this.isOpen = isOpen;
		this.createdTimestamp = createdTimestamp;
		this.modifiedTimestamp = modifiedTimestamp;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdStrCenter() {
		return this.idStrCenter;
	}

	public void setIdStrCenter(String idStrCenter) {
		this.idStrCenter = idStrCenter;
	}

	public String getImgSrc() {
		return this.imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getIsOpen() {
		return this.isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getModifiedTimestamp() {
		return this.modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

}