package com.alex.spring.security6.app.filemanager.controller;

import com.alex.spring.security6.app.filemanager.model.FileEntity;
import com.alex.spring.security6.app.filemanager.service.FileServiceFacadeInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @Mock
    private FileServiceFacadeInterface fileServiceFacade;

    @InjectMocks
    private FileController fileController;



    @Test
    void testUploadFile_whenFileIsEmpty_thenBadRequest() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", new byte[0]);

        ResponseEntity<String> response = fileController.uploadFile(emptyFile);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("File is empty", response.getBody());
        verify(fileServiceFacade, never()).addFile(any());
    }

    @Test
    void testUploadFile_whenFileIsValid_thenCreated() {
        MockMultipartFile validFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello".getBytes());

        ResponseEntity<String> response = fileController.uploadFile(validFile);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("File uploaded", response.getBody());
        verify(fileServiceFacade, times(1)).addFile(validFile);
    }

    @Test
    void deleteFile_whenIdIsValid_thenReturnsOkStatus() {
        Long fileId = 1L;


        ResponseEntity<String> response = fileController.deleteFile(fileId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File deleted", response.getBody());
        verify(fileServiceFacade).deleteFile(fileId);
    }

    @Test
    void testUpdateFile_whenFileIsEmpty_thenBadRequest() {

        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", new byte[0]);


        ResponseEntity<String> response = fileController.updateFile(emptyFile);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("File is empty", response.getBody());
        verify(fileServiceFacade, never()).updateFile(any());
    }

    @Test
    void testUpdateFile_whenFileIsValid_thenOk() {

        MockMultipartFile validFile = new MockMultipartFile("file", "test.txt", "text/plain", "Updated content".getBytes());


        ResponseEntity<String> response = fileController.updateFile(validFile);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File updated", response.getBody());
        verify(fileServiceFacade, times(1)).updateFile(validFile);
    }


    @Test
    void testDownloadFileByName_shouldReturnFileWithCorrectHeaders() {

        String fileName = "test.txt";
        byte[] fileData = "Hello, world!".getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFileData(fileData);

        when(fileServiceFacade.findByFileName(fileName)).thenReturn(fileEntity);


        ResponseEntity<byte[]> response = fileController.downloadFileByName(fileName);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("attachment; filename=\"test.txt\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, response.getHeaders().getContentType());
        assertArrayEquals(fileData, response.getBody());

        verify(fileServiceFacade, times(1)).findByFileName(fileName);
    }

    @Test
    void testDownloadFileByName_whenFileNotFound_thenThrowException() {

        String fileName = "missing.txt";
        when(fileServiceFacade.findByFileName(fileName)).thenThrow(new RuntimeException("File not found"));


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileController.downloadFileByName(fileName);
        });

        assertEquals("File not found", exception.getMessage());
        verify(fileServiceFacade).findByFileName(fileName);
    }



    @Test
    void testDownloadFileByName_whenFileIsEmpty_thenReturnEmptyBody() {

        String fileName = "empty.txt";
        byte[] emptyData = new byte[0];

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFileData(emptyData);

        when(fileServiceFacade.findByFileName(fileName)).thenReturn(fileEntity);


        ResponseEntity<byte[]> response = fileController.downloadFileByName(fileName);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("attachment; filename=\"empty.txt\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertArrayEquals(emptyData, response.getBody());
    }


}