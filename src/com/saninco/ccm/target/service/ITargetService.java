package com.saninco.ccm.target.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Target;
import com.saninco.ccm.target.model.SearchTargetVO;
import com.saninco.ccm.vo.MapEntryVO;

public interface ITargetService {
	
	String common (List<Target> targetList,int type) throws BPLException,ParseException;;

	String searchTarget(SearchTargetVO searchTargetVO) throws BPLException;

	String getCircuitList(Integer integer) throws BPLException;

	List<MapEntryVO<String, String>> chargeTypeList()throws BPLException;

	String getTragetSearchTotalPageNo(SearchTargetVO searchTargetVO)throws BPLException ;

	String createTargetToExcel(SearchTargetVO searchTargetVO,
			String excelDirPath, List<String> titles,List<Integer> targetIds)throws BPLException;

	String saveTarget(List<Target> targetList,int type)throws BPLException;

	String queryCopyTargetList(List<Target> targetList)throws BPLException;


	String downLoadResultToExcel(List<Integer> targetIds, String excelDirPath,
			List<String> titles) throws BPLException;

	
	String commonResult(List<Integer> targetIds,int type)throws BPLException, ParseException;

	String excelUp(File file) throws SQLException, FileNotFoundException, IOException, ParseException;
	
	String read2007Excel(File file,String fileName)throws IOException ,BPLException;
	
	String ProcedureProcessVerification(List<Target> targetList) throws SQLException ,BPLException;
	
	String saveTG(List<Target> targetList,int type)	throws BPLException, ParseException;

}
