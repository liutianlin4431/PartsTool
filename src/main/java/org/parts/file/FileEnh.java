package org.parts.file;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

/**
 * 文件增强工具
 * 
 * @author liuti
 *
 */
@SuppressWarnings("deprecation")
public class FileEnh {
	/**
	 * 文件读取缓冲最大限制
	 */
	private static final int BUFFER_SIZE = 8192;

	/**
	 * 规范路径隔离符（规范为当前系统的隔离符）
	 * 
	 * @param path 路径
	 * @return
	 */

	public static String specSeparator(String path) {
		return FilePartsTool.StandardPath(path);
	}

	/**
	 * 删除文件路径
	 * 
	 * @param filePath 文件路径
	 */
	public static void delPath(String filePath) {
		FilePartsTool.delPath(filePath);
	}

	/**
	 * 将文件读取为byte[]
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] fileToByte(File file) {
		return FilePartsTool.fileToByte(file);
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
				try {
					decoder.decode(bb);
				} catch (CharacterCodingException e) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断文件是否为文本文件
	 * 
	 * @param fbyte 文件流
	 * @param chs   文件字符集
	 * @return
	 */
	public static boolean isTextFile(byte[] fbyte, String chs) {
		Charset charset = Charset.forName(chs);
		try {
			CharsetDecoder decoder = charset.newDecoder();
			decoder.onMalformedInput(CodingErrorAction.REPORT);
			decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
			int offset = 0;
			while (offset < fbyte.length) {
				int length = Math.min(BUFFER_SIZE, fbyte.length - offset);
				byte[] buffer = Arrays.copyOfRange(fbyte, offset, offset + length);
				ByteBuffer bb = ByteBuffer.wrap(buffer, 0, length);
				try {
					decoder.decode(bb);
				} catch (CharacterCodingException e) {
					return false;
				}
				offset += length;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
