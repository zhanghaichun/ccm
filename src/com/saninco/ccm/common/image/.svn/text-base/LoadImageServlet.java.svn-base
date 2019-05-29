package com.saninco.ccm.common.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jfree.util.Log;

import com.saninco.ccm.util.SystemUtil;

public class LoadImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4203512726280929735L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String servletPath = request.getServletPath();
		String []photoPaths = url.split(servletPath);
		File file = new File(SystemUtil.getSysConfigMap().get("wiki_file_path")+ photoPaths[1]);
		if (file.exists()) {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			if ("gif".equalsIgnoreCase(ImageUtil.getExt(file.getName()))) {
				response.setContentType("image/gif");
			} else if ("jpeg".equalsIgnoreCase(ImageUtil.getExt(file.getName()))) {
				response.setContentType("image/jpeg");
			} else if ("png".equalsIgnoreCase(ImageUtil.getExt(file.getName()))) {
				response.setContentType("image/png");
			} else if ("jpg".equalsIgnoreCase(ImageUtil.getExt(file.getName()))) {
				response.setContentType("image/jpeg");
			} else if ("bmp".equalsIgnoreCase(ImageUtil.getExt(file.getName()))) {
				response.setContentType("image/bmp");
			}
			ServletOutputStream sos = response.getOutputStream();
			FileInputStream is = new FileInputStream(file);
			IOUtils.copy(is , sos);
			is.close();
			sos.flush();
			sos.close();
		}
	}

}
