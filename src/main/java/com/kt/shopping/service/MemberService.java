package com.kt.shopping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kt.shopping.domain.UpdateMember;
import com.kt.shopping.dto.MemberCreateRequest;
import com.kt.shopping.domain.Member;
import com.kt.shopping.dto.MemberReadResponse;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	// 계정 생성
	public void createFromH2(MemberCreateRequest request){
		Member member = new Member(
			request.loginId(),
			request.password(),
			request.name(),
			request.birthday()
		);
		memberRepository.save(member);
	}
	// loginid에 해당하는 계정의 이름, 생년월일 업데이트
	public void updateFromH2(int id,MemberUpdateRequest request){
		UpdateMember um = new UpdateMember(
			request.name(),
			request.birthday()
		);
		memberRepository.update(id, um);
	}
	// loginid에 해당하는 계정 삭제
	public void deleteFromH2(int id){
		memberRepository.delete(id);
	}
	public MemberReadResponse getDataFromH2(int id){
		return memberRepository.read(id);
	}
	public List<MemberReadResponse> getAllDataFromH2(){
		return memberRepository.readAll();
	}
}
