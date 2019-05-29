package com.saninco.ccm.circuitold.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.circuitold.model.SearchCircuitVO;


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
			sb.append("'\",currInvoiceAmount:\"',toJSON(invoice_amount is null,'',invoice_amount),");
			sb.append("'\",prevInvoiceAmount:\"',(select ifnull(sum((ifnull(pc.payment_amount, 0) + ifnull(pc.dispute_amount, 0)) + ifnull(pc.credit_amount, 0)),0) from invoice ic,proposal pc where ic.id = pc.invoice_id and pc.circuit_number = stripped_circuit_number and pc.proposal_flag = 1 and pc.rec_active_flag = 'Y' and ic.ban_id = ban_id and f_get_root_item_type(pc.item_type_id) = 3),");
		}
		sb.append("'\",lineItemCodeDescription:\"',toJSON(line_item_code_description is null,toJSON(description IS NULL,'',description),line_item_code_description),");
		sb.append("'\",usoc:\"',toJSON(usoc is null,'',usoc),");
		sb.append("'\",usoc_description:\"',toJSON(usoc_description is null,'',usoc_description),'\"");
		sb.append("}') a from (");
		if (sio.getStrippedCircuit() != null
				|| sio.getStrippedCircuitArray() != null) {
			sb.append(voFromConditions(sio, "search_circuit_view"));
			sb.append(voWhereConditions(sio, userId, "search_circuit_view"));
		} else {
			sb.append(voFromConditions(sio, "search_circuit_all_view"));
			sb.append(voWhereConditions(sio, userId,"search_circuit_all_view"));
		}
		sb.append(" )w ");
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getCircuitSearchQueryString.");
		return sb.toString();
	}

	private String voHistoryWhereConditions(SearchCircuitVO sio, int userId) {
		boolean useCircuitNumber = false;
		boolean useScoaCode = false;
		boolean useInvoice = false;
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM (SELECT v.stripped_circuit_number as stripped_circuit_number,invoice_number, invoice_year, invoice_month, invoice_date, v.account_number AS account_number, ");
		sb.append(" vendor_name, charge_type, sum(invoice_amount) AS invoice_amount, v.account_code_name AS account_code_name, currency_name, ");
		sb.append(" line_of_business, assigned_analyst_name, line_item_code, line_item_code_description, ");
		sb.append(" usoc,usoc_description,v.ban_id ");
		if(sio.getStrippedCircuit() != null
				|| sio.getStrippedCircuitArray() != null){
			sb.append(" FROM search_circuit_history_view v ");
		}else{
			sb.append(" FROM search_circuit_history_all_view v ");
		}
		if (sio.getStrippedCircuit() != null
				|| sio.getStrippedCircuitArray() != null) {
			useCircuitNumber = true;
			sb.append(",(select distinct clean_circuit from ccm_dwdb.circuit_inventory where 1=1 ");
			if (sio.getStrippedCircuit() != null) {
				sb.append(" and clean_circuit like '%"
						+ sio.getStrippedCircuit() + "%' ");
			}
			if (sio.getStrippedCircuitArray() != null) {
				sb.append(" and clean_circuit in (");
				for (int i = 0; i < sio.getStrippedCircuitArray().length; i++) {
					sb.append("\"" + sio.getStrippedCircuitArray()[i] + "\"");
					if (i < sio.getStrippedCircuitArray().length - 1) {
						sb.append(",");
					}
				}
				sb.append(") ");
			}
			sb.append(" ) ci ");
		} else {
			useCircuitNumber = false;
		}
		if (sio.getFullScoaCode() != null || sio.getCompany() != null
				|| sio.getLocation() != null || sio.getDepartement() != null
				|| sio.getProduction() != null || sio.getAccount() != null
				|| sio.getChannel() != null || sio.getFullScoaCode() != null) {
			useScoaCode = true;
			sb.append(", ( SELECT id FROM account_code WHERE 1=1 ");
			if (sio.getCompany() != null) {
				sb.append(" and company is not null ");
				sb.append(" and company ='" + sio.getCompany() + "' ");
			}
			if (sio.getLocation() != null) {
				sb.append(" and location is not null ");
				sb.append(" and location = '" + sio.getLocation() + "' ");
			}
			if (sio.getDepartement() != null) {
				sb.append(" and department is not null ");
				sb.append(" and department = '" + sio.getDepartement() + "' ");
			}
			if (sio.getProduction() != null) {
				sb.append(" and product is not null ");
				sb.append(" and product = '" + sio.getProduction() + "' ");
			}
			if (sio.getAccount() != null) {
				sb.append(" and account is not null ");
				sb.append(" and account = '" + sio.getAccount() + "' ");
			}
			if (sio.getChannel() != null) {
				sb.append(" and channel is not null ");
				sb.append(" and channel = '" + sio.getChannel() + "' ");
			}
			if (sio.getFullScoaCode() != null) {
				sb.append(" and account_code_name is not null ");
				sb.append(" and account_code_name='" + sio.getFullScoaCode()
						+ "' ");
			}
			sb.append(" ) ac ");
		} else {
			useScoaCode = false;
		}
		if (sio.getInvoiceDateStartsOn() != null
				|| sio.getInvoiceDateEndsOn() != null
				|| sio.getInvoiceDateWiPastNumOfDays() != null
				|| sio.getInvoicestartDateEndsOn() != null
				|| sio.getInvoicestartDateStartsOn() != null
				|| sio.getInvoicestartDateWiPastNumOfDays() != null
				|| sio.getInvoiceendDateEndsOn() != null
				|| sio.getInvoiceendDateStartsOn() != null
				|| sio.getInvoiceendDateWiNextNumOfDays() != null
				|| sio.getInvoiceendDateWiPastNumOfDays() != null
				|| sio.getVendorId() != null
				|| sio.getVendorNameArray() != null || sio.getBanId() != null
				|| sio.getBanArray() != null || sio.getLineOfbusiness() != null) {
			useInvoice = true;
			sb.append(",(SELECT i.id AS invoice_id, b.id AS ban_id, v.id as vendor_id  FROM ccm_dwdb.invoice i, ban b, vendor v  WHERE i.ban_id = b.id AND b.vendor_id = v.id ");
			if (sio.getInvoiceDateWiPastNumOfDays() == null) {
				if (sio.calCircuitDateStartsOn() != null)
					sb.append(" and i.invoice_date >= "
							+ sio.calCircuitDateStartsOn());
				if (sio.calCircuitDateEndsOn() != null)
					sb.append(" and i.invoice_date <= "
							+ sio.calCircuitDateEndsOn());
			} else {
				sb.append(" and i.invoice_date "
								+ sio.calCircuitDateStartsOn());
			}
			if (sio.getInvoicestartDateWiPastNumOfDays() == null) {
				if (sio.calCircuitStartDateStartsOn() != null)
					sb.append(" and i.invoice_start_date >= "
							+ sio.calCircuitStartDateStartsOn());
				if (sio.calCircuitStartDateEndsOn() != null)
					sb.append(" and i.invoice_start_date <= "
							+ sio.calCircuitStartDateEndsOn());
			} else {
				sb.append(" and i.invoice_start_date "
						+ sio.calCircuitStartDateStartsOn());
			}
			if (sio.getInvoicestartDateWiPastNumOfDays() != null
					|| sio.calCircuitStartDateStartsOn() != null
					|| sio.calCircuitStartDateEndsOn() != null) {
				sb.append(" and i.invoice_start_date is not null ");
			}
			if (sio.getInvoiceendDateWiPastNumOfDays() == null) {
				if (sio.calInvoiceEndDateStartsOn() != null)
					sb.append(" and i.invoice_end_date >= "
							+ sio.calInvoiceEndDateStartsOn());
				if (sio.calInvoiceEndDateEndsOn() != null)
					sb.append(" and i.invoice_end_date <= "
							+ sio.calInvoiceEndDateEndsOn());
			} else {
				sb.append(" and i.invoice_end_date "
						+ sio.calInvoiceEndDateStartsOn());
			}
			if (sio.getInvoiceendDateWiPastNumOfDays() != null
					|| sio.getInvoiceendDateWiPastNumOfDays() != null
					|| sio.calInvoiceEndDateEndsOn() != null
					|| sio.calInvoiceEndDateStartsOn() != null) {
				sb.append(" and i.invoice_end_date is not null ");
			}
			if (sio.getVendorId() != null) {
				sb.append(" and v.id =" + sio.getVendorId() + " ");
			}
			if (sio.getVendorNameArray() != null) {
				sb.append(" and v.vendor_name in (");
				for (int i = 0; i < sio.getVendorNameArray().length; i++) {
					sb.append("\"" + sio.getVendorNameArray()[i] + "\"");
					if (i < sio.getVendorNameArray().length - 1) {
						sb.append(" , ");
					}
				}
				sb.append(") ");
			}
			if (sio.getBanId() != null) {
				sb.append(" and b.id =" + sio.getBanId() + " ");
			}
			if (sio.getBanArray() != null) {
				sb.append(" and b.account_number in (");
				for (int i = 0; i < sio.getBanArray().length; i++) {
					sb.append("\"" + sio.getBanArray()[i] + "\"");
					if (i < sio.getBanArray().length - 1) {
						sb.append(",");
					}
				}
				sb.append(") ");
			}
			if (sio.getLineOfbusiness() != null) {
				sb.append(" and b.line_of_business=\""
						+ sio.getLineOfbusiness() + "\" ");
			}
			sb.append("  ) t ");
		} else {
			useInvoice = false;
		}
		sb.append(" where 1=1 ");
		if (useCircuitNumber) {
			sb.append(" and v.stripped_circuit_number = ci.clean_circuit ");
		}
		if (useScoaCode) {
			sb.append(" and v.account_code_id = ac.id and v.account_code_id is not null ");
		}
		if (useInvoice) {
			sb.append(" AND v.invoice_id = t.invoice_id ");
		}
		if (sio.getLineItemCode() != null) {
			sb.append(" and v.line_item_code is not null ");
			sb.append(" and v.line_item_code='" + sio.getLineItemCode() + "' ");
		}
		if (sio.getItemType() != null) {
			sb.append(" and v.item_type_summary ='" + sio.getItemType() + "' ");
		}
		if (sio.getInvoiceYearMonth() != null) {
			sb.append(" and v.invoice_year_month=\""
					+ sio.getInvoiceYearMonth() + "\"");
		}
		sb.append(" GROUP BY ban_id, assigned_analyst_id, ");
		sb.append(" account_code_id, currency_id, invoice_date, stripped_circuit_number, charge_type,line_item_code, line_item_code_description order by null) t ");
		return sb.toString();
	}

	private String voWhereConditions(SearchCircuitVO sio, int userId,
			String view) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM (SELECT v.invoice_number as invoice_number,v.stripped_circuit_number as stripped_circuit_number, invoice_year, invoice_month, invoice_date, v.account_number AS account_number, ");
		sb.append(" vendor_name, charge_type, invoice_amount AS invoice_amount, v.account_code_name AS account_code_name, currency_name, ");
		sb.append(" line_of_business, assigned_analyst_name, line_item_code, line_item_code_description,description, item_type_summary, ");
		sb.append(" v.ban_id, invoice_year_month,usoc,usoc_description ");
		sb.append(whereConditions(sio, userId, view));
		if (sio.getFilter() != null) {
			sb.append(" and ");
			sb.append(sio.getFilter());
		}
		sb.append(" GROUP BY ban_id,invoice_number, assigned_analyst_id, ");
		sb.append(" account_code_id, currency_id, invoice_date, stripped_circuit_number, charge_type,line_item_code, line_item_code_description, rate order by null ) v ) t ");
		return sb.toString();
	}

	private String voFromConditions(SearchCircuitVO svo, String view) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT invoice_number,ban_id, stripped_circuit_number,invoice_year,invoice_month,invoice_date,account_number,vendor_name,charge_type,invoice_amount,");
//		sb.append("  (SELECT sum(v.invoice_amount) FROM search_circuit_view v WHERE v.ban_id = t.ban_id AND v.invoice_year_month = t.invoice_year_month AND v.stripped_circuit_number = t.stripped_circuit_number ");
//		if (svo.getItemType() != null) {
//			sb.append(" AND item_type_summary ='" + svo.getItemType() + "' ");
//		}
//		sb.append(" GROUP BY v.ban_id, v.stripped_circuit_number, v.invoice_year, v.invoice_month ORDER BY NULL) as invoice_amount, ");
		sb.append(" account_code_name,currency_name,line_of_business,assigned_analyst_name,line_item_code,line_item_code_description,description,item_type_summary,usoc,usoc_description ");
//		if ("MRC".equals(svo.getItemType())) {
//			sb.append(" ,(SELECT sum(v.invoice_amount) FROM "
//							+ view
//							+ " v WHERE item_type_summary = 'MRC' AND   v.ban_id = t.ban_id ");
//			sb.append(" AND   v.invoice_year_month = t.invoice_year_month  AND   v.stripped_circuit_number = t.stripped_circuit_number");
//			sb.append(" GROUP BY v.ban_id, v.stripped_circuit_number, v.invoice_year, v.invoice_month order by null) AS curr_invoice_amount, ");
//			sb.append(" (SELECT sum(v.invoice_amount) FROM "
//							+ view
//							+ " v WHERE item_type_summary = 'MRC' AND   v.ban_id = t.ban_id ");
//			sb.append(" AND   v.stripped_circuit_number = t.stripped_circuit_number AND   v.invoice_year_month = left(replace(convert(subdate(str_to_date(concat(t.invoice_year_month,'01'),'%Y%m%d'),interval 1 month),char),'-',''),6) ");
//			sb.append(" GROUP BY v.ban_id, v.stripped_circuit_number, v.invoice_year, v.invoice_month order by null) AS prev_invoice_amount ");
//		}
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
		if (sio.isHistoricalInvoice() == true) {
			sb.append(voHistoryWhereConditions(sio, userId));
		} else if (sio.getStrippedCircuit() != null
				|| sio.getStrippedCircuitArray() != null) {
			sb.append(voWhereConditions(sio, userId, "search_circuit_view"));
		} else {
			sb.append(voWhereConditions(sio, userId, "search_circuit_all_view"));
		}
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
		sb.append("toJSON(line_item_code_description is null,toJSON(description IS NULL,'',description),line_item_code_description),");
		sb.append("toJSON(usoc is null,'',usoc),");
		sb.append("toJSON(usoc_description is null,'',usoc_description)");
		if (svo.isHistoricalInvoice() == true) {
			sb.append(voHistoryWhereConditions(svo, userId));
		} else if (svo.getStrippedCircuit() != null
				|| svo.getStrippedCircuitArray() != null) {
			sb.append(voWhereConditions(svo, userId, "search_circuit_view"));
		} else {
			sb.append(voWhereConditions(svo, userId, "search_circuit_all_view"));
		}
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
		sb.append("select concat('{itemTypeName:\"',item_type_summary,'\"}') ");
		if (svo.getStrippedCircuit() != null
				|| svo.getStrippedCircuitArray() != null) {
			sb.append(whereConditions(svo, userId, "search_circuit_view"));
		} else {
			sb.append(whereConditions(svo, userId, "search_circuit_all_view"));
		}
		sb.append(" group by it.item_type_summary ) v ");
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
		sb.append("select concat('{invoiceYearMonth:\"',invoice_year_month,'\"}') ");
		if (svo.getStrippedCircuit() != null
				|| svo.getStrippedCircuitArray() != null) {
			sb.append(whereConditions(svo, userId, "search_circuit_view"));
		} else {
			sb.append(whereConditions(svo, userId, "search_circuit_all_view"));
		}
		sb.append(" group by date_format(i.invoice_date, '%Y%m') ) v");
		logger.info("Exit method getTabsQueryString");
		return sb.toString();
	}

	private String whereConditions(SearchCircuitVO sio, int userId,String view){
		boolean useCircuitNumber = false;
		boolean useScoaCode = false;
		boolean useInvoice = false;
		StringBuffer sb = new StringBuffer();
		sb.append("From ("+fromViewConditions(sio,userId,view));
//		if(sio.getStrippedCircuit()!=null || sio.getStrippedCircuitArray()!=null){
//	        	useCircuitNumber= true;
//	        	sb.append(",(select distinct clean_circuit from circuit_inventory where 1=1 ");
//	        	if(sio.getStrippedCircuit()!=null ){
//	        		sb.append(" and clean_circuit like '%"+sio.getStrippedCircuit()+"%' ");
//	        	}
//	        	if(sio.getStrippedCircuitArray()!=null){
//	    			sb.append(" and clean_circuit in (");
//	    			for(int i=0;i<sio.getStrippedCircuitArray().length;i++){
//	    				sb.append("\"" + sio.getStrippedCircuitArray()[i]+"\"");
//	    				if(i<sio.getStrippedCircuitArray().length-1){
//	    					sb.append(",");
//	    				}
//	    			}
//	    			sb.append(") ");
//	    			}	
//	        	sb.append(" ) ci ");
//		 }else{
//			useCircuitNumber = false;
//		}
//		if (sio.getFullScoaCode() != null || sio.getCompany() != null
//				|| sio.getLocation() != null || sio.getDepartement() != null
//				|| sio.getProduction() != null || sio.getAccount() != null
//				|| sio.getChannel() != null || sio.getFullScoaCode()!=null) {
//			useScoaCode = true;
//			sb.append(", ( SELECT id FROM account_code WHERE 1=1 ");
//			  if(sio.getCompany()!=null){
//			    	sb.append(" and company is not null ");
//					sb.append(" and company ='"+sio.getCompany()+"' ");
//				}
//				if(sio.getLocation()!=null){
//					sb.append(" and location is not null ");
//					sb.append(" and location = '"+sio.getLocation()+"' ");
//				}
//				if(sio.getDepartement()!=null){
//					sb.append(" and department is not null ");
//					sb.append(" and department = '"+sio.getDepartement()+"' ");
//				}
//				if(sio.getProduction()!=null){
//					sb.append(" and product is not null ");
//					sb.append(" and product = '"+sio.getProduction()+"' ");
//				}
//				if(sio.getAccount()!=null){
//					sb.append(" and account is not null ");
//					sb.append(" and account = '"+sio.getAccount()+"' ");
//				}
//				if(sio.getChannel()!=null){
//					sb.append(" and channel is not null ");
//					sb.append(" and channel = '"+sio.getChannel()+"' ");
//				}
//				if(sio.getFullScoaCode()!=null){
//		    		sb.append(" and account_code_name is not null ");
//					sb.append(" and account_code_name='"+sio.getFullScoaCode()+"' ");
//				}
//				sb.append(" ) ac ");
//		}else{
//			useScoaCode = false;
//		}
//		if (sio.getInvoiceDateStartsOn() != null
//				|| sio.getInvoiceDateEndsOn() != null
//				|| sio.getInvoiceDateWiPastNumOfDays() != null
//				|| sio.getInvoicestartDateEndsOn() != null
//				|| sio.getInvoicestartDateStartsOn() != null
//				|| sio.getInvoicestartDateWiPastNumOfDays() != null
//				|| sio.getInvoiceendDateEndsOn() != null
//				|| sio.getInvoiceendDateStartsOn() != null
//				|| sio.getInvoiceendDateWiNextNumOfDays() != null
//				|| sio.getInvoiceendDateWiPastNumOfDays() != null
//				|| sio.getVendorId() != null
//				|| sio.getVendorNameArray() != null || sio.getBanId() != null
//				|| sio.getBanArray() != null || sio.getLineOfbusiness() != null) {
//			useInvoice =true;
//			sb.append(",(SELECT i.id AS invoice_id, b.id AS ban_id, v.id as vendor_id  FROM invoice i, ban b, vendor v  WHERE i.ban_id = b.id AND b.vendor_id = v.id ");
//			if(sio.getInvoiceDateWiPastNumOfDays() == null){
//				if(sio.calCircuitDateStartsOn()!=null)
//					sb.append(" and i.invoice_date >= "+sio.calCircuitDateStartsOn()); 
//				if(sio.calCircuitDateEndsOn()!=null)
//					sb.append(" and i.invoice_date <= "+sio.calCircuitDateEndsOn()); 
//			}else{
//				sb.append(" and i.invoice_date "+sio.calCircuitDateStartsOn()); 
//			}
//	    	if(sio.getInvoicestartDateWiPastNumOfDays() == null){
//				if(sio.calCircuitStartDateStartsOn()!=null)
//					sb.append(" and i.invoice_start_date >= "+sio.calCircuitStartDateStartsOn()); 
//				if(sio.calCircuitStartDateEndsOn()!=null)
//					sb.append(" and i.invoice_start_date <= "+sio.calCircuitStartDateEndsOn()); 
//			}else{
//				sb.append(" and i.invoice_start_date "+sio.calCircuitStartDateStartsOn()); 
//			}
//			if(sio.getInvoicestartDateWiPastNumOfDays() != null || sio.calCircuitStartDateStartsOn()!=null || sio.calCircuitStartDateEndsOn()!=null){
//				sb.append(" and i.invoice_start_date is not null ");
//			}
//			if(sio.getInvoiceendDateWiPastNumOfDays() == null){
//				if(sio.calInvoiceEndDateStartsOn()!=null)
//					sb.append(" and i.invoice_end_date >= "+sio.calInvoiceEndDateStartsOn()); 
//				if(sio.calInvoiceEndDateEndsOn()!=null)
//					sb.append(" and i.invoice_end_date <= "+sio.calInvoiceEndDateEndsOn()); 
//			}else{
//				sb.append(" and i.invoice_end_date "+sio.calInvoiceEndDateStartsOn());
//			}
//			if(sio.getInvoiceendDateWiPastNumOfDays() != null || sio.getInvoiceendDateWiPastNumOfDays() != null || sio.calInvoiceEndDateEndsOn()!=null || sio.calInvoiceEndDateStartsOn()!=null){
//				sb.append(" and i.invoice_end_date is not null ");
//			}
//			if(sio.getVendorId()!=null){
//		    	sb.append(" and v.id ="+sio.getVendorId()+" ");
//		    }
//		    if(sio.getVendorNameArray()!=null){
//		    	sb.append(" and v.vendor_name in (");
//		    	for(int i = 0;i<sio.getVendorNameArray().length;i++){
//		    		sb.append("\""+sio.getVendorNameArray()[i]+"\"");
//		    		if(i<sio.getVendorNameArray().length-1){
//		    			sb.append(" , ");
//		    		}
//		    	}
//		    	sb.append(") ");
//		    }
//		    if(sio.getBanId()!=null){
//		    	sb.append(" and b.id ="+sio.getBanId()+" ");
//		    }
//		    if(sio.getBanArray()!=null){
//		    	sb.append(" and b.account_number in (");
//		    	for(int i =0; i<sio.getBanArray().length ; i++){
//		    		sb.append("\""+sio.getBanArray()[i]+"\"");
//		    		if(i<sio.getBanArray().length-1){
//		    			sb.append(",");
//		    		}
//		    	}
//		    	sb.append(") ");
//		    }
//		    if(sio.getLineOfbusiness()!=null){
//	        	sb.append(" and b.line_of_business=\""+sio.getLineOfbusiness()+"\" ");
//	        }
//		    sb.append("  ) t ");
//		}else{
//			useInvoice=false;
//		}
//		sb.append(" where 1=1 ");
//		if(useCircuitNumber){
//			sb.append(" and v.stripped_circuit_number = ci.clean_circuit ");
//		}
//		if(useScoaCode){
//			sb.append(" and v.account_code_id = ac.id and v.account_code_id is not null ");
//		}
//		if (useInvoice) {
//			sb.append(" AND v.invoice_id = t.invoice_id ");
//		}
//		if (sio.getLineItemCode() != null) {
//			sb.append(" and v.line_item_code is not null ");
//			sb.append(" and v.line_item_code='" + sio.getLineItemCode() + "' ");
//		}
//		if (sio.getItemType() != null) {
//			sb.append(" and v.item_type_summary ='" + sio.getItemType() + "' ");
//		}
//		if (sio.getInvoiceYearMonth() != null) {
//			sb.append(" and v.invoice_year_month=\""
//					+ sio.getInvoiceYearMonth() + "\"");
//		}
		return sb.toString();
	}
	
	private String fromViewConditions(SearchCircuitVO sio, int userId,String view){
		logger.info("Enter method fromViewConditions");
		boolean useScoaCode = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbFrom = new StringBuffer();
		StringBuffer sbWhere = new StringBuffer();
		if(sio.getStrippedCircuit()!=null || sio.getStrippedCircuitArray()!=null){
        	if(sio.getStrippedCircuit()!=null ){
        		sbWhere.append(" AND ci.clean_circuit like '%"+sio.getStrippedCircuit()+"%' ");
        	}
        	if(sio.getStrippedCircuitArray()!=null){
        		sbWhere.append(" AND ci.clean_circuit in (");
    			for(int i=0;i<sio.getStrippedCircuitArray().length;i++){
    				sbWhere.append("\"" + sio.getStrippedCircuitArray()[i]+"\"");
    				if(i<sio.getStrippedCircuitArray().length-1){
    					sbWhere.append(",");
    				}
    			}
    			sbWhere.append(") ");
    		}	
		}
		
		if (sio.getFullScoaCode() != null || sio.getCompany() != null
				|| sio.getLocation() != null || sio.getDepartement() != null
				|| sio.getProduction() != null || sio.getAccount() != null
				|| sio.getChannel() != null || sio.getFullScoaCode()!=null) {
			  if(sio.getCompany()!=null){
				  sbWhere.append(" AND ac.company is not null ");
				  sbWhere.append(" AND ac.company ='"+sio.getCompany()+"' ");
				}
				if(sio.getLocation()!=null){
					sbWhere.append(" AND ac.location is not null ");
					sbWhere.append(" AND ac.location = '"+sio.getLocation()+"' ");
				}
				if(sio.getDepartement()!=null){
					sbWhere.append(" AND ac.department is not null ");
					sbWhere.append(" AND ac.department = '"+sio.getDepartement()+"' ");
				}
				if(sio.getProduction()!=null){
					sbWhere.append(" AND ac.product is not null ");
					sbWhere.append(" AND ac.product = '"+sio.getProduction()+"' ");
				}
				if(sio.getAccount()!=null){
					sbWhere.append(" AND ac.account is not null ");
					sbWhere.append(" AND ac.account = '"+sio.getAccount()+"' ");
				}
				if(sio.getChannel()!=null){
					sbWhere.append(" AND ac.channel is not null ");
					sbWhere.append(" AND ac.channel = '"+sio.getChannel()+"' ");
				}
				if(sio.getFullScoaCode()!=null){
					sbWhere.append(" AND ac.account_code_name is not null ");
					sbWhere.append(" AND ac.account_code_name='"+sio.getFullScoaCode()+"' ");
				}
				useScoaCode = true;
		}
		
		if(sio.getInvoiceDateWiPastNumOfDays() == null){
			if(sio.calCircuitDateStartsOn()!=null)
				sbWhere.append(" AND i.invoice_date >= "+sio.calCircuitDateStartsOn()); 
			if(sio.calCircuitDateEndsOn()!=null)
				sbWhere.append(" AND i.invoice_date <= "+sio.calCircuitDateEndsOn()); 
		}else{
			sbWhere.append(" AND i.invoice_date "+sio.calCircuitDateStartsOn()); 
		}
    	if(sio.getInvoicestartDateWiPastNumOfDays() == null){
			if(sio.calCircuitStartDateStartsOn()!=null)
				sbWhere.append(" AND i.invoice_start_date >= "+sio.calCircuitStartDateStartsOn()); 
			if(sio.calCircuitStartDateEndsOn()!=null)
				sbWhere.append(" AND i.invoice_start_date <= "+sio.calCircuitStartDateEndsOn()); 
		}else{
			sbWhere.append(" AND i.invoice_start_date "+sio.calCircuitStartDateStartsOn()); 
		}
		if(sio.getInvoicestartDateWiPastNumOfDays() != null || sio.calCircuitStartDateStartsOn()!=null || sio.calCircuitStartDateEndsOn()!=null){
			sbWhere.append(" AND i.invoice_start_date is not null ");
		}
		if(sio.getInvoiceendDateWiPastNumOfDays() == null){
			if(sio.calInvoiceEndDateStartsOn()!=null)
				sbWhere.append(" AND i.invoice_end_date >= "+sio.calInvoiceEndDateStartsOn()); 
			if(sio.calInvoiceEndDateEndsOn()!=null)
				sbWhere.append(" AND i.invoice_end_date <= "+sio.calInvoiceEndDateEndsOn()); 
		}else{
			sbWhere.append(" AND i.invoice_end_date "+sio.calInvoiceEndDateStartsOn());
		}
		if(sio.getInvoiceendDateWiPastNumOfDays() != null || sio.getInvoiceendDateWiPastNumOfDays() != null || sio.calInvoiceEndDateEndsOn()!=null || sio.calInvoiceEndDateStartsOn()!=null){
			sbWhere.append(" AND i.invoice_end_date is not null ");
		}
		if(sio.getVendorId()!=null){
			sbWhere.append(" AND v.id ="+sio.getVendorId()+" ");
	    }
	    if(sio.getVendorNameArray()!=null){
	    	sbWhere.append(" AND v.vendor_name in (");
	    	for(int i = 0;i<sio.getVendorNameArray().length;i++){
	    		sbWhere.append("\""+sio.getVendorNameArray()[i]+"\"");
	    		if(i<sio.getVendorNameArray().length-1){
	    			sbWhere.append(" , ");
	    		}
	    	}
	    	sbWhere.append(") ");
	    }
	    if(sio.getBanId()!=null){
	    	sbWhere.append(" AND b.id ="+sio.getBanId()+" ");
	    }
	    if(sio.getBanArray()!=null){
	    	sbWhere.append(" AND b.account_number in (");
	    	for(int i =0; i<sio.getBanArray().length ; i++){
	    		sbWhere.append("\""+sio.getBanArray()[i]+"\"");
	    		if(i<sio.getBanArray().length-1){
	    			sbWhere.append(",");
	    		}
	    	}
	    	sbWhere.append(") ");
	    }
	    if(sio.getLineOfbusiness()!=null){
	    	sbWhere.append(" AND b.line_of_business=\""+sio.getLineOfbusiness()+"\" ");
        }
		
		if (sio.getLineItemCode() != null) {
			sbWhere.append(" AND ii.line_item_code is not null ");
			sbWhere.append(" AND ii.line_item_code='" + sio.getLineItemCode() + "' ");
		}
		
		if (sio.getItemType() != null) {
			sbWhere.append(" AND it.item_type_summary ='" + sio.getItemType() + "' ");
		}
		
		if (sio.getInvoiceYearMonth() != null) {
			sbWhere.append(" AND date_format(i.invoice_date, '%Y%m')=\""
					+ sio.getInvoiceYearMonth() + "\"");
		}
		
		sbFrom.append(" select i.invoice_number AS invoice_number,");
		
		if("search_circuit_view".equals(view)){
			sbFrom.append(" ci.clean_circuit AS stripped_circuit_number,");
		}else if("search_circuit_all_view".equals(view)){
			sbFrom.append(" ii.stripped_circuit_number AS stripped_circuit_number,");
		}
		
		sbFrom.append(" date_format(i.invoice_date, '%Y') AS invoice_year, date_format(i.invoice_date, '%m') AS invoice_month,");
		sbFrom.append(" i.invoice_date AS invoice_date,");
		
		if("search_circuit_view".equals(view)){
			sbFrom.append(" ci.account_number AS account_number,");
		}else if("search_circuit_all_view".equals(view)){
			sbFrom.append(" b.account_number AS account_number,");
		}
		
		sbFrom.append(" v.vendor_name AS vendor_name, it.item_type_name AS charge_type,");
		sbFrom.append(" sum((ifnull(p.payment_amount, 0) + ifnull(p.dispute_amount, 0)) + ifnull(p.credit_amount, 0)) AS invoice_amount,");
		sbFrom.append(" ac.account_code_name AS account_code_name, c.currency_name AS currency_name,");
		sbFrom.append(" b.line_of_business AS line_of_business, concat(u.first_name, ' ', u.last_name) AS assigned_analyst_name,");
		sbFrom.append(" ii.line_item_code AS line_item_code, ii.line_item_code_description AS line_item_code_description,p.description,");
		sbFrom.append(" it.item_type_summary AS item_type_summary, i.id AS invoice_id,");
		sbFrom.append(" p.item_type_id AS item_type_id, i.invoice_start_date AS invoice_start_date,");
		sbFrom.append(" i.invoice_end_date AS invoice_end_date, i.assigned_analyst_id AS assigned_analyst_id,");
		sbFrom.append(" p.account_code_id AS account_code_id, b.currency_id AS currency_id,");
		sbFrom.append(" date_format(i.invoice_date, '%Y%m') AS invoice_year_month,");
		sbFrom.append(" v.id AS vendor_id, b.id AS ban_id, ac.company AS company,");
		sbFrom.append(" ac.location AS location, ac.department AS department, ac.product AS product,");
		sbFrom.append(" ac.account AS account, ac.channel AS channel, p.usoc AS usoc, p.usoc_description AS usoc_description, p.rate AS rate");
		
		sbFrom.append(" FROM");
		
		if("search_circuit_view".equals(view)){
			sbFrom.append(" (");
		}
		
		
		
		if("search_circuit_view".equals(view)){
			sbFrom.append(" ((((((((circuit_inventory ci STRAIGHT_JOIN ban b ON ci.ban_id = b.id) ");
			sbFrom.append(" JOIN vendor v ON b.vendor_id = v.id)");
			sbFrom.append(" JOIN invoice i ON b.id = i.ban_id)");
			sbFrom.append(" JOIN invoice_item ii ON ii.invoice_id = i.id AND ii.stripped_circuit_number = ci.stripped_circuit_number ) ");
			sbFrom.append(" JOIN proposal p ON ii.id = p.invoice_item_id) ");
			sbFrom.append(" LEFT JOIN item_type it ON p.item_type_id = it.id)");
			
			sbFrom.append(" ");
			
			
		}else if("search_circuit_all_view".equals(view)){
			sbFrom.append(" ((((((((proposal p JOIN invoice_item ii ON ii.id = p.invoice_item_id) ");
			
			sbFrom.append(" JOIN item_type it ON p.item_type_id = it.id)");
			sbFrom.append(" JOIN invoice i ON p.invoice_id = i.id)");
			sbFrom.append(" JOIN ban b ON i.ban_id = b.id)");
			sbFrom.append(" JOIN vendor v ON b.vendor_id = v.id)");
		}
		
		sbFrom.append(" LEFT JOIN user u ON i.assigned_analyst_id = u.id)");
		
		if(useScoaCode){
			sbFrom.append(" JOIN account_code ac ON ac.id = p.account_code_id)");
		}else{
			sbFrom.append(" LEFT JOIN account_code ac ON ac.id = p.account_code_id)");
		}
		sbFrom.append(" LEFT JOIN currency c ON b.currency_id = c.id)");
		sbFrom.append(" WHERE  p.rec_active_flag = 'Y' AND v.rec_active_flag = 'Y'");
		sbFrom.append(" AND b.rec_active_flag = 'Y' AND b.master_ban_flag = 'Y'");
		sbFrom.append(" AND i.rec_active_flag = 'Y' AND i.invoice_status_id >= 9");
		sbFrom.append(" AND i.invoice_status_id <> 80 AND p.proposal_flag = 1");
		sbFrom.append(" AND p.invoice_item_id IS NOT NULL AND ii.rec_active_flag = 'Y'");
		sbFrom.append(" AND ii.proposal_flag = 1");

		if("search_circuit_view".equals(view)){
			sbFrom.append(" AND ci.rec_active_flag = 'Y'");
//			sbFrom.append(" AND p.circuit_number IS NOT NULL");
		}
		
		sb.append(sbFrom.toString());
		sb.append(sbWhere.toString());
		//group by date_format(i.invoice_date, '%Y%m')
		logger.info("Exit method fromViewConditions");
		return sb.toString();
	}

}
