package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IUploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("googleDrive")
public class GoogleDriveUploadController {
    private final IUploadFileService uploadFileService;

    @Autowired
    public GoogleDriveUploadController(final IUploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/api/getAll")
    public ResponseEntity<ResponseObject> getAllFiles(){
        log.info("Get all files is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all files is completed !",
                        uploadFileService.getAllGoogleDriveFiles()
                )
        );
    }

    @PostMapping ("/api/create")
    public ResponseEntity<ResponseObject> createdFolder(@RequestParam String folderName){
        log.info("Get all files is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all files is completed !",
                        uploadFileService.createdNewFolders(folderName)
                )
        );
    }

    @DeleteMapping("/api/delete/{fileId}")
    public ResponseEntity<ResponseObject> deleteFiles(@PathVariable String fileId){
        this.uploadFileService.deleteFiles(fileId);
        log.info("Get all files is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all files is completed !",
                        ""
                )
        );
    }
}
