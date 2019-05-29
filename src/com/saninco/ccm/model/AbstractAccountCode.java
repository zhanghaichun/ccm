package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractAccountCode entity provides the base persistence definition of the
 * AccountCode entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractAccountCode implements java.io.Serializable {

	// Fields

	private Integer id;
	private String accountCodeName;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String accountCodeDescription;
	private String company;
	private String location;
	private String department;
	private String product;
	private String account;
	private String channel;
	private String activeFlag;
	private String segment7;
	private String segment8;
	private String interco;

	private Set proposals = new HashSet(0);
	private Set credits = new HashSet(0);
	private Set reconciles = new HashSet(0);
	private Set paymentDetails = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractAccountCode() {
	}

	/** full constructor */
	public AbstractAccountCode(String accountCodeName, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, String accountCodeDescription,
			String company, String location, String department, String product,
			String account, String channel, String segment7, String segment8,
			String interco, Set proposals, Set credits, Set reconciles,
			Set paymentDetails,String activeFlag) {
		this.accountCodeName = accountCodeName;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.accountCodeDescription = accountCodeDescription;
		this.company = company;
		this.location = location;
		this.department = department;
		this.product = product;
		this.account = account;
		this.channel = channel;
		this.segment7 = segment7;
		this.segment8 = segment8;
		this.interco = interco;
		this.proposals = proposals;
		this.credits = credits;
		this.reconciles = reconciles;
		this.paymentDetails = paymentDetails;
		this.activeFlag = activeFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountCodeName() {
		return this.accountCodeName;
	}

	public void setAccountCodeName(String accountCodeName) {
		this.accountCodeName = accountCodeName;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return this.modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String getAccountCodeDescription() {
		return this.accountCodeDescription;
	}

	public void setAccountCodeDescription(String accountCodeDescription) {
		this.accountCodeDescription = accountCodeDescription;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSegment7() {
		return this.segment7;
	}

	public void setSegment7(String segment7) {
		this.segment7 = segment7;
	}

	public String getSegment8() {
		return this.segment8;
	}

	public void setSegment8(String segment8) {
		this.segment8 = segment8;
	}

	public String getInterco() {
		return this.interco;
	}

	public void setInterco(String interco) {
		this.interco = interco;
	}

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	public Set getCredits() {
		return this.credits;
	}

	public void setCredits(Set credits) {
		this.credits = credits;
	}

	public Set getReconciles() {
		return this.reconciles;
	}

	public void setReconciles(Set reconciles) {
		this.reconciles = reconciles;
	}

	public Set getPaymentDetails() {
		return this.paymentDetails;
	}

	public void setPaymentDetails(Set paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	/**
	 * @return the activeFlag
	 */
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag
	 *            the activeFlag to set
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractAccountCode [");
		if (account != null)
			builder.append("account=").append(account).append(", ");
		if (accountCodeDescription != null)
			builder.append("accountCodeDescription=").append(
					accountCodeDescription).append(", ");
		if (accountCodeName != null)
			builder.append("accountCodeName=").append(accountCodeName).append(
					", ");
		if (activeFlag != null)
			builder.append("activeFlag=").append(activeFlag).append(", ");
		if (channel != null)
			builder.append("channel=").append(channel).append(", ");
		if (company != null)
			builder.append("company=").append(company).append(", ");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (department != null)
			builder.append("department=").append(department).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (interco != null)
			builder.append("interco=").append(interco).append(", ");
		if (location != null)
			builder.append("location=").append(location).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (product != null)
			builder.append("product=").append(product).append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (segment7 != null)
			builder.append("segment7=").append(segment7).append(", ");
		if (segment8 != null)
			builder.append("segment8=").append(segment8);
		builder.append("]");
		return builder.toString();
	}


}