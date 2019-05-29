package com.saninco.ccm.dao.contract;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.vo.contract.SearchContractVO;

public class ContractDaoImpl extends HibernateDaoSupport implements IContractDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 从数据库中获取 carrier entity name list, 数据库中contract_file 表中的 carrier_entity_name 字段的值。
	 */
	public List<String> getCarrierEntityNameList() {
		logger.info("Enter method getCarrierEntityNameList.");
		List<String> carrierEntityNameList = null;
		Session session = getSession();
		
		try {
			carrierEntityNameList = session.createSQLQuery("SELECT cf.carrier_entity_name FROM contract_file cf WHERE cf.rec_active_flag='Y' AND cf.carrier_entity_name IS NOT NULL GROUP BY cf.carrier_entity_name").list();
			logger.info("Exit method getCarrierEntityNameList.");
			return carrierEntityNameList;
		} finally {
			releaseSession(session);
		}
		
	}


	/**
	 * 从数据库中获取 carrier type list, 数据库中contract_file 表中的 carrier_type 字段的值。
	 */
	public List<String> getCarrierTypeList() {
		logger.info("Enter method getCarrierTypeList.");
		List<String> carrierTypeList = null;
		Session session = getSession();
		
		try {
			carrierTypeList = session.createSQLQuery("SELECT cf.carrier_type FROM contract_file cf WHERE cf.rec_active_flag='Y' AND cf.carrier_type IS NOT NULL GROUP BY cf.carrier_type").list();
			logger.info("Exit method getCarrierTypeList.");
			return carrierTypeList;
		} finally {
			releaseSession(session);
		}
		
	}

	/**
	 * 从数据库中获取 agreement type list, 数据库中contract_file 表中的 agreement_type 字段的值。
	 */
	public List<String> getAgreementTypeList() {
		logger.info("Enter method getAgreementTypeList.");
		List<String> agreementTypeList = null;
		Session session = getSession();
		
		try {
			agreementTypeList = session.createSQLQuery("SELECT cf.agreement_type FROM contract_file cf WHERE cf.rec_active_flag='Y' AND cf.agreement_type IS NOT NULL GROUP BY cf.agreement_type").list();
			logger.info("Exit method getAgreementTypeList.");
			return agreementTypeList;
		} finally {
			releaseSession(session);
		}
		
	}

	/**
	 * 从数据库中获取 products/services list, 数据库中contract_file 表中的 products_services 字段的值。
	 */
	public List<String> getProductsServicesList() {
		logger.info("Enter method getProductsServicesList.");
		List<String> productsServicesList = null;
		Session session = getSession();
		
		try {
			productsServicesList = session.createSQLQuery("SELECT cf.products_services FROM contract_file cf WHERE cf.rec_active_flag='Y' AND cf.products_services IS NOT NULL GROUP BY cf.products_services").list();
			logger.info("Exit method getProductsServicesList.");
			return productsServicesList;
		} finally {
			releaseSession(session);
		}
		
	}

	/**
	 * 从数据库中获取 term list, 数据库中contract_file 表中的 term_combined 字段的值。
	 */
	public List<String> getTermCombinedList() {
		logger.info("Enter method getTermCombinedList.");
		List<String> termCombinedList = null;
		Session session = getSession();
		
		try {
			termCombinedList = session.createSQLQuery("SELECT cf.term_combined FROM contract_file cf WHERE cf.rec_active_flag='Y' AND cf.term_combined IS NOT NULL GROUP BY cf.term_combined").list();
			logger.info("Exit method getTermCombinedList.");
			return termCombinedList;
		} finally {
			releaseSession(session);
		}
		
	}

	/**
	 * 从数据库中查询　Contract 列表分页显示时所需要的信息。
	 * 真正查询的是contract_file表中的记录的条数。
	 */
	public long getContractViewListPageNo(SearchContractVO searchContractVO) {
		logger.info("Enter method getContractViewListPageNo.");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(*) AS contractCounts ");
		sb.append( contractWhereConditions(searchContractVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getContractViewListPageNo.");
		return count;
	}

	/**
	 * 从数据库中查询　Contract 列表分页显示时所需要的信息。
	 */
	public List<String> searchContractList(SearchContractVO searchContractVO) {

		logger.info("Enter method searchContractList.");
		final String sql = this.searchContractListQueryString(searchContractVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> contractList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchContractList.");
		return contractList;
	}
	
	/**
	 * 从数据库中查询 TerminationPenalty  列表分页显示时所需要的信息。
	 * @throws SQLException 
	 */
	public long getTerminationPenaltyListNo(SearchContractVO searchContractVO) throws SQLException {
		logger.info("Enter method getTerminationPenaltyListNo.");
		
		this.calculateContractPenalty(searchContractVO);
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(*) AS tmp_master_inventory_penalty");
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getTerminationPenaltyListNo.");
		return count;
	}
	
	/**
	 * 从数据库中查询　Contract 列表分页显示时所需要的信息。
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public List<String> searchTerminationPenaltyList(SearchContractVO searchContractVO) throws SQLException {

		logger.info("Enter method searchTerminationPenaltyList.");
		this.calculateContractPenalty(searchContractVO);
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT CONCAT('{masterInventoryId: \"', toJSON(master_inventory_id IS NULL, '', master_inventory_id), "); // master_inventory_id
		sb.append(" '\",contractName: \"', toJSON(contract_name IS NULL, '', contract_name), "); // contract_name
		sb.append(" '\",strippedCircuitNumber: \"', toJSON(stripped_circuit_number IS NULL, '', stripped_circuit_number), "); // stripped_circuit_number
		sb.append(" '\",rate: \"', toJSON(rate IS NULL, '', format(rate,2)), "); // rate 
		sb.append(" '\",term: \"', toJSON(term IS NULL, '', term), "); // term 
		sb.append(" '\",penaltyAmount: \"', toJSON(penalty_amount IS NULL, '', penalty_amount), "); // penalty_amount 
		sb.append(" '\"}') "); // concat 函数结束符。
		sb.append(" FROM tmp_master_inventory_penalty");
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		List<String> terminationPenaltyList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchTerminationPenaltyList.");
		return terminationPenaltyList;
	}
	
	/**
	 * @author SP_CALCULATE_CONTRACT_PENALTY
	 */
	public void calculateContractPenalty(SearchContractVO searchContractVO) throws SQLException{
		logger.info("Enter method calculateContractPenalty.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_CALCULATE_CONTRACT_PENALTY(?,?)");
			proc.setString(1, searchContractVO.getCircuitNumber());
			if(searchContractVO.getTerminationDate() == null || "".equals(searchContractVO.getTerminationDate())){
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String strDate = sdf.format(d);
				proc.setString(2, strDate);
			} else {
				proc.setString(2, searchContractVO.getTerminationDate());
			}
			
			proc.execute();
			logger.info("Exit method calculateContractPenalty.");
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * Contract 列表中所需信息查询的 SQL语句。
	 * @param searchContractVO
	 * @return
	 */
	private String searchContractListQueryString(SearchContractVO searchContractVO) {
		logger.info("Enter method searchContractListQueryString.");
		StringBuffer sb = new StringBuffer();
		
		
		sb.append(" SELECT CONCAT('{id: ', toJSON(cf.id IS NULL, '', cf.id), "); // Id.
		sb.append(" ',internalId: \"', toJSON(cf.internal_id IS NULL, '', cf.internal_id), "); // Internal Id #.
		sb.append(" '\",supplierContractNumber: \"', toJSON(cf.supplier_contract_number IS NULL, '', cf.supplier_contract_number), "); // Supplier Master Contract #.
		sb.append(" '\",nameOfAgreement: \"', toJSON(cf.name_of_agreement IS NULL, '', cf.name_of_agreement), "); // Name of Agreement.
		sb.append(" '\",contractAmendment: \"', toJSON(cf.contract_amendment IS NULL, '', cf.contract_amendment), "); // Contract Amendment.
		
		
		sb.append(" '\",amendmentDate: \"', toJSON(cf.amendment_date IS NULL, '', cf.amendment_date ), "); // Amendment Date.
		sb.append(" '\",carrierEntityName: \"', toJSON(cf.carrier_entity_name IS NULL, '', cf.carrier_entity_name), "); // Carrier Entity Name.
		sb.append(" '\",carrierAddress: \"', toJSON(cf.carrier_address IS NULL, '', cf.carrier_address), "); // Carrier Address.
		sb.append(" '\",rogersLegalName: \"', toJSON(cf.rogers_legal_name IS NULL, '', cf.rogers_legal_name), "); // Rogers Legal Entity Name.
		sb.append(" '\",carrierType: \"', toJSON(cf.carrier_type IS NULL, '', cf.carrier_type), "); // Carrier Type.
		sb.append(" '\",agreementType: \"', toJSON(cf.agreement_type IS NULL, '', cf.agreement_type), "); // Agreement Type.
		sb.append(" '\",productsServices: \"', toJSON(cf.products_services IS NULL, '', cf.products_services), "); // Products/Services.
		sb.append(" '\",schedules: \"', toJSON(cf.schedules IS NULL, '', cf.schedules), "); // Schedules.
		sb.append(" '\",termCommence: \"', toJSON(cf.term_commence IS NULL, '', cf.term_commence), "); // Term - Commence.
		sb.append(" '\",termExpiry: \"', toJSON(cf.term_expiry IS NULL, '', cf.term_expiry), "); // Term - Expiry.
		sb.append(" '\",term: \"', toJSON(cf.term_combined IS NULL, '', cf.term_combined), "); // Term.
		sb.append(" '\",renewalOptions: \"', toJSON(cf.renewal_options IS NULL, '', cf.renewal_options), "); // Renewal Options.
		sb.append(" '\",renewalNoticePeriod: \"', toJSON(cf.renewal_notice_period IS NULL, '', cf.renewal_notice_period), "); // Renewal Notice Period (days).
		sb.append(" '\",terminationNoticePeriod: \"', toJSON(cf.termination_notice_period IS NULL, '', cf.termination_notice_period), "); // Termination Notice Period (days).
		sb.append(" '\",disputePolicy: \"', toJSON(cf.dispute_policy IS NULL, '', cf.dispute_policy ), "); // Dispute Policy (days).
		sb.append(" '\",backBillingPolicy: \"', toJSON(cf.back_billing_policy IS NULL, '', cf.back_billing_policy), "); // Back Billing Policy (days).
		sb.append(" '\",volumeSpendCommitment: \"', toJSON(cf.volume_spend_commitment IS NULL, '', FORMAT(cf.volume_spend_commitment,2)), "); // Volume/Spend Commitment.
		
		
		sb.append(" '\",earlyCancellationPenalty: \"', toJSON(cf.early_cancellation_penalty IS NULL, '', cf.early_cancellation_penalty), "); // Early Cancellation Penalty.
		sb.append(" '\",contractSignedDate: \"', toJSON(cf.contract_signed_date IS NULL, '', cf.contract_signed_date), "); // Contract Signed Date.
		
		
		sb.append(" '\",sla: \"', toJSON(cf.sla IS NULL, '', cf.sla), "); // SLA (Latency, MTTR, Service Availability/Network Availability).
		sb.append(" '\",notes: \"', toJSON(cf.notes IS NULL, '', cf.notes), "); // Notes.
		sb.append(" '\",paymentTerms: \"', toJSON(cf.payment_terms IS NULL, '', cf.payment_terms), "); // Payment Terms (days).
		sb.append(" '\",currency: \"', toJSON(cf.currency IS NULL, '', cf.currency), "); // Currency.
		sb.append(" '\",taxNumber: \"', toJSON(cf.tax_number IS NULL, '', cf.tax_number), "); // GST/HST/QST Numbers.
		sb.append(" '\",growthIncentiveClauses: \"', toJSON(cf.growth_incentive_clauses IS NULL, '', cf.growth_incentive_clauses), "); // Growth Incentive Clauses (tied to Spend).
		sb.append(" '\",creditDiscount: \"', toJSON(cf.credit_discount IS NULL, '', cf.credit_discount), "); // Credit %/Discount.
		sb.append(" '\",orderPlacementDetails: \"', toJSON(cf.order_placement_details IS NULL, '', cf.order_placement_details), "); // Order Placement Details (Business Practices).
		sb.append(" '\",priceIncreaseNotice: \"', toJSON(cf.price_increase_notice IS NULL, '', cf.price_increase_notice), "); // Price Increase Notice.
		sb.append(" '\",nda: \"', toJSON(cf.nda IS NULL, '', cf.nda), "); // NDA (pre-requisite to MSA).
		
		
		sb.append(" '\"}') "); // concat 函数结束符。
		
		sb.append( contractWhereConditions(searchContractVO) );
		
		// 排序条件。
		sb.append(" ORDER BY " + searchContractVO.getSortField() + " " + searchContractVO.getSortingDirection());
		
		// 分页限制条件。
		sb.append(" " + searchContractVO.getLimitCause() + " ");
		
		logger.info("Exit method searchContractListQueryString.");
		return sb.toString();
	}

	/**
	 * Contract 列表中所需信息查询的 SQL语句中的 from 条件和 where 条件。
	 * @param searchContractVO
	 * @return
	 */
	private String contractWhereConditions(SearchContractVO searchContractVO) {

		logger.info("Enter method contractWhereConditions.");
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM contract_file AS cf ");
		sb.append(" WHERE cf.rec_active_flag='Y' ");
		
		if (searchContractVO != null) {
			
			if ( searchContractVO.getInternalId() != null ) { // Internal Id
				String internalId = StringHandling.sqlStringReplace( searchContractVO.getInternalId() );
				sb.append("AND cf.internal_id LIKE '%" + internalId + "%' ");
			}
			
			if ( searchContractVO.getSupplierContractNumber() != null ) { // Supplier Contract Number
				String supplierContractNumber = StringHandling.sqlStringReplace( searchContractVO.getSupplierContractNumber() );
				sb.append("AND cf.supplier_contract_number LIKE '%" + supplierContractNumber + "%' ");
			}
			
			if ( searchContractVO.getNameOfAgreement() != null ) { // Name of Agreement
				String nameOfAgreement = StringHandling.sqlStringReplace( searchContractVO.getNameOfAgreement() );
				sb.append("AND cf.name_of_agreement LIKE '%" + nameOfAgreement + "%' ");
			}
			
			if ( searchContractVO.getAmendmentDate() != null ) { // Amendment Date (Date)
				sb.append("AND DATEDIFF(cf.amendment_date, '" + searchContractVO.getAmendmentDate() + "')=0 ");
			}
			
			if ( searchContractVO.getCarrierEntityName() != null ) { // Carrier Entity Name(dropdown filter)
				sb.append("AND cf.carrier_entity_name='" + searchContractVO.getCarrierEntityName() + "' ");
			}
			
			if ( searchContractVO.getCarrierAddress() != null ) { // Carrier Address
				String carrierAddress = StringHandling.sqlStringReplace( searchContractVO.getCarrierAddress() );
				sb.append("AND cf.carrier_address LIKE '%" + carrierAddress + "%' ");
			}
			
			if ( searchContractVO.getRogersLegalEntityName() != null ) { // Rogers Legal Name
				String rogersLegalName = StringHandling.sqlStringReplace( searchContractVO.getRogersLegalEntityName() );
				sb.append("AND cf.rogers_legal_name LIKE '%" + rogersLegalName + "%' ");
			}
			
			if ( searchContractVO.getCarrierType() != null ) { // Carrier Type(dropdown filter)
				sb.append("AND cf.carrier_type='" + searchContractVO.getCarrierType() + "' ");
			}
			
			if ( searchContractVO.getAgreementType() != null ) { // Agreement Type(dropdown filter)
				sb.append("AND cf.agreement_type='" + searchContractVO.getAgreementType() + "' ");
			}
			
			if ( searchContractVO.getProductsOrServices() != null ) { // Products/Services(dropdown filter)
				sb.append("AND cf.products_services='" + searchContractVO.getProductsOrServices() + "' ");
			}
			
			if ( searchContractVO.getSchedules() != null ) { // Schedules
				String schedules = StringHandling.sqlStringReplace( searchContractVO.getSchedules() );
				sb.append("AND cf.schedules LIKE '%" + schedules + "%' ");
			}
			
			if ( searchContractVO.getTermCombined() != null ) { // TERM (dropdown filter)
				sb.append("AND cf.term_combined='" + searchContractVO.getTermCombined() + "' ");
			}
			
			if ( searchContractVO.getContractSignedDate() != null ) { // Contract Signed Date (Date)
				sb.append("AND DATEDIFF(cf.contract_signed_date, '" + searchContractVO.getContractSignedDate() + "')=0 ");
			}
			
			if ( searchContractVO.getTermExpiry() != null ) { // Term Expiry (Date)
				sb.append("AND DATEDIFF(cf.term_expiry, '" + searchContractVO.getTermExpiry() + "')=0 ");
			}
			
			if ( searchContractVO.getFilter() != null ) { // Filter
				sb.append( "AND " + searchContractVO.getFilter() + " " );
			}
			
			
			
		}
		
		
		
		logger.info("Exit method contractWhereConditions.");
		return sb.toString();
	}

	/**
	 * 下载contract列表
	 */
	public List<Object[]> downloadContractToExcel(SearchContractVO searchContractVO) {

		logger.info("Enter method downloadContractToExcel.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		sb.append( downloadContractToExcelQueryString(searchContractVO) );
		sb.append(" ORDER BY " + searchContractVO.getSortField() + " " + searchContractVO.getSortingDirection());
		sb.append( " " + searchContractVO.getLimitCause() + " " );

		try {

			List<Object[]> contractExcelDataColumns = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method downloadContractToExcel.");
			return contractExcelDataColumns;

		} finally {
			releaseSession(session);
		}

			
		
	}

	/**
	 * 将 contract 下载到excel文件中用到的sql查询语句.
	 */
	private String downloadContractToExcelQueryString(SearchContractVO searchContractVO) {

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT toJSON(cf.internal_id IS NULL, '', cf.internal_id), "); // Internal Id #.
		sb.append(" toJSON(cf.supplier_contract_number IS NULL, '', cf.supplier_contract_number), "); // Supplier Master Contract #.
		sb.append(" toJSON(cf.name_of_agreement IS NULL, '', cf.name_of_agreement), "); // Name of Agreement.
		sb.append(" toJSON(cf.contract_amendment IS NULL, '', cf.contract_amendment), ");  // Contract Amendment.
		sb.append(" toJSON(cf.amendment_date IS NULL, '', cf.amendment_date ), ");  // Amendment Date.
		sb.append(" toJSON(cf.carrier_entity_name IS NULL, '', cf.carrier_entity_name), ");  // Carrier Entity Name.
		sb.append(" toJSON(cf.carrier_address IS NULL, '', cf.carrier_address), ");  // Carrier Address.
		sb.append(" toJSON(cf.rogers_legal_name IS NULL, '', cf.rogers_legal_name), ");  // Rogers Legal Entity Name.
		sb.append(" toJSON(cf.carrier_type IS NULL, '', cf.carrier_type), ");  // Carrier Type.
		sb.append(" toJSON(cf.agreement_type IS NULL, '', cf.agreement_type), ");  // Agreement Type.
		sb.append(" toJSON(cf.products_services IS NULL, '', cf.products_services), ");  // Products/Services.
		sb.append(" toJSON(cf.schedules IS NULL, '', cf.schedules), ");  // Schedules.
		sb.append(" toJSON(cf.term_commence IS NULL, '', cf.term_commence), ");  // Term - Commence.
		sb.append(" toJSON(cf.term_expiry IS NULL, '', cf.term_expiry), ");  // Term - Expiry.
		sb.append(" toJSON(cf.term_combined IS NULL, '', cf.term_combined), ");  // Term.
		sb.append(" toJSON(cf.renewal_options IS NULL, '', cf.renewal_options), ");  // Renewal Options.
		sb.append(" toJSON(cf.renewal_notice_period IS NULL, '', cf.renewal_notice_period), ");  // Renewal Notice Period (days).
		sb.append(" toJSON(cf.termination_notice_period IS NULL, '', cf.termination_notice_period), ");  // Termination Notice Period (days).
		sb.append(" toJSON(cf.dispute_policy IS NULL, '', cf.dispute_policy ), ");  // Dispute Policy (days).
		sb.append(" toJSON(cf.back_billing_policy IS NULL, '', cf.back_billing_policy), ");  // Back Billing Policy (days).
		sb.append(" toJSON(cf.volume_spend_commitment IS NULL, '', FORMAT(cf.volume_spend_commitment,2)), ");  // Volume/Spend Commitment.
		sb.append(" toJSON(cf.early_cancellation_penalty IS NULL, '', cf.early_cancellation_penalty), ");  // Early Cancellation Penalty.
		sb.append(" toJSON(cf.contract_signed_date IS NULL, '', cf.contract_signed_date), ");  // Contract Signed Date.

		sb.append(" toJSON(cf.sla IS NULL, '', cf.sla), ");  // SLA (Latency, MTTR, Service Availability/Network Availability).
		sb.append(" toJSON(cf.notes IS NULL, '', cf.notes), ");  // Notes.
		sb.append(" toJSON(cf.payment_terms IS NULL, '', cf.payment_terms), ");  // Payment Terms (days).
		sb.append(" toJSON(cf.currency IS NULL, '', cf.currency), ");  // Currency.
		sb.append(" toJSON(cf.tax_number IS NULL, '', cf.tax_number), ");  // GST/HST/QST Numbers.
		sb.append(" toJSON(cf.growth_incentive_clauses IS NULL, '', cf.growth_incentive_clauses), ");  // Growth Incentive Clauses (tied to Spend).
		sb.append(" toJSON(cf.credit_discount IS NULL, '', cf.credit_discount), ");  // Credit %/Discount.
		sb.append(" toJSON(cf.order_placement_details IS NULL, '', cf.order_placement_details), ");  // Order Placement Details (Business Practices).
		sb.append(" toJSON(cf.price_increase_notice IS NULL, '', cf.price_increase_notice), ");  // Price Increase Notice.
		sb.append(" toJSON(cf.nda IS NULL, '', cf.nda) ");  // NDA (pre-requisite to MSA).
		

		sb.append( contractWhereConditions(searchContractVO) );

		return sb.toString();
	}

}