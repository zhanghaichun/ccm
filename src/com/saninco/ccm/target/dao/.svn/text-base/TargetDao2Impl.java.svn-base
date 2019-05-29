package com.saninco.ccm.target.dao;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Target;
import com.saninco.ccm.model.VendorCircuit;
import com.saninco.ccm.util.SystemUtil;


public class TargetDao2Impl extends HibernateDaoSupport implements ITargetDao2 {

	public Object modifyObject(String name, String type) {
		if("Vendor".equals(type)){
			String hql=" from Vendor where vendorName='"+name+"'";
			List<Object> list =this.getHibernateTemplate().find(hql);
			if(list.size()>0&&list!=null){
				return list.get(0);
			}
		}
		if("Ban".equals(type)){
			List<Object> list=this.getHibernateTemplate().find("  from Ban  where account_number='"+name+"'");
			if(list.size()>0&&list!=null){
				return list.get(0);
			}
		}
		
		if("CharyType".equals(type)){
			List<Object> list=this.getHibernateTemplate().find("  from TargetChargeType  where chargeTypeName='"+name+"'");
			if(list.size()>0&&list!=null){
				return list.get(0);
			}
		}
		return null;
	}

	public List getBanIdByVendorId(Integer vendorId) {
		Session session = getSession();
		try {
			List l = session.createSQLQuery(
							"select b.id as id,b.account_number as name from ban b where b.rec_active_flag = 'Y' and b.ban_status_id = 1 and b.master_ban_flag='Y' and b.vendor_id = "
									+ vendorId
									+ " order by b.account_number asc ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List getCircuitList(Integer banId) {
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select vc.id,vc.stripped_circuit_number from vendor_circuit vc "
						+"where vc.rec_active_flag='Y'"
							+"and vc.ban_id="+banId+ 
									" and ifnull(vc.stripped_circuit_number,'') <> '' ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List getCircuitListByBanVendor(Integer banId, Integer vendorId) {
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select vc.id,vc.stripped_circuit_number from vendor_circuit vc "
						+"where vc.rec_active_flag='Y'"
							+"and vc.ban_id="+banId
							+"  and vc.vendor_id="+vendorId+ 
									" and ifnull(vc.stripped_circuit_number,'') <> '' ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public Map<String, Object> getProcedureVerfication(String targets) throws SQLException {
		Session session =getSession();
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Connection con=session.connection();
			con.setReadOnly(false);
			CallableStatement proc = null;
			proc = con.prepareCall("call SP_Verfication_Process(?,?)");
			proc.setString(1, targets);
			proc.registerOutParameter(2, Types.VARCHAR); 

			proc.execute();
			String returnresult = proc.getString(2);
			
			result.put("returnresult", returnresult);
			
			
			return result;
		}finally{
			releaseSession(session);
		}
	}

	public Integer saveTarget(Target target) throws BPLException {
		target.setCreatedBy(SystemUtil.getCurrentUserId());
		target.setCreatedTimestamp(new Date());
		target.setModifiedBy(SystemUtil.getCurrentUserId());
		target.setModifiedTimestamp(new Date());
		target.setRecActiveFlag("Y");
		int pk = (Integer) this.getHibernateTemplate().save(target);
		return pk;
		
	}

	public Integer updateTarget(Target target) throws BPLException {
		target.setModifiedBy(SystemUtil.getCurrentUserId());
		target.setModifiedTimestamp(new Date());
		target.setRecActiveFlag("Y");
		getHibernateTemplate().update(target);
		return null;
	}

}
