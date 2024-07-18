package org.maxim.RestApi.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.maxim.RestApi.DTO.FileDTO;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.model.UFile;
import org.maxim.RestApi.service.FileService;
import org.maxim.RestApi.utils.LocalDateTimeAdapter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/files/*")
public class FileControllerV1 extends HttpServlet {
    private final FileService fileService = new FileService();
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String requestURI = request.getRequestURI();
        String substring = requestURI.substring(requestURI.lastIndexOf("/") + 1);

        if (!substring.isEmpty()) {
            try {
                int id = Integer.parseInt(substring);
                FileDTO file = fileService.getFile(id);
                String json = gson.toJson(file);
                writer.println(json);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.println("error Invalid file ID");
            }
        } else {
            List<FileDTO> allFiles = fileService.getAll();
            String json = gson.toJson(allFiles);
            writer.println(json);
        }
        writer.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        File filePath = new File("src/main/resources/files");
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        String name = UUID.randomUUID().toString();
        File file = new File(filePath, name);

        try (ServletInputStream inputStream = req.getInputStream();
             FileWriter fileWriter = new FileWriter(file)) {
            int b;
            while ((b = inputStream.read()) != -1) {
                fileWriter.write(b);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        FileDTO savedFile = fileService.addFile(file, req);
        String json = gson.toJson(savedFile);
        PrintWriter writer = resp.getWriter();
        writer.println(json);
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String collect = reader.lines().collect(Collectors.joining());
        FileDTO fileDTO = gson.fromJson(collect, FileDTO.class);
        FileDTO updatedFile = fileService.updateFile(fileDTO, request);
        String json = gson.toJson(updatedFile);
        PrintWriter writer = response.getWriter();
        writer.println(json);
        writer.close();
    }


}