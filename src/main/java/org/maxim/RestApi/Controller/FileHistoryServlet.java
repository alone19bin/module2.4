package org.maxim.RestApi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.service.FileService;
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

@WebServlet("/files/history")
public class FileHistoryServlet extends HttpServlet {
    private final FileService fileService = new FileService();
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        List<Event> events = fileService.getAllEvents();
        writer.println(gson.toJson(events));
    }
}
