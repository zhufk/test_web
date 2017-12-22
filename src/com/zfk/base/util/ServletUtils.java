package com.zfk.base.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {
	private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);

	public static final long ONE_YEAR_SECONDS = 31536000L;

	public static final String KEY_STATIC_FILE = "static.file";

	public static final String KEY_VIEW_PREFIX = "view.prefix";

	public static final String ACTION_PAGE = "/page";

	private static final String[] STATIC_FILES = StringUtils.split(PropertyUtils.getString("static.file"), ",");

	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000L);

		response.setHeader("Cache-Control",
				new StringBuilder().append("private, max-age=").append(expiresSeconds).toString());
	}

	public static void setNoCacheHeader(HttpServletResponse response) {
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");

		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1L) && (lastModified < ifModifiedSince + 1000L)) {
			response.setStatus(304);
			return false;
		}
		return true;
	}

	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while ((!conditionSatisfied) && (commaTokenizer.hasMoreTokens())) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(304);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=\"")
					.append(encodedfileName).append("\"").toString());
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
	}

	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null", new Object[0]);
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap();
		String pre = prefix;
		if (pre == null) {
			pre = "";
		}
		while ((paramNames != null) && (paramNames.hasMoreElements())) {
			String paramName = (String) paramNames.nextElement();
			if (("".equals(pre)) || (paramName.startsWith(pre))) {
				String unprefixed = paramName.substring(pre.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0)) {
					values = new String[0];
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		StringBuilder queryStringBuilder = new StringBuilder();

		String pre = prefix;
		if (pre == null) {
			pre = "";
		}
		Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry) it.next();
			queryStringBuilder.append(pre).append((String) entry.getKey()).append("=").append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append("&");
			}
		}
		return queryStringBuilder.toString();
	}

	public static HttpServletRequest getRequest() {
		try {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
		}
		return null;
	}

	public static boolean isStaticFile(String uri) {
		if (STATIC_FILES == null) {
			logger.error("config.static.file.empty");
		}
		if (StringUtils.endsWithAny(uri, STATIC_FILES)) {
			return true;
		}
		return false;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.ServletUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */