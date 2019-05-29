package com.saninco.ccm.dao.validation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.vo.SearchInvoiceVO;

public class MRCVarianceDaoImpl extends HibernateDaoSupport implements IMRCVarianceDao {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public long countMRCVarianceNo(SearchInvoiceVO searchInvoiceVO) {
		
		logger.info("Enter method countMRCVarianceNo.");
        
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(1) FROM audit_mrc_variance");
        sb.append( " WHERE invoice_id = " + searchInvoiceVO.getInvoiceId() );
//        sb.append( rateSearchWhereConditions(searchRateSearchVO) );
        
        final String sql = sb.toString();
        
        HibernateTemplate template = this.getHibernateTemplate();
        
        Integer count = (Integer) template.execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session)throws HibernateException, SQLException {
                List l = session.createSQLQuery(sql).list();
                return new Integer(l.get(0).toString());
            }
        });
        
        logger.info("Exit method countMRCVarianceNo.");
        return count;
	}

	@Override
	public List<String> listMRCVarianceListings(SearchInvoiceVO searchInvoiceVO) {

		logger.info("Enter method listMRCVarianceListings.");
        StringBuffer sb = new StringBuffer();

        sb.append( this.listMRCVarianceListingsQueryString(searchInvoiceVO) );
        
        if (searchInvoiceVO.getFilter() != null) {
        	sb.append( " WHERE " + searchInvoiceVO.getFilter() + " " );
        }
        

        // 排序条件。
        sb.append(" ORDER BY " + searchInvoiceVO.getSortField() + " " + searchInvoiceVO.getSortingDirection());
        
        // 分页限制条件。
        sb.append(" " + searchInvoiceVO.getLimitCause() + " ");

        final String sql = sb.toString();

        HibernateTemplate template = this.getHibernateTemplate();
        List<String> mrcVarianceList = (List<String>) template.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        return session.createSQLQuery(sql).list();
                    }
                });
        logger.info("Exit method listMRCVarianceListings.");
        return mrcVarianceList;
	}

	private String listMRCVarianceListingsQueryString(SearchInvoiceVO searchInvoiceVO) {

		logger.info("Enter method listMRCVarianceListingsQueryString.");
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT CONCAT('{strippedCircuitNumber: \"', toJSON(r.strippedCircuitNumber IS NULL, '', r.strippedCircuitNumber), ");

        sb.append(" '\",previousMRC: \"', toJSON( r.previousMRC IS NULL, '', FORMAT(r.previousMRC, 2) ), ");
        sb.append(" '\",currentMRC: \"', toJSON(r.currentMRC IS NULL, '', FORMAT(r.currentMRC,2) ), ");
        sb.append(" '\",mrcVariance: \"', toJSON(r.mrcVariance IS NULL, '', FORMAT(r.mrcVariance,2) ), ");
        sb.append(" '\",occAmount: \"', toJSON(r.occAmount IS NULL, '', FORMAT(r.occAmount, 2) ), ");
        
        sb.append(" '\",statusValidationId: \"', toJSON(r.statusValidationId IS NULL, '', r.statusValidationId), ");
        sb.append(" '\",rateValidationId: \"', toJSON(r.rateValidationId IS NULL, '',r.rateValidationId), ");
        sb.append(" '\",statusValidationName: \"', toJSON(r.statusValidationName IS NULL, '', r.statusValidationName), ");
        sb.append(" '\",rateValidationName: \"', toJSON(r.rateValidationName IS NULL, '',r.rateValidationName), ");
        
        sb.append(" '\",varianceReason: \"', toJSON(r.varianceReason IS NULL, '', r.varianceReason), "); 
        sb.append(" '\",varianceNotes: \"', toJSON(r.varianceNotes IS NULL, '', r.varianceNotes), ");
        sb.append(" '\"}')  FROM "); 
        
        sb.append( " ( SELECT amv.stripped_circuit_number AS strippedCircuitNumber, " ); 
        sb.append( " amv.mrc_previous AS previousMRC, " ); 
        sb.append( " amv.mrc_current AS currentMRC, " ); 
        sb.append( " amv.mrc_variance AS mrcVariance, " ); 
        sb.append( " amv.occ AS occAmount, " ); 
        
        sb.append( " amv.audit_circuit_status AS statusValidationId, " ); // {Number}
        sb.append( " amv.audit_rate_status AS rateValidationId, " ); // {Number}
        sb.append( " aus.audit_status_name AS statusValidationName, " ); // {String}
        sb.append( " aurs.audit_status_name AS rateValidationName, " ); // {String}
        
        sb.append( " vr.variance_reason_name AS varianceReason, " ); 
        
        sb.append( " amv.variance_notes AS varianceNotes " ); 
        sb.append( " FROM audit_mrc_variance amv " ); 
        sb.append( " LEFT JOIN variance_reason vr ON vr.id = amv.variance_reason_id " ); 
        sb.append( " LEFT JOIN audit_status aus ON aus.id = amv.audit_circuit_status " ); 
        sb.append( " LEFT JOIN audit_status aurs ON aurs.id = amv.audit_rate_status " ); 
        sb.append( " WHERE invoice_id = " + searchInvoiceVO.getInvoiceId() );
        sb.append( " 	AND (amv.audit_circuit_status != 1 " );
        sb.append( " 		OR amv.audit_rate_status != 1) " );
        
        sb.append( ") r" ); 
        
        logger.info("Exit method listMRCVarianceListingsQueryString.");
        return sb.toString();
        
        
	}

	@Override
	public List<String> listVarianceReasons() {

		logger.info("Enter method listVarianceReasons.");
		
        List<String> varianceReasonList = null;
        Session session = getSession();
        
        
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT DISTINCT variance_reason_name FROM variance_reason ");
        
        sb.append(" ORDER BY variance_reason_name ");
        
        try {
        	varianceReasonList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listVarianceReasons.");
            return varianceReasonList;
        } finally {
            releaseSession(session);
        }
	}

	@Override
	public List<Map<String, String>> getMasterInventoryDetails(SearchInvoiceVO searchInvoiceVO) {

		logger.info("Enter method getMasterInventoryDetails.");
		
		String banId = searchInvoiceVO.getBanId();
		String strippedCircuitNumber = searchInvoiceVO.getCricuitNumber();
		
        List<Map<String, String>> masterInventoryDetailsList = null;
        Session session = getSession();
        
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT ");
        sb.append("	UPPER(mi.circuit_status) AS circuitStatus ,  ");
        sb.append("(  ");
        sb.append("	SELECT COUNT(1) FROM master_inventory "); // Query master inventory details count.
        sb.append(" WHERE ban_id = " + banId);
        sb.append(" AND stripped_circuit_number = " + strippedCircuitNumber);
        sb.append(") AS detailsCount, ");
        sb.append("mi.service_id AS serviceId,  ");
        sb.append("mi.unique_circuit_id AS uniqueCircuitId,  ");
        sb.append("mi.product_category AS productCategory,  ");
        sb.append("DATE_FORMAT(mi.installation_date, '%Y/%m/%d') AS installationDate,  ");
        sb.append("DATE_FORMAT(mi.disconnection_date, '%Y/%m/%d') AS disconnectionDate ");
        sb.append("FROM master_inventory mi ");
        sb.append("WHERE mi.ban_id =  " + banId );
        sb.append("		AND mi.stripped_circuit_number =  " + strippedCircuitNumber );
        sb.append(" ORDER BY id DESC ");
        sb.append(" LIMIT 1; ");
        
        try {
        	masterInventoryDetailsList = session.createSQLQuery( sb.toString() )
        		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            logger.info("Exit method getMasterInventoryDetails.");
            return masterInventoryDetailsList;
            
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            releaseSession(session);
        }
        
		return null;
	}

	@Override
	public long countVarianceItemsNo(SearchInvoiceVO searchInvoiceVO) {
		
		logger.info("Enter method countVarianceItemsNo.");
        
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT COUNT(1) FROM");
        sb.append(this.organizeVarianceItemsSqlStatements(searchInvoiceVO) );
        sb.append(" GROUP BY r.id" );
        
        final String sql = sb.toString();
        
        HibernateTemplate template = this.getHibernateTemplate();
        
        Integer count = (Integer) template.execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session)throws HibernateException, SQLException {
                List l = session.createSQLQuery(sql).list();
                return new Integer(l.get(0).toString());
            }
        });
        
        logger.info("Exit method countVarianceItemsNo.");
        return count;
	}

	private String organizeVarianceItemsSqlStatements(SearchInvoiceVO searchInvoiceVO) {

		StringBuffer sb = new StringBuffer();	
			
		sb.append("(");
		
		sb.append(" select \r\n" + 
				"    p.id,\r\n" + 
				"    ar.audit_status_id,\r\n" + 
				"    au.audit_status_name,\r\n" + 
				"    ii.stripped_circuit_number as strippedCircuitNumber,\r\n" + 
				"    it.item_type_name as chargeType,\r\n" + 
				"    p.payment_amount as currentAmount,\r\n" + 
				"    (\r\n" + 
				"        select payment_amount from proposal\r\n" + 
				"        where id = p.previous_proposal_id\r\n" + 
				"    ) as previousAmount,\r\n" + 
				"    p.previous_proposal_id,\r\n" + 
				"    ifnull(ii.description, ii.item_name) as itemDescription,\r\n" + 
				"    ii.usoc as usoc,\r\n" + 
				"    ii.line_item_code as lineItemCode,\r\n" + 
				"    ii.line_item_code_description as lineItemCodeDescription,\r\n" + 
				"    ii.start_date as fromDate,\r\n" + 
				"    ii.end_date as toDate,\r\n" + 
				"    'Tariff' as referenceType,\r\n" + 
				"    ar.rate as rate,\r\n" + 
				"    ar.rate_effective_date as rateEffectiveDate,\r\n" + 
				"    t.rate_mode AS rateMode,\r\n" + 
				"    pt.product_name as product,\r\n" + 
				"    pc.component_name as subProduct,\r\n" + 
				"    rrto.tariff_file_name as tariffFileName,\r\n" + 
				"    rrto.tariff_page as tariffPage,\r\n" + 
				"    rrto.part_section as tariffPartSection,\r\n" + 
				"    rrto.item_number as itemNumber,\r\n" + 
				"    rrto.crtc_number as crtcNumber,\r\n" + 
				"    rrto.rules_details as ruleDetails,\r\n" + 
				"    null as contractFileName,\r\n" + 
				"    null as contractTermMonth,\r\n" + 
				"    null as contractServiceScheduleName,\r\n" + 
				"    null as contractEarlyTerminationFee\r\n" + 
				"\r\n" + 
				"from audit_result ar \r\n" + 
				"    left join audit_status au on au.id = ar.audit_status_id \r\n" + 
				"    left join proposal p on ar.proposal_id = p.id\r\n" + 
				"    left join invoice i on i.id = p.invoice_id\r\n" + 
				"    left join invoice_item ii on p.invoice_item_id = ii.id\r\n" + 
				"    left join product pt on p.product_id = pt.id\r\n" + 
				"    left join product_component pc on p.product_component_id = pc.id\r\n" + 
				"    left join item_type it on it.id = p.item_type_id\r\n" + 
				"    left join tariff t on t.id = p.audit_reference_id\r\n" + 
				"    left join rate_rule_tariff_original rrto on rrto.audit_reference_mapping_id = p.audit_reference_mapping_id\r\n" + 
				"\r\n" + 
				"where ii.stripped_circuit_number = "+ searchInvoiceVO.getCricuitNumber() +"\r\n" + 
				"    and p.invoice_id = "+ searchInvoiceVO.getInvoiceId() +" \r\n" + 
				"    and ar.audit_status_id != 1\r\n" + 
				"    and ( p.item_type_id in (13, 15) or p.item_type_id LIKE '3%' or p.item_type_id LIKE '5%' )\r\n" + 
				"    and p.audit_reference_type_id not in (4001, 4)\r\n" + 
				"    and p.audit_reference_type_id = 2\r\n" + 
				"    and p.rec_active_flag = 'Y'\r\n" + 
				"    and ii.rec_active_flag = 'Y'\r\n" + 
				"    and p.proposal_flag = 1\r\n" + 
				"\r\n" + 
				"union\r\n" + 
				"\r\n" + 
				"select \r\n" + 
				"    p.id,\r\n" + 
				"    ar.audit_status_id,\r\n" + 
				"    au.audit_status_name,\r\n" + 
				"    ii.stripped_circuit_number as strippedCircuitNumber,\r\n" + 
				"    it.item_type_name as chargeType,\r\n" + 
				"    p.payment_amount as currentAmount,\r\n" + 
				"    (\r\n" + 
				"        select payment_amount from proposal\r\n" + 
				"        where id = p.previous_proposal_id\r\n" + 
				"    ) as previousAmount,\r\n" + 
				"    p.previous_proposal_id,\r\n" + 
				"    ifnull(ii.description, ii.item_name) as itemDescription,\r\n" + 
				"    ii.usoc as usoc,\r\n" + 
				"    ii.line_item_code as lineItemCode,\r\n" + 
				"    ii.line_item_code_description as lineItemCodeDescription,\r\n" + 
				"    ii.start_date as fromDate,\r\n" + 
				"    ii.end_date as toDate,\r\n" + 
				"    'Contract' as referenceType,\r\n" + 
				"    ar.rate as rate,\r\n" + 
				"    ar.rate_effective_date as rateEffectiveDate,\r\n" + 
				"    c.rate_mode AS rateMode,\r\n" + 
				"    pt.product_name as product,\r\n" + 
				"    pc.component_name as subProduct,\r\n" + 
				"    null as tariffFileName,\r\n" + 
				"    null as tariffPage,\r\n" + 
				"    null as tariffPartSection,\r\n" + 
				"    null as itemNumber,\r\n" + 
				"    null as crtcNumber,\r\n" + 
				"    null as ruleDetails,\r\n" + 
				"    rrto.contract_name as contractFileName,\r\n" + 
				"    rrto.term_months as contractTermMonth,\r\n" + 
				"    rrto.contract_service_schedule_name as contractServiceScheduleName,\r\n" + 
				"    rrto.early_termination_fee as contractEarlyTerminationFee\r\n" + 
				"\r\n" + 
				"from audit_result ar\r\n" + 
				"    left join audit_status au on au.id = ar.audit_status_id\r\n" + 
				"    left join proposal p on ar.proposal_id = p.id\r\n" + 
				"    left join invoice i on i.id = p.invoice_id\r\n" + 
				"    left join invoice_item ii on p.invoice_item_id = ii.id\r\n" + 
				"    left join product pt on p.product_id = pt.id\r\n" + 
				"    left join product_component pc on p.product_component_id = pc.id\r\n" + 
				"    left join item_type it on it.id = p.item_type_id\r\n" + 
				"    left join contract c on c.id = p.audit_reference_id\r\n" + 
				"    left join rate_rule_contract_original rrto on rrto.audit_reference_mapping_id = p.audit_reference_mapping_id\r\n" + 
				"\r\n" + 
				"where ii.stripped_circuit_number = "+ searchInvoiceVO.getCricuitNumber() +"\r\n" + 
				"    and p.invoice_id = "+ searchInvoiceVO.getInvoiceId() +"\r\n" + 
				"    and ar.audit_status_id != 1\r\n" + 
				"    and ( p.item_type_id in (13, 15) or p.item_type_id LIKE '3%' or p.item_type_id LIKE '5%' )\r\n" + 
				"    and p.audit_reference_type_id not in (4001, 4)\r\n" + 
				"    and p.audit_reference_type_id = 3\r\n" + 
				"    and p.rec_active_flag = 'Y'\r\n" + 
				"    and ii.rec_active_flag = 'Y'\r\n" + 
				"    and p.proposal_flag = 1\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"union\r\n" + 
				"\r\n" + 
				"select \r\n" + 
				"    p.id,\r\n" + 
				"    ar.audit_status_id,\r\n" + 
				"    au.audit_status_name,\r\n" + 
				"    ii.stripped_circuit_number as strippedCircuitNumber,\r\n" + 
				"    it.item_type_name as chargeType,\r\n" + 
				"    p.payment_amount as currentAmount,\r\n" + 
				"    (\r\n" + 
				"        select payment_amount from proposal\r\n" + 
				"        where id = p.previous_proposal_id\r\n" + 
				"    ) as previousAmount,\r\n" + 
				"    p.previous_proposal_id,\r\n" + 
				"    ifnull(ii.description, ii.item_name) as itemDescription,\r\n" + 
				"    ii.usoc as usoc,\r\n" + 
				"    ii.line_item_code as lineItemCode,\r\n" + 
				"    ii.line_item_code_description as lineItemCodeDescription,\r\n" + 
				"    ii.start_date as fromDate,\r\n" + 
				"    ii.end_date as toDate,\r\n" + 
				"    'Vendor Rate' as referenceType,\r\n" + 
				"    ar.rate as rate,\r\n" + 
				"    ar.rate_effective_date as rateEffectiveDate,\r\n" + 
				"    am.rate_mode AS rateMode,\r\n" + 
				"    pt.product_name as product,\r\n" + 
				"    pc.component_name as subProduct,\r\n" + 
				"    null as tariffFileName,\r\n" + 
				"    null as tariffPage,\r\n" + 
				"    null as tariffPartSection,\r\n" + 
				"    null as itemNumber,\r\n" + 
				"    null as crtcNumber,\r\n" + 
				"    null as ruleDetails,\r\n" + 
				"    null as contractFileName,\r\n" + 
				"    null as contractTermMonth,\r\n" + 
				"    null as contractServiceScheduleName,\r\n" + 
				"    null as contractEarlyTerminationFee\r\n" + 
				"\r\n" + 
				"from audit_result ar\r\n" + 
				"    left join audit_status au on au.id = ar.audit_status_id\r\n" + 
				"    left join proposal p on ar.proposal_id = p.id\r\n" + 
				"    left join invoice i on i.id = p.invoice_id\r\n" + 
				"    left join invoice_item ii on p.invoice_item_id = ii.id\r\n" + 
				"    left join product pt on p.product_id = pt.id\r\n" + 
				"    left join product_component pc on p.product_component_id = pc.id\r\n" + 
				"    left join item_type it on it.id = p.item_type_id\r\n" + 
				"    left join audit_mtm am on am.id = p.audit_reference_id\r\n" + 
				"    left join rate_rule_mtm_original rrto on rrto.audit_reference_mapping_id = p.audit_reference_mapping_id\r\n" + 
				"\r\n" + 
				"where ii.stripped_circuit_number = "+ searchInvoiceVO.getCricuitNumber() +"\r\n" + 
				"    and p.invoice_id = "+ searchInvoiceVO.getInvoiceId() +"\r\n" + 
				"    and ar.audit_status_id != 1\r\n" + 
				"    and ( p.item_type_id in (13, 15) or p.item_type_id LIKE '3%' or p.item_type_id LIKE '5%' )\r\n" + 
				"    and p.audit_reference_type_id not in (4001, 4)\r\n" + 
				"    and p.audit_reference_type_id = 18\r\n" + 
				"    and p.rec_active_flag = 'Y'\r\n" + 
				"    and ii.rec_active_flag = 'Y'\r\n" + 
				"    and p.proposal_flag = 1 ");
		
		sb.append(") r");
		
		return sb.toString();
	}

	@Override
	public List<String> listVarianceItemsListings(SearchInvoiceVO searchInvoiceVO) {
		
		logger.info("Enter method listVarianceItemsListings.");
        StringBuffer sb = new StringBuffer();

        sb.append( this.listVarianceItemsListingsQueryString(searchInvoiceVO) );
        
        if (searchInvoiceVO.getFilter() != null) {
        	sb.append( " WHERE " + searchInvoiceVO.getFilter() + " " );
        }
        
        sb.append(" GROUP BY r.id" );
        
        // 排序条件。
        sb.append(" ORDER BY " + searchInvoiceVO.getSortField() + " " + searchInvoiceVO.getSortingDirection());
        
        // 分页限制条件。
        sb.append(" " + searchInvoiceVO.getLimitCause() + " ");

        final String sql = sb.toString();

        HibernateTemplate template = this.getHibernateTemplate();
        List<String> mrcVarianceList = (List<String>) template.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        return session.createSQLQuery(sql).list();
                    }
                });
        logger.info("Exit method listVarianceItemsListings.");
        return mrcVarianceList;
	}

	private String listVarianceItemsListingsQueryString(SearchInvoiceVO searchInvoiceVO) {
		
		logger.info("Enter method listVarianceItemsListingsQueryString.");
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT CONCAT('{audit_status_id: \"', toJSON(r.audit_status_id IS NULL, '', r.audit_status_id), ");
        sb.append(" '\",audit_status_name: \"', toJSON( r.audit_status_name IS NULL, '', r.audit_status_name ), ");
        sb.append(" '\",id: \"', toJSON( r.id IS NULL, '', r.id ), ");
        sb.append(" '\",strippedCircuitNumber: \"', toJSON( r.strippedCircuitNumber IS NULL, '', r.strippedCircuitNumber ), ");
        sb.append(" '\",chargeType: \"', toJSON(r.chargeType IS NULL, '', r.chargeType ), ");
        sb.append(" '\",currentAmount: \"', toJSON(r.currentAmount IS NULL, '', FORMAT(r.currentAmount,2) ), ");
        sb.append(" '\",previoutAmount: \"', toJSON(r.previousAmount IS NULL, '', FORMAT(r.previousAmount, 2) ), ");
        
        sb.append(" '\",itemDescription: \"', toJSON(r.itemDescription IS NULL, '', r.itemDescription), ");
        sb.append(" '\",usoc: \"', toJSON(r.usoc IS NULL, '',r.usoc), ");
        sb.append(" '\",lineItemCode: \"', toJSON(r.lineItemCode IS NULL, '', r.lineItemCode), ");
        sb.append(" '\",lineItemCodeDescription: \"', toJSON(r.lineItemCodeDescription IS NULL, '',r.lineItemCodeDescription), ");
        
        sb.append(" '\",fromDate: \"', toJSON(r.fromDate IS NULL, '', r.fromDate), "); 
        sb.append(" '\",toDate: \"', toJSON(r.toDate IS NULL, '', r.toDate), "); 
        sb.append(" '\",referenceType: \"', toJSON(r.referenceType IS NULL, '', r.referenceType), ");
        sb.append(" '\",rate: \"', toJSON(r.rate IS NULL, '', format(r.rate, 5) ), ");
        sb.append(" '\",rateEffectiveDate: \"', toJSON(r.rateEffectiveDate IS NULL, '', r.rateEffectiveDate), ");
        sb.append(" '\",rateMode: \"', toJSON(r.rateMode IS NULL, '', r.rateMode), ");
        sb.append(" '\",product: \"', toJSON(r.product IS NULL, '', r.product), ");
        sb.append(" '\",subProduct: \"', toJSON(r.subProduct IS NULL, '', r.subProduct), ");
        sb.append(" '\",tariffFileName: \"', toJSON(r.tariffFileName IS NULL, '', r.tariffFileName), ");
        sb.append(" '\",tariffPage: \"', toJSON(r.tariffPage IS NULL, '', r.tariffPage), ");
        sb.append(" '\",tariffPartSection: \"', toJSON(r.tariffPartSection IS NULL, '', r.tariffPartSection), ");
        sb.append(" '\",itemNumber: \"', toJSON(r.itemNumber IS NULL, '', r.itemNumber), ");
        sb.append(" '\",crtcNumber: \"', toJSON(r.crtcNumber IS NULL, '', r.crtcNumber), ");
        sb.append(" '\",ruleDetails: \"', toJSON(r.ruleDetails IS NULL, '', r.ruleDetails), ");
        sb.append(" '\",contractFileName: \"', toJSON(r.contractFileName IS NULL, '', r.contractFileName), ");
        sb.append(" '\",contractTermMonth: \"', toJSON(r.contractTermMonth IS NULL, '', r.contractTermMonth), ");
        sb.append(" '\",contractServiceScheduleName: \"', toJSON(r.contractServiceScheduleName IS NULL, '', r.contractServiceScheduleName), ");
        sb.append(" '\",contractEarlyTerminationFee: \"', toJSON(r.contractEarlyTerminationFee IS NULL, '', r.contractEarlyTerminationFee), ");
        sb.append(" '\"}')  FROM "); 

        sb.append( 	this.organizeVarianceItemsSqlStatements(searchInvoiceVO) );
        
        logger.info("Exit method listVarianceItemsListingsQueryString.");
        return sb.toString();
	}

	@Override
	public long countTwoMonthsVarianceProposalItemsNo(SearchInvoiceVO searchInvoiceVO) {
		
		logger.info("Enter method countTwoMonthsVarianceProposalItemsNo.");
        
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT COUNT(1)");
//        sb.append(this.organizeVarianceItemsFromAndWhereSqlStatements(searchInvoiceVO) );
        sb.append(this.organizeTwoMonthsVarianceProposalItemsFromAndWhereSqlStatements(searchInvoiceVO) );
        
        final String sql = sb.toString();
        
        HibernateTemplate template = this.getHibernateTemplate();
        
        Integer count = (Integer) template.execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session)throws HibernateException, SQLException {
                List l = session.createSQLQuery(sql).list();
                return new Integer(l.get(0).toString());
            }
        });
        
        logger.info("Exit method countTwoMonthsVarianceProposalItemsNo.");
        return count;
	}

	private String organizeTwoMonthsVarianceProposalItemsFromAndWhereSqlStatements(SearchInvoiceVO searchInvoiceVO) {
		
		StringBuffer sb = new StringBuffer();
		
		String lastInvoiceId = this.getLastInvoiceId( searchInvoiceVO.getInvoiceId() );
		
		sb.append(" from audit_result ar\r\n" + 
				"    left join proposal p on ar.proposal_id = p.id\r\n" + 
				"    left join account_code ac on ac.id = p.account_code_id\r\n" + 
				"    left join invoice i on i.id = p.invoice_id\r\n" + 
				"    left join invoice_item ii on p.invoice_item_id = ii.id\r\n" + 
				"    left join item_type it on it.id = p.item_type_id\r\n" + 
				"    left join product pt on p.product_id = pt.id\r\n" + 
				"    left join product_component pc on p.product_component_id = pc.id\r\n" + 
				"where ii.stripped_circuit_number = "+ searchInvoiceVO.getCricuitNumber() +"\r\n" + 
				"    and (\r\n" + 
				"            ( p.invoice_id = "+ searchInvoiceVO.getInvoiceId() +" \r\n" + 
				"                and ( p.item_type_id in (13, 15) or p.item_type_id LIKE '3%' or p.item_type_id LIKE '5%') )\r\n" + 
				"            OR \r\n" + 
				"            (p.invoice_id = "+ lastInvoiceId +" and (p.item_type_id in (13) or p.item_type_id LIKE '3%') )\r\n" + 
				"        )\r\n" + 
				"    and ar.audit_reference_type_id not in (4001, 4)\r\n" + 
				"    and p.rec_active_flag = 'Y'\r\n" + 
				"    and ii.rec_active_flag = 'Y'\r\n" + 
				"    and p.proposal_flag = 1 ");
		
		return sb.toString();
	}

	private String getLastInvoiceId(String invoiceId) {
		
		StringBuffer sb = new StringBuffer();
		String LastInvoiceId = "";
		List<Map<String, String>> idMapList = null;
		
		sb.append(" SELECT bi.id as lastInvoiceId\r\n" + 
				"FROM invoice bi, invoice i\r\n" + 
				"WHERE bi.ban_id = i.ban_id\r\n" + 
				"    AND bi.invoice_date < i.invoice_date\r\n" + 
				"    AND bi.rec_active_flag = 'Y'\r\n" + 
				"    AND bi.invoice_status_id >= 9\r\n" + 
				"    AND bi.invoice_status_id NOT IN (80, 98)\r\n" + 
				"    AND i.id = "+ invoiceId +"\r\n" + 
				"ORDER BY bi.invoice_date DESC LIMIT 1 ");
		
		Session session = getSession();
		
		try{
			idMapList = session.createSQLQuery(sb.toString())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
			LastInvoiceId = String.valueOf( idMapList.get(0).get("lastInvoiceId") );
			
			return LastInvoiceId;
			
		} catch(Exception e)  {
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		
		return "";
		
	}

	@Override
	public List<String> listTwoMonthsVarianceProposalItemsListings(SearchInvoiceVO searchInvoiceVO) {

		logger.info("Enter method listTwoMonthsVarianceProposalItemsListings.");
        StringBuffer sb = new StringBuffer();

        sb.append( this.listTwoMonthsVarianceProposalItemsListingsQueryString(searchInvoiceVO) );
        
        if (searchInvoiceVO.getFilter() != null) {
        	sb.append( " WHERE " + searchInvoiceVO.getFilter() + " " );
        }

        // 排序条件。
        sb.append(" ORDER BY " + searchInvoiceVO.getSortField() + " " + searchInvoiceVO.getSortingDirection());
        
        // 分页限制条件。
        sb.append(" " + searchInvoiceVO.getLimitCause() + " ");

        final String sql = sb.toString();

        HibernateTemplate template = this.getHibernateTemplate();
        List<String> mrcVarianceList = (List<String>) template.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        return session.createSQLQuery(sql).list();
                    }
                });
        logger.info("Exit method listTwoMonthsVarianceProposalItemsListings.");
        return mrcVarianceList;
	}

	private String listTwoMonthsVarianceProposalItemsListingsQueryString(SearchInvoiceVO searchInvoiceVO) {
		
		logger.info("Enter method listTwoMonthsVarianceProposalItemsListingsQueryString.");
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT CONCAT('{proposalId: \"', toJSON(r.proposalId IS NULL, '', r.proposalId), ");
        sb.append(" '\",itemName: \"', toJSON( r.itemName IS NULL, '', r.itemName ), ");
        sb.append(" '\",paymentAmount: \"', toJSON( r.paymentAmount IS NULL, '', format(r.paymentAmount, 2) ), ");
        sb.append(" '\",disputeAmount: \"', toJSON( r.disputeAmount IS NULL, '', format(r.disputeAmount, 2) ), ");
        sb.append(" '\",creditAmount: \"', toJSON(r.creditAmount IS NULL, '', format(r.creditAmount, 2) ), ");
        sb.append(" '\",scoa: \"', toJSON(r.scoa IS NULL, '', r.scoa ), ");
        sb.append(" '\",circuitNumber: \"', toJSON(r.circuitNumber IS NULL, '', r.circuitNumber ), ");
        
        sb.append(" '\",strippedCircuitNumber: \"', toJSON(r.strippedCircuitNumber IS NULL, '', r.strippedCircuitNumber), ");
        sb.append(" '\",product: \"', toJSON(r.product IS NULL, '',r.product), ");
        sb.append(" '\",component: \"', toJSON(r.component IS NULL, '', r.component), ");
        sb.append(" '\",fromDate: \"', toJSON(r.fromDate IS NULL, '',r.fromDate), ");
        
        sb.append(" '\",toDate: \"', toJSON(r.toDate IS NULL, '', r.toDate), "); 
        sb.append(" '\",billingNumber: \"', toJSON(r.billingNumber IS NULL, '', r.billingNumber), ");
        sb.append(" '\",quantity: \"', toJSON(r.quantity IS NULL, '', r.quantity), ");
        sb.append(" '\",usoc: \"', toJSON(r.usoc IS NULL, '', r.usoc), ");
        sb.append(" '\",usocDescription: \"', toJSON(r.usocDescription IS NULL, '', r.usocDescription), ");
        sb.append(" '\",lineItemCode: \"', toJSON(r.lineItemCode IS NULL, '', r.lineItemCode), ");
        sb.append(" '\",lineItemCodeDescription: \"', toJSON(r.lineItemCodeDescription IS NULL, '', r.lineItemCodeDescription), ");
        sb.append(" '\"}')  FROM "); 
        
        sb.append( " ( SELECT p.id as proposalId, " ); 
        sb.append( "  ii.item_name as itemName, " ); 
        sb.append( "  p.payment_amount as paymentAmount, " ); 
        sb.append( "  p.dispute_amount as disputeAmount, " ); 
        sb.append( "  p.credit_amount as creditAmount, " ); 
        sb.append( "  ac.account_code_name  as scoa, " ); 
        sb.append( "  ii.circuit_number as circuitNumber, " ); 
        sb.append( "  ii.stripped_circuit_number as strippedCircuitNumber, " ); 
        sb.append( "  pt.product_name as product, " ); 
        sb.append( "  pc.component_name as component, " ); 
        sb.append( "  ii.start_date as fromDate, " ); 
        sb.append( "  ii.end_date as toDate, " ); 
        sb.append( "  p.billing_number as billingNumber, " ); 
        sb.append( "  p.quantity as quantity, " ); 
        sb.append( "  ii.usoc as usoc, " ); 
        sb.append( "  ii.usoc_description as usocDescription, " ); 
        sb.append( "  ii.line_item_code as lineItemCode, " ); 
        sb.append( "  ii.line_item_code_description as lineItemCodeDescription " ); 
        sb.append( 	this.organizeTwoMonthsVarianceProposalItemsFromAndWhereSqlStatements(searchInvoiceVO) ); 
        sb.append( ") r" ); 
        
        logger.info("Exit method listTwoMonthsVarianceProposalItemsListingsQueryString.");
        return sb.toString();
	}

}
