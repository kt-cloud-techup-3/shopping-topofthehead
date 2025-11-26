package com.kt.shopping.service.history;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.domain.history.History;
import com.kt.shopping.domain.history.HistoryType;
import com.kt.shopping.repository.history.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
	private final HistoryRepository historyRepository;
	@Override
	public void create(HistoryType type, String content, Long userId) {
		historyRepository.save(
			new History(
				type, content, userId
			)
		);
	}
}
