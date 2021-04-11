package com.hh.spc.products.models.repository;

import org.springframework.data.repository.CrudRepository;
import com.hh.sbc.commons.models.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {
	
}
