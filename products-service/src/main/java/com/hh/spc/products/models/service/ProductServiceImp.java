package com.hh.spc.products.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hh.sbc.commons.models.Product;

import com.hh.spc.products.models.repository.ProductRepository;

@Service
public class ProductServiceImp implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Product save(Product product) {
		return this.productRepository.save(product);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {

		this.productRepository.deleteById(id);
	}

}
