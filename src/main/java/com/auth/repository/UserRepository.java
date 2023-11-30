package com.auth.repository;

import com.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  {

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
            UPDATE tb_user AS u
            SET u.name = :newName, u.username = :newUsername, u.password = :newPassword
            WHERE u.id_user = :id
            """
    )
    void updateUserOnly(Long id, String newName, String newUsername, String newPassword);
}
