package com.zfk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zfk.base.entity.ResultData;
import com.zfk.entity.User;
import com.zfk.util.FileUploadUtils;

@Controller
@RequestMapping("/file")
public class FileController {

	@RequestMapping({ "addUser" })
	@ResponseBody
	public Map<String, Object> addUser(HttpServletRequest request, String name, String age) {
		// 把带二进制表单数据的request对象交给spring转换 得到一个文件和普通数据分开的新request对象
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		System.out.println("getServletPath()==" + request.getServletPath());// fileC/addUser
		
		System.out.println("getContextPath()==" + request.getContextPath());// test_web
		System.out.println("getServletContext().getContextPath()==" + request.getServletContext().getContextPath());// test_web
		
		System.out.println("getServletContext()==" + request.getServletContext());//org.apache.catalina.core.ApplicationContextFacade
		System.out.println("getSession().getServletContext()==" + request.getSession().getServletContext());//org.apache.catalina.core.ApplicationContextFacade

		// 获取from表单参数
		// String name = multipartRequest.getParameter("name");
		// String age = multipartRequest.getParameter("age");

		System.out.println("name===" + name);
		System.out.println("age===" + age);

		// 获得Request中的图片 photo 是from表单文件的name
		List<MultipartFile> fileList = multipartRequest.getFiles("file");
		for (MultipartFile file : fileList) {
			if (!file.isEmpty()) {
				// 对素材进行操作
				System.out.println("Name###" + file.getName());
				System.out.println("ContentType###" + file.getContentType());
				System.out.println("OriginalFilename" + file.getOriginalFilename());
				System.out.println("Size###" + file.getSize());
				// 保存文件
				// file.transferTo(dest);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "true");
		map.put("msg", "添加照片成功！");
		return map;
	}
	
	@RequestMapping({ "addUser2" })
	@ResponseBody
	public Map<String, Object> addUser2(HttpServletRequest request, User user) {
		// 把带二进制表单数据的request对象交给spring转换 得到一个文件和普通数据分开的新request对象
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		System.out.println("getContextPath()==" + request.getContextPath());// test_web
		System.out.println("getServletPath()==" + request.getServletPath());// fileC/addUser
		System.out.println("getServletContext()==" + request.getServletContext());
		System.out.println("getServletContext().getContextPath()==" + request.getServletContext().getContextPath());// test_web
		System.out.println("getSession().getServletContext()==" + request.getSession().getServletContext());

		// 获取from表单参数
		// String name = multipartRequest.getParameter("name");
		// String age = multipartRequest.getParameter("age");

		System.out.println("name===" + user.getName());
		System.out.println("age===" + user.getAge());

		// 获得Request中的图片 photo 是from表单文件的name
		List<MultipartFile> fileList = multipartRequest.getFiles("file");
		for (MultipartFile file : fileList) {
			if (!file.isEmpty()) {
				// 对素材进行操作
				System.out.println("Name###" + file.getName());
				System.out.println("ContentType###" + file.getContentType());
				System.out.println("OriginalFilename" + file.getOriginalFilename());
				System.out.println("Size###" + file.getSize());
				// 保存文件
				// file.transferTo(dest);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", "true");
		map.put("msg", "添加照片成功！");
		return map;
	}

	@RequestMapping({ "upload_image" })
	@ResponseBody
	public ResultData uploadImage(HttpServletRequest request) throws Exception {
		ResultData result = FileUploadUtils.saveFileOrImage(request, null, 1);
		return result;
	}

	@RequestMapping({ "upload_file" })
	@ResponseBody
	public ResultData uploadFile(HttpServletRequest request) throws Exception {
		ResultData result = FileUploadUtils.saveFileOrImage(request, null, 2);
		return result;
	}
}
