package com.kt.shopping.common;

public class PreValidCondition {
	public static void validate(boolean condition, ErrorCode errorCode){
		if(!condition) throw new CustomException(errorCode);
	}
}
