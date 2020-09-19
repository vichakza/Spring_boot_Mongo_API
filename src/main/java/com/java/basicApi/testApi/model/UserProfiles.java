package com.java.basicApi.testApi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userProfiles" )
public class UserProfiles {
	@Id
	private String id;
	private String username;
	private String password;
	private String address;
	private String phone;
	private float salary;
	private String referCode;
	private String memberType;
	
	public UserProfiles(String id, String username, String password, String address, String phone, float salary,
			String referCode, String memberType) {
		this.username = username;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.salary = salary;
		this.referCode = referCode;
		this.memberType = memberType;
	}

	
	public UserProfiles() {
		super();
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", username=" + username + ", password=" + password + ", address=" + address
				+ ", phone=" + phone + ", salary=" + salary + ", referCode=" + referCode + ", memberType=" + memberType
				+ "]";
	}
	
}
