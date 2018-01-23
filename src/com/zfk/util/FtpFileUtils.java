package com.zfk.util;

import com.zfk.base.util.PropertyUtils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * <p>Description: FTP文件上传工具类</p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2018/1/19
 */
public class FtpFileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FtpFileUtils.class);

    private static final String FTP_HOST_KEY = "ftp.address.host";
    private static final String FTP_PORT_KEY = "ftp.address.port";
    private static final String FTP_USERNAME_KEY = "ftp.address.username";
    private static final String FTP_PASSWORD_KEY = "ftp.address.password";
    private static final String IMAGE_PATH_IMAGE = "image.formal.path";


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String filename, InputStream input) {
        logger.info("uploadFile method filename: {}", filename);
        //FTP服务器hostname
        String host = PropertyUtils.getConfig(FTP_HOST_KEY);
        //FTP服务器端口
        int port = Integer.parseInt(PropertyUtils.getConfig(FTP_PORT_KEY));
        //FTP登录账号
        String username = PropertyUtils.getConfig(FTP_USERNAME_KEY);
        //FTP登录密码
        String password = PropertyUtils.getConfig(FTP_PASSWORD_KEY);
        //FTP服务器基础目录
        String basePath = PropertyUtils.getConfig(IMAGE_PATH_IMAGE);


        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器(如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器)
            ftp.connect(host, port);
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.info("ftp disconnect...");
                return result;
            }
            //切换到上传目录
            result = ftp.changeWorkingDirectory(basePath);
            if (result) {
                ftp.setBufferSize(1024);
                ftp.setControlEncoding("utf-8");
                //设置上传文件的类型为二进制类型
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                //上传文件
                result = ftp.storeFile(filename, input);
                logger.info("ftp upload file result: {}", result);
            }
        } catch (Exception e) {
            logger.error("uploadFile Exception: ", e);
        } finally {
            try {
                input.close();
                ftp.logout();
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException ioe) {
                logger.error("uploadFile IOException: ", ioe);
            }
        }
        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     *
     * @param fileName  要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String fileName, String localPath) {
        //FTP服务器hostname
        String host = PropertyUtils.getConfig(FTP_HOST_KEY);
        //FTP服务器端口
        int port = Integer.parseInt(PropertyUtils.getConfig(FTP_PORT_KEY));
        //FTP登录账号
        String username = PropertyUtils.getConfig(FTP_USERNAME_KEY);
        //FTP登录密码
        String password = PropertyUtils.getConfig(FTP_PASSWORD_KEY);
        //FTP服务器上的相对路径
        String remotePath = PropertyUtils.getConfig(IMAGE_PATH_IMAGE);


        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            // 转移到FTP服务器目录
            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());

                    OutputStream is = new FileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }

            ftp.logout();
            result = true;
        } catch (Exception e) {
            logger.error("downloadFile Exception: ", e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return result;
    }
}