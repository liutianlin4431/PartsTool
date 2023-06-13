package org.parts.command.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * 脚本运行输出
 *
 * @author liutianlin
 * @create 2022/6/6
 */
public class ScriptOutput extends Thread {
    private InputStream is;
    private String encoded = "GBK";
    private StringBuffer consoleInfo = new StringBuffer();

    private ScriptOutput(InputStream is) {
        this.is = is;
    }

    private ScriptOutput(InputStream is, String encoded) {
        this.is = is;
        this.encoded = encoded;
    }

    public static ScriptOutput start(InputStream is) {
        ScriptOutput so = new ScriptOutput(is);
        so.start();
        return so;
    }

    public static ScriptOutput start(InputStream is, String encoded) {
        ScriptOutput so = new ScriptOutput(is, encoded);
        so.start();
        return so;
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
     * @return
     */
    public String getConsoleInfo() {
        int max = 60480000;
        int i = 0;
        while (this.isAlive()) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
            if (i > max) break;
        }
        return this.consoleInfo.toString();
    }

    /**
     * 获取控制台信息列表
     *
     * @return
     */
    public List<String> getConsoleInfoList() {
        String ci = this.getConsoleInfo();
        return Arrays.asList(ci.split("\n"));
    }

    @Override
    public void run() {
        this.readInputStream();
    }

    /**
     * 读取输入流内容
     *
     * @return
     */
    public String readInputStream() {
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(bis, encoded));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("")) consoleInfo.append(line + "\n");
            }
            return consoleInfo.toString();
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
