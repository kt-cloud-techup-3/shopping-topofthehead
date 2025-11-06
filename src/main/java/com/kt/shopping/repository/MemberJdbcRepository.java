package com.kt.shopping.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kt.shopping.dto.MemberCredential;
import com.kt.shopping.dto.MemberUpdateRequest;
import com.kt.shopping.rowmapper.MemberRowMapper;
import com.kt.shopping.domain.member.Member;
import com.kt.shopping.dto.MemberGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberJdbcRepository {
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
	public void updateMemberData(int loginId, MemberUpdateRequest member){
		String updateDataSql = """
				update member
				set name = ?,
				    email = ?,
				    mobile = ?,
				    gender = ?,
				birthday = ?
				where loginid = ?;
			""";
		jdbcTemplate.update(updateDataSql,
			member.name(),
			member.email(),
			member.mobile(),
			member.gender().name(),
			member.birthday(),
			loginId);
	}

	public void updateMemberPassword(int id , String password){
		String updatePasswordSql = """
				update member
				set password = ?
				where id = ?;
		""";
		jdbcTemplate.update(updatePasswordSql, password, id);
	}

	public void delete(int id){
		String deleteSql = """
			delete from member
			where loginid = ?;
			""";
		jdbcTemplate.update(deleteSql,id);
	}

	public Optional<MemberGetResponse> read(int id){
		String selectSql = """
			select name, email, mobile, gender , birthday
			from member
			where loginid = ?;
			""";
		return jdbcTemplate.query(selectSql,new MemberRowMapper(),id )
			.stream().findFirst();
	}


	public List<MemberGetResponse> readAll(){
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

	public boolean existsById(int id){
		// Reference Type인 경우 null을 포함가능 → Primitive Type인 boolean type을 사용
		var sql = "SELECT EXISTS (SELECT id from member where id = ?);";
		return Boolean.TRUE.equals(jdbcTemplate.update(sql,Boolean.class,id));
	}

	// id에 해당하는 계정의 id와 password를 포함한 DTO를 return
	// 계정이 존재하지 않는 경우 Null이 발생할 수 있으므로 Optional로 return
	public Optional<MemberCredential> getCredential(int id){
		String selectSql = """
			select id, password
			from member
			where loginid = ?;
			""";
		var lst = jdbcTemplate.query(selectSql,rowMapperForPassword(),id );
		// Null을 포함할 수 있으므로 Optional 로 반환
		return lst.stream().findFirst();
	}
	// 로우매퍼를 함수형인터페이스 구현체를 반환하도록 메서드 레벨에서 선언
	private RowMapper<MemberCredential> rowMapperForPassword(){
		return (rs, rowNum)-> mapToPassword(rs);
	}
	private MemberCredential mapToPassword(ResultSet rs) throws SQLException {
		return new MemberCredential(
			rs.getString("id"),
			rs.getString("password"));
	}

	public void updatePassword(int id, String password){
		var sql = "UPDATE MEMBER SET password = ? WHERE id = ?;";
		jdbcTemplate.update(sql,id,password);
	}


	// 페이징 처리 관련

	// 클라이언트(외부)에서 현재 페이지번호 및 페이지에 표현할 데이터수를 받아오면
	// 동적으로 해당 범위의 데이터를 가져와서 반환
	public Pair<List<MemberGetResponse>,Long> getMembersByPage(int page, int size, String keyword){
		// limit , offset 설정 후 해당 페이지 범위의 데이터 가져오기
		String getmemberssql = """
				select name, email, mobile, gender, birthday 
				 from member
				 WHERE name like '%%%s%%'
				 limit %d offset %d;
			""".formatted(keyword,size,page);
		var members = jdbcTemplate.query(getmemberssql, new MemberRowMapper());
		// 총 데이터 수 가져오기
		String totalcountsql = "select count(*) from member";
		var totalcount = jdbcTemplate.queryForObject(totalcountsql,Long.class);
		return Pair.of(members,totalcount);
	}







}
