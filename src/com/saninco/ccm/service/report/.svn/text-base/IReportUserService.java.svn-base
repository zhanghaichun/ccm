package com.saninco.ccm.service.report;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchReportUserVO;

public interface IReportUserService {
	/**
	 * Search Report Name And Rtag Color Info List [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String searchReportNameAndColorList(SearchReportUserVO rvo)throws BPLException ;
	/**
	 * Search Report Name And Rtag Color Totail Number [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getReportNameAndColorTotailNO(SearchReportUserVO rvo)throws BPLException;
	/**
	 * Created New Created Report Class [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public String saveCreatedReport(SearchReportUserVO rvo)throws RuntimeException;
	/**
	 * Check Name Is Repeat
	 * @param rvo
	 * @return
	 */
	public String getNameIsRepeat(SearchReportUserVO rvo);
	/**
	 * Get Vendor Name By Ban Id
	 * @Author Chao.Liu Date: Nov 3, 2010
	 * @Belong To 
	 * @param rvo
	 * @return
	 */
	public String getVendorNameByBanId(SearchReportUserVO rvo)throws BPLException;
	
	public String serchViewReportNameColorList(SearchReportUserVO rvo)throws BPLException;
	
	public String getViewZReportNameAndColorTotailNo(SearchReportUserVO rvo)throws BPLException;
	
	public String getViewNameTotalPageNo(SearchReportUserVO svo) throws BPLException;
	
	/*
	 * By hongtao 2011-09-20
	 */
	public String deleteReport(String createdReportId) throws BPLException;

	public String searcgViewNameList(SearchReportUserVO svo) throws BPLException;
	
	public String doReportParameterFunction(String reportId)throws BPLException ;
	
}
