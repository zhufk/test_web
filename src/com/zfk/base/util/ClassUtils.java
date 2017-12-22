package com.zfk.base.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.log4j.Logger;

import com.zfk.base.exceptoin.UncheckedException;

public class ClassUtils {
	private static Logger log = Logger.getLogger(ClassUtils.class);

	public static List<Class<?>> findClassesByParentClass(Class<?> parentClass, String... packageNames) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> targetClass : findClasses(packageNames)) {
			if ((!targetClass.equals(parentClass)) && (parentClass.isAssignableFrom(targetClass))
					&& (!Modifier.isAbstract(targetClass.getModifiers()))) {
				classes.add(targetClass);
			}
		}
		return classes;
	}

	public static List<Class<?>> findClassesByAnnotationClass(Class<? extends Annotation> annotationClass,
			String... packageNames) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> targetClass : findClasses(packageNames)) {
			if (targetClass.isAnnotationPresent(annotationClass)) {
				classes.add(targetClass);
			}
		}
		return classes;
	}

	public static List<Class<?>> findClasses(String... packageNames) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (String className : findClassNames(packageNames)) {
			try {
				classes.add(Class.forName(className));
			} catch (Exception e) {
				log.warn("加载[" + className + "]类时发生异常。", e);
			}
		}
		return classes;
	}

	private static List<String> findClassNames(String... packageNames) {
		List<String> classNames = new ArrayList<String>();
		try {
			for (String packageName : packageNames) {
				String packagePath = packageName.replace(".", "/");
				Enumeration<URL> packageUrls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
				while (packageUrls.hasMoreElements()) {
					URL packageUrl = (URL) packageUrls.nextElement();
					if ("jar".equals(packageUrl.getProtocol())) {
						classNames.addAll(getClassNamesFromJar(packageUrl, packageName));
					} else {
						classNames.addAll(getClassNamesFromDir(packageUrl, packageName));
					}
				}
			}
		} catch (Exception e) {
			throw new UncheckedException("获取指定包下类名集合时发生异常。", e);
		}
		return classNames;
	}

	private static List<String> getClassNamesFromJar(URL url, String packageName) {
		List<String> classNames = new ArrayList<String>();
		try {
			String jarPath = URLDecoder.decode(url.toExternalForm(), "UTF-8");
			if (log.isDebugEnabled()) {
				log.debug("开始获取[" + jarPath + "]中的类名...");
			}
			jarPath = StringUtils.substringAfter(jarPath, "jar:file:");
			jarPath = StringUtils.substringBeforeLast(jarPath, "!");
			JarFile jarInputStream = new JarFile(jarPath);
			try {
				Enumeration<JarEntry> jarEntries = jarInputStream.entries();
				while (jarEntries.hasMoreElements()) {
					JarEntry jarEntry = (JarEntry) jarEntries.nextElement();
					classNames.addAll(getClassNamesFromJar(jarEntry, packageName));
				}
			} finally {
				jarInputStream.close();
			}
		} catch (Exception e) {
			log.warn("获取jar包中的类名时发生异常。", e);
		}
		return classNames;
	}

	private static List<String> getClassNamesFromJar(JarEntry jarEntry, String packageName) {
		List<String> classNames = new ArrayList<String>();
		if ((!jarEntry.isDirectory()) && (jarEntry.getName().endsWith(".class"))) {
			String className = jarEntry.getName();
			className = className.replaceFirst(".class$", "");
			className = className.replace('/', '.');
			if (className.contains(packageName)) {
				classNames.add(className);
			}
		}
		return classNames;
	}

	private static List<String> getClassNamesFromDir(URL url, String packageName) {
		try {
			String dirPath = URLDecoder.decode(url.getFile(), "UTF-8");
			if (log.isDebugEnabled()) {
				log.debug("开始获取[" + dirPath + "]中的类名...");
			}
			return getClassNamesFromDir(new File(dirPath), packageName);
		} catch (Exception e) {
			throw new UncheckedException("从目录中获取类名时发生异常。", e);
		}
	}

	private static List<String> getClassNamesFromDir(File dir, String packageName) {
		List<String> classNames = new ArrayList<String>();
		try {
			File[] files = dir.listFiles();
			String separator = System.getProperty("file.separator");
			for (File file : files) {
				if (file.isDirectory()) {
					classNames.addAll(getClassNamesFromDir(file, packageName + "." + file.getName()));
				} else if (file.getName().endsWith(".class")) {
					String className = file.getPath();
					className = className.replace(separator, ".");
					className = packageName + StringUtils.substringAfterLast(className, packageName);
					className = className.replaceFirst(".class$", "");
					classNames.add(className);
				}
			}
		} catch (Exception e) {
			log.warn("获取目录中的类名时发生异常。", e);
		}
		return classNames;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.ClassUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */