package com.saninco.ccm.service.role;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IRoleDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Function;
import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.RoleFunction;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchRoleVO;
import com.saninco.ccm.vo.SearchTicketVO;
/**
 * @see CodeReview By Chao.Liu on the 2010/08/25 AM
 * @author Chao.Liu
 *
 */
public class RoleServiceImpl implements IRoleService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IRoleDao roleDao;
	
	public RoleServiceImpl(){}
	/**
	 * Make Role List Totail Number Json String [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getSearchRoleTotalNO(SearchRoleVO rvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Make Role List Totail Number Json String.", rvo));

		StringBuffer sb = new StringBuffer();
		try {
			long count = roleDao.getSearchRoleTotalNO(rvo);
			sb.append(rvo.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Make Role List Info Json String [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getSearchRole(SearchRoleVO rvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Make Role List Info Json String.", rvo));

		StringBuffer sb = new StringBuffer();
		try {
			List<String> l = roleDao.getSearchRole(rvo);
			sb.append(rvo.getListJsonCompatible(l));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Delete One Role Info [Chao.Liu]
	 * @param rid
	 * @return
	 */
	public String deleteRole(Integer rid)throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Delete One Role Info.", rid));

		StringBuffer sb = new StringBuffer();
		try {
			roleDao.deleteRole(rid);
			// EX expanding
			List<RoleFunction> rfl = roleDao.getRoleFunByRid(rid);
			for (RoleFunction rf : rfl) {
				rf.setRecActiveFlag("N");
				roleDao.updateObject(rf);
			}
			sb.append("{error:false}");
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			sb.append("{error:true}");
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Add Or Update Role Name [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public String saveRoleName(SearchRoleVO rvo){
		logger.info(CcmFormat.formatEnterLog("Add Or Update Role Name.", rvo));

		StringBuffer sb = new StringBuffer();
		Integer count = roleDao.getRoleName(rvo.getRname());
		if(count == 0 && !"".equals(rvo.getRname())){
			Role r = null;
			if(rvo.getIsSave()!= null){
				r = (Role) roleDao.getObject(Role.class, rvo.getId());
				r.setRoleName(rvo.getRname());
				r.setModifiedBy(SystemUtil.getCurrentUserId());
				r.setModifiedTimestamp(new Date());
				roleDao.updateObject(r);
			}else{
				r = new Role();
				r.setRoleName(rvo.getRname());
				r.setCreatedBy(SystemUtil.getCurrentUserId());
				r.setCreatedTimestamp(new Date());
				r.setModifiedBy(SystemUtil.getCurrentUserId());
				r.setModifiedTimestamp(new Date());
				r.setRecActiveFlag("Y");
				roleDao.saveObject(r);
			}
			
			sb.append("{id:"+r.getId()+",error:false}");
		}else{
			sb.append("{error:\"The role name is duplicated or null!!\"}");
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Search Function List Info [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getFunctionList(SearchRoleVO rvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Function List Info.", rvo));

		StringBuffer sb = new StringBuffer();
		List fl = null;
		String s1 = "";
		String s2 = "";
		try {
			fl = roleDao.getFunctionList();
			if(rvo.getIsSave() != null){
				List rfl = roleDao.getRoleFunctionList(rvo.getId());
			
				for(int i=0; i<rfl.size(); i++){
					for(int j=0; j<fl.size(); j++){
						if(this.getFunctionString(fl.get(j),rvo).equals(this.getFunctionString(rfl.get(i),rvo))){
//						if(fl.get(j).equals(rfl.get(i))){
							fl.remove(j);
							break;
						}
					}
					if(i!=0) s2+=",";
					s2+=this.getFunctionString(rfl.get(i),rvo);
				}
			}
			for(int i=0; i<fl.size(); i++){
				if(i!=0) s1+=",";
				s1+=this.getFunctionString(fl.get(i),rvo);
			}
			sb.append("{s1:["+s1+"],s2:["+s2+"]}");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	private String getFunctionString(Object o,SearchRoleVO rvo) throws SQLException, IOException{
		if (o instanceof Blob) {
			return rvo.getBlobContent((Blob)o);
		}
		return o.toString();
	}
	/**
	 * Add Or Delete Function To Role  [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public String addFunOrDel(SearchRoleVO rvo){
		logger.info(CcmFormat.formatEnterLog("Add Or Delete Function To Role.", rvo));

		StringBuffer sb = new StringBuffer();
		
		String[] fids = rvo.getFidArray().split(",");
		RoleFunction rf = null;
		for(String s : fids){
			if(rvo.getIsAddOrDel().equals("Add")){
				rf = new RoleFunction();
				Role r = (Role)roleDao.getObject(Role.class,rvo.getId());
				rf.setRole(r);
				Function f = (Function)roleDao.getObject(Function.class,new Integer(s));
				rf.setFunction(f);
				rf.setCreatedBy(SystemUtil.getCurrentUserId());
				rf.setCreatedTimestamp(new Date());
				rf.setModifiedBy(SystemUtil.getCurrentUserId());
				rf.setModifiedTimestamp(new Date());
				rf.setRecActiveFlag("Y");
				
				roleDao.saveObject(rf);
			}else{
				rf = roleDao.getRoleFunByRidAndFid(rvo.getId(), s);
				rf.setRecActiveFlag("N");
				
				roleDao.updateObject(rf);
			}
		}
		sb.append("{error:false}");

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	//// Getter And Setter
	public void setRoleDao(IRoleDao roleDao) { this.roleDao = roleDao; } 
}
