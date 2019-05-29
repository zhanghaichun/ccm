package com.saninco.ccm.dao.mtmRules;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.vo.mtmRules.SearchMtMRulesVO;

public class MtMRulesDaoImpl extends HibernateDaoSupport implements IMtMRulesDao {
    private final Logger logger = Logger.getLogger(this.getClass());

    /**
     * 获取页面中 Charge Type 下拉列表中的内容。
     */
    public List<String> listChargeType() {
        
        logger.info("Enter method listChargeType.");
        List<String> summaryVendorNameList = null;
        Session session = getSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("select charge_type from audit_reference_mapping ");
        sb.append("where audit_reference_type_id = 18 and charge_type is not null ");
        sb.append("and rec_active_flag = 'Y' group by charge_type order by charge_type ");
        
        try {
            summaryVendorNameList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listChargeType.");
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
        sb.append("where arm.audit_reference_type_id = 18 and  arm.key_field_original is not null ");
        sb.append("and arm.rec_active_flag='Y' group by arm.key_field_original order by arm.key_field_original");
        
        try {
            keyFieldList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listKeyField.");
            return keyFieldList;
        } finally {
            releaseSession(session);
        }
        
    }

    /**
     * 获取页面中 Summary Vendor Name 下拉列表中的内容。
     */
    public List<String> listSummaryVendorName() {
        
        logger.info("Enter method listSummaryVendorName.");
        List<String> summaryVendorNameList = null;
        Session session = getSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("select arm.summary_vendor_name from audit_reference_mapping arm ");
        sb.append("where arm.audit_reference_type_id = 18 and  arm.summary_vendor_name is not null ");
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
     * 获取页面中 Vendor Name  下拉列表中的内容。
     */
    public List<Object[]> listVendorName() {
        
        logger.info("Enter method listVendorName.");
        List<Object[]> vendorNameList = null;
        Session session = getSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("select v0.id,v0.vendor_name ");
        sb.append("from vendor v0 where v0.rec_active_flag='Y' and v0.vendor_name is not null ");
        sb.append("order by v0.vendor_name asc ");
        
        try {
        	vendorNameList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listVendorName.");
            return vendorNameList;
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
        sb.append("where arm.audit_reference_type_id = 18 and  arm.sub_product is not null ");
        sb.append("and arm.rec_active_flag='Y' group by arm.sub_product order by arm.sub_product ");
        
        try {
            subProductList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listSubProduct.");
            return subProductList;
        } finally {
            releaseSession(session);
        }
        
    }


    /**
     * 查询 MtM Rules 列表中的分页信息。
     */
    public long countMtMRulesViewListPageNo(SearchMtMRulesVO searchMtMRulesVO) {
        logger.info("Enter method countMtMRulesViewListPageNo.");
        
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(*) as tariffRulesCounts ");
        sb.append( "from ( ");
        sb.append( mtmListQueryString(searchMtMRulesVO) );
        sb.append( ") t" );
        sb.append( mtmRulesWhereConditions(searchMtMRulesVO,false) );
        
        final String sql = sb.toString();
        
        HibernateTemplate template = this.getHibernateTemplate();
        
        Integer count = (Integer) template.execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session)throws HibernateException, SQLException {
                List l = session.createSQLQuery(sql).list();
                return new Integer(l.get(0).toString());
            }
        });
        logger.info("Exit method countMtMRulesViewListPageNo.");
        return count;
    }

