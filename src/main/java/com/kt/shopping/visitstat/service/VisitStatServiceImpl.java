package com.kt.shopping.visitstat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.visitstat.domain.VisitStat;
import com.kt.shopping.visitstat.repository.VisitStatRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitStatServiceImpl implements VisitStatService {
	private final VisitStatRepository visitStatRepository;
	@Override
	public void create(
		Long userId,
		String ip,
		String userAgent
	) {
		visitStatRepository.save(
			new VisitStat(userId, userAgent, ip)
		);
	}
}
