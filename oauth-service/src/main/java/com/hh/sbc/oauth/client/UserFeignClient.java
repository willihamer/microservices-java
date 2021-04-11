package com.hh.sbc.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.hh.sbc.userscommons.entity.User;

@FeignClient(name = "users-service")
public interface UserFeignClient {

	@GetMapping("/users/search/lookForUser")
	public User findByUsername(@RequestParam String username);

	@PutMapping("/users/{id}")
	public User update(@RequestBody User user, @PathVariable Long id);
	
}