    /**
     * MtM Rules 列表查询 SQL语句中的 from , where 条件
     */
    private String mtmRulesWhereConditions(SearchMtMRulesVO searchMtMRulesVO,boolean isExcel) {

        logger.info("Enter method mtmRulesWhereConditions.");
        StringBuffer sb = new StringBuffer();
        
        if (!isExcel){
        	sb.append(" where 1 = 1 ");
        }
        if (searchMtMRulesVO != null) {
            
            if ( searchMtMRulesVO.getKeyField() != null ) { 
//                String keyField = StringHandling.sqlStringReplace( searchMtMRulesVO.getKeyField() );
                sb.append("AND t.keyField = '" + searchMtMRulesVO.getKeyField() + "' ");
            }
            
            if ( searchMtMRulesVO.getSubProduct() != null ) { 
                String subProduct = StringHandling.sqlStringReplace( searchMtMRulesVO.getSubProduct() );
                sb.append("AND t.subProduct = '" + subProduct + "' ");
            }
            
            if ( searchMtMRulesVO.getStrippedCircuitNumber() != null ) { 
            	String strippedCircuitNumber = StringHandling.sqlStringReplace( searchMtMRulesVO.getStrippedCircuitNumber() );
            	sb.append("AND t.strippedCircuitNumber like '%" + strippedCircuitNumber + "%' ");
            }
            
            if ( searchMtMRulesVO.getChargeType() != null ) {
                String chargeType = StringHandling.sqlStringReplace( searchMtMRulesVO.getChargeType() );
                sb.append("AND t.chargeType = '" + chargeType + "' ");
            }
            
            if ( searchMtMRulesVO.getRate() != null ) {
            	String rate = StringHandling.sqlStringReplace( searchMtMRulesVO.getRate() );
            	sb.append("AND t.rate = '" + rate + "' ");
            }
            
            if ( searchMtMRulesVO.getRateEffectiveDate() != null ) {
            	String rateEffectiveDate = StringHandling.sqlStringReplace( searchMtMRulesVO.getRateEffectiveDate() );
            	sb.append("AND t.rateEffectiveDate = '" + rateEffectiveDate + "' ");
            }
            
            if ( searchMtMRulesVO.getSummaryVendorName() != null ) {
                String summaryVendorName = StringHandling.sqlStringReplace( searchMtMRulesVO.getSummaryVendorName() );
                sb.append("AND t.summaryVendorName = '" + summaryVendorName + "' ");
            }
            
            if ( searchMtMRulesVO.getTerm() != null ) {
            	String term = StringHandling.sqlStringReplace( searchMtMRulesVO.getTerm() );
            	sb.append("AND t.term = '" + term + "' ");
            }
            
            if ( searchMtMRulesVO.getRateStatus() != null && !"".equals(searchMtMRulesVO.getRateStatus())) {
            	String rateStatus = StringHandling.sqlStringReplace( searchMtMRulesVO.getRateStatus() );
            	sb.append("AND t.rateStatus = '" + rateStatus + "' ");
            }
            
            if ( searchMtMRulesVO.getUsoc() != null ) {
                String usoc = StringHandling.sqlStringReplace( searchMtMRulesVO.getUsoc() );
                sb.append("AND t.usoc like '%" + usoc + "%' ");
            }
            
            if ( searchMtMRulesVO.getUsocDescription() != null ) {
                String usocDescription = StringHandling.sqlStringReplace( searchMtMRulesVO.getUsocDescription() );
                sb.append("AND t.usocDescription like '%" + usocDescription + "%' ");
            }
            
            if ( searchMtMRulesVO.getLineItemCode() != null ) {
                String lineItemCode = StringHandling.sqlStringReplace( searchMtMRulesVO.getLineItemCode() );
                sb.append("AND t.lineItemCode like '%" + lineItemCode + "%' ");
            }
            
            if ( searchMtMRulesVO.getLineItemCodeDescription() != null ) {
                String lineItemCodeDescription = StringHandling.sqlStringReplace( searchMtMRulesVO.getLineItemCodeDescription() );
                sb.append("AND t.lineItemCodeDescription like '%" + lineItemCodeDescription + "%' ");
            }  
            
            if ( searchMtMRulesVO.getItemDescription() != null ) {
                String itemDescription = StringHandling.sqlStringReplace( searchMtMRulesVO.getItemDescription() );
                sb.append("AND t.itemDescription like '%" + itemDescription + "%' ");
            } 
            
            if ( searchMtMRulesVO.getFilter() != null ) { // Filter
                sb.append( "AND " + searchMtMRulesVO.getFilter() + " " );
            }
        }
        
        
        logger.info("Exit method mtmRulesWhereConditions.");
        return sb.toString();
    }

