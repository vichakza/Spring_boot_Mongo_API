package com.java.basicApi.testApi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.basicApi.testApi.model.UserProfiles;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfiles, String> {
	public UserProfiles findByUsername(String username);
//	public Optional<UserProfiles> findByUserName(String username);
	public Boolean existsByUsername(String username);
//	public Boolean existsByEmail(String email);
}
