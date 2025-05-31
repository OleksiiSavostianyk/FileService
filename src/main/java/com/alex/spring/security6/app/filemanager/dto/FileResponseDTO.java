package com.alex.spring.security6.app.filemanager.dto;


import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDTO {
    private String fileName;
    private String fileType;
    private int fileSize;
    private String uploadDate;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileResponseDTO that = (FileResponseDTO) o;
        return Objects.equals(fileName, that.fileName)
                && Objects.equals(fileType, that.fileType)
                && Objects.equals(fileSize, that.fileSize)
                && Objects.equals(uploadDate, that.uploadDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, fileType, fileSize, uploadDate);
    }
}
