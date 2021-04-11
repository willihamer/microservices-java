package com.hh.sbc.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.hh.sbc.oauth.services.IUserService;
import com.hh.sbc.userscommons.entity.User;

@Component
public class AditionalInfoToken implements TokenEnhancer {

	@Autowired
	private IUserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();

		User user = userService.findByUsername(authentication.getName());
		info.put("name", user.getName());
		info.put("last name", user.getLastName());
		info.put("email", user.getEmail());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		return accessToken;

	}

}
