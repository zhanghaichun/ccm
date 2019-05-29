package com.saninco.ccm.dao.tariffRules;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.vo.tariffRules.SearchTariffRulesVO;

public class TariffRulesDaoImpl extends HibernateDaoSupport implements ITariffRulesDao {
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
        sb.append("where audit_reference_type_id = 2 and charge_type is not null ");
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
        sb.append("where arm.audit_reference_type_id = 2 and  arm.key_field_original is not null ");
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
        sb.append("where arm.audit_reference_type_id = 2 and  arm.summary_vendor_name is not null ");
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
     * 获取页面中 Tariff Name 下拉列表中的内容。
     */
    public List<String> listTariffName() {
        
        logger.info("Enter method listTariffName.");
        List<String> tariffNameList = null;
        Session session = getSession();
        
        StringBuffer sb = new StringBuffer();
        sb.append("select t.name from audit_reference_mapping arm ");
        sb.append("left join tariff t on t.id = arm.audit_reference_id ");
        sb.append("where arm.audit_reference_type_id = 2 and  arm.key_field_original is not null ");
        sb.append("and arm.rec_active_flag='Y' and t.rec_active_flag = 'Y' group by t.name order by t.name ");
        
        try {
        	tariffNameList = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method listTariffName.");
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
        sb.append("select arm.sub_product from audit_reference_mapping arm ");
        sb.append("where arm.audit_reference_type_id = 2 and  arm.sub_product is not null ");
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
     * 查询 Tariff Rules 列表中的分页信息。
     */
    public long countTariffRulesViewListPageNo(SearchTariffRulesVO searchTariffRulesVO) {
        logger.info("Enter method countTariffRulesViewListPageNo.");
        
        StringBuffer sb = new StringBuffer();
        sb.append(" select count(*) as tariffRulesCounts ");
        sb.append( tariffRulesWhereConditions(searchTariffRulesVO) );
        
        final String sql = sb.toString();
        
        HibernateTemplate template = this.getHibernateTemplate();
        
        Integer count = (Integer) template.execute(new HibernateCallback() {
            
            public Object doInHibernate(Session session)throws HibernateException, SQLException {
                List l = session.createSQLQuery(sql).list();
                return new Integer(l.get(0).toString());
            }
        });
        logger.info("Exit method countTariffRulesViewListPageNo.");
        return count;
    }

    /**
     * Tariff Rules 列表查询 SQL语句中的 from , where 条件
     */
    private String tariffRulesWhereConditions(SearchTariffRulesVO searchTariffRulesVO) {

        logger.info("Enter method tariffRulesWhereConditions.");
        StringBuffer sb = new StringBuffer();

        sb.append(" from tariff t ");

        sb.append(" left join audit_reference_mapping arm on t.id = arm.audit_reference_id ");

        sb.append(" left join tariff_rate_by_quantity trbq on t.id = trbq.tariff_id and t.rate_mode in ('tariff_rate_by_quantity', 'tariff_rate_by_quantity_base_amount') ");
        
        sb.append(" left join audit_rate_period arp on ( (arp.reference_table = 'tariff_rate_by_quantity' and arp.reference_id = trbq.id) or ");
        sb.append(" (arp.reference_table = 'tariff' and arp.reference_id = arm.audit_reference_id) )");
        
       

        sb.append(" where arm.audit_reference_type_id = 2 and t.source = 'Rogers' ");

        sb.append(" and t.rec_active_flag = 'Y' and arm.rec_active_flag = 'Y' and arp.rec_active_flag = 'Y' ");
        
        
        if (searchTariffRulesVO != null) {
            
            if ( searchTariffRulesVO.getKeyField() != null ) { 
//                String keyField = StringHandling.sqlStringReplace( searchTariffRulesVO.getKeyField() );
                sb.append("AND arm.key_field_original = '" + searchTariffRulesVO.getKeyField() + "' ");
            }
            
            if ( searchTariffRulesVO.getSubProduct() != null ) { 
                String subProduct = StringHandling.sqlStringReplace( searchTariffRulesVO.getSubProduct() );
                sb.append("AND arm.sub_product = '" + subProduct + "' ");
            }
            
            if ( searchTariffRulesVO.getItemType() != null ) { 
                String itemType = StringHandling.sqlStringReplace( searchTariffRulesVO.getItemType() );
                sb.append("AND arm.usage_item_type like '%" + itemType + "%' ");
            }
            
            if ( searchTariffRulesVO.getChargeType() != null ) {
                String chargeType = StringHandling.sqlStringReplace( searchTariffRulesVO.getChargeType() );
                sb.append("AND arm.charge_type = '" + chargeType + "' ");
            }
            
            if ( searchTariffRulesVO.getSummaryVendorName() != null ) {
                String summaryVendorName = StringHandling.sqlStringReplace( searchTariffRulesVO.getSummaryVendorName() );
                sb.append("AND arm.summary_vendor_name = '" + summaryVendorName + "' ");
            }
            
            // 这里使用的 vendor name 是前台传递过来的 vendor id.
            if ( searchTariffRulesVO.getVendorName() != null ) {
                String vendorName = StringHandling.sqlStringReplace( searchTariffRulesVO.getVendorName() );
                sb.append("AND arm.vendor_group_id in (select vgv.vendor_group_id from vendor_group vg ");
                sb.append("left join vendor_group_vendor vgv on vg.id = vgv.vendor_group_id ");
                sb.append("where vgv.vendor_id = '"+ vendorName +"') ");
            }
            
            if ( searchTariffRulesVO.getTariffName() != null ) {
                String tariffName = StringHandling.sqlStringReplace( searchTariffRulesVO.getTariffName() );
                sb.append("AND t.name like '%" + tariffName + "%' ");
            }
            
            if ( searchTariffRulesVO.getUsoc() != null ) {
                String usoc = StringHandling.sqlStringReplace( searchTariffRulesVO.getUsoc() );
                sb.append("AND arm.usoc like '%" + usoc + "%' ");
            }
            
            if ( searchTariffRulesVO.getUsocDescription() != null ) {
                String usocDescription = StringHandling.sqlStringReplace( searchTariffRulesVO.getUsocDescription() );
                sb.append("AND arm.usoc_description like '%" + usocDescription + "%' ");
            }
            
            if ( searchTariffRulesVO.getRate() != null ) {
                sb.append("AND arp.rate = '" + searchTariffRulesVO.getRate() + "' ");
            }
            
            if ( searchTariffRulesVO.getEffectiveDate() != null ) {
                String effectiveDate = StringHandling.sqlStringReplace( searchTariffRulesVO.getEffectiveDate() );
                sb.append("AND date_format(arp.start_date, '%Y/%m/%d') = '" + effectiveDate + "' ");
            }
            
            if ( searchTariffRulesVO.getRateStatus() != null && !"".equals(searchTariffRulesVO.getRateStatus()) ) {
                sb.append("AND (case when ('"+ searchTariffRulesVO.getRateStatus() +"' = 'active') then arp.end_date is null  ");
                sb.append(" else arp.end_date is not null end ) ");
                
                
            }
            
            if ( searchTariffRulesVO.getLineItemCode() != null ) {
                String lineItemCode = StringHandling.sqlStringReplace( searchTariffRulesVO.getLineItemCode() );
                sb.append("AND arm.line_item_code like '%" + lineItemCode + "%' ");
            }
            
            if ( searchTariffRulesVO.getLineItemCodeDescription() != null ) {
                String lineItemCodeDescription = StringHandling.sqlStringReplace( searchTariffRulesVO.getLineItemCodeDescription() );
                sb.append("AND arm.line_item_code_description like '%" + lineItemCodeDescription + "%' ");
            }  
            
            if ( searchTariffRulesVO.getItemDescription() != null ) {
                String itemDescription = StringHandling.sqlStringReplace( searchTariffRulesVO.getItemDescription() );
                sb.append("AND arm.item_description like '%" + itemDescription + "%' ");
            } 
            
            if ( searchTariffRulesVO.getFilter() != null ) { // Filter
                sb.append( "AND " + searchTariffRulesVO.getFilter() + " " );
            }

            
            
        }
        
        
        logger.info("Exit method tariffRulesWhereConditions.");
        return sb.toString();
    }

    /**
     * 从数据库中查询 Tariff Rules列表信息。
     */
     @SuppressWarnings("unchecked")
	public List<String> listTariffRules(SearchTariffRulesVO searchTariffRulesVO) {

        logger.info("Enter method listTariffRules.");

        final String sql = this.listTariffRulesQueryString(searchTariffRulesVO);

        HibernateTemplate template = this.getHibernateTemplate();
        List<String> contractRulesList = (List<String>) template.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session)throws HibernateException, SQLException {
                        return session.createSQLQuery(sql).list();
                    }
                });
        logger.info("Exit method listTariffRules.");
        return contractRulesList;
    }

    /**
     * Tariff rules列表信息查询的 SQL语句。
     */
    private String listTariffRulesQueryString(SearchTariffRulesVO searchTariffRulesVO) {

        logger.info("Enter method listTariffRulesQueryString.");
        StringBuffer sb = new StringBuffer();
        
        
        sb.append(" SELECT CONCAT('{ruleID: ', toJSON(arm.id IS NULL, '', arm.id), ");

        sb.append(" ',chargeType: \"', toJSON(arm.charge_type IS NULL, '', arm.charge_type), ");
        
        sb.append(" '\",rateStatus: \"', toJSON(arp.end_date IS NULL, 'Active', 'InActive'), ");

        sb.append(" '\",keyField: \"', toJSON(arm.key_field_original IS NULL, '', arm.key_field_original), ");

        sb.append(" '\",rateEffectiveDate: \"', toJSON( date_format(arp.start_date, '%Y-%m-%d') IS NULL, '', date_format(arp.start_date, '%Y-%m-%d') ), ");

        sb.append(" '\",summaryVendorName: \"', toJSON(arm.summary_vendor_name IS NULL, '', arm.summary_vendor_name), ");
        
        
        sb.append(" '\",vendorName: \"', toJSON(arm.vendor_name IS NULL, '', arm.vendor_name ), ");

        sb.append(" '\",usoc: \"', toJSON(arm.usoc IS NULL, '', arm.usoc), ");

        sb.append(" '\",usocDescription: \"', toJSON(arm.usoc_description IS NULL, '', arm.usoc_description), ");

        sb.append(" '\",subProduct: \"', toJSON(arm.sub_product IS NULL, '', arm.sub_product), ");

        sb.append(" '\",lineItemCodeDescription: \"', toJSON(arm.line_item_code_description IS NULL, '', arm.line_item_code_description), ");

        sb.append(" '\",lineItemCode: \"', toJSON(arm.line_item_code IS NULL, '', arm.line_item_code ), ");

        sb.append(" '\",itemType: \"', toJSON(arm.usage_item_type IS NULL, '', arm.usage_item_type), ");

        sb.append(" '\",itemDescription: \"', toJSON(arm.item_description IS NULL, '', arm.item_description), ");

        sb.append(" '\",quantityBegin: \"', toJSON(trbq.quantity_begin IS NULL, '', trbq.quantity_begin), ");

        sb.append(" '\",quantityEnd: \"', toJSON(trbq.quantity_end IS NULL, '', trbq.quantity_end), ");

        sb.append(" '\",tariff: \"', toJSON(t.name IS NULL, '', t.name), "); 

        sb.append(" '\",baseAmount: \"', toJSON(trbq.base_amount IS NULL, '', trbq.base_amount), "); 

        sb.append(" '\",multiplier: \"', toJSON(t.multiplier IS NULL, '', t.multiplier), "); 

        sb.append(" '\",rate: \"', toJSON(arp.rate IS NULL, '', format(arp.rate,5) ), ");


        sb.append(" '\",detailDescription: \"', toJSON(arp.rules_details IS NULL, '', arp.rules_details ), "); 
        sb.append(" '\",tariffPage: \"', toJSON(t.page IS NULL, '', t.page), "); 

        sb.append(" '\",pdfPage: \"', toJSON(t.pdf_page IS NULL, '', t.pdf_page), "); 

        sb.append(" '\",discount: \"', toJSON(t.discount IS NULL, '', format(t.discount, 2)), ");

        sb.append(" '\",notes: \"', toJSON(arm.notes IS NULL, '', arm.notes), "); 
                
        sb.append(" '\"}') "); // concat 函数结束符。
        
        sb.append( tariffRulesWhereConditions(searchTariffRulesVO) );
        
        // 排序条件。
        sb.append(" ORDER BY " + searchTariffRulesVO.getSortField() + " " + searchTariffRulesVO.getSortingDirection());
        
        // 分页限制条件。
        sb.append(" " + searchTariffRulesVO.getLimitCause() + " ");
        
        logger.info("Exit method listTariffRulesQueryString.");
        return sb.toString();
    }

    /**
     * 下载contract rules列表
     */
    public List<Object[]> downloadTariffRulesToExcel(SearchTariffRulesVO searchTariffRulesVO) {

        logger.info("Enter method downloadTariffRulesToExcel.");
        Session session = getSession();
        StringBuffer sb = new StringBuffer();

        sb.append( downloadTariffRulesToExcelQueryString(searchTariffRulesVO) );

        sb.append(" ORDER BY " + searchTariffRulesVO.getSortField() + " " + searchTariffRulesVO.getSortingDirection());

        sb.append( " " + searchTariffRulesVO.getLimitCause() + " " );


        try {

            List<Object[]> tariffRulesExcelDataColumns = session.createSQLQuery(sb.toString()).list();
            logger.info("Exit method downloadTariffRulesToExcel.");
            return tariffRulesExcelDataColumns;

        } finally {
            releaseSession(session);
        }

            
        
    }

    /**
     * 将 tariff rules 下载到excel文件中用到的sql查询语句.
     */
    private String downloadTariffRulesToExcelQueryString(SearchTariffRulesVO searchTariffRulesVO) {

        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT ");

        sb.append(" toJSON(arp.end_date IS NULL, 'Active', 'Inactive'), "); // status

        sb.append(" toJSON(arp.start_date IS NULL, '', date_format(arp.start_date, '%Y-%m-%d') ), "); // effective date
        sb.append(" toJSON(arp.rate is null, '', format(arp.rate,5) ), ");
        sb.append(" toJSON(trbq.base_amount IS NULL, '', trbq.base_amount), ");
        sb.append(" toJSON(t.multiplier IS NULL, '', t.multiplier), ");
        sb.append(" toJSON(trbq.quantity_begin IS NULL, '', trbq.quantity_begin), ");
        
        sb.append(" toJSON(trbq.quantity_end IS NULL, '', trbq.quantity_end), ");
        sb.append(" toJSON(t.discount IS NULL, '', format(t.discount, 2)), ");
        sb.append(" toJSON(t.name IS NULL, '', t.name), ");
        
        sb.append(" toJSON(t.page IS NULL, '', t.page), ");
        
        sb.append(" toJSON(t.pdf_page IS NULL, '', t.pdf_page), ");
        sb.append(" toJSON(arm.usoc IS NULL, '', arm.usoc ), ");
        sb.append(" toJSON(arm.usoc_description IS NULL, '', arm.usoc_description), ");
        sb.append(" toJSON(arm.line_item_code IS NULL, '', arm.line_item_code ), ");
        sb.append(" toJSON(arm.line_item_code_description IS NULL, '', arm.line_item_code_description), ");
        sb.append(" toJSON(arm.item_description IS NULL, '', arm.item_description), ");
        sb.append(" toJSON(arm.usage_item_type IS NULL, '', arm.usage_item_type), ");
        sb.append(" toJSON(arm.summary_vendor_name IS NULL, '', arm.summary_vendor_name), ");
        sb.append(" toJSON(arm.vendor_name IS NULL, '', arm.vendor_name), ");
        sb.append(" toJSON(arm.charge_type IS NULL, '', arm.charge_type), ");
        sb.append(" toJSON(arm.sub_product IS NULL, '',arm.sub_product ), ");
        sb.append(" toJSON(arp.rules_details IS NULL, '', arp.rules_details ), ");
        sb.append(" toJSON(arm.key_field_original IS NULL, '', arm.key_field_original) ");
        sb.append( tariffRulesWhereConditions(searchTariffRulesVO) );

        return sb.toString();
    }
}