package com.alex.spring.security6.app.filemanager.service;

import com.alex.spring.security6.app.filemanager.dto.FileResponseDTO;
import com.alex.spring.security6.app.filemanager.exception.FileNotFoundException;
import com.alex.spring.security6.app.filemanager.model.FileEntity;
import com.alex.spring.security6.app.filemanager.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FileResponseServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileResponseService fileResponseService;

    @Test
    void getFileResponseDTO_whenFileExists_thenReturnDTO() {
        long fileId = 1L;
        byte[] fileData = "data".getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName("test.txt");
        fileEntity.setFileType("txt");
        fileEntity.setFileData(fileData);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));

        FileResponseDTO dto = fileResponseService.getFileResponseDTO(fileId);

        assertEquals("test.txt", dto.getFileName());
        assertEquals(fileData.length, dto.getFileSize());
        assertEquals("txt", dto.getFileType());

        assertNull(dto.getUploadDate());
    }

    @Test
    void getFileResponseDTO_whenFileNotFound_thenThrowException() {
        long fileId = 2L;

        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        FileNotFoundException ex = assertThrows(FileNotFoundException.class, () -> {
            fileResponseService.getFileResponseDTO(fileId);
        });

        assertTrue(ex.getMessage().contains("File not found with id: " + fileId));
    }
}