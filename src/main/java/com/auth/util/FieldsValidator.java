package com.auth.util;

import com.auth.dto.AuthUserDTO;
import com.auth.dto.RegisterUserDTO;
import com.auth.dto.UpdateUserDTO;
import com.auth.handler.CheckFieldsException;
import com.auth.handler.MessagesExceptions;

import java.util.Objects;
import java.util.stream.Stream;

public class FieldsValidator {

    public static void checkNullAuthUserDTO(AuthUserDTO authUserDTO) {
        if (Stream.of(authUserDTO.username(), authUserDTO.password()).anyMatch(Objects::isNull)) {
            throw new CheckFieldsException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
    }

    public static void checkNullRegisterUserDTO(RegisterUserDTO registerUserDTO) {
        if (Stream.of(registerUserDTO.name(), registerUserDTO.username(), registerUserDTO.password(), registerUserDTO.role()).anyMatch(Objects::isNull)) {
            throw new CheckFieldsException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
    }

    public static void checkNullUpdateUserDTO(UpdateUserDTO updateUserDTO){
        if(Stream.of(updateUserDTO.name(), updateUserDTO.username(), updateUserDTO.password()).anyMatch(Objects::isNull)){
            throw new CheckFieldsException(MessagesExceptions.EXCEPTION_BLANK_FIELD);
        }
    }

}
