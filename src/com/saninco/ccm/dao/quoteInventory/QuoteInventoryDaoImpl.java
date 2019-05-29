package com.saninco.ccm.dao.quoteInventory;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;

public class QuoteInventoryDaoImpl extends HibernateDaoSupport implements IQuoteInventoryDao {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 从数据库中获取 prodiver list, 数据库中quote 表中的 provider 字段的值。
	 */
	public List<String> getProviderList() {
		logger.info("Enter method getProviderList.");
		List<String> providerList = null;
		Session session = getSession();
		
		try {
			providerList = session.createSQLQuery("SELECT q.provider FROM quote q WHERE q.rec_active_flag='Y' GROUP BY q.provider").list();
			logger.info("Exit method getProviderList.");
			return providerList;
		} finally {
			releaseSession(session);
		}
		
		
	}
	
	/**
	 * 从数据库中获取 product list, 数据库中quote 表中的 product_internal 字段的值。
	 */
	public List<String> getProductList() {
		logger.info("Enter method getProductList.");
		List<String> productList = null;
		Session session = getSession();
		
		try {
			productList = session.createSQLQuery("SELECT q.product_internal FROM quote q WHERE q.rec_active_flag='Y' GROUP BY q.product_internal").list();
			logger.info("Exit method getProductList.");
			return productList;
		} finally {
			releaseSession(session);
		}
		
		
	}
	
