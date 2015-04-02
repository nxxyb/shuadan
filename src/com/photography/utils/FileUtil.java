package com.photography.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具类（提供文件名、扩展名、文件编号等操作方法）
 *
 * @since 2011-9-9
 * @author Zhao Zhihong
 *
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class FileUtil {

	/**
	 * 取得不包含路径的文件名<br/>
	 * <p>
	 * <b>用法：</b>getFileName("c:\\projects\\temp.log") <br/>
	 * <b>结果： </b>temp.log
	 * </p>
	 * @param filename 包含路径在内的文件名
	 * @return 不包含所在路径的文件名
	 */
	public static String getFileName(String filename) {
		if(filename == null){
			throw new IllegalArgumentException("filename should not be null");
		}

		return getFileName(new File(filename));
	}

	/**
	 * 取得不包含路径的文件名<br/>
	 * <p>
	 * <b>用法：</b>getFileName(new File("c:\\projects\\temp.log")) <br/>
	 * <b>结果： </b>temp.log
	 * </p>
	 * @param file 文件
	 * @return 不包含所在路径的文件名
	 */
	public static String getFileName(File file) {
		if(file == null){
			throw new IllegalArgumentException("file should not be null");
		}

		return file.getName();
	}

	/**
	 * 取得文件所在完整路径<br/>
	 * <p>
	 * <b>用法：</b>getFilePath(new File("c:\\projects\\temp.log")) <br/>
	 * <b>结果： </b>c:\\projects
	 * </p>
	 * @param filename 包含路径在内的文件名
	 * @return 不包含所在路径的文件名
	 */
	public static String getFilePath(String filename) {
		if(filename == null){
			throw new IllegalArgumentException("filename should not be null");
		}

		return getFilePath(new File(filename));
	}

	/**
	 * 取得文件所在完整路径<br/>
	 * <p>
	 * <b>用法：</b>getFilePath(new File("c:\\projects\\temp.log")) <br/>
	 * <b>结果： </b>c:\\projects
	 * </p>
	 * @param file 文件
	 * @return 不包含所在路径的文件名
	 */
	public static String getFilePath(File file) {
		if(file == null){
			throw new IllegalArgumentException("file should not be null");
		}

		return file.getParent();
	}

	/**
	 * 取得不包含路径和扩展名的文件名<br/>
	 * <p>
	 * <b>用法：</b>getFileNameWithoutExtension("temp.log") <br/>
	 * <b>结果： </b>temp
	 * </p>
	 * @param filename 不包含所在路径的文件名
	 * @return 不包含所在路径和扩展名的文件名
	 */
	public static String getFileNameWithoutExtension(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException("filename with extension should not be null");
		}
		
		if (filename.lastIndexOf('.') == -1) {
			return filename;
		} else {
			return filename.substring(0, filename.lastIndexOf('.'));
		}
	}

	/**
	 * 取得不包含路径和扩展名的文件名<br/>
	 * <p>
	 * <b>用法：</b>getFileNameWithoutExtension(new File("c:\\projects\\temp.log")) <br/>
	 * <b>结果： </b>temp
	 * </p>
	 * @param file 文件
	 * @return 不包含所在路径和扩展名的文件名
	 */
	public static String getFileNameWithoutExtension(File file) {
		if(file == null){
			throw new IllegalArgumentException("file should not be null");
		}

		return getFileNameWithoutExtension(getFileName(file));
	}

	/**
	 * 取得文件名的扩展名<br/>
	 * <p>
	 * <b>用法：</b>getFileExtension("c:\\projects\\temp.log") <br/>
	 * <b>结果： </b>log
	 * </p>
	 * @param filename 文件名
	 * @return 文件的扩展名
	 */
	public static String getFileExtension(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException("filename should not be null");
		}

		if (filename.lastIndexOf('.') == -1){
			return null;
		} else {
			return filename.substring(filename.lastIndexOf('.') + 1, filename.length());
		}

	}

	/**
	 * 取得文件名的扩展名<br/>
	 * <p>
	 * <b>用法：</b>getFileExtension(new File("c:\\projects\\temp.log")) <br/>
	 * <b>结果： </b>log
	 * </p>
	 * @param file 文件
	 * @return 文件的扩展名
	 */
	public static String getFileExtension(File file) {
		String filename = getFileName(file);
		return getFileExtension(filename);
	}

	/**
	 * 替换文件的扩展名<br/>
	 * <p>
	 * <b>用法：</b>replaceFileExtension("c:\\projects\\temp.log") <br/>
	 * <b>结果： </b>log
	 * </p>
	 * @param file 文件
	 * @return 替换过扩展名后的文件名（不包含文件所在路径）
	 */
	public static String replaceFileExtension(String filename, String newExtension) {
		if (filename == null || filename.length() == 0) {
			return filename;
		}

		String extension = newExtension;

		if ('.' != newExtension.charAt(0)){
			extension = "." + newExtension;
		}

		return getFileNameWithoutExtension(filename) + extension;
	}

	/**
	 * 判断文件名是否重复，如果重复则自动在文件名后追加编号生成新文件名<br/>
	 * <p>
	 * <b>用法：</b>如果在C盘根目录已经存在temp.log文件，此时又想保存一个同名文件到C盘根目录，
	 * 这种情况可调用此方法getNumberedFileName("c:\\temp.log") <br/>
	 * <b>结果： </b>temp(1).log
	 * </p>
	 * @param filename 包含绝对路径文件名
	 * @param startNum 编号（用于内部递归，外部调用时不需要传入此参数）
	 * @return 包含绝对路径的文件名（文件名已经存在则自动补充编号）
	 */
	public static String getNumberedFileName(String filename, int... startNum) {
		File file = new File(filename);

		if (startNum.length == 0) {
			startNum = new int[1];
			startNum[0] = 0;
		}

		if (file.exists()) {
			startNum[0]++;
			String newFilename = getFileNameWithoutExtension(filename);
			if (newFilename.indexOf("(" + (startNum[0] - 1) + ")") != -1) {
				newFilename = newFilename.substring(0, newFilename.length() - ("(" + (startNum[0] - 1) + ")").length());
			}
			newFilename += "(" + startNum[0] + ")." + getFileExtension(filename);
			return getNumberedFileName(newFilename, startNum[0]);
		} else {
			return filename;
		}
	}

	/**
	 * 生成唯一的临时文件名，并不创建文件<br/>
	 * <p>
	 * <b>用法：</b>如果需要在系统临时目录生成唯一的文件名，可以调用此方法generateTemproryFile("xls")<br/>
	 * <b>结果： </b>%java.io.tmpdir%/{唯一UUID编号}.xls
	 * </p>
	 * @param ext 后缀名
	 * @return 完整的文件路径（系统临时目录+UUID+后缀）
	 */
	public static String generateTemproryFileName(String ext){
		Properties p = System.getProperties();
		String tempPath = p.getProperty("java.io.tmpdir");

		return tempPath + File.separator + UUID.randomUUID().toString() + "." + ext;
	}

	/**
	 * 生成唯一的临时文件名，并不创建文件<br/>
	 * <p>
	 * <b>用法：</b>如果需要生成唯一的文件名，可以调用此方法generateUniqueFileName("c:/temp",  "xls")<br/>
	 * <b>结果： </b>c:/temp/{唯一UUID编号}.xls
	 * </p>
	 * @param path 文件路径
	 * @param ext 后缀名
	 * @return 文件路径+UUID+后缀
	 */
	public static String generateUniqueFileName(String path, String ext){
		return path + File.separator + UUID.randomUUID().toString() + "." + ext;
	}
	
	/**
	 * 获得classes目录中的文件完整路径<br/>
	 * <p>
	 * <b>用法：</b>如果需要获得classes目录中的文件完整路径，可以调用此方法getRealPath("/com/tdqs/core/ABC.properties")<br/>
	 * <b>结果： </b>${base_dir}/classes/com/tdqs/core/ABC.properties
	 * </p>
	 * @param classpath 相对于classes目录的文件路径
	 * @return 完整路径
	 */
	public static String getRealPath(String classpath) throws URISyntaxException {
		return FileUtil.class.getResource(classpath).toURI().getPath();
	}

	/**
	 * 删除文件或文件夹，忽略结果
	 * @param file 文件或文件夹
	 *
	 * @author Zhao Zhihong
	 */
	public static void deleteFile(File file){
		if (!file.exists()){
			return;
		}

		if (!file.isDirectory()){
			file.delete();
		} else {
			File[] children = file.listFiles();

			if (children != null){
				//删除所有文件和子文件夹
				for (File child : children){
					deleteFile(child);
				}
			}

			file.delete();
		}
	}
	
	/**
	 * 保存文件
	 * @param filePath 文件路径
	 * @param file     文件
	 * @throws Exception
	 * @author 徐雁斌
	 */
	public static void saveFile(String filePath,MultipartFile file) throws Exception{
    	File sourceFile = new File(filePath);
    	if(!sourceFile.exists()){
    		sourceFile.mkdir();
    	}
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath, file.getOriginalFilename()));
	}
}
