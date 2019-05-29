/**
 * 
 */
package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Priority;
import com.saninco.ccm.model.Severity;
import com.saninco.ccm.model.Ticket;
import com.saninco.ccm.model.TicketStatus;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserDAO;
import com.saninco.ccm.vo.SearchTicketVO;


/**
 * Spring Hibernate DAO class for Ticket and ticket details based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * (Optimization of complete by xinyu.Liu)
 *
 */
@SuppressWarnings("unchecked")
public class TicketDaoImpl extends HibernateDaoSupport implements ITicketDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final Log log = LogFactory.getLog(UserDAO.class);
	
	// property constants
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}
	
	/**
	 * 
	 */
	public TicketDaoImpl() {
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#save(com.saninco.ccm.model.Ticket)
	 */
	public void save(Ticket transientInstance) {
		logger.debug("saving Ticket instance");
		try {
			getHibernateTemplate().save(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#delete(com.saninco.ccm.model.Ticket)
	 */
	public void delete(Ticket persistentInstance) {
		logger.debug("deleting Ticket instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findById(java.lang.Integer)
	 */
	public Ticket findById(java.lang.Integer id) {
		logger.debug("getting Ticket instance with id: " + id);
		try {
			Ticket instance = (Ticket) getHibernateTemplate().get(
					"com.saninco.ccm.model.Ticket", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findByExample(com.saninco.ccm.model.Ticket)
	 */
	public List findByExample(Ticket instance) {
		logger.debug("finding Ticket instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			logger.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		logger.debug("finding Ticket instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Ticket as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findByTitle(java.lang.Object)
	 */
	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findByContent(java.lang.Object)
	 */
	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findByCreatedBy(java.lang.Object)
	 */
	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findByModifiedBy(java.lang.Object)
	 */
	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findByRecActiveFlag(java.lang.Object)
	 */
	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#findAll()
	 */
	public List findAll() {
		logger.debug("finding all Ticket instances");
		try {
			String queryString = "from Ticket";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#merge(com.saninco.ccm.model.Ticket)
	 */
	public Ticket merge(Ticket detachedInstance) {
		logger.debug("merging Ticket instance");
		try {
			Ticket result = (Ticket) getHibernateTemplate().merge(
					detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#attachDirty(com.saninco.ccm.model.Ticket)
	 */
	public void attachDirty(Ticket instance) {
		logger.debug("attaching dirty Ticket instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao1#attachClean(com.saninco.ccm.model.Ticket)
	 */
	public void attachClean(Ticket instance) {
		logger.debug("attaching clean Ticket instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}
	
	private String getTicketSearchQueryString(SearchTicketVO sio, int userId){
		logger.info("Enter method getTicketSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{");
		sb.append("id:',id,',");
		sb.append("title:\"',toJSON(title is null,'',title),'\",");
		sb.append("priority_name:\"',toJSON(priority_name is null,'',priority_name),'\",");
		sb.append("severity_name:\"',toJSON(severity_name is null,'',severity_name),'\",");
		sb.append("ticket_status_name:\"',toJSON(ticket_status_name is null,'',ticket_status_name),'\",");
		sb.append("created_timestamp:\"',toJSON(created_timestamp is null,'',created_timestamp),'\",");
		sb.append("modified_timestamp:\"',toJSON(modified_timestamp is null,'',modified_timestamp),'\",");
		sb.append("created_name:\"',toJSON(created_name is null,'',created_name),'\",");
		sb.append("modified_name:\"',toJSON(modified_name is null,'',modified_name),'\"");
		sb.append("}') from search_ticket_view  ");
		this.searchWhere(sio, sb); 
		// sorting...
		sb.append(" order by " + sio.getSortField() + " " + sio.getSortingDirection());
		sb.append(" LIMIT " + sio.getStartIndex() + "," + sio.getRecPerPage());
		logger.info("Exit method getTicketSearchQueryString.");
		return sb.toString();
	}

	private String getTicketSearchNumberQueryString(SearchTicketVO sio, int userId){
		logger.info("Enter method getTicketSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) from search_ticket_view ");
		this.searchWhere(sio, sb); 
		logger.info("Exit method getTicketSearchNumberQueryString.");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao#getNumberOfTickets(com.saninco.ccm.vo.SearchTicketVO)
	 */
	public long getNumberOfTickets(SearchTicketVO sio, int userId) {
		logger.info("Enter method getNumberOfTickets.");
		final String sql = this.getTicketSearchNumberQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfTickets.");
		return count;
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao#searchTicket(com.saninco.ccm.vo.SearchTicketVO)
	 */
	public List<String> searchTicket(SearchTicketVO sio, int userId) {
		logger.info("Enter method searchTicket.");
		final String sql = this.getTicketSearchQueryString(sio, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchTicket.");
		return l;
	}
	/**
	 * Get Ticket List Object DownLoad Excel
	 * @param sio
	 * @param userId
	 * @return
	 */
	public List<Object[]> searchTicketByObject(SearchTicketVO sio, int userId) {
		logger.info("Enter method searchTicket.");
		final String sql = this.searchTicketByObjectSqlString(sio);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Object[]> l = (List<Object[]>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchTicket.");
		return l;
	}
	private String searchTicketByObjectSqlString(SearchTicketVO sio){
		logger.info("Enter method searchTicketByObjectSqlString.");
		
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" id, ");
		sb.append(" title, ");
		sb.append(" priority_name, ");
		sb.append(" severity_name, ");
		sb.append(" ticket_status_name, ");
		sb.append(" created_timestamp, ");
		sb.append(" created_name, ");
		sb.append(" modified_name ");
		sb.append(" from search_ticket_view  ");
		this.searchWhere(sio, sb); 
		sb.append(sio.getLimitCause());
		logger.info("Exit method searchTicketByObjectSqlString.");
		return sb.toString();
	}
	
	// ChaoXun TiaoJian
	public void searchWhere(SearchTicketVO sio,StringBuffer sb){
		sb.append("where 1=1 ");
		if(sio.getFilter()!=null){
			sb.append(" and ");
			sb.append(sio.getFilter());			
		}	
		if(sio.getTicketNumber() != null){
			sb.append(" and id = " + sio.getTicketNumber());
		}
		if(sio.getCreatedById() != null){
			sb.append(" and created_by = " + sio.getCreatedById());
		}
		if(sio.getModifiedById() != null){
			sb.append(" and modified_by = " + sio.getModifiedById());
		}
		if(sio.getTicketDueWiPastNumOfDays() != null){
			sb.append(" and datediff(created_timestamp, ADDDATE( now(), INTERVAL -" + sio.getTicketDueWiPastNumOfDays() + " DAY))>=0 and datediff(created_timestamp, ADDDATE( now(), INTERVAL 0 DAY))<=0");
		}else{
			if(sio.getTicketDueStartsOn() != null){
				sb.append(" and datediff( created_timestamp, '" + sio.getTicketDueStartsOn() + "')>=0");
			}
			if(sio.getTicketDueEndsOn() != null){
				sb.append(" and datediff( created_timestamp, '" + sio.getTicketDueEndsOn() + "')<=0");
			}
		}
		if(sio.getPriorityId() != null){
			sb.append(" and priority_id = " + sio.getPriorityId());
		}
		if(sio.getSeverityId() != null){
			sb.append(" and severity_id = " + sio.getSeverityId());
		}
		if(sio.getStatusId() != null){
			sb.append(" and ticket_status_id = " + sio.getStatusId() + " ");
		}
		
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketDao#getNumberOfInvoices(com.saninco.ccm.vo.ViewTicketDetailVO)
	 */
	public long getNumberOfTicketDetail(String ticketId) {
		logger.info("Enter method getNumberOfTicketDetail.");
		final String sql = this.getTicketDeatilSearchNumberQueryString(ticketId);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getNumberOfTicketDetail.");
		return count;
	}
	
	private String getTicketDeatilSearchNumberQueryString(String ticketId){
		logger.info("Enter method getTicketDeatilSearchNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from (((((((( ticket t join user u) join ticket_history th) join priority p2) join severity s2) join ticket_status ts2)  ");
		sb.append(" left join priority p on (th.priority_id_from = p.id)) ");
		sb.append(" left join severity s on(th.severity_id_from = s.id )) ");
		sb.append(" left join ticket_status ts on (th.ticket_status_id_from = ts.id ))");
		sb.append(" where t.rec_active_flag='Y' ");
		sb.append(" and th.priority_id_to = p2.id ");
		sb.append(" and th.severity_id_to = s2.id ");
		sb.append(" and th.ticket_status_id_to = ts2.id ");
		sb.append(" and u.id = th.created_by ");
		sb.append(" and t.id = th.ticket_id ");
		sb.append(" and t.id =" + ticketId + " ");
		logger.info("Exit method getTicketDeatilSearchNumberQueryString.");
		return sb.toString();
	}
	
	public List<String> searchTicketDetail(SearchTicketVO searchTicketVO){
		logger.info("Enter method searchTicketDetail");
		final String sql = this.getTicketDetail(searchTicketVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return l;
		    }
		});
		logger.info("Exit method searchTicketDetail");
		return list;
	}
	
	private String getTicketDetail(SearchTicketVO svo){
		logger.info("Enter method getTicketDetail.");
		StringBuffer sb = new StringBuffer("select concat('{id:',th.id,',no:\"',if(t.id is null,'',t.id),");
		sb.append("'\",pid_action:\"',if(th.priority_id_from = th.priority_id_to,'',concat('Priority Id from: ',if(p.priority_name is null,'',p.priority_name),'')),");
		sb.append("'\",pid_action2:\"',if(th.priority_id_from = th.priority_id_to,'',concat('Priority Id to: ',p2.priority_name,'')),");
		sb.append("'\",sid_action:\"',if(th.severity_id_from = th.severity_id_to,'',concat('Severity Id from: ',if(s.severity_name is null,'',s.severity_name),'')),");
		sb.append("'\",sid_action2:\"',if(th.severity_id_from = th.severity_id_to,'',concat('Severity Id to : ',s2.severity_name,'')),");
		sb.append("'\",tsid_action:\"',if(th.ticket_status_id_from = th.ticket_status_id_to,'',concat('Ticket Status Id from: ',if(ts.ticket_status_name is null,'',ts.ticket_status_name),'')),");
		sb.append("'\",tsid_action2:\"',if(th.ticket_status_id_from = th.ticket_status_id_to,'',concat('Ticket Status Id to: ',ts2.ticket_status_name,'')),");
		sb.append("'\",attachmentPointId:\"',if(th.attachment_point_id is null,'',th.attachment_point_id),");
		sb.append("'\",comment:\"',toJSON(th.comment is null,'',th.comment),");
		sb.append("'\",title:\"',toJSON(t.title is null,'',t.title),");
		sb.append("'\",content:\"',toJSON(th.content is null,'',th.content),");
		sb.append("'\",timestamp:\"',if(th.created_timestamp is null,'',th.created_timestamp),");
		sb.append("'\",createdName:\"',concat(u.first_name,' ',u.last_name),'\"}') a ");
		sb.append(" from (((((((( ticket t join user u) join ticket_history th) join priority p2) join severity s2) join ticket_status ts2)  ");
		sb.append(" left join priority p on (th.priority_id_from = p.id)) ");
		sb.append(" left join severity s on(th.severity_id_from = s.id )) ");
		sb.append(" left join ticket_status ts on (th.ticket_status_id_from = ts.id ))");
		sb.append("where t.rec_active_flag='Y' ");
		sb.append(" and th.priority_id_to = p2.id ");
		sb.append(" and th.severity_id_to = s2.id ");
		sb.append(" and th.ticket_status_id_to = ts2.id ");
		sb.append(" and u.id = th.created_by ");
		sb.append(" and t.id = th.ticket_id ");
		sb.append(" and t.id =" + svo.getTicketId() + " ");
		sb.append(" group by th.created_timestamp ");
		sb.append(" desc ");
		sb.append("limit " + svo.getStartIndex());
		sb.append(","+svo.getRecPerPage()); 
		logger.info("Exit method getTicketDetail.");
		return sb.toString();
	}
	
	/**
	 * find User By id
	 */
	public User findUserById(Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"com.saninco.ccm.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * find Priority By id
	 */
	public Priority findPriorityById(Integer id) {
		log.debug("getting Priority instance with id: " + id);
		try {
			Priority instance = (Priority) getHibernateTemplate().get(
					"com.saninco.ccm.model.Priority", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * find TicketStatus By id
	 */
	public TicketStatus findTicketStatusById(Integer id) {
		log.debug("getting TicketStatus instance with id: " + id);
		try {
			TicketStatus instance = (TicketStatus) getHibernateTemplate().get(
					"com.saninco.ccm.model.TicketStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * find Ticket By id
	 */
	public Ticket findTicketById(Integer id) {
		log.debug("getting Ticket instance with id: " + id);
		try {
			Ticket instance = (Ticket) getHibernateTemplate().get(
					"com.saninco.ccm.model.Ticket", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * find Severity By id
	 */
	public Severity findSeverityById(Integer id) {
		log.debug("getting Severity instance with id: " + id);
		try {
			Severity instance = (Severity) getHibernateTemplate().get(
					"com.saninco.ccm.model.Severity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public AttachmentPoint findAttachmentPointById(java.lang.Integer id) {
		log.debug("getting AttachmentPoint instance with id: " + id);
		try {
			AttachmentPoint instance = (AttachmentPoint) getHibernateTemplate()
					.get("com.saninco.ccm.model.AttachmentPoint", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Object get(Class c,Integer id){
		return this.getHibernateTemplate().get(c, id);
	}
	
}
