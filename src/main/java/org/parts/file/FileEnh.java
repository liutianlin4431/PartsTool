package org.parts.file;

/**
 * 文件增强工具
 * 
 * @author liuti
 *
 */
@SuppressWarnings("deprecation")
public class FileEnh extends FilePartsTool {

	/**
	 * 规范路径隔离符（规范为当前系统的隔离符）
	 * 
	 * @param path 文本路径
	 * @return
	 */
	public static String specSeparator(String path) {
		return StandardPath(path);
	}
}
