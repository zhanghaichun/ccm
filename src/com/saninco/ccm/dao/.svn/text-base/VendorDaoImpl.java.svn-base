/**
 * 
 */
package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Contact;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.model.VendorStatus;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.ContactVO;
import com.saninco.ccm.vo.SearchVendorVo;

/**
 * @author Joe.Yang
 *
 */
public class VendorDaoImpl extends HibernateDaoSupport implements IVendorDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	public VendorDaoImpl() {
	}
	
	public VendorStatus findByStatusId(Integer id) {
		logger.debug("getting VendorStatus instance with id: " + id);
		try {
			VendorStatus instance = (VendorStatus) getHibernateTemplate().get(
					"com.saninco.ccm.model.VendorStatus", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public void saveVendor(Vendor vendor) {
		logger.debug("saving vendor instance");
		try {
			getHibernateTemplate().save(vendor);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
		
	}
	
	public void editContact(ContactVO cvo) {
		logger.info("Enter method editContact.");
		final String queryString = editContactQuertString(cvo);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method editContact.");
		
	}
	private String editContactQuertString(ContactVO cvo) {
		logger.info("Enter method editContactQuertString.");
		StringBuffer sb = new StringBuffer("update contact set ");
			sb.append("  first_name = '" + DaoUtil.ccmEscape(cvo.getFirstName()) + "' ");
			sb.append(" , last_name = '" + DaoUtil.ccmEscape(cvo.getLastName()) + "' ");
			sb.append(" , attention_name = '" + DaoUtil.ccmEscape(cvo.getAttentionName()) + "' ");
			sb.append(" , co_name = '" + DaoUtil.ccmEscape(cvo.getCoName()) + "' ");
			sb.append(" , address_1 = '" + DaoUtil.ccmEscape(cvo.getAddress1()) + "' ");
			sb.append(" , address_2 = '" + DaoUtil.ccmEscape(cvo.getAddress2()) + "' ");
			sb.append(" , city = '" + DaoUtil.ccmEscape(cvo.getCity()) + "' ");
			sb.append(" , country = '" + DaoUtil.ccmEscape(cvo.getCountry()) + "' ");
			sb.append(" , postal_code = '" + DaoUtil.ccmEscape(cvo.getPostalCode()) + "' ");
			sb.append(" , primary_phone = '" + cvo.getPrimaryPhone() + "' ");
			sb.append(" , other_phone = '" + cvo.getOtherPhone() + "' ");
			sb.append(" , office_phone = '" + cvo.getOfficePhone() + "' ");
			sb.append(" , cell_phone = '" + cvo.getCellPhone() + "' ");
			sb.append(" , province = '" + cvo.getProvince() + "' ");
			sb.append(" , fax_number = '" + cvo.getFaxNumber() + "' ");
			sb.append(" , email = '" + cvo.getEmail() + "' ");
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + SystemUtil.getCurrentUserId() + "' ");
		sb.append(" where id = " + cvo.getId() + " ");
		logger.info("Exit method editContactQuertString.");
		return sb.toString();
	}
	
	public void save(Contact transientInstance) {
		logger.debug("saving Contact instance");
		try {
			getHibernateTemplate().save(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}
	
	public void delectContact(Integer contactId) {
		logger.info("Enter method delectContact.");
		final String queryString = deleteContactQueryString(contactId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method delectContact.");
	}
	
	private String deleteContactQueryString(Integer contactId) {
		logger.info("Enter method deleteContactQueryString.");
		StringBuffer sb = new StringBuffer("update contact set ");
		sb.append(" contact.rec_active_flag = 'N' ");
		sb.append(" where id = " + contactId + " ");
		logger.info("Exit method deleteContactQueryString.");
		return sb.toString();
	}	
	
	public void updateVendorMainData(String UpvendorvendorNameTxt,Integer vendorId,String vendorAcronymM,String summaryVendorNameM,Integer vendorStatusM,String channel,String lpcRate) {
		logger.info("Enter method updateVendorMainData.");
		final String queryString = updateVendorMainDataQueryString(UpvendorvendorNameTxt,vendorId,vendorAcronymM,summaryVendorNameM,vendorStatusM,channel,lpcRate);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		
		logger.info("Enter method updateVendorMainData.");
	}
	
	private String updateVendorMainDataQueryString(String UpvendorvendorNameTxt,Integer vendorId,String vendorAcronymM,String summaryVendorNameM,Integer vendorStatusM,String channel,String lpcRate) {
		logger.info("Enter method updateVendorMainDataQueryString.");
		StringBuffer sb = new StringBuffer("update vendor set ");
		sb.append(" vendor_acronym = '" + vendorAcronymM + "' ");
		if(summaryVendorNameM!=null){
			sb.append(" , summary_vendor_name = '" + summaryVendorNameM + "' ");
		}
		
		sb.append(" , channel = '" + channel + "' ");

		if ("".equals(lpcRate)) {
			sb.append(" , lpc_rate = null ");
		}else {
			Double lpcRateValue = Double.parseDouble(lpcRate);
			sb.append(" , lpc_rate = " + lpcRateValue + " ");
		}

		if(UpvendorvendorNameTxt!=null){
			sb.append(" , vendor_name = '" + UpvendorvendorNameTxt + "' ");
		}
		sb.append(" , vendor_status_id = '" + vendorStatusM + "' ");
		sb.append(" , modified_timestamp = now() ");
		sb.append(" , modified_by = '" + SystemUtil.getCurrentUserId() + "' ");
		sb.append(" where id = " + vendorId + " ");
		logger.info("Exit method updateVendorMainDataQueryString.");
		return sb.toString();
	}	
	
	public Vendor findById(java.lang.Integer id) {
		logger.debug("getting Vendor instance with id: " + id);
		try {
			Vendor instance = (Vendor) getHibernateTemplate().get(
					"com.saninco.ccm.model.Vendor", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public Vendor load(java.lang.Integer id) {
		logger.debug("loading Vendor instance with id: " + id);
		try {
			Vendor instance = (Vendor) getHibernateTemplate().load(Vendor.class, id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("load failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IVendorDao#getVendors()
	 */
	@SuppressWarnings("unchecked")
	public List getVendors() {
		logger.info("Enter method getVendors.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select v.id,v.vendor_name from vendor v where v.rec_active_flag='Y' and v.vendor_name!='ALL' order by v.vendor_name").list();
			logger.info("Exit method getVendors.");
			return l;
		} finally {
			releaseSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getUserPreviledgedVendors(final Integer userId) {
		logger.info("Enter method getUserPreviledgedVendors with userId "+userId+".");
		Session session = getSession();
		try {
//			List l = session.createSQLQuery("select v0.id,v0.vendor_name from vendor v0 where v0.vendor_status_id!=2 and v0.rec_active_flag='Y' order by v0.vendor_name asc ").list();
			List l = session.createSQLQuery("select v0.id,v0.vendor_name from vendor v0 where v0.rec_active_flag='Y' order by v0.vendor_name asc ").list();
			logger.info("Exit method getUserPreviledgedVendors.");
			return l;
		} finally {
			releaseSession(session);
		}
	}

	//add By donghao.guo for search condition
	public List<Object[]> getSearchConditionVendors() {
		logger.info("Enter method getSearchConditionVendors.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select v0.id,v0.vendor_name from vendor v0 where v0.rec_active_flag='Y' order by v0.vendor_name asc ").list();
			logger.info("Exit method getSearchConditionVendors.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	//add By senxu.tian for search condition
	public List<Object[]> getSearchConditionSummaryVendors() {
		logger.info("Enter method getSearchConditionSummaryVendors.");
		Session session = getSession();
		try {
			List l = session.createSQLQuery("SELECT v0.id, v0.summary_vendor_name " +
					"FROM vendor v0 " +
					"WHERE v0.rec_active_flag = 'Y' " +
					"AND v0.vendor_status_id = 1 " +
					"AND v0.summary_vendor_name is not null " +
					"AND v0.summary_vendor_name != '' " +
					"GROUP BY v0.summary_vendor_name " +
					"ORDER BY v0.vendor_name ASC").list();
			logger.info("Exit method getSearchConditionSummaryVendors.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
	public List<VendorStatus> getVendorStatus() {
		logger.info("Enter method getVendorStatus.");
		final String sql = "select distinct v0.* from vendor_status v0";
		HibernateTemplate template = this.getHibernateTemplate();
		List<VendorStatus> l = (List<VendorStatus>)template.execute(new HibernateCallback() {
	    public Object doInHibernate(Session session) throws HibernateException, SQLException {
	    	return session.createSQLQuery(sql).addEntity(VendorStatus.class).list();
	    }
	});
		logger.info("Exit method getVendorStatus.");
		return l;
	}
	
	public long getContactCount(ContactVO cvo){
		logger.info("Enter method getContactCount.");
		final String sql = getContactCountString(cvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getContactCount.");
		return count;
	}
	
	private String getContactCountString(ContactVO cvo) {
		logger.info("Enter method getContactCountString.");
		StringBuffer sb = new StringBuffer("select count(1) from contact c ");
		sb.append("where c.rec_active_flag =  'Y' ");
		sb.append(" and c.address_1 = '" + DaoUtil.ccmEscape(cvo.getAddress1()) + "' ");
		sb.append(" and c.city = '" + DaoUtil.ccmEscape(cvo.getCity()) + "' ");
		sb.append(" and c.province = '" + DaoUtil.ccmEscape(cvo.getProvince()) + "' ");
		sb.append(" and c.country = '" + DaoUtil.ccmEscape(cvo.getCountry()) + "' ");
		sb.append(" and c.postal_code = '" + DaoUtil.ccmEscape(cvo.getPostalCode()) + "' ");
		sb.append(" and c.primary_phone = '" + DaoUtil.ccmEscape(cvo.getPrimaryPhone()) + "' ");
		sb.append(" and c.email = '" + DaoUtil.ccmEscape(cvo.getEmail()) + "' ");
		sb.append(" and c.vendor_id = '" + cvo.getVendor() + "' ");
		logger.info("Exit method getContactCountString.");
		return sb.toString();
	}


	public long getVendorContactNO(ContactVO cvo) {
		logger.info("Enter method getVendorContactNO.");
		final String sql = getVendorContactNOString(cvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getVendorContactNO.");
		return count;
	}
	
	public long getTariffsNO(ContactVO cvo) {
		logger.info("Enter method getTariffsNO.");
		final String sql = getTariffsNOString(cvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getTariffsNO.");
		return count;
	}
	
	public long getBanExemptionCertificateNO(ContactVO cvo,String invoiceDate){
		logger.info("Enter method getBanExemptionCertificateNO.");
		final String sql = getBanExemptionCertificateNOString(cvo,invoiceDate);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getBanExemptionCertificateNO.");
		return count;
	}
	
	private String getVendorContactNOString(ContactVO cvo) {
		logger.info("Enter method getVendorContactNOString.");
		StringBuffer sb = new StringBuffer("select count(1) from contact  ");
		sb.append("where contact.vendor_id =" + cvo.getVendor() + "");
		sb.append(" and contact.rec_active_flag =  'Y'  ");
		logger.info("Exit method getVendorContactNOString.");
		return sb.toString();
	}
	
	public List<String> searchVendorContact(ContactVO cvo) {
		logger.info("Enter method searchVendorContact.");
		final String queryString = searchVendorContactSqlString(cvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchVendorContact.");
		return list;
	}
	
	private String getTariffsNOString(ContactVO cvo) {
		logger.info("Enter method getTariffsNOString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(searchTariffWhereConditions(cvo));
		logger.info("Exit method getTariffsNOString.");
		return sb.toString();
	}
	
	private String getBanExemptionCertificateNOString(ContactVO cvo,String invoiceDate) {
		logger.info("Enter method getBanExemptionCertificateNOString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(searchBanExemptionCertificateWhereConditions(cvo,invoiceDate));
		logger.info("Exit method getBanExemptionCertificateNOString.");
		return sb.toString();
	}
	
	public List<String> searchTariff(ContactVO cvo) {
		logger.info("Enter method searchTariff.");
		final String queryString = getTariffSqlString(cvo);
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
	
	public List<String> searchBanExemptionCertificate(ContactVO cvo,String invoiceDate) {
		logger.info("Enter method searchBanExemptionCertificate.");
		final String queryString = getBanExemptionCertificateSqlString(cvo,invoiceDate);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});
		logger.info("Exit method searchBanExemptionCertificate.");
		return list;
	}
	
	public void deleteBanExemptionCertificate(Integer id) {
		logger.info("Enter method deleteBanExemptionCertificate.");
		final String queryString = " delete from ban_exemption where id = " +id;
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method deleteBanExemptionCertificate.");
		
	}
	
	private String getTariffSqlString(ContactVO cvo) {
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
		sb.append(searchTariffWhereConditions(cvo));
		if (cvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(cvo.getFilter());
		}
		sb.append(" order by " + cvo.getSortField() + " "
				+ cvo.getSortingDirection());
		sb.append(" LIMIT " + cvo.getStartIndex() + "," + cvo.getRecPerPage());
		logger.info("Exit method getTariffSqlString.");
		return sb.toString();
	}
	
	private String getBanExemptionCertificateSqlString(ContactVO cvo,String invoiceDate) {
		logger.info("Enter method getBanExemptionCertificateSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',ban_exemption.id,'\", ");
		sb
				.append("certificate_name:\"',toJSON(ban_exemption.certificate_name is null,'',ban_exemption.certificate_name),'\", ");
		sb
				.append("file_path:\"',toJSON(ban_exemption.file_path is null,'',ban_exemption.file_path),'\", ");
		sb
				.append("created_timestamp:\"',toJSON(ban_exemption.created_timestamp is null,'',ban_exemption.created_timestamp ),'\", ");
//		sb
//				.append("created_by:\"',toJSON(`user`.user_name is null,'',`user`.user_name),'\", ");
		sb
				.append("modified_timestamp:\"',toJSON(ban_exemption.modified_timestamp is null,'',ban_exemption.modified_timestamp),'\", ");
		sb
				.append("notes:\"',toJSON(ban_exemption.notes is null,'',ban_exemption.notes),'\", ");
		sb
				.append("term_start_date:\"',toJSON(ban_exemption.term_start_date is null,'',DATE_FORMAT(ban_exemption.term_start_date,'%Y-%m-%d')),'\", ");
		sb
				.append("term_end_date:\"',toJSON(ban_exemption.term_end_date is null,'',DATE_FORMAT(ban_exemption.term_end_date,'%Y-%m-%d')),'\" ");
		sb.append("}') ");
		sb.append(searchBanExemptionCertificateWhereConditions(cvo,invoiceDate));
		if (cvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(cvo.getFilter());
		}
		sb.append(" order by " + cvo.getSortField() + " "
				+ cvo.getSortingDirection());
		sb.append(" LIMIT " + cvo.getStartIndex() + "," + cvo.getRecPerPage());
		logger.info("Exit method getBanExemptionCertificateSqlString.");
		return sb.toString();
	}
	
	public long getAllChargeTotal(SearchVendorVo svvo){
		logger.info("Enter method getAllChargeTotal.");
		final String sql = getAllChargeString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getAllChargeTotal.");
		return count;
	}
	
	private String  getAllChargeString(SearchVendorVo svvo) {
		logger.info("Enter method getAllChargeString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(getAllChargeWhereStatement(svvo));
		logger.info("Exit method getAllChargeString.");
		return sb.toString();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<String> searchAllCharges(SearchVendorVo svvo) {
		logger.info("Enter method searchAllCharges.");
		final String queryString = searchAllChargesSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchAllCharges.");
		return list;
	}
	
	private String searchAllChargesSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchAllChargesSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("invoice_number:\"',toJSON(i.invoice_number is null,'',i.invoice_number),'\", ");
		sb.append("invoice_date:\"',toJSON(i.invoice_date is null,'',i.invoice_date),'\", ");
		sb.append("invoice_mrc:\"',toJSON(i.invoice_mrc is null,'',i.invoice_mrc ),'\", ");
		sb.append("invoice_occ:\"',toJSON(i.invoice_occ is null,'',i.invoice_occ ),'\", ");
		sb.append("invoice_usage:\"',toJSON(i.invoice_usage is null,'',i.invoice_usage ),'\", ");
		sb.append("invoice_late_payment_charge:\"',toJSON(i.invoice_late_payment_charge is null,'',i.invoice_late_payment_charge ),'\", ");
		sb.append("credit_adjustment_amount:\"',toJSON((ifnull(i.invoice_credit,0)+ifnull(i.invoice_adjustment,0)) is null,'',(ifnull(i.invoice_credit,0)+ifnull(i.invoice_adjustment,0)) ),'\", ");
		sb.append("payment_amount:\"',toJSON(i.payment_amount is null,'',i.payment_amount ),'\", ");
		sb.append("invoice_status_name:\"',toJSON(ins.invoice_status_name is null,'',ins.invoice_status_name),'\" ");
		sb.append("}') ");
		sb.append(getAllChargeWhereStatement(svvo));
		if (svvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svvo.getFilter());
		}
		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		sb.append(" LIMIT " + svvo.getStartIndex() + "," + svvo.getRecPerPage());
		logger.info("Exit method searchAllChargesSqlString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchAllChargesToExcel(SearchVendorVo svvo) {
		logger.info("Enter method searchAllChargesToExcel.");
		final String queryString = searchAllChargesToExcelSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchAllChargesToExcel.");
		return list;
	}
	
	private String searchAllChargesToExcelSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchAllChargesToExcelSqlString.");
		StringBuffer sb = new StringBuffer("select i.invoice_number , i.invoice_date , i.invoice_mrc , i.invoice_occ , i.invoice_usage , i.invoice_late_payment_charge , (ifnull(i.invoice_credit,0)+ifnull(i.invoice_adjustment,0)) , i.payment_amount , ins.invoice_status_name");
		sb.append(getAllChargeWhereStatement(svvo));
		
		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		sb.append(" LIMIT " + svvo.getStartIndex() + "," + svvo.getRecPerPage());
		logger.info("Exit method searchAllChargesToExcelSqlString.");
		return sb.toString();
	}
	
	private String getAllChargeWhereStatement(SearchVendorVo svvo){
		StringBuffer sb = new StringBuffer(" from invoice i , invoice_status ins ,ban b ");
		sb.append(" where i.invoice_status_id = ins.id ");
		sb.append(" and i.ban_id=b.id");
		sb.append(" and i.rec_active_flag =  'Y' and i.invoice_status_id >=9  and i.invoice_status_id <>98");
		sb.append(" and b.rec_active_flag= 'Y' and b.master_ban_flag='Y' ");
		if (svvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svvo.getFilter());
		}
		if(svvo.getVendorId() != "" && svvo.getVendorId() != null )
			sb.append(" and b.vendor_id = "+svvo.getVendorId()+" ");
		if(svvo.getBanId() != "" && svvo.getBanId() != null )
			sb.append(" and i.ban_id = "+svvo.getBanId()+" ");
		return sb.toString();
	}
	
	public long getRecurringChargeTotal(SearchVendorVo svvo){
		logger.info("Enter method getRecurringChargeTotal.");
		final String sql = getRecurringChargeString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getRecurringChargeTotal.");
		return count;
	}
	
	private String  getRecurringChargeString(SearchVendorVo svvo) {
		
		logger.info("Enter method getRecurringChargeString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(getRecurringChargeStatement(svvo));
		logger.info("Exit method getRecurringChargeString.");
		return sb.toString();
		
	}
	
	public List<String> searchRecurringCharge(SearchVendorVo svvo) {
		logger.info("Enter method searchRecurringCharge.");
		final String queryString = searchRecurringChargeSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchRecurringCharge.");
		return list;
	}
	
	private String searchRecurringChargeSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchRecurringChargeSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("stripped_circuit_number:\"',toJSON(c.stripped_circuit_number is null,'',c.stripped_circuit_number),'\", ");
		sb.append("line_item_desc:\"',toJSON(c.line_item_desc is null,'',c.line_item_desc),'\", ");
		sb.append("total_cost_amount:\"',toJSON(c.total_cost_amount is null,'',c.total_cost_amount ),'\", ");
		sb.append("counts:\"',toJSON(c.counts is null,'',c.counts ),'\", ");
		sb.append("numberOfOccurrence:\"',toJSON((c.total_cost_amount/c.counts) is null,'',(c.total_cost_amount/c.counts) ),'\" ");
		sb.append("}') ");
		sb.append(getRecurringChargeStatement(svvo));
		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		sb.append(" LIMIT " + svvo.getStartIndex() + "," + svvo.getRecPerPage());
		logger.info("Exit method searchRecurringChargeSqlString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchRecurringChargeToExcel(SearchVendorVo svvo) {
		logger.info("Enter method searchRecurringChargeToExcel.");
		final String queryString = searchRecurringChargeToExcelSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchRecurringChargeToExcel.");
		return list;
	}
	
	private String searchRecurringChargeToExcelSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchRecurringChargeToExcelSqlString.");
		StringBuffer sb = new StringBuffer("select c.stripped_circuit_number,c.line_item_desc,c.total_cost_amount,c.counts,Round((c.total_cost_amount/c.counts),2) ");
		sb.append(getRecurringChargeStatement(svvo));
		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		sb.append(" LIMIT " + svvo.getStartIndex() + "," + svvo.getRecPerPage());
		logger.info("Exit method searchRecurringChargeToExcelSqlString.");
		return sb.toString();
	}
	
	private String getRecurringChargeStatement(SearchVendorVo svvo){
		StringBuffer sb = new StringBuffer(" from cost_item c ");
		sb.append(" where c.rec_active_flag =  'Y' and (c.item_type_id = 13 or c.item_type_id like '3%')");
		if(svvo.getBanId() != "" && svvo.getBanId() != null )
			sb.append(" and (c.ban_id = "+svvo.getBanId()+" ");
		else if(svvo.getVendorId() != "" && svvo.getVendorId() != null )
			sb.append(" and c.ban_id in (select ba.id from ban ba where ba.rec_active_flag = 'Y' and ba.vendor_id = (select v.id from ban b ,vendor v where b.vendor_id = v.id and b.rec_active_flag = 'Y' and v.rec_active_flag = 'Y' and b.id = "+svvo.getBanId()+" )) ");
		if (svvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svvo.getFilter());
		}
		sb.append(" ) ");
		return sb.toString();
	}
	
	public long getSearchSortProposalTotalPageNo(SearchVendorVo svvo){
		logger.info("Enter method getSearchSortProposalTotalPageNo.");
		final String sql = getSearchSortProposalString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getSearchSortProposalTotalPageNo.");
		return count;
	}
	
	private String  getSearchSortProposalString(SearchVendorVo svvo) {
		
		logger.info("Enter method getSearchSortProposalString.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(getSortProposalStatement(svvo,true));
		logger.info("Exit method getSearchSortProposalString.");
		return sb.toString();
		
	}
	
	public List<String> getSearchSortProposalTable(SearchVendorVo svvo) {
		logger.info("Enter method getSearchSortProposalTable.");
		final String queryString = searchSortProposalTableSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method getSearchSortProposalTable.");
		return list;
	}
	
	private String searchSortProposalTableSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchSortProposalTableSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("invoice_number:\"',toJSON(t.invoice_number is null,'',t.invoice_number),'\", ");
		sb.append("invoice_date:\"',toJSON(t.invoice_date is null,'',t.invoice_date),'\", ");
		sb.append("item_name:\"',toJSON(t.item_name is null,'',t.item_name ),'\", ");
		sb.append("item_amount:\"',toJSON(t.item_amount is null,'',t.item_amount ),'\", ");
		sb.append("payment_amount:\"',toJSON(t.payment_amount is null,'',t.payment_amount),'\", ");
		sb.append("dispute_amount:\"',toJSON(t.dispute_amount is null,'',t.dispute_amount),'\" ");
//		sb.append("approver:\"',toJSON(t.approver is null,'',t.approver),'\", ");
//		sb.append("approved_date:\"',toJSON(t.approved_date is null,'',t.approved_date),'\" ");
		sb.append("}') ");
		sb.append(" from (select i.invoice_number , i.invoice_date , p.item_name , ii.item_amount , p.payment_amount , p.dispute_amount ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then (select concat(u.first_name, \" \", u.last_name) from user u where u.id = i.modified_by) ");
//		sb.append(" else (select concat(u.first_name, \" \", u.last_name) from history_db.invoice_history ih, user u where ih.id = i.id ");
//		sb.append(" and ih.invoice_status_id = 21 and ih.modified_by = u.id and u.rec_active_flag = 'Y' order by ih.modified_timestamp desc limit 1) end) as approver, ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then i.modified_by else ");
//		sb.append(" (select ih.modified_by from history_db.invoice_history ih where ih.id = i.id and ih.invoice_status_id = 21 order by ih.modified_timestamp desc limit 1) end) as approved_date ");
		sb.append(getSortProposalStatement(svvo,false));
//		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		logger.info("Exit method searchSortProposalTableSqlString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchSortProposalToExcel(SearchVendorVo svvo) {
		logger.info("Enter method searchSortProposalToExcel.");
		final String queryString = searchSortProposalToExcelSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchSortProposalToExcel.");
		return list;
	}
	
	private String searchSortProposalToExcelSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchSortProposalToExcelSqlString.");
		StringBuffer sb = new StringBuffer("select t.invoice_number , t.invoice_date , t.item_name , t.item_amount , t.payment_amount , t.dispute_amount ");
		sb.append(" from (select i.invoice_number , i.invoice_date , p.item_name , ii.item_amount , p.payment_amount , p.dispute_amount ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then (select concat(u.first_name, \" \", u.last_name) from user u where u.id = i.modified_by) ");
//		sb.append(" else (select concat(u.first_name, \" \", u.last_name) from history_db.invoice_history ih, user u where ih.id = i.id ");
//		sb.append(" and ih.invoice_status_id = 21 and ih.modified_by = u.id and u.rec_active_flag = 'Y' order by ih.modified_timestamp desc limit 1) end) as approver, ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then i.modified_by else ");
//		sb.append(" (select ih.modified_by from history_db.invoice_history ih where ih.id = i.id and ih.invoice_status_id = 21 order by ih.modified_timestamp desc limit 1) end) as approved_date ");
		sb.append(getSortProposalStatement(svvo,false));
	
//		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		logger.info("Exit method searchSortProposalToExcelSqlString.");
		return sb.toString();
	}
	
	private String getSortProposalStatement(SearchVendorVo svvo,boolean isCount){
//		StringBuffer sb = new StringBuffer(" from (select i.invoice_number , i.invoice_date , p.item_name , ii.item_amount , p.payment_amount , p.dispute_amount, ");
		StringBuffer sb = new StringBuffer(" from (proposal p , invoice_item ii ,invoice i,ban b) ");
//		sb.append(" (select concat(u.first_name, \" \", u.last_name) from history_db.invoice_history ih, user u ");
//		sb.append(" where ih.modified_by = u.id and u.rec_active_flag = 'Y' and ih.invoice_status_id = 21 and ih.bar_code = i.bar_code ");
//		sb.append(" order by ih.modified_timestamp desc limit 1) as approver, ");
//		sb.append(" (select ih.modified_timestamp from history_db.invoice_history ih, user u ");
//		sb.append(" where ih.modified_by = u.id and u.rec_active_flag = 'Y' and ih.invoice_status_id = 21 and ih.bar_code = i.bar_code ");
//		sb.append(" order by ih.modified_timestamp desc limit 1) as approved_date ");
//		sb.append(" from (proposal p , invoice_item ii ,invoice i ,cost_item c ) where i.id = ii.invoice_id and ii.id = p.invoice_item_id and ii.cost_item_id = c.id ");
//		sb.append(" from (proposal p , invoice_item ii ,invoice i ");
		
		sb.append(" where 1 = 1 ");
		sb.append(" and ii.proposal_flag = 1 and i.rec_active_flag = 'Y' and i.invoice_status_id <> 98 and p.proposal_flag=1");
		sb.append(" and ii.rec_active_flag = 'Y' and p.rec_active_flag = 'Y' ");
		sb.append(" and b.id=i.ban_id and b.rec_active_flag='Y' and b.master_ban_flag='Y' ");
		if(svvo.getBanId() != "" && svvo.getBanId() != null ){
			sb.append(" and i.ban_id = "+svvo.getBanId());
		}
		if(svvo.getVendorId() != "" && svvo.getVendorId() != null ){
			sb.append(" and b.vendor_id = "+svvo.getVendorId()+" ");
		}
		if(svvo.getCostItemId() != "" && svvo.getCostItemId() != null )
			sb.append(" and ii.stripped_circuit_number = '"+svvo.getCostItemId()+"' ");
		sb.append(" and i.id = ii.invoice_id and ii.id = p.invoice_item_id ");
		if(svvo.getItemTypeId() != "" && svvo.getItemTypeId() != null ){
			String itemTypeIds[] =  svvo.getItemTypeId().split(",");
			String itemType = "";
			for(String itemTypeId : itemTypeIds){
				if(itemTypeIds.length > 1 && itemType != ""){
					itemType += " or ii.item_type_id = 1"+itemTypeId+" or ii.item_type_id like '"+itemTypeId+"%' ";
				}else{
					itemType += " ii.item_type_id = 1"+itemTypeId+" or ii.item_type_id like '"+itemTypeId+"%' ";
				}
			}
			sb.append(" and ( "+itemType+" ) ");
		}
		if (svvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svvo.getFilter());
		}
		if(!isCount){
			sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
			sb.append(" LIMIT " + svvo.getStartIndex() + "," + svvo.getRecPerPage());			
			sb.append(" ) t ");
		}
		return sb.toString();
	}
	
	public long getSearchPaymentTotalPageNo(SearchVendorVo svvo){
		logger.info("Enter method getSearchPaymentTable.");
		final String sql = getSearchPaymentSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getSearchPaymentTable.");
		return count;
	}
	
	private String  getSearchPaymentSqlString(SearchVendorVo svvo) {
		
		logger.info("Enter method getSearchPaymenttring.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(getPaymentStatement(svvo,true));
		logger.info("Exit method getSearchPaymenttring.");
		return sb.toString();
		
	}
	
	public List<String> getSearchPaymentTable(SearchVendorVo svvo) {
		logger.info("Enter method getSearchPaymentTotalPageNo.");
		final String queryString = searchPaymentTableSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method getSearchPaymentTotalPageNo.");
		return list;
	}
	
	private String searchPaymentTableSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchPaymentTableSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("invoice_number:\"',toJSON(t.invoice_number is null,'',t.invoice_number),'\", ");
		sb.append("invoice_date:\"',toJSON(t.invoice_date is null,'',t.invoice_date),'\", ");
		sb.append("payment_amount:\"',toJSON(t.payment_amount is null,'',t.payment_amount ),'\", ");
		sb.append("payment_reference_code:\"',toJSON(t.payment_reference_code is null,'',t.payment_reference_code ),'\" ");
//		sb.append("approver:\"',toJSON(t.approver is null,'',t.approver),'\", ");
//		sb.append("approved_date:\"',toJSON(t.approved_date is null,'',t.approved_date),'\" ");
		sb.append("}') ");
		sb.append(" from (select i.invoice_number, i.invoice_date, i.payment_amount, p.payment_reference_code ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then (select concat(u.first_name, \" \", u.last_name) from user u where u.id = i.modified_by) ");
//		sb.append(" else (select concat(u.first_name, \" \", u.last_name) from history_db.invoice_history ih, user u where ih.id = i.id ");
//		sb.append(" and ih.invoice_status_id = 21 and ih.modified_by = u.id and u.rec_active_flag = 'Y' order by ih.modified_timestamp desc limit 1) end) as approver, ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then i.modified_by else ");
//		sb.append(" (select ih.modified_by from history_db.invoice_history ih where ih.id = i.id and ih.invoice_status_id = 21 order by ih.modified_timestamp desc limit 1) end) as approved_date ");
		sb.append(getPaymentStatement(svvo,false));
//		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		logger.info("Exit method searchPaymentTableSqlString.");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> searchPaymentToExcel(SearchVendorVo svvo) {
		logger.info("Enter method searchPaymentToExcel.");
		final String queryString = searchPaymentToExcelSqlString(svvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> list = (List<Object[]>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchPaymentToExcel.");
		return list;
	}
	
	private String searchPaymentToExcelSqlString(SearchVendorVo svvo) {
		logger.info("Enter method searchPaymentToExcelSqlString.");
		StringBuffer sb = new StringBuffer("select t.invoice_number, t.invoice_date, t.payment_amount, t.payment_reference_code ");
		sb.append(" from (select i.invoice_number, i.invoice_date, i.payment_amount, p.payment_reference_code ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then (select concat(u.first_name, \" \", u.last_name) from user u where u.id = i.modified_by) ");
//		sb.append(" else (select concat(u.first_name, \" \", u.last_name) from history_db.invoice_history ih, user u where ih.id = i.id ");
//		sb.append(" and ih.invoice_status_id = 21 and ih.modified_by = u.id and u.rec_active_flag = 'Y' order by ih.modified_timestamp desc limit 1) end) as approver, ");
//		sb.append(" (case when i.invoice_status_id < 21 then '' when i.invoice_status_id = 21 then i.modified_by else ");
//		sb.append(" (select ih.modified_by from history_db.invoice_history ih where ih.id = i.id and ih.invoice_status_id = 21 order by ih.modified_timestamp desc limit 1) end) as approved_date ");
		sb.append(getPaymentStatement(svvo,false));
		if (svvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svvo.getFilter());
		}
//		sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
		logger.info("Exit method searchPaymentToExcelSqlString.");
		return sb.toString();
	}
	
	private String getPaymentStatement(SearchVendorVo svvo,boolean isCount){
		StringBuffer sb = new StringBuffer(" from ((invoice i inner join ban b on i.ban_id=b.id and b.rec_active_flag = 'Y' and b.master_ban_flag='Y') left join payment p on p.invoice_id = i.id and p.rec_active_flag = 'Y') where i.rec_active_flag='Y' and i.invoice_status_id >=9 and i.invoice_status_id <>98 ");
		if(svvo.getBanId() != "" && svvo.getBanId() != null ){
			sb.append(" and i.ban_id = "+svvo.getBanId());
		}
		if(svvo.getVendorId() != "" && svvo.getVendorId() != null )
			sb.append(" and b.vendor_id = "+svvo.getVendorId()+" ");
		if (svvo.getFilter() != null) {
			sb.append(" and ");
			sb.append(svvo.getFilter());
		}
		if(!isCount){
			sb.append(" order by " + svvo.getSortField() + " " + svvo.getSortingDirection());
			sb.append(" LIMIT " + svvo.getStartIndex() + "," + svvo.getRecPerPage());			
			sb.append(" ) t ");
		}
		return sb.toString();
	}
	
	private String searchTariffWhereConditions(ContactVO cvo){
		StringBuffer sb = new StringBuffer(" from tariff_link");
		sb.append(" where tariff_link.rec_active_flag = 'Y' ");
		if(cvo.getVendor() != "" && cvo.getVendor() != null )
			sb.append(" and tariff_link.vendor_id='"+cvo.getVendor()+"'");
		if(cvo.getBan() != "" && cvo.getBan() != null )
			sb.append(" and tariff_link.ban_id='"+cvo.getBan()+"'");
		return sb.toString();
	}
	private String searchBanExemptionCertificateWhereConditions(ContactVO cvo,String invoiceDate){
		StringBuffer sb = new StringBuffer(" from ban_exemption");
		sb.append(" where ban_exemption.rec_active_flag = 'Y' ");
		if(cvo.getBan() != "" && cvo.getBan() != null ) {
			sb.append(" and ban_exemption.ban_id='"+cvo.getBan()+"'");
		}
		if (invoiceDate  != "" && invoiceDate != null ) {
			sb.append(" and ban_exemption.term_end_date >='"+invoiceDate+"'");
		}
		return sb.toString();
	}
	
	private String searchVendorContactSqlString(ContactVO cvo) {
		logger.info("Enter method searchVendorContactSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',contact.id,'\", ");
		sb.append("first_name:\"',toJSON(contact.first_name is null,'',contact.first_name),'\", ");
		sb.append("last_name:\"',toJSON(contact.last_name is null,'',contact.last_name ),'\", ");
		sb.append("attention_name:\"',toJSON(contact.attention_name is null,'',contact.attention_name ),'\", ");
		sb.append("co_name:\"',toJSON(contact.co_name is null,'',contact.co_name ),'\", ");
		sb.append("address_1:\"',toJSON(contact.address_1 is null,'',contact.address_1 ),'\", ");
		sb.append("address_2:\"',toJSON(contact.address_2 is null,'',contact.address_2 ),'\", ");
		sb.append("city:\"',toJSON(contact.city is null,'',contact.city ),'\", ");
		sb.append("province:\"',toJSON(contact.province is null,'',contact.province ),'\", ");
		sb.append("country:\"',toJSON(contact.country is null,'',contact.country ),'\", ");
		sb.append("postal_code:\"',toJSON(contact.postal_code is null,'',contact.postal_code ),'\", ");
		sb.append("primary_phone:\"',toJSON(contact.primary_phone is null,'',contact.primary_phone ),'\", ");
		sb.append("other_phone:\"',toJSON(contact.other_phone is null,'',contact.other_phone ),'\", ");
		sb.append("office_phone:\"',toJSON(contact.office_phone is null,'',contact.office_phone ),'\", ");
		sb.append("cell_phone:\"',toJSON(contact.cell_phone is null,'',contact.cell_phone ),'\", ");
		sb.append("fax_number:\"',toJSON(contact.fax_number is null,'',contact.fax_number ),'\", ");
		sb.append("email:\"',toJSON(contact.email is null,'',contact.email),'\" ");
		sb.append("}')  from contact ");
		sb.append("where contact.vendor_id =" + cvo.getVendor() + "");
		sb.append(" and contact.rec_active_flag =  'Y'  ");
		sb.append(" order by " + cvo.getSortField() + " " + cvo.getSortingDirection());
		sb.append(" LIMIT " + cvo.getStartIndex() + "," + cvo.getRecPerPage());
		logger.info("Exit method searchVendorContactSqlString.");
		return sb.toString();
	}
	
	public List contactList(ContactVO cvo) {
		logger.info("Enter method contactList.");
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" contact.first_name , ");
		sb.append(" contact.last_name , ");
		sb.append(" contact.attention_name , ");
		sb.append(" contact.co_name , ");
		sb.append(" contact.address_1 , ");
		sb.append(" contact.address_2 , ");
		sb.append(" contact.city , ");
		sb.append(" contact.province , ");
		sb.append(" contact.country , ");
		sb.append(" contact.postal_code , ");
		sb.append(" contact.primary_phone , ");
		sb.append(" contact.other_phone , ");
		sb.append(" contact.office_phone , ");
		sb.append(" contact.cell_phone , ");
		sb.append(" contact.fax_number , ");
		sb.append(" contact.email  ");
		sb.append(" FROM contact ");
		sb.append(" where contact.rec_active_flag =  'Y' ");
		sb.append(" and contact.vendor_id =" + cvo.getVendor() + "");
		sb.append(cvo.getOrderByCause(null));
		sb.append(cvo.getLimitCause());
		Session session = getSession();
		try {
			List re = session.createSQLQuery(sb.toString()).list();
			logger.info("Exit method contactList.");
			return re;
		} finally {
			releaseSession(session);
		}
	}
	
	public Integer findMaxId() {
		Session session = getSession();
		try {
			return (Integer)session.createSQLQuery("select max(v.id) from vendor v").uniqueResult();
		} finally {
			releaseSession(session);
		}
	}

	public void deleteVendor(Integer id) {
		logger.info("Enter method deleteVendor.");
		final String queryString = deleteVendorQueryString(id);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method deleteVendor.");
	}
	private String deleteVendorQueryString(Integer id) {
		logger.info("Enter method deleteContactQueryString.");
		StringBuffer sb = new StringBuffer("update vendor set ");
		sb.append(" vendor.rec_active_flag = 'N' ");
		sb.append(" where id = " + id + " ");
		logger.info("Exit method deleteContactQueryString.");
		return sb.toString();
	}	

}