    /**
     * 从数据库中查询 mtm Rules列表信息。
     */
     public List<String> listMtMRules(SearchMtMRulesVO searchMtMRulesVO) {

        logger.info("Enter method listMtMRules.");

        final String sql = this.listMtMRulesQueryString(searchMtMRulesVO);

        HibernateTemplate template = this.getHibernateTemplate();
        List<String> mtmRulesList = (List<String>) template.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        return session.createSQLQuery(sql).list();
                    }
                });
        logger.info("Exit method listMtMRules.");
        return mtmRulesList;
    }

    /**
     * MtM rules列表信息查询的 SQL语句。
     */
    private String listMtMRulesQueryString(SearchMtMRulesVO searchMtMRulesVO) {

        logger.info("Enter method listMtMRulesQueryString.");
        StringBuffer sb = new StringBuffer();
        
        
        sb.append(" SELECT CONCAT('{ruleID: ', toJSON(ruleID IS NULL, '', ruleID), ");
        sb.append(" ',chargeType: \"', toJSON(chargeType IS NULL, '', chargeType), ");
        sb.append(" '\",rateStatus: \"', toJSON(rateStatus IS NULL, '', rateStatus), ");
        sb.append(" '\",summaryVendorName: \"', toJSON(summaryVendorName IS NULL, '', summaryVendorName), ");
        sb.append(" '\",keyField: \"', toJSON(keyField IS NULL, '', keyField), ");
        sb.append(" '\",usoc: \"', toJSON(usoc IS NULL, '', usoc), ");
        sb.append(" '\",usocDescription: \"', toJSON(usocDescription IS NULL, '', usocDescription), ");
        sb.append(" '\",strippedCircuitNumber: \"', toJSON(strippedCircuitNumber IS NULL, '', strippedCircuitNumber), ");
        sb.append(" '\",subProduct: \"', toJSON(subProduct IS NULL, '', subProduct), ");
        sb.append(" '\",rate: \"', toJSON(rate IS NULL, '', rate), ");
        sb.append(" '\",rateEffectiveDate: \"', toJSON(rateEffectiveDate IS NULL, '', rateEffectiveDate), ");
        sb.append(" '\",term: \"', toJSON(term IS NULL, '', term), ");
        sb.append(" '\",itemDescription: \"', toJSON(itemDescription IS NULL, '', itemDescription), ");
        sb.append(" '\",lineItemCode: \"', toJSON(lineItemCode IS NULL, '', lineItemCode ), ");
        sb.append(" '\",lineItemCodeDescription: \"', toJSON(lineItemCodeDescription IS NULL, '', lineItemCodeDescription), ");
        sb.append(" '\"}') from ("); // concat 函数结束符。
        sb.append( mtmListQueryString(searchMtMRulesVO) );
        sb.append( ") t" );
        sb.append( mtmRulesWhereConditions(searchMtMRulesVO,false) );
        
        // 排序条件。
        sb.append(" ORDER BY " + searchMtMRulesVO.getSortField() + " " + searchMtMRulesVO.getSortingDirection());
        
        // 分页限制条件。
        sb.append(" " + searchMtMRulesVO.getLimitCause() + " ");
        
        logger.info("Exit method listMtMRulesQueryString.");
        return sb.toString();
    }
    
    /**
     * MtM rules列表信息查询的 SQL语句。
     */
    private String mtmListQueryString(SearchMtMRulesVO searchMtMRulesVO) {

        logger.info("Enter method mtmListQueryString.");
        StringBuffer sb = new StringBuffer();
        
        
        sb.append("SELECT toJSON(arm.id IS NULL, '', arm.id) AS ruleId,");
        sb.append("arm.charge_type AS chargeType,");
        sb.append("arm.summary_vendor_name  AS summaryVendorName,");
        sb.append("(CASE WHEN (arp.end_date IS NULL OR arp.end_date = '') THEN 'Active' ELSE 'Inactive' END) AS rateStatus ,");
        sb.append("arm.key_field_original AS keyField,");
        sb.append("arm.usoc,");
        sb.append("arm.usoc_description AS usocDescription,");
        sb.append("arm.circuit_number AS strippedCircuitNumber,");
        sb.append("arm.sub_product AS subProduct,");
        sb.append("arp.rate as rate,");
        sb.append("DATE_FORMAT(arp.start_date, '%Y-%m-%d') AS rateEffectiveDate,");
        sb.append("am.term,");
        sb.append("arm.item_description AS itemDescription,");
        sb.append("arm.line_item_code AS lineItemCode,");
        sb.append("arm.line_item_code_description AS lineItemCodeDescription");
        
        sb.append(" FROM audit_mtm am ");

        sb.append(" LEFT JOIN audit_reference_mapping arm ON     am.id = arm.audit_reference_id AND arm.audit_reference_type_id = 18 ");

        sb.append(" LEFT JOIN audit_rate_period arp ON arp.reference_id = arm.audit_reference_id AND arp.reference_table = 'audit_mtm' ");

        sb.append(" where am.source = 'Rogers' ");

        sb.append(" AND arm.rec_active_flag = 'Y'");
        
        logger.info("Exit method mtmListQueryString.");
        return sb.toString();
    }

    /**
     * 下载contract rules列表
     */
    public List<Object[]> downloadMtMRulesToExcel(SearchMtMRulesVO searchMtMRulesVO) {

        logger.info("Enter method downloadMtMRulesToExcel.");
        Session session = getSession();
        StringBuffer sb = new StringBuffer();

        sb.append( downloadMtMRulesToExcelQueryString(searchMtMRulesVO) );

        sb.append(" ORDER BY " + searchMtMRulesVO.getSortField() + " " + searchMtMRulesVO.getSortingDirection());

        sb.append( " " + searchMtMRulesVO.getLimitCause() + " " );


        try {

            List<Object[]> mtmRulesExcelDataColumns = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method downloadMtMRulesToExcel.");
            return mtmRulesExcelDataColumns;

        } finally {
            releaseSession(session);
        }

            
        
    }

    /**
     * 将 MtM rules 下载到excel文件中用到的sql查询语句.
     */
    private String downloadMtMRulesToExcelQueryString(SearchMtMRulesVO searchMtMRulesVO) {

        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT ");
        sb.append(" toJSON(rateStatus IS NULL, '', rateStatus) AS rateStatus, ");
        sb.append(" toJSON(rateEffectiveDate IS NULL, '', rateEffectiveDate) AS rateEffectiveDate, ");
        sb.append(" toJSON(term IS NULL, '', term) AS term, ");
        sb.append(" toJSON(rate IS NULL, '', rate) AS rate, ");
        sb.append(" toJSON(strippedCircuitNumber IS NULL, '', strippedCircuitNumber) AS strippedCircuitNumber, ");
        sb.append(" toJSON(usoc IS NULL, '', usoc) AS usoc, ");
        sb.append(" toJSON(usocDescription IS NULL, '', usocDescription) AS usocDescription, ");
        sb.append(" toJSON(lineItemCode IS NULL, '', lineItemCode ) AS lineItemCode, ");
        sb.append(" toJSON(lineItemCodeDescription IS NULL, '', lineItemCodeDescription) AS lineItemCodeDescription, ");
        sb.append(" toJSON(itemDescription IS NULL, '', itemDescription) AS itemDescription, ");
        sb.append(" toJSON(summaryVendorName IS NULL, '', summaryVendorName) AS summaryVendorName, ");
        sb.append(" toJSON(chargeType IS NULL, '', chargeType) AS chargeType, ");
        sb.append(" toJSON(subProduct IS NULL, '', subProduct) AS subProduct, ");
        sb.append(" toJSON(keyField IS NULL, '', keyField) AS keyField ");
        sb.append(" from ("); // concat 函数结束符。
        sb.append( mtmListQueryString(searchMtMRulesVO) );
        sb.append( ") t" );
        sb.append( mtmRulesWhereConditions(searchMtMRulesVO,false) );
//        sb.append( mtmListQueryString(searchMtMRulesVO) );
//        sb.append( mtmRulesWhereConditions(searchMtMRulesVO,true) );

        return sb.toString();
    }
}