	/**
	 * 从数据库中查询　Quote Inventory 列表所需要的信息。
	 */
	public List<String> searchQuoteInventoryList(SearchQuoteVO searchQuoteVO) {
		
		logger.info("Enter method searchQuoteInventoryList.");
		final String sql = this.searchQuoteInventoryListQueryString(searchQuoteVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> quoteInventoryList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchQuoteInventoryList.");
		return quoteInventoryList;
	}
	
	/**
	 * Quote Inventory 列表中所需信息查询的 SQL语句。
	 * @param searchQuoteVO
	 * @return
	 */
	private String searchQuoteInventoryListQueryString(SearchQuoteVO searchQuoteVO) {
		
		logger.info("Enter method searchQuoteInventoryListQueryString.");
		StringBuffer sb = new StringBuffer();
		
		// Integer 类型数据。
		sb.append(" SELECT CONCAT('{id: ', toJSON(q.id IS NULL, '', q.id), "); // Key.
		sb.append(" ',opportunitySites: \"', toJSON(q.opportunity_sites IS NULL, '', q.opportunity_sites), "); // Opportunity (# sites).
		sb.append(" '\",daysToQuote: \"', toJSON(q.days_to_quote IS NULL, '', q.days_to_quote), "); // No. of Days To Quote.
		sb.append(" '\",sitePerProduct: \"', toJSON(q.site_per_product IS NULL, '', q.site_per_product), "); // Site Per Product.
		sb.append(" '\",quoteProviderDays: \"', toJSON(q.quote_provider_days IS NULL, '', q.quote_provider_days), "); // No. of Days To Quote Provider.
		
		// Double 类型数据。
		sb.append(" '\",quantity: \"', toJSON(q.quantity IS NULL, '', FORMAT(q.quantity, 2) ), "); // Quantity.
		sb.append(" '\",access: \"', toJSON(q.access IS NULL, '', FORMAT(q.access,2)), "); // Access.
		sb.append(" '\",evcNumber1: \"', toJSON(q.evc_number_1 IS NULL, '', FORMAT(q.evc_number_1,2)), "); // EVC1 Price.
		sb.append(" '\",evcNumber2: \"', toJSON(q.evc_number_2 IS NULL, '', FORMAT(q.evc_number_2, 2)), "); // EVC2 Price.
		sb.append(" '\",evcNumber3: \"', toJSON(q.evc_number_3 IS NULL, '', FORMAT(q.evc_number_3, 2)), "); // EVC3 Price.
		sb.append(" '\",portCharge: \"', toJSON(q.port_charge IS NULL, '', FORMAT(q.port_charge, 2)), "); // Port Charge.
		sb.append(" '\",mrcYear1: \"', toJSON(q.mrc_year_1 IS NULL, '', FORMAT(q.mrc_year_1, 2)), "); // MRC Year 1.
		sb.append(" '\",mrcYear2: \"', toJSON(q.mrc_year_2 IS NULL, '', FORMAT(q.mrc_year_2, 2)), "); // MRC Year 2.
		sb.append(" '\",mrcYear3: \"', toJSON(q.mrc_year_3 IS NULL, '', FORMAT(q.mrc_year_3,2)), "); // MRC Year 3.
		sb.append(" '\",mrcYear5: \"', toJSON(q.mrc_year_5 IS NULL, '', FORMAT(q.mrc_year_5,2)), "); // MRC Year 5.
		sb.append(" '\",mrcYear7: \"', toJSON(q.mrc_year_7 IS NULL, '', FORMAT(q.mrc_year_7,2)), "); // MRC Year 7.
		sb.append(" '\",mrcYear10: \"', toJSON(q.mrc_year_10 IS NULL, '', FORMAT(q.mrc_year_10,2)), "); // MRC Year 10.
		sb.append(" '\",mrcYear15: \"', toJSON(q.mrc_year_15 IS NULL, '', FORMAT(q.mrc_year_15,2)), "); // MRC Year 15.
		sb.append(" '\",nrc: \"', toJSON(q.nrc IS NULL, '', FORMAT(q.nrc, 2)), "); // NRC.
		sb.append(" '\",constructionCosts: \"', toJSON(q.construction_costs IS NULL, '', FORMAT(q.construction_costs, 2) ), "); // Construction Costs.
		sb.append(" '\",cbsMrcOriginal: \"', toJSON(q.cbs_mrc_original IS NULL, '', FORMAT(q.cbs_mrc_original, 2)), "); // CBS - MRC (Original).
		sb.append(" '\",cbsNrcConstruction: \"', toJSON(q.cbs_nrc_construction_original IS NULL, '', FORMAT(q.cbs_nrc_construction_original,2)), "); // CBS - NRC + Construction (Original).
		
		// Date 类型数据。
		sb.append(" '\",dateRecvd: \"', toJSON(q.date_recvd IS NULL, '', q.date_recvd), "); // Date Recvd.
		sb.append(" '\",dateIssued: \"', toJSON(q.date_issued IS NULL, '', q.date_issued), "); // Date Issued.
		
		// String 类型数据。
		sb.append(" '\",recordId: \"', toJSON(q.record_id IS NULL, '', q.record_id), "); // Record Id.
		sb.append(" '\",status: \"', toJSON(q.status IS NULL, '', q.status), "); // Status.
		sb.append(" '\",type: \"', toJSON(q.type IS NULL, '', q.type), "); // Type.
		sb.append(" '\",zendeskQuoteId: \"', toJSON(q.zendesk_quote_id IS NULL, '', q.zendesk_quote_id), "); // Zendesk Quote Id.
		sb.append(" '\",nsa: \"', toJSON(q.nsa IS NULL, '', q.nsa), "); // NSA.
		sb.append(" '\",otherRequester: \"', toJSON(q.other_requester IS NULL, '', q.other_requester), "); // Other Requester.
		sb.append(" '\",enterpriseCustomer: \"', toJSON(q.enterprise_customer IS NULL, '', q.enterprise_customer), "); // Enterprise Customer.
		sb.append(" '\",customerType: \"', toJSON(q.customer_type IS NULL, '', q.customer_type), "); // Customer Type.
		sb.append(" '\",customerSegment: \"', toJSON(q.customer_segment IS NULL, '', q.customer_segment), "); // Customer Segment.
		sb.append(" '\",productGroup: \"', toJSON(q.product_group IS NULL, '', q.product_group), "); // Product Group.
		sb.append(" '\",productSubGroup: \"', toJSON(q.product_sub_group IS NULL, '', q.product_sub_group), "); // Product Sub Group.
		sb.append(" '\",productRequested: \"', toJSON(q.product_requested IS NULL, '', q.product_requested), "); // Product Requested.
		sb.append(" '\",productInternal: \"', toJSON(q.product_internal IS NULL, '', q.product_internal), "); // Product Internal.
		sb.append(" '\",provider: \"', toJSON(q.provider IS NULL, '', q.provider), "); // Provider.
		sb.append(" '\",productExternal: \"', toJSON(q.product_external IS NULL, '', q.product_external), "); // Product External.
		sb.append(" '\",accessMB: \"', toJSON(q.access_mb IS NULL, '', q.access_mb), "); // Access(MB).
		sb.append(" '\",EVC1: \"', toJSON(q.evc_1 = '' OR q.evc_1 IS NULL, '', FORMAT(q.evc_1,2)), "); // EVC1.
		sb.append(" '\",classOfService1: \"', toJSON(q.class_of_service_1 IS NULL, '', q.class_of_service_1), "); // EVC1 Class of Service.
		sb.append(" '\",EVC2: \"', toJSON(q.evc_2 = '' OR q.evc_2 IS NULL, '', FORMAT(q.evc_2,2) ), "); // EVC2.
		sb.append(" '\",classOfService2: \"', toJSON(q.class_of_service_2 IS NULL, '', q.class_of_service_2), "); // EVC2 Class of Service.
		sb.append(" '\",numberOfPorts: \"', toJSON(q.no_of_port IS NULL, '', q.no_of_port), "); // # of Port(s).
		sb.append(" '\",addnEVCs: \"', toJSON(q.addn_evcs IS NULL, '', q.addn_evcs), "); // Addn EVCs.
		sb.append(" '\",partyDateRequested: \"', toJSON(q.party_date_requested IS NULL, '', q.party_date_requested), "); // 3RD Party Date Requested.
		sb.append(" '\",partyDateReceived: \"', toJSON(q.party_date_received IS NULL, '', q.party_date_received), "); // 3RD Party Date Received.
		sb.append(" '\",onNearNet: \"', toJSON(q.on_near_net IS NULL, '', q.on_near_net), "); // On-Net/Near-Net.
		sb.append(" '\",currency: \"', toJSON(q.currency IS NULL, '', q.currency), "); // Currency.
		sb.append(" '\",aSuiteOrUnit: \"', toJSON(q.a_unit IS NULL, '', q.a_unit), "); // A-Suite/Unit.
		sb.append(" '\",aStreetNumber: \"', toJSON(q.a_street_number IS NULL, '', q.a_street_number), "); // A-Street Number.
		sb.append(" '\",aStreetName: \"', toJSON(q.a_street_name IS NULL, '', q.a_street_name), "); // A-Street Name.
		sb.append(" '\",aCity: \"', toJSON(q.a_city IS NULL, '', a_city), "); // A-City.
		sb.append(" '\",aProvince: \"', toJSON(q.a_province IS NULL, '', q.a_province), "); // A-Prov.
		sb.append(" '\",aPostalCode: \"', toJSON(q.a_postal_code IS NULL, '', q.a_postal_code), "); // A-Postal Code.
		sb.append(" '\",aCountry: \"', toJSON(q.a_country IS NULL, '', q.a_country), "); // A-Country.
		sb.append(" '\",ZSuiteOrUnit: \"', toJSON(q.z_unit IS NULL, '', q.z_unit), "); // Z-Suite/Unit.
		sb.append(" '\",zStreetNumber: \"', toJSON(q.z_street_number IS NULL, '', q.z_street_number), "); // Z-Street Number.
		sb.append(" '\",zStreetName: \"', toJSON(q.z_street_name IS NULL, '', q.z_street_name), "); // Z-Street Name.
		sb.append(" '\",zCity: \"', toJSON(q.z_city IS NULL, '', q.z_city), "); // Z-City.
		sb.append(" '\",zProvince: \"', toJSON(q.z_province IS NULL, '', q.z_province), "); // Z-Prov.
		sb.append(" '\",zPostalCode: \"', toJSON(q.z_postal_code IS NULL, '', q.z_postal_code), "); // Z-Postal Code.
		sb.append(" '\",zCountry: \"', toJSON(q.z_country IS NULL, '', q.z_country), "); // Z-Country.
		sb.append(" '\",notes: \"', toJSON(q.notes IS NULL, '', q.notes), "); // Notes.
		sb.append(" '\",carrierQuote: \"', toJSON(q.carrier_quote IS NULL, '', q.carrier_quote), "); // Presale/FOXX # - Carrier Quote #.
		sb.append(" '\",quoteDeskRep: \"', toJSON(q.quote_desk_rep IS NULL, '', q.quote_desk_rep), "); // Quote Desk Rep.
		sb.append(" '\",firmWonBid: \"', toJSON(q.firm_won_bid IS NULL, '', q.firm_won_bid), "); // Firm/Won Bid.
		sb.append(" '\",costSavings: \"', toJSON(q.cost_savings IS NULL, '', q.cost_savings), "); // Cost Savings.
		
		sb.append(" '\"}') "); // concat 函数结束符。
		
		sb.append( quoteInventoryWhereConditions(searchQuoteVO, null) );
		
		// 排序条件。
		sb.append(" ORDER BY " + searchQuoteVO.getSortField() + " " + searchQuoteVO.getSortingDirection());
		
		// 分页限制条件。
		sb.append(" " + searchQuoteVO.getLimitCause() + " ");
		
		logger.info("Exit method searchQuoteInventoryListQueryString.");
		return sb.toString();
		
	}
	
	/**
	 * Quote Inventory 列表中所需信息查询的 SQL语句中的 from 条件和 where 条件。
	 * @param searchQuoteVO
	 * @return
	 */
	private String quoteInventoryWhereConditions(SearchQuoteVO searchQuoteVO, List<Integer> ids) {
		
		logger.info("Enter method quoteInventoryWhereConditions.");
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM quote AS q ");
		sb.append(" WHERE q.rec_active_flag='Y' ");
		
		if (searchQuoteVO != null) {
			
			if (searchQuoteVO.getIsCompareFilter() != null && searchQuoteVO.getIsCompareFilter() == true) { // Compare 状态下的搜索。
				
				sb.append( composeCompareQueryString(searchQuoteVO) );
			} else { // 非compare条件下的搜索。
				
				if ( searchQuoteVO.getRecordId() != null ) { // Record Id
					String recordId = StringHandling.sqlStringReplace( searchQuoteVO.getRecordId() );
					sb.append(" AND q.record_id LIKE '%" + recordId + "%' ");
				}
				
				if ( searchQuoteVO.getZendeskQuoteId() != null ) { // Zendesk Quote Id
					String zendeskQuoteId = StringHandling.sqlStringReplace( searchQuoteVO.getZendeskQuoteId() );
					sb.append(" AND q.zendesk_quote_id LIKE '%" + zendeskQuoteId + "%' ");
				}

				if ( searchQuoteVO.getEnterpriseCustomer() != null ) { // Enterprise Customer
					String enterpriseCustomer = StringHandling.sqlStringReplace( searchQuoteVO.getEnterpriseCustomer() );
					sb.append(" AND q.enterprise_customer LIKE '%" + enterpriseCustomer + "%' ");
				}
				
				if ( searchQuoteVO.getDateReceivedStartOn() != null ) { // Date Received (From)
					sb.append(" AND DATEDIFF(q.date_recvd, '" + searchQuoteVO.getDateReceivedStartOn() + "')>=0 ");
				}

				if ( searchQuoteVO.getDateReceivedEndOn() != null ) { // Date Received (To)
					sb.append(" AND DATEDIFF(q.date_recvd, '" + searchQuoteVO.getDateReceivedEndOn() + "')<=0 ");
				}

				if ( searchQuoteVO.getDateIssuedStartOn() != null ) { // Date Issued (From)
					sb.append(" AND DATEDIFF(q.date_issued, '" + searchQuoteVO.getDateIssuedStartOn() + "')>=0 ");
				}

				if ( searchQuoteVO.getDateIssuedEndOn() != null ) { // Date Issued (To)
					sb.append(" AND DATEDIFF(q.date_issued, '" + searchQuoteVO.getDateIssuedEndOn() + "')<=0 ");
				}
				
				sb.append( composeCompareQueryString(searchQuoteVO) );
			}

			if ( searchQuoteVO.getFilter() != null ) { // Filter
				sb.append( " AND " + searchQuoteVO.getFilter() + " " );
			}


			if (ids !=null) { // 通过选中的复选框将id传回来，将特定条目的列表项下载到excel中。
				sb.append("  AND q.id in (" + ids.toString().substring(1, ids.toString().length()-1) + ") ");
			}
		}
		
		
		
		logger.info("Exit method quoteInventoryWhereConditions.");
		return sb.toString();
	}
	
	/**
	 * 组合compare条件下的sql查询语句，因为对于quote条件列表的查询，
	 * 无论是否处于compare状态都需要这些条件的查询，
	 * 这是在compare的状态下只能应用到这些条件，因此单独提出来一个方法。
	 * 
	 * 鑫哥将compare 状态下的搜索逻辑抽离为一个 sql function, 在函数内部实现compare 功能的逻辑。
	 * `FN_IS_CTPQ_MATCHING`(reference_type, reference_id, provider_name(vendor_name 这里的provider类似于vendor), product_name,
	 * a_street_number, a_street_name, a_city, a_province,  a_postal_code,
	 * z_street_number, z_street_name, z_city, z_province,  z_postal_code)
	 * 除了前两个参数其余的都是允许为空值的。
	 * @param searchQuoteVO
	 * @return
	 */
	public String composeCompareQueryString(SearchQuoteVO searchQuoteVO) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" AND FN_IS_CTPQ_MATCHING('quote', q.id, ");

