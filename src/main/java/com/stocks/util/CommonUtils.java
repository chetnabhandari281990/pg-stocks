package com.stocks.util;

import java.util.stream.Collectors;

import org.springframework.validation.Errors;

import com.google.gson.Gson;
import com.stocks.exception.StockRunTimeException;

public class CommonUtils {

	private static final Gson gson = new Gson();

	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}
	
	public static void validateRequestBody(Errors errors) {
		if (errors.hasErrors()) {
        	  throw new StockRunTimeException(AppConstants.ERROR_CODE_INVALID_REQUEST_BODY,errors.getAllErrors()
                      .stream().map(x -> x.getDefaultMessage())
                      .collect(Collectors.joining(",")));


        }
	}

}
