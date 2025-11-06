package com.kt.shopping.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.kt.shopping.domain.member.Gender;
import com.kt.shopping.dto.MemberGetResponse;
public class MemberRowMapper implements RowMapper<MemberGetResponse> {
	// ResultSet : SQL Select가 실행된 결과의 객체 반환하여 내부 next()를 통해 가져온 데이터 순회해서 가져올 경우
	// getInt("컬럼명") , getString("컬럼명") , ....을 통해 데이터를 가져와서 DTO객체로 Mapping.
	public MemberGetResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new MemberGetResponse(
			// SQL 데이터의 Column을 rs객체를 통해 가져와서
			// template 역할 Class의 Field로 Mapping
			rs.getString("name"),
			rs.getString("email"),
			rs.getString("mobile"),
			// valueOf : String을 받아서 해당하는 field의 데이터타입으로 변환
			Gender.valueOf(rs.getString("gender")),
			rs.getObject("birthday", LocalDate.class));
	}
}
