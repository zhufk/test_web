package com.zfk.base.advice;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.zfk.base.util.PropertyUtils;

public class MetaDataAdvice implements MethodBeforeAdvice {
	private static final String PUBLIC_PATH = "publicPath";
	private static final String APP_PATH = "appPath";
	private static final String SESSION_ID = "sessionId";

	public void before(Method method, Object[] params, Object arg) throws Throwable {
		HttpServletRequest hreq = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String publicPath = PropertyUtils.getConfig("publicPath");
		hreq.setAttribute("publicPath", publicPath);
		String appPath = PropertyUtils.getConfig("appPath");
		hreq.setAttribute("appPath", appPath);
		// hreq.setAttribute("sessionId", sessionId);
		System.out.println("publicPath==="+publicPath);
	}
}