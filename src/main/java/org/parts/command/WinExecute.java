package org.parts.command;

import org.apache.http.client.config.AuthSchemes;
import org.parts.file.FileEnh;

import cn.hutool.core.lang.Console;
import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;
import io.cloudsoft.winrm4j.winrm.WinRmToolResponse;

/**
 * 远程win执行；默认端口5985
 *
 * @author ltl
 */
@SuppressWarnings("deprecation")
public class WinExecute {
	private static WinExecute le = null;

	/**
	 * 创建类（单例）
	 *
	 * @return
	 */
	public static WinExecute init() {
		if (le == null) {
			synchronized (WinExecute.class) {
				if (le == null) {
					le = new WinExecute();
				}
			}
		}
		return le;
	}

	private WinExecute() {
	}

	public Integer sshExecute(String host, String user, String pwd, Integer port, String command) {
		WinRmClientContext context = WinRmClientContext.newInstance();
		try {
			Console.error("ERROR:{}",
					new Exception("此处执行时，已经将命令中的分隔符改为当前服务所在系统的分隔符。如果服务在linux系统时，则分隔符改为了\"/\"，可能存在win命令执行失败的问题"));
			command = FileEnh.specSeparator(command);// 统一执行命令斜杠方向
			WinRmTool tool = WinRmTool.Builder.builder(host, user, pwd).setAuthenticationScheme(AuthSchemes.NTLM)
					.port(port).useHttps(false).context(context).build();
			tool.setOperationTimeout(5000L);
			WinRmToolResponse resp = tool.executeCommand(command);
			return resp.getStatusCode();
		} catch (Exception e) {
			Console.error(e);
			return -1;
		} finally {
			context.shutdown();
		}
	}
}
