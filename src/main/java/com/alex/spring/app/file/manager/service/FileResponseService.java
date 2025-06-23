package com.alex.spring.app.file.manager.service;

import com.alex.spring.app.file.manager.dto.FileResponseDTO;
import com.alex.spring.app.file.manager.exceptions.FileNotFoundException;
import com.alex.spring.app.file.manager.model.FileEntity;
import com.alex.spring.app.file.manager.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileResponseService implements FileResponseServiceInterface{

    private final FileRepository  fileRepository;

    @Autowired
    public FileResponseService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }







        @Override
        @Transactional(readOnly = true)
        public FileResponseDTO getFileResponseDTO(long fileId) {
            FileEntity file = fileRepository.findById(fileId)
                    .orElseThrow(() -> new FileNotFoundException("File not found with id: " + fileId + " "));

            FileResponseDTO fileResponseDTO = new FileResponseDTO();
            fileResponseDTO.setFileName(file.getFileName());
            fileResponseDTO.setFileSize(file.getFileData().length);
            fileResponseDTO.setFileType(file.getFileType());
            fileResponseDTO.setUploadDate(fileResponseDTO.getUploadDate());

            return fileResponseDTO;
        }

}
