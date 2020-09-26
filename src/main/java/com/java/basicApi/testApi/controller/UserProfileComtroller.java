package com.java.basicApi.testApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.basicApi.testApi.exception.ResourceNotFound;
import com.java.basicApi.testApi.model.ErrorM;
import com.java.basicApi.testApi.model.UserProfiles;
import com.java.basicApi.testApi.service.UserProfileService;

@RestController
public class UserProfileComtroller {
	@Autowired
	private UserProfileService userProService;
	
	@RequestMapping(value="/createUserProfile", method = RequestMethod.POST)
	public String createUserPro(@RequestParam("username") String username, @RequestParam("password") String password, 
			@RequestParam("address") String address, @RequestParam("phone") String phone, @RequestParam("salary") float salary) {
		
		ErrorM userProEr = userProService.create(username, password, address, phone, salary);
		if("1".equals(userProEr.getCode())) {
			return userProEr.getMessage();
		}
		return "Create Successfully!!!";
	}
	
	@RequestMapping(value="/getUserProfile", method = RequestMethod.GET)
	public UserProfiles getCustomer(@RequestParam("username") String username) {
		//System.out.println("userName >>" + username);
		return userProService.getByName(username);
	}
	
	@RequestMapping(value="/getUserProfileId", method = RequestMethod.GET)
	public ResponseEntity<UserProfiles> getCustomerId(@RequestParam String id) throws ResourceNotFound {
		return userProService.getById(id);
	}
	
	@RequestMapping(value="/getAllUserProfiles", method = RequestMethod.GET)
	@PreAuthorize("hasRole('MODERATOR')")
	public List<UserProfiles> getAll() {
		return userProService.getAll();
	}
	
	@RequestMapping(value="/upDateUserProfile", method = RequestMethod.PUT)
	public String update(@RequestParam("username") String username, @RequestParam("password") String password, 
			@RequestParam("address") String address, @RequestParam("phone") String phone, @RequestParam("salary") float salary) {
		userProService.update(username, password, address, phone, salary);
		return "Update Successfully username : " + username;
	}
	
	@RequestMapping(value="/deleteUserProfile", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('MODERATOR')") //@PreAuthorize("hasRole('ADMIN')")
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
