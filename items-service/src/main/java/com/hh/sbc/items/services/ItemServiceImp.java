package com.hh.sbc.items.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hh.sbc.commons.models.Product;
import com.hh.sbc.items.models.Item;

@Service("serviceRestTemplate")
public class ItemServiceImp implements ItemService {

	@Autowired
	private RestTemplate clientRest;

	@Value("${url}")
	private String url;

	@Override
	public List<Item> findAll() {
		List<Product> products = Arrays.asList(clientRest.getForObject(url + "list", Product[].class));

		return products.stream().map((p) -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer quantity) {
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		Product product = clientRest.getForObject(url + "view/{id}", Product.class, pathVariables);
		return new Item(product, quantity);
	}

	@Override
	public Product save(Product product) {

		HttpEntity<Product> body = new HttpEntity<Product>(product);

		ResponseEntity<Product> response = this.clientRest.exchange("http://products-service/create", HttpMethod.POST,
				body, Product.class);
		Product responseProduct = response.getBody();
		return responseProduct;
	}

	@Override
	public Product update(Product product, Long id) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());

		ResponseEntity<Product> response = this.clientRest.exchange("http://products-service/edit/{id}", HttpMethod.PUT,
				body, Product.class, pathVariables);
		Product responseProduct = response.getBody();
		return responseProduct;
	}

	@Override
	public void delete(Long id) {
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString());
		this.clientRest.delete("http://products-service/delete", pathVariables);

	}

}
