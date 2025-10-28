package com.kt.shopping.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private int loginId;
	private String password;
	private String name;
	private LocalDate birtday;
}
