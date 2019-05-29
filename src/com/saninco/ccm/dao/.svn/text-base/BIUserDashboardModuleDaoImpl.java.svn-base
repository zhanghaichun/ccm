package com.saninco.ccm.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIUserDashboardModule;
import com.saninco.ccm.vo.dashboard.DashboardChartDataVO;
import com.saninco.ccm.vo.dashboard.DashboardListbyVO;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIUserDashboardModule entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIUserDashboardModule
 * @author MyEclipse Persistence Tools
 */
public class BIUserDashboardModuleDaoImpl extends HibernateDaoSupport implements IBIUserDashboardModuleDao {
	private static final Log log = LogFactory
			.getLog(BIUserDashboardModuleDaoImpl.class);
	// property constants
	public static final String CONTROL_DATA = "controlData";
	public static final String SORT_NO = "sortNo";
	private static final String[] DASHBOARD_TABLES = new String[]{"noTable", "vendor_spend_dashboard", "dispute_dashboard", "vendor_circuit_dashboard","quote_issued_dashboard","quote_vendor_response_dashboard"}; 
	private static final String[] WITH_OUT_ROGERS_VENDOR_DASHBOARD_TABLES = new String[]{"vendor_spend_dashboard", "dispute_dashboard", "vendor_circuit_dashboard"}; 

	public List<DashboardChartDataVO> searchChartData(Map<String, String[]> c, DashboardListbyVO listBy, Integer moduleId, double total) {
		logger.info("Enter method searchChartData.");
		String sql = this.buildChartDataQuery(c, listBy, moduleId, total);
		List<DashboardChartDataVO>  result = new ArrayList<DashboardChartDataVO>();
		try {
			Iterator<Object[]> it = getSession().createSQLQuery(sql).list().iterator();
			while (it.hasNext()) {
				Object[] r = it.next();
				DashboardChartDataVO dv = new DashboardChartDataVO();
				dv.setOther(r[0].toString());
				dv.setY(Double.valueOf(r[1].toString()));
				if (r.length == 3) {
					dv.setName(r[2].toString());
				}
				result.add(dv);
			}
		} catch (RuntimeException re) {
			log.error("searchChartData failed", re);
			throw re;
		}
		
		logger.info("Exit method searchChartData.");
		return result;
	}
	
	public double searchTotalChartData(Map<String, String[]> c, DashboardListbyVO listBy, Integer moduleId) {
		
		logger.info("Enter method searchTotalChartData.");
		String sql = buildChartDataSumQuery(c, listBy, moduleId);
		double result = 0;
		try {
			Object o =getSession().createSQLQuery(sql).list().get(0);
			
			if (o != null) {
				String r = o.toString();
				result = Double.valueOf(r);
			}
		} catch (RuntimeException re) {
			log.error("searchTotalChartData failed", re);
			throw re;
		}
		
		logger.info("Exit method searchTotalChartData.");
		return result;
		
		
	}
	
