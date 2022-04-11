package org.parts.command;

import com.jcraft.jsch.*;
import org.parts.file.FilePartsTool;

import java.io.*;

/**
 * 远程linux工具类
 *
 * @author ltl
 */
public class LinuxExecute {
    private static LinuxExecute le = null;

    /**
     * 创建类（单例）
     *
     * @return
     */
    public static LinuxExecute init() {
        if (le == null) {
            synchronized (LinuxExecute.class) {
                if (le == null) {
                    le = new LinuxExecute();
                }
            }
        }
        return le;
    }

    private LinuxExecute() {
    }

    /**
     * 跨服务器执行linux命令； 使用jsch时密码不能为空； 当不使用jsch时，命令自动添加ssh命令，不可传入开头为ssh的命令语句；
     *
     * @param host    ip地址
     * @param user    用户
     * @param pwd     密码
     * @param port    端口
     * @param command 语句
     * @return -1:则为try{}catch{}异常；
     */
    public Integer sshExecute(String host, String user, String pwd, Integer port, String command) {
        StringBuffer sb = new StringBuffer();
        Session session = null;
        Channel channel = null;
        try {
            command = FilePartsTool.StandardPath(command);// 统一执行命令斜杠方向
            session = getSession(host, user, pwd, port);
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            InputStream in = channel.getInputStream();
            channel.connect();
            int nextChar;
            while ((nextChar = in.read()) != -1) {
                sb.append((char) nextChar);
            }
            System.out.println(sb.toString());
            channel.disconnect();
            session.disconnect();
            return channel.getExitStatus();
        } catch (Exception e) {
            System.err.println("错误信息为：" + e.getMessage());
            return -1;
        } finally {
            closeSSH(session, channel, null);
        }

    }

    /**
     * 文件上传;使用jsch时密码不能为空； 当不使用jsch时，命令自动添加ssh命令，不可传入开头为ssh的命令语句；
     *
     * @param host       ip地址
     * @param user       用户
     * @param pwd        密码
     * @param port       端口
     * @param directory  上传目录
     * @param uploadFile 要上传的文件路径
     */

    public boolean sshUpload(String host, String user, String pwd, Integer port, String directory, String uploadFile) {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSession(host, user, pwd, port);
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            try {
                channelSftp.cd(directory);
            } catch (SftpException sException) {
                // 指定上传路径不存在
                if (ChannelSftp.SSH_FX_NO_SUCH_FILE == sException.id) {
                    channelSftp.mkdir(directory);// 创建目录
                    channelSftp.cd(directory); // 进入目录
                }
            }
            File file = new File(uploadFile);
            channelSftp.put(new FileInputStream(file), file.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeSSH(session, channel, channelSftp);
        }
    }

    /**
     * @param host       ip地址
     * @param user       用户
     * @param pwd        密码
     * @param port       端口
     * @param remotePath 远程文件根路径
     * @param remoteFile 远程文件全路径
     * @param localFile  本地存放路径
     * @return
     */
    public boolean sshDownload(String host, String user, String pwd, Integer port, String remotePath, String remoteFile, String localFile) {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            session = getSession(host, user, pwd, port);
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            String rootPath = localFile.substring(0, localFile.lastIndexOf(File.separator));
            File rootPathFile = new File(rootPath);
            if (!rootPathFile.exists()) {
                rootPathFile.mkdirs();
            }
            File file = new File(localFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream output = new FileOutputStream(file);
            channelSftp.cd(remotePath);
            channelSftp.get(remoteFile, output);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            closeSSH(session, channel, channelSftp);
        }
    }

    /**
     * 获取SSH中的Session连接
     *
     * @param host ip地址
     * @param user 用户
     * @param pwd  密码
     * @param port 端口
     * @return
     */
    public Session getSession(String host, String user, String pwd, Integer port) throws Exception {
        JSch jsch = new JSch();
        jsch.getSession(user, host, port);
        Session session = jsch.getSession(user, host, port);
        session.setPassword(pwd);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    /**
     * 关闭SSH连接
     *
     * @param session
     * @param channel
     * @param channelSftp
     */
    private void closeSSH(Session session, Channel channel, ChannelSftp channelSftp) {
        if (session != null) {
            session.disconnect();
        }
        if (channel != null) {
            channel.disconnect();
        }
        if (channelSftp != null) {
            channelSftp.disconnect();
        }
    }

}
