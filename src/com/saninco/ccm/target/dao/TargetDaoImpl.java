package com.saninco.ccm.target.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Target;
import com.saninco.ccm.model.TargetChargeType;
import com.saninco.ccm.model.TargetResult;
import com.saninco.ccm.target.model.SearchTargetVO;
import com.saninco.ccm.util.SystemUtil;

public class TargetDaoImpl extends HibernateDaoSupport implements ITargetDao{

	public List<String> searchTarget(SearchTargetVO searchTargetVO,
			Integer currentUserId) {
		
		final String sql = this.getTargetSearchQueryString(searchTargetVO, currentUserId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		return l;
	}
	
	private String getTargetSearchQueryString(SearchTargetVO sio, int userId){
		
		StringBuffer sb = new StringBuffer("select concat('{id:',t.id,',vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),");
		sb.append("'\",circuit_number:\"',if(t.stripped_circuit_number is null,'',t.stripped_circuit_number),");
		sb.append("'\",charge_type_name:\"',if(tc.charge_type_name is null,'',tc.charge_type_name),");
		sb.append("'\",target_amount:\"',if(t.target_amount is null,'',t.target_amount),");
		sb.append("'\",change_amount:\"',if(t.change_amount is null,'',t.change_amount),");
		sb.append("'\",change_percentage:\"',if(t.change_percentage is null,'',concat(t.change_percentage,'%')),");
		sb.append("'\",variation_percentage:\"',if(t.allowed_variation_percentage is null,'',concat(t.allowed_variation_percentage,'%')),");
		sb.append("'\",targetPeriod:\"',if(concat(t.period_from,'-',t.period_to) is null,'',concat(t.period_from,'-',t.period_to)),");
		sb.append("'\",allowed_period_shift:\"',if(t.allowed_period_shift is null,'',t.allowed_period_shift),");
		sb.append("'\",created_by:\"',concat(ifnull(u.first_name,''),' ',ifnull(u.last_name,'')) ,");
		sb.append("'\",created_timestamp:\"',ifnull(t.created_timestamp,'') ,");
		sb.append("'\"");
		sb.append("}') a ");
		
		sb.append(" from (target t left join user u on t.created_by=u.id) left join target_charge_type tc on t.charge_type_id = tc.id left join vendor v on v.id = t.vendor_id left join ban b  on b.id = t.ban_id ");
		
		sb.append(voWhereConditions(sio,userId,null));
		
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		return sb.toString();
	}
	
	private String voWhereConditions(SearchTargetVO sio, int userId ,List<Integer> targetIds) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" where t.rec_active_flag='Y' ");
		
		
		if(sio.getFilter()!=null){
			sb.append(" and ");
			sb.append(sio.getFilter());
		}
		if(sio.getVendorId()!=null) {
			sb.append(" and t.vendor_id="+sio.getVendorId()+" ");
			if(sio.getBanId()!=null) sb.append(" and t.ban_id in ("+sio.getBanId()+") ");
		}else{
			if(sio.getBanId()!=null) sb.append(" and t.ban_id in ("+sio.getBanId()+") ");
		}
		if(targetIds !=null) {
		for(int i=0;i<targetIds.size();i++){
			
		if(i==0){
			sb.append(" and ");
		}else{
			sb.append(" or ");
		}
			sb.append("t.id = " + targetIds.get(i));
			}
		}
		
		if(sio.getCircuitNumber()!=null){
			sb.append(" and t.stripped_circuit_number='"+sio.getCircuitNumber()+"' ");
		}
		
		if(sio.getChargeTypeSelect()!=null){
			sb.append(" and t.charge_type_id='"+sio.getChargeTypeSelect()+"' ");
		}
		
		if(sio.getvPercent()!=null){
			sb.append(" and t.allowed_variation_percentage="+sio.getvPercent()+" ");
		}
		
