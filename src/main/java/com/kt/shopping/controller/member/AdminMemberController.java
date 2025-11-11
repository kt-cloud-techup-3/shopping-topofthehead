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
import com.kt.shopping.dto.member.MemberResponse;
import com.kt.shopping.dto.member.MemberUpdateRequest;
import com.kt.shopping.service.member.MemberServiceImpl;

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
		Paging paging,
		@RequestParam(required = false) String keyword
	){
		Pageable pageable = PageRequest.of(paging.page()-1 , paging.size());
		return memberservice.searchAll(pageable, keyword);
	}

	// 유저 상세 조회
	@GetMapping("/{loginId}")
	@ResponseStatus(HttpStatus.OK)
	public MemberResponse.MemberGetResponse detail(@PathVariable String loginId){
		return memberservice.getMember(loginId);
	}

	// 유저 정보 수정
	@PutMapping("/{loginId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> update(@PathVariable String loginId, @RequestBody MemberUpdateRequest request) {
		// loginid에 해당하는 계정의 이름, 생년월일 업데이트
		memberservice.updateMember(loginId, request);
		return ApiResult.ok();
	}

	// 유저 삭제
	@DeleteMapping("/{loginId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Void> delete(@PathVariable String loginId) {
		// loginid에 해당하는 계정 삭제
		memberservice.deleteMember(loginId);
		return ApiResult.ok();
	}

	// 유저 비밀번호 초기화

}
