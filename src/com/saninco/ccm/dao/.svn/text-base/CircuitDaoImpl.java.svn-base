package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchCircuitVO;

public class CircuitDaoImpl extends HibernateDaoSupport implements ICircuitDao {
	@SuppressWarnings("unchecked")
	public List<Object[]> searchCircuit(SearchCircuitVO sio, int userId) {
		logger.info("Enter method searchCircuit.");
		final String sql = this.getCircuitSearchQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchCircuit.");
		return l;
	}
	private String getCircuitSearchQueryString(SearchCircuitVO sio, int userId) {
		logger.info("Enter method getCircuitSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{circuitNumber:\"',toJSON(stripped_circuit_number is null ,'',stripped_circuit_number),'\",invoice_number:\"',toJSON(invoice_number is null,'',invoice_number),");
		sb.append("'\",billDate:\"',if(invoice_date is null,'',invoice_date),");
		sb.append("'\",billYear:\"',toJSON(invoice_year is null ,'',invoice_year),'\",billMonth:\"',toJSON(invoice_month is null,'',invoice_month),");
		sb.append("'\",ban:\"',toJSON(account_number is null,'',account_number),");
		sb.append("'\",vendor:\"',vendor_name,");
		sb.append("'\",chargeType:\"',toJSON(charge_type is null,'',charge_type),");
		sb.append("'\",invoiceAmount:\"',toJSON(invoice_amount is null,'',invoice_amount),");
		sb.append("'\",scoa:\"',toJSON(account_code_name is null,'',account_code_name),");
		sb.append("'\",currency:\"',toJSON(currency_name is null,'',currency_name),");
		sb.append("'\",lineOfbusiness:\"',toJSON(line_of_business is null,'',line_of_business),");
		sb.append("'\",analyst:\"',toJSON(assigned_analyst_name is null,'',assigned_analyst_name),");
		sb.append("'\",lineItemCode:\"',toJSON(line_item_code is null,'',line_item_code),");
		sb.append("'\",itemTypeSummary:\"',toJSON(item_type_summary is null,'',item_type_summary),");
		if("MRC".equals(sio.getItemType())){
			sb.append("'\",currInvoiceAmount:\"',toJSON(curr_invoice_amount is null,'',curr_invoice_amount),");
			sb.append("'\",prevInvoiceAmount:\"',toJSON(prev_invoice_amount is null,'',prev_invoice_amount),");
		}
		sb.append("'\",lineItemCodeDescription:\"',toJSON(line_item_code_description is null,'',line_item_code_description),");
		sb.append("'\",usoc:\"',toJSON(usoc is null,'',usoc),");
		sb.append("'\",usoc_description:\"',toJSON(usoc_description is null,'',usoc_description),'\"");
		sb.append("}') a from (");
		sb.append(voFromConditions(sio));
		sb.append(voWhereConditions(sio));
		sb.append(" )w ");
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getCircuitSearchQueryString.");
		return sb.toString();
	}
	
	private String voWhereConditions(SearchCircuitVO sio) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM (SELECT i.invoice_number, ii.stripped_circuit_number, i.invoice_date,date_format(i.invoice_date,'%Y') AS invoice_year,date_format(i.invoice_date,'%m') AS invoice_month,");
		sb.append(" date_format(i.invoice_date,'%Y%m') AS invoice_year_month, b.id as ban_id, b.account_number, v.vendor_name, it.item_type_name AS charge_type,it.item_type_summary,");
		sb.append(" sum(((ifnull(p.payment_amount,0) +ifnull(p.dispute_amount,0)) +ifnull(p.credit_amount,0)))AS invoice_amount,");
		sb.append(" ac.account_code_name,c.currency_name,b.line_of_business,concat(u.first_name,' ',u.last_name)AS assigned_analyst_name,");
		sb.append(" ii.line_item_code,ii.line_item_code_description,ii.usoc,ii.usoc_description ");
		
		sb.append(whereConditions(sio));
//		sb.append(" GROUP BY i.ban_id, i.invoice_number, i.assigned_analyst_id, p.account_code_id, b.currency_id,");
//		sb.append(" i.invoice_date, ii.stripped_circuit_number, it.item_type_name, ii.line_item_code, ii.line_item_code_description,");
//		sb.append(" ii.usoc, ii.usoc_description");
		sb.append(" GROUP BY i.ban_id, i.id,  p.account_code_id,");
		sb.append(" ii.stripped_circuit_number, it.item_type_name, ii.line_item_code, ii.usoc");
		if (sio.getFilter() != null) {
			sb.append(" having ");
			sb.append(sio.getFilter());
		}
		sb.append(" order by null) t");
		return sb.toString();
	}

