package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceNotes;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.InvoiceNoteVO;
import com.saninco.ccm.vo.SearchDisputeVO;

public class InvoiceNoteDaoImpl extends HibernateDaoSupport implements IIvoiceNoteDao {
	

	public long getInvoiceNoteTotalPageNo(InvoiceNoteVO invoiceNoteVO,
			Integer currentUserId) {
		final String sql = this.getInvoiceNoteTotalPageNoQueryString(invoiceNoteVO,currentUserId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
		    @SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		return count;
	}
		
	private String getInvoiceNoteTotalPageNoQueryString(
			InvoiceNoteVO invoiceNoteVO,Integer currentUserId) {
		logger.info("Enter method getInvoiceNoteTotalPageNoQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(voWhereConditions(invoiceNoteVO,currentUserId));
		logger.info("Exit method getInvoiceNoteTotalPageNoQueryString.");
		return sb.toString();
	}
		
	private String voWhereConditions(InvoiceNoteVO iio, int userId) {
			
			StringBuffer sb = new StringBuffer();
			sb.append(" from invoice_notes t left join user u on u.id=t.analyst_id LEFT JOIN attachment_point ap ON ap.id = t.attachment_point_id");
			sb.append(" where t.rec_active_flag='Y' and (t.private_flag = 'N' or (t.private_flag = 'Y' and t.analyst_id = "+userId+")) ");
			if(iio.getInvoiceId()!=null){
				sb.append(" and ");
				sb.append("t.invoice_id="+iio.getInvoiceId());
			}
			
			if(iio.getBanId()!=null){
				sb.append(" and ");
				sb.append("t.ban_id="+iio.getBanId());
			}
			
			if(iio.getFilter()!=null){
				sb.append(" and " + iio.getFilter()+" " );
			}
			
			return sb.toString();
		}

	
	
	public List<Object[]> doGetUserUri() {
		final String sql = "SELECT u.id, concat(u.first_name, ' ', u.last_name) AS name FROM user u WHERE u.rec_active_flag = 'Y' and u.active_flag = 'Y' and u.system_user_flag = 'N' and u.id > 100;";
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		return l;
	}
	
	public List<Object[]> doGetRoleUri() {
		final String sql = "SELECT id,role_name FROM `role` WHERE rec_active_flag = 'Y' ORDER BY role_name ASC;";
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		return l;
	}
	
	public List<String> searchInvoiceNote(InvoiceNoteVO invoiceNoteVO,
			Integer currentUserId) {
		final String sql = this.getInvoiceNoteQueryString(invoiceNoteVO, currentUserId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		return l;
	}
	
	private String getInvoiceNoteQueryString(InvoiceNoteVO sio, int userId){
			
			StringBuffer sb = new StringBuffer("select concat('{id:',t.id,");
			sb.append("',notes:\"',ifnull(notes,'') ,'\",analyst:\"',concat(u.first_name,' ',u.last_name),");
			sb.append("'\",created_timestamp:\"',ifnull(date_format(t.created_timestamp,'%Y-%m-%d %H:%i:%s'), '') ,");
			sb.append("'\",attachmentPoint:\"',if(ap.rec_active_flag = 'N','',if(attachment_point_id IS NULL, '', attachment_point_id)),");
			sb.append("'\"");
			sb.append("}') a ");
			
			sb.append(voWhereConditions(sio,userId));
			sb.append(sio.getOrderByCause(null) + " ");
			sb.append(sio.getLimitCause() + " ");
			return sb.toString();
		}

	public void saveInvoiceNote(InvoiceNotes invoiceNote) {
		logger.debug("saving saveInvoiceNote instance");
		try {
			getHibernateTemplate().save(invoiceNote);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
		
	}
	
	public Ban ban(Integer banId) {
		List<?> banList = getHibernateTemplate().find("from Ban where id=?", banId);
		if(banList != null && banList.size() == 1) {
			return (Ban) banList.get(0);
		}
		return null;
	}
	
	public Invoice invoice(Integer invoiceId) {
		List<?> invoiceList = getHibernateTemplate().find("from Invoice where id=?", invoiceId);
		if(invoiceList != null && invoiceList.size() == 1) {
			return (Invoice) invoiceList.get(0);
		}
		return null;
	}
	
	public List<Object[]> queryNotifyUsers(String noteUserId, String noteRoleId){
		final String sql = getQueryNotifyUsersString(noteUserId, noteRoleId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		return l;
	}
	
	private String getQueryNotifyUsersString(String noteUserId, String noteRoleId){
		
		if(noteUserId == null || "".equals(noteUserId)) noteUserId = "-1";
		if(noteRoleId == null || "".equals(noteRoleId)) noteRoleId = "-1";
		
		StringBuffer sb = new StringBuffer("select a.id,a.name from (select u.id,concat(u.first_name,' ',u.last_name) name from user u where u.rec_active_flag = 'Y' and u.active_flag = 'Y'" +
				" and u.id in ("+noteUserId+") AND u.id > 100 AND u.system_user_flag = 'N'");
			sb.append(" union ");
			sb.append(" select r.user_id,concat(u.first_name, ' ', u.last_name) name from user_role r , user u where r.user_id = u.id and u.rec_active_flag = 'Y' and u.active_flag = 'Y' and r.rec_active_flag = 'Y' and r.role_id in ("+noteRoleId+") AND u.id > 100 AND u.system_user_flag = 'N' ");
			sb.append(" ) a order by a.id ;");
		return sb.toString();
	}
	
	public String saveMsMessage(String msChannelId,String privateFlag, String noteUserId, 
								String noteRoleId,String noteNotes,Integer pointId,String msReferenceId,String msReferenceNumber,String msReferenceTypeId){
		logger.info("Enter method saveMsMessage.");
		final String queryString = getSaveMsMessageSQLString(msChannelId,privateFlag, noteUserId, noteRoleId,noteNotes,pointId,msReferenceId,msReferenceNumber,msReferenceTypeId);
		Session session = getSession();
		try {
			SQLQuery sqlQuery = session.createSQLQuery(queryString.toString());
			sqlQuery.setParameter("notes", noteNotes);
			sqlQuery.executeUpdate();
		} finally {
			releaseSession(session);
		}
		
		HibernateTemplate template = this.getHibernateTemplate();
		String msMessageId = template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select last_insert_id();").uniqueResult();
			}
		}).toString();
		
		logger.info("Enter method saveMsMessage.");
		return msMessageId;
	}
	
	private String getSaveMsMessageSQLString(String msChannelId ,String privateFlag, String noteUserId, String noteRoleId,
											 String noteNotes,Integer pointId,String msReferenceId,String msReferenceNumber ,String msReferenceTypeId){
         
		StringBuffer sb = new StringBuffer("INSERT INTO ms_message( channel_id, msg_text, attachment_point_id, private_flag, reference_type_id, reference_id, " +
																   "reference_number, created_timestamp, created_by, rec_active_flag) VALUES (");
		sb.append(msChannelId +",:notes,"+pointId+",");
		if(privateFlag != null && !"".equals(privateFlag)){
			sb.append("'Y',");
		}else{
			sb.append("'N',");
		}
		sb.append(msReferenceTypeId+",");		//reference_type_id
		sb.append(msReferenceId+",");		//reference_id
		sb.append("'"+msReferenceNumber+"',");		//reference_number
		sb.append("now(),");	//created_timestamp
		sb.append(SystemUtil.getCurrentUserId() + ",");		//created_by
		sb.append("'Y'");		//rec_active_flag
		sb.append(");");
		
		return sb.toString();
	}
	
	public List<Integer> queryMsChannelId(String filterUserId){
		final String sql = getQueryMsChannelIdSQLString(filterUserId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		return l;
	}
	
	private String getQueryMsChannelIdSQLString(String filterUserId){
		
		if(filterUserId == null || "".equals(filterUserId)) filterUserId = "-1";
		StringBuffer sb = new StringBuffer("select m.id from ms_channel m where m.rec_active_flag = 'Y' and m.concat_user_ids = '"+filterUserId+"';");
		return sb.toString();
	}
	
	public String createMsChannel(String msChannelTypeId ,String filterUserIds, String filterUserNames,String currentUserId){
		final String sql = getCreateMsChannelSQLString(msChannelTypeId,filterUserIds,filterUserNames,currentUserId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
		});
		
		String msChannelId = template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery("select last_insert_id();").uniqueResult();
			}
		}).toString();
		
		
		return msChannelId;
	}
	
	private String getCreateMsChannelSQLString(String msChannelTypeId ,String filterUserIds, String filterUserNames,String currentUserId){
		StringBuffer sb = new StringBuffer("insert into ms_channel(channel_type_id,concat_user_ids, channel_name, latest_message_timestamp, created_timestamp, created_by, rec_active_flag)  " +
				" VALUES ");
			sb.append("("+msChannelTypeId+",'"+filterUserIds+"' ,left('"+filterUserNames+"', 255),  now(), now() ,"+currentUserId+",'Y');");
		return sb.toString();
	}
	
	public void createMsChannelUser(String msChannelId,String userId,String currentUserId){
		
		final String sql = getCreateMsChannelUserSQLString(msChannelId,userId,currentUserId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
		});
	}
	
	private String getCreateMsChannelUserSQLString(String msChannelId,String userId,String currentUserId){
		StringBuffer sb = new StringBuffer("insert into ms_channel_user(channel_id, user_id, unread_flag, created_timestamp, created_by,  rec_active_flag) " +
				" VALUES ");
			sb.append("("+msChannelId+", "+userId+", 'Y', now(), "+currentUserId+",'Y');");
		return sb.toString();
	}
	
	public void updateLatestMsMessageId(String msChannelId,String msMessageId){
		
		final String sql = getUpdateLatestMsMessageIdSQLString(msChannelId,msMessageId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
		});
	}
	
	private String getUpdateLatestMsMessageIdSQLString(String msChannelId,String msMessageId){
		StringBuffer sb = new StringBuffer("update ms_channel set latest_message_id = "+msMessageId+" , latest_message_timestamp = now() where id = "+msChannelId+";");
		return sb.toString();
	}
	
	public void updateUnreadFlag(String msChannelId){
		
		final String sql = getUpdateUnreadFlagSQLString(msChannelId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
		});
	}
	
	private String getUpdateUnreadFlagSQLString(String msChannelId){
		StringBuffer sb = new StringBuffer("update ms_channel_user set unread_flag = 'Y' where channel_id = "+msChannelId+";");
		return sb.toString();
	}
	
	public void saveMsFavorite(String userId,String msMessageId){
		final String sql = getSaveMsFavoriteSQLString(userId,msMessageId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).executeUpdate();
			}
		});
	}
	
	private String getSaveMsFavoriteSQLString(String userId,String msMessageId){
		StringBuffer sb = new StringBuffer("insert into ms_favorite(user_id,message_id,created_timestamp,created_by,rec_active_flag)");
		sb.append("values ( "+userId+","+msMessageId+",now(),"+userId+",'Y');");
		return sb.toString();
	}
}
