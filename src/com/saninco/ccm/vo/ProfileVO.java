package com.saninco.ccm.vo;

import java.io.Serializable;

/**
 * @author Jian.Dong
 * */
public class ProfileVO extends SearchVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long delegateToUserId;
	private String fromDate;
	private String toDate;
	private Long userDelegationId;
	private int flagOfEdit;

	/**
	 * @return the flagOfEdit
	 */
	public int getFlagOfEdit() {
		return flagOfEdit;
	}

	/**
	 * @param flagOfEdit
	 *            the flagOfEdit to set
	 */
	public void setFlagOfEdit(int flagOfEdit) {
		this.flagOfEdit = flagOfEdit;
	}

	public Long getUserDelegationId() {
		return userDelegationId;
	}

	public void setUserDelegationId(Long userDelegationId) {
		this.userDelegationId = userDelegationId;
	}

	public Long getDelegateToUserId() {
		return delegateToUserId;
	}

	public void setDelegateToUserId(Long delegateToUserId) {
		this.delegateToUserId = delegateToUserId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String toString() {
		return "ProfileVO [delegateToUserId=" + delegateToUserId
				+ ", flagOfEdit=" + flagOfEdit + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", userDelegationId="
				+ userDelegationId + "]";
	}

}
