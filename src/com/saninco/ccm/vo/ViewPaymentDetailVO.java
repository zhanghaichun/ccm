package com.saninco.ccm.vo;

import java.io.Serializable;

/**
 * @author xinyu.Liu
 */
public class ViewPaymentDetailVO extends SearchVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5319007026175999502L;

	// payment ### id
	private String paymentId;
	// action ### id
	private String actionId;
	// payment_history ### notes
	private String notes;

	// Second Page section
	private int pageNoThree;
	private int recPerPageThree;

	private String sortFieldThree;
	private String sortingDirectionThree;

	/**
	 * @return Second Page section
	 */
	public int getStartIndexThree() {
		return (this.pageNoThree - 1) * this.recPerPageThree;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId.trim();
	}

	public int getPageNoThree() {
		return pageNoThree;
	}

	public void setPageNoThree(int pageNoThree) {
		this.pageNoThree = pageNoThree;
	}

	public int getRecPerPageThree() {
		return recPerPageThree;
	}

	public void setRecPerPageThree(int recPerPageThree) {
		this.recPerPageThree = recPerPageThree;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes.trim();
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId.trim();
	}

	public String getSortFieldThree() {
		return sortFieldThree;
	}

	public void setSortFieldThree(String sortFieldThree) {
		this.sortFieldThree = sortFieldThree.trim();
	}

	public String getSortingDirectionThree() {
		return sortingDirectionThree;
	}

	public void setSortingDirectionThree(String sortingDirectionThree) {
		this.sortingDirectionThree = sortingDirectionThree.trim();
	}

	public String toString() {
		return "ViewPaymentDetailVO [actionId=" + actionId + ", notes=" + notes
				+ ", pageNoThree=" + pageNoThree + ", paymentId=" + paymentId
				+ ", recPerPageThree=" + recPerPageThree + ", sortFieldThree="
				+ sortFieldThree + ", sortingDirectionThree="
				+ sortingDirectionThree + "]";
	}

}
