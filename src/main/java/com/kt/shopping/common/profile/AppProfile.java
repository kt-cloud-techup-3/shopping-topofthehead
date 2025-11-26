package com.kt.shopping.common.profile;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Profile;

@Retention(RetentionPolicy.RUNTIME)
// 프로필 - dev, prod는 Slack에 메시지를 전송하도록 설정
@Profile({"dev", "prod"})
public @interface AppProfile { }
