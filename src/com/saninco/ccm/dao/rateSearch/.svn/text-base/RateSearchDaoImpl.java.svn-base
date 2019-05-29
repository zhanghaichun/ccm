package com.saninco.ccm.dao.rateSearch;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.vo.rateSearch.SearchRateSearchVO;

public class RateSearchDaoImpl extends HibernateDaoSupport implements IRateSearchDao {
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * 获取页面中 Summary Vendor Name 下拉列表中的内容。
     */
    public List<String> listSummaryVendorName() {
        
        logger.info("Enter method listSummaryVendorName.");
        List<String> summaryVendorNameList = null;
        Session session = getSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT r.summaryVendorName FROM ");
        sb.append( this.unionQuerySQLListResult() );
        sb.append(" WHERE r.summaryVendorName IS NOT NULL AND  r.summaryVendorName != '' ");
        sb.append(" ORDER BY r.summaryVendorName; ");

        try {
            summaryVendorNameList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listSummaryVendorName.");
            return summaryVendorNameList;
        } finally {
            releaseSession(session);
        }
        
    }
    
    /**
     * 获取页面中 Vendor Name  下拉列表中的内容。
     */
    public List<String> listVendorName() {
        
        logger.info("Enter method listVendorName.");
        List<String> vendorNameList = null;
        Session session = getSession();
        
        StringBuffer sb = new StringBuffer();

        /*sb.append(" SELECT v0.id,v0.vendor_name ");
        sb.append(" FROM vendor v0 WHERE v0.rec_active_flag='Y' AND v0.vendor_name IS NOT NULL ");
        sb.append(" AND v0.vendor_name != '' ");
        sb.append("ORDER BY v0.vendor_name ASC ");*/
        
        sb.append(" SELECT DISTINCT r.vendorName FROM ");
        sb.append( this.unionQuerySQLListResult() );
        sb.append(" WHERE r.vendorName IS NOT NULL AND  r.vendorName != '' ");
        sb.append(" ORDER BY r.vendorName;");
        
        try {
            vendorNameList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listVendorName.");
            return vendorNameList;
        } finally {
            releaseSession(session);
        }
        
    }


