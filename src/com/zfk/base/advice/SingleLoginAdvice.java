package com.zfk.base.advice;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SingleLoginAdvice implements AfterReturningAdvice {
	protected static Logger log = LoggerFactory.getLogger(SingleLoginAdvice.class);

	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = "";
		if (request != null) {
			ip = request.getRemoteAddr();
		}
		System.out.println("ip==="+ip);
	}
}
