package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/chain")
public class ChainController {

    private final IChainService chainService;

    @Autowired
    public ChainController(final IChainService chainService) {
        this.chainService = chainService;
    }

    @GetMapping("")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllChain() {
        log.info("Get All chain success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All chain success !",
                        this.chainService.getAllChain()
                )
        );
    }

    @GetMapping("/api/getName")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllChainName() {
        log.info("Search All chain name success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Search All chain name success !",
                        this.chainService.getAllChainName()
                )
        );
    }

    @GetMapping("/{fileName}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getImageChain(
            @PathVariable String fileName
    ) {
        log.info("Get image chain success !");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(
                        this.chainService.readImageUrl(fileName)
                );
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseOutput> getAllChain(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String sortBy,
            @RequestParam String sortField
    ) {
        log.info("Get All chain success !");
        return new ResponseEntity<>(chainService.getAllChain(
                page, limit, sortBy, sortField), HttpStatus.OK);
    }

    @GetMapping("/api/search")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> searchAllChainByName(@RequestParam String name) {
        log.info("Get All chain success !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All chain success !",
                        this.chainService.searchAllChainName(name)
                )
        );
    }

    @PostMapping ("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        chainService.uploadChainImages(fileName)
                )
        );
    }

    @PostMapping ("/api/local_upload")
    public ResponseEntity<ResponseObject> uploadLocalFiles(@RequestParam MultipartFile fileName){
        log.info("Upload images is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        chainService.uploadLocalChainImages(fileName)
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertChain(@Valid @RequestBody ChainDTO chainDTO) {
        chainDTO.setChainCode("C-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0, 18)
        );
        log.info("Insert is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert is completed !",
                        chainService.upsert(chainDTO)
                )
        );
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateChain(@Valid @RequestBody ChainDTO chainDTO, @PathVariable Long id) {
        chainDTO.setId(id);
        log.info("Update is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        200,
                        "Update is completed !",
                        chainService.upsert(chainDTO)
                )
        );
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteChain(@RequestBody ChainDTO chainDTO, @PathVariable Long id){
        chainDTO.setId(id);
        this.chainService.deleteChain(chainDTO);
        log.info("Delete is completed !");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete is completed !",
                            "{}"
                    )
            );
    }

}
