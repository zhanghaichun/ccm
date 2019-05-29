package com.saninco.ccm.dao.contractRules;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.vo.contractRules.SearchContractRulesVO;

public class ContractRulesDaoImpl extends HibernateDaoSupport implements IContractRulesDao {
	private final Logger logger = Logger.getLogger(this.getClass());


	/**
	 * 获取页面中 Summary Vendor Name 下拉列表中的内容。
	 */
	public List<String> listSummaryVendorName() {
		
		logger.info("Enter method listSummaryVendorName.");
		List<String> summaryVendorNameList = null;
		Session session = getSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select arm.summary_vendor_name from audit_reference_mapping arm ");
		sb.append("where arm.audit_reference_type_id = 3 and  arm.summary_vendor_name is not null ");
		sb.append("and arm.rec_active_flag='Y' group by arm.summary_vendor_name order by arm.summary_vendor_name ");
		
		try {
			summaryVendorNameList = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method listSummaryVendorName.");
			return summaryVendorNameList;
		} finally {
			releaseSession(session);
		}
		
	}


	/**
	 * 获取页面中 Key Field 下拉列表中的内容。
	 */
	public List<String> listKeyField() {
		
		logger.info("Enter method listKeyField.");
		List<String> keyFieldList = null;
		Session session = getSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select arm.key_field_original from audit_reference_mapping arm ");
		sb.append("where arm.audit_reference_type_id = 3 and  arm.key_field_original is not null ");
		sb.append("and arm.rec_active_flag='Y' group by arm.key_field_original order by arm.key_field_original ");
		
		try {
			keyFieldList = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method listKeyField.");
			return keyFieldList;
		} finally {
			releaseSession(session);
		}
		
	}

	/**
	 * 获取页面中 Sub Product 下拉列表中的内容。
	 */
	public List<String> listSubProduct() {
		
		logger.info("Enter method listSubProduct.");
		List<String> subProductList = null;
		Session session = getSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select arm.sub_product from audit_reference_mapping arm ");
		sb.append("where arm.audit_reference_type_id = 3 and  arm.sub_product is not null ");
		sb.append("and arm.rec_active_flag='Y' group by arm.sub_product order by arm.sub_product");
		
		try {
			subProductList = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method listSubProduct.");
			return subProductList;
		} finally {
			releaseSession(session);
		}
		
	}

	/**
	 * 获取页面中 Contract Name 下拉列表中的内容。
	 */
	public List<String> listContractName() {
		
		logger.info("Enter method listContractName.");
		List<String> contractNameList = null;
		Session session = getSession();
		
		StringBuffer sb = new StringBuffer();
		sb.append("select cf.contract_number from contract c ");
		sb.append("left JOIN contract_file cf on c.contract_file_id = cf.id ");
		sb.append("where c.source = 'Rogers' and cf.contract_number is not null ");
		sb.append("and c.rec_active_flag = 'Y' and cf.rec_active_flag = 'Y' ");
		sb.append("group by cf.contract_number order by cf.contract_number ");
		
		
		try {
			contractNameList = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method listContractName.");
			return contractNameList;
		} finally {
			releaseSession(session);
		}
		
	}