		if ( searchQuoteVO.getProvider() != null ) { // Provider Name.
			String provider = StringHandling.sqlStringReplace( searchQuoteVO.getProvider() );
			sb.append( "'" + provider + "', " ); 
		} else {
			sb.append( searchQuoteVO.getProvider() + ", " );
		}

		if ( searchQuoteVO.getProductInternal() != null ) { // Product Name.
			String productInternal = StringHandling.sqlStringReplace( searchQuoteVO.getProductInternal() );
			sb.append( "'" + productInternal + "', " ); 
		} else {
			sb.append( searchQuoteVO.getProductInternal() + ", " );
		}

		if ( searchQuoteVO.getaStreetNumber() != null ) { // A Street Number.
			String aStreetNumber = StringHandling.sqlStringReplace( searchQuoteVO.getaStreetNumber() );
			sb.append( "'" + aStreetNumber + "', " ); 
		} else {
			sb.append( searchQuoteVO.getaStreetNumber() + ", " );
		}

		if ( searchQuoteVO.getaStreetName() != null ) { // A Street Name.
			String aStreetName = StringHandling.sqlStringReplace( searchQuoteVO.getaStreetName() );
			sb.append( "'" + searchQuoteVO.getaStreetName() + "', " );
		} else {
			sb.append( searchQuoteVO.getaStreetName() + ", " );
		}
		 
