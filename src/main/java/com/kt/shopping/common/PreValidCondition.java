package com.kt.shopping.common;

import com.kt.shopping.common.exception.CustomException;

public class PreValidCondition {
	public static void validate(boolean condition, ErrorCode errorCode){
		if(!condition) throw new CustomException(errorCode);
	}
}
