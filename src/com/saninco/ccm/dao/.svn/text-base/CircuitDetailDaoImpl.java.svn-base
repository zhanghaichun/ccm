package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.Contract;
import com.saninco.ccm.model.Tariff;
import com.saninco.ccm.model.VendorCircuit;
import com.saninco.ccm.vo.SearchCircuitVO;

public class CircuitDetailDaoImpl extends HibernateDaoSupport implements ICircuitDetailDao {

	public VendorCircuit findVendorCircuitById(Integer vendorCircuitId) {
		VendorCircuit vendorCircuit = (VendorCircuit) getHibernateTemplate().get(VendorCircuit.class, vendorCircuitId);
		
		return vendorCircuit;
	}
	
	public Integer findVendorCircuitId(String proposalId) {
		logger.info("Enter method findVendorCircuitId.");
		final String sql = this.getfindVendorCircuitIdSql(proposalId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return l.get(0) != null ? new Integer(l.get(0).toString()) : l.get(0);
			}
		});
		logger.info("Exit method findVendorCircuitId.");
		return count;
	}
	
	private String getfindVendorCircuitIdSql(String proposalId) {
		logger.info("Enter method getfindVendorCircuitIdSql.");
		StringBuffer sb = new StringBuffer("select ii.vendor_circuit_id from proposal p ,invoice_item ii where p.invoice_item_id = ii.id and ii.rec_active_flag = 'Y' and p.rec_active_flag = 'Y' and p.id = "+proposalId);
		logger.info("Exit method getfindVendorCircuitIdSql.");
		return sb.toString();
	}
	public long searchSCOATotalPageNo(SearchCircuitVO svo) {
		logger.info("Enter method searchSCOATotalPageNo.");
		final String sql = this.getSearchSCOATotalPageNoQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method searchSCOATotalPageNo.");
		return count;
	}

	private String getSearchSCOATotalPageNoQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getSearchSCOATotalPageNoQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(buildSCOAConditions(svo));
		logger.info("Exit method getSearchSCOATotalPageNoQueryString.");
		return sb.toString();
	}
	
	private String buildSCOAConditions(SearchCircuitVO svo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM cost_item c");
		sb.append(" INNER JOIN vendor_circuit vc ON vc.id = c.vendor_circuit_id");
		sb.append(" INNER JOIN account_code ac ON c.account_code_id = ac.id ");
		sb.append(" where vc.id = ");
		sb.append(svo.getVendorCircuitId());
		sb.append(" and c.rec_active_flag = 'Y'");
		sb.append(" and vc.rec_active_flag = 'Y'");
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		return sb.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchSCOAList(SearchCircuitVO svo) {
		logger.info("Enter method searchCircuit.");
		final String sql = this.getSCOASearchQueryString(svo);
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
	
	private String getSCOASearchQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getSCOASearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{chargeType:\"',toJSON(c.charge_type is null ,'',c.charge_type),");
		sb.append("'\",invoiceDate:\"',toJSON(invoice_date is null,'',invoice_date),");
		sb.append("'\",expectedCost:\"',toJSON(c.expected_cost is null,'',c.expected_cost),");
		sb.append("'\",company:\"',toJSON(ac.company is null,'',ac.company),");
		sb.append("'\",location:\"',toJSON(ac.location is null,'',ac.location),");
		sb.append("'\",department:\"',toJSON(ac.department is null,'',ac.department),");
		sb.append("'\",product:\"',toJSON(ac.product is null,'',ac.product),");
		sb.append("'\",account:\"',toJSON(ac.account is null,'',ac.account),");
		sb.append("'\",channel:\"',toJSON(ac.channel is null,'',ac.channel),");
		sb.append("'\"}') a ");
		sb.append(buildSCOAConditions(svo));
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getSCOASearchQueryString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getSCOADataForExcel(SearchCircuitVO svo) {
		logger.info("Enter method getSCOAListForExcel.");
		final String sql = this.getSCOAListForExcelQueryString(svo);
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
	
	private String getSCOAListForExcelQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getSCOAListForExcelQueryString.");
		StringBuffer sb = new StringBuffer("select toJSON(c.charge_type is null ,'',c.charge_type),");
		sb.append("'\",invoiceDate:\"',toJSON(invoice_date is null,'',invoice_date),");
		sb.append("toJSON(c.expected_cost is null,'',c.expected_cost),");
		sb.append("toJSON(ac.company is null,'',ac.company),");
		sb.append("toJSON(ac.location is null,'',ac.location),");
		sb.append("toJSON(ac.department is null,'',ac.department),");
		sb.append("toJSON(ac.product is null,'',ac.product),");
		sb.append("toJSON(ac.account is null,'',ac.account),");
		sb.append("toJSON(ac.channel is null,'',ac.channel)");
		sb.append(buildSCOAConditions(svo));
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getSCOAListForExcelQueryString.");
		return sb.toString();
	}
	
	
	public long searchCostTotalPageNo(SearchCircuitVO svo) {
		logger.info("Enter method searchCostTotalPageNo.");
		final String sql = this.getSearchCostTotalPageNoQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method searchCostTotalPageNo.");
		return count;
	}
	
	private String getSearchCostTotalPageNoQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getSearchCostTotalPageNoQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) from (select count(1) ");
		sb.append(buildCostConditions(svo));
		sb.append(" group by i.invoice_number,i.invoice_date,it.item_type_summary ) t");
		logger.info("Exit method getSearchCostTotalPageNoQueryString.");
		return sb.toString();
	}
	
	private String buildCostConditions(SearchCircuitVO svo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM invoice i");
		sb.append(" INNER JOIN invoice_item ii ON ii.invoice_id = i.id");
		sb.append(" INNER JOIN vendor_circuit vc ON vc.id = ii.vendor_circuit_id");
		sb.append(" LEFT JOIN item_type it ON ii.item_type_id = it.id");
		sb.append(" where vc.id = ");
		sb.append(svo.getVendorCircuitId());
		sb.append(" and i.rec_active_flag = 'Y'");
		sb.append(" and ii.rec_active_flag = 'Y'");
		sb.append(" and vc.rec_active_flag = 'Y'");
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")  
	public List<Object[]> searchCostList(SearchCircuitVO svo) {
		logger.info("Enter method searchCircuit.");
		final String sql = this.getCostSearchQueryString(svo);
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

	private String getCostSearchQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getCostSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{invoiceNumber:\"',toJSON(i.invoice_number is null ,'',i.invoice_number),");
		sb.append("'\",invoiceDate:\"',toJSON(i.invoice_date is null,'',i.invoice_date),");
		sb.append("'\",chargeType:\"',toJSON(it.item_type_summary is null,'',it.item_type_summary),");
		sb.append("'\",itemAmount:\"',format(if(sum(ii.item_amount) is null,'',sum(ii.item_amount)),2),");
		sb.append("'\"}') a ");
		sb.append(buildCostConditions(svo));
		sb.append("group by i.invoice_number,i.invoice_date,it.item_type_summary");
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getCostSearchQueryString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getCostDataForExcel(SearchCircuitVO svo) {
		logger.info("Enter method getCostDataForExcel.");
		final String sql = this.getCostListForExcelQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getCostDataForExcel.");
		return l;
	}
	
	private String getCostListForExcelQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getCostListForExcelQueryString.");
		StringBuffer sb = new StringBuffer("select toJSON(i.invoice_number is null ,'',i.invoice_number),");
		sb.append("toJSON(i.invoice_date is null,'',i.invoice_date),");
		sb.append("toJSON(it.item_type_summary is null,'',it.item_type_summary) as charge_type,");
		sb.append("format(if(sum(ii.item_amount) is null,'',sum(ii.item_amount)),2)");
		sb.append(buildCostConditions(svo));
		sb.append("group by i.invoice_number,i.invoice_date,it.item_type_summary");
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getCostListForExcelQueryString.");
		return sb.toString();
	}
	
	public long searchTariffTotalPageNo(SearchCircuitVO svo) {
		logger.info("Enter method searchTariffTotalPageNo.");
		final String sql = this.getSearchTariffTotalPageNoQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method searchTariffTotalPageNo.");
		return count;
	}
	public long doSearchMappingPageNo(SearchCircuitVO svo) {
		logger.info("Enter method doSearchMappingPageNo.");
		final String sql = this.getSearchMappingTotalPageNoQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method doSearchMappingPageNo.");
		return count;
	}
	private String getSearchMappingTotalPageNoQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getSearchMappingTotalPageNoQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(buildMappingConditions(svo));
		logger.info("Exit method getSearchMappingTotalPageNoQueryString.");
		return sb.toString();
	}
	private String buildMappingConditions(SearchCircuitVO svo) {
		logger.info("Enter method Mapping.");
		StringBuffer sb = new StringBuffer();		
		sb.append(" from mapping");
		sb.append(" where vendor_circuit_id =  ");
		sb.append(svo.getVendorCircuitId());
		sb.append(" and rec_active_flag = 'Y'");
		
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		logger.info("Exit method Mapping.");
		return sb.toString();
	}
	private String getSearchTariffTotalPageNoQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getSearchTariffTotalPageNoQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(buildTariffConditions(svo));
		logger.info("Exit method getSearchTariffTotalPageNoQueryString.");
		return sb.toString();
	}
	
	private String buildTariffConditions(SearchCircuitVO svo) {
		logger.info("Enter method buildTariffConditions.");
		StringBuffer sb = new StringBuffer();
//		sb.append("from attachment_file af");
//		sb.append(" inner join attachment_point ap on af.attachment_point_id = ap.id");
//		sb.append(" where ap.table_name = 'vendor_circuit'");
//		sb.append(" and ap.table_id_value =" + svo.getVendorCircuitId()+ " ");
//		sb.append(" and ap.rec_active_flag =  'Y'");
//		sb.append(" and af.rec_active_flag =  'Y'");
		
		sb.append(" from tariff_link");
		sb.append(" where circuit_id = ");
		sb.append(svo.getVendorCircuitId());
		sb.append(" and rec_active_flag = 'Y'");
		
		if (svo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svo.getFilter());
		}
		logger.info("Exit method buildTariffConditions.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")  
	public List<Object[]> doSearchMappingList(SearchCircuitVO svo) {
		logger.info("Enter method doSearchMappingList.");
		final String sql = this.getMappingSearchQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
			throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method doSearchMappingList.");
		return l;
	}
	private String getMappingSearchQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getMappingSearchQueryString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',id,'\", ");
		sb.append("tariff_id:\"',toJSON(tariff_id is null,'',tariff_id),'\", ");
		sb.append("contract_id:\"',toJSON(contract_id is null,'',contract_id),'\", ");
		sb.append("tc_flag:\"',toJSON(tc_flag is null,'',tc_flag),'\", ");
		sb.append("item_number:\"',toJSON(item_number is null,'',item_number),'\", ");
		sb.append("agreement_number:\"',toJSON(agreement_number is null,'',agreement_number),'\", ");
		sb.append("description:\"',toJSON(description is null,'',description),'\", ");
		sb.append("description_source:\"',toJSON(description_source is null,'',description_source ),'\" ");
		sb.append("}') a ");
		sb.append(buildMappingConditions(svo));
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getMappingSearchQueryString.");
		return sb.toString();
	}
	@SuppressWarnings("unchecked")  
	public List<Object[]> searchTariffList(SearchCircuitVO svo) {
		logger.info("Enter method searchCircuit.");
		final String sql = this.getTariffSearchQueryString(svo);
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
	
	private String getTariffSearchQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getTariffSearchQueryString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',id,'\", ");
		sb.append("tariff_name:\"',toJSON(tariff_name is null,'',tariff_name),'\", ");
		sb.append("tariff_path:\"',toJSON(tariff_path is null,'',tariff_path),'\", ");
		sb.append("notes:\"',toJSON(notes is null,'',notes),'\", ");
		sb.append("created_timestamp:\"',toJSON(created_timestamp is null,'',created_timestamp ),'\" ");
		sb.append("}') a ");
		sb.append(buildTariffConditions(svo));
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getTariffSearchQueryString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getTariffDataForExcel(SearchCircuitVO svo) {
		logger.info("Enter method getTariffDataForExcel.");
		final String sql = this.getTariffListForExcelQueryString(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getTariffDataForExcel.");
		return l;
	}
	
	private String getTariffListForExcelQueryString(SearchCircuitVO svo) {
		logger.info("Enter method getTariffListForExcelQueryString.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append("toJSON(tariff_name is null,'',tariff_name),");
		sb.append("toJSON(created_timestamp is null,'',created_timestamp ),");
		sb.append("toJSON(notes is null,'',notes)");
		sb.append(buildTariffConditions(svo));
		sb.append(svo.getOrderByCause(null) + " ");
		sb.append(svo.getLimitCause() + " ");
		logger.info("Exit method getTariffListForExcelQueryString.");
		return sb.toString();
	}
	
	public void deleteAttachmentFile(AttachmentFile attachmentFile) {
		this.getHibernateTemplate().delete(attachmentFile);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> doSearchLogicalPathList(Integer vendorCircuitId) {
		Session session=getSession();
		try {
			List<Object[]> list=null;
			list=session.createSQLQuery("SELECT l.logical_path_circuit_id, l.logical_path_circuit_number, c.circuit_type_name FROM logical_path l, circuit_type c WHERE l.circuit_type_id = c.id AND l.rec_active_flag = 'Y' AND vendor_circuit_id = "+vendorCircuitId+" ORDER BY c.level_no;").list();
			return list;
		} finally{
			releaseSession(session);
		}
		
		
	}
	
	public Tariff doSearchTariff(Integer id) {

		try {
			Tariff tariff=(Tariff)getHibernateTemplate().get(
					"com.saninco.ccm.model.Tariff", id);
			return tariff;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	public Contract SearchContract(Integer id) {

		try {
			Contract contract=(Contract)getHibernateTemplate().get(
					"com.saninco.ccm.model.Contract", id);
			return contract;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
}
