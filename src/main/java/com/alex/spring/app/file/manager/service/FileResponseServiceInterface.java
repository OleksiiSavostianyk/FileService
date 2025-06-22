package com.alex.spring.app.file.manager.service;

import com.alex.spring.app.file.manager.dto.FileResponseDTO;

public interface FileResponseServiceInterface {

     FileResponseDTO getFileResponseDTO(long fileId);
}
