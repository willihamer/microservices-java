package com.hh.sbc.items.services;

import java.util.List;

import com.hh.sbc.commons.models.Product;
import com.hh.sbc.items.models.Item;

public interface ItemService {

	public List<Item> findAll();

	public Item findById(Long id, Integer Quantity);
	
	public Product save(Product product);
	
	public Product update(Product product, Long id);
	
	public void delete(Long id);
	
	
	

}
