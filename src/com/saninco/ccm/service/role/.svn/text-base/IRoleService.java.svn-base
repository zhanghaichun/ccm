package com.saninco.ccm.service.role;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.SearchRoleVO;
/**
 * 
 * @author Chao.Liu
 *
 */
public interface IRoleService {
	/**
	 * Make Role List Totail Number Json String [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getSearchRoleTotalNO(SearchRoleVO rvo) throws BPLException;
	/**
	 * Make Role List Info Json String [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getSearchRole(SearchRoleVO rvo) throws BPLException;
	/**
	 * Delete One Role Info [Chao.Liu]
	 * @param rid
	 * @return
	 */
	public String deleteRole(Integer rid)throws BPLException;
	/**
	 * Add Or Update Role Name [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public String saveRoleName(SearchRoleVO rvo);
	/**
	 * Search Function List Info [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getFunctionList(SearchRoleVO rvo)throws BPLException;
	/**
	 * Add Or Delete Function To Role  [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public String addFunOrDel(SearchRoleVO rvo);
	
}
