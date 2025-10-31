package com.kt.shopping.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kt.shopping.dto.MemberCreateRequest;
import com.kt.shopping.dto.MemberReadResponse;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.service.MemberService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name="멤버컨트롤러")
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberservice;
	@ApiResponses(value = {
		@ApiResponse(responseCode="400", description = "유효성 검사 실패"),
		@ApiResponse(responseCode = "500", description = "서버 에러")
	})
	@PostMapping("/members")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody MemberCreateRequest request) {
		// 계정생성
		memberservice.createMember(request);
	}
	@PutMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable int id, @RequestBody MemberUpdateRequest request) {
		// loginid에 해당하는 계정의 이름, 생년월일 업데이트
		memberservice.updateMember(id, request);
	}
	@DeleteMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable int id) {
		// loginid에 해당하는 계정 삭제
		memberservice.deleteMember(id);
	}
	@GetMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberReadResponse getMember(@PathVariable int id){
		// loginid에 해당하는 계정  가져오기
		return memberservice.getMember(id);
	}
	@GetMapping("/members")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberReadResponse> getAllMembers(){
		// 모든 계정  가져오기
		return memberservice.getAllMembers();
	}
}
