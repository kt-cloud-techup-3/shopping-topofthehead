package com.kt.shopping.dto;

import java.time.LocalDate;

public record MemberUpdateRequest( String name, LocalDate birthday ) { }