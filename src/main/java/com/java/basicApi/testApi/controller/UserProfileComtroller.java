package com.java.basicApi.testApi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.basicApi.testApi.exception.ResourceNotFound;
import com.java.basicApi.testApi.model.ErrorM;
import com.java.basicApi.testApi.model.UserProfiles;
import com.java.basicApi.testApi.service.UserProfileService;

@RestController
@RequestMapping("/userProfiles")
public class UserProfileComtroller {
	@Autowired
	private UserProfileService userProService;
	
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	@RequestMapping(value="/createUserProfile", method = RequestMethod.POST)
	public String createUserPro(@RequestParam("username") String username, @RequestParam("password") String password, 
			@RequestParam("address") String address, @RequestParam("phone") String phone, @RequestParam("salary") float salary) {
		
		ErrorM userProEr = userProService.create(username, password, address, phone, salary);
		if("1".equals(userProEr.getCode())) {
			return userProEr.getMessage();
		}
		return "Create Successfully!!!";
	}
	
	@GetMapping("/getByName")
	public UserProfiles getCustomer(@RequestParam("username") String username) {
		//System.out.println("userName >>" + username);
		return userProService.getByName(username);
	}
	
	@GetMapping("/getById")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public UserProfiles getCustomerId(@RequestParam String id) throws ResourceNotFound {
		return userProService.getById(id);
	}
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<UserProfiles> getAll() {
		return userProService.getAll();
	}
	
	@PutMapping("/updateUserProfile")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')") //@PathVariable(name = "id") String id
	public String update(@RequestParam("id") String id, @Valid @RequestBody UserProfiles newUserPro) throws ResourceNotFound {
		UserProfiles oldUserPro = userProService.getById(id);
	
		//set update
		oldUserPro.setUsername(newUserPro.getUsername().isEmpty() ? oldUserPro.getUsername() : newUserPro.getUsername());
		oldUserPro.setPassword(newUserPro.getPassword().isEmpty() ? oldUserPro.getPassword() : newUserPro.getPassword());
		oldUserPro.setAddress(newUserPro.getAddress().isEmpty() ? oldUserPro.getAddress() : newUserPro.getAddress());
		oldUserPro.setPhone(newUserPro.getPhone().isEmpty() ? oldUserPro.getPhone() : newUserPro.getPhone());
		oldUserPro.setSalary(String.valueOf(newUserPro.getSalary()).isEmpty() ? oldUserPro.getSalary() : newUserPro.getSalary());
		
		ErrorM error = userProService.update(oldUserPro);
		String msg = "";
		if(error.getMessage().isEmpty()) {
			msg = "Update Successfully username : " + newUserPro.getUsername();
		}else {
			msg =  error.getMessage();
		}
		return msg;
	}
	
	@RequestMapping(value="/deleteUserProfile", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')") //@PreAuthorize("hasRole('MODERATOR')")
	public String delete(@RequestParam("username") String username) {
		userProService.delete(username);
		return "Deleted name : " + username;
	}
	
	@RequestMapping(value="/deleteAllUser", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('MODERATOR')")
	public String deleteAllUserProfile() {
		userProService.deleteAll();
		return "Deleted all records";
	}

}
