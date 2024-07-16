package org.maxim.RestApi.testControler;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.model.User;
import org.maxim.RestApi.repository.UserRepository;
import org.maxim.RestApi.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;


    private User getStubUser() {
        return new User(1, "name", new ArrayList<Event>());
    }

    @Test
    void getUser() {
        when(userRepository.get(1)).thenReturn(getStubUser());
        User user = userService.getUser(1);
        assertEquals("name", user.getName());
    }

    @Test
    void updateUser() {
        when(userRepository.update(any(User.class))).thenReturn(getStubUser());
        User user = userService.updateUser(new User());
        assertEquals("name", user.getName());
    }

    @Test
    void createUser() {
        when(userRepository.create(any(User.class))).thenReturn(getStubUser());
        User user = userService.createUser(new User());
        assertEquals("name", user.getName());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(getStubUser().getId());
        verify(userRepository, times(1)).delete(anyInt());
    }

    @Test
    void getAll() {
        List<User> userList = new ArrayList<>();
        when(userRepository.getAll()).thenReturn(userList);
        List<User> userEntities = userService.getAll();
        assertEquals(userList, userEntities);
    }
}
