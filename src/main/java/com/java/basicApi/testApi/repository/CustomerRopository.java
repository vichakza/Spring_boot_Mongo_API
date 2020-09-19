package com.java.basicApi.testApi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.basicApi.testApi.model.Customer;

@Repository
public interface CustomerRopository extends MongoRepository<Customer, String> {
	public Customer findByName(String name);

}