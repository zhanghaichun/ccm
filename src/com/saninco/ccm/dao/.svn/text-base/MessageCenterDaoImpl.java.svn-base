package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.vo.SearchMessageCenterVO;


public class MessageCenterDaoImpl extends HibernateDaoSupport implements IMessageCenterDao {
	
	public List<Object[]> getMessageReferenceList() {
		logger.info("Enter method getMessageReferenceList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("select mrt.id ,mrt.reference_type_name from ms_reference_type mrt").list();
					}
				});
		logger.info("Exit method getMessageReferenceList.");
		return l;
	}
	
	public List<Object[]> getMessageCreatedByList() {
		logger.info("Enter method getMessageCreatedByList.");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery("select u.id id,concat(u.first_name,' ',u.last_name) uname from user u where u.system_user_flag!='Y' and u.rec_active_flag = 'Y' and u.active_flag = 'Y' order by concat(u.first_name,' ',u.last_name) asc").list();
					}
				});
		logger.info("Exit method getMessageCreatedByList.");
		return l;
	}
	public long getNumberOfMessageCenter(SearchMessageCenterVO sv, int userId) {
		logger.info("Enter method getNumberOfMessageCenter.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append(" from ("+getMessageCenterSqlConditions(sv,userId)+") t");
		sb.append(" where 1=1 ");
		if (sv.getFilter() != null) {
			sb.append(" and ");
			sb.append(sv.getFilter());
		}
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
		logger.info("Exit method getNumberOfMessageCenter.");
		return count;
	}
	private String getMessageCenterSqlConditions(SearchMessageCenterVO sv, int userId) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT mm.id, ");
	    sb.append(" toJSON(mrt.reference_type_name IS NULL, '', mrt.reference_type_name) reference_type_name, ");
	    sb.append(" toJSON(mm.reference_id IS NULL, '', mm.reference_id) reference_id, ");
	    sb.append(" toJSON(mm.reference_number IS NULL, '', mm.reference_number) reference_number, ");
	    sb.append(" toJSON(mm.msg_text IS NULL, '', mm.msg_text) msg_text, ");
	    sb.append(" toJSON(mm.attachment_point_id IS NULL, '', mm.attachment_point_id) attachment_point_id, ");
	    sb.append(" toJSON(mf.id IS NULL, 'N', 'Y') favorite_flag, ");
	    sb.append(" toJSON(mm.private_flag IS NULL, '', mm.private_flag) private_flag, ");
	    sb.append(" toJSON(msc.id IS NULL, '', msc.id) channel_id, ");
	    sb.append(" toJSON(msc.channel_type_id IS NULL, '', msc.channel_type_id) channel_type_id, ");
	    sb.append(" toJSON(msc.channel_type_id != 1, '',(select user_id from ms_channel_user where channel_id=msc.id and msc.channel_type_id=1 and user_id!=" + userId + ")) user_id, ");
	    sb.append(" (CASE WHEN msc.channel_type_id = 1 THEN (SELECT concat(u.first_name,' ', u.last_name) FROM ms_channel_user mu, user u WHERE mu.user_id = u.id ");
	    sb.append("AND msc.channel_type_id = 1 AND channel_id = msc.id AND user_id != "+userId+") WHEN msc.channel_type_id = 2 THEN toJSON(msc.channel_name IS NULL, '', msc.channel_name) ");
	    sb.append("WHEN msc.channel_type_id = 3 THEN 'MySelf' ELSE toJSON(msc.channel_name IS NULL, '', msc.channel_name) END ) channel_name, ");
	    sb.append(" toJSON(mm.created_timestamp IS NULL, '', mm.created_timestamp) created_timestamp ");
		sb.append(" FROM ms_message mm ");
		sb.append("  LEFT JOIN ms_reference_type mrt ON mm.reference_type_id = mrt.id ");
		sb.append(" LEFT JOIN ms_channel msc ON mm.channel_id = msc.id AND msc.rec_active_flag = 'Y' ");	
		sb.append(" LEFT JOIN ms_favorite mf ON mf.message_id = mm.id  AND mf.rec_active_flag = 'Y' ");		
        sb.append("  WHERE mm.rec_active_flag = 'Y' and mm.channel_id in (select mcu.channel_id from ms_channel_user mcu where mcu.rec_active_flag = 'Y' and mcu.user_id = "+userId+")");
        sb.append("  AND ((mm.private_flag = 'Y' AND mm.created_by = "+userId+") OR mm.private_flag = 'N')");
        if (sv != null) {
        	if (sv.getCreatedBy() != null) {
            	sb.append(" AND mm.created_by = " + sv.getCreatedBy());
            }
        	if (sv.getReferenceTypeId() != null) {
            	sb.append(" AND mm.reference_type_id = " + sv.getReferenceTypeId());
            }
    		if (sv.getCreatedTimesFrom() != null) {
    			sb.append(" AND  DATE_FORMAT(mm.created_timestamp,'%Y/%m/%d')>= " + sv.getCreatedTimesFrom());
    		}
    		if (sv.getCreatedTimesTo() != null) {
    			sb.append(" AND DATE_FORMAT(mm.created_timestamp,'%Y/%m/%d') <= " + sv.getCreatedTimesTo());
    		}
    		if (sv.getFavoriteFlag() != null) {
    			sb.append(" AND mf.rec_active_flag = 'Y' ");
    		}
    		if (sv.getPrivateFlag() != null) {
    			sb.append(" AND mm.private_flag = " + sv.getPrivateFlag());
    		}
    		if (sv.getNotes() != null) {
    			sb.append(" AND mm.msg_text like '%" + sv.getNotes()+"%'");
    		}
    		if (sv.getReference_number() != null) {
    			sb.append(" AND mm.reference_number like '%" + sv.getReference_number()+"%'");
    		}
        }
		return sb.toString();
	}
	public List<Object[]> searchMessageCenter(SearchMessageCenterVO sv, int userId) {
		logger.info("Enter method searchMessageCenter.");
		final String sql = this.getMessageCenterSql(sv,userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method searchMessageCenter.");
		return l;
	}
	private String getMessageCenterSql(SearchMessageCenterVO sv, int userId) {
		StringBuffer sb = new StringBuffer();
	    sb.append("SELECT CONVERT(concat('{id:\"',t.id, ");
	    sb.append("'\",reference_type_name:\"',t.reference_type_name,");
	    sb.append("'\",reference_id:\"',t.reference_id,");
	    sb.append("'\",reference_number:\"',t.reference_number,");
	    sb.append("'\",msg_text:\"',t.msg_text,");
	    sb.append("'\",attachment_point_id:\"',t.attachment_point_id,");
	    sb.append("'\",favorite_flag:\"',t.favorite_flag,");
	    sb.append("'\",private_flag:\"',t.private_flag,");
	    sb.append("'\",channel_id:\"',t.channel_id,");
	    sb.append("'\",channel_type_id:\"',t.channel_type_id,");
	    sb.append("'\",user_id:\"', t.user_id,");
	    sb.append("'\",channel_name:\"',t.channel_name, ");
	    sb.append("'\",created_timestamp:\"',t.created_timestamp,");
	    sb.append("'\"}')USING utf8) AS a");
        sb.append(" from ("+getMessageCenterSqlConditions(sv,userId)+") t");
        sb.append(" where 1=1 ");
		if (sv.getFilter() != null) {
			sb.append(" and ");
			sb.append(sv.getFilter());
		}
        sb.append(" group by t.id ");
        sb.append(sv.getOrderByCause(null) + " ");
		sb.append(sv.getLimitCause() + " ");
	    return sb.toString();
	}
	
	public List<Object[]> getMessageCenterDataForExcel(SearchMessageCenterVO sv, int userId) {
		logger.info("Enter method getMessageCenterDataForExcel.");
		final String sql = this.getMessageCenterForExcelSql(sv,userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(sql).list();
					}
				});
		logger.info("Exit method getMessageCenterDataForExcel.");
		return l;
	}
	private String getMessageCenterForExcelSql(SearchMessageCenterVO sv, int userId) {
		StringBuffer sb = new StringBuffer();
	    sb.append("SELECT ");
	    sb.append("t.reference_type_name,");
        sb.append("t.reference_number,");
        sb.append("t.msg_text,");
        sb.append("t.attachment_point_id,");
        sb.append("t.favorite_flag,");
        sb.append("t.private_flag,");
        sb.append("t.channel_name,");
        sb.append("t.created_timestamp");
        sb.append(" from ("+getMessageCenterSqlConditions(sv,userId)+") t");
        sb.append(" where 1=1 ");
        if (sv.getFilter() != null) {
			sb.append(" and ");
			sb.append(sv.getFilter());
		}
        sb.append(" group by t.id ");
        sb.append(sv.getOrderByCause(null) + " ");
	//	sb.append(sv.getLimitCause() + " ");
	    return sb.toString();
	}
	
	public void updatMessageFavoriteFlag(int id,String favoriteFlag,int userId){
		Session session=getSession();
		
		try {
			if(favoriteFlag.equals("N")){
				StringBuffer sb = new StringBuffer();
				sb.append("insert into ms_favorite(user_id, message_id, created_timestamp, created_by, rec_active_flag)values");
				sb.append("('"+userId+"','"+id+"',now(),'"+userId+"','Y')");
				final String queryString = sb.toString();
				HibernateTemplate template = this.getHibernateTemplate();
				template.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).executeUpdate();
					}
				});
			}else{
				session.createSQLQuery("delete From ms_favorite where message_id = "+id+" and user_id ="+userId).executeUpdate();
			}
			
		} finally{
			releaseSession(session);
		}
	}
	
}
