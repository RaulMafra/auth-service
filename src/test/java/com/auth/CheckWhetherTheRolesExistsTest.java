package com.auth;

import com.auth.dto.RegisterUserDTO;
import com.auth.handler.BusinessException;
import com.auth.model.RoleName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckWhetherTheRolesExistsTest {

    static RegisterUserDTO user1 = new RegisterUserDTO("Raul", "raulc", "raul123", RoleName.ROLE_ADMINISTRATOR);
    static RegisterUserDTO user2 = new RegisterUserDTO("Jose", "josex", "jose123", RoleName.ROLE_USER);

    @Test
    public void comparesAllRolesOfTheEnumClass() {

        List<RoleName> expected = Arrays.asList(RoleName.ROLE_USER, RoleName.ROLE_ADMINISTRATOR);

        List<RoleName> roles = Arrays.asList(RoleName.values());
        assertEquals(roles, expected);
    }

    @Test
    public void checkWhetherTheRoleProvidedIsNotEqualsTheRolesOfTheEnumClass(){
        List<RoleName> roles = Arrays.asList(RoleName.values());

        if(roles.stream().noneMatch(role -> role.equals(user1.role()))){
            throw new BusinessException("Role not exists");
        }

        assertDoesNotThrow(() -> BusinessException.class);
    }

}
