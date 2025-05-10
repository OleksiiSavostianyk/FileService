package com.alex.spring.security6.app.filemanager.service;

import com.alex.spring.security6.app.filemanager.dto.FileResponseDTO;

public interface FileResponseServiceInterface {

    public FileResponseDTO getFileResponseDTO(long fileId);
}
