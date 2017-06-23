package com.manage.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class HttpService {

	private HttpServletRequest request;
	private HttpSession session;
	private HttpServletResponse response;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest httpservletrequest) {
		request = httpservletrequest;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession httpsession) {
		session = httpsession;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse httpservletresponse) {
		response = httpservletresponse;
	}
}
