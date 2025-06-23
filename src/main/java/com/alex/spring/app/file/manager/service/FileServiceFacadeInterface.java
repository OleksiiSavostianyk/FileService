package com.alex.spring.app.file.manager.service;

import com.alex.spring.app.file.manager.dto.FileResponseDTO;
import com.alex.spring.app.file.manager.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceFacadeInterface {
    void addFile(MultipartFile file);

    void deleteFile(long fileId);

    void updateFile(MultipartFile multipartFile);

    FileEntity findFileById(long fileId);


    FileEntity findByFileName(String fileName);









    FileResponseDTO getResponseFileDTO(long id);




}
