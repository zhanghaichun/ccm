/**
 * 
 */
package com.saninco.ccm.service.quicklink;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.vo.MapEntryVO;

/**
 * @author Joe.Yang
 * 
 */
public interface IQuicklinkService {
	public String saveSearchQuicklink(String quicklinkName, String queryString, EnumQuicklinkType quicklinkType) throws BPLException;

	public void deleteQuicklink(Integer id) throws BPLException;

	public String getQuicklink(Integer id) throws BPLException;

	public List<MapEntryVO<String, String>> getUserQuicklinks(EnumQuicklinkType quicklinkType) throws BPLException;
	public List<MapEntryVO<String, String>> getUserQuicklinks() throws BPLException;
	public String getThisPageName(int quicklinkId)throws BPLException;
	public void saveSearchQuicklink(String quicklinkName,String queryString)throws BPLException;
	public String updateQuicklink(String quicklinkName)throws BPLException;
	public String updateQuicklink(String quicklinkName,Object o)throws BPLException;
}
