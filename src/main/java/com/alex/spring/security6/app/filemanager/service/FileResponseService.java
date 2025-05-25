package com.alex.spring.security6.app.filemanager.service;

import com.alex.spring.security6.app.filemanager.dto.FileResponseDTO;
import com.alex.spring.security6.app.filemanager.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileResponseService implements FileResponseServiceInterface{

    private FileRepository fileRepository;

    @Autowired
    public FileResponseService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }




        @Override
        @Transactional(readOnly = true)
        public FileResponseDTO getFileResponseDTO(long fileId) {
            return null;
        }

}
