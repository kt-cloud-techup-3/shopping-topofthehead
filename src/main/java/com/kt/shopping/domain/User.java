package com.kt.shopping.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int loginId;
	private String password;
	private String name;
	private LocalDate birtday;
}
