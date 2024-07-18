package org.maxim.RestApi.service;

import org.maxim.RestApi.DTO.Converter.UserConverter;
import org.maxim.RestApi.DTO.UserDTO;
import org.maxim.RestApi.model.User;
import org.maxim.RestApi.repository.UserRepository;
import org.maxim.RestApi.repository.hiber.HibernateUserRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
    }

    public UserDTO getUser(Integer id) {
        User user = userRepository.get(id);
        return UserConverter.toDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserConverter.toEntity(userDTO);
        User createdUser = userRepository.create(user);
        return UserConverter.toDTO(createdUser);
    }

    public UserDTO updateUser(UserDTO userDTO) {
        User user = UserConverter.toEntity(userDTO);
        User updatedUser = userRepository.update(user);
        return UserConverter.toDTO(updatedUser);
    }

    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    public List<UserDTO> getAll() {
        return userRepository.getAll().stream()
                .map(UserConverter::toDTO)
                .collect(Collectors.toList());
    }
}
