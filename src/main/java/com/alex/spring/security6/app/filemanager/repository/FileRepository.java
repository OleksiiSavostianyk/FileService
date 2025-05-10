package com.alex.spring.security6.app.filemanager.repository;

import com.alex.spring.security6.app.filemanager.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findById(Long aLong);
    Optional<FileEntity> findByFileName(String fileName);

}
