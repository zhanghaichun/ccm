package com.saninco.ccm.action.validation;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.validation.IMRCVarianceService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

public class MRCVarianceAction extends CcmActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SearchInvoiceVO searchInvoiceVO;
	private IMRCVarianceService mrcVarianceService;
	

	@Override
	public String exec() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String countMRCVarianceNo() throws Exception {
		
		logger.info("Enter method countMRCVarianceNo.");
		
        String pageNoResult = null;
        
        try{
        	
        	pageNoResult = mrcVarianceService.countMRCVarianceNo(searchInvoiceVO);
        	
        }catch(Exception e){
        	
            logger.error("countMRCVarianceNo error: ", e);
            pageNoResult = "{error:\"countMRCVarianceNo error: "+e.getMessage()+"\"}";
        }
        
        this.writeOutputStream(pageNoResult); 
        logger.info("Exit method countMRCVarianceNo.");
        return null;
	}
	
	public String countTwoMonthsVarianceProposalItemsNo() throws Exception {
		
		logger.info("Enter method countTwoMonthsVarianceProposalItemsNo.");
		
        String pageNoResult = null;
        
        try{
        	
        	pageNoResult = mrcVarianceService.countTwoMonthsVarianceProposalItemsNo(searchInvoiceVO);
        	
        }catch(Exception e){
        	
            logger.error("countTwoMonthsVarianceProposalItemsNo error: ", e);
            pageNoResult = "{error:\"countTwoMonthsVarianceProposalItemsNo error: "+e.getMessage()+"\"}";
        }
        
        this.writeOutputStream(pageNoResult); 
        logger.info("Exit method countTwoMonthsVarianceProposalItemsNo.");
        return null;
	}
	
	public String countVarianceItemsNo() throws Exception {
		
		logger.info("Enter method countVarianceItemsNo.");
		
        String pageNoResult = null;
        
        try{
        	
        	pageNoResult = mrcVarianceService.countVarianceItemsNo(searchInvoiceVO);
        	
        }catch(Exception e){
        	
            logger.error("countVarianceItemsNo error: ", e);
            pageNoResult = "{error:\"countVarianceItemsNo error: "+e.getMessage()+"\"}";
        }
        
        this.writeOutputStream(pageNoResult); 
        logger.info("Exit method countMRCVarianceNo.");
        return null;
	}
	
	/**
	 * Get Variance reasons.
	 * @return
	 * @throws Exception
	 */
	public String getVarianceReasons() throws Exception {
	 	
		List<MapEntryVO<String, String>> varianceReasonsList = mrcVarianceService.listVarianceReasons();
    	
        JSONArray references = JSONArray.fromObject( varianceReasonsList );
        this.writeOutputStream(references.toString());
        
    	return null;
    	
    }
	
	/**
	 * Get master inventory details for mrc variance.
	 * @return
	 * @throws Exception
	 */
	public String getMasterInventoryDetails() throws Exception {
	 	
		List<Map<String, String>> masterInventoryDetailsList = mrcVarianceService.getMasterInventoryDetails(searchInvoiceVO);
    	
        JSONArray references = JSONArray.fromObject( masterInventoryDetailsList );
        this.writeOutputStream(references.toString());
        
    	return null;
    	
    }
	
	public String listMRCVarianceListings() throws Exception {
		
		logger.info("Enter method listMRCVarianceListings.");
        String result = null;
        
        try{
            result = mrcVarianceService.listMRCVarianceListings(searchInvoiceVO);
        }catch(Exception e){
            logger.error("listMRCVarianceListings error: ", e);
            result = "{error:\"listMRCVarianceListings error: "+e.getMessage()+"\"}";
        }
        
        this.writeOutputStream(result); 
        logger.info("Exit method listMRCVarianceListings.");
        return null;
	}
	
	public String listVarianceItemsListings() throws Exception {
		
		logger.info("Enter method listVarianceItemsListings.");
        String result = null;
        
        try{
            result = mrcVarianceService.listVarianceItemsListings(searchInvoiceVO);
        }catch(Exception e){
            logger.error("listVarianceItemsListings error: ", e);
            result = "{error:\"listVarianceItemsListings error: "+e.getMessage()+"\"}";
        }
        
        this.writeOutputStream(result); 
        logger.info("Exit method listVarianceItemsListings.");
        return null;
	}
	
	public String listTwoMonthsVarianceProposalItemsListings() throws Exception {
		
		logger.info("Enter method listTwoMonthsVarianceProposalItemsListings.");
        String result = null;
        
        try{
            result = mrcVarianceService.listTwoMonthsVarianceProposalItemsListings(searchInvoiceVO);
        }catch(Exception e){
            logger.error("listTwoMonthsVarianceProposalItemsListings error: ", e);
            result = "{error:\"listTwoMonthsVarianceProposalItemsListings error: "+e.getMessage()+"\"}";
        }
        
        this.writeOutputStream(result); 
        logger.info("Exit method listTwoMonthsVarianceProposalItemsListings.");
        return null;
	}

	public SearchInvoiceVO getSearchInvoiceVO() {
		return searchInvoiceVO;
	}

	public void setSearchInvoiceVO(SearchInvoiceVO searchInvoiceVO) {
		this.searchInvoiceVO = searchInvoiceVO;
	}

	public IMRCVarianceService getMrcVarianceService() {
		return mrcVarianceService;
	}

	public void setMrcVarianceService(IMRCVarianceService mrcVarianceService) {
		this.mrcVarianceService = mrcVarianceService;
	}
	
	
	
	

}
