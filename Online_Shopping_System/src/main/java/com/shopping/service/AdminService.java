package com.shopping.service;

import java.util.List;

import com.shopping.model.Admin;
import com.shopping.model.Customer;
import com.shopping.model.Product;



public interface AdminService {
	Admin saveAdmin(Admin admin);
	Admin loginAdmin(Admin admin);
	
	public List<Product> getAllProducts(long adminId);
	public List<Customer> getAllCustomers(long adminId);
}
