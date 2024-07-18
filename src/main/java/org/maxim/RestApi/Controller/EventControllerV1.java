package org.maxim.RestApi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.maxim.RestApi.DTO.EventDTO;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.service.EventService;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/events/*")
public class EventControllerV1 extends HttpServlet {
    private final EventService eventService = new EventService();
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        String substring = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);
        if (!substring.isEmpty()) {
            try {
                int id = Integer.parseInt(substring);
                EventDTO event = eventService.getEvent(id);
                String s = gson.toJson(event);
                writer.println(s);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.println("Invalid event ID");
            }
        } else {
            List<EventDTO> all = eventService.getAll();
            String s = gson.toJson(all);
            writer.println(s);
        }
        writer.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String collect = reader.lines().collect(Collectors.joining());
        EventDTO eventDTO = gson.fromJson(collect, EventDTO.class);
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        String json = gson.toJson(createdEvent);
        PrintWriter writer = resp.getWriter();
        writer.println(json);
        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        String requestURI = req.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        if (!substring.isEmpty()) {
            try {
                int id = Integer.parseInt(substring);
                eventService.deleteEvent(id);
                writer.println("Event deleted ");
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.println("error Invalid event ");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.println("error Event ID is required");
        }
        writer.close();
    }
}