		if ( searchQuoteVO.getACity() != null ) { // A City.
			String aCity = StringHandling.sqlStringReplace( searchQuoteVO.getACity() );
			sb.append( "'" + aCity + "', " );
		} else {
			sb.append( searchQuoteVO.getACity() + ", " );
		}

		if ( searchQuoteVO.getAProvince() != null ) { // A Province.
			String aProvince = StringHandling.sqlStringReplace( searchQuoteVO.getAProvince() );
			sb.append( "'" + aProvince + "', " );
		} else {
			sb.append( searchQuoteVO.getAProvince() + ", " );
		}

		if ( searchQuoteVO.getAPostalCode() != null ) { // A Postal Code.
			String aPostalCode = StringHandling.sqlStringReplace( searchQuoteVO.getAPostalCode() );
			sb.append( "'" + aPostalCode + "', " ); 
		} else {
			sb.append( searchQuoteVO.getAPostalCode() + ", " );
		}

		if ( searchQuoteVO.getzStreetNumber() != null ) { // Z Street Number.
			String zStreetNumber = StringHandling.sqlStringReplace( searchQuoteVO.getzStreetNumber() );
			sb.append( "'" + zStreetNumber + "', " ); 
		} else {
			sb.append( searchQuoteVO.getzStreetNumber() + ", " );
		}

