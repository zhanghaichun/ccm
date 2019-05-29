package com.saninco.ccm.vo;

import java.io.Serializable;

public class InvoiceNoteVO extends SearchVO implements Serializable {
	
//	private static final long serialVersionUID = -7114200802404926024L;
		private String notes;
		private String createDate;
		private String vendorId;
		private String banId;
		private String userId;
		private String banAccountNumber;
		private String invoiceId;
		
	
		public String getInvoiceId() {
			return invoiceId;
		}
		public void setInvoiceId(String invoiceId) {
			this.invoiceId = invoiceId;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		public String getCreateDate() {
			return createDate;
		}
		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}
		public String getVendorId() {
			return vendorId;
		}
		public void setVendorId(String vendorId) {
			this.vendorId = vendorId;
		}
		public String getBanId() {
			return banId;
		}
		public void setBanId(String banId) {
			this.banId = banId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getBanAccountNumber() {
			return banAccountNumber;
		}
		public void setBanAccountNumber(String banAccountNumber) {
			this.banAccountNumber = banAccountNumber;
		}
		
		
	
	
	
}
