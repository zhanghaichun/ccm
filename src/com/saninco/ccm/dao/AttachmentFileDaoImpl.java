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

import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author Jian.Dong
 */
public class AttachmentFileDaoImpl extends HibernateDaoSupport implements IAttachmentFileDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public Integer createPoint(AttachmentPoint ap) {
		Integer newId = null;
		try {
			logger.debug("saving AttachmentPoint instance");
			newId = (Integer)getHibernateTemplate().save(ap);
			ap.setId(newId);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
		return newId;
	}

	public AttachmentPoint loadPoint(Integer apid) {
		AttachmentPoint a = null;
		try {
			logger.debug("loading AttachmentPoint instance");
			a = (AttachmentPoint)getHibernateTemplate().load(AttachmentPoint.class, apid);
			logger.debug("load successful");
		} catch (RuntimeException re) {
			logger.error("load failed", re);
			throw re;
		}
		return a;
	}

	public void save(AttachmentFile af) {
		try {
			logger.debug("saving AttachmentFile instance");
			getHibernateTemplate().save(af);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}
	public AttachmentFile findById(java.lang.Integer id) {
		logger.debug("getting AttachmentFile instance with id: " + id);
		try {
			AttachmentFile instance = (AttachmentFile) getHibernateTemplate()
					.get("com.saninco.ccm.model.AttachmentFile", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public List<AttachmentFile> findAllByAttchmentPiontId(java.lang.Integer id) {
		return (List<AttachmentFile>)getHibernateTemplate().find(
				"from AttachmentFile af where af.recActiveFlag='Y' and af.id=?", id);
	}
	@SuppressWarnings("unchecked")
	public List<AttachmentFile> findAllByAttchmentPiontIdPiontId(java.lang.Integer id) {
		return (List<AttachmentFile>)getHibernateTemplate().find(
				"from AttachmentFile af where af.recActiveFlag='Y' and af.attachmentPoint.id=?", id);
	}
	public void delete(AttachmentFile persistentInstance) {
		logger.debug("deleting AttachmentFile instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}
	
	public AttachmentFile merge(AttachmentFile detachedInstance) {
		logger.debug("merging AttachmentFile instance");
		try {
			AttachmentFile result = (AttachmentFile) getHibernateTemplate()
					.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}
	/**
	 * @author pengfei.wang
	 * */
	public long getAttachmentPointIdCount(SearchInvoiceVO svo) {
		logger.info("Enter method getAttachmentPointIdCount.");
		final String sql = toGetAttachmentPointIdCount(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getAttachmentPointIdCount.");
		return count;
	}
	
	/**
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String toGetAttachmentPointIdCount(SearchInvoiceVO svo) {
		logger.info("Enter method toGetAttachmentPointIdCount.");
		StringBuffer sb = new StringBuffer("select count(1) from attachment_file ");
		sb.append("where rec_active_flag='Y' ");
		sb.append(" and attachment_point_id ="+svo.getBoxInId()+"");	
		logger.info("Exit method toGetAttachmentPointIdCount.");
		return sb.toString();
	}
	//wenbo.zhang
	public long getDisputeAttachmentPointIdCountDao(SearchInvoiceVO svo) {
		logger.info("Enter method getDisputeAttachmentPointIdCountDao.");
		final String sql = toGetDisputeAttachmentPointIdCount(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getDisputeAttachmentPointIdCountDao.");
		return count;
	}
	private String toGetDisputeAttachmentPointIdCount(SearchInvoiceVO svo) {
		logger.info("Enter method toGetDisputeAttachmentPointIdCount.");
/*		StringBuffer sb = new StringBuffer("select count(1) from attachment_file ");
		sb.append("where rec_active_flag='Y' ");
		sb.append(" and attachment_point_id ="+svo.getBoxInId()+"");	*/
		StringBuffer sb = new StringBuffer("select count(1) from (SELECT attachment_file.* FROM attachment_file, dispute ");
		sb.append("  WHERE attachment_file.attachment_point_id = dispute.attachment_point_id");
		sb.append(" AND dispute.id = " + svo.getDisputeId());
		sb.append("  AND attachment_file.rec_active_flag = 'Y'  union all ");
		sb.append(" select * from attachment_file where attachment_point_id in (SELECT DISTINCT p.attachment_point_id ");
		sb.append(" FROM proposal p, dispute d  ");
		sb.append(" WHERE p.dispute_id = d.id AND d.id =" + svo.getDisputeId()+" )) t");
		logger.info("Exit method toGetDisputeAttachmentPointIdCount.");
		return sb.toString();
	}
	private String getDisputeSearchAttachmentPointId(SearchInvoiceVO svo) {
		logger.info("Enter method getDisputeSearchAttachmentPointId.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',attachment_file.id,'\", ");
		sb.append("file_name:\"',if(attachment_file.file_name is null,'',attachment_file.file_name),'\", ");
		sb.append("file_path:\"',if(attachment_file.file_path is null,'',attachment_file.file_path),'\" ");
		sb.append("}')  from (SELECT attachment_file.* FROM attachment_file, dispute ");
		sb.append("   WHERE attachment_file.attachment_point_id = dispute.attachment_point_id");
		sb.append("  AND dispute.id = " + svo.getDisputeId());
		sb.append("  AND attachment_file.rec_active_flag = 'Y'  union all ");
		sb.append(" select * from attachment_file where attachment_point_id in (SELECT DISTINCT p.attachment_point_id ");
		sb.append(" FROM proposal p, dispute d  ");
		sb.append("WHERE p.dispute_id = d.id AND d.id =" + svo.getDisputeId()+" )) attachment_file");	
		sb.append(" order by " + svo.getSortField() + " " + svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		logger.info("Exit method getDisputeSearchAttachmentPointId.");
		System.out.println( svo.getSortField());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * */
	public List<String> searchAttachmentPointId(SearchInvoiceVO svo) {
		logger.info("Enter method searchAttachmentPointId.");
		final String queryString = getSearchAttachmentPointId(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method searchAttachmentPointId.");
		return list;
	}
	@SuppressWarnings("unchecked")
	public List searchAttachmentPointIdList(SearchInvoiceVO svo) {
		Session session=getSession();
		try {
			List<Object> list=null;
			list=(List<Object>)session.createSQLQuery("select file_name,file_path from attachment_file where rec_active_flag='Y'and attachment_point_id ="+svo.getBoxInId()).list();
			return list;
		} finally{
			releaseSession(session);
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public void deleteAttchmentPiont(Integer attachmentPointId){
		final String queryString = deleteAttchmentPiontQueryString(attachmentPointId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	
	private String deleteAttchmentPiontQueryString(Integer attachmentPointId) {
		logger.info("Enter method deleteAttchmentPiontQueryString.");
		StringBuffer sb = new StringBuffer("update attachment_point ap set ");
		sb.append(" ap.rec_active_flag = 'N'");
		sb.append(" , ap.modified_timestamp = now() ");
		sb.append(" , ap.modified_by = '" + SystemUtil.getCurrentUserId() + "' ");
		sb.append("  where ap.id = " + attachmentPointId + " ");
		logger.info("Exit method deleteAttchmentPiontQueryString.");
		return sb.toString();
	}
	
	//wenbo.zhang
	public List<String> getDisputeSearchAttachmentPointIdDao(SearchInvoiceVO svo) {
		logger.info("Enter method getDisputeSearchAttachmentPointIdDao.");
		final String queryString = getDisputeSearchAttachmentPointId(svo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method getDisputeSearchAttachmentPointIdDao.");
		return list;
	}
	/**
	 * @author pengfei.wang
	 * SearchAttachmentPointId queryString
	 * */
	private String getSearchAttachmentPointId(SearchInvoiceVO svo) {
		logger.info("Enter method getMiscSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',attachment_file.id,'\", ");
		sb.append("file_name:\"',if(attachment_file.file_name is null,'',attachment_file.file_name),'\", ");
		sb.append("file_path:\"',if(attachment_file.file_path is null,'',attachment_file.file_path),'\" ");
		sb.append("}')  from attachment_file ");
		sb.append("where rec_active_flag='Y' ");
		sb.append(" and attachment_point_id ="+svo.getBoxInId()+"");	
		sb.append(" order by " + svo.getSortField() + " " + svo.getSortingDirection());
		sb.append(" LIMIT " + svo.getStartIndex() + "," + svo.getRecPerPage());
		logger.info("Exit method getMiscSqlString.");
		return sb.toString();
	}
	
}
