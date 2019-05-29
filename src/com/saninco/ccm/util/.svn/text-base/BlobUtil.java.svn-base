package com.saninco.ccm.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.SQLException;

import java.sql.Blob;

public final class BlobUtil {

	 public static final String getBlobString(Blob b) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedInputStream bis = new BufferedInputStream(b
					.getBinaryStream());
			int len = (int) b.length();
			byte[] bt = new byte[len];
			int readLen = 0;
			while ((readLen = bis.read(bt)) != -1) {
				sb.append(new String(bt, 0, readLen));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