	private String buildChartDataQuery(Map<String, String[]> c, DashboardListbyVO listBy, Integer moduleId, double total) {
		StringBuffer sb = new StringBuffer();
		
		String orderBy = "y";
		
		if (moduleId == 1 || moduleId == 2) {
			sb.append(" SELECT concat('$',format(sum(ifnull(t.amount, 0)), 2)) AS other, FORMAT(sum(ifnull(t.amount,0))/" + total + "*100, 2)+0 AS y" );
		} else if (moduleId == 3) {
			sb.append(" SELECT count(distinct t.circuit_number) AS other, FORMAT(count(distinct t.circuit_number)/" + total + "*100, 2)+0 AS y" );
		} else if (moduleId == 4) {
			sb.append(" SELECT sum(ifnull(t.issued_count, 0)) AS other, FORMAT(sum(ifnull(t.issued_count, 0))/" + total + "*100, 2)+0 AS y" );
		} else if (moduleId == 5) {
			sb.append(" SELECT sum(ifnull(t.vendor_response_count, 0)) AS other, FORMAT(sum(ifnull(t.vendor_response_count, 0))/" + total + "*100, 2)+0 AS y" );
		}
		
		if (listBy.getListbyTable() != null && listBy.getListbyTable().length() > 0) {
			sb.append(",ifnull(v." + listBy.getChartShowName() + ", '') AS name FROM bi_db." + listBy.getListbyTable() + " v LEFT JOIN bi_db." + DASHBOARD_TABLES[moduleId] + " t ON v.id = t."+listBy.getListbyTable()+"_id");
		} else {
			if (listBy.getChartShowName() != null && listBy.getChartShowName().length() > 0) {
				if ("year_and_month".equals(listBy.getChartShowName())) {
					sb.append(",DATE_FORMAT(concat(substr(t.year_and_month, 1, 4), '-', substr(t.year_and_month, 5, 6), '-01'),'%b, %Y') AS name ");
				} else {
					sb.append(",t." + listBy.getChartShowName() + " AS name ");
				}
				
				if ("quarter".equals(listBy.getKey())) {
					orderBy = "year DESC, quarter";
				} else {
					orderBy = listBy.getKey();
				}
			}
				
			sb.append(" FROM  bi_db." + DASHBOARD_TABLES[moduleId] + " t ");
		}
		
		if (ArrayUtils.contains(WITH_OUT_ROGERS_VENDOR_DASHBOARD_TABLES, DASHBOARD_TABLES[moduleId])) {
			if (!c.containsKey("vendor")) {
				sb.append(" LEFT JOIN bi_db.vendor z on z.id = t.vendor_id");
			} 
		}
		
		if (c.containsKey("timePeriod")) {
			sb.append(",(SELECT bi_db.FN_GET_TIME_PERIOD_LIMIT(" + c.get("timePeriod")[0] + ", " + moduleId + " ,TRUE) AS time_period_start ,bi_db.FN_GET_TIME_PERIOD_LIMIT(" + c.get("timePeriod")[0] + ", " + moduleId + ",FALSE)AS time_period_end) tp");
		}
		
		sb.append(buildChartDataConditions(c, true, orderBy, listBy, DASHBOARD_TABLES[moduleId]));
		
		return sb.toString();
	}
	
	private String buildChartDataSumQuery(Map<String, String[]> c, DashboardListbyVO listBy, Integer moduleId) {
		StringBuffer sb = new StringBuffer();
		
		if (moduleId == 1 || moduleId == 2) {
			sb.append(" SELECT sum(ifnull(t.amount, 0))  AS other");
		} else if (moduleId == 3) {
			sb.append(" SELECT count(distinct t.circuit_number)  AS other");
		} else if (moduleId == 4) {
			sb.append(" SELECT sum(ifnull(t.issued_count, 0))  AS other");
		} else if (moduleId == 5) {
			sb.append(" SELECT sum(ifnull(t.vendor_response_count, 0))  AS other");
		}
		
		if (listBy.getListbyTable() != null && listBy.getListbyTable().length() > 0) {
			sb.append(" FROM bi_db." + listBy.getListbyTable() + " v LEFT JOIN bi_db." + DASHBOARD_TABLES[moduleId] + " t ON v.id = t."+listBy.getListbyTable()+"_id");
		} else {
			sb.append(" FROM  bi_db." + DASHBOARD_TABLES[moduleId] + " t ");
		}
		
		if (ArrayUtils.contains(WITH_OUT_ROGERS_VENDOR_DASHBOARD_TABLES, DASHBOARD_TABLES[moduleId])) {
			if (!c.containsKey("vendor")) {
				sb.append(" LEFT JOIN bi_db.vendor z on z.id = t.vendor_id");
			} 
		}
		
		if (c.containsKey("timePeriod")) {
			sb.append(",(SELECT bi_db.FN_GET_TIME_PERIOD_LIMIT(" + c.get("timePeriod")[0] + ", " + moduleId + " ,TRUE) AS time_period_start ,bi_db.FN_GET_TIME_PERIOD_LIMIT(" + c.get("timePeriod")[0] + ", " + moduleId + ",FALSE)AS time_period_end) tp");
		}
		sb.append(buildChartDataConditions(c, true, "other", listBy, DASHBOARD_TABLES[moduleId]));
		
		String result = "SELECT sum(ifnull(o.other, 0)) FROM ("+sb.toString()+") o";
		
		return result;
	}
	
