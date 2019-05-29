package com.saninco.ccm.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.SystemUtil;

public class QuoteDaoImpl extends HibernateDaoSupport implements IQuoteDao {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public QuoteDaoImpl() {
	}
	

	public void createQuoteTemporary(){
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(
					"CREATE TABLE quote_import (`id` int(11) NOT NULL AUTO_INCREMENT," +
					"`batch_no`  varchar(64)," +
					"`row_no`  int(11)," +
					"`record_id`  varchar(32)," +
					"`status`  varchar(32)  ," +
					"`date_recvd`  varchar(32) ," +
					"`date_issued`  varchar(32) ," +
					"`days_to_quote`  varchar(32) ," +
					"`type`  varchar(32) ," +
					"`zendesk_quote_id`  varchar(32) ," +
					"`opportunity_sites`  varchar(32) ," +
					"`nsa`  varchar(128) ," +
					"`other_requester`  varchar(128) ," +
					"`enterprise_customer`  varchar(128) ," +
					"`customer_type`  varchar(64) ," +
					"`customer_segment`  varchar(64) ," +
					"`product_group`  varchar(32) ," +
					"`product_sub_group`  varchar(32) ," +
					"`product_requested`  varchar(32) ," +
					"`product_internal`  varchar(32) ," +
					"`site_per_product`  varchar(32) ," +
					"`provider`  varchar(64) ," +
					"`quantity`  varchar(32) ," +
					"`product_external`  varchar(32) ," +
					"`access_mb`  varchar(32) ," +
					"`evc_1`  varchar(128) ," +
					"`class_of_service_1`  varchar(32) ," +
					"`evc_2`  varchar(128) ," +
					"`class_of_service_2`  varchar(32) ," +
					"`no_of_port`  varchar(32) ," +
					"`addn_evcs`  varchar(128) ," +
					"`party_date_requested`  varchar(32) ," +
					"`party_date_received`  varchar(32) ," +
					"`quote_provider_days`  varchar(32) ," +
					"`on_near_net`  varchar(32) ," +
					"`currency`  varchar(32) ," +
					"`access`  varchar(32) ," +
					"`evc_number_1`  varchar(32) ," +
					"`evc_number_2`  varchar(32) ," +
					"`evc_number_3`  varchar(32) ," +
					"`port_charge`  varchar(32) ," +
					"`mrc_year_1`  varchar(32) ," +
					"`mrc_year_2`  varchar(32) ," +
					"`mrc_year_3`  varchar(32) ," +
					"`mrc_year_5`  varchar(32) ," +
					"`mrc_year_7`  varchar(32) ," +
					"`mrc_year_10`  varchar(32) ," +
					"`mrc_year_15`  varchar(32) ," +
					"`nrc`  varchar(32) ," +
					"`construction_costs`  varchar(32) ," +
					"`a_street`  varchar(255) ," +
					"`a_unit`  varchar(255) ," +
					"`a_city`  varchar(64) ," +
					"`a_postal_code`  varchar(16) ," +
					"`a_province`  varchar(64) ," +
					"`a_country`  varchar(64) ," +
					"`z_street`  varchar(255) ," +
					"`z_unit`  varchar(255) ," +
					"`z_city`  varchar(64) ," +
					"`z_postal_code`  varchar(16) ," +
					"`z_province`  varchar(64) ," +
					"`z_country`  varchar(64) ," +
					"`notes`  varchar(768) ," +
					"`carrier_quote`  varchar(128) ," +
					"`cbs_mrc_original`  varchar(32) ," +
					"`cbs_nrc_construction_original`  varchar(32) ," +
					"`quote_desk_rep`  varchar(32) ," +
					"`firm_won_bid`  varchar(32) ," +
					"`cost_savings`  varchar(32) , " +
					"`is_update`  char(1) DEFAULT 'Y', " +
					"`user_id`  int(11) , " +
					"PRIMARY KEY (`id`))" +
					"ENGINE=InnoDB DEFAULT CHARACTER SET=utf8 COLLATE=utf8_unicode_ci");
			query.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		} finally {
			releaseSession(session);
		}
	}
	public void insertQuoteTemporary(List<Map<String,String>> dataList,List<String> columnList,String batchNo) throws SQLException{
		StringBuffer insertSb = new StringBuffer("insert into quote_import (`quote_id`  ," +
					"`record_id`    ," +
					"`status`    ," +
					"`date_recvd`   ," +
					"`date_issued`   ," +
					"`days_to_quote`   ," +
					"`type`   ," +
					"`zendesk_quote_id`   ," +
					"`opportunity_sites`   ," +
					"`nsa`  ," +
					"`other_requester`  ," +
					"`enterprise_customer`  ," +
					"`customer_type`  ," +
					"`customer_segment`  ," +
					"`product_group`   ," +
					"`product_sub_group`   ," +
					"`product_requested`   ," +
					"`product_internal`   ," +
					"`site_per_product`   ," +
					"`provider`  ," +
					"`quantity`   ," +
					"`product_external`   ," +
					"`access_mb`   ," +
					"`evc_1`  ," +
					"`class_of_service_1`   ," +
					"`evc_2`  ," +
					"`class_of_service_2`   ," +
					"`no_of_port`   ," +
					"`addn_evcs`  ," +
					"`party_date_requested`   ," +
					"`party_date_received`   ," +
					"`quote_provider_days`   ," +
					"`on_near_net`   ," +
					"`currency`   ," +
					"`access`   ," +
					"`evc_number_1`   ," +
					"`evc_number_2`   ," +
					"`evc_number_3`   ," +
					"`port_charge`   ," +
					"`mrc_year_1`   ," +
					"`mrc_year_2`   ," +
					"`mrc_year_3`   ," +
					"`mrc_year_5`   ," +
					"`mrc_year_7`   ," +
					"`mrc_year_10`   ," +
					"`mrc_year_15`   ," +
					"`nrc`   ," +
					"`construction_costs`   ," +
					"`a_unit`  ," +
					"`a_street_number`  ," +
					"`a_street_name`  ," +
					"`a_city`   ," +
					"`a_province`  ," +
					"`a_postal_code`   ," +
					"`a_country`  ," +
					"`z_unit`  ," +
					"`z_street_number`  ," +
					"`z_street_name`  ," +
					"`z_city`   ," +
					"`z_province`  ," +
					"`z_postal_code`   ," +
					"`z_country`  ," +
					"`notes`  ," +
					"`carrier_quote`  ," +
					"`cbs_mrc_original`   ," +
					"`cbs_nrc_construction_original`   ," +
					"`quote_desk_rep`   ," +
					"`firm_won_bid`   ," +
					"`cost_savings`," +
					"`user_id`," +
					"`batch_no`," +
					"`row_no`) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//		StringBuffer sb = new StringBuffer();
//		for (int i=0;i<dataList.size();i++){
//			sb.append("(");
//			Map<String,String> map = dataList.get(i);
//			for(int j=0;j<columnList.size();j++){
//				String str = map.get(columnList.get(j));
//				str = str.replace(":", "\\:");
//				str = str.replace("'", "\\'");
//				sb.append("'"+str+"',");
//			}
//			sb.deleteCharAt(sb.length()-1);
//			sb.append("),");
//		}
//		sb.deleteCharAt(sb.length()-1);
//		insertSb.append(sb);
//		Session session = getSession();
//		Query query = session.createSQLQuery(insertSb.toString());
//		query.executeUpdate();
		Session session = getSession();
		try {
			Connection conn = session.connection();
			PreparedStatement stmt = conn.prepareStatement(insertSb.toString());
			conn.setAutoCommit(false);
			for (int i=0;i<dataList.size();i++){
				Map<String,String> map = dataList.get(i);
				for(int j=0;j<columnList.size();j++){
					String str = map.get(columnList.get(j));
					stmt.setString(j+1,str);
				}
				stmt.setString(columnList.size()+1,SystemUtil.getCurrentUserId()+"");
				stmt.setString(columnList.size()+2,batchNo);
				stmt.setString(columnList.size()+3,(i+1)+"");
				stmt.addBatch();
			    if (i % 300 == 0) {
			        stmt.executeBatch();
			        conn.commit();
			    }
			}
			stmt.executeBatch();
			conn.commit();
			session.close();
		} finally {
			releaseSession(session);
		}
		
	}
	
	public void callQuoteVerification(String batchNo) throws SQLException {
		logger.info("Enter method callQuoteVerification.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_QUOTE_VERIFICATION('"+batchNo+"')");
			proc.execute();
			logger.info("Exit method callQuoteVerification.");
		} finally {
			releaseSession(session);
		}
	}
	
	public List<Map<String,Object>> queryTmpQuoteError() {
		logger.info("Enter method queryTmpQuoteError.");
		final String sql = "select row_number,record_id,field,note from tmp_quote_error";
		Session session = getSession();
		List list;
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method queryTmpQuoteError.");
		return list;
	}
}
