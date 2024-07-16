package org.maxim.RestApi.service;

import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.model.UFile;
import org.maxim.RestApi.model.User;
import org.maxim.RestApi.repository.FileRepository;
import org.maxim.RestApi.repository.UserRepository;
import org.maxim.RestApi.repository.hiber.HibernateFileRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class FileService {
    private final FileRepository fileRepository;
    private final EventService eventService;

    public FileService() {
        fileRepository = new HibernateFileRepositoryImpl();
        eventService = new EventService();

    }

    public FileService(FileRepository fileRepository, EventService eventService) {
        this.fileRepository = fileRepository;
        this.eventService = eventService;
    }

    public Event makeEvent(HttpServletRequest request, String nameEvent, UFile file) {
        String userIdHeader = request.getHeader("user-id");
        if (userIdHeader == null) {
            throw new IllegalArgumentException("Header 'user-id' is missing");
        }

        Integer userId;
        try {
            userId = Integer.parseInt(userIdHeader);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Header 'user-id' must be an integer", e);
        }

        User user = new User();
        user.setId(userId);
        System.out.println("User ID: " + userId); // Логирование ID пользователя

        Event event = new Event();
        event.setName(nameEvent);
        event.setCreated(LocalDateTime.now());
        event.setUFile(file);
        event.setUser(user);
        System.out.println("Creating Event: " + event); // Логирование события
        return eventService.createEvent(event);

    }
    public UFile addFile(File file, HttpServletRequest request) {
        UFile uFile = new UFile();
        uFile.setName(file.getName());
        uFile.setFilePath(file.getPath());
        UFile created = fileRepository.create(uFile);
        Event event = makeEvent(request, "Upload", created);
        eventService.createEvent(event);

        return created;
    }
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
    public UFile getFile(Integer id) {
        return fileRepository.get(id);
    }

    public List<UFile> getAll() {
        return fileRepository.getAll();
    }

    public UFile updateFile(UFile uFile, HttpServletRequest request) {
        UFile update = fileRepository.update(uFile);
        Event event = makeEvent(request, "Update", update);
        eventService.createEvent(event);
        return update;
    }

    public void deleteFile(UFile uFile, HttpServletRequest request) {
        Integer id = uFile.getId();
        fileRepository.delete(id);
        makeEvent(request, "Delete", uFile);
    }
}