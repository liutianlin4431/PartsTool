package org.parts.command.utils;

import cn.hutool.core.lang.Console;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 脚本运行输出
 *
 * @author liutianlin
 * @create 2022/6/6
 */
public class ScriptOutput extends Thread {
    private InputStream is;
    private String encoded = "GBK";

    private ScriptOutput(InputStream is) {
        this.is = is;
    }

    private ScriptOutput(InputStream is, String encoded) {
        this.is = is;
        this.encoded = encoded;
    }

    public static Thread start(InputStream is) {
        Thread thread = new ScriptOutput(is);
        thread.start();
        return thread;
    }

    public static Thread start(InputStream is, String encoded) {
        Thread thread = new ScriptOutput(is, encoded);
        thread.start();
        return thread;
    }

    /**
     * 待测试，真的能停止线程么？
     *
     * @param infoStreamThread
     */
    public static void stop(Thread... infoStreamThread) {
        if (infoStreamThread != null && infoStreamThread.length > 0) {
            for (Thread t : infoStreamThread) {
                t.interrupt();
            }
        }
    }

    /**
     * 获取控制台信息
     *
     * @param is
     * @return
     */
    public static String getConsoleInfo(InputStream is) {
        return new ScriptOutput(is).readInputStream();
    }

    @Override
    public void run() {
        Console.log(this.readInputStream());
    }

    /**
     * 读取输入流内容
     *
     * @return
     */
    public String readInputStream() {
        try {
            StringBuffer sb = new StringBuffer();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(bis, encoded));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("")) sb.append(line + "\n");
                System.out.println(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
