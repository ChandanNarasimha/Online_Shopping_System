package com.shopping.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.exception.ResourceNotFoundException;
import com.shopping.model.Admin;
import com.shopping.model.Customer;
import com.shopping.model.Product;
import com.shopping.repository.AdminRepository;
import com.shopping.repository.CustomerRepository;
import com.shopping.repository.ProductRepository;
import com.shopping.service.AdminService;


@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProductRepository productRepository;

	public AdminServiceImpl(AdminRepository adminRepository) {
		super();
		this.adminRepository = adminRepository;
	}

	@Override
	public Admin saveAdmin(Admin admin) {
		System.out.println("admin register service" + admin);

		return adminRepository.save(admin);
	}

	@Override
	public Admin loginAdmin(Admin admin) {
		return this.adminRepository.findByAdminEmailIdAndAdminPassword(admin.adminEmailId, admin.adminPassword)
				.orElseThrow(() -> new ResourceNotFoundException("Admin ", "Id",
						admin.adminEmailId + "and password " + admin.adminPassword));
	}

	@Override
	public List<Product> getAllProducts(long adminId) {

		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public List<Customer> getAllCustomers(long adminId) {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

}
