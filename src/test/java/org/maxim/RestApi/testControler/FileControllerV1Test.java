package org.maxim.RestApi.testControler;



import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maxim.RestApi.Controller.FileControllerV1;
import org.maxim.RestApi.DTO.FileDTO;
import org.maxim.RestApi.service.FileService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileControllerV1Test {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileControllerV1 fileController;

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
    public void testGetAllFiles() throws Exception {
        when(request.getRequestURI()).thenReturn("/files/");
        List<FileDTO> files = Collections.singletonList(new FileDTO());
        when(fileService.getAll()).thenReturn(files);

        fileController.doGet(request, response);


    }

    @Test
    public void testGetFileById() throws Exception {
        when(request.getRequestURI()).thenReturn("/files/1");
        FileDTO file = new FileDTO();
        file.setId(1);
        when(fileService.getFile(1)).thenReturn(file);

        fileController.doGet(request, response);


    }
}

