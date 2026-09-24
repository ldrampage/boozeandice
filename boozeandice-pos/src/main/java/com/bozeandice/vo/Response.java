package com.bozeandice.vo;

import java.io.Serializable;
import java.util.Map;

public class Response<T> {
	
	private int statusCode;
	private String message;
	private T data;
	private Map<String, Serializable> properties;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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
	public Map<String, Serializable> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Serializable> properties) {
		this.properties = properties;
	}
	
	

}
