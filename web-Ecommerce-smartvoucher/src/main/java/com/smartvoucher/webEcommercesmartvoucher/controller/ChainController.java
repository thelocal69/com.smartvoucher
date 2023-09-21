package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.baseResponse.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chain")
public class ChainController {

    private final IChainService chainService;

    @Autowired
    public ChainController(final IChainService chainService) {
        this.chainService = chainService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllChain(){
        return new ResponseEntity<>(chainService.getAllChain(), HttpStatus.OK);
    }

    @PostMapping("/api/insert")
    public ResponseEntity<ResponseObject> insertChain(@RequestBody ChainDTO chainDTO){
        List<ChainDTO> chainDTOList = chainService.getAllChainCode(chainDTO);
        if (!chainDTOList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Chain code is duplicated !",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Insert is completed !",
                            chainService.upsert(chainDTO)
                    )
            );
        }
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<ResponseObject> updateMerchant(@RequestBody ChainDTO chainDTO, @PathVariable Long id){
        chainDTO.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Update is completed !",
                            chainService.upsert(chainDTO)
                    )
            );
        }
}
