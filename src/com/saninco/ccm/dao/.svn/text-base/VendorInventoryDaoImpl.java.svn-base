package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.vo.SearchVendorInventoryVO;

public class VendorInventoryDaoImpl extends HibernateDaoSupport implements IVendorInventoryDao {
	public List<Object[]> getConditionProductList() {
		logger.info("Enter method getConditionProductList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("SELECT vp.id, vp.product_name FROM vi_product vp WHERE vp.rec_active_flag = 'Y'").list();
					}
				});
		logger.info("Exit method getConditionProductList.");
		return l;
	}
	
	public List<Object[]> getConditionAccessList() {
		logger.info("Enter method getConditionAccessList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("select va.id, va.access_name from vi_access va where va.rec_active_flag = 'Y'").list();
					}
				});
		logger.info("Exit method getConditionAccessList.");
		return l;
	}
	
	public List<Object[]> getConditionBandwidthList() {
		logger.info("Enter method getConditionBandwidthList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("SELECT vb.id, vb.bandwidth_name FROM vi_bandwidth vb WHERE vb.rec_active_flag = 'Y'").list();
					}
				});
		logger.info("Exit method getConditionBandwidthList.");
		return l;
	}
	
	public List<Object[]> getConditionQosList() {
		logger.info("Enter method getConditionQosList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("SELECT vq.id, vq.qos_name FROM vi_qos vq WHERE vq.rec_active_flag = 'Y'").list();
					}
				});
		logger.info("Exit method getConditionQosList.");
		return l;
	}
	public List<Object[]> getConditionCountryList() {
		logger.info("Enter method getConditionCountryList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("SELECT c.id,c.country_name FROM country c WHERE c.rec_active_flag = 'Y'").list();
					}
				});
		logger.info("Exit method getConditionCountryList.");
		return l;
	}
	public List<Object[]> getConditionProvinceList(int cId){
		logger.info("Enter method getConditionProvinceList.");
		final int cId2 = cId;
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("SELECT p.id,p.province_name FROM province p WHERE p.rec_active_flag = 'Y' and p.country_id = "+cId2).list();
					}
				});
		logger.info("Exit method getConditionProvinceList.");
		return l;
	}
	
	public List<Object[]> getListByCityId(int pId){
		logger.info("Enter method getListByCityId.");
		final int pId2 = pId;
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("SELECT c.id,c.city_name FROM city c WHERE c.rec_active_flag = 'Y' and c.province_id = "+pId2).list();
					}
				});
		logger.info("Exit method getListByCityId.");
		return l;
	}
	public List<Object[]> getConditionTermList() {
		logger.info("Enter method getConditionTermList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("SELECT vt.id, vt.term_name FROM vi_term vt WHERE vt.rec_active_flag = 'Y'").list();
					}
				});
		logger.info("Exit method getConditionTermList.");
		return l;
	}
	
	public long getNumberOfVendorInventory(SearchVendorInventoryVO sv) {
		logger.info("Enter method getNumberOfVendorInventory.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(getVendorInventorySqlConditions(sv, true));
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
		logger.info("Exit method getNumberOfVendorInventory.");
		return count;
	}
	
	public List<Object[]> searchVendorInventory(SearchVendorInventoryVO sv) {
		logger.info("Enter method searchVendorInventory.");
		final String sql = this.getVendorInventorySql(sv);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchVendorInventory.");
		return l;
	}
	
	private String getVendorInventorySql(SearchVendorInventoryVO sv) {
		StringBuffer sb = new StringBuffer();
	    sb.append("SELECT CONVERT(concat('{id:\"',vi.id,");
	    sb.append("'\",quoteNumber:\"',toJSON(vi.quote_number IS NULL, '', vi.quote_number),");
	    sb.append("'\",vendorId:\"',vi.vendor_id,");
        sb.append("'\",vendorName:\"',toJSON(v.vendor_name IS NULL, '', v.vendor_name),");
        sb.append("'\",productId:\"',vi.product_id,");
        sb.append("'\",productName:\"',toJSON(vp.product_name IS NULL, '', vp.product_name),");
        sb.append("'\",accessId:\"',vi.access_id,");
        sb.append("'\",accessName:\"',toJSON(va.access_name IS NULL, '', va.access_name),");
        sb.append("'\",bandwidthId:\"',vi.bandwidth_id,");
        sb.append("'\",bandwidthName:\"',toJSON(vb.bandwidth_name IS NULL, '', vb.bandwidth_name),");
        sb.append("'\",qosId:\"',vi.qos_id,");
        sb.append("'\",qosName:\"',toJSON(vq.qos_name IS NULL, '', vq.qos_name),");
        sb.append("'\",dataIssued:\"',toJSON(vi.date_issued IS NULL, '', vi.date_issued),");
        sb.append("'\",expiryDate:\"',toJSON(vi.expiry_date IS NULL, '', vi.expiry_date),");
        sb.append("'\",mrc:\"',toJSON(vi.mrc IS NULL, '', vi.mrc),");
        sb.append("'\",nrc:\"',toJSON(vi.nrc IS NULL, '', vi.nrc),");
        sb.append("'\",constructionCost:\"',toJSON(vi.construction_cost IS NULL, '', vi.construction_cost),");
        sb.append("'\",termId:\"',vi.term_id,");
        sb.append("'\",termName:\"',toJSON(vt.term_name IS NULL, '', vt.term_name),");
        sb.append("'\",unit:\"',toJSON(vi.unit IS NULL, '', vi.unit),");
        sb.append("'\",streetNumber:\"',toJSON(vi.street_number IS NULL, '', vi.street_number),");
        sb.append("'\",streetName:\"',toJSON(vi.street_name IS NULL, '', vi.street_name),");
        sb.append("'\",city:\"',toJSON(vi.city IS NULL, '', vi.city),");
        sb.append("'\",postalCode:\"',toJSON(vi.postal_code IS NULL, '', vi.postal_code),");
        sb.append("'\",province:\"',toJSON(vi.province IS NULL, '', vi.province),");
        sb.append("'\",country:\"',toJSON(vi.country IS NULL, '', vi.country),");
        sb.append("'\",latitude:\"',toJSON(vi.latitude IS NULL, '', vi.latitude),");
        sb.append("'\",logitude:\"',toJSON(vi.longitude IS NULL, '', vi.longitude),");
        sb.append("'\",opportunityName:\"',toJSON(vi.opportunity_name IS NULL, '', vi.opportunity_name),");
        sb.append("'\"}')USING utf8) AS a");
        sb.append(getVendorInventorySqlConditions(sv, true));
        sb.append(sv.getOrderByCause(null) + " ");
		sb.append(sv.getLimitCause() + " ");
	    return sb.toString();
	}
	
	public List<Object[]> getVendorInventoryDataForExcel(SearchVendorInventoryVO sv) {
		logger.info("Enter method getVendorInventoryDataForExcel.");
		final String sql = this.getVendorInventoryForExcelSql(sv);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getVendorInventoryDataForExcel.");
		return l;
	}
	
	public List<Map> getVendorInventoryDataForLetter(SearchVendorInventoryVO sv) {
		logger.info("Enter method getVendorInventoryDataForLetter.");
		final String sql = this.getVendorInventoryForExcelSql(sv);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		List<Map> ll = new ArrayList<Map>();
		for (int i = 0; i < l.size(); i++) {
			Map map = new HashMap();
			map.put("id", l.get(i)[0]);
			map.put("quote_number", l.get(i)[1]);
			map.put("vendor_name", l.get(i)[2]);
			map.put("product_name", l.get(i)[3]);
			map.put("access_name", l.get(i)[4]);
			map.put("bandwidth_name", l.get(i)[5]);
			map.put("qos_name", l.get(i)[6]);
			map.put("date_issued", l.get(i)[7]);
			map.put("expiry_date", l.get(i)[8]);
			map.put("mrc", l.get(i)[9]);
			map.put("nrc", l.get(i)[10]);
			map.put("construction_cost", l.get(i)[11]);
			map.put("term_name", l.get(i)[12]);
			map.put("unit", l.get(i)[13]);
			map.put("street_number", l.get(i)[14]);
			map.put("street_name", l.get(i)[15]);
			map.put("city", l.get(i)[16]);
			map.put("postal_code", l.get(i)[17]);
			map.put("province", l.get(i)[18]);
			map.put("country", l.get(i)[19]);
			map.put("latitude", l.get(i)[20]);
			map.put("longitude", l.get(i)[21]);
			map.put("opportunity_name", l.get(i)[22]);
			ll.add(map);
		}
		logger.info("Exit method getVendorInventoryDataForLetter.");
		return ll;
	}
	
	private String getVendorInventoryForExcelSql(SearchVendorInventoryVO sv) {
		StringBuffer sb = new StringBuffer();
	    sb.append("SELECT vi.id,");
	    sb.append("toJSON(vi.quote_number IS NULL, '', vi.quote_number),");
        sb.append("toJSON(v.vendor_name IS NULL, '', v.vendor_name),");
        sb.append("toJSON(vp.product_name IS NULL, '', vp.product_name),");
        sb.append("toJSON(va.access_name IS NULL, '', va.access_name),");
        sb.append("toJSON(vb.bandwidth_name IS NULL, '', vb.bandwidth_name),");
        sb.append("toJSON(vq.qos_name IS NULL, '', vq.qos_name),");
        sb.append("if(vi.date_issued IS NULL, '', vi.date_issued),");
        sb.append("if(vi.expiry_date IS NULL, '', vi.expiry_date),");
        sb.append("if(vi.mrc IS NULL, '', cast(round(vi.mrc,2)   as   DECIMAL(20,2))),");
        sb.append("if(vi.nrc IS NULL, '', cast(round(vi.nrc,2)   as   DECIMAL(20,2))),");
        sb.append("if(vi.construction_cost IS NULL, '', cast(round(vi.construction_cost,2)   as   DECIMAL(20,2))),");
        sb.append("toJSON(vt.term_name IS NULL, '', vt.term_name),");
        sb.append("toJSON(vi.unit IS NULL, '', vi.unit),");
        sb.append("toJSON(vi.street_number IS NULL, '', vi.street_number),");
        sb.append("toJSON(vi.street_name IS NULL, '', vi.street_name),");
        sb.append("toJSON(vi.city IS NULL, '', vi.city),");
        sb.append("toJSON(vi.postal_code IS NULL, '', vi.postal_code),");
        sb.append("toJSON(vi.province IS NULL, '', vi.province),");
        sb.append("toJSON(vi.country IS NULL, '', vi.country),");
        sb.append("if(vi.latitude IS NULL, '', cast(round(vi.latitude,2)   as   DECIMAL(20,2))),");
        sb.append("if(vi.longitude IS NULL, '', cast(round(vi.longitude,2)   as   DECIMAL(20,2))),");
        sb.append("toJSON(vi.opportunity_name IS NULL, '', vi.opportunity_name)");
        sb.append(getVendorInventorySqlConditions(sv, true));
        sb.append(sv.getOrderByCause(null) + " ");
	//	sb.append(sv.getLimitCause() + " ");
	    return sb.toString();
	}
	
	private String getVendorInventorySqlConditions(SearchVendorInventoryVO sv, boolean doFilter) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM vendor_inventory vi ");
		sb.append(" LEFT JOIN vendor v ON vi.vendor_id = v.id");
        sb.append(" LEFT JOIN vi_access va ON vi.access_id = va.id");
        sb.append(" LEFT JOIN vi_bandwidth vb ON vi.bandwidth_id = vb.id");
        sb.append(" LEFT JOIN vi_product vp ON vi.product_id = vp.id");
        sb.append(" LEFT JOIN vi_qos vq ON vi.qos_id = vq.id");
        sb.append(" LEFT JOIN vi_term vt ON vi.term_id = vt.id WHERE vi.rec_active_flag = 'Y'");
        if (sv != null) {
        	if (sv.getAccessId() != null) {
            	sb.append(" AND vi.access_id = " + sv.getAccessId());
            }
        	if (sv.getBandwidthId() != null) {
            	sb.append(" AND vi.bandwidth_id = " + sv.getBandwidthId());
            }
    		if (sv.getCity() != null) {
    			sb.append(" AND vi.city = '" + sv.getCity() + "'");
    		}
    		if (sv.getConstructionCostFrom() != null) {
    			sb.append(" AND vi.construction_cost >= " + sv.getConstructionCostFrom());
    		}
    		if (sv.getConstructionCostTo() != null) {
    			sb.append(" AND vi.construction_cost <= " + sv.getConstructionCostTo());
    		}
    		if (sv.getCountry() != null) {
    			sb.append(" AND vi.country = '" + sv.getCountry() + "'");
    		}
    		if (sv.getDateIssuedFrom() != null) {
    			sb.append(" AND vi.date_issued >= " + sv.getDateIssuedFrom());
    		}
    		if (sv.getDateIssuedTo() != null) {
    			sb.append(" AND vi.date_issued <= " + sv.getDateIssuedTo());
    		}
    		if (sv.getExpiryDateFrom() != null) {
    			sb.append(" AND vi.expiry_date >= " + sv.getExpiryDateFrom());
    		}
    		if (sv.getExpiryDateTo() != null) {
    			sb.append(" AND vi.expiry_date <= " + sv.getExpiryDateTo());
    		}
    		if (sv.getInventoryId() != null) {
    			sb.append(" AND vi.id = " + sv.getInventoryId());
    		}
    		if (sv.getLatitudeFrom() != null) {
    			sb.append(" AND vi.latitude >= " + sv.getLatitudeFrom());
    		}
    		if (sv.getLatitudeTo() != null) {
    			sb.append(" AND vi.latitude <= " + sv.getLatitudeTo());
    		}
    		if (sv.getLongitudeFrom() != null) {
    			sb.append(" AND vi.longitude >= " + sv.getLongitudeFrom());
    		}
    		if (sv.getLongitudeTo() != null) {
    			sb.append(" AND vi.longitude <= " + sv.getLongitudeTo());
    		}
    		if (sv.getMrcFrom() != null) {
    			sb.append(" AND vi.mrc >= " + sv.getMrcFrom());
    		}
    		if (sv.getMrcTo() != null) {
    			sb.append(" AND vi.mrc <= " + sv.getMrcTo());
    		}
    		if (sv.getNrcFrom() != null) {
    			sb.append(" AND vi.nrc >= " + sv.getNrcFrom());
    		}
    		if (sv.getNrcTo() != null) {
    			sb.append(" AND vi.nrc <= " + sv.getNrcTo());
    		}
    		if (sv.getOpportunityName() != null) {
    			sb.append(" AND vi.opportunity_name like '%" + sv.getOpportunityName() +"%'");
    		}
    		if (sv.getPostalCode() != null) {
    			sb.append(" AND vi.postal_code = '" + sv.getPostalCode() + "'");
    		}
    		if (sv.getProductId() != null) {
    			sb.append(" AND vi.product_id = " + sv.getProductId());
    		}
    		if (sv.getProvince() != null) {
    			sb.append(" AND vi.province = '" + sv.getProvince() + "'");
    		}
    		if (sv.getQosId() != null) {
    			sb.append(" AND vi.qos_id = " + sv.getQosId());
    		}
    		if (sv.getQuoteNumber() != null) {
    			sb.append(" AND vi.quote_number = '" + sv.getQuoteNumber() + "'");
    		}
    		if (sv.getStreetName() != null) {
    			sb.append(" AND vi.street_name like '%" + sv.getStreetName() + "%'");
    		}
    		if (sv.getStreetNumber() != null) {
    			sb.append(" AND vi.street_number = '" + sv.getStreetNumber() + "'");
    		}
    		if (sv.getTermId() != null) {
    			sb.append(" AND vi.term_id = " + sv.getTermId());
    		}
    		if (sv.getUnit() != null) {
    			sb.append(" AND vi.unit = '" + sv.getUnit() + "'");
    		}
    		if (sv.getVendorId() != null) {
    			sb.append(" AND vi.vendor_id = " + sv.getVendorId());
    		}
    		if (doFilter) {
    			if (sv.getFilter() != null) {
    				sb.append(" and ");
    				sb.append(sv.getFilter());
    			}
    		}
        }
		return sb.toString();
	}
	
}
