package com.zfk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.zfk.base.constant.CharsConsts;
import com.zfk.base.constant.WayConsts;
import com.zfk.base.entity.ResultData;
import com.zfk.base.exceptoin.UncheckedException;
import com.zfk.base.util.DateUtils;
import com.zfk.base.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/**
 * <p>
 * Description: 文件上传工具类
 * </p>
 *
 * @author evan
 * @version 1.0.0
 *          <p>
 *          Company:workway
 *          </p>
 *          <p>
 *          Copyright:Copyright(c)2017-2018
 *          </p>
 * @date 2017/11/14
 */
public final class FileUploadUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtils.class);

	/**
	 * 保存文件或者图片
	 *
	 * @param request
	 * @param oldFileName
	 *            表示已有文件名称
	 * @param type
	 *            1:表示图片，2：表示文件
	 * @return
	 */
	public static final ResultData<String> saveFileOrImage(HttpServletRequest request, String oldFileName, int type)
			throws IOException {
		LOGGER.info("saveFileOrImage param oldFileName: {},type: {}", oldFileName, type);
		ResultData<String> resultData = new ResultData<String>();

		long startTime = System.currentTimeMillis();
		String imageName = "";
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {

			LOGGER.info("正在进行文件上传处理......");

			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String fileName = file.getOriginalFilename();
					if (StringUtils.isBlank(fileName) && StringUtils.isBlank(oldFileName)) {
						LOGGER.info("没有上传文件...");
						return new ResultData<String>(false, (1 == type ? "请上传图片！" : "请上传文件！"));
					} else if (StringUtils.isBlank(fileName) && StringUtils.isNotBlank(oldFileName)) {
						LOGGER.info("已有文件，文件名称：{}", oldFileName);
						return new ResultData<String>(true);
					} else {
						if (StringUtils.isNotBlank(oldFileName) && fileName.equals(oldFileName)) {
							LOGGER.info("已存在的文件或图片，原文件名：{}, 新文件名: {}", new Object[] { oldFileName, fileName });
							return new ResultData<String>(true);
						}
					}
					imageName = getRandomName(fileName);
					String path = (1 == type) ? (WayConsts.IMAGE_PATH + imageName) : (WayConsts.FILE_PATH + imageName);

					LOGGER.info("文件原名称：{}, 文件新名称：{}, 存放路径：{}", fileName, imageName, path);

					// 上传
					file.transferTo(createFile(path));
				}

			}

		}
		long endTime = System.currentTimeMillis();
		LOGGER.info("上传文件所需时间：{}ms", (endTime - startTime));

		resultData.setSuccess(true);
		resultData.setData(imageName);

		return resultData;
	}

	/**
	 * 以精确到秒的当前时间戳生成随机文件名。
	 *
	 * @param fileName
	 *            已有文件名
	 * @return 返回生成的随机文件名。
	 */
	public static String getRandomName(String fileName) {
		return DateUtils.formatDate(new Date(), DateUtils.MILLISECOND_N) + getFileType(fileName);
	}

	/**
	 * 从带有类型后缀的文件名中获取文件名的类型后缀。
	 *
	 * @param fileName
	 *            带有类型后缀的文件名
	 * @return 返回文件名的类型后缀。
	 */
	public static String getFileType(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 根据文件的完整路径创建一个新文件。如果目录不存在时先创建目录再创建文件。
	 *
	 * @param filePath
	 *            文件完整路径
	 * @return 返回创建的File文件对象。
	 */
	public static File createFile(String filePath) {
		try {
			File fileDir = new File(getFullFileDir(filePath));
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}

			File file = new File(filePath);
			file.createNewFile();
			return file;
		} catch (IOException e) {
			throw new UncheckedException("创建文件时发生错误。", e);
		}
	}

	/**
	 * 从文件完整路径中截取完整的文件目录。
	 *
	 * @param filePath
	 *            文件完整路径
	 * @return 返回从文件完整路径中截取完整的文件目录。
	 */
	public static String getFullFileDir(String filePath) {
		int lastSlashIndex = filePath.lastIndexOf(CharsConsts.SLASH);
		int lastBackSlashIndex = filePath.lastIndexOf(CharsConsts.BACKSLASH);
		// 如果没找到斜杠和反斜杠则返回空字符串
		// 如果斜杠位置在反斜杠位置之后则从斜杠位置截取文件目录
		// 否则从反斜杠位置截取文件目录
		if (lastSlashIndex == -1 && lastBackSlashIndex == -1) {
			return "";
		} else if (lastSlashIndex > lastBackSlashIndex) {
			return StringUtils.substringBeforeLast(filePath, CharsConsts.SLASH);
		} else {
			return StringUtils.substringBeforeLast(filePath, CharsConsts.BACKSLASH);
		}
	}

	/**
	 * @param filename
	 *            文件的原始名称
	 * @return uuid+"_"+文件的原始名称 1
	 * @Method: makeFileName
	 * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 */
	private static String makeFileName(String filename) { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}

	/**
	 * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 *
	 * @param filename
	 *            文件名，要根据文件名生成存储目录
	 * @param savePath
	 *            文件存储路径
	 * @return 新的存储目录
	 * @Method: makePath
	 * @Description:
	 */
	private static String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		// 0--15
		int dir1 = hashcode & 0xf;
		// 0-15
		int dir2 = (hashcode & 0xf0) >> 4;
		// 构造新的保存目录 upload\2\3 upload\3\5
		String dir = savePath + "\\" + dir1 + "\\" + dir2;
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}
}