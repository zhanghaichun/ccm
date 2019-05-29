package com.saninco.ccm.service.scoa;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IAccountCodelDao;
import com.saninco.ccm.dao.IRoleDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.Function;
import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.RoleFunction;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.AccountCodeVO;
import com.saninco.ccm.vo.SearchRoleVO;
import com.saninco.ccm.vo.SearchTicketVO;
/**
 * @author Chao.Liu Date:Sep 9, 2010
 */
public class AccountCodeServiceImpl implements IAccountCodeService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IAccountCodelDao accountCodelDao;
	
	public AccountCodeServiceImpl(){}
	/**
	 * Check name is repeate
	 * @author Chao.Liu Date:Sep 9, 2010
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String checkScoaCodeName(AccountCodeVO avo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Check name is repeate.", avo));

		StringBuffer sb = new StringBuffer();
		try {
			String result = accountCodelDao.checkScoaCodeName(avo);
			if(!"".equals(result) && "N".equals(result)){
				sb.append("{error:\"SCOA already Inactive!\"}");
			}else if (!"".equals(result) && "Y".equals(result)){
				sb.append("{error:\"SCOA already exists!\"}");
			} else {
				sb.append("{error:false}");
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String checkScoaCodeName0(AccountCodeVO avo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Check name is repeate.", avo));

		StringBuffer sb = new StringBuffer();
		try {
			
			Object result[] = accountCodelDao.checkScoaCodeNameOne(avo);
			if(result!=null){
				if(!"".equals(result) && ( "N".equals(result[0].toString()) || "N".equals(result[1].toString()) ) ){
					sb.append("{update:\"This SCOA code was inactive. Do you want to reactive it?\"}");
				}else if (!"".equals(result) && "Y".equals(result[0].toString())  && "Y".equals(result[1].toString())){
					sb.append("{error:\"This SCOA code exists already!\"}");
				} else {
					sb.append("{error:false}");
				}
			}else {
				sb.append("{error:false}");
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	/**
	 * Save New  Scoa Code
	 * @author Chao.Liu Date:Sep 9, 2010
	 * @param avo
	 * @return
	 * @throws BPLException
	 */
	public String saveNewScoaCode(AccountCodeVO avo,String type) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Save New  Scoa Code.", avo));
		StringBuffer sb = new StringBuffer();
		try {
			if("1".equals(type)){
					AccountCode ac = new AccountCode();
					ac.setAccountCodeName(avo.getScoaName());
					
					ac.setCreatedBy(SystemUtil.getCurrentUserId());
					ac.setCreatedTimestamp(new Date());
					ac.setModifiedBy(SystemUtil.getCurrentUserId());
					ac.setModifiedTimestamp(new Date());
					ac.setRecActiveFlag("Y");
					
					accountCodelDao.save(ac);
					
					sb.append("{error:false}");
			}
			if("0".equals(type)){
				AccountCode ac = new AccountCode();
				ac.setActiveFlag("Y");
				ac.setAccountCodeName(avo.getScoaName());
				ac.setModifiedBy(SystemUtil.getCurrentUserId());
				ac.setModifiedTimestamp(new Date());
				ac.setRecActiveFlag("Y");
				
				accountCodelDao.save0(ac);
				
				sb.append("{error:false}");
			}
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			sb.append("{error:\"Save failure!\"}");
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String inactiveScoaCode(AccountCodeVO avo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Inactive Scoa Code.", avo));
		StringBuilder sb = new StringBuilder();
		try {
			accountCodelDao.delete(avo);
			sb.append("{error:false}");
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			sb.append("{error:\"Inactive failure!\"}");
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	// Setter And Getter
	public void setAccountCodelDao(IAccountCodelDao accountCodelDao) { this.accountCodelDao = accountCodelDao; }
    public IAccountCodelDao getAccountCodelDao() { return accountCodelDao; }
	
}
