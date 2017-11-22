package com.zfk.util;

import com.zfk.base.constant.EncodingConsts;
import com.zfk.base.constant.MeasureConsts;
import com.zfk.base.util.FileUtils;
import com.zfk.base.util.PropertyUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * <p>Description: </p>
 *
 * @author evan
 * @version 1.0.0
 * <p>Company:workway</p>
 * <p>Copyright:Copyright(c)2017-2018</p>
 * @date 2017/11/14
 */
public class FileUploadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtils.class);

    public static boolean saveFile(HttpServletRequest request) throws FileUploadException, IOException {

        //得到上传文件的保存目录
        String savePath = PropertyUtils.getString("file.formal.path");
        //上传时生成的临时文件保存目录
        String tempPath = PropertyUtils.getString("file.temp.path");
        File tmpFile = FileUtils.createFile(tempPath);

        //1、创建一个DiskFileItemFactory工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
        //设置缓冲区的大小
        factory.setSizeThreshold(MeasureConsts.K * 10);
        //设置上传时生成的临时文件的保存目录
        factory.setRepository(tmpFile);
        //2、创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            @Override
            public void update(long pBytesRead, long pContentLength, int arg2) {
                LOGGER.info("文件大小为：{} ,当前已处理：{}", new Object[]{pContentLength, pBytesRead});
                /**
                 * 文件大小为：14608,当前已处理：4096
                 *文件大小为：14608,当前已处理：7367
                 *文件大小为：14608,当前已处理：11419
                 *文件大小为：14608,当前已处理：14608
                 */
            }
        });

        //解决上传文件名的中文乱码
        upload.setHeaderEncoding(EncodingConsts.UTF_8);
        //3、判断提交上来的数据是否是上传表单的数据
        if (!ServletFileUpload.isMultipartContent(request)) {
            //按照传统方式获取数据
            return false;
        }
        //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
        upload.setFileSizeMax(MeasureConsts.M);
        //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为3MB
        upload.setSizeMax(MeasureConsts.M * 3);

        //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            //如果fileitem中封装的是普通输入项的数据
            if (item.isFormField()) {
                continue;
            } else {
                //如果fileitem中封装的是上传文件
                //得到上传的文件名称，
                String filename = item.getName();

                LOGGER.info(filename);

                if (StringUtils.isEmpty(filename)) {
                    continue;
                }

                //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                filename = filename.substring(filename.lastIndexOf("\\") + 1);
                //得到上传文件的扩展名
                String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);

                //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                LOGGER.info("上传的文件的扩展名是：" + fileExtName);

                //获取item中的上传文件的输入流
                InputStream in = item.getInputStream();
                //得到文件保存的名称
                String saveFilename = makeFileName(filename);
                //得到文件的保存目录
                String realSavePath = makePath(saveFilename, savePath);
                //创建一个文件输出流
                FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFilename);
                //创建一个缓冲区
                byte[] buffer = new byte[MeasureConsts.K];
                //判断输入流中的数据是否已经读完的标识
                int len = 0;
                //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                while ((len = in.read(buffer)) > 0) {
                    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    out.write(buffer, 0, len);
                }
                //关闭输入流
                in.close();
                //关闭输出流
                out.close();
                //删除处理文件上传时生成的临时文件
                item.delete();

            }
        }

        return true;
    }


    /**
     * @param filename 文件的原始名称
     * @return uuid+"_"+文件的原始名称
     * 1
     * @Method: makeFileName
     * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
     */
    private static String makeFileName(String filename) {  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }

    /**
     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
     *
     * @param filename 文件名，要根据文件名生成存储目录
     * @param savePath 文件存储路径
     * @return 新的存储目录
     * @Method: makePath
     * @Description:
     */
    private static String makePath(String filename, String savePath) {
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        //0--15
        int dir1 = hashcode & 0xf;
        //0-15
        int dir2 = (hashcode & 0xf0) >> 4;
        //构造新的保存目录 upload\2\3  upload\3\5
        String dir = savePath + "\\" + dir1 + "\\" + dir2;
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        return dir;
    }
}