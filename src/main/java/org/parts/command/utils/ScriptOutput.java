package org.parts.command.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 脚本运行输出
 *
 * @author liutianlin
 * @create 2022/6/6
 */
public class ScriptOutput extends Thread {
    private Integer ln = 0;
    private boolean end = false;
    private InputStream is;
    private String encoded = "GBK";
    private List<String> consoleInfo = new ArrayList<>();

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
        return String.join("\n", this.getConsoleInfoList());
    }

    /**
     * 获取控制台信息列表
     *
     * @return
     */
    public List<String> getConsoleInfoList() {
        while (!end) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.consoleInfo;
    }

    /**
     * 是否继续
     *
     * @return
     */
    public boolean next() {
        if (ln < consoleInfo.size()) {
            return true;
        } else if (ln >= consoleInfo.size()) {
            while (true) {
                try {
                    if (ln < consoleInfo.size()) {
                        return true;
                    } else if (ln >= consoleInfo.size() && end) {
                        return false;
                    }
                    TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 读取最新一行
     *
     * @return
     */
    public String readLine() {
        ln++;
        return this.consoleInfo.get(ln - 1);
    }

    /**
     * 读取输入流内容
     */
    @Override
    public void run() {
        try {
            end = false;
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(bis, encoded));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("")) if (line.contains("\n")) {
                    String lineArray[] = line.split("\n");
                    for (String one : lineArray) {
                        one = one.trim();
                        if (!one.equals("")) {
                            consoleInfo.add(one);
                        }
                    }
                } else consoleInfo.add(line);
            }
            end = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            end = true;
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
