package com.alex.spring.app.file.manager.repository;

import com.alex.spring.app.file.manager.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findById(Long id);
    Optional<FileEntity> findByFileName(String fileName);

}


