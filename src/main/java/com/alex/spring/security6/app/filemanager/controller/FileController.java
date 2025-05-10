package com.alex.spring.security6.app.filemanager.controller;

import com.alex.spring.security6.app.filemanager.service.FileServiceFacadeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;


@RestController
@RequestMapping("/files")
public class FileController {

    private FileServiceFacadeInterface fileServiceFacade;


   @Autowired
    public FileController(FileServiceFacadeInterface fileServiceFacade) {
        this.fileServiceFacade = fileServiceFacade;
    }





    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }


            fileServiceFacade.addFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded");
    }




    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id ) {

           fileServiceFacade.deleteFile(id);
           return ResponseEntity.status(HttpStatus.OK).body("File deleted");
    }



    @PutMapping("/update")
    public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file) {
       if (file.isEmpty()) {
           return ResponseEntity.badRequest().body("File is empty");
       }


           fileServiceFacade.updateFile(file);
           return ResponseEntity.status(HttpStatus.OK).body("File updated");

    }





}
