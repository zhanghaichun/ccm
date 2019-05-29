/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.RoleFunction;
import com.saninco.ccm.vo.SearchRoleVO;

public interface IRoleDao {

	public List<Object[]> getRole();

	public Integer getSearchRoleTotalNO(SearchRoleVO rvo);

	public List<String> getSearchRole(SearchRoleVO rvo);

	public void deleteRole(Integer rid);

	public Integer getRoleName(String rname);

	public Object getObject(Class c, Integer id);

	public void updateObject(Object o);

	public void saveObject(Object o);

	public List getFunctionList();

	public List getRoleFunctionList(Integer rid);

	public RoleFunction getRoleFunByRidAndFid(Integer rid, String fid);

	public List<RoleFunction> getRoleFunByRid(Integer rid);

}
