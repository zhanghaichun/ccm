package com.saninco.ccm.dao.rateSearch;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

import com.saninco.ccm.util.ExcelBatchDaoDelegate;
import com.saninco.ccm.util.ExcelFileUtil;
import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.rateSearch.SearchRateSearchVO;

public class RateExcelMtMBatchDaoImpl extends HibernateDaoSupport implements ExcelBatchDaoDelegate {
	
	public void insertInventoryTemporary(List<Map<String,String>> dataList,List<String> columnList,String batchNo) throws SQLException{
		StringBuffer insertSb = new StringBuffer("insert into rate_mtm_length_import (`rate_id`  ," +
					"`charge_type`," +
					"`summary_vendor_name`," +
					"`key_field`," +
					"`usoc`," +
					"`usoc_long_description`," +
					"`stripped_circuit_number`," +
					"`sub_product`," +
					"`rate`," +
					"`effective_date`," +
					"`term`," +
					"`item_description`," +
					"`line_item_code`," +
					"`line_item_code_description`," +
				    "`user_id`," +
					"`batch_no`," +
					"`row_no`) " +
					"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			logger.info("Exit method insertTemporary.");
		} finally {
			releaseSession(session);
		}
	}
	
	public void callInventoryVerification(String batchNo) throws SQLException{
		logger.info("Enter method callInventoryVerification.");
		Session session = getSession();
		try {
			Connection con = session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_RATE_MTM_VERIFICATION('"+batchNo+"')");
			proc.execute();
			logger.info("Exit method callInventoryVerification.");
		} finally {
			releaseSession(session);
		}
    }
	public List<Map<String,Object>> queryTmpInventoryError(String batchNo){
		logger.info("Enter method queryTmpInventoryError.");
		final String sql = "select row_number,field,note from tmp_rate_error order by row_number limit "+ ExcelFileUtil.MAX_DATA_LENGTH;
		Session session = getSession();
		List list;
		try{
			list = session.createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method queryTmpInventoryError.");
		return list;
	}
    
}