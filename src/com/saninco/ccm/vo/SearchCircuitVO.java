package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.saninco.ccm.util.SystemUtil;
/**
	 * create by donghao.guo 2011/09/22
     *
	 */
public class SearchCircuitVO extends SearchVO implements Serializable {

	
	private static final long serialVersionUID = -6421254342932367307L;
	private String vendorId;
	private String banId;
	private String lineOfbusiness;
	private String strippedCircuit;
	private String lineItemCode;
	private String fullScoaCode;
	private String company;
	private String location;
	private String departement;
	private String production;
	private String account;
	private String channel;
	private boolean historicalInvoice;
	private String itemType;
	// invoice date
	private String invoiceDateStartsOn;
	private String invoiceDateEndsOn;
	private String invoiceDateWiPastNumOfDays;
	// invoice start date
	private String invoicestartDateStartsOn;
	private String invoicestartDateEndsOn;
	private String invoicestartDateWiPastNumOfDays;
	// invoice end date
	private String invoiceendDateStartsOn;
	private String invoiceendDateEndsOn;
	private String invoiceendDateWiPastNumOfDays;
	private String invoiceendDateWiNextNumOfDays;
	// invoice Year Month 
	private String invoiceYearMonth;
	
	private String serviceType;
	private String circuitType;
	private String orderType;
	private String asrNumber;
	private String PON;
	private String endCustomer;
	
	// invoice date
	private String newInvoiceDateStartsOn;
	private String newInvoiceDateEndsOn;
	private String newInvoiceDateWiPastNumOfDays;
	
	
	// circuit detail use fields
	private String vendorCircuitId;
	
	//
	private String customerId;
	private String a_address;
	
