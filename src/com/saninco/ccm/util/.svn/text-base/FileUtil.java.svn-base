/**
 * 
 */
package com.saninco.ccm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author joe.yang
 *
 */
public class FileUtil {
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while ((offset < bytes.length)&&
                ((numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) ) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }
    
	public static File getFileFromDirectory(String path){
		File choosenFile = null;
		File receivedFile = new File(path);
		if(receivedFile.exists()&&receivedFile.isDirectory()){
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					File f = new File(dir, name);
					return f.isFile() && (!name.startsWith(".")) && name.toLowerCase().endsWith(".zip");
			    }
			};
			String [] children = receivedFile.list(filter);
			
			if(children!=null&&children.length>0){
				choosenFile = new File(receivedFile, children[0]);
			}
		}
		return choosenFile;
	}
	
	/**
	 * Move file to toDir directory
	 * @param from
	 * @param toDir
	 */
	public static void moveFile(File from, String toDir) throws IOException {
		File toFile = new File(toDir, from.getName());
		copyFile(from, toFile);
		from.delete();
	}
	
	/**
	 * Copy file
	 * @param in
	 * @param out
	 * @throws IOException
	 */
    public static void copyFile(File in, File out) throws IOException {
    	if(!in.exists()){
			in.createNewFile();
		}
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}

	/*
     * Map里必须存在 file_name 与 file_path 列.并且查询文件要按file_name排序
     * */
    public static List<Map<String,Object>> deduplicationFile(List<Map<String,Object>> list){
    	
    	List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
    	
    	if(list == null ) return resultList;
    	
    	resultList.addAll(list);
    	String firstFileName = "";
    	String firstFilePath = "";
    	
    	for(int i=0 ; i<list.size() ; i++){
    		Map<String,Object> itemFile = list.get(i);
    		String itemFileName = itemFile.get("file_name").toString();
        	String itemFilePath = itemFile.get("file_path").toString();
    		
    		if (i == 0 ){
    			firstFileName = itemFileName;
    			firstFilePath = itemFilePath;
    		}else{
    			if(firstFileName.equals(itemFileName) && 
    			   getFileSize(firstFilePath).equals(getFileSize(itemFilePath)) &&
    			   getModifiedTime(firstFilePath).equals(getModifiedTime(itemFilePath)) ){
    				resultList.remove(itemFile);
    			}else{
    				firstFileName = itemFileName;
        			firstFilePath = itemFilePath;
    			}
    		}
    		
    	}
    	return resultList;
    }
    
    
    /**
     * 获取文件名与文件大小
     * @param file
     */
    public static String getFileSize(String filePath) {
    	
    	String result = "";
    	File f = new File(filePath);  
        if (f.exists() && f.isFile()) {
            String fileName = f.getName();
            result = fileName + f.length();
        }
        
        return result;
    }

    /** 
     * 读取修改时间的方法 
     */  
    public static String getModifiedTime(String filePath){ 
    	
        File f = new File("C:\\test.txt");              
        Calendar cal = Calendar.getInstance();  
        long time = f.lastModified();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
        cal.setTimeInMillis(time);    
        
        return formatter.format(cal.getTime());
    }  
}
