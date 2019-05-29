package com.saninco.ccm.common.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import com.saninco.ccm.util.SystemUtil;

public class ImageUtil {

	private static String userFileDir = SystemUtil.sysConfigMap.get("wiki_file_path");

	public static void deleteImage(String filePath){
		String destFile = userFileDir + File.separator + filePath;
		File file=new File(destFile);
		if(file.exists()){
			file.delete();
		}
	}
	public static void createDir(String pathdir) throws Exception {
		try {
			File dir = new File(pathdir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		} catch (Exception e) {
			System.err.println(new Date() + ":" + e.getLocalizedMessage());
		}
	}
	public static void upload(File uploadfile, String outputPath)throws Exception {
		FileInputStream fin = new FileInputStream(uploadfile);
		FileOutputStream fout = new FileOutputStream(outputPath);
		try {
			
			byte[] buf = new byte[20480];
			int bufsize = 0;
			while ((bufsize = fin.read(buf, 0, buf.length)) != -1) {
				fout.write(buf, 0, bufsize);
			}
			
		} catch (Exception e) {
			System.err.println(new Date() + ":" + e.getLocalizedMessage());
		} finally {
			fin.close();
			fout.close();
			fout.flush();
		}
	}
	public static String getExt(String filename) {
		return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
	}
}
