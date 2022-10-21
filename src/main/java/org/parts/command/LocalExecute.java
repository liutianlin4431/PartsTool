package org.parts.command;

import org.parts.command.utils.ScriptOutput;
import org.parts.file.FilePartsTool;

import java.util.concurrent.*;

/**
 * 本地命令执行工具类
 *
 * @author ltl
 */
public class LocalExecute {
    private static LocalExecute le = null;
    private static ExecutorService executorSer = null;

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
            // 统一执行命令斜杠方向
            command = FilePartsTool.StandardPath(command);
            Process pos = Runtime.getRuntime().exec(command);
            // input信息需要在error之前执行，否input信息会阻塞-如果一步执行不存在此问题
            ScriptOutput.start(pos.getInputStream());
            ScriptOutput.start(pos.getErrorStream());
            return pos.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
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
        ExecutorService executorService = null;
        try {
            // 开始执行cmd命令
            Process pos = Runtime.getRuntime().exec(cmd);
            // 开启异步任务
            executorService = Executors.newSingleThreadExecutor();
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    // input信息需要在error之前执行，否input信息会阻塞-如果异步执行不存在此问题
                    ScriptOutput.start(pos.getInputStream());
                    ScriptOutput.start(pos.getErrorStream());
                    return pos.waitFor();
                }
            });
            if (timeout == null || timeout < 1) {
                pos.destroyForcibly();
                future.cancel(true);//结束异步监听
                return 1;
            }
            for (int i = 0; i > -1; i++) {
                TimeUnit.SECONDS.sleep(1);
                if (future.isDone()) {
                    return future.get();
                }
                if (i >= timeout) {
                    pos.destroyForcibly();
                    future.cancel(true);//结束异步监听
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            return 1;
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }
}
