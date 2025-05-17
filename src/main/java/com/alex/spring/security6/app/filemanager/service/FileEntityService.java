package com.alex.spring.security6.app.filemanager.service;

import com.alex.spring.security6.app.filemanager.dto.FileResponseDTO;
import com.alex.spring.security6.app.filemanager.exception.FileNotFoundException;
import com.alex.spring.security6.app.filemanager.exception.FileSaveException;
import com.alex.spring.security6.app.filemanager.model.FileEntity;
import com.alex.spring.security6.app.filemanager.repository.FileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FileEntityService implements FileEntityServiceInterface  {

    private final FileRepository fileRepository;

    @Autowired
    public FileEntityService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }




    @Override
    public void uploadFile(MultipartFile file) {

        Optional<FileEntity> fileEntityDB = fileRepository.findByFileName(file.getOriginalFilename());

        if (fileEntityDB.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String fileName = file.getOriginalFilename();
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);



                FileEntity fileEntity = new FileEntity(fileName, fileType, bytes);
                fileRepository.save(fileEntity);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save the file", e);
            }
        } else {
            updateFile(file);
        }
    }

    @Override
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);

    }

    @Override
    public void updateFile(MultipartFile file) {
        String name = file.getOriginalFilename();


        try {
            FileEntity fileEntity  = findByFileName(name);
            byte[] bytes = file.getBytes();
            fileEntity.setFileData(bytes);
            fileRepository.save(fileEntity);

        } catch (IOException e) {
            throw new FileSaveException("Failed to save the file:  " + e.getMessage() );
        }




    }

    @Override
    public FileEntity findById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found with id: " + id + " "));

    }

    @Override
    public FileEntity findByFileName(String fileName) {
        return fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new FileNotFoundException("File not found with name: " + fileName + " "));
    }






}
