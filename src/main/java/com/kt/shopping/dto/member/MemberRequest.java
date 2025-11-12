package com.kt.shopping.dto.member;

import java.time.LocalDate;

import com.kt.shopping.domain.member.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MemberRequest {
	@Schema(name = "MemberRequest.Create")
	public record Create(
		@NotBlank
		String loginId,
		// 최소 8자 이상 대문자 소문자 숫자 특수문자 포함
		@NotBlank
		@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^])[A-Za-z\\d!@#$%^]{8,}$")
		String password,
		@NotBlank
		String name,
		@NotBlank
		@Pattern(regexp="^[0-9A-Za-z._%+-]+@[0-9A-Za-z.-]+\\.[A-Za-z]{2,6}$")
		String email,
		@NotBlank
		@Pattern(regexp="^(0\\d{1,2})-(\\d{3,4})-(\\d{4})$")
		String mobile,
		@NotNull
		Gender gender,
		@NotNull
		LocalDate birthday
	) { }
	@Schema(name = "MemberRequest.Update")
	public record Update(
		@NotBlank
		String name,
		@NotBlank
		@Pattern(regexp="^[0-9A-Za-z._%+-]+@[0-9A-Za-z.-]+\\.[A-Za-z]{2,6}$")
		String email,
		@NotBlank
		@Pattern(regexp="^(0\\d{1,2})-(\\d{3,4})-(\\d{4})$")
		String mobile,
		@NotNull
		Gender gender,
		@NotNull
		LocalDate birthday
	) { }
	@Schema(name = "MemberRequest.UpdatePassword")
	public record UpdatePassword(
		@NotBlank
		@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^])[A-Za-z\\d!@#$%^]{8,}$")
		String oldpassword,
		@NotBlank
		@Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^])[A-Za-z\\d!@#$%^]{8,}$")
		String newpassword
	) { }
}
