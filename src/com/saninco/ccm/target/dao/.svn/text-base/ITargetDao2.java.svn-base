package com.saninco.ccm.target.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Target;
import com.saninco.ccm.vo.MapEntryVO;



public interface ITargetDao2 {
	Object modifyObject(String name,String type);
	
	List getBanIdByVendorId(Integer vendorId);
	
    List getCircuitList(Integer banId);
    
    List getCircuitListByBanVendor(Integer banId,Integer vendorId);
    
    Map<String, Object> getProcedureVerfication(String targets) throws SQLException;
    
    Integer saveTarget (Target target) throws BPLException;
    Integer updateTarget (Target target) throws BPLException;
}