	/**
	 * 查询 Contract Rules 列表中的分页信息。
	 */
	public long countContractRulesViewListPageNo(SearchContractRulesVO searchContractRulesVO) {
		logger.info("Enter method countContractRulesViewListPageNo.");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) as contractRulesCounts ");
		sb.append( contractRulesWhereConditions(searchContractRulesVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method countContractRulesViewListPageNo.");
		return count;
	}

	/**
	 * Contract Rules 列表查询 SQL语句中的 from , where 条件。
	 * @param searchContractRulesVO
	 * @return
	 */
	private String contractRulesWhereConditions(SearchContractRulesVO searchContractRulesVO) {

		logger.info("Enter method contractRulesWhereConditions.");
		   
		final String WITHIN_SIX_MONTHS = "withinSixMonths";
	    final String SIX_TO_TWELVE_MONTHS = "sixToTwelveMonths";
	    final String THIRTEEN_TO_TWENTY_FOUR_MONTHS = "thirteenToTwentyFourMonths";
	    final String EXPIRED = "Expired";
	    
	    
		StringBuffer sb = new StringBuffer();

		sb.append(" from contract c ");
		
		sb.append(" left join audit_reference_mapping arm on c.id = arm.audit_reference_id ");
		
		sb.append(" left join contract_file cf on c.contract_file_id = cf.id  ");
		
		sb.append(" left join audit_rate_period arp on arp.reference_id = arm.audit_reference_id ");
		
		sb.append(" where arm.audit_reference_type_id = 3 and c.source = 'Rogers' ");
		sb.append(" and c.rec_active_flag = 'Y' ");
		
		
		if (searchContractRulesVO != null) {
			
			if ( searchContractRulesVO.getSummaryVendorName() != null ) { 
				String summaryVendorName = StringHandling.sqlStringReplace( searchContractRulesVO.getSummaryVendorName() );
				sb.append("AND arm.summary_vendor_name like '%" + summaryVendorName + "%' ");
			}
			
			if ( searchContractRulesVO.getKeyField() != null ) { 
				String keyField = StringHandling.sqlStringReplace( searchContractRulesVO.getKeyField() );
				sb.append("AND arm.key_field_original = '" + keyField + "' ");
			}
			
			if ( searchContractRulesVO.getSubProduct() != null ) {
				String subProduct = StringHandling.sqlStringReplace( searchContractRulesVO.getSubProduct() );
				sb.append("AND arm.sub_product = '" + subProduct + "' ");
			}
			
			if ( searchContractRulesVO.getContractName() != null ) {
				String contractName = StringHandling.sqlStringReplace( searchContractRulesVO.getContractName() );
				sb.append("AND c.name like '%" + contractName + "%' ");
			}
			
			if ( searchContractRulesVO.getUsoc() != null ) {
				String usoc = StringHandling.sqlStringReplace( searchContractRulesVO.getUsoc() );
				sb.append("AND arm.usoc like '%" + usoc + "%' ");
			}
			
			if ( searchContractRulesVO.getUsocLongDescription() != null ) {
				String usocLongDescription = StringHandling.sqlStringReplace( searchContractRulesVO.getUsocLongDescription() );
				sb.append("AND arm.usoc_description LIKE '%" + usocLongDescription + "%' ");
			}
			
			if ( searchContractRulesVO.getRate() != null ) {
				sb.append("AND arp.rate = '" + searchContractRulesVO.getRate() + "' ");
			}
			
			if ( searchContractRulesVO.getEffectiveDate() != null ) {
				sb.append("AND date_format(arp.start_date, '%Y/%m/%d') = '"+ searchContractRulesVO.getEffectiveDate() +"' ");
			}
			
			if ( searchContractRulesVO.getStrippedCircuitNumber() != null ) {
				String strippedCircuitNumber = StringHandling.sqlStringReplace( searchContractRulesVO.getStrippedCircuitNumber() );
				sb.append("AND arm.circuit_number like '%" + strippedCircuitNumber + "%' ");
			}
			
			if ( searchContractRulesVO.getItemDescription() != null ) {
				String itemDescription = StringHandling.sqlStringReplace( searchContractRulesVO.getItemDescription() );
				sb.append("AND arm.item_description like '%" + itemDescription + "%' ");
			}
			
			if ( searchContractRulesVO.getLineItemCode() != null ) {
				String lineItemCode = StringHandling.sqlStringReplace( searchContractRulesVO.getLineItemCode() );
				sb.append("AND arm.line_item_code like '%" + lineItemCode + "%' ");
			}
			
			if ( searchContractRulesVO.getLineItemCodeDescription() != null ) {
				String lineItemCodeDescription = StringHandling.sqlStringReplace( searchContractRulesVO.getLineItemCodeDescription() );
				sb.append("AND arm.line_item_code_description like '%" + lineItemCodeDescription + "%' ");
			}
			
			// Expiration Period
			if( searchContractRulesVO.getExpirationPeriod() != null ) {
				String expirationPeriod = StringHandling.sqlStringReplace( searchContractRulesVO.getExpirationPeriod() );
				
				if ( EXPIRED.equals(expirationPeriod) ) { // Expired
					sb.append("AND cf.term_quantity - TIMESTAMPDIFF(MONTH, arp.start_date, CURRENT_DATE) <= 0 ");
				}
				else if ( WITHIN_SIX_MONTHS.equals(expirationPeriod) ) { // Within 6 months
					
					sb.append("AND cf.term_quantity - TIMESTAMPDIFF(MONTH, arp.start_date, CURRENT_DATE) <= 6 AND cf.term_quantity - TIMESTAMPDIFF(MONTH, arp.start_date, CURRENT_DATE) > 0");
					
				} else if( SIX_TO_TWELVE_MONTHS.equals(expirationPeriod) ) { // 6 - 12 months
					sb.append("AND cf.term_quantity - TIMESTAMPDIFF(MONTH, arp.start_date, CURRENT_DATE) <= 12 AND cf.term_quantity - TIMESTAMPDIFF(MONTH, arp.start_date, CURRENT_DATE) > 6");
					
				} else if (THIRTEEN_TO_TWENTY_FOUR_MONTHS.equals(expirationPeriod)) { // 13-24 months
					sb.append("AND cf.term_quantity - TIMESTAMPDIFF(MONTH, arp.start_date, CURRENT_DATE) <= 24 AND cf.term_quantity - TIMESTAMPDIFF(MONTH, arp.start_date, CURRENT_DATE) > 12");
				} 
			}
			
			if ( searchContractRulesVO.getFilter() != null ) { // Filter
				sb.append( "AND " + searchContractRulesVO.getFilter() + " " );
			}
			
			
			
		}
		
		
		
		
		
		logger.info("Exit method contractRulesWhereConditions.");
		return sb.toString();
	}

	/**
	 * 从数据库中查询　Contract Rules列表信息。
	 */
	 public List<String> listContractRules(SearchContractRulesVO searchContractRulesVO) {

		logger.info("Enter method listContractRules.");

		final String sql = this.listContractRulesQueryString(searchContractRulesVO);

		HibernateTemplate template = this.getHibernateTemplate();
		List<String> contractRulesList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method listContractRules.");
		return contractRulesList;
	}

	/**
	 * Contract rules列表信息查询的 SQL语句。
	 */
	private String listContractRulesQueryString(SearchContractRulesVO searchContractRulesVO) {

		logger.info("Enter method listContractRulesQueryString.");
		StringBuffer sb = new StringBuffer();
		
		
		sb.append(" SELECT CONCAT('{indexId: ', toJSON(arm.id IS NULL, '', arm.id), ");

		sb.append(" ',summaryVendorName: \"', toJSON(arm.summary_vendor_name IS NULL, '', arm.summary_vendor_name), ");

		sb.append(" '\",keyField: \"', toJSON(arm.key_field_original IS NULL, '', arm.key_field_original), ");

		sb.append(" '\",usoc: \"', toJSON(arm.usoc IS NULL, '', arm.usoc), ");

		sb.append(" '\",usocLongDescription: \"', toJSON(arm.usoc_description IS NULL, '', arm.usoc_description), ");
		sb.append(" '\",expirationPeriod: \"', toJSON(arp.start_date IS NULL, '', FN_GET_EXPIRATION_PERIOD(arp.start_date, cf.term_quantity)), ");
		
		
		sb.append(" '\",circuitNumber: \"', toJSON(arm.circuit_number IS NULL, '', arm.circuit_number ), ");

		sb.append(" '\",subProduct: \"', toJSON(arm.sub_product IS NULL, '', arm.sub_product), ");

		sb.append(" '\",rate: \"', toJSON( arp.rate IS NULL,'', format(arp.rate, 5)), ");
		sb.append(" '\",mmbc: \"', toJSON( c.mmbc IS NULL,'', c.mmbc), ");
		sb.append(" '\",discount: \"', toJSON( c.discount IS NULL,'', format(c.discount, 5)), ");

		sb.append(" '\",rateEffectiveDate: \"', toJSON(arp.start_date IS NULL, '', date_format(arp.start_date, '%Y-%m-%d')), ");

		sb.append(" '\",termQuantity: \"', toJSON(cf.term_quantity IS NULL, '', cf.term_quantity), ");

		sb.append(" '\",penaltyInitialPercent: \"', toJSON(cf.penalty_initial_percent IS NULL, '', concat( cast( format(cf.penalty_initial_percent, 2)*100 as char), '%') ), ");

		sb.append(" '\",itemDescription: \"', toJSON(arm.item_description IS NULL, '', arm.item_description), ");

		sb.append(" '\",contractName: \"', toJSON(cf.contract_number IS NULL, '', cf.contract_number), ");

		sb.append(" '\",schedule: \"', toJSON(c.schedule IS NULL, '', c.schedule), ");

		sb.append(" '\",lineItemCode: \"', toJSON(arm.line_item_code IS NULL, '', arm.line_item_code), ");

		sb.append(" '\",lineItemCodeDescription: \"', toJSON(arm.line_item_code_description IS NULL, '', arm.line_item_code_description), "); 

		sb.append(" '\",notes: \"', toJSON(arm.notes IS NULL, '', arm.notes), "); 

				
		sb.append(" '\"}') "); // concat 函数结束符。
		
		sb.append( contractRulesWhereConditions(searchContractRulesVO) );
		
		// 排序条件。
		sb.append(" ORDER BY " + searchContractRulesVO.getSortField() + " " + searchContractRulesVO.getSortingDirection());
		
		// 分页限制条件。
		sb.append(" " + searchContractRulesVO.getLimitCause() + " ");
		
		logger.info("Exit method listContractRulesQueryString.");
		return sb.toString();
	}

	/**
	 * 下载contract rules列表
	 */
	public List<Object[]> downloadContractRulesToExcel(SearchContractRulesVO searchContractRulesVO) {

		logger.info("Enter method downloadContractRulesToExcel.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		sb.append( downloadContractRulesToExcelQueryString(searchContractRulesVO) );
		sb.append(" ORDER BY " + searchContractRulesVO.getSortField() + " " + searchContractRulesVO.getSortingDirection());
		sb.append( " " + searchContractRulesVO.getLimitCause() + " " );

		try {

			List<Object[]> contractRulesExcelDataColumns = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method downloadContractRulesToExcel.");
			return contractRulesExcelDataColumns;

		} finally {
			releaseSession(session);
		}

			
		
	}

	/**
	 * 将 contract rules 下载到excel文件中用到的sql查询语句.
	 */
	private String downloadContractRulesToExcelQueryString(SearchContractRulesVO searchContractRulesVO) {

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ");

		sb.append(" toJSON(arp.start_date IS NULL, '',date_format(arp.start_date, '%Y-%m-%d') ), ");
		sb.append(" toJSON(cf.term_quantity IS NULL, '', cf.term_quantity), ");
		sb.append(" toJSON(arp.start_date IS NULL, '', FN_GET_EXPIRATION_PERIOD(arp.start_date, cf.term_quantity)), ");

		sb.append(" toJSON(cf.penalty_initial_percent IS NULL, 0, concat( cast( format(cf.penalty_initial_percent, 2)*100 as char), '%') ), ");
		sb.append(" toJSON(arp.rate IS NULL, '',  format(arp.rate, 5) ), ");
		sb.append(" toJSON( c.mmbc IS NULL,'', c.mmbc), ");
		sb.append(" toJSON( c.discount IS NULL,'', format(c.discount, 5)), ");
		sb.append(" toJSON(cf.contract_number IS NULL, '', cf.contract_number), ");

		sb.append(" toJSON(c.schedule IS NULL, '', c.schedule), ");
		sb.append(" toJSON(arm.circuit_number IS NULL, '', arm.circuit_number ), ");
		sb.append(" toJSON(arm.usoc IS NULL, '', arm.usoc), "); 

		sb.append(" toJSON(arm.usoc_description IS NULL, '', arm.usoc_description), ");
		sb.append(" toJSON(arm.line_item_code IS NULL, '', arm.line_item_code), ");
		sb.append(" toJSON(arm.line_item_code_description IS NULL, '', arm.line_item_code_description), ");
		sb.append(" toJSON(arm.item_description IS NULL, '', arm.item_description), ");
		sb.append(" toJSON(arm.summary_vendor_name IS NULL, '', arm.summary_vendor_name), ");

		sb.append(" toJSON(arm.sub_product IS NULL, '', arm.sub_product), ");
		sb.append(" toJSON(arm.key_field_original IS NULL, '', arm.key_field_original) ");

		sb.append( contractRulesWhereConditions(searchContractRulesVO) );

		return sb.toString();
	}


}