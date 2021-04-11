package com.hh.sbc.users.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.hh.sbc.userscommons.entity.User;

@RepositoryRestResource(path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	@RestResource(path = "lookForUser")
	public User findByUsername(@Param("username") String username);

	@Query("SELECT u FROM User u WHERE u.username=?1")
	public User getByUsername(String username);

}
