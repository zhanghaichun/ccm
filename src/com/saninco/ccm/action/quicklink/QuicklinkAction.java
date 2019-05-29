/**
 * 
 */
package com.saninco.ccm.action.quicklink;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.CcmFormat;

/**
 * @author Joe.Yang
 * @see To keep user by Chao.Liu
 */
public class QuicklinkAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7394816552994304287L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private int quicklinkId;
	
	private IQuicklinkService quicklinkService;

	/**
	 * 
	 */
	public QuicklinkAction() {
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@Override
	public String exec() throws Exception {
		return null;
	}

	public String deleteQuicklink() throws Exception {
		logger.info("Enter method deleteQuicklink." + quicklinkId);
		String s = "{id:"+this.quicklinkId+"}";
		try {
			this.quicklinkService.deleteQuicklink(this.quicklinkId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			s = "{error:\" Delete Quicklink Error!\"}";
		}
		this.writeOutputStream(s);	
		logger.info("Exit method deleteQuicklink.");
		return null;
	}
	
	public String getQuicklink() throws Exception {
		logger.info("Enter method getQuicklink." + quicklinkId);
		String s = null;
		try {
			s = this.quicklinkService.getQuicklink(this.quicklinkId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			s = "{error:\" Get Quicklink Error!\"}";
		}
		this.writeOutputStream(s);	
		logger.info("Exit method getQuicklink.");
		return null;
	}
	
	/**
	 * @author Chao.Liu
	 * @return
	 * @throws Exception
	 */
	public String getThisPageName()throws Exception {
		logger.info("Enter method getThisPageName");
		String s = null;
		try {
			s = this.quicklinkService.getThisPageName(quicklinkId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			s = "{error:\" Get Curren Page Name Error!\"}";
		}
		this.writeOutputStream(s);	
		logger.info("Exit method getThisPageName.");
		return null;
	}
	
	/**
	 * Save Location 
	 * @author Chao.Liu Date:Aug 29, 2010
	 * @return
	 * @throws Exception
	 */
	public String saveLocation()throws Exception {
		logger.info("Enter method saveLocation");
		String url = this.getRequest().getParameter("url");
		String[] strs = new String[3];
		try {
			strs[0] = this.getRequest().getParameter("quicklinkName");
			strs[1]  = this.getRequest().getParameter("queryString");
			strs[2]  = "";
			
			this.request.getSession().setAttribute("backUrl", strs);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		this.writeOutputStream(url);	
		logger.info("Exit method saveLocation.");
		return null;
	}
	
	/**
	 * Get Location
	 * @author Chao.Liu Date:Aug 29, 2010
	 * @return
	 * @throws Exception
	 */
	public String getLocation()throws Exception {
		logger.info("Enter method getLocation");
		String quicklinkName = this.getRequest().getParameter("quicklinkName");
		Object o = this.request.getSession().getAttribute("backUrl");
		String s = "";
		try {
			s = this.quicklinkService.updateQuicklink(quicklinkName,o);
			if(s.equals("mark_delete")){
				String[] strs = (String[]) o;
				strs[2] = "mark_delete";
				this.request.getSession().setAttribute("backUrl", strs);
				s = "";
			}else if(s.equals("")){
				this.request.getSession().removeAttribute("backUrl");
				s = "";
			}else{
				this.request.getSession().removeAttribute("backUrl");
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			s = "{error:\" Get Location Error!\"}";
		}
		this.writeOutputStream(s);	
		logger.info("Exit method getLocation.");
		return null;
	}
	
	public void setQuicklinkId(int quicklinkId) {
		this.quicklinkId = quicklinkId;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}
	
}
