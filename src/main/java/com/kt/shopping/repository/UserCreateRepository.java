package com.kt.shopping.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.kt.shopping.domain.User;

@Repository
public class UserCreateRepository {
	private final JdbcTemplate jdbcTemplate;
	public UserCreateRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public void save(User user){
		// 서비스에서 DTO를 비즈니스모델로 바꾼 후 전달
		String sql = """
			insert into user1(loginId, password, name, birthday) values (?,?,?,?);
			""";
		jdbcTemplate.update(sql,
			user.getLoginId(),
			user.getPassword(),
			user.getName(),
			user.getBirtday()
			);
	}
}
