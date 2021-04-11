package com.hh.sbc.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hh.sbc.oauth.client.UserFeignClient;
import com.hh.sbc.userscommons.entity.User;

import brave.Tracer;
import feign.FeignException;

@Service
public class UserService implements IUserService, UserDetailsService {

	private Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserFeignClient client;

	@Autowired
	private Tracer tracer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			User user = client.findByUsername(username);

			List<GrantedAuthority> authorities = user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

			log.info("User authenticated: " + username);

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					user.isEnabled(), true, true, true, authorities);
		} catch (FeignException e) {
			log.error("The user '" + username + "' does not exists");
			tracer.currentSpan().tag("error.message", "The user '" + username + "' does not exists: " + e.getMessage()); 
			throw new UsernameNotFoundException("The user '" + username + "' does not exists");
		}

	}

	@Override
	public User findByUsername(String username) {

		return client.findByUsername(username);
	}

	@Override
	public User update(User user, Long id) {
		return client.update(user, id);
	}

}
