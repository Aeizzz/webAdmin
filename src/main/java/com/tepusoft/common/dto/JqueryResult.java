package com.tepusoft.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JqueryResult {
	private int code;
	@JsonProperty(value="msg")
	private String message;
	private Object data;
	
	
	private JqueryResult() {
		super();
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public static class Builder {
		private  int code;
		private String message;
		private Object data;
		
		public Builder(int code){
			this.code=code;
		}
		public Builder message(String message) {
            this.message = message;
            return this;
        }
		public Builder data(Object data) {
            this.data = data;
            return this;
        }
		 public JqueryResult build() { // 构建，返回一个新对象
	            return new JqueryResult(this);
	        }
		
	}
	private JqueryResult(Builder b) {
		this.code = b.code;
		this.message = b.message;
		this.data = b.data;
	}
}
