package com.saninco.ccm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.ExcelFileUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInventoryDashboardVO;
import com.saninco.ccm.vo.UpdateMasterInventoryVO;

public class MasterInventoryDaoImpl extends HibernateDaoSupport implements IMasterInventoryDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 */
	public MasterInventoryDaoImpl() {
	}
	
	//add By donghao.guo for search condition
	@SuppressWarnings("unchecked")
	public List<Object[]> getSearchConditionProduct() {
		logger.info("Enter method getSearchConditionProduct.");
		Session session = getSession();
		try {
			List<Object[]> l = session.createSQLQuery("SELECT m.product_category AS product FROM master_inventory m WHERE m.product_category <> '' GROUP BY m.product_category ORDER BY m.product_category").list();
			logger.info("Exit method getSearchConditionProduct.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * 页面初始化时 查询 Dashboard 饼图
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> searchMasterInventoryComplete() {
		logger.info("Enter method searchMasterInventoryComplete.");
		Session session = getSession();
		try {
			StringBuffer sb = new StringBuffer(" SELECT 'Completed' AS complete_flag");
			sb.append("  ,(SELECT COUNT(1)");
			sb.append("  FROM master_inventory m");
			sb.append("  WHERE m.rec_active_flag = 'Y'");
			sb.append("  AND   m.complete_flag = 'Y') AS complete_count");
			sb.append("  UNION "); 
			sb.append("  SELECT 'Not Completed' AS complete_flag "); 
			sb.append("  ,(SELECT COUNT(1) "); 
			sb.append("  FROM master_inventory m "); 
			sb.append("  WHERE m.rec_active_flag = 'Y' "); 
			sb.append("  AND   m.complete_flag = 'N')AS complete_count "); 
			List<Object[]> l = session.createSQLQuery(sb.toString()).list(); 
			logger.info("Exit method searchMasterInventoryComplete.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * Get Master inventory cost type list from database
	 * @return
	 */
	public List<Object[]> searchMasterInventoryCostType() {
		
		logger.info("Enter method searchMasterInventoryCostType.");
		
		Session session = getSession();
		
		// SQL string
		final String sqlString = " SELECT DISTINCT(cost_type) FROM master_inventory " +
									" WHERE cost_type IS NOT NULL AND rec_active_flag = 'Y' ";
		
		try {
			
			List costTypeList = session.createSQLQuery(sqlString).list();
			
			logger.info("Exit method searchMasterInventoryCostType.");
			
			return costTypeList;
			
		} finally {
			releaseSession(session);
		}
	}
	
	
	/**
	 * 根据检索条件查询 Dashboard 饼图
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchMasterInventoryCompleteByFilter(SearchInventoryDashboardVO searchInventoryDashboardVO) {
		logger.info("Enter method searchMasterInventoryCompleteByFilter.");
		Session session = getSession();
		try {
			StringBuffer sb = new StringBuffer(" SELECT 'Completed' AS complete_flag");
			sb.append("  ,(SELECT COUNT(1)");
			sb.append(this.fromAndWhereSQLForChart(searchInventoryDashboardVO));
			sb.append("  AND   m.complete_flag = 'Y') AS complete_count");
			sb.append("  UNION "); 
			sb.append("  SELECT 'Not Completed' AS complete_flag "); 
			sb.append("  ,(SELECT COUNT(1) "); 
			sb.append(this.fromAndWhereSQLForChart(searchInventoryDashboardVO)); 
			sb.append("  AND  m.complete_flag = 'N') AS complete_count "); 
			List l = session.createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			logger.info("Exit method searchMasterInventoryCompleteByFilter.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	
	
	public long getMasterInventoryListPageNo(SearchInventoryDashboardVO sdo){
		Integer count = 0;
		HibernateTemplate template = this.getHibernateTemplate();
		if(sdo.getFilter()!=null){
			final String sql = " select count(1) from ( SELECT concat(m.id,'') as mid," + this.searchMasterInventoryShowCol(false) + this.fromAndWhereSQL(sdo,false) + ") dn";
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		} else {	
			final String sql = " select count(1) " +this.fromAndWhereSQL(sdo,false);
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		}
		logger.info("Exit method getMasterInventoryListPageNo.");
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchMasterInventoryList(SearchInventoryDashboardVO sdo){
		logger.info("Enter method searchMasterInventoryList.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.searchMasterInventoryQueryString(sdo));
		sql.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		Session session = getSession();
		List list;
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method searchMasterInventoryList.");
		return list;
	}
	
	/**
	 * 查询 Master Inventory 历史修改记录 行数
	 * @param  sdo [description]
	 * @return     [description]
	 */
	public long getInventoryChangeHistoryListPageNo(SearchInventoryDashboardVO sdo){
		logger.info("Enter method getInventoryChangeHistoryListPageNo.");
		final String sql = " select count(1) from ("+this.searchInventoryChangeHistoryQueryString(sdo)+ ") dn";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getInventoryChangeHistoryListPageNo.");
		return count;
	}
	/**
	 * 查询 Master Inventory 历史修改记录 列表
	 * @param  sdo [description]
	 * @return     [description]
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getInventoryChangeHistoryList(SearchInventoryDashboardVO sdo){
		logger.info("Enter method getInventoryChangeHistoryList.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.searchInventoryChangeHistoryQueryString(sdo));
		sql.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		Session session = getSession();
		List list;
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method getInventoryChangeHistoryList.");
		return list;
	}
	
	/**
	 * 查询 Master Inventory Update History count 的SQL语句。
	 * @param  sdo [description]
	 * @return     [description]
	 */
	private String searchInventoryChangeHistoryQueryString(SearchInventoryDashboardVO sdo){
		
		StringBuffer sb = new StringBuffer(" SELECT ifnull(concat(first_name , ' ' , last_name),'') as user_name,");
		sb.append(" IFNULL(date_format(m.modified_timestamp, '%Y-%m-%d %H:%i:%s'),'') AS history_modified_timestamp,");
		sb.append(" m.id as mid,");
		sb.append(this.searchMasterInventoryShowCol(true));
		sb.append(" FROM history_db.master_inventory_history m");
		sb.append(" LEFT JOIN invoice i");
		sb.append(" ON m.latest_invoice_id = i.id");
		sb.append(" LEFT JOIN account_code ac ON m.account_code_id = ac.id");
		sb.append(" LEFT JOIN user u ON m.modified_by = u.id");
		sb.append(" LEFT JOIN ban b ON b.id = m.ban_id");
		sb.append(" INNER JOIN vendor v");
		sb.append(" ON m.vendor_id = v.id");
		sb.append(" WHERE m.vendor_id = v.id");
		sb.append(" AND m.rec_active_flag = 'Y'");
		sb.append(" AND m.id = " + sdo.getInventoryId());
		
		if(sdo.getFilter()!=null){
			sb.append(" having " + sdo.getFilter());
		}	
		sb.append(sdo.getOrderByCause(null));
		return sb.toString();
	}
	
	
	/**
	 * 查询 Master Inventory Update History count 的SQL语句。
	 * @param  sdo [description]
	 * @return     [description]
	 */
	private String searchInventoryChangeHistoryForExcelQueryString(SearchInventoryDashboardVO sdo){
		
		StringBuffer sb = new StringBuffer(" SELECT ifnull(concat(first_name , ' ' , last_name),'') as user_name,"); 
		sb.append(" IFNULL(date_format(m.modified_timestamp, '%Y-%m-%d %H:%i:%s'),'') AS history_modified_timestamp,");
		sb.append(" concat(m.id,'') as mid,");
		sb.append(this.searchMasterInventoryShowCol(true));
		sb.append(" FROM history_db.master_inventory_history m");
		sb.append(" LEFT JOIN invoice i");
		sb.append(" ON m.latest_invoice_id = i.id");
		sb.append(" LEFT JOIN account_code ac ON m.account_code_id = ac.id");
		sb.append(" LEFT JOIN user u ON m.modified_by = u.id");
		sb.append(" LEFT JOIN ban b ON b.id = m.ban_id");
		sb.append(" INNER JOIN vendor v");
		sb.append(" ON m.vendor_id = v.id");
		sb.append(" WHERE m.vendor_id = v.id");
		sb.append(" AND m.rec_active_flag = 'Y'");
		sb.append(" AND m.id = " + sdo.getInventoryId());
		
		if(sdo != null){
			if(sdo.getFilter()!=null){
				sb.append(" having " + sdo.getFilter());
			}	
			sdo.setSortField("modified_timestamp");
			sdo.setSortingDirection("desc");
			sb.append(sdo.getOrderByCause("m"));
		}
		
		return sb.toString();
	}
	
	
	
	/**
	 * 查询 Master Inventory count 的SQL语句。
	 * @param  sdo [description]
	 * @return     [description]
	 */
	private String searchMasterInventoryQueryString(SearchInventoryDashboardVO sdo){
		logger.info("Enter method searchMasterInventoryQueryString.");
		
		StringBuffer sb = new StringBuffer(" SELECT m.id as mid,"); // Inventory Id
		sb.append(this.searchMasterInventoryShowCol(false));
		sb.append(this.fromAndWhereSQL(sdo,true));
		logger.info("Exit method searchDisputeActionRequestQueryString.");
		return sb.toString();
	}
	
	
	public void insertMasterInventoryTemporary(List<Map<String,String>> dataList,List<String> columnList,String batchNo) throws SQLException{
		logger.info("Enter method insertMasterInventoryTemporary.");
		// 插入 field length 相关的表
		// master_inventory_length_import 这个表中存储的是 每个字段的长度
		// 因为后台的存储过程在验证的时候需要验证每个
		// 字段的长度是否符合要求
		StringBuffer insertSb = new StringBuffer("insert into master_inventory_length_import (`master_inventory_id`  ," +
					"`vendor_name`    ," +
					"`summary_vendor_name`    ," +
					"`ban`    ," +
					"`latest_invoice_number`    ," +
					"`line_of_business`    ," +
					"`lastest_invoice_date`    ," +
					"`stripped_circuit_number`    ," +
					"`unique_circuit_id`   ," +
					"`service_id`   ," +
					"`service_id_mrr`   ," +
					"`service_id_mrr_province`   ," +
					"`revenue_match_date`   ," +
					"`status`   ," +
					"`service_id_match_status`   ," +
					"`type`   ," +
					"`installation_date`  ," +
					"`first_invoice_date`  ," +
					"`first_invoice_number`  ," +
					"`order_number`  ," +
					"`order_type`  ," +
					"`quote_number`  ," +
					"`disconnection_date`  ," +
					"`validation_source_system`  ," +
					"`cost_type`  ," +
					"`service_description`  ," +
					"`product_category`   ," +
					"`sub_product_category`   ," +
					"`project`   ," +
					"`project_category_status`   ," +
					"`a_street_number`  ," +
					"`a_street_name`  ," +
					"`a_unit`  ," +
					"`a_city`   ," +
					"`a_province`  ," +
					"`a_postal_code`   ," +
					"`a_country`  ," +
					"`z_street_number`  ," +
					"`z_street_name`  ," +
					"`z_unit`  ," +
					"`z_city`   ," +
					"`z_province`  ," +
					"`z_postal_code`   ," +
					"`z_country`  ," +
					"`region`  ," +
					"`serving_wire_centre`   ," +
					"`aggregator_cid`   ," +
					"`time_slot_vlan_assignment`   ," +
					"`comments`  ," +
					"`trunk_group_clli`  ," +
					"`customer_billing_account`  ," +
					"`business_segment`   ," +
					"`end_user`  ," +
					"`scoa`  ," +
					"`owner`   ," +
					"`owner_email`   ," +
					"`last_signoff_date`  ," +
//					"`mileage`  ," +
					"`usoc`  ," +
//					"`rate`  ," +
//					"`rate_effective_date`  ," +
//					"`circuit_term`  ," +
//					"`contract`  ," +
//					"`tariff`  ," +
//					"`tariff_page`  ," +
//					"`expiry_date`  ," +
//					"`rate_status`  ," +
//					"`agreement_type`  ," +
					"`intercompany_business_unit`  ," +
					"`intercompany_channel`  ," +
					"`fsa_code`  ," +
					"`serviceability_fibre`  ," +
					"`serviceability_cable`  ," +
					"`modified_timestamp`  ," +
					"`modified_by`  ," +
//					"`rate_discrepancy_flag`  ," +
//					"`termination_penalty_amount`  ," +
					"`user_id`," +
					"`batch_no`," +
					"`row_no`) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		Session session = getSession();
		try {
			Connection conn = session.connection();
			PreparedStatement stmt = conn.prepareStatement(insertSb.toString());
			conn.setAutoCommit(false);
			
			int columnSize = columnList.size();
			String userId = SystemUtil.getCurrentUserId()+"";
			// 循环数据项设置对应的列值
			for (int i=0;i<dataList.size();i++){
				Map<String,String> map = dataList.get(i);
				for(int j=0;j<columnList.size();j++){
					String str = "";
					if(map.get(columnList.get(j))!=null){
						str = map.get(columnList.get(j));
					}
					stmt.setString(j+1,str); // 将列值和数据项对应起来。
				}
				stmt.setString(columnSize+1,userId); // user id
				stmt.setString(columnSize+2,batchNo); // batch_no
				stmt.setString(columnSize+3,(i+1)+""); // row_no
				stmt.addBatch();
//			    if (i % 1000 == 0) {
//			        stmt.executeBatch();
//			        conn.commit();
//			        stmt.clearBatch();
//			    }
//				stmt.executeUpdate();
			}
			stmt.executeBatch();
			conn.commit();
			session.close();
			logger.info("Exit method insertMasterInventoryTemporary.");
		} finally {
			releaseSession(session);
		}
	}
	
	public void callMasterInventoryVerification(String batchNo) throws SQLException {
		logger.info("Enter method callMasterInventoryVerification.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_MASTER_INVENTORY_VERIFICATION('"+batchNo+"')");
			proc.execute();
			logger.info("Exit method callMasterInventoryVerification.");
		} finally {
			releaseSession(session);
		}
	}
	
	public List<Map<String,Object>> queryTmpMasterInventoryError() {
		logger.info("Enter method queryTmpMasterInventoryError.");
		final String sql = "select row_number,field,note from tmp_master_inventory_error order by row_number limit "+ ExcelFileUtil.MAX_DATA_LENGTH;
		Session session = getSession();
		List list;
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method queryTmpMasterInventoryError.");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getMasterInventoryDataForExcel(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getMasterInventoryDataForExcel.");
		final String sql = this.getMasterInventoryListForExcelQueryString(sdo);
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		List<Object[]> re;
		Session session = getSession();
		try {
			re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getMasterInventoryDataForExcel.");
			return re;	
		} finally {
			releaseSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public long getMasterInventoryDataForExcelPageNo(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getMasterInventoryDataForExcelPageNo.");
		Integer count = 0;
		HibernateTemplate template = this.getHibernateTemplate();
		if(sdo.getFilter()!=null){
			final String sql = " select count(1) from ( SELECT concat(m.id,'') as mid," + this.searchMasterInventoryShowCol(false) + this.fromAndWhereSQL(sdo,false) + ") dn";
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		} else {	
			final String sql = " select count(1) " +this.fromAndWhereSQL(sdo,false);
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		}
		logger.info("Exit method getMasterInventoryDataForExcelPageNo.");
		return count;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getInventoryChangeHistoryDataForExcel(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getInventoryChangeHistoryDataForExcel.");
		final String sql = this.searchInventoryChangeHistoryForExcelQueryString(sdo);
		List<Object[]> re;
		Session session = getSession();
		try {
			re = session.createSQLQuery(sql).list();
			logger.info("Exit method getInventoryChangeHistoryDataForExcel.");
			return re;	
		} finally {
			releaseSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public long getInventoryChangeHistoryDataForExcelPageNo(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getInventoryChangeHistoryDataForExcelPageNo.");
//		final String sql = " select count(1) from ("+this.searchInventoryChangeHistoryForExcelQueryString(sdo)+ ") dn";
		final String sql = " select count(1) " +this.fromAndWhereSQL(sdo,false);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getInventoryChangeHistoryDataForExcelPageNo.");
		return count;
	}
	
	
	public String getMasterInventoryListForExcelQueryString(SearchInventoryDashboardVO sdo) {
		
		logger.info("Enter method getMasterInventoryListForExcelQueryString.");
		StringBuffer sb = new StringBuffer(" SELECT concat(m.id,'') as mid,"); // Inventory Id
		sb.append(this.searchMasterInventoryShowCol(false));
		sb.append(this.fromAndWhereSQL(sdo,true));
		return sb.toString();
	}
	
	public String searchMasterInventoryShowCol(Boolean isHistory) {
		StringBuffer sb = new StringBuffer();
		sb.append(" if(v.vendor_name IS NULL, '', v.vendor_name) AS vendor_name,"); // VENDOR_NAME
		sb.append(" if(v.summary_vendor_name IS NULL, '', v.summary_vendor_name) AS summary_vendor_name,"); // SUMMARY_VENDOR_NAME
		sb.append(" if(b.id IS NULL,'',b.account_number) AS ban_name,");//BAN
		sb.append(" if(i.invoice_number IS NULL, '',i.invoice_number) AS invoice_number,"); // INVOICE NUMBER
		sb.append(" if(b.id IS NULL,'',b.line_of_business) AS line_of_business,");// LINE OF BUSINESS
		sb.append("IFNULL((SELECT date_format(i.invoice_date, '%Y-%m-%d') FROM invoice i WHERE i.id = m.latest_invoice_id),'') AS invoice_date,");// INVOICE DATE
		sb.append(" if(m.stripped_circuit_number IS NULL, '', m.stripped_circuit_number) AS stripped_circuit_number,"); // STRIPPED CIRCUIT NUMBER
		sb.append(" if(m.unique_circuit_id IS NULL, '',m.unique_circuit_id) AS unique_circuit_id,"); // UNIQUE CIRCUITID
		sb.append(" if(m.service_id IS NULL, '',m.service_id) AS service_id,"); // SERVICE ID
		sb.append(" if(m.service_id_mrr IS NULL, '',m.service_id_mrr) AS service_id_mrr,"); // SERVICE ID MRR
		sb.append(" if(m.service_id_mrr_province IS NULL, '',m.service_id_mrr_province) AS service_id_mrr_province,"); // SERVICE ID MRR
		sb.append(" if(m.revenue_match_date IS NULL, '',date_format(m.revenue_match_date, '%Y-%m-%d')) AS revenue_match_date,"); // REVENUE MATCH DATE
		sb.append(" if(m.circuit_status IS NULL, '', m.circuit_status) AS circuit_status,"); // CIRCUIT ID STATUS
		sb.append(" if(m.service_id_match_status IS NULL, '',m.service_id_match_status) AS service_id_match_status,"); // SERVICE ID MATCH STATUS
//		sb.append(" if(m.circuit_type IS NULL, '', m.circuit_type) AS circuit_type,"); // TYPE
		sb.append(" if(m.access_type IS NULL, '', m.access_type) AS access_type,"); // TYPE
		sb.append(" if(m.installation_date IS NULL, '',date_format(m.installation_date, '%Y-%m-%d')) AS installation_date,"); // INSTALL DATE
		sb.append("IFNULL((SELECT date_format(i.invoice_date, '%Y-%m-%d') FROM invoice i WHERE i.id = m.first_invoice_id),'') AS first_invoice_date,");// FIRST INVOICE DATE
		sb.append("IFNULL((SELECT i.invoice_number FROM invoice i WHERE i.id = m.first_invoice_id),'') AS first_invoice_number,");//FIRST INVOICE NUMBER
		sb.append(" if(m.order_number IS NULL, '',m.order_number) AS order_number,"); //ORDER NUMBER
		sb.append(" if(m.order_type IS NULL, '',m.order_type) AS order_type,"); //ORDER TYPE
		sb.append(" if(m.quote_number IS NULL, '',m.quote_number) AS quote_number,"); //QUOTE NUMBER
		sb.append(" if(m.disconnection_date IS NULL, '',date_format(m.disconnection_date, '%Y-%m-%d')) AS disconnection_date,"); // DISCONNECTION DATE
		sb.append(" if(m.validation_source_system IS NULL, '',m.validation_source_system) AS validation_source_system,"); // VALIDATIONSOURCESYSTEM
		sb.append(" if(m.cost_type IS NULL, '',m.cost_type) AS cost_type,"); //COST TYPE
		sb.append(" if(m.service_description IS NULL, '',m.service_description) AS service_description,"); //SERVICE DESCRIPTION
		sb.append(" if(m.product_category IS NULL, '',m.product_category) AS product_category,"); // PRODUCTCATEGORY
		sb.append(" if(m.sub_product_category IS NULL, '',m.sub_product_category) AS sub_product_category,"); //SUBPRODUCTCATEGORY
		sb.append(" if(m.project IS NULL, '',m.project) AS project,"); // PROJECT
		sb.append(" if(m.project_category_status IS NULL, '',m.project_category_status) AS project_category_status,"); // PROJECT CATEGORY STATUS
		sb.append(" if(m.a_street_number IS NULL, '',m.a_street_number) AS a_street_number,"); //A ADDRESS STREET NUMBER
		sb.append(" if(m.a_street_name IS NULL, '',m.a_street_name) AS a_street_name,"); // A ADDRESS STREET NAME
		sb.append(" if(m.a_unit IS NULL, '',m.a_unit) AS a_unit,"); // A ADDRESS SUITE
		sb.append(" if(m.a_city IS NULL, '',m.a_city) AS a_city,"); // A ADDRESS CITY
		sb.append(" if(m.a_province IS NULL, '',m.a_province) AS a_province,"); // A ADDRESS PROVINCE
		sb.append(" if(m.a_postal_code IS NULL, '',m.a_postal_code) AS a_postal_code,"); // A ADDRESS POSTAL CODE
		sb.append(" if(m.a_country IS NULL, '',m.a_country) AS a_country,"); // A ADDRESS COUNTRY
		sb.append(" if(m.z_street_number IS NULL, '',m.z_street_number) AS z_street_number,"); //Z ADDRESS STREET NUMBER
		sb.append(" if(m.z_street_name IS NULL, '',m.z_street_name) AS z_street_name,"); //Z ADDRESS STREET NAME
		sb.append(" if(m.z_unit IS NULL, '',m.z_unit) AS z_unit,"); // Z ADDRESS SUITE
		sb.append(" if(m.z_city IS NULL, '',m.z_city) AS z_city,"); // Z ADDRESS CITY
		sb.append(" if(m.z_province IS NULL, '',m.z_province) AS z_province,"); // Z ADDRESS PROVINCE
		sb.append(" if(m.z_postal_code IS NULL, '',m.z_postal_code) AS z_postal_code,"); // Z ADDRESS POSTAL CODE
		sb.append(" if(m.z_country IS NULL, '',m.z_country) AS z_country,"); // Z ADDRESS COUNTRY
		sb.append(" if(m.region IS NULL, '',m.region) AS region,"); // Z ADDRESS COUNTRY
		sb.append(" if(m.serving_wire_centre IS NULL, '',m.serving_wire_centre) AS serving_wire_centre,"); // SERVING WIRE CENTRE
		sb.append(" if(m.aggregator_cid IS NULL, '',m.aggregator_cid) AS aggregator_cid,"); // AGGREGATOR CIRCUITID
		sb.append(" if(m.time_slot_vlan_assignment IS NULL, '',m.time_slot_vlan_assignment) AS time_slot_vlan_assignment,"); // TIME SLOT OR VLAN ASSIGNMENT
		sb.append(" if(m.comments IS NULL, '',m.comments) AS comments,"); //COMMENTS
		sb.append(" if(m.trunk_group_clli IS NULL, '',m.trunk_group_clli) AS trunk_group_clli,"); //TRUNK GROUP CLLI
		sb.append(" if(m.customer_billing_account IS NULL, '',m.customer_billing_account) AS customer_billing_account,"); //CUSTOMER BILLING ACCOUNT #
		sb.append(" if(m.business_segment IS NULL, '',m.business_segment) AS business_segment,"); // BUSINESS SEGMENT
		sb.append(" if(m.end_user IS NULL, '',m.end_user) AS end_user,"); // END USER
		sb.append(" if(m.scoa IS NULL, '',m.scoa) AS scoa,");//SCOA 
		sb.append(" if(m.owner IS NULL, '',m.owner) AS owner,"); // OWNER
		sb.append(" if(m.owner_email IS NULL, '',m.owner_email) AS owner_email,"); // OWNER E-MAIL
		sb.append(" if(m.last_signoff_date IS NULL, '',date_format(m.last_signoff_date, '%Y-%m-%d')) AS last_signoff_date,"); // LAST SIGNOFF DATE
		sb.append(" if(m.usoc IS NULL, '',m.usoc) AS usoc,"); // USOC
		sb.append(" if(m.intercompany_business_unit IS NULL, '',m.intercompany_business_unit) AS intercompany_business_unit,"); // INTERCOMPANY BUSINESS UNIT
		sb.append(" if(m.intercompany_channel IS NULL, '',m.intercompany_channel) AS intercompany_channel,");// INTERCOMPANY CHANNEL
		sb.append(" if(m.fsa_code IS NULL, '',m.fsa_code) AS fsa_code,");// INTERCOMPANY CHANNEL
		sb.append(" if(m.serviceability_fibre IS NULL, '',m.serviceability_fibre) AS serviceability_fibre,");// INTERCOMPANY CHANNEL
		sb.append(" if(m.serviceability_cable IS NULL, '',m.serviceability_cable) AS serviceability_cable ");// INTERCOMPANY CHANNEL
		if(!isHistory){
			sb.append(", if(m.modified_timestamp IS NULL, '',date_format(m.modified_timestamp, '%Y-%m-%d')) AS modified_timestamp,");// MODIFIED DATE
			sb.append(" IFNULL(CONCAT(u.first_name, ' ', u.last_name), '') AS modified_user ");// MODIFIED USER
		}
		
		return sb.toString();
	}
	
	private String fromAndWhereSQL(SearchInventoryDashboardVO sdo , boolean isSort){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM master_inventory m");
		sb.append(" LEFT JOIN invoice i");
		sb.append(" ON m.latest_invoice_id = i.id");
		sb.append(" LEFT JOIN account_code ac ON m.account_code_id = ac.id");
		sb.append(" LEFT JOIN user u ON m.modified_by = u.id");
		sb.append(" LEFT JOIN ban b ON b.id = m.ban_id");
		sb.append(" LEFT JOIN account_code a ON a.account_code_name = m.scoa");
		sb.append(" INNER JOIN vendor v");
		sb.append(" ON m.vendor_id = v.id");
		sb.append(" WHERE m.vendor_id = v.id");
		sb.append(" AND m.rec_active_flag = 'Y'");
		
		if(sdo.getMid() != null && !"all".equals(sdo.getMid())) {
			sb.append(" AND m.id = " + sdo.getMid());
		}
		if(sdo.getStripCircuitNumber() != null && !"".equals(sdo.getStripCircuitNumber())) {
			sb.append(" AND m.stripped_circuit_number = '" + sdo.getStripCircuitNumber()+ "' ");
		}
		if(sdo.getSummaryVendorName() != null && !"all".equals(sdo.getSummaryVendorName())) {
			sb.append(" AND m.vendor_id in (select id from vendor where summary_vendor_name = '" + sdo.getSummaryVendorName() + "')");
		}
		
		if(sdo.getBanId() != null && !"all".equals(sdo.getBanId())) {
			sb.append(" AND m.ban_id = " + sdo.getBanId());
		}
		
		if(sdo.getCircuitId() != null && !"".equals(sdo.getCircuitId())) {
			sb.append(" AND m.stripped_circuit_number LIKE '%" + sdo.getCircuitId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getInventoryId() != null &&  !"".equals(sdo.getInventoryId())) {
			sb.append(" AND m.id = '" + sdo.getInventoryId() + "'");
		}
		
		if(sdo.getServiceId() != null &&  !"".equals(sdo.getServiceId())) {
			sb.append(" AND m.service_id LIKE '%" + sdo.getServiceId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getCompleteFlag() != null &&  !"all".equals(sdo.getCompleteFlag())){
			sb.append(" AND m.complete_flag = '" + sdo.getCompleteFlag() + "'");
		}
		
		if(sdo.getCustomerBillingAccount() != null &&  !"".equals(sdo.getCustomerBillingAccount())) {
//			sb.append(" AND (m.customer_billing_account LIKE '%" + sdo.getCustomerBillingAccount().replace("%", "\\%") + "%' OR )");
			String[] cbas = sdo.getCustomerBillingAccount().split(",");
			sb.append(" AND (");
			for(int i = 0; i < cbas.length; i++) {
				if(i == cbas.length - 1) {
					sb.append("m.customer_billing_account LIKE '%" + cbas[i].replace("%", "\\%") + "%'");
				} else {
					sb.append("m.customer_billing_account LIKE '%" + cbas[i].replace("%", "\\%") + "%' OR ");
				}
				
			}
			sb.append(")");
		}
		
		if(sdo.getEndUser() != null &&  !"".equals(sdo.getEndUser())) {
			sb.append(" AND m.end_user LIKE '%" + sdo.getEndUser().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getaStreetNumber() != null &&  !"".equals(sdo.getaStreetNumber())) {
			sb.append(" AND m.a_street_number LIKE '%" + sdo.getaStreetNumber().replace("%", "\\%") + "%'");
		}
		if(sdo.getaStreetName() != null &&  !"".equals(sdo.getaStreetName())) {
			sb.append(" AND m.a_street_name LIKE '%" + sdo.getaStreetName().replace("%", "\\%") + "%'");
		}
		if(sdo.getaUnit() != null &&  !"".equals(sdo.getaUnit())) {
			sb.append(" AND m.a_unit LIKE '%" + sdo.getaUnit().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCity() != null &&  !"".equals(sdo.getaCity())) {
			sb.append(" AND m.a_city LIKE '%" + sdo.getaCity().replace("%", "\\%") + "%'");
		}
		if(sdo.getaProvince() != null &&  !"".equals(sdo.getaProvince())) {
			sb.append(" AND m.a_province LIKE '%" + sdo.getaProvince().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCountry() != null &&  !"".equals(sdo.getaCountry())) {
			sb.append(" AND m.a_country LIKE '%" + sdo.getaCountry().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getIntercompanyBusinessUnit() != null &&  !"".equals(sdo.getIntercompanyBusinessUnit())) {
			sb.append(" AND m.intercompany_business_unit LIKE '%" + sdo.getIntercompanyBusinessUnit().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getProductCategory() != null &&  !"".equals(sdo.getProductCategory())) {
			sb.append(" AND m.product_category LIKE '%" + sdo.getProductCategory().replace("%", "\\%") + "%'");
		}
		
Map<String, String> itemMap = new HashMap<String, String>();
		
		if(sdo.getScoaCompany() != null &&  !"".equals(sdo.getScoaCompany())) {
			itemMap.put(" a.company = '", sdo.getScoaCompany() + "'");
		}
		if(sdo.getScoaAccount() != null &&  !"".equals(sdo.getScoaAccount())) {
			
			itemMap.put(" a.account = '", sdo.getScoaAccount() + "'");
		}
		if(sdo.getScoaChannel() != null &&  !"".equals(sdo.getScoaChannel())) {
			itemMap.put(" a.channel = '", sdo.getScoaChannel() + "'");
		}
		if(sdo.getScoaDepartment() != null &&  !"".equals(sdo.getScoaDepartment())) {
			itemMap.put(" a.department = '", sdo.getScoaDepartment() + "'");
		}
		if(sdo.getScoaLocation() != null &&  !"".equals(sdo.getScoaLocation())) {
			itemMap.put(" a.location = '", sdo.getScoaLocation() + "'");
		}
		if(sdo.getScoaProduct() != null &&  !"".equals(sdo.getScoaProduct())) {
			itemMap.put(" a.product = '", sdo.getScoaProduct() + "'");
		}
		
		if(itemMap.values().size()  > 0) { 
			sb.append(" AND ((");
			StringBuffer scoaStrBufferForAND = new StringBuffer();
			for(String keyAND : itemMap.keySet()){
			   String valueAND = itemMap.get(keyAND);
			   scoaStrBufferForAND.append(" AND " + keyAND + valueAND);
			}
			
			scoaStrBufferForAND.delete(0, 5);
			sb.append(scoaStrBufferForAND);
			
			sb.append(") OR (");
			StringBuffer scoaStrBufferForOR = new StringBuffer();
			for(String keyOR : itemMap.keySet()){
			   String valueOR = itemMap.get(keyOR);
			   scoaStrBufferForOR.append(" AND locate('" + valueOR + "," + "m.scoa) > 0");
			   
			}
			
			scoaStrBufferForOR.delete(0, 5);
			sb.append(scoaStrBufferForOR);
			
			sb.append("))");
			
			
		}
		
		if(sdo.getFilter()!=null){
			sb.append(" having " + sdo.getFilter());
		}	
		if(isSort){
			sb.append(sdo.getOrderByCause(null));
		}
		
		return sb.toString();
	}
	
private String fromAndWhereSQLForChart(SearchInventoryDashboardVO sdo){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM master_inventory m");
		sb.append(" LEFT JOIN account_code a ON a.account_code_name = m.scoa");
		sb.append(" WHERE m.rec_active_flag = 'Y'");
		
		if(sdo.getSummaryVendorName() != null && !"all".equals(sdo.getSummaryVendorName())) {
			sb.append(" AND m.vendor_id in (select id from vendor where summary_vendor_name = '" + sdo.getSummaryVendorName() + "')");
		}
		
		if(sdo.getBanId() != null && !"all".equals(sdo.getBanId())) {
			sb.append(" AND m.ban_id = " + sdo.getBanId());
		}
		
		if(sdo.getCircuitId() != null && !"".equals(sdo.getCircuitId())) {
			sb.append(" AND m.stripped_circuit_number LIKE '%" + sdo.getCircuitId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getInventoryId() != null &&  !"".equals(sdo.getInventoryId())) {
			sb.append(" AND m.id = '" + sdo.getInventoryId() + "'");
		}
		
		if(sdo.getServiceId() != null &&  !"".equals(sdo.getServiceId())) {
			sb.append(" AND m.service_id LIKE '%" + sdo.getServiceId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getCompleteFlag() != null &&  !"all".equals(sdo.getCompleteFlag())){
			sb.append(" AND m.complete_flag = '" + sdo.getCompleteFlag() + "'");
		}
		
		if(sdo.getCustomerBillingAccount() != null &&  !"".equals(sdo.getCustomerBillingAccount())) {
			sb.append(" AND m.customer_billing_account LIKE '%" + sdo.getCustomerBillingAccount().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getEndUser() != null &&  !"".equals(sdo.getEndUser())) {
			sb.append(" AND m.end_user LIKE '%" + sdo.getEndUser().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getaStreetNumber() != null &&  !"".equals(sdo.getaStreetNumber())) {
			sb.append(" AND m.a_street_number LIKE '%" + sdo.getaStreetNumber().replace("%", "\\%") + "%'");
		}
		if(sdo.getaStreetName() != null &&  !"".equals(sdo.getaStreetName())) {
			sb.append(" AND m.a_street_name LIKE '%" + sdo.getaStreetName().replace("%", "\\%") + "%'");
		}
		if(sdo.getaUnit() != null &&  !"".equals(sdo.getaUnit())) {
			sb.append(" AND m.a_unit LIKE '%" + sdo.getaUnit().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCity() != null &&  !"".equals(sdo.getaCity())) {
			sb.append(" AND m.a_city LIKE '%" + sdo.getaCity().replace("%", "\\%") + "%'");
		}
		if(sdo.getaProvince() != null &&  !"".equals(sdo.getaProvince())) {
			sb.append(" AND m.a_province LIKE '%" + sdo.getaProvince().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCountry() != null &&  !"".equals(sdo.getaCountry())) {
			sb.append(" AND m.a_country LIKE '%" + sdo.getaCountry().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getIntercompanyBusinessUnit() != null &&  !"".equals(sdo.getIntercompanyBusinessUnit())) {
			sb.append(" AND m.intercompany_business_unit LIKE '%" + sdo.getIntercompanyBusinessUnit().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getProductCategory() != null &&  !"".equals(sdo.getProductCategory())) {
			sb.append(" AND m.product_category LIKE '%" + sdo.getProductCategory().replace("%", "\\%") + "%'");
		}
		
		Map<String, String> itemMap = new HashMap<String, String>();
		
		if(sdo.getScoaCompany() != null &&  !"".equals(sdo.getScoaCompany())) {
			itemMap.put(" a.company = '", sdo.getScoaCompany() + "'");
		}
		if(sdo.getScoaAccount() != null &&  !"".equals(sdo.getScoaAccount())) {
			
			itemMap.put(" a.account = '", sdo.getScoaAccount() + "'");
		}
		if(sdo.getScoaChannel() != null &&  !"".equals(sdo.getScoaChannel())) {
			itemMap.put(" a.channel = '", sdo.getScoaChannel() + "'");
		}
		if(sdo.getScoaDepartment() != null &&  !"".equals(sdo.getScoaDepartment())) {
			itemMap.put(" a.department = '", sdo.getScoaDepartment() + "'");
		}
		if(sdo.getScoaLocation() != null &&  !"".equals(sdo.getScoaLocation())) {
			itemMap.put(" a.location = '", sdo.getScoaLocation() + "'");
		}
		if(sdo.getScoaProduct() != null &&  !"".equals(sdo.getScoaProduct())) {
			itemMap.put(" a.product = '", sdo.getScoaProduct() + "'");
		}
		
		
		if(itemMap.values().size()  > 0) { 
			sb.append(" AND ((");
			StringBuffer scoaStrBufferForAND = new StringBuffer();
			for(String keyAND : itemMap.keySet()){
			   String valueAND = itemMap.get(keyAND);
			   scoaStrBufferForAND.append(" AND " + keyAND + valueAND);
			}
			
			scoaStrBufferForAND.delete(0, 5);
			sb.append(scoaStrBufferForAND);
			
			sb.append(") OR (");
			StringBuffer scoaStrBufferForOR = new StringBuffer();
			for(String keyOR : itemMap.keySet()){
			   String valueOR = itemMap.get(keyOR);
			   scoaStrBufferForOR.append(" AND locate('" + valueOR + "," + "m.scoa) > 0");
			   
			}
			
			scoaStrBufferForOR.delete(0, 5);
			sb.append(scoaStrBufferForOR);
			
			sb.append("))");
			
			
		}
		
		return sb.toString();
	}

	/**
	 * MI update data
	 */
	public void updateMasterInventory(UpdateMasterInventoryVO mivo) throws SQLException {
		logger.debug("updateMasterInventory");
		Integer count=0;
		Session session = getSession();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" update master_inventory SET stripped_circuit_number = '" + CcmUtil.stringToSql(mivo.getStrippedCircuitNumber()) + "'");
			sb.append(" ,unique_circuit_id = '" + CcmUtil.stringToSql(mivo.getUniqueCircuitId()) + "'");
			sb.append(" ,service_id = '" + CcmUtil.stringToSql(mivo.getServiceId()) + "'");
			sb.append(" ,service_id_mrr = '" + CcmUtil.stringToSql(mivo.getServiceIdMrr()) + "'");
			sb.append(" ,service_id_mrr_province = '" + CcmUtil.stringToSql(mivo.getServiceIdMrrProvince()) + "'");
			sb.append(" ,circuit_status = '" + CcmUtil.stringToSql(mivo.getCircuitStatus()) + "'");
			sb.append(" ,access_type = '" + CcmUtil.stringToSql(mivo.getAccessType()) + "'");
			if(mivo.getInstallDate() != null && !"".equals(mivo.getInstallDate())){
				sb.append(" ,installation_date = str_to_date('" + CcmUtil.stringToSql(mivo.getInstallDate()) + "', '%Y/%m/%d')");
			} else {
				sb.append(" ,installation_date = null");
			}
			sb.append(" ,order_number = '" + CcmUtil.stringToSql(mivo.getOrderNumber()) + "'");
			sb.append(" ,order_type = '" + CcmUtil.stringToSql(mivo.getOrderType()) + "'");
			sb.append(" ,quote_number = '" + CcmUtil.stringToSql(mivo.getQuoteNumber()) + "'");
			if(mivo.getDisconnectionDate() != null && !"".equals(mivo.getDisconnectionDate())){
				sb.append(" ,disconnection_date = str_to_date('" + CcmUtil.stringToSql(mivo.getDisconnectionDate()) + "', '%Y/%m/%d')");
			} else {
				sb.append(" ,disconnection_date = null");
			}
			sb.append(" ,validation_source_system = '" + CcmUtil.stringToSql(mivo.getValidationSourceSystem()) + "'");
			sb.append(" ,cost_type = '" + CcmUtil.stringToSql(mivo.getCostType()) + "'");
			sb.append(" ,service_description = '" + CcmUtil.stringToSql(mivo.getServiceDescription()) + "'");
			sb.append(" ,product_category = '" + CcmUtil.stringToSql(mivo.getProductCategory()) + "'");
			sb.append(" ,sub_product_category = '" + CcmUtil.stringToSql(mivo.getSubProductCategory()) + "'");
			sb.append(" ,project = '" + CcmUtil.stringToSql(mivo.getProject()) + "'");
			sb.append(" ,project_category_status = '" + CcmUtil.stringToSql(mivo.getProjectCategoryStatus()) + "'");
			sb.append(" ,a_street_number = '" + CcmUtil.stringToSql(mivo.getaStreetNumber()) + "'");
			sb.append(" ,a_street_name = '" + CcmUtil.stringToSql(mivo.getaStreetName()) + "'");
			sb.append(" ,a_unit = '" + CcmUtil.stringToSql(mivo.getaUnit()) + "'");
			sb.append(" ,a_city = '" + CcmUtil.stringToSql(mivo.getaCity()) + "'");
			sb.append(" ,a_province = '" + CcmUtil.stringToSql(mivo.getaProvince()) + "'");
			sb.append(" ,a_postal_code = '" + CcmUtil.stringToSql(mivo.getaPostalCode()) + "'");
			sb.append(" ,a_country = '" + CcmUtil.stringToSql(mivo.getaCountry()) + "'");
			sb.append(" ,z_street_number = '" + CcmUtil.stringToSql(mivo.getzStreetNumber()) + "'");
			sb.append(" ,z_street_name = '" + CcmUtil.stringToSql(mivo.getzStreetName()) + "'");
			sb.append(" ,z_unit = '" + CcmUtil.stringToSql(mivo.getzUnit()) + "'");
			sb.append(" ,z_city = '" + CcmUtil.stringToSql(mivo.getzCity()) + "'");
			sb.append(" ,z_province = '" + CcmUtil.stringToSql(mivo.getzProvince()) + "'");
			sb.append(" ,z_postal_code = '" + CcmUtil.stringToSql(mivo.getzPostalCode()) + "'");
			sb.append(" ,z_country = '" + CcmUtil.stringToSql(mivo.getzCountry()) + "'");
			sb.append(" ,region = '" + CcmUtil.stringToSql(mivo.getRegion()) + "'");
			sb.append(" ,serving_wire_centre = '" + CcmUtil.stringToSql(mivo.getServingWireCentre()) + "'");
			sb.append(" ,aggregator_cid = '" + CcmUtil.stringToSql(mivo.getAggregatorCid()) + "'");
			sb.append(" ,time_slot_vlan_assignment = '" + CcmUtil.stringToSql(mivo.getTimeSlotVlanAssignment()) + "'");
			sb.append(" ,comments = '" + CcmUtil.stringToSql(mivo.getComments()) + "'");
			sb.append(" ,trunk_group_clli = '" + CcmUtil.stringToSql(mivo.getTrunkGroupClli()) + "'");
			sb.append(" ,customer_billing_account = '" + CcmUtil.stringToSql(mivo.getCustomerBillingAccount()) + "'");
			sb.append(" ,business_segment = '" + CcmUtil.stringToSql(mivo.getBusinessSegment()) + "'");
			sb.append(" ,end_user = '" + CcmUtil.stringToSql(mivo.getEndUser()) + "'");
			sb.append(" ,scoa = '" + CcmUtil.stringToSql(mivo.getScoa()) + "'");
			sb.append(" ,owner = '" + CcmUtil.stringToSql(mivo.getOwner()) + "'");
			sb.append(" ,owner_email = '" + CcmUtil.stringToSql(mivo.getOwnerEmail()) + "'");
			if(mivo.getLastSignoffDate() != null && !"".equals(mivo.getLastSignoffDate())){
				sb.append(" ,last_signoff_date = str_to_date('" + CcmUtil.stringToSql(mivo.getLastSignoffDate()) + "', '%Y/%m/%d')");
			} else {
				sb.append(" ,last_signoff_date = null");
			}
			sb.append(" ,usoc = '" + mivo.getUsoc() + "'");
			sb.append(" ,intercompany_business_unit = '" + CcmUtil.stringToSql(mivo.getIntercompanyBusinessUnit()) + "'");
			sb.append(" ,intercompany_channel = '" + CcmUtil.stringToSql(mivo.getIntercompanyChannel()) + "'");
			sb.append(" ,fsa_code = '" + CcmUtil.stringToSql(mivo.getFsaCode()) + "'");
			sb.append(" ,serviceability_fibre = '" + CcmUtil.stringToSql(mivo.getServiceabilityFibre()) + "'");
			sb.append(" ,serviceability_cable = '" + CcmUtil.stringToSql(mivo.getServiceabilityCable()) + "'");
			sb.append(" ,modified_timestamp = now()");
			sb.append(" ,modified_by = " +SystemUtil.getCurrentUserId());
			sb.append(" WHERE id = "+ mivo.getId() +";");
			count = session.createSQLQuery(sb.toString()).executeUpdate();
			
		} catch (RuntimeException re) {
			logger.error("updateMasterInventory error", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}
	
	public long getMasterInventoryRateListPageNo(SearchInventoryDashboardVO sdo){
		Integer count = 0;
		HibernateTemplate template = this.getHibernateTemplate();
		if(sdo.getFilter()!=null){
			final String sql = " select count(1) from ( SELECT concat(m.id,'') as mid," + this.searchMasterInventoryRateShowCol(false) + this.fromAndWhereForRateSQL(sdo,false) + ") dn";
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		} else {	
			final String sql = " select count(1) " +this.fromAndWhereForRateSQL(sdo,false);
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		}
		logger.info("Exit method getMasterInventoryRateListPageNo.");
		return count;
	}
	public List<Map<String,Object>> searchMasterInventoryRateList(SearchInventoryDashboardVO sdo){
		logger.info("Enter method searchMasterInventoryRateList.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.searchMasterInventoryRateQueryString(sdo));
		sql.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		Session session = getSession();
		List list;
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method searchMasterInventoryRateList.");
		return list;
	}
	public List<Object[]> searchMasterInventoryRateComplete(){
		logger.info("Enter method searchMasterInventoryRateComplete.");
		Session session = getSession();
		try {
			StringBuffer sb = new StringBuffer(" SELECT 'Completed' AS complete_flag");
			sb.append("  ,(SELECT COUNT(1)");
			sb.append("  FROM master_inventory m");
			sb.append("  WHERE m.rec_active_flag = 'Y'");
			sb.append("  AND   m.complete_flag = 'Y') AS complete_count");
			sb.append("  UNION "); 
			sb.append("  SELECT 'Not Completed' AS complete_flag "); 
			sb.append("  ,(SELECT COUNT(1) "); 
			sb.append("  FROM master_inventory m "); 
			sb.append("  WHERE m.rec_active_flag = 'Y' "); 
			sb.append("  AND   m.complete_flag = 'N')AS complete_count "); 
			List<Object[]> l = session.createSQLQuery(sb.toString()).list(); 
			logger.info("Exit method searchMasterInventoryRateComplete.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	public String searchMasterInventoryRateShowCol(Boolean isHistory) {
		StringBuffer sb = new StringBuffer();
		sb.append(" ifnull(v.vendor_name, '') AS vendor_name,");
		sb.append(" ifnull(v.summary_vendor_name, '') AS summary_vendor_name,");
		sb.append(" ifnull(b.account_number, '') AS ban_name,");
		sb.append(" ifnull(i.invoice_number, '') AS invoice_number,");
		sb.append(" ifnull(b.line_of_business, '') AS line_of_business,");
		sb.append(" ifnull(mir.stripped_circuit_number, '') AS stripped_circuit_number,");
		sb.append(" ifnull(m.service_id, '') AS service_id,");
		sb.append(" ifnull(m.validation_source_system, '') AS validation_source_system,");
		sb.append(" ifnull(m.circuit_status, '') AS circuit_status,");
		sb.append(" ifnull(date_format(m.installation_date, '%Y-%m-%d'),'') AS installation_date,");
		sb.append(" IFNULL((SELECT date_format(i.invoice_date, '%Y-%m-%d')");
		sb.append(" FROM invoice i");
		sb.append(" WHERE i.id = mir.first_invoice_id),");
		sb.append(" '')");
		sb.append(" AS first_invoice_date,");
		sb.append(" IFNULL((SELECT i.invoice_number");
		sb.append(" FROM invoice i");
		sb.append(" WHERE i.id = mir.first_invoice_id),'') AS first_invoice_number,");
		sb.append(" IFNULL(date_format(m.disconnection_date, '%Y-%m-%d'),'' ) AS disconnection_date,");
		sb.append(" ifnull(m.service_description, '')  AS service_description,");
		sb.append(" ifnull(m.access_type, '') AS access_type,");
		sb.append(" ifnull(pd.product_name,'') AS product_category,");
		sb.append(" ifnull(pc.component_name,'') AS sub_product_category,");
		sb.append(" ifnull(m.project,'') AS project,");
		sb.append(" ifnull(m.project_category_status, '') AS project_category_status,");
		sb.append(" ifnull(m.a_street_number, '') AS a_street_number,");
		sb.append(" ifnull(m.a_street_name, '') AS a_street_name,");
		sb.append(" ifnull(m.a_unit, '') AS a_unit,");
		sb.append(" ifnull(m.a_city, '') AS a_city,");
		sb.append(" ifnull(m.a_province, '') AS a_province,");
		sb.append(" ifnull(m.a_postal_code, '') AS a_postal_code,");
		sb.append(" ifnull(m.aggregator_cid, '') AS aggregator_cid,");
		sb.append(" ifnull(m.customer_billing_account, '') AS customer_billing_account,");
		sb.append(" ifnull(m.business_segment, '') AS business_segment,");
		sb.append(" ifnull(m.end_user, '') AS end_user,");
		sb.append(" ifnull(m.scoa, '') AS scoa,");
		sb.append(" ifnull(m.owner, '') AS owner,");
		sb.append(" ifnull(m.intercompany_business_unit, '') AS intercompany_business_unit,");
		sb.append(" ifnull(m.intercompany_channel, '') AS intercompany_channel,");
		sb.append(" ifnull(ii.usoc, '') AS usoc,");
		sb.append(" ifnull(format(ii.quantity,2), '') AS quantity,");
		sb.append(" ifnull(ii.rate, '') AS billing_rate,");
		sb.append(" ifnull(format(ii.item_amount,2), '') AS item_amount,");
		sb.append(" ifnull(mir.agreement_type, '') AS agreement_type,");
		sb.append(" ifnull(c.name, '') AS contract,");
		sb.append(" ifnull(cf.term_combined, '') AS contract_term,");
		sb.append(" if(cf.penalty_initial_percent IS NULL, '',concat(FORMAT(cf.penalty_initial_percent,2)*100,'%')) AS termination_fee,");
		sb.append(" ifnull(tf.name, '') AS tariff,");
		sb.append(" ifnull(tf.page, '') AS tariff_page,");
		sb.append(" ifnull(mir.rate, '') AS rate,");
		sb.append(" ifnull(mir.rate_effective_date, '') AS rate_effective_date,");
		sb.append(" ifnull(format(mir.base_amount,2), '') AS base_amount,");
		sb.append(" ifnull(mir.rate_multiplier, '') AS rate_multiplier,");
		sb.append(" ifnull(format(mir.discount,2), '') AS discount,");
		sb.append(" ifnull(mir.rate_status, '') AS rate_status,");
		sb.append(" ifnull(mir.rate_discrepancy_flag, '') AS rate_discrepancy_flag,");
		sb.append(" ifnull(date_format(mir.expiry_date, '%Y-%m-%d'),'') AS expiry_date");
		return sb.toString();
	}

	private String fromAndWhereForRateSQL(SearchInventoryDashboardVO sdo, boolean isSort){
		
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM master_inventory_rate mir");
		sb.append(" LEFT JOIN ban b ON b.id = mir.ban_id");
		sb.append(" LEFT JOIN vendor v ON b.vendor_id = v.id");
		sb.append(" LEFT JOIN invoice_item ii ON mir.invoice_item_id = ii.id");
		sb.append(" LEFT JOIN proposal p ON mir.proposal_id = p.id");
		sb.append(" LEFT JOIN master_inventory m ON m.id = mir.master_inventory_id");
		sb.append(" LEFT JOIN invoice i ON mir.latest_invoice_id = i.id");
		sb.append(" LEFT JOIN user u ON mir.modified_by = u.id");
		sb.append(" LEFT JOIN contract c ON c.id = mir.contract_id");
		sb.append(" LEFT JOIN contract_file cf ON cf.id = c.contract_file_id");
		sb.append(" LEFT JOIN tariff tf ON tf.id = mir.tariff_id");
		sb.append(" LEFT JOIN product pd ON p.product_id = pd.id");
		sb.append(" LEFT JOIN product_component pc ON p.product_component_id = pc.id");
		sb.append(" WHERE mir.rec_active_flag = 'Y'");
		if(sdo.getMid() != null && !"all".equals(sdo.getMid())) {
			sb.append(" AND mir.id = " + sdo.getMid());
		}
		
		if(sdo.getSummaryVendorName() != null && !"all".equals(sdo.getSummaryVendorName())) {
			sb.append(" AND v.id in (select id from vendor where summary_vendor_name = '" + sdo.getSummaryVendorName() + "')");
		}
		
		if(sdo.getBanId() != null && !"all".equals(sdo.getBanId())) {
			sb.append(" AND mir.ban_id = " + sdo.getBanId());
		}
		
		if(sdo.getCircuitId() != null && !"".equals(sdo.getCircuitId())) {
			sb.append(" AND mir.stripped_circuit_number LIKE '%" + sdo.getCircuitId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getInventoryId() != null &&  !"".equals(sdo.getInventoryId())) {
			sb.append(" AND mir.id = '" + sdo.getInventoryId() + "'");
		}
		
		if(sdo.getServiceId() != null &&  !"".equals(sdo.getServiceId())) {
			sb.append(" AND m.service_id LIKE '%" + sdo.getServiceId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getCustomerBillingAccount() != null &&  !"".equals(sdo.getCustomerBillingAccount())) {
//			sb.append(" AND (m.customer_billing_account LIKE '%" + sdo.getCustomerBillingAccount().replace("%", "\\%") + "%' OR )");
			String[] cbas = sdo.getCustomerBillingAccount().split(",");
			sb.append(" AND (");
			for(int i = 0; i < cbas.length; i++) {
				if(i == cbas.length - 1) {
					sb.append("m.customer_billing_account LIKE '%" + cbas[i].replace("%", "\\%") + "%'");
				} else {
					sb.append("m.customer_billing_account LIKE '%" + cbas[i].replace("%", "\\%") + "%' OR ");
				}
				
			}
			sb.append(")");
		}
		
		if(sdo.getEndUser() != null &&  !"".equals(sdo.getEndUser())) {
			sb.append(" AND m.end_user LIKE '%" + sdo.getEndUser().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getaStreetNumber() != null &&  !"".equals(sdo.getaStreetNumber())) {
			sb.append(" AND m.a_street_number LIKE '%" + sdo.getaStreetNumber().replace("%", "\\%") + "%'");
		}
		if(sdo.getaStreetName() != null &&  !"".equals(sdo.getaStreetName())) {
			sb.append(" AND m.a_street_name LIKE '%" + sdo.getaStreetName().replace("%", "\\%") + "%'");
		}
		if(sdo.getaUnit() != null &&  !"".equals(sdo.getaUnit())) {
			sb.append(" AND m.a_unit LIKE '%" + sdo.getaUnit().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCity() != null &&  !"".equals(sdo.getaCity())) {
			sb.append(" AND m.a_city LIKE '%" + sdo.getaCity().replace("%", "\\%") + "%'");
		}
		if(sdo.getaProvince() != null &&  !"".equals(sdo.getaProvince())) {
			sb.append(" AND m.a_province LIKE '%" + sdo.getaProvince().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCountry() != null &&  !"".equals(sdo.getaCountry())) {
			sb.append(" AND m.a_country LIKE '%" + sdo.getaCountry().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getIntercompanyBusinessUnit() != null &&  !"".equals(sdo.getIntercompanyBusinessUnit())) {
			sb.append(" AND m.intercompany_business_unit LIKE '%" + sdo.getIntercompanyBusinessUnit().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getFilter()!=null){
			sb.append(" having " + sdo.getFilter());
		}	
		if(isSort){
			sb.append(sdo.getOrderByCause(null));
		}
		
		return sb.toString();
	}
	
	private String searchMasterInventoryRateQueryString(){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT mir.id as mid,");
		sb.append(" ifnull(v.vendor_name, '') AS vendor_name,");
		sb.append(" ifnull(v.summary_vendor_name, '') AS summary_vendor_name,");
		sb.append(" ifnull(b.account_number, '') AS ban_name,");
		sb.append(" ifnull(i.invoice_number, '') AS invoice_number,");
		sb.append(" ifnull(b.line_of_business, '') AS line_of_business,");
		sb.append(" ifnull(mir.stripped_circuit_number, '') AS stripped_circuit_number,");
		sb.append(" ifnull(m.service_id, '') AS service_id,");
		sb.append(" ifnull(m.validation_source_system, '') AS validation_source_system,");
		sb.append(" ifnull(m.circuit_status, '') AS circuit_status,");
		sb.append(" ifnull(date_format(m.installation_date, '%Y-%m-%d'),'') AS installation_date,");
		sb.append(" IFNULL((SELECT date_format(i.invoice_date, '%Y-%m-%d')");
		sb.append(" FROM invoice i");
		sb.append(" WHERE i.id = mir.first_invoice_id),");
		sb.append(" '')");
		sb.append(" AS first_invoice_date,");
		sb.append(" IFNULL((SELECT i.invoice_number");
		sb.append(" FROM invoice i");
		sb.append(" WHERE i.id = mir.first_invoice_id),'') AS first_invoice_number,");
		sb.append(" IFNULL(date_format(m.disconnection_date, '%Y-%m-%d'),'' ) AS disconnection_date,");
		sb.append(" ifnull(m.service_description, '')  AS service_description,");
		sb.append(" ifnull(m.access_type, '') AS access_type,");
		sb.append(" ifnull(pd.product_name,'') AS product_category,");
		sb.append(" ifnull(pc.component_name,'') AS sub_product_category,");
		sb.append(" ifnull(m.project,'') AS project,");
		sb.append(" ifnull(m.project_category_status, '') AS project_category_status,");
		sb.append(" ifnull(m.a_street_number, '') AS a_street_number,");
		sb.append(" ifnull(m.a_street_name, '') AS a_street_name,");
		sb.append(" ifnull(m.a_unit, '') AS a_unit,");
		sb.append(" ifnull(m.a_city, '') AS a_city,");
		sb.append(" ifnull(m.a_province, '') AS a_province,");
		sb.append(" ifnull(m.a_postal_code, '') AS a_postal_code,");
		sb.append(" ifnull(m.aggregator_cid, '') AS aggregator_cid,");
		sb.append(" ifnull(m.customer_billing_account, '') AS customer_billing_account,");
		sb.append(" ifnull(m.business_segment, '') AS business_segment,");
		sb.append(" ifnull(m.end_user, '') AS end_user,");
		sb.append(" ifnull(m.scoa, '') AS scoa,");
		sb.append(" ifnull(m.owner, '') AS owner,");
		sb.append(" ifnull(m.intercompany_business_unit, '') AS intercompany_business_unit,");
		sb.append(" ifnull(m.intercompany_channel, '') AS intercompany_channel,");
		sb.append(" ifnull(ii.usoc, '') AS usoc,");
		sb.append(" ifnull(format(ii.quantity,2), '') AS quantity,");
		sb.append(" ifnull(ii.rate, '') AS billing_rate,");
		sb.append(" ifnull(format(ii.item_amount,2), '') AS item_amount,");
		sb.append(" ifnull(mir.agreement_type, '') AS agreement_type,");
		sb.append(" ifnull(c.name, '') AS contract,");
		sb.append(" ifnull(cf.term_combined, '') AS contract_term,");
		sb.append(" if(cf.penalty_initial_percent IS NULL, '',concat(FORMAT(cf.penalty_initial_percent,2)*100,'%')) AS termination_fee,");
		sb.append(" ifnull(tf.name, '') AS tariff,");
		sb.append(" ifnull(tf.page, '') AS tariff_page,");
		sb.append(" ifnull(mir.rate, '') AS rate,");
		sb.append(" ifnull(mir.rate_effective_date, '') AS rate_effective_date,");
		sb.append(" ifnull(format(mir.base_amount,2), '') AS base_amount,");
		sb.append(" ifnull(mir.rate_multiplier, '') AS rate_multiplier,");
		sb.append(" ifnull(format(mir.discount,2), '') AS discount,");
		sb.append(" ifnull(mir.rate_status, '') AS rate_status,");
		sb.append(" ifnull(mir.rate_discrepancy_flag, '') AS rate_discrepancy_flag,");
		sb.append(" ifnull(date_format(mir.expiry_date, '%Y-%m-%d'),'') AS expiry_date");
		return sb.toString();
	}
	
	private String searchMasterInventoryRateQueryString(SearchInventoryDashboardVO sdo){
		logger.info("Enter method searchMasterInventoryRateQueryString.");
		
		StringBuffer sb = new StringBuffer(" SELECT mir.id as mid,");
		sb.append(this.searchMasterInventoryRateShowCol(false));
		sb.append(this.fromAndWhereSQLForRate(sdo));
		logger.info("Exit method searchDisputeActionRequestQueryString.");
		return sb.toString();
	}
	
	
	private String fromAndWhereSQLForRate(SearchInventoryDashboardVO sdo){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM master_inventory_rate mir");
		sb.append(" LEFT JOIN ban b ON b.id = mir.ban_id");
		sb.append(" LEFT JOIN vendor v ON b.vendor_id = v.id");
		sb.append(" LEFT JOIN invoice_item ii ON mir.invoice_item_id = ii.id");
		sb.append(" LEFT JOIN proposal p ON mir.proposal_id = p.id");
		sb.append(" LEFT JOIN master_inventory m ON m.id = mir.master_inventory_id");
		sb.append(" LEFT JOIN invoice i ON mir.latest_invoice_id = i.id");
		sb.append(" LEFT JOIN user u ON mir.modified_by = u.id");
		sb.append(" LEFT JOIN contract c ON c.id = mir.contract_id");
		sb.append(" LEFT JOIN contract_file cf ON cf.id = c.contract_file_id");
		sb.append(" LEFT JOIN tariff tf ON tf.id = mir.tariff_id");
		sb.append(" LEFT JOIN product pd ON p.product_id = pd.id");
		sb.append(" LEFT JOIN product_component pc ON p.product_component_id = pc.id");
		sb.append(" WHERE mir.rec_active_flag = 'Y'");
		
		if(sdo.getMid() != null && !"all".equals(sdo.getMid())) {
			sb.append(" AND mir.id = " + sdo.getMid());
		}
		
		if(sdo.getSummaryVendorName() != null && !"all".equals(sdo.getSummaryVendorName())) {
			sb.append(" AND v.id in (select id from vendor where summary_vendor_name = '" + sdo.getSummaryVendorName() + "')");
		}
		
		if(sdo.getBanId() != null && !"all".equals(sdo.getBanId())) {
			sb.append(" AND mir.ban_id = " + sdo.getBanId());
		}
		
		if(sdo.getCircuitId() != null && !"".equals(sdo.getCircuitId())) {
			sb.append(" AND mir.stripped_circuit_number LIKE '%" + sdo.getCircuitId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getInventoryId() != null &&  !"".equals(sdo.getInventoryId())) {
			sb.append(" AND m.id = '" + sdo.getInventoryId() + "'");
		}
		
		if(sdo.getServiceId() != null &&  !"".equals(sdo.getServiceId())) {
			sb.append(" AND m.service_id LIKE '%" + sdo.getServiceId().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getCustomerBillingAccount() != null &&  !"".equals(sdo.getCustomerBillingAccount())) {
//			sb.append(" AND (m.customer_billing_account LIKE '%" + sdo.getCustomerBillingAccount().replace("%", "\\%") + "%' OR )");
			String[] cbas = sdo.getCustomerBillingAccount().split(",");
			sb.append(" AND (");
			for(int i = 0; i < cbas.length; i++) {
				if(i == cbas.length - 1) {
					sb.append("m.customer_billing_account LIKE '%" + cbas[i].replace("%", "\\%") + "%'");
				} else {
					sb.append("m.customer_billing_account LIKE '%" + cbas[i].replace("%", "\\%") + "%' OR ");
				}
				
			}
			sb.append(")");
		}
		
		if(sdo.getEndUser() != null &&  !"".equals(sdo.getEndUser())) {
			sb.append(" AND m.end_user LIKE '%" + sdo.getEndUser().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getaStreetNumber() != null &&  !"".equals(sdo.getaStreetNumber())) {
			sb.append(" AND m.a_street_number LIKE '%" + sdo.getaStreetNumber().replace("%", "\\%") + "%'");
		}
		if(sdo.getaStreetName() != null &&  !"".equals(sdo.getaStreetName())) {
			sb.append(" AND m.a_street_name LIKE '%" + sdo.getaStreetName().replace("%", "\\%") + "%'");
		}
		if(sdo.getaUnit() != null &&  !"".equals(sdo.getaUnit())) {
			sb.append(" AND m.a_unit LIKE '%" + sdo.getaUnit().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCity() != null &&  !"".equals(sdo.getaCity())) {
			sb.append(" AND m.a_city LIKE '%" + sdo.getaCity().replace("%", "\\%") + "%'");
		}
		if(sdo.getaProvince() != null &&  !"".equals(sdo.getaProvince())) {
			sb.append(" AND m.a_province LIKE '%" + sdo.getaProvince().replace("%", "\\%") + "%'");
		}
		if(sdo.getaCountry() != null &&  !"".equals(sdo.getaCountry())) {
			sb.append(" AND m.a_country LIKE '%" + sdo.getaCountry().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getIntercompanyBusinessUnit() != null &&  !"".equals(sdo.getIntercompanyBusinessUnit())) {
			sb.append(" AND m.intercompany_business_unit LIKE '%" + sdo.getIntercompanyBusinessUnit().replace("%", "\\%") + "%'");
		}
		
		if(sdo.getFilter()!=null){
			sb.append(" having " + sdo.getFilter());
		}	
		sb.append(sdo.getOrderByCause(null));
		
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public long getMasterInventoryRateDataForExcelPageNo(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getMasterInventoryRateDataForExcelPageNo.");
		Integer count = 0;
		HibernateTemplate template = this.getHibernateTemplate();
		if(sdo.getFilter()!=null){
			final String sql = " select count(1) from ( SELECT concat(mir.id,'') as mid," + this.searchMasterInventoryRateShowCol(false) + this.fromAndWhereForRateSQL(sdo,false) + ") dn";
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		} else {	
			final String sql = " select count(1) " +this.fromAndWhereForRateSQL(sdo,false);
			count = (Integer)template.execute(new HibernateCallback() {
			    @SuppressWarnings("unchecked")
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
			    	List l = session.createSQLQuery(sql).list();
			    	return new Integer(l.get(0).toString());
			    }
			});
		}
		logger.info("Exit method getMasterInventoryRateDataForExcelPageNo.");
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getMasterInventoryRateDataForExcel(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getMasterInventoryRateDataForExcel.");
		final String sql = this.getMasterInventoryListRateForExcelQueryString(sdo);
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		sb.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		List<Object[]> re;
		Session session = getSession();
		try {
			re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getMasterInventoryRateDataForExcel.");
			return re;	
		} finally {
			releaseSession(session);
		}
	}
	
	public String getMasterInventoryListRateForExcelQueryString(SearchInventoryDashboardVO sdo) {
		
		logger.info("Enter method getMasterInventoryListRateForExcelQueryString.");
		StringBuffer sb = new StringBuffer(" SELECT concat(mir.id,'') as mid,"); // Inventory Id
		sb.append(this.searchMasterInventoryRateShowCol(false));
		sb.append(this.fromAndWhereForRateSQL(sdo,true));
		return sb.toString();
	}
	
	/**
	 * 查询 Master Inventory Rate 历史修改记录 行数
	 * @param  sdo [description]
	 * @return     [description]
	 */
	public long getInventoryRateChangeHistoryListPageNo(SearchInventoryDashboardVO sdo){
		logger.info("Enter method getInventoryRateChangeHistoryListPageNo.");
		final String sql = " select count(1) from ("+this.searchInventoryRateChangeHistoryQueryString(sdo,false)+ ") dn";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getInventoryRateChangeHistoryListPageNo.");
		return count;
	}
	/**
	 * 查询 Master Inventory Rate历史修改记录 列表
	 * @param  sdo [description]
	 * @return     [description]
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getInventoryRateChangeHistoryList(SearchInventoryDashboardVO sdo){
		logger.info("Enter method getInventoryRateChangeHistoryList.");
		StringBuilder sql = new StringBuilder();
		sql.append(this.searchInventoryRateChangeHistoryQueryString(sdo,false));
		sql.append(" LIMIT " + sdo.getStartIndex() + "," + sdo.getRecPerPage());
		Session session = getSession();
		List list;
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method getInventoryRateChangeHistoryList.");
		return list;
	}
	/**
	 * 查询 Master Inventory Update History count 的SQL语句。
	 * @param  sdo [description]
	 * @return     [description]
	 */
	private String searchInventoryRateChangeHistoryQueryString(SearchInventoryDashboardVO sdo,boolean isExcel){
		
		StringBuffer sb = new StringBuffer(" SELECT ifnull(concat(first_name, ' ', last_name), '') AS user_name,");
		sb.append(" 	       IFNULL(date_format(mir.op_timestamp, '%Y-%m-%d %H:%i:%s'), '')	");
		sb.append(" 	          AS history_modified_timestamp,	");
		sb.append(" 	       concat(mir.id,'') AS mid,	");
		sb.append(" 	       ifnull(v.vendor_name, '') AS vendor_name,	");
		sb.append(" 	       ifnull(v.summary_vendor_name, '') AS summary_vendor_name,	");
		sb.append(" 	       ifnull(b.account_number, '') AS ban_name,	");
		sb.append(" 	       ifnull(i.invoice_number, '') AS invoice_number,	");
		sb.append(" 	       ifnull(b.line_of_business, '') AS line_of_business,	");
		sb.append(" 	       ifnull(mir.stripped_circuit_number, '') AS stripped_circuit_number,	");
		sb.append(" 	       ifnull(mh.service_id, '') AS service_id,	");
		sb.append(" 	       ifnull(mh.validation_source_system, '') AS validation_source_system,	");
		sb.append(" 	       ifnull(mh.circuit_status, '') AS circuit_status,	");
		sb.append(" 	       ifnull(date_format(mh.installation_date, '%Y-%m-%d'), '')	");
		sb.append(" 	          AS installation_date,	");
		sb.append(" 	       IFNULL((SELECT date_format(i.invoice_date, '%Y-%m-%d')	");
		sb.append(" 	                 FROM invoice i	");
		sb.append(" 	                WHERE i.id = mir.first_invoice_id),	");
		sb.append(" 	              '')	");
		sb.append(" 	          AS first_invoice_date,	");
		sb.append(" 	       IFNULL((SELECT i.invoice_number	");
		sb.append(" 	                 FROM invoice i	");
		sb.append(" 	                WHERE i.id = mir.first_invoice_id),	");
		sb.append(" 	              '')	");
		sb.append(" 	          AS first_invoice_number,	");
		sb.append(" 	       IFNULL(date_format(mh.disconnection_date, '%Y-%m-%d'), '')	");
		sb.append(" 	          AS disconnection_date,	");
		sb.append(" 	       ifnull(mh.service_description, '') AS service_description,	");
		sb.append(" 	       ifnull(mh.access_type, '') AS access_type,	");
		sb.append(" 	       ifnull(pd.product_name, '') AS product_category,	");
		sb.append(" 	       ifnull(pc.component_name, '') AS sub_product_category,	");
		sb.append(" 	       ifnull(mh.project, '') AS project,	");
		sb.append(" 	       ifnull(mh.project_category_status, '') AS project_category_status,	");
		sb.append(" 	       ifnull(mh.a_street_number, '') AS a_street_number,	");
		sb.append(" 	       ifnull(mh.a_street_name, '') AS a_street_name,	");
		sb.append(" 	       ifnull(mh.a_unit, '') AS a_unit,	");
		sb.append(" 	       ifnull(mh.a_city, '') AS a_city,	");
		sb.append(" 	       ifnull(mh.a_province, '') AS a_province,	");
		sb.append(" 	       ifnull(mh.a_postal_code, '') AS a_postal_code,	");
		sb.append(" 	       ifnull(mh.aggregator_cid, '') AS aggregator_cid,	");
		sb.append(" 	       ifnull(mh.customer_billing_account, '') AS customer_billing_account,	");
		sb.append(" 	       ifnull(mh.business_segment, '') AS business_segment,	");
		sb.append(" 	       ifnull(mh.end_user, '') AS end_user,	");
		sb.append(" 	       ifnull(mh.scoa, '') AS scoa,	");
		sb.append(" 	       ifnull(mh.owner, '') AS owner,	");
		sb.append(" 	       ifnull(mh.intercompany_business_unit, '')	");
		sb.append(" 	          AS intercompany_business_unit,	");
		sb.append(" 	       ifnull(mh.intercompany_channel, '') AS intercompany_channel,	");
		sb.append(" 	       ifnull(ii.usoc, '') AS usoc,	");
		sb.append(" 	       ifnull(format(ii.quantity, 2), '') AS quantity,	");
		sb.append(" 	       ifnull(ii.rate, '') AS billing_rate,	");
		sb.append(" 	       ifnull(format(ii.item_amount, 2), '') AS item_amount,	");
		sb.append(" 	       ifnull(mir.agreement_type, '') AS agreement_type,	");
		sb.append(" 	       ifnull(c.name, '') AS contract,	");
		sb.append(" 	       ifnull(cf.term_combined, '') AS contract_term,	");
		sb.append(" 	       if(cf.penalty_initial_percent IS NULL,	");
		sb.append(" 	          '',	");
		sb.append(" 	          concat(FORMAT(cf.penalty_initial_percent, 2) * 100, '%'))	");
		sb.append(" 	          AS termination_fee,	");
		sb.append(" 	       ifnull(tf.name, '') AS tariff,	");
		sb.append(" 	       ifnull(tf.page, '') AS tariff_page,	");
		sb.append(" 	       ifnull(mir.rate, '') AS rate,	");
		sb.append(" 	       ifnull(mir.rate_effective_date, '') AS rate_effective_date,	");
		sb.append(" 	       ifnull(format(mir.base_amount,2), '') AS base_amount,	");
		sb.append(" 	       ifnull(mir.rate_multiplier, '') AS rate_multiplier,	");
		sb.append(" 	       ifnull(format(mir.discount,2), '') AS discount,	");
		sb.append(" 	       ifnull(mir.rate_status, '') AS rate_status,	");
		sb.append(" 	       ifnull(mir.rate_discrepancy_flag, '') AS rate_discrepancy_flag,	");
		sb.append(" 	       ifnull(date_format(mir.expiry_date, '%Y-%m-%d'), '') AS expiry_date	");
		sb.append(" 	  FROM history_db.master_inventory_rate_history mir	");
		sb.append(" 	       LEFT JOIN ban b ON b.id = mir.ban_id	");
		sb.append(" 	       LEFT JOIN vendor v ON b.vendor_id = v.id	");
		sb.append(" 	       LEFT JOIN invoice_item ii ON mir.invoice_item_id = ii.id	");
		sb.append(" 	       LEFT JOIN proposal p ON mir.proposal_id = p.id	");
		sb.append(" 	       LEFT JOIN history_db.master_inventory_history mh	");
		sb.append(" 	       		ON     mir.master_inventory_id = mh.id	");
		sb.append(" 	       			AND mir.op_timestamp <= mh.op_timestamp	");
		sb.append(" 	       			AND (SELECT mirh.op_timestamp	");
		sb.append(" 	       			FROM history_db.master_inventory_rate_history mirh	");
		sb.append(" 	       			WHERE     mirh.id = mir.id	");
		sb.append(" 	       			AND mirh.op_timestamp > mir.op_timestamp	");
		sb.append(" 	       			ORDER BY mirh.op_timestamp	");
		sb.append(" 	       			LIMIT 1) >= mh.op_timestamp	");
		sb.append(" 	       LEFT JOIN invoice i ON mir.latest_invoice_id = i.id	");
		sb.append(" 	       LEFT JOIN user u ON mir.modified_by = u.id	");
		sb.append(" 	       LEFT JOIN contract c ON c.id = mir.contract_id	");
		sb.append(" 	       LEFT JOIN contract_file cf ON cf.id = c.contract_file_id	");
		sb.append(" 	       LEFT JOIN tariff tf ON tf.id = mir.tariff_id	");
		sb.append(" 	       LEFT JOIN product pd ON p.product_id = pd.id	");
		sb.append(" 	       LEFT JOIN product_component pc ON p.product_component_id = pc.id	");
		sb.append(" 	 WHERE mir.rec_active_flag = 'Y' AND mir.id = " +sdo.getInventoryId());
		sb.append(" 	UNION ALL	");
		sb.append(" 	SELECT ifnull(concat(first_name, ' ', last_name), '') AS user_name,	");
		sb.append(" 	       IFNULL(date_format(mh.op_timestamp, '%Y-%m-%d %H:%i:%s'), '')	");
		sb.append(" 	          AS history_modified_timestamp,	");
		sb.append(" 	       concat(mir.id,'') AS mid,	");
		sb.append(" 	       ifnull(v.vendor_name, '') AS vendor_name,	");
		sb.append(" 	       ifnull(v.summary_vendor_name, '') AS summary_vendor_name,	");
		sb.append(" 	       ifnull(b.account_number, '') AS ban_name,	");
		sb.append(" 	       ifnull(i.invoice_number, '') AS invoice_number,	");
		sb.append(" 	       ifnull(b.line_of_business, '') AS line_of_business,	");
		sb.append(" 	       ifnull(mir.stripped_circuit_number, '') AS stripped_circuit_number,	");
		sb.append(" 	       ifnull(mh.service_id, '') AS service_id,	");
		sb.append(" 	       ifnull(mh.validation_source_system, '') AS validation_source_system,	");
		sb.append(" 	       ifnull(mh.circuit_status, '') AS circuit_status,	");
		sb.append(" 	       ifnull(date_format(mh.installation_date, '%Y-%m-%d'), '')	");
		sb.append(" 	          AS installation_date,	");
		sb.append(" 	       IFNULL((SELECT date_format(i.invoice_date, '%Y-%m-%d')	");
		sb.append(" 	                 FROM invoice i	");
		sb.append(" 	                WHERE i.id = mir.first_invoice_id),	");
		sb.append(" 	              '')	");
		sb.append(" 	          AS first_invoice_date,	");
		sb.append(" 	       IFNULL((SELECT i.invoice_number	");
		sb.append(" 	                 FROM invoice i	");
		sb.append(" 	                WHERE i.id = mir.first_invoice_id),	");
		sb.append(" 	              '')	");
		sb.append(" 	          AS first_invoice_number,	");
		sb.append(" 	       IFNULL(date_format(mh.disconnection_date, '%Y-%m-%d'), '')	");
		sb.append(" 	          AS disconnection_date,	");
		sb.append(" 	       ifnull(mh.service_description, '') AS service_description,	");
		sb.append(" 	       ifnull(mh.access_type, '') AS access_type,	");
		sb.append(" 	       ifnull(pd.product_name, '') AS product_category,	");
		sb.append(" 	       ifnull(pc.component_name, '') AS sub_product_category,	");
		sb.append(" 	       ifnull(mh.project, '') AS project,	");
		sb.append(" 	       ifnull(mh.project_category_status, '') AS project_category_status,	");
		sb.append(" 	       ifnull(mh.a_street_number, '') AS a_street_number,	");
		sb.append(" 	       ifnull(mh.a_street_name, '') AS a_street_name,	");
		sb.append(" 	       ifnull(mh.a_unit, '') AS a_unit,	");
		sb.append(" 	       ifnull(mh.a_city, '') AS a_city,	");
		sb.append(" 	       ifnull(mh.a_province, '') AS a_province,	");
		sb.append(" 	       ifnull(mh.a_postal_code, '') AS a_postal_code,	");
		sb.append(" 	       ifnull(mh.aggregator_cid, '') AS aggregator_cid,	");
		sb.append(" 	       ifnull(mh.customer_billing_account, '') AS customer_billing_account,	");
		sb.append(" 	       ifnull(mh.business_segment, '') AS business_segment,	");
		sb.append(" 	       ifnull(mh.end_user, '') AS end_user,	");
		sb.append(" 	       ifnull(mh.scoa, '') AS scoa,	");
		sb.append(" 	       ifnull(mh.owner, '') AS owner,	");
		sb.append(" 	       ifnull(mh.intercompany_business_unit, '')	");
		sb.append(" 	          AS intercompany_business_unit,	");
		sb.append(" 	       ifnull(mh.intercompany_channel, '') AS intercompany_channel,	");
		sb.append(" 	       ifnull(ii.usoc, '') AS usoc,	");
		sb.append(" 	       ifnull(format(ii.quantity, 2), '') AS quantity,	");
		sb.append(" 	       ifnull(ii.rate, '') AS billing_rate,	");
		sb.append(" 	       ifnull(format(ii.item_amount, 2), '') AS item_amount,	");
		sb.append(" 	       ifnull(mir.agreement_type, '') AS agreement_type,	");
		sb.append(" 	       ifnull(c.name, '') AS contract,	");
		sb.append(" 	       ifnull(cf.term_combined, '') AS contract_term,	");
		sb.append(" 	       if(cf.penalty_initial_percent IS NULL,	");
		sb.append(" 	          '',	");
		sb.append(" 	          concat(FORMAT(cf.penalty_initial_percent, 2) * 100, '%'))	");
		sb.append(" 	          AS termination_fee,	");
		sb.append(" 	       ifnull(tf.name, '') AS tariff,	");
		sb.append(" 	       ifnull(tf.page, '') AS tariff_page,	");
		sb.append(" 	       ifnull(mir.rate, '') AS rate,	");
		sb.append(" 	       ifnull(mir.rate_effective_date, '') AS rate_effective_date,	");
		sb.append(" 	       ifnull(format(mir.base_amount,2), '') AS base_amount,	");
		sb.append(" 	       ifnull(mir.rate_multiplier, '') AS rate_multiplier,	");
		sb.append(" 	       ifnull(format(mir.discount,2), '') AS discount,	");
		sb.append(" 	       ifnull(mir.rate_status, '') AS rate_status,	");
		sb.append(" 	       ifnull(mir.rate_discrepancy_flag, '') AS rate_discrepancy_flag,	");
		sb.append(" 	       ifnull(date_format(mir.expiry_date, '%Y-%m-%d'), '') AS expiry_date	");
		sb.append(" 	  FROM history_db.master_inventory_history mh	");
		sb.append(" 	       LEFT JOIN master_inventory_rate mir	");
		sb.append(" 	          ON     mir.master_inventory_id = mh.id	");
		sb.append(" 	       LEFT JOIN ban b ON b.id = mir.ban_id	");
		sb.append(" 	       LEFT JOIN vendor v ON b.vendor_id = v.id	");
		sb.append(" 	       LEFT JOIN invoice_item ii ON mir.invoice_item_id = ii.id	");
		sb.append(" 	       LEFT JOIN proposal p ON mir.proposal_id = p.id	");
		sb.append(" 	       LEFT JOIN invoice i ON mir.latest_invoice_id = i.id	");
		sb.append(" 	       LEFT JOIN user u ON mh.modified_by = u.id	");
		sb.append(" 	       LEFT JOIN contract c ON c.id = mir.contract_id	");
		sb.append(" 	       LEFT JOIN contract_file cf ON cf.id = c.contract_file_id	");
		sb.append(" 	       LEFT JOIN tariff tf ON tf.id = mir.tariff_id	");
		sb.append(" 	       LEFT JOIN product pd ON p.product_id = pd.id	");
		sb.append(" 	       LEFT JOIN product_component pc ON p.product_component_id = pc.id	");
		sb.append(" 	 WHERE mir.rec_active_flag = 'Y' AND mir.id = " +sdo.getInventoryId());
		
		if(sdo.getFilter()!=null){
			sb.append(" having " + sdo.getFilter());
		}	
		if(isExcel){
			sb.append(" ORDER BY history_modified_timestamp desc");
		}else{
			sb.append(sdo.getOrderByCause(null));
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getInventoryRateChangeHistoryDataForExcel(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getInventoryRateChangeHistoryDataForExcel.");
		final String sql = this.searchInventoryRateChangeHistoryForExcelQueryString(sdo);
		List<Object[]> re;
		Session session = getSession();
		try {
			re = session.createSQLQuery(sql).list();
			logger.info("Exit method getInventoryRateChangeHistoryDataForExcel.");
			return re;	
		} finally {
			releaseSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	public long getInventoryRateChangeHistoryDataForExcelPageNo(SearchInventoryDashboardVO sdo) {
		logger.info("Enter method getInventoryRateChangeHistoryDataForExcelPageNo.");
		final String sql = " select count(1) from (" +this.searchInventoryRateChangeHistoryForExcelQueryString(sdo)+") t ";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getInventoryRateChangeHistoryDataForExcelPageNo.");
		return count;
	}
	
	private String searchInventoryRateChangeHistoryForExcelQueryString(SearchInventoryDashboardVO sdo){
		
//		StringBuffer sb = new StringBuffer(" SELECT ifnull(concat(first_name , ' ' , last_name),'') as user_name,"); 
//		sb.append(" IFNULL(date_format(mir.modified_timestamp, '%Y-%m-%d %h:%i:%s'),'') AS history_modified_timestamp,");
//		sb.append(" concat(mir.id,'') as mid,");
//		sb.append(this.searchMasterInventoryRateShowCol(true));
//		sb.append(" FROM history_db.master_inventory_rate_history mir");
//		
//		sb.append(" LEFT JOIN ban b ON b.id = mir.ban_id");
//		sb.append(" LEFT JOIN vendor v ON b.vendor_id = v.id");
//		sb.append(" LEFT JOIN invoice_item ii ON mir.invoice_item_id = ii.id");
//		sb.append(" LEFT JOIN proposal p ON mir.proposal_id = p.id");
//		sb.append(" LEFT JOIN master_inventory m ON m.id = mir.master_inventory_id");
//		sb.append(" LEFT JOIN invoice i ON mir.latest_invoice_id = i.id");
//		sb.append(" LEFT JOIN user u ON mir.modified_by = u.id");
//		sb.append(" LEFT JOIN contract c ON c.id = mir.contract_id");
//		sb.append(" LEFT JOIN contract_file cf ON cf.id = c.contract_file_id");
//		sb.append(" LEFT JOIN tariff tf ON tf.id = mir.tariff_id");
//		sb.append(" LEFT JOIN product pd ON p.product_id = pd.id");
//		sb.append(" LEFT JOIN product_component pc ON p.product_component_id = pc.id");
//		sb.append(" WHERE mir.rec_active_flag = 'Y'");
//		sb.append(" AND mir.id = " + sdo.getInventoryId());
//		
//		if(sdo != null){
//			if(sdo.getFilter()!=null){
//				sb.append(" having " + sdo.getFilter());
//			}	
//			sdo.setSortField("modified_timestamp");
//			sdo.setSortingDirection("desc");
//			sb.append(sdo.getOrderByCause("mir"));
//		}
		
		return this.searchInventoryRateChangeHistoryQueryString(sdo,true);
	}
}






