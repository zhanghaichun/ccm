package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;

public interface IQuoteDao {
	public void createQuoteTemporary() throws SQLException;
	public void callQuoteVerification(String batchNo) throws SQLException;
	public List<Map<String,Object>> queryTmpQuoteError();
	public void insertQuoteTemporary(List<Map<String,String>> dataList,List<String> columnList,String batchNo) throws SQLException;
}

