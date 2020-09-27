package com.java.basicApi.testApi.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.basicApi.testApi.exception.ResourceNotFound;
import com.java.basicApi.testApi.model.ErrorM;
import com.java.basicApi.testApi.model.ErrorM.STATUS;
import com.java.basicApi.testApi.model.UserProfiles;
import com.java.basicApi.testApi.repository.UserProfileRepository;

@Service
public class UserProfileService {
	@Autowired
	private UserProfileRepository userProRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	//Create operation
	public ErrorM create(String username, String password, String address, String phone, float salary) {
		ErrorM error = new ErrorM();
		error.setStatus(STATUS.FAIL);
		String msg = "";
		String referCode = "";
		String memberType = "Draft";
		if(phone.isEmpty()) {
			msg = "please enter phone number";
			error.setCode("1");
			error.setMessage(msg);
		}else {
			SimpleDateFormat formatter= new SimpleDateFormat("yyymmdd");
			Date date = new Date(System.currentTimeMillis());
			referCode = formatter.format(date)+phone.substring(6);
		}
		
		memberType = this.CheckMemberType(salary);
		if("Draft".equals(memberType)) {
			msg = "please enter salary more that 15,000.0 Bath";
			error.setCode("1");
			error.setMessage(msg);
		}
		
		//UserProfile userPro = new UserProfile(userName, passWord, address, phone, salary, referCode, memberType);
		UserProfiles userPro = new UserProfiles();
		userPro.setUsername(username);
		userPro.setPassword(password);
		userPro.setAddress(address);
		userPro.setPhone(phone);
		userPro.setSalary(salary);
		userPro.setReferCode(referCode);
		userPro.setMemberType(memberType);
		
		if(!"1".equals(error.getCode())){
			userProRepo.save(userPro);
		}
		
		return error;
	}
	
	public String CheckMemberType(float salary) {
		String memberType = "Draft";
		if(salary >50000.0) {
			memberType = "Platinum";
		}
		else if(salary >= 30000.0 && salary <= 50000.0) {
			memberType = "Gold";
		}else if(salary > 15000.0 && salary < 30000.0 ) {
			memberType = "Silver";
		}else {
			memberType = "Draft";
		}
		
		return memberType;
	}
	
	//Retrieve operation
	public List<UserProfiles> getAll(){
		return userProRepo.findAll();
	}
	
	//Find by name
	public UserProfiles getByName(String username) { //UserProfiles
		return userProRepo.findByUsername(username);
	}
	
	//Find by name
//	public Optional<UserProfiles> getByNameNew(String username) {
//		return userProRepo.findByUserName(username);
//	}
	
	//Find by id
	public UserProfiles getById(String id) throws ResourceNotFound {
		UserProfiles userPro = userProRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Error, username Not found id => " + id));
		return userPro;
	}
	
	//Update operation
	public ErrorM update(UserProfiles userProfiles) throws ResourceNotFound { //String username, String password, String address, String phone, float salary
		
		String msg = "";
		ErrorM error = new ErrorM();
		String memberType = CheckMemberType(userProfiles.getSalary());
		if("Draft".equals(memberType)) {
			msg = "please enter salary more that 15,000.0 Bath";
			error.setCode("1");
			error.setMessage(msg);
		}
		
		System.out.println("------------- userProfiles : " + userProfiles.toString());
		userProfiles.setPassword(encoder.encode(userProfiles.getPassword()));
		userProfiles.setMemberType(memberType);
		System.out.println("--after update- userProfiles : " + userProfiles.toString());
			
			if(!"1".equals(error.getCode())){
				userProRepo.save(userProfiles);
			}
			
		return error;
	}
	
	//Delete operation
	public void deleteAll() {
		userProRepo.deleteAll();
	}
	
	//Delete by name
	public void delete(String username) {
		UserProfiles userPro = userProRepo.findByUsername(username);
		userProRepo.delete(userPro);
	}
	
}

