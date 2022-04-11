package org.parts.command;

import org.parts.file.FilePartsTool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * 本地命令执行工具类
 *
 * @author ltl
 */
public class LocalExecute {
    private static LocalExecute le = null;

    /**
     * 创建类（单例）
     *
     * @return
     */
    public static LocalExecute init() {
        if (le == null) {
            synchronized (LocalExecute.class) {
                if (le == null) {
                    le = new LocalExecute();
                }
            }
        }
        return le;
    }

    private LocalExecute() {
    }

    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    public Integer runCommand(String command) {
        try {
            command = FilePartsTool.StandardPath(command);// 统一执行命令斜杠方向
            Process pos = Runtime.getRuntime().exec(command);
            // input信息需要在error之前执行，否input信息会阻塞-如果一步执行不存在此问题
            printLog(pos.getInputStream());
            printLog(pos.getErrorStream());
            return pos.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        } finally {
        }
    }

    /**
     * 执行命令-超时摧毁
     *
     * @param cmd
     * @param timeout 在规定秒内结束
     * @return
     */
    public Integer runCommand(String cmd, Long timeout) {
        try {
            // 开始执行cmd命令
            Process pos = Runtime.getRuntime().exec(cmd);
            // input信息需要在error之前执行，否input信息会阻塞-如果异步执行不存在此问题
            printLog(pos.getInputStream());
            printLog(pos.getErrorStream());
            if (pos.waitFor(timeout, TimeUnit.SECONDS)) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            return 1;
        } finally {
        }
    }

    public void printLog(InputStream is) {
        InputStreamReader ir = null;
        BufferedReader input = null;
        try {
            ir = new InputStreamReader(is);
            input = new BufferedReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                System.out.println(ln);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (ir != null) {
                    ir.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }
}
