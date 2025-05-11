package com.alex.spring.security6.app.filemanager.service;

import com.alex.spring.security6.app.filemanager.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

public interface FileEntityServiceInterface {

    void uploadFile(MultipartFile file);

    void deleteFile(Long id);

    void updateFile(MultipartFile file);

    FileEntity  findById(Long id);

    FileEntity findByFileName(String fileName);


}
