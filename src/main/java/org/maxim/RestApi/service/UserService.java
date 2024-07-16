package org.maxim.RestApi.service;

import org.maxim.RestApi.model.User;
import org.maxim.RestApi.repository.UserRepository;
import org.maxim.RestApi.repository.hiber.HibernateUserRepositoryImpl;

import java.util.List;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
    }

    public User getUser(Integer id) {
        return userRepository.get(id);
    }

    public User createUser(User user) {
        return userRepository.create(user);
    }

    public User updateUser(User user) {
        return  userRepository.update(user);
    }

    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    public List<User> getAll () {
        return userRepository.getAll();
    }

}