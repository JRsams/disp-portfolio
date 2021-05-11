package org.camunda.stock;

public class Response {
	private String message;
	private String result;

public Response(String message, String result) {
	this.message = message;
	this.result = result;
}

public Response() {
	
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getResult() {
	return result;
}

public void setResult(String result) {
	this.result = result;
}
}