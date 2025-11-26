package com.kt.shopping.service.history;

import com.kt.shopping.domain.history.HistoryType;

public interface HistoryService {
	void create(HistoryType type, String content, Long userId);
}
