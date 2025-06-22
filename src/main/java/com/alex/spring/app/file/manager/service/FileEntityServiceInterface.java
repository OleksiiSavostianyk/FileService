package com.alex.spring.app.file.manager.service;

import com.alex.spring.app.file.manager.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileEntityServiceInterface {

    void uploadFile(MultipartFile file);

    void deleteFile(Long id);

    void updateFile(MultipartFile file);

    FileEntity  findById(Long id);

    FileEntity findByFileName(String fileName);


}
