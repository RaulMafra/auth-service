package com.auth.util;

import com.auth.dto.AuthUserDTO;
import com.auth.dto.RegisterUserDTO;
import com.auth.dto.UpdateUserDTO;
import com.auth.handler.exceptions.CheckFieldsException;
import com.auth.handler.MessagesExceptions;
import com.auth.model.RoleName;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FieldsValidator {

    public static void checkNullFieldsAuthUserDTO(AuthUserDTO authUserDTO) {
        if (Stream.of(authUserDTO.username(), authUserDTO.password()).anyMatch(Objects::isNull)) {
            throw new CheckFieldsException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
    }

    public static void checkFieldsRegisterUserDTO(List<RegisterUserDTO> registerUserDTO) {
        registerUserDTO.stream().forEach(user -> {
            if (Stream.of(user.name(), user.username(), user.password(), user.role()).anyMatch(Objects::isNull)){
                throw new CheckFieldsException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
            }
            if(user.role().equals(RoleName.ROLE_ADMINISTRATOR)){
                throw new CheckFieldsException(MessagesExceptions.CHECK_ROLE_ADMINISTRATOR);
            }
        });
    }

    public static void checkNullFieldsUpdateUserDTO(UpdateUserDTO updateUserDTO){
        if(Stream.of(updateUserDTO.name(), updateUserDTO.username(), updateUserDTO.password()).anyMatch(Objects::isNull)){
            throw new CheckFieldsException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
    }

    public static void checkNullFieldMyUser(String username){
        if(username == null){
            throw new CheckFieldsException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
    }

}
