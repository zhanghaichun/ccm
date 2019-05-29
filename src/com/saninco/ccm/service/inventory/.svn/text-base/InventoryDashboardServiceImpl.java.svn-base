package com.saninco.ccm.service.inventory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IMasterInventoryDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelFileUtil;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInventoryDashboardVO;
import com.saninco.ccm.vo.UpdateMasterInventoryVO;

public class InventoryDashboardServiceImpl implements
		IInventoryDashboardService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IMasterInventoryDao masterInventoryDao;
	
	
	public String getInventoryChangeHistoryListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Master Inventory List Page No", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			//getDisputeActionRequestListPageNo
			count = masterInventoryDao.getInventoryChangeHistoryListPageNo(searchInventoryDashboardVO);
			sb.append(searchInventoryDashboardVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String getInventoryChangeHistoryList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> l = null;
		try {
			l = masterInventoryDao.getInventoryChangeHistoryList(searchInventoryDashboardVO);
			sb.append(getListJsonCompatibleByInventoryChangeHistory(l,searchInventoryDashboardVO));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getMasterInventoryListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog("search Master Inventory List Page No", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			//getDisputeActionRequestListPageNo
			count = masterInventoryDao.getMasterInventoryListPageNo(searchInventoryDashboardVO);
			sb.append(searchInventoryDashboardVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String searchMasterInventoryList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> l = null;
		try {
			l = masterInventoryDao.searchMasterInventoryList(searchInventoryDashboardVO);
			sb.append(getListJsonCompatibleByMasterInventory(l,searchInventoryDashboardVO));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * 通过正则表达式将字符串中的特殊字符去掉
	 *  例如： []$'* 等在前台会产生错误的特殊字符
	 */
	private String strippedSpecialCharacters(String paramString) {
		
		String destString = null;
		
		Pattern p = Pattern.compile("[\\*\\$'\\]\\[\\^%;\\:\\?\"]");
		
		Matcher m = p.matcher(paramString);
		
		destString = m.replaceAll("");
		
		return destString;
	}
	
	/**
	 * 将查询回来的 master_inventory 表的 历史修改记录 拼接成json 串
	 * @param list
	 * @param searchInventoryDashboardVO
	 * @return
	 * @throws Exception
	 */
	private String getListJsonCompatibleByInventoryChangeHistory(List<Map<String,Object>> list,SearchInventoryDashboardVO searchInventoryDashboardVO) throws Exception {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInventoryDashboardVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(searchInventoryDashboardVO.getStartIndex() + size);
			sb.append(" ,data:[");
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> ll = list.get(i);
				
				String userName = ll.get("user_name").toString();
				String historyModifiedTimestamp = (String)ll.get("history_modified_timestamp");
				String mid = ll.get("mid").toString();
				String vendorName = (String)ll.get("vendor_name");//Vendor Name
				String summaryVendorName = (String)ll.get("summary_vendor_name");//Summary Vendor Name
				String banName = (String)ll.get("ban_name");//BAN
				String invoiceNumber = (String)ll.get("invoice_number");//Invoice Number
				String lineOfBusiness = (String)ll.get("line_of_business");//Line of Business
				String invoiceDate = (String)ll.get("invoice_date");//Latest Invoice Date
				String strippedCircuitNumber = (String)ll.get("stripped_circuit_number");//Stripped Circuit Number
				String uniqueCircuitId = (String)ll.get("unique_circuit_id");//Unique Circuit ID
				String serviceId = (String)ll.get("service_id");//Service ID
				String serviceIdMrr = (String)ll.get("service_id_mrr");//Service ID MRR
				String revenueMatchDate = (String)ll.get("revenue_match_date");//Revenue Match Date
				String circuitStatus = (String)ll.get("circuit_status");//Circuit Status
				String serviceIdMatchStatus = (String)ll.get("service_id_match_status");//Service ID Match Status
//				String circuitType = (String)ll.get("circuit_type");//Circuit Type
				String accessType = (String)ll.get("access_type");//Access Type
				String installDate = (String)ll.get("installation_date");//Installation Date
				String firstInvoiceDate = (String)ll.get("first_invoice_date");//First Invoice Date
				String firstInvoiceNumber = (String)ll.get("first_invoice_number");//First Invoice Number
				String orderNumber = (String)ll.get("order_number");//Order Number
				String orderType = (String)ll.get("order_type");//Order Type
				String quoteNumber = (String)ll.get("quote_number");//Quote Number
				String disconnectionDate = (String)ll.get("disconnection_date");//Disconnection Date
				String validationSourceSystem = (String)ll.get("validation_source_system");//Validation Source System
				String costType = (String)ll.get("cost_type");//Cost Type
				String serviceDescription = (String)ll.get("service_description");//Service Description
				String productCategory = (String)ll.get("product_category");//Product Category
				String subProductCategory = (String)ll.get("sub_product_category");//Sub Product Category
				String project = (String)ll.get("project");//Project
				String projectCategoryStatus = (String)ll.get("project_category_status");//Project Category Status
				String aStreetNumber = (String)ll.get("a_street_number");//A Street Number
				String aStreetName = (String)ll.get("a_street_name");//A Street Name
				String aUnit = (String)ll.get("a_unit");//A Unit
				String aCity = (String)ll.get("a_city");//A City
				String aProvince = (String)ll.get("a_province");//A Province
				String aPostalCode = (String)ll.get("a_postal_code");//A Postal Code
				String aCountry = (String)ll.get("a_country");//A Country
				String zStreetNumber = (String)ll.get("z_street_number");//Z Street Number
				String zStreetName = (String)ll.get("z_street_name");//Z Street Name
				String zUnit = (String)ll.get("z_unit");//Z Unit
				String zCity = (String)ll.get("z_city");//Z City
				String zProvince = (String)ll.get("z_province");//Z Province
				String zPostalCode = (String)ll.get("z_postal_code");//Z Postal Code
				String zCountry = (String)ll.get("z_country");//Z Country
				String servingWireCentre = (String)ll.get("serving_wire_centre");//Serving Wire Centre
				String aggregatorCid = (String)ll.get("aggregator_cid");//Aggregator CID
				String timeSlotVlanAssignment = (String)ll.get("time_slot_vlan_assignment");//Time Slot or VLAN Assignment
				String comments = (String)ll.get("comments");//Comments
				String trunkGroupClli = (String)ll.get("trunk_group_clli");//Trunk Group CLLI
				String customerBillingAccount = (String)ll.get("customer_billing_account");//Customer Billing Account #
				String businessSegment = (String)ll.get("business_segment");//Business Segment
				String endUser = (String)ll.get("end_user");//End User
				String scoa = (String)ll.get("scoa");//SCOA
				String owner = (String)ll.get("owner");//Owner
				String ownerEmail = (String)ll.get("owner_email");//OWNER E-MAIL
				String lastSignoffDate = (String)ll.get("last_signoff_date");//Last Signoff Date
//				String mileage = (String)ll.get("mileage");//Mileage
				String usoc = (String)ll.get("usoc");//USOC
				String intercompanyBusinessUnit = (String)ll.get("intercompany_business_unit");//Intercompany Business Unit
				String intercompanyChannel = (String)ll.get("intercompany_channel");//Intercompany Channel
				
				Object str2 = "{id:"+ mid
						+",userName:\""+userName
						+"\",historyModifiedTimestamp:\""+historyModifiedTimestamp
						+"\",vendorName:\""+vendorName
						+"\",summaryVendorName:\""+summaryVendorName
						+"\",banName:\""+banName
						+"\",invoiceNumber:\""+invoiceNumber
						+"\",lineOfBusiness:\""+lineOfBusiness
						+"\",invoiceDate:\""+invoiceDate
						+"\",strippedCircuitNumber:\""+strippedCircuitNumber
						+"\",uniqueCircuitId:\""+uniqueCircuitId
						+"\",serviceId:\""+serviceId
						+"\",serviceIdMrr:\""+serviceIdMrr
						+"\",revenueMatchDate:\""+revenueMatchDate
						+"\",circuitStatus:\""+circuitStatus
						+"\",serviceIdMatchStatus:\""+serviceIdMatchStatus
						+"\",accessType:\""+accessType
						+"\",installDate:\""+installDate
						+"\",firstInvoiceDate:\""+firstInvoiceDate
						+"\",firstInvoiceNumber:\""+firstInvoiceNumber
						+"\",orderNumber:\""+orderNumber
						+"\",orderType:\""+orderType
						+"\",quoteNumber:\""+quoteNumber
						+"\",disconnectionDate:\""+disconnectionDate
						+"\",validationSourceSystem:\""+validationSourceSystem
						+"\",costType:\""+costType
						+"\",serviceDescription:\""+serviceDescription
						+"\",productCategory:\""+productCategory
						+"\",subProductCategory:\""+subProductCategory
						+"\",project:\""+project
						+"\",projectCategoryStatus:\""+projectCategoryStatus
						+"\",aStreetNumber:\""+strippedSpecialCharacters(aStreetNumber)
						+"\",aStreetName:\""+strippedSpecialCharacters(aStreetName)
						+"\",aUnit:\""+strippedSpecialCharacters(aUnit)
						+"\",aCity:\""+strippedSpecialCharacters(aCity)
						+"\",aProvince:\""+strippedSpecialCharacters(aProvince)
						+"\",aPostalCode:\""+strippedSpecialCharacters(aPostalCode)
						+"\",aCountry:\""+strippedSpecialCharacters(aCountry)
						+"\",zStreetNumber:\""+strippedSpecialCharacters(zStreetNumber)
						+"\",zStreetName:\""+strippedSpecialCharacters(zStreetName)
						+"\",zUnit:\""+strippedSpecialCharacters(zUnit)
						+"\",zCity:\""+strippedSpecialCharacters(zCity)
						+"\",zProvince:\""+strippedSpecialCharacters(zProvince)
						+"\",zPostalCode:\""+strippedSpecialCharacters(zPostalCode)
						+"\",zCountry:\""+strippedSpecialCharacters(zCountry)
						+"\",servingWireCentre:\""+servingWireCentre
						+"\",aggregatorCid:\""+aggregatorCid
						+"\",timeSlotVlanAssignment:\""+timeSlotVlanAssignment
						+"\",comments:\""+comments
						+"\",trunkGroupClli:\""+trunkGroupClli
						+"\",customerBillingAccount:\""+customerBillingAccount
						+"\",businessSegment:\""+businessSegment
						+"\",endUser:\""+endUser
						+"\",scoa:\""+scoa
						+"\",owner:\""+owner
						+"\",ownerEmail:\""+ownerEmail
						+"\",lastSignoffDate:\""+lastSignoffDate
						+"\",usoc:\""+usoc
						+"\",intercompanyBusinessUnit:\""+intercompanyBusinessUnit
						+"\",intercompanyChannel:\""+intercompanyChannel + "\"}";
				if (i != 0)
					sb.append(",");
				sb.append(str2.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}
	
	private String getListJsonCompatibleByMasterInventory(List<Map<String,Object>> list,SearchInventoryDashboardVO searchInventoryDashboardVO) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInventoryDashboardVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(searchInventoryDashboardVO.getStartIndex() + size);
			sb.append(" ,data:[");
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> ll = list.get(i);
				
				String mid = ll.get("mid").toString();
				String vendorName = CcmUtil.stringToSql((String)ll.get("vendor_name"));//Vendor Name
				String summaryVendorName = CcmUtil.stringToSql((String)ll.get("summary_vendor_name"));//Summary Vendor Name
				String banName = CcmUtil.stringToSql((String)ll.get("ban_name"));//BAN
				String invoiceNumber = CcmUtil.stringToSql((String)ll.get("invoice_number"));//Invoice Number
				String lineOfBusiness = CcmUtil.stringToSql((String)ll.get("line_of_business"));//Line of Business
				String invoiceDate = CcmUtil.stringToSql((String)ll.get("invoice_date"));//Latest Invoice Date
				String strippedCircuitNumber = CcmUtil.stringToSql((String)ll.get("stripped_circuit_number"));//Stripped Circuit Number
				String uniqueCircuitId = CcmUtil.stringToSql((String)ll.get("unique_circuit_id"));//Unique Circuit ID
				String serviceId = CcmUtil.stringToSql((String)ll.get("service_id"));//Service ID
				String serviceIdMrr = CcmUtil.stringToSql((String)ll.get("service_id_mrr"));//Service ID MRR
				String serviceIdMrrProvince = CcmUtil.stringToSql((String)ll.get("service_id_mrr_province"));//Service ID MRR
				String revenueMatchDate = CcmUtil.stringToSql((String)ll.get("revenue_match_date"));//Revenue Match Date
				String circuitStatus = CcmUtil.stringToSql((String)ll.get("circuit_status"));//Circuit Status
				String serviceIdMatchStatus = CcmUtil.stringToSql((String)ll.get("service_id_match_status"));//Service ID Match Status
//				String circuitType = CcmUtil.stringToSql((String)ll.get("circuit_type"));//Circuit Type
				String accessType = CcmUtil.stringToSql((String)ll.get("access_type"));//Access Type
				String installDate = CcmUtil.stringToSql((String)ll.get("installation_date"));//Installation Date
				String firstInvoiceDate = CcmUtil.stringToSql((String)ll.get("first_invoice_date"));//First Invoice Date
				String firstInvoiceNumber = CcmUtil.stringToSql((String)ll.get("first_invoice_number"));//First Invoice Number
				String orderNumber = CcmUtil.stringToSql((String)ll.get("order_number"));//Order Number
				String orderType = CcmUtil.stringToSql((String)ll.get("order_type"));//Order Type
				String quoteNumber = CcmUtil.stringToSql((String)ll.get("quote_number"));//Quote Number
				String disconnectionDate = CcmUtil.stringToSql((String)ll.get("disconnection_date"));//Disconnection Date
				String validationSourceSystem = CcmUtil.stringToSql((String)ll.get("validation_source_system"));//Validation Source System
				String costType = CcmUtil.stringToSql((String)ll.get("cost_type"));//Cost Type
				String serviceDescription = CcmUtil.stringToSql((String)ll.get("service_description"));//Service Description
				String productCategory = CcmUtil.stringToSql((String)ll.get("product_category"));//Product Category
				String subProductCategory = CcmUtil.stringToSql((String)ll.get("sub_product_category"));//Sub Product Category
				String project = CcmUtil.stringToSql((String)ll.get("project"));//Project
				String projectCategoryStatus = CcmUtil.stringToSql((String)ll.get("project_category_status"));//Project Category Status
				String aStreetNumber = CcmUtil.stringToSql((String)ll.get("a_street_number"));//A Street Number
				String aStreetName = CcmUtil.stringToSql((String)ll.get("a_street_name"));//A Street Name
				String aUnit = CcmUtil.stringToSql((String)ll.get("a_unit"));//A Unit
				String aCity = CcmUtil.stringToSql((String)ll.get("a_city"));//A City
				String aProvince = CcmUtil.stringToSql((String)ll.get("a_province"));//A Province
				String aPostalCode = CcmUtil.stringToSql((String)ll.get("a_postal_code"));//A Postal Code
				String aCountry = CcmUtil.stringToSql((String)ll.get("a_country"));//A Country
				String zStreetNumber = CcmUtil.stringToSql((String)ll.get("z_street_number"));//Z Street Number
				String zStreetName = CcmUtil.stringToSql((String)ll.get("z_street_name"));//Z Street Name
				String zUnit = CcmUtil.stringToSql((String)ll.get("z_unit"));//Z Unit
				String zCity = CcmUtil.stringToSql((String)ll.get("z_city"));//Z City
				String zProvince = CcmUtil.stringToSql((String)ll.get("z_province"));//Z Province
				String zPostalCode = CcmUtil.stringToSql((String)ll.get("z_postal_code"));//Z Postal Code
				String zCountry = CcmUtil.stringToSql((String)ll.get("z_country"));//Z Country
				String region = CcmUtil.stringToSql((String)ll.get("region"));//Z Country
				String servingWireCentre = CcmUtil.stringToSql((String)ll.get("serving_wire_centre"));//Serving Wire Centre
				String aggregatorCid = CcmUtil.stringToSql((String)ll.get("aggregator_cid"));//Aggregator CID
				String timeSlotVlanAssignment = CcmUtil.stringToSql((String)ll.get("time_slot_vlan_assignment"));//Time Slot or VLAN Assignment
				String comments = CcmUtil.stringToSql((String)ll.get("comments"));//Comments
				String trunkGroupClli = CcmUtil.stringToSql((String)ll.get("trunk_group_clli"));//Trunk Group CLLI
				String customerBillingAccount = CcmUtil.stringToSql((String)ll.get("customer_billing_account"));//Customer Billing Account #
				String businessSegment = CcmUtil.stringToSql((String)ll.get("business_segment"));//Business Segment
				String endUser = CcmUtil.stringToSql((String)ll.get("end_user"));//End User
				String scoa = CcmUtil.stringToSql((String)ll.get("scoa"));//SCOA
				String owner = CcmUtil.stringToSql((String)ll.get("owner"));//Owner
				String ownerEmail = CcmUtil.stringToSql((String)ll.get("owner_email"));//OWNER E-MAIL
				String lastSignoffDate = CcmUtil.stringToSql((String)ll.get("last_signoff_date"));//Last Signoff Date
//				String mileage = CcmUtil.stringToSql((String)ll.get("mileage"));//Mileage
				String multiplier = CcmUtil.stringToSql((String)ll.get("multiplier"));//multiplier
				String usoc = CcmUtil.stringToSql((String)ll.get("usoc"));//USOC
				String rate = CcmUtil.stringToSql((String)ll.get("rate"));//Rate
				String rateEffectiveDate = CcmUtil.stringToSql((String)ll.get("rate_effective_date"));//RATE EFFECTIVE DATE
				String contractNumber = CcmUtil.stringToSql((String)ll.get("contract_number"));//Contract
				String tariffName = CcmUtil.stringToSql((String)ll.get("tariff_name"));// Tariff
				String tariffPage = CcmUtil.stringToSql((String)ll.get("tariff_page"));// Tariff
				String circuitTerm = CcmUtil.stringToSql((String)ll.get("circuit_term"));//CIRCUIT TERM
				String expiryDate = CcmUtil.stringToSql((String)ll.get("expiry_date"));//Expiry Date
				String rateStatus = CcmUtil.stringToSql((String)ll.get("rate_status"));//Rate Status
				String agreementType = CcmUtil.stringToSql((String)ll.get("agreement_type"));//RAGREEMENT TYPE
				String intercompanyBusinessUnit = CcmUtil.stringToSql((String)ll.get("intercompany_business_unit"));//Intercompany Business Unit
				String intercompanyChannel = CcmUtil.stringToSql((String)ll.get("intercompany_channel"));//Intercompany Channel
				String fsaCode = CcmUtil.stringToSql((String)ll.get("fsa_code"));//Intercompany Channel
				String serviceabilityFibre = CcmUtil.stringToSql((String)ll.get("serviceability_fibre"));//Intercompany Channel
				String serviceabilityCable = CcmUtil.stringToSql((String)ll.get("serviceability_cable"));//Intercompany Channel
				String modifiedTimestamp = CcmUtil.stringToSql((String)ll.get("modified_timestamp"));//Modified Date
				String modifiedUser = CcmUtil.stringToSql((String)ll.get("modified_user"));//Modified User
				String rateDiscrepancyFlag = CcmUtil.stringToSql((String)ll.get("rate_discrepancy_flag"));//RATE DISCREPANCY
				String terminationPenaltyPercentage = CcmUtil.stringToSql((String)ll.get("termination_penalty_percentage"));//TERMINATION PENALTY
				
				
				
				
				

				Object str2 = "{id:"+ ll.get("mid")
//						+",vendorName:\""+vendorName
//						+"\",summaryVendorName:\""+summaryVendorName
						+",vendorName:\""+vendorName
						+"\",summaryVendorName:\""+summaryVendorName
						+"\",banName:\""+banName
						+"\",invoiceNumber:\""+invoiceNumber
						+"\",lineOfBusiness:\""+lineOfBusiness
						+"\",invoiceDate:\""+invoiceDate
						+"\",strippedCircuitNumber:\""+strippedCircuitNumber
						+"\",uniqueCircuitId:\""+uniqueCircuitId
						+"\",serviceId:\""+serviceId
						+"\",serviceIdMrr:\""+serviceIdMrr
						+"\",serviceIdMrrProvince:\""+serviceIdMrrProvince
						+"\",revenueMatchDate:\""+revenueMatchDate
						+"\",circuitStatus:\""+circuitStatus
						+"\",serviceIdMatchStatus:\""+serviceIdMatchStatus
						+"\",accessType:\""+accessType
						+"\",installDate:\""+installDate
						+"\",firstInvoiceDate:\""+firstInvoiceDate
						+"\",firstInvoiceNumber:\""+firstInvoiceNumber
						+"\",orderNumber:\""+orderNumber
						+"\",orderType:\""+orderType
						+"\",quoteNumber:\""+quoteNumber
						+"\",disconnectionDate:\""+disconnectionDate
						+"\",validationSourceSystem:\""+validationSourceSystem
						+"\",costType:\""+costType
						+"\",serviceDescription:\""+serviceDescription
						+"\",productCategory:\""+productCategory
						+"\",subProductCategory:\""+subProductCategory
						+"\",project:\""+project
						+"\",projectCategoryStatus:\""+projectCategoryStatus
						+"\",aStreetNumber:\""+aStreetNumber
						+"\",aStreetName:\""+aStreetName
						+"\",aUnit:\""+aUnit
						+"\",aCity:\""+aCity
						+"\",aProvince:\""+aProvince
						+"\",aPostalCode:\""+aPostalCode
						+"\",aCountry:\""+aCountry
						+"\",zStreetNumber:\""+zStreetNumber
						+"\",zStreetName:\""+zStreetName
						+"\",zUnit:\""+zUnit
						+"\",zCity:\""+zCity
						+"\",zProvince:\""+zProvince
						+"\",zPostalCode:\""+zPostalCode
						+"\",zCountry:\""+zCountry
						+"\",region:\""+region
						+"\",tariffPage:\""+tariffPage
						+"\",servingWireCentre:\""+servingWireCentre
						+"\",aggregatorCid:\""+aggregatorCid
						+"\",timeSlotVlanAssignment:\""+timeSlotVlanAssignment
						+"\",comments:\""+comments
						+"\",trunkGroupClli:\""+trunkGroupClli
						+"\",customerBillingAccount:\""+customerBillingAccount
						+"\",businessSegment:\""+businessSegment
						+"\",endUser:\""+endUser
						+"\",scoa:\""+scoa
						+"\",owner:\""+owner
						+"\",ownerEmail:\""+ownerEmail
						+"\",lastSignoffDate:\""+lastSignoffDate
						+"\",multiplier:\""+multiplier
						+"\",usoc:\""+usoc
						+"\",rate:\""+rate
						+"\",rateEffectiveDate:\""+rateEffectiveDate
						+"\",contractNumber:\""+contractNumber
						+"\",tariffName:\""+tariffName
						+"\",tariffPage:\""+tariffPage
						+"\",circuitTerm:\""+circuitTerm
						+"\",expiryDate:\""+expiryDate
						+"\",rateStatus:\""+rateStatus
						+"\",agreementType:\""+agreementType
						+"\",intercompanyBusinessUnit:\""+intercompanyBusinessUnit
						+"\",intercompanyChannel:\""+intercompanyChannel
						+"\",fsaCode:\""+fsaCode
						+"\",serviceabilityFibre:\""+serviceabilityFibre
						+"\",serviceabilityCable:\""+serviceabilityCable
						+"\",modifiedTimestamp:\""+modifiedTimestamp
						+"\",modifiedUser:\""+modifiedUser
						+"\",rateDiscrepancyFlag:\""+rateDiscrepancyFlag
						+"\",terminationPenaltyPercentage:\""+terminationPenaltyPercentage +"\"}";
//						+"\",modifiedUser:\""+modifiedUser +"\"}";
				
				if (i != 0)
					sb.append(",");
				sb.append(str2.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public List<MapEntryVO<String, String>> searchMasterInventoryComplete()
			throws BPLException {
		logger.info("Enter method searchMasterInventoryComplete.");
		List<MapEntryVO<String, String>> l = null;
		List completeList = null;
		try {
			completeList = masterInventoryDao.searchMasterInventoryComplete();
			l = new ArrayList<MapEntryVO<String, String>>(completeList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object c : completeList){
            Object[] complete = (Object[])c;
            MapEntryVO<String, String> m = new MapEntryVO<String, String>(complete[0].toString(), complete[1].toString());
            l.add(m);
        }
		logger.info("Exit method searchMasterInventoryComplete.");
		return l;
	}
	
	
	/**
	 * Get Master inventory cost type list
	 */
	public List<MapEntryVO<String, String>> searchMasterInventoryCostType()
			throws BPLException {
		
		logger.info("Enter method searchMasterInventoryCostType.");
		
		List<MapEntryVO<String, String>> costTypeMapEntryList = null;
		List costTypeList = null;
		
		// try ... catch
		try {
			
			costTypeList = masterInventoryDao.searchMasterInventoryCostType();
			
			costTypeMapEntryList = new ArrayList<MapEntryVO<String, String>>(costTypeList.size());
			
		} catch (Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		// Iterate cost type list.
        for(Object c : costTypeList){
        	
            String costType = (String)c;
            
            if( !costType.equals("ALL")  ){

            	MapEntryVO<String, String> m = new MapEntryVO<String, String>( costType, costType );
                
                costTypeMapEntryList.add(m);
            	
        	}	
            
            
            
        }
        
		logger.info("Exit method searchMasterInventoryCostType.");
		
		return costTypeMapEntryList;
	}
	
	@Override
	public String searchMasterInventoryCompleteByFilter(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		logger.info("Enter method searchMasterInventoryComplete.");
		List<Map<String, Object>> completeList = null;
		StringBuffer sb = new StringBuffer();
		try {
			completeList = masterInventoryDao.searchMasterInventoryCompleteByFilter(searchInventoryDashboardVO);
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
        sb.append(this.getListJsonMasterInventoryComplete(completeList));
        
		logger.info("Exit method searchMasterInventoryComplete.");
		return sb.toString();
	}
	
	
	private String getListJsonMasterInventoryComplete(List<Map<String, Object>> list) throws BPLException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
		if (list != null && list.size() > 0) {
			sb.append("{\"data\":[");
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> ll = list.get(i);
				String completeFlag = (String)ll.get("complete_flag");
				String completeCount = ll.get("complete_count").toString();
				
				Object str2 = "{\"completeFlag\":\"" + completeFlag + "\",\"completeCount\":\""+completeCount + "\"}";
				
				if (i != 0)
					sb.append(",");
				sb.append(str2.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}
	
	
	public String createInventoryChangeHistoryToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = masterInventoryDao.getInventoryChangeHistoryDataForExcelPageNo(sdo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Master Inventory");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				sdo.setPageNo(1);
				sdo.setRecPerPage((int) count);
				list = masterInventoryDao.getInventoryChangeHistoryDataForExcel(sdo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					sdo.setPageNo(j + 1);
					sdo.setRecPerPage(recPerPage);
					list = masterInventoryDao.getInventoryChangeHistoryDataForExcel(sdo);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	
	public String createMasterInventoryToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
//			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 20000;
			count = masterInventoryDao.getMasterInventoryDataForExcelPageNo(sdo);
//			jExcelUtil.createWritableWorkbook(excelDirPath);
//			jExcelUtil.createWritableSheet(0, "Master Inventory");
//			jExcelUtil.writeTitle(0, titles);
			JExcelFileUtil jUtil = new JExcelFileUtil();
			jUtil.createExcelFile(excelDirPath, titles,"Master Inventory");
			if (count <= recPerPage) {
				sdo.setPageNo(1);
				sdo.setRecPerPage((int) count);
				list = masterInventoryDao.getMasterInventoryDataForExcel(sdo);
				for (int i = 0; i < list.size(); i++) {
					jUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					sdo.setPageNo(j + 1);
					sdo.setRecPerPage(recPerPage);
					list = masterInventoryDao.getMasterInventoryDataForExcel(sdo);
					for (int i = 0; i < list.size(); i++) {
						jUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jUtil.write();
//			jExcelUtil.setColumnView(new int[] { 30, 30, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60});
//			jExcelUtil.write();
//			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	
	public String getMasterInventoryRateListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog("search Master Inventory Rate List Page No", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			//getDisputeActionRequestListPageNo
			count = masterInventoryDao.getMasterInventoryRateListPageNo(searchInventoryDashboardVO);
			sb.append(searchInventoryDashboardVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String searchMasterInventoryRateList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> l = null;
		try {
			l = masterInventoryDao.searchMasterInventoryRateList(searchInventoryDashboardVO);
			sb.append(getListJsonCompatibleByMasterInventoryRate(l,searchInventoryDashboardVO));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	@Override
	public List<MapEntryVO<String, String>> searchMasterInventoryRateComplete()
			throws BPLException {
		logger.info("Enter method searchMasterInventoryRateComplete.");
		List<MapEntryVO<String, String>> l = null;
		List completeList = null;
		try {
			completeList = masterInventoryDao.searchMasterInventoryRateComplete();
			l = new ArrayList<MapEntryVO<String, String>>(completeList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object c : completeList){
            Object[] complete = (Object[])c;
            MapEntryVO<String, String> m = new MapEntryVO<String, String>(complete[0].toString(), complete[1].toString());
            l.add(m);
        }
		logger.info("Exit method searchMasterInventoryComplete.");
		return l;
	}
	
	private String getListJsonCompatibleByMasterInventoryRate(List<Map<String,Object>> list,SearchInventoryDashboardVO searchInventoryDashboardVO) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInventoryDashboardVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(searchInventoryDashboardVO.getStartIndex() + size);
			sb.append(" ,data:[");
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> ll = list.get(i);
				
				String mid = ll.get("mid").toString();
				String banName = CcmUtil.stringToSql((String)ll.get("ban_name"));//BAN
				String vendorName = CcmUtil.stringToSql((String)ll.get("vendor_name"));//Vendor Name
				String summaryVendorName = CcmUtil.stringToSql((String)ll.get("summary_vendor_name"));//Summary Vendor Name
				String invoiceNumber = CcmUtil.stringToSql((String)ll.get("invoice_number"));//Invoice Number
				String lineOfBusiness = CcmUtil.stringToSql((String)ll.get("line_of_business"));//Line of Business
				String strippedCircuitNumber = CcmUtil.stringToSql((String)ll.get("stripped_circuit_number"));//Stripped Circuit Number
				String serviceId = CcmUtil.stringToSql((String)ll.get("service_id"));//Service ID
				String validationSourceSystem = CcmUtil.stringToSql((String)ll.get("validation_source_system"));//Validation Source System
				String circuitStatus = CcmUtil.stringToSql((String)ll.get("circuit_status"));//Circuit Status
				String installDate = CcmUtil.stringToSql((String)ll.get("installation_date"));//Installation Date
				String firstInvoiceDate = CcmUtil.stringToSql((String)ll.get("first_invoice_date"));//First Invoice Date
				String firstInvoiceNumber = CcmUtil.stringToSql((String)ll.get("first_invoice_number"));//First Invoice Number
				String disconnectionDate = CcmUtil.stringToSql((String)ll.get("disconnection_date"));//Disconnection Date
				String serviceDescription = CcmUtil.stringToSql((String)ll.get("service_description"));//Service Description
				String accessType = CcmUtil.stringToSql((String)ll.get("access_type"));//Access Type
				String productCategory = CcmUtil.stringToSql((String)ll.get("product_category"));//Product Category
				String subProductCategory = CcmUtil.stringToSql((String)ll.get("sub_product_category"));//Sub Product Category
				String project = CcmUtil.stringToSql((String)ll.get("project"));//Project
				String projectCategoryStatus = CcmUtil.stringToSql((String)ll.get("project_category_status"));//Project Category Status
				String aStreetNumber = CcmUtil.stringToSql((String)ll.get("a_street_number"));//A Street Number
				String aStreetName = CcmUtil.stringToSql((String)ll.get("a_street_name"));//A Street Name
				String aUnit = CcmUtil.stringToSql((String)ll.get("a_unit"));//A Unit
				String aCity = CcmUtil.stringToSql((String)ll.get("a_city"));//A City
				String aProvince = CcmUtil.stringToSql((String)ll.get("a_province"));//A Province
				String aPostalCode = CcmUtil.stringToSql((String)ll.get("a_postal_code"));//A Postal Code
				String aggregatorCid = CcmUtil.stringToSql((String)ll.get("aggregator_cid"));//Aggregator CID
				String customerBillingAccount = CcmUtil.stringToSql((String)ll.get("customer_billing_account"));//Customer Billing Account #
				String businessSegment = CcmUtil.stringToSql((String)ll.get("business_segment"));//Business Segment
				String endUser = CcmUtil.stringToSql((String)ll.get("end_user"));//End User
				String scoa = CcmUtil.stringToSql((String)ll.get("scoa"));//SCOA
				String owner = CcmUtil.stringToSql((String)ll.get("owner"));//Owner
				String intercompanyBusinessUnit = CcmUtil.stringToSql((String)ll.get("intercompany_business_unit"));//Intercompany Business Unit
				String intercompanyChannel = CcmUtil.stringToSql((String)ll.get("intercompany_channel"));//Intercompany Channel
				String usoc = CcmUtil.stringToSql((String)ll.get("usoc"));//USOC
				String quantity = CcmUtil.stringToSql((String)ll.get("quantity"));//quantity
				String billingRate = CcmUtil.stringToSql((String)ll.get("billing_rate"));//billingRate
				String itemAmount = CcmUtil.stringToSql((String)ll.get("item_amount"));//itemAmount
				String agreementType = CcmUtil.stringToSql((String)ll.get("agreement_type"));//RAGREEMENT TYPE
				String contract = CcmUtil.stringToSql((String)ll.get("contract"));//Contract
				String contractTerm = CcmUtil.stringToSql((String)ll.get("contract_term"));
				String terminationFee = CcmUtil.stringToSql((String)ll.get("termination_fee"));
				String tariff = CcmUtil.stringToSql((String)ll.get("tariff"));// Tariff
				String tariffPage = CcmUtil.stringToSql((String)ll.get("tariff_page"));// Tariff
				String rate = CcmUtil.stringToSql((String)ll.get("rate"));//Rate
				String rateEffectiveDate = CcmUtil.stringToSql((String)ll.get("rate_effective_date"));//RATE EFFECTIVE DATE
				String baseAmount = CcmUtil.stringToSql((String)ll.get("base_amount"));
				String rateMultiplier = CcmUtil.stringToSql((String)ll.get("rate_multiplier"));
				String discount = CcmUtil.stringToSql((String)ll.get("discount"));
				String rateStatus = CcmUtil.stringToSql((String)ll.get("rate_status"));//Rate Status
				String rateDiscrepancyFlag = CcmUtil.stringToSql((String)ll.get("rate_discrepancy_flag"));//RATE DISCREPANCY
				String expiryDate = CcmUtil.stringToSql((String)ll.get("expiry_date"));//Expiry Date
				
				

				Object str2 = "{id:"+ ll.get("mid")
//						+",vendorName:\""+vendorName
//						+"\",summaryVendorName:\""+summaryVendorName
						+",vendorName:\""+vendorName
						+"\",summaryVendorName:\""+summaryVendorName
						+"\",banName:\""+banName
						+"\",invoiceNumber:\""+invoiceNumber
						+"\",lineOfBusiness:\""+lineOfBusiness
						+"\",strippedCircuitNumber:\""+strippedCircuitNumber
						+"\",serviceId:\""+serviceId
						+"\",circuitStatus:\""+circuitStatus
						+"\",accessType:\""+accessType
						+"\",installDate:\""+installDate
						+"\",firstInvoiceDate:\""+firstInvoiceDate
						+"\",firstInvoiceNumber:\""+firstInvoiceNumber
						+"\",disconnectionDate:\""+disconnectionDate
						+"\",validationSourceSystem:\""+validationSourceSystem
						+"\",serviceDescription:\""+serviceDescription
						+"\",productCategory:\""+productCategory
						+"\",subProductCategory:\""+subProductCategory
						+"\",project:\""+project
						+"\",projectCategoryStatus:\""+projectCategoryStatus
						+"\",aStreetNumber:\""+aStreetNumber
						+"\",aStreetName:\""+aStreetName
						+"\",aUnit:\""+aUnit
						+"\",aCity:\""+aCity
						+"\",aProvince:\""+aProvince
						+"\",aPostalCode:\""+aPostalCode
						+"\",aggregatorCid:\""+aggregatorCid
						+"\",customerBillingAccount:\""+customerBillingAccount
						+"\",businessSegment:\""+businessSegment
						+"\",endUser:\""+endUser
						+"\",scoa:\""+scoa
						+"\",owner:\""+owner
						+"\",usoc:\""+usoc
						+"\",quantity:\""+quantity
						+"\",billingRate:\""+billingRate
						+"\",itemAmount:\""+itemAmount
						+"\",rate:\""+rate
						+"\",rateEffectiveDate:\""+rateEffectiveDate
						+"\",tariffPage:\""+tariffPage
						+"\",expiryDate:\""+expiryDate
						+"\",rateStatus:\""+rateStatus
						+"\",agreementType:\""+agreementType
						+"\",contract:\""+contract
						+"\",contractTerm:\""+contractTerm
						+"\",terminationFee:\""+terminationFee
						+"\",tariff:\""+tariff
						+"\",intercompanyBusinessUnit:\""+intercompanyBusinessUnit
						+"\",intercompanyChannel:\""+intercompanyChannel
						+"\",baseAmount:\""+baseAmount
						+"\",rateMultiplier:\""+rateMultiplier
						+"\",discount:\""+discount
						+"\",rateDiscrepancyFlag:\""+rateDiscrepancyFlag +"\"}";
//						+"\",modifiedUser:\""+modifiedUser +"\"}";
				
				if (i != 0)
					sb.append(",");
				sb.append(str2.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}
	
	public String createMasterInventoryRateToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			long count = 0;
			int recPerPage = 2000;
			count = masterInventoryDao.getMasterInventoryRateDataForExcelPageNo(sdo);
			JExcelFileUtil jUtil = new JExcelFileUtil();
			jUtil.createExcelFile(excelDirPath, titles,"Master Inventory");
			if (count <= recPerPage) {
				sdo.setPageNo(1);
				sdo.setRecPerPage((int) count);
				list = masterInventoryDao.getMasterInventoryRateDataForExcel(sdo);
				for (int i = 0; i < list.size(); i++) {
					jUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					sdo.setPageNo(j + 1);
					sdo.setRecPerPage(recPerPage);
					list = masterInventoryDao.getMasterInventoryRateDataForExcel(sdo);
					for (int i = 0; i < list.size(); i++) {
						jUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jUtil.write();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	public String getInventoryRateChangeHistoryListPageNo(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Master Inventory List Page No", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = masterInventoryDao.getInventoryRateChangeHistoryListPageNo(searchInventoryDashboardVO);
			sb.append(searchInventoryDashboardVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String getInventoryRateChangeHistoryList(SearchInventoryDashboardVO searchInventoryDashboardVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Action Request List", searchInventoryDashboardVO));
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> l = null;
		try {
			l = masterInventoryDao.getInventoryRateChangeHistoryList(searchInventoryDashboardVO);
			sb.append(getListJsonCompatibleByInventoryRateChangeHistory(l,searchInventoryDashboardVO));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public IMasterInventoryDao getMasterInventoryDao() {
		return masterInventoryDao;
	}

	public void setMasterInventoryDao(IMasterInventoryDao masterInventoryDao) {
		this.masterInventoryDao = masterInventoryDao;
	}
	
	
	public void updateMasterInventory(UpdateMasterInventoryVO mivo) throws BPLException {
		logger.info("enter updateMasterInventory");
		try {
			masterInventoryDao.updateMasterInventory(mivo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	public String createInventoryRateChangeHistoryToExcel(SearchInventoryDashboardVO sdo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = masterInventoryDao.getInventoryRateChangeHistoryDataForExcelPageNo(sdo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Master Inventory Rate");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				sdo.setPageNo(1);
				sdo.setRecPerPage((int) count);
				list = masterInventoryDao.getInventoryRateChangeHistoryDataForExcel(sdo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					sdo.setPageNo(j + 1);
					sdo.setRecPerPage(recPerPage);
					list = masterInventoryDao.getInventoryRateChangeHistoryDataForExcel(sdo);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	private String getListJsonCompatibleByInventoryRateChangeHistory(List<Map<String,Object>> list,SearchInventoryDashboardVO searchInventoryDashboardVO) throws Exception {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(searchInventoryDashboardVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(searchInventoryDashboardVO.getStartIndex() + size);
			sb.append(" ,data:[");
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> ll = list.get(i);
				
				String userName = ll.get("user_name").toString();
				String historyModifiedTimestamp = (String)ll.get("history_modified_timestamp");
				String mid = ll.get("mid").toString();
				String banName = CcmUtil.stringToSql((String)ll.get("ban_name"));//BAN
				String vendorName = CcmUtil.stringToSql((String)ll.get("vendor_name"));//Vendor Name
				String summaryVendorName = CcmUtil.stringToSql((String)ll.get("summary_vendor_name"));//Summary Vendor Name
				String invoiceNumber = CcmUtil.stringToSql((String)ll.get("invoice_number"));//Invoice Number
				String lineOfBusiness = CcmUtil.stringToSql((String)ll.get("line_of_business"));//Line of Business
				String strippedCircuitNumber = CcmUtil.stringToSql((String)ll.get("stripped_circuit_number"));//Stripped Circuit Number
				String serviceId = CcmUtil.stringToSql((String)ll.get("service_id"));//Service ID
				String validationSourceSystem = CcmUtil.stringToSql((String)ll.get("validation_source_system"));//Validation Source System
				String circuitStatus = CcmUtil.stringToSql((String)ll.get("circuit_status"));//Circuit Status
				String installDate = CcmUtil.stringToSql((String)ll.get("installation_date"));//Installation Date
				String firstInvoiceDate = CcmUtil.stringToSql((String)ll.get("first_invoice_date"));//First Invoice Date
				String firstInvoiceNumber = CcmUtil.stringToSql((String)ll.get("first_invoice_number"));//First Invoice Number
				String disconnectionDate = CcmUtil.stringToSql((String)ll.get("disconnection_date"));//Disconnection Date
				String serviceDescription = CcmUtil.stringToSql((String)ll.get("service_description"));//Service Description
				String accessType = CcmUtil.stringToSql((String)ll.get("access_type"));//Access Type
				String productCategory = CcmUtil.stringToSql((String)ll.get("product_category"));//Product Category
				String subProductCategory = CcmUtil.stringToSql((String)ll.get("sub_product_category"));//Sub Product Category
				String project = CcmUtil.stringToSql((String)ll.get("project"));//Project
				String projectCategoryStatus = CcmUtil.stringToSql((String)ll.get("project_category_status"));//Project Category Status
				String aStreetNumber = CcmUtil.stringToSql((String)ll.get("a_street_number"));//A Street Number
				String aStreetName = CcmUtil.stringToSql((String)ll.get("a_street_name"));//A Street Name
				String aUnit = CcmUtil.stringToSql((String)ll.get("a_unit"));//A Unit
				String aCity = CcmUtil.stringToSql((String)ll.get("a_city"));//A City
				String aProvince = CcmUtil.stringToSql((String)ll.get("a_province"));//A Province
				String aPostalCode = CcmUtil.stringToSql((String)ll.get("a_postal_code"));//A Postal Code
				String aggregatorCid = CcmUtil.stringToSql((String)ll.get("aggregator_cid"));//Aggregator CID
				String customerBillingAccount = CcmUtil.stringToSql((String)ll.get("customer_billing_account"));//Customer Billing Account #
				String businessSegment = CcmUtil.stringToSql((String)ll.get("business_segment"));//Business Segment
				String endUser = CcmUtil.stringToSql((String)ll.get("end_user"));//End User
				String scoa = CcmUtil.stringToSql((String)ll.get("scoa"));//SCOA
				String owner = CcmUtil.stringToSql((String)ll.get("owner"));//Owner
				String intercompanyBusinessUnit = CcmUtil.stringToSql((String)ll.get("intercompany_business_unit"));//Intercompany Business Unit
				String intercompanyChannel = CcmUtil.stringToSql((String)ll.get("intercompany_channel"));//Intercompany Channel
				String usoc = CcmUtil.stringToSql((String)ll.get("usoc"));//USOC
				String quantity = CcmUtil.stringToSql((String)ll.get("quantity"));//quantity
				String billingRate = CcmUtil.stringToSql((String)ll.get("billing_rate"));//billingRate
				String itemAmount = CcmUtil.stringToSql((String)ll.get("item_amount"));//itemAmount
				String agreementType = CcmUtil.stringToSql((String)ll.get("agreement_type"));//RAGREEMENT TYPE
				String contract = CcmUtil.stringToSql((String)ll.get("contract"));//Contract
				String contractTerm = CcmUtil.stringToSql((String)ll.get("contract_term"));
				String terminationFee = CcmUtil.stringToSql((String)ll.get("termination_fee"));
				String tariff = CcmUtil.stringToSql((String)ll.get("tariff"));// Tariff
				String tariffPage = CcmUtil.stringToSql((String)ll.get("tariff_page"));// Tariff
				String rate = CcmUtil.stringToSql((String)ll.get("rate"));//Rate
				String rateEffectiveDate = CcmUtil.stringToSql((String)ll.get("rate_effective_date"));//RATE EFFECTIVE DATE
				String baseAmount = CcmUtil.stringToSql((String)ll.get("base_amount"));
				String rateMultiplier = CcmUtil.stringToSql((String)ll.get("rate_multiplier"));
				String discount = CcmUtil.stringToSql((String)ll.get("discount"));
				String rateStatus = CcmUtil.stringToSql((String)ll.get("rate_status"));//Rate Status
				String rateDiscrepancyFlag = CcmUtil.stringToSql((String)ll.get("rate_discrepancy_flag"));//RATE DISCREPANCY
				String expiryDate = CcmUtil.stringToSql((String)ll.get("expiry_date"));//Expiry Date
				
				Object str2 = "{id:"+ mid
						+",userName:\""+userName
						+"\",historyModifiedTimestamp:\""+historyModifiedTimestamp
						+"\",vendorName:\""+vendorName
						+"\",summaryVendorName:\""+summaryVendorName
						+"\",banName:\""+banName
						+"\",invoiceNumber:\""+invoiceNumber
						+"\",lineOfBusiness:\""+lineOfBusiness
						+"\",strippedCircuitNumber:\""+strippedCircuitNumber
						+"\",serviceId:\""+serviceId
						+"\",circuitStatus:\""+circuitStatus
						+"\",accessType:\""+accessType
						+"\",installDate:\""+installDate
						+"\",firstInvoiceDate:\""+firstInvoiceDate
						+"\",firstInvoiceNumber:\""+firstInvoiceNumber
						+"\",disconnectionDate:\""+disconnectionDate
						+"\",validationSourceSystem:\""+validationSourceSystem
						+"\",serviceDescription:\""+serviceDescription
						+"\",productCategory:\""+productCategory
						+"\",subProductCategory:\""+subProductCategory
						+"\",project:\""+project
						+"\",projectCategoryStatus:\""+projectCategoryStatus
						+"\",aStreetNumber:\""+aStreetNumber
						+"\",aStreetName:\""+aStreetName
						+"\",aUnit:\""+aUnit
						+"\",aCity:\""+aCity
						+"\",aProvince:\""+aProvince
						+"\",aPostalCode:\""+aPostalCode
						+"\",aggregatorCid:\""+aggregatorCid
						+"\",customerBillingAccount:\""+customerBillingAccount
						+"\",businessSegment:\""+businessSegment
						+"\",endUser:\""+endUser
						+"\",scoa:\""+scoa
						+"\",owner:\""+owner
						+"\",usoc:\""+usoc
						+"\",quantity:\""+quantity
						+"\",billingRate:\""+billingRate
						+"\",itemAmount:\""+itemAmount
						+"\",rate:\""+rate
						+"\",rateEffectiveDate:\""+rateEffectiveDate
						+"\",tariffPage:\""+tariffPage
						+"\",expiryDate:\""+expiryDate
						+"\",rateStatus:\""+rateStatus
						+"\",agreementType:\""+agreementType
						+"\",contract:\""+contract
						+"\",contractTerm:\""+contractTerm
						+"\",terminationFee:\""+terminationFee
						+"\",tariff:\""+tariff
						+"\",intercompanyBusinessUnit:\""+intercompanyBusinessUnit
						+"\",intercompanyChannel:\""+intercompanyChannel
						+"\",baseAmount:\""+baseAmount
						+"\",rateMultiplier:\""+rateMultiplier
						+"\",discount:\""+discount
						+"\",rateDiscrepancyFlag:\""+rateDiscrepancyFlag +"\"}";
//						+"\",modifiedUser:\""+modifiedUser +"\"}";
				if (i != 0)
					sb.append(",");
				sb.append(str2.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}
	
}
