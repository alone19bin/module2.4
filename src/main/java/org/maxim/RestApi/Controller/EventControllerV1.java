package org.maxim.RestApi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.service.EventService;
import org.maxim.RestApi.utils.LocalDateTimeAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/events/*")
public class EventControllerV1 extends HttpServlet {
    private final EventService eventService = new EventService();
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();


        String substring = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") +1);
        if(!substring.isEmpty()) {
            int id = Integer.parseInt(substring);
            Event event = eventService.getEvent(id);
            String s = gson.toJson(event);
            writer.println(s);
        } else {
            List<Event> all = eventService.getAll();
            String s = gson.toJson(all);
            writer.println(s);
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        String substring = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") +1);
        if (!substring.isEmpty()) {
            int id = Integer.parseInt(substring);
            eventService.deleteEvent(id);
            writer.println("Event Delete");
        } else {
            writer.println("Incorrect id event ");
        }
        writer.close();
    }
}