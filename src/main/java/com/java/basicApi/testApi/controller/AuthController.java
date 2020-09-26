package com.java.basicApi.testApi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.basicApi.testApi.model.ERole;
import com.java.basicApi.testApi.model.ErrorM;
import com.java.basicApi.testApi.model.Role;
import com.java.basicApi.testApi.model.UserProfiles;
import com.java.basicApi.testApi.model.ErrorM.STATUS;
import com.java.basicApi.testApi.payload.request.LoginRequest;
import com.java.basicApi.testApi.payload.request.SignupRequest;
import com.java.basicApi.testApi.payload.response.JwtResponse;
import com.java.basicApi.testApi.payload.response.MessageResponse;
import com.java.basicApi.testApi.repository.RoleRepository;
import com.java.basicApi.testApi.repository.UserProfileRepository;
import com.java.basicApi.testApi.security.jwt.JwtUtils;
import com.java.basicApi.testApi.security.service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserProfileRepository userProRepository;
	   
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")	
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												userDetails.getId(), 
												userDetails.getUsername(), 
												userDetails.getAddress(),
												userDetails.getPhone(),
												userDetails.getSalary(),
												userDetails.getReferCode(),
												userDetails.getMemberType(),
												roles));
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		String msgTofrom = "";
		
		if (userProRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		
		UserProfiles userProfile = new UserProfiles(
											signUpRequest.getUsername(),
											encoder.encode(signUpRequest.getPassword()),
											signUpRequest.getAddress(),
											signUpRequest.getPhone(),
											signUpRequest.getSalary()
											);
		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
			
			ErrorM error = new ErrorM();
			error.setStatus(STATUS.FAIL);
			String msg = "";
			String referCode = "";
			String memberType = "Draft";
			if(signUpRequest.getPhone().isEmpty()) {
				msg = "please enter phone number";
				error.setCode("1");
				error.setMessage(msg);
			}else {
				SimpleDateFormat formatter= new SimpleDateFormat("yyymmdd");
				Date date = new Date(System.currentTimeMillis());
				referCode = formatter.format(date)+signUpRequest.getPhone().substring(6);
			}
			
			memberType = this.CheckMemberType(signUpRequest.getSalary());
			if("Draft".equals(memberType)) {
				msg = "please enter salary more that 15,000.0 Bath";
				error.setCode("1");
				error.setMessage(msg);
			}
			
			
			userProfile.setReferCode(referCode);
			userProfile.setMemberType(memberType);
			userProfile.setRoles(roles);
			if(!"1".equals(error.getCode())) {
				userProRepository.save(userProfile);
			}
			
			if(error.getMessage().isEmpty()) {
				msgTofrom = "User registered successfully!";
			}else {
				msgTofrom = error.getMessage();
			}
		}
		return ResponseEntity.ok(new MessageResponse(msgTofrom));
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
}
