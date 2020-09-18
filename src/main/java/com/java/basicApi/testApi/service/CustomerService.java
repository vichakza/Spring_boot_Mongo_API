package com.java.basicApi.testApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.basicApi.testApi.exception.ResourceNotFound;
import com.java.basicApi.testApi.model.Customer;
import com.java.basicApi.testApi.repository.CustomerRopository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRopository cusRepo;
	
	//Create operation
	public Customer create(String name, String email) {
		return cusRepo.save(new Customer(name,email));
	}
	
	//Retrieve operation
	public List<Customer> getAll(){
		return cusRepo.findAll();
	}
	
	//Find by name
	public Customer getByName(String name) {
		return cusRepo.findByName(name);
	}
	
	//Find by id
	public ResponseEntity<Customer> getById(String id) throws ResourceNotFound {
		Customer cus = cusRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Error, Customer Not found id => " + id));
		return ResponseEntity.ok().body(cus);
	}
	
	//Update operation
	public Customer update(String name,String email) {
		Customer cus = cusRepo.findByName(name);
		cus.setEmail(email);
		return cusRepo.save(cus);
	}
	
	//Delete operation
	public void deleteAll() {
		cusRepo.deleteAll();
	}
	
	//Delete by name
	public void delete(String name) {
		Customer cus = cusRepo.findByName(name);
		cusRepo.delete(cus);
	}
	
}
