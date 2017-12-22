package com.zfk.base.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.zfk.base.exceptoin.UncheckedException;

@Component
@Lazy(false)
public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) {
		context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static <T> T getBean(String beanName) {
		return (T) context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> beanType) {
		return context.getBean(beanType);
	}

	public static List<Resource> getResourcesByWildcard(String... wildcardResourcePaths) {
		List<Resource> resources = new ArrayList<Resource>();
		try {
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			for (String basename : wildcardResourcePaths) {
				for (Resource resource : resourcePatternResolver.getResources(basename)) {
					resources.add(resource);
				}
			}
		} catch (Exception e) {
			throw new UncheckedException("获取资源列表时反生异常。", e);
		}
		return resources;
	}

	public static List<String> getResourcePathsByWildcard(String resourceDir, String... wildcardResourcePaths) {
		List<String> resourcePaths = new ArrayList<String>();
		try {
			for (Resource resource : getResourcesByWildcard(wildcardResourcePaths)) {
				String uri = resource.getURI().toString();
				if (((resource instanceof FileSystemResource)) || ((resource instanceof UrlResource))) {
					String resourcePath = "classpath:" + resourceDir + StringUtils.substringAfter(uri, resourceDir);
					resourcePaths.add(resourcePath);
				}
			}
		} catch (Exception e) {
			throw new UncheckedException("获取资源文件时发生异常。", e);
		}
		return resourcePaths;
	}

	public static List<String> getResourceBasenamesByWildcard(String resourceDir, String... wildcardResourcePaths) {
		List<String> resourceBasenames = new ArrayList<String>();
		for (String resourcePath : getResourcePathsByWildcard(resourceDir, wildcardResourcePaths)) {
			resourceBasenames.add(StringUtils.substringBeforeLast(resourcePath, "."));
		}
		return resourceBasenames;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.SpringUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */