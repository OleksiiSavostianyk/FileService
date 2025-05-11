package com.alex.spring.security6.app.filemanager.service;

import com.alex.spring.security6.app.filemanager.dto.FileResponseDTO;
import com.alex.spring.security6.app.filemanager.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileServiceFacadeInterface {
    void addFile(MultipartFile file);

    void deleteFile(long fileId);

    void updateFile(MultipartFile multipartFile);

    FileEntity findFileById(long fileId);


    FileEntity findByFileName(String fileName);









    FileResponseDTO getResponseFileDTO(long id);




}
