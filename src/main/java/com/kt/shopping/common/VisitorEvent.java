package com.kt.shopping.common;

public record VisitorEvent(
	String ip,
	String userAgent,
	Long userId
) { }

