package com.photography.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 
 * 
 * @author 李源 
 * @since 2012-7-16 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class ZipUtil {
	
	private static Logger log = Logger.getLogger(ZipUtil.class);

	public static boolean doZip(String filesDirPath, String zipFilePath) {
		return doZip(new File(filesDirPath), zipFilePath);
	}

	private static boolean doZip(File inputFile, String zipFileName) {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			out.setEncoding("utf-8");
			boolean result = doZip(out, inputFile, "");

			return result;
		} catch (FileNotFoundException ex) {
			// ex.printStackTrace();
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException ex) {
				// ex.printStackTrace();
				return false;
			}
		}
	}

	private static boolean doZip(ZipOutputStream out, File f, String base) {
		try {
			if (f.isDirectory()) {
				File[] fl = f.listFiles();
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
				base = base.length() == 0 ? "" : base + "/";
				for (int i = 0; i < fl.length; i++) {
					doZip(out, fl[i], base + fl[i].getName());
				}
			} else {
				out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
				FileInputStream in = new FileInputStream(f);
				int b;
				while ((b = in.read()) != -1) {
					out.write(b);
				}
				in.close();
			}
			return true;
		} catch (IOException ex) {
			// ex.printStackTrace();
			return false;
		}
	}

	public static boolean unZip(String srcFile, String dest, boolean deleteFile) {
		try {
			File file = new File(srcFile);
			if (!file.exists()) {
				// throw new RuntimeException("解压文件不存在!");
				return false;
			}
			ZipFile zipFile = new ZipFile(file);
			Enumeration e = zipFile.getEntries();
			while (e.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(dest + name);
					f.mkdirs();
				} else {
					File f = new File(dest + zipEntry.getName());
					f.getParentFile().mkdirs();
					f.createNewFile();
					InputStream is = zipFile.getInputStream(zipEntry);
					FileOutputStream fos = new FileOutputStream(f);
					int length = 0;
					byte[] b = new byte[1024];
					while ((length = is.read(b, 0, 1024)) != -1) {
						fos.write(b, 0, length);
					}
					is.close();
					fos.close();
				}
			}

			if (zipFile != null) {
				zipFile.close();
			}

			if (deleteFile) {
				file.deleteOnExit();
			}

			return true;
		} catch (IOException ex) {
			return false;
		}
	}
	
	public static File zip(String folder,String zipFileName) {
		File folderFile = new File(folder);
		File[] fileArray = folderFile.listFiles();
		List<File> fileList = new ArrayList<File>();
		List<String> fileNameList = new ArrayList<String>();
		for(File file : fileArray) {
			fileList.add(file);
			fileNameList.add(file.getName());
		}
		return zipFileList(fileList,fileNameList,zipFileName);
	}
	
	/**
	 * 将一组文件打包成一个zip
	 * 
	 * @param listfile
	 *            一组文件
	 * @param listfilename
	 *            一组文件对应的文件名
	 * @param foldername
	 *            压缩文件第一级的文件夹名
	 * @return
	 * @throws IOException
	 */
	private static File zipFileList(List<File> listfile,
			List<String> listfilename, String foldername) {
		byte[] buf = new byte[1024];
		int length = 0;
		try {
			foldername = deployFoldDir(foldername);
			ZipEntry zipEntry = new ZipEntry(foldername);
			File file = File.createTempFile("temp", ".zip");
			OutputStream os = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			ZipOutputStream zos = new ZipOutputStream(bos);
			zos.putNextEntry(zipEntry);
			int count = listfilename.size();
			for (int i = 0; i < count; i++) {
				try {
					ZipEntry subZipEntry = new ZipEntry(foldername
							+ listfilename.get(i));
					zos.putNextEntry(subZipEntry);
					InputStream subIs = new FileInputStream(listfile.get(i));
					BufferedInputStream subBis = new BufferedInputStream(subIs);

					while ((length = subBis.read(buf)) > 0) {
						zos.write(buf, 0, length);
					}
					subBis.close();
					subIs.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					continue;
				}
			}
			zos.closeEntry();
			zos.close();
			bos.close();
			os.close();
			return file;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 处理路径
	 * 
	 * @param dir
	 * @return
	 */
	private static String deployFoldDir(String dir) {
		if (dir != null && !"".equals(dir)) {
			dir = dir.replace("\\", "/");
			return dir.endsWith("/") ? dir : dir + "/";
		} else {
			return "";
		}
	}
}