		if ( searchQuoteVO.getzStreetName() != null ) { // Z Street Name. 
			String zStreetName = StringHandling.sqlStringReplace( searchQuoteVO.getzStreetName() );
			sb.append( "'" + zStreetName + "', " ); 
		} else {
			sb.append( searchQuoteVO.getzStreetName() + ", " );
		}

		if ( searchQuoteVO.getZCity() != null ) { // Z City.
			String zCity = StringHandling.sqlStringReplace( searchQuoteVO.getZCity() );
			sb.append( "'" + zCity + "', " );  
		} else {
			sb.append( searchQuoteVO.getZCity() + ", " );
		}

		if ( searchQuoteVO.getZProvince() != null ) { // Z Province.
			String zProvince = StringHandling.sqlStringReplace( searchQuoteVO.getZProvince() );
			sb.append( "'" + zProvince + "', " );  
		} else {
			sb.append( searchQuoteVO.getZProvince() + ", " );
		}

		if ( searchQuoteVO.getZPostalCode() != null ) { // Z Postal Code.
			String zPostalCode = StringHandling.sqlStringReplace( searchQuoteVO.getZPostalCode() ); 
			sb.append( "'" + zPostalCode + "'" ); 
		} else {
			sb.append( searchQuoteVO.getZPostalCode());
		}
		
