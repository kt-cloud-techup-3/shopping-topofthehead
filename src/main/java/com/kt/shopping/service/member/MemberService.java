package com.kt.shopping.service.member;

import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.dto.member.MemberCreateRequest;

public interface MemberService {
	void createMember(MemberCreateRequest memberCreateRequest);
}
