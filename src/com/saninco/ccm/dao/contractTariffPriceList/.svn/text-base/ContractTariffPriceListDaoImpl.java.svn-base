package com.saninco.ccm.dao.contractTariffPriceList;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.vo.contractTariffPriceList.SearchContractTariffPriceListVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;

public class ContractTariffPriceListDaoImpl extends HibernateDaoSupport implements IContractTariffPriceListDao {

	private final Logger logger = Logger.getLogger(this.getClass());
	private SearchQuoteVO searchQuoteVO;
	
	
	/**
	 * @return the searchQuoteVO
	 */
	public SearchQuoteVO getSearchQuoteVO() {
		return searchQuoteVO;
	}

	/**
	 * @param searchQuoteVO the searchQuoteVO to set
	 */
	public void setSearchQuoteVO(SearchQuoteVO searchQuoteVO) {
		this.searchQuoteVO = searchQuoteVO;
	}

	/**
	 * 从数据库中查询　Contract 列表所需要的信息。
	 */
	public List<String> getContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		
		logger.info("Enter method getContractList.");
		
		StringBuffer sb = new StringBuffer();
		sb.append( searchContractListQuerySelectString() );
		sb.append( contractWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );

		sb.append( contractTariffPriceListSortAndLimitConditions(searchContractTariffPriceListVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> contractList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getContractList.");
		return contractList;
	}

	/**
	 * Contract/Tariff/Price List 列表的或者是下载时的分页功能。
	 * @param  searchContractTariffPriceListVO [description]
	 * @return                                 [description]
	 */
	private String contractTariffPriceListSortAndLimitConditions(SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		StringBuffer sb = new StringBuffer();

		// 排序条件。
		sb.append(" ORDER BY " + searchContractTariffPriceListVO.getSortField() + " " + searchContractTariffPriceListVO.getSortingDirection());
		
		// 分页限制条件。
		sb.append(" " + searchContractTariffPriceListVO.getLimitCause() + " ");

		return sb.toString();
	}
	
	/**
	 * 查询Contract 列表中的字段信息，只是查询字段信息，就是只有select语句。
	 * @return
	 */
	private String searchContractListQuerySelectString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT CONCAT('{fileName: \"', toJSON(fileName IS NULL, '', fileName), ");
		sb.append(" '\", expiryDate: \"', toJSON(expiryDate IS NULL, '', expiryDate), ");
		sb.append(" '\", serviceType: \"', toJSON(serviceType IS NULL, '', serviceType), ");
		sb.append(" '\", usoc: \"', toJSON(usoc IS NULL, '', usoc), ");
		sb.append(" '\", circuitNumber: \"', toJSON(circuitNumber IS NULL, '', circuitNumber), ");
		sb.append(" '\", rate: \"', toJSON( rate IS NULL, '', rate), ");
		sb.append(" '\", amendment: \"', toJSON(amendment IS NULL, '', amendment), ");
		sb.append(" '\", schedule: \"', toJSON(schedule IS NULL, '', schedule), ");
		sb.append(" '\", penaltyAmount: \"', toJSON(penaltyAmount IS NULL, '', FORMAT(penaltyAmount,2) ), ");
		sb.append(" '\", penaltyNotes: \"', toJSON(penaltyNotes IS NULL, '', penaltyNotes), ");
		sb.append(" '\", rateDiscrepancyFlag: \"', toJSON(rateDiscrepancyFlag IS NULL, 'N', 'Y'), ");
		sb.append(" '\", attachmentPointId: \"', toJSON(attachmentPointId IS NULL, '', attachmentPointId), ");
		sb.append(" '\", id: \"', toJSON(id IS NULL, '', id), ");
		
		sb.append(" '\"}') ");
		
		return sb.toString();
	}
	
	/**
	 * 从数据库中查询 expiry date contract 的个数。
	 */
	public long getContractCountsByExpiryDate(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method getContractCountsByExpiryDate.");
		StringBuffer sb = new StringBuffer();
		
		if (searchContractTariffPriceListVO != null){
			
			if ( searchContractTariffPriceListVO.getIsExpiryDateContract() != null && 
					searchContractTariffPriceListVO.getIsExpiryDateContract() == true ){
				sb.append(" SELECT COUNT(DISTINCT expiryDateContractId ) AS expiry_contract_count ");
				sb.append( contractWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );
			}
		}
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});

