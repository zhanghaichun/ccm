package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBan entity provides the base persistence definition of the Ban
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractBan implements java.io.Serializable {

	// Fields

	private Integer id;
	private InvoiceFormat invoiceFormat;
	private BanStatus banStatus;
	private BanInactiveReason banInactiveReason;
	private InvoiceChannel invoiceChannel;
	private Currency currency;
	private AccountType accountType;
	private Vendor vendor;
	private PaymentGroup paymentGroup;
	private InvoiceStructure invoiceStructure;
	private PaymentMethod paymentMethod;
	private PaymentTerm paymentTerm;
	private String accountNumber;
	private String acna;
	private String ccna;
	private String cic;
	private String icoid;
	private String icsc;
	private String occ;
	private String processStatus;
	private String lata;
	private String stateLevelCc;
	private String productType;
	private String accountStructureInd;
	private String bsaInd;
	private String apSupplierNumber;
	private String apSupplierName;
	private String apSupplierSite;
	private String apCustomerName;
	private Integer banPrimiaryContactId;
	private Integer banPaymentContactId;
	private Integer banDisputeContactId;
	private Integer banTariffContactId;
	private Integer banBillingContactId;
	private Date banCreateDate;
	private Date banCloseDate;
	private Integer usageBackbillMonths;
	private Integer billDay;
	private Date statusTimestamp;
	private String invoiceFrequency;
	private String billingCenter;
	private String lineOfBusiness;
	private String notes;
	private String notice;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private Integer histBanId;
	private String ccmVendorName;
	private String masterBanFlag;
	private String autopayFlag;
	private Integer autopayCount;
	private Integer autopayMaxCount;
	private String paperAccountNumber;
	private String manualAdjustmentApprovalFlag;
	private String externalApproveFlag;
	private Integer maxApprovalLevel;
	private String billTo;
	private String taxRegistrationNumber;
	
	private Set receivedPayments = new HashSet(0);
	private Set tariffLinks = new HashSet(0);
	private Set inventories = new HashSet(0);
	private Set credits = new HashSet(0);
	private Set invoiceSts = new HashSet(0);
	private Set userPreviledges = new HashSet(0);
	private Set invoices = new HashSet(0);
	
	// Add Fields By Chao.Liu
	private String company;
	private String location;
	private String department;
	private String channel;
	private String mibFlag;
	private String createdBanFlag;
	private Integer analystId;
	private String analyst;
	private String currentDueType;
	private String originalAccountNumber;
	private String scoaCodeType;
	private Integer approver1Id;
	private Integer approver2Id;
	private Integer approver3Id;
	private String level1ApprovalFlag;
	private Set userPrivileges = new HashSet(0);
	
	private Set barCodes = new HashSet(0);
	private String missingEmailflag;
	private String systemModifiedMIFFlag;
	private Integer approver4Id;
	private Integer daysToDue;
	private String rejectNotes;
	
	
	
	  
	
	
	// Constructors

	/** default constructor */
	public AbstractBan() {
	}

	/** full constructor */
	public AbstractBan(InvoiceFormat invoiceFormat, BanStatus banStatus,
			BanInactiveReason banInactiveReason,InvoiceChannel invoiceChannel, Currency currency,
			AccountType accountType, Vendor vendor, PaymentGroup paymentGroup,
			InvoiceStructure invoiceStructure, PaymentMethod paymentMethod,
			PaymentTerm paymentTerm, String accountNumber, String acna,
			String ccna, String cic, String icoid, String icsc, String occ,
			String lata, String stateLevelCc, String productType,
			String accountStructureInd, String bsaInd, String apSupplierNumber,
			String apSupplierName, String apSupplierSite,
			String apCustomerName, Integer banPrimiaryContactId,
			Integer banPaymentContactId, Integer banDisputeContactId,
			Integer banTariffContactId, Integer banBillingContactId,
			Date banCreateDate, Date banCloseDate, Integer usageBackbillMonths,
			Integer billDay, Date statusTimestamp, String invoiceFrequency,
			String billingCenter, String lineOfBusiness, String notes,String notice,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, String autopayFlag,Integer autopayCount,Integer autopayMaxCount ,String billTo,String taxRegistrationNumber, String banHistId,
			Integer histVendorId, Integer histBanId, Set receivedPayments,
			Set tariffLinks, Set inventories, Set credits, Set invoiceSts,
			Set userPreviledges, Set invoices,String level1ApprovalFlag,String missingEmailflag, String systemModifiedMIFFlag,Integer approver4Id,Integer daysToDue,String rejectNotes
			) {
		this.invoiceFormat = invoiceFormat;
		this.banStatus = banStatus;
		this.banInactiveReason = banInactiveReason;
		this.invoiceChannel = invoiceChannel;
		this.currency = currency;
		this.accountType = accountType;
		this.vendor = vendor;
		this.paymentGroup = paymentGroup;
		this.invoiceStructure = invoiceStructure;
		this.paymentMethod = paymentMethod;
		this.paymentTerm = paymentTerm;
		this.accountNumber = accountNumber!=null?accountNumber.trim():accountNumber;
		this.acna = acna;
		this.ccna = ccna;
		this.cic = cic;
		this.icoid = icoid;
		this.icsc = icsc;
		this.occ = occ;
		this.lata = lata;
		this.stateLevelCc = stateLevelCc;
		this.productType = productType;
		this.accountStructureInd = accountStructureInd;
		this.bsaInd = bsaInd;
		this.apSupplierNumber = apSupplierNumber;
		this.apSupplierName = apSupplierName;
		this.apSupplierSite = apSupplierSite;
		this.apCustomerName = apCustomerName;
		this.banPrimiaryContactId = banPrimiaryContactId;
		this.banPaymentContactId = banPaymentContactId;
		this.banDisputeContactId = banDisputeContactId;
		this.banTariffContactId = banTariffContactId;
		this.banBillingContactId = banBillingContactId;
		this.banCreateDate = banCreateDate;
		this.banCloseDate = banCloseDate;
		this.usageBackbillMonths = usageBackbillMonths;
		this.billDay = billDay;
		this.statusTimestamp = statusTimestamp;
		this.invoiceFrequency = invoiceFrequency;
		this.billingCenter = billingCenter;
		this.lineOfBusiness = lineOfBusiness;
		this.notes = notes;
		this.notice = notice;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.histBanId = histBanId;
		this.receivedPayments = receivedPayments;
		this.tariffLinks = tariffLinks;
		this.inventories = inventories;
		this.credits = credits;
		this.invoiceSts = invoiceSts; 
		this.userPreviledges = userPreviledges;
		this.invoices = invoices;
		this.level1ApprovalFlag= level1ApprovalFlag;
		this.missingEmailflag = missingEmailflag;
		this.systemModifiedMIFFlag = systemModifiedMIFFlag;
		this.approver4Id = approver4Id;
		this.daysToDue = daysToDue;
		this.autopayFlag = autopayFlag;
		this.autopayCount = autopayCount;
		this.autopayMaxCount = autopayMaxCount;
		this.billTo = billTo;
		this.taxRegistrationNumber = taxRegistrationNumber;
		this.rejectNotes = rejectNotes;
	}

	// Property accessors

	
	
	
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InvoiceFormat getInvoiceFormat() {
		return this.invoiceFormat;
	}

	public void setInvoiceFormat(InvoiceFormat invoiceFormat) {
		this.invoiceFormat = invoiceFormat;
	}

	public BanStatus getBanStatus() {
		return this.banStatus;
	}

	public void setBanStatus(BanStatus banStatus) {
		this.banStatus = banStatus;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public InvoiceChannel getInvoiceChannel() {
		return this.invoiceChannel;
	}

	public void setInvoiceChannel(InvoiceChannel invoiceChannel) {
		this.invoiceChannel = invoiceChannel;
	}

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public AccountType getAccountType() {
		return this.accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public PaymentGroup getPaymentGroup() {
		return this.paymentGroup;
	}

	public void setPaymentGroup(PaymentGroup paymentGroup) {
		this.paymentGroup = paymentGroup;
	}

	public InvoiceStructure getInvoiceStructure() {
		return this.invoiceStructure;
	}

	public void setInvoiceStructure(InvoiceStructure invoiceStructure) {
		this.invoiceStructure = invoiceStructure;
	}

	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentTerm getPaymentTerm() {
		return this.paymentTerm;
	}

	public void setPaymentTerm(PaymentTerm paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getAccountNumber() {
		return this.accountNumber!=null?this.accountNumber.trim():this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber!=null?accountNumber.trim():accountNumber;
	}

	public String getAcna() {
		return this.acna;
	}

	public void setAcna(String acna) {
		this.acna = acna;
	}

	public String getCcna() {
		return this.ccna;
	}

	public void setCcna(String ccna) {
		this.ccna = ccna;
	}

	public String getCic() {
		return this.cic;
	}

	public void setCic(String cic) {
		this.cic = cic;
	}

	public String getIcoid() {
		return this.icoid;
	}

	public void setIcoid(String icoid) {
		this.icoid = icoid;
	}

	public String getIcsc() {
		return this.icsc;
	}

	public void setIcsc(String icsc) {
		this.icsc = icsc;
	}

	public String getOcc() {
		return this.occ;
	}

	public void setOcc(String occ) {
		this.occ = occ;
	}

	public String getLata() {
		return this.lata;
	}

	public void setLata(String lata) {
		this.lata = lata;
	}

	public String getStateLevelCc() {
		return this.stateLevelCc;
	}

	public void setStateLevelCc(String stateLevelCc) {
		this.stateLevelCc = stateLevelCc;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAccountStructureInd() {
		return this.accountStructureInd;
	}

	public void setAccountStructureInd(String accountStructureInd) {
		this.accountStructureInd = accountStructureInd;
	}

	public String getBsaInd() {
		return this.bsaInd;
	}

	public void setBsaInd(String bsaInd) {
		this.bsaInd = bsaInd;
	}

	public String getApSupplierNumber() {
		return this.apSupplierNumber;
	}

	public void setApSupplierNumber(String apSupplierNumber) {
		this.apSupplierNumber = apSupplierNumber;
	}

	public String getApSupplierName() {
		return this.apSupplierName;
	}

	public void setApSupplierName(String apSupplierName) {
		this.apSupplierName = apSupplierName;
	}

	public String getApSupplierSite() {
		return this.apSupplierSite;
	}

	public void setApSupplierSite(String apSupplierSite) {
		this.apSupplierSite = apSupplierSite;
	}

	public String getApCustomerName() {
		return this.apCustomerName;
	}

	public void setApCustomerName(String apCustomerName) {
		this.apCustomerName = apCustomerName;
	}

	public Integer getBanPrimiaryContactId() {
		return this.banPrimiaryContactId;
	}

	public void setBanPrimiaryContactId(Integer banPrimiaryContactId) {
		this.banPrimiaryContactId = banPrimiaryContactId;
	}

	public Integer getBanPaymentContactId() {
		return this.banPaymentContactId;
	}

	public void setBanPaymentContactId(Integer banPaymentContactId) {
		this.banPaymentContactId = banPaymentContactId;
	}

	public Integer getBanDisputeContactId() {
		return this.banDisputeContactId;
	}

	public void setBanDisputeContactId(Integer banDisputeContactId) {
		this.banDisputeContactId = banDisputeContactId;
	}

	public Integer getBanTariffContactId() {
		return this.banTariffContactId;
	}

	public void setBanTariffContactId(Integer banTariffContactId) {
		this.banTariffContactId = banTariffContactId;
	}

	public Integer getBanBillingContactId() {
		return this.banBillingContactId;
	}

	public void setBanBillingContactId(Integer banBillingContactId) {
		this.banBillingContactId = banBillingContactId;
	}

	public Date getBanCreateDate() {
		return this.banCreateDate;
	}

	public void setBanCreateDate(Date banCreateDate) {
		this.banCreateDate = banCreateDate;
	}

	public Date getBanCloseDate() {
		return this.banCloseDate;
	}

	public void setBanCloseDate(Date banCloseDate) {
		this.banCloseDate = banCloseDate;
	}

	public Integer getUsageBackbillMonths() {
		return this.usageBackbillMonths;
	}

	public void setUsageBackbillMonths(Integer usageBackbillMonths) {
		this.usageBackbillMonths = usageBackbillMonths;
	}

	public Integer getBillDay() {
		return this.billDay;
	}

	public void setBillDay(Integer billDay) {
		this.billDay = billDay;
	}

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public String getInvoiceFrequency() {
		return this.invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getBillingCenter() {
		return this.billingCenter;
	}

	public void setBillingCenter(String billingCenter) {
		this.billingCenter = billingCenter;
	}

	public String getLineOfBusiness() {
		return this.lineOfBusiness;
	}

	public void setLineOfBusiness(String lineOfBusiness) {
		this.lineOfBusiness = lineOfBusiness;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
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

	public Integer getHistBanId() {
		return this.histBanId;
	}

	public void setHistBanId(Integer histBanId) {
		this.histBanId = histBanId;
	}

	public Set getReceivedPayments() {
		return this.receivedPayments;
	}

	public void setReceivedPayments(Set receivedPayments) {
		this.receivedPayments = receivedPayments;
	}

	public Set getTariffLinks() {
		return this.tariffLinks;
	}

	public void setTariffLinks(Set tariffLinks) {
		this.tariffLinks = tariffLinks;
	}

	public Set getInventories() {
		return this.inventories;
	}

	public void setInventories(Set inventories) {
		this.inventories = inventories;
	}

	public Set getCredits() {
		return this.credits;
	}

	public void setCredits(Set credits) {
		this.credits = credits;
	}

	public Set getInvoiceSts() {
		return this.invoiceSts;
	}

	public void setInvoiceSts(Set invoiceSts) {
		this.invoiceSts = invoiceSts;
	}
 

	public Set getUserPreviledges() {
		return this.userPreviledges;
	}

	public void setUserPreviledges(Set userPreviledges) {
		this.userPreviledges = userPreviledges;
	}

	public Set getInvoices() {
		return this.invoices;
	}

	public void setInvoices(Set invoices) {
		this.invoices = invoices;
	}

	/**
	 * @return the ccmVendorName
	 */
	public String getCcmVendorName() {
		return ccmVendorName;
	}

	/**
	 * @param ccmVendorName
	 *            the ccmVendorName to set
	 */
	public void setCcmVendorName(String ccmVendorName) {
		this.ccmVendorName = ccmVendorName;
	}

	/**
	 * @return the masterBanFlag
	 */
	public String getMasterBanFlag() {
		return masterBanFlag;
	}

	/**
	 * @param masterBanFlag
	 *            the masterBanFlag to set
	 */
	public void setMasterBanFlag(String masterBanFlag) {
		this.masterBanFlag = masterBanFlag;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractBan [");
		if (accountNumber != null)
			builder.append("accountNumber=").append(accountNumber).append(", ");
		if (accountStructureInd != null)
			builder.append("accountStructureInd=").append(accountStructureInd)
					.append(", ");
		if (accountType != null)
			builder.append("accountType=").append(accountType).append(", ");
		if (acna != null)
			builder.append("acna=").append(acna).append(", ");
		if (apCustomerName != null)
			builder.append("apCustomerName=").append(apCustomerName).append(
					", ");
		if (apSupplierName != null)
			builder.append("apSupplierName=").append(apSupplierName).append(
					", ");
		if (apSupplierNumber != null)
			builder.append("apSupplierNumber=").append(apSupplierNumber)
					.append(", ");
		if (apSupplierSite != null)
			builder.append("apSupplierSite=").append(apSupplierSite).append(
					", ");
		if (banBillingContactId != null)
			builder.append("banBillingContactId=").append(banBillingContactId)
					.append(", ");
		if (banCloseDate != null)
			builder.append("banCloseDate=").append(banCloseDate).append(", ");
		if (banCreateDate != null)
			builder.append("banCreateDate=").append(banCreateDate).append(", ");
		if (banDisputeContactId != null)
			builder.append("banDisputeContactId=").append(banDisputeContactId)
					.append(", ");
		if (banPaymentContactId != null)
			builder.append("banPaymentContactId=").append(banPaymentContactId)
					.append(", ");
		if (banPrimiaryContactId != null)
			builder.append("banPrimiaryContactId=")
					.append(banPrimiaryContactId).append(", ");
		if (banStatus != null)
			builder.append("banStatus=").append(banStatus).append(", ");
		if (banInactiveReason != null)
			builder.append("banInactiveReason=").append(banInactiveReason).append(", ");
		if (banTariffContactId != null)
			builder.append("banTariffContactId=").append(banTariffContactId)
					.append(", ");
		if (billDay != null)
			builder.append("billDay=").append(billDay).append(", ");
		if (billingCenter != null)
			builder.append("billingCenter=").append(billingCenter).append(", ");
		if (bsaInd != null)
			builder.append("bsaInd=").append(bsaInd).append(", ");
		if (ccmVendorName != null)
			builder.append("ccmVendorName=").append(ccmVendorName).append(", ");
		if (ccna != null)
			builder.append("ccna=").append(ccna).append(", ");
		if (cic != null)
			builder.append("cic=").append(cic).append(", ");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (currency != null)
			builder.append("currency=").append(currency).append(", ");
		if (histBanId != null)
			builder.append("histBanId=").append(histBanId).append(", ");
		if (icoid != null)
			builder.append("icoid=").append(icoid).append(", ");
		if (icsc != null)
			builder.append("icsc=").append(icsc).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (invoiceChannel != null)
			builder.append("invoiceChannel=").append(invoiceChannel).append(
					", ");
		if (invoiceFormat != null)
			builder.append("invoiceFormat=").append(invoiceFormat).append(", ");
		if (invoiceFrequency != null)
			builder.append("invoiceFrequency=").append(invoiceFrequency)
					.append(", ");
		if (invoiceStructure != null)
			builder.append("invoiceStructure=").append(invoiceStructure)
					.append(", ");
		if (lata != null)
			builder.append("lata=").append(lata).append(", ");
		if (lineOfBusiness != null)
			builder.append("lineOfBusiness=").append(lineOfBusiness).append(
					", ");
		if (masterBanFlag != null)
			builder.append("masterBanFlag=").append(masterBanFlag).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (notes != null)
			builder.append("notes=").append(notes).append(", ");
		if (notice != null)
			builder.append("notice=").append(notice).append(", ");
		if (occ != null)
			builder.append("occ=").append(occ).append(", ");
		if (paymentGroup != null)
			builder.append("paymentGroup=").append(paymentGroup).append(", ");
		if (paymentMethod != null)
			builder.append("paymentMethod=").append(paymentMethod).append(", ");
		if (paymentTerm != null)
			builder.append("paymentTerm=").append(paymentTerm).append(", ");
		if (processStatus != null)
			builder.append("processStatus=").append(processStatus).append(", ");
		if (productType != null)
			builder.append("productType=").append(productType).append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (stateLevelCc != null)
			builder.append("stateLevelCc=").append(stateLevelCc).append(", ");
		if (statusTimestamp != null)
			builder.append("statusTimestamp=").append(statusTimestamp).append(
					", ");
		if (usageBackbillMonths != null)
			builder.append("usageBackbillMonths=").append(usageBackbillMonths)
					.append(", ");
		if (vendor != null)
			builder.append("vendor=").append(vendor).append(", ");;
		if(level1ApprovalFlag != null){
			builder.append("level1ApprovalFlag=").append(level1ApprovalFlag).append(", ");
		}
		if (missingEmailflag != null)
			builder.append("missingEmailflag=").append(missingEmailflag).append(", ");
		if (systemModifiedMIFFlag != null)
			builder.append("systemModifiedMIFFlag=").append(systemModifiedMIFFlag).append(", ");
		if (approver4Id != null)
			builder.append("approver4Id=").append(approver4Id).append(", ");
		if (daysToDue != null)
			builder.append("daysToDue=").append(daysToDue);
		builder.append("]");
		return builder.toString();
	}
	/******************Add By Chao.Liu***************************/

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCreatedBanFlag() {
		return createdBanFlag;
	}

	public void setCreatedBanFlag(String createdBanFlag) {
		this.createdBanFlag = createdBanFlag;
	}

	public String getAnalyst() {
		return analyst;
	}

	public void setAnalyst(String analyst) {
		this.analyst = analyst;
	}

	public Integer getAnalystId() {
		return analystId;
	}

	public void setAnalystId(Integer analystId) {
		this.analystId = analystId;
	}

	public String getCurrentDueType() {
		return currentDueType;
	}

	public void setCurrentDueType(String currentDueType) {
		this.currentDueType = currentDueType;
	}

	public String getOriginalAccountNumber() {
		return originalAccountNumber;
	}

	public void setOriginalAccountNumber(String originalAccountNumber) {
		this.originalAccountNumber = originalAccountNumber;
	}

	public String getScoaCodeType() {
		return scoaCodeType;
	}

	public void setScoaCodeType(String scoaCodeType) {
		this.scoaCodeType = scoaCodeType;
	}

	public Integer getApprover1Id() {
		return approver1Id;
	}

	public void setApprover1Id(Integer approver1Id) {
		this.approver1Id = approver1Id;
	}

	public Integer getApprover2Id() {
		return approver2Id;
	}

	public void setApprover2Id(Integer approver2Id) {
		this.approver2Id = approver2Id;
	}

	public Integer getApprover3Id() {
		return approver3Id;
	}

	public void setApprover3Id(Integer approver3Id) {
		this.approver3Id = approver3Id;
	}

	public Set getUserPrivileges() {
		return userPrivileges;
	}

	public void setUserPrivileges(Set userPrivileges) {
		this.userPrivileges = userPrivileges;
	}

	public Set getBarCodes() {
		return barCodes;
	}

	public void setBarCodes(Set barCodes) {
		this.barCodes = barCodes;
	}

	public String getMibFlag() {
		return mibFlag;
	}

	public void setMibFlag(String mibFlag) {
		this.mibFlag = mibFlag;
	}
	
	public String getManualAdjustmentApprovalFlag() {
		return manualAdjustmentApprovalFlag;
	}

	public void setManualAdjustmentApprovalFlag(String manualAdjustmentApprovalFlag) {
		this.manualAdjustmentApprovalFlag = manualAdjustmentApprovalFlag;
	}
	
	
	
	/**
	 * @return the externalApproveFlag
	 */
	public String getExternalApproveFlag() {
		return externalApproveFlag;
	}

	/**
	 * @param externalApproveFlag the externalApproveFlag to set
	 */
	public void setExternalApproveFlag(String externalApproveFlag) {
		this.externalApproveFlag = externalApproveFlag;
	}

	public String getAutopayFlag() {
		return autopayFlag;
	}

	public void setAutopayFlag(String autopayFlag) {
		this.autopayFlag = autopayFlag;
	}
	
	public Integer getAutopayCount() {
		return autopayCount;
	}

	public void setAutopayCount(Integer autopayCount) {
		this.autopayCount = autopayCount;
	}

	public Integer getAutopayMaxCount() {
		return autopayMaxCount;
	}

	public void setAutopayMaxCount(Integer autopayMaxCount) {
		this.autopayMaxCount = autopayMaxCount;
	}

	public Integer getMaxApprovalLevel() {
		return maxApprovalLevel;
	}

	public void setMaxApprovalLevel(Integer maxApprovalLevel) {
		this.maxApprovalLevel = maxApprovalLevel;
	}

	public String getPaperAccountNumber() {
		return paperAccountNumber;
	}

	public void setPaperAccountNumber(String paperAccountNumber) {
		this.paperAccountNumber = paperAccountNumber;
	}

	public String getLevel1ApprovalFlag() {
		return level1ApprovalFlag;
	}

	public void setLevel1ApprovalFlag(String level1ApprovalFlag) {
		this.level1ApprovalFlag = level1ApprovalFlag;
	}

	public String getMissingEmailflag() {
		return missingEmailflag;
	}

	public void setMissingEmailflag(String missingEmailflag) {
		this.missingEmailflag = missingEmailflag;
	}

	public Integer getApprover4Id() {
		return approver4Id;
	}

	public void setApprover4Id(Integer approver4Id) {
		this.approver4Id = approver4Id;
	}

	public Integer getDaysToDue() {
		return daysToDue ;
	}

	public void setDaysToDue(Integer daysToDue) {
		this.daysToDue = daysToDue;
	}

	public BanInactiveReason getBanInactiveReason() {
		return banInactiveReason;
	}

	public void setBanInactiveReason(BanInactiveReason banInactiveReason) {
		this.banInactiveReason = banInactiveReason;
	}

	public String getBillTo() {
		return billTo;
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	public String getRejectNotes() {
		return rejectNotes;
	}

	public void setRejectNotes(String rejectNotes) {
		this.rejectNotes = rejectNotes;
	}

	public String getSystemModifiedMIFFlag() {
		return systemModifiedMIFFlag;
	}

	public void setSystemModifiedMIFFlag(String systemModifiedMIFFlag) {
		this.systemModifiedMIFFlag = systemModifiedMIFFlag;
	}

}