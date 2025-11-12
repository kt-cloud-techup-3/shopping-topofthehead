package com.kt.shopping.service.member;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.common.PreValidCondition;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.dto.member.MemberRequest;
import com.kt.shopping.dto.member.MemberResponse;
import com.kt.shopping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
@Transactional	 // 트랜잭션의 원자성 보장
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	// 계정 생성
	@Override
	public void createMember(MemberRequest.Create request){
		var member = new MemberEntity(
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

	// id에 해당하는 계정의 이름, 생년월일 업데이트
	@Override
	public void updateMember(Long id,MemberRequest.Update request){
		MemberEntity memberEntity = memberRepository.findMemberByIdOrThrowNotFound(id);
		// 업데이트 시 Dirty Checking에 의해 자동으로 DB Table에 반영
		memberEntity.updateContent(request);
	}
	// id에 해당하는 계정 삭제
	@Override
	public void deleteMember(Long id){
		memberRepository.deleteById(id);
	}

	@Override
	public List<MemberResponse.Details> getAllMembers(){
		var members = memberRepository.findAll();
		return members.stream().map(member->
			MemberResponse.of(member)
		).toList();
	}

	// 해당 Id를 가진 데이터가 DB에 존재하는 경우 TRUE를 반환
	@Override
	public boolean isDuplicatedId(Long id){
		return memberRepository.existsById(id);
	}

	@Override
	public void changePassword(Long id, String currentpassword , String newpassword){
		// 계정이 존재하지 않는 경우를 대비해 Optional 객체로 받아와서 NPE 예방
		// 사전에 DB에 비밀번호를 갱신할 사용자가 존재하는지 조회 후 존재하지않으면 예외발생
		MemberEntity memberEntity = memberRepository.findMemberByIdOrThrowNotFound(id);
		// 사용자가 비밀번호를 변경하기위해 기존에 입력한 비밀번호와 기존 DB상 저장된 비밀번호가 동일해야함
		// 입력된 비밀번호가 DB상 비밀번호와 동일하지 않은 경우 예외 발생
		PreValidCondition.validate(
			!memberEntity.getPassword().equals(currentpassword),
			ErrorCode.DOES_NOT_MATCH_OLD_PASSWORD
		);
		// 기존 비밀번호와 변경하려는 비밀번호가 동일한 경우 예외발생
		PreValidCondition.validate(
			currentpassword.equals(newpassword),
			ErrorCode.CAN_NOT_ALLOWED_SAME_PASSWORD
		);
		// 검증이 모두 완료된 경우 비밀번호 변경
		memberEntity.updatePassword(newpassword);
	}

	@Override
	@Transactional(readOnly=true) // 수정에 대한 Dirty Checking을 금지
	public MemberResponse.Details getMember(Long id){
		MemberEntity memberEntity = memberRepository.findMemberByIdOrThrowNotFound(id);
		return MemberResponse.of(memberEntity);
	}
	@Override
	@Transactional(readOnly = true)
	public Page<MemberEntity> searchAll(Pageable pageable, String keyword){
		// Page<Entity클래스> 반환
		return memberRepository.findAllByNameContaining(keyword, pageable);
	}
}
