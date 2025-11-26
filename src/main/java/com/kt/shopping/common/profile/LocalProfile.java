package com.kt.shopping.common.profile;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Profile;

@Retention(RetentionPolicy.RUNTIME)
// 프로필 - local, default, test는 Slack에 전송하지않고 로그에 메시지를 출력하도록 설정
@Profile({"local", "default", "test"})
public @interface LocalProfile { }
