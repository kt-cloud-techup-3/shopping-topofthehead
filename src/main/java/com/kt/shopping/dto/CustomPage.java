package com.kt.shopping.dto;

import java.util.List;

import com.kt.shopping.dto.member.MemberResponse;

public record CustomPage(
	List<MemberResponse.MemberGetResponse> members, // 전달할 데이터
	int size, // 페이지 당 표현된 데이터 수
	int page, // 현재 표현하는 페이지 번호
	int pages, // 총 페이지수
	long totalelementcount // 총 데이터 수
) {
}
