package com.saninco.ccm.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.validation.IMRCVarianceDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

public class MRCVarianceServiceImpl implements IMRCVarianceService{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private IMRCVarianceDao mrcVarianceDao;

	@Override
	public String countMRCVarianceNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog("Rate Search page number", searchInvoiceVO));
        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            count = mrcVarianceDao.countMRCVarianceNo(searchInvoiceVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchInvoiceVO.getTotalPageNoJson(count) );
        
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
	}

	

	@Override
	public String listMRCVarianceListings(SearchInvoiceVO searchInvoiceVO) throws BPLException {

		StringBuffer sb = new StringBuffer();
        List<String> rateSearchList = null;
        
        try {
            
            rateSearchList = mrcVarianceDao.listMRCVarianceListings(searchInvoiceVO);

            sb.append(searchInvoiceVO.getListJsonCompatible(rateSearchList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
        
	}
	
	@Override
	public List<MapEntryVO<String, String>> listVarianceReasons() throws BPLException {
		
		logger.info("Enter method listVarianceReasons.");
        List<MapEntryVO<String, String>> varianceReasonsMapList = null;
        List<String> varianceReasonsList = null;
        
        try {
            
        	varianceReasonsList = mrcVarianceDao.listVarianceReasons();
        	varianceReasonsMapList = new ArrayList<MapEntryVO<String, String>>(varianceReasonsList.size());
        	
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : varianceReasonsList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                varianceReasonsMapList.add(m);
            }   
        }
        
        logger.info("Exit method listVarianceReasons.");
        return varianceReasonsMapList;
        
	}
	
	@Override
	public List<Map<String, String>> getMasterInventoryDetails(SearchInvoiceVO searchInvoiceVO)
			throws BPLException {
		
		List<Map<String, String>> mapString = new ArrayList<Map<String, String>>();
		mapString = mrcVarianceDao.getMasterInventoryDetails(searchInvoiceVO);
		
		return mapString;
	}

	
	
	public IMRCVarianceDao getMrcVarianceDao() {
		return mrcVarianceDao;
	}

	public void setMrcVarianceDao(IMRCVarianceDao mrcVarianceDao) {
		this.mrcVarianceDao = mrcVarianceDao;
	}



	@Override
	public String countVarianceItemsNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		
		logger.info("Enter method countVarianceItemsNo");
        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            count = mrcVarianceDao.countVarianceItemsNo(searchInvoiceVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchInvoiceVO.getTotalPageNoJson(count) );
        
        logger.info("Exit method countVarianceItemsNo");
        return sb.toString();
        
	}



	@Override
	public String listVarianceItemsListings(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		
		logger.info("Enter method listVarianceItemsListings");
		StringBuffer sb = new StringBuffer();
        List<String> varianceItemList = null;
        
        try {
            
        	varianceItemList = mrcVarianceDao.listVarianceItemsListings(searchInvoiceVO);

            sb.append(searchInvoiceVO.getListJsonCompatible(varianceItemList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        logger.info("Exit method listVarianceItemsListings");
        return sb.toString();
	}



	@Override
	public String countTwoMonthsVarianceProposalItemsNo(SearchInvoiceVO searchInvoiceVO) throws BPLException {
		
		logger.info("Enter method countTwoMonthsVarianceProposalItemsNo");
        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            count = mrcVarianceDao.countTwoMonthsVarianceProposalItemsNo(searchInvoiceVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchInvoiceVO.getTotalPageNoJson(count) );
        
        logger.info("Exit method countTwoMonthsVarianceProposalItemsNo");
        return sb.toString();
	}



	@Override
	public String listTwoMonthsVarianceProposalItemsListings(SearchInvoiceVO searchInvoiceVO) throws BPLException {

		logger.info("Enter method listTwoMonthsVarianceProposalItemsListings");
		StringBuffer sb = new StringBuffer();
        List<String> varianceItemList = null;
        
        try {
            
        	varianceItemList = mrcVarianceDao.listTwoMonthsVarianceProposalItemsListings(searchInvoiceVO);

            sb.append(searchInvoiceVO.getListJsonCompatible(varianceItemList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        logger.info("Exit method listTwoMonthsVarianceProposalItemsListings");
        return sb.toString();
        
	}



	


	

}
