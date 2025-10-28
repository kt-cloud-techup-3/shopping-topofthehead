package com.kt.shopping.dto;

import java.time.LocalDate;

public record UserCreateRequest (int loginId, String password , String name, LocalDate birthday ) { }