	private String voFromConditions(SearchCircuitVO svo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT invoice_number, stripped_circuit_number,invoice_year,invoice_month,invoice_date,account_number,vendor_name,charge_type,");
		sb.append(" invoice_amount,account_code_name,currency_name,line_of_business,assigned_analyst_name,line_item_code,line_item_code_description,item_type_summary,usoc,usoc_description ");
		if ("MRC".equals(svo.getItemType())) {
			sb.append(" ,(SELECT sum(ii.item_amount) FROM invoice_item ii");
			sb.append(" inner join invoice i on ii.invoice_id = i.id");
			sb.append(" inner join ban b on i.ban_id = b.id");
			sb.append(" inner join item_type ON ii.item_type_id = item_type.id");
			sb.append(" WHERE item_type.item_type_summary = 'MRC'");
			sb.append(" AND i.ban_id = t.ban_id");
			sb.append(" AND ii.stripped_circuit_number = t.stripped_circuit_number");
			sb.append(" AND date_format(i.invoice_date,'%Y%m') = t.invoice_year_month");
			sb.append(" GROUP BY i.ban_id, ii.stripped_circuit_number)");
			sb.append(" AS curr_invoice_amount");
			
			sb.append(" ,(SELECT sum(ii.item_amount) FROM invoice_item ii");
			sb.append(" inner join invoice i on ii.invoice_id = i.id");
			sb.append(" inner join ban b on i.ban_id = b.id");
			sb.append(" inner join item_type ON ii.item_type_id = item_type.id");
			sb.append(" WHERE item_type.item_type_summary = 'MRC'");
			sb.append(" AND i.ban_id = t.ban_id");
			sb.append(" AND ii.stripped_circuit_number = t.stripped_circuit_number");
			sb.append(" AND date_format(i.invoice_date,'%Y%m') = left(replace(convert(subdate(str_to_date(concat(t.invoice_year_month,'01'),'%Y%m%d'),interval 1 month),char),'-',''),6)");
			sb.append(" GROUP BY i.ban_id, ii.stripped_circuit_number)");
			sb.append(" AS prev_invoice_amount");
		}
		return sb.toString();
	}

	public long getNumberOfCircuits(SearchCircuitVO sio, int userId) {
		logger.info("Enter method getNumberOfCircuit.");
		final String sql = this.getCircuitSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfCircuit.");
		return count;
	}

