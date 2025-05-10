package com.alex.spring.security6.app.filemanager.service;


import com.alex.spring.security6.app.filemanager.dto.FileResponseDTO;
import com.alex.spring.security6.app.filemanager.model.FileEntity;
import com.alex.spring.security6.app.filemanager.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FileServiceFacade implements FileServiceFacadeInterface{

private final FileResponseServiceInterface fileResponseServiceInterface;
private final FileEntityServiceInterface fileEntityServiceInterface;

@Autowired
public FileServiceFacade(FileResponseServiceInterface fileResponseServiceInterface,
                         FileEntityServiceInterface fileEntityServiceInterface) {

    this.fileResponseServiceInterface = fileResponseServiceInterface;
    this.fileEntityServiceInterface = fileEntityServiceInterface;

}

@Override
public void addFile(MultipartFile file) {
    fileEntityServiceInterface.uploadFile(file);
}

@Override
public void deleteFile(long fileId) {
    fileEntityServiceInterface.deleteFile(fileId);
}

@Override
public void updateFile(MultipartFile multipartFile) {
    fileEntityServiceInterface.updateFile(multipartFile);
}

@Override
public Optional<FileEntity> findFileById(long fileId) {
  return   fileEntityServiceInterface.findById(fileId);
}











@Override
public FileResponseDTO getResponseFileDTO(long id) {
    return fileResponseServiceInterface.getFileResponseDTO(id);
}


}
