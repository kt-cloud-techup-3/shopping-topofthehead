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
	public void createMember(@RequestBody MemberCreateRequest request) {
		// 계정생성
		memberservice.createFromH2(request);
	}
	@PutMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateMember(@PathVariable int id, @RequestBody MemberUpdateRequest request) {
		// loginid에 해당하는 계정의 이름, 생년월일 업데이트
		memberservice.updateFromH2(id, request);
	}
	@DeleteMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteMember(@PathVariable int id) {
		// loginid에 해당하는 계정 삭제
		memberservice.deleteFromH2(id);
	}
	@GetMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MemberReadResponse getMember(@PathVariable int id){
		// loginid에 해당하는 계정  가져오기
		return memberservice.getDataFromH2(id);
	}
	@GetMapping("/members")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberReadResponse> getMember(){
		// 모든 계정  가져오기
		return memberservice.getAllDataFromH2();
	}
}
