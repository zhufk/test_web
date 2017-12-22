package com.zfk.base.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

public class RequestParamUtils {
	private static final Logger logger = LoggerFactory.getLogger(RequestParamUtils.class);

	public static Map<String, Object> requestParamsConvertor(HttpServletRequest request, Method method, Object[] params)
			throws Exception {
		Map<String, Object> paramsMap = new HashMap();

		Enumeration<String> dispParamNames = request.getParameterNames();

		if (dispParamNames.hasMoreElements()) {
			while (dispParamNames.hasMoreElements()) {
				String dispParamName = (String) dispParamNames.nextElement();
				paramsMap.put(dispParamName, request.getParameter(dispParamName));
			}
			return paramsMap;
		}
		Annotation[][] annotations = method.getParameterAnnotations();
		if (annotations != null) {
			for (int i = 0; i < annotations.length; i++) {
				for (int j = 0; j < annotations[i].length; j++) {
					Annotation annotation = annotations[i][j];
					if ((annotation != null) && ((annotation instanceof RequestBody))) {
						if ((params[i] instanceof Map)) {
							paramsMap = (Map) params[i];
							break;
						}
						try {
							ConvertUtils.objectToMap(params[i], paramsMap);
						} catch (Exception e) {
							logger.error(" RequestParamUtils 转换异常 params[i]=" + params[i]);
							return null;
						}
					}
				}
			}
		}

		return paramsMap;
	}

	private static String requestParamStrHandler(HttpServletRequest request) throws IOException {
		String submitMehtod = request.getMethod();

		if (submitMehtod.equals("GET"))
			return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
		if (submitMehtod.equals("POST")) {
			return getRequestPostStr(request);
		}

		return null;
	}

	private static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte[] buffer = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		request.getInputStream().close();
		return buffer;
	}

	private static String getRequestPostStr(HttpServletRequest request) throws IOException {
		byte[] buffer = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		return new String(buffer, charEncoding);
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.RequestParamUtils JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */