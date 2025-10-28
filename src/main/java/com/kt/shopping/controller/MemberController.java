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

@RestController
public class MemberController {
	private final MemberService memberservice;
	public MemberController(MemberService memberservice){
		this.memberservice = memberservice;
	}
	@PostMapping("/members")
	@ResponseStatus(HttpStatus.CREATED)
	public void createMemberApi(@RequestBody MemberCreateRequest request) {
		// 계정생성
		memberservice.createMember(request);
	}
	@PutMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateMemberApi(@PathVariable int id, @RequestBody MemberUpdateRequest request) {
		// loginid에 해당하는 계정의 이름, 생년월일 업데이트
		memberservice.updateMember(id, request);
	}
	@DeleteMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteMemberApi(@PathVariable int id) {
		// loginid에 해당하는 계정 삭제
		memberservice.deleteMember(id);
	}
	@GetMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberReadResponse getMemberApi(@PathVariable int id){
		// loginid에 해당하는 계정  가져오기
		return memberservice.getMember(id);
	}
	@GetMapping("/members")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberReadResponse> getAllMembersApi(){
		// 모든 계정  가져오기
		return memberservice.getAllMembers();
	}
}
