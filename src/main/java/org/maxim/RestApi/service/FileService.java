package org.maxim.RestApi.service;


import org.maxim.RestApi.DTO.Converter.EventConverter;
import org.maxim.RestApi.DTO.Converter.FileConverter;
import org.maxim.RestApi.DTO.FileDTO;
import org.maxim.RestApi.model.Event;
import org.maxim.RestApi.model.UFile;
import org.maxim.RestApi.model.User;
import org.maxim.RestApi.repository.FileRepository;
import org.maxim.RestApi.repository.hiber.HibernateFileRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {
    private final FileRepository fileRepository;
    private final EventService eventService;

    public FileService() {
        fileRepository = new HibernateFileRepositoryImpl();
        eventService = new EventService();
    }

    public FileDTO addFile(File file, HttpServletRequest request) {
        UFile uFile = new UFile();
        uFile.setName(file.getName());
        uFile.setFilePath(file.getPath());
        UFile created = fileRepository.create(uFile);
        Event event = makeEvent(request, "Upload", created);
        eventService.createEvent(EventConverter.toDTO(event));
        return FileConverter.toDTO(created);
    }

    public FileDTO getFile(Integer id) {
        UFile file = fileRepository.get(id);
        return FileConverter.toDTO(file);
    }

    public List<FileDTO> getAll() {
        return fileRepository.getAll().stream()
                .map(FileConverter::toDTO)
                .collect(Collectors.toList());
    }

    public FileDTO updateFile(FileDTO fileDTO, HttpServletRequest request) {
        UFile uFile = FileConverter.toEntity(fileDTO);
        UFile updatedFile = fileRepository.update(uFile);
        Event event = makeEvent(request, "Update", updatedFile);
        eventService.createEvent(EventConverter.toDTO(event));
        return FileConverter.toDTO(updatedFile);
    }

    public void deleteFile(Integer id, HttpServletRequest request) {
        UFile uFile = fileRepository.get(id);
        fileRepository.delete(id);
        Event event = makeEvent(request, "Delete", uFile);
        eventService.createEvent(EventConverter.toDTO(event));
    }

    private Event makeEvent(HttpServletRequest request, String nameEvent, UFile file) {
        String userIdHeader = request.getHeader("user-id");
        if (userIdHeader == null) {
            System.out.println("error in makeEvent");
        }

        Integer userId;
        try {
            userId = Integer.parseInt(userIdHeader);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(" error in Header 'user-id' ", e);
        }

        User user = new User();
        user.setId(userId);

        Event event = new Event();
        event.setName(nameEvent);
        event.setCreated(LocalDateTime.now());
        event.setUFile(file);
        event.setUser(user);

        return event;
    }
}



