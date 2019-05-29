package com.saninco.ccm.vo;

public class WorkspaceVO extends SearchVO {
	private Integer uid;
	private Integer dueDay;
	private Integer disputeDay;

	public WorkspaceVO() {
	}

	/*
	 * Getter And Setter
	 */
	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getDueDay() {
		return dueDay;
	}

	public void setDueDay(Integer dueDay) {
		this.dueDay = dueDay;
	}

	public String toString() {
		return "WorkspaceVO [dueDay=" + dueDay + ", uid=" + uid + "]";
	}

	public Integer getDisputeDay() {
		return disputeDay;
	}

	public void setDisputeDay(Integer disputeDay) {
		this.disputeDay = disputeDay;
	}

}
