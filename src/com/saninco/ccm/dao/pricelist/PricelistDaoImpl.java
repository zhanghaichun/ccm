package com.saninco.ccm.dao.pricelist;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.util.StringHandling;
import com.saninco.ccm.vo.pricelist.SearchPricelistVO;

public class PricelistDaoImpl extends HibernateDaoSupport implements IPricelistDao {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	/**
	 * 从数据库中获取band list 列表
	 * group by
	 */
	public List<String> getBandList() {
		logger.info("Enter method getBandList.");
		List<String> bandList = null;
		Session session = getSession();
		
		try {
			bandList = session.createSQLQuery("SELECT pl.band FROM price_list pl WHERE pl.rec_active_flag='Y' AND pl.band IS NOT NULL GROUP BY pl.band").list();
			logger.info("Exit method getBandList.");
			return bandList;
		} finally {
			releaseSession(session);
		}
	}

	@Override
	/**
	 * 从数据库中获取 service type list 列表
	 * group by
	 * @return [description]
	 */
	public List<String> getServiceTypeList() {
		logger.info("Enter method getServiceTypeList.");
		List<String> serviceTypeList = null;
		Session session = getSession();
		
		try {
			serviceTypeList = session.createSQLQuery("SELECT pl.service_type FROM price_list pl WHERE pl.rec_active_flag='Y' AND pl.service_type IS NOT NULL GROUP BY pl.service_type").list();
			logger.info("Exit method getServiceTypeList.");
			return serviceTypeList;
		} finally {
			releaseSession(session);
		}
	}
	
	

	@Override
	/**
	 * 获取Pricelist分页信息。
	 */
	public long getPricelistViewListPageNo(SearchPricelistVO searchPricelistVO) {
		logger.info("Enter method getPricelistViewListPageNo.");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(*) AS pricelistCounts ");
		sb.append( pricelistWhereConditions(searchPricelistVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getContractViewListPageNo.");
		return count;
	}
	
	@Override
	/**
	 * 查询Pricelist列表信息。
	 */
	public List<String> searchPricelistList(SearchPricelistVO searchPricelistVO) {
		logger.info("Enter method searchPricelistList.");
		final String sql = this.searchPricelistListQueryString(searchPricelistVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> pricelistList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchPricelistList.");
		return pricelistList;
	}
	
	/**
	 * Pricelist 列表中所需信息查询的 SQL语句。
	 * @param searchContractVO
	 * @return
	 */
	private String searchPricelistListQueryString(SearchPricelistVO searchPricelistVO) {
		logger.info("Enter method searchPricelistListQueryString.");
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT CONCAT('{id: ', toJSON(pl.id IS NULL, '', pl.id), "); // Id.
		sb.append(" ',fileName: \"', toJSON(plf.price_list_name IS NULL, '', plf.price_list_name), "); // File Name.
		sb.append(" '\",serviceType: \"', toJSON(pl.service_type IS NULL, '', pl.service_type), "); // Service Type.
		sb.append(" '\",band: \"', toJSON(pl.band IS NULL, '', pl.band), "); // Band.
		sb.append(" '\",effectiveDate: \"', toJSON(pl.effective_date IS NULL, '', DATE_FORMAT(pl.effective_date,'%Y-%m-%d')), "); // Effective Date.
		
		sb.append(" '\"}') "); // concat 函数结束符。
		
		sb.append( pricelistWhereConditions(searchPricelistVO) );
		
		// 排序条件。
		sb.append(" ORDER BY " + searchPricelistVO.getSortField() + " " + searchPricelistVO.getSortingDirection());
		
		// 分页限制条件。
		sb.append(" " + searchPricelistVO.getLimitCause() + " ");
		
		logger.info("Exit method searchPricelistListQueryString.");
		return sb.toString();
	}

	/**
	 * 下载 pricelist.
	 */
	public List<Object[]> downloadPricelistToExcel(SearchPricelistVO searchPricelistVO) {

		logger.info("Enter method downloadPricelistToExcel.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		sb.append( downloadPricelistToExcelQueryString(searchPricelistVO) );
		sb.append(" ORDER BY " + searchPricelistVO.getSortField() + " " + searchPricelistVO.getSortingDirection());
		sb.append( " " + searchPricelistVO.getLimitCause() + " " );

		try {

			List<Object[]> pricelistExcelDataColumns = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method downloadPricelistToExcel.");
			return pricelistExcelDataColumns;

		} finally {
			releaseSession(session);
		}
	}

	/**
	 * 将 contract 下载到excel文件中用到的sql查询语句.
	 */
	private String downloadPricelistToExcelQueryString(SearchPricelistVO searchPricelistVO) {

		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT toJSON(plf.price_list_name IS NULL, '', plf.price_list_name), "); // File Name.
		sb.append(" toJSON(pl.service_type IS NULL, '', pl.service_type), "); // Service Type.
		sb.append(" toJSON(pl.band IS NULL, '', pl.band), "); // Band.
		sb.append(" toJSON(pl.effective_date IS NULL, '', DATE_FORMAT(pl.effective_date,'%Y-%m-%d')) ");  // Effective Date.
		
		sb.append( pricelistWhereConditions(searchPricelistVO) );

		return sb.toString();
	}

	/**
	 * 查询分页信息和pricelist表中数据的where条件。
	 * @param searchPricelistVO
	 * @return
	 */
	private String pricelistWhereConditions(SearchPricelistVO searchPricelistVO) {

		logger.info("Enter method pricelistWhereConditions.");
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM price_list AS pl ");
		sb.append(" INNER JOIN price_list_file plf ON pl.price_list_file_id = plf.id ");
		sb.append(" WHERE pl.rec_active_flag='Y' ");
		
		if (searchPricelistVO != null) {
			
			if( searchPricelistVO.getFileName() != null ) {
				String fileName = StringHandling.sqlStringReplace( searchPricelistVO.getFileName() );
				sb.append("AND plf.price_list_name LIKE '%" + fileName + "%' ");
			}
			
			if( searchPricelistVO.getServiceType() != null ) {
				sb.append("AND pl.service_type='" + searchPricelistVO.getServiceType() + "' ");
			}
			
			if( searchPricelistVO.getModeOfBand() != null ) {
				sb.append("AND pl.band='" + searchPricelistVO.getModeOfBand() + "' ");
			}
			
			if( searchPricelistVO.getEffectiveDate() != null ) {
				sb.append("AND DATEDIFF(pl.effective_date, '" + searchPricelistVO.getEffectiveDate() + "')=0 ");
			}
			
			
			if ( searchPricelistVO.getFilter() != null ) { // Filter
				sb.append( "AND " + searchPricelistVO.getFilter() + " " );
			}
		}
		
		
		
		logger.info("Exit method pricelistWhereConditions.");
		return sb.toString();
	}

	

}