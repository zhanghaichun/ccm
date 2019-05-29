package com.saninco.ccm.model.bi;

public class BIQuoteVendor extends AbstractBIQuoteVendor implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1974604743310147401L;
		// Constructors
		private String labelName;
		
		/** default constructor */
		public BIQuoteVendor() {
		}

		/** minimal constructor */
		public BIQuoteVendor(Integer id) {
			super(id);
		}

		/** full constructor */
		public BIQuoteVendor(Integer id, String vendorName) {
			super(id, vendorName);
		}

		public String getLabelName() {
			return labelName == null ? this.getVendorName() : labelName;
		}

		public void setLabelName(String labelName) {
			this.labelName = labelName;
		}

}
