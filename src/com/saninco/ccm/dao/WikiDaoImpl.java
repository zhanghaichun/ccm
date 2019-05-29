package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Wiki;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchWikiVO;

public class WikiDaoImpl extends HibernateDaoSupport implements IWikiDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public List<Wiki> findWikiList(final SearchWikiVO searchWikiVO) {
		final StringBuilder sb = new StringBuilder();
		sb.append("From Wiki wk where wk.recActiveFlag='Y'");
		if (searchWikiVO.getTitle() != null && !"".equals(searchWikiVO.getTitle())) {
			String title = DaoUtil.ccmEscape(searchWikiVO.getTitle());
			sb.append(" and wk.title like'%" + title + "%'");
		}
		if (searchWikiVO.getLististop()!=null && searchWikiVO.getLististop() == 1) {
			sb.append(" and wk.lististop =1");
		}
		sb.append(" order by wk.modifiedTimestamp desc,wk.lististop asc");
		HibernateTemplate template = this.getHibernateTemplate();
		List<Wiki> wikiList = (List<Wiki>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	Query query = session.createQuery(sb.toString());
		    	if (searchWikiVO.getRecPerPage() == 0) {
		    		if (searchWikiVO.getLististop()!=null && searchWikiVO.getLististop() == 1) {
		    			query.setMaxResults(5);
		    		} else {
		    			query.setMaxResults(10);
		    		}
		    	} else {
		    		query.setMaxResults(searchWikiVO.getRecPerPage());
		    	}
	    		if (searchWikiVO.getStartIndex() == 0) {
	    			query.setFirstResult(0);  
	    		} else {
	    			query.setFirstResult(searchWikiVO.getStartIndex());  
	    		}
		    	
		    	return query.list();
		    }
		});
		return wikiList;
	}

	public void saveWiki(Wiki wiki) {
		logger.debug("saving Wiki instance");
		try {
			wiki.setCreatedBy(SystemUtil.getCurrentUserId());
			wiki.setCreatedTimestamp(new Date());
			wiki.setModifiedBy(SystemUtil.getCurrentUserId());
			wiki.setModifiedTimestamp(new Date());
			wiki.setRecActiveFlag("Y");
			getHibernateTemplate().save(wiki);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public void deleteWiki(Wiki wiki) {
		logger.debug("deleting Wiki instance");
		try {
			final StringBuffer sb = new StringBuffer("update wiki set ");
			sb.append(" wiki.rec_active_flag = 'N' ");
			sb.append(" where id = " + wiki.getId() + " ");
			HibernateTemplate template = this.getHibernateTemplate();
			template.execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					return session.createSQLQuery(sb.toString()).executeUpdate();
				}
			});
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	public void updateWiki(Wiki wiki) {
		logger.debug("udpate Wiki instance");
		try {
			getHibernateTemplate().update(wiki);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	public Wiki findWikiById(Integer id) {
		logger.debug("getting Wiki instance with id: " + id);
		try {
			Wiki instance = (Wiki) getHibernateTemplate().get(
					"com.saninco.ccm.model.Wiki", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	public long findWikiTotalCount(SearchWikiVO searchWikiVO) {
		logger.info("Enter method findWikiTopTotalCount.");
		StringBuffer sb = new StringBuffer("select count(1) ");
		sb.append("from wiki ");
		sb.append("where ");
		if (searchWikiVO.getLististop() != null && searchWikiVO.getLististop() == 1) {
			sb.append(" lististop=1 and ");
		}
		if (searchWikiVO.getTitle() != null && !"".equals(searchWikiVO.getTitle())) {
			String title = DaoUtil.ccmEscape(searchWikiVO.getTitle());
			sb.append(" title like '%" + title + "%' and ");
		}
		if(searchWikiVO.getFilter()!=null){
			sb.append(searchWikiVO.getFilter());
			sb.append(" and ");
		}	
		sb.append(" rec_active_flag='Y' ");
		Session session = getSession();
		Integer count;
		
		try{
			count = ((BigInteger)session.createSQLQuery(sb.toString()).uniqueResult()).intValue();
		}finally{
			releaseSession(session);
		}
		
		logger.info("Exit method getNumberOfReportAdminTagsAndRoles.");
		return count;
	}

	public List<String> findJSONWikiList(SearchWikiVO searchWikiVO) {
		final String sql = this.getSearchWikiQueryString(searchWikiVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchTeams.");
		return list;
	}
	
	private String getSearchWikiQueryString(SearchWikiVO searchWikiVO) {
		logger.info("Enter method getSearchWikiQueryString.");
		
		StringBuffer sb = new StringBuffer("SELECT concat('{id:', w.id,");
		sb.append(" ',title:\"',w.title,");
		sb.append(" '\",lististop:\"',w.lististop,");
		sb.append(" '\"}')");
		sb.append(" FROM wiki w");
		sb.append(" WHERE 1=1 and ");
		if (searchWikiVO.getTitle() != null && !"".equals(searchWikiVO.getTitle())) {
			String title = DaoUtil.ccmEscape(searchWikiVO.getTitle());
			sb.append(" w.title like'%" + title + "%' and ");
		}
		if(searchWikiVO.getFilter()!=null){
			sb.append(searchWikiVO.getFilter());
			sb.append(" and ");
		}
		sb.append(" w.rec_active_flag='Y' order by w.modified_timestamp desc,w.lististop asc ");

//		sb.append(searchWikiVO.getOrderByCause(null) + " ");
		sb.append(searchWikiVO.getLimitCause() + " ");
		logger.info("Enter method getSearchWikiQueryString.");
		return sb.toString();
	}
	
}
