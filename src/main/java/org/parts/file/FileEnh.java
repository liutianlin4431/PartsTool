package org.parts.file;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

/**
 * 文件增强工具
 * 
 * @author liuti
 *
 */
@SuppressWarnings("deprecation")
public class FileEnh extends FilePartsTool {
	/**
	 * 文件读取缓冲最大限制
	 */
	private static final int BUFFER_SIZE = 8192;

	/**
	 * 规范路径隔离符（规范为当前系统的隔离符）
	 * 
	 * @param path 文本路径
	 * @return
	 */
	public static String specSeparator(String path) {
		return StandardPath(path);
	}

	/**
	 * 判断文件是否为文本文件
	 * 
	 * @param file 文件
	 * @param chs  文件字符集
	 * @return
	 */
	public static boolean isTextFile(File file, String chs) {
		Charset charset = Charset.forName(chs);
		try (FileInputStream fis = new FileInputStream(file)) {
			CharsetDecoder decoder = charset.newDecoder();
			decoder.onMalformedInput(CodingErrorAction.REPORT);
			decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
			byte[] buffer = new byte[BUFFER_SIZE];
			int n = 0;
			while ((n = fis.read(buffer)) != -1) {
				ByteBuffer bb = ByteBuffer.wrap(buffer, 0, n);
				decoder.decode(bb);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
