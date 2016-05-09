package com.androidoven.server.model;

import com.androidoven.transport.xsd.common.Customer;

public class CustomerPojo {
	
	private static CustomerPojo I = null;
	private Customer customer = null;
	
	private CustomerPojo() {}
	
	private void setupCustomer() {
		this.customer = new Customer();
		this.customer.setId("admin");
		this.customer.setName("Dish Next Door");
		this.customer.setPassword("Password123");
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public static CustomerPojo getInstance() {
		if (null == I) {
			I = new CustomerPojo();
			I.setupCustomer();
		}
		return I;
	}

	public boolean verifyCustomer(Customer customer) {
		return customer.getId().equals(this.customer.getId()) && customer.getPassword().equals(this.customer.getPassword());
	}

}