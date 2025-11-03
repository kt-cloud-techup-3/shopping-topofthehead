package com.kt.shopping.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kt.shopping.dto.MemberCreateRequest;
import com.kt.shopping.domain.Member;
import com.kt.shopping.dto.MemberReadResponse;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	// 계정 생성
	public void createMember(MemberCreateRequest request){
		var member = new Member(
			memberRepository.getMaxId()+1, // 1씩 더하면서 PK 보장
			request.loginId(),
			request.password(),
			request.name(),
			request.email(),
			request.mobile(),
			request.gender(),
			request.birthday(),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
		memberRepository.save(member);
	}
	// loginid에 해당하는 계정의 이름, 생년월일 업데이트
	public void updateMember(int id,MemberUpdateRequest request){
		memberRepository.update(id, request);
	}
	// loginid에 해당하는 계정 삭제
	public void deleteMember(int id){
		memberRepository.delete(id);
	}
	public MemberReadResponse getMember(int id){
		return memberRepository.read(id);
	}
	public List<MemberReadResponse> getAllMembers(){
		return memberRepository.readAll();
	}

	public boolean isDuplicatedLoginId(String loginId){
		return memberRepository.existsByLoginId(loginId);
	}

	// TODO : ID 중복검사 만들기
}
