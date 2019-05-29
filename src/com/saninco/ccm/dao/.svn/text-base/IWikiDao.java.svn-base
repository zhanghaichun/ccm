package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Wiki;
import com.saninco.ccm.vo.SearchWikiVO;

public interface IWikiDao {

	public List<Wiki> findWikiList(SearchWikiVO searchWikiVO);
	
	public void saveWiki(Wiki wiki);
	
	public void updateWiki(Wiki wiki);
	
	public void deleteWiki(Wiki wiki);

	public Wiki findWikiById(Integer id);

	public long findWikiTotalCount(SearchWikiVO searchWikiVO);

	public List<String> findJSONWikiList(SearchWikiVO searchWikiVO);

}
