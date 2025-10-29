package com.kt.shopping.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import com.kt.shopping.dto.MemberReadResponse;
public class MemberRowMapper implements RowMapper<MemberReadResponse> {
	public MemberReadResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new MemberReadResponse(
			// SQL 데이터의 Column을 rs객체를 통해 가져와서
			// template 역할 Class의 Field로 Mapping.
			rs.getString("name"),
			LocalDate.parse(rs.getString("birthday")));
	}
}
