package com.kt.shopping.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kt.shopping.common.CustomException;
import com.kt.shopping.common.ErrorCode;
import com.kt.shopping.domain.member.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	default MemberEntity findByIdOrThrow(Long id, ErrorCode errorCode) {
		return findById(id).orElseThrow(()-> new CustomException(errorCode));
	}
	List<MemberEntity> findAll();
	Optional<MemberEntity> findByLoginId(String loginId);
	void deleteByLoginId(String loginId);
	// Query Method 방식
	Boolean existsByLoginId(String loginId);
	// JPQL 방식
	@Query("""
		SELECT EXISTS ( SELECT m FROM MemberEntity m WHERE m.loginId = ?1 )
	""")
	Boolean existsByLoginIdByJPQL(String loginId);
	// Native SQL 방식
	@Query(value = """
		SELECT EXISTS (SELECT * FROM Member WHERE loginId = ?1);
	""",nativeQuery = true)
	Boolean existsByLoginIdByNativeSQL(String loginId);

	Page<MemberEntity> findAllByNameContaining(String name, Pageable pageable);
}
