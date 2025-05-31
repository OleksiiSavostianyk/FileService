package com.alex.spring.security6.app.filemanager.service;

import com.alex.spring.security6.app.filemanager.exception.FileNotFoundException;
import com.alex.spring.security6.app.filemanager.exception.FileSaveException;
import com.alex.spring.security6.app.filemanager.model.FileEntity;
import com.alex.spring.security6.app.filemanager.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileEntityServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileEntityService fileEntityService;

    private FileEntityService spyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spyService = Mockito.spy(fileEntityService);
    }





    @Test
    void uploadFile_whenFileNotExists_thenSaveNewFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello".getBytes());

        when(fileRepository.findByFileName("test.txt")).thenReturn(Optional.empty());
        when(fileRepository.save(any(FileEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        fileEntityService.uploadFile(file);

        verify(fileRepository, times(1)).save(any(FileEntity.class));
    }

    @Test
    void uploadFile_whenFileExists_thenCallUpdateFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello".getBytes());

        FileEntity existingFile = new FileEntity("test.txt", "txt", "Old data".getBytes());
        when(fileRepository.findByFileName("test.txt")).thenReturn(Optional.of(existingFile));

        FileEntityService spyService = Mockito.spy(fileEntityService);
        doNothing().when(spyService).updateFile(file);

        spyService.uploadFile(file);

        verify(spyService, times(1)).updateFile(file);
        verify(fileRepository, never()).save(any());
    }

    @Test
    void uploadFile_whenIOExceptionThrown_thenThrowRuntimeException() throws IOException {

        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.txt");
        when(fileRepository.findByFileName("test.txt")).thenReturn(Optional.empty());
        when(file.getBytes()).thenThrow(new IOException("IO error"));
        when(fileRepository.findByFileName("test.txt")).thenReturn(Optional.empty());

        doThrow(new IOException("IO error")).when(file).getBytes();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileEntityService.uploadFile(file);
        });

        assertTrue(exception.getMessage().contains("Failed to save the file"));
    }

    @Test
    void deleteFile_shouldCallDeleteByIdOnce() {
        Long fileId = 1L;

        fileEntityService.deleteFile(fileId);

        verify(fileRepository, times(1)).deleteById(fileId);
    }

    @Test
    void updateFile_shouldUpdateFileEntityAndSave() throws IOException {
        String fileName = "test.txt";
        byte[] fileData = "Hello".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile("file", fileName, "text/plain", fileData);

        FileEntity existingEntity = new FileEntity();
        existingEntity.setFileName(fileName);
        existingEntity.setFileData("Old data".getBytes());


        doReturn(existingEntity).when(spyService).findByFileName(fileName);


        spyService.updateFile(multipartFile);


        assertArrayEquals(fileData, existingEntity.getFileData());
        verify(fileRepository, times(1)).save(existingEntity);
    }

    @Test
    void updateFile_whenIOException_thenThrowFileSaveException() throws IOException {
        String fileName = "test.txt";
        MockMultipartFile multipartFile = mock(MockMultipartFile.class);

        when(multipartFile.getOriginalFilename()).thenReturn(fileName);

        FileEntity existingEntity = new FileEntity();

        doReturn(existingEntity).when(spyService).findByFileName(fileName);
        when(multipartFile.getBytes()).thenThrow(new IOException("IO error"));

        FileSaveException exception = assertThrows(FileSaveException.class, () -> {
            spyService.updateFile(multipartFile);
        });

        assertTrue(exception.getMessage().contains("Failed to save the file"));
    }


    @Test
    void findById_whenFileExists_thenReturnFileEntity() {
        Long fileId = 1L;
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(fileId);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        FileEntity result = fileEntityService.findById(fileId);

        assertNotNull(result);
        assertEquals(fileId, result.getId());
    }

    @Test
    void findById_whenFileNotFound_thenThrowFileNotFoundException() {
        Long fileId = 1L;

        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        FileNotFoundException exception = assertThrows(FileNotFoundException.class, () -> {
            fileEntityService.findById(fileId);
        });

        assertTrue(exception.getMessage().contains("File not found with id: " + fileId));
    }

    @Test
    void findByFileName_whenFileExists_thenReturnFileEntity() {
        String fileName = "test.txt";
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);

        when(fileRepository.findByFileName(fileName)).thenReturn(Optional.of(fileEntity));

        FileEntity result = fileEntityService.findByFileName(fileName);

        assertNotNull(result);
        assertEquals(fileName, result.getFileName());
    }

    @Test
    void findByFileName_whenFileNotFound_thenThrowFileNotFoundException() {
        String fileName = "missing.txt";

        when(fileRepository.findByFileName(fileName)).thenReturn(Optional.empty());

        FileNotFoundException exception = assertThrows(FileNotFoundException.class, () -> {
            fileEntityService.findByFileName(fileName);
        });

        assertTrue(exception.getMessage().contains("File not found with name: " + fileName));
    }

}