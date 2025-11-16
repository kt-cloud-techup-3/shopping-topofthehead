package com.kt.shopping.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kt.shopping.common.CustomException;
import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.domain.member.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	// 에러 발생
	default MemberEntity findMemberByIdOrThrowNotFound(Long id){
		return findById(id).orElseThrow((()-> new CustomException(ErrorCode.NOT_FOUND_USER)));
	}
	List<MemberEntity> findAll();

	Optional<MemberEntity> findByName(String name);

	Optional<MemberEntity> findById(Long id);

	// JPQL 방식
	@Query("""
		SELECT EXISTS ( SELECT m FROM MemberEntity m WHERE m.id = ?1 )
	""")
	Boolean existsByIdByJPQL(String id);

	// Native SQL 방식
	@Query(value = """
		SELECT EXISTS (SELECT * FROM Member WHERE id = ?1);
	""",nativeQuery = true)
	Boolean existsByIdByNativeSQL(String id);


	Page<MemberEntity> findAllByNameContaining(String name, Pageable pageable);
}
