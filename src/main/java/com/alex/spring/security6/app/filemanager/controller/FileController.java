package com.alex.spring.security6.app.filemanager.controller;

import com.alex.spring.security6.app.filemanager.model.FileEntity;
import com.alex.spring.security6.app.filemanager.service.FileServiceFacadeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;




@RestController
@RequestMapping("/files")
public class FileController {

    private final FileServiceFacadeInterface fileServiceFacade;


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


    @GetMapping("/download/name/{name}")
    public ResponseEntity<byte[]> downloadFileByName(@PathVariable String name) {
        FileEntity file = fileServiceFacade.findByFileName(name);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file.getFileData());
    }







    }






