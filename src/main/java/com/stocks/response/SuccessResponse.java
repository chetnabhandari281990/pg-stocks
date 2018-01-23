package com.stocks.response;

public class SuccessResponse<T> {
	
	 public SuccessResponse() {
		super();
	}

	public SuccessResponse(T body) {
		super();
		this.body = body;
	}

	T body;

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	

}
