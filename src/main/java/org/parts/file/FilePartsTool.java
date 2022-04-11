package org.parts.file;

import org.parts.command.LocalExecute;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;

public class FilePartsTool {
    private final static String os_name = System.getProperty("os.name");

    /**
     * 将文件分隔符转换为当前系统分隔符
     *
     * @param path
     * @return
     */
    public static String StandardPath(String path) {
        path = path.replaceAll("\\{1,}", Matcher.quoteReplacement(File.separator));
        path = path.replaceAll("/+", Matcher.quoteReplacement(File.separator));
        path = path.replaceAll(Matcher.quoteReplacement(File.separator) + "+", Matcher.quoteReplacement(File.separator));
        return path;
    }

    /**
     * 删除当前路径
     *
     * @param path
     */
    public static void delPath(String path) {
        if (path != null) {
            if (os_name.toLowerCase().contains("windows")) {
                File file = new File(path);
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File son : files) {
                        if (son.isDirectory()) {
                            delPath(son.getAbsolutePath());
                            son.delete();
                        } else {
                            son.delete();
                        }
                    }
                    file.delete();
                } else {
                    file.delete();
                }
            } else {
                LocalExecute.init().runCommand("rm -rf " + path);
            }
        }
    }

    /**
     * File转byte[]
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] fileToByte(File file) {
        byte[] bytes = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
