package com.kt.shopping.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.dto.CustomPage;
import com.kt.shopping.dto.MemberGetResponse;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.service.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="관리자" , description="관리자용 계정입니다.")
@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
	private final MemberService memberservice;

	// 유저 리스트 조회
	@GetMapping
	// 실무에서 defaultValue=""는 테스트용 초기값 정의용도로 사용
	// 1번 페이지부터 시작하므로 1로 설정
	@ResponseStatus(HttpStatus.OK)
	public CustomPage search(
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(required = false) String keyword
	){
		return memberservice.search(page,size,keyword);
	}

	// 유저 상세 조회
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberGetResponse detail(@PathVariable int id){
		return memberservice.getMember(id);
	}

	// 유저 정보 수정
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable int id, @RequestBody MemberUpdateRequest request) {
		// loginid에 해당하는 계정의 이름, 생년월일 업데이트
		memberservice.updateMember(id, request);
	}

	// 유저 삭제
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable int id) {
		// loginid에 해당하는 계정 삭제
		memberservice.deleteMember(id);
	}

	// 유저 비밀번호 초기화

}
