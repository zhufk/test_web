package com.zfk.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static String getCookie(String key, HttpServletRequest request) {
		String value = null;
		if (request != null) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equalsIgnoreCase(key)) {
						value = c.getValue();
						break;
					}
				}
			}
		}
		return value;
	}

	public static void setCookie(String key, String value, int timeOut, HttpServletResponse response) {
		if (response != null) {
			Cookie cookie = new Cookie(key, value);
			cookie.setPath("/");
			cookie.setMaxAge(timeOut);
			response.addCookie(cookie);
		}
	}

	public static void clearCookie(String key, HttpServletRequest request, HttpServletResponse response) {
		if ((request == null) || (response == null)) {
			return;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return;
		}
		for (Cookie c : cookies) {
			if (key.equalsIgnoreCase(c.getName())) {
				c.setMaxAge(0);
				response.addCookie(c);
				break;
			}
		}
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.CookieUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */