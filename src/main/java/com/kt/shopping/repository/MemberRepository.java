package com.kt.shopping.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.rowmapper.MemberRowMapper;
import com.kt.shopping.domain.Member;
import com.kt.shopping.dto.MemberReadResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
	private final JdbcTemplate jdbcTemplate;
	public void save(Member member){
		// 서비스에서 DTO를 비즈니스모델로 바꾼 후 전달
		String insertSql = """
			insert into member(id,
									 loginId, 
									 password, 
									 name, 
									 birthday,
									 mobile,
									 email,
									 gender,
									 createdAt,
									 updatedAt) values (?,?,?,?,?,?,?,?,?,?);
			""";
		jdbcTemplate.update(insertSql,
			member.getId(),
			member.getLoginId(),
			member.getPassword(),
			member.getName(),
			member.getBirthday(),
			member.getMobile(),
			member.getEmail(),
			member.getGender().name(), // Enum 이므로 String으로 반환
			member.getCreatedAt(),
			member.getUpdatedAt()
			);
	}
	public void update(int loginId, MemberUpdateRequest member){
		String updateSql = """
				update member
				set name = ?,
				    email = ?,
				    mobile = ?,
				    gender = ?,
				birthday = ?
				where loginid = ?;
			""";
		jdbcTemplate.update(updateSql,
			member.name(),
			member.email(),
			member.mobile(),
			member.gender().name(),
			member.birthday(),
			loginId);
	}
	public void delete(int id){
		String deleteSql = """
			delete from member
			where loginid = ?;
			""";
		jdbcTemplate.update(deleteSql,id);
	}
	public MemberReadResponse read(int id){
		String selectSql = """
			select name, email, mobile, gender , birthday
			from member
			where loginid = ?;
			""";
		return jdbcTemplate.queryForObject(selectSql,new MemberRowMapper(),id );
	}

	public List<MemberReadResponse> readAll(){
		String selectSqlAll = """
			select name, email, mobile, gender , birthday
			from member;
			""";
		return jdbcTemplate.query(selectSqlAll,new MemberRowMapper());
	}
	public Long getMaxId(){
		String getMaxSQL = """
			select MAX(id) FROM MEMBER;
			""";
		var maxId = jdbcTemplate.queryForObject(getMaxSQL,Long.class);
		return  (maxId == null)? 0L : maxId;
	}
}
