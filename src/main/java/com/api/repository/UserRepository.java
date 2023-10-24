package com.api.repository;

import com.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT e FROM User e JOIN FETCH e.role WHERE e.username = (:username)")
	public User findByUsername(@Param("username") String username);
	
//	boolean existByUsername(String username);

}
