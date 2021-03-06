package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.AuditMemory;
import com.saninco.ccm.model.DisputeReason;
import com.saninco.ccm.model.Email;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceDAO;
import com.saninco.ccm.model.InvoiceItem;
import com.saninco.ccm.model.InvoiceLabel;
import com.saninco.ccm.model.InvoiceStatus;
import com.saninco.ccm.model.Label;
import com.saninco.ccm.model.Originator;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.TransactionHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.service.invoice.InvoiceDetailServiceImpl;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author xinyu.Liu
 * 
 */
public class InvoiceDetailDaoImpl extends HibernateDaoSupport implements
		IInvoiceDetailDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final Log log = LogFactory.getLog(InvoiceDAO.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 */
	public InvoiceDetailDaoImpl() {
	}

	public List<Object[]> searchListTabDataByItemType(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataByItemType.");
		final String sql = this.searchListTabDataByItemTypeQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchListTabDataByItemType.");
		return c;
	}

	private String searchListTabDataByItemTypeQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataByItemTypeQueryString.");
		StringBuffer sb = new StringBuffer(
				"select t.item_type_name, ii.item_type_id, count(0) as number_of_line,");
		sb
				.append(" sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount) as total_amount ");
		sb.append(" from ((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' ");
		sb
				.append(" and ii.rec_active_flag = 'Y')) left join item_type t on (ii.item_type_id = t.id and ii.rec_active_flag = 'Y')) ");
		sb.append(" where ii.invoice_id = " + sio.getInvoiceId()
				+ "  and  p.proposal_flag = 1   ");
		if (sio.getItemTypeId() != null) {
			sb.append(" and ii.item_type_id = " + sio.getItemTypeId() + " ");
		}
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb.append(" and p.invoice_item_id in ( " + sio.getInvoiceItemId()
				+ " )   ");
		sb.append(" group by t.item_type_name asc ");
		logger.info("Exit method searchListTabDataByItemTypeQueryString.");
		return sb.toString();
	}
	
	/**
	 * Insert scoa data into temporary table.
	 */
	public void insertSCOADataTemporary(List<Map<String,String>> dataList, List<String> columnList, 
			String batchNo, String invoiceId) throws SQLException {
			
		// SQL string.
		StringBuffer insertSb = new StringBuffer("INSERT INTO scoa_import (`proposal_id`," +
				"`account_code_name`," +
				"`user_id`," +
				"`batch_no`," +
				"`row_no`) " +
				"values (?,?,?,?,?)");
	
		Session session = getSession();
		
		try {
			
			Connection conn = session.connection();
			PreparedStatement stmt = conn.prepareStatement(insertSb.toString());
			conn.setAutoCommit(false);
			
			// Iterate scoa data list.
			for (int i=0;i<dataList.size();i++){
				
				Map<String,String> map = dataList.get(i);
				
				// Iterating excel column data that insert into temporary table.
				for(int j=0;j<columnList.size();j++){
					
					String str = map.get(columnList.get(j));
					stmt.setString(j+1,str);
					
				}
				
				stmt.setString(columnList.size()+1,SystemUtil.getCurrentUserId()+""); // User id.
				stmt.setString(columnList.size()+2,batchNo); // Batch No.
				stmt.setString(columnList.size()+3,(i+1)+""); // Row No.
				stmt.addBatch();
				
			    if ( i > 0 && i % 300 == 0) {
			        stmt.executeBatch();
			        conn.commit();
			    }
			    
			} // /. For
			
			stmt.executeBatch();
			conn.commit();
			
			// Validating uploaded scoa data
			CallableStatement proc = null;
			proc = conn.prepareCall("call SP_UPLOAD_SCOA_VERIFICATION('"+batchNo+"', '"+ invoiceId +"')");
			proc.execute();
			
			conn.commit();
			
		} finally {
			
			releaseSession(session);
			
			
		}
		
	}

	/**
	 * Query error data list from error temporary table
	 * so that creating error data excel file.
	 * @return
	 */
	public List<Map<String,Object>> queryTmpSCOAError() {
		
		logger.info("Enter method queryTmpSCOAError.");
		
		final String sql = "SELECT row_number, record_id, field, note FROM tmp_scoa_error";
		
		Session session = getSession();
		
		// Error list.
		List list;
		
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method queryTmpSCOAError.");
		
		return list;
		
	}
	
	public List<Object[]> searchListTabDataByDisputed(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataByDisputed.");
		final String sql = this.searchListTabDataByDisputedQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchListTabDataByDisputed.");
		return c;
	}

	private String searchListTabDataByDisputedQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataByDisputedQueryString.");
		StringBuffer sb = new StringBuffer(
				"select t.item_type_name, ii.item_type_id, count(0) as number_of_line,");
		sb
				.append(" sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount) as total_amount ");
		sb.append(" from ((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' ");
		sb
				.append(" and ii.rec_active_flag = 'Y')) left join item_type t on (ii.item_type_id = t.id and ii.rec_active_flag = 'Y')) ");
		sb.append(" where ii.invoice_id = " + sio.getInvoiceId()
				+ "  and  p.proposal_flag = 1   ");
		if (sio.getItemTypeId() != null) {
			sb.append(" and ii.item_type_id = " + sio.getItemTypeId() + " ");
		}
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb
				.append(" and p.dispute_amount <> 0 and p.dispute_amount is not null  ");
		sb.append(" group by t.item_type_name asc ");
		logger.info("Exit method searchListTabDataByDisputedQueryString.");
		return sb.toString();
	}

	public List<Object[]> searchListTabDataBySCOA(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataBySCOA.");
		final String sql = this.searchListTabDataBySCOAQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchListTabDataBySCOA.");
		return c;
	}

	private String searchListTabDataBySCOAQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataBySCOAQueryString.");
		StringBuffer sb = new StringBuffer(
				"select t.item_type_name, ii.item_type_id, count(0) as number_of_line,");
		sb
				.append(" sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount) as total_amount ");
		sb.append(" from ((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' ");
		sb
				.append(" and ii.rec_active_flag = 'Y')) left join item_type t on (ii.item_type_id = t.id and ii.rec_active_flag = 'Y')) ");
		sb.append(" where ii.invoice_id = " + sio.getInvoiceId()
				+ "  and  p.proposal_flag = 1   ");
		if (sio.getItemTypeId() != null) {
			sb.append(" and ii.item_type_id = " + sio.getItemTypeId() + " ");
		}
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb.append(" and p.account_code_id is null  ");
		sb.append(" group by t.item_type_name asc ");
		logger.info("Exit method searchListTabDataBySCOAQueryString.");
		return sb.toString();
	}

	public List<Object[]> searchListTabDataByBrowse(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataByBrowse.");
		final String sql = this.searchListTabDataByBrowseQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchListTabDataByBrowse.");
		return c;
	}

	private String searchListTabDataByBrowseQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method searchListTabDataByBrowseQueryString.");
		StringBuffer sb = new StringBuffer(
				"select t.item_type_name, ii.item_type_id, count(0) as number_of_line,");
		sb
				.append(" sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount) as total_amount ");
		sb.append(" from ((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' ");
		sb
				.append(" and ii.rec_active_flag = 'Y')) left join item_type t on (ii.item_type_id = t.id and ii.rec_active_flag = 'Y')) ");
		sb.append(" where ii.invoice_id = " + sio.getInvoiceId()
				+ "  and  p.proposal_flag = 1   ");
		if (sio.getItemTypeId() != null) {
			sb.append(" and ii.item_type_id = " + sio.getItemTypeId() + " ");
		}
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb.append(" and p.account_code_id is null  ");
		sb.append(" group by t.item_type_name asc ");
		logger.info("Exit method searchListTabDataByBrowseQueryString.");
		return sb.toString();
	}

	public List<Object[]> findTypeOpenAccByItemType(
			SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findTypeOpenAccByItemType.");
		final String sql = this
				.findTypeOpenAccByItemTypeQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findTypeOpenAccByItemType.");
		return c;
	}

	private String findTypeOpenAccByItemTypeQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method findTypeOpenAccByItemTypeQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,");
		sb
				.append("',disputeAmount:\"',ROUND(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",paymentAmount:\"',ROUND(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",creditAmount:\"',ROUND(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",dispute:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",payment:\"',format(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",credit:\"',format(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb
				.append("'\",chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),");
		sb
				.append("'\",serviceType:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb
				.append("'\",accountCodeId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",SCOA:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\",itemName:\"',toJSON(ii.item_name is null,'',ii.item_name),");
		sb
				.append("'\",itemAmount:\"',format(if(ii.item_amount is null,'',ii.item_amount),2),");
		sb
				.append("'\",accountCodeId:\"',format(if(p.account_code_id is null,'',p.account_code_id),2),");
		sb
				.append("'\",proposalFlag:\"',if(p.proposal_flag is null,'',p.proposal_flag),");
		sb
				.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb
				.append("'\",amount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2),");
		sb
				.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb
				.append("'\",SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb
				.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		if (!"".equals(sio.getTableField())) {
			sb.append("'\"," + sio.getTableField() + " ");
		}
		sb.append("'\"}') a ");
		sb.append(voItemTypeListTabWhereConditions(sio));
		// sb.append(" group by ii.id ");
		sb.append(sio.getOrderByCause(null));
		sb.append("limit " + sio.getStartIndex());
		sb.append("," + sio.getRecPerPage());
		logger.info("Exit method findTypeOpenAccByItemTypeQueryString.");
		return sb.toString();
	}

	public long getItemTypeListTabNumber(SearchInvoiceVO sio) {
		logger.info("Enter method getItemTypeListTabNumber.");
		final String sql = this.getItemTypeListTabNumberQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getItemTypeListTabNumber.");
		return count;
	}

	private String getItemTypeListTabNumberQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getItemTypeListTabNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voItemTypeListTabWhereConditions(sio));
		logger.info("Exit method getItemTypeListTabNumberQueryString.");
		return sb.toString();
	}

	private String voItemTypeListTabWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voItemTypeListTabWhereConditions.");
		StringBuffer sb = new StringBuffer(
				" from ((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id )) ");
		sb
				.append(" left join account_code ac on (ac.id = p.account_code_id and ac.rec_active_flag = 'Y')) ");
		sb.append(" where p.item_type_id=" + sio.getItemTypeId() + " ");
		sb.append(" and p.invoice_item_id in ( " + sio.getInvoiceItemId()
				+ " ) ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		logger.info("Exit method voItemTypeListTabWhereConditions.");
		return sb.toString();
	}

	public List<Object[]> getSOCADisputeListOfObjectByitemTypeId(
			SearchInvoiceVO sio) {
		logger.info("Enter method getSOCADisputeListOfObjectByitemTypeId.");

		final String sql = this.getSOCADisputeListOfObjectByitemTypeIdQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getSOCADisputeListOfObjectByitemTypeId.");
		return c;
	}

	private String getSOCADisputeListOfObjectByitemTypeIdQueryString(
			SearchInvoiceVO sio) {
		logger.info("Enter method getSOCADisputeListOfObjectByitemTypeIdQueryString.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" if(p.id is null,'',p.id), "); // Proposal Id.
		sb.append(" if(ii.item_name is null,'',ii.item_name), "); // Item.
		sb.append(" format(if(p.payment_amount is null,0,p.payment_amount),2), "); // Payment.
		sb.append(" format(if(p.dispute_amount is null,0,p.dispute_amount),2), "); // Dispute.

		// yinghe.fu add by
		if ( ( "13".equals(sio.getItemTypeId()) || sio.getItemTypeId().indexOf("3") == 0 )
				&& "Browse Invoice".equals(sio.getAccordionName())) { // MRC
			sb.append(" format(if(ci.expected_total_mrc is null,0,ci.expected_total_mrc),2), ");
		}

		sb.append(" format(if(p.credit_amount is null,0,p.credit_amount),2), "); //Credit Amount	
		sb.append(" if(ac.account_code_name is null,'',ac.account_code_name), "); //SCOA 
		sb.append(" if(p.service_type is null,'',p.service_type), ");  //Service Type
		
		
		if ( !( "14".equals(sio.getItemTypeId()) || sio.getItemTypeId().indexOf("4") == 0 || "18".equals(sio.getItemTypeId()) || sio.getItemTypeId().indexOf("8") == 0 )
				&& "Browse Invoice".equals(sio.getAccordionName())) {
			sb.append(" if(p.service_id is null,'',p.service_id), ");  //Service Id
		}
		
		
		sb.append(" if(p.charge_type is null,'',p.charge_type), ");    //Charge Type 
		if(!("SCOA Coding".equals(sio.getAccordionName()) || "Disputed Items".equals(sio.getAccordionName()))) {
		//	sb.append(" if(ps.province_source_name is null,'',ps.province_source_name), "); // Province Source.
			sb.append(" if(pp.province_acronym is null,'',pp.province_acronym), ");
		}

		
		if ( ( "14".equals(sio.getItemTypeId()) || sio.getItemTypeId().indexOf("4") == 0 )
				&& "Browse Invoice".equals(sio.getAccordionName())) { // Usage
			sb.append(" if(ii.terminating_province is null,'',ii.terminating_province), ");
		}
		
		sb.append(" if(p.circuit_number is null,'',p.circuit_number), ");
		
		if(!("SCOA Coding".equals(sio.getAccordionName()) || "Disputed Items".equals(sio.getAccordionName()))) {
			sb.append(" if(ii.stripped_circuit_number is null,'',ii.stripped_circuit_number), ");
			sb.append(" if(pt.product_name is null,'',pt.product_name), "); // Product.
			sb.append(" if(pc.component_name is null,'',pc.component_name), "); // Component.
		}
		
		

		if ( !( "18".equals(sio.getItemTypeId()) || sio.getItemTypeId().indexOf("8") == 0 )
				&& "Browse Invoice".equals(sio.getAccordionName())) { // Tax
			sb.append(" if(ii.purchase_order_number is null, '', ii.purchase_order_number), ");
			sb.append(" IF(ii.start_date IS NULL,'',ii.start_date), "); // From Date.
			sb.append(" IF(ii.end_date IS NULL,'',ii.end_date) "); // To Date.
		} else {
			sb.append(" if(ii.purchase_order_number is null, '', ii.purchase_order_number) ");
		}

		
		
		sb.append(" " + sio.getTableField() + " ");

		if ("Browse Invoice".equals(sio.getAccordionName())) {
			sb.append(" ,IF(d.dispute_number IS NULL,'',d.dispute_number), "); // Dispute Number.
			sb.append(" IF(d.notes IS NULL,'',d.notes) "); // Notes.
			
		}
		sb.append(" from proposal p left join invoice_item ii ");
		sb.append(" on p.invoice_item_id is not null and ii.id = p.invoice_item_id ");
		sb.append(" left join province pp on pp.id = p.province_id ");
		sb.append(" left join  province_source ps on ps.id = p.province_source_id ");
		sb.append(" left join  product pt on p.product_id = pt.id");
		sb.append(" left join  product_component as pc on p.product_component_id = pc.id ");
		sb.append(" left join account_code ac on ac.id = p.account_code_id ");

		// Modify By xin.huang 2012-02-29 Start
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio.getInvoiceId());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.rec_active_flag = 'Y' and  ac.active_flag = 'Y'");
		}
		// Modify By xin.huang 2012-02-29 End
		sb.append(" LEFT JOIN invoice i ON i.id = p.invoice_id ");
		sb.append(" LEFT JOIN ban b ON i.ban_id = b.id ");
		sb.append(" LEFT JOIN circuit_inventory ci ON ii.stripped_circuit_number = ci.stripped_circuit_number AND ci.ban_id = b.id ");
		
		if ("Browse Invoice".equals(sio.getAccordionName())) {
			sb.append(" LEFT JOIN dispute AS d on p.dispute_id = d.id ");

			
		}
		// Where condition.
		sb.append(" where p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" and p.rec_active_flag = 'Y' ");

		if (!"Browse Invoice".equals(sio.getAccordionName())) {
			sb.append(" and (if(p.payment_amount is null,0,p.payment_amount) != 0 ");
			sb.append(" or if(p.dispute_amount is null,0,p.dispute_amount) != 0 ");
			sb.append(" or if(p.credit_amount is null,0,p.credit_amount) != 0 ) ");
		}

		if ("Browse Invoice".equals(sio.getAccordionName())) {
			// if (sio.getParentProposalId() == null ||
			// "null".equals(sio.getParentProposalId())) {
			// sb.append(" and p.parent_proposal_id is null ");
			// } else {
			// sb.append(" and p.parent_proposal_id = " +
			// sio.getParentProposalId() + " ");
			// }
		} else if ("SCOA Coding".equals(sio.getAccordionName())) {
//			sb.append(" and p.proposal_flag = 1 ");
			// Modify By xin.huang 2012-02-29 Start
			//sb.append(" and p.account_code_id is null ");
			sb.append(" and ac.id is null ");
			// Modify By xin.huang 2012-02-29 End
			sb.append(" and p.invoice_item_id is not null ");
		} else if ("Disputed Items".equals(sio.getAccordionName())) {
//			sb.append(" and p.proposal_flag = 1 ");
			sb.append(" and p.dispute_amount <> 0 and p.dispute_amount is not null ");
		} else {
//			sb.append(" and p.proposal_flag = 1 ");
			if (sio.getInvoiceItemId() != null) {
				sb.append(" and p.invoice_item_id in ( "
						+ sio.getInvoiceItemId() + " ) ");
			} else {
				sb.append(" and p.invoice_item_id is null ");
			}
		}
	    sb.append(" and p.proposal_flag = 1 ");
		sb.append(" and f_get_root_item_type(p.item_type_id) = f_get_root_item_type(" + sio.getItemTypeId() + ") ");
		if (sio.getFilter() != null) {
			sb.append(" and ");
			sb.append(sio.getFilter());
		}
		sb.append(" order by " + sio.getSortField() + " "
				+ sio.getSortingDirection());

		logger.info("Exit method getSOCADisputeListOfObjectByitemTypeIdQueryString.");
		return sb.toString();
	}


	public List<Object[]> findTypeOpenAccByBrowseInvoice(
			SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findTypeOpenAccByBrowseInvoice.");
		final String sql = this
				.typeOpenAccByBrowseInvoiceQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findTypeOpenAccByBrowseInvoice.");
		return c;
	}

	/**
	 * Invoice detail 中 ‘Browse Invoice’ tab content 中的除了 'All'
	 * 以外的其他的 sub tab 的内容的数据查询。
	 * @param  sio [description]
	 * @return     [description]
	 */
	private String typeOpenAccByBrowseInvoiceQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method typeOpenAccByBrowseInvoiceQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,");
		sb.append("',disputeAmount:\"',ROUND(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb.append("'\",paymentAmount:\"',ROUND(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb.append("'\",audit_status_id:\"',toJSON(p.audit_status_id is null,'',p.audit_status_id),");
		sb.append("'\",audit_status_name:\"',toJSON(aus.audit_status_name is null,'',aus.audit_status_name),");
		sb.append("'\",audit_exists_status_id:\"',toJSON(audit_exists_status_id is null,'',audit_exists_status_id),");
		sb.append("'\",audit_exists_status_name:\"',toJSON(aues.audit_status_name is null,'',aues.audit_status_name),");
		// yinghe.fu
		sb.append("'\",expectedMRC:\"',ROUND(if(ci.expected_total_mrc is null,0,ci.expected_total_mrc),2),");
		sb.append("'\",creditAmount:\"',ROUND(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb.append("'\",dispute:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb.append("'\",payment:\"',format(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb.append("'\",credit:\"',format(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb.append("'\",province:\"',toJSON( pp.province_acronym is null,'', pp.province_acronym),");
		sb.append("'\",province_source:\"',toJSON( ps.province_source_name is null,'', ps.province_source_name),");
		sb.append("'\",terminating_province:\"',toJSON( ii.terminating_province is null,'', ii.terminating_province),");
		sb.append("'\",circuitNumber:\"',if(p.circuit_number is null,'',p.circuit_number),");
		sb.append("'\",stripped_circuit_number:\"',if(ii.stripped_circuit_number is null,'',ii.stripped_circuit_number),");
		sb.append("'\",product_name:\"',if(pt.product_name is null,'',pt.product_name),");
		sb.append("'\",component_name:\"',if(pc.component_name is null,'',pc.component_name),");
		sb.append("'\",chargeType:\"',if(p.charge_type is null,'',p.charge_type),");
		sb.append("'\",serviceType:\"',if(p.service_type is null,'',p.service_type),");
        sb.append("'\",serviceId:\"',if(p.service_id is null,'',p.service_id),");
		sb.append("'\",accountCodeId:\"',if(p.account_code_id is null,'',p.account_code_id),");
		sb.append("'\",SCOA:\"',if(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\",itemName:\"',toJSON(ii.item_name is null,'',ii.item_name),");
		sb.append("'\",itemAmount:\"',format(if(ii.item_amount is null,'',ii.item_amount),2),");
		sb.append("'\",accountCodeId:\"',format(if(p.account_code_id is null,'',p.account_code_id),2),");
		sb.append("'\",proposalFlag:\"',if(p.proposal_flag is null,'',p.proposal_flag),");
		sb.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb.append("'\",amount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2),");
		sb.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb.append("'\",SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		sb.append("'\",pon:\"',toJSON(ii.purchase_order_number IS NULL, '', ii.purchase_order_number),");
		sb.append("'\",fromDate:\"',toJSON(ii.start_date IS NULL,'',ii.start_date),"); // From Date
		sb.append("'\",toDate:\"',toJSON(ii.end_date IS NULL,'',ii.end_date),"); // To Date
		sb.append("'\",disputeId:\"',toJSON(d.id IS NULL,'',d.id),"); // Dispute Id.
		sb.append("'\",disputeNumber:\"',toJSON(d.dispute_number IS NULL,'',d.dispute_number),"); // Dispute Number.
		sb.append("'\",disputeNotes:\"',toJSON(d.notes IS NULL,'',d.notes),"); // Notes.
		
		// modfiy by xin.huang on 2012-10-24 start
		sb.append("'\",circuitId:\"',toJSON(ii.vendor_circuit_id IS NULL, '', ii.vendor_circuit_id),");
		sb.append("'\",tariffCount:\"',(select count(1)  from tariff_link where circuit_id = ii.vendor_circuit_id and rec_active_flag = 'Y'),");
		// modfiy by xin.huang on 2012-10-24 end
		if (!"".equals(sio.getTableField())) {
			sb.append("'\"," + sio.getTableField() + " ");
		}
		sb.append("'\"}') a ");

		// from .. where (包含表的链接)。
		sb.append(voBrowseInvoiceListTabWhereConditions(sio));
		// sb.append(" group by ii.id ");
		sb.append(sio.getOrderByCause("ii"));
		sb.append("limit " + sio.getStartIndex());
		sb.append("," + sio.getRecPerPage());
		logger.info("Exit method typeOpenAccByBrowseInvoiceQueryString.");
		return sb.toString();
	}

	public long getBrowseInvoiceListTabNumber(SearchInvoiceVO sio) {
		logger.info("Enter method getBrowseInvoiceListTabNumber.");
		final String sql = this.getBrowseInvoiceListTabNumberQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getBrowseInvoiceListTabNumber.");
		return count;
	}

	private String getBrowseInvoiceListTabNumberQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getBrowseInvoiceListTabNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voBrowseInvoiceListTabWhereConditions(sio));
		logger.info("Exit method getBrowseInvoiceListTabNumberQueryString.");
		return sb.toString();
	}

	/**
	 * from .. where （SQL）
	 * Browse Invoice 标签页中，除了 all 以外的其他的子标签页
	 * 中的字段内容显示是所需的表关联信息以及Where 条件限制。
	 * @param  sio [description]
	 * @return     [description]
	 */
	private String voBrowseInvoiceListTabWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voBrowseInvoiceListTabWhereConditions.");
		StringBuffer sb = new StringBuffer(" from proposal p left join invoice_item ii ");
		sb.append(" on p.invoice_item_id is not null and ii.id = p.invoice_item_id ");
		// yinghe.fu
		sb.append(" left join account_code ac on ac.id = p.account_code_id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio.getInvoiceId());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.active_flag = 'Y'");
			sb.append(" and ac.rec_active_flag = 'Y'");
		}
		sb.append(" left join audit_status aus on p.audit_status_id = aus.id ");
		sb.append(" left join audit_status aues on p.audit_exists_status_id = aues.id ");
		sb.append(" left join invoice i on i.id = p.invoice_id ");
		sb.append(" left join ban b on i.ban_id = b.id ");
		sb.append(" left join circuit_inventory ci on ii.stripped_circuit_number = ci.stripped_circuit_number and ci.ban_id = b.id ");

		sb.append(" left join  province pp on pp.id = p.province_id ");
		sb.append(" left join  province_source ps on ps.id = p.province_source_id ");
		sb.append(" left join  product as pt on p.product_id = pt.id ");
		sb.append(" left join  product_component as pc on p.product_component_id = pc.id ");
		
		sb.append(" left join dispute AS d on p.dispute_id = d.id ");

		// Where 查询条件。
		sb.append(" where f_get_root_item_type(p.item_type_id) = f_get_root_item_type(" + sio.getItemTypeId() + ") ");
//		sb.append(" where p.item_type_id in (select t1.id from item_type t1,item_type t2 where t1.item_type_name = t2.item_type_name and t2.id = " + sio.getItemTypeId() + ") ");
		sb.append(" and p.proposal_flag = 1 ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
//		sb
//				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		if (sio.getFilter() != null) {
			sb.append(" and ");
			if("(1=1 and p.audit_status_id like '%4%')".equals(sio.getFilter()) ) {
				sb.append("(1=1 and p.audit_status_id like '%4%' or p.audit_status_id is null)");
			}else {
				sb.append(sio.getFilter());
			}
		}
		logger.info("Exit method voBrowseInvoiceListTabWhereConditions.");
		return sb.toString();
	}

	public List<Object[]> findTypeOpenAccByDisputeItems(
			SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findTypeOpenAccByDisputeItems.");
		final String sql = this
				.typeOpenAccByDisputeItemsQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findTypeOpenAccByDisputeItems.");
		return c;
	}

	private String typeOpenAccByDisputeItemsQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method typeOpenAccByDisputeItemsQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,");
		sb
				.append("',disputeAmount:\"',ROUND(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",paymentAmount:\"',ROUND(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",creditAmount:\"',ROUND(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",dispute:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",payment:\"',format(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",credit:\"',format(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",circuitNumber:\"',if(p.circuit_number is null,'',p.circuit_number),");
		sb
				.append("'\",chargeType:\"',if(p.charge_type is null,'',p.charge_type),");
		sb
				.append("'\",serviceType:\"',if(p.service_type is null,'',p.service_type),");
		sb
				.append("'\",accountCodeId:\"',if(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",SCOA:\"',if(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\",itemName:\"',toJSON(ii.item_name is null,'',ii.item_name),");
		sb
				.append("'\",itemAmount:\"',format(if(ii.item_amount is null,'',ii.item_amount),2),");
		sb
				.append("'\",pon:\"',if(ii.purchase_order_number IS NULL, '', ii.purchase_order_number),");
		sb
				.append("'\",accountCodeId:\"',format(if(p.account_code_id is null,'',p.account_code_id),2),");
		sb
				.append("'\",proposalFlag:\"',if(p.proposal_flag is null,'',p.proposal_flag),");
		sb
				.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb
				.append("'\",amount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2),");
		sb
				.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb
				.append("'\",SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb
				.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		if (!"".equals(sio.getTableField())) {
			sb.append("'\"," + sio.getTableField() + " ");
		}
		sb.append("'\"}') a ");
		sb.append(voDisputeItemsListTabWhereConditions(sio));
		// sb.append(" group by ii.id ");
		sb.append(sio.getOrderByCause(null));
		sb.append("limit " + sio.getStartIndex());
		sb.append("," + sio.getRecPerPage());
		logger.info("Exit method typeOpenAccByDisputeItemsQueryString.");
		return sb.toString();
	}

	public long getDisputeItemsListTabNumber(SearchInvoiceVO sio) {
		logger.info("Enter method getDisputeItemsListTabNumber.");
		final String sql = this.getDisputeItemsListTabNumberQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getDisputeItemsListTabNumber.");
		return count;
	}

	private String getDisputeItemsListTabNumberQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getDisputeItemsListTabNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voDisputeItemsListTabWhereConditions(sio));
		logger.info("Exit method getDisputeItemsListTabNumberQueryString.");
		return sb.toString();
	}

	private String voDisputeItemsListTabWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voDisputeItemsListTabWhereConditions.");
		StringBuffer sb = new StringBuffer(
				" from ((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id )) ");
		sb.append(" left join account_code ac on (ac.id = p.account_code_id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio
				.getInvoiceId());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.rec_active_flag = 'Y' and ac.active_flag = 'Y'");
		}
		sb.append(" )) where f_get_root_item_type(p.item_type_id)=f_get_root_item_type('" + sio.getItemTypeId() + "') ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" and p.proposal_flag = 1 ");
		sb
				.append(" and p.dispute_amount <> 0 and p.dispute_amount is not null ");
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		if (sio.getFilter() != null) {
			sb.append(" and ");
			sb.append(sio.getFilter());
		}
		logger.info("Exit method voDisputeItemsListTabWhereConditions.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findTypeOpenAcc(SearchInvoiceVO)
	 * 
	 */
	public List<Object[]> findTypeOpenAcc(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findTypeOpenAcc.");
		final String sql = this.typeOpenAccQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findTypeOpenAcc.");
		return c;
	}

	private String typeOpenAccQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method typeOpenAccQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,");
		sb
				.append("',disputeAmount:\"',ROUND(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",paymentAmount:\"',ROUND(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",creditAmount:\"',ROUND(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",dispute:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",payment:\"',format(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",credit:\"',format(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",circuitNumber:\"',if(p.circuit_number is null,'',p.circuit_number),");
		sb
				.append("'\",chargeType:\"',if(p.charge_type is null,'',p.charge_type),");
		sb
				.append("'\",serviceType:\"',if(p.service_type is null,'',p.service_type),");
		sb
				.append("'\",accountCodeId:\"',if(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",SCOA:\"',if(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\",itemName:\"',toJSON(ii.item_name is null,'',ii.item_name),");
		sb
				.append("'\",itemAmount:\"',format(if(ii.item_amount is null,'',ii.item_amount),2),");
		sb
				.append("'\",pon:\"',if(ii.purchase_order_number IS NULL, '', ii.purchase_order_number),");
		sb
				.append("'\",accountCodeId:\"',format(if(p.account_code_id is null,'',p.account_code_id),2),");
		sb
				.append("'\",proposalFlag:\"',if(p.proposal_flag is null,'',p.proposal_flag),");
		sb
				.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb
				.append("'\",amount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2),");
		sb
				.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb
				.append("'\",SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb
				.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		if (!"".equals(sio.getTableField())) {
			sb.append("'\"," + sio.getTableField() + " ");
		}
		sb.append("'\"}') a ");
		sb.append(voEmptySCOAListTabWhereConditions(sio));
		// sb.append(" group by ii.id ");
		sb.append(sio.getOrderByCause(null));
		sb.append("limit " + sio.getStartIndex());
		sb.append("," + sio.getRecPerPage());
		logger.info("Exit method typeOpenAccQueryString.");
		return sb.toString();
	}

	public long getEmptySCOAListTabNumber(SearchInvoiceVO sio) {
		logger.info("Enter method getEmptySCOAListTabNumber.");
		final String sql = this.getEmptySCOAListTabNumberQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getEmptySCOAListTabNumber.");
		return count;
	}

	private String getEmptySCOAListTabNumberQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getEmptySCOAListTabNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voEmptySCOAListTabWhereConditions(sio));
		logger.info("Exit method getEmptySCOAListTabNumberQueryString.");
		return sb.toString();
	}

	private String voEmptySCOAListTabWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voEmptySCOAListTabWhereConditions.");
		StringBuffer sb = new StringBuffer(
				" from (((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id )) ");
		sb
		        .append(" left join province pp on (pp.id = p.province_id)) ");
		sb
				.append(" left join account_code ac on (ac.id = p.account_code_id ");
		//wenbo.zhang
		//2016-07-08
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio
				.getInvoiceId());
		if (invoiceStatusId < 21) {
					sb.append(" and ac.active_flag = 'Y'");
					sb.append(" and ac.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
		sb.append(" where p.item_type_id=" + sio.getItemTypeId() + " ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" and p.proposal_flag = 1 ");
		if (sio.getInvoiceItemId() != null)
			sb.append(" and p.invoice_item_id in ( " + sio.getInvoiceItemId()
					+ " ) ");
		sb.append(" and ac.id is null ");
		sb
				.append(" and (if(p.payment_amount is null,0,p.payment_amount) != 0  or if(p.dispute_amount is null,0,p.dispute_amount) != 0  or if(p.credit_amount is null,0,p.credit_amount) != 0 ) ");
		if (sio.getFilter() != null) {
			sb.append(" and ");
			sb.append(sio.getFilter());
		}
		logger.info("Exit method voEmptySCOAListTabWhereConditions.");
		return sb.toString();
	}

	public void updateDisputeAmount(String invoiceId) {
		logger.info("Enter method updateDisputeAmount.");
		final String queryString = updateDisputeAmountQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateDisputeAmount.");
	}

	private String updateDisputeAmountQueryString(String invoiceId) {
		logger.info("Enter method updateDisputeAmountQueryString.");
		StringBuffer sb = new StringBuffer("update dispute d set ");
		sb
				.append(" d.dispute_amount=(select if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount))  ");
		sb
				.append(" from proposal p where p.rec_active_flag='Y' AND p.proposal_flag='1' ");
		sb.append(" AND p.dispute_id is not null AND p.dispute_id=d.id)  ,modified_timestamp = now(), modified_by = "+ SystemUtil.getCurrentUserId());
		// sb.append(" where d.rec_active_flag='Y' AND d.id in (" + disputeId +
		// ") ");
		// sb.append(" where d.rec_active_flag='Y' AND d.id in (select
		// p.dispute_id from proposal p where p.id in ("+disputeId+")) ");
		sb.append("  where d.rec_active_flag='Y' and d.invoice_id = "
				+ invoiceId + " ");
		logger.info("Exit method updateDisputeAmountQueryString.");
		return sb.toString();
	}

	public List<Object[]> searchProposalCountList(SearchInvoiceVO sio,
			String name) {
		logger.info("Enter method searchProposalCountList.");
		final String sql = this.searchProposalCountListQueryString(sio, name);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchProposalCountList.");
		return c;
	}

	private String searchProposalCountListQueryString(SearchInvoiceVO sio,
			String name) {
		logger.info("Enter method searchProposalCountListQueryString.");
		StringBuffer sb = new StringBuffer("select ");
		sb
				.append(" if(sum(the.co) is null,0,sum(the.co)) coun, format(if(sum(the.pa) is null,0,sum(the.pa)),2) paymentSum, ");
		sb
				.append(" format(if(sum(the.pa) is null,0,sum(the.da)),2) disputeSum, ");
		sb
				.append(" format(if(sum(the.ca) is null,0,sum(the.ca)),2) creditSum ");

		sb.append(" from ( ");
		sb
				.append(" select count(*) as co, round(if(sum(payment_amount) is null,0,sum(payment_amount)),2) as pa,");
		sb
				.append(" round(if(sum(dispute_amount) is null,0,sum(dispute_amount)),2) as da, ");
		sb
				.append(" round(if(sum(credit_amount) is null,0,sum(credit_amount)),2) as ca ");
		sb.append(" from proposal p ");
		sb.append(" where p.rec_active_flag = 'Y' ");
		if(!"BrowseInvoice".equals(name)){
			sb
					.append(" and (if(p.payment_amount is null,0,p.payment_amount) != 0 ");
			sb.append(" or if(p.dispute_amount is null,0,p.dispute_amount) != 0 ");
			sb.append(" or if(p.credit_amount is null,0,p.credit_amount) != 0 ) ");
		}

		if ("EmptySCOA".equals(name)) {
			sb.append(" and p.proposal_flag = 1 ");
			// Modify by xin.huang 2011-09-29 Start
			// sb.append(" and p.account_code_id is null ");
			Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio
					.getInvoiceId());
			if (invoiceStatusId < 21) {
				sb
						.append(" and p.account_code_id not in (select ac.id from account_code ac where ac.id = p.account_code_id and ac.active_flag = 'Y' and ac.rec_active_flag = 'Y') ");
			} else {
				sb.append(" and 1 = 0 "); // do not search anything
			}
			// Modify by xin.huang 2011-09-29 End
			sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
			sb.append(" and p.invoice_item_id is not null ");
		}
		if ("DisputeItems".equals(name)) {
			sb.append(" and p.proposal_flag = 1 ");
			sb
					.append(" and p.dispute_amount <> 0 and p.dispute_amount is not null ");
			sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
			sb.append(" and p.invoice_item_id is not null ");
		}
		if("BrowseInvoice".equals(name)){
			sb.append(" and p.proposal_flag = 1 ");
			sb.append(" and p.invoice_item_id is not null ");
			sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		}

		if ("ItemLabelList".equals(name)) {
			sb.append(" and p.proposal_flag = '1' ");
			if (sio.getInvoiceItemId() != null) {
				sb.append(" and p.invoice_item_id in ( "
						+ sio.getInvoiceItemId() + " ) ");
			} else {
				sb.append(" and p.invoice_item_id is null ");
			}
			sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		}

		sb.append(" group by p.account_code_id asc ");
		sb.append(" ) as the ");
		logger.info("Exit method searchProposalCountListQueryString.");
		return sb.toString();
	}
	
	public List<Object[]> getSOCADisputeForBrowseInvoiceListOfObject(StringBuffer querySql,String invoiceId,String itemTypeId) {
		logger.info("Enter method getSOCADisputeForBrowseInvoiceListOfObject.");
		final String sql = querySql +" from invoice_item ii where  ii.rec_active_flag = 'Y' and ii.invoice_id = " + invoiceId + " and ii.item_type_id = " + itemTypeId;
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
		.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getSOCADisputeForBrowseInvoiceListOfObject.");
		return c;
	}

	/**
	 * 'SCOA and Dispute' 手风琴菜单列表显示信息。
	 */
	public List<Object[]> getSOCADisputeListOfObject(SearchInvoiceVO sio) {
		logger.info("Enter method getSOCADisputeListOfObject.");
		final String sql = this.getSOCADisputeListOfObjectQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getSOCADisputeListOfObject.");
		return c;
	}

	/**
	 * Query string.
	 * 查询 ‘SCOA and Dispute’ 中的三个手风琴菜单的列表，
	 * SQL查询字段的顺序严格按照 table header title 列的顺序。
	 * 不同的菜单中可能有不同的列名。
	 * @param  sio [description]
	 * @return     [description]
	 */
	private String getSOCADisputeListOfObjectQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getSOCADisputeListOfObjectQueryString.");

		String BROWSE_INVOICE = "Browse Invoice";
		String SCOA_CODING = "SCOA Coding";
		String DISPUTED_ITEMS = "Disputed Items";
		StringBuffer sb = null;
		
		if ( BROWSE_INVOICE.equals( sio.getAccordionName() ) ) {

			sb = this.getListOfObjectForBrowseInoviceQueryString( sio );
		} else {

			sb = new StringBuffer("select ");
			// add by yinghe.fu at 20110919
			sb.append(" if(p.id is null,'',p.id),");

			sb.append(" if(p.item_type_id is null,'',it.item_type_name),");

			sb.append(" if(p.item_name is null,'',p.item_name),");
			sb.append(" format(if(p.payment_amount is null,0,p.payment_amount),2), ");
			sb.append(" format(if(p.dispute_amount is null,0,p.dispute_amount),2), ");
			sb.append(" format(if(p.credit_amount is null,0,p.credit_amount),2), ");
			sb.append(" if(ii.purchase_order_number is null, '', ii.purchase_order_number), ");
			
			
			sb.append(" if(p.circuit_number is null,'',p.circuit_number), ");
			//sb.append(" if(ii.stripped_circuit_number is null,'',ii.stripped_circuit_number), ");
			
			sb.append(" if(p.billing_number is null,'',p.billing_number), ");
			sb.append(" if(ac.account_code_name is null,'',ac.account_code_name), ");
			sb.append(" if(p.dispute_reason_id is null,'',dr.dispute_reason_name), ");
			sb.append(" if(p.notes is null,'',p.notes), ");
			sb.append(" if(p.description is null,'',p.description), ");

			sb.append(" if(p.address is null,'',p.address), ");
			sb.append(" if(p.service_type is null,'',p.service_type), ");
			sb.append(" if(p.charge_type is null,'',p.charge_type), ");

			sb.append(" if(p.usoc is null,'',p.usoc), ");
			sb.append(" if(p.usoc_description is null,'',p.usoc_description),");
		//	sb.append(" format(if(p.driect_quantity is null,0,p.driect_quantity),2), ");
			sb.append(" format(if(p.quantity is null,0,p.quantity),2), ");
			sb.append(" if(p.rate is null,'0.00',p.rate), ");
			sb.append(" format(if(p.minutes is null,0,p.minutes),2), ");
			sb.append(" format(if(p.number_of_calls is null,0,p.number_of_calls),2), ");
			sb.append(" if(p.cellular_indicator is null,'',p.cellular_indicator), ");
			sb.append(" if(p.country is null,'',p.country), ");
			sb.append(" format(if(p.mileage is null,0,p.mileage),2), ");
			sb.append(" if(p.asg is null,'',p.asg), ");
			sb.append(" if(p.jurisdiction is null,'',p.jurisdiction), ");
			sb.append(" if(p.direction is null,'',p.direction) ");

			// Modify By xin.huang 2012-02-29 Start.
			// From condition.
			sb.append(" from proposal p left join invoice_item ii ");
			sb.append(" on p.invoice_item_id is not null and ii.id = p.invoice_item_id  ");
			sb.append(" left join account_code ac on p.account_code_id = ac.id ");
			Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio.getInvoiceId());

			if (invoiceStatusId < 21) {
				sb.append(" and ac.active_flag = 'Y' AND  ac.rec_active_flag = 'Y'");
			}
			sb.append(" left join product pt on p.product_id = pt.id ");
			sb.append(" left join product_component pc on p.product_component_id = pc.id ");
			
			

			sb.append(" left join dispute_reason dr on p.dispute_reason_id = dr.id ");
			// Modify By xin.huang 2012-02-29 End
			sb.append(" left join item_type it on p.item_type_id = it.id ");
			sb.append(" left join province pp on pp.id = p.province_id ");

			// Where condition.
			sb.append(" where p.rec_active_flag = 'Y' ");
			sb.append(" and p.invoice_item_id is not null ");
			sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");

			// 非 Browse Invoice 菜单中的列的判断条件 （下面三个）。
			sb.append(" and (if(p.payment_amount is null,0,p.payment_amount) != 0 ");
			sb.append(" or if(p.dispute_amount is null,0,p.dispute_amount) != 0 ");
			sb.append(" or if(p.credit_amount is null,0,p.credit_amount) != 0 ) ");

			if ( SCOA_CODING.equals( sio.getAccordionName() ) ) {

				sb.append(" and p.proposal_flag = 1 ");
				sb.append(" and ac.id is null ");
				sb.append(" and p.invoice_item_id is not null ");
			} else if ( DISPUTED_ITEMS.equals( sio.getAccordionName() ) ) {

				sb.append(" and p.proposal_flag = 1 ");
				sb.append(" and p.dispute_amount <> 0 and p.dispute_amount is not null ");
			} else {

				sb.append(" and p.proposal_flag = 1 ");

				if (sio.getInvoiceItemId() != null) {
					sb.append(" and p.invoice_item_id in ( "+ sio.getInvoiceItemId() + " ) ");
				} else {
					sb.append(" and p.invoice_item_id is null ");
				}
			}

			if (sio.getFilter() != null) {
				sb.append(" and ");
				sb.append(sio.getFilter());
			}
			sb.append(" order by " + sio.getSortField() + " "
					+ sio.getSortingDirection());
		}

		

		logger.info("Exit method getSOCADisputeListOfObjectQueryString.");
		return sb.toString();
	}

	/**
	 * 这个方法是专门为 Browse Invoice 菜单定制的，
	 * 在这个菜单中有一些特殊的列是其他两个菜单没有的。
	 * 因此单独提出一个方法用来查询下载文件中的列值。
	 * @param  sio [description]
	 * @return     [description]
	 */
	private StringBuffer getListOfObjectForBrowseInoviceQueryString( SearchInvoiceVO sio ) {
		logger.info("Enter method getListOfObjectForBrowseInoviceQueryString.");

		StringBuffer sb = new StringBuffer("select ");
		
		sb.append(" if(p.id is null,'',p.id),"); // Proposal Id.

		sb.append(" if(p.item_name is null,'',p.item_name),"); // Item.
		sb.append(" format(if(p.payment_amount is null,0,p.payment_amount),2), "); // Payment.
		sb.append(" format(if(p.dispute_amount is null,0,p.dispute_amount),2), "); // Dispute.
		sb.append(" format(if(p.credit_amount is null,0,p.credit_amount),2), "); // Credit.
		sb.append(" if(ii.purchase_order_number is null, '', ii.purchase_order_number), "); // PON.
		
		// Browse Invoice 手风琴菜单中才会有这两个字段 （province source , province）.
//		sb.append(" if(ps.province_source_name is null,'',ps.province_source_name), "); // Province Source.
		sb.append(" if(pp.province_acronym is null,'',pp.province_acronym), "); // Procinve.
		
		
		sb.append(" if(p.circuit_number is null,'',p.circuit_number), "); // Circuit number.
		sb.append(" if(ii.stripped_circuit_number is null,'',ii.stripped_circuit_number), ");

		// Browse Invoice 手风琴菜单中才会有下面这两个字段 (product, component).
		sb.append(" if(pt.product_name is null,'',pt.product_name), "); // Product.
		sb.append(" if(pc.component_name is null,'',pc.component_name), "); // Component.
		
		sb.append(" if(p.billing_number is null,'',p.billing_number), "); // Billing number.
		sb.append(" if(ac.account_code_name is null,'',ac.account_code_name), "); // SCOA.
		sb.append(" if(p.dispute_reason_id is null,'',dr.dispute_reason_name), "); // Dispute category.
		sb.append(" if(p.notes is null,'',p.notes), "); // Notes.
		sb.append(" if(p.description is null,'',p.description), "); // Description. 

		sb.append(" if(p.address is null,'',p.address), "); // Address.
		sb.append(" if(p.service_type is null,'',p.service_type), "); // Service Type.
		sb.append(" if(p.charge_type is null,'',p.charge_type), "); // Charge Type.

		sb.append(" if(p.usoc is null,'',p.usoc), "); // USOC.
		sb.append(" if(p.usoc_description is null,'',p.usoc_description),"); // USOC Description/
		sb.append(" format(if(p.driect_quantity is null,0,p.driect_quantity),2), "); // Eo Direct Quantity
		sb.append(" format(if(p.quantity is null,0,p.quantity),2), "); // Quantity.
		sb.append(" if(p.rate is null,'0.00',p.rate), "); // Rate.
		sb.append(" format(if(p.minutes is null,0,p.minutes),2), "); // Minutes.
		sb.append(" format(if(p.number_of_calls is null,0,p.number_of_calls),2), "); // Number of Calls.
		sb.append(" if(p.cellular_indicator is null,'',p.cellular_indicator), "); // Cellular.
		sb.append(" if(p.country is null,'',p.country), "); // Country.  
		sb.append(" format(if(p.mileage is null,0,p.mileage),2), "); // Mileage.
		sb.append(" if(p.asg is null,'',p.asg), "); // ASG.
		sb.append(" if(p.jurisdiction is null,'',p.jurisdiction), "); // Jurisdiction.
		sb.append(" if(p.direction is null,'',p.direction), "); // direction.
		sb.append(" IF(ii.start_date IS NULL,'',ii.start_date), "); // From Date.
		sb.append(" IF(ii.end_date IS NULL,'',ii.end_date), "); // To Date.
		//Modify By wenbo.zhang 2018-07-26
		sb.append(" IF(d.dispute_number IS NULL,'',d.dispute_number), "); // Dispute Number.
		sb.append(" IF(d.notes IS NULL,'',d.notes) "); // Notes.
		
		// Modify By xin.huang 2012-02-29 Start.
		// From condition.
		sb.append(" from proposal p left join invoice_item ii ");
		sb.append(" on p.invoice_item_id is not null and ii.id = p.invoice_item_id  ");
		sb.append(" left join account_code ac on p.account_code_id = ac.id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio.getInvoiceId());

		if (invoiceStatusId < 21) {
			sb.append(" and ac.active_flag = 'Y' AND  ac.rec_active_flag = 'Y'");
		}
		sb.append(" left join product pt on p.product_id = pt.id ");
		sb.append(" left join product_component pc on p.product_component_id = pc.id ");
		
		
		

		sb.append(" left join dispute_reason dr on p.dispute_reason_id = dr.id ");
		// Modify By xin.huang 2012-02-29 End
		sb.append(" left join item_type it on p.item_type_id = it.id ");
		sb.append(" left join province pp on pp.id = p.province_id ");
		sb.append(" left join province_source ps on ps.id = p.province_source_id ");
		//Modify By wenbo.zhang 2018-07-26
		sb.append(" left join dispute AS d on p.dispute_id = d.id ");
		// Where condition.
		sb.append(" where p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_item_id is not null ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and p.proposal_flag = 1 ");
		logger.info("Exit method getListOfObjectForBrowseInoviceQueryString.");
		return sb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDateilDao#getNumberOfSOCADisputeList(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getNumberOfSOCADisputeList(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfSOCADisputeList.");
		final String sql = this.getNumberOfSOCADisputeListQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfSOCADisputeList.");
		return count;
	}

	private String getNumberOfSOCADisputeListQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfSOCADisputeListQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from proposal p ");
		sb.append(" where p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_item_id is not null ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		sb
				.append(" and (if(p.payment_amount is null,0,p.payment_amount) != 0 ");
		sb.append(" or if(p.dispute_amount is null,0,p.dispute_amount) != 0 ");
		sb.append(" or if(p.credit_amount is null,0,p.credit_amount) != 0 ) ");
		if (sio.getAccordionName() == "Browse Invoice") {
			if (sio.getParentProposalId() == null
					|| "null".equals(sio.getParentProposalId())) {
				sb.append(" and p.parent_proposal_id is null ");
			} else {
				sb.append(" and p.parent_proposal_id = "
						+ sio.getParentProposalId() + " ");
			}
		} else if (sio.getAccordionName() == "SCOA Coding") {
			sb.append(" and p.proposal_flag = 1 ");
			sb.append(" and p.account_code_id is null ");
			sb.append(" and p.invoice_item_id is not null ");
		} else if (sio.getAccordionName() == "Disputed Items") {
			sb.append(" and p.proposal_flag = 1 ");
			sb
					.append(" and p.dispute_amount <> 0 and p.dispute_amount is not null ");
		} else {
			if (sio.getInvoiceItemId() != null) {
				sb.append(" and p.invoice_item_id in ( "
						+ sio.getInvoiceItemId() + " ) ");
			} else {
				sb.append(" and p.invoice_item_id is null ");
			}
		}
		sb.append(" order by p.item_name asc, p.id asc ");
		logger.info("Exit method getNumberOfSOCADisputeListQueryString.");
		return sb.toString();
	}

	public void updateGroupDisputeProposalByDispute(SearchInvoiceVO sio,
			Integer userId) {
		logger.info("Enter method updateGroupDisputeProposal.");
		final String queryString = updateGroupDisputeProposalByDisputeQueryString(
				sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateGroupDisputeProposal.");
	}

	public void updateGroupDisputeProposalByPayment(SearchInvoiceVO sio,
			Integer userId) {
		logger.info("Enter method updateGroupDisputeProposal.");
		final String queryString = updateGroupDisputeProposalByPaymentQueryString(
				sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateGroupDisputeProposal.");
	}

	public void updateSCOACodingAndNoteData(SearchInvoiceVO sio, Integer userId) {
		logger.info("Enter method updateSCOACodingAndNoteData.");
		final String queryString = updateSCOACodingAndNoteDataQueryString(sio,
				userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateSCOACodingAndNoteData.");
	}

	private String updateSCOACodingAndNoteDataQueryString(SearchInvoiceVO sio,
			Integer userId) {
		logger.info("Enter method updateSCOACodingAndNoteDataQueryString.");
		StringBuffer sb = new StringBuffer("update proposal set ");
		sb.append(" account_code_id = '" + sio.getScoaCodeId() + "' ");
		sb.append(" , scoa_source_id = 720101 ");
		sb.append(" , notes = '" + CcmUtil.stringToSql(sio.getNote()) + "' ");
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + userId + "' ");
		sb.append(" where id in (" + sio.getBoxInId() + ") ");
		logger.info("Exit method updateSCOACodingAndNoteDataQueryString.");
		return sb.toString();
	}

	public void updateSCOACodingSingleAndNoteData(SearchInvoiceVO sio,
			String accountCodeId, String amount) {
		logger.info("Enter method updateSCOACodingSingleAndNoteData.");
		final String queryString = updateSCOACodingSingleAndNoteDataQueryString(
				sio, accountCodeId, amount);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateSCOACodingSingleAndNoteData.");
	}

	private String updateSCOACodingSingleAndNoteDataQueryString(
			SearchInvoiceVO sio, String accountCodeId, String amount) {
		logger
				.info("Enter method updateSCOACodingSingleAndNoteDataQueryString.");
		StringBuffer sb = new StringBuffer("insert into proposal( ");
		sb
				.append(" parent_proposal_id,invoice_id,invoice_item_id,item_type_id,account_code_id,"
						+ "payment_amount,dispute_amount,proposal_flag,item_name,circuit_number,service_type,"
						+ "charge_type,description,payment_id,dispute_id,attachment_point_id,dispute_reason_id,"
						+ "originator_id,created_timestamp,created_by,modified_timestamp,modified_by,rec_active_flag,"
						+ "dispute_reason,credit_amount,balance_amount,address,rate,quantity,usoc,usoc_description,"
						+ "minutes,number_of_calls,country,direction,billing_number,asg,jurisdiction,cellular_indicator,"
						+ "mileage,notes,org_proposal_id,scoa_source_id ");
		sb.append(" ) select ");
		sb
				.append(" p.parent_proposal_id,p.invoice_id,p.invoice_item_id,p.item_type_id,'"
						+ accountCodeId
						+ "',"
						+ "'"
						+ amount
						+ "',p.dispute_amount,p.proposal_flag,p.item_name,p.circuit_number,p.service_type,"
						+ "p.charge_type,p.description,p.payment_id,p.dispute_id,p.attachment_point_id,p.dispute_reason_id,"
						+ "p.originator_id,p.created_timestamp,p.created_by,now(),'"
						+ SystemUtil.getCurrentUserId()
						+ "',p.rec_active_flag,"
						+ "p.dispute_reason,p.credit_amount,p.balance_amount,p.address,p.rate,p.quantity,p.usoc,p.usoc_description,"
						+ "p.minutes,p.number_of_calls,p.country,p.direction,p.billing_number,p.asg,p.jurisdiction,p.cellular_indicator,"
						+ "p.mileage,'"
						+ CcmUtil.stringToSql(sio.getNote())
						+ "',p.org_proposal_id , 720201");
		sb.append(" from proposal p where p.id = " + sio.getBoxInId() + " ");
		logger
				.info("Exit method updateSCOACodingSingleAndNoteDataQueryString.");
		return sb.toString();
	}

	public void updateMoveToCreditProposal(SearchInvoiceVO sio, Integer userId) {
		logger.info("Enter method updateMoveToCreditProposal.");
		final String queryString = updateMoveToCreditProposalQueryString(sio,
				userId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateMoveToCreditProposal.");
	}

	private String updateMoveToCreditProposalQueryString(SearchInvoiceVO sio,
			Integer userId) {
		logger.info("Enter method updateMoveToCreditProposalQueryString.");
		StringBuffer sb = new StringBuffer("update proposal set ");
		sb
				.append(" credit_amount = if(payment_amount is null,0,payment_amount) + if(credit_amount is null,0,credit_amount) ");
		sb
				.append(" , balance_amount = if(payment_amount is null,0,payment_amount) + if(balance_amount is null,0,balance_amount) ");
		sb.append(" , payment_amount = '0' ");
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + userId + "' ");
		sb.append(" where id in (" + sio.getBoxInId() + ") ");
		logger.info("Exit method updateMoveToCreditProposalQueryString.");
		return sb.toString();
	}

	private String updateGroupDisputeProposalByDisputeQueryString(
			SearchInvoiceVO sio, Integer userId) {
		logger
				.info("Enter method updateGroupDisputeProposalByDisputeQueryString.");
		StringBuffer sb = new StringBuffer("update proposal set ");
		sb
				.append(" dispute_amount = if(payment_amount is null,0,payment_amount) + if(dispute_amount is null,0,dispute_amount) ");
		sb.append(" , payment_amount = '0', dispute_id = null ");
		if (!"".equals(sio.getOriginatorId())) {
			sb.append(" , originator_id = '" + sio.getOriginatorId() + "' ");
		} else {
			sb.append(" , originator_id = null ");
		}
		sb.append(" , modified_by = '" + userId + "' ");
		sb.append(" , notes = '" + CcmUtil.stringToSql(sio.getDescription())
				+ "' ");
		sb.append(" , dispute_reason_id = '" + sio.getDisputeReasonId() + "' ");
		sb.append(" where id in (" + sio.getBoxInId() + ") ");
		logger
				.info("Exit method updateGroupDisputeProposalByDisputeQueryString.");
		return sb.toString();
	}

	private String updateGroupDisputeProposalByPaymentQueryString(
			SearchInvoiceVO sio, Integer userId) {
		logger
				.info("Enter method updateGroupDisputeProposalByDisputeQueryString.");
		StringBuffer sb = new StringBuffer("update proposal set ");
		sb
				.append(" payment_amount = if(payment_amount is null,0,payment_amount) + if(dispute_amount is null,0,dispute_amount) ");
		sb.append(" , dispute_amount = '0', dispute_id = null ");
		if (!"".equals(sio.getOriginatorId())) {
			sb.append(" , originator_id = '" + sio.getOriginatorId() + "' ");
		} else {
			sb.append(" , originator_id = null ");
		}
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + userId + "' ");
		sb.append(" , notes = '" + CcmUtil.stringToSql(sio.getDescription())
				+ "' ");
		if (!"".equals(sio.getDisputeReasonId())) {
			sb.append(" , dispute_reason_id = '" + sio.getDisputeReasonId()
					+ "' ");
		} else {
			sb.append(" , dispute_reason_id = null ");
		}
		sb.append(" where id in (" + sio.getBoxInId() + ") ");
		logger
				.info("Exit method updateGroupDisputeProposalByDisputeQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDateilDao#getNumberOfItemType(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getNumberOfItemType(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfItemType.");
		final String sql = this.getNumberOfItemTypeQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfItemType.");
		return count;
	}

	private String getNumberOfItemTypeQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfItemTypeQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voItemTypeListWhereConditions(sio));
		logger.info("Exit method getNumberOfItemTypeQueryString.");
		return sb.toString();
	}

	public List<Object[]> searchBanExemptionDate(String invoiceId){
		logger.info("Enter method searchBanExemptionDate.");
		final String sql = " SELECT be.certificate_name,toJSON(be.term_start_date IS NULL,'', DATE_FORMAT(be.term_start_date,'%Y/%m/%d'))"
		                   +",toJSON(be.term_end_date IS NULL,'',DATE_FORMAT(be.term_end_date,'%Y/%m/%d'))"
		                   +" FROM ban_exemption be ,invoice i WHERE  be.ban_id = i.ban_id AND be.rec_active_flag = 'Y' AND i.rec_active_flag = 'Y' AND be.term_start_date < i.invoice_date AND be.term_end_date > i.invoice_date"
		                   +" AND i.id ="+invoiceId+" ORDER BY be.certificate_name ASC";
                
         
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchBanExemptionDate.");
		return l;
		
	}
	
	/**
	 * Get autopay error notes lists from database.
	 */
	public List<Object[]> searchAutopayErrorNotesDBList(String invoiceId){
		
		logger.info("Enter method searchAutopayErrorNotesDBList.");
		
		// SQL string.
		final String sql = "SELECT invoice_id, error_notes " +
							" FROM autopay_journal "+
							" WHERE rec_active_flag='Y' AND invoice_id=" + invoiceId;
		
                
         
		HibernateTemplate template = this.getHibernateTemplate();
		
		List<Object[]> autopayErrorNotesList = (List<Object[]>)template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
			
		});
		
		logger.info("Exit method searchAutopayErrorNotesDBList.");
		
		return autopayErrorNotesList;
		
	}

	public List<String> findSCOACodingTabLists(SearchInvoiceVO sio) {
		logger.info("Enter method findSCOACodingTabLists.");
		final String sql = this.findSCOACodingTabListsQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findSCOACodingTabLists.");
		return l;
	}

	private String findSCOACodingTabListsQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method findSCOACodingTabListsQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',id.id,");
		sb.append("',filed:\"',id.table_field,");
		sb.append("'\",title:\"',id.display_title,");
		sb.append("'\",format:\"',id.display_format,");
		sb.append("'\",width:\"',ifnull(id.display_width,100),");
		sb.append("'\"}') a ");
		sb.append("from item_display id ");
		sb.append("where id.rec_active_flag = 'Y' ");
		sb.append("and id.key_field_flag = 'Y' ");
		if (sio.getItemTypeId() != null) {
			sb.append(" and id.item_type_id = " + sio.getItemTypeId() + " ");
		} else {
			sb.append(" and id.item_type_id is null ");
		}
		if (sio.getInvoiceStructureCodeId() != null) {
			sb.append(" and id.invoice_structure_id = "
					+ sio.getInvoiceStructureCodeId() + " ");
		} else {
			sb.append(" and id.invoice_structure_id is null ");
		}
		sb.append(" order by id.display_order asc ");
		logger.info("Exit method findSCOACodingTabListsQueryString.");
		return sb.toString();
	}

	public List<String> getAccordionTabsBySCOA(SearchInvoiceVO sio) {
		logger.info("Enter method getAccordionTabsBySCOA.");
		final String sql = this.getAccordionTabsBySCOAQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getAccordionTabsBySCOA.");
		return l;
	}

	private String getAccordionTabsBySCOAQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method getAccordionTabsBySCOAQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{id:',p.item_type_id,");
		sb
				.append("',itemTypeName:\"',toJSON(t.item_type_name is null,'',t.item_type_name),");
		sb.append("'\",numberLine:\"',count(0),");
		sb
				.append("'\",totalAmount:\"',format((sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount)),2),");
		sb.append("'\"}') a ");
		sb
				.append(" from ((proposal p left join invoice_item ii on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' and ii.rec_active_flag = 'Y')) ");
		sb
				.append(" left join item_type t on (p.item_type_id = t.id and p.rec_active_flag = 'Y') ");
		sb
				.append(" left join account_code ac on (p.account_code_id = ac.id and ac.active_flag = 'Y' and ac.rec_active_flag = 'Y')) "); // Modify
		// By
		// xin.huang
		// 2011-09-29
		sb.append(" where ii.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and ii.proposal_flag = 1 ");
		sb.append(" and ac.id is null "); // Modify By xin.huang 2011-09-29
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb.append(" group by t.item_type_name asc ");
		logger.info("Exit method getAccordionTabsBySCOAQueryString.");
		return sb.toString();
	}

	public List<String> getAccordionTabsByBrowse(SearchInvoiceVO sio) {
		logger.info("Enter method getAccordionTabsByBrowse.");
		final String sql = this.getAccordionTabsByBrowseQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getAccordionTabsByBrowse.");
		return l;
	}

	private String getAccordionTabsByBrowseQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method getAccordionTabsByBrowseQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',the.itemId,");
		sb
				.append("',itemTypeName:\"',toJSON(the.itemTypeName is null,'',the.itemTypeName),");
		sb
				.append("'\",numberLine:\"',if(sum(the.coun) is null,0,sum(the.coun)),");
		sb.append("'\",totalAmount:\"',format(sum(the.amount),2),");
		sb.append("'\"}') a ");
		sb.append(" from ( ");
		sb.append(" select p.item_type_id as itemId , count(0) as coun ,");
		sb
				.append(" round((sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount)),2) as amount, ");
		sb
				.append(" if(item_type_summary is null,'',item_type_summary) as itemTypeName ");
//		sb
//		.append(" if(t.item_type_name is null,'',t.item_type_name) as itemTypeName ");
//		sb
//				.append(" from proposal p left join invoice_item ii on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' and ii.rec_active_flag = 'Y') ");
		sb
		.append(" from proposal p");
//		sb
//				.append(" left join item_type t on (p.item_type_id = t.id and p.rec_active_flag = 'Y') ");
		sb
				.append(" left join item_type t on (p.item_type_id = t.id) ");
//		sb.append(" left join account_code ac on (ac.id = p.account_code_id) ");
		sb.append(" where p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and  p.proposal_flag = 1 ");
		sb.append(" and  p.rec_active_flag = 'Y' ");
//		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio
//				.getInvoiceId());
//		if (invoiceStatusId < 21) {
//			sb.append(" and ac.id is not null ");
//			sb.append(" and ac.active_flag = 'Y'");
//			sb.append(" and ac.rec_active_flag = 'Y'");
//		}
//		sb
//				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
//		sb.append(" group by t.item_type_name, p.account_code_id asc ");
		sb.append(" group by F_get_root_item_type(t.id), p.account_code_id asc ");
		sb.append(" ) as the group by the.itemTypeName desc ");
		logger.info("Exit method getAccordionTabsByBrowseQueryString.");
		return sb.toString();
	}
	
	public List<Object> getInvoiceItemTypeIds(SearchInvoiceVO sio) {
		logger.info("Enter method getInvoiceItemTypeIds.");
		final String sql = this.getInvoiceItemTypeIdsQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object> l = (List<Object>) template
		.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getAccordionTabsByBrowse.");
		return l;
	}
	
	private String getInvoiceItemTypeIdsQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method getAccordionTabsByBrowseQueryString.");
		StringBuffer sb = new StringBuffer(" select distinct p.item_type_id , i.invoice_structure_id ,it.item_type_name from ((proposal p, invoice i) left join item_type it on p.item_type_id = it.id) ");
		sb.append(" where p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and  p.invoice_id = i.id ");
		sb.append(" and  p.proposal_flag = 1 ");
		sb.append(" and  p.rec_active_flag = 'Y' ");
		sb.append(" and  p.item_type_id is not null ");
		sb.append(" and  i.rec_active_flag = 'Y' ");
		sb .append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb.append(" group by F_get_root_item_type(p.item_type_id), p.account_code_id asc ");
		logger.info("Exit method getAccordionTabsByBrowseQueryString.");
		return sb.toString();
	}

	public List<String> getAccordionTabsByItemType(SearchInvoiceVO sio) {
		logger.info("Enter method getAccordionTabsByItemType.");
		final String sql = this.getAccordionTabsByItemTypeQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getAccordionTabsByItemType.");
		return l;
	}

	private String getAccordionTabsByItemTypeQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method getAccordionTabsByItemTypeQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',the.itemId,");
		sb
				.append("',itemTypeName:\"',toJSON(the.itemTypeName is null,'',the.itemTypeName),");
		sb
				.append("'\",numberLine:\"',if(sum(the.coun) is null,0,sum(the.coun)),");
		sb.append("'\",totalAmount:\"',format(sum(the.amount),2),");
		sb.append("'\"}') a ");
		sb.append(" from ( ");
		sb.append(" select p.item_type_id as itemId , count(0) as coun ,");
		sb
				.append(" round((sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount)),2) as amount, ");
		sb
				.append(" if(t.item_type_name is null,'',t.item_type_name) as itemTypeName ");
		sb
				.append(" from ((proposal p left join invoice_item ii on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' and ii.rec_active_flag = 'Y')) ");
		sb
				.append(" left join item_type t on (p.item_type_id = t.id and p.rec_active_flag = 'Y')) ");
		sb.append(" where ii.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and  p.proposal_flag = 1 ");
		sb.append(" and p.invoice_item_id in ( " + sio.getInvoiceItemId()
				+ " ) ");
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb.append(" group by t.item_type_name, p.account_code_id asc ");
		sb.append(" ) as the group by the.itemTypeName ");
		logger.info("Exit method getAccordionTabsByItemTypeQueryString.");
		return sb.toString();
	}

	public List<String> getAccordionTabsByDisputed(SearchInvoiceVO sio) {
		logger.info("Enter method getAccordionTabsByDisputed.");
		final String sql = this.getAccordionTabsByDisputedQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getAccordionTabsByDisputed.");
		return l;
	}

	private String getAccordionTabsByDisputedQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method getAccordionTabsByDisputedQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',the.itemId,");
		sb
				.append("',itemTypeName:\"',toJSON(the.itemTypeName is null,'',the.itemTypeName),");
		sb
				.append("'\",numberLine:\"',if(sum(the.coun) is null,0,sum(the.coun)),");
		sb.append("'\",totalAmount:\"',format(sum(the.amount),2),");
		sb.append("'\"}') a ");
		sb.append(" from ( ");
		sb.append(" select p.item_type_id as itemId , count(0) as coun ,");
		sb
				.append(" round((sum(p.payment_amount) + sum(p.dispute_amount) + sum(p.credit_amount)),2) as amount, ");
		sb
				.append(" if(t.item_type_name is null,'',t.item_type_name) as itemTypeName ");
		sb
				.append(" from ((proposal p left join invoice_item ii on (p.invoice_item_id is not null and ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' and ii.rec_active_flag = 'Y')) ");
		sb
				.append(" left join item_type t on (p.item_type_id = t.id and p.rec_active_flag = 'Y')) ");
		sb.append(" where ii.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and  p.proposal_flag = 1 ");
		sb.append(" and dispute_amount <> 0 and dispute_amount is not null ");
		sb
				.append(" and (ifnull(p.payment_amount,0) != 0  or ifnull(p.dispute_amount,0) != 0  or ifnull(p.credit_amount,0) != 0 ) ");
		sb.append(" group by t.item_type_name, p.account_code_id asc ");
		sb.append(" ) as the group by the.itemTypeName ");
		logger.info("Exit method getAccordionTabsByDisputedQueryString.");
		return sb.toString();
	}

	public List<String> searchItemTypeList(SearchInvoiceVO sio) {
		logger.info("Enter method searchItemTypeList.");
		final String sql = this.searchItemTypeListQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchItemTypeList.");
		return l;
	}

	private String searchItemTypeListQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method searchItemTypeListQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,");
		sb.append("',item:\"',toJSON(p.item_name is null,'',p.item_name),");
		sb
				.append("'\",itemTypeName:\"',toJSON(p.item_type_id is null,'',it.item_type_name),");
		sb
				.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb
				.append("'\",payment:\"',format(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",dispute:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",credit:\"',format(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",creditAmount:\"',ROUND(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",amount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2),");
		sb
				.append("'\",disputeAmount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount)),2),");
		sb
				.append("'\",cellularIndicator:\"',toJSON(p.cellular_indicator is null,'',p.cellular_indicator),");
		sb
				.append("'\",direction:\"',toJSON(p.direction is null,'',p.direction),");
		sb
				.append("'\",quantity:\"',format(if(p.quantity is null,0,p.quantity),2),");
		sb.append("'\",rate:\"',if(p.rate is null,'0.00',p.rate),");
		sb
				.append("'\",minutes:\"',format(if(p.minutes is null,0,p.minutes),2),");
		sb
				.append("'\",numberCalls:\"',format(if(p.number_of_calls is null,0,p.number_of_calls),2),");
		sb
				.append("'\",mileage:\"',format(if(p.mileage is null,0,p.mileage),2),");

		sb
				.append("'\",circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb
				.append("'\",chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),");
		sb
				.append("'\",billingNumber:\"',toJSON(p.billing_number is null,'',p.billing_number),");
		sb.append("'\",country:\"',toJSON(p.country is null,'',p.country),");
		sb
				.append("'\",usocDescription:\"',toJSON(p.usoc_description is null,'',p.usoc_description),");
		sb.append("'\",address:\"',toJSON(p.address is null,'',p.address),");
		sb.append("'\",usoc:\"',toJSON(p.usoc is null,'',p.usoc),");
		sb
				.append("'\",jurisdiction:\"',toJSON(p.jurisdiction is null,'',p.jurisdiction),");
		sb.append("'\",asg:\"',toJSON(p.asg is null,'',p.asg),");
		sb
				.append("'\",serviceType:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb
				.append("'\",disputeCategory:\"',toJSON(p.dispute_reason_id is null,'',dr.dispute_reason_name),");
		sb.append("'\",notes:\"',toJSON(p.notes is null,'',p.notes),");
		sb
				.append("'\",description:\"',toJSON(p.description is null,'',p.description),");
		sb
				.append("'\",proposalFlag:\"',toJSON(p.proposal_flag is null,'',p.proposal_flag),");

		sb
				.append("'\",SCOA:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb
				.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb
				.append("'\",SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb
				.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(voItemTypeListWhereConditions(sio));

		sb.append(" order by " + sio.getSortField() + " "
				+ sio.getSortingDirection() + ", p.id asc ");

		// sb.append(" " + sio.getOrderByCause(null) + " ");
		sb.append(" " + sio.getLimitCause() + " ");
		logger.info("Exit method searchItemTypeListQueryString.");
		return sb.toString();
	}

	private String voItemTypeListWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voItemTypeListWhereConditions.");
		StringBuffer sb = new StringBuffer(" from ");
		sb
				.append(" (((proposal p left join account_code ac on (p.account_code_id = ac.id)) ");
		sb
				.append(" left join dispute_reason dr on (p.dispute_reason_id = dr.id)) ");
		sb.append(" left join item_type it on (p.item_type_id = it.id)) ");
		sb.append(" where p.rec_active_flag = 'Y' ");
		sb.append(" and p.proposal_flag = '1' ");

		sb.append(" and (ifnull(p.payment_amount,0) != 0 ");
		sb.append(" or ifnull(p.dispute_amount,0) != 0 ");
		sb.append(" or ifnull(p.credit_amount,0) != 0 ) ");

		if (sio.getFilter() != null) {
			sb.append(" and ");
			sb.append(sio.getFilter());
		}

		if (sio.getInvoiceItemId() != null) {
			sb.append(" and p.invoice_item_id in ( " + sio.getInvoiceItemId()
					+ " ) ");
		} else {
			sb.append(" and p.invoice_item_id is null ");
		}
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		logger.info("Exit method voItemTypeListWhereConditions.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDateilDao#getNumberOfDisputeItems(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getNumberOfDisputeItems(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfDisputeItems.");
		final String sql = this.getNumberOfDisputeItemsQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfDisputeItems.");
		return count;
	}
	
	public long getTransactionHistoryCount(Integer invoiceId) {
		logger.info("Enter method getTransactionHistoryCount.");
		final String sql = "select count(1) from transaction_history where invoice_status_id in (17,18) and rec_active_flag = 'Y' and invoice_id = "+invoiceId;
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getTransactionHistoryCount.");
		return count;
	}
	
	public long getExternalEmailCount(String externalEmailId) {
		logger.info("Enter method getExternalEmailCount.");
		final String sql = "select count(1) from external_email where   confirm_flag = 'Y' and id = "+ externalEmailId;
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getExternalEmailCount.");
		return count;
	}
	
	public long doViewOriginalList(Integer invoiceId) {
		logger.info("Enter method doViewOriginalList.");
		final String sql = "select count(1) from original o where o.rec_active_flag = 'Y' and o.invoice_id = "+invoiceId;
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method doViewOriginalList.");
		return count;
	}

	private String getNumberOfDisputeItemsQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfDisputeItemsQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voDisputeItemsListWhereConditions(sio));
		logger.info("Exit method getNumberOfDisputeItemsQueryString.");
		return sb.toString();
	}

	public List<String> searchDisputeItemsList(SearchInvoiceVO sio) {
		logger.info("Enter method searchDisputeItemsList.");
		final String sql = this.searchDisputeItemsListQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchDisputeItemsList.");
		return l;
	}

	private String searchDisputeItemsListQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method searchDisputeItemsListQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,");
		sb.append("',item:\"',toJSON(p.item_name is null,'',p.item_name),");
		sb
				.append("'\",itemTypeName:\"',toJSON(p.item_type_id is null,'',it.item_type_name),");
		sb
				.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb
				.append("'\",payment:\"',format(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",dispute:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",credit:\"',format(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",creditAmount:\"',ROUND(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",amount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2),");
		sb
				.append("'\",disputeAmount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount)),2),");
		sb
				.append("'\",cellularIndicator:\"',if(p.cellular_indicator is null,'',p.cellular_indicator),");
		sb.append("'\",direction:\"',if(p.direction is null,'',p.direction),");
		sb
				.append("'\",quantity:\"',format(if(p.quantity is null,0,p.quantity),2),");
		sb.append("'\",rate:\"',if(p.rate is null,'0.00',p.rate),");
		sb
				.append("'\",minutes:\"',format(if(p.minutes is null,0,p.minutes),2),");
		sb
				.append("'\",numberCalls:\"',format(if(p.number_of_calls is null,0,p.number_of_calls),2),");
		sb
				.append("'\",mileage:\"',format(if(p.mileage is null,0,p.mileage),2),");

		sb
				.append("'\",circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb
				.append("'\",chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),");
		sb
				.append("'\",billingNumber:\"',toJSON(p.billing_number is null,'',p.billing_number),");
		sb.append("'\",country:\"',toJSON(p.country is null,'',p.country),");
		sb
				.append("'\",usocDescription:\"',toJSON(p.usoc_description is null,'',p.usoc_description),");
		sb.append("'\",address:\"',toJSON(p.address is null,'',p.address),");
		sb.append("'\",usoc:\"',toJSON(p.usoc is null,'',p.usoc),");
		sb
				.append("'\",jurisdiction:\"',toJSON(p.jurisdiction is null,'',p.jurisdiction),");
		sb.append("'\",asg:\"',toJSON(p.asg is null,'',p.asg),");
		sb
				.append("'\",serviceType:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb
				.append("'\",disputeCategory:\"',toJSON(p.dispute_reason_id is null,'',dr.dispute_reason_name),");
		sb.append("'\",notes:\"',toJSON(p.notes is null,'',p.notes),");
		sb
				.append("'\",description:\"',toJSON(p.description is null,'',p.description),");
		sb
				.append("'\",proposalFlag:\"',toJSON(p.proposal_flag is null,'',p.proposal_flag),");
		sb
				.append("'\",pon:\"',toJSON(ii.purchase_order_number IS NULL, '', ii.purchase_order_number),");

		sb
				.append("'\",SCOA:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb
				.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb
				.append("'\",SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb
				.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(voDisputeItemsListWhereConditions(sio));
		sb.append(" order by " + sio.getSortField() + " "
				+ sio.getSortingDirection() + ", p.id asc ");
		// sb.append(" " + sio.getOrderByCause(null) + " ");
		sb.append(" " + sio.getLimitCause() + " ");
		logger.info("Exit method searchDisputeItemsListQueryString.");
		return sb.toString();
	}

	private String voDisputeItemsListWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voDisputeItemsListWhereConditions.");
		StringBuffer sb = new StringBuffer(" from ((((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id )) ");
		sb
				.append(" left join account_code ac on (p.account_code_id = ac.id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio
				.getInvoiceId());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.rec_active_flag = 'Y' and ac.active_flag = 'Y'");
		}
		sb
				.append(" )) left join dispute_reason dr on (p.dispute_reason_id = dr.id)) ");
		sb.append(" left join item_type it on (p.item_type_id = it.id)) ");
		sb.append(" where p.rec_active_flag = 'Y' ");

		sb.append(" and (ifnull(p.payment_amount,0) != 0 ");
		sb.append(" or ifnull(p.dispute_amount,0) != 0 ");
		sb.append(" or ifnull(p.credit_amount,0) != 0 ) ");

		if (sio.getFilter() != null) {
			sb.append(" and ");
			sb.append(sio.getFilter());
		}

		sb.append(" and p.proposal_flag = 1 ");
		sb
				.append(" and p.dispute_amount <> 0 and p.dispute_amount is not null ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and p.invoice_item_id is not null ");
		logger.info("Exit method voDisputeItemsListWhereConditions.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDateilDao#getNumberOfEmptySCOAs(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getNumberOfEmptySCOAs(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfEmptySCOAs.");
		final String sql = this.getNumberOfEmptySCOAsQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfEmptySCOAs.");
		return count;
	}

	private String getNumberOfEmptySCOAsQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfEmptySCOAsQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voEmptySCOAListWhereConditions(sio));
		logger.info("Exit method getNumberOfEmptySCOAsQueryString.");
		return sb.toString();
	}

	public List<String> searchEmptySCOAList(SearchInvoiceVO sio) {
		logger.info("Enter method searchEmptySCOAList.");
		final String sql = this.searchEmptySCOAListQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchEmptySCOAList.");
		return l;
	}

	private String searchEmptySCOAListQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method searchEmptySCOAListQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',p.id,");
		sb.append("',item:\"',toJSON(p.item_name is null,'',p.item_name),");
		sb
				.append("'\",itemTypeName:\"',toJSON(p.item_type_id is null,'',it.item_type_name),");
		sb
				.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb
				.append("'\",payment:\"',format(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",dispute:\"',format(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",credit:\"',format(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",creditAmount:\"',ROUND(if(p.credit_amount is null,0,p.credit_amount),2),");
		sb
				.append("'\",amount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2),");
		sb
				.append("'\",disputeAmount:\"',ROUND((if(p.dispute_amount is null,0,p.dispute_amount)),2),");
		sb
				.append("'\",cellularIndicator:\"',toJSON(p.cellular_indicator is null,'',p.cellular_indicator),");
		sb
				.append("'\",direction:\"',toJSON(p.direction is null,'',p.direction),");
		sb
				.append("'\",quantity:\"',format(if(p.quantity is null,0,p.quantity),2),");
		sb.append("'\",rate:\"',if(p.rate is null,'0.00',p.rate),");
		sb
				.append("'\",minutes:\"',format(if(p.minutes is null,0,p.minutes),2),");
		sb
				.append("'\",numberCalls:\"',format(if(p.number_of_calls is null,0,p.number_of_calls),2),");
		sb
				.append("'\",mileage:\"',format(if(p.mileage is null,0,p.mileage),2),");
		sb
				.append("'\",pon:\"',if(ii.purchase_order_number IS NULL, '', ii.purchase_order_number),");

		sb
				.append("'\",circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb
				.append("'\",chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),");
		sb
				.append("'\",billingNumber:\"',toJSON(p.billing_number is null,'',p.billing_number),");
		sb.append("'\",country:\"',toJSON(p.country is null,'',p.country),");
		sb
				.append("'\",usocDescription:\"',toJSON(p.usoc_description is null,'',p.usoc_description),");
		sb.append("'\",address:\"',toJSON(p.address is null,'',p.address),");
		sb.append("'\",usoc:\"',toJSON(p.usoc is null,'',p.usoc),");
		sb
				.append("'\",jurisdiction:\"',toJSON(p.jurisdiction is null,'',p.jurisdiction),");
		sb.append("'\",asg:\"',toJSON(p.asg is null,'',p.asg),");
		sb
				.append("'\",serviceType:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb
				.append("'\",disputeCategory:\"',toJSON(p.dispute_reason_id is null,'',dr.dispute_reason_name),");
		sb.append("'\",notes:\"',toJSON(p.notes is null,'',p.notes),");
		sb
				.append("'\",description:\"',toJSON(p.description is null,'',p.description),");
		sb
				.append("'\",proposalFlag:\"',toJSON(p.proposal_flag is null,'',p.proposal_flag),");

		sb
				.append("'\",SCOA:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),"); // fixed
		// bug
		// by
		// xin.huang
		sb
				.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb
				.append("'\",SCOAId:\"',toJSON(p.account_code_id is null,'',p.account_code_id),");
		sb
				.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb
				.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		sb.append("'\"}') a ");
		sb.append(voEmptySCOAListWhereConditions(sio));
		sb.append(" order by " + sio.getSortField() + " "
				+ sio.getSortingDirection() + ", p.id asc ");
		// sb.append(" " + sio.getOrderByCause(null) + " ");
		sb.append(" " + sio.getLimitCause() + " ");
		logger.info("Exit method searchEmptySCOAListQueryString.");
		return sb.toString();
	}

	private String voEmptySCOAListWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voEmptySCOAListWhereConditions.");
		StringBuffer sb = new StringBuffer(" from ((((proposal p left join invoice_item ii ");
		sb
				.append(" on (p.invoice_item_id is not null and ii.id = p.invoice_item_id )) ");
		
		sb
				.append(" left join account_code ac on (p.account_code_id = ac.id  "); // Mod
		// By
		// wenbo.zhang
		// 2016-07-28
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio
				.getInvoiceId());
		if (invoiceStatusId < 21) {
					sb.append(" and ac.active_flag = 'Y'");
					sb.append(" and ac.rec_active_flag = 'Y'");
		}	
		sb.append(" ))");
		// By
		// xin.huang
		// 2011-09-29
		sb
				.append(" left join dispute_reason dr on (p.dispute_reason_id = dr.id)) ");
		sb.append(" left join item_type it on (p.item_type_id = it.id)) ");
		sb.append(" where p.rec_active_flag = 'Y' ");

		sb.append(" and (ifnull(p.payment_amount,0) != 0 ");
		sb.append(" or ifnull(p.dispute_amount,0) != 0 ");
		sb.append(" or ifnull(p.credit_amount,0) != 0 ) ");

		if (sio.getFilter() != null) {
			sb.append(" and ");
			sb.append(sio.getFilter());
		}

		sb.append(" and p.proposal_flag = 1 ");
		sb.append(" and ac.id is null"); // Mod By xin.huang 2011-09-29
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" and p.invoice_item_id is not null ");
		logger.info("Exit method voEmptySCOAListWhereConditions.");
		return sb.toString();
	}

	public List<Object[]> findItemLabelListByInvoiceId(SearchInvoiceVO sio) {
		logger.info("Enter method findItemLabelListByInvoiceId.");
		final String sql = this.findItemLabelListByInvoiceIdQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemLabelListByInvoiceId.");
		return l;
	}

	private String findItemLabelListByInvoiceIdQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method findItemLabelListByInvoiceIdQueryString.");
		// StringBuffer sb = new StringBuffer("select concat('{id:',il.id,");
		// sb.append("',label:\"',if(il.label_id is null,'',il.label_id),");
		// sb.append("'\",invoiceItemId:\"',if(il.invoice_item_id is
		// null,'',il.invoice_item_id),");
		// sb.append("'\",labelName:\"',l.label_name,");
		// sb.append("'\"}') a ");
		StringBuffer sb = new StringBuffer("select distinct ");
		sb.append(" if(il.label_id is null,'',il.label_id), ");
		sb.append(" if(il.invoice_item_id is null,'',il.invoice_item_id), ");
		sb.append(" l.label_name,count(*), ");
		sb
				.append(" if(sum(p.payment_amount) is null,0,sum(p.payment_amount)),");
		sb
				.append(" if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)),");
		sb.append(" if(sum(p.credit_amount) is null,0,sum(p.credit_amount)) ");
		sb
				.append(" from item_label il left join label l on (l.id = il.label_id) ,proposal p ");
		sb.append(" where il.rec_active_flag = 'Y' ");
		sb.append(" and il.invoice_id = " + sio.getInvoiceId() + " ");
		sb
				.append(" and p.invoice_item_id = il.invoice_item_id and p.invoice_id = il.invoice_id ");
		sb.append(" group by il.invoice_item_id,label_id ");
		sb.append(" order by il.label_id asc ");
		logger.info("Exit method findItemLabelListByInvoiceIdQueryString.");
		return sb.toString();
	}

	public List<String> searchProposalList(SearchInvoiceVO sio) {
		logger.info("Enter method searchProposalList.");
		final String sql = this.searchProposalListQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchProposalList.");
		return l;
	}

	/**
	 * 查询一个账单明细的各种属性，用于在前台账单明细信息列表的显示。
	 * @param  sio 描述 invoice 账单的实体类。
	 * @return     [description]
	 */
	private String searchProposalListQueryString(SearchInvoiceVO sio) {
		logger.info("Exit method searchProposalListQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',toJSON(p.id is null,'',p.id),");
		sb.append("',item:\"',toJSON(p.item_name is null,'',p.item_name),");
		// sb.append("'\",count:\"',prop.coun,");
		sb.append("'\",audit_status_id:\"',toJSON(p.audit_status_id is null,'',p.audit_status_id),");
		sb.append("'\",audit_status_name:\"',toJSON(aus.audit_status_name is null,'',aus.audit_status_name),");
		sb.append("'\",audit_exists_status_id:\"',toJSON(audit_exists_status_id is null,'',audit_exists_status_id),");
		sb.append("'\",audit_exists_status_name:\"',toJSON(aues.audit_status_name is null,'',aues.audit_status_name),");
		sb.append("'\",product_name:\"',toJSON(pt.product_name is null,'',pt.product_name),"); // Product
		sb.append("'\",component_name:\"',toJSON(pc.component_name is null,'',pc.component_name),"); // Component
		sb.append("'\",itemTypeId:\"',toJSON(p.item_type_id is null,'',p.item_type_id),");
		sb.append("'\",payment:\"',toJSON(false,'',format(if(p.payment_amount is null,0,p.payment_amount),2)),");
		sb.append("'\",dispute:\"',toJSON(false,'',format(if(p.dispute_amount is null,0,p.dispute_amount),2)),");
		sb.append("'\",credit:\"',toJSON(false,'',format(if(p.credit_amount is null,0,p.credit_amount),2)),");
		sb.append("'\",mileage:\"',toJSON(false,'',format(if(p.mileage is null,0,p.mileage),2)),");
		sb.append("'\",paymentAmount:\"',toJSON(false,'',ROUND(if(p.payment_amount is null,0,p.payment_amount),2)),");
		sb.append("'\",disputeAmount:\"',toJSON(false,'',ROUND(if(p.dispute_amount is null,0,p.dispute_amount),2)),");
		sb.append("'\",creditAmount:\"',toJSON(false,'',ROUND(if(p.credit_amount is null,0,p.credit_amount),2)),");
		sb.append("'\",mileageAmount:\"',toJSON(false,'',ROUND(if(p.mileage is null,0,p.mileage),2)),");
		sb.append("'\",amount:\"',toJSON(false,'',ROUND((if(p.dispute_amount is null,0,p.dispute_amount) + if(p.payment_amount is null,0,p.payment_amount)),2)),");

		sb.append("'\",quantity:\"',toJSON(false,'',format(if(p.quantity is null,0,p.quantity),2)),");
		sb.append("'\",driect_quantity:\"',toJSON(false,'',format(if(p.driect_quantity is null,0,p.driect_quantity),2)),");
		sb.append("'\",quantityAmount:\"',toJSON(false,'',ROUND(if(p.quantity is null,0,p.quantity),2)),");
		sb.append("'\",rate:\"',toJSON(false,'',if(p.rate is null,'0.00',p.rate)),");
		sb.append("'\",rateAmount:\"',toJSON(false,'',if(p.rate is null,'0.00',p.rate)),");
		sb.append("'\",minutes:\"',toJSON(false,'',format(if(p.minutes is null,0,p.minutes),2)) ,");
		sb.append("'\",minutesAmount:\"',toJSON(false,'',ROUND(if(p.minutes is null,0,p.minutes),2)),");
		sb.append("'\",numberCalls:\"',toJSON(false,'',format(if(p.number_of_calls is null,0,p.number_of_calls),2)),");
		sb.append("'\",numberCallsAmount:\"',toJSON(false,'',ROUND(if(p.number_of_calls is null,0,p.number_of_calls),2)),");

		sb.append("'\",billingNumber:\"',toJSON(p.billing_number is null,'',p.billing_number),");
		sb.append("'\",address:\"',toJSON(p.address is null,'',p.address),");
		sb.append("'\",usoc:\"',toJSON(p.usoc is null,'',p.usoc),");
		sb.append("'\",usocDescription:\"',toJSON(p.usoc_description is null,'',p.usoc_description),");
		sb.append("'\",cellularIndicator:\"',toJSON(p.cellular_indicator is null,'',p.cellular_indicator),");
		sb.append("'\",country:\"',toJSON(p.country is null,'',p.country),");

		sb.append("'\",asg:\"',toJSON(p.asg is null,'',p.asg),");
		sb.append("'\",jurisdiction:\"',toJSON(p.jurisdiction is null,'',p.jurisdiction),");
		sb.append("'\",direction:\"',toJSON(p.direction is null,'',p.direction),");
		sb.append("'\",province:\"',toJSON(pp.province_acronym is null,'',pp.province_acronym),");
		sb.append("'\",province_source:\"',toJSON(ps.province_source_name is null,'',ps.province_source_name),"); // Province source
		
		sb.append("'\",terminating_province:\"',toJSON( ii.terminating_province is null,'', ii.terminating_province),");
		sb.append("'\",circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),");
		sb.append("'\",stripped_circuit_number:\"',toJSON( ii.stripped_circuit_number is null,'', ii.stripped_circuit_number),");
		sb.append("'\",serviceType:\"',toJSON(p.service_type is null,'',p.service_type),");
		sb.append("'\",chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),");
		sb.append("'\",proposalFlag:\"',toJSON(p.proposal_flag is null,'',p.proposal_flag),");
		sb.append("'\",notes:\"',toJSON(p.notes is null,'',p.notes) ,");
		sb.append("'\",description:\"',toJSON(p.description is null,'',p.description),");
		sb.append("'\",SCOA:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\",originatorId:\"',toJSON(p.originator_id is null,'',p.originator_id),");
		sb.append("'\",disputeCategory:\"',toJSON(p.dispute_reason_id is null,'',dr.dispute_reason_name),");
		sb.append("'\",SCOAId:\"',toJSON(p.account_code_id  is null,'',p.account_code_id ),");
		sb.append("'\",disputeCategoryId:\"',toJSON(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb.append("'\",pon:\"',toJSON(ii.purchase_order_number IS NULL, '', ii.purchase_order_number),");
		sb.append("'\",fromDate:\"',toJSON(ii.start_date IS NULL,'',ii.start_date),"); // From Date
		sb.append("'\",toDate:\"',toJSON(ii.end_date IS NULL,'',ii.end_date),"); // To Date
		sb.append("'\",attachmentPoint:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),");
		sb.append("'\",disputeId:\"',toJSON(d.id is null,'',d.id),");
		sb.append("'\",disputeNumber:\"',toJSON(d.dispute_number is null,'',d.dispute_number),");
		sb.append("'\",disputeNotes:\"',toJSON(d.notes is null,'',d.notes),");
		sb.append("'\",circuitId:\"',toJSON(ii.vendor_circuit_id is null,'',ii.vendor_circuit_id),");
		// modfiy by xin.huang on 2012-10-24 start
		sb.append("'\",tariffCount:\"',toJSON(false,'',(select count(1)  from tariff_link where circuit_id = ii.vendor_circuit_id and rec_active_flag = 'Y')),");
		// modfiy by xin.huang on 2012-10-24 start
		sb.append("'\"}') a ");

		// 查询字段的表和限制条件（包含对限制条件的一些处理。 from .. where）
		sb.append(voProposalListWhereConditions(sio));

		// 数据排序条件。
		sb.append(" order by " + sio.getSortField() + " "
				+ sio.getSortingDirection());
		// sb.append(" " + sio.getOrderByCause(null) + " ");
		
		// 对数据的条数进行限制。
		sb.append(" " + sio.getLimitCause() + " ");
		logger.info("Exit method searchProposalListQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDateilDao#getNumberOfProposals(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getNumberOfProposals(SearchInvoiceVO sio) {
		logger.info("Enter method getNumberOfProposals.");
		final String sql = this.getNumberOfProposalsQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfProposals.");
		return count;
	}

	private String getNumberOfProposalsQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getInvoiceSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voProposalListWhereConditions(sio));
		logger.info("Exit method getInvoiceSearchNumberQueryString.");
		return sb.toString();
	}

	
	/**
	 * 查询账单明细 （proposal list）所关联到的表和限制条件。 （SQL： from .. where）
	 * @param  sio 描述 invoice 账单的实体类。
	 * @return     [description]
	 * @see #searchProposalListQueryString
	 */
	private String voProposalListWhereConditions(SearchInvoiceVO sio) {
		logger.info("Exit method voProposalListWhereConditions.");

		// 数据的来源表和一些相关的连接表。
		StringBuffer sb = new StringBuffer(" from ");

		sb.append(" proposal p left join account_code ac on p.account_code_id = ac.id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(sio.getInvoiceId());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.active_flag = 'Y'");
			sb.append(" and ac.rec_active_flag = 'Y'");
		}
		// By
		// xin.huang
		// 2011-10-07
		sb.append(" left join audit_status aus on p.audit_status_id = aus.id ");
		sb.append(" left join audit_status aues on p.audit_exists_status_id = aues.id ");
		sb.append(" left join dispute_reason dr on p.dispute_reason_id = dr.id ");
		sb.append(" left join item_type it on p.item_type_id = it.id" +
				" left join invoice_item ii on p.invoice_item_id = ii.id and ii.rec_active_flag = 'Y'");
		sb.append(" left join  province pp on pp.id = p.province_id ");
		sb.append(" left join  province_source ps on ps.id = p.province_source_id ");
		sb.append(" left join  product pt on p.product_id = pt.id");
		sb.append(" left join  product_component as pc on p.product_component_id = pc.id ");
		sb.append(" left join  dispute  as d on p.dispute_id = d.id ");

		// Where 条件。
		sb.append(" where p.rec_active_flag = 'Y' ");

//		sb.append(" and (ifnull(p.payment_amount,0) != 0 ");
//		sb.append(" or ifnull(p.dispute_amount,0) != 0 ");
//		sb.append(" or ifnull(p.credit_amount,0) != 0 ) ");

		if (sio.getFilter() != null) {
			sb.append(" and ");
			
			if("(1=1 and p.audit_status_id like '%4%')".equals(sio.getFilter()) ) {
				sb.append("(1=1 and p.audit_status_id like '%4%' or p.audit_status_id is null)");
			}else {
				sb.append(sio.getFilter());
			}
		}

		sb.append(" and p.proposal_flag = 1 ");
		sb.append(" and p.invoice_item_id is not null ");
		sb.append(" and p.invoice_id = " + sio.getInvoiceId() + " ");

		logger.info("Exit method voProposalListWhereConditions.");
		return sb.toString();
	}

	public List<String> findInvoiceItemTypeListNameDefult(String itemTypeId,
			String invoiceStructureCodeId) {
		logger.info("Enter method findInvoiceItemTypeListNameDefult.");
		final String queryString = findInvoiceItemTypeListNameDefultQueryString(
				itemTypeId, invoiceStructureCodeId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method findInvoiceItemTypeListNameDefult.");
		return list;
	}

	private String findInvoiceItemTypeListNameDefultQueryString(
			String itemTypeId, String invoiceStructureCodeId) {
		logger
				.info("Enter method findInvoiceItemTypeListNameDefultQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',id.id,");
		sb.append("',filed:\"',id.table_field,");
		sb.append("'\",title:\"',id.display_title,");
		sb.append("'\",format:\"',id.display_format,");
		sb.append("'\",width:\"',id.display_width,");
		sb.append("'\"}') a ");
		sb.append("from item_display id ");
		sb.append("where id.rec_active_flag = 'Y' ");
		sb.append(" and id.item_type_id = " + itemTypeId + " ");
		sb.append(" and id.invoice_structure_id = " + invoiceStructureCodeId
				+ " ");
		sb.append(" order by id.display_order asc ");
		logger
				.info("Enter method findInvoiceItemTypeListNameDefultQueryString.");
		return sb.toString();
	}

	public List<String> findInvoiceItemTypeListName(SearchInvoiceVO sio) {
		logger.info("Enter method findInvoiceItemTypeListName.");
		final String queryString = findInvoiceItemTypeListNameQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method findInvoiceItemTypeListName.");
		return list;
	}

	private String findInvoiceItemTypeListNameQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method findInvoiceItemTypeListNameQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',id.id,");
		sb.append("',filed:\"',id.table_field,");
		sb.append("'\",title:\"',id.display_title,");
		sb.append("'\",format:\"',id.display_format,");
		sb.append("'\",width:\"',id.display_width,");
		sb.append("'\"}') a ");
		sb.append("from item_display id ");
		sb.append("where id.rec_active_flag = 'Y' ");
		if (sio.getItemTypeId() != null) {
			sb.append(" and id.item_type_id = " + sio.getItemTypeId() + " ");
		} else {
			sb.append(" and id.item_type_id is null ");
		}
		if (sio.getInvoiceStructureCodeId() != null) {
			sb.append(" and id.invoice_structure_id = "
					+ sio.getInvoiceStructureCodeId() + " ");
		} else {
			sb.append(" and id.invoice_structure_id is null ");
		}
		sb.append(" order by id.display_order asc ");
		logger.info("Enter method findInvoiceItemTypeListNameQueryString.");
		return sb.toString();
	}

	public List<Object[]> findNotProposedAmount(String invoiceId,
			String invoiceItemInId) {
		logger.info("Enter method invoiceItemInId.");
		final String queryString = invoiceItemInIdQueryString(invoiceId,
				invoiceItemInId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method invoiceItemInId.");
		return list;
	}

	private String invoiceItemInIdQueryString(String invoiceId,
			String invoiceItemInId) {
		logger.info("Enter method invoiceItemInIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select p.invoice_id,p.invoice_item_id,");
		sb
				.append(" (if(ii.item_amount is null,0,ii.item_amount) - sum(p.payment_amount) - sum(p.dispute_amount)) as amount ");
		sb.append(" from proposal p join invoice_item ii ");
		sb
				.append(" where ii.rec_active_flag = 'Y' and p.invoice_item_id = ii.id ");
		if (invoiceId != null) {
			sb.append(" and ii.invoice_id = " + invoiceId + " ");
		}
		sb.append(" and p.invoice_item_id in (" + invoiceItemInId + ") ");
		sb.append(" group by p.invoice_item_id  ");

		logger.info("Enter method invoiceItemInIdQueryString.");
		return sb.toString();
	}

	public void updateProposalsAmount(SearchInvoiceVO sio,
			String invoiceItemIdStrings, String type) {
		logger.info("Enter method updateProposalsAmount.");
		final String queryString = uupdateProposalsAmountQueryString(sio,
				invoiceItemIdStrings, type);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateProposalsAmount.");
	}

	private String uupdateProposalsAmountQueryString(SearchInvoiceVO sio,
			String invoiceItemIdStrings, String type) {
		logger.info("Enter method uupdateProposalsAmountQueryString.");
		StringBuffer sb = new StringBuffer("update proposal p set ");
		sb.append("p.account_code_id = " + sio.getNowAccountCodeId() + " ");
		sb.append(" , p.scoa_source_id = 720301 ");
		sb.append(",p.dispute_reason_id = " + sio.getNowDisputeCategoryId()
				+ " ");
		if (sio.getNote() != null) {
			sb.append(",p.description = '" + CcmUtil.stringToSql(sio.getNote())
					+ "' ");
		}
		sb.append(",p.modified_timestamp = '"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()) + "' ");
		sb.append(",p.modified_by = " + SystemUtil.getCurrentUserId() + " ");

		if (type.equals("ProposedAmountUpdatePayment")) {
			sb.append(",p.payment_amount = p.payment_amount + "
					+ sio.getAmount() + " ");
		}
		if (type.equals("ProposedAmountUpdateDispute")) {
			sb.append(",p.dispute_amount = p.dispute_amount + "
					+ sio.getAmount() + " ");
		}

		if (type.equals("ACAndDCEqualAndPaymentZero")) {
			sb.append(",p.payment_amount = 0 ");
		}
		if (type.equals("ACAndDCEqualAndDisputeZero")) {
			sb.append(",p.dispute_amount = 0 ");
		}
		if (type.equals("ACAndDCEqualAndTransformationPayment")) {
			sb
					.append(",p.payment_amount = p.payment_amount + p.dispute_amount ");
		}
		if (type.equals("ACAndDCEqualAndTransformationDispute")) {
			sb
					.append(",p.dispute_amount = p.payment_amount + p.dispute_amount ");
		}
		sb.append("where p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append("and p.invoice_item_id in (" + invoiceItemIdStrings + ") ");
		if (sio.getPastAccountCodeId() != null) {
			sb.append("and p.account_code_id = " + sio.getPastAccountCodeId()
					+ " ");
		} else {
			sb.append("and p.account_code_id is null ");
		}
		if (sio.getPastDisputeCategoryId() != null) {
			sb.append("and p.dispute_reason_id = "
					+ sio.getPastDisputeCategoryId() + " ");
		} else {
			sb.append("and p.dispute_reason_id is null ");
		}
		sb.append("and p.rec_active_flag = 'Y' ");
		logger.info("Exit method uupdateProposalsAmountQueryString.");
		return sb.toString();
	}

	public void updateProposalsAmountZero(SearchInvoiceVO sio,
			String invoiceItemIdStrings, String proposalName) {
		logger.info("Enter method updateProposalsAmountZero.");
		final String queryString = updateProposalsAmountZeroQueryString(sio,
				invoiceItemIdStrings, proposalName);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateProposalsAmountZero.");
	}

	private String updateProposalsAmountZeroQueryString(SearchInvoiceVO sio,
			String invoiceItemIdStrings, String proposalName) {
		logger.info("Enter method updateProposalsAmountZeroQueryString.");
		StringBuffer sb = new StringBuffer("update proposal p set ");
		if (proposalName.equals("Payment")) {
			sb.append("p.payment_amount = 0 ");
		}
		if (proposalName.equals("Dispute")) {
			sb.append("p.dispute_amount = 0 ");
		}
		sb.append("where p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append("and p.invoice_item_id in (" + invoiceItemIdStrings + ") ");
		sb.append("and p.account_code_id = "
				+ CcmUtil.stringToSql(sio.getNowAccountCodeId()) + " ");
		sb.append("and p.dispute_reason_id = " + sio.getNowDisputeCategoryId()
				+ " ");
		sb.append("and p.rec_active_flag = 'Y' ");
		logger.info("Exit method updateProposalsAmountZeroQueryString.");
		return sb.toString();
	}

	public void updateProposals(SearchInvoiceVO sio, String invoiceItemIdStrings) {
		logger.info("Enter method updateProposals.");
		final String queryString = updateProposalsQueryString(sio,
				invoiceItemIdStrings);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method updateProposals.");
	}

	private String updateProposalsQueryString(SearchInvoiceVO sio,
			String invoiceItemIdStrings) {
		logger.info("Enter method updateProposalsQueryString.");
		StringBuffer sb = new StringBuffer("update proposal p set ");
		sb.append("p.account_code_id = " + sio.getNowAccountCodeId() + " ");
		sb.append(" , p.scoa_source_id = 720401 ");
		sb.append(",p.dispute_reason_id = " + sio.getNowDisputeCategoryId()
				+ " ");
		sb.append(",p.modified_timestamp = '"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date()) + "' ");
		sb.append(",p.modified_by = " + SystemUtil.getCurrentUserId() + " ");
		sb.append("where p.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append("and p.invoice_item_id in (" + invoiceItemIdStrings + ") ");
		if (sio.getPastAccountCodeId() != null) {
			sb.append("and p.account_code_id = " + sio.getPastAccountCodeId()
					+ " ");
		} else {
			sb.append("and p.account_code_id is null ");
		}
		if (sio.getPastDisputeCategoryId() != null) {
			sb.append("and p.dispute_reason_id = "
					+ sio.getPastDisputeCategoryId() + " ");
		} else {
			sb.append("and p.dispute_reason_id is null ");
		}
		sb.append("and p.rec_active_flag = 'Y' ");
		logger.info("Exit method updateProposalsQueryString.");
		return sb.toString();
	}

	public List<String> findInvoiceItemByInvoiceItemId(String invoiceItemId,
			String invoiceItemName, Integer invoiceId) {
		logger.info("Enter method findInvoiceItemByInvoiceItemId.");
		final String queryString = findInvoiceItemByInvoiceItemIdQueryString(
				invoiceItemId, invoiceItemName, invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method findInvoiceItemByInvoiceItemId.");
		return list;
	}

	public Object[] findInvoiceItemByInvoiceItemIdObject(String invoiceItemId,
			String invoiceItemName, Integer invoiceId) {
		logger.info("Enter method findInvoiceItemByInvoiceItemIdObject.");
		final String queryString = findInvoiceItemByInvoiceItemIdObjectQueryString(
				invoiceItemId, invoiceItemName, invoiceId);
		Session session = getSession();
		try {
			Object[] obj = (Object[]) session.createSQLQuery(queryString)
			.uniqueResult();
			logger.info("Enter method findInvoiceItemByInvoiceItemIdObject.");
			return obj;
		} finally {
			releaseSession(session);
		}
	}

	private String voWhereConditionsByItemName(String invoiceItemInId,
			Integer invoiceId) {
		logger.info("Enter method voWhereConditionsByItemName.");
		StringBuffer sb = new StringBuffer();
		sb.append(" p.invoice_item_id = item.iid and p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_id = " + invoiceId + " ");
		sb.append("and p.invoice_item_id in (" + invoiceItemInId + ") ");
		logger.info("Enter method voWhereConditionsByItemName.");
		return sb.toString();
	}

	private String findInvoiceItemByInvoiceItemIdObjectQueryString(
			String invoiceItemId, String invoiceItemName, Integer invoiceId) {
		logger
				.info("Enter method findInvoiceItemByInvoiceItemIdObjectQueryString.");
		StringBuffer sb = new StringBuffer(
				"select '"
						+ invoiceItemName
						+ "',format(sum(if(i.item_amount is null,0,i.item_amount)),2),format((sum(if(i.payment_sum is null,0,i.payment_sum)) + if(prop.payment is null,0,prop.payment)),2),format((sum(if(i.dispute_sum is null,0,i.dispute_sum)) + if(prop.dispute is null,0,prop.dispute)),2) ");
		sb.append(" from invoice_item i, ");
		sb
				.append(" (select sum(if(p.payment_amount is null,0,p.payment_amount))as payment,");
		sb
				.append(" sum(if(p.dispute_amount is null,0,p.dispute_amount))as dispute ");
		sb.append(" from proposal p where p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_id = " + invoiceId + " ");
		sb.append(" and p.invoice_item_id = " + invoiceItemId + " ) as prop ");
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_id = " + invoiceId + " ");
		sb.append(" and i.id  = " + invoiceItemId + " ");
		logger
				.info("Exit method findInvoiceItemByInvoiceItemIdObjectQueryString.");
		return sb.toString();
	}

	private String findInvoiceItemByInvoiceItemIdQueryString(
			String invoiceItemId, String invoiceItemName, Integer invoiceId) {
		logger.info("Enter method findInvoiceItemByInvoiceItemIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{amount:\"',format(sum(if(i.item_amount is null,0,i.item_amount)),2),");
		sb
				.append("'\",payment:\"',format((sum(if(i.payment_sum is null,0,i.payment_sum)) + if(prop.payment is null,0,prop.payment)),2),");
		sb
				.append("'\",dispute:\"',format((sum(if(i.dispute_sum is null,0,i.dispute_sum)) + if(prop.dispute is null,0,prop.dispute)),2),");
		sb.append("'\",itemName:\"" + invoiceItemName + "");
		sb.append("\"}') a ");
		sb.append(" from invoice_item i, ");
		sb
				.append(" (select sum(if(p.payment_amount is null,0,p.payment_amount))as payment,");
		sb
				.append(" sum(if(p.dispute_amount is null,0,p.dispute_amount))as dispute ");
		sb.append(" from proposal p where p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_id = " + invoiceId + " ");
		sb.append(" and p.invoice_item_id = " + invoiceItemId + " ) as prop ");
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_id = " + invoiceId + " ");
		sb.append(" and i.id  = " + invoiceItemId + " ");
		logger.info("Exit method findInvoiceItemByInvoiceItemIdQueryString.");
		return sb.toString();
	}

	public List<Object[]> findInvoiceItemTotalToInvoiceItem(Integer invoiceId) {
		logger.info("Enter method findInvoiceItemTotalToInvoiceItem.");
		final String queryString = findInvoiceItemTotalToInvoiceItemQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method findInvoiceItemTotalToInvoiceItem.");
		return list;
	}

	private String findInvoiceItemTotalToInvoiceItemQueryString(
			Integer invoiceId) {
		logger
				.info("Enter method findInvoiceItemTotalToInvoiceItemQueryString.");
		StringBuffer sb = new StringBuffer("select i.id ");
		sb.append(" from invoice_item i ");
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_id = " + invoiceId + " ");
		sb.append(" and i.parent_item_id is null ");
		sb.append(" and i.proposal_flag = 1 ");
		logger
				.info("Exit method findInvoiceItemTotalToInvoiceItemQueryString.");
		return sb.toString();
	}

	public List<String> findInvoiceItemByInvoiceId(Integer invoiceId,
			String inId) {
		logger.info("Enter method findInvoiceItemByInvoiceId.");
		final String queryString = findInvoiceItemByInvoiceIdQueryString(
				invoiceId, inId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method findInvoiceItemByInvoiceId.");
		return list;
	}
		
	/**
	 * 使用proposalId 来查询 validation_result 相关信息。
	 * @author haichun.zhang.
	 * @param {String} proposalId.
	 * @param {String} invoiceId.
	 * @param {String} auditSourceType.
	 * @see InvoiceDetailServiceImpl#searchValidationResult
	 */
	public List<Object[]> searchValidationResult(String proposalId, String invoiceId, String auditSourceType, String circuitNumber) {
		logger.info("Enter method searchValidationResult.");
		
		String TAX_SOURCE_TYPE = "Tax";
		String PAYMENT_SOURCE_TYPE = "Payment";
		String LPC_SOURCE_TYPE = "LPC";
		String USAGE_SOURCE_TYPE = "Usage";
		String CURRENT_DUE_TYPE = "CurrentDue";
		String EXISTS = "Exists";
		String OCC_SOURCE_TYPE = "OCC";
		
		StringBuffer sb = new StringBuffer("SELECT audit_status.audit_status_name, "); // Status.
		sb.append(" product.product_name, "); // Product.
		
		// 当数据库中的actual_amount 和 expect_amount为 null的时候，页面上的显示应该什么都不显示
		// 而不是 将 amount 值 format 为 0.00. （actual_amount 和 expect_amount 的类型为 double 类型）。
		sb.append(" IF(ar.actual_amount IS NULL , null , IF(ar.audit_source_id = '8010',format(ar.actual_amount,2),format(ar.actual_amount,2))) AS actual_amount, "); // Actual amount.
		
		sb.append(" IF(ar.expect_amount IS NULL , null , IF(ar.audit_source_id = '8010',format(ar.expect_amount,2),format(ar.expect_amount,2))) AS expect_amount, "); // Expect amount.
		sb.append(" art.audit_reference_type_name, "); // Validation reference.
		sb.append(" audit_source.audit_source_name, "); // Validation method.
		sb.append(" audit_source.audit_source_description, "); // Validation method description.

        sb.append(" IF(ar.data IS NULL , '' , ar.data) AS notes_data, "); // audit result notes data.
	    sb.append(" IF(ar.notes IS NULL , '' , ar.notes) AS notes_notes, "); // Notes.
	
		
		sb.append(" ar.audit_reference_id, "); // audit_reference_id.
		sb.append(" ar.audit_status_id, "); // audit_status_id.

		// audit_reference_type_id.
		// 主要用来判断是否显示 reference_type_name.
		sb.append(" ar.audit_reference_type_id, "); 
		sb.append(" pc.component_name, "); // Component name.
//		sb.append(" FORMAT(IFNULL(p.quantity,p.minutes),0) AS quantity, "); // Quantity
		sb.append(" FORMAT(IFNULL(if(p.driect_quantity>0,p.driect_quantity,p.quantity), p.minutes), 0) AS quantity, "); // Quantity
		sb.append(" ar.audit_source_id, ");
		sb.append(" ar.id AS audit_result_id");

		sb.append(" FROM audit_result ar ");
		sb.append(" LEFT JOIN audit_status ON ar.audit_status_id = audit_status.id "); // 关联 audit_status 表
		sb.append(" LEFT JOIN audit_reference_type art ON ar.audit_reference_type_id = art.id "); // 关联 audit_reference_type 表
		sb.append(" LEFT JOIN audit_source ON ar.audit_source_id = audit_source.id "); // 关联 audit_source 表
		sb.append(" LEFT JOIN proposal AS p ON ar.proposal_id = p.id "); // 关联 proposal 表
		sb.append(" LEFT JOIN product_component AS pc ON p.product_component_id = pc.id "); // 关联 product_component 表
		sb.append(" LEFT JOIN product ON p.product_id = product.id "); // 关联 product 表


		sb.append(" WHERE ");

		if ( auditSourceType != null && auditSourceType.equals( TAX_SOURCE_TYPE ) )  { // Tax

			sb.append(" ar.invoice_id = " + invoiceId + "  ");
        	sb.append(" AND ar.audit_source_id LIKE '60%' ");
		} else if ( auditSourceType != null && auditSourceType.equals( OCC_SOURCE_TYPE ) ) { // Payment

			sb.append(" ar.invoice_id = " + invoiceId + "  ");
        	sb.append(" AND ar.audit_source_id LIKE '12%' ");
		} else if ( auditSourceType != null && auditSourceType.equals( PAYMENT_SOURCE_TYPE ) ) { // Payment

			sb.append(" ar.invoice_id = " + invoiceId + "  ");
        	sb.append(" AND ar.audit_source_id LIKE '70%' ");
		} else if ( auditSourceType != null && auditSourceType.equals( LPC_SOURCE_TYPE ) ) { // LPC

			sb.append(" ar.invoice_id = " + invoiceId + "  ");
        	sb.append(" AND ar.audit_source_id LIKE '90%' ");
		}else if ( auditSourceType != null &&  auditSourceType.equals(USAGE_SOURCE_TYPE)) { // USAGE
			sb.append(" ar.invoice_id = " + invoiceId + "  ");
        	sb.append(" AND ar.audit_source_id = '8003'");
		}else if ( auditSourceType != null &&  auditSourceType.equals(CURRENT_DUE_TYPE)) { // CVPP
			sb.append(" ar.invoice_id = " + invoiceId + "  ");
        	sb.append(" AND ar.audit_source_id IN (9202, 8003, 8004, 8005, 8006, 8007, 8008, 8009,4002)");
		}else if ( auditSourceType != null &&  auditSourceType.equals(EXISTS)) { // EXISTS
			
			if (!circuitNumber.equals("undefined")) {
				
				sb.append(" ar.proposal_id IN ( ");
				sb.append( this.searchProposalIds(invoiceId, circuitNumber) );
				sb.append(" ) ");
				
			} else {
				sb.append(" ar.proposal_id = " + proposalId + "  ");
			}
			
	        sb.append(" AND ar.rec_active_flag = 'Y' ");
	    	sb.append(" AND ar.audit_source_id IN (4001,4)");
	    		
		}
		else { // Common source type.
			
			if (!circuitNumber.equals("undefined")) {
				
				sb.append(" ar.proposal_id IN ( ");
				
				sb.append( this.searchProposalIds(invoiceId, circuitNumber) );
				
				sb.append(" ) ");
				
			} else {
				sb.append(" ar.proposal_id = " + proposalId + "  ");
			}

			
	        sb.append(" AND ar.rec_active_flag = 'Y' ");
	        sb.append(" AND ar.audit_source_id NOT IN (4001,4)");
	        
		 }
		
		if (!circuitNumber.equals("undefined")) {
    		sb.append(" GROUP BY actual_amount");
    	}

        final String queryString = sb.toString();
        HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				return session.createSQLQuery(queryString).list();
			}
		});
       

		logger.info("Enter method searchValidationResult.");
		return list;
	}
	
	private String searchProposalIds(String invoiceId, String circuitNumber) {
		
		StringBuffer sb = new StringBuffer();
		
		List<Integer> proposalIdsList = new ArrayList<Integer>();
		StringBuffer returnStringBuffer = new StringBuffer();
		
		Session session = getSession();
		
		sb.append(" 	SELECT p.id ");
		sb.append(" 	FROM proposal p, invoice_item ii ");
		sb.append(" 	WHERE p.invoice_item_id = ii.id ");
		sb.append(" 		AND p.invoice_id = " + invoiceId );
		sb.append(" 		AND ii.stripped_circuit_number = " + circuitNumber );
		sb.append(" 		AND p.rec_active_flag = 'Y' " );
		sb.append(" 		AND p.proposal_flag = 1 " );
		sb.append(" 		AND ( p.item_type_id = 13 OR p.item_type_id = 15 " );
		sb.append(" 			OR p.item_type_id LIKE '3%' OR p.item_type_id LIKE '5%' " );
		sb.append(" 		) " );
		
		try{
			proposalIdsList = session.createSQLQuery(sb.toString()).list();
			
			for (int i = 0; i < proposalIdsList.size(); i ++) {
				returnStringBuffer.append(proposalIdsList.get(i)).append(",");
			}
			
			return returnStringBuffer.toString().substring(0, returnStringBuffer.toString().length() - 1);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		
		return null;
	}

	public Object[] searchInvoiceId (String invoiceId){
		logger.info("Enter method searchInvoiceId.");
		Session session = getSession();
		try {
			Object[] obj = (Object[]) session.createSQLQuery( "select  i.id,i.invoice_structure_id,i.account_number from invoice i where i.id ="+invoiceId)
			.uniqueResult();
			logger.info("Exit method searchInvoiceId.");
			return obj;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * 查询 Validation reference 信息。
	 * 其中 attachment_point_id 字段值可用来下载文件。
	 * @author haichun.zhang.
	 * @param  {object} auditReferenceType 存在于 audit_reference_type表中的字段 
	 * (audit_reference_type_name)。
	 * @param  {object} auditReferenceId 存在于 audit_result表中的字段
	 * (audit_reference_id)。
	 * @see InvoiceDetailServiceImpl#searchValidationResult
	 */
	public List<Object[]> queryValidationReferenceInfo(Object auditReferenceType, Object auditReferenceId, String auditResultId) {
		// Contract , 11

		logger.info("Enter method queryValidationReferenceInfo.");

		// 将 reference_type 转换成正确格式的表名。
		String referenceTypeTableName = ((String) auditReferenceType).toLowerCase().replaceAll(" ", "_");
		
		StringBuffer sb = new StringBuffer();
		
		if ( "tariff".equals(referenceTypeTableName) ) {
			
			String invoiceDate = this.getInvoiceDate(auditResultId);
			
			sb.append( "SELECT t.tariff_file_id, " );
			sb.append( "t.id, " );
//			sb.append( "FN_GET_VALIDATION_RESULT_TARIFF_LINK("+ auditReferenceId +", '"+ invoiceDate.replaceAll(" ", "") +"'), " );
			sb.append( "t.name, " );
			sb.append( "tf.attachment_point_id " );
			sb.append( "FROM tariff t " );
			sb.append( "LEFT JOIN tariff_file tf ON tf.id = t.tariff_file_id " );
			sb.append( "WHERE t.id = " + auditReferenceId + ";" );
			
			
		} else {
			
			sb.append("SELECT reference_type."+ referenceTypeTableName +"_file_id, ");
			sb.append(" reference_type.id, "); // 这个字段是 audit_result 表中的 audit_reference_id 这个字段的值。
			sb.append(" reference_type.name, " + referenceTypeTableName +"_file.attachment_point_id"); 
			
			sb.append(" FROM "+ referenceTypeTableName +" reference_type ");
			sb.append(" LEFT JOIN "+ referenceTypeTableName +"_file ON reference_type." + referenceTypeTableName +"_file_id = " + referenceTypeTableName +"_file.id " );

			sb.append(" WHERE ");
			sb.append(" reference_type.id = " + auditReferenceId + "  ");
			
		}
		
		
		

        final String queryString = sb.toString();
        HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				return session.createSQLQuery(queryString).list();
			}
		});
       

		logger.info("Enter method queryValidationReferenceInfo.");
		return list;
	}

	public Object[] findInvoiceItemByInvoiceIdObject(Integer invoiceId) {
		logger.info("Enter method findInvoiceItemByInvoiceIdObject.");
		final String queryString = findInvoiceItemByInvoiceIdObjectQueryString(invoiceId);
		Session session = getSession();
		try {
			Object[] obj = (Object[]) session.createSQLQuery(queryString)
			.uniqueResult();
			logger.info("Enter method findInvoiceItemByInvoiceIdObject.");
			return obj;
		} finally {
			releaseSession(session);
		}
	}

	private String voWhereConditionsByTotal(Integer invoiceId) {
		logger.info("Enter method voWhereConditionsByTotal.");
		StringBuffer sb = new StringBuffer();
		sb.append("where p.rec_active_flag = 'Y' ");
		// sb.append(" and p.invoice_item_id is not null ");
		sb.append(" and p.invoice_id = " + invoiceId + " ");
		logger.info("Enter method voWhereConditionsByTotal.");
		return sb.toString();
	}

	private String findInvoiceItemByInvoiceIdObjectQueryString(Integer invoiceId) {
		logger
				.info("Enter method findInvoiceItemByInvoiceIdObjectQueryString.");
		StringBuffer sb = new StringBuffer(
				"select 'Total',format(if(item.amount is null,0,item.amount),2),format(sum(if(p.payment_amount is null,0,p.payment_amount)),2),format(sum(if(p.dispute_amount is null,0,p.dispute_amount)),2) ");
		sb.append(" from proposal p ");
		sb
				.append(",(select sum(i.item_amount) as amount from invoice_item i where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_id = " + invoiceId + " ");
		sb.append(" and i.parent_item_id is null ) as item  ");
		sb.append(voWhereConditionsByTotal(invoiceId));
		logger.info("Exit method findInvoiceItemByInvoiceIdObjectQueryString.");
		return sb.toString();
	}

	private String findInvoiceItemByInvoiceIdQueryString(Integer invoiceId,
			String inId) {
		logger.info("Enter method findInvoiceItemByInvoiceIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{amount:\"',format(sum(if(i.item_amount is null,0,i.item_amount)),2),");
		if (!("".equals(inId))) {
			sb
					.append("'\",payment:\"',format((sum(if(i.payment_sum is null,0,i.payment_sum)) + if(prop.payment is null,0,prop.payment),2),");
			sb
					.append("'\",dispute:\"',format((sum(if(i.dispute_sum is null,0,i.dispute_sum)) + if(prop.dispute is null,0,prop.dispute),2),");
		} else {
			sb
					.append("'\",payment:\"',format(sum(if(i.payment_sum is null,0,i.payment_sum)),2),");
			sb
					.append("'\",dispute:\"',format(sum(if(i.dispute_sum is null,0,i.dispute_sum)),2),");
		}
		sb.append("'\",itemName:\"','Total',");
		sb.append("'\"}') a ");
		sb.append(" from invoice_item i ");
		if (!("".equals(inId))) {
			sb
					.append(" ,(select sum(payment_amount) as payment, sum(dispute_amount) as dispute ");
			sb.append(" from proposal p ");
			sb.append(" where p.rec_active_flag = 'Y' ");
			sb.append(" and p.invoice_id = " + invoiceId + " ");
			sb.append(" and p.invoice_item_id in (" + inId + ") ) as prop ");
		}
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_id = " + invoiceId + " ");
		sb.append(" and i.parent_item_id is null ");
		logger.info("Exit method findInvoiceItemByInvoiceIdQueryString.");
		return sb.toString();
	}

	public List<Object[]> getRelatedInvoicesByRelativeInvoiceId(
			Integer invoiceId) {
		logger.info("Enter method getRelatedInvoicesByRelativeInvoiceId.");
		final String queryString = getRelatedInvoicesByRelativeInvoiceIdQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method getRelatedInvoicesByRelativeInvoiceId.");
		return list;
	}

	private String getRelatedInvoicesByRelativeInvoiceIdQueryString(
			Integer invoiceId) {
		logger
				.info("Enter method getRelatedInvoicesByRelativeInvoiceIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select i.id as iid, i.invoice_number as number ");
		sb.append(" from relative_invoice ri, invoice i ");
		sb.append(" where ri.invoice2_id = i.id ");
		sb.append(" and ri.invoice1_id = " + invoiceId + "  ");
		sb.append(" and ri.rec_active_flag = 'Y' ");
		logger
				.info("Enter method getRelatedInvoicesByRelativeInvoiceIdQueryString.");
		return sb.toString();
	}

	public List<Object[]> getRelatedInvoicesByInvoiceId(Integer invoiceId) {
		logger.info("Enter method getRelatedInvoicesByInvoiceId.");
		final String queryString = getRelatedInvoicesByInvoiceIdQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method getRelatedInvoicesByInvoiceId.");
		return list;
	}

	private String getRelatedInvoicesByInvoiceIdQueryString(Integer invoiceId) {
		logger.info("Enter method getRelatedInvoicesByInvoiceIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select i.id as iid, i.invoice_number as number ");
		sb.append(" from relative_invoice ri, invoice i ");
		sb.append(" where ri.invoice1_id = i.id ");
		sb.append(" and ri.invoice2_id = " + invoiceId + " ");
		sb.append(" and ri.rec_active_flag = 'Y' ");
		logger.info("Enter method getRelatedInvoicesByInvoiceIdQueryString.");
		return sb.toString();
	}

	public List<Object[]> getInvoiceExcel(String invoiceId) {
		logger.info("Enter method getInvoiceExcel.");
		final String queryString = getInvoiceExcelQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method getInvoiceExcel.");
		return list;
	}

	private String getInvoiceExcelQueryString(String invoiceId) {
		logger.info("Enter method getInvoiceExcelQueryString.");
		StringBuffer sb = new StringBuffer(
				"select if(ii.invoice_id is null,'',ii.invoice_id), if(it.item_name is null,'',it.item_name), if(ii.item_name is null,'',ii.item_name), if(ii.rate is null,'0.00',ii.rate), if(ii.original_amount is null,'',ii.original_amount),");
		sb
				.append(" if(ii.discount is null,'',ii.discount), if(ii.item_amount is null,'',ii.item_amount), if(ii.item_key is null,'',ii.item_key), if(ii.start_date is null,'',ii.start_date), if(ii.end_date is null,'',ii.end_date)");
		sb.append(" from invoice_item ii,item_type it ");
		sb.append(" where ii.item_type_id = it.id ");
		sb.append(" and ii.invoice_id=" + invoiceId + "");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" order by ii.id asc ");
		logger.info("Enter method getInvoiceExcelQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findItemLowerMoneySum(Integer)
	 */
	public String findItemLowerMoneySum(Integer itemId) throws SQLException {
		logger.info("Enter method findItemLowerMoneySum.");
		String call = null;
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call GET_SUB_ITEM(?,?)");
			proc.setInt(1, itemId);
			proc.registerOutParameter(2, Types.VARCHAR);
			proc.execute();
			call = proc.getString(2);
			logger.info("Exit method findItemLowerMoneySum.");
			return call;
		} finally {
			releaseSession(session);
		}
	}

	public void UpdateInvoiceItemTable(Integer invoiceId) throws SQLException {
		logger.info("Enter method UpdateInvoiceItemTable.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_POPULATE_PROPOSAL_SUM(?)");
			proc.setInt(1, invoiceId);
			proc.execute();
			logger.info("Exit method UpdateInvoiceItemTable.");
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * 更新某一张账单的 external approve flag 标记
	 * @param {Integer} invoiceId
	 */
	public void updateExternalApproveFlagForCertainInvoice(Integer invoiceId) {
		
		logger.info("Enter method updateExternalApproveFlagForCertainInvoice");
		
		StringBuffer updateStringBuffer = new StringBuffer();
		
		updateStringBuffer.append(" UPDATE invoice ");
		updateStringBuffer.append(" SET external_approve_flag = 'N' ");
		updateStringBuffer.append(" WHERE rec_active_flag = 'Y' ");
		updateStringBuffer.append(" AND id = " + invoiceId);
		
		Session session = getSession();
		try {
			session.createSQLQuery(updateStringBuffer.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		
		
		logger.info("Exit method updateExternalApproveFlagForCertainInvoice");
	}

	public void UpdateProposal(Integer invoiceItemId, Integer accountCodeId,
			Integer disputeReasonId, Integer nowAccountCodeId,
			Integer nowDisputeReasonId, String action, Integer userId,
			String description) throws SQLException {
		logger.info("Enter method findItemLowerMoneySum.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call UPDATE_PROPOSAL(?,?,?,?,?,?,?,?)");
			// proc = con.prepareCall("call UPDATE_PROPOSAL(?,?,?,?,?,?)");
			proc.setInt(1, invoiceItemId);
			proc.setInt(2, accountCodeId);
			proc.setInt(3, disputeReasonId);
			// proc.setString(4, action);
			// proc.setInt(5, userId);
			// proc.setString(6, description);
			proc.setInt(4, nowAccountCodeId);
			proc.setInt(5, nowDisputeReasonId);
			proc.setString(6, action);
			proc.setInt(7, userId);
			proc.setString(8, description);
			proc.execute();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method findItemLowerMoneySum.");
	}

	private String itemLowerMoneySumQueryString(Integer invoiceItemId) {
		logger.info("Enter method itemLowerMoneySumQueryString.");
		StringBuffer sb = new StringBuffer("call GET_SUB_ITEM(" + invoiceItemId
				+ ",@item_id);");
		sb.append("select @item_id as id_out;");
		logger.info("Exit method itemLowerMoneySumQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findItemProposal(String)
	 */
	public List<String> findItemProposal(String itemId) {
		logger.info("Enter method findItemProposal.");
		final String sql = this.itemProposaQueryString(itemId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemProposal.");
		return c;
	}

	private String itemProposaQueryString(String itemId) {
		logger.info("Enter method itemProposaQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{dispute:\"',sum(if(p.dispute_amount is null,0,p.dispute_amount)),");
		sb
				.append("'\",pay:\"',sum(if(p.payment_amount is null,0,p.payment_amount)),");
		sb
				.append("'\",cleanup:\"',sum(if(p.cleanup_amount is null,0,p.cleanup_amount)),");
		sb.append("'\"}') a ");
		sb.append("from proposal p ");
		sb.append("where p.rec_active_flag = \"Y\" ");
		sb.append("and p.invoice_item_id = " + itemId);
		logger.info("Exit method itemProposaQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#getNumberOfInvoices(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getInvoiceDetailNumberOfInvoices(SearchInvoiceVO sio) {
		logger.info("Enter method getInvoiceDetailNumberOfInvoices.");
		final String sql = this.getInvoiceDetailNumberQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getInvoiceDetailNumberOfInvoices.");
		return count;
	}

	private String getInvoiceDetailNumberQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getInvoiceDetailNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from proposal p left join invoice_item ii ");
		sb
				.append(" on (ii.id = p.invoice_item_id and p.rec_active_flag = 'Y' and p.invoice_id = "
						+ sio.getInvoiceId() + ") ");
		sb.append(" where ii.rec_active_flag = 'Y' ");
		sb.append(" and ii.item_type_id=" + sio.getItemTypeId() + " ");
		sb.append(" and p.invoice_item_id is not null ");
		sb.append(" and p.proposal_flag = 1 ");
		sb
				.append(" and (if(p.payment_amount is null,0,p.payment_amount) != 0  or if(p.dispute_amount is null,0,p.dispute_amount) != 0  or if(p.credit_amount is null,0,p.credit_amount) != 0 ) ");
		logger.info("Exit method getInvoiceDetailNumberQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findProposalByitemId(String)
	 */
	public List<String> findProposalByitemId(String itemId) {
		logger.info("Enter method findProposalByitemId.");
		final String sql = this.proposalByitemIdQueryString(itemId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findProposalByitemId.");
		return c;
	}

	private String proposalByitemIdQueryString(String itemId) {
		logger.info("Enter method proposalByitemIdQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',ii.id,");
		sb
				.append("',pay:\"',if(ii.payment_sum is null,0,ii.payment_sum)+sum(if(p.payment_amount is null,0,p.payment_amount)),");
		sb
				.append("'\",dispute:\"',if(ii.dispute_sum is null,0,ii.dispute_sum)+sum(if(p.dispute_amount is null,0,p.dispute_amount)),");
		sb
				.append("'\",cleanup:\"',if(ii.cleanup_sum is null,0,ii.cleanup_sum)+sum(if(p.cleanup_amount is null,0,p.cleanup_amount)),");
		sb
				.append("'\",shortPay:\"',if(ii.item_amount is null,0,ii.item_amount) - if(ii.payment_sum is null,0,ii.payment_sum)+sum(if(p.payment_amount is null,0,p.payment_amount)) ,");
		sb.append("'\",itemname:\"',toJSON(ii.item_name is null,'',ii.item_name),");
		sb.append("'\",rate:\"',if(ii.rate is null,'0.00',ii.rate),");
		sb
				.append("'\",itemAmount:\"',if(ii.item_amount is null,0,ii.item_amount),'\", original:\"',if(ii.original_amount is null,'',ii.original_amount),");
		sb
				.append("'\",discount:\"',if(ii.discount is null,'',ii.discount),'\", quantity:\"',if(ii.quantity is null,'',ii.quantity),");
		sb
				.append("'\",itemKey:\"',if(ii.item_key is null,'',ii.item_key),'\", startDate:\"',if(ii.start_date is null,'',ii.start_date),");
		sb
				.append("'\",endDate:\"',if(ii.end_date is null,'',ii.end_date),'\", circuitNumber:\"',if(ii.circuit_number is null,'',ii.circuit_number),");
		sb.append("'\"}') a ");
		sb
				.append("from invoice_item ii left join proposal p on p.invoice_item_id = ii.id ");
		sb.append("where ii.rec_active_flag = \"Y\" ");
		sb.append("and ii.id = " + itemId);
		logger.info("Exit method proposalByitemIdQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findItemTypeListByItemId(String)
	 */
	public List<String> findItemTypeListByItemId(String invoiceItemId,
			String invoiceId) {
		logger.info("Enter method findItemTypeListByItemId.");
		final String sql = this.itemTypeByItemIdQueryString(invoiceItemId,
				invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemTypeListByItemId.");
		return c;
	}

	private String itemTypeByItemIdQueryString(String invoiceItemId,
			String invoiceId) {
		logger.info("Enter method itemTypeByItemIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select distinct concat('{typeId:',if(ii.item_type_id is null,'',ii.item_type_id),");
		sb.append("',structureCodeId:',s.id,");
		sb
				.append("',typeName:\"',if(it.item_type_name is null,'',it.item_type_name),");
		sb
				.append("'\",structureCode:\"',if(s.invoice_structure_code is null,'',s.invoice_structure_code),");
		sb.append("'\"}') a ");
		sb
				.append("from  invoice i join invoice_item ii join item_type it join invoice_structure s ");
		sb.append(" where ii.invoice_id = i.id ");
		sb.append(" and ii.item_type_id = it.id ");
		sb.append(" and i.invoice_structure_id = s.id ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" and ii.invoice_id = " + invoiceId + " ");
		sb.append(" and ii.parent_item_id = " + invoiceItemId + " ");
		logger.info("Exit method itemTypeByItemIdQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findItemTypeList(String)
	 */
	public List<String> findItemTypeList(String invoiceId) {
		logger.info("Enter method findItemTypeList.");
		final String sql = this.itemTypeQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemTypeList.");
		return c;
	}

	private String itemTypeQueryString(String invoiceId) {
		logger.info("Enter method invoiceItemAllByinvoiceIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select distinct concat('{typeId:',if(ii.item_type_id is null,'',ii.item_type_id),");
		sb.append("',structureCodeId:',s.id,");
		sb
				.append("',typeName:\"',if(it.item_type_name is null,'',it.item_type_name),");
		sb
				.append("'\",structureCode:\"',if(s.invoice_structure_code is null,'',s.invoice_structure_code),");
		sb.append("'\"}') a ");
		sb
				.append("from invoice i join invoice_item ii join item_type it join invoice_structure s ");
		sb.append(" where ii.invoice_id = i.id ");
		sb.append(" and ii.item_type_id = it.id ");
		sb.append(" and i.invoice_structure_id = s.id ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" and i.id = " + invoiceId + " ");
		sb.append(" and ii.parent_item_id is null ");
		logger.info("Exit method invoiceItemAllByinvoiceIdQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findInvoiceItemList(String)
	 */
	public List<String> findInvoiceItemList(SearchInvoiceVO sio) {
		logger.info("Enter method findInvoiceItemList.");
		final String sql = this.invoiceItemAllByinvoiceIdQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findInvoiceItemList.");
		return c;
	}

	public List<Object[]> findInvoiceItemListByObject(SearchInvoiceVO sio) {
		logger.info("Enter method findInvoiceItemListByObject.");
		final String sql = this
				.invoiceItemAllByinvoiceIdByObjectQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findInvoiceItemListByObject.");
		return c;
	}

	private String voWhereConditionsByItem(SearchInvoiceVO sio) {
		logger.info("Exit method voWhereConditionsByItem.");
		StringBuffer sb = new StringBuffer();
		sb.append(" where i.rec_active_flag = 'Y' ");
		if (sio.getParentItemId() != null) {
			sb.append(" and i.parent_item_id = " + sio.getParentItemId() + " ");
		} else {
			sb.append(" and i.parent_item_id is null ");
		}
		sb.append(" and i.invoice_id = " + sio.getInvoiceId() + " ");
		sb
				.append(" group by i.id ) as item left join proposal p on (item.id = p.invoice_item_id)) ");
		sb.append(" group by item.id ");
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method voWhereConditionsByItem.");
		return sb.toString();
	}

	private String invoiceItemAllByinvoiceIdByObjectQueryString(
			SearchInvoiceVO sio) {
		logger
				.info("Enter method invoiceItemAllByinvoiceIdByObjectQueryString.");
		StringBuffer sb = new StringBuffer(
				"select item.name,format(if(item.amount is null,0,item.amount),2), format((if(sum(p.payment_amount) is null,0,sum(p.payment_amount)) + if(item.payment is null,0,item.payment)),2),");
		sb
				.append("format((if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)) + if(item.dispute is null,0,item.dispute)),2) ");
		sb
				.append(" from ((select i.id as id,i.item_name as name,i.payment_sum as payment,i.dispute_sum as dispute,i.item_amount as amount,i.proposal_flag as flag,count(ci.parent_item_id) as coun from invoice_item i left join invoice_item ci on (i.id = ci.parent_item_id) ");
		sb.append(voWhereConditionsByItem(sio));
		logger
				.info("Exit method invoiceItemAllByinvoiceIdByObjectQueryString.");
		return sb.toString();
	}

	private String invoiceItemAllByinvoiceIdQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method invoiceItemAllByinvoiceIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{id:',item.id,',itemName:\"',if(item.name is null,'',item.name),");
		sb
				.append("'\",payment:\"',format(if(sum(p.payment_amount) is null,0,sum(p.payment_amount)) + if(item.payment is null,0,item.payment),2),");
		sb
				.append("'\",dispute:\"',format(if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)) + if(item.dispute is null,0,item.dispute),2),");
		sb
				.append("'\",amount:\"',format(if(item.amount is null,0,item.amount),2),");
		sb.append("'\",proposalFlag:\"',if(item.flag is null,'',item.flag),");
		sb.append("'\",countId:\"',item.coun,");
		sb.append("'\"}') a ");
		sb
				.append("from ((select i.id as id,i.item_name as name,i.payment_sum as payment,i.dispute_sum as dispute,i.item_amount as amount,i.proposal_flag as flag,count(ci.parent_item_id) as coun from invoice_item i left join invoice_item ci on (i.id = ci.parent_item_id) ");
		sb.append(voWhereConditionsByItem(sio));
		logger.info("Exit method invoiceItemAllByinvoiceIdQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#getInvoiceItemListOfInvoices(com.saninco.ccm.vo.SearchInvoiceVO)
	 */
	public long getInvoiceItemListOfInvoices(SearchInvoiceVO sio) {
		logger.info("Enter method getInvoiceItemListOfInvoices.");
		final String sql = this.getInvoiceItemListQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getInvoiceItemListOfInvoices.");
		return count;
	}

	private String getInvoiceItemListQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method getInvoiceItemListQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb
				.append("from ((select i.id as id,i.item_name as name,i.payment_sum as payment,i.dispute_sum as dispute,i.item_amount as amount,i.proposal_flag as flag,count(ci.parent_item_id) as coun from invoice_item i left join invoice_item ci on (i.id = ci.parent_item_id) ");
		sb.append(" where i.rec_active_flag = 'Y' ");
		if (sio.getParentItemId() != null) {
			sb.append(" and i.parent_item_id = " + sio.getParentItemId() + " ");
		} else {
			sb.append(" and i.parent_item_id is null ");
		}
		sb.append(" and i.invoice_id = " + sio.getInvoiceId() + " ");
		sb
				.append(" group by i.id ) as item left join proposal p on (item.id = p.invoice_item_id)) ");
		logger.info("Exit method getInvoiceItemListQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findInvoiceLabelId(String,String,String)
	 */
	public List<String> findInvoiceLabelId(String invoiceId, String labelId,
			String recActiveFlag) {
		logger.info("Enter method findInvoiceLabelId.");
		final String sql = this.invoiceLabelIdQueryString(invoiceId, labelId,
				recActiveFlag);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findInvoiceLabelId.");
		return c;
	}

	private String invoiceLabelIdQueryString(String invoiceId, String labelId,
			String recActiveFlag) {
		logger.info("Enter method invoiceLabelIdQueryString.");
		StringBuffer sb = new StringBuffer("select i.id ");
		sb.append("from invoice_label i ");
		if (invoiceId != null || labelId != null || recActiveFlag != null) {
			sb.append("where ");
		}
		if (invoiceId != null)
			sb.append(" i.label_id =" + labelId + " ");
		if (labelId != null)
			sb.append(" and i.invoice_id =" + invoiceId + " ");
		if (recActiveFlag != null)
			sb.append(" and i.rec_active_flag =\"" + recActiveFlag + "\"");
		logger.info("Exit method invoiceLabelIdQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#searchInvoiceLabel(Integer)
	 */
	public List<String> searchInvoiceLabel(Integer invoiceId) {
		logger.info("Enter method searchInvoiceLabel.");
		final String sql = this.invoiceLabelByinvoiceIdQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchInvoiceLabel.");
		return c;
	}

	private String invoiceLabelByinvoiceIdQueryString(int invoiceId) {
		logger.info("Enter method invoiceLabelByinvoiceIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select l.icon_file_path ");
//		"select if(l.icon_file_path is null,'',l.icon_file_path) ");
		// StringBuffer sb = new StringBuffer("select i.label_id ");
		// StringBuffer sb = new StringBuffer("select
		// concat('{id:',i.id,',label:\"',if(l.label_name is
		// null,'',l.label_name),");
		// sb.append("'\",labelType:\"',if(t.label_type_name is
		// null,'',t.label_type_name),");
		// sb.append("'\",icon:\"',if(l.icon_file_path is
		// null,'',l.icon_file_path),");
		// sb.append("'\"}') a ");
		sb.append("from invoice_label i, label l ");
		sb.append("where i.label_id = l.id and i.rec_active_flag = \"Y\" ");
		sb.append(" and i.invoice_id =" + invoiceId + " ");
		sb.append(" and l.icon_file_path is not null ");
		sb.append(" and l.icon_file_path <> \"\" ");
		sb.append("order by l.label_type_id, i.id ");
		logger.info("Exit method invoiceLabelByinvoiceIdQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findParentItemIdByinvoiceId(int)
	 */
	public List<BigInteger> findParentItemIdByinvoiceId(int invoiceId) {
		logger.info("Enter method findParentItemIdByinvoiceId.");
		final String sql = this.parentItemIdByinvoiceIdQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<BigInteger> c = (List<BigInteger>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findParentItemIdByinvoiceId.");
		return c;
	}

	private String parentItemIdByinvoiceIdQueryString(int invoiceId) {
		logger.info("Enter method parentItemIdByinvoiceIdQueryString.");
		StringBuffer sb = new StringBuffer("select i.id ");
		sb.append("from invoice_item i where i.invoice_id = " + invoiceId);
		logger.info("Exit method parentItemIdByinvoiceIdQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findTypeOpenAccItemId(SearchInvoiceVO)
	 */
	public List<BigInteger> findTypeOpenAccItemId(
			SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findTypeOpenAccItemId.");
		final String sql = this.typeOpenAccIdQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<BigInteger> c = (List<BigInteger>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findTypeOpenAccItemId.");
		return c;
	}

	private String typeOpenAccIdQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method typeOpenAccIdQueryString.");
		StringBuffer sb = new StringBuffer("select i.id ");
		sb.append("from invoice_item i ");
		sb.append("where i.rec_active_flag = \"Y\" ");

		if (sio.getItemId() != null) {
			sb.append(" and i.parent_item_id=" + sio.getItemId() + " ");
		} else {
			sb.append(" and i.invoice_id=" + sio.getInvoiceId() + " ");
		}
		if (sio.getItemTypeId() != null)
			sb.append(" and i.item_type_id=" + sio.getItemTypeId() + " ");
		sb.append(sio.getOrderByCause(null));
		sb.append("limit " + sio.getStartIndex());
		sb.append("," + sio.getRecPerPage());
		logger.info("Exit method typeOpenAccIdQueryString.");
		return sb.toString();
	}

	public List<Object[]> findItemTypeAndInvoiceStructure(String invoiceId,
			String banId) {
		logger.info("Enter method findItemTypeAndInvoiceStructure.");
		final String sql = this.findItemTypeAndInvoiceStructureQueryString(
				invoiceId, banId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemTypeAndInvoiceStructure.");
		return c;
	}

	private String findItemTypeAndInvoiceStructureQueryString(String invoiceId,
			String banId) {
		logger.info("Enter method findItemTypeAndInvoiceStructureQueryString.");
		StringBuffer sb = new StringBuffer(
				"select distinct s.id as sid,s.invoice_structure_code,it.id as itid,it.item_type_name ");
		sb
				.append(" from invoice_item ii join invoice i join ban b join item_type it join invoice_structure s ");
		sb.append(" where ii.invoice_id = i.id ");
		sb.append(" and ii.item_type_id = it.id ");
		sb.append(" and b.invoice_structure_id = s.id ");
		sb.append(" and i.ban_id = b.id ");
		sb.append(" and ii.rec_active_flag = 'Y' ");
		sb.append(" and i.id = " + invoiceId + " ");
		sb.append(" and b.id = " + banId + " ");
		logger.info("Exit method findItemTypeAndInvoiceStructureQueryString.");
		return sb.toString();
	}

	public long findInvoiceItemListNumber(SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findInvoiceItemListNumber.");
		final String sql = this
				.findInvoiceItemListNumberQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method findInvoiceItemListNumber.");
		return count;
	}

	private String findInvoiceItemListNumberQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method findInvoiceItemListNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb
				.append(" from invoice_item ii left join invoice i on (ii.invoice_id = i.id) left join ban b on (i.ban_id = b.id) ");
		sb.append(" where ii.rec_active_flag = 'Y' ");
		sb.append(" and b.invoice_structure_id = "
				+ sio.getInvoiceStructureCodeId() + " ");
		sb.append(" and ii.item_type_id = " + sio.getItemTypeId() + " ");
		sb.append(" and ii.invoice_id = " + sio.getInvoiceId() + " ");
		logger.info("Exit method findInvoiceItemListNumberQueryString.");
		return sb.toString();
	}

	public List<Object[]> findInvoiceItemListObject(SearchInvoiceVO sio) {
		logger.info("Enter method findInvoiceItemListObject.");
		final String sql = this.findInvoiceItemListObjectQueryString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findInvoiceItemListObject.");
		return c;
	}

	private String findInvoiceItemListObjectQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method findInvoiceItemListObjectQueryString.");
		StringBuffer sb = new StringBuffer("select " + sio.getTableField()
				+ " ");
		sb
				.append(" from invoice_item ii left join invoice i on (ii.invoice_id = i.id) left join ban b on (i.ban_id = b.id) ");
		sb.append(" where ii.rec_active_flag = 'Y' ");
		sb.append(" and b.invoice_structure_id = "
				+ sio.getInvoiceStructureCodeId() + " ");
		sb.append(" and ii.item_type_id = " + sio.getItemTypeId() + " ");
		sb.append(" and ii.invoice_id = " + sio.getInvoiceId() + " ");
		sb.append(" " + sio.getOrderByCause(null) + " ");
		sb.append(" " + sio.getLimitCause() + " ");
		logger.info("Exit method findInvoiceItemListObjectQueryString.");
		return sb.toString();
	}

	public List<Object[]> findItemDisplayTitleName(
			SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findItemDisplayTitleName.");
		final String sql = this
				.findItemDisplayTitleNameQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemDisplayTitleName.");
		return c;
	}

	public List<Object[]> findItemDisplayTitleNameDefult(String itemTypeId,
			String invoiceStructureId) {
		logger.info("Enter method findItemDisplayTitleNameDefult.");
		final String sql = this.findItemDisplayTitleNameDefultQueryString(
				itemTypeId, invoiceStructureId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemDisplayTitleNameDefult.");
		return l;
	}

	private String findItemDisplayTitleNameDefultQueryString(String itemTypeId,
			String invoiceStructureId) {
		logger.info("Enter method findItemDisplayTitleNameQueryString.");
		StringBuffer sb = new StringBuffer(
				"select d.display_title,d.table_field,d.display_format,d.display_order,d.display_width ");
		sb.append(" from item_display d ");
		sb.append(" where d.rec_active_flag = 'Y' ");
		sb.append(" and d.key_field_flag = 'Y' ");
		sb.append(" and d.item_type_id = " + itemTypeId + " ");
		sb.append(" and d.invoice_structure_id = " + invoiceStructureId + " ");
		sb.append(" order by d.display_order asc ");
		logger.info("Exit method findItemDisplayTitleNameQueryString.");
		return sb.toString();
	}

	private String findItemDisplayTitleNameQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method findItemDisplayTitleNameQueryString.");
		StringBuffer sb = new StringBuffer(
				"select d.display_title,d.table_field,d.display_format ");
		sb.append(" from item_display d ");
		sb.append(" where d.rec_active_flag = 'Y' ");
		sb.append(" and d.key_field_flag = 'Y' ");
		sb.append(" and d.item_type_id = " + sio.getItemTypeId() + " ");
		sb.append(" and d.invoice_structure_id = "
				+ sio.getInvoiceStructureCodeId() + " ");
		sb.append(" order by d.display_order asc ");
		logger.info("Exit method findItemDisplayTitleNameQueryString.");
		return sb.toString();
	}

	public List<Object[]> findInvoiceSummary(String invoiceId) {
		logger.info("Enter method findInvoiceSummary.");
		final String sql = this.findInvoiceSummaryQueryString(invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findInvoiceSummary.");
		return c;
	}

	private String findInvoiceSummaryQueryString(String invoiceId) {
		logger.info("Enter method findInvoiceSummaryQueryString.");
		StringBuffer sb = new StringBuffer(
				"select i.invoice_number,v.vendor_name,b.account_number,if(b.line_of_business is null,'',b.line_of_business),");
		sb
				.append("i.invoice_date,i.invoice_due_date,i.invoice_receive_date,i.invoice_start_date,i.invoice_end_date,");
		sb
				.append("c.currency_name,s.invoice_status_name,concat(u.first_name,' ',last_name),");
		sb.append("if(i.invoice_usage is null,0,format(i.invoice_usage,2)),");
		sb.append("format(if(i.invoice_tax is null,0,i.invoice_tax) + ");
		sb.append("if(i.invoice_surcharge is null,0,i.invoice_surcharge) + ");
		sb
				.append("if(i.invoice_regulation_fee is null,0,i.invoice_regulation_fee),2), ");
		sb
				.append("if(i.invoice_late_payment_charge is null,0,format(i.invoice_late_payment_charge,2)),");
		sb.append("if(i.invoice_mrc is null,0,format(i.invoice_mrc,2)),");
		sb.append("if(i.invoice_occ is null,0,format(i.invoice_occ,2)),");
		sb
				.append("if(i.invoice_adjustment is null,0,format(i.invoice_adjustment,2)),");
		sb.append("if(i.invoice_credit is null,0,format(i.invoice_credit,2)),");
		sb
				.append("if(i.calculated_current_due is null,0,format(i.calculated_current_due,2)),");
		sb
				.append("if(i.facepage_difference is null,0,format(i.facepage_difference,2)),");

		sb
				.append("if(i.invoice_previous_balance is null,0,format(i.invoice_previous_balance,2)),");
		sb
				.append("if(i.invoice_previous_payment is null,0,format(i.invoice_previous_payment,2)),");
		sb
				.append("if(i.invoice_balance_forward is null,0,format(i.invoice_balance_forward,2)),");
		sb
				.append("if(i.invoice_total_due is null,0,format(i.invoice_total_due,2)),");

		sb.append("if(i.payment_amount is null,0,format(i.payment_amount,2)),");
		sb.append("if(i.dispute_amount is null,0,format(i.dispute_amount,2)),");
		sb
				.append("if(i.misc_adjustment_amount is null,0,format(i.misc_adjustment_amount,2))");

		sb
				.append(" from invoice i join vendor v join ban b join invoice_status s join user u join currency c");
		sb.append(" where i.rec_active_flag = 'Y' ");
		sb.append(" and b.currency_id = c.id ");
		sb.append(" and i.vendor_id = v.id ");
		sb.append(" and i.ban_id = b.id ");
		sb.append(" and i.assigned_analyst_id = u.id ");
		sb.append(" and i.invoice_status_id = s.id ");
		sb.append(" and i.id = " + invoiceId + " ");
		logger.info("Exit method findInvoiceSummaryQueryString.");
		return sb.toString();
	}

	public List<Object[]> findTypeOpenAccByObject(
			SearchInvoiceVO searchInvoiceVO) {
		logger.info("Enter method findTypeOpenAccByObject.");
		final String sql = this.typeOpenAccByObjectQueryString(searchInvoiceVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findTypeOpenAccByObject.");
		return c;
	}

	private String typeOpenAccByObjectQueryString(SearchInvoiceVO sio) {
		logger.info("Enter method typeOpenAccByObjectQueryString.");
		StringBuffer sb = new StringBuffer("select " + sio.getTableField()
				+ " ");
		sb.append("from ((invoice i join ban b) join invoice_item ii)");
		sb.append(" where ii.rec_active_flag = \"Y\" ");
		sb.append(" and i.id = ii.invoice_id ");
		sb.append(" and i.ban_id = b.id ");
		sb.append(" and ii.invoice_id=" + sio.getInvoiceId() + " ");
		if (sio.getInvoiceItemId() != null) {
			sb.append(" and ii.parent_item_id=" + sio.getInvoiceItemId() + " ");
		} else {
			sb.append(" and ii.parent_item_id is null ");
		}
		if (sio.getItemTypeId() != null)
			sb.append(" and ii.item_type_id=" + sio.getItemTypeId() + " ");
		if (sio.getInvoiceStructureCodeId() != null)
			sb.append(" and b.invoice_structure_id="
					+ sio.getInvoiceStructureCodeId() + " ");
		logger.info("Exit method typeOpenAccByObjectQueryString.");
		return sb.toString();
	}

	private String voWhereConditions(SearchInvoiceVO sio) {
		logger.info("Enter method voWhereConditions.");
		StringBuffer sb = new StringBuffer();
		sb.append(" where ii.rec_active_flag = \"Y\" ");

		if (sio.getInvoiceItemId() != null) {
			sb.append(" and ii.parent_item_id=" + sio.getInvoiceItemId() + " ");
		} else {
			sb.append(" and ii.invoice_id=" + sio.getInvoiceId() + " ");
			sb.append(" and ii.parent_item_id is null ");
		}
		if (sio.getItemTypeId() != null)
			sb.append(" and ii.item_type_id=" + sio.getItemTypeId() + " ");
		sb.append(" group by ii.id ");
		sb.append(sio.getOrderByCause(null));
		sb.append("limit " + sio.getStartIndex());
		sb.append("," + sio.getRecPerPage());
		logger.info("Exit method voWhereConditions.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findItemProposalByItemId(Integer)
	 */
	public List<String> findItemProposalByItemId(Integer itemId) {
		logger.info("Enter method findItemProposalByItemId.");
		final String sql = this.itemProposalByItemIdQueryString(itemId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findItemProposalByItemId.");
		return c;
	}

	private String itemProposalByItemIdQueryString(Integer itemId) {
		logger.info("Enter method itemProposalByItemIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{id:',p.id,',paymentAmount:\"',ROUND(if(p.payment_amount is null,0,p.payment_amount),2),");
		sb
				.append("'\",pay:\"',ROUND(if(ii.payment_sum is null,0,ii.payment_sum),2),");
		sb
				.append("'\",dispute:\"',ROUND(if(ii.dispute_sum is null,0,ii.dispute_sum),2),");
		sb
				.append("'\",cleanup:\"',ROUND(if(ii.cleanup_sum is null,0,ii.cleanup_sum),2),");
		sb
				.append("'\",disputeAmount:\"',ROUND(if(p.dispute_amount is null,0,p.dispute_amount),2),");
		sb
				.append("'\",cleanupAmount:\"',ROUND(if(p.cleanup_amount is null,0,p.cleanup_amount),2),");
		sb
				.append("'\",cleanupNotes:\"',if(p.cleanup_notes is null,'',p.cleanup_notes),");
		sb
				.append("'\",disputeNotes:\"',if(p.dispute_notes is null,'',p.dispute_notes),");
		sb
				.append("'\",paymentNotes:\"',if(p.payment_notes is null,'',p.payment_notes),");
		sb
				.append("'\",itemId:\"',if(p.invoice_item_id is null,'',p.invoice_item_id),");
		sb
				.append("'\",paymentTransactionNumber:\"',if(pa.payment_transaction_number is null,'',pa.payment_transaction_number),");
		sb
				.append("'\",disputeTransactionNumber:\"',if(d.dispute_number is null,'',d.dispute_number),");
		sb
				.append("'\",scoaCode:\"',if(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("'\"}') a ");
		sb
				.append("from ((((invoice_item ii left join proposal p on p.invoice_item_id = ii.id) left join payment pa on p.payment_id = pa.id) left join dispute d on p.dispute_id = d.id) left join account_code ac on p.account_code_id = ac.id)");
		sb.append("where ");
		sb.append(" ii.id = " + itemId + " ");
		sb.append(" order by p.id ");
		logger.info("Exit method itemProposalByItemIdQueryString.");
		return sb.toString();
	}

	public List<Object[]> searchInvoiceItemGroupProposals(String invoiceItemId) {
		logger.info("Enter method searchInvoiceItemGroupProposals.");
		final String queryString = searchInvoiceItemGroupProposalsQueryString(invoiceItemId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method searchInvoiceItemGroupProposals.");
		return list;
	}

	private String searchInvoiceItemGroupProposalsQueryString(
			String invoiceItemId) {
		logger.info("Enter method searchInvoiceItemProposalsQueryString.");
		StringBuffer sb = new StringBuffer(
				"select if(p.account_code_id is null,'',p.account_code_id), if(p.dispute_reason_id is null,'',p.dispute_reason_id), ");
		sb
				.append(" if(sum(p.payment_amount) is null,0,sum(p.payment_amount)),if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)), ");
		sb
				.append(" if(ac.account_code_name is null,'',ac.account_code_name),if(dr.dispute_reason_name is null,'',dr.dispute_reason_name) ");
		sb
				.append(" FROM invoice_item ii, (proposal p left join dispute_reason dr on (p.dispute_reason_id = dr.id) left join account_code ac  on(p.account_code_id = ac.id)) ");
		sb.append(" WHERE ii.id = p.invoice_item_id ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND ((ii.id = " + invoiceItemId
				+ " AND ii.proposal_flag = 1 ) ");
		sb
				.append(" OR (ii.invoice_id = (select invoice_id from invoice_item where id = "
						+ invoiceItemId + ") ");
		sb.append(" AND ii.item_type_id in (select itst.item_type_id ");
		sb.append(" from item_structure itst ");
		sb.append(" where ");
		sb
				.append(" ((itst.parent_item_type_id = (select item_type_id from invoice_item where id = "
						+ invoiceItemId
						+ ") and itst.proposal_flag = 1 and itst.rec_active_flag='Y') ");
		sb.append(" OR ");
		sb.append(" (itst.parent_item_type_id in (select itst.item_type_id ");
		sb.append(" from item_structure itst ");
		sb
				.append(" where itst.parent_item_type_id = (select item_type_id from invoice_item where id = "
						+ invoiceItemId
						+ ") and itst.proposal_flag = 2 and itst.rec_active_flag='Y') ");
		sb.append(" AND itst.proposal_flag = 1) ");
		sb.append(" OR ");
		sb.append(" (itst.parent_item_type_id in  ");
		sb.append(" (select itst.id ");
		sb.append(" from item_structure itst ");
		sb
				.append(" where itst.parent_item_type_id in (select itst.item_type_id ");
		sb.append(" from item_structure itst ");
		sb
				.append(" where itst.parent_item_type_id = (select item_type_id from invoice_item where id = "
						+ invoiceItemId
						+ ") and itst.proposal_flag = 2 and itst.rec_active_flag='Y') ");
		sb.append(" AND itst.proposal_flag = 2) ");
		sb.append(" AND itst.proposal_flag = 1)) ");
		sb
				.append(" AND itst.invoice_structure_id = (select invoice_structure_id from invoice where id =  ");
		sb.append(" (select invoice_id from invoice_item where id = "
				+ invoiceItemId + " ))))) ");
		sb.append(" GROUP BY p.account_code_id, p.dispute_reason_id ");
		logger.info("Enter method searchInvoiceItemProposalsQueryString.");
		return sb.toString();
	}

	public List<String> searchInvoiceItemProposals(String invoiceItemId) {
		logger.info("Enter method searchInvoiceItemProposals.");
		final String queryString = searchInvoiceItemProposalsQueryString(invoiceItemId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method searchInvoiceItemProposals.");
		return list;
	}

	private String searchInvoiceItemProposalsQueryString(String invoiceItemId) {
		logger.info("Enter method searchInvoiceItemProposalsQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{accountCode:\"',if(ac.account_code_name is null,'',ac.account_code_name),");
		sb
				.append("'\",payment:\"',format(if(sum(p.payment_amount) is null,0,sum(p.payment_amount)),2),");
		sb
				.append("'\",dispute:\"',format(if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)),2),");
		sb
				.append("'\",disputeCategory:\"',if(dr.dispute_reason_name is null,'',dr.dispute_reason_name),");
		sb
				.append("'\",disputeCategoryId:\"',if(p.dispute_reason_id is null,'',p.dispute_reason_id),");
		sb
				.append("'\",accountCodeId:\"',if(p.account_code_id is null,'',p.account_code_id),");
		sb.append("'\"}') a ");

		sb
				.append(" FROM invoice_item ii, (proposal p left join dispute_reason dr on (p.dispute_reason_id = dr.id) left join account_code ac  on(p.account_code_id = ac.id)) ");
		sb.append(" WHERE ii.id = p.invoice_item_id ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND ((ii.id = " + invoiceItemId
				+ " AND ii.proposal_flag = 1 ) ");
		sb
				.append(" OR (ii.invoice_id = (select invoice_id from invoice_item where id = "
						+ invoiceItemId + ") ");
		sb.append(" AND ii.item_type_id in (select itst.item_type_id ");
		sb.append(" from item_structure itst ");
		sb.append(" where ");
		sb
				.append(" ((itst.parent_item_type_id = (select item_type_id from invoice_item where id = "
						+ invoiceItemId
						+ ") and itst.proposal_flag = 1 and itst.rec_active_flag='Y') ");
		sb.append(" OR ");
		sb.append(" (itst.parent_item_type_id in (select itst.item_type_id ");
		sb.append(" from item_structure itst ");
		sb
				.append(" where itst.parent_item_type_id = (select item_type_id from invoice_item where id = "
						+ invoiceItemId
						+ ") and itst.proposal_flag = 2 and itst.rec_active_flag='Y') ");
		sb.append(" AND itst.proposal_flag = 1) ");
		sb.append(" OR ");
		sb.append(" (itst.parent_item_type_id in  ");
		sb.append(" (select itst.id ");
		sb.append(" from item_structure itst ");
		sb
				.append(" where itst.parent_item_type_id in (select itst.item_type_id ");
		sb.append(" from item_structure itst ");
		sb
				.append(" where itst.parent_item_type_id = (select item_type_id from invoice_item where id = "
						+ invoiceItemId
						+ ") and itst.proposal_flag = 2 and itst.rec_active_flag='Y') ");
		sb.append(" AND itst.proposal_flag = 2) ");
		sb.append(" AND itst.proposal_flag = 1)) ");
		sb
				.append(" AND itst.invoice_structure_id = (select invoice_structure_id from invoice where id =  ");
		sb.append(" (select invoice_id from invoice_item where id = "
				+ invoiceItemId + " ))))) ");
		sb.append(" GROUP BY p.account_code_id, p.dispute_reason_id ");
		logger.info("Enter method searchInvoiceItemProposalsQueryString.");
		return sb.toString();
	}

	public List<String> searchItemGroupProposalTotalAmount(String boxInId,
			Integer invoiceId) {
		logger.info("Enter method searchItemGroupProposalTotalAmount.");
		final String queryString = searchItemGroupProposalTotalAmountQueryString(
				boxInId, invoiceId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method searchItemGroupProposalTotalAmount.");
		return list;
	}

	private String searchItemGroupProposalTotalAmountQueryString(
			String boxInId, Integer invoiceId) {
		logger
				.info("Enter method searchItemGroupProposalTotalAmountQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{totalAmount:\"',format(if(item.amount is null,0,item.amount),2),");
		sb
				.append("'\",payment:\"',format((if(item.payment is null,0,item.payment) + (if(sum(p.payment_amount) is null,0,sum(p.payment_amount)))),2),");
		sb
				.append("'\",dispute:\"',format((if(item.dispute is null,0,item.dispute) + (if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)))),2),");
		sb
				.append("'\",notProposed:\"',format((if(item.amount is null,0,item.amount) - (if(item.payment is null,0,item.payment) + (if(sum(p.payment_amount) is null,0,sum(p.payment_amount)))) ");
		sb
				.append(" -(if(item.dispute is null,0,item.dispute) + (if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)))) ),2), ");
		sb.append("'\"}') a ");
		sb.append(" from proposal p ");
		sb
				.append(",(select sum(ii.item_amount) as amount,sum(ii.payment_sum) as payment,sum(ii.dispute_sum) as dispute from invoice_item ii where ii.rec_active_flag = 'Y' ");
		sb.append(" and ii.id in (" + boxInId + ")) as item ");
		sb.append(" where p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_item_id in (" + boxInId + ") ");
		logger
				.info("Enter method searchItemGroupProposalTotalAmountQueryString.");
		return sb.toString();
	}

	public List<String> searchItemProposalTotalAmount(String invoiceItemId,
			String invoiceItemInId) {
		logger.info("Enter method searchItemProposalTotalAmount.");
		final String queryString = searchItemProposalTotalAmountQueryString(
				invoiceItemId, invoiceItemInId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Enter method searchItemProposalTotalAmount.");
		return list;
	}

	private String searchItemProposalTotalAmountQueryString(
			String invoiceItemId, String invoiceItemInId) {
		logger.info("Enter method searchItemProposalTotalAmountQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{totalAmount:\"',format(if(item.amount is null,0,item.amount),2),");
		sb
				.append("'\",payment:\"',format((if(item.payment is null,0,item.payment) + (if(sum(p.payment_amount) is null,0,sum(p.payment_amount)))),2),");
		sb
				.append("'\",dispute:\"',format((if(item.dispute is null,0,item.dispute) + (if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)))),2),");
		sb
				.append("'\",notProposed:\"',format((if(item.amount is null,0,item.amount) - (if(item.payment is null,0,item.payment) + (if(sum(p.payment_amount) is null,0,sum(p.payment_amount)))) ");
		sb
				.append(" -(if(item.dispute is null,0,item.dispute) + (if(sum(p.dispute_amount) is null,0,sum(p.dispute_amount)))) ),2), ");
		sb.append("'\"}') a ");
		sb.append(" from proposal p ");
		sb
				.append(",(select ii.item_amount as amount,ii.payment_sum as payment,ii.dispute_sum as dispute from invoice_item ii where ii.rec_active_flag = 'Y' ");
		sb.append(" and ii.id = " + invoiceItemId + ") as item ");
		sb.append(" where p.rec_active_flag = 'Y' ");
		sb.append(" and p.invoice_item_id = " + invoiceItemId + " ");
		logger.info("Enter method searchItemProposalTotalAmountQueryString.");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saninco.ccm.dao.IInvoiceDetailDao#findInvoiceIdBybanId(int)
	 */
	public List<String> findInvoiceIdBybanId(Integer banId) {
		logger.info("Enter method findInvoiceIdBybanId.");
		final String sql = this.invoiceIdBybanIdQueryString(banId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> c = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method findInvoiceIdBybanId.");
		return c;
	}

	private String invoiceIdBybanIdQueryString(Integer banId) {
		logger.info("Enter method invoiceIdBybanIdQueryString.");
		StringBuffer sb = new StringBuffer(
				"select concat('{id:',i.id,',number:\"',if(i.invoice_number is null,'',i.invoice_number),");
		sb.append("'\",date:\"',if(i.invoice_date is null,'',i.invoice_date),");
		sb.append("'\"}') a ");
		sb.append("from invoice i ");
		sb.append(" where i.ban_id = " + banId + " ");
		sb.append(" and i.rec_active_flag = 'Y' ");
		sb.append(" and i.invoice_status_id >= 9 ");
		sb.append(" order by i.invoice_date ");
		logger.info("Exit method invoiceIdBybanIdQueryString.");
		return sb.toString();
	}

	/**
	 * save Proposal
	 */
	public Long saveProposal(Proposal transientInstance) {
		log.debug("saving Proposal instance");
		Long id = null;
		try {
			id = (Long) getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
		return id;
	}

	public void addTransactionHistory(TransactionHistory th,String openInterface,int workflowActionId,String workflowUserName,String notes) {
		logger.info("Enter method addTransactionHistory.");
		Session session = getSession();
 		StringBuffer sb = new StringBuffer("insert into transaction_history(invoice_id, invoice_status_id ,workflow_user_id,notes, created_by, workflow_user_name,attachment_point_id,workflow_action_id,created_timestamp, rec_active_flag) VALUES ("
				+th.getInvoice().getId()+",(select invoice_status_id from invoice where id = "+th.getInvoice().getId()+" ),");
		
		if(th.getUser()==null){
			sb.append("null,");
		}else{
			sb.append( th.getUser().getId()+ ",");
		}
		if(notes==null){
			notes = "";
		}
		sb.append(":notes,");
		
		if("Y".equals(openInterface)){
			sb.append(121);
		}else{
			sb.append(th.getUser().getId()); 
		}		
		if(workflowUserName==null){
			sb.append(",null");
		}else{
			sb.append(",'"+ workflowUserName+"'");
		}
		
		if(th.getAttachmentPoint()==null){
			sb.append(",null");
		}else{
			sb.append(",'"+ th.getAttachmentPoint().getId()+"'");
		}
		
		sb.append(","+workflowActionId+",now(),'Y')");
		
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
			sqlQuery.setParameter("notes", notes);
		    sqlQuery.executeUpdate();
			logger.info("Exit method addTransactionHistory.");
		} finally {
			releaseSession(session);
		}
	}
	
	public String[] addExternalEmail(String toEmail) {
		logger.info("Enter method addExternalEmail.");
		String toUser = "";
		String budgetId = "";
		String userId = "";
		userId = searchUserIdByEamil(toEmail);
		budgetId = externalEmailBudgetOwnerId(toEmail);
		toUser = externalEmailToUser(userId,budgetId,toEmail);
		toUser = "".equals(toUser)? toEmail : toUser;
		
		final String queryString = "insert into external_email (budget_id, user_id, to_user, to_email, confirm_flag, created_timestamp, created_by, modified_timestamp, modified_by, rec_active_flag) VALUES("+budgetId+", "+userId+", '"+toUser+"', '"+toEmail+"', 'N', now(), "+SystemUtil.getCurrentUserId()+", now(), "+SystemUtil.getCurrentUserId()+", 'Y')";
		Session session = getSession();
		try {
			session.createSQLQuery(queryString.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		
		HibernateTemplate template = this.getHibernateTemplate();
		String externalEmailId = template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select last_insert_id();").uniqueResult();
			}
		}).toString();
		
		String[] strings = new String[]{externalEmailId,toUser};
		
		logger.info("Exit method addExternalEmail.");
		return strings;
	}
	
	
    public String externalEmailToUser(String userId,String budgetId,String toEmail) {
		logger.info("Enter method externalEmailToUser.");
		final String queryString = "select name from "+
		" (select budget_owner name from budget_owner where id = " + budgetId + " "+
        " union "+
        "select concat(u.first_name, ' ', u.last_name) name from user u where u.id = " + userId + " )a "+
        "limit 1";
		
		String username = "";

		Session session = getSession();
		try {
			username = (String)session.createSQLQuery(queryString).uniqueResult();
			if ( username == null){
				username = "";
			}
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method externalEmailToUser.");
		return username;
	}
    
    public String externalEmailBudgetOwnerId(String toEmail) {
		logger.info("Enter method externalEmailBudgetOwnerId.");
		final String queryString = "select id from budget_owner where budget_owner_email = '" + toEmail+"' order by rec_active_flag desc limit 1";	

		String externalEmailBudgetOwnerId  = "";
		Session session = getSession();
		try {
			externalEmailBudgetOwnerId = String.valueOf(session.createSQLQuery(queryString).uniqueResult());
		} finally {
			releaseSession(session);
		}

		logger.info("Exit method externalEmailBudgetOwnerId.");
		return externalEmailBudgetOwnerId;
	}
	
    public String searchUserIdByEamil(String toEmail) {
    	logger.info("Enter method searchUserIdByEamil.");
    	final String queryString = "select id from user where email = '" + toEmail+"' order by rec_active_flag desc limit 1";	
    	
    	String userId  = "";
    	Session session = getSession();
    	try {
    		userId = String.valueOf(session.createSQLQuery(queryString).uniqueResult());
    	} finally {
    		releaseSession(session);
    	}
    	
    	logger.info("Exit method searchUserIdByEamil.");
    	return userId;
    }
    
	
	
	public void updateExternalEmail(String id) {
		logger.info("Enter method updateExternalEmail.");

		StringBuffer sb = new StringBuffer("update external_email  set confirm_flag = 'Y', modified_timestamp = now() where id =");
		sb.append(id);
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateExternalEmail.");
	}
	
	public void updateBanAutoPay(Integer id){
		logger.info("Enter method updateBanAutoPay.");

		StringBuffer sb = new StringBuffer("UPDATE ban b SET b.autopay_count = 0, b.modified_by = "+SystemUtil.getCurrentUserId()+",b.modified_timestamp= now() where b.id = " + id);
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateBanAutoPay.");
	}
	
	public String getExternalEmailUser(String id) {
		logger.info("Enter method getExternalEmailUser.");

		final String queryString = "select ifnull(to_user,'') from external_email where id = " + id+";";	
    	
    	String toUser  = "";
    	Session session = getSession();
    	try {
    		toUser = String.valueOf(session.createSQLQuery(queryString).uniqueResult());
    	} finally {
    		releaseSession(session);
    	}
    	
		logger.info("Exit method getExternalEmailUser.");
		
		return toUser;
	}
	
	/**
	 * find User By Id
	 */
	public User findUserById(Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"com.saninco.ccm.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find DisputeReason By Id
	 */
	public DisputeReason findDisputeReasonById(Integer id) {
		log.debug("getting DisputeReason instance with id: " + id);
		try {
			DisputeReason instance = (DisputeReason) getHibernateTemplate()
					.get("com.saninco.ccm.model.DisputeReason", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find Proposal By Id
	 */
	public Proposal findProposalById(Long id) {
		log.debug("getting Proposal instance with id: " + id);
		try {
			Proposal instance = (Proposal) getHibernateTemplate().get(
					"com.saninco.ccm.model.Proposal", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public InvoiceStatus loadInvoiceStatusById(Integer id) {
		log.debug("loading InvoiceStatus instance with id: " + id);
		try {
			InvoiceStatus instance = (InvoiceStatus) getHibernateTemplate().load(
					InvoiceStatus.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("load failed", re);
			throw re;
		}
	}

	/**
	 * find AccountCode By Id
	 */
	public AccountCode findAccountCodeById(Integer id) {
		log.debug("getting AccountCode instance with id: " + id);
		try {
			AccountCode instance = (AccountCode) getHibernateTemplate().get(
					"com.saninco.ccm.model.AccountCode", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Originator findOriginatorById(Integer id) {
		log.debug("getting Originator instance with id: " + id);
		try {
			Originator instance = (Originator) getHibernateTemplate().get(
					"com.saninco.ccm.model.Originator", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find InvoiceLabel By Id
	 */
	public InvoiceLabel findInvoiceLabelById(Integer id) {
		log.debug("getting InvoiceLabel instance with id: " + id);
		try {
			InvoiceLabel instance = (InvoiceLabel) getHibernateTemplate().get(
					"com.saninco.ccm.model.InvoiceLabel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find InvoiceItem By Id
	 */
	public InvoiceItem findInvoiceItemById(Integer id) {
		log.debug("getting InvoiceItem instance with id: " + id);
		try {
			InvoiceItem instance = (InvoiceItem) getHibernateTemplate().get(
					"com.saninco.ccm.model.InvoiceItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * save InvoiceLabel
	 */
	public void saveInvoiceLabel(InvoiceLabel transientInstance) {
		log.debug("saving InvoiceLabel instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * find Label By Id
	 */
	public Label findLabelById(Integer id) {
		log.debug("getting Label instance with id: " + id);
		try {
			Label instance = (Label) getHibernateTemplate().get(
					"com.saninco.ccm.model.Label", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find Invoice By Id
	 */
	public Invoice findInvoiceById(Integer id) {
		log.debug("getting Invoice instance with id: " + id);
		try {
			Invoice instance = (Invoice) getHibernateTemplate().get(
					"com.saninco.ccm.model.Invoice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Object[] findbudgetOwnerEmail(Integer id) {
		logger.info("Enter method findbudgetOwnerEmail.");
		Session session = getSession();
		Object[] budgetOwner = new Object[3];
		budgetOwner[0] = "";
		budgetOwner[1] = "";
		try {
			List<Object[]> re = (List<Object[]>)session.createSQLQuery("SELECT b.budget_owner,ifnull(b.budget_owner_email,'') , count(1) FROM account_code a, proposal p, budget b WHERE     a.id = p.account_code_id AND a.budget_id = b.id AND p.invoice_id = "+id+" AND b.budget_owner is not null GROUP BY b.budget_owner order by count(1) desc ").list();
			for (int i = 0; i<re.size();i++) {
				budgetOwner[0] = re.get(i)[0];
				budgetOwner[1] = re.get(i)[1];
				if( !"".equals(budgetOwner[1]) && budgetOwner[1] != null) {
					break;
				}
			}
			logger.info("Exit method findbudgetOwnerEmail.");
			return budgetOwner;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author Audit Invoice
	 */
	public void auditInvoice(Integer id) throws SQLException{
		logger.info("Enter method auditInvoice.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_AUDIT_INVOICE(?)");
			proc.setInt(1, id);
			proc.execute();
			logger.info("Exit method auditInvoice.");
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author Audit Invoice
	 */
	public void recodeInvoiceSCOA(Integer id) throws SQLException{
		logger.info("Enter method recodeInvoiceSCOA.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_INVOICE_SCOA_CODE(?)");
			proc.setInt(1, id);
			proc.execute();
			logger.info("Exit method recodeInvoiceSCOA.");
		} finally {
			releaseSession(session);
		}
	}
	

	/**
	 * @author pengfei.wang Original query table
	 */
	public List<String> searchOriginal(SearchInvoiceVO svo) {
		logger.info("Enter method searchOriginal.");
		final String queryString = getFindAllSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Exit method searchOriginal.");
		return list;
	}

	/**
	 * @author pengfei.wang Original queryString
	 * 
	 */
	private String getFindAllSqlString(SearchInvoiceVO svo) {
		logger.info("Enter method getFindAllSqlString.");

		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',original.id,'\", ");
		sb
				.append("file_name:\"',toJSON(original.file_name is null,'',original.file_name),'\", ");
		sb
				.append("file_path:\"',toJSON(original.file_path is null,'',original.file_path),'\", ");
		sb
				.append("created_timestamp:\"',toJSON(original.created_timestamp is null,'',original.created_timestamp ),'\", ");
		sb
				.append("created_by:\"',toJSON(`user`.user_name is null,'',`user`.user_name),'\", ");
		sb
				.append("modified_timestamp:\"',toJSON(original.modified_timestamp is null,'',original.modified_timestamp),'\" ");
		sb.append("}')  from original,`user`  ");
		sb.append("where `user`.id= original.created_by  ");
		sb.append(" and `user`.rec_active_flag =  'Y'  ");
		sb.append(" and (original.invoice_id =" + svo.getInvoiceNumber()
				+ " or original.bar_code=" + svo.getBarCode() + ") ");
		sb.append(" and `user`.active_flag =  'Y'  ");
		sb.append(" and original.rec_active_flag = 'Y' ");
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		sb.append(" order by " + svo.getSortField() + " "
				+ svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		logger.info("Exit method getFindAllSqlString.");
		return sb.toString();
	}

	/**
	 * @author pengfei.wang Original list. The paging
	 */
	public long getOriginalTotalNO(SearchInvoiceVO svo) {
		logger.info("Enter method getOriginalTotalNO.");
		final String sql = getOriginalTotalNOString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getOriginalTotalNO.");
		return count;
	}

	/**
	 * @author pengfei.wang The paging quertString
	 */
	private String getOriginalTotalNOString(SearchInvoiceVO svo) {
		logger.info("Enter method getOriginalTotalNOString.");
		StringBuffer sb = new StringBuffer(
				"select count(1) from original,`user` ");
		sb.append("where `user`.rec_active_flag =  'Y' ");
		sb.append(" and `user`.active_flag =  'Y'  ");
		sb.append(" and original.rec_active_flag = 'Y' ");
		sb.append(" and (original.invoice_id =" + svo.getInvoiceNumber()
				+ " or original.bar_code=" + svo.getBarCode() + ") ");
		sb.append(" and `user`.id= original.created_by  ");
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		logger.info("Exit method getOriginalTotalNOString.");
		return sb.toString();
	}

	/**
	 * @author pengfei.wang Tariff list. The paging
	 */
	public long getTariffTotalNO(SearchInvoiceVO svo) {
		logger.info("Enter method getTariffTotalNO.");
		final String sql = getTariffTotalNOString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getTariffTotalNO.");
		return count;
	}

	/**
	 * @author pengfei.wang The paging quertString
	 */
	private String getTariffTotalNOString(SearchInvoiceVO svo) {
		logger.info("Enter method getTariffSqlString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(searchTariffWhereConditions(svo));
//		StringBuffer sb = new StringBuffer("select count(1) from tariff_link,`user`  ");
//		sb.append("where `user`.rec_active_flag =  'Y' ");
//		sb.append(" and `user`.id= tariff_link.created_by  ");
//		sb.append(" and `user`.active_flag =  'Y'  ");
//		sb.append(" and tariff_link.ban_id in (select b1.id from ban b1,ban b2 where b1.vendor_id = b2.vendor_id and b1.rec_active_flag = 'Y' and b1.master_ban_flag = 'Y' and b1.ban_status_id = 1 and b2.id = " + svo.getBanId() + ") ");
//		sb.append(" and tariff_link.rec_active_flag = 'Y' ");
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		logger.info("Exit method getTariffSqlString.");
		return sb.toString();
	}

	/**
	 * find InvoiceItem By Id
	 */
	public InvoiceItem findInvoiceItemById(Long id) {
		log.debug("getting InvoiceItem instance with id: " + id);
		try {
			InvoiceItem instance = (InvoiceItem) getHibernateTemplate().get(
					"com.saninco.ccm.model.InvoiceItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @author pengfei.wang search Tariff
	 */
	public List<String> searchTariff(SearchInvoiceVO svo) {
		logger.info("Enter method searchTariff.");
		final String queryString = getTariffSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Exit method searchTariff.");
		return list;
	}

	/**
	 * @author pengfei.wang Original queryString
	 */
	private String getTariffSqlString(SearchInvoiceVO svo) {
		logger.info("Enter method getTariffSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',tariff_link.id,'\", ");
		sb
				.append("tariff_name:\"',toJSON(tariff_link.tariff_name is null,'',tariff_link.tariff_name),'\", ");
		sb
				.append("tariff_path:\"',toJSON(tariff_link.tariff_path is null,'',tariff_link.tariff_path),'\", ");
		sb
				.append("created_timestamp:\"',toJSON(tariff_link.created_timestamp is null,'',tariff_link.created_timestamp ),'\", ");
//		sb
//				.append("created_by:\"',toJSON(`user`.user_name is null,'',`user`.user_name),'\", ");
		sb
				.append("modified_timestamp:\"',toJSON(tariff_link.modified_timestamp is null,'',tariff_link.modified_timestamp),'\", ");
		sb
				.append("notes:\"',toJSON(tariff_link.notes is null,'',tariff_link.notes),'\" ");
		sb.append("}') ");
		sb.append(searchTariffWhereConditions(svo));
		
//		sb.append("}')  from tariff_link,`user` ");
//		sb.append("where tariff_link.ban_id in (select b1.id from ban b1,ban b2 where b1.vendor_id = b2.vendor_id and b1.rec_active_flag = 'Y' and b1.master_ban_flag = 'Y' and b1.ban_status_id = 1 and b2.id = " + svo.getBanId() + ") ");
//		sb.append(" and tariff_link.rec_active_flag = 'Y' ");
//		sb.append(" and `user`.id= tariff_link.created_by  ");
//		sb.append(" and `user`.active_flag =  'Y'  ");
//		sb.append(" and `user`.rec_active_flag =  'Y'  ");
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		sb.append(" order by " + svo.getSortField() + " "
				+ svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		logger.info("Exit method getTariffSqlString.");
		return sb.toString();
	}

	private String searchTariffWhereConditions (SearchInvoiceVO svo){
		logger.info("Enter method searchTariffWhereConditions.");
		StringBuffer sb = new StringBuffer(" from tariff_link ");
		sb.append(" where tariff_link.rec_active_flag = 'Y' ");
		sb.append(" and (tariff_link.ban_id = "+ svo.getBanId() + " or tariff_link.vendor_id = (select v.id from ban b , vendor v where b.vendor_id = v.id and b.rec_active_flag = 'Y' and b.master_ban_flag = 'Y' and v.rec_active_flag = 'Y' and b.id = "+ svo.getBanId() + "))");
		return sb.toString();
			
	}
	/**
	 * @author wei.su According to the records of invoiceId all dispute
	 */
	public long getDisputeTotalNOByInvoiceId(SearchInvoiceVO svo, int invoiceId) {
		logger.info("Enter method getDisputeTotalNOByInvoiceId.");
		StringBuffer sb = new StringBuffer();
		sb
				.append("  SELECT count(1)FROM proposal ,dispute ,dispute_status,invoice WHERE ");
		if (svo.getFilter() != null) {
			sb.append(svo.getFilter());
			sb.append(" and ");
		}
		sb
				.append("proposal.dispute_id IS NOT NULL  AND proposal.dispute_id =  dispute.id AND dispute.dispute_status_id =  dispute_status.id");
		sb.append(" and invoice.id=");
		sb.append(invoiceId);
		final String sql = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getDisputeTotalNOByInvoiceId.");
		return count;
	}

	/**
	 * @author pengfei.wang PaymentTab pagination inquiry statistics
	 */
	public long getPaymenttablaTotalNO(SearchInvoiceVO svo) {
		logger.info("Enter method getPaymenttablaTotalNO.");
		final String sql = getPaymenttablaTotalNOString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getPaymenttablaTotalNO.");
		return count;
	}

	/**
	 * @author pengfei.wang The paging quertString
	 */
	private String getPaymenttablaTotalNOString(SearchInvoiceVO svo) {
		logger.info("Enter method getPaymenttablaTotalNOString.");
		StringBuffer sb = new StringBuffer(
				"select count(1) from proposal ");
		//wenbo.zhang
		//2016-07-29
		sb.append("left join account_code a ON a.id= proposal.account_code_id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append("where proposal.invoice_id =" + svo.getInvoiceId() + " ");
		sb.append(" and proposal.invoice_item_id is NULL  ");
		sb.append(" and proposal.payment_amount!=0 ");
		sb.append(" and proposal.payment_amount is not null ");
		sb.append(" and proposal.proposal_flag = '1' ");
		sb.append(" and proposal.rec_active_flag = 'Y' ");
		logger.info("Exit method getPaymenttablaTotalNOString.");
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	/**
	 * @author wei.su According to invoiceId get all the dispute to set back and
	 */
	public List<String> searchDisputesByInvoiceId(SearchInvoiceVO svo,
			int invoiceId) {
		logger.info("Enter method searchDisputesByInvoiceId.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',dispute.id,'\", ");
		sb
				.append("dispute_number:\"',if(dispute.dispute_number is null,'',dispute.dispute_number),'\", ");
		sb
				.append("dispute_amount:\"',if(proposal.dispute_amount is null,'',proposal.dispute_amount ),'\", ");
		sb
				.append("dispute_status_name:\"',if(dispute_status.dispute_status_name is null,'',dispute_status.dispute_status_name),'\" ");
		sb.append("}')  from proposal ,dispute ,dispute_status,invoice ");
		sb.append("where invoice.id=" + invoiceId);
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		sb
				.append(" and  proposal.dispute_id IS NOT NULL  AND proposal.dispute_id =  dispute.id AND dispute.dispute_status_id =  dispute_status.id");
		sb.append(" order by " + svo.getSortField() + " "
				+ svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Exit method searchDisputesByInvoiceId.");
		return list;

	}

	/**
	 * @author pengfei.wang PaymentTab Search
	 */
	public List<String> searchPaymenttabla(SearchInvoiceVO svo) {
		logger.info("Enter method searchPaymenttabla.");
		final String queryString = getPaymenttablaSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Exit method searchPaymenttabla.");
		return list;
	}

	/**
	 * @author pengfei.wang PaymentTab queryString
	 */
	private String getPaymenttablaSqlString(SearchInvoiceVO svo) {
		logger.info("Enter method getPaymenttablaSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',proposal.id,'\", ");
		sb.append("account_code_id:\"',toJSON(a.id is null,'',a.id),'\", ");
		sb
				.append("account_code_name:\"',toJSON(a.account_code_name is null,'',a.account_code_name),'\", ");
		sb
				.append("payment_amount:\"',toJSON(proposal.payment_amount is null,'',format(proposal.payment_amount,2)),'\", ");
		sb
				.append("creditAmount:\"',toJSON(proposal.credit_amount is null,'',proposal.credit_amount ),'\", ");
		sb
				.append("proposal_flag:\"',toJSON(proposal.proposal_flag is null,'',proposal.proposal_flag),'\", ");
		sb
				.append("attachment_point_id_down_payment:\"',toJSON(proposal.attachment_point_id is null,'',proposal.attachment_point_id),'\", ");
		sb
				.append("description:\"',toJSON(proposal.notes is null,'',proposal.notes),'\" ");
		sb.append("}')  from proposal ");
		
		//wenbo.zhang
		//2016-07-29
		sb.append("left join account_code a ON a.id= proposal.account_code_id");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" where proposal.invoice_id =" + svo.getInvoiceId() + " ");
		sb.append(" and proposal.invoice_item_id is NULL   ");
		sb.append(" and proposal.payment_amount!=0 ");
		sb.append(" and proposal.payment_amount is not null ");
		sb.append(" and proposal.proposal_flag = '1'  ");
		sb.append(" and proposal.rec_active_flag = 'Y' ");
		sb.append(" order by " + svo.getSortField() + " "
				+ svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		logger.info("Exit method getPaymenttablaSqlString.");
		return sb.toString();
	}

	public List getMiscPaymentObject(SearchInvoiceVO svo) {
		logger.info("Enter method getManuallyCreatedPaymentObject.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" a.account_code_name , ");
		sb.append(" format(p.payment_amount,2) , ");
		sb.append(" format(p.credit_amount,2) , ");
		sb.append(" p.notes  ");
		sb.append(" from proposal p, account_code a ");
		sb.append("where p.invoice_id =" + svo.getInvoiceId() + " ");
		sb.append(" and p.invoice_item_id is NULL   ");
		sb.append(" and p.proposal_flag = '1'  ");
		sb.append(" and p.rec_active_flag = 'Y' ");
		sb.append(" and a.id= p.account_code_id  ");
		sb.append(" and a.rec_active_flag =  'Y'  ");
		sb.append(" and p.payment_amount!=0 ");
		sb.append(" and p.payment_amount is not null ");

		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getManuallyCreatedPaymentObject.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * 
	 */
	public long getMiscTotalNO(SearchInvoiceVO svo) {
		logger.info("Enter method getMiscTotalNO.");
		final String sql = getMiscTotalNOString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getMiscTotalNO.");
		return count;
	}

	/**
	 * @author pengfei.wang The paging quertString
	 */
	private String getMiscTotalNOString(SearchInvoiceVO svo) {
		logger.info("Enter method getMiscTotalNOString.");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		StringBuffer sb = new StringBuffer("select count(1)");
		sb
				.append(" FROM (((((reconcile r left join payment p on(r.payment_id=p.id))"
						+ " left join dispute d on(r.dispute_id=d.id))"
						+ " left join invoice i on(d.invoice_id=i.id))"
						+ " left join user u on(r.created_by=u.id))"
						+ " left join account_code a on(r.account_code_id=a.id ");
		if (invoiceStatusId < 21) {
			sb.append(" and a.active_flag = 'Y'");
			sb.append(" and a.rec_active_flag = 'Y'");
        }
        sb.append(" )) ");
		sb
				.append(" WHERE r.dispute_id is not null and r.reconcile_status_id=7 AND r.rec_active_flag='Y' AND i.ban_id="
						+ svo.getBanId());
		if (invoiceStatusId < 21) {
			sb
					.append(" and ((p.invoice_id="
							+ svo.getInvoiceId1()
							+ ") or (r.payment_id is null and (r.payment_invoice_id is NULL or r.payment_invoice_id="
							+ svo.getInvoiceId1() + ")))");
		} else {
			sb.append(" and ((p.invoice_id=" + svo.getInvoiceId1()
					+ ") or (r.payment_id is null and r.payment_invoice_id="
					+ svo.getInvoiceId1() + "))");
		}
		logger.info("Exit method getMiscTotalNOString.");
		return sb.toString();
	}

	/**
	 * 
	 */
	public List<String> searchMisc(SearchInvoiceVO svo) {
		logger.info("Enter method searchMisc.");
		final String queryString = getMiscSqlString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Exit method searchMisc.");
		return list;
	}

	/**
	 * @author pengfei.wang PaymentTab queryString
	 */
	private String getMiscSqlString(SearchInvoiceVO svo) {
		logger.info("Enter method getMiscSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',reconcile.id,'\", ");
		sb
				.append("account_code_id:\"',toJSON(account_code.account_code_name is null,'',account_code.account_code_name),'\", ");
		sb
				.append("reconcile_amount:\"',toJSON(reconcile.reconcile_amount is null,'',format(reconcile.reconcile_amount,2) ),'\", ");
		sb
				.append("reconcile_date:\"',toJSON(reconcile.reconcile_date is null,'',reconcile.reconcile_date ),'\", ");
		sb
				.append("attachment_point_id_down:\"',toJSON(reconcile.attachment_point_id is null,'',reconcile.attachment_point_id ),'\", ");
		sb
				.append("created_by:\"',toJSON(`user`.first_name is null,'',`user`.first_name),' ',toJSON(`user`.last_name is null,'',`user`.last_name),'\", ");
		sb
				.append("notes:\"',toJSON(reconcile.notes is null,'',reconcile.notes),'\" ");
		sb.append("}')");
		sb
				.append(" FROM (((((reconcile left join payment on(reconcile.payment_id=payment.id))"
						+ " left join dispute on(reconcile.dispute_id=dispute.id))"
						+ " left join invoice on(dispute.invoice_id=invoice.id))"
						+ " left join user on(reconcile.created_by=user.id))"
						+ " left join account_code on(reconcile.account_code_id=account_code.id ");
		//wenbo.zhang
		//2016-07-29
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and account_code.active_flag = 'Y'");
					sb.append(" and account_code.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
		sb
				.append(" WHERE reconcile.dispute_id is not null and reconcile.reconcile_status_id=7 AND reconcile.rec_active_flag='Y' AND invoice.ban_id="
						+ svo.getBanId());

		if (invoiceStatusId < 21) {
			sb
					.append(" and ((payment.invoice_id="
							+ svo.getInvoiceId1()
							+ ") or (reconcile.payment_id is null and (reconcile.payment_invoice_id is NULL or reconcile.payment_invoice_id="
							+ svo.getInvoiceId1() + ")))");
		} else {
			sb
					.append(" and ((payment.invoice_id="
							+ svo.getInvoiceId1()
							+ ") or (reconcile.payment_id is null and reconcile.payment_invoice_id="
							+ svo.getInvoiceId1() + "))");
		}
		sb.append(" order by " + svo.getSortField() + " "
				+ svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		logger.info("Exit method getMiscSqlString.");
		return sb.toString();
	}

	/**
	 * @author Jian.Dong
	 */
	public String getTotalPayment(Integer invoiceId, Integer banId) {
		String tableName = createTotalPaymentTmpTable(invoiceId, banId);
		StringBuilder sb = new StringBuilder(
				"SELECT if(sum(tmp.payment_amount) is null,'0.00',format(sum(tmp.payment_amount),2)) ");
		sb.append(" from (");
		sb
				.append(" select round(sum(t.payment_amount),2) payment_amount from ");
		sb.append(tableName);
		sb.append(" t group by t.account_code_id");
		sb.append(") tmp");
		Session session = getSession();
		try {
			String result = ((String) session.createSQLQuery(sb.toString())
					.uniqueResult());
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getTotalDisputePayment(Integer invoiceId) {
		String paid = this.getTotalPaidDisputePayment(invoiceId);
		String shortPaid = this.getTotalShortPaidDisputePayment(invoiceId);

		Double total = 0d;
		try {
			total = CcmFormat.paseCurrency(paid).doubleValue()
					+ CcmFormat.paseCurrency(shortPaid).doubleValue();
		} catch (ParseException e) {
			logger.error(e);
		}

		return CcmFormat.formatCurrency(total);
	}

	/**
	 * @author Jian.Dong
	 */
	public String getTotalPaidDisputePayment(Integer invoiceId) {
		StringBuffer sb = new StringBuffer(
				"select if(sum(tmp.dispute_amount) is null,'0.00',format(sum(tmp.dispute_amount),2)) FROM (");
		sb.append(" SELECT round(sum(p.dispute_amount),2) dispute_amount ");
		sb.append(" FROM proposal p, dispute d ");
		sb.append(" WHERE p.dispute_id = d.id ");
		sb.append(" AND d.flag_shortpaid='N' ");
		sb.append(" AND p.invoice_item_id is not NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + invoiceId);
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String result = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getTotalShortPaidDisputePayment(Integer invoiceId) {
		StringBuffer sb = new StringBuffer(
				"select if(sum(tmp.dispute_amount) is null,'0.00',format(sum(tmp.dispute_amount),2)) FROM (");
		sb.append(" SELECT round(sum(p.dispute_amount),2) dispute_amount ");
		sb.append(" FROM proposal p, dispute d ");
		sb.append(" WHERE p.dispute_id=d.id ");
		sb.append(" AND d.flag_shortpaid='Y' ");
		sb.append(" AND p.invoice_item_id is NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + invoiceId);
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String result = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getTotalDispute(Integer invoiceId) {
		Session session = getSession();
		try {
			String result = ((String) session
					.createSQLQuery(
							"select if(sum(tmp.dispute_amount) is null,'0.00',format(sum(tmp.dispute_amount),2)) "
							+ "(SELECT round(sum(d.dispute_amount),2) from dispute d where d.invoice_id="
							+ invoiceId
							+ " and d.rec_active_flag='Y' and d.flag_shortpaid in ('Y','N') AND d.dispute_amount!=0 AND d.dispute_amount is not null GROUP BY p.account_code_id) tmp")
							.uniqueResult());
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getTotalPaidDispute(Integer invoiceId) {
		// String result = ((String) getSession().createSQLQuery("select
		// if(sum(d.dispute_amount) is
		// null,'0.00',format(sum(d.dispute_amount),2)) " +
		// "from dispute d where d.invoice_id=" + invoiceId + " " +
		// "and d.rec_active_flag='Y' and d.flag_shortpaid='N' AND
		// d.dispute_amount!=0 " +
		// "AND d.dispute_amount is not null").uniqueResult());
		//
		// return result;
		StringBuffer sb = new StringBuffer(
				"select if(sum(tmp.dispute_amount) is null,'0.00',format(sum(tmp.dispute_amount),2)) FROM (");
		sb.append(" select round(sum(p.dispute_amount),2) dispute_amount ");
		sb.append(" FROM proposal p, dispute d ");
		sb.append(" WHERE p.dispute_id = d.id ");
		sb.append(" AND d.flag_shortpaid='N' ");
		sb.append(" AND p.invoice_item_id is not NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + invoiceId);
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String result = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getTotalShortPaidDispute(Integer invoiceId) {
		// String result = ((String) getSession().createSQLQuery("select
		// if(sum(d.dispute_amount) is
		// null,'0.00',format(sum(d.dispute_amount),2)) " +
		// "from dispute d where d.invoice_id=" + invoiceId + " and
		// d.rec_active_flag='Y' and d.flag_shortpaid='Y' " +
		// "AND d.dispute_amount!=0 AND d.dispute_amount is not
		// null").uniqueResult());
		// return result;//
		StringBuffer sb = new StringBuffer(
				"select if(sum(tmp.dispute_amount) is null,'0.00',format(sum(tmp.dispute_amount),2)) from (");
		sb.append(" select round(sum(p.dispute_amount),2) dispute_amount ");
		sb.append(" FROM proposal p, dispute d ");
		sb.append(" WHERE p.dispute_id=d.id ");
		sb.append(" AND d.flag_shortpaid='Y' ");
		sb.append(" AND p.invoice_item_id is not NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + invoiceId);
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String result = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getPaidforPrevious(Integer invoiceId) {
		StringBuffer sb = new StringBuffer(
				"select if(sum(tmp.dispute_amount) is null,'0.00',format(sum(tmp.dispute_amount),2)) FROM (");
		sb.append(" select round(sum(p.dispute_amount),2) dispute_amount ");
		sb.append(" FROM proposal p, dispute d ");
		sb.append(" WHERE p.dispute_id = d.id ");
		sb.append(" AND d.flag_shortpaid='N' ");
		sb.append(" AND p.invoice_item_id is NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + invoiceId);
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String result = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getshortPaidforPrevious(Integer invoiceId) {
		StringBuffer sb = new StringBuffer(
				"select if(sum(tmp.dispute_amount) is null,'0.00',format(sum(tmp.dispute_amount),2)) FROM (");
		sb.append(" select round(sum(p.dispute_amount),2) dispute_amount ");
		sb.append(" FROM proposal p, dispute d ");
		sb.append(" WHERE p.dispute_id=d.id ");
		sb.append(" AND d.flag_shortpaid='Y' ");
		sb.append(" AND p.invoice_item_id is NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + invoiceId);
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String result = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			return result;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List getPaymentScoaAmount(SearchInvoiceVO svo) {
		logger.info("Enter method getPaymentScoaAmount.");
		String tableName = createTotalPaymentTmpTable(svo.getInvoiceId1(), svo
				.getBanId1());
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("scoa:\"',toJSON(s.account_code_name is null,'',s.account_code_name),'\", ");
		sb
				.append("scoaDescription:\"',toJSON(s.account_code_description is null,'',s.account_code_description),'\", ");
		sb
				.append("totalPayment:\"',toJSON(sum(s.payment_amount) is null,'',format(sum(s.payment_amount),2)),'\"}')");
		sb.append(" from " + tableName + " s");
		sb.append(" group by s.account_code_id ");
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getPaymentScoaAmount.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	private String createTotalPaymentTmpTable(Integer invoiceId, Integer banId) {
		String tableName = "search_total_amount_invoice_" + invoiceId;
		Session session = getSession();
		try {
			session.connection().setReadOnly(false);
			session.createSQLQuery("drop table if EXISTS " + tableName)
					.executeUpdate();
			StringBuffer sql_14 = new StringBuffer();
			StringBuffer sql_23_1 = new StringBuffer();
			StringBuffer sql_23_2 = new StringBuffer();
			StringBuffer sql_5 = new StringBuffer();
			StringBuffer sql_6 = new StringBuffer();
			
			sql_14.append("SELECT '14' flag, p.account_code_id, a.account_code_name, a.account_code_description,  p.invoice_id,    null ban_id, p.payment_amount      payment_amount,      p.description,        null date1,             null created_by, p.attachment_point_id FROM ");
			sql_14.append(" proposal p ");
			sql_14.append(" left join account_code a ON a.id= p.account_code_id");
			Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(invoiceId);
			if (invoiceStatusId < 21) {
				sql_14.append(" and a.active_flag = 'Y'");
				sql_14.append(" and a.rec_active_flag = 'Y'");
			}	
			sql_14.append("  WHERE p.account_code_id=a.id AND p.payment_amount is not NULL AND p.payment_amount!=0 AND p.rec_active_flag='Y' AND p.proposal_flag='1' and p.invoice_id="
					+ invoiceId);
			
			
			sql_23_1.append(" SELECT '23' flag, p.account_code_id, a.account_code_name, a.account_code_description,  p.invoice_id,    null ban_id, p.dispute_amount      payment_amount,      p.description,        null date1,             null created_by, p.attachment_point_id FROM");
			sql_23_1.append(" dispute d,proposal p ");
			sql_23_1.append(" left join account_code a ON a.id= p.account_code_id");
			if (invoiceStatusId < 21) {
				sql_23_1.append(" and a.active_flag = 'Y'");
				sql_23_1.append(" and a.rec_active_flag = 'Y'");
			}
			sql_23_1.append(" WHERE p.dispute_id=d.id AND p.account_code_id=a.id  AND d.flag_shortpaid='N'  AND p.invoice_item_id is not NULL  AND p.dispute_amount is not NULL  AND p.dispute_amount!=0  AND p.rec_active_flag='Y'  AND p.proposal_flag='1' and p.invoice_id="
					+ invoiceId);

			
			
			sql_23_2.append("  SELECT '23' flag, p.account_code_id, a.account_code_name, a.account_code_description,  p.invoice_id,    null ban_id, p.dispute_amount*-1   payment_amount,      p.description,        null date1,             null created_by, p.attachment_point_id FROM ");
			sql_23_2.append(" dispute d,proposal p ");
			sql_23_2.append(" left join account_code a ON a.id= p.account_code_id");
			if (invoiceStatusId < 21) {
				sql_23_2.append(" and a.active_flag = 'Y'");
				sql_23_2.append(" and a.rec_active_flag = 'Y'");
			}
			sql_23_2.append(" WHERE p.dispute_id=d.id AND p.account_code_id=a.id  AND d.flag_shortpaid='Y'  AND p.invoice_item_id is NULL      AND p.dispute_amount is not NULL  AND p.dispute_amount!=0  AND p.rec_active_flag='Y'  AND p.proposal_flag='1' and p.invoice_id="
					+ invoiceId);

			
			sql_5.append(" SELECT '5'  flag, r.account_code_id, a.account_code_name, a.account_code_description,  d.invoice_id,    i.ban_id,    r.reconcile_amount    payment_amount,      r.notes description,  r.reconcile_date date1, r.created_by,    r.attachment_point_id ");
			sql_5.append(" FROM (((((reconcile r left join payment p on(r.payment_id=p.id)) left join dispute d on(r.dispute_id=d.id)) left join invoice i on(d.invoice_id=i.id)) left join user u on(r.created_by=u.id)) left join account_code a on(r.account_code_id=a.id ");
			if (invoiceStatusId < 21) {
				sql_5.append(" and a.active_flag = 'Y'");
				sql_5.append(" and a.rec_active_flag = 'Y'");
	        }
			sql_5.append(" )) ");
			sql_5.append(" WHERE r.dispute_id is not null and r.reconcile_status_id=7 AND r.rec_active_flag='Y' AND i.ban_id="
					+ banId);

			if (invoiceStatusId < 21) {
				sql_5.append(" and ((p.invoice_id="
						+ invoiceId
						+ ") or (r.payment_id is null and (r.payment_invoice_id is NULL or r.payment_invoice_id="
						+ invoiceId + ")))");

			} else {
				sql_5.append(" and ((p.invoice_id="
						+ invoiceId
						+ ") or (r.payment_id is null and r.payment_invoice_id="
						+ invoiceId + "))");
			}
			sql_6.append(" SELECT '6'  flag, r.account_code_id, a.account_code_name, a.account_code_description,  i.id invoice_id, i.ban_id,    r.reconcile_amount*-1 payment_amount,      r.notes description,  r.reconcile_date date1, r.created_by,    r.attachment_point_id FROM ");
			sql_6.append(" invoice i, user u, reconcile r ");
			sql_6.append(" left join account_code a ON r.account_code_id=a.id");
			if (invoiceStatusId < 21) {
				sql_6.append(" and a.active_flag = 'Y'");
				sql_6.append(" and a.rec_active_flag = 'Y'");
			}
			sql_6.append(" WHERE r.credit_invoice_id=i.id  AND r.created_by=u.id  AND r.reconcile_status_id in (1,3,4,6) AND r.rec_active_flag='Y' AND r.payment_id is NULL  and i.id="
					+ invoiceId);

			Query query = session.createSQLQuery(
					"CREATE TEMPORARY TABLE " + tableName + " as " + sql_14
							+ " union all " + sql_23_1 + " union all "
							+ sql_23_2 + " union all " + sql_5 + " union all "
							+ sql_6);
			query.executeUpdate();
			return tableName;
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getPaymentScoaAmountTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getPaymentScoaAmountTotalPageNo.");
		String tableName = createTotalPaymentTmpTable(svo.getInvoiceId1(), svo
				.getBanId1());
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(
					"select count(1) from (select count(1) from " + tableName
					+ " s group by s.account_code_id) t").uniqueResult();
			logger.info("Exit method getPaymentScoaAmountTotalPageNo.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List<Object[]> getPaymentScoaAmountToExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getPaymentScoaAmountToExcel.");
		String tableName = createTotalPaymentTmpTable(svo.getInvoiceId1(), svo
				.getBanId1());
		StringBuffer sb = new StringBuffer("select");
		sb.append(" if(s.account_code_name is null,'',s.account_code_name)");
		sb
				.append(",if(sum(s.payment_amount) is null,'',format(sum(s.payment_amount),2)) ");
		sb
				.append(",if(s.account_code_description is null,'',s.account_code_description)");
		sb.append(" from ");
		sb.append(tableName);
		sb.append(" s group by s.account_code_id ");
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getPaymentScoaAmountToExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * @author mingyang.li
	 */
	public long getInvoiceDetailByInvoiceNumberTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getInvoiceDetailByInvoiceNumberTotalPageNo.");
		final String sql = "select count(1) a "+searchInvoiceDetailListWhereConditions(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		
		logger.info("Exit method getInvoiceDetailByInvoiceNumberTotalPageNo.");
		return count;
	}

	public List<Object[]> getInvoiceDetailByInvoiceNumber(SearchInvoiceVO sio) {
		logger.info("Enter method getInvoiceDetailByInvoiceNumber.");
		final String sql = this.searchInvoiceDetailListByInvoiceNumber(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> c = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getInvoiceDetailByInvoiceNumber.");
		return c;
	}
	
	private String searchInvoiceDetailListByInvoiceNumber(SearchInvoiceVO sio){
		logger.info("Enter method searchInvoiceDetailListByInvoiceNumber.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("proposal_id:\"',p.id,'\", ");
		sb.append("invoice_item_id:\"',ii.id,'\", ");
		sb.append("item_type_id:\"',p.item_type_id,'\", ");
		sb.append("item_type_name:\"',toJSON(it.item_type_name is null,'',it.item_type_name),'\", ");
		sb.append("item_name:\"',toJSON(ii.item_name is null,'',ii.item_name),'\", ");
		sb.append("item_amount:\"',toJSON(ii.item_amount is null,'',format(ii.item_amount,2)),'\", ");
		sb.append("tax_code:\"',toJSON(tc.tax_code is null,'',tc.tax_code),'\", ");
		sb.append("payment_amount:\"',toJSON(p.payment_amount is null,'',format(p.payment_amount,2)),'\", ");
		sb.append("dispute_amount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\", ");
		sb.append("credit_amount:\"',toJSON(p.credit_amount is null,'',format(p.credit_amount,2)),'\", ");
		sb.append("account_code_id:\"',toJSON(p.account_code_id is null,'',p.account_code_id),'\",");
		sb.append("scoa:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\",");
		sb.append("service_type:\"',toJSON(p.service_type is null,'',p.service_type),'\",");
		sb.append("charge_type:\"',toJSON(p.charge_type is null,'',p.charge_type),'\", ");
		sb.append("notes:\"',toJSON(p.notes is null,'',p.notes),'\" ");
		sb.append("}')");
		sb.append(searchInvoiceDetailListWhereConditions(sio));
		sb.append(" order by " + sio.getSortField() + " "
				+ sio.getSortingDirection());
		sb.append(" LIMIT " + sio.getStartIndex() + "," + sio.getRecPerPage());
		logger.info("Exit method getInvoiceDetailByInvoiceNumber.");
		return sb.toString();
	}
	
	private String searchInvoiceDetailListWhereConditions(SearchInvoiceVO sio){
		
		StringBuffer sb = new StringBuffer(" from (((((proposal p left join invoice_item ii ");
		sb.append(" on p.invoice_item_id is not null and ii.id = p.invoice_item_id) ");
		sb.append(" left join invoice i on i.id = p.invoice_id ) left join tax_code tc on ii.tax_code_id = tc.id) left join account_code ac on ac.id = p.account_code_id) left join item_type it on it.id = p.item_type_id)");
		sb.append(" where p.proposal_flag = 1 and p.payment_amount <> 0 and p.rec_active_flag = 'Y' and i.invoice_number = '"+sio.getInvoiceNumber()+"' ");
		sb.append(" and f_get_root_item_type(p.item_type_id) in( 6,8,9 ) and (i.invoice_status_id between 9 and 15) and i.rec_active_flag = 'Y' and ii.rec_active_flag = 'Y' ");
		return sb.toString();
	}
	
	/**
	 * @author Jian.Dong
	 */
	public List getApplyingCreditPayment(SearchInvoiceVO svo) {
		logger.info("Enter method getApplyingCreditPayment.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("scoa:\"',toJSON(a.account_code_name is null,'',a.account_code_name),'\", ");
		sb
				.append("amount:\"',toJSON(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),'\", ");
		sb.append("description:\"',toJSON(r.notes is null,'',r.notes),'\", ");
		sb
				.append("date:\"',toJSON(r.reconcile_date is null,'',r.reconcile_date),'\", ");
		sb
				.append("createBy:\"',CONCAT(toJSON(u.first_name is null,'',u.first_name),' ',toJSON(u.last_name is null,'',u.last_name)),'\", ");
		sb
				.append("files:\"',toJSON(r.attachment_point_id  is null,'',r.attachment_point_id),'\"}')");
		sb.append(" FROM reconcile r ,user u, account_code a");
		sb.append(" WHERE r.rec_active_flag='Y' ");
		sb.append(" AND r.created_by=u.id AND r.account_code_id=a.id");
		sb
				.append(" AND r.reconcile_status_id in (1,3,4,6) AND r.payment_id is NULL");
		sb.append(" AND r.credit_invoice_id=" + svo.getInvoiceId1());
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getApplyingCreditPayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getApplyingCreditPaymentTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getApplyingCreditPaymentTotalPageNo.");
		StringBuffer sb = new StringBuffer("SELECT count(1) ");
		sb.append(" FROM reconcile r ");
		sb.append(" WHERE r.rec_active_flag='Y' ");
		sb
				.append(" AND r.reconcile_status_id in (1,3,4,6) AND r.payment_id is NULL");
		sb.append(" AND r.credit_invoice_id=" + svo.getInvoiceId1());
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getApplyingCreditPaymentTotalPageNo.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getApplyingCredit(SearchInvoiceVO svo) {
		logger.info("Enter method getApplyingCredit.");
		StringBuffer sb = new StringBuffer(
				"SELECT if(sum(tmp.reconcile_amount) is null,'0.00',format(sum(tmp.reconcile_amount),2)) from (");
		sb.append(" SELECT round(sum(r.reconcile_amount),2) reconcile_amount ");
		sb.append(" FROM reconcile r ");
		sb.append(" WHERE r.rec_active_flag='Y' ");
//		sb		update by mingyang.li 2012.08.07
//				.append(" AND r.reconcile_status_id in (1,3,4,6) AND r.payment_id is NULL");
		sb
				.append(" AND r.reconcile_status_id in (1,3,4,6)");
		sb.append(" AND r.credit_invoice_id=" + svo.getInvoiceId1());
		sb.append(" GROUP BY r.account_code_id) tmp");
		Session session = getSession();
		try {
			String bi = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getApplyingCredit.");
			return ((bi == null) ? "0.00" : bi.toString());
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List getCurrentInvoicePayment(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentInvoicePayment.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("itemType:\"',toJSON(it1.item_type_name is null,'',it1.item_type_name),'\", ");
		sb
				.append("scoa:\"',toJSON(a.account_code_name is null,'',a.account_code_name),'\", ");
		sb
				.append("amount:\"',toJSON(SUM(p.payment_amount) is null,'',format(SUM(p.payment_amount),2)),'\"}')");
		sb.append(getCurrentInvoiceWhereCause(svo));
		sb.append(" group by it1.item_type_name,a.account_code_name ");
		// sb.append(" order by it1.item_type_name asc,a.account_code_name asc,
		// ");
		sb.append(" order by  ");
		sb.append(svo.getOrderBy(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCurrentInvoicePayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	private String getCurrentInvoiceWhereCause(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM item_type it1,proposal p ");
		//wenbo.zhang
		//2016-07-29
		sb.append(" left join account_code a ON a.id= p.account_code_id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" WHERE p.invoice_item_id is not NULL ");
		sb.append(" AND p.item_type_id=it1.id ");
		sb.append(" AND p.payment_amount is not NULL ");
		sb.append(" AND p.payment_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + svo.getInvoiceId1());
		return sb.toString();
	}

	/**
	 * @author Jian.Dong
	 */
	public List getCurrentInvoicePaymentObject(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentInvoicePaymentObject.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" if(it1.item_type_name is null,'',it1.item_type_name)");
		sb.append(",if(a.account_code_name is null,'',a.account_code_name)");
		sb
				.append(",if(SUM(p.payment_amount) is null,'',format(SUM(p.payment_amount),2))");
		sb.append(getCurrentInvoiceWhereCause(svo));
		sb.append(" group by it1.item_type_name,a.account_code_name ");
		sb.append(" order by it1.item_type_name asc,a.account_code_name asc, ");
		sb.append(svo.getOrderBy(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCurrentInvoicePaymentObject.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getCurrentInvoicePaymentTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentInvoicePaymentTotalPageNo.");
		StringBuffer sb = new StringBuffer("select count(1) from (SELECT p.id ");
		sb.append(getCurrentInvoiceWhereCause(svo));
		sb.append(" group by it1.item_type_name,a.account_code_name ");
		sb
				.append(" order by it1.item_type_name asc,a.account_code_name asc ) hb");
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getCurrentInvoicePaymentTotalPageNo.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getCurrentInvoice(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentInvoice.");
		StringBuffer sb = new StringBuffer(
				"SELECT if(sum(tmp.payment_amount) is null,'0.00',format(sum(tmp.payment_amount),2)) FROM (");
		// Modify by xin.huang on 2013-05-03 Start (fix round bug)
		sb.append(" SELECT sum(p.payment_amount) payment_amount ");
	//	sb.append(" SELECT round(sum(p.payment_amount),2) payment_amount ");
		// Modify by xin.huang on 2013-05-03 End
		sb.append(" FROM item_type it1,proposal p ");
		//wenbo.zhang
		//2016-07-29
		sb.append(" left join account_code a ON a.id= p.account_code_id");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" WHERE p.invoice_item_id is not NULL ");
		sb.append(" AND p.item_type_id=it1.id ");
		sb.append(" AND p.payment_amount is not NULL ");
		sb.append(" AND p.payment_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + svo.getInvoiceId1());
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String bi = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getCurrentInvoice.");
			return ((bi == null) ? "0.00" : bi.toString());
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List getManuallyCreatedPayment(SearchInvoiceVO svo) {
		logger.info("Enter method getManuallyCreatedPayment.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("scoa:\"',toJSON(a.account_code_name is null,'',a.account_code_name),'\", ");
		sb
				.append("amount:\"',toJSON(p.payment_amount is null,'',format(p.payment_amount,2)),'\", ");
		sb.append("description:\"',toJSON(p.notes is null,'',p.notes),'\", ");
		sb
				.append("files:\"',toJSON(p.attachment_point_id  is null,'',p.attachment_point_id),'\"}')");
		sb.append(getManuallyCreatedWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getManuallyCreatedPayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List getManuallyCreatedPaymentObject(SearchInvoiceVO svo) {
		logger.info("Enter method getManuallyCreatedPaymentObject.");
		StringBuffer sb = new StringBuffer("select");
		sb.append(" if(a.account_code_name is null,'',a.account_code_name)");
		sb
				.append(",if(p.payment_amount is null,'',format(p.payment_amount,2))");
		sb.append(",if(p.notes is null,'',p.notes)");
		sb.append(getManuallyCreatedWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getManuallyCreatedPaymentObject.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getManuallyCreatedPaymentTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getManuallyCreatedPaymentTotalPageNo.");
		StringBuffer sb = new StringBuffer("SELECT count(1) ");
		sb.append(getManuallyCreatedWhereCause(svo));
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getManuallyCreatedPaymentTotalPageNo.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getManuallyCreated(SearchInvoiceVO svo) {
		logger.info("Enter method getManuallyCreated.");
		StringBuffer sb = new StringBuffer(
				"SELECT if(sum(tmp.payment_amount) is null,'0.00',format(sum(tmp.payment_amount),2)) FROM (");
		sb.append(" SELECT round(sum(p.payment_amount),2) payment_amount ");
		sb.append(" FROM proposal p");
		sb.append(" WHERE p.invoice_item_id is NULL ");
		sb.append(" AND p.payment_amount is not NULL ");
		sb.append(" AND p.payment_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + svo.getInvoiceId1());
		sb.append(" GROUP BY p.account_code_id) tmp");
		Session session = getSession();
		try {
			String bi = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getManuallyCreated.");
			return bi;
		} finally {
			releaseSession(session);
		}
	}

	private Object getManuallyCreatedWhereCause(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM proposal p ");
		sb.append(" left join account_code a on p.account_code_id=a.id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" WHERE p.invoice_item_id is NULL ");
		sb.append(" AND p.payment_amount is not NULL ");
		sb.append(" AND p.payment_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + svo.getInvoiceId1());
		return sb.toString();
	}

	/**
	 * @author Jian.Dong
	 */
	public List getDisputePaybackPayment(SearchInvoiceVO svo) {
		logger.info("Enter method getDisputePaybackPayment.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("scoa:\"',toJSON(a.account_code_name is null,'',a.account_code_name),'\", ");
		sb
				.append("amount:\"',toJSON(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),'\", ");
		sb.append("description:\"',toJSON(r.notes is null,'',r.notes),'\", ");
		sb
				.append("date:\"',toJSON(r.reconcile_date is null,'',r.reconcile_date),'\", ");
		sb
				.append("createBy:\"',CONCAT(toJSON(u.first_name is null,'',u.first_name),' ',toJSON(u.last_name is null,'',u.last_name)),'\", ");
		sb
				.append("files:\"',toJSON(r.attachment_point_id  is null,'',r.attachment_point_id),'\"}')");
		sb.append(getDisputePaybackWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getDisputePaybackPayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getDisputePaybackPaymentTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getDisputePaybackPaymentTotalPageNo.");
		StringBuffer sb = new StringBuffer("SELECT count(1) ");
		sb.append(getDisputePaybackWhereCause(svo));
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getDisputePaybackPaymentTotalPageNo.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getDisputePayback(SearchInvoiceVO svo) {
		logger.info("Enter method getdisputePayback.");
		StringBuffer sb = new StringBuffer(
				"SELECT if(sum(tmp.reconcile_amount) is null,'0.00',format(sum(tmp.reconcile_amount),2)) from (");
		sb.append(" SELECT round(sum(r.reconcile_amount),2) reconcile_amount ");
		sb
				.append(" FROM (((((reconcile r left join payment p on(r.payment_id=p.id))"
						+ " left join dispute d on(r.dispute_id=d.id and d.rec_active_flag='Y'))"
						+ " left join invoice i on(d.invoice_id=i.id and i.rec_active_flag='Y'))"
						+ " left join user u on(r.created_by=u.id))"
						+ " left join account_code a on(r.account_code_id=a.id ");
		//wenbo.zhang
		//2016-07-29
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
		sb
				.append(" WHERE r.dispute_id is not null and r.reconcile_status_id=7 AND r.rec_active_flag='Y' AND i.ban_id="
						+ svo.getBanId1());

		if (invoiceStatusId < 21) {
			sb
					.append(" and ((p.invoice_id="
							+ svo.getInvoiceId1()
							+ ") or (r.payment_id is null and (r.payment_invoice_id is NULL or r.payment_invoice_id="
							+ svo.getInvoiceId1() + ")))");
		} else {
			sb.append(" and ((p.invoice_id=" + svo.getInvoiceId1()
					+ ") or (r.payment_id is null and r.payment_invoice_id="
					+ svo.getInvoiceId1() + "))");
		}
		sb.append(" GROUP BY r.account_code_id) tmp");
		Session session = getSession();
		try {
			String bi = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getdisputePayback.");
			return ((bi == null) ? "0.00" : bi.toString());
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List<Object[]> getDisputePaybackToExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getDisputePaybackPayment.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("if(a.account_code_name is null,'',a.account_code_name)");
		sb
				.append(",if(r.reconcile_amount is null,'',format(r.reconcile_amount,2))");
		sb.append(",if(r.notes is null,'',r.notes)");
		sb.append(",if(r.reconcile_date is null,'',r.reconcile_date)");
		sb
				.append(",CONCAT(if(u.first_name is null,'',u.first_name),' ',if(u.last_name is null,'',u.last_name))");
		sb.append(getDisputePaybackWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getDisputePaybackPayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	private Object getDisputePaybackWhereCause(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" FROM (((((reconcile r left join payment p on(r.payment_id=p.id))"
						+ " left join dispute d on(r.dispute_id=d.id))"
						+ " left join invoice i on(d.invoice_id=i.id))"
						+ " left join user u on(r.created_by=u.id))"
						+ " left join account_code a on(r.account_code_id=a.id");
		//wenbo.zhang
		//2016-07-29
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
		sb
				.append(" WHERE r.dispute_id is not null and r.reconcile_status_id=7 AND r.rec_active_flag='Y' AND i.ban_id="
						+ svo.getBanId1());
		
		if (invoiceStatusId < 21) {
			sb
					.append(" and ((p.invoice_id="
							+ svo.getInvoiceId1()
							+ ") or (r.payment_id is null and (r.payment_invoice_id is NULL or r.payment_invoice_id="
							+ svo.getInvoiceId1() + ")))");
		} else {
			sb.append(" and ((p.invoice_id=" + svo.getInvoiceId1()
					+ ") or (r.payment_id is null and r.payment_invoice_id="
					+ svo.getInvoiceId1() + "))");
		}
		return sb.toString();
	}

	/**
	 * @author Jian.Dong
	 */
	public List getCurrentDispute(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentDispute.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("claimNumber:\"',toJSON(d.claim_number is null,'',d.claim_number),'\", ");
		sb
				.append("disputeCategory:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),'\", ");
		sb
				.append("amount:\"',toJSON(d.dispute_amount is null,'',format(d.dispute_amount,2)),'\", ");
		sb
				.append("recurring:\"',toJSON(d.flag_recurring is null,'',d.flag_recurring),'\", ");
		sb
				.append("shortpaid:\"',toJSON(d.flag_shortpaid is null,'',d.flag_shortpaid),'\", ");
		sb
				.append("level:\"',toJSON(d.confidence_level is null,'',d.confidence_level),'\", ");
		sb
				.append("reserveAmount:\"',toJSON(d.reserve_amount is null,'',format(d.reserve_amount,2)),'\", ");
		sb
				.append("files:\"',toJSON(d.attachment_point_id  is null,'',d.attachment_point_id),'\"}')");
		sb
				.append(" from (dispute d left join dispute_reason dr on(d.dispute_reason_id=dr.id)) ");
		sb
				.append(" where d.rec_active_flag='Y' AND d.flag_shortpaid in ('Y','N') AND d.dispute_amount!=0 AND d.dispute_amount is not null AND d.invoice_id="
						+ svo.getInvoiceId1());
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCurrentDispute.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List getCurrentDisputeDownExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentDisputeDownExcel.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" if(d.claim_number is null,'',d.claim_number), ");
		sb
				.append(" if(dr.dispute_reason_name is null,'',dr.dispute_reason_name), ");
		sb
				.append(" if(d.dispute_amount is null,'',format(d.dispute_amount,2)), ");
		sb.append(" if(d.flag_recurring is null,'',d.flag_recurring), ");
		sb.append(" if(d.flag_shortpaid is null,'',d.flag_shortpaid), ");
		sb.append(" concat(d.confidence_level,'%'), ");
		sb
				.append(" if(d.reserve_amount is null,'',format(d.reserve_amount,2)) ");
		sb
				.append(" from (dispute d left join dispute_reason dr on(d.dispute_reason_id=dr.id)) ");
		sb
				.append(" where d.rec_active_flag='Y' AND d.flag_shortpaid in ('Y','N') AND d.dispute_amount!=0 AND d.dispute_amount is not null AND d.invoice_id="
						+ svo.getInvoiceId1());
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCurrentDisputeDownExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getCurrentDisputeTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentDispute.");
		StringBuffer sb = new StringBuffer("SELECT count(1) ");
		sb
				.append(" from (dispute d left join dispute_reason dr on(d.dispute_reason_id=dr.id)) ");
		sb
				.append(" where d.rec_active_flag='Y' AND d.flag_shortpaid in ('Y','N') AND d.dispute_amount!=0 AND d.dispute_amount is not null AND d.invoice_id="
						+ svo.getInvoiceId1());
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getCurrentDispute.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List previous3DisputeAmount(SearchInvoiceVO svo) {
		logger.info("Enter method previous3DisputeAmount.");
		StringBuffer sb = new StringBuffer(
				"select if(sum(d.dispute_amount) is null,'0',format(sum(d.dispute_amount),2)) from dispute d,invoice i ");
		sb
				.append("where d.rec_active_flag='Y' and d.invoice_id=i.id and i.invoice_date<(select ii.invoice_date from invoice ii where ii.id=");
		sb.append(svo.getInvoiceId1());
		sb.append(") and i.ban_id=");
		sb.append(svo.getBanId1());
		sb.append(" GROUP by i.id order by i.invoice_date desc limit 0,3");
		Session session = getSession();
		try {
			List r = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method previous3DisputeAmount.");
			return r;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List previous3PaymentAmount(SearchInvoiceVO svo) {
		logger.info("Enter method previous3PaymentAmount.");
		StringBuffer sb = new StringBuffer(
				"select if(sum(p.payment_amount) is null,'0',format(sum(p.payment_amount),2)) from payment p,invoice i ");
		sb
				.append("where p.rec_active_flag='Y' and p.invoice_id=i.id and i.invoice_date<(select ii.invoice_date from invoice ii where ii.id=");
		sb.append(svo.getInvoiceId1());
		sb.append(") and i.ban_id=");
		sb.append(svo.getBanId1());
		sb.append(" GROUP by i.id order by i.invoice_date desc limit 0,3");
		Session session = getSession();
		try {
			List r = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method previous3PaymentAmount.");
			return r;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public String getMinPaymentStatus(SearchInvoiceVO svo) {
		String sql = "select min(ps.id),ps.payment_status_name from payment p, payment_status ps where p.rec_active_flag='Y' and p.invoice_id="
				+ svo.getInvoiceId1() + " and p.payment_status_id=ps.id";
		Session session = getSession();
		try {
			Object[] objs = (Object[]) session.createSQLQuery(sql)
			.uniqueResult();
			String name = (String) objs[1];
			return name;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List getCurrentInvoiceHistory(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentInvoiceHistory.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("created_timestamp:\"',toJSON(th.created_timestamp is null,'',date_format(th.created_timestamp,'%Y-%m-%d')),'\", ");
		sb
				.append("invoice_status_name:\"',toJSON(_is.invoice_status_name is null,'',_is.invoice_status_name),'\", ");
		sb
				.append("payment_status_name:\"',toJSON(ps.payment_status_name is null,'',ps.payment_status_name),'\", ");
		sb
				.append("workflow_action_name:\"',toJSON(wa.workflow_action_name is null,'',wa.workflow_action_name),'\", ");
		sb
				.append("user:\"',IF(th.workflow_user_id IS NULL ,th.workflow_user_name , CONCAT(toJSON(u.first_name is null,'',u.first_name),' ',toJSON(u.last_name is null,'',u.last_name))),'\", ");
		sb.append("notes:\"',toJSON(th.notes is null,'',th.notes),'\", ");
		sb
				.append("files:\"',toJSON(th.attachment_point_id  is null,'',th.attachment_point_id),'\"}')");
		sb
				.append(" FROM (((transaction_history th left join invoice_status _is on(th.invoice_status_id=_is.id)  left join payment_status ps on(th.payment_status_id=ps.id)) left join workflow_action wa on(th.workflow_action_id=wa.id)) left join user u on(th.workflow_user_id=u.id))");
		sb.append(" WHERE th.rec_active_flag='Y' AND th.invoice_id="
				+ svo.getInvoiceId1());
		sb
				.append(" AND th.dispute_id is NULL AND th.payment_id is NULL AND th.created_by <> 0");
		sb.append(" ORDER BY th.created_timestamp DESC,th.id DESC");
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCurrentInvoiceHistory.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List getPastThreePaymentPage(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer(
				"select CONCAT('{ date:\"',toJSON(i.invoice_date is null ,'' ,i.invoice_date),'\","
						+ "disputeAmount:\"',toJSON(i.dispute_amount is null,'0.00',format(i.dispute_amount,2)),'\","
						+ "paymentAmount:\"',toJSON(i.payment_amount is null,'0.00',format(i.payment_amount,2)),'\"}') from invoice i");
		sb.append(" where i.rec_active_flag='Y' and i.ban_id=");
		sb.append(svo.getBanId1());
		sb
				.append(" and i.invoice_date<(select ii.invoice_date from invoice ii where ii.id=");
		sb.append(svo.getInvoiceId1());
		sb.append(") order by i.invoice_date desc limit 0,3");
		Session session = getSession();
		try {
			List list = session.createSQLQuery(sb.toString()).list();
			// List result = new ArrayList();
			//
			// for (int i = 0; i < list.size(); i++) {
			// Object o = getSession().createSQLQuery(
			// "select CONCAT('{ date:\"',if(i.invoice_date is null ,''
			// ,i.invoice_date),'\","
			// + "disputeAmount:\"',if(i.dispute_amount is
			// null,'0.00',format(i.dispute_amount,2)),'\","
			// + "paymentAmount:\"',if(i.payment_amount is
			// null,'0.00',format(i.payment_amount,2)),'\"}') " +
			// "from invoice i where i.id=" + list.get(i)).uniqueResult();
			// result.add(o);
			// }
			return list;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List<Object[]> getCurrentInvoiceHistoryToExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getCurrentInvoiceHistoryToExcel.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("if(th.created_timestamp is null,'',th.created_timestamp)");
		sb
				.append(",if(_is.invoice_status_name is null,'',_is.invoice_status_name)");
		sb
				.append(",if(ps.payment_status_name is null,'',ps.payment_status_name)");
		sb
				.append(",if(wa.workflow_action_name is null,'',wa.workflow_action_name)");
		sb
				.append(",IF(th.workflow_user_id IS NULL ,th.workflow_user_name , CONCAT(toJSON(u.first_name is null,'',u.first_name),' ',toJSON(u.last_name is null,'',u.last_name))) ");
		sb.append(",if(th.notes is null,'',th.notes)");
		sb
				.append(" FROM ((((transaction_history th left join invoice_status _is on(th.invoice_status_id=_is.id)) left join payment_status ps on(th.payment_status_id=ps.id)) left join workflow_action wa on(th.workflow_action_id=wa.id)) left join user u on(th.workflow_user_id=u.id))");
		sb.append(" WHERE th.rec_active_flag='Y' AND th.invoice_id="
				+ svo.getInvoiceId1());
		sb
				.append(" AND th.dispute_id is NULL AND th.payment_id is NULL AND th.created_by != 0 ");
		sb.append(" ORDER BY th.created_timestamp DESC,th.id DESC");
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCurrentInvoiceHistoryToExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List<Object[]> getApplyingCreditToExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getApplyingCreditPayment.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("if(a.account_code_name is null,'',a.account_code_name)");
		sb
				.append(",if(r.reconcile_amount is null,'',format(r.reconcile_amount,2))");
		sb.append(",if(r.notes is null,'',r.notes)");
		sb.append(",if(r.reconcile_date is null,'',r.reconcile_date)");
		sb
				.append(",CONCAT(if(u.first_name is null,'',u.first_name),' ',if(u.last_name is null,'',u.last_name))");
		sb.append(" FROM reconcile r, user u, account_code a");
		sb.append(" WHERE r.rec_active_flag='Y' ");
		sb.append(" AND r.created_by=u.id AND r.account_code_id=a.id");
		sb
				.append(" AND r.reconcile_status_id in (1,3,4,6) AND r.payment_id is NULL");
		sb.append(" AND r.credit_invoice_id=" + svo.getInvoiceId1());
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getApplyingCreditPayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Pengfei.Wang
	 */
	public List<Object[]> getMiscDisputePaybackToExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getDisputePaybackPayment.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("if(a.account_code_name is null,'',a.account_code_name)");
		sb
				.append(",if(r.reconcile_amount is null,'',format(r.reconcile_amount,2))");
		sb.append(",if(r.notes is null,'',r.notes)");
		sb.append(",if(r.reconcile_date is null,'',r.reconcile_date)");
		sb
				.append(",CONCAT(if(u.first_name is null,'',u.first_name),' ',if(u.last_name is null,'',u.last_name))");
		sb
				.append(" FROM (((((reconcile r left join payment p on(r.payment_id=p.id))"
						+ " left join dispute d on(r.dispute_id=d.id))"
						+ " left join invoice i on(d.invoice_id=i.id))"
						+ " left join user u on(r.created_by=u.id))"
						+ " left join account_code a on(r.account_code_id=a.id))");
		sb
				.append(" WHERE r.dispute_id is not null and r.reconcile_status_id=7 AND r.rec_active_flag='Y' AND i.ban_id="
						+ svo.getBanId());
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
			sb
					.append(" and ((p.invoice_id="
							+ svo.getInvoiceId1()
							+ ") or (r.payment_id is null and (r.payment_invoice_id is NULL or r.payment_invoice_id="
							+ svo.getInvoiceId1() + ")))");
		} else {
			sb.append(" and ((p.invoice_id=" + svo.getInvoiceId1()
					+ ") or (r.payment_id is null and r.payment_invoice_id="
					+ svo.getInvoiceId1() + "))");
		}
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getDisputePaybackPayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public Long getInvoiceCreditBalance(Integer id) {
		logger.info("Enter method getInvoiceCreditBalance.");
		StringBuffer sb = new StringBuffer("select count(1)");
		sb.append(" FROM proposal p");
		sb.append(" WHERE p.rec_active_flag='Y' AND p.invoice_id=" + id);
		sb.append(" AND p.credit_amount!=0 AND p.credit_amount is not null");
		sb.append(" AND p.balance_amount!=0 AND p.balance_amount is not null ");
		Session session = getSession();
		try {
			BigInteger re = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getInvoiceCreditBalance.");
			return re.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public Double[] getProposalAmount(Integer id) {
		logger.info("Enter method getProposalAmount.");
		StringBuffer sb = new StringBuffer(
				"select sum(p.payment_amount),sum(p.dispute_amount) ");
		sb.append(" FROM proposal p where p.invoice_id=");
		sb.append(id);
		sb
				.append(" AND (p.account_code_id=0 or p.account_code_id is null) AND p.rec_active_flag='Y' AND p.proposal_flag='1'");
		Session session = getSession();
		try {
			Object[] o = (Object[]) session.createSQLQuery(sb.toString())
			.uniqueResult();
			Double[] re = new Double[3];
			re[0] = (Double) o[0];
			re[1] = (Double) o[1];
			BigInteger re2 = (BigInteger) session
			.createSQLQuery(
					"select count(1) from reconcile r where r.rec_active_flag='Y' and (r.reconcile_amount is not null or r.reconcile_amount!=0) and (r.account_code_id=0 or r.account_code_id is null) and r.credit_invoice_id="
					+ id).uniqueResult();
			re[2] = re2.doubleValue();
			
			logger.info("Exit method getProposalAmount.");
			return re;
		} finally {
			releaseSession(session);
		}
		// return new Double[]{new Double(0),new Double(0)};
	}

	/**
	 * @author Jian.Dong
	 */
	public void setBanProcessStatus(Integer id, String string) {
		logger.info("Enter method setBanProcessStatus.");
		if (string != null) {
			string = "'" + string + "'";
		} else {
			string = "null";
		}
		StringBuffer sb = new StringBuffer("update ban b set b.process_status=");
		sb.append(string);
		sb.append(" where b.id=");
		sb.append(id);
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method setBanProcessStatus.");
	}

	public Double getInvoicePaymentAmount(Invoice invoice) {
		SearchInvoiceVO svo = new SearchInvoiceVO();
		svo.setInvoiceId1(invoice.getId());
		svo.setBanId1((invoice.getBan() == null ? null : invoice.getBan()
				.getId()));

		String totalShortPaidDisputePayment = getTotalShortPaidDisputePayment(svo
				.getInvoiceId1());
		String totalPaidDisputePayment = getTotalPaidDisputePayment(svo
				.getInvoiceId1());
		String currentInvoice = getCurrentInvoice(svo);
		String manuallyCreated = getManuallyCreated(svo);
		String disputePayback = getDisputePayback(svo);
		String applyingCredit = getApplyingCredit(svo);
		Double totalPayment = null;
		try {
			totalPayment = (CcmFormat.paseCurrency(totalPaidDisputePayment)
					.doubleValue()
					- CcmFormat.paseCurrency(totalShortPaidDisputePayment)
							.doubleValue()
					+ CcmFormat.paseCurrency(currentInvoice).doubleValue()
					+ CcmFormat.paseCurrency(manuallyCreated).doubleValue()
					+ CcmFormat.paseCurrency(disputePayback).doubleValue() - CcmFormat
					.paseCurrency(applyingCredit).doubleValue());
			totalPayment = CcmFormat.paseCurrency(CcmFormat.formatCurrency(totalPayment)).doubleValue();
		} catch (ParseException e) {
			try {
				totalPayment = CcmFormat.paseCurrency(
						getTotalPayment(svo.getInvoiceId1(), svo.getBanId1()))
						.doubleValue();
			} catch (ParseException e1) {
				logger.error(e);
			}
		}
		return totalPayment;
	}

	
	public long getBanDisputeDueDate(Integer invoiceId){
		
		logger.info("Enter method getBanDisputeDueDate.");
		
		Integer assignmentUserId = SystemUtil.getCurrentUserId();
		
		String sb = "SELECT count(1) FROM invoice i, dispute d , proposal p WHERE d.invoice_id = i.id  AND i.ban_id = "+
		" (SELECT ii.ban_id FROM invoice ii WHERE ii.id = "+ invoiceId +" ) AND p.dispute_id = d.id AND p.dispute_amount <> 0 AND p.balance_amount <> 0 " +
		" AND d.dispute_status_id > 22 AND d.dispute_status_id < 98 AND p.proposal_flag = 1 AND p.rec_active_flag = 'Y'" +
//		Invoice做Approve或者Level 1 Approve时,无论是哪个用户,都要拦截.
//		" AND exists (select * from ban b where b.id = i.ban_id and (b.approver1_id = "+assignmentUserId+" or b.analyst_id = "+assignmentUserId+" ))" +
		" AND exists (select * from invoice where id = "+invoiceId+" and invoice_status_id in (9,10,11,12,13,14,15,21))" +
		" AND (datediff(now(), d.created_timestamp) > 60) AND FN_GET_DISPUTE_LAST_UPDATED_DATE_INTERVAL_BY_CURRENT(d.id) > 30 AND i.rec_active_flag = 'Y' AND d.rec_active_flag = 'Y' ";
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb)
			.uniqueResult();
			logger.info("Exit method getBanDisputeDueDate.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	public long getInvoiceDisputeStatusCloseAndActionRequestOpenCount(Invoice invoice){
		logger.info("Enter method getInvoiceDisputeStatusCloseAndActionRequestOpenCount.");
		Integer assignmentUserId = SystemUtil.getCurrentUserId();
		
		String sb = "SELECT count(1) FROM invoice i, dispute d, dispute_action_request dar "+
        " WHERE i.id = d.invoice_id AND d.id = dar.dispute_id AND d.dispute_status_id not in (99,98)  AND dar.dispute_action_request_status_id = 1"+
        " AND i.rec_active_flag = 'Y' AND d.rec_active_flag = 'Y' AND dar.rec_active_flag = 'Y'" +
//		Invoice做Approve或者Level 1 Approve时,无论是哪个用户,都要拦截.
//      " AND exists (select * from ban b where b.id = i.ban_id and (b.approver1_id = "+assignmentUserId+" or b.analyst_id = "+assignmentUserId+" ))" +
        " AND exists (select * from invoice where id = "+invoice.getId()+" and invoice_status_id in (9,10,11,12,13,14,15,21))" +
        " AND i.ban_id = ( select ban_id from invoice  where id = "+invoice.getId()+")";
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb)
			.uniqueResult();
			logger.info("Exit method getInvoiceDisputeStatusCloseAndActionRequestOpenCount.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author Jian.Dong
	 */
	public Integer getAssignmentUserId(Invoice invoice) {
		logger.info("Enter method getAssignmentUserId.");
		Session session = getSession();
		try {
		
			Integer invoiceStatusId = invoice.getInvoiceStatus().getId();
			SearchInvoiceVO sv = new SearchInvoiceVO();
			sv.setInvoiceId1(invoice.getId());
	//		if (invoiceStatusId >= 29 && invoiceStatusId <= 32) {
	//			return 0;
	//		}
			if (invoiceStatusId >= 33 && invoiceStatusId <= 36) {
				return 0;
			}
	
			String selectItem = null;
			Double bi = getInvoicePaymentAmount(invoice);
			Double paymentThreshold2 = new Double(
					(String) session
							.createSQLQuery(
									"select sc.value from sys_config sc where sc.parameter='payment_approval_threshold_2'")
							.uniqueResult());
			if (invoiceStatusId >= 21 && invoiceStatusId <= 24) {
				// mod by yinghe.fu
				Double paidDisputeLose = 0d;
				try {
					paidDisputeLose = CcmFormat
							.paseCurrency(getPaidDisputeLose(invoice)).doubleValue();
				} catch (ParseException e) {
					logger.error(e);
					RuntimeException re = new RuntimeException(e);
					throw re;
				}
	
				Double paidDisputeLoseThreshold = new Double(
						(String) session
								.createSQLQuery(
										"select sc.value from sys_config sc where sc.parameter='paid_dispute_lose_threshold_2'")
								.uniqueResult());
				
				Double manuallyCreated = 0d;
				try {
					manuallyCreated = CcmFormat
							.paseCurrency(getManuallyCreated(sv)).doubleValue();
				} catch (ParseException e) {
					logger.error(e);
					RuntimeException re = new RuntimeException(e);
					throw re;
				}
				Double miscellaneousThreshold = new Double(
						(String) session
								.createSQLQuery(
										"select sc.value from sys_config sc where sc.parameter='miscellaneous_adjustment_thresho'")
								.uniqueResult());
				
				SearchInvoiceVO svo = new SearchInvoiceVO();
				svo.setInvoiceId1(invoice.getId());
				svo.setBanId1(invoice.getBan().getId());
				Double disputePayback = 0d;
				try {
					disputePayback = CcmFormat.paseCurrency(
							getDisputePayback(svo)).doubleValue();
				} catch (ParseException e) {
					logger.error(e);
					RuntimeException re = new RuntimeException(e);
					throw re;
				}
	
				Double paybackThreshold2 = new Double(
						(String) session
								.createSQLQuery(
										"select sc.value from sys_config sc where sc.parameter='payback_approval_threshold_2'")
								.uniqueResult());
				
				
				if (manuallyCreated > miscellaneousThreshold) {
					selectItem = "approver2_id";
				}else if (paidDisputeLose > paidDisputeLoseThreshold) {
					selectItem = "approver2_id";
				}else if (disputePayback > paybackThreshold2) {
					selectItem = "approver2_id";
				}  else {
					if (bi != null && bi > paymentThreshold2) {
						selectItem = "approver2_id";
					} else {
						return 0;
					}
				}
			}
	
			Double paymentThreshold3 = new Double(
					(String) session
							.createSQLQuery(
									"select sc.value from sys_config sc where sc.parameter='payment_approval_threshold_3'")
							.uniqueResult());
			if (invoiceStatusId >= 25 && invoiceStatusId <= 28) {
				
				if (bi != null && bi > paymentThreshold3) {
					selectItem = "approver3_id";
				} else {
					return 0;
				}
			}
			
			Double paymentThreshold4 = new Double(
					(String) session
					.createSQLQuery(
							"select sc.value from sys_config sc where sc.parameter='payment_approval_threshold_4'")
							.uniqueResult());
			if (invoiceStatusId >= 29 && invoiceStatusId <= 32) {
				
				if (bi != null && bi > paymentThreshold4) {
					selectItem = "approver4_id";
				} else {
					return 0;
				}
			}
	
			if (invoiceStatusId >= 10 && invoiceStatusId <= 20) {
				// mod by yinghe.fu
				
				Double manuallyCreated = 0d;
				Double paidDisputeLose = 0d;
				try {
					manuallyCreated = CcmFormat
							.paseCurrency(getManuallyCreated(sv)).doubleValue();
				} catch (ParseException e) {
					logger.error(e);
					RuntimeException re = new RuntimeException(e);
					throw re;
				}
				try {
					paidDisputeLose = CcmFormat
							.paseCurrency(getPaidDisputeLose(invoice)).doubleValue();
				} catch (ParseException e) {
					logger.error(e);
					RuntimeException re = new RuntimeException(e);
					throw re;
				}
				
				Double paidDisputeLoseThreshold = new Double(
						(String) session
								.createSQLQuery(
										"select sc.value from sys_config sc where sc.parameter='paid_dispute_lose_threshold_1'")
								.uniqueResult());
				String Level1ApprovalFlag = 
						(String) session
						.createSQLQuery(
								"select b.Level_1_Approval_Flag from ban b inner join invoice i on i.ban_id = b.id where i.id="+sv.getInvoiceId1())
						.uniqueResult();
				if ("Y".equals(Level1ApprovalFlag)){
					selectItem = "approver1_id";
//				} else if (manuallyCreated > 0) {
				} else if (manuallyCreated != 0) {
					selectItem = "approver1_id";
				} else if (paidDisputeLose > paidDisputeLoseThreshold) {
					selectItem = "approver1_id";
				} else {
					selectItem = "approver1_id";
					SearchInvoiceVO svo = new SearchInvoiceVO();
					svo.setInvoiceId1(invoice.getId());
					svo.setBanId1(invoice.getBan().getId());
					Double disputePayback = 0d;
					try {
						disputePayback = CcmFormat.paseCurrency(
								getDisputePayback(svo)).doubleValue();
					} catch (ParseException e) {
						logger.error(e);
						RuntimeException re = new RuntimeException(e);
						throw re;
					}
					Double paymentThreshold1 = new Double(
							(String) session
									.createSQLQuery(
											"select sc.value from sys_config sc where sc.parameter='payment_approval_threshold_1'")
									.uniqueResult());
					Double paybackThreshold = new Double(
							(String) session
									.createSQLQuery(
											"select sc.value from sys_config sc where sc.parameter='payback_threshold_1'")
									.uniqueResult());
					if (bi > paymentThreshold1 || disputePayback > paybackThreshold) {
						selectItem = "approver1_id";
					} else {
						return 0;
					}
				}
	
			}
	
			if (selectItem == null) {
				return 0;
			}
	
			StringBuffer sb = new StringBuffer("select b.");
			sb.append(selectItem);
			sb.append(" from ban b");
			sb.append(" where b.rec_active_flag='Y' and b.id=");
			sb.append(invoice.getBan().getId());
			sb.append(" and b.");
			sb.append(selectItem);
			sb.append(" is not null");
			List list = session.createSQLQuery(sb.toString()).list();
			Integer assignmentUserId = null;
			if (list.size() < 1 || getApproveId(list) == null) {
				sb = new StringBuffer("select b.");
				sb.append(selectItem);
				sb.append(" from ban b");
				sb.append(" where b.rec_active_flag='Y' and b.vendor_id=");
				sb.append(invoice.getVendor().getId());
				sb.append(" and b.");
				sb.append(selectItem);
				sb.append(" is not null");
				list = session.createSQLQuery(sb.toString()).list();
				if (list.size() < 1 || getApproveId(list) == null) {
					sb = new StringBuffer("select b.");
					sb.append(selectItem);
					sb.append(" from ban b");
					sb.append(" where b.rec_active_flag='Y' and b.vendor_id=0");
					sb.append(" and b.");
					sb.append(selectItem);
					sb.append(" is not null");
					list = session.createSQLQuery(sb.toString()).list();
					if (list.size() < 1 || getApproveId(list) == null) {
						return 99;
					} else {
						assignmentUserId = (Integer) getApproveId(list);
					}
				} else {
					assignmentUserId = (Integer) getApproveId(list);
				}
			} else {
				assignmentUserId = (Integer) getApproveId(list);
			}
			logger.info("Exit method getAssignmentUserId.");
			return assignmentUserId;
		} finally {
			releaseSession(session);
		}
	}

	public String getPaidDisputeLose(Invoice invoice) {
		logger.info("Enter method getPaidDisputeLose.");
		StringBuffer sb = new StringBuffer(
				"SELECT if(sum(r.reconcile_amount) is null,'0.00',sum(r.reconcile_amount)) as dispute_amount FROM proposal p ");
		sb.append(" INNER JOIN invoice i");
		sb.append(" ON (p.invoice_id = i.id)");
		sb.append(" INNER JOIN reconcile r");
		sb.append(" ON (r.dispute_proposal_id = p.id)");
		sb.append(" INNER JOIN reconcile_status rs");
		sb.append(" ON (r.reconcile_status_id = rs.id)");
		sb.append(" INNER JOIN dispute d");
		sb.append(" ON (p.dispute_id = d.id)");
		sb.append(" WHERE r.payment_invoice_id =" + invoice.getId());
		sb.append(" AND   rs.id = 5");
		sb.append(" AND   r.dispute_proposal_id is not NULL");
		sb.append(" AND   p.proposal_flag = 1");
		sb.append(" AND   p.rec_active_flag = 'Y'");
		sb.append(" AND   r.rec_active_flag = 'Y'");
		sb.append(" AND   d.rec_active_flag = 'Y'");
		Session session = getSession();
		try {
			String bi = (String) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getPaidDisputeLose.");
			return bi;
		} finally {
			releaseSession(session);
		}
	}

	private Integer getApproveId(List list) {
		for (Object i : list) {
			if (i != null)
				return (Integer) i;
		}
		return null;
	}

	/**
	 * @author Jian.Dong
	 */
	public void updateInvoice(Invoice invoice, Integer newStatusId,
			Integer assignmentUserId, Integer workflowActionId, String message) {
		logger.info("Enter method updateInvoice.");
		StringBuffer sb = new StringBuffer(
				"update invoice i set i.invoice_status_id=");
		sb.append(newStatusId);
		sb.append(",i.assignment_user_id=");
		sb.append(assignmentUserId);
		sb.append(",i.workflow_action_id=");
		sb.append(workflowActionId);
		sb.append(",i.workflow_user_id=");
		sb.append(SystemUtil.getCurrentUserId());
		sb.append(",i.modified_timestamp='");
		sb.append(CcmFormat.formatDateTime(new Date()));
		sb.append("',i.modified_by=");
		sb.append(SystemUtil.getCurrentUserId());
		sb.append(",i.payment_amount=");
		sb.append((invoice.getPaymentAmount() == null ? 0 : invoice
				.getPaymentAmount()));
		sb.append(",i.dispute_amount=");
		sb.append((invoice.getDisputeAmount() == null ? 0 : invoice
				.getDisputeAmount()));
		sb.append(",i.misc_adjustment_amount=");
		sb.append((invoice.getMiscAdjustmentAmount() == null ? 0 : invoice
				.getMiscAdjustmentAmount()));
		sb.append(",i.notes='");
		sb.append(CcmUtil.stringToSql(message));
		sb.append("' where i.id=");
		sb.append(invoice.getId());
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateInvoice.");
	}

	private Object getPaidDisputeWhereCause(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM dispute d, proposal p ");
		//wenbo.zhang
		//2016-07-29
		sb.append("left join account_code a ON a.id = p.account_code_id");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" WHERE p.dispute_id = d.id ");
		sb.append(" AND d.flag_shortpaid='N' ");
		sb.append(" AND p.invoice_item_id is not NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + svo.getInvoiceId1());
		return sb.toString();
	}

	/**
	 * @author Jian.Dong
	 */
	public List getPaidDisputePayment(SearchInvoiceVO svo) {
		logger.info("Enter method getPaidDisputePayment.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("scoa:\"',toJSON(a.account_code_name is null,'',a.account_code_name),'\", ");
		sb
				.append("amount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\", ");
		sb
				.append("description:\"',toJSON(p.description is null,'',p.description),'\", ");
		sb.append("notes:\"',toJSON(p.notes is null,'',p.notes),'\", ");
		sb
				.append("files:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),'\"}')");
		sb.append(getPaidDisputeWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getPaidDisputePayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getPaidDisputePaymentTotalPageNo.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(getPaidDisputeWhereCause(svo));
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getPaidDisputePaymentTotalPageNo.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List<Object[]> getExcelByPaidDisputePayment(SearchInvoiceVO svo) {
		logger.info("Enter method getExcelByPaidDisputePayment.");
		StringBuffer sb = new StringBuffer("select");
		sb.append(" if(a.account_code_name is null,'',a.account_code_name)");
		sb
				.append(",if(p.dispute_amount is null,'',format(p.dispute_amount,2))");
		sb.append(",if(p.description is null,'',p.description)");
		sb.append(",if(p.notes is null,'',p.notes)");
		sb.append(getPaidDisputeWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getExcelByPaidDisputePayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	private String getShortPaidDisputeWhereCause(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM dispute d, proposal p");
		sb.append(" left join account_code a on p.account_code_id=a.id ");
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and a.active_flag = 'Y'");
					sb.append(" and a.rec_active_flag = 'Y'");
		}
		sb.append(" WHERE p.dispute_id=d.id ");
		sb.append(" AND d.flag_shortpaid='Y' ");
		sb.append(" AND p.invoice_item_id is NULL ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.invoice_id=" + svo.getInvoiceId1());
		return sb.toString();
	}

	/**
	 * @author Jian.Dong
	 */
	public List getShortPaidDisputePayment(SearchInvoiceVO svo) {
		logger.info("Enter method getShortPaidDisputePayment.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("scoa:\"',toJSON(a.account_code_name is null,'',a.account_code_name),'\", ");
		sb
				.append("amount:\"',toJSON(p.dispute_amount is null,'',format(p.dispute_amount,2)),'\", ");
		sb.append("description:\"',toJSON(p.notes is null,'',p.notes),'\", ");
		sb
				.append("files:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),'\"}')");
		sb.append(getShortPaidDisputeWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getShortPaidDisputePayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public long getShortPaidDisputePaymentTotalPageNo(SearchInvoiceVO svo) {
		logger.info("Enter method getShortPaidDisputePaymentTotalPageNo.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(getShortPaidDisputeWhereCause(svo));
		Session session = getSession();
		try {
			BigInteger bi = (BigInteger) session.createSQLQuery(sb.toString())
			.uniqueResult();
			logger.info("Exit method getShortPaidDisputePaymentTotalPageNo.");
			return bi.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public List<Object[]> getExcelByShortPaidDisputePayment(SearchInvoiceVO svo) {
		logger.info("Enter method getExcelByShortPaidDisputePayment.");
		StringBuffer sb = new StringBuffer("select");
		sb.append(" if(a.account_code_name is null,'',a.account_code_name)");
		sb
				.append(",if(p.dispute_amount is null,'',format(p.dispute_amount,2))");
		sb.append(",if(p.notes is null,'',p.notes)");
		sb.append(getShortPaidDisputeWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getExcelByShortPaidDisputePayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * @author Jian.Dong
	 */
	public Long getUngroupedDisputeCount(Integer id) {
		logger.info("Enter method getUngroupedDisputeCount.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(" FROM proposal p");
		sb.append(" WHERE p.dispute_id is null ");
		sb.append(" AND p.dispute_amount is not NULL ");
		sb.append(" AND p.dispute_amount!=0 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		sb.append(" AND p.proposal_flag='1'");
		sb.append(" AND p.invoice_id=" + id);
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method getUngroupedDisputeCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

	private String getOutstandingCreditWhereCause(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" FROM (proposal p left join account_code ac on(p.account_code_id=ac.id");
		//wenbo.zhang
		//2016-07-29
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and ac.active_flag = 'Y'");
					sb.append(" and ac.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
		sb.append(" WHERE p.invoice_id=" + svo.getInvoiceId1());
		sb.append(" AND p.credit_amount<>0 and p.balance_amount<>0");
		sb.append(" AND p.proposal_flag='1' ");
		sb.append(" AND p.rec_active_flag='Y'");
		if (svo.getFilter() != null && !"".equals(svo.getFilter())) {
			sb.append(" AND ");
			sb.append(svo.getFilter());
		}
		return sb.toString();
	}

	public List<String> getOutstandingCreditPageData(SearchInvoiceVO svo) {
		logger.info("Enter method getOutstandingCreditPageData.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb
				.append("account_code_id:\"',toJSON(p.account_code_id is null,'',p.account_code_id),'\", ");// ///////////
		// This
		// is
		// the
		// js
		// validate
		sb.append("id:\"',toJSON(p.id is null,'',p.id),'\", ");// ///////////
		// This is the
		// js validate
		sb
				.append("balance_amount:\"',toJSON(p.balance_amount is null,0,p.balance_amount),'\", ");// ////////////
		// This
		// is
		// the
		// js
		// validate
		sb
				.append("creditAmount:\"',toJSON(p.credit_amount is null,'',format(p.credit_amount,2)),'\", ");
		sb
				.append("outstandingAmount:\"',toJSON(p.balance_amount is null,'',format(p.balance_amount,2)),'\", ");
		sb.append("item:\"',toJSON(p.item_name is null,'',p.item_name),'\", ");
		sb
				.append("scoa:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\", ");
		sb
				.append("circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),'\", ");
		sb
				.append("billingNumber:\"',toJSON(p.billing_number is null,'',p.billing_number),'\", ");
		sb
				.append("description:\"',toJSON(p.description is null,'',p.description),'\", ");
		sb.append("address:\"',toJSON(p.address is null,'',p.address),'\", ");
		sb
				.append("serviceType:\"',toJSON(p.service_type is null,'',p.service_type),'\", ");
		sb
				.append("chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),'\", ");
		sb.append("usoc:\"',toJSON(p.usoc is null,'',p.usoc),'\", ");
		sb
				.append("usocDescription:\"',toJSON(p.usoc_description is null,'',p.usoc_description),'\", ");
		sb
				.append("quantity:\"',toJSON(p.quantity is null,'',format(p.quantity,2)),'\", ");
		sb.append("rate:\"',toJSON(p.rate is null,'0.00',p.rate),'\", ");
		sb
				.append("minutes:\"',toJSON(p.minutes is null,'',format(p.minutes,2)),'\", ");
		sb
				.append("numberOfCalls:\"',toJSON(p.number_of_calls is null,'',format(p.number_of_calls,2)),'\", ");
		sb
				.append("cellularIndicator:\"',toJSON(p.cellular_indicator is null,'',p.cellular_indicator),'\", ");
		sb.append("country:\"',toJSON(p.country is null,'',p.country),'\", ");
		sb
				.append("mileage:\"',toJSON(p.mileage is null,'',format(p.mileage,2)),'\", ");
		sb.append("asg:\"',toJSON(p.asg is null,'',p.asg),'\", ");
		sb
				.append("jurisdiction:\"',toJSON(p.jurisdiction is null,'',p.jurisdiction),'\", ");
		sb
				.append("direction:\"',toJSON(p.direction is null,'',p.direction),'\", ");
		sb
		.append("notes:\"',toJSON(p.notes is null,'',p.notes),'\", ");
		sb
				.append("files:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),'\"}')");
		sb.append(getOutstandingCreditWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getOutstandingCreditPageData.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> saveOutstandingCreditPageExcel(SearchInvoiceVO svo) {
		logger.info("Enter method saveOutstandingCreditPageExcel.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("if(p.credit_amount is null,'',format(p.credit_amount,2))");
		sb.append(",if(p.balance_amount is null,'',format(p.balance_amount,2))");
		sb.append(",if(p.item_name is null,'',p.item_name)");
		sb.append(",if(ac.account_code_name is null,'',ac.account_code_name)");
		sb.append(",if(p.circuit_number is null,'',p.circuit_number)");
		sb.append(",if(p.billing_number is null,'',p.billing_number)");
		sb.append(",if(p.description is null,'',p.description)");
		sb.append(",if(p.address is null,'',p.address)");
		sb.append(",if(p.service_type is null,'',p.service_type)");
		sb.append(",if(p.charge_type is null,'',p.charge_type)");
		sb.append(",if(p.usoc is null,'',p.usoc)");
		sb.append(",if(p.usoc_description is null,'',p.usoc_description)");
		sb.append(",if(p.quantity is null,'',format(p.quantity,2))");
		sb.append(",if(p.rate is null,'0.00',p.rate)");
		sb.append(",if(p.minutes is null,'',format(p.minutes,2))");
		sb
				.append(",if(p.number_of_calls is null,'',format(p.number_of_calls,2))");
		sb.append(",if(p.cellular_indicator is null,'',p.cellular_indicator)");
		sb.append(",if(p.country is null,'',p.country)");
		sb.append(",if(p.mileage is null,'',format(p.mileage,2))");
		sb.append(",if(p.asg is null,'',p.asg)");
		sb.append(",if(p.jurisdiction is null,'',p.jurisdiction)");
		sb.append(",if(p.direction is null,'',p.direction)");
		sb.append(",if(p.notes is null,'',p.notes)");
		sb.append(getOutstandingCreditWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method saveOutstandingCreditPageExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public Long getOutstandingCreditPageTotalNo(SearchInvoiceVO svo) {
		logger.info("Enter method getOutstandingCreditPageTotalNo.");
		StringBuffer sb = new StringBuffer("select count(1)");
		sb.append(getOutstandingCreditWhereCause(svo));
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method getOutstandingCreditPageTotalNo.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

	public void updateCreditToCurrentPayment(String inProposalIds) {
		String sql = "update proposal p set p.payment_amount=(p.payment_amount+p.balance_amount),p.credit_amount=(p.credit_amount-p.balance_amount),p.balance_amount=0,p.modified_by="
				+ SystemUtil.getCurrentUserId()
				+ ",p.modified_timestamp='"
				+ CcmFormat.formatDateTime(new Date())
				+ "' where p.id in ("
				+ inProposalIds + ")";
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}

	public void updateOutstandingCreditSplitApply2(Integer invoiceId,
			Long proposalId, AttachmentPoint point, String notes,
			Double balanceAmount, Integer accountCodeId) {
		String sql = "update proposal p set p.payment_amount=(p.payment_amount+?),p.credit_amount=(p.credit_amount-?),p.balance_amount=(p.balance_amount-?),p.modified_by="
				+ SystemUtil.getCurrentUserId()
				+ ",p.modified_timestamp='"
				+ CcmFormat.formatDateTime(new Date())
				+ "',p.attachment_point_id="
				+ (point == null ? "null" : point.getId())
				+ ",p.notes='"
				+ notes + "' where p.id =" + proposalId;
		Session session = getSession();
		try {
			Query q = session.createSQLQuery(sql);
			q.setParameter(0, balanceAmount);
			q.setParameter(1, balanceAmount);
			q.setParameter(2, balanceAmount);
			q.executeUpdate();
		} finally {
			releaseSession(session);
		}
	}

	public List<Proposal> findCredits(String inProposalIds) {
		Session session = getSession();
		try {
			return session.createQuery(
					"from com.saninco.ccm.model.Proposal p where p.id in ("
					+ inProposalIds + ") order by abs(p.balanceAmount) asc").list();
		} finally {
			releaseSession(session);
		}
	}

	public void cleanBlanceAmount(String inProposalIds) {
		String sql = "update proposal p set p.balance_amount=0,p.modified_by="
				+ SystemUtil.getCurrentUserId() + ",p.modified_timestamp='"
				+ CcmFormat.formatDateTime(new Date()) + "' where p.id in ("
				+ inProposalIds + ")";
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}

	public Proposal findCredit(Long proposalId) {
		Session session = getSession();
		try {
			return (Proposal) session.createQuery(
					"from com.saninco.ccm.model.Proposal p where p.id="
					+ proposalId).uniqueResult();
		} finally {
			releaseSession(session);
		}
	}

	public AccountCode loadAccountCode(Integer accountCodeId) {
		Session session = getSession();
		try {
			return (AccountCode) session
			.load(AccountCode.class, accountCodeId);
		} finally {
			releaseSession(session);
		}
	}

	public void addBlanceAmount(double d, Integer proposalId) {
		String sql = "update proposal p set p.balance_amount=(p.balance_amount+"
				+ d
				+ "),p.modified_by="
				+ SystemUtil.getCurrentUserId()
				+ ",p.modified_timestamp='"
				+ CcmFormat.formatDateTime(new Date())
				+ "' where p.id="
				+ proposalId;
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * Credit 标签页中 Outstanding Dispute 子项的内容查询时的查询条件。
	 * @param  svo [description]
	 * @return     [description]
	 */
	private String getNotReconcileDisputeWhereCause(SearchInvoiceVO svo) {
		
		StringBuffer sb = new StringBuffer();
		sb
				.append(" FROM (proposal p left join account_code ac on(p.account_code_id=ac.id");
		//wenbo.zhang
		//2016-07-29
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
			sb.append(" and ac.active_flag = 'Y'");
			sb.append(" and ac.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
		sb.append(" , dispute d, invoice i,dispute_reason dr");
		sb.append(" WHERE p.dispute_id=d.id");
		sb.append(" AND d.invoice_id=i.id");
		sb.append(" AND d.dispute_reason_id=dr.id");
		sb.append(" AND i.ban_id=" + svo.getBanId1());
		sb.append(" AND p.dispute_amount<>0 and p.balance_amount<>0");
		sb.append(" AND d.dispute_status_id>22 AND d.dispute_status_id < 98 ");
		sb.append(" AND p.proposal_flag=1 ");
		sb.append(" AND p.rec_active_flag='Y' ");
		if (svo.getFilter() != null && !"".equals(svo.getFilter())) {
			sb.append(" AND ");
			sb.append(svo.getFilter());
		}
		return sb.toString();
	}

	/**
	 * Credit 标签页中 Outstanding Dispute 子项的内容。
	 * @param  svo [description]
	 * @return     [description]
	 */
	public List getNotReconcileDisputePageData(SearchInvoiceVO svo) {
		logger.info("Enter method getNotReconcileDisputePageData.");
		Integer assignmentUserId = SystemUtil.getCurrentUserId();
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',toJSON(p.id is null,'',p.id),'\", ");
		
		sb.append("flag:\"',if((datediff(now(), d.created_timestamp) > 60 and FN_GET_DISPUTE_LAST_UPDATED_DATE_INTERVAL_BY_CURRENT(d.id) > 30),'Y', 'N'),'\", ");
		sb.append("account_code_id:\"',toJSON(p.account_code_id is null,'',p.account_code_id),'\", ");
		
		sb.append("balance_amount:\"',toJSON(p.balance_amount is null,0,p.balance_amount),'\", ");
		
		sb.append("disputeAmount:\"',toJSON(p.dispute_amount is null,'', format(p.dispute_amount,2)),'\", ");
		sb.append("outstandingAmount:\"',toJSON(p.balance_amount is null,'',format(p.balance_amount,2)),'\", ");
		sb.append("claimNumber:\"',toJSON(d.claim_number is null,'',d.claim_number),'\", ");

		// Dispute Number.
		sb.append("disputeId:\"',toJSON(d.id is null,'',d.id),'\", ");
		sb.append("disputeNumber:\"',toJSON(d.dispute_number is null,'',d.dispute_number),'\", ");

		sb.append("flagShortpaid:\"',toJSON(d.flag_shortpaid is null,'',d.flag_shortpaid),'\", ");
		sb.append("disputeNotes:\"',toJSON(d.notes is null,'',d.notes),'\", ");
		sb.append("proposalNotes:\"',toJSON(p.notes is null,'',p.notes),'\", ");
		sb.append("createdTimestamp:\"',toJSON(d.created_timestamp is null,'',d.created_timestamp),'\", ");
		sb.append("disputeCategory:\"',toJSON(dr.dispute_reason_name is null,'',dr.dispute_reason_name),'\", ");
		sb.append("item:\"',toJSON(p.item_name is null,'',p.item_name),'\", ");
		sb.append("scoa:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\", ");
		sb.append("circuitNumber:\"',toJSON(p.circuit_number is null,'',p.circuit_number),'\", ");
		sb.append("billingNumber:\"',toJSON(p.billing_number is null,'',p.billing_number),'\", ");
		sb.append("description:\"',toJSON(p.description is null,'',p.description),'\", ");
		sb.append("address:\"',toJSON(p.address is null,'',p.address),'\", ");
		sb.append("serviceType:\"',toJSON(p.service_type is null,'',p.service_type),'\", ");
		sb.append("chargeType:\"',toJSON(p.charge_type is null,'',p.charge_type),'\", ");
		sb.append("usoc:\"',toJSON(p.usoc is null,'',p.usoc),'\", ");
		sb.append("usocDescription:\"',toJSON(p.usoc_description is null,'',p.usoc_description),'\", ");
		sb.append("quantity:\"',toJSON(p.quantity is null,'',format(p.quantity,2)),'\", ");
		sb.append("rate:\"',toJSON(p.rate is null,'0.00',p.rate),'\", ");
		sb.append("minutes:\"',toJSON(p.minutes is null,'',format(p.minutes,2)),'\", ");
		sb.append("numberOfCalls:\"',toJSON(p.number_of_calls is null,'',format(p.number_of_calls,2)),'\", ");
		sb.append("cellularIndicator:\"',toJSON(p.cellular_indicator is null,'',p.cellular_indicator),'\", ");
		sb.append("country:\"',toJSON(p.country is null,'',p.country),'\", ");
		sb.append("mileage:\"',toJSON(p.mileage is null,'',format(p.mileage,2)),'\", ");
		sb.append("asg:\"',toJSON(p.asg is null,'',p.asg),'\", ");
		sb.append("jurisdiction:\"',toJSON(p.jurisdiction is null,'',p.jurisdiction),'\", ");
		sb.append("direction:\"',toJSON(p.direction is null,'',p.direction),'\", ");
		sb.append("files:\"',toJSON(p.attachment_point_id is null,'',p.attachment_point_id),'\"}')");
		sb.append(getNotReconcileDisputeWhereCause(svo));
		
		if (svo.getSortField().equals("flag")){
			sb.append(" ORDER BY if((datediff(now(), d.status_timestamp) > 60) AND FN_GET_DISPUTE_LAST_UPDATED_DATE_INTERVAL_BY_CURRENT(d.id) > 30,'Y', 'N') "+svo.getSortingDirection());
		}else{
			sb.append(svo.getOrderByCause(null));
		}
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getNotReconcileDisputePageData.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getNotReconcileDisputePageExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getNotReconcileDisputePageExcel.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" if(p.dispute_amount is null,'', format(p.dispute_amount,2))");
		sb.append(",if(p.balance_amount is null,'',p.balance_amount)");
		sb.append(",if(d.claim_number is null,'',d.claim_number)");

		// Dispute Number.
		sb.append(",if(d.dispute_number is null,'',d.dispute_number)");
		sb.append(",if(d.flag_shortpaid is null,'',d.flag_shortpaid)");
		
		sb.append(",if(d.notes is null,'',d.notes)");
		sb.append(",if(p.notes is null,'',p.notes)");
		
		sb.append(",if(d.created_timestamp is null,'',d.created_timestamp)");
		sb.append(",if(p.dispute_reason_id is null,'',dr.dispute_reason_name)");
		sb.append(",if(p.item_name is null,'',p.item_name)");
		sb.append(",if(ac.account_code_name is null,'',ac.account_code_name)");
		sb.append(",if(p.circuit_number is null,'',p.circuit_number)");
		sb.append(",if(p.billing_number is null,'',p.billing_number)");
		sb.append(",if(p.description is null,'',p.description)");
		sb.append(",if(p.address is null,'',p.address)");
		sb.append(",if(p.service_type is null,'',p.service_type)");
		sb.append(",if(p.charge_type is null,'',p.charge_type)");
		sb.append(",if(p.usoc is null,'',p.usoc)");
		sb.append(",if(p.usoc_description is null,'',p.usoc_description)");
		sb.append(",if(p.quantity is null,'',format(p.quantity,2))");
		sb.append(",if(p.rate is null,'0.00',p.rate)");
		sb.append(",if(p.minutes is null,'',format(p.minutes,2))");
		sb.append(",if(p.number_of_calls is null,'',format(p.number_of_calls,2))");
		sb.append(",if(p.cellular_indicator is null,'',p.cellular_indicator)");
		sb.append(",if(p.country is null,'',p.country)");
		sb.append(",if(p.mileage is null,'',format(p.mileage,2))");
		sb.append(",if(p.asg is null,'',p.asg)");
		sb.append(",if(p.jurisdiction is null,'',p.jurisdiction)");
		sb.append(",if(p.direction is null,'',p.direction) ");
		sb.append(getNotReconcileDisputeWhereCause(svo));
		if (svo.getSortField().equals("flag")){
			sb.append(" ORDER BY if((datediff(now(), d.status_timestamp) > 60) AND FN_GET_DISPUTE_LAST_UPDATED_DATE_INTERVAL_BY_CURRENT(d.id) > 30,'Y', 'N') "+svo.getSortingDirection());
		}else{
			sb.append(svo.getOrderByCause(null));
		}
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getNotReconcileDisputePageExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public long getNotReconcileDisputePageTotalNo(SearchInvoiceVO svo) {
		logger.info("Enter method getOutstandingCreditPageTotalNo.");
		StringBuffer sb = new StringBuffer("select count(1)");
		sb.append(getNotReconcileDisputeWhereCause(svo));
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method getOutstandingCreditPageTotalNo.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

	private Object getTheInvoiceReconciliationWhereCause(SearchInvoiceVO svo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" FROM (((((reconcile r left join proposal dp on(r.dispute_proposal_id=dp.id)) "
						+ " left join reconcile_status rs on(r.reconcile_status_id=rs.id)) "
						+ " left join user u on(r.created_by=u.id))"						
						+ " left join dispute d on(dp.dispute_id=d.id))"
		                + " left join account_code ac on(ac.id=r.account_code_id ");
		//wenbo.zhang
		//2016-07-29
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and ac.active_flag = 'Y'");
					sb.append(" and ac.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
		sb.append(" WHERE r.credit_invoice_id=" + svo.getInvoiceId1());
		sb.append(" AND r.rec_active_flag='Y' ");
		return sb.toString();
	}

	public List getTheInvoiceReconciliationPageData(SearchInvoiceVO svo) {
		logger.info("Enter method getTheInvoiceReconciliationPageData.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',toJSON(r.id is null,'',r.id),'\", ");
		sb
		        .append("disputeId:\"',toJSON(d.id is null,'',d.id),'\", ");
		sb
				.append("item:\"',toJSON(dp.item_name is null,'',dp.item_name),'\", ");
		sb
				.append("scoa:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\", ");
		sb
				.append("claimNumber:\"',toJSON(d.claim_number is null,'',d.claim_number),'\", ");
		sb
				.append("reconcileDate:\"',toJSON(r.reconcile_date is null,'',r.reconcile_date),'\", ");
		sb
				.append("reconcileAmount:\"',toJSON(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),'\", ");
		sb
				.append("reconcileStatus:\"',toJSON(rs.reconcile_status_name is null,'',rs.reconcile_status_name),'\", ");
		sb
				.append("firstName:\"',toJSON(u.first_name is null,'',u.first_name),'\", ");
		sb
				.append("lastName:\"',toJSON(u.last_name is null,'',u.last_name),'\", ");
		sb      
                .append("disputeNotes:\"',toJSON(d.notes is null,'',d.notes),'\", ");
        sb      
                .append("reconcileNotes:\"',toJSON(r.notes is null,'',r.notes),'\", ");
        sb
				.append("files:\"',toJSON(r.attachment_point_id is null,'',r.attachment_point_id),'\"}')");
		sb.append(getTheInvoiceReconciliationWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getTheInvoiceReconciliationPageData.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getTheInvoiceReconciliationPageExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getTheInvoiceReconciliationPageData.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" if(dp.item_name is null,'',dp.item_name)");
		sb.append(",if(ac.account_code_name is null,'',ac.account_code_name)");
		sb.append(",if(d.claim_number is null,'',d.claim_number)");
		sb.append(",if(r.reconcile_date is null,'',r.reconcile_date)");
		sb
				.append(",if(r.reconcile_amount is null,'',format(r.reconcile_amount,2))");
		sb
				.append(",if(rs.reconcile_status_name is null,'',rs.reconcile_status_name)");
		sb
				.append(",CONCAT(if(u.first_name is null,'',u.first_name),' ',if(u.last_name is null,'',u.last_name))");
		sb.append(",if(d.notes is null,'',d.notes)");
		sb.append(",if(r.notes is null,'',r.notes)");
		sb.append(getTheInvoiceReconciliationWhereCause(svo));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getTheInvoiceReconciliationPageData.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public long getTheInvoiceReconciliationPageTotalNo(SearchInvoiceVO svo) {
		logger.info("Enter method getTheInvoiceReconciliationPageTotalNo.");
		StringBuffer sb = new StringBuffer("select count(1)");
		sb.append(getTheInvoiceReconciliationWhereCause(svo));
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method getTheInvoiceReconciliationPageTotalNo.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getCloseAsDisputeLosePageExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getCloseAsDisputeLosePageExcel.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("if(dp.item_name is null,'',dp.item_name),");
		sb
				.append("if(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("if(d.claim_number is null,'',d.claim_number),");
		sb.append("if(r.reconcile_date is null,'',date_format(r.reconcile_date,'%Y-%m-%d')),");
		sb
				.append("if(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),");
		sb
				.append("if(rs.reconcile_status_name is null,'',rs.reconcile_status_name),");
		sb.append("CONCAT(u.first_name,' ',u.last_name),");
		sb.append("if(d.notes is null,'',d.notes), ");
		sb.append("if(r.notes is null,'',r.notes) ");
		sb.append(getCloseAsDisputeLoseAndWinWhereCause(svo,"Lose"));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCloseAsDisputeLosePageExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}
	
	public List<Object[]> getCloseAsDisputeWinPageExcel(SearchInvoiceVO svo) {
		logger.info("Enter method getCloseAsDisputeWinPageExcel.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("if(dp.item_name is null,'',dp.item_name),");
		sb
				.append("if(ac.account_code_name is null,'',ac.account_code_name),");
		sb.append("if(d.claim_number is null,'',d.claim_number),");
		sb.append("if(r.reconcile_date is null,'',date_format(r.reconcile_date,'%Y-%m-%d')),");
		sb
				.append("if(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),");
		sb
				.append("if(rs.reconcile_status_name is null,'',rs.reconcile_status_name),");
		sb.append("CONCAT(u.first_name,' ',u.last_name),");
		sb.append("if(d.notes is null,'',d.notes), ");
		sb.append("if(r.notes is null,'',r.notes) ");
		sb.append(getCloseAsDisputeLoseAndWinWhereCause(svo,"Win"));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCloseAsDisputeWinPageExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public List getCloseAsDisputeLosePageData(SearchInvoiceVO svo) {
		logger.info("Enter method getCloseAsDisputeLosePageData.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',toJSON(r.id is null,'',r.id),'\", ");
		sb
		        .append("disputeId:\"',toJSON(d.id is null,'',d.id),'\", ");
		sb
				.append("item:\"',toJSON(dp.item_name is null,'',dp.item_name),'\", ");
		sb
				.append("scoa:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\", ");
		sb
				.append("claimNumber:\"',toJSON(d.claim_number is null,'',d.claim_number),'\", ");
		sb
				.append("reconcileDate:\"',toJSON(r.reconcile_date is null,'',date_format(r.reconcile_date,'%Y-%m-%d')),'\", ");
		sb
				.append("reconcileAmount:\"',toJSON(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),'\", ");
		sb
				.append("reconcileStatus:\"',toJSON(rs.reconcile_status_name is null,'',rs.reconcile_status_name),'\", ");
		sb
				.append("firstName:\"',toJSON(u.first_name is null,'',u.first_name),'\", ");
		sb
				.append("lastName:\"',toJSON(u.last_name is null,'',u.last_name),'\", ");
		sb
                .append("disputeNotes:\"',toJSON(d.notes is null,'',d.notes),'\", ");
        sb
                .append("reconcileNotes:\"',toJSON(r.notes is null,'',r.notes),'\", ");
        sb
				.append("files:\"',toJSON(r.attachment_point_id is null,'',r.attachment_point_id),'\"}')");
		sb.append(getCloseAsDisputeLoseAndWinWhereCause(svo,"Lose"));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCloseAsDisputeLosePageData.");
			return re;
		} finally {
			releaseSession(session);
		}
	}

	public long getCloseAsDisputeLosePageTotalNo(SearchInvoiceVO svo) {
		logger.info("Enter method getCloseAsDisputeLosePageTotalNo.");
		StringBuffer sb = new StringBuffer("select count(1)");
		sb.append(getCloseAsDisputeLoseAndWinWhereCause(svo,"Lose"));
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method getCloseAsDisputeLosePageTotalNo.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	public List getCloseAsDisputeWinPageData(SearchInvoiceVO svo) {
		logger.info("Enter method getCloseAsDisputeWinPageData.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',toJSON(r.id is null,'',r.id),'\", ");
		sb
		.append("disputeId:\"',toJSON(d.id IS NULL, '', d.id),'\", ");
		sb
				.append("item:\"',toJSON(dp.item_name is null,'',dp.item_name),'\", ");
		sb
				.append("scoa:\"',toJSON(ac.account_code_name is null,'',ac.account_code_name),'\", ");
		sb
				.append("claimNumber:\"',toJSON(d.claim_number is null,'',d.claim_number),'\", ");
		sb
				.append("reconcileDate:\"',toJSON(r.reconcile_date is null,'',date_format(r.reconcile_date,'%Y-%m-%d')),'\", ");
		sb
				.append("reconcileAmount:\"',toJSON(r.reconcile_amount is null,'',format(r.reconcile_amount,2)),'\", ");
		sb
				.append("reconcileStatus:\"',toJSON(rs.reconcile_status_name is null,'',rs.reconcile_status_name),'\", ");
		sb
				.append("firstName:\"',toJSON(u.first_name is null,'',u.first_name),'\", ");
		sb
				.append("lastName:\"',toJSON(u.last_name is null,'',u.last_name),'\", ");

		sb
                .append("disputeNotes:\"',toJSON(d.notes is null,'',d.notes),'\", ");
        sb
                .append("reconcileNotes:\"',toJSON(r.notes is null,'',r.notes),'\", ");
		sb
				.append("files:\"',toJSON(r.attachment_point_id is null,'',r.attachment_point_id),'\"}')");
		sb.append(getCloseAsDisputeLoseAndWinWhereCause(svo,"Win"));
		sb.append(svo.getOrderByCause(null));
		sb.append(svo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getCloseAsDisputeWinPageData.");
			return re;
		} finally {
			releaseSession(session);
		}
	}
	
	public long getCloseAsDisputeWinPageTotalNo(SearchInvoiceVO svo) {
		logger.info("Enter method getCloseAsDisputeWinPageTotalNo.");
		StringBuffer sb = new StringBuffer("select count(1)");
		sb.append(getCloseAsDisputeLoseAndWinWhereCause(svo,"Win"));
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method getCloseAsDisputeWinPageTotalNo.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}	

	private Object getCloseAsDisputeLoseAndWinWhereCause(SearchInvoiceVO svo,String type) {
		StringBuffer sb = new StringBuffer();
		sb
				.append(" FROM ((((((reconcile r left join proposal dp on(r.dispute_proposal_id=dp.id)) "
						+ " left join reconcile_status rs on(r.reconcile_status_id=rs.id)) "
						+ " left join user u on(r.created_by=u.id))");
		sb
		.append( " left join account_code ac on(ac.id=r.account_code_id");
		//wenbo.zhang
		//2016-07-08
		Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(svo
				.getInvoiceId1());
		if (invoiceStatusId < 21) {
					sb.append(" and ac.active_flag = 'Y'");
					sb.append(" and ac.rec_active_flag = 'Y'");
		}
		sb.append(" )) ");
				sb
				.append( " left join dispute d on(dp.dispute_id=d.id))"
						+ " left join invoice i on(d.invoice_id=i.id))");
		sb.append(" WHERE i.ban_id=" + svo.getBanId1());
		if("Lose".equals(type)) {
			sb
			.append(" AND r.rec_active_flag='Y' and rs.id in (5,7) and r.dispute_proposal_id is not null");
		}else {
			sb
			.append(" AND r.rec_active_flag='Y' and rs.id in (1,2) and r.dispute_proposal_id is not null");
		}
		if (svo.getFilter() != null && !"".equals(svo.getFilter())) {
			sb.append(" AND ");
			sb.append(svo.getFilter());
		}
		
		return sb.toString();
	}

	public Long[] getUnLevelAndNotes(Invoice invoice2) {
		logger.info("Enter method getUnLevelAndNotes.");
		String sLevel = "select count(1) from dispute d where d.confidence_level is null and d.rec_active_flag='Y' and d.invoice_id="
				+ invoice2.getId();
//		String sNotes = "select count(1) from dispute d where (d.notes is null or CHAR_LENGTH(d.notes)<10) and d.rec_active_flag='Y' and d.invoice_id="
//				+ invoice2.getId();
		Session session = getSession();
		try {
			BigInteger unLevel = (BigInteger) session.createSQLQuery(sLevel)
			.uniqueResult();
//			BigInteger unNotes = (BigInteger) session.createSQLQuery(sNotes)
//			.uniqueResult();
			logger.info("Exit method getUnLevelAndNotes.");
//			return new Long[] { unLevel.longValue(), unNotes.longValue() };
			return new Long[] { unLevel.longValue(), 0L};
		} finally {
			releaseSession(session);
		}
	}

	public void addOutstandingDisputeSCOA(String id, String scoa, String note) {
		logger.info("Enter method addOutstandingDisputeSCOA.");
		String notes = findProposalNotes(id,note.trim());
		String sql = "update proposal p set p.account_code_id=" + scoa
		        + ",p.notes= '" + notes +"'"
				+ ",p.scoa_source_id= 720501"
				+ ",p.modified_by=" + SystemUtil.getCurrentUserId()
				+ ",p.modified_timestamp='"
				+ CcmFormat.formatDateTime(new Date()) + "' where p.id=" + id;
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method addOutstandingDisputeSCOA.");
	}

	
	public Integer addEmailApprove(Email email) {
		logger.info("Enter method addEmailApprove.");
		Integer newId = null;
		try {
			logger.debug("saving Email Approve");
			newId = (Integer) getHibernateTemplate().save(email);
			email.setId(newId);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
		logger.info("Exit method addEmailApprove.");
		return newId;
	}
	public List<Email> searchEmailApprove() {

		logger.info("Enter method searchEmailApprove.");
		Session session = getSession();
		try {
			
			String queryString = "from Email  where emailStatus in (0,3) and emailType = 'A' and recActiveFlag = 'Y'";
			return (List<Email>)getHibernateTemplate().find(queryString);
		} finally {
			releaseSession(session);
			logger.info("Exit method searchEmailApprove.");
		}

	}
	public void updateEmailApprove(Integer newId,Integer emailStatusId){
		logger.info("Enter method updateEmailApprove.");
		String sql = "update email e set e.email_status=" + emailStatusId
				+ ",e.modified_by=" + SystemUtil.getCurrentUserId()
				+ ",e.modified_timestamp='"
				+ CcmFormat.formatDateTime(new Date()) + "' where e.id ="
				+ newId;
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateEmailApprove.");
	}
	
	public void addOutstandingDisputeSCOAAll(String inProposalId, String scoa , String note) {
		logger.info("Enter method addOutstandingDisputeSCOAAll.");
		
		String notes = findProposalNotes(inProposalId,note);
		String sql = "update proposal p set p.account_code_id=" + scoa
		        + ",p.notes= '" + notes +"'"
				+ ",p.scoa_source_id= 720601"
				+ ",p.modified_by=" + SystemUtil.getCurrentUserId()
				+ ",p.modified_timestamp='"
				+ CcmFormat.formatDateTime(new Date()) + "' where p.id = "+ inProposalId ;
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method addOutstandingDisputeSCOAAll.");
	}
	
	private String findProposalNotes(String proposalId, String note){
		String result = "";
		String sql = "select ifnull(p.notes,'') from proposal p where p.id = "+proposalId;
		Session session = getSession();
		try {
			List l = session.createSQLQuery(sql).list();
			if (l.size() > 0) {
				String proposalNotes = l.get(0).toString();
				if (proposalNotes != null && !"".equals(proposalNotes)) {
					if( note != null && !"".equals(note)){
						result = proposalNotes+" ; "+note;
					}else{
						result = proposalNotes;
					}
				}else{
					result = note;
				}
			}else{
				result = note;
			}
			return result;
		} finally {
			releaseSession(session);
		}
	}

	public void setReconcilePaymentInvoiceId(Invoice invoice) {
		logger.info("Enter method setReconcilePaymentInvoiceId.");
		Session session = getSession();
		try {
			Integer invoiceStatusId = getInvoiceStatusIdByInvoiceId(invoice.getId());
			String sql = "select r.id FROM (((((reconcile r left join payment p on(r.payment_id=p.id)) left join dispute d on(r.dispute_id=d.id)) left join invoice i on(d.invoice_id=i.id)) left join user u on(r.created_by=u.id)) left join account_code a on(r.account_code_id=a.id)) WHERE r.dispute_id is not null and r.reconcile_status_id=7 AND r.rec_active_flag='Y' AND i.ban_id="
				+ invoice.getBan().getId();
			if (invoiceStatusId < 21) {
				sql += " and ((p.invoice_id="
					+ invoice.getId()
					+ ") or (r.payment_id is null and (r.payment_invoice_id is NULL or r.payment_invoice_id="
					+ invoice.getId() + ")))";
			} else {
				sql += " and ((p.invoice_id=" + invoice.getId()
				+ ") or (r.payment_id is null and r.payment_invoice_id="
				+ invoice.getId() + "))";
			}
			List rs = session.createSQLQuery(sql).list();
			for (Object r : rs) {
				session.createSQLQuery(
						"update reconcile rr set rr.payment_invoice_id="
						+ invoice.getId() + " where rr.id=" + r)
						.executeUpdate();
			}
			
			String sql2 ="select r.id FROM (((((reconcile r left join payment p on(r.payment_id=p.id)) left join dispute d on(r.dispute_id=d.id)) left join invoice i on(d.invoice_id=i.id)) left join user u on(r.created_by=u.id)) left join account_code a on(r.account_code_id=a.id)) WHERE r.dispute_id is not null and r.reconcile_status_id=5 AND r.rec_active_flag='Y' AND i.ban_id="
				+ invoice.getBan().getId();
			if (invoiceStatusId < 21) {
				sql2 += " and ((p.invoice_id="
					+ invoice.getId()
					+ ") or (r.payment_id is null and (r.payment_invoice_id is NULL or r.payment_invoice_id="
					+ invoice.getId() + ")))";
			} else {
				sql2 += " and ((p.invoice_id=" + invoice.getId()
				+ ") or (r.payment_id is null and r.payment_invoice_id="
				+ invoice.getId() + "))";
			}
			
			List rs2 = session.createSQLQuery(sql2).list();
			for (Object r : rs2) {
				session.createSQLQuery(
						"update reconcile rr set rr.payment_invoice_id="
						+ invoice.getId() + " where rr.id=" + r)
						.executeUpdate();
			}
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method setReconcilePaymentInvoiceId.");
	}

	public Integer getInvoiceStatusIdByInvoiceId(Integer id) {
		Session session = getSession();
		try {
			return (Integer) session.createSQLQuery(
					"select i.invoice_status_id from invoice i where i.id=" + id)
					.uniqueResult();
		} finally {
			releaseSession(session);
		}
	}

	public Integer getInvoiceStatusIdByInvoiceId(String invoiceId) {
		Integer id = Integer.valueOf(invoiceId);
		return getInvoiceStatusIdByInvoiceId(id);
	}

	public void clearReconcilePaymentInvoiceId(Invoice invoice) {
		logger.info("Enter method clearReconcilePaymentInvoiceId.");
		//mod by yinghe.fu 20120229
		String sql = "update reconcile r set r.payment_invoice_id=null where r.reconcile_status_id in (5,7) AND r.rec_active_flag='Y' AND r.payment_invoice_id="
				+ invoice.getId();
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method clearReconcilePaymentInvoiceId.");
	}

	public void recodeTaxInvoice(String invoiceId) {
		logger.info("Enter method recodeTaxInvoice.");
		Session session = getSession();
		try {
			Connection conn = session.connection();
			CallableStatement proc = conn
					.prepareCall("{ call sp_scoa_tax_recode(?,?) }");
			proc.setString(1, invoiceId);
			proc.registerOutParameter(2, Types.VARCHAR);
			proc.execute();
			String testPrint = proc.getString(2);
			// System.out.println("=testPrint=is="+testPrint);
		} catch (Exception e) {
			logger.error("", e);
			throw new RuntimeException(e);
		} finally {
			releaseSession(session);
		}

		logger.info("Exit method recodeTaxInvoice.");
	}

	public AttachmentPoint loadAttachmentPoint(String attachmentPointId) {
		if (attachmentPointId == null || "".equals(attachmentPointId)) {
			return null;
		}
		return (AttachmentPoint) getHibernateTemplate().load(AttachmentPoint.class,
				Integer.parseInt(attachmentPointId));
	}

	/**
	 * Update invoice notes Add by Xin Huang on 2011-08-31
	 */
	public void updateInvoiceNotes(SearchInvoiceVO svo) {
		logger.info("Enter method updateInvoiceNotes.");
		String notes = null;
		if (svo.getNote() != null) {
			notes = "'" + DaoUtil.ccmEscape(svo.getNote()) + "'";
		} else {
			notes = "null";
		}
		StringBuffer sb = new StringBuffer("update invoice i set i.notes = ");
		sb.append(notes);
		sb.append(" where i.id = ");
		sb.append(svo.getInvoiceId());
		sb.append(" and i.assigned_analyst_id = "); // Make sure analyst can
		// only modify own invoice
		sb.append(SystemUtil.getCurrentUserId());
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method setBanProcessStatus.");
	}
	
	public Map<String, Object> checkApprovePrivilege(Integer invoiceId) throws SQLException {
		logger.info("Enter method checkWorkflowPrivilege.");
		Session session = getSession();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_GET_APPROVAL_BUTTON(?,?,?,?,?)");
			// V_USER_ID INT, V_INVOICE_ID INT,OUT V_RETURN_CODE VARCHAR(10) ,OUT V_MESSAGE_IDS TEXT  ,OUT V_NEXT_INVOICE_STATUS_ID
			proc.setInt(1, SystemUtil.getCurrentUserId());
			proc.setInt(2, invoiceId);
			proc.registerOutParameter(3, Types.VARCHAR); 
			proc.registerOutParameter(4, Types.VARCHAR); 
			proc.registerOutParameter(5, Types.INTEGER); 

			proc.execute();
			
			String returnCode = proc.getString(3);
			String messageIds = proc.getString(4);
			Integer nextStatusId = proc.getInt(5);
			
			result.put("returnCode", returnCode);
			result.put("messageIds", messageIds);
			result.put("nextStatusId", nextStatusId);
			return result;
		} finally {
			logger.info("Exit method checkWorkflowPrivilege.");
			releaseSession(session);
		}
	}
	
	public void callSpApproveInvoices(Integer invoiceId, Integer workflowActionId, String notes,String oldInvoiceStatusId) throws SQLException {
		logger.info("Enter method callSpApproveInvoices.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_APPROVE_INVOICES(?,?,?,?,?,?,?)");
			// SP_APPROVE_INVOICES(V_USER_ID INT, V_INVOICE_ID INT, V_WORKFLOW_ACTION_ID INT, V_NOTES VARCHAR(768),OUT V_RETURN_CODE VARCHAR(10) ,OUT V_MESSAGE TEXT)
			proc.setInt(1, SystemUtil.getCurrentUserId());
			proc.setInt(2, invoiceId);
			proc.setInt(3, workflowActionId);
			try {
				proc.setInt(4, Integer.valueOf(oldInvoiceStatusId));
			} catch (NumberFormatException e) {
				proc.setInt(4, -1);
			}
			proc.setString(5, notes);
			proc.registerOutParameter(6, Types.VARCHAR); 
			proc.registerOutParameter(7, Types.VARCHAR); 

			proc.execute();
			
			String returnCode = proc.getString(6);
			String message = proc.getString(7);
			
			if (!"SUCCESS".equals(returnCode)) {
				throw new RuntimeException(message);
			}
			
		} finally {
			logger.info("Exit method callSpApproveInvoices.");
			releaseSession(session);
		}
	}
	
	public String showMessages(String messageIds) {
		logger.info("Enter method showMessages.");
		StringBuffer message = new StringBuffer();
		if (messageIds != null && !"".equals(messageIds)) {
			StringBuffer sb = new StringBuffer("select message_description from message");
			sb.append(" where id in (");
			sb.append(messageIds);
			sb.append(")"); 
			Session session = getSession();
			try {
				List<String> list = (List<String>)session.createSQLQuery(sb.toString()).list();
				for (String s : list) {
					message.append(s);
					message.append("<br/>");
				}
			} finally {
				releaseSession(session);
			}
		}
		logger.info("Exit method setBanProcessStatus.");
		return message.toString();
	}
	public void updateActionRequest(Integer invoiceId){
		logger.info("Enter method updateActionRequest.");
		
		final String queryString = "UPDATE dispute_action_request dar SET dar.dispute_action_request_status_id = 1 WHERE dar.dispute_id IN " +
		                             "(SELECT d.id FROM dispute d, invoice i WHERE d.invoice_id = i.id AND i.account_number = "+
		                             "(SELECT ii.account_number FROM invoice ii WHERE ii.id = "+invoiceId+")"+
		                             " AND i.rec_active_flag = 'Y' AND d.rec_active_flag = 'Y') AND dar.dispute_action_request_status_id = 2"+
		                             " AND rec_active_flag = 'Y'";
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		
		logger.info("Exit method updateActionRequest.");
	}
	
	public List<Map<String,Object>> searchInvoiceOriginal(String invoiceId) {
        logger.info("Enter method searchInvoiceOriginal.");
		
		final String sql = "SELECT file_name, file_path  FROM original where  rec_active_flag = 'Y' and invoice_id = " + invoiceId;
		
		Session session = getSession();

		List list;
		
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		
		logger.info("Exit method searchInvoiceOriginal.");
		
		return list;
	}
	
	public Long selectInvoiceValidationByInvoiceIdCount(String invoiceId) {
		logger.info("Enter method selectInvoiceValidationByInvoiceIdCount.");
		StringBuffer sb = new StringBuffer(selectInvoiceValidationByInvoiceIdCountSql(invoiceId));
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method selectInvoiceValidationByInvoiceIdCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	public Long selectTelephoneNumberValidationByInvoiceIdCount(String invoiceId) {
		logger.info("Enter method selectTelephoneNumberValidationByInvoiceIdCount.");
		StringBuffer sb = new StringBuffer(selectTelephoneNumberValidationByInvoiceIdCountSql(invoiceId));
		Session session = getSession();
		try {
			BigInteger count = (BigInteger) session.createSQLQuery(
					sb.toString()).uniqueResult();
			logger.info("Exit method selectTelephoneNumberValidationByInvoiceIdCount.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	public List<Object[]> getInvoiceValidationToExcel(String invoiceId) {
		logger.info("Enter method getInvoiceToExcel.");
		Session session = getSession();
		
		try {
			List<Object[]> re = session.createSQLQuery(selectInvoiceValidationByInvoiceIdForExcel(invoiceId)).list();
			logger.info("Exit method getDisputePaybackPayment.");
			return re;
		} finally {
			releaseSession(session);
		}
	}
	
	public List<Object[]> getTelephoneNumberValidationToExcel(String invoiceId) {
		logger.info("Enter method selectTelephoneNumberValidationByInvoiceIdForExcel.");
		Session session = getSession();
		
		try {
			List<Object[]> re = session.createSQLQuery(selectTelephoneNumberValidationByInvoiceIdForExcel(invoiceId)).list();
			logger.info("Exit method selectTelephoneNumberValidationByInvoiceIdForExcel.");
			return re;
		} finally {
			releaseSession(session);
		}
	}
	
	public String selectTelephoneNumberValidationByInvoiceIdCountSql(String invoiceId) {
		String sql = "SELECT count(1) from (" + selectTelephoneNumberValidationByInvoiceIdForExcel(invoiceId)+") a";
		return sql;
	}
	
	
	public String selectTelephoneNumberValidationByInvoiceIdForExcel(String invoiceId) {
		String sql = "SELECT concat(a.line,''), a.telephone_number, a.action, a.s_e_indicator, a.collect_indicator," +
				" a.bill_third_indicator, a.time_stamp, a.complete, a.validation_result" +
				" FROM audit_bns_telephone_number a WHERE a.audit_bns_id IN" +
				"(SELECT ab.id FROM audit_bns ab, invoice i " +
				"WHERE ab.ban_id = i.ban_id AND ab.invoice_date = DATE_FORMAT(i.invoice_date, '%Y%m') AND i.id = "+invoiceId+")";
		return sql;
	}
	

	public String selectInvoiceValidationByInvoiceIdCountSql(String invoiceId) {
		String sql = "SELECT count(1)" + selectInvoiceValidationByInvoiceIdWhere(invoiceId);
		return sql;
	}
	
	
	public String selectInvoiceValidationByInvoiceIdForExcel(String invoiceId) {
		
		// 拼接 SQL 字符串
		StringBuffer sb = new StringBuffer("SELECT ");
		sb.append(" v.vendor_name AS vendorName, ");
		sb.append(" b.account_number AS accountNumber, ");
		sb.append(" i.invoice_number AS invoiceNumber, ");
		sb.append(" DATE_FORMAT(i.invoice_date,'%Y-%m-%d') AS invoiceDate, ");
		sb.append(" pd.product_name AS productName, ");
		sb.append(" pc.component_name AS componentName, ");
		sb.append(" it.item_type_summary AS chargeType, ");
		sb.append(" p.circuit_number AS circuitNumber, ");
		sb.append(" p.usoc AS usoc, ");
		sb.append(" IFNULL(ii.usoc_description,ii.usoc_long_description) AS usocDescription, ");
		sb.append(" audit_status.audit_status_name AS auditStatus, ");
		sb.append(" audit_source.audit_source_name AS auditMethod, ");
		sb.append(" REPLACE(REPLACE(a.notes, '<br/>', ' '), '\\n', ' ') AS auditNotes, ");
		sb.append(" a.actual_amount AS actualAmount, ");
		sb.append(" a.expect_amount AS expectAmount, ");
		sb.append(" IFNULL(p.quantity,p.minutes) AS quantity, ");
		sb.append(" p.rate AS actualRate, ");
		sb.append(" a.rate AS expectRate, ");
		sb.append(" DATE_FORMAT(a.rate_effective_date,'%Y-%m-%d') AS effectiveDate, ");
		sb.append(" rt.audit_reference_type_name AS referenceType, ");
		
		sb.append(" ( CASE ");
		sb.append(" 	WHEN a.audit_reference_type_id = 3 THEN c.name ");
		sb.append(" 	WHEN a.audit_reference_type_id = 2 THEN t.name ");
		sb.append(" 	ELSE pl.name ");
		sb.append(" END) AS referenceName,");
		
		sb.append(" p.item_name AS itemName, ");
		sb.append(" CONCAT(p.id,'') AS proposalId ");
		
		// 拼接 Where 条件。
		sb.append( selectInvoiceValidationByInvoiceIdWhere(invoiceId) );
		
		return sb.toString();
	}
	
	public String selectInvoiceValidationByInvoiceIdWhere(String invoiceId) {
		String sql = 
				" FROM audit_result a LEFT JOIN audit_status ON a.audit_status_id = audit_status.id " +
				" LEFT JOIN audit_source ON a.audit_source_id = audit_source.id " +
				" LEFT JOIN audit_reference_type rt ON a.audit_reference_type_id = rt.id " +
				" LEFT JOIN contract c on c.id = a.audit_reference_id and  a.audit_reference_type_id = 3" +
				" LEFT JOIN tariff t on t.id =  a.audit_reference_id and a.audit_reference_type_id = 2" +
				" LEFT JOIN price_list pl on pl.id  =  a.audit_reference_id and a.audit_reference_type_id = 5" +
				" LEFT JOIN proposal p on p.id = a.proposal_id" +
				" LEFT JOIN invoice i on i.id = a.invoice_id" +
				" LEFT JOIN ban b on i.ban_id = b.id" +
				" LEFT JOIN vendor v on b.vendor_id = v.id" +
				" LEFT JOIN product pd on a.product_id = pd.id " +
				" LEFT JOIN product_component pc on pc.id = p.product_component_id" +
				" LEFT JOIN item_type it on it.id = p.item_type_id " +
				" LEFT JOIN invoice_item ii on ii.id = p.invoice_item_id and ii.rec_active_flag = 'Y'" +
				" WHERE a.invoice_id = "+invoiceId+";";
		return sql;
	}
	
	public List<Object[]> getAuditStatusDetailList(String invoiceId) {
		logger.info("Enter method getAuditStatusDetailList.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" f_get_root_item_type(p.item_type_id) AS item_type_id,");
		sb.append(" count(DISTINCT if(p.audit_status_id = 1,p.id,null)) as passed_count,");
		sb.append(" count(DISTINCT if(p.audit_status_id = 2,p.id,null)) as failed_count,");
		sb.append(" count(DISTINCT if(p.audit_status_id = 3,p.id,null)) as cannot_validate_count");
		sb.append(" FROM proposal p");
		sb.append(" WHERE");
		sb.append(" p.invoice_id = ");
		sb.append(invoiceId);
		sb.append(" AND   p.rec_active_flag = 'Y'");
		sb.append(" GROUP BY f_get_root_item_type(p.item_type_id);");
		
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getAuditStatusDetailList.");
			return re;
		} finally {
			releaseSession(session);
		}
	}
	
	public Object[] searchAuditMemory(String proposalId){
		logger.info("Enter method searchAuditMemory.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("b.id, b.account_number,p.charge_type,p.circuit_number,p.usoc,p.item_name,ii.line_item_code, p.description");
		sb.append(" FROM proposal p,invoice_item ii,invoice i,ban b ");
		sb.append(" WHERE     p.invoice_item_id = ii.id AND p.invoice_id = i.id AND i.ban_id = b.id AND p.id = " +proposalId);
		sb.append(" AND b.rec_active_flag = 'Y' AND p.rec_active_flag = 'Y' AND ii.rec_active_flag = 'Y' AND i.rec_active_flag = 'Y'");
		Session session = getSession();
		try {
			Object[] obj = (Object[])session.createSQLQuery(sb.toString()).uniqueResult();;
			logger.info("Exit method searchAuditMemory.");
			return obj;
		} finally {
			releaseSession(session);
		}
		
		
	}
	
	public Object[] searchAuditMemoryDynamic(Object[] obj) {
		logger.info("Enter method searchAuditMemoryDynamic.");
		StringBuffer sb = new StringBuffer("SELECT am.id,am.rate,am.product_id,am.product_component_id,am.province_id FROM audit_memory am ");
		sb.append("  WHERE     am.ban_id = " +obj[0]);
		if (obj[2] == null) { 
			sb.append(" AND (am.charge_type is null or am.charge_type ='')");
		}else{
			sb.append(" AND am.charge_type = '"+obj[2]+"'");
		}
		if (obj[3] == null) { 
			sb.append(" AND (am.circuit_number is null or am.circuit_number ='')");
		}else{
			sb.append(" AND am.circuit_number = '"+obj[3]+"'");
		}
		if (obj[4] == null) { 
			sb.append(" AND (am.usoc is null or am.usoc ='')");
		}else{
			sb.append(" AND am.usoc = :usoc");
		}
		if (obj[5] == null) { 
			sb.append(" AND (am.item_name is null or am.item_name ='')");
		}else{
			sb.append(" AND am.item_name = :item_name");
		}
		if (obj[6] == null) { 
			sb.append(" AND (am.line_item_code is null or am.line_item_code ='')");
		}else{
			sb.append(" AND am.line_item_code = :line_item_code");
		}
		if (obj[7] == null) { 
			sb.append(" AND (am.description is null or am.description ='')");
		}else{
			sb.append(" AND am.description = :description");
		}
		sb.append(" AND am.rec_active_flag = 'Y' LIMIT 1");
		Session session = getSession();
		try {
			
			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
			if (obj[4] != null) { 
				sqlQuery.setParameter("usoc", obj[4]);
			}
			if (obj[5] != null) { 
				sqlQuery.setParameter("item_name", obj[5]);
			}
			if (obj[6] != null) { 
				sqlQuery.setParameter("line_item_code", obj[6]);
			}
			if (obj[7] != null) { 
				sqlQuery.setParameter("description", obj[7]);
			}
			Object[] objResult = (Object[])sqlQuery.uniqueResult();
			
			logger.info("Exit method searchAuditMemoryDynamic.");
			return objResult;
		} finally {
			releaseSession(session);
		}
	}
	
	public String saveMemory(AuditMemory auditMemory){
		logger.info("Enter method saveMemory.");
		
		
		final StringBuffer sb = new StringBuffer("insert into audit_memory (ban_id, charge_type, circuit_number, stripped_circuit_number, usoc, item_name, description, line_item_code, rate, product_id, product_component_id, province_id, created_timestamp, created_by, modified_timestamp, modified_by, rec_active_flag) VALUES(");
		sb.append(auditMemory.getBanId()+",'"+auditMemory.getChargeType()+"','"+auditMemory.getCircuitNumber()+"',f_translate('"+auditMemory.getCircuitNumber()+"', '(.\\_/-:) ',''),'");
		
		sb.append(auditMemory.getUsoc()+"','"+auditMemory.getItemName()+"','"+auditMemory.getDescription()+"','"+auditMemory.getLineItemCode()+"',");
		sb.append(auditMemory.getRate() == null ? "0" : auditMemory.getRate());
		sb.append (","+auditMemory.getProductId());
		sb.append(","+auditMemory.getProductComponentId());
		sb.append(","+auditMemory.getProvinceId());
		sb.append(",now(),"+SystemUtil.getCurrentUserId()+",now(),"+SystemUtil.getCurrentUserId()+",'Y')");
		Session session = getSession();
		
		try {
			SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
			sqlQuery.executeUpdate();
		} finally {
			releaseSession(session);
		}

		HibernateTemplate template = this.getHibernateTemplate();
		String auditMemoryId = template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select last_insert_id();").uniqueResult();
			}
		}).toString();
		logger.info("Enter method saveMemory.");
		return auditMemoryId;
	}
	
	public void updateMemory(AuditMemory auditMemory){
        logger.info("Enter method updateMemory.");	
        final StringBuffer sb = new StringBuffer("update audit_memory set rate=");
        sb.append(auditMemory.getRate() == null ? "0" : auditMemory.getRate());
        sb.append(", product_id="+auditMemory.getProductId()+", product_component_id="+auditMemory.getProductComponentId()+", province_id="+auditMemory.getProvinceId()+" where id = "+auditMemory.getId());
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sb.toString()).executeUpdate();
			}
		});
		
		logger.info("Exit method updateMemory.");
	}
	
	/**
	 * 检查 当前invoice_id 所属的ban 在 bill_keep_ban 表中 是否存在
	 */
	public Boolean isBillKeepBan(String invoiceId) {
		
		Boolean isExist = false;
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" count(1) as banCount from invoice i, bill_keep_ban b where i.ban_id = b.ban_id and i.id = ");
		sb.append(invoiceId);
		
		Session session = getSession();
		
		try {
			BigInteger count = (BigInteger)session.createSQLQuery(sb.toString()).uniqueResult();
			int i = count.intValue();
			if(i > 0){
				isExist = true;
			}
		} finally {
			releaseSession(session);
		}
		
		return isExist;
	}
	/**
	 * 检查 当前invoice_id 所属的ban 是否为Cabs CD Ban
	 */
	public Boolean isCabsCDBan(String invoiceId) {
		Boolean isExist = false;
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" count(1) as banCount FROM ban b, invoice i WHERE b.id = i.ban_id and i.id = " + invoiceId);
		sb.append(" AND b.invoice_structure_id = 31");
		sb.append(" AND (substring(b.account_number, 4, 1) = 'C'");
		sb.append(" OR substring(b.account_number, 4, 1) = 'D')");
		sb.append(" AND b.rec_active_flag = 'Y'");
		
		Session session = getSession();
		
		try {
			BigInteger count = (BigInteger)session.createSQLQuery(sb.toString()).uniqueResult();
			int i = count.intValue();
			if(i > 0){
				isExist = true;
			}
		} finally {
			releaseSession(session);
		}
		
		return isExist;
	}
	
	public long autopaySwitch(String invoiceId){
		logger.info("Enter method autopaySwitch.");
		
		// 显示当前账单是否是AutoPay的.
		final StringBuffer sb = new StringBuffer("select count(1) from payment_autopay_journal where invoice_id = "+ invoiceId + ";");
//		final StringBuffer sb = new StringBuffer("select count(1) from invoice i where i.id = " + invoiceId + " and i.invoice_status_id >= 21 ");
//		sb.append(" and ( not exists (select * from history_db.invoice_history ih where ih.id = i.id and ih.invoice_status_id = 10 ) ");
//		sb.append(" or exists (select * from history_db.invoice_history ih where ih.id = i.id and ih.invoice_status_id = 21 and ih.modified_by = 121 )); ");
//		final StringBuffer sb = new StringBuffer("SELECT count(0) FROM ban b, invoice i ");
//		sb.append(" WHERE i.ban_id = b.id  and b.autopay_flag = 'Y' AND ");
//		sb.append("i.id = " + invoiceId );
		Session session = getSession();
		try {
			BigInteger count = (BigInteger)session.createSQLQuery(sb.toString()).uniqueResult();
			
			return count.longValue();
			
		} finally {
			logger.info("Exit method autopaySwitch.");
			releaseSession(session);
		}
		
	}
		
	public List<Object[]> getSearchProductList(String proposalId){
		logger.info("Enter method getSearchProductList.");
		Session session = getSession();
		try {
			
			List l = session.createSQLQuery("select p.id, p.product_name from product p where p.rec_active_flag = 'y' and p.product_name != 'all' group by p.product_name;").list();
			logger.info("Exit method getSearchProductList.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	public List<Object[]> getSearchProductComponentList(){
		logger.info("Enter method getSearchProductComponentList.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select id ,component_name from product_component where rec_active_flag='Y'  and component_name!='ALL'").list();
			logger.info("Exit method getSearchProductComponentList.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	public List<Object[]> getSearchProvinceList(){
		logger.info("Enter method getSearchProvinceList.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select id,province_name from province where  province_name!='ALL'").list();
			logger.info("Exit method getSearchProvinceList.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	public void updateAutopaySwitch(Integer banId,String autopaySwitch){
		logger.info("Enter method updateAutopaySwitch.");
		final StringBuffer sb = new StringBuffer(" update ban set autopay_flag = '"+ autopaySwitch );
		sb.append("' ,autopay_count = (select value from sys_config where parameter = 'Auto_Pay_Max_Count'),autopay_max_count = (select value from sys_config where parameter = 'Auto_Pay_Max_Count') where id = " +banId);
		Session session = getSession();
		try {
			session.createSQLQuery(sb.toString()).executeUpdate();
		
		} finally {
			logger.info("Exit method updateAutopaySwitch.");
			releaseSession(session);
		}
		
	}
	
	/**
	 * 根据proposal id 查询item_type_id
	 */
	public String getItemTypeByProposalId(String proposalId) {
		logger.info("Enter method getItemTypeByProposalId.");
		final String sql = "select f_get_root_item_type(item_type_id) from proposal where id = " + proposalId + ";";
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Enter method getItemTypeByProposalId.");
		return list.get(0).toString();
	}
	
	public Map<String, Object> queryTariffValidationDetail(String referenceId, String referenceTypeId, String auditResultId) {
		
		// 获取通过auditResultId来获取invoice_date
//		String invoiceDate = this.getInvoiceDate(auditResultId);

		// 通过invoiceDate来获取rate 和 rate_effective_date 信息。
		String rateReferenceInfo = this.getRateReferenceInfo(referenceId, referenceTypeId);

		// String rate = rateReferenceInfoMap.get("rate").toString();
		// String rateEffectiveDate = rateReferenceInfoMap.get("rateEffectiveDate").toString();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
        final String queryString = this.queryTCPValidationDetail(referenceId, referenceTypeId);
        
        HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				return session.createSQLQuery(queryString).list();
			}
		});
		if(list != null && list.size() == 1){
			Object[] obj = list.get(0);
			if ("2".equals(referenceTypeId)) {
				map.put("file_name", obj[0] == null? "" : obj[0].toString());
				map.put("page",  obj[1] == null? "" : obj[1].toString());
				map.put("part_section",  obj[2] == null? "" : obj[2].toString());
				map.put("item_number",  obj[3] == null? "" : obj[3].toString());
				map.put("paragraph",  obj[4] == null? "" : obj[4].toString());
				map.put("tariff_last_revised",  obj[5] == null? "" : obj[5].toString());
				map.put("service_type",  obj[6] == null? "" : obj[6].toString());
				map.put("band",  obj[7] == null? "" : obj[7].toString());
				map.put("provider",  obj[8] == null? "" : obj[8].toString());
				map.put("rate_mode",  obj[9] == null? "" : obj[9].toString());
				// map.put("rate",  obj[10] == null? "" : obj[10].toString());
//				map.put("rate",  rate == null? "" : rate);
				map.put("rate_reference_info",  rateReferenceInfo == null? "" : rateReferenceInfo);
//				map.put("rate_effective_date",  rateEffectiveDate == null? "" : rateEffectiveDate);
				map.put("attachment_point_id",  obj[11] == null? "" : obj[11].toString());
			} else if("3".equals(referenceTypeId)) {
				map.put("file_name", obj[0] == null? "" : obj[0].toString());
				map.put("expiry_date",  obj[1] == null? "" : obj[1].toString());
				map.put("service_type",  obj[2] == null? "" : obj[2].toString());
				map.put("usoc_description",  obj[3] == null? "" : obj[3].toString());
				map.put("circuit_number",  obj[4] == null? "" : obj[4].toString());
				// map.put("rate",  obj[5] == null? "" : obj[5].toString());
//				map.put("rate",  rate == null? "" : rate);
				map.put("rate_reference_info",  rateReferenceInfo == null? "" : rateReferenceInfo);
//				map.put("rate_effective_date",  rateEffectiveDate == null? "" : rateEffectiveDate);
				map.put("amendment",  obj[6] == null? "" : obj[6].toString());
				map.put("schedule",  obj[7] == null? "" : obj[7].toString());
				map.put("attachment_point_id",  obj[8] == null? "" : obj[8].toString());
			} else if("5".equals(referenceTypeId)) {
				map.put("file_name", obj[0] == null? "" : obj[0].toString());
				map.put("service_type",  obj[1] == null? "" : obj[1].toString());
				map.put("band",  obj[2] == null? "" : obj[2].toString());
				map.put("rate_mode",  obj[3] == null? "" : obj[3].toString());
				// map.put("rate",  obj[4] == null? "" : obj[4].toString());
//				map.put("rate",  rate == null? "" : rate);
//				map.put("rate_effective_date",  rateEffectiveDate == null? "" : rateEffectiveDate);
				map.put("effective_date",  obj[5] == null? "" : obj[5].toString());
			}
			
			
		}
		logger.info("Enter method queryTariffValidationDetail.");
		return map;
	}

	/**
	 * 获取 audit reference 信息。
	 */
	private String getRateReferenceInfo(String referenceId, String referenceTypeId) {

		String rateReferenceInfo = null;
		String referenceType = null;
		StringBuffer querySqlSB = new StringBuffer();


		// 获取和当前验证规则有关的表
		if ( "2".equals(referenceTypeId) ) {
			referenceType = "tariff";
		} else if ( "3".equals(referenceTypeId) ) {
			referenceType = "contract";
		} else if ( "18".equals(referenceTypeId) ) {
			referenceType = "mtm";
		}

		querySqlSB.append("SELECT FN_GET_AUDIT_RATE_TEXT('"+ referenceType +"', "+ referenceId +") ");

		Session session = getSession();
		
		try {
			rateReferenceInfo = session.createSQLQuery( querySqlSB.toString() ).uniqueResult().toString();
			
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			releaseSession(session);
		}

		return rateReferenceInfo;


	}

	/**
	 * 通过auditResultId来获取invoiceId, 再通过invoiceId来获取invoiceDate.
	 */
	private String getInvoiceDate(String auditResultId) {

		String invoiceDate = null;

		String querySql = "select invoice_date from invoice where id = (select invoice_id from audit_result where id = "+ auditResultId +")";

		Session session = getSession();
		
		try {
			invoiceDate = session.createSQLQuery(querySql).uniqueResult().toString();
			
		} finally {
			releaseSession(session);
		}

		return invoiceDate;


	}
	
	/**
	 * Tariff, Contract, Price List验证详细查询的 sql 语句拼写
	 */
	private String queryTCPValidationDetail(String referenceId, String referenceTypeId) {
		StringBuffer sb = new StringBuffer();
		
		if ("2".equals(referenceTypeId)) {
			// Tariff
			sb.append(" SELECT tf.tariff_name AS file_name,");
			sb.append(" t.page,");
			sb.append(" t.part_section,");
			sb.append(" t.item_number,");
			sb.append(" t.paragraph,");
			sb.append(" DATE_FORMAT(t.tariff_last_revised,'%Y-%m-%d') AS tariff_last_revised,");
			sb.append(" t.service_type,");
			sb.append(" t.band,");
			sb.append(" t.provider,");
			sb.append(" (CASE WHEN t.rate_mode = 'tariff_rate_by_distance' THEN 'Rate by Mileage'");
			sb.append(" WHEN t.rate_mode = 'order_date_check'THEN 'Rate by Order Date'");
			sb.append(" WHEN t.rate_mode = 'tariff_rate_by_term' THEN 'Rate by Term'");
			sb.append(" WHEN t.rate_mode = 'tariff_rate_by_province' THEN 'Rate by Province'");
			sb.append(" WHEN (t.rate_mode = 'tariff_rate_by_trunk' OR t.rate_mode = 'tariff_rate_by_trunk_province') THEN 'Rate by Trunk'");
			sb.append(" ELSE 'Rate' END) AS rate_mode,");
			sb.append(" FN_GET_AUDIT_RATE_TEXT('tariff', t.id) as rate,");
			sb.append(" tf.attachment_point_id");
			sb.append(" FROM tariff t INNER JOIN tariff_file tf ON t.tariff_file_id = tf.id");
			sb.append(" where t.id = " + referenceId);
		} else if("3".equals(referenceTypeId)) {
			//Contract
			sb.append(" SELECT cf.contract_number AS file_name,");
			sb.append(" cf.expiry_date,");
			sb.append(" c.service_type,");
			sb.append(" c.usoc_description,");
			sb.append(" c.circuit_number,");
			sb.append(" (CASE WHEN c.rate_mode = 'rate_any' " +
					"THEN (SELECT GROUP_CONCAT(FORMAT(a.rate,2) SEPARATOR ' or ') " +
					"FROM contract_rate_by_any a " +
					"WHERE a.contract_id = c.id) " +
					"ELSE FORMAT(c.rate,2) END) AS rate,");
			sb.append(" c.amendment,");
			sb.append(" c.schedule,");
			sb.append(" cf.attachment_point_id");
			sb.append(" FROM contract c INNER JOIN contract_file cf ON c.contract_file_id = cf.id");
			sb.append(" where c.id = " + referenceId);
		} else if("5".equals(referenceTypeId)) {
			//Price List
			sb.append(" SELECT plf.price_list_name AS file_name,");
			sb.append(" pl.service_type,");
			sb.append(" pl.band,");
			sb.append(" (CASE WHEN pl.rate_mode = 'rate_by_term' THEN 'Rate by Term' ELSE 'Rate' END) AS rate_mode,");
			sb.append(" FN_GET_AUDIT_RATE_TEXT('pricelist' ,pl.id) AS rate,");
			sb.append(" DATE_FORMAT(pl.effective_date,'%Y-%m-%d') AS effective_date,");
			sb.append(" plf.attachment_point_id");
			sb.append(" FROM price_list pl");
			sb.append(" INNER JOIN price_list_file plf ON pl.price_list_file_id = plf.id");
			sb.append(" where pl.id = " + referenceId);
		}
		
		
		return sb.toString();
	}

	public List<Map<String, Object>> searchOriginalByInvoiceId(Invoice invoice) {
		logger.info("Enter method searchOriginal.");
		final String queryString = getOriginalAttachmentSqlString(invoice);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if(list != null && list.size() > 0){
			for (int i=0;i<list.size();i++){
				Object[] obj = list.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("file_name", obj[0] == null? "" : obj[0].toString());
				map.put("file_path", obj[1] == null? "" : obj[1].toString());
				dataList.add(map);
			}
		}
		return dataList;
	}
	
	private String getOriginalAttachmentSqlString(Invoice invoice) {
		logger.info("Enter method getOriginalAttachmentSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT original.file_name,");
		sb.append(" original.file_path");
		sb.append(" FROM original ,`user`");
		sb.append(" where `user`.id= original.created_by");
		sb.append(" and `user`.rec_active_flag =  'Y'  ");
		sb.append(" and (original.invoice_id =" + invoice.getId()
				+ " or original.bar_code=" + invoice.getBarCode() + ") ");
		sb.append(" and `user`.active_flag =  'Y'  ");
		sb.append(" and original.rec_active_flag = 'Y' ");
		logger.info("Exit method getOriginalAttachmentSqlString.");
		return sb.toString();
	}


	public long searchIsUasgeBan(String invoiceId){
		logger.info("Enter method searchIsUasgeBan.");
		final String sql = "SELECT COUNT(1) FROM invoice_item WHERE invoice_id = "+ invoiceId + 
				" AND (item_type_id = 14 OR item_type_id LIKE '4%') " +
				" AND proposal_flag = 1 AND rec_active_flag = 'Y' AND item_name = 'Peak Minutes';";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method searchIsUasgeBan.");
		return count;
	}
	
	public List<Object[]> runReportSummary(int invoiceId,int term){
		logger.info("Enter method runReportSummary.");
		Session session = getSession();
		try {
//			session.createSQLQuery("CALL rpt_cabs_cd_ban_validation_result("+invoiceId+","+term+");");
			runReportFunction(invoiceId,term,false);
			final String sql = "SELECT summary_vendor_name AS 'Vendor Name', " +
					"audit_source_name AS 'Audit Method', " +
					"traffic_type AS 'Traffic Type', " +
					"invoice_month AS 'Invoice Month', " +
					"actual_amount AS 'Sum of Actual', " +
					"expect_amount AS 'Sum of Expect' " +
					"FROM tmp_cabs_cd_ban_validation_view " +
					"order by id";
			logger.info("Exit method runReportSummary.");
			List l = session.createSQLQuery(sql).list();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseSession(session);
		}
		return null;
	}
	
	public List<Object[]> runReportDetail(int invoiceId,int term){
		logger.info("Enter method runReportDetail.");
		Session session = getSession();
		try {
//			session.createSQLQuery("CALL rpt_cabs_cd_ban_validation_detail_table("+invoiceId+","+term+");");
			runReportFunction(invoiceId,term,true);
			final String sql = "SELECT summary_vendor_name as 'Vendor Name'," +
			"account_number as 'Account Number'," +
			"invoice_number as 'Invoice Number'," +
			"audit_source_name as 'Audit Method'," +
			"audit_status_name as 'Audit Status'," +
			"CONCAT(DATE_FORMAT(invoice_date, '%m/%d/%Y')) as 'Invoice Date'," +
			"(case when substring_index(substring_index(replace(notes,'</br>',''),'(',-1),')',1) like '%800%' then 'CDN_800' " +
			"when substring_index(substring_index(replace(notes,'</br>',''),'(',-1),')',1) like '%FGD_EO%' then 'FGD_EO' " +
			"when substring_index(substring_index(replace(notes,'</br>',''),'(',-1),')',1) like '%Other%' then 'FGD' " +
			"end) as 'Traffic Type', " +
			"replace(notes,'</br>','') as 'Audit Notes', " +
			"format(ifnull(actual_amount,0),2) as 'Actual', " +
			"format(ifnull(expect_amount,0),2) as 'Expect', " +
			"audit_reference_type_name as 'Reference Type' " +
			"FROM tmp_cabs_cd_ban_validation " +
			"ORDER BY summary_vendor_name,account_number,invoice_date ";
			logger.info("Exit method runReportDetail.");
			List l = session.createSQLQuery(sql).list();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			releaseSession(session);
		}
		return null;
	}
	
	public void runReportFunction(int invoiceId,int term,boolean isDetail) throws SQLException {
		logger.info("Enter method runReportFunction.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			if(isDetail){
				proc = con.prepareCall("call rpt_cabs_cd_ban_validation_detail_table(?,?)");
			}else{
				proc = con.prepareCall("call rpt_cabs_cd_ban_validation_result(?,?)");
			}
			proc.setInt(1, invoiceId);
			proc.setInt(2, term);
			proc.execute();
			logger.info("Exit method runReportFunction.");
		} finally {
			releaseSession(session);
		}
	}
	
	public Object[] queryInvoiceDate(int invoiceId){
		logger.info("Enter method queryInvoiceDate.");
		StringBuffer sb = new StringBuffer("SELECT i.invoice_date, v.summary_vendor_name FROM invoice i LEFT JOIN vendor v ON v.id = i.vendor_id");
		sb.append(" WHERE i.id =  " +invoiceId);
		sb.append(" AND i.rec_active_flag = 'Y' AND v.rec_active_flag = 'Y'");
		Session session = getSession();
		try {
			Object[] obj = (Object[])session.createSQLQuery(sb.toString()).uniqueResult();;
			logger.info("Exit method queryInvoiceDate.");
			return obj;
		} finally {
			releaseSession(session);
		}
	}
}