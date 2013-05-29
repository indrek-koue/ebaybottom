package com.titanium.ebaybottom.model;

import java.util.List;

public class Error {

	private List<String> errorId;
	private List<String> domain;
	private List<String> severity;
	private List<String> category;
	private List<String> message;
	private List<String> subdomain;
	private List<String> parameter;
	
	public List<String> getErrorId() {
		return errorId;
	}
	public void setErrorId(List<String> errorId) {
		this.errorId = errorId;
	}
	public List<String> getDomain() {
		return domain;
	}
	public void setDomain(List<String> domain) {
		this.domain = domain;
	}
	public List<String> getSeverity() {
		return severity;
	}
	public void setSeverity(List<String> severity) {
		this.severity = severity;
	}
	public List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}
	public List<String> getMessage() {
		return message;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	}
	public List<String> getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(List<String> subdomain) {
		this.subdomain = subdomain;
	}
	public List<String> getParameter() {
		return parameter;
	}
	public void setParameter(List<String> parameter) {
		this.parameter = parameter;
	}
	@Override
	public String toString() {
		return String
				.format("Id=%s, message=%s", errorId, message);
	}

}
