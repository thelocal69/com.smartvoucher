package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

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
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All chain success !",
                        this.chainService.getAllChain()
                )
        );
    }

    @PostMapping ("/api/upload")
    public ResponseEntity<ResponseObject> uploadFiles(@RequestParam MultipartFile fileName){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Upload images is completed !",
                        chainService.uploadChainImages(fileName).getWebViewLink()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertChain(@Valid @RequestBody ChainDTO chainDTO) {
        chainDTO.setChainCode(
                UUID.randomUUID()
                        .toString()
                        .replace("-","")
                        .substring(0, 20)
        );
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
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete is completed !",
                            "{}"
                    )
            );
    }

}