		sb.append(" ) = 1");
		return sb.toString();
	}
	
	/**
	 * 从数据库中查询　Quote Inventory 列表分页显示时所需要的信息。
	 */
	public long getQuoteInventoryViewListPageNo(SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method getQuoteInventoryViewListPageNo.");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(*) AS quoteCounts ");
		sb.append( quoteInventoryWhereConditions(searchQuoteVO, null) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getQuoteInventoryViewListPageNo.");
		return count;
	}

	/**
	 * 从数据库中获得下载成excel文件的字段的值
	 */
	public List<Object[]> downloadQuoteInventoryToExcel(SearchQuoteVO searchQuoteVO, List<Integer> ids) {
		
		logger.info("Enter method downloadQuoteInventoryToExcel.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		sb.append( downloadQuoteInventoryToExcelQueryString(searchQuoteVO,ids) );
		sb.append(" ORDER BY " + searchQuoteVO.getSortField() + " " + searchQuoteVO.getSortingDirection());
		sb.append( " " + searchQuoteVO.getLimitCause() + " " );

		try {

			List<Object[]> quoteInventoryExcelDataColumns = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method downloadQuoteInventoryToExcel.");
			return quoteInventoryExcelDataColumns;

		} finally {
			releaseSession(session);
		}
	}

	/**
	 * 将 quote inventory 下载到excel文件中用到的sql查询语句。
	 * @param  searchQuoteVO [description]
	 * @param  ids           [description]
	 * @return               [description]
	 */
	private String downloadQuoteInventoryToExcelQueryString(SearchQuoteVO searchQuoteVO, List<Integer> ids) {
		
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT toJSON(q.id IS NULL, '', q.id), "); // Record Id.
		sb.append(" toJSON(q.record_id IS NULL, '', q.record_id), "); // Record Id.
		sb.append(" toJSON(q.status IS NULL, '', q.status), "); // Status.
		sb.append(" toJSON(q.date_recvd IS NULL, '', q.date_recvd), ");  // Date Recvd.
		sb.append(" toJSON(q.date_issued IS NULL, '', q.date_issued), ");  // Date Issued.
		sb.append(" toJSON(q.days_to_quote IS NULL, '', q.days_to_quote), ");  // No. of Days To Quote.
		sb.append(" toJSON(q.type IS NULL, '', q.type), ");  // Type.
		sb.append(" toJSON(q.zendesk_quote_id IS NULL, '', q.zendesk_quote_id), ");  // Zendesk Quote Id.
		sb.append(" toJSON(q.opportunity_sites IS NULL, '', q.opportunity_sites), ");  // Opportunity (# sites).
		sb.append(" toJSON(q.nsa IS NULL, '', q.nsa), ");  // NSA.
		sb.append(" toJSON(q.other_requester IS NULL, '', q.other_requester), ");  // Other Requester.
		sb.append(" toJSON(q.enterprise_customer IS NULL, '', q.enterprise_customer), ");  // Enterprise Customer.
		sb.append(" toJSON(q.customer_type IS NULL, '', q.customer_type), ");  // Customer Type.
		sb.append(" toJSON(q.customer_segment IS NULL, '', q.customer_segment), ");  // Customer Segment.
		sb.append(" toJSON(q.product_group IS NULL, '', q.product_group), ");  // Product Group.
		sb.append(" toJSON(q.product_sub_group IS NULL, '', q.product_sub_group), ");  // Product Sub Group.
		sb.append(" toJSON(q.product_requested IS NULL, '', q.product_requested), ");  // Product Requested.
		sb.append(" toJSON(q.product_internal IS NULL, '', q.product_internal), ");  // Product Internal.
		sb.append(" toJSON(q.site_per_product IS NULL, '', q.site_per_product), ");  // Site Per Product.
		sb.append(" toJSON(q.provider IS NULL, '', q.provider), ");  // Provider.
		sb.append(" toJSON(q.quantity IS NULL, '', FORMAT(q.quantity, 2) ), ");  // Quantity.
		sb.append(" toJSON(q.product_external IS NULL, '', q.product_external), ");  // Product External.
		sb.append(" toJSON(q.access_mb IS NULL, '', q.access_mb), ");  // Access(MB).
		sb.append(" toJSON(q.evc_1 = '' OR q.evc_1 IS NULL, '', FORMAT(q.evc_1,2) ), ");  // EVC1.
		sb.append(" toJSON(q.class_of_service_1 IS NULL, '', q.class_of_service_1), ");  // EVC1 Class of Service.
		sb.append(" toJSON(q.evc_2 = '' OR q.evc_2 IS NULL, '', FORMAT(q.evc_2,2) ), ");  // EVC2.
		sb.append(" toJSON(q.class_of_service_2 IS NULL, '', q.class_of_service_2), ");  // EVC2 Class of Service.
		sb.append(" toJSON(q.no_of_port IS NULL, '', q.no_of_port), ");  // # of Port(s).
		sb.append(" toJSON(q.addn_evcs IS NULL, '', q.addn_evcs), ");  // Addn EVCs.
		sb.append(" toJSON(q.party_date_requested IS NULL, '', q.party_date_requested), ");  // 3RD Party Date Requested.
		sb.append(" toJSON(q.party_date_received IS NULL, '', q.party_date_received), ");  // 3RD Party Date Received.
		sb.append(" toJSON(q.quote_provider_days IS NULL, '', q.quote_provider_days), ");  // No. of Days To Quote Provider.
		sb.append(" toJSON(q.on_near_net IS NULL, '', q.on_near_net), ");  // On-Net/Near-Net.
		sb.append(" toJSON(q.currency IS NULL, '', q.currency), ");  // Currency.
		sb.append(" toJSON(q.access IS NULL, '', FORMAT(q.access,2)), ");  // Access.
		sb.append(" toJSON(q.evc_number_1 IS NULL, '', FORMAT(q.evc_number_1,2)), ");  // EVC1 Price.
		sb.append(" toJSON(q.evc_number_2 IS NULL, '', FORMAT(q.evc_number_2, 2)), ");  // EVC2 Price.
		sb.append(" toJSON(q.evc_number_3 IS NULL, '', FORMAT(q.evc_number_3, 2)), ");  // EVC3 Price.
		sb.append(" toJSON(q.port_charge IS NULL, '', FORMAT(q.port_charge, 2)), ");  // Port Charge.
		sb.append(" toJSON(q.mrc_year_1 IS NULL, '', FORMAT(q.mrc_year_1, 2)), ");  // MRC Year 1.
		sb.append(" toJSON(q.mrc_year_2 IS NULL, '', FORMAT(q.mrc_year_2, 2)), ");  // MRC Year 2.
		sb.append(" toJSON(q.mrc_year_3 IS NULL, '', FORMAT(q.mrc_year_3,2)), ");  // MRC Year 3.
		sb.append(" toJSON(q.mrc_year_5 IS NULL, '', FORMAT(q.mrc_year_5,2)), ");  // MRC Year 5.
		sb.append(" toJSON(q.mrc_year_7 IS NULL, '', FORMAT(q.mrc_year_7,2)), ");  // MRC Year 7.
		sb.append(" toJSON(q.mrc_year_10 IS NULL, '', FORMAT(q.mrc_year_10,2)), ");  // MRC Year 10.
		sb.append(" toJSON(q.mrc_year_15 IS NULL, '', FORMAT(q.mrc_year_15,2)), ");  // MRC Year 15.
		sb.append(" toJSON(q.nrc IS NULL, '', FORMAT(q.nrc, 2)), ");  // NRC.
		sb.append(" toJSON(q.construction_costs IS NULL, '', FORMAT(q.construction_costs, 2) ), ");  // Construction Costs.
		sb.append(" toJSON(q.a_unit IS NULL, '', q.a_unit), ");  // A-Suite/Unit.
		sb.append(" toJSON(q.a_street_number IS NULL, '', q.a_street_number), ");  // A-Street Number.
		sb.append(" toJSON(q.a_street_name IS NULL, '', q.a_street_name), ");  // A-Street Name.
		sb.append(" toJSON(q.a_city IS NULL, '', a_city), ");  // A-City.
		sb.append(" toJSON(q.a_province IS NULL, '', q.a_province), ");  // A-Prov.
		sb.append(" toJSON(q.a_postal_code IS NULL, '', q.a_postal_code), ");  // A-Postal Code.
		sb.append(" toJSON(q.a_country IS NULL, '', q.a_country), ");  // A-Country.
		sb.append(" toJSON(q.z_unit IS NULL, '', q.z_unit), ");  // Z-Suite/Unit.
		sb.append(" toJSON(q.z_street_number IS NULL, '', q.z_street_number), ");  // Z-Street Number.
		sb.append(" toJSON(q.z_street_name IS NULL, '', q.z_street_name), ");  // Z-Street Name.
		sb.append(" toJSON(q.z_city IS NULL, '', q.z_city), ");  // Z-City.
		sb.append(" toJSON(q.z_province IS NULL, '', q.z_province), ");  // Z-Prov.
		sb.append(" toJSON(q.z_postal_code IS NULL, '', q.z_postal_code), ");  // Z-Postal Code.
		sb.append(" toJSON(q.z_country IS NULL, '', q.z_country), ");  // Z-Country.
		sb.append(" toJSON(q.notes IS NULL, '', q.notes), ");  // Notes.
		sb.append(" toJSON(q.carrier_quote IS NULL, '', q.carrier_quote), ");  // Presale/FOXX # - Carrier Quote #.
		sb.append(" toJSON(q.cbs_mrc_original IS NULL, '', FORMAT(q.cbs_mrc_original, 2)), ");  // CBS - MRC (Original).
		sb.append(" toJSON(q.cbs_nrc_construction_original IS NULL, 0.00, FORMAT(q.cbs_nrc_construction_original,2)), ");  // CBS - NRC + Construction (Original).
		sb.append(" toJSON(q.quote_desk_rep IS NULL, '', q.quote_desk_rep), ");  // Quote Desk Rep.
		sb.append(" toJSON(q.firm_won_bid IS NULL, '', q.firm_won_bid), ");  // Firm/Won Bid.
		sb.append(" toJSON(q.cost_savings IS NULL, '', q.cost_savings) ");  // Cost Savings.

		sb.append( quoteInventoryWhereConditions(searchQuoteVO, ids) );

		return sb.toString();
	}
	
	
}