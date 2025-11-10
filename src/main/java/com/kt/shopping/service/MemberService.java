package com.kt.shopping.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.dto.MemberCreateRequest;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.dto.MemberGetResponse;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	// 계정 생성
	@Transactional // 트랜잭션의 ACID 보장
	public void createMember(MemberCreateRequest request){
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

	// loginid에 해당하는 계정의 이름, 생년월일 업데이트
	@Transactional
	public void updateMember(String loginId,MemberUpdateRequest request){
		MemberEntity memberEntity = memberRepository.findByLoginId(loginId).orElseThrow(()->
			new IllegalArgumentException("존재하지 않는 회원입니다."));
		// 업데이트 시 Dirty Checking에 의해 자동으로 DB Table에 반영
		memberEntity.updateContent(request);
	}
	// loginid에 해당하는 계정 삭제
	@Transactional
	public void deleteMember(String loginId){
		memberRepository.deleteByLoginId(loginId);
	}


	public List<MemberEntity> getAllMembers(){
		return memberRepository.findAll();
	}

	// 해당 loginId를 가진 데이터가 DB에 존재하는 경우 TRUE를 반환
	public boolean isDuplicatedId(String loginId){
		return memberRepository.existsByLoginId(loginId);
	}

	@Transactional
	public void changePassword(String loginId, String currentpassword , String newpassword){
		// 계정이 존재하지 않는 경우를 대비해 Optional 객체로 받아와서 NPE 예방
		// 사전에 DB에 비밀번호를 갱신할 사용자가 존재하는지 조회 후 존재하지않으면 예외발생
		MemberEntity memberEntity = memberRepository.findByLoginId(loginId).orElseThrow(
			()-> new IllegalArgumentException("존재하지 않는 회원입니다")
		);
		// 사용자가 비밀번호를 변경하기위해 기존에 입력한 비밀번호와 기존 DB상 저장된 비밀번호가 동일해야함
		// 입력된 비밀번호가 DB상 비밀번호와 동일하지 않은 경우 예외 발생
		if ( !memberEntity.getPassword().equals(currentpassword) ){
			throw new IllegalArgumentException("기존 비밀번호와 일치하지 않습니다.");
		}
		// 기존 비밀번호와 변경하려는 비밀번호가 동일한 경우 예외발생
		if ( currentpassword.equals(newpassword) ){
			throw new IllegalArgumentException("기존 비밀번호와 동일");
		}
		// 검증이 모두 완료된 경우 비밀번호 변경
		memberEntity.updatePassword(newpassword);
	}

	@Transactional(readOnly=true) // 수정에 대한 Dirty Checking을 금지
	public MemberGetResponse getMember(String loginId){
		MemberEntity memberEntity = memberRepository.findByLoginId(loginId).orElseThrow(()->
			new IllegalArgumentException("존재하지 않는 회원입니다"));
		return new MemberGetResponse(
			memberEntity.getName(),
			memberEntity.getEmail(),
			memberEntity.getMobile(),
			memberEntity.getGender(),
			memberEntity.getBirthday()
		);
	}
	@Transactional(readOnly = true)
	public Page<MemberEntity> searchAll(Pageable pageable, String keyword){
		// Page<Entity클래스> 반환
		return memberRepository.findAllByNameContaining(keyword, pageable);
	}
}