	private String getCircuitSearchNumberQueryString(SearchCircuitVO sio,
			int userId) {
		logger.info("Enter method getCircuitSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voWhereConditions(sio));
		logger.info("Exit method getCircuitSearchNumberQueryString.");
		return sb.toString();
	}

	public List<Object[]> getCircuitDataForExcel(SearchCircuitVO svo, int userId) {
		logger.info("Enter method getCircuitDataForExcel.");
		final String sql = this.getCircuitDataForExcelQueryString(svo, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getCircuitDataForExcel.");
		return l;
	}

	private String getCircuitDataForExcelQueryString(SearchCircuitVO svo,
			int userId) {
		logger.info("Enter method getCircuitDataForExcelQueryString.");
		StringBuffer sb = new StringBuffer(
				"select toJSON(stripped_circuit_number is null ,'',stripped_circuit_number),toJSON(invoice_number is null,'',invoice_number),if(invoice_date is null,'',invoice_date),");
		sb.append("toJSON(invoice_year is null ,'',invoice_year),toJSON(invoice_month is null,'',invoice_month),");
		sb.append("toJSON(account_number is null,'',account_number),");
		sb.append("vendor_name,");
		sb.append("toJSON(charge_type is null,'',charge_type),");
		sb.append("toJSON(invoice_amount is null,'',invoice_amount),");
		sb.append("toJSON(account_code_name is null,'',account_code_name),");
		sb.append("toJSON(currency_name is null,'',currency_name),");
		sb.append("toJSON(line_of_business is null,'',line_of_business),");
		sb.append("toJSON(assigned_analyst_name is null,'',assigned_analyst_name),");
		sb.append("toJSON(line_item_code is null,'',line_item_code),");
		sb.append("toJSON(line_item_code_description is null,'',line_item_code_description),");
		sb.append("toJSON(usoc is null,'',usoc),");
		sb.append("toJSON(usoc_description is null,'',usoc_description)");
		
		sb.append(voWhereConditions(svo));
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getCircuitDataForExcelQueryString.");
		return sb.toString();
	}

	public List<String> getTabs(SearchCircuitVO svo, int userId) {
		logger.info("Enter method getTabs.");
		final String sql = this.getTabsQueryString(svo, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getTabs.");
		return l;
	}

	private String getTabsQueryString(SearchCircuitVO svo, int userId) {
		logger.info("Enter method getTabsQueryString");
		StringBuffer sb = new StringBuffer();
		sb.append("select concat('{itemTypeName:\"',it.item_type_summary,'\"}') ");
		sb.append(whereConditions(svo));
		sb.append(" group by it.item_type_summary ");
		logger.info("Exit method getTabsQueryString");
		return sb.toString();
	}

	public List<String> getCircuitDateHyperlink(SearchCircuitVO svo, int userId) {
		logger.info("Enter method getCircuitDateHyperlink.");
		final String sql = this.getCircuitDateHyperlinkQueryString(svo, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getCircuitDateHyperlink.");
		return l;
	}

	private String getCircuitDateHyperlinkQueryString(SearchCircuitVO svo,
			int userId) {
		logger.info("Enter method getTabsQueryString");
		StringBuffer sb = new StringBuffer();
		sb.append("select DISTINCT concat('{invoiceYearMonth:\"',date_format(i.invoice_date, '%Y%m'),'\"}') ");
		sb.append(whereConditions(svo));
		sb.append(" ORDER BY i.invoice_date ");
		logger.info("Exit method getTabsQueryString");
		return sb.toString();
	}

	private String whereConditions(SearchCircuitVO sio){
		StringBuffer sb = new StringBuffer();
//		sb.append(" FROM (SELECT distinct vc.id ");
		sb.append(" FROM (SELECT distinct vc.id as id ");
		if ( sio.getLineItemCode() != null || sio.getFullScoaCode() != null 
				|| sio.getCompany() != null || sio.getLocation() != null 
				|| sio.getDepartement() != null || sio.getProduction() != null 
				|| sio.getAccount() != null || sio.getChannel() != null ) {
			sb.append(", ac.id as account_code_id ");
		}
		
		sb.append(buildVendorCircuitConditions(sio, false));
		sb.append(" ) t ");
		sb.append(" INNER JOIN invoice_item ii ON t.id = ii.vendor_circuit_id");
//		sb.append(" INNER JOIN proposal p ON p.invoice_item_id = ii.id");
		sb.append(" INNER JOIN proposal p ON (p.invoice_item_id = ii.id ");
		if ( sio.getLineItemCode() != null || sio.getFullScoaCode() != null 
				|| sio.getCompany() != null || sio.getLocation() != null 
				|| sio.getDepartement() != null || sio.getProduction() != null 
				|| sio.getAccount() != null || sio.getChannel() != null ) {
			sb.append(" and p.account_code_id = t.account_code_id)");
		}else{
			sb.append(" )");
		}
		sb.append(" INNER JOIN invoice i ON i.id = ii.invoice_id");
		sb.append(" INNER JOIN ban b ON b.id = i.ban_id");
	    sb.append(" INNER JOIN vendor v ON i.vendor_id = v.id");
	    sb.append(" LEFT JOIN item_type it ON ii.item_type_id = it.id");
	    sb.append(" LEFT JOIN account_code ac ON p.account_code_id = ac.id");
	    sb.append(" LEFT JOIN currency c ON b.currency_id = c.id");
	    sb.append(" LEFT JOIN user u ON i.assigned_analyst_id = u.id");
	    sb.append(" WHERE v.rec_active_flag = 'Y'");
	    sb.append(" AND b.rec_active_flag = 'Y'");
	    sb.append(" AND b.master_ban_flag = 'Y'");
	    sb.append(" AND i.rec_active_flag = 'Y'");
	    sb.append(" AND i.invoice_status_id >= 9");
	    sb.append(" AND i.invoice_status_id <> 80");
	    sb.append(" AND p.rec_active_flag = 'Y'");
	    sb.append(" AND p.proposal_flag = '1'");
	    sb.append(" AND ii.rec_active_flag = 'Y'");
	    sb.append(" AND ii.proposal_flag = '1'");
	    sb.append(" AND ii.vendor_circuit_id IS NOT NULL");
	    
	    if (sio.getVendorId() != null) {
	    	sb.append(" AND v.id = ");
	    	sb.append(sio.getVendorId());
	    }
	    if (sio.getBanId() != null) {
	    	sb.append(" AND b.id = ");
	    	sb.append(sio.getBanId());
	    }
	    if (sio.getLineOfbusiness()!= null) {
	    	sb.append(" AND b.line_of_business = '");
	    	sb.append(sio.getLineOfbusiness());
	    	sb.append("'");
	    }
		if (sio.getItemType() != null) {
			sb.append(" AND it.item_type_summary ='" + sio.getItemType() + "' ");
		}
		if (sio.getInvoiceYearMonth() != null) {
//			sb.append(" and invoice_year_month=\""
			sb.append(" AND date_format(i.invoice_date,'%Y%m')=\""
					+ sio.getInvoiceYearMonth() + "\"");
		}
	    
		return sb.toString();
	}
	
	public long getNumberOfVendorCircuits(SearchCircuitVO sio, int userId) {
		logger.info("Enter method getNumberOfVendorCircuits.");
		final String sql = this.getVendorCircuitSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfCircuit.");
		return count;
	}

	private String getVendorCircuitSearchNumberQueryString(SearchCircuitVO sio,
			int userId) {
		logger.info("Enter method getVendorCircuitSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) from (select vc.id ");
		sb.append(buildVendorCircuitConditions(sio));
		sb.append(" group by vc.id ) c");
		logger.info("Exit method getVendorCircuitSearchNumberQueryString.");
		return sb.toString();
	}
	
	public List<Object[]> searchVendorCircuit(SearchCircuitVO sio, int userId) {
		logger.info("Enter method searchVendorCircuit.");
		final String sql = this.getVendorCircuitSearchQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchVendorCircuit.");
		return l;
	}
	private String getVendorCircuitSearchQueryString(SearchCircuitVO sio, int userId) {
		logger.info("Enter method getVendorCircuitSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{circuitNumber:\"',toJSON(vc.circuit_number is null ,'',vc.circuit_number),");
		sb.append("'\",id:\"',vc.id,");
		sb.append("'\",vendorName:\"',toJSON(v.vendor_name is null,'',v.vendor_name),");
		sb.append("'\",vendorAccountNumber:\"',toJSON(vc.account_number is null,'',vc.account_number),");
		sb.append("'\"}') a ");
		sb.append(buildVendorCircuitConditions(sio));
		sb.append(" group by vc.id");
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getVendorCircuitSearchQueryString.");
		return sb.toString();
	}
	private String buildVendorCircuitConditions(SearchCircuitVO sco) {
		return buildVendorCircuitConditions(sco, true);
	}
	private String buildVendorCircuitConditions(SearchCircuitVO sco, boolean doFilter) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM vendor_circuit vc");
		
/*		if ( sco.getLineItemCode() != null || sco.getFullScoaCode() != null 
				|| sco.getCompany() != null || sco.getLocation() != null 
				|| sco.getDepartement() != null || sco.getProduction() != null 
				|| sco.getAccount() != null || sco.getChannel() != null ) {
			sb.append(" INNER JOIN cost_item ci on ci.vendor_circuit_id = vc.id");
			sb.append(" LEFT JOIN account_code ac ON ac.id = ci.account_code_id");
		}*/
		sb.append(" INNER JOIN vendor v  ON vc.vendor_id = v.id");
		
		sb.append(" WHERE vc.rec_active_flag = 'Y'");
		if (sco.getVendorId() != null && !sco.getVendorId().equals("")) {
			sb.append(" and vc.vendor_id = ");
			sb.append(sco.getVendorId());
		}
		if (sco.getBanId() != null && !sco.getBanId().equals("")) {
			sb.append(" and vc.ban_id = ");
			sb.append(sco.getBanId());
		}
		if (sco.getStrippedCircuit() != null && !sco.getStrippedCircuit().equals("")) {
			sb.append(" and vc.stripped_circuit_number like '%"
					+ sco.getStrippedCircuit() + "%' ");
		}
		if (sco.getServiceType() != null) {
			sb.append(" and vc.service_type = '");
			sb.append(sco.getServiceType());
			sb.append("'");
		}
		if (sco.getCircuitType() != null) {
			sb.append(" and vc.circuit_type = '");
			sb.append(sco.getCircuitType());
			sb.append("'");
		}
		
		if (sco.getLineOfbusiness() != null){
        	sb.append(" and vc.line_of_business = '");
        	sb.append(sco.getLineOfbusiness());
        	sb.append("'");
        }
		if (sco.getOrderType() != null){
			sb.append(" and vc.order_type = '");
			sb.append(sco.getOrderType());
			sb.append("'");
		}
		if (sco.getAsrNumber() != null){
			sb.append(" and vc.asr_number = '");
			sb.append(sco.getAsrNumber());
			sb.append("'");
		}
		if (sco.getPON() != null){
			sb.append(" and vc.vendor_pon = '");
			sb.append(sco.getPON());
			sb.append("'");
		}
		if (sco.getEndCustomer() != null){
			sb.append(" and vc.customer_name = '");
			sb.append(sco.getEndCustomer());
			sb.append("'");
		}
		if(sco.getInvoiceDateWiPastNumOfDays() == null){
			if(sco.calCircuitDateStartsOn()!=null)
				sb.append(" and vc.order_date >= "+sco.calCircuitDateStartsOn()); 
			if(sco.calCircuitDateEndsOn()!=null)
				sb.append(" and vc.order_date <= "+sco.calCircuitDateEndsOn()); 
		}else{
			sb.append(" and vc.order_date "+sco.calCircuitDateStartsOn()); 
		}
    	if(sco.getInvoicestartDateWiPastNumOfDays() == null){
			if(sco.calCircuitStartDateStartsOn()!=null)
				sb.append(" and vc.circuit_active_start_date >= "+sco.calCircuitStartDateStartsOn()); 
			if(sco.calCircuitStartDateEndsOn()!=null)
				sb.append(" and vc.circuit_active_start_date <= "+sco.calCircuitStartDateEndsOn()); 
		}else{
			sb.append(" and vc.circuit_active_start_date "+sco.calCircuitStartDateStartsOn()); 
		}
		if(sco.getInvoiceendDateWiPastNumOfDays() == null){
			if(sco.calInvoiceEndDateStartsOn()!=null)
				sb.append(" and vc.Circuit_Disconnected_Date >= "+sco.calInvoiceEndDateStartsOn()); 
			if(sco.calInvoiceEndDateEndsOn()!=null)
				sb.append(" and vc.Circuit_Disconnected_Date <= "+sco.calInvoiceEndDateEndsOn()); 
		}else{
			sb.append(" and vc.Circuit_Disconnected_Date "+sco.calInvoiceEndDateStartsOn());
		}
		if(sco.getA_address() != null){
			sb.append(" and vc.a_address = '");
			sb.append(sco.getA_address());
			sb.append("'");
		}
		if(sco.getCustomerId() != null){
			sb.append(" and vc.customer_id = '"+ sco.getCustomerId()+"'");
		}
		/*if ( sco.getLineItemCode() != null || sco.getFullScoaCode() != null 
				|| sco.getCompany() != null || sco.getLocation() != null 
				|| sco.getDepartement() != null || sco.getProduction() != null 
				|| sco.getAccount() != null || sco.getChannel() != null ) {
		
			sb.append(" and ci.rec_active_flag = 'Y'");
	        if (sco.getLineItemCode() != null){
				sb.append(" and ci.line_item_code = '");
				sb.append(sco.getLineItemCode());
				sb.append("'");
			}
		    if (sco.getFullScoaCode() != null){
		    	sb.append(" and ac.account_code_name = '");
		    	sb.append(sco.getFullScoaCode());
		    	sb.append("'");
		    } else {
		    	if(sco.getCompany()!=null){
		    		sb.append(" and ac.company = '");
			    	sb.append(sco.getCompany());
			    	sb.append("'");
		    	}
		    	if(sco.getLocation()!=null){
		    		sb.append(" and ac.location = '");
			    	sb.append(sco.getLocation());
			    	sb.append("'");
		    	}
		    	if(sco.getDepartement()!=null){
		    		sb.append(" and ac.department = '");
			    	sb.append(sco.getDepartement());
			    	sb.append("'");
		    	}
		    	if(sco.getProduction()!=null){
		    		sb.append(" and ac.product = '");
			    	sb.append(sco.getProduction());
			    	sb.append("'");
		    	}
		    	if(sco.getAccount()!=null){
		    		sb.append(" and ac.account = '");
			    	sb.append(sco.getAccount());
			    	sb.append("'");
		    	}
		    	if(sco.getChannel()!=null){
		    		sb.append(" and ac.channel = '");
			    	sb.append(sco.getChannel());
			    	sb.append("'");
		    	}
		    }
		}*/
		if (doFilter) {
			if (sco.getFilter() != null) {
				sb.append(" and ");
				sb.append(sco.getFilter());
			}
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getVendorCircuitDataForExcel(SearchCircuitVO svo) {
		logger.info("Enter method getSCOAListForExcel.");
		final String sql = this.getVendorCircuitDataForExcelQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getSCOAListForExcel.");
		return l;
	}
	
	private String getVendorCircuitDataForExcelQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getVendorCircuitDataForExcelQueryString.");
		StringBuffer sb = new StringBuffer("select toJSON(vc.circuit_number is null ,'',vc.circuit_number),");
		sb.append("toJSON(v.vendor_name is null,'',v.vendor_name),");
		sb.append("toJSON(vc.account_number is null,'',vc.account_number)");
		sb.append(buildVendorCircuitConditions(svo));
		sb.append(" group by vc.id");
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getVendorCircuitDataForExcelQueryString.");
		return sb.toString();
	}
	//zwb
	public List<Object[]> getSearchConditioncustomers() {
		logger.info("Enter method getSearchConditioncustomers.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select c.id,c.customer_name from customer c where c.active_flag = 'Y' and c.rec_active_flag = 'Y'").list();
			logger.info("Exit method getSearchConditioncustomers.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	public List searchReportDownloadNumAndMaxNum(String param){
		logger.info("Enter method searchReportDownloadNumAndMaxNum.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select value from sys_config where parameter = '"+param+"'").list();
			logger.info("Exit method searchReportDownloadNumAndMaxNum.");
			return l;
		} finally {
			releaseSession(session);
		}
		
	}
	
	public void updateReportDownloadNum(Boolean finishFlag){
		logger.info("Enter method updateReportDownloadNum.");
		Session session = getSession();
		try {
			Query query ;
			if(finishFlag){
				query = session.createSQLQuery("update sys_config set value = if((value - 1) >= 0,(value - 1),0) " +
						" where parameter ='report_download_num';");  
			}else{
				query = session.createSQLQuery("update sys_config set value = (value + 1) " +
						" where parameter ='report_download_num';");  
			}
			query.executeUpdate();  
		} finally {
			releaseSession(session);
		}
		
	}
}
