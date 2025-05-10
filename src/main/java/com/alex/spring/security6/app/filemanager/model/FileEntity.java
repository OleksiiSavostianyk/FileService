package com.alex.spring.security6.app.filemanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "Files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;


    @Column(name = "data", columnDefinition = "bytea")
    private byte[] fileData;

    public FileEntity(String fileName, String fileType, byte[] fileData) {

        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
    }


    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", uploadDate='" + uploadDate + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntity that = (FileEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(uploadDate, that.uploadDate) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(fileType, that.fileType) &&
                Objects.deepEquals(fileData, that.fileData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uploadDate, fileName, fileType, Arrays.hashCode(fileData));
    }
}
