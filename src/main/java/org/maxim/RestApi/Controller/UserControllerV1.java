package org.maxim.RestApi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.maxim.RestApi.DTO.UserDTO;
import org.maxim.RestApi.model.User;
import org.maxim.RestApi.service.UserService;
import org.maxim.RestApi.utils.LocalDateTimeAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/users/*")

public class UserControllerV1 extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        String json = "";
        if (!substring.isEmpty()) {
            try {
                int id = Integer.parseInt(substring);
                UserDTO user = userService.getUser(id);
                json = GSON.toJson(user);
                writer.println(json);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.println("error Invalid user ID");
            }
        } else {
            List<UserDTO> users = userService.getAll();
            json = GSON.toJson(users);
            writer.println(json);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String collect = reader.lines().collect(Collectors.joining());
        UserDTO userDTO = GSON.fromJson(collect, UserDTO.class);
        UserDTO createdUser = userService.createUser(userDTO);
        String json = GSON.toJson(createdUser);
        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String collect = reader.lines().collect(Collectors.joining());
        UserDTO userDTO = GSON.fromJson(collect, UserDTO.class);
        UserDTO updatedUser = userService.updateUser(userDTO);
        String json = GSON.toJson(updatedUser);
        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        if (!substring.isEmpty()) {
            try {
                int id = Integer.parseInt(substring);
                userService.deleteUser(id);
                writer.println("message User deleted successfully");
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.println("error Invalid user ID");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.println("error User ID is required");
        }
        writer.close();
    }
}
