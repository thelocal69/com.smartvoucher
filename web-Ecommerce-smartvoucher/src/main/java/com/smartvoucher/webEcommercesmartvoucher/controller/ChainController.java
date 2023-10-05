package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.dto.ChainDTO;
import com.smartvoucher.webEcommercesmartvoucher.service.IChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllChain() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        "Success",
                        "Get All chain success !",
                        this.chainService.getAllChain()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertChain(@RequestBody ChainDTO chainDTO) {
        List<ChainDTO> chainDTOList = chainService.getAllChainCode(chainDTO);
        if (!chainDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Chain code is duplicated !",
                            ""
                    )
            );
        } else {
            boolean existMerchantCode = chainService.existMerchantCode(chainDTO);
            return  (existMerchantCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject(
                                    "Success",
                                    "Insert is completed !",
                                    chainService.upsert(chainDTO)
                            )
            )
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ResponseObject(
                                    "Failed",
                                    "Merchant code not exist or empty !",
                                        ""
                )
            );
        }
    }

    @PutMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> updateChain(@RequestBody ChainDTO chainDTO, @PathVariable Long id) {
        chainDTO.setId(id);
        boolean exist = chainService.existChain(chainDTO);
        if (exist){
            boolean existMerchantCode = chainService.existMerchantCode(chainDTO);
            return  (existMerchantCode)
                    ? ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Update is completed !",
                            chainService.upsert(chainDTO)
                    )
            )
                    : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            "Failed",
                            "Merchant code not exist or empty !",
                            ""
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "Failed",
                            "Cannot update chain id = "+id,
                            ""
                    )
            );
        }
    }

    @DeleteMapping("/api/{id}")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> deleteChain(@RequestBody ChainDTO chainDTO, @PathVariable Long id){
        chainDTO.setId(id);
        if (this.chainService.deleteChain(chainDTO)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "Success",
                            "Delete is completed !",
                            "{}"
                    )
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "Failed",
                            "Cannot deleted chain id = "+id,
                            ""
                    )
            );
        }
    }

}
