package com.kt.shopping.controller.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.kt.shopping.common.ApiResult;
import com.kt.shopping.common.Paging;
import com.kt.shopping.common.SwaggerSupporter;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.dto.member.MemberRequest;
import com.kt.shopping.dto.member.MemberResponse;
import com.kt.shopping.service.member.MemberServiceImpl;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name="관리자" , description="관리자용 계정입니다.")
@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class AdminMemberController extends SwaggerSupporter {
	private final MemberServiceImpl memberservice;

	// 유저 리스트 조회
	// 실무에서 defaultValue=""는 테스트용 초기값 정의용도로 사용
	// 1번 페이지부터 시작하므로 1로 설정
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<MemberEntity> search(
		// @RequestParams 를 통해 Controller로 전달되는 page, size를 해당 객체로 Mapping
		@Parameter(hidden=true) Paging paging,
		@RequestParam(required = false) String keyword
	){
		// Paging 객체 내부에서 수신된 page, size를 기반으로
		// Pageable객체를 생성하여 반환
		Pageable pageable = paging.toPageable();
		return memberservice.searchAll(pageable, keyword);
	}

	// 유저 상세 조회
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberResponse.Details detail(@PathVariable Long id){
		return memberservice.getMember(id);
	}

	// 유저 정보 수정
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> update(@PathVariable Long id, @RequestBody MemberRequest.Update request) {
		// loginid에 해당하는 계정의 이름, 생년월일 업데이트
		memberservice.updateMember(id, request);
		return ApiResult.ok();
	}

	// 유저 삭제
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> delete(@PathVariable Long id) {
		// loginid에 해당하는 계정 삭제
		memberservice.deleteMember(id);
		return ApiResult.ok();
	}

	// 유저 비밀번호 초기화

}
