package com.kt.shopping.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Exception 발생 시 예외발생문 간결화하는 용도
public class Exceptions {
	private static final String START_WITH = "at "; // 예외는 at으로 시작하므로
	// 해당 구문으로 시작하는 예외메시지 제외하는 용도
	private static final List<String> BLOCKED_PACKAGES_START_WITH =
		List.of(
			"jdk",
			"org.spring",
			"java.base",
			"org.hibernate",
			"org.apache",
			"com.sun",
			"javax.servlet",
			"jakarta.servlet",
			"SpringCGLIB",
			"com.fasterxml.jackson",
			"jdk.internal",
			"io.netty",
			"reactor.core",
			"reactor.netty",
			"com.kt.aspect"
		);
	// Stacktrace에서 가져와서 에러메시지 가공
	public static String simplify(Throwable throwable) {
		var sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw));
		return truncate(sw.toString());
	}
	private static String truncate(String message) {
		return Arrays.stream(message.split("\n"))
			.filter(it -> !(it.contains(START_WITH) && containsAny(it)))
			.collect(Collectors.joining("\n"));
	}
	private static boolean containsAny(String source) {
		for (String keyword : BLOCKED_PACKAGES_START_WITH) {
			if (source.contains(keyword)) {
				return true;
			}
		}
		return false;
	}
}
