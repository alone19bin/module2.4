package org.maxim.RestApi.DTO.Converter;

import org.maxim.RestApi.DTO.UserDTO;
import org.maxim.RestApi.model.User;

public class UserConverter {
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        return user;
    }
}

