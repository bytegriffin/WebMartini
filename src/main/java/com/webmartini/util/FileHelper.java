package com.webmartini.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public final class FileHelper {

	public static void createFile(String filepath, MultipartFile attachment) {
		File file = new File(filepath);
		file.setWritable(true, false);
		if (!file.exists()) {
			file.mkdirs();
		}
		String path = filepath + "/" + attachment.getOriginalFilename();
		File serverfile = new File(path);
		try {
			FileUtils.copyInputStreamToFile(attachment.getInputStream(), serverfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	public static String getOriginFileName(String url) {
		if (StringHelper.isNullOrBlank(url)) {
			return null;
		}
		return new File(url).getName();
	}

	public static void download(String realpath,HttpServletResponse response) {
		response.setContentType("application/x-msdownload; charset=utf-8");  
		response.setHeader("content-disposition","attachment; filename="+FileHelper.getOriginFileName(realpath));
		try {
			File f = new File(realpath);
			InputStream in = new FileInputStream(f);
			int i;
			ServletOutputStream out = response.getOutputStream();
			while ((i = in.read()) != -1) {
				out.write(i);
			}
			out.flush();
			in.close();
			out.close();
		} catch (Exception ex) {
			
		}
	}
	
	public static boolean exist(String filename) {
		if(StringHelper.isNullOrBlank(filename)){
			return false;
		}
		File file = new File(filename);
		return file.exists();
	}

}
