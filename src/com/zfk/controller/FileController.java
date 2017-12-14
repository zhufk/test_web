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

@Controller
@RequestMapping("/fileC")
public class FileController {
	
	
	@RequestMapping(value = "addUser")
	@ResponseBody
	public Map<String, Object> addUser(HttpServletRequest request) {
		 //把带二进制表单数据的request对象交给spring转换 得到一个文件和普通数据分开的新request对象
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    
	    System.out.println("getContextPath=="+request.getContextPath());//test_web
	    System.out.println("getServletPath=="+request.getServletPath());//fileC/addUser
	    System.out.println("getServletContext=="+request.getServletContext().getContextPath());//test_web
	    
	    // 获取from表单参数
	    String name = multipartRequest.getParameter("name");
	    String age = multipartRequest.getParameter("age");
	    
	    System.out.println("name==="+name);
	    System.out.println("age==="+age);
 		
 	    //获得Request中的图片 photo 是from表单文件的name
	    List<MultipartFile> fileList = multipartRequest.getFiles("file");          
	    for (MultipartFile mf : fileList) {  
            if(!mf.isEmpty()){
            	// 对素材进行操作
	            System.out.println("Name###"+mf.getName());
	            System.out.println("ContentType###"+mf.getContentType());
	            System.out.println("OriginalFilename"+mf.getOriginalFilename());
	            System.out.println("Size###"+mf.getSize());
	            //保存文件
	            //mf.transferTo(dest);
            }
        }
	    
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("success", "true");
		map.put("msg", "添加照片成功！");
		return map;
	}
}
