package com.kt.shopping.dto.member;

import java.time.LocalDate;

import com.kt.shopping.domain.member.Gender;
import com.kt.shopping.domain.member.MemberEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface MemberResponse {

	@Schema(name = "MemberResponse.Details")
	record Details(
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
	){ }

		// 정적 팩토리 메서드
		static Details of(MemberEntity memberEntity){
			return new Details(
				memberEntity.getName(),
				memberEntity.getEmail(),
				memberEntity.getMobile(),
				memberEntity.getGender(),
				memberEntity.getBirthday()
			);
		}
	}

