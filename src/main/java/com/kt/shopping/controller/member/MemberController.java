package com.kt.shopping.controller.member;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.kt.shopping.common.ApiResult;
import com.kt.shopping.common.SwaggerSupporter;
import com.kt.shopping.domain.member.MemberEntity;
import com.kt.shopping.dto.member.MemberRequest;
import com.kt.shopping.dto.member.MemberResponse;
import com.kt.shopping.service.member.MemberServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Tag(name="유저", description = "유저계정입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController extends SwaggerSupporter {
	private final MemberServiceImpl memberservice;
	// localhost:8080/members/로 요청 시 해당 컨트롤러로 매핑
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResult<Void> create(@Valid @RequestBody MemberRequest.Create request) {
		// 계정생성
		memberservice.createMember(request);
		return ApiResult.ok();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<MemberResponse.Details> getMember(@PathVariable Long id){
		// id에 해당하는 계정  가져오기
		MemberResponse.Details memberreadresponse = memberservice.getMember(id);
		// HATEOAS 구현을 위한 EntityModel 생성
		EntityModel<MemberResponse.Details> entityModel = EntityModel.of(memberreadresponse);
		// 특정 Controller로 접근하는 하이퍼링크를 포함하는 링크객체 생성
		WebMvcLinkBuilder linkToMember1 = linkTo(methodOn(MemberController.class).search());
		// EntityModel에 링크객체 추가
		entityModel.add(linkToMember1.withRel("모든 멤버 조회"));
		// 계정과 하이퍼링크를 포함하는 EntityModel 반환
		return entityModel;
	}
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberResponse.Details> search(){
		// 모든 계정  가져오기
		return memberservice.getAllMembers();
	}

	// @GetMapping으로 질의 하는것이므로  Query String 이용
	// /users/duplicate-id?id=s1234 로 전달된 값을 검증
	// @RequestParam의 속성은 기본이 required=true → URL에 API 전송 시 id가 미포함 시
	// IllegalArgumentException 발생 시 400 에러발생
	// Query String이 꼭 필요하지 않는경우 @RequestParam(required = false) 설정
	@GetMapping("/duplicate-id")
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<Boolean> isDuplicateId(@RequestParam(required = false) Long id){
		// Reference Type인 경우 null을 포함가능 → primitive인 boolean이 아닌 RefereceType인 Boolean 사용
		// 1화 29분 : Generics 사용 예정 및 Generics는 Reference Type만 사용가능하므로 사용
		var result =  memberservice.isDuplicatedId(id);
		return ApiResult.ok(result);
	}


	// 변경할 id를 path에 전달 및 RequestBody에 현재 비밀번호와 변경할 비밀번호를 함께 전달하여 변경 수행
	@PutMapping("/{id}/update-password")
	// 1. MessageBody에 id를 넣는다
	// 2. uri에 id값을 넣는다. /users/{id}/update-password
	// 3. 인증/인가 객체에서 id값을 꺼낸다 : 가장 권고하나 배우지않은상태
	public ApiResult<Void> updatePassword(@RequestBody @Valid MemberRequest.UpdatePassword request,
		@PathVariable Long id) {
		// @RequestBody로 HTTPRequestBody와 mapping된 DTO에서 데이터 가져옴
		memberservice.changePassword(id,request.oldpassword(),request.newpassword());
		return ApiResult.ok();
	}

}
