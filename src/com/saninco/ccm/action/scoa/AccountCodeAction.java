package com.saninco.ccm.action.scoa;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.scoa.IAccountCodeService;
import com.saninco.ccm.vo.AccountCodeVO;

/**
 * @author Chao.Liu Date:Sep 9, 2010 (Optimization of complete by xinyu.Liu)
 */
public class AccountCodeAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private AccountCodeVO avo = new AccountCodeVO();
	
	private IAccountCodeService accountCodeService;
	
	private String type=null;

	private boolean isMockup = false;
	
	/**
	 * 
	 */
	public String exec() throws Exception {
		return null;
	}
	
	/**
	 * Check name is repeate
	 * @author Chao.Liu Date:Sep 9, 2010
	 * @return
	 * @throws Exception
	 */
	public String checkScoaCodeName()throws Exception {
		logger.info("Enter method checkScoaCodeName.");
		String result = "";
		if(isMockup){
			result = checkScoaCodeNameMockup();
		}else{
			result = accountCodeService.checkScoaCodeName0(avo);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method checkScoaCodeName.");
		return null;
	}
	
	/**
	 * Save New  Scoa Code
	 * @author Chao.Liu Date:Sep 9, 2010
	 * @return
	 * @throws Exception
	 */
	public String saveNewScoaCode()throws Exception {
		logger.info("Enter method saveNewScoaCode.");
		String result = "";
		if(isMockup){
			result = saveNewScoaCodeMockup();
		}else{
			result = accountCodeService.saveNewScoaCode(avo,type);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveNewScoaCode.");
		return null;
	}
	
	public String inactiveScoaCode()throws Exception {
		logger.info("Enter method inactiveScoaCode.");
		String result = "";
		if (isMockup){
			result = inactiveScoaCodeMockup();
		} else {
			result = accountCodeService.inactiveScoaCode(avo);
		}
		this.writeOutputStream(result);
		logger.info("Exit method inactiveScoaCode.");
		return null;
	}
	
	private String checkScoaCodeNameMockup(){
		return "{error:\"SCOA name already exists!\"}";
	}
	
	private String saveNewScoaCodeMockup(){
		return "{error:\"Save failure!\"}";
	}
	
	private String inactiveScoaCodeMockup(){
		return "{error:\"Inactive failure!\"}";
	}
	
	public AccountCodeVO getAvo() {
		return avo;
	}

	public void setAvo(AccountCodeVO avo) {
		this.avo = avo;
	}

	public IAccountCodeService getAccountCodeService() {
		return accountCodeService;
	}

	public void setAccountCodeService(IAccountCodeService accountCodeService) {
		this.accountCodeService = accountCodeService;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
