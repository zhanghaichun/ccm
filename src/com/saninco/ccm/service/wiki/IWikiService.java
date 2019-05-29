package com.saninco.ccm.service.wiki;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Wiki;
import com.saninco.ccm.vo.SearchWikiVO;

public interface IWikiService {
	
	public List<Wiki> findWikiList(SearchWikiVO searchWikiVO) throws BPLException;
	
	
	public void saveWiki(Wiki wiki) throws BPLException;
	
	public void deleteWiki(Wiki wiki) throws BPLException;
	
	public void updateWiki(Wiki wiki) throws BPLException;


	public Wiki findWikiById(Integer id) throws BPLException ;


	public void updateLististopById(String wikiIds, Wiki wiki) throws BPLException;


	public String findWikiTopTotalCount(SearchWikiVO searchWikiVO) throws BPLException;


	public long findTotalCount(SearchWikiVO searchWikiVO) throws BPLException;


	public List<String> findJSONWikiList(SearchWikiVO searchWikiVO);
	
}
