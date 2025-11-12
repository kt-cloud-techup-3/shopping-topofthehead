package com.kt.shopping.service.member;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.dto.member.MemberRequest;
import com.kt.shopping.dto.member.MemberResponse;

public interface MemberService {
	void createMember(MemberRequest.Create memberCreateRequest);
	void updateMember(Long id, MemberRequest.Update memberUpdateRequest);
	void deleteMember(Long id);
	List<MemberResponse.Details> getAllMembers();
	boolean isDuplicatedId(Long id);
	void changePassword(Long id,String currentpassword , String newpassword);
	MemberResponse.Details getMember(Long id);
	Page<MemberEntity> searchAll(Pageable pageable, String keyword);
}