		logger.info("Exit method getContractCountsByExpiryDate.");
		return count;
	}


	/**
	 * 从数据库中查询　Contract 列表分页显示时所需要的信息。
	 */
	public long getContractListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method getContractListPageNo.");
		
		StringBuffer sb = new StringBuffer();

		if (searchContractTariffPriceListVO != null) {
			sb.append(" SELECT COUNT(*) AS contractCounts ");
			sb.append( contractWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );
		}
		
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});

		logger.info("Exit method getContractListPageNo.");
		return count;
	}
	
	/**
	 *	查询contract 列表的where 条件。
	 * @param searchContractTariffPriceListVO
	 * @return
	 */
	private String contractWhereConditions(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" FROM (SELECT cf.contract_number AS fileName,  ");
		sb.append("  cf.expiry_date AS expiryDate,  ");
		sb.append("  c.service_type AS serviceType,  ");
		sb.append("  c.usoc_description AS usoc,  ");
		sb.append("  c.circuit_number AS circuitNumber,  ");
		sb.append("  (CASE  WHEN c.rate_mode = 'rate_any' THEN (SELECT GROUP_CONCAT(FORMAT(a.rate,2) SEPARATOR ' or ') FROM contract_rate_by_any a  WHERE a.contract_id = c.id)  ELSE FORMAT(c.rate,2)  END) AS rate,  ");
		sb.append("  c.amendment AS amendment,  ");
		sb.append("  c.schedule AS schedule,  ");
		sb.append("  c.penalty_amount AS penaltyAmount,  ");
		sb.append("  c.penalty_notes AS penaltyNotes,  ");
		sb.append("  (SELECT r.id FROM audit_result r INNER JOIN invoice i ON r.invoice_id = i.id WHERE i.rec_active_flag = 'Y' AND i.invoice_date >= subdate(curdate(),INTERVAL 1 YEAR) AND r.audit_reference_type_id  = 3 AND r.audit_reference_id = c.id AND r.audit_status_id = 2 LIMIT 1) AS rateDiscrepancyFlag,  ");
		sb.append("  cf.attachment_point_id AS attachmentPointId,  ");
		sb.append("  c.id AS id,  ");
		sb.append("  cf.id AS expiryDateContractId  ");

		sb.append(" FROM contract AS c INNER JOIN contract_file AS cf ON c.contract_file_id = cf.id "); // -------
		sb.append(" WHERE c.rec_active_flag='Y' ");
		
		if (searchContractTariffPriceListVO != null) {
			
			if ( searchContractTariffPriceListVO.getIsCompareFilter() != null && searchContractTariffPriceListVO.getIsCompareFilter() == true ) { // Compare filter (quote inventory 页面。)
				
				if (searchQuoteVO != null) {
					sb.append(" AND FN_IS_CTPQ_MATCHING('contract', c.id, ");
					sb.append( composeCompareQueryString(searchQuoteVO) );
					sb.append(" ) = 1");
				}
				
				
			} else { // Contract/Tariff/Price List 页面。

				if ( searchContractTariffPriceListVO.getIsExpiryDateContract() != null && 
						searchContractTariffPriceListVO.getIsExpiryDateContract() == true ) { // Expiry Date Contract
					
					sb.append(" AND cf.expiry_date <= ADDDATE(CURDATE(),INTERVAL 60 day) ");
					sb.append(" AND cf.expiry_date >= curdate() ");
					sb.append(" AND cf.term = 'YEAR' ");
					
				} else {
					
					if( searchContractTariffPriceListVO.getFileName() != null ) { // File Name
						sb.append(" AND cf.contract_number LIKE '%"+ searchContractTariffPriceListVO.getFileName() +"%' ");
					}
					
					// Contract 表的搜索条件， 包括 vendor, ban, product, product component
					// , circuit number, usoc
					if( !isSearchConditionsNull(searchContractTariffPriceListVO) ) {
						
						sb.append( auditMappingSearchConditions("3", "c", searchContractTariffPriceListVO) );
					}
					
					if ( searchContractTariffPriceListVO.getFilter() != null ) { // Filter
						sb.append(" AND " + searchContractTariffPriceListVO.getFilter() + " ");
					}
				}

			}
			
			sb.append(" ) as contractList ");
			
			
		}
		
		
		
		return sb.toString();
	}
	
	
	

	/**
	 * 下载 Contract List 列表中的信息到 Excel 文件中。
	 */
	public List<Object[]> downloadContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		
		logger.info("Enter method downloadContractList.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		sb.append( downloadContractToExcelQuerySelectString() );
		sb.append( contractWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );
		sb.append( contractTariffPriceListSortAndLimitConditions(searchContractTariffPriceListVO) );

		try {

			List<Object[]> contractExcelDataColumns = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method downloadContractList.");
			return contractExcelDataColumns;

		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * 查询Contract 列表中的字段信息，只是查询字段信息，就是只有select语句, 但是这个是
	 * 添加到Excel文件中。
	 * @return
	 */
	private String downloadContractToExcelQuerySelectString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT toJSON(fileName IS NULL, '', fileName), ");
		sb.append(" toJSON(expiryDate IS NULL, '', expiryDate), ");
		sb.append(" toJSON(serviceType IS NULL, '', serviceType), ");
		sb.append(" toJSON(usoc IS NULL, '', usoc), ");
		sb.append(" toJSON(circuitNumber IS NULL, '', circuitNumber), ");
		sb.append(" toJSON(rate IS NULL, '', rate), ");
		sb.append(" toJSON(amendment IS NULL, '', amendment), ");
		sb.append(" toJSON(schedule IS NULL, '', schedule), ");
		sb.append(" toJSON(penaltyAmount IS NULL, '', FORMAT(penaltyAmount,2) ), ");
		sb.append(" toJSON(penaltyNotes IS NULL, '', penaltyNotes), ");
		sb.append(" toJSON(rateDiscrepancyFlag IS NULL, 'N', 'Y')");
		
		return sb.toString();
	}

	/**
	 * 查询Tariff 列表的分页信息。
	 */
	public long getTariffListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method getTariffListPageNo.");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(*) AS tariffCounts ");
		sb.append( tariffWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getTariffListPageNo.");
		return count;
	}
	
	/**
	 * 查询tariff 列表的where 条件。
	 * @param searchContractTariffPriceListVO
	 * @return
	 */
	private String tariffWhereConditions( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		StringBuffer sb = new StringBuffer();

		sb.append(" FROM (SELECT tf.tariff_name AS fileName, ");
		sb.append(" t.page AS page, ");
		sb.append(" t.part_section AS partSection, ");
		sb.append(" t.item_number AS itemNumber, ");
		sb.append(" t.paragraph AS paragraph, ");
		sb.append(" t.tariff_last_revised AS tariffLastRevised, ");
		sb.append(" t.service_type AS serviceType, ");
		sb.append(" t.band AS band, ");
		sb.append(" t.provider AS provider, ");
		sb.append(" CONCAT((CASE  WHEN t.rate_mode = 'tariff_rate_by_distance' THEN 'Rate by Mileage' WHEN t.rate_mode = 'order_date_check' THEN 'Rate by Order Date' WHEN t.rate_mode = 'tariff_rate_by_term' THEN  'Rate by Term' WHEN t.rate_mode = 'tariff_rate_by_province' THEN 'Rate by Province' WHEN (t.rate_mode = 'tariff_rate_by_trunk' OR t.rate_mode = 'tariff_rate_by_trunk_province') THEN 'Rate by Trunk' ELSE 'Rate' END), '') AS rateMode, ");
		sb.append(" FN_GET_AUDIT_RATE_TEXT('tariff', t.id) AS rate, ");
		sb.append(" (SELECT r.id FROM audit_result r INNER JOIN invoice i ON r.invoice_id = i.id WHERE i.rec_active_flag = 'Y' AND i.invoice_date >= subdate(curdate(),INTERVAL 1 YEAR) AND r.audit_reference_type_id  = 2 AND r.audit_reference_id = t.id AND r.audit_status_id = 2 LIMIT 1) AS rateDiscrepancyFlag, ");
		sb.append(" tf.attachment_point_id AS attachmentPointId, ");
		sb.append(" t.id AS id ");

		sb.append(" FROM tariff t INNER JOIN tariff_file tf ON t.tariff_file_id = tf.id "); // --------
		sb.append(" WHERE t.rec_active_flag='Y' ");
		
		if ( searchContractTariffPriceListVO != null ) {

			if ( searchContractTariffPriceListVO.getIsCompareFilter() != null && searchContractTariffPriceListVO.getIsCompareFilter() == true ) { // Compare Filter.
				
				if ( searchQuoteVO != null ) {
					
					sb.append(" AND FN_IS_CTPQ_MATCHING('tariff', t.id, ");
					sb.append( composeCompareQueryString(searchQuoteVO) );
					sb.append(" ) = 1");
				}
				
			} else {

				if( searchContractTariffPriceListVO.getFileName() != null ) { // File Name
					sb.append(" AND tf.tariff_name LIKE '%"+ searchContractTariffPriceListVO.getFileName() +"%' ");
				}
				
				// Tariff 表的搜索条件， 包括 vendor, ban, product, product component
				// , circuit number, usoc
				if( !isSearchConditionsNull(searchContractTariffPriceListVO) ) {
					
					sb.append( auditMappingSearchConditions("2", "t", searchContractTariffPriceListVO) );
				}
				
				if ( searchContractTariffPriceListVO.getFilter() != null ) { // Filter
					sb.append(" AND " + searchContractTariffPriceListVO.getFilter() + " ");
				}
			}
			
			
		}

		sb.append(" ) AS TariffList ");
		
		
		return sb.toString();
	}


	/**
	 * 查询Tariff 列表。
	 */
	public List<String> getTariffList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method getTariffList.");
		
		StringBuffer sb = new StringBuffer();
		sb.append( searchTariffListQuerySelectString() );
		sb.append( tariffWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );

		sb.append( contractTariffPriceListSortAndLimitConditions(searchContractTariffPriceListVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> tariffList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getTariffList.");
		return tariffList;
	}
	
	/**
	 * 查询Tariff 列表中的字段信息，只是查询字段信息，就是只有select语句。
	 * @return
	 */
	private String searchTariffListQuerySelectString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT CONCAT('{fileName: \"', toJSON(fileName IS NULL, '', fileName), ");
		sb.append(" '\", page: \"', toJSON(page IS NULL, '', page), ");
		sb.append(" '\", partSection: \"', toJSON( partSection IS NULL, '',  partSection), ");
		sb.append(" '\", itemNumber: \"', toJSON( itemNumber IS NULL, '',  itemNumber), ");
		sb.append(" '\", paragraph: \"', toJSON(paragraph IS NULL, '', paragraph), ");
		sb.append(" '\", tariffLastRevised: \"', toJSON(tariffLastRevised IS NULL, '', tariffLastRevised), ");
		sb.append(" '\", serviceType: \"', toJSON( serviceType IS NULL, '',  serviceType), ");
		sb.append(" '\", band: \"', toJSON( band IS NULL, '',  band), ");
		sb.append(" '\", provider: \"', toJSON( provider IS NULL, '',  provider), ");
		sb.append(" '\", rateMode: \"', toJSON(rateMode IS NULL, '', rateMode), ");
		sb.append(" '\", rate: \"', toJSON(rate IS NULL, '', rate), ");
		sb.append(" '\", rateDiscrepancyFlag: \"', toJSON(rateDiscrepancyFlag IS NULL, 'N', 'Y'), ");
		sb.append(" '\", attachmentPointId: \"', toJSON(attachmentPointId IS NULL, '', attachmentPointId), ");
		sb.append(" '\", id: \"', toJSON(id IS NULL, '', id), ");
		
		sb.append(" '\"}') ");
		
		return sb.toString();
	}


	/**
	 * 下载 Tariff List 列表到Excel文件中.
	 */
	public List<Object[]> downloadTariffList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method downloadTariffList.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		sb.append( downloadTariffToExcelQuerySelectString() );
		sb.append( tariffWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );
		sb.append( contractTariffPriceListSortAndLimitConditions(searchContractTariffPriceListVO) );

		try {

			List<Object[]> tariffExcelDataColumns = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method downloadTariffList.");
			return tariffExcelDataColumns;

		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * 查询Tariff 列表中的字段信息，只是查询字段信息，就是只有select语句, 但是这个是
	 * 添加到Excel文件中。
	 * @return
	 */
	private String downloadTariffToExcelQuerySelectString() {
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT toJSON(fileName IS NULL, '', fileName), "); // File Name
		sb.append(" toJSON(page IS NULL, '', page), "); // Page
		sb.append(" toJSON( partSection IS NULL, '',  partSection), "); // Part Section
		sb.append(" toJSON( itemNumber IS NULL, '',  itemNumber), "); // Item #
		sb.append(" toJSON(paragraph IS NULL, '', paragraph), "); // Paragraph
		sb.append(" toJSON(tariffLastRevised IS NULL, '', tariffLastRevised), "); // Tariff Last Revised
		sb.append(" toJSON( serviceType IS NULL, '',  serviceType), "); // Service Type
		sb.append(" toJSON( band IS NULL, '',  band), "); // Band
		sb.append(" toJSON( provider IS NULL, '',  provider), "); // Provider
		sb.append(" toJSON(rateMode IS NULL, '', rateMode), "); // Rate Mode
		sb.append(" toJSON(rate IS NULL, '', rate), "); // Rate Description
		sb.append(" toJSON(rateDiscrepancyFlag IS NULL, 'N', 'Y')");
		
		return sb.toString();
	}


	/**
	 * 查询Price List 列表的分页信息。
	 */
	public long getPriceListListPageNo( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method getPriceListListPageNo.");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT COUNT(*) AS priceListCounts ");
		sb.append( priceListWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getPriceListListPageNo.");
		return count;
	}
	
	/**
	 * 查询price List 列表的where 条件。
	 * @param searchContractTariffPriceListVO
	 * @return
	 */
	private String priceListWhereConditions( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM (SELECT plf.price_list_name AS fileName, ");
		sb.append(" pl.service_type AS serviceType, ");
		sb.append(" pl.band AS band, ");
		sb.append(" CONCAT( (CASE WHEN pl.rate_mode = 'rate_by_term' THEN 'Rate by Term' ELSE 'Rate' END), '') AS rateMode, ");
		sb.append(" FN_GET_AUDIT_RATE_TEXT('pricelist' ,pl.id ) AS rate, ");
		sb.append(" pl.effective_date AS effectiveDate, ");
		sb.append(" (SELECT r.id FROM audit_result r INNER JOIN invoice i ON r.invoice_id = i.id WHERE i.rec_active_flag = 'Y' AND i.invoice_date >= subdate(curdate(),INTERVAL 1 YEAR) AND r.audit_reference_type_id  = 5 AND r.audit_reference_id = pl.id AND r.audit_status_id = 2 LIMIT 1) AS rateDiscrepancyFlag, ");
		sb.append(" plf.attachment_point_id AS attachmentPointId, ");
		sb.append(" pl.id AS id ");

		sb.append(" FROM price_list pl INNER JOIN price_list_file plf ON pl.price_list_file_id = plf.id "); // -------
		sb.append(" WHERE pl.rec_active_flag='Y' ");
		
		if ( searchContractTariffPriceListVO != null ) {

			if ( searchContractTariffPriceListVO.getIsCompareFilter() != null && searchContractTariffPriceListVO.getIsCompareFilter() == true ) { // Compare Filter.
				
				if ( searchQuoteVO != null ) {
					
					sb.append(" AND FN_IS_CTPQ_MATCHING('pricelist', pl.id, ");
					sb.append( composeCompareQueryString(searchQuoteVO) );
					sb.append(" ) = 1");
				}
				
			} else {

				if( searchContractTariffPriceListVO.getFileName() != null ) { // File Name
					sb.append(" AND plf.price_list_name LIKE '%"+ searchContractTariffPriceListVO.getFileName() +"%' ");
				}
				
				// Price List 表的搜索条件， 包括 vendor, ban, product, product component
				// , circuit number, usoc
				if( !isSearchConditionsNull(searchContractTariffPriceListVO) ) {
					
					sb.append( auditMappingSearchConditions("5", "pl", searchContractTariffPriceListVO) );
					
				}
				
				if ( searchContractTariffPriceListVO.getFilter() != null ) { // Filter
					sb.append(" AND " + searchContractTariffPriceListVO.getFilter() + " ");
				}
			}
			
			
		}
		
		sb.append(" ) AS priceListList ");
		
		return sb.toString();
	}

	/**
	 * 查询Price List 列表。
	 */
	public List<String> getPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method getPriceListList.");
		
		StringBuffer sb = new StringBuffer();
		sb.append( searchPriceListListQuerySelectString() );
		sb.append( priceListWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );

		sb.append( contractTariffPriceListSortAndLimitConditions(searchContractTariffPriceListVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> priceListList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getPriceListList.");
		return priceListList;
	}
	
	/**
	 * 查询Price List 列表中的字段信息，只是查询字段信息，就是只有select语句。
	 * @return
	 */
	private String searchPriceListListQuerySelectString() {

		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT CONCAT('{fileName: \"', toJSON(fileName IS NULL, '', fileName), ");
		sb.append(" '\", serviceType: \"', toJSON(serviceType IS NULL, '', serviceType), ");
		sb.append(" '\", band: \"', toJSON(  band IS NULL, '',   band), ");
		sb.append(" '\", rateMode: \"', toJSON(rateMode IS NULL, '', rateMode), ");
		sb.append(" '\", rate: \"', toJSON(rate IS NULL, '', rate), ");
		sb.append(" '\", effectiveDate: \"', toJSON(  effectiveDate IS NULL, '',   effectiveDate), ");
		sb.append(" '\", rateDiscrepancyFlag: \"', toJSON(rateDiscrepancyFlag IS NULL, 'N', 'Y'), ");
		sb.append(" '\", attachmentPointId: \"', toJSON(attachmentPointId IS NULL, '', attachmentPointId), ");
		sb.append(" '\", id: \"', toJSON(id IS NULL, '', id), ");
		
		sb.append(" '\"}') ");
		
		return sb.toString();
	}

	
	/**
	 * 下载 Price List 列表到Excel文件中.
	 */
	public List<Object[]> downloadPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) {
		logger.info("Enter method downloadPriceListList.");
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		sb.append( downloadPriceListToExcelQuerySelectString() );
		sb.append( priceListWhereConditions(searchContractTariffPriceListVO, searchQuoteVO) );
		sb.append( contractTariffPriceListSortAndLimitConditions(searchContractTariffPriceListVO) );

		try {

			List<Object[]> priceListExcelDataColumns = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method downloadPriceListList.");
			return priceListExcelDataColumns;

		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * 查询Price List 列表中的字段信息，只是查询字段信息，就是只有select语句, 但是这个是
	 * 添加到Excel文件中。
	 * @return
	 */
	private String downloadPriceListToExcelQuerySelectString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT toJSON(fileName IS NULL, '', fileName), "); // File Name
		sb.append("  toJSON(serviceType IS NULL, '', serviceType), "); // Service Type
		sb.append(" toJSON(  band IS NULL, '',   band), "); // Band
		sb.append(" toJSON(rateMode IS NULL, '', rateMode), "); // Rate Mode
		sb.append(" toJSON(rate IS NULL, '', rate), "); // Rate Description
		sb.append(" toJSON(  effectiveDate IS NULL, '',   effectiveDate), "); // Effective Date
		sb.append(" toJSON(rateDiscrepancyFlag IS NULL, 'N', 'Y') "); // Effective Date
		
		return sb.toString();
	}
	
	/**
	 * 这个方法用来判断 Contract/Tariff/Price List 传过来的搜索条件是否全部为空，
	 * 这些条件中唯一不包含对fileName 的判断
	 * 如果一个搜索条件也没有加入的话， sql语句会做特殊的处理。
	 */
	private Boolean isSearchConditionsNull(SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		
		boolean isConditionsNull = false;
		
		isConditionsNull = (searchContractTariffPriceListVO.getVendorId() == null) &&
						(searchContractTariffPriceListVO.getBanId() == null) && (searchContractTariffPriceListVO.getCircuitNumber() == null) &&
						(searchContractTariffPriceListVO.getProductName() == null) && (searchContractTariffPriceListVO.getProductComponentName() == null) &&
						(searchContractTariffPriceListVO.getUsoc() == null);
			
		return isConditionsNull;
	}
	
	/**
	 * 应用于 Contract / Tariff/ Price List 表中的查询条件，
	 * 这些条件主要存在于 audit_reference_mapping_search 数据库表中。
	 * @param auditReferenceTypeId
	 * @param auditReferenceTypeName
	 * @param searchContractTariffPriceListVO
	 * @return
	 */
	private String auditMappingSearchConditions(String auditReferenceTypeId, String auditReferenceTypeName, SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" AND (SELECT s.audit_reference_id FROM audit_reference_mapping_search s ");
		sb.append(" WHERE s.audit_reference_type_id = "+ auditReferenceTypeId +" AND  s.audit_reference_id = "+ auditReferenceTypeName +".id ");
		
		if ( searchContractTariffPriceListVO.getVendorId() != null ) { // Vendor
			sb.append(" AND s.vendor_id='" + searchContractTariffPriceListVO.getVendorId() + "' ");
		}
		
		if ( searchContractTariffPriceListVO.getBanId() != null ) { // Ban
			sb.append(" AND s.ban_id='" + searchContractTariffPriceListVO.getBanId() + "' ");
		}
		
		if ( searchContractTariffPriceListVO.getCircuitNumber() != null ) { // Circuit Number
			sb.append(" AND s.stripped_circuit_number='" + searchContractTariffPriceListVO.getCircuitNumber() + "' ");
		}
		
		if ( searchContractTariffPriceListVO.getProductName() != null ) { // Product
			sb.append(" AND s.product_id IN (SELECT p.id FROM product p WHERE p.product_name='"+ searchContractTariffPriceListVO.getProductName() +"') ");
		}
		
		if ( searchContractTariffPriceListVO.getProductComponentName() != null ) { // Product Component
			sb.append(" AND  s.product_component_id IN (SELECT pc.id FROM product_component pc WHERE pc.component_name='"+ searchContractTariffPriceListVO.getProductComponentName() +"') ");
		}
		
		if ( searchContractTariffPriceListVO.getUsoc() != null ) { // USOC
			sb.append(" AND s.usoc='" + searchContractTariffPriceListVO.getUsoc() + "' ");
		}
		
		sb.append(" LIMIT 1) = "+ auditReferenceTypeName +".id  ");
		
		return sb.toString();
	}


	/**
	 * 获取 product component 筛选条件列表。
	 */
	public List<String> getProductComponentList() {
		logger.info("Enter method getProductComponentList.");
		List<String> productComponentList = null;
		Session session = getSession();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT pc.component_name FROM product_component pc ");
		sb.append(" WHERE pc.rec_active_flag='Y' GROUP BY pc.component_name ");
		
		try {
			productComponentList = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getProductComponentList.");
			return productComponentList;
		} finally {
			releaseSession(session);
		}
	}

	
	/**
	 * 获取 product 筛选条件列表。
	 */
	public List<String> getProductList() {
		logger.info("Enter method getProductList.");
		List<String> productList = null;
		Session session = getSession();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT p.product_name FROM product p ");
		sb.append(" WHERE p.rec_active_flag='Y' GROUP BY p.product_name ");
		
		try {
			productList = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method getProductList.");
			return productList;
		} finally {
			releaseSession(session);
		}
	}

	/**
	 * 在compare的条件下拼接 contract / tariff/ price list 列表的查询条件
	 * @return               [description]
	 */
	private String composeCompareQueryString(SearchQuoteVO searchQuoteVO) {
		StringBuffer sb = new StringBuffer(" ");
		
		if ( searchQuoteVO.getProvider() != null ) { // Provider Name.
			sb.append( "'" + searchQuoteVO.getProvider() + "', " ); 
		} else {
			sb.append( searchQuoteVO.getProvider() + ", " );
		}

		if ( searchQuoteVO.getProductInternal() != null ) { // Product Name.
			sb.append( "'" + searchQuoteVO.getProductInternal() + "', " ); 
		} else {
			sb.append( searchQuoteVO.getProductInternal() + ", " );
		}

		if ( searchQuoteVO.getaStreetNumber() != null ) { // A Street Number.
			sb.append( "'" + searchQuoteVO.getaStreetNumber() + "', " ); 
		} else {
			sb.append( searchQuoteVO.getaStreetNumber() + ", " );
		}

		if ( searchQuoteVO.getaStreetName() != null ) { // A Street Name.
			sb.append( "'" + searchQuoteVO.getaStreetName() + "', " );
		} else {
			sb.append( searchQuoteVO.getaStreetName() + ", " );
		}
		 
		if ( searchQuoteVO.getACity() != null ) { // A City.
			sb.append( "'" + searchQuoteVO.getACity() + "', " );
		} else {
			sb.append( searchQuoteVO.getACity() + ", " );
		}

		if ( searchQuoteVO.getAProvince() != null ) { // A Province.
			sb.append( "'" + searchQuoteVO.getAProvince() + "', " );
		} else {
			sb.append( searchQuoteVO.getAProvince() + ", " );
		}

		if ( searchQuoteVO.getAPostalCode() != null ) { // A Postal Code.
			sb.append( "'" + searchQuoteVO.getAPostalCode() + "', " ); 
		} else {
			sb.append( searchQuoteVO.getAPostalCode() + ", " );
		}

		if ( searchQuoteVO.getzStreetNumber() != null ) { // Z Street Number.
			sb.append( "'" + searchQuoteVO.getzStreetNumber() + "', " ); 
		} else {
			sb.append( searchQuoteVO.getzStreetNumber() + ", " );
		}

		if ( searchQuoteVO.getzStreetName() != null ) { // Z Street Name. 
			sb.append( "'" + searchQuoteVO.getzStreetName() + "', " ); 
		} else {
			sb.append( searchQuoteVO.getzStreetName() + ", " );
		}

		if ( searchQuoteVO.getZCity() != null ) { // Z City.
			sb.append( "'" + searchQuoteVO.getZCity() + "', " );  
		} else {
			sb.append( searchQuoteVO.getZCity() + ", " );
		}

		if ( searchQuoteVO.getZProvince() != null ) { // Z Province.
			sb.append( "'" + searchQuoteVO.getZProvince() + "', " );  
		} else {
			sb.append( searchQuoteVO.getZProvince() + ", " );
		}

		if ( searchQuoteVO.getZPostalCode() != null ) { // Z Postal Code. 
			sb.append( "'" + searchQuoteVO.getZPostalCode() + "'" ); 
		} else {
			sb.append( searchQuoteVO.getZPostalCode());
		}
		
		return sb.toString();
	}
	
	
	/**
	 * 查询Rate Discrepancy List 列表。
	 */
	public List<String> getRateDiscrepancyList( SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		logger.info("Enter method getRateDiscrepancyList.");
		
		StringBuffer sb = new StringBuffer();
		sb.append( searchRateDiscrepancyListQuerySelectString() );
		sb.append( rateDiscrepancyListWhereConditions(searchContractTariffPriceListVO) );

		sb.append( contractTariffPriceListSortAndLimitConditions(searchContractTariffPriceListVO) );
		
		final String sql = sb.toString();
		
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> priceListList = (List<String>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getRateDiscrepancyList.");
		return priceListList;
	}
	
	/**
	 * 从数据库中查询　Rate Discrepancy 列表分页显示时所需要的信息。
	 */
	public long getRateDiscrepancyListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		logger.info("Enter method getRateDiscrepancyListPageNo.");
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(searchRateDiscrepancyListQuerySelectString());
		sb.append(rateDiscrepancyListWhereConditions(searchContractTariffPriceListVO));
		
		
		final String sql = " select count(1) from ("+sb.toString()+ ") dn";
		
		HibernateTemplate template = this.getHibernateTemplate();
		
		Integer count = (Integer) template.execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getRateDiscrepancyListPageNo.");
		return count;
	}
	
	/**
	 * 查询Rate Discrepancy List 列表中的字段信息，只是查询字段信息，就是只有select语句。
	 * @return
	 */
	private String searchRateDiscrepancyListQuerySelectString() {

		StringBuffer sb = new StringBuffer();
		
		String attachmentPointId = "(CASE " +
				"WHEN r.audit_reference_type_id = 2 THEN " +
				"(SELECT tf.attachment_point_id " +
				"FROM tariff t, " +
				"tariff_file tf " +
				"WHERE t.tariff_file_id = tf.id " +
				"AND   t.id = r.audit_reference_id) " +
				"WHEN r.audit_reference_type_id = 3 THEN " +
				"(SELECT cf.attachment_point_id " +
				"FROM contract c," +
				" contract_file cf " +
				"WHERE c.contract_file_id = cf.id " +
				"AND   c.id = r.audit_reference_id) " +
				"WHEN r.audit_reference_type_id = 5 THEN " +
				"(SELECT plf.attachment_point_id " +
				"FROM price_list pl, " +
				"price_list_file plf " +
				"WHERE pl.price_list_file_id = plf.id " +
				"AND   pl.id = r.audit_reference_id) END)";
		
		sb.append(" SELECT CONCAT('{invoiceNumber: \"', toJSON(i.invoice_number IS NULL, '', i.invoice_number), ");
		sb.append(" '\", accountNumber: \"', toJSON(i.account_number IS NULL, '', i.account_number), ");
		sb.append(" '\", invoiceDate: \"', toJSON(  i.invoice_date IS NULL, '',   i.invoice_date), ");
		sb.append(" '\", circuitNumber: \"', toJSON(  p.circuit_number IS NULL, '',   p.circuit_number), ");
		sb.append(" '\", usoc: \"', toJSON(  p.usoc IS NULL, '', p.usoc), ");
		sb.append(" '\", usocDescription: \"', toJSON(  p.usoc_description IS NULL, '', p.usoc_description), ");
		sb.append(" '\", actualAmount: \"', toJSON(  r.actual_amount IS NULL, '', r.actual_amount), ");
		sb.append(" '\", expectAmount: \"', toJSON(  r.expect_amount IS NULL, '', r.expect_amount), ");
		sb.append(" '\", productName: \"', toJSON(  pd.product_name IS NULL, '', pd.product_name), ");
		sb.append(" '\", componentName: \"', toJSON(  pc.component_name IS NULL, '', pc.component_name), ");
		sb.append(" '\", itemTypeName: \"', toJSON( it.item_type_name IS NULL, '', it.item_type_name), ");
		sb.append(" '\", itemName: \"', toJSON( p.item_name IS NULL, '', p.item_name), ");
		sb.append(" '\", vendorName: \"', toJSON( v.vendor_name IS NULL, '', v.vendor_name), ");
		sb.append(" '\", attachmentPointId: \"', toJSON("+ attachmentPointId +" IS NULL, '', "+ attachmentPointId +"), ");
		
		sb.append(" '\"}') ");
		
		return sb.toString();
	}
	
	/**
	 * 查询Rate Discrepancy List 列表的where 条件。
	 * @param searchContractTariffPriceListVO
	 * @return
	 */
	private String rateDiscrepancyListWhereConditions( SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		StringBuffer sb = new StringBuffer();
		
		
		sb.append(" FROM audit_result r");
		sb.append(" INNER JOIN invoice i");
		sb.append(" ON r.invoice_id = i.id");
		sb.append(" INNER JOIN ban b");
		sb.append(" ON b.id = i.ban_id");
		sb.append(" INNER JOIN vendor v");
		sb.append(" ON v.id = b.vendor_id");
		sb.append(" INNER JOIN proposal p");
		sb.append(" ON r.proposal_id = p.id");
		sb.append(" LEFT JOIN product pd");
		sb.append(" ON p.product_id = pd.id");
		sb.append(" LEFT JOIN product_component pc");
		sb.append(" ON p.product_component_id = pc.id");
		sb.append(" LEFT JOIN item_type it");
		sb.append(" ON it.id = p.item_type_id");
		sb.append(" WHERE i.rec_active_flag = 'Y'");
		sb.append(" AND   i.invoice_date >= subdate( curdate(), INTERVAL 1 YEAR)");
		// 如果是tariff，是2；如果是Contract是3，如果是Price list，是5
		sb.append(" AND   r.audit_reference_type_id = " + searchContractTariffPriceListVO.getAuditReferenceTypeId());
		// 当前tariff，Contract，Price list的记录ID
		sb.append(" AND   r.audit_reference_id = " + searchContractTariffPriceListVO.getAuditReferenceId());
		sb.append(" AND   r.audit_status_id = 2");
//		sb.append(" ORDER BY i.id DESC, p.circuit_number");
		
		return sb.toString();
	}
	
	/**
	 * 导出 Rate Discrepancy 数据到 excel
	 * @param searchContractTariffPriceListVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getRateDiscrepancyDataForExcel( SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		logger.info("Enter method getRateDiscrepancyDataForExcel.");
		
		final String sql = this.searchRateDiscrepancyForExcelQueryString(searchContractTariffPriceListVO);
		List<Object[]> re;
		Session session = getSession();
		try {
			re = session.createSQLQuery(sql).list();
			logger.info("Exit method getRateDiscrepancyDataForExcel.");
			return re;	
		} finally {
			releaseSession(session);
		}
	}
	
	/**
	 * 导出 Rate Discrepancy 数据到 excel
	 * @param searchContractTariffPriceListVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public long getRateDiscrepancyDataForExcelPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO) {
		logger.info("Enter method getRateDiscrepancyDataForExcelPageNo.");
		
		final String sql = " select count(1) from ("+ this.searchRateDiscrepancyForExcelQueryString(searchContractTariffPriceListVO) + ") dn";
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getRateDiscrepancyDataForExcelPageNo.");
		return count;
	}
	
	
	/**
	 * 查询 Rate Discrepancy List 的SQL语句, 用于excel 导出
	 * @param  searchContractTariffPriceListVO [description]
	 * @return     [description]
	 */
	private String searchRateDiscrepancyForExcelQueryString(SearchContractTariffPriceListVO searchContractTariffPriceListVO){
		
		
		StringBuffer sb = new StringBuffer(" SELECT i.invoice_number,"); 
		sb.append(" i.account_number,");
		sb.append(" i.invoice_date,");
		sb.append(" p.circuit_number,");
		sb.append(" p.usoc,");
		sb.append(" p.usoc_description,");
		sb.append(" r.actual_amount,");
		sb.append(" r.expect_amount,");
		sb.append(" pd.product_name,");
		sb.append(" pc.component_name,");
		sb.append(" it.item_type_name,");
		sb.append(" p.item_name,");
		sb.append(" v.vendor_name");
		sb.append(rateDiscrepancyListWhereConditions(searchContractTariffPriceListVO));
		
		if(searchContractTariffPriceListVO.getFilter()!=null){
			sb.append(" and " + searchContractTariffPriceListVO.getFilter());
		}	
		sb.append(searchContractTariffPriceListVO.getOrderByCause(null));
		return sb.toString();
	}

	
	
	
}