	private String buildChartDataConditions(Map<String, String[]> c, boolean isGroupBy, String orderBy, DashboardListbyVO listBy, String fromTable) {
		StringBuffer sb = new StringBuffer();
		if (listBy == null)
			listBy = new DashboardListbyVO();
		Integer limit = listBy.getLimit();
		sb.append(" WHERE 1=1 ");
		if (c.containsKey("timePeriod")) {
			sb.append(" AND (t.year_and_month BETWEEN  tp.time_period_start AND tp.time_period_end OR t.year_and_month IS NULL)");
		}
		if (c.containsKey("vendor")) {
			sb.append(" AND " + (isGroupBy && "vendor".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.vendor_id") + " IN ("+formatStringArrayToString(c.get("vendor"))+")");
		} else if (c.containsKey("quoteVendor")) {
			sb.append(" AND " + (isGroupBy && "quote_vendor".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.quote_vendor_id") + " IN ("+formatStringArrayToString(c.get("quoteVendor"))+")");
		} else {
			if (ArrayUtils.contains(WITH_OUT_ROGERS_VENDOR_DASHBOARD_TABLES,fromTable)) {
				sb.append(" AND z.rogers_flag = 'N' ");
			}
		}
		if (c.containsKey("region")) {
			sb.append(" AND " + (isGroupBy && "province".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.province_id") + " IN ("+formatStringArrayToString(c.get("region"))+")");
		}
		if (c.containsKey("product")) {
			sb.append(" AND " + (isGroupBy && "product".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.product_id") + " IN ("+formatStringArrayToString(c.get("product"))+")");
		}
		if (c.containsKey("cto")) {
			sb.append(" AND " + (isGroupBy && "audit_reference".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.audit_reference_id") + " IN ("+formatStringArrayToString(c.get("cto"))+")");
		}
		if (c.containsKey("budget")) {
			sb.append(" AND " + (isGroupBy && "budget".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.budget_id") + " IN ("+formatStringArrayToString(c.get("budget"))+")");
		}
		if (c.containsKey("productLabel")) {
			sb.append(" AND " + (isGroupBy && "product".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.product_id") + " IN (SELECT p.id FROM bi_db.product p WHERE p.media_label = \"" + c.get("productLabel")[0] + "\" OR p.function_label = \"" + c.get("productLabel")[0] + "\" OR p.generation_label = \"" + c.get("productLabel")[0] + "\")");
		}
		if (c.containsKey("budgetOwner")) {
			sb.append(" AND " + (isGroupBy && "budget".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.budget_id") + " IN (SELECT p.id FROM bi_db.budget p WHERE p.budget_owner = \"" + c.get("budgetOwner")[0] + "\")");
		}
		
		if (c.containsKey("quoteProduct")) {
			sb.append(" AND " + (isGroupBy && "quote_product".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.quote_product_id") + " IN ("+formatStringArrayToString(c.get("quoteProduct"))+")");
		}
		if (c.containsKey("quoteProductLabel")) {
			sb.append(" AND " + (isGroupBy && "quote_product".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.quote_product_id") + " IN (SELECT p.id FROM bi_db.quote_product p WHERE p.media_label = \"" + c.get("quoteProductLabel")[0] + "\" OR p.function_label = \"" + c.get("quoteProductLabel")[0] + "\" OR p.generation_label = \"" + c.get("quoteProductLabel")[0] + "\")");
		}
		if (c.containsKey("quoteAging")) {
			sb.append(" AND " + (isGroupBy && "quote_aging".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.quote_aging_id") + " IN ("+formatStringArrayToString(c.get("quoteAging"))+")");
		}
		if (c.containsKey("quoteIssuedStatus")) {
			sb.append(" AND " + (isGroupBy && "quote_issued_status".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.quote_issued_status_id") + " IN ("+formatStringArrayToString(c.get("quoteIssuedStatus"))+")");
		}
		if (c.containsKey("quoteNetType")) {
			sb.append(" AND " + (isGroupBy && "quote_net_type".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.quote_net_type_id") + " IN ("+formatStringArrayToString(c.get("quoteNetType"))+")");
		}
		if (c.containsKey("quoteVendorResponseStatus")) {
			sb.append(" AND " + (isGroupBy && "quote_vendor_response_status".equals(listBy.getListbyTable()) ? "v."+listBy.getKey() : "t.quote_vendor_response_status_id") + " IN ("+formatStringArrayToString(c.get("quoteVendorResponseStatus"))+")");
		}
		
		if (isGroupBy) {
			sb.append(" GROUP BY "+(listBy.getListbyTable() != null &&  listBy.getListbyTable().length() > 0 ? "v" : "t")+".`" + listBy.getKey() + "`");
		}
		sb.append(" ORDER BY " + orderBy + " DESC ");
		if (limit != null && limit > 0) {
			sb.append(" LIMIT " + limit);
		}
		return sb.toString();
	}
	
	private String formatStringArrayToString(String[] s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(s[i]);
		}
		return sb.toString();
	}
	
	public void save(BIUserDashboardModule transientInstance) {
		log.debug("saving BIUserDashboardModule instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void delete(BIUserDashboardModule persistentInstance) {
		log.debug("deleting BIUserDashboardModule instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public void updateSort(Integer userDashboardId, Integer sortNo) {
		
		log.debug("Entry updateSort");
		try {
			String sql = "UPDATE bi_db.user_dashboard_module SET sort_no = sort_no - 1 WHERE user_dashboard_id = " + userDashboardId + " AND sort_no > " + sortNo;
			getSession().createSQLQuery(sql).executeUpdate();
			log.debug("updateSort successful");
		} catch (RuntimeException re) {
			log.error("updateSort failed", re);
			throw re;
		}
		log.debug("Exit updateSort");
	}
	

	public void updateSortNoByDashboardAndSortNo(Integer userDashboardId, Integer sortNo, Integer newSortNo) {
		
		log.debug("Entry updateSort");
		try {
			String sql = "UPDATE bi_db.user_dashboard_module SET sort_no = " + newSortNo + " WHERE user_dashboard_id = " + userDashboardId + " AND sort_no = " + sortNo;
			getSession().createSQLQuery(sql).executeUpdate();
			log.debug("updateSort successful");
		} catch (RuntimeException re) {
			log.error("updateSort failed", re);
			throw re;
		}
		log.debug("Exit updateSort");
	}

	public BIUserDashboardModule findById(java.lang.Integer id) {
		log.debug("getting BIUserDashboardModule instance with id: " + id);
		try {
			BIUserDashboardModule instance = (BIUserDashboardModule) getSession()
					.get("com.saninco.ccm.model.bi.BIUserDashboardModule", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Integer countSameUserDashboardModuleName(String name, Integer userDashboardId, Integer id) {
		logger.info("Enter method countSameUserDashboardModuleName.");
		Integer result = null;
		
		try {
			String r = getSession().createSQLQuery("SELECT count(*) FROM bi_db.user_dashboard_module u WHERE u.name = '"+name+"' AND u.user_dashboard_id = "+userDashboardId+" AND u.id != "+id).list().get(0).toString();
			result = Integer.valueOf(r);
			log.debug("countSameUserDashboardModuleName successful");
		} catch (RuntimeException re) {
			log.error("countSameUserDashboardModuleName failed", re);
			throw re;
		}
		logger.info("Exit method countSameUserDashboardModuleName.");
		return result;
	}
	
	public Integer getNewUserDashboardModuleNumber(Integer userDashboardId, String newUserDashboardModuleName) {
		logger.info("Enter method getNewUserDashboardModuleNumber.");
		Integer result = null;
		
		try {
			List list = getSession().createSQLQuery("SELECT substring_index(u.name, ' ', -1) FROM bi_db.user_dashboard_module u WHERE u.user_dashboard_id = "+userDashboardId+"  AND u.name REGEXP '^"+newUserDashboardModuleName+" [0-9]{0,}$' ORDER BY u.name DESC LIMIT 1").list();
			if (list.size() > 0) {
				String r = list.get(0).toString();
				result = Integer.valueOf(r);
			}
			
			log.debug("getNewUserDashboardModuleNumber successful");
		} catch (RuntimeException re) {
			log.error("getNewUserDashboardModuleNumber failed", re);
			throw re;
		}
		logger.info("Exit method getNewUserDashboardModuleNumber.");
		return result;
		
	}
	
	public boolean isCanUseCondition(Integer userDashboardModuleId, String controlKey) {
		
		logger.info("Enter method isCanUseCondition.");
		
		Integer result = null;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(*) FROM bi_db.user_dashboard_module udm ");
		sb.append(" INNER JOIN bi_db.dashboard_module_control dmc ON dmc.dashboard_module_id = udm.dashboard_module_id ");
		sb.append(" INNER JOIN bi_db.dashboard_control dc ON dc.id = dmc.dashbord_control_id ");
		sb.append(" WHERE dc.control_key = \"" + controlKey + "\" AND udm.id = " + userDashboardModuleId);
		
		try {
			String r = getSession().createSQLQuery(sb.toString()).list().get(0).toString();
			result = Integer.valueOf(r);
			log.debug("isCanUseCondition successful");
		} catch (RuntimeException re) {
			log.error("isCanUseCondition failed", re);
			throw re;
		}
		logger.info("Exit method isCanUseCondition.");
		
		return result > 0;
	}

	public Integer getNextSortNo(Integer userDashboardId) {
		logger.info("Enter method getNextSortNo.");
		Integer result = null;
		
		try {
			String r = getSession().createSQLQuery("SELECT count(*)+1 FROM bi_db.user_dashboard_module a WHERE a.user_dashboard_id = " + userDashboardId).list().get(0).toString();
			result = Integer.valueOf(r);
			log.debug("getNextSortNo successful");
		} catch (RuntimeException re) {
			log.error("getNextSortNo failed", re);
			throw re;
		}
		logger.info("Exit method getNextSortNo.");
		return result;
	}
	
	public List findByExample(BIUserDashboardModule instance) {
		log.debug("finding BIUserDashboardModule instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.saninco.ccm.model.bi.BIUserDashboardModule")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BIUserDashboardModule instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BIUserDashboardModule as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByControlData(Object controlData) {
		return findByProperty(CONTROL_DATA, controlData);
	}

	public List findBySortNo(Object sortNo) {
		return findByProperty(SORT_NO, sortNo);
	}
	
	public List findAll() {
		log.debug("finding all BIUserDashboardModule instances");
		try {
			String queryString = "from BIUserDashboardModule";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findAllQuoteAging() {
		logger.debug("Enter method findAllQuoteAging.");
		try {
			String queryString = "from BIQuoteAging";
			Query queryObject = getSession().createQuery(queryString);
			logger.debug("Exit method findAllQuoteAging.");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findAllQuoteAging error", re);
			throw re;
		}
	}
	
	public List findAllQuoteIssuedStatus() {
		logger.debug("Enter method findAllQuoteIssuedStatus.");
		try {
			String queryString = "from BIQuoteIssuedStatus";
			Query queryObject = getSession().createQuery(queryString);
			logger.debug("Exit method findAllQuoteIssuedStatus.");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findAllQuoteIssuedStatus error", re);
			throw re;
		}
	}
	
	public List findAllQuoteNetType() {
		logger.debug("Enter method findAllQuoteNetType.");
		try {
			String queryString = "from BIQuoteNetType";
			Query queryObject = getSession().createQuery(queryString);
			logger.debug("Exit method findAllQuoteNetType.");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findAllQuoteNetType error", re);
			throw re;
		}
	}
	
	public List findAllQuoteProduct() {
		logger.debug("Enter method findAllQuoteProduct.");
		try {
			String queryString = "from BIQuoteProduct";
			Query queryObject = getSession().createQuery(queryString);
			logger.debug("Exit method findAllQuoteProduct.");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findAllQuoteProduct error", re);
			throw re;
		}
	}
	
	public List findAllQuoteVendor() {
		logger.debug("Enter method findAllQuoteVendor.");
		try {
			String queryString = "from BIQuoteVendor";
			Query queryObject = getSession().createQuery(queryString);
			logger.debug("Exit method findAllQuoteVendor.");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findAllQuoteVendor error", re);
			throw re;
		}
	}
	
	public List findAllQuoteVendorResponseStatus() {
		logger.debug("Enter method findAllQuoteVendorResponseStatus.");
		try {
			String queryString = "from BIQuoteVendorResponseStatus";
			Query queryObject = getSession().createQuery(queryString);
			logger.debug("Exit method findAllQuoteVendorResponseStatus.");
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findAllQuoteVendorResponseStatus error", re);
			throw re;
		}
	}
	
	public BIUserDashboardModule merge(BIUserDashboardModule detachedInstance) {
		log.debug("merging BIUserDashboardModule instance");
		try {
			BIUserDashboardModule result = (BIUserDashboardModule) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIUserDashboardModule instance) {
		log.debug("attaching dirty BIUserDashboardModule instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIUserDashboardModule instance) {
		log.debug("attaching clean BIUserDashboardModule instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}