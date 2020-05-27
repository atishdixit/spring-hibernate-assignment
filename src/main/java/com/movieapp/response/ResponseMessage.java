package com.movieapp.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author admin
 *
 */
@XmlRootElement
public class ResponseMessage {

	private int code;
	private String status;
	private String message;
	private Object result;
	private String methodName;

	public ResponseMessage() {
	}

	public ResponseMessage(String methodName, int code, String status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.methodName = methodName;
	}

	public ResponseMessage(String methodName, int code, String status, Object result) {
		this.code = code;
		this.status = status;
		this.setResult(result);
		this.methodName = methodName;
	}

	public ResponseMessage(int code, String status, String message, Object result) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.setResult(result);
	}

	public ResponseMessage(String methodname, int code, String status, String message, Object result) {
		this.methodName = methodname;
		this.code = code;
		this.status = status;
		this.message = message;
		this.setResult(result);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
