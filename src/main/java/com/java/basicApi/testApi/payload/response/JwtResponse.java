package com.java.basicApi.testApi.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String username;
	private String address;
	private String phone;
	private float salary;
	private String referCode;
	private String memberType;
	private List<String> roles;

	public JwtResponse(String token, String id, String username, String address, String phone, float salary,
			String referCode, String memberType, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.address = address;
		this.phone = phone;
		this.salary = salary;
		this.referCode = referCode;
		this.memberType = memberType;
		this.roles = roles;
	}

	public String getReferCode() {
		return referCode;
	}
	
	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}
	
	public String getMemberType() {
		return memberType;
	}
	
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}
