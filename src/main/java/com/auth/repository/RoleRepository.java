package com.auth.repository;

import com.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            UPDATE tb_roles AS roles SET roles.name = :newRole WHERE roles.id = :id
            """)
    void updateRole(Long id, String newRole);
}
