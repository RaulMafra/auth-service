package com.auth.repository;

import com.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = """
            UPDATE tb_user
            SET name = :name, username = :username, password = :password
            WHERE id_user = :id
            """)
    void updateUser(Long id, String name, String username, String password);
}
