package org.parts.command;

import org.parts.command.utils.ScriptOutput;
import org.parts.file.FileEnh;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
            // 统一执行命令斜杠方向
            command = FileEnh.specSeparator(command);
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
     * <br/>
     * 根据通常exe设定，返回0则为正常结束，非0则为异常结束
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
            if (timeout == null || timeout < 0) {
                pos.destroyForcibly();
                future.cancel(true);// 结束异步监听
                return 1;
            }
            //将秒转为毫秒
            timeout = timeout * 1000;
            for (long i = 0; i < timeout; ) {
                //睡眠200毫秒
                Thread.sleep(200);
                //判断程序是否还在执行
                if (future.isDone()) {
                    return future.get();
                }
                //累加已睡眠时间
                i += 200;
                //是否已经超时
                if (i >= timeout) {
                    pos.destroyForcibly();
                    future.cancel(true);// 结束异步监听
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
