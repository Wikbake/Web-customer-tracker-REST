package com.github.wikbake.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wikbake.springdemo.entity.Customer;
import com.github.wikbake.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// autowire the CustomerService
	@Autowired
	private CustomerService customerService;
	
	// add mapping for GET /customers
	@GetMapping("/customers")
	private List<Customer> getCustomers() {
		
		return customerService.getCustomers();
	}
	
	// add a mapping for GET /customers/{customerId} 
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if (theCustomer == null)
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		
		return theCustomer;
	}
	
	
	// add mapping for POST /customers - add new customer
	@PostMapping("/customers")		
	public Customer addCustomer(@RequestBody Customer theCustomer) { 		// use @RB to access the request body as a POJO
		
		// also just in case the pass an id in JSON - set id to 0 
		// this is a force save of a new item, instead of update
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// add mapping for PUT /customers - update existing customer
	@PutMapping("/customers")		
	public Customer updateCustomer(@RequestBody Customer theCustomer) { 		// use @RB to access the request body as a POJO
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// add mapping for DELETE /customers/{customerId} - delete existing customer
	@DeleteMapping("/customers/{customerId}")		
	public String deleteCustomer(@PathVariable int customerId) { 		// use @RB to access the request body as a POJO
		
		Customer tempCustomer = customerService.getCustomer(customerId);
		
			// throw exception if null 
		if (tempCustomer == null)
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		
		customerService.deleteCustomer(customerId);
		
		return "Deleted customer id - " + customerId;
	}
	
}
