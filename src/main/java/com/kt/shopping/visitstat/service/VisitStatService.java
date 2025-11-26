package com.kt.shopping.visitstat.service;


public interface VisitStatService {
	void create(
		Long userId,
		String ip,
		String userAgent
	);
}