    /**
     * Contract #/Tariff Reference。
     */
    public List<String> listContractOrTariffName(String referenceType) {
        
        logger.info("Enter method listContractOrTariffName.");
        List<String> tariffNameList = null;
        Session session = getSession();
        
        
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT DISTINCT r.contractNumberOrTariffReference FROM ");
        sb.append( this.unionQuerySQLListResult() );
        sb.append(" WHERE r.contractNumberOrTariffReference IS NOT NULL ");
        sb.append(" AND  r.contractNumberOrTariffReference != '' ");
        
        if ( referenceType != null && !"".equals(referenceType) ) {
        	sb.append(" AND  r.referenceTypeName ='"+ referenceType +"' "  );
        }
        
        sb.append(" ORDER BY r.contractNumberOrTariffReference ");
        
        try {
            tariffNameList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listContractOrTariffName.");
            return tariffNameList;
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
        sb.append("SELECT DISTINCT r.subProduct FROM ");
        sb.append( this.unionQuerySQLListResult() );
        sb.append(" WHERE r.subProduct IS NOT NULL AND  r.subProduct != '' ");
        sb.append(" ORDER BY r.subProduct ;");
        
        try {
            subProductList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listSubProduct.");
            return subProductList;
        } finally {
            releaseSession(session);
        }
        
    }

    /*
    
        Get Rate Search count.
     */
    public long countRateSearchPageNo(SearchRateSearchVO searchRateSearchVO) {
        logger.info("Enter method countRateSearchPageNo.");
        
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(*) AS rateSearchCount FROM ");
        sb.append( this.unionQuerySQLListResult() );
        sb.append( rateSearchWhereConditions(searchRateSearchVO) );
        
        final String sql = sb.toString();
        
        HibernateTemplate template = this.getHibernateTemplate();
        
        Integer count = (Integer) template.execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session)throws HibernateException, SQLException {
                List l = session.createSQLQuery(sql).list();
                return new Integer(l.get(0).toString());
            }
        });
        logger.info("Exit method countRateSearchPageNo.");
        return count;
    }

    public long countContractSummaryPageNo(SearchRateSearchVO searchRateSearchVO) {
        logger.info("Enter method countContractSummaryPageNo.");
        
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(*) AS contractSummaryCount FROM ");
        sb.append( this.contractSummaryListResultSQL() );
        sb.append( rateSearchWhereConditions(searchRateSearchVO) );
        
        final String sql = sb.toString();
        
        HibernateTemplate template = this.getHibernateTemplate();
        
        Integer count = (Integer) template.execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session)throws HibernateException, SQLException {
                List l = session.createSQLQuery(sql).list();
                return new Integer(l.get(0).toString());
            }
        });
        logger.info("Exit method countRateSearchPageNo.");
        return count;
    }

    /* Query Rate Search list. */
    public List<String> listRateSearch(SearchRateSearchVO searchRateSearchVO) {

        logger.info("Enter method listRateSearch.");
        StringBuffer sb = new StringBuffer();

        sb.append( this.listRateSearchQueryString(searchRateSearchVO) );

        // 排序条件。
        sb.append(" ORDER BY " + searchRateSearchVO.getSortField() + " " + searchRateSearchVO.getSortingDirection());
        
        // 分页限制条件。
        sb.append(" " + searchRateSearchVO.getLimitCause() + " ");

        final String sql = sb.toString();

        HibernateTemplate template = this.getHibernateTemplate();
        List<String> rateSearchList = (List<String>) template.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        return session.createSQLQuery(sql).list();
                    }
                });
        logger.info("Exit method listRateSearch.");
        return rateSearchList;
    }

    public List<String> listContractSummary(SearchRateSearchVO searchRateSearchVO) {

        logger.info("Enter method listContractSummary.");
        StringBuffer sb = new StringBuffer();

        sb.append( this.listContractSummaryQueryString(searchRateSearchVO) );

        // 排序条件。
        sb.append(" ORDER BY " + searchRateSearchVO.getSortField() + " " + searchRateSearchVO.getSortingDirection());
        
        // 分页限制条件。
        sb.append(" " + searchRateSearchVO.getLimitCause() + " ");

        final String sql = sb.toString();

        HibernateTemplate template = this.getHibernateTemplate();
        List<String> rateSearchList = (List<String>) template.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        return session.createSQLQuery(sql).list();
                    }
                });
        logger.info("Exit method listContractSummary.");
        return rateSearchList;
    }


    private String listRateSearchQueryString(SearchRateSearchVO searchRateSearchVO) {

        logger.info("Enter method listRateSearchQueryString.");
        StringBuffer sb = new StringBuffer();
        
        
        sb.append(" SELECT CONCAT('{rateStatus: \"', toJSON(r.rateStatus IS NULL, '', r.rateStatus), ");

        sb.append(" '\",effectiveDate: \"', toJSON( r.effectiveDate IS NULL, '', date_format(r.effectiveDate, '%Y-%m-%d') ), ");
        sb.append(" '\",rate: \"', toJSON(r.rate IS NULL, '', format(r.rate,6) ), ");
        sb.append(" '\",strippedCircuitNumber: \"', toJSON(r.strippedCircuitNumber IS NULL, '', r.strippedCircuitNumber), ");
        sb.append(" '\",baseAmount: \"', toJSON(r.baseAmount IS NULL, '',r.baseAmount), "); 
        sb.append(" '\",multiplier: \"', toJSON(r.multiplier IS NULL, '', r.multiplier), "); 
        sb.append(" '\",quantityBegin: \"', toJSON(r.quantityBegin IS NULL, '', r.quantityBegin), ");

        sb.append(" '\",quantityEnd: \"', toJSON(r.quantityEnd IS NULL, '', r.quantityEnd), ");
        sb.append(" '\",discount: \"', toJSON(r.discount IS NULL, '', format(r.discount, 2)), ");
        sb.append(" '\",referenceTypeName: \"', toJSON(r.referenceTypeName IS NULL, '', r.referenceTypeName), "); 
        sb.append(" '\",term: \"', toJSON(r.term IS NULL, '', r.term), "); 
        sb.append(" '\",referenceName: \"', toJSON(r.contractNumberOrTariffReference IS NULL, '', r.contractNumberOrTariffReference), "); 
        sb.append(" '\",tariffPage: \"', toJSON(r.tariffPage IS NULL, '', r.tariffPage), "); 
        sb.append(" '\",code: \"', toJSON(r.code IS NULL, '', r.code), ");
        sb.append(" '\",description: \"', toJSON(r.description IS NULL, '', r.description), ");
//        sb.append(" '\",pdfPage: \"', toJSON(r.pdfPage IS NULL, '', r.pdfPage), "); 
        // sb.append(" '\",usoc: \"', toJSON(r.usoc IS NULL, '', r.usoc), ");

        // sb.append(" '\",usocDescription: \"', toJSON(r.usocLongDescription IS NULL, '', r.usocLongDescription), ");
        // sb.append(" '\",lineItemCode: \"', toJSON(r.lineItemCode IS NULL, '', r.lineItemCode ), ");
        // sb.append(" '\",lineItemCodeDescription: \"', toJSON(r.lineItemCodeDescription IS NULL, '', r.lineItemCodeDescription), ");
        sb.append(" '\",itemDescription: \"', toJSON(r.itemDescription IS NULL, '', r.itemDescription), ");
        sb.append(" '\",itemType: \"', toJSON(r.itemType IS NULL, '', r.itemType), ");
        sb.append(" '\",summaryVendorName: \"', toJSON(r.summaryVendorName IS NULL, '', r.summaryVendorName), ");
        sb.append(" '\",vendorName: \"', toJSON(r.vendorName IS NULL, '', r.vendorName ), ");
        sb.append(" '\",chargeType: \"', toJSON(r.chargeType IS NULL, '', r.chargeType), ");
        sb.append(" '\",subProduct: \"', toJSON(r.subProduct IS NULL, '', r.subProduct), ");
        sb.append(" '\",detailDescription: \"', toJSON(r.detailsDescription IS NULL, '', r.detailsDescription ), "); 
        sb.append(" '\",keyField: \"', toJSON(r.keyField IS NULL, '', r.keyField), ");


        sb.append(" '\"}')  FROM "); 
        sb.append( this.unionQuerySQLListResult() ); 
        
        sb.append( this.rateSearchWhereConditions(searchRateSearchVO) );
        
        logger.info("Exit method listRateSearchQueryString.");
        return sb.toString();
    }

    private String listContractSummaryQueryString(SearchRateSearchVO searchRateSearchVO)  {

        logger.info("Enter method listContractSummaryQueryString.");
        StringBuffer sb = new StringBuffer();
        
        
        sb.append(" SELECT CONCAT('{summaryVendorName: \"', toJSON(s.summaryVendorName IS NULL, '', s.summaryVendorName), ");

        sb.append(" '\",contractName: \"', toJSON( s.contractName IS NULL, '', s.contractName ), ");
        sb.append(" '\",serviceScheduleName: \"', toJSON(s.serviceScheduleName IS NULL, '', s.serviceScheduleName ), ");
        sb.append(" '\",effectiveDate: \"', toJSON(s.effectiveDate IS NULL, '', DATE_FORMAT(s.effectiveDate, '%Y-%m-%d') ), ");
        sb.append(" '\",termQuantity: \"', toJSON(s.termQuantity IS NULL, '',s.termQuantity), "); 
        sb.append(" '\",renewalTerm: \"', toJSON(renewalTerm IS NULL, '',renewalTerm), "); 
        sb.append(" '\",expirationPeriod: \"', toJSON(s.expirationPeriod IS NULL, '', s.expirationPeriod), "); 
        sb.append(" '\"}')  FROM "); 
        sb.append( this.contractSummaryListResultSQL() ); 
        
        sb.append( this.rateSearchWhereConditions(searchRateSearchVO) );
        
        logger.info("Exit method listRateSearchQueryString.");
        return sb.toString();
    }

    /*Download rate search*/
    public List<Object[]> downloadRateSearch(SearchRateSearchVO searchRateSearchVO) {

        logger.info("Enter method downloadRateSearch.");
        Session session = getSession();
        StringBuffer sb = new StringBuffer();

        sb.append( downloadRateSearchQueryString(searchRateSearchVO) );

        sb.append(" ORDER BY " + searchRateSearchVO.getSortField() + " " + searchRateSearchVO.getSortingDirection());

        sb.append( " " + searchRateSearchVO.getLimitCause() + " " );


        try {

            List<Object[]> rateSearchExcelDataColumns = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method downloadRateSearch.");
            return rateSearchExcelDataColumns;

        } finally {
            releaseSession(session);
        }

            
        
    }
    
    public List<Object[]> downloadRateTemplate(SearchRateSearchVO searchRateSearchVO){
    	
    	logger.info("Enter method downloadRateTemplate.");
    	Session session = getSession();
    	StringBuffer sb = new StringBuffer();
    	if(searchRateSearchVO.getReferenceType().equals("Tariff")){
    		sb.append( downloadTariffRateTemplateQueryString(searchRateSearchVO) );
		}else if(searchRateSearchVO.getReferenceType().equals("MtM")){
			sb.append( downloadMtMRateTemplateQueryString(searchRateSearchVO) );
		}else if(searchRateSearchVO.getReferenceType().equals("Contract")){
			sb.append( downloadContractRateTemplateQueryString(searchRateSearchVO) );
		}
    	sb.append( " " + searchRateSearchVO.getLimitCause() + " " );
    	try {
    		List<Object[]> rateSearchExcelDataColumns = session.createSQLQuery(sb.toString()).list();
    		logger.info("Exit method downloadRateTemplate.");
    		return rateSearchExcelDataColumns;
    	} finally {
    		releaseSession(session);
    	}
    }

    private String downloadTariffRateTemplateQueryString(SearchRateSearchVO searchRateSearchVO){
    	StringBuffer sb = new StringBuffer();
    	sb.append(" SELECT toJSON(t.id IS NULL, '', t.id) AS 'ID',");
    	sb.append("  t.charge_type AS 'Charge Type %Contains%',");
    	sb.append("  t.key_field AS 'Key Field*',");
    	sb.append("  date_format(t.rate_effective_date, '%Y-%m-%d') AS 'Rate Effective Date',");
    	sb.append("  t.summary_vendor_name AS 'Summary Vendor Name (SVN) %Contains%',");
    	sb.append("  t.vendor_name AS 'Vendor Name (VN)  %Contains%',");
    	sb.append("  t.usoc AS 'USOC',");
    	sb.append("  t.usoc_description AS 'USOC Description',");
    	sb.append("  t.sub_product AS 'Sub Product',");
    	sb.append("  t.line_item_code_description AS 'Line Item Code Description',");
    	sb.append("  t.line_item_code AS 'Line Item Code',");
    	sb.append("  t.item_type AS 'Item Type',");
    	sb.append("  t.item_description AS 'Item Description',");
    	sb.append("  toJSON(t.quantity_begin IS NULL, '', t.quantity_begin) AS 'Qty Begin',");
    	sb.append("  toJSON(t.quantity_end IS NULL, '', t.quantity_end) AS 'Qty End',");
    	sb.append("  t.tariff_file_name AS 'Tariff File Name',");
    	sb.append("  t.tariff_name AS 'Tariff Name',");
    	sb.append("  t.base_amount AS 'Base Amount',");
    	sb.append("  t.multiplier AS 'Multiplier',");
    	sb.append("  t.rate AS 'Rate',");
    	sb.append("  t.rules_details AS 'Details from Tariff',");
    	sb.append("  t.tariff_page AS 'Tariff Page',");
    	sb.append("  t.part_section AS 'Part Section',");
    	sb.append("  t.item_number AS 'Item #',");
    	sb.append("  t.crtc_number AS 'CRTC #',");
    	sb.append("  t.discount AS 'Discount',");
    	sb.append("  t.exclusion_ban AS 'Exclusion Ban',");
    	sb.append("  t.exclusion_item_description AS 'Exclusion Item Description',");
    	sb.append("  t.notes AS 'Notes'");
    	sb.append("   FROM rate_rule_tariff_original t");
    	
    	if ( searchRateSearchVO != null ) {

            sb.append(" WHERE t.rec_active_flag = 'Y' ");
            
            if(searchRateSearchVO.getSummaryVendorName() != null){
            	sb.append("AND t.summary_vendor_name LIKE '%"+ searchRateSearchVO.getSummaryVendorName() +"%' ");
            }
            
            if(searchRateSearchVO.getVendorName() != null){
            	sb.append("AND t.vendor_name LIKE '%"+ searchRateSearchVO.getVendorName() +"%' ");
            }
            
            if(searchRateSearchVO.getSubProduct() != null){
            	sb.append("AND t.sub_product LIKE '%"+ searchRateSearchVO.getSubProduct() +"%' ");
            }
            
            if(searchRateSearchVO.getContractNumberOrTariffReference() != null){
            	sb.append("AND t.tariff_name LIKE '%"+ searchRateSearchVO.getContractNumberOrTariffReference() +"%' ");
            }
            
            if(searchRateSearchVO.getCrtcNumber() != null){
            	sb.append("AND t.crtc_number LIKE '%"+ searchRateSearchVO.getCrtcNumber() +"%' ");
            }
            
            if(searchRateSearchVO.getPartOrSection() != null){
            	sb.append("AND t.part_section LIKE '%"+ searchRateSearchVO.getPartOrSection() +"%' ");
            }
            
            if(searchRateSearchVO.getItemNumber() != null){
            	sb.append("AND t.item_number LIKE '%"+ searchRateSearchVO.getItemNumber() +"%' ");
            }
            
            if(searchRateSearchVO.getTariffPage() != null){
            	sb.append("AND t.tariff_page LIKE '%"+ searchRateSearchVO.getTariffPage() +"%' ");
            }
            
            if(searchRateSearchVO.getCode() != null){
            	sb.append("AND (t.usoc LIKE '%"+ searchRateSearchVO.getCode() +"%' or t.line_item_code LIKE '%"+ searchRateSearchVO.getCode() +"%')");
            }
            
            if(searchRateSearchVO.getRate() != null){
            	sb.append("AND t.rate = "+ searchRateSearchVO.getRate() +" ");
            }
            
            if(searchRateSearchVO.getRateStatus() != null){
            	sb.append("AND t.rate_status = '"+ searchRateSearchVO.getRateStatus() +"' ");
            }
            
            if(searchRateSearchVO.getChargeType() != null){
            	sb.append("AND t.charge_type = '"+ searchRateSearchVO.getChargeType() +"' ");
            }
            
            if(searchRateSearchVO.getDescription() != null){
            	sb.append("AND (t.usoc_description LIKE '%"+ searchRateSearchVO.getDescription() +"%' OR t.line_item_code_description LIKE '%"+ searchRateSearchVO.getDescription() +"%'" +
            			"  OR t.item_description LIKE '%"+ searchRateSearchVO.getDescription() +"%')");
            }
            
            if(searchRateSearchVO.getItemType() != null){
            	sb.append("AND t.item_type = '"+ searchRateSearchVO.getItemType() +"' ");
            }
            
            if(searchRateSearchVO.getEffectiveDate() != null){
            	sb.append("AND t.rate_effective_date = '"+ searchRateSearchVO.getEffectiveDate() +"' ");
            }
    	}
    	return sb.toString();
    }
    
    private String downloadMtMRateTemplateQueryString(SearchRateSearchVO searchRateSearchVO){
    	StringBuffer sb = new StringBuffer();
    	sb.append(" SELECT toJSON(t.id IS NULL, '', t.id) AS 'ID',");
    	sb.append("  t.charge_type AS 'Charge Type %Contains%',");
    	sb.append("  t.summary_vendor_name AS 'Summary Vendor Name (SVN) %Contains%',");
    	sb.append("  t.key_field AS 'Key Field*',");
    	sb.append("  t.usoc AS 'USOC',");
    	sb.append("  t.usoc_description AS 'USOC Description',");
    	sb.append("  t.stripped_circuit_number AS 'Stripped Circuit Number',");
    	sb.append("  t.sub_product AS 'Sub Product',");
    	sb.append("  t.rate AS 'Rate',");
    	sb.append("  date_format(t.rate_effective_date, '%Y-%m-%d') AS 'Rate Effective Date',");
    	sb.append("  t.term AS 'Term',");
    	sb.append("  t.item_description AS 'Item Description',");
    	sb.append("  t.line_item_code AS 'Line Item Code',");
    	sb.append("  t.line_item_code_description AS 'Line Item Code Description'");
    	sb.append("   FROM rate_rule_mtm_original t");
        
    	if ( searchRateSearchVO != null ) {
    		
    		sb.append(" WHERE t.rec_active_flag = 'Y' ");
    		
    		if(searchRateSearchVO.getSummaryVendorName() != null){
    			sb.append("AND t.summary_vendor_name LIKE '%"+ searchRateSearchVO.getSummaryVendorName() +"%' ");
    		}
    		
    		if(searchRateSearchVO.getSubProduct() != null){
    			sb.append("AND t.sub_product LIKE '%"+ searchRateSearchVO.getSubProduct() +"%' ");
    		}
    		
    		if(searchRateSearchVO.getCode() != null){
//    			sb.append("AND t.line_item_code LIKE '%"+ searchRateSearchVO.getCode() +"%' ");
    			sb.append("AND (t.usoc LIKE '%"+ searchRateSearchVO.getCode() +"%' or t.line_item_code LIKE '%"+ searchRateSearchVO.getCode() +"%')");
    		}
    		
    		if(searchRateSearchVO.getRate() != null){
    			sb.append("AND t.rate = "+ searchRateSearchVO.getRate() +" ");
    		}
    		
    		if(searchRateSearchVO.getRateStatus() != null){
            	sb.append("AND t.rate_status = '"+ searchRateSearchVO.getRateStatus() +"' ");
            }
    		
    		if(searchRateSearchVO.getDescription() != null){
//    			sb.append("AND t.usoc_description LIKE '%"+ searchRateSearchVO.getDescription() +"%' ");
    			sb.append("AND (t.usoc_description LIKE '%"+ searchRateSearchVO.getDescription() +"%' OR t.line_item_code_description LIKE '%"+ searchRateSearchVO.getDescription() +"%'" +
            			"  OR t.item_description LIKE '%"+ searchRateSearchVO.getDescription() +"%')");
    		}
    		if(searchRateSearchVO.getEffectiveDate() != null){
    			sb.append("AND t.rate_effective_date = '"+ searchRateSearchVO.getEffectiveDate() +"' ");
    		}
    		
    		if(searchRateSearchVO.getStrippedCircuitNumber() != null){
    			String strippedCircuitNumber = StringHandling.sqlStringReplace( searchRateSearchVO.getStrippedCircuitNumber() );
                sb.append("AND t.stripped_circuit_number like '%" + strippedCircuitNumber + "%' ");
            }
    		if(searchRateSearchVO.getChargeType() != null){
    			sb.append("AND t.charge_type LIKE '%"+ searchRateSearchVO.getChargeType() +"%' ");
            }
    	}
    	return sb.toString();
    }
    
    private String downloadContractRateTemplateQueryString(SearchRateSearchVO searchRateSearchVO){
    	StringBuffer sb = new StringBuffer();
    	sb.append(" SELECT toJSON(t.id IS NULL, '', t.id) AS 'ID',");
    	sb.append("  t.summary_vendor_name AS 'Summary Vendor Name (SVN) %Contains%',");
    	sb.append("  t.charge_type AS 'Charge Type %Contains%',");
    	sb.append("  t.key_field AS 'Key Field*',");
    	sb.append("  t.usoc AS 'USOC',");
    	sb.append("  t.usoc_description AS 'USOC Description',");
    	sb.append("  t.stripped_circuit_number AS 'Stripped Circuit Number',");
    	sb.append("  t.sub_product AS 'Sub Product',");
    	sb.append("  t.rate AS 'Rate',");
    	sb.append("  date_format(t.rate_effective_date, '%Y-%m-%d') AS 'Rate Effective Date',");
    	sb.append("  t.term_months AS 'Term (Months)',");
    	sb.append("  t.renewal_term_after_term_expiration AS 'Renewal Term after Term Expiration',");
    	sb.append("  t.early_termination_fee AS 'Early Termination Fee',");
    	sb.append("  t.item_description AS 'Item Description',");
    	sb.append("  t.contract_name AS 'Contract Name',");
    	sb.append("  t.contract_service_schedule_name AS 'Contract Service Schedule Name',");
    	sb.append("  t.line_item_code AS 'Line Item Code',");
    	sb.append("  t.line_item_code_description AS 'Line Item Code Description',");
    	sb.append("  toJSON(t.total_volume_begin IS NULL, '', t.total_volume_begin) AS 'Total Volume Begin',");
    	sb.append("  toJSON(t.total_volume_end IS NULL, '', t.total_volume_end) AS 'Total Volume End',");
    	sb.append("  t.mmbc AS 'MMBC',");
    	sb.append("  t.discount AS 'Discount %',");
    	sb.append("  t.notes AS 'Notes'");
    	sb.append("   FROM rate_rule_contract_original t");
    	
    	if ( searchRateSearchVO != null ) {
    		
    		sb.append(" WHERE t.rec_active_flag = 'Y' ");
    		
    		if(searchRateSearchVO.getSummaryVendorName() != null){
    			sb.append("AND t.summary_vendor_name LIKE '%"+ searchRateSearchVO.getSummaryVendorName() +"%' ");
    		}
    		
    		if(searchRateSearchVO.getSubProduct() != null){
    			sb.append("AND t.sub_product LIKE '%"+ searchRateSearchVO.getSubProduct() +"%' ");
    		}
    		
    		if(searchRateSearchVO.getCode() != null){
//    			sb.append("AND t.line_item_code LIKE '%"+ searchRateSearchVO.getCode() +"%' ");
    			sb.append("AND (t.usoc LIKE '%"+ searchRateSearchVO.getCode() +"%' or t.line_item_code LIKE '%"+ searchRateSearchVO.getCode() +"%')");
    		}
    		
    		if(searchRateSearchVO.getRateStatus() != null){
            	sb.append("AND t.rate_status = '"+ searchRateSearchVO.getRateStatus() +"' ");
            }
    		
    		if(searchRateSearchVO.getRate() != null){
    			sb.append("AND t.rate = "+ searchRateSearchVO.getRate() +" ");
    		}
    		
    		if(searchRateSearchVO.getDescription() != null){
//    			sb.append("AND t.usoc_description LIKE '%"+ searchRateSearchVO.getDescription() +"%' ");
    			sb.append("AND (t.usoc_description LIKE '%"+ searchRateSearchVO.getDescription() +"%' OR t.line_item_code_description LIKE '%"+ searchRateSearchVO.getDescription() +"%'" +
            			"  OR t.item_description LIKE '%"+ searchRateSearchVO.getDescription() +"%')");
    		}
    		
    		if(searchRateSearchVO.getEffectiveDate() != null){
    			sb.append("AND t.rate_effective_date = '"+ searchRateSearchVO.getEffectiveDate() +"' ");
    		}
    		
    		if(searchRateSearchVO.getContractNumberOrTariffReference() != null){
            	sb.append("AND t.contract_name LIKE '%"+ searchRateSearchVO.getContractNumberOrTariffReference() +"%' ");
            }
    		
    		if(searchRateSearchVO.getStrippedCircuitNumber() != null){
    			String strippedCircuitNumber = StringHandling.sqlStringReplace( searchRateSearchVO.getStrippedCircuitNumber() );
                sb.append("AND t.stripped_circuit_number like '%" + strippedCircuitNumber + "%' ");
            }
    		
    		if(searchRateSearchVO.getChargeType() != null){
    			sb.append("AND t.charge_type LIKE '%"+ searchRateSearchVO.getChargeType() +"%' ");
            }
    	}
    	return sb.toString();
    }
    
    
    private String downloadRateSearchQueryString(SearchRateSearchVO searchRateSearchVO) {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");

        sb.append(" toJSON(r.rateStatus IS NULL, '', r.rateStatus), "); // status

        sb.append(" toJSON( r.effectiveDate IS NULL, '', date_format(r.effectiveDate, '%Y-%m-%d') ), "); // effective date
        sb.append(" toJSON(r.rate IS NULL, '', format(r.rate,6) ), ");
        sb.append(" toJSON(r.strippedCircuitNumber IS NULL, '', r.strippedCircuitNumber), ");
        sb.append(" toJSON(r.baseAmount IS NULL, '',r.baseAmount), ");
        sb.append(" toJSON(r.multiplier IS NULL, '', r.multiplier), ");
//        sb.append(" toJSON(r.province IS NULL, '', r.province), ");
        
        sb.append(" toJSON(r.quantityBegin IS NULL, '', r.quantityBegin), ");
        sb.append(" toJSON(r.quantityEnd IS NULL, '', r.quantityEnd), ");
//        sb.append(" toJSON(r.imbalanceStart IS NULL, '', r.imbalanceStart), ");
//        sb.append(" toJSON(r.imbalanceEnd IS NULL, '', r.imbalanceEnd), ");
        sb.append(" toJSON(r.discount IS NULL, '', format(r.discount, 2)), ");
        
        sb.append(" toJSON(r.referenceTypeName IS NULL, '', r.referenceTypeName), ");
        
        sb.append(" toJSON(r.term IS NULL, '', r.term), ");
        sb.append(" toJSON(r.contractNumberOrTariffReference IS NULL, '', r.contractNumberOrTariffReference), ");
        sb.append(" toJSON(r.tariffPage IS NULL, '', r.tariffPage), ");
        sb.append(" toJSON(r.code IS NULL, '', r.code), ");
        sb.append(" toJSON(r.description IS NULL, '', r.description), ");
//        sb.append(" toJSON(r.pdfPage IS NULL, '', r.pdfPage), ");
        // sb.append(" toJSON(r.usoc IS NULL, '', r.usoc), ");
        // sb.append(" toJSON(r.usocLongDescription IS NULL, '', r.usocLongDescription), ");
        // sb.append(" toJSON(r.lineItemCode IS NULL, '', r.lineItemCode ), ");
        // sb.append(" toJSON(r.lineItemCodeDescription IS NULL, '', r.lineItemCodeDescription), ");
        sb.append(" toJSON(r.itemDescription IS NULL, '', r.itemDescription), ");
        sb.append(" toJSON(r.itemType IS NULL, '', r.itemType), ");
        sb.append(" toJSON(r.summaryVendorName IS NULL, '', r.summaryVendorName), ");
        sb.append(" toJSON(r.vendorName IS NULL, '', r.vendorName ), ");
        sb.append(" toJSON(r.chargeType IS NULL, '', r.chargeType), ");
        sb.append(" toJSON(r.subProduct IS NULL, '', r.subProduct), ");
        sb.append(" toJSON(r.detailsDescription IS NULL, '', r.detailsDescription ) ");
        // sb.append(" toJSON(r.keyField IS NULL, '', r.keyField) FROM ");
        sb.append(" FROM ");

        sb.append( this.unionQuerySQLListResult() );
        sb.append( this.rateSearchWhereConditions(searchRateSearchVO) );

        return sb.toString();
    }

    /*Download Contract Summary*/
    public List<Object[]> downloadContractSummary(SearchRateSearchVO searchRateSearchVO) {

        logger.info("Enter method downloadContractSummary.");
        Session session = getSession();
        StringBuffer sb = new StringBuffer();

        sb.append( downloadContractSummaryQueryString(searchRateSearchVO) );

        sb.append(" ORDER BY " + searchRateSearchVO.getSortField() + " " + searchRateSearchVO.getSortingDirection());

        sb.append( " " + searchRateSearchVO.getLimitCause() + " " );


        try {

            List<Object[]> contractSummaryExcelDataColumns = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method downloadContractSummary.");
            return contractSummaryExcelDataColumns;

        } finally {
            releaseSession(session);
        }

            
        
    }

    private String downloadContractSummaryQueryString(SearchRateSearchVO searchRateSearchVO) {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");

        sb.append(" toJSON(s.summaryVendorName IS NULL, '', s.summaryVendorName), "); 
        sb.append(" toJSON(s.contractName IS NULL, '', s.contractName), "); 
        sb.append(" toJSON(s.serviceScheduleName IS NULL, '', s.serviceScheduleName), "); 

        sb.append(" toJSON( s.effectiveDate IS NULL, '', date_format(s.effectiveDate, '%Y-%m-%d') ), "); 
        sb.append(" toJSON(s.termQuantity IS NULL, '', s.termQuantity), "); 
        sb.append(" toJSON(s.renewalTerm IS NULL, '', s.renewalTerm), "); 
        
        sb.append(" toJSON(s.expirationPeriod IS NULL, '', s.expirationPeriod) FROM ");

        sb.append( this.contractSummaryListResultSQL() );
        sb.append( this.rateSearchWhereConditions(searchRateSearchVO) );

        return sb.toString();
    }

    /*  
     [Where] conditions.
    */
    private String rateSearchWhereConditions(SearchRateSearchVO searchRateSearchVO) {

        StringBuffer sb = new StringBuffer();

        if ( searchRateSearchVO != null ) {

            sb.append(" WHERE 1 = 1 ");
            
            if( "1".equals(searchRateSearchVO.getRateViewType()) ) { // Rate Search search conditions.
            	
            	if ( searchRateSearchVO.getVendorName() != null ) {
            		
            		String vendorName = StringHandling.sqlStringReplace( searchRateSearchVO.getVendorName() );
                    /*sb.append("AND r.vendorGroupId in (select vgv.vendor_group_id from vendor_group vg ");
                    sb.append("left join vendor_group_vendor vgv on vg.id = vgv.vendor_group_id ");
                    sb.append("where vgv.vendor_id = '"+ vendorName +"') ");*/
            		
            		sb.append("AND r.vendorName LIKE '%"+ vendorName +"%' ");
            	}
            	
            	if ( searchRateSearchVO.getSubProduct() != null ) { 
                    String subProduct = StringHandling.sqlStringReplace( searchRateSearchVO.getSubProduct() );
                    sb.append("AND r.subProduct like '%" + subProduct + "%' ");
                }
            	
            	
            	if ( searchRateSearchVO.getChargeType() != null && !"all".equals(searchRateSearchVO.getChargeType())) {
                    String chargeType = StringHandling.sqlStringReplace( searchRateSearchVO.getChargeType() );
                    sb.append("AND r.chargeType like '%" + chargeType + "%' ");
                }
         	
            	if ( searchRateSearchVO.getCrtcNumber() != null ) {
                    String crtcNumber = StringHandling.sqlStringReplace( searchRateSearchVO.getCrtcNumber() );
                    sb.append("AND r.crtcNumber = '" + crtcNumber + "' ");
                }
            	
            	if ( searchRateSearchVO.getPartOrSection() != null ) {
                    String partOrSection = StringHandling.sqlStringReplace( searchRateSearchVO.getPartOrSection() );
                    sb.append("AND r.partOrSection = '" + partOrSection + "' ");
                }
            	
            	if ( searchRateSearchVO.getItemNumber() != null ) {
                    String itemNumber = StringHandling.sqlStringReplace( searchRateSearchVO.getItemNumber() );
                    sb.append("AND r.itemNumber = '" + itemNumber + "' ");
                }
            	
            	if ( searchRateSearchVO.getTariffPage() != null ) {
                    String tariffPage = StringHandling.sqlStringReplace( searchRateSearchVO.getTariffPage() );
                    sb.append("AND r.tariffPage = '" + tariffPage + "' ");
                }
            	
            	if ( searchRateSearchVO.getCode() != null ) {
                    String code = StringHandling.sqlStringReplace( searchRateSearchVO.getCode() );
                    // sb.append("AND ( r.usoc = '" + code + "' OR ");
                    // sb.append(" r.lineItemCode = '" + code + "' ) ");
                    sb.append(" AND r.code = '" + code + "' ");
                }
            	
            	if ( searchRateSearchVO.getDescription() != null ) {
                    String description = StringHandling.sqlStringReplace( searchRateSearchVO.getDescription() );
                    // sb.append("AND ( r.usocLongDescription like '%" + description + "%' OR ");
                    // sb.append(" r.lineItemCodeDescription  like '%" + description + "%' OR ");
                    sb.append("AND ( r.description like '%" + description + "%' OR ");
                    sb.append(" r.itemDescription like '%" + description + "%' ) ");
                }
            	
            	
            	if ( searchRateSearchVO.getItemType() != null ) { 
                    String itemType = StringHandling.sqlStringReplace( searchRateSearchVO.getItemType() );
                    sb.append("AND r.itemType like '%" + itemType + "%' ");
                }
            	
            	if ( searchRateSearchVO.getRate() != null ) {
                    sb.append("AND r.rate = '" + searchRateSearchVO.getRate() + "' ");
                }
            	
            	if ( searchRateSearchVO.getRateStatus() != null && !"".equals(searchRateSearchVO.getRateStatus()) ) {
                    sb.append("AND r.rateStatus = '"+ searchRateSearchVO.getRateStatus() +"' ");
                }
            	
                
                if ( searchRateSearchVO.getReferenceType() != null && !"".equals(searchRateSearchVO.getReferenceType())) {
                    String referenceType = StringHandling.sqlStringReplace( searchRateSearchVO.getReferenceType() );
                    sb.append("AND r.referenceTypeName like '%" + referenceType + "%' ");
                }
                
                if ( searchRateSearchVO.getSummaryVendorName() != null ) {
                    String summaryVendorName = StringHandling.sqlStringReplace( searchRateSearchVO.getSummaryVendorName() );
                    sb.append("AND r.summaryVendorName like '%" + summaryVendorName + "%' ");
                }
                
                if ( searchRateSearchVO.getContractNumberOrTariffReference() != null ) {
                    String contractNumberOrTariffReference = StringHandling.sqlStringReplace( searchRateSearchVO.getContractNumberOrTariffReference() );
                    sb.append("AND r.contractNumberOrTariffReference like '%" + contractNumberOrTariffReference + "%' ");
                }

                if ( searchRateSearchVO.getStrippedCircuitNumber() != null ) {
                    String strippedCircuitNumber = StringHandling.sqlStringReplace( searchRateSearchVO.getStrippedCircuitNumber() );
                    sb.append("AND r.strippedCircuitNumber like '%" + strippedCircuitNumber + "%' ");
                }
                
                if ( searchRateSearchVO.getEffectiveDate() != null ) {
                    String effectiveDate = StringHandling.sqlStringReplace( searchRateSearchVO.getEffectiveDate() );
                    sb.append("AND r.effectiveDate = '" + effectiveDate + "' ");
                }
            	
            } else if ( "2".equals(searchRateSearchVO.getRateViewType()) ) { // Contract Summary search conditions.
            	
            	if ( searchRateSearchVO.getSummaryVendorName() != null ) {
                    String summaryVendorName = StringHandling.sqlStringReplace( searchRateSearchVO.getSummaryVendorName() );
                    sb.append("AND s.summaryVendorName like '%" + summaryVendorName + "%' ");
                }
                
                if ( searchRateSearchVO.getContractNumberOrTariffReference() != null ) {
                    String contractNumberOrTariffReference = StringHandling.sqlStringReplace( searchRateSearchVO.getContractNumberOrTariffReference() );
                    sb.append("AND s.contractName like '%" + contractNumberOrTariffReference + "%' ");
                }
                
                if ( searchRateSearchVO.getEffectiveDate() != null ) {
                    String effectiveDate = StringHandling.sqlStringReplace( searchRateSearchVO.getEffectiveDate() );
                    sb.append("AND s.effectiveDate = '" + effectiveDate + "' ");
                }
                
                if ( searchRateSearchVO.getExpirationPeriod() != null ) {
                    String expirationPeriod = StringHandling.sqlStringReplace( searchRateSearchVO.getExpirationPeriod() );
                    sb.append("AND s.expirationPeriod like '%" + expirationPeriod + "%' ");
                }
            }
            
            if ( searchRateSearchVO.getFilter() != null ) { // Filter
                sb.append( "AND " + searchRateSearchVO.getFilter() + " " );
            }

        }

        

        return sb.toString();
    }


    /*
    Union SQL.
     */
    private String unionQuerySQLListResult() {

        StringBuffer sb = new StringBuffer();

        /**
         * Combine [Contract, Tariff, Month to Month] 
         */
        sb.append(" (SELECT t.rate_status AS rateStatus, ");
        sb.append(" t.rate_effective_date AS effectiveDate, ");
        sb.append(" t.rate AS rate, ");
        sb.append(" NULL AS strippedCircuitNumber, ");
        sb.append(" t.base_amount AS baseAmount, ");
        sb.append(" t.multiplier AS multiplier, ");
        sb.append(" t.quantity_begin AS quantityBegin, ");
        sb.append(" t.quantity_end AS quantityEnd, ");
        sb.append(" t.discount AS discount, ");
        sb.append(" 'Tariff' AS referenceTypeName, ");
        sb.append(" NULL AS term, ");
        sb.append(" t.tariff_name AS contractNumberOrTariffReference, ");
        sb.append(" t.tariff_page AS tariffPage, ");
        sb.append(" ifnull(t.usoc, t.line_item_code) AS code, ");
        sb.append(" ifnull(t.usoc_description, t.line_item_code_description) AS description, ");
        // sb.append(" t.usoc AS usoc, ");
        // sb.append(" t.usoc_description AS usocLongDescription, ");
        // sb.append(" t.line_item_code AS lineItemCode, ");
        // sb.append(" t.line_item_code_description AS lineItemCodeDescription, ");
        sb.append(" t.item_description AS itemDescription, ");
        sb.append(" t.item_type AS itemType, ");
        sb.append(" t.summary_vendor_name AS summaryVendorName, ");
        sb.append(" t.vendor_name AS vendorName, ");
        sb.append(" t.charge_type AS chargeType, ");
        sb.append(" t.sub_product AS subProduct, ");
        sb.append(" t.rules_details AS detailsDescription, ");
        sb.append(" t.key_field AS keyField, ");
        sb.append(" t.part_section AS partOrSection, ");
        sb.append(" t.item_number AS itemNumber, ");
        sb.append(" t.crtc_number AS crtcNumber ");
        sb.append("           FROM rate_rule_tariff_original t ");
        sb.append("          WHERE t.rec_active_flag = 'Y' ");
        sb.append("         UNION ");
        sb.append("         SELECT m.rate_status AS rateStatus, ");
        sb.append(" m.rate_effective_date AS effectiveDate, ");
        sb.append(" m.rate AS rate, ");
        sb.append(" m.stripped_circuit_number AS strippedCircuitNumber, ");
        sb.append(" NULL AS baseAmount, ");
        sb.append(" NULL AS multiplier, ");
        sb.append(" NULL AS quantityBegin, ");
        sb.append(" NULL AS quantityEnd, ");
        sb.append(" NULL AS discount, ");
        sb.append(" 'MtM' AS referenceTypeName, ");
        sb.append(" NULL AS term, ");
        sb.append(" NULL AS contractNumberOrTariffReference, ");
        sb.append(" NULL AS tariffPage, ");
        sb.append(" ifnull(m.usoc, m.line_item_code) AS code, ");
        sb.append(" ifnull(m.usoc_description, m.line_item_code_description) AS description, ");
        // sb.append(" m.usoc AS usoc, ");
        // sb.append(" m.usoc_description AS usocLongDescription, ");
        // sb.append(" m.line_item_code AS lineItemCode, ");
        // sb.append(" m.line_item_code_description AS lineItemCodeDescription, ");
        sb.append(" m.item_description AS itemDescription, ");
        sb.append(" NULL AS itemType, ");
        sb.append(" m.summary_vendor_name AS summaryVendorName, ");
        sb.append(" NULL AS vendorName, ");
        sb.append(" m.charge_type AS chargeType, ");
        sb.append(" m.sub_product AS subProduct, ");
        sb.append(" NULL AS detailsDescription, ");
        sb.append(" m.key_field AS keyField, ");
        sb.append(" NULL AS partOrSection, ");
        sb.append(" NULL AS itemNumber, ");
        sb.append(" NULL AS crtcNumber ");
        sb.append("           FROM rate_rule_mtm_original m ");
        sb.append("          WHERE m.rec_active_flag = 'Y' ");
        sb.append("         UNION ");
        sb.append("         SELECT c.rate_status AS rateStatus, ");
        sb.append(" c.rate_effective_date AS effectiveDate, ");
        sb.append(" c.rate AS rate, ");
        sb.append(" c.stripped_circuit_number AS strippedCircuitNumber, ");
        sb.append(" NULL AS baseAmount, ");
        sb.append(" NULL AS multiplier, ");
        sb.append(" c.total_volume_begin AS quantityBegin, ");
        sb.append(" c.total_volume_end AS quantityEnd, ");
        sb.append(" c.discount AS discount, ");
        sb.append(" 'Contract' AS referenceTypeName, ");
        sb.append(" c.term_months AS term, ");
        sb.append(" c.contract_name AS contractNumberOrTariffReference, ");
        sb.append(" NULL AS tariffPage, ");
        sb.append(" ifnull(c.usoc, c.line_item_code) AS code, ");
        sb.append(" ifnull(c.usoc_description, c.line_item_code_description) AS description, ");
        // sb.append(" c.usoc AS usoc, ");
        // sb.append(" c.usoc_description AS usocLongDescription, ");
        // sb.append(" c.line_item_code AS lineItemCode, ");
        // sb.append(" c.line_item_code_description AS lineItemCodeDescription, ");
        sb.append(" c.item_description AS itemDescription, ");
        sb.append(" NULL AS itemType, ");
        sb.append(" c.summary_vendor_name AS summaryVendorName, ");
        sb.append(" NULL AS vendorName, ");
        sb.append(" c.charge_type AS chargeType, ");
        sb.append(" c.sub_product AS subProduct, ");
        sb.append(" NULL AS detailsDescription, ");
        sb.append(" c.key_field AS keyField, ");
        sb.append(" NULL AS partOrSection, ");
        sb.append(" NULL AS itemNumber, ");
        sb.append(" NULL AS crtcNumber ");
        sb.append("           FROM rate_rule_contract_original c ");
        sb.append("          WHERE c.rec_active_flag = 'Y') r ");

        return sb.toString();
    }

    /* List contract summary sql */
    private String contractSummaryListResultSQL() {

        StringBuffer sb = new StringBuffer();

        sb.append(" (SELECT ");
        sb.append(" c.summary_vendor_name AS summaryVendorName, ");
        sb.append(" c.contract_name AS contractName, ");
        sb.append(" c.contract_service_schedule_name AS serviceScheduleName, ");
        sb.append(" c.rate_effective_date AS effectiveDate, ");
        sb.append(" c.term_months AS termQuantity, ");
        sb.append(" c.renewal_term_after_term_expiration AS renewalTerm, ");
        sb.append(" FN_GET_EXPIRATION_PERIOD ( ");
        sb.append(" c.rate_effective_date, ");
        sb.append(" c.term_months ");
        sb.append(" ) AS expirationPeriod ");

        sb.append(" FROM ");

        sb.append(" rate_rule_contract_original c ");

        sb.append(" WHERE ");
        sb.append(" c.rec_active_flag = 'Y' ");
        sb.append(" GROUP BY ");
        sb.append(" summaryVendorName,contractName, ");
        sb.append(" serviceScheduleName) AS s ");

        return sb.toString();
    }

    public List<Object[]> queryKeyFieldListByReferenceType(Integer referenceTypeId){
    	logger.info("Enter method queryKeyFieldListByReferenceType.");
    	Session session = getSession();
    	StringBuffer sb = new StringBuffer();
    	sb.append("select key_field_original,'' from audit_key_field where audit_reference_type_id = "+referenceTypeId+" order by key_field_original");
    	try {
    		List<Object[]> rateSearchExcelDataColumns = session.createSQLQuery(sb.toString()).list();
    		logger.info("Exit method queryKeyFieldListByReferenceType.");
    		return rateSearchExcelDataColumns;
    	} finally {
    		releaseSession(session);
    	}
    } 
    
}