	// Override
	public String getLimitCause() {
		if (getRecPerPage() == 0) {
			return "";
		}
		return " LIMIT " + getStartIndex() + "," + getRecPerPage();
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
	public String getLineOfbusiness() {
		return lineOfbusiness;
	}
	public void setLineOfbusiness(String lineOfbusiness) {
		this.lineOfbusiness = lineOfbusiness;
	}
	public String getStrippedCircuit() {
		return strippedCircuit;
	}
	public void setStrippedCircuit(String strippedCircuit) {
		this.strippedCircuit = strippedCircuit;
	}
	public String getLineItemCode() {
		return lineItemCode;
	}
	public void setLineItemCode(String lineItemCode) {
		this.lineItemCode = lineItemCode;
	}
	public String getFullScoaCode() {
		return fullScoaCode;
	}
	public void setFullScoaCode(String fullScoaCode) {
		this.fullScoaCode = fullScoaCode;
	}
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
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getProduction() {
		return production;
	}
	public void setProduction(String production) {
		this.production = production;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public boolean isHistoricalInvoice() {
		return historicalInvoice;
	}
	public void setHistoricalInvoice(boolean historicalInvoice) {
		this.historicalInvoice = historicalInvoice;
	}
	public String getInvoiceDateStartsOn() {
		return invoiceDateStartsOn;
	}
	public void setInvoiceDateStartsOn(String invoiceDateStartsOn) {
		this.invoiceDateStartsOn = invoiceDateStartsOn;
	}
	public String getInvoiceDateEndsOn() {
		return invoiceDateEndsOn;
	}
	public void setInvoiceDateEndsOn(String invoiceDateEndsOn) {
		this.invoiceDateEndsOn = invoiceDateEndsOn;
	}
	public String getInvoiceDateWiPastNumOfDays() {
		return invoiceDateWiPastNumOfDays;
	}
	public void setInvoiceDateWiPastNumOfDays(String invoiceDateWiPastNumOfDays) {
		this.invoiceDateWiPastNumOfDays = invoiceDateWiPastNumOfDays;
	}
	public String getInvoicestartDateStartsOn() {
		return invoicestartDateStartsOn;
	}
	public void setInvoicestartDateStartsOn(String invoicestartDateStartsOn) {
		this.invoicestartDateStartsOn = invoicestartDateStartsOn;
	}
	public String getInvoicestartDateEndsOn() {
		return invoicestartDateEndsOn;
	}
	public void setInvoicestartDateEndsOn(String invoicestartDateEndsOn) {
		this.invoicestartDateEndsOn = invoicestartDateEndsOn;
	}
	public String getInvoicestartDateWiPastNumOfDays() {
		return invoicestartDateWiPastNumOfDays;
	}
	public void setInvoicestartDateWiPastNumOfDays(
			String invoicestartDateWiPastNumOfDays) {
		this.invoicestartDateWiPastNumOfDays = invoicestartDateWiPastNumOfDays;
	}
	public String getInvoiceendDateStartsOn() {
		return invoiceendDateStartsOn;
	}
	public void setInvoiceendDateStartsOn(String invoiceendDateStartsOn) {
		this.invoiceendDateStartsOn = invoiceendDateStartsOn;
	}
	public String getInvoiceendDateEndsOn() {
		return invoiceendDateEndsOn;
	}
	public void setInvoiceendDateEndsOn(String invoiceendDateEndsOn) {
		this.invoiceendDateEndsOn = invoiceendDateEndsOn;
	}
	public String getInvoiceendDateWiPastNumOfDays() {
		return invoiceendDateWiPastNumOfDays;
	}
	public void setInvoiceendDateWiPastNumOfDays(
			String invoiceendDateWiPastNumOfDays) {
		this.invoiceendDateWiPastNumOfDays = invoiceendDateWiPastNumOfDays;
	}
	public String getInvoiceendDateWiNextNumOfDays() {
		return invoiceendDateWiNextNumOfDays;
	}
	public void setInvoiceendDateWiNextNumOfDays(
			String invoiceendDateWiNextNumOfDays) {
		this.invoiceendDateWiNextNumOfDays = invoiceendDateWiNextNumOfDays;
	}
	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String calCircuitDateStartsOn() {
		String r = null;
		Calendar cal=Calendar.getInstance();
		if (this.invoiceDateStartsOn != null){
			r = "str_to_date('"+ this.invoiceDateStartsOn +"','%Y/%m/%d')";
		}else {
			if (this.invoiceDateWiPastNumOfDays != null){
				cal.setTime(new Date());
				cal.add(Calendar.DATE, Integer.parseInt("-"+invoiceDateWiPastNumOfDays));
				Date date=cal.getTime();
				r = "between "+"str_to_date('"+ SystemUtil.parseString(date) +"','%Y/%m/%d')"+" and " + "str_to_date('"+ SystemUtil.parseString(new Date()) +"','%Y/%m/%d')";
			}
		}
		return r;
	}
	
	public String calInvoiceDateStartsOn() {
		String r = null;
		Calendar cal=Calendar.getInstance();
		if (this.newInvoiceDateStartsOn != null){
			r = "str_to_date('"+ this.newInvoiceDateStartsOn +"','%Y/%m/%d')";
		}else {
			if (this.newInvoiceDateWiPastNumOfDays != null){
				cal.setTime(new Date());
				cal.add(Calendar.DATE, Integer.parseInt("-"+newInvoiceDateWiPastNumOfDays));
				Date date=cal.getTime();
				r = "between "+"str_to_date('"+ SystemUtil.parseString(date) +"','%Y/%m/%d')"+" and " + "str_to_date('"+ SystemUtil.parseString(new Date()) +"','%Y/%m/%d')";
			}
		}
		return r;
	}
	
	public String calInvoiceDateEndsOn() {
		String r = null;
		if (this.newInvoiceDateEndsOn != null){
			r = " str_to_date('"+ this.newInvoiceDateEndsOn +"','%Y/%m/%d') ";
		}
		return r;
	}

	public String calCircuitDateEndsOn() {
		String r = null;
		if (this.invoiceDateEndsOn != null){
			r = " str_to_date('"+ this.invoiceDateEndsOn +"','%Y/%m/%d') ";
		}
		return r;
	}
	public String calCircuitStartDateStartsOn(){
		String r = null;
		Calendar cal=Calendar.getInstance();
		if(this.invoicestartDateStartsOn!=null){
			r = "str_to_date('"+ this.invoicestartDateStartsOn +"','%Y/%m/%d')";
		}else{
			if(this.invoicestartDateWiPastNumOfDays!=null){
				cal.setTime(new Date());
				cal.add(Calendar.DATE, Integer.parseInt("-"+invoicestartDateWiPastNumOfDays));
				Date date=cal.getTime();
				r = "between "+"str_to_date('"+ SystemUtil.parseString(date) +"','%Y/%m/%d')"+" and " + "str_to_date('"+ SystemUtil.parseString(new Date()) +"','%Y/%m/%d')";
			}
		}
		return r;
	}
	public String calCircuitStartDateEndsOn() {
		String r = null;
		if (this.invoicestartDateEndsOn != null){
			r = "str_to_date('"+ this.invoicestartDateEndsOn +"','%Y/%m/%d')";
		}
		return r;
	}
	public String calInvoiceEndDateStartsOn() {
		String r = null;
		Calendar cal=Calendar.getInstance();
		if (this.invoiceendDateStartsOn != null){
			r = "str_to_date('"+ this.invoiceendDateStartsOn +"','%Y/%m/%d')";
		}else if (this.invoiceendDateWiPastNumOfDays != null) {
			cal.setTime(new Date());
			cal.add(Calendar.DATE, Integer.parseInt("-"+invoiceendDateWiPastNumOfDays));
			Date date=cal.getTime();
			r = "between "+"str_to_date('"+ SystemUtil.parseString(date) +"','%Y/%m/%d')"+" and " + "str_to_date('"+ SystemUtil.parseString(new Date()) +"','%Y/%m/%d')";
		} else if (this.invoiceendDateWiNextNumOfDays != null) {
			r ="str_to_date('"+ SystemUtil.parseString(new Date()) +"','%Y/%m/%d')";
		}
		return r;
	}

	public String calInvoiceEndDateEndsOn() {
		String r = null;
		Calendar cal=Calendar.getInstance();
		if (this.invoiceendDateEndsOn != null){
			r = "str_to_date('"+ this.invoiceendDateEndsOn +"','%Y/%m/%d')";
		}else if (this.invoiceendDateWiNextNumOfDays != null) {
			cal.setTime(new Date());
			cal.add(Calendar.DATE, Integer.parseInt(invoiceendDateWiNextNumOfDays));
			Date date=cal.getTime();
			r = "str_to_date('"+ SystemUtil.parseString(date) +"','%Y/%m/%d')";
		}
		return r;
	}
	public String getInvoiceYearMonth() {
		return invoiceYearMonth;
	}
	public void setInvoiceYearMonth(String invoiceYearMonth) {
		this.invoiceYearMonth = invoiceYearMonth;
	}
	public String getVendorCircuitId() {
		return vendorCircuitId;
	}
	public void setVendorCircuitId(String vendorCircuitId) {
		this.vendorCircuitId = vendorCircuitId;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getCircuitType() {
		return circuitType;
	}
	public void setCircuitType(String circuitType) {
		this.circuitType = circuitType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getAsrNumber() {
		return asrNumber;
	}
	public void setAsrNumber(String asrNumber) {
		this.asrNumber = asrNumber;
	}
	public String getPON() {
		return PON;
	}
	public void setPON(String pon) {
		PON = pon;
	}
	public String getEndCustomer() {
		return endCustomer;
	}
	public void setEndCustomer(String endCustomer) {
		this.endCustomer = endCustomer;
	}

	public String getNewInvoiceDateStartsOn() {
		return newInvoiceDateStartsOn;
	}

	public void setNewInvoiceDateStartsOn(String newInvoiceDateStartsOn) {
		this.newInvoiceDateStartsOn = newInvoiceDateStartsOn;
	}

	public String getNewInvoiceDateEndsOn() {
		return newInvoiceDateEndsOn;
	}

	public void setNewInvoiceDateEndsOn(String newInvoiceDateEndsOn) {
		this.newInvoiceDateEndsOn = newInvoiceDateEndsOn;
	}

	public String getNewInvoiceDateWiPastNumOfDays() {
		return newInvoiceDateWiPastNumOfDays;
	}

	public void setNewInvoiceDateWiPastNumOfDays(
			String newInvoiceDateWiPastNumOfDays) {
		this.newInvoiceDateWiPastNumOfDays = newInvoiceDateWiPastNumOfDays;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getA_address() {
		return a_address;
	}

	public void setA_address(String aAddress) {
		a_address = aAddress;
	}



}
