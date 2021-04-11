package com.hh.sbc.items.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.hh.sbc.commons.models.Product;
import com.hh.sbc.items.clients.ProductClientRest;
import com.hh.sbc.items.models.Item;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductClientRest clientFeign;

	@Override
	public List<Item> findAll() {
		return clientFeign.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer Quantity) {
		return new Item(clientFeign.detail(id), Quantity);
	}

	@Override
	public Product save(Product product) {
		return this.clientFeign.create(product);
	}

	@Override
	public Product update(Product product, Long id) {
		return this.clientFeign.update(product, id);
	}

	@Override
	public void delete(Long id) {
		this.clientFeign.delete(id);

	}

}
