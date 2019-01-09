package com.sentiment.analysis.sentimentanalysis.model;

import lombok.Data;

/**
 * Created by youngwa on 2018/7/22.
 * dto的包装类
 */
@Data
public class ResponseResult<T> implements ResponseBase{
	public final static String SUCCESS = "0";
	private boolean isSuccess = true;
	private String code = "0";
	private String message;
	private T data;

	public ResponseResult() {
	}

	public ResponseResult(boolean isSuccess, String code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}

	public ResponseResult(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
