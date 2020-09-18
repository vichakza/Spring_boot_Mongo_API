package com.java.basicApi.testApi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserProfile {
	@Id
	private String id;
	private String userName;
	private String passWord;
	private String address;
	private int phone;
	private float salary;
	private String referCode;
	private String memberType;
	
	public UserProfile(String userName, String passWord, String address, int phone, float salary, String referCode,
			String memberType) {
		this.userName = userName;
		this.passWord = passWord;
		this.address = address;
		this.phone = phone;
		this.salary = salary;
		this.referCode = referCode;
		this.memberType = memberType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
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
		return "UserProfile [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", address=" + address
				+ ", phone=" + phone + ", salary=" + salary + ", referCode=" + referCode + ", memberType=" + memberType
				+ "]";
	}

}
