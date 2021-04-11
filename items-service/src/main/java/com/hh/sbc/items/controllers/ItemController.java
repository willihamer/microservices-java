package com.hh.sbc.items.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hh.sbc.commons.models.Product;
import com.hh.sbc.items.models.Item;
import com.hh.sbc.items.services.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
public class ItemController {

	private static Logger log = org.slf4j.LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("serviceFeign")
//	@Qualifier("serviceRestTemplate")
	private ItemService itemService;

	@Value("${configuration.text}")
	private String text;

	@GetMapping("/list")
	public List<Item> list() {
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/view/{id}/q/{quantity}")
	public Item detail(@PathVariable Long id, @PathVariable Integer quantity) {
		return itemService.findById(id, quantity);
	}

	public Item alternativeMethod(Long id, Integer quantity) {
		Item item = new Item();
		Product product = new Product();

		item.setQuantity(quantity);
		product.setId(id);
		product.setName("Sony Camera, ex");
		product.setPrice(500.00);
		item.setProduct(product);
		return item;
	}

	@GetMapping("/get-config")
	public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {

		log.info(this.text);

		Map<String, String> json = new HashMap<>();
		json.put("text", this.text);
		json.put("port", port);

		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("Author.name", env.getProperty("configuration.author.name"));
		}

		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return this.itemService.save(product);
	}
	
	@PutMapping("/edit/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product edit (@RequestBody Product product, @PathVariable Long id) {
		return this.itemService.update(product, id);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		this.itemService.delete(id);
	}
}
