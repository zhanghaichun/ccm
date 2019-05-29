package com.saninco.ccm.action.wiki;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Wiki;
import com.saninco.ccm.service.wiki.IWikiService;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchWikiVO;

public class WikiAction extends CcmActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3012998203253993970L;
	
	private Wiki wiki = new Wiki();
	private IWikiService wikiService;
	private List<Wiki> wikiList;
	private String wikiIds;
	private File upload;  //文件
	private String uploadContentType;  //文件类型
	private String uploadFileName;   //文件名
	private SearchWikiVO searchWikiVO = new SearchWikiVO();
	private Integer totalHomePage;
	private Integer totalPage;

	@Override
	public String exec() throws Exception {
		return null;
	}
	
	public String wikiShow() throws BPLException {
		searchWikiVO.setLististop(1);
		wikiList = wikiService.findWikiList(searchWikiVO);
		long count = wikiService.findTotalCount(searchWikiVO);
		totalHomePage = new Double(Math.ceil((double) count/5)).intValue();
		return SUCCESS;
	}
	
	public String wikiDetail() throws BPLException {
		wiki = wikiService.findWikiById(wiki.getId());
		return SUCCESS;
	}
	
	public String reloadHomeWikiList() throws Exception {
		searchWikiVO.setLististop(1);
		wikiList = wikiService.findWikiList(searchWikiVO);
		long count = wikiService.findTotalCount(searchWikiVO);
		totalPage = new Double(Math.ceil((double) count/searchWikiVO.getRecPerPage())).intValue();
		JSONArray jsonarray = JSONArray.fromObject(wikiList);  
		String result = "{'totalPage':" + totalPage + ",'wikiList' : " + jsonarray.toString() + ",'totalCount':" + count + "}";
		this.writeOutputStream(result);
		return null;
	}
	
	public String wikiManagement() throws Exception  {
		wikiList = wikiService.findWikiList(searchWikiVO);
		JSONArray jsonarray = JSONArray.fromObject(wikiList);  
		long count = wikiService.findTotalCount(searchWikiVO);
		totalPage = new Double(Math.ceil((double) count/searchWikiVO.getRecPerPage())).intValue();
		String result = "{'totalPage':" + totalPage + ",'wikiList' : " + jsonarray.toString() + ",'totalCount':" + count + "}";
		this.writeOutputStream(result);
		return null;
	}
	
	public String queryWikiList() throws Exception  {
		List<String> wikiJSONList = wikiService.findJSONWikiList(searchWikiVO);
		String result = searchWikiVO.getListJsonCompatible(wikiJSONList);
		this.writeOutputStream(result);
		return null;
	}
	
	public String publishOrUpdateWiki() {
		try {
			if (wiki.getId() == null) {
				wikiService.saveWiki(wiki);
			} else {
				wikiService.updateWiki(wiki);
			}
		} catch (BPLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String findWikiById() throws Exception {
		try {
			wiki = wikiService.findWikiById(wiki.getId());
			String json = "{id:" + wiki.getId() + ",title:'" + wiki.getTitle() + "',content:'" + CcmUtil.wikiContent2JsonAll(wiki.getContent()) + "',lististop:" + wiki.getLististop() + "}";
			this.writeOutputStream(json);
		} catch (BPLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String deleteWiki() {
		try {
			wikiService.deleteWiki(wiki);
		} catch (BPLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String updateLististopById() {
		try {
			wikiService.updateLististopById(wikiIds,wiki);
		} catch (BPLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String wikiUploadImage() throws IOException {
		HttpServletResponse response =ServletActionContext.getResponse();

		response.setCharacterEncoding("GBK");

		PrintWriter out = response.getWriter();
		
		 //对文件进行校验  
        if(upload==null || uploadContentType==null || uploadFileName==null){  
            out.print("<font color=\"red\" size=\"2\">* Please select upload image file</font>");  
            return null;  
        }  
          
        if ((uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg"))  
                && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".jpg")) {  
            //IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
        }else if(uploadContentType.equals("image/png") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".png")){  
              
        }else if(uploadContentType.equals("image/gif") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".gif")){  
              
        }else if(uploadContentType.equals("image/bmp") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".bmp")){  
              
        }else if(uploadContentType.equals("image/tiff") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".tif")){  
              
        }else if(uploadContentType.equals("image/x-png") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".png")){  
        	
        }else{  
            out.print("<font color=\"red\" size=\"2\">* Invalid image format upload attempt(.jpg/.gif/.bmp/.tif/.png)</font>");  
            return null;  
        }  
          
        if(upload.length() > 5*1024*1024){  
            out.print("<font color=\"red\" size=\"2\">* Shall not be greater than the file size 5MB</font>");  
            return null;  
        } 
		
        uploadFile();
		
		return null;
	}
	
	public String wikiUploadFlash() throws IOException {
		HttpServletResponse response =ServletActionContext.getResponse();

		response.setCharacterEncoding("GBK");

		PrintWriter out = response.getWriter();
		
		 //对文件进行校验  
        if(upload==null || uploadContentType==null || uploadFileName==null){  
            out.print("<font color=\"red\" size=\"2\">* Please select upload swf file</font>");  
            return null;  
        }  
          
        if(uploadContentType.equals("application/x-shockwave-flash") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".swf")){  
              
        }else{  
            out.print("<font color=\"red\" size=\"2\">* Invalid flash format upload attempt(.swf)</font>");  
            return null;  
        }  
          
        if(upload.length() > 5*1024*1024){  
            out.print("<font color=\"red\" size=\"2\">* Shall not be greater than the file size 5MB</font>");  
            return null;  
        } 
		
        uploadFile();
		return null;
	}
	
	private void uploadFile() throws IOException {
		PrintWriter out = response.getWriter();
		InputStream is = new FileInputStream(upload);

		String uploadPath = SystemUtil.sysConfigMap.get("wiki_file_path");   //设置保存目录  
//		String uploadPath = ServletActionContext.getServletContext().getRealPath("/img");

		String fileName =UUID.randomUUID().toString(); //采用UUID的方式随机命名
	
		fileName+= uploadFileName.substring(uploadFileName.length() - 4);
	
		File toFile = new File(uploadPath, fileName);
	
		OutputStream os = new FileOutputStream(toFile);   
	
		byte[] buffer = new byte[1024];  
	
		int length = 0;
	
		while ((length = is.read(buffer)) > 0) {  
	
		        os.write(buffer, 0, length);  
	
		}  
		is.close();
		os.close();
		String callback =ServletActionContext.getRequest().getParameter("CKEditorFuncNum"); 
	
		out.println("<script type=\"text/javascript\">");
//		out.println("window.parent.CKEDITOR.tools.callFunction("+ callback + ",'" +uploadPath.substring(1,uploadPath.length())+ fileName + "','')"); 
		out.println("window.parent.CKEDITOR.tools.callFunction("+ callback + ",'" +ServletActionContext.getRequest().getContextPath() + uploadPath.substring(1,uploadPath.length())+ fileName + "','')"); 
		out.println("</script>");
	}
	
	public String selectWikiTopCount() throws Exception {
		logger.info("Enter method selectWikiTopCount.");
		String result = null;
		try {
			result = wikiService.findWikiTopTotalCount(searchWikiVO);
		} catch (Exception e) {
			logger.error("selectWikiTopCount error: ", e);
			result = "{error:\"selectWikiTopCount error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method wikiTopTotalCount.");
		return null;
	}
	
	public String queryWikiListTotalPageNo() throws IOException {
		String result = "";
		try {
			long count = wikiService.findTotalCount(searchWikiVO);
			result = searchWikiVO.getTotalPageNoJson(count);
		} catch (Exception e) {
			logger.error("selectWikiTopCount error: ", e);
			result = "{error:\"selectWikiTopCount error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		return null;
	}
	
	public Wiki getWiki() {
		return wiki;
	}

	public void setWiki(Wiki wiki) {
		this.wiki = wiki;
	}

	public void setWikiService(IWikiService wikiService) {
		this.wikiService = wikiService;
	}

	public List<Wiki> getWikiList() {
		return wikiList;
	}

	public void setWikiList(List<Wiki> wikiList) {
		this.wikiList = wikiList;
	}

	public String getWikiIds() {
		return wikiIds;
	}

	public void setWikiIds(String wikiIds) {
		this.wikiIds = wikiIds;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public SearchWikiVO getSearchWikiVO() {
		return searchWikiVO;
	}

	public void setSearchWikiVO(SearchWikiVO searchWikiVO) {
		this.searchWikiVO = searchWikiVO;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalHomePage() {
		return totalHomePage;
	}

	public void setTotalHomePage(Integer totalHomePage) {
		this.totalHomePage = totalHomePage;
	}
	
}
