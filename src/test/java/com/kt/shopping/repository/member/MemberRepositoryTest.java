package com.kt.shopping.repository.member;

import static com.mysema.commons.lang.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kt.shopping.domain.member.Gender;
import com.kt.shopping.domain.member.MemberEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MemberRepositoryTest {

	private MemberEntity memberEntity;

	@Autowired
	MemberRepository memberRepository;

	@BeforeEach
	void setup(){
		// 준비단계 : given
		memberEntity = memberRepository.save(
			new MemberEntity(
				"wjdtn",
				"wWd747@@",
				"이정수",
				"wjdtn747@nave.com",
				"010-7615-0619",
				Gender.MALE,
				LocalDate.now()
			)
		);
	}

	@Test
	void 이름으로_멤버_검색(){
		Optional<MemberEntity> name = memberRepository.findByName("이정수");

		assertThat(name).isPresent();

	}
	//
	// @AfterEach
	// void tearDown(){
	// 	memberRepository.deleteAll();
	// }

}