		if(sio.getChangeAmount()!=null){
			sb.append(" and t.change_amount="+sio.getChangeAmount()+" ");
		}
		if(sio.getTargetAmount()!=null){
			sb.append(" and t.target_amount="+sio.getTargetAmount()+" ");
		}
		if(sio.getChangePercentage()!=null){
			sb.append(" and t.change_percentage="+sio.getChangePercentage()+" ");
		}
		if(sio.getTargetId() != null){
			sb.append(" and t.id="+sio.getTargetId() );
		}
		if(sio.getTargetPeriodStartsOn() != null){
			sb.append(" and t.period_from >="+sio.getTargetPeriodStartsOn()  );
		}
		if(sio.getTargetPeriodEndsOn() != null){
			sb.append(" and t.period_to  <="+sio.getTargetPeriodEndsOn()  );
		}
		if(sio.getCreatedDateStartsOn() != null){
			sb.append(" and DATE_FORMAT(t.created_timestamp,'%Y/%m/%d')  >='"+sio.getCreatedDateStartsOn()+"'");
		}
		if(sio.getCreatedEndsOn() != null){
			sb.append(" and DATE_FORMAT(t.created_timestamp,'%Y/%m/%d')  <='"+sio.getCreatedEndsOn()+"'" );
		}
		if(sio.getCreatedBy() != null){
			sb.append(" and t.created_by ="+sio.getCreatedBy()+"" );
		}
		return sb.toString();
	}

	public List getCircuitList(Integer currentUserId, Integer banId) {
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select vc.id,vc.stripped_circuit_number from vendor_circuit vc "
						+"where vc.rec_active_flag='Y'"
							+"and vc.ban_id="+banId+ 
									" and ifnull(vc.stripped_circuit_number,'') <> '' "
							        +"  order by vc.stripped_circuit_number asc").list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List<TargetChargeType> chargeTypeList() {
		logger.info("Enter method chargeTypeList");
		List<TargetChargeType> l = (List<TargetChargeType>)this.getHibernateTemplate().find("from TargetChargeType");
		logger.info("Exit method chargeTypeList");
		return l;
	}

	public long getTragetSearchTotalPageNo(SearchTargetVO searchTargetVO,
			Integer currentUserId) {
		logger.info("Enter method getTragetSearchTotalPageNo.");
		final String sql = this.getTragetSearchTotalPageNoQueryString(searchTargetVO,currentUserId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getTragetSearchTotalPageNo.");
		return count;
	}

	private String getTragetSearchTotalPageNoQueryString(
			SearchTargetVO searchTargetVO,Integer currentUserId) {
		logger.info("Enter method getInvoiceSearchNumberQueryString.");
		//Example {id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",
		//date:\"2009/06/18\",due:\"2009/07/18\",total:\"$1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from target t left join target_charge_type tc on t.charge_type_id = tc.id left join vendor v on v.id = t.vendor_id left join ban b  on b.id = t.ban_id ");
	
		sb.append(voWhereConditions(searchTargetVO,currentUserId,null));
		logger.info("Exit method getInvoiceSearchNumberQueryString.");
		return sb.toString();
	}

	public List<Object[]> searchTargetByObject(SearchTargetVO searchTargetVO,
			Integer currentUserId,List<Integer> targetIds) {
		final String sql = this.getInvoiceSearchByObjectQueryString(searchTargetVO, currentUserId,targetIds);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchInvoiceByObject.");
		return l;
	}
	
	private String getInvoiceSearchByObjectQueryString(SearchTargetVO sio, int currentUserId,List<Integer> targetIds){
		logger.info("Enter method getInvoiceSearchByObjectQueryString.");
		StringBuffer sb = new StringBuffer("SELECT v.vendor_name, b.account_number, t.stripped_circuit_number,tc.charge_type_name,t.target_amount,t.change_amount,t.change_percentage,t.allowed_variation_percentage,concat(t.period_from,'-',t.period_to),t.allowed_period_shift,if(t.created_by is null,' ',(select concat(first_name,' ',last_name) from user where id=t.created_by)),t.created_timestamp");
		sb.append(" from target t left join target_charge_type tc on t.charge_type_id = tc.id left join vendor v on v.id = t.vendor_id left join ban b  on b.id = t.ban_id ");
		sb.append(voWhereConditions(sio,currentUserId,targetIds));
		sb.append(sio.getOrderByCause(null) + " ");
		sb.append(sio.getLimitCause() + " ");
		logger.info("Exit method getInvoiceSearchByObjectQueryString.");
		return sb.toString();
	}

	public Target saveTarget(Target target,int type) {
		logger.info("Enter method saveTarget.");
		Session session=null;
		Integer targetId=null;
		Date date = new Date();
		try {
			 session = this.getSession();
			 String sql = " from target t where  if("+target.getVendorId()+" is null,t.vendor_id is null,t.vendor_id ="+target.getVendorId()+")  " +
		 		" and if("+target.getBanId()+" is null,t.ban_id is null,t.ban_id="+target.getBanId()+") " +
		 		" and if("+target.getStrippedCircuitNumber()+" is null,t.stripped_circuit_number is null,t.stripped_circuit_number="+target.getStrippedCircuitNumber()+")  " +
		 		" and if("+target.getChargeTypeId()+" is null ,t.charge_type_id is null,t.charge_type_id="+target.getChargeTypeId()+") " +
		 		" and if("+target.getPeriodFrom()+" is null,t.period_from is null,t.period_from="+target.getPeriodFrom()+") " +
		 		" and if("+target.getPeriodTo()+" is null,t.period_to is null,t.period_to="+target.getPeriodTo()+") " +
		 	    " and if("+target.getAllowedPeriodShift()+" is null,t.allowed_period_shift is null ,t.allowed_period_shift="+target.getAllowedPeriodShift()+")" +
		 	    " and if("+target.getTargetAmount()+" is null,t.target_amount is null,t.target_amount="+target.getTargetAmount()+") " +
		 	    " and if("+target.getChangeAmount()+" is null,t.change_amount is null,t.change_amount="+target.getChangeAmount()+")" +
		 	    " and if("+target.getChangePercentage()+" is null,t.change_percentage is null ,t.change_percentage="+target.getChangePercentage()+") " +
		 	    " and if("+target.getAllowedVariationPercentage()+" is null,t.allowed_variation_percentage is null,t.allowed_variation_percentage="+target.getAllowedVariationPercentage()+") ";
			 targetId =(Integer)session.createSQLQuery("select t.id"+sql).uniqueResult();
			 date = (Date)session.createSQLQuery("select t.created_timestamp"+sql).uniqueResult();
		}finally{
			releaseSession(session);
		}
		if(null!=targetId){
			target.setId(targetId);
			target.setCreatedBy(SystemUtil.getCurrentUserId());
			target.setCreatedTimestamp(date);
			target.setModifiedBy(SystemUtil.getCurrentUserId());
			target.setModifiedTimestamp(new Date());
			target.setRecActiveFlag("Y");
			getHibernateTemplate().saveOrUpdate(target);
			if(3==type){
				Session session2=null;
				try {
					session2=this.getSession();
					session2.createSQLQuery("update target_result tr set tr.rec_active_flag='N' where target_id = "+targetId).executeUpdate();
				}finally{
					releaseSession(session);
				}
			}
		}else {
			target.setCreatedBy(SystemUtil.getCurrentUserId());
			target.setCreatedTimestamp(new Date());
			target.setModifiedBy(SystemUtil.getCurrentUserId());
			target.setModifiedTimestamp(new Date());
			target.setRecActiveFlag("Y");
			getHibernateTemplate().saveOrUpdate(target);
		}
		logger.info("Exit method saveTarget.");
		return target;
	}
	
	public void save(Object o){
		this.getHibernateTemplate().saveOrUpdate(o);
	}

	public List<String> queryCopyTargetList(List<Target> targetList) {
		final String sql = this.getQueryCopyTargetListString(targetList);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		return l;
	}

	private String getQueryCopyTargetListString(List<Target> targetList) {
		StringBuffer sb = new StringBuffer("SELECT concat('{id:', t.id,',vendorId:\"',toJSON(t.vendor_id IS NULL,'', t.vendor_id),");
		sb.append("'\",banId:\"', toJSON(t.ban_id IS NULL,'',t.ban_id),");
		         sb.append("'\",circuit_number:\"',if(t.stripped_circuit_number IS NULL,'',t.stripped_circuit_number),");
		         sb.append("'\",charge_type_id:\"',if(t.charge_type_id IS NULL,'',t.charge_type_id),");
		         sb.append("'\",target_amount:\"',if(t.target_amount IS NULL,'',t.target_amount),");
		         sb.append("'\",change_amount:\"',if( t.change_amount IS NULL,'',t.change_amount),");
		         sb.append("'\",change_percentage:\"',if( t.change_percentage IS NULL,'',t.change_percentage),");
		         sb.append("'\",variation_percentage:\"',if(t.allowed_variation_percentage IS NULL,'',t.allowed_variation_percentage),");
		         sb.append("'\",period_from:\"',if(t.period_from is null, '',t.period_from),");
		         sb.append("'\",period_to:\"',if(t.period_to is null,'',t.period_to),");
		         sb.append("'\",allowed_period_shift:\"',if(t.allowed_period_shift is null,'',t.allowed_period_shift),");
		         sb.append("'\"}')");
	sb.append(" a");
		sb.append(" FROM target t");
		sb.append(" WHERE t.rec_active_flag = 'Y'");
		sb.append(" and t.id in (");
		for(int i=0;i<targetList.size();i++){
			if(i != 0){
				sb.append(",");
			}
			sb.append(targetList.get(i).getId());
		}
		sb.append(")");
		return sb.toString();
	}

	public Double getInvoiceItemAmountByTarget(Target target,
			String periodFrom, String periodTo) {
		logger.info("Enter method getInvoiceItemAmountByTarget.");
		Session session = this.getSession();
		StringBuffer sql = new StringBuffer("");		
		sql.append(" select if(round(sum(p.payment_amount), 2) is null,0,round(sum(p.payment_amount), 2)) ");
		sql.append("FROM target t,ban b,invoice i,invoice_item ii,proposal p WHERE t.id = "+target.getId());
		if(target.getBanId() != null){
			sql.append(" and t.ban_id = b.id");
		}else if(target.getVendorId() != null){
			sql.append(" and t.vendor_id = b.vendor_id");
		}
		sql.append(" AND b.master_ban_flag = 'Y' AND b.ban_status_id = 1 AND b.rec_active_flag = 'Y' AND i.ban_id = b.id AND i.rec_active_flag = 'Y' AND i.id = ii.invoice_id and p.invoice_id = i.id AND p.invoice_item_id = ii.id ");
		sql.append(" AND "+periodFrom+" <= DATE_FORMAT(i.invoice_date, '%Y%m')  AND DATE_FORMAT(i.invoice_date, '%Y%m') <= "+periodTo+" AND ii.rec_active_flag = 'Y' AND f_get_root_item_type(ii.item_type_id) = t.charge_type_id");
		sql.append(" AND (CASE WHEN (ifnull(t.stripped_circuit_number, '') = '') THEN 1 = 1 ELSE (t.stripped_circuit_number = ii.stripped_circuit_number OR t.stripped_circuit_number = ii.circuit_number) END )AND p.rec_active_flag = 'Y' AND p.proposal_flag = 1");
	
/*		sql.append(" select if(round(sum(p.payment_amount), 2) is null,0,round(sum(p.payment_amount), 2)) from ");
		sql.append(" (SELECT i.id as invoice_id, vc.id as vendor_circuit_id,t.charge_type_id as charge_type_id  FROM target t");
		sql.append(" JOIN vendor_circuit vc on 1=1");
//		sql.append(" and (case when (ifnull(t.vendor_id, '') = '') then 1 = 1 ELSE t.vendor_id = vc.vendor_id end)");
		sql.append(" and (case when (ifnull(t.ban_id, '') = '') then 1 = 1 ELSE t.ban_id = vc.ban_id end)");
		sql.append(" and (case when (ifnull(t.stripped_circuit_number, '') = '') then 1 = 1 ELSE t.stripped_circuit_number = vc.stripped_circuit_number end)");
//		del by mingyang.li start 2014.06.19
//		sql.append("       ON (vc.vendor_id = t.vendor_id OR t.vendor_id IS NULL)");
//		sql.append("  AND (vc.ban_id = t.ban_id OR t.ban_id IS NULL)");
//		sql.append("  AND ((vc.stripped_circuit_number = t.stripped_circuit_number and ifnull(vc.stripped_circuit_number,'') <> '') or (t.stripped_circuit_number is null and ifnull(vc.stripped_circuit_number,'') <> ''))");
//		del by mingyang.li end 2014.06.19
		sql.append(" JOIN ban b");
		sql.append(" ON ((t.ban_id IS not null and b.id = vc.ban_id) or (t.ban_id IS null and b.vendor_id = vc.vendor_id))");
		sql.append(" JOIN invoice i");
		sql.append(" ON i.ban_id = b.id");
		sql.append(" WHERE     t.id ="+target.getId()+" AND i.rec_active_flag = 'Y' And i.invoice_status_id >=9 and i.invoice_status_id !=98 and b.master_ban_flag='Y' ");
        sql.append(" and b.ban_status_id=1 and b.rec_active_flag='Y'  AND "+periodFrom+" <= DATE_FORMAT(i.invoice_date, '%Y%m')  AND DATE_FORMAT(i.invoice_date, '%Y%m') <= "+periodTo+") a");
		sql.append(" JOIN invoice_item ii");
		sql.append(" ON ii.invoice_id = a.invoice_id");
		sql.append(" AND ii.vendor_circuit_id = a.vendor_circuit_id");
		if(target.getChargeTypeId() != null){
			sql.append(" AND (f_get_root_item_type(ii.item_type_id) = a.charge_type_id)");
		}
		sql.append(" JOIN proposal p ON ii.id = p.invoice_item_id");
		sql.append(" WHERE p.proposal_flag = 1");
		sql.append(" and p.rec_active_flag = 'Y'");

		
		Session session = this.getSession();*/
		logger.info("Exit method getInvoiceItemAmountByTarget.");
		return (Double) session.createSQLQuery(sql.toString()).uniqueResult();
	}

	public List<TargetResult> queryTarResultListByTargetId(Integer id) {
		List<TargetResult> list = (List<TargetResult>)this.getHibernateTemplate().find("from TargetResult where recActiveFlag='Y' and targetId = "+id+" order by period asc");
		return list;
	}
	
	public void updateObject(Object o){
		this.getHibernateTemplate().update(o);
	}

	public void deleteTargetResultByTargetId(Integer id) {
		final String sql = "update target_result set rec_active_flag = 'N' where target_id = "+id;
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}

	public String queryTargetById(Integer id) {
		final String sql = this.getQueryTarget(id);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		return l.get(0);
	}

	private String getQueryTarget(Integer id) {
		StringBuffer sb = new StringBuffer("select concat('{id:',t.id,',vendor:\"',toJSON(v.vendor_name is null ,'',v.vendor_name),");
		sb.append("'\",ban:\"',toJSON(b.account_number is null ,'',b.account_number),");
		sb.append("'\",circuit_number:\"',if(t.stripped_circuit_number is null,'',t.stripped_circuit_number),");
		sb.append("'\",charge_type_name:\"',if(tc.charge_type_name is null,'',tc.charge_type_name),");
		sb.append("'\",target_amount:\"',if(t.target_amount is null,'',t.target_amount),");
		sb.append("'\",change_amount:\"',if(t.change_amount is null,'',t.change_amount),");
		sb.append("'\",change_percentage:\"',if(t.change_percentage is null,'',t.change_percentage),");
		sb.append("'\",variation_percentage:\"',if(t.allowed_variation_percentage is null,'',t.allowed_variation_percentage),");
		sb.append("'\",targetPeriod:\"',if(concat(t.period_from,'-',t.period_to) is null,'',concat(t.period_from,'-',t.period_to)),");
		sb.append("'\",allowed_period_shift:\"',if(t.allowed_period_shift is null,'',t.allowed_period_shift),");
		sb.append("'\"");
		sb.append("}') a ");
		
		sb.append(" from target t left join target_charge_type tc on t.charge_type_id = tc.id left join vendor v on v.id = t.vendor_id left join ban b  on b.id = t.ban_id ");
		sb.append(" where t.rec_active_flag='Y' ");
		sb.append(" and t.id="+id);
		return sb.toString();
	}

	public List<Object[]> searchTargetResultByObject(List<Integer> targetIds,
			Integer currentUserId) {
		final String sql = this.getTargetResultSearchByObjectQueryString(targetIds);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchTargetResultByObject.");
		return l;
	}

	private String getTargetResultSearchByObjectQueryString(
			List<Integer> targetIds) {
		logger.info("Enter method getTargetResultSearchByObjectQueryString.");
		StringBuffer sb = new StringBuffer("");
		sb.append("SELECT tr.id,");
		sb.append("   tr.target_id,");
		sb.append("   tr.period,");
		sb.append("   tr.allowed_period_shift,");
		sb.append("   tr.actual_amount,");
		sb.append("   tr.actual_change_amount,");
		sb.append("   tr.actual_change_percentage,");
		sb.append("   tr.actual_variation_percentage,");
		sb.append("   tr.result_flag,");
		sb.append("    tr.remark,");
		sb.append("    tr.created_timestamp,");
		sb.append("    u.user_name,");
		sb.append("    tr.modified_timestamp,");
		sb.append("    u2.user_name");
		sb.append(" FROM target_result tr");
		sb.append(" LEFT JOIN user u");
		sb.append("   ON tr.created_by = u.id");
		sb.append(" LEFT JOIN user u2");
		sb.append("   ON tr.modified_by = u2.id");
		sb.append("   WHERE tr.rec_active_flag = 'Y'");
		sb.append(" and tr.target_id in (");
		for(Integer id : targetIds){
			sb.append(id+",");
		}
		sb.append(" '');");
		logger.info("Exit method getTargetResultSearchByObjectQueryString.");
		return sb.toString();
	}

	public Target findById(java.lang.Integer id) {
		//log.debug("getting AccountCode instance with id: " + id);
		try {
			Target instance = (Target) getHibernateTemplate().get(
					"com.saninco.ccm.model.Target", id);
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}

	public List findTargetPeriod(Target target,int totleMonth) {
		Session session = getSession();
		try {
			String result="";
			return session.createSQLQuery("select date_format(date_sub('"+target.getPeriodFrom()+"01', interval "+totleMonth+" month), '%Y%m'),date_format(date_sub('"+target.getPeriodTo()+"01', interval "+totleMonth+" month), '%Y%m'),date_format(date_sub('"+target.getPeriodFrom()+"01', interval -"+totleMonth+" month), '%Y%m'),date_format(date_sub('"+target.getPeriodTo()+"01', interval -"+totleMonth+" month), '%Y%m')").list();
		}  finally {
			releaseSession(session);
		}
	}
	
	public TargetResult findTargetResultByTargetIdAndPeriod(Integer id,
			String string) {
		List<TargetResult> list = (List<TargetResult>)this.getHibernateTemplate().find("from TargetResult where recActiveFlag='Y' and period='"+string+"' and targetId = "+id);
		if(list.size()>0 && list!=null){
			return list.get(0);
		}
		return null;
		
	}
	
	public String seacherUsernameByUserId(Integer id){
		Session session = getSession();
		try {
			String result="";
			List list = session.createSQLQuery("select concat(u.first_name,' ',u.last_name) from user u where u.id="+id+" and u.rec_active_flag='Y'").list();
			result =(String) list.get(0);
			return result;
		}  finally {
			releaseSession(session);
		}
	}

	public Double getInvoiceAmountByTarget(Target target,
			String periodFrom, String periodTo) {
		if(((null!=target.getChargeTypeId())&&!"".equals(target.getChargeTypeId()))){
			String targetChargename=getTargetCharget(target.getChargeTypeId());
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT  if(round(sum("+targetChargename+"), 2) is null,0,round(sum("+targetChargename+"), 2)) ");
			sql.append(" FROM target t");
			sql.append("    JOIN vendor_circuit vc");
			sql.append("       ON  (vc.vendor_id = t.vendor_id or t.vendor_id is null)");
			sql.append(" AND       (vc.ban_id = t.ban_id or t.ban_id is null)");
			sql.append(" AND       ((vc.stripped_circuit_number = t.stripped_circuit_number and ifnull(vc.stripped_circuit_number,'') <> '') or (t.stripped_circuit_number is null and ifnull(vc.stripped_circuit_number,'') <> ''))");
			sql.append("     JOIN ban b");
			sql.append("       ON (b.id = t.ban_id or t.ban_id IS NULL)");
			sql.append("     JOIN invoice i");
			sql.append("       ON i.ban_id = b.id");
			sql.append(" WHERE t.id ="+target.getId());
			sql.append(" and i.rec_active_flag = 'Y'");
			sql.append(" and  "+periodFrom+" <= DATE_FORMAT(i.invoice_date,'%Y%m') and DATE_FORMAT(i.invoice_date,'%Y%m') <= "+periodTo+";");
			
			
			Session session = this.getSession();
			return (Double) session.createSQLQuery(sql.toString()).uniqueResult();
		
		
		}else{
			return 0.0;
		}
	}
	
	private String  getTargetCharget(Integer id){
		final String sql = "select charge_type_name from target_charge_type where id = "+id;
		Session session = getSession();
		String result="";
		try {
			List l=session.createSQLQuery(sql).list();
			if(l.size()>0){
				result=l.get(0).toString();
			}
		} finally {
			releaseSession(session);
		}
		return result;
	}

	public Target deleteTargetResult(Target target, int type) {
		if(target.getId()!=null && type!=2){
			Session session=null;
			try {
				session=this.getSession();
				session.createSQLQuery("update target_result tr set tr.rec_active_flag='N' where target_id = "+target.getId()).executeUpdate();
			}finally{
				releaseSession(session);
			}
		}
		return null;
	}
	
	
}
