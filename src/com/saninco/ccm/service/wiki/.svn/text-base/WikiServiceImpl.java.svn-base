package com.saninco.ccm.service.wiki;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.dao.IWikiDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.Wiki;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchWikiVO;


public class WikiServiceImpl implements IWikiService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IWikiDao wikiDao;
	private IUserDao userDao;
	
	public WikiServiceImpl(){}
	
	public List<Wiki> findWikiList(SearchWikiVO searchWikiVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("findWikiList"));
		List<Wiki> wikiList = null;
		try {
			wikiList = wikiDao.findWikiList(searchWikiVO);
			for (Wiki wk : wikiList) {
				if (wk.getModifiedBy() != null) {
					User publishUser = userDao.findById(wk.getModifiedBy());
					if (publishUser != null && publishUser.getFirstName() != null && publishUser.getLastName() != null) {
						wk.setPublishUser(publishUser.getFirstName() + " " + publishUser.getLastName());
					}
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return wikiList;
	}
	public void saveWiki(Wiki wiki) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("saveWiki"));
		try {
			wikiDao.saveWiki(wiki);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public void deleteWiki(Wiki wiki) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("deleteWiki"));
		try {
			wikiDao.deleteWiki(wiki);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}

	public void updateWiki(Wiki newWiki) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("updateWiki"));
		try {
			Wiki wiki = wikiDao.findWikiById(newWiki.getId());
			wiki.setContent(newWiki.getContent());
			wiki.setContentText(newWiki.getContentText());
			wiki.setTitle(newWiki.getTitle());
			wiki.setLististop(newWiki.getLististop());
			wiki.setModifiedBy(SystemUtil.getCurrentUserId());
			wiki.setModifiedTimestamp(new Date());
			wikiDao.updateWiki(wiki);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public Wiki findWikiById(Integer id) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("findWikiById"));
		try {
			Wiki wiki = wikiDao.findWikiById(id);
			if (wiki.getModifiedBy() != null) {
				User publishUser = userDao.findById(wiki.getModifiedBy());
				if (publishUser.getFirstName() != null && publishUser.getLastName() != null) {
					wiki.setPublishUser(publishUser.getFirstName() + " " + publishUser.getLastName());
				}
			}
			return wiki;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public void updateLististopById(String wikiIds, Wiki wiki) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("updateLististopById"));
		try {
			if (wikiIds != null && !"".equals(wikiIds)) {
				String[] ids = wikiIds.split(",");
				for (String id : ids) {
					if (id != null && !"".equals(id)) {
						Wiki newWiki = wikiDao.findWikiById(Integer.parseInt(id));
						newWiki.setLististop(wiki.getLististop());
						newWiki.setModifiedBy(SystemUtil.getCurrentUserId());
						newWiki.setModifiedTimestamp(new Date());
						wikiDao.updateWiki(newWiki);
					}
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public String findWikiTopTotalCount(SearchWikiVO searchWikiVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.", searchWikiVO));
		StringBuffer sb = new StringBuffer();
		try {
			long count = wikiDao.findWikiTotalCount(searchWikiVO);
			sb.append(searchWikiVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public long findTotalCount(SearchWikiVO searchWikiVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.", searchWikiVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = wikiDao.findWikiTotalCount(searchWikiVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return count;
	}

	public void setWikiDao(IWikiDao wikiDao) {
		this.wikiDao = wikiDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public List<String> findJSONWikiList(SearchWikiVO searchWikiVO) {
		return wikiDao.findJSONWikiList(searchWikiVO);
	}
	
}
