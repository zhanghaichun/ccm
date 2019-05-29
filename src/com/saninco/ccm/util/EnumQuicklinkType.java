/**
 * 
 */
package com.saninco.ccm.util;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Joe.Yang
 * 
 * add DISPUTE,CODE_DISPUTE beijing 2010-4-16 Jian.Dong
 */
public class EnumQuicklinkType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -121195131794001368L;
	private String name;
	private String code;

	private static String CODE_INVOICE = "Invoice";
	private static String CODE_PAYMENT = "Payment";
	private static String CODE_DISPUTE = "Dispute";
	//Donghao.Guo Add
	private static String CODE_CIRCUIT = "Circuit";
	private static String CODE_OLD_CIRCUIT = "OldCircuit";
	private static String CODE_VENDOR_INVENTORY = "VendorInventory";
	private static String CODE_MASTER_INVENTORY = "MasterInventory";
	private static String CODE_CONTRACT_TARIFF_PRICELIST = "ContractTariffPriceList";
	private static String CODE_QUOTE_INVENTORY = "QuoteInventory";

	// xinyu.Liu
	private static String CODE_CREDIT = "Credit";
	// Chao.Liu Add
	private static String CODE_TICKET = "Ticket";
	// Chao.Liu Add
	private static String CODE_User = "User";
	//wei.su add
	private static String CODE_RECONCILE = "Reconcile";
	


	public static final EnumQuicklinkType INVOICE = new EnumQuicklinkType(CODE_INVOICE, "Invoice");
	public static final EnumQuicklinkType PAYMENT = new EnumQuicklinkType(CODE_PAYMENT, "Payment");
	public static final EnumQuicklinkType DISPUTE = new EnumQuicklinkType(CODE_DISPUTE, "Dispute");
	// Donghao.Guo Add
	public static final EnumQuicklinkType CIRCUIT = new EnumQuicklinkType(CODE_CIRCUIT, "Circuit");
	public static final EnumQuicklinkType OldCIRCUIT = new EnumQuicklinkType(CODE_OLD_CIRCUIT, "OldCircuit");
	public static final EnumQuicklinkType VENDOR_INVENTORY = new EnumQuicklinkType(CODE_VENDOR_INVENTORY, "VendorInventory");
	public static final EnumQuicklinkType MASTER_INVENTORY = new EnumQuicklinkType(CODE_MASTER_INVENTORY, "MasterInventory");
	public static final EnumQuicklinkType CONTRACT_TARIFF_PRICELIST = new EnumQuicklinkType(CODE_CONTRACT_TARIFF_PRICELIST, "ContractTariffPriceList");
	public static final EnumQuicklinkType QUOTE_INVENTORY = new EnumQuicklinkType(CODE_QUOTE_INVENTORY, "QuoteInventory");
	// xinyu.Liu
	public static final EnumQuicklinkType CREDIT = new EnumQuicklinkType(CODE_CREDIT, "Credit");
	// Chao.Liu Add
	public static final EnumQuicklinkType TICKET = new EnumQuicklinkType(CODE_TICKET, "Ticket");
	public static final EnumQuicklinkType User = new EnumQuicklinkType(CODE_User, "User");
	
	// wei.su Add
	public static final EnumQuicklinkType RECONCILE = new EnumQuicklinkType(CODE_RECONCILE, "Reconcile");
	
	private static final EnumQuicklinkType[] quicklinkFieldCodes = { INVOICE, PAYMENT, DISPUTE, TICKET, RECONCILE, User };


	public static final List QUICKLINKFIELDLIST = Collections.unmodifiableList(Arrays.asList(quicklinkFieldCodes));

	private EnumQuicklinkType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String toString() {
		return "code=" + code + "; name=" + name;
	}

	public static EnumUserSearchField valueOf(String code) {
		Iterator iter = QUICKLINKFIELDLIST.iterator();
		while (iter.hasNext()) {
			EnumUserSearchField userSearchFieldCode = (EnumUserSearchField) iter.next();
			if (code.equals(userSearchFieldCode.getCode())) {
				return userSearchFieldCode;
			}
		}
		return null;
	}

	public static EnumUserSearchField nameOf(String name) {
		Iterator iter = QUICKLINKFIELDLIST.iterator();
		while (iter.hasNext()) {
			EnumUserSearchField userSearchFieldCode = (EnumUserSearchField) iter.next();
			if (name.equals(userSearchFieldCode.getName())) {
				return userSearchFieldCode;
			}
		}
		return null;
	}

	private Object readResolve() throws ObjectStreamException {
		if (code.equalsIgnoreCase(CODE_INVOICE))
			return INVOICE;
		if (code.equalsIgnoreCase(CODE_PAYMENT))
			return PAYMENT;
		if (code.equalsIgnoreCase(CODE_DISPUTE))
			return DISPUTE;
		if (code.equalsIgnoreCase(CODE_CREDIT))
			return CREDIT;
		// xinyu.Liu
		if (code.equalsIgnoreCase(CODE_CREDIT))
			return CREDIT;
		// Chao.Liu Add
		if (code.equalsIgnoreCase(CODE_TICKET))
			return TICKET;
		if (code.equalsIgnoreCase(CODE_User))
			return User;
		
		//WEI.SU Add
		if (code.equalsIgnoreCase(CODE_RECONCILE))
			return RECONCILE;
		return null;
	}

}
