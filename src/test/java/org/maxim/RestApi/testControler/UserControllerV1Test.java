package org.maxim.RestApi.testControler;




import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maxim.RestApi.Controller.UserControllerV1;
import org.maxim.RestApi.DTO.UserDTO;
import org.maxim.RestApi.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerV1Test {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerV1 userController;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(request.getRequestURI()).thenReturn("/users/");
        List<UserDTO> users = Collections.singletonList(new UserDTO());
        when(userService.getAll()).thenReturn(users);

        userController.doGet(request, response);


    }

    @Test
    public void testGetUserById() throws Exception {
        when(request.getRequestURI()).thenReturn("/users/1");
        UserDTO user = new UserDTO();
        user.setId(1);
        when(userService.getUser(1)).thenReturn(user);

        userController.doGet(request, response);


    }
}



