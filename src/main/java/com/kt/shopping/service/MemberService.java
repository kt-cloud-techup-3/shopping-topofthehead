package com.kt.shopping.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.dto.CustomPage;
import com.kt.shopping.dto.MemberCreateRequest;
import com.kt.shopping.domain.member.Member;
import com.kt.shopping.dto.MemberCredential;
import com.kt.shopping.dto.MemberGetResponse;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.repository.MemberJdbcRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberJdbcRepository memberJdbcRepository;
	// 계정 생성
	public void createMember(MemberCreateRequest request){
		var member = new Member(
			memberJdbcRepository.getMaxId()+1,
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
		memberJdbcRepository.save(member);

	}
	// loginid에 해당하는 계정의 이름, 생년월일 업데이트
	public void updateMember(int id,MemberUpdateRequest request){
		memberJdbcRepository.read(id).orElseThrow(()->
			new IllegalArgumentException("존재하지 않는 회원입니다."));
		memberJdbcRepository.updateMemberData(id, request);
	}
	// loginid에 해당하는 계정 삭제
	public void deleteMember(int id){
		memberJdbcRepository.delete(id);
	}



	public MemberGetResponse getMember(int id){
		return memberJdbcRepository.read(id).orElseThrow(()->
			new IllegalArgumentException("존재하지 않는 회원입니다"));
	}
	public List<MemberGetResponse> getAllMembers(){
		return memberJdbcRepository.readAll();
	}

	// 해당 loginId를 가진 데이터가 DB에 존재하는 경우 TRUE를 반환
	public boolean isDuplicatedId(int Id){
		return memberJdbcRepository.existsById(Id);
	}

	public void changePassword(int id, String currentpassword , String newpassword){
		// 계정이 존재하지 않는 경우를 대비해 Optional 객체로 받아와서 NPE 예방
		// 사전에 DB에 비밀번호를 갱신할 사용자가 존재하는지 조회 후 존재하지않으면 예외발생
		MemberCredential credentialfromdb = memberJdbcRepository.getCredential(id).orElseThrow(
			()-> new IllegalArgumentException("존재하지 않는 회원입니다")
		);
		// 사용자가 비밀번호를 변경하기위해 기존에 입력한 비밀번호와 기존 DB상 저장된 비밀번호가 동일해야함
		// 입력된 비밀번호가 DB상 비밀번호와 동일하지 않은 경우 예외 발생
		if ( !credentialfromdb.password().equals(currentpassword) ){
			throw new IllegalArgumentException("기존 비밀번호와 일치하지 않습니다.");
		}
		// 기존 비밀번호와 변경하려는 비밀번호가 동일한 경우 예외발생
		if ( currentpassword.equals(newpassword) ){
			throw new IllegalArgumentException("기존 비밀번호와 동일");
		}
		// 검증이 모두 완료된 경우 비밀번호 변경
		memberJdbcRepository.updateMemberPassword(id,newpassword);
	}
	public CustomPage search(int page, int size, String keyword){
		// DB는 offset 페이지 * 데이터수 에서 0번부터 인덱싱이 시작되므로 페이지번호 - 1 처리
		var pair = memberJdbcRepository.getMembersByPage(page - 1,size, keyword);
		// 커스텀 페이지 DTO 반환
		return new CustomPage(
			pair.getKey(), // 단위 페이지범위에 해당하는 데이터
			size, // 단위 페이지에 출력할 데이터 수
			page, // 조회할 페이지 번호
			(int)(Math.ceil((double)pair.getValue()/page)), // 총 페이지 수
			pair.getValue() // 총 데이터 수
		);
	}
}
