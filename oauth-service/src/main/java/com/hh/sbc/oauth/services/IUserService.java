package com.hh.sbc.oauth.services;

import com.hh.sbc.userscommons.entity.User;

public interface IUserService {

	public User findByUsername(String username);

	public User update(User user, Long id);
}
