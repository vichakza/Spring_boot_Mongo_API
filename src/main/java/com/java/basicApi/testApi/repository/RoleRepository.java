package com.java.basicApi.testApi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.java.basicApi.testApi.model.ERole;
import com.java.basicApi.testApi.model.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	Optional<Role> findByName(ERole name);
	
}
