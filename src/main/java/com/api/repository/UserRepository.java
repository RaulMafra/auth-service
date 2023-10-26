package com.api.repository;

import com.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

//	@Query("SELECT e FROM User e JOIN FETCH e.role WHERE e.username = (:username)")
//	 User findByUsername(@Param("username") String username);

    Optional<User> findByUsername(String username);

//	boolean existByUsername(String username);

}
