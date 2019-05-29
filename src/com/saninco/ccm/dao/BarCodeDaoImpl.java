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

import com.saninco.ccm.model.BarCode;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author Joe.Yang
 *
 */
public class BarCodeDaoImpl extends HibernateDaoSupport implements IBarCodeDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	// property constants
	/**
	 * 
	 */
	public BarCodeDaoImpl() {
	}
	
	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IBanDao1#findById(java.lang.Integer)
	 */
	public BarCode findById(java.lang.Integer id) {
		logger.debug("getting BarCode instance with id: " + id);
		try {
			BarCode instance = (BarCode) getHibernateTemplate().get(
					"com.saninco.ccm.model.BarCode", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	
	public List findByProperty(String propertyName, Object value) {
		logger.debug("finding BarCode instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BarCode as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<String> findBarCodeToString(String barCode){
		logger.info("Enter method findBarCodeToString.");
		final String sql = this.findBarCodeToStringQueryString(barCode);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method findBarCodeToString.");
		return l;
	}
	
	private String findBarCodeToStringQueryString(String barCode) {
		logger.info("Exit method findBarCodeToStringQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',bc.id,");
		sb.append("',vendor:\"',if(bc.ban_id is null,'',v.vendor_name),");
		sb.append("'\",ban:\"',if(bc.ban_id is null,'',b.account_number),");
		sb.append("'\",invoiceDate:\"',if(bc.invoice_date is null,'',bc.invoice_date),");
		sb.append("'\",invoiceDueDate:\"',if(isv.invoice_due_date is null,'',isv.invoice_due_date),");
		sb.append("'\",currentDue:\"',if(isv.invoice_current_due is null,'',isv.invoice_current_due),");
		sb.append("'\",totalDue:\"',if(isv.invoice_total_due is null,'',isv.invoice_total_due),");
		sb.append("'\"}') a ");
		sb.append(" from (((bar_code bc left join ban b on (bc.ban_id = b.id)) left join vendor v on (b.vendor_id = v.id)) ");
		sb.append(" left join invoice_st_v isv on (isv.rec_active_flag = 'Y' and isv.bar_code = '" + barCode + "' )) ");
		sb.append(" where bc.rec_active_flag = 'Y' ");
		sb.append(" and bc.bar_code = '" + barCode + "' ");
		logger.info("Exit method findBarCodeToStringQueryString.");
		return sb.toString();
	}


}
