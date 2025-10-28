package com.kt.shopping.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.kt.shopping.dto.MemberReadResponse;

public class MemberRowMapper implements RowMapper<MemberReadResponse> {
	public MemberReadResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		MemberReadResponse member = new MemberReadResponse();
		member.setName(rs.getString("name"));
		member.setBirthday(LocalDate.parse(rs.getString("birthday")));
		return member;
	}
}
