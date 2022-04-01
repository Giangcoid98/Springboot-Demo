package com.example.springboot.controller;

import com.example.springboot.models.ResponseObject;
import com.example.springboot.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUpLoadController {
    //Inject Storage Service here
    @Autowired
    private IStorageService storageService;
    // this controller receive file/image from client
    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            //save files to a folder => use service
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully",generatedFileName)
                  //  838838a1ac4b4c56a7ae52485a86d0c8.jpg => lam sao de moi tren trinh duyet web
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok" , exception.getMessage() ,"")
            );


        }
    }
    // get image'url
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent((fileName));
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }
    // // get all files da upload
    @GetMapping("")
    public ResponseEntity<ResponseObject>getUploadedFiles(){
            try{
                List<String> urls = storageService.loadAll()
                        .map(path -> {
                            //convert fileName to url ( send request "readDetailFile")

                            String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUpLoadController.class,
                                    "readDetailFile",
                                    path.getFileName().toString()).build().toUri().toString();
                            return urlPath;
                        })
                        .collect(Collectors.toList());
                return ResponseEntity.ok(new ResponseObject("ok" , "List files successfully",urls));

    }
            catch (Exception exception){
                return  ResponseEntity.ok(new ResponseObject("failed" ,"List files" , new String[]{}));
            }

            }
}

