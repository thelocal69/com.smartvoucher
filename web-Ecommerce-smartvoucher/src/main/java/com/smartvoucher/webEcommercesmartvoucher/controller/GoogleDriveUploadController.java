package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all files is completed !",
                        uploadFileService.createdNewFolders(folderName)
                )
        );
    }

    @PostMapping ("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName, @RequestParam String folderId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all files is completed !",
                        uploadFileService.uploadGoogleDriveFiles(fileName, folderId)
                )
        );
    }

    @DeleteMapping("/api/delete/{fileId}")
    public ResponseEntity<ResponseObject> deleteFiles(@PathVariable String fileId){
        this.uploadFileService.deleteFiles(fileId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get all files is completed !",
                        ""
                )
        );
    }
}
