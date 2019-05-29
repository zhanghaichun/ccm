package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * Ban entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Ban extends AbstractBan implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Ban() {
	}

	/** full constructor */
	public Ban(InvoiceFormat invoiceFormat, BanStatus banStatus,
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
			Integer modifiedBy, String recActiveFlag,String autopayFlag,Integer autopayCount,Integer autopayMaxCount ,String billTo,String taxRegistrationNumber, String banHistId,
			Integer histVendorId, Integer histBanId, Set receivedPayments,
			Set tariffLinks, Set inventories, Set credits, Set invoiceSts,
			Set userPreviledges, Set invoices,String level1ApprovalFlag,String missingEmailflag,String systemModifiedMIFFlag ,Integer approver4Id,Integer daysToDue,String rejectNotes
			) {
		super(invoiceFormat, banStatus,banInactiveReason,invoiceChannel, currency, accountType,
				vendor, paymentGroup, invoiceStructure, paymentMethod,
				paymentTerm, accountNumber, acna, ccna, cic, icoid, icsc, occ,
				lata, stateLevelCc, productType, accountStructureInd, bsaInd,
				apSupplierNumber, apSupplierName, apSupplierSite,
				apCustomerName, banPrimiaryContactId, banPaymentContactId,
				banDisputeContactId, banTariffContactId, banBillingContactId,
				banCreateDate, banCloseDate, usageBackbillMonths, billDay,
				statusTimestamp, invoiceFrequency, billingCenter,
				lineOfBusiness, notes, notice, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag, autopayFlag, autopayCount, autopayMaxCount ,billTo, taxRegistrationNumber, banHistId,
				histVendorId, histBanId, receivedPayments, tariffLinks,
				inventories, credits, invoiceSts,
				userPreviledges, invoices,level1ApprovalFlag,missingEmailflag,systemModifiedMIFFlag,approver4Id,daysToDue,rejectNotes);
	}

}
