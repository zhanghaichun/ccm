/**
 * 
 */
package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.saninco.ccm.util.SystemUtil;

/**
 * @author Joe.Yang
 * 
 *         add userId beijing 2010-4-16 Jian.Dong delete
 *         recPerPage,pageNo,sortField,sortingDirection,getStartIndex();
 *         Jian.Dong
 */
public class SearchInvoiceVO extends SearchVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891774932870661027L;
	private String vendorId;
	private String banId;
	private String currencyId;
	private String invoiceNumber;
	private String statusId;
	private String analystId;
	private String paymentStatusId;
	private String paymentReference;
	private Integer daysInStatus;
	private String proposalId;
	private String parentProposalId;
	private Integer handleId;
	private String invoiceStructureCodeId;
	private String inst;
	private String tableField;
	private String barCode;
	private String accordionName;
	private String invoiceAdjustment;
	private boolean historicalInvoice;
	private List<String> displayTitle = new ArrayList<String>();
	private String single;
	private String[] proposalsId;

	// payment date
	private String paymentDateStartsOn;
	private String paymentDateEndsOn;

	private String paymentDateWiPastNumOfDays;
	private String paymentReferenceCode;
	private String paidDate; 

	// invoice date
	private String invoiceDateStartsOn;
	private String invoiceDateEndsOn;
	private String invoiceDateWiPastNumOfDays;
	// invoice due date
	private String invoiceDueStartsOn;
	private String invoiceDueEndsOn;
	private String invoiceDueWiPastNumOfDays;
	private String invoiceDueWiNextNumOfDays;
	// amount
	private String leastAmount;
	private String greatestAmount;
	// receive date
	private String invoiceReceiveStartsOn;
	private String invoiceReceiveEndsOn;
	private String invoiceReceiveWiPastNumOfDays;
	private String radio;

	private Integer userId;

	private String typeName;
	private String labelName;

	// Invoice Detail
	private String itemId;
	private String invoiceId;
	private String parentItemId;
	private String itemTypeId;
	private String invoiceItemId;
	private String scoaCodeId;

	// Proposal
	private String disputeReason;
	private String disputeReasonId;
	private String paymentAmount;
	private String disputeAmount;
	private String cleanupAmount;
	private String paymentNotes;
	private String disputeNotes;
	private String cleanupNotes;
	private String cricuitNumber;
	private String serviceType;

	private String boxInId;
	private String pastAccountCodeId;
	private String pastDisputeCategoryId;
	private String nowAccountCodeId;
	private String nowDisputeCategoryId;
	private String pastProposalName;
	private String nowProposalName;
	private String note;
	private String amount;
	private String originatorId;
	private String description;
	private Integer attachmentPointId;

	// Credit Tab
	private String creditId;
	private String disputeId;
	private String reconcileId;
	private String accountCodeId;

	private String commNote;
	private Double reconcileAmount;

	private Integer invoiceId1;
	private Integer banId1;

	private String lineOfBusiness;
	
	private String emailFlag;
	
	private String site;
	
	private String scoacode;
	private boolean insertConfirmed;
	private boolean reject;
	
	private boolean upPayment;
	
	public boolean isUpPayment() {
		return upPayment;
	}

	public void setUpPayment(boolean upPayment) {
		this.upPayment = upPayment;
	}

	public boolean isInsertConfirmed() {
		return insertConfirmed;
	}

	public void setInsertConfirmed(boolean insertConfirmed) {
		this.insertConfirmed = insertConfirmed;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getInvoiceId1() {
		return invoiceId1;
	}

	public void setInvoiceId1(Integer invoiceId1) {
		this.invoiceId1 = invoiceId1;
	}

	public Integer getBanId1() {
		return banId1;
	}

	public void setBanId1(Integer banId1) {
		this.banId1 = banId1;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginatorId() {
		return originatorId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setOriginatorId(String originatorId) {
		this.originatorId = originatorId;
	}

	public Double getReconcileAmount() {
		return reconcileAmount;
	}

	public void setReconcileAmount(Double reconcileAmount) {
		this.reconcileAmount = reconcileAmount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SearchInvoiceVO() {
	}

	/**
	 * @return the vendorId
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId
	 *            the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId.trim();
	}

	/**
	 * @return the banId
	 */
	public String getBanId() {
		return banId;
	}

	/**
	 * @param banId the banId to set
	 */
	public void setBanId(String banId) {
		this.banId = banId==null ? null : banId.trim();
	}

	/**
	 * @return the currencyId
	 */
	public String getCurrencyId() {
		return currencyId;
	}

	/**
	 * @param currencyId
	 *            the currencyId to set
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId.trim();
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;//shui xie de
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber.trim();
	}

	/**
	 * @return the statusId
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId.trim();
	}

	/**
	 * @return the invoiceDateStartsOn
	 */
	public String getInvoiceDateStartsOn() {
		return invoiceDateStartsOn;
	}

	/**
	 * @param invoiceDateStartsOn
	 *            the invoiceDateStartsOn to set
	 */
	public void setInvoiceDateStartsOn(String invoiceDateStartsOn) {
		this.invoiceDateStartsOn = invoiceDateStartsOn.trim();
	}

	/**
	 * @return the invoiceDateEndsOn
	 */
	public String getInvoiceDateEndsOn() {
		return invoiceDateEndsOn;
	}

	/**
	 * @param invoiceDateEndsOn
	 *            the invoiceDateEndsOn to set
	 */
	public void setInvoiceDateEndsOn(String invoiceDateEndsOn) {
		this.invoiceDateEndsOn = invoiceDateEndsOn.trim();
	}

	/**
	 * @return the invoiceDueStartsOn
	 */
	public String getInvoiceDueStartsOn() {
		return invoiceDueStartsOn;
	}

	/**
	 * @param invoiceDueStartsOn
	 *            the invoiceDueStartsOn to set
	 */
	public void setInvoiceDueStartsOn(String invoiceDueStartsOn) {
		this.invoiceDueStartsOn = invoiceDueStartsOn.trim();
	}

	/**
	 * @return the invoiceDueEndsOn
	 */
	public String getInvoiceDueEndsOn() {
		return invoiceDueEndsOn;
	}

	/**
	 * @param invoiceDueEndsOn
	 *            the invoiceDueEndsOn to set
	 */
	public void setInvoiceDueEndsOn(String invoiceDueEndsOn) {
		this.invoiceDueEndsOn = invoiceDueEndsOn.trim();
	}

	/**
	 * @return the leastAmount
	 */
	public String getLeastAmount() {
		return leastAmount;
	}

	/**
	 * @param leastAmount
	 *            the leastAmount to set
	 */
	public void setLeastAmount(String leastAmount) {
		this.leastAmount = leastAmount.trim();
	}

	/**
	 * @return the greatestAmount
	 */
	public String getGreatestAmount() {
		return greatestAmount;
	}

	/**
	 * @param greatestAmount
	 *            the greatestAmount to set
	 */
	public void setGreatestAmount(String greatestAmount) {
		this.greatestAmount = greatestAmount.trim();
	}

	/**
	 * @return the invoiceReceiveStartsOn
	 */
	public String getInvoiceReceiveStartsOn() {
		return invoiceReceiveStartsOn;
	}

	/**
	 * @param invoiceReceiveStartsOn
	 *            the invoiceReceiveStartsOn to set
	 */
	public void setInvoiceReceiveStartsOn(String invoiceReceiveStartsOn) {
		this.invoiceReceiveStartsOn = invoiceReceiveStartsOn.trim();
	}

	/**
	 * @return the invoiceReceiveEndsOn
	 */
	public String getInvoiceReceiveEndsOn() {
		return invoiceReceiveEndsOn;
	}

	/**
	 * @param invoiceReceiveEndsOn
	 *            the invoiceReceiveEndsOn to set
	 */
	public void setInvoiceReceiveEndsOn(String invoiceReceiveEndsOn) {
		this.invoiceReceiveEndsOn = invoiceReceiveEndsOn.trim();
	}

	/**
	 * @return the invoiceDateWiPastNumOfDays
	 */
	public String getInvoiceDateWiPastNumOfDays() {
		return invoiceDateWiPastNumOfDays;
	}

	/**
	 * @param invoiceDateWiPastNumOfDays
	 *            the invoiceDateWiPastNumOfDays to set
	 */
	public void setInvoiceDateWiPastNumOfDays(String invoiceDateWiPastNumOfDays) {
		this.invoiceDateWiPastNumOfDays = invoiceDateWiPastNumOfDays.trim();
	}

	/**
	 * @return the invoiceDueWiPastNumOfDays
	 */
	public String getInvoiceDueWiPastNumOfDays() {
		return invoiceDueWiPastNumOfDays;
	}

	/**
	 * @param invoiceDueWiPastNumOfDays
	 *            the invoiceDueWiPastNumOfDays to set
	 */
	public void setInvoiceDueWiPastNumOfDays(String invoiceDueWiPastNumOfDays) {
		this.invoiceDueWiPastNumOfDays = invoiceDueWiPastNumOfDays.trim();
	}

	/**
	 * @return the invoiceDueWiNextNumOfDays
	 */
	public String getInvoiceDueWiNextNumOfDays() {
		return invoiceDueWiNextNumOfDays;
	}

	/**
	 * @param invoiceDueWiNextNumOfDays
	 *            the invoiceDueWiNextNumOfDays to set
	 */
	public void setInvoiceDueWiNextNumOfDays(String invoiceDueWiNextNumOfDays) {
		this.invoiceDueWiNextNumOfDays = invoiceDueWiNextNumOfDays.trim();
	}

	/**
	 * @return the invoiceReceiveWiPastNumOfDays
	 */
	public String getInvoiceReceiveWiPastNumOfDays() {
		return invoiceReceiveWiPastNumOfDays;
	}

	/**
	 * @param invoiceReceiveWiPastNumOfDays
	 *            the invoiceReceiveWiPastNumOfDays to set
	 */
	public void setInvoiceReceiveWiPastNumOfDays(
			String invoiceReceiveWiPastNumOfDays) {
		this.invoiceReceiveWiPastNumOfDays = invoiceReceiveWiPastNumOfDays
				.trim();
	}

	public String calInvoiceDateStartsOn() {
		String r = null;
		if (this.invoiceDateStartsOn != null)
			r = "'" + this.invoiceDateStartsOn + "'";
		else {
			if (this.invoiceDateWiPastNumOfDays != null)
				r = "ADDDATE('" + SystemUtil.parseString(new Date())
						+ "', INTERVAL -" + invoiceDateWiPastNumOfDays
						+ " DAY)";
		}
		return r;
	}

	public String calInvoiceDateEndsOn() {
		String r = null;
		if (this.invoiceDateEndsOn != null)
			r = "'" + this.invoiceDateEndsOn + "'";
		return r;
	}

	public String calPaymentDateStartsOn() {
		String r = null;
		if (this.paymentDateStartsOn != null)
			r = "'" + this.paymentDateStartsOn + "'";
		else {
			if (this.paymentDateWiPastNumOfDays != null)
				r = "ADDDATE('" + SystemUtil.parseString(new Date())
						+ "', INTERVAL -" + paymentDateWiPastNumOfDays
						+ " DAY)";
		}
		return r;
	}

	public String calPaymentDateEndsOn() {
		String r = null;
		if (this.paymentDateEndsOn != null)
			r = "'" + this.paymentDateEndsOn + "'";
		return r;
	}

	// modified by xinyu.Liu on 2010.5.11 for CCM-21
	public String calInvoiceDueStartsOn() {
		String r = null;
		if (this.invoiceDueStartsOn != null)
			r = "'" + this.invoiceDueStartsOn + "'";
		else if (this.invoiceDueWiPastNumOfDays != null) {
			r = "ADDDATE('" + SystemUtil.parseString(new Date())
					+ "', INTERVAL -" + invoiceDueWiPastNumOfDays + " DAY)";
		} else if (this.invoiceDueWiNextNumOfDays != null) {
			r = "'" + SystemUtil.parseString(new Date()) + "'";
		}
		return r;
	}

	public String calInvoiceDueEndsOn() {
		String r = null;
		if (this.invoiceDueEndsOn != null)
			r = "'" + this.invoiceDueEndsOn + "'";
		else if (this.invoiceDueWiNextNumOfDays != null) {
			r = "ADDDATE('" + SystemUtil.parseString(new Date())
					+ "', INTERVAL " + invoiceDueWiNextNumOfDays + " DAY)";
		}
		return r;
	}

	public String calInvoiceReceiveStartsOn() {
		String r = null;
		if (this.invoiceReceiveStartsOn != null)
			r = "'" + this.invoiceReceiveStartsOn + "'";
		else {
			if (this.invoiceReceiveWiPastNumOfDays != null)
				r = "ADDDATE('" + SystemUtil.parseString(new Date())
						+ "', INTERVAL -" + invoiceReceiveWiPastNumOfDays
						+ " DAY)";
		}
		return r;
	}

	public String calInvoiceReceiveEndsOn() {
		String r = null;
		if (this.invoiceReceiveEndsOn != null)
			r = "'" + this.invoiceReceiveEndsOn + "'";
		return r;
	}

	public String getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(String itemTypeId) {
		this.itemTypeId = itemTypeId;
	}

	public String getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(String invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getDisputeAmount() {
		return disputeAmount;
	}

	public void setDisputeAmount(String disputeAmount) {
		this.disputeAmount = disputeAmount;
	}

	public String getPaymentNotes() {
		return paymentNotes;
	}

	public void setPaymentNotes(String paymentNotes) {
		this.paymentNotes = paymentNotes;
	}

	public String getDisputeNotes() {
		return disputeNotes;
	}

	public void setDisputeNotes(String disputeNotes) {
		this.disputeNotes = disputeNotes;
	}

	public String getCleanupNotes() {
		return cleanupNotes;
	}

	public void setCleanupNotes(String cleanupNotes) {
		this.cleanupNotes = cleanupNotes;
	}

	public String getCleanupAmount() {
		return cleanupAmount;
	}

	public void setCleanupAmount(String cleanupAmount) {
		this.cleanupAmount = cleanupAmount;
	}

	public String getScoaCodeId() {
		return scoaCodeId;
	}

	public void setScoaCodeId(String scoaCodeId) {
		this.scoaCodeId = scoaCodeId;
	}

	public String getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}

	public String getBoxInId() {
		return boxInId;
	}

	public void setBoxInId(String boxInId) {
		this.boxInId = boxInId;
	}

	public String getPastAccountCodeId() {
		return pastAccountCodeId;
	}

	public void setPastAccountCodeId(String pastAccountCodeId) {
		this.pastAccountCodeId = pastAccountCodeId;
	}

	public String getPastDisputeCategoryId() {
		return pastDisputeCategoryId;
	}

	public void setPastDisputeCategoryId(String pastDisputeCategoryId) {
		this.pastDisputeCategoryId = pastDisputeCategoryId;
	}

	public String getNowAccountCodeId() {
		return nowAccountCodeId;
	}

	public void setNowAccountCodeId(String nowAccountCodeId) {
		this.nowAccountCodeId = nowAccountCodeId;
	}

	public String getNowDisputeCategoryId() {
		return nowDisputeCategoryId;
	}

	public void setNowDisputeCategoryId(String nowDisputeCategoryId) {
		this.nowDisputeCategoryId = nowDisputeCategoryId;
	}

	public String getPastProposalName() {
		return pastProposalName;
	}

	public void setPastProposalName(String pastProposalName) {
		this.pastProposalName = pastProposalName;
	}

	public String getNowProposalName() {
		return nowProposalName;
	}

	public void setNowProposalName(String nowProposalName) {
		this.nowProposalName = nowProposalName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreditId() {
		return creditId;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	public String getAccountCodeId() {
		return accountCodeId;
	}

	public void setAccountCodeId(String accountCodeId) {
		this.accountCodeId = accountCodeId;
	}

	public String getCommNote() {
		return commNote;
	}

	public void setCommNote(String commNote) {
		this.commNote = commNote.trim();
	}

	public String getDisputeId() {
		return disputeId;
	}

	public void setDisputeId(String disputeId) {
		this.disputeId = disputeId;
	}

	public String getReconcileId() {
		return reconcileId;
	}

	public void setReconcileId(String reconcileId) {
		this.reconcileId = reconcileId;
	}

	public String getAnalystId() {
		return analystId;
	}

	public void setAnalystId(String analystId) {
		this.analystId = analystId;
	}

	public String getPaymentStatusId() {
		return paymentStatusId;
	}

	public void setPaymentStatusId(String paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public Integer getDaysInStatus() {
		return daysInStatus;
	}

	public void setDaysInStatus(Integer daysInStatus) {
		this.daysInStatus = daysInStatus;
	}

	public String getPaymentDateStartsOn() {
		return paymentDateStartsOn;
	}

	public void setPaymentDateStartsOn(String paymentDateStartsOn) {
		this.paymentDateStartsOn = paymentDateStartsOn;
	}

	public String getPaymentDateEndsOn() {
		return paymentDateEndsOn;
	}

	public void setPaymentDateEndsOn(String paymentDateEndsOn) {
		this.paymentDateEndsOn = paymentDateEndsOn;
	}

	public String getPaymentDateWiPastNumOfDays() {
		return paymentDateWiPastNumOfDays;
	}

	public void setPaymentDateWiPastNumOfDays(String paymentDateWiPastNumOfDays) {
		this.paymentDateWiPastNumOfDays = paymentDateWiPastNumOfDays;
	}

	public String getDisputeReason() {
		return disputeReason;
	}

	public void setDisputeReason(String disputeReason) {
		this.disputeReason = disputeReason;
	}

	public Integer getHandleId() {
		return handleId;
	}

	public void setHandleId(Integer handleId) {
		this.handleId = handleId;
	}

	public String getInvoiceStructureCodeId() {
		return invoiceStructureCodeId;
	}

	public void setInvoiceStructureCodeId(String invoiceStructureCodeId) {
		this.invoiceStructureCodeId = invoiceStructureCodeId;
	}

	public String getInst() {
		return inst;
	}

	public void setInst(String inst) {
		this.inst = inst;
	}

	public String getTableField() {
		return tableField;
	}

	public void setTableField(String tableField) {
		this.tableField = tableField;
	}

	public List<String> getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(List<String> displayTitle) {
		this.displayTitle = displayTitle;
	}

	public String toString() {
		return "SearchInvoiceVO [accountCodeId=" + accountCodeId + ", amount="
				+ amount + ", analystId=" + analystId + ", banId=" + banId
				+ ", banId1=" + banId1 + ", boxInId=" + boxInId
				+ ", cleanupAmount=" + cleanupAmount + ", cleanupNotes="
				+ cleanupNotes + ", commNote=" + commNote + ", creditId="
				+ creditId + ", currencyId=" + currencyId + ", daysInStatus="
				+ daysInStatus + ", description=" + description
				+ ", displayTitle=" + displayTitle + ", disputeAmount="
				+ disputeAmount + ", disputeId=" + disputeId
				+ ", disputeNotes=" + disputeNotes + ", disputeReason="
				+ disputeReason + ", greatestAmount=" + greatestAmount
				+ ", handleId=" + handleId + ", inst=" + inst
				+ ", invoiceDateEndsOn=" + invoiceDateEndsOn
				+ ", invoiceDateStartsOn=" + invoiceDateStartsOn
				+ ", invoiceDateWiPastNumOfDays=" + invoiceDateWiPastNumOfDays
				+ ", invoiceDueEndsOn=" + invoiceDueEndsOn
				+ ", invoiceDueStartsOn=" + invoiceDueStartsOn
				+ ", invoiceDueWiNextNumOfDays=" + invoiceDueWiNextNumOfDays
				+ ", invoiceDueWiPastNumOfDays=" + invoiceDueWiPastNumOfDays
				+ ", invoiceId=" + invoiceId + ", invoiceId1=" + invoiceId1
				+ ", invoiceItemId=" + invoiceItemId + ", invoiceNumber="
				+ invoiceNumber + ", invoiceReceiveEndsOn="
				+ invoiceReceiveEndsOn + ", invoiceReceiveStartsOn="
				+ invoiceReceiveStartsOn + ", invoiceReceiveWiPastNumOfDays="
				+ invoiceReceiveWiPastNumOfDays + ", invoiceStructureCodeId="
				+ invoiceStructureCodeId + ", itemId=" + itemId
				+ ", itemTypeId=" + itemTypeId + ", leastAmount=" + leastAmount
				+ ", note=" + note + ", nowAccountCodeId=" + nowAccountCodeId
				+ ", nowDisputeCategoryId=" + nowDisputeCategoryId
				+ ", nowProposalName=" + nowProposalName + ", originatorId="
				+ originatorId + ", parentItemId=" + parentItemId
				+ ", pastAccountCodeId=" + pastAccountCodeId
				+ ", pastDisputeCategoryId=" + pastDisputeCategoryId
				+ ", pastProposalName=" + pastProposalName + ", paymentAmount="
				+ paymentAmount + ", paymentDateEndsOn=" + paymentDateEndsOn
				+ ", paymentDateStartsOn=" + paymentDateStartsOn
				+ ", paymentDateWiPastNumOfDays=" + paymentDateWiPastNumOfDays
				+ ", paymentNotes=" + paymentNotes + ", paymentReference="
				+ paymentReference + ", paymentStatusId=" + paymentStatusId
				+ ", proposalId=" + proposalId + ", reconcileAmount="
				+ reconcileAmount + ", reconcileId=" + reconcileId
				+ ", scoaCodeId=" + scoaCodeId + ", statusId=" + statusId
				+ ", tableField=" + tableField + ", typeName=" + typeName
				+ ", userId=" + userId + ", vendorId=" + vendorId + "]";
	}

	public String getParentProposalId() {
		return parentProposalId;
	}

	public void setParentProposalId(String parentProposalId) {
		this.parentProposalId = parentProposalId;
	}

	public String getCricuitNumber() {
		return cricuitNumber;
	}

	public void setCricuitNumber(String cricuitNumber) {
		this.cricuitNumber = cricuitNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getDisputeReasonId() {
		return disputeReasonId;
	}

	public void setDisputeReasonId(String disputeReasonId) {
		this.disputeReasonId = disputeReasonId;
	}

	public String getAccordionName() {
		return accordionName;
	}

	public void setAccordionName(String accordionName) {
		this.accordionName = accordionName;
	}

	public Integer getAttachmentPointId() {
		return attachmentPointId;
	}

	public void setAttachmentPointId(Integer attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String[] getProposalsId() {
		return proposalsId;
	}

	public void setProposalsId(String[] proposalsId) {
		this.proposalsId = proposalsId;
	}

	public boolean isHistoricalInvoice() {
		return historicalInvoice;
	}

	public void setHistoricalInvoice(boolean historicalInvoice) {
		this.historicalInvoice = historicalInvoice;
	}

	public String getLineOfBusiness() {
		return lineOfBusiness;
	}

	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	public String getScoacode() {
		return scoacode;
	}

	public void setScoacode(String scoacode) {
		this.scoacode = scoacode;
	}

	public String getInvoiceAdjustment() {
		return invoiceAdjustment;
	}

	public void setInvoiceAdjustment(String invoiceAdjustment) {
		this.invoiceAdjustment = invoiceAdjustment;
	}

	public boolean isReject() {
		return reject;
	}

	public void setReject(boolean reject) {
		this.reject = reject;
	}

	public String getPaymentReferenceCode() {
		return paymentReferenceCode;
	}

	public void setPaymentReferenceCode(String paymentReferenceCode) {
		this.paymentReferenceCode = paymentReferenceCode;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}



}
