package com.java.basicApi.testApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.basicApi.testApi.exception.ResourceNotFound;
import com.java.basicApi.testApi.model.Customer;
import com.java.basicApi.testApi.service.CustomerService;

@RestController
public class MainController {

	@Autowired
	private CustomerService cusService;

	@RequestMapping(value="/createCustomer", method = RequestMethod.POST)
	public String create(@RequestParam String name, @RequestParam String email) {
		Customer cus = cusService.create(name, email);
		return "Create Successfully >> " + cus.toString();
	}
	
	@RequestMapping(value="/getcustomer", method = RequestMethod.GET)
	public Customer getCustomer(@RequestParam("name") String name) {
		System.out.println("name >> " + name);
		return cusService.getByName(name);
	}
	
	@RequestMapping(value="/getcustomerId", method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomerId(@RequestParam String id) throws ResourceNotFound {
		return cusService.getById(id);
	}
	
	@RequestMapping(value="/getAllCustomers", method = RequestMethod.GET)
	public List<Customer> getAll() {
		return cusService.getAll();
	}
	
	@RequestMapping(value="/upDateCustomer", method = RequestMethod.PUT)
	public String update(@RequestParam String name, @RequestParam String email) {
		cusService.update(name, email);
		return "Update Successfully name : " + name;
	}
	
	@RequestMapping(value="/deleteCustomer", method = RequestMethod.DELETE)
	public String delete(@RequestParam String name) {
		cusService.delete(name);
		return "Deleted name : " + name;
	}
	
	@RequestMapping(value="/deleteAllCustomer", method = RequestMethod.DELETE)
	public String deleteAll() {
		cusService.deleteAll();
		return "Deleted all records";
	}
}
