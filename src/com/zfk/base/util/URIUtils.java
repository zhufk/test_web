package com.zfk.base.util;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class URIUtils {
	public static final String HTTP = "http://";
	public static final String HTTPS = "https://";

	public static String getCurrentURL(String currentUrl, String contextName) {
		if ((StringUtils.isNotBlank(currentUrl)) && (currentUrl.startsWith(contextName + "/"))) {
			currentUrl = currentUrl.substring(currentUrl.indexOf(contextName) + contextName.length());
		}

		return currentUrl;
	}

	public static String getCurrentURI(String currentUrl, String contextName) {
		currentUrl = getCurrentURL(currentUrl, contextName);
		currentUrl = getTagURI(currentUrl);

		return currentUrl;
	}

	public static String getTagURI(String url) {
		if ((StringUtils.isNotBlank(url)) && (url.startsWith("/"))) {
			url = url.replaceFirst("/", "");
		}
		url = url.replaceAll("/", ":");
		return url;
	}

	public static String removeFirstAndLastSlash(String url) {
		if ((StringUtils.isNotBlank(url)) && (url.startsWith("/"))) {
			url = url.replaceFirst("/", "");
		}

		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 2);
		}

		return url;
	}

	public static List<String> getUrlMappings(Method method) {
		List<String> cUrls = new ArrayList<String>();
		RequestMapping cMapping = (RequestMapping) method.getDeclaringClass().getAnnotation(RequestMapping.class);
		RequestMapping mMapping = (RequestMapping) method.getAnnotation(RequestMapping.class);

		String[] cValues = null;
		if (cMapping != null) {
			cValues = cMapping.value();
		}
		String[] mValues = mMapping.value();
		for (String mv : mValues) {
			mv = removeFirstAndLastSlash(mv);
			String url = "/";
			if (cValues != null) {
				for (String cv : cValues) {
					cv = removeFirstAndLastSlash(cv);
					url = url + cv + "/" + mv;
					cUrls.add(url);
					url = "/";
				}
			} else {
				url = url + mv;
				cUrls.add(url);
			}
		}

		return cUrls;
	}

	public static String urlDecodeUTF8(String input) {
		try {
			return input == null ? null : URLDecoder.decode(input, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String urlEncodeUTF8(String input) {
		try {
			return input == null ? null : URLEncoder.encode(input, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getDomainName(String url) {
		if (StringUtils.isNotBlank(url)) {
			if (url.contains("https://")) {
				url = url.replaceFirst("https://", "");
			} else if (url.contains("http://")) {
				url = url.replaceFirst("http://", "");
			}

			url = url.substring(0, url.indexOf("/"));
		}

		return url;
	}

	public static String getUrl() {
		HttpServletRequest hreq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String url = hreq.getRequestURI();
		return url;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.URIUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */