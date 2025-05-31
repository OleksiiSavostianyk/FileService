package com.alex.spring.security6.app.filemanager.controller;

import com.alex.spring.security6.app.filemanager.exception.FileNotFoundException;
import com.alex.spring.security6.app.filemanager.exception.FileSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleNotFound() {
        FileNotFoundException ex = new FileNotFoundException("myfile.txt");
        ResponseEntity<String> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("File not found"));
        assertTrue(response.getBody().contains("myfile.txt"));
    }

    @Test
    void testHandleAll_FileSaveException() {
        FileSaveException ex = new FileSaveException("disk full");
        ResponseEntity<String> response = handler.handleAll(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("File save error"));
        assertTrue(response.getBody().contains("disk full"));
    }

    @Test
    void testHandleOtherExceptions() {
        Exception ex = new Exception("something bad");
        ResponseEntity<String> response = handler.handleOtherExceptions(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Unexpected error"));
        assertTrue(response.getBody().contains("something bad"));
    }
}