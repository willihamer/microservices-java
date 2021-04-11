package com.hh.sbc.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hh.sbc.oauth.services.IUserService;
import com.hh.sbc.userscommons.entity.User;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private Tracer tracer;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {

		if (authentication.getName().equalsIgnoreCase("angularapp") || authentication.getName().equalsIgnoreCase("reactapp")) {
			return; 
		}
		
		User user = this.userService.findByUsername(authentication.getName());

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println("Success Login: " + userDetails.getUsername());
		log.info("Success Login: " + userDetails.getUsername());

		if (user.getAttempts() != null) {
			user.setAttempts(0);
			this.userService.update(user, user.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {

		System.out.println("Errpr Login: " + exception.getMessage());
		log.error("Errpr Login: " + exception.getMessage());
		tracer.currentSpan().tag("error.message", "Errpr Login: " + exception.getMessage());

		try {
			User user = this.userService.findByUsername(authentication.getName());
			if (user.getAttempts() == null) {
				user.setAttempts(0);
			}

			user.setAttempts(user.getAttempts() + 1);

			if (user.getAttempts() >= 3) {
				user.setEnabled(false);
				
			}

			this.userService.update(user, user.getId());

		} catch (FeignException e) {
			log.error(String.format("The user %s was not found: %s", authentication.getName(), e.getMessage()));
		}

	}

}
