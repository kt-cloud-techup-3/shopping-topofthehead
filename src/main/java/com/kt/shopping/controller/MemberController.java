package com.kt.shopping.controller;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.kt.shopping.dto.MemberCreateRequest;
import com.kt.shopping.dto.MemberReadResponse;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.service.MemberService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@ApiResponses(value = {
	@ApiResponse(responseCode="400", description = "유효성 검사 실패"),
	@ApiResponse(responseCode = "500", description = "서버 에러")
})
@Tag(name="멤버컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberservice;
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody MemberCreateRequest request) {
		// 계정생성
		memberservice.createMember(request);
	}
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable int id, @RequestBody MemberUpdateRequest request) {
		// loginid에 해당하는 계정의 이름, 생년월일 업데이트
		memberservice.updateMember(id, request);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable int id) {
		// loginid에 해당하는 계정 삭제
		memberservice.deleteMember(id);
	}
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntityModel<MemberReadResponse> getMember(@PathVariable int id){
		// loginid에 해당하는 계정  가져오기
		MemberReadResponse memberreadresponse = memberservice.getMember(id);
		// HATEOAS 구현을 위한 EntityModel 생성
		EntityModel<MemberReadResponse> entityModel = EntityModel.of(memberreadresponse);
		// 특정 Controller로 접근하는 하이퍼링크를 포함하는 링크객체 생성
		WebMvcLinkBuilder linkToMember1 = linkTo(methodOn(MemberController.class).getAllMembers());
		// EntityModel에 링크객체 추가
		entityModel.add(linkToMember1.withRel("모든 멤버 조회"));
		// 계정과 하이퍼링크를 포함하는 EntityModel 반환
		return entityModel;
	}
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<MemberReadResponse> getAllMembers(){
		// 모든 계정  가져오기
		return memberservice.getAllMembers();
	}

	// /users/duplicate-login-id?loginId=s1234 로 전달된 값을 검증
	// @RequestParam의 속성은 기본이 required=true → URL에 API 전송 시 loginId가 미포함 시
	// IllegalArgumentException 발생 시 400 에러발생
	// Query String이 꼭 필요하지 않는경우 @RequestParam(required = false) 설정
	@GetMapping("/duplicate-login-id")
	@ResponseStatus(HttpStatus.OK)
	public Boolean isDuplicateLoginId(@RequestParam String loginId){
		// Reference Type인 경우 null을 포함가능 → primitive인 boolean이 아닌 RefereceType인 Boolean 사용
		// 1화 29분 : Generics 사용 예정 및 Generics는 Reference Type만 사용가능하므로 사용
		return memberservice.isDuplicatedLoginId(loginId);
	}
}
