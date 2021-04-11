package com.hh.spc.products.models.service;

import java.util.List;

import com.hh.sbc.commons.models.Product;

public interface IProductService {

	public List<Product> findAll();

	public Product findById(Long id);
	
	public Product save(Product product);
	
	public void deleteById(Long id);

}
