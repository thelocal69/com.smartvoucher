package com.smartvoucher.webEcommercesmartvoucher.Controller;

import com.smartvoucher.webEcommercesmartvoucher.DTO.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.Entity.SerialEntity;
import com.smartvoucher.webEcommercesmartvoucher.Payload.BaseResponse;
import com.smartvoucher.webEcommercesmartvoucher.Service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serial")
public class SerialController {

    private SerialService serialService;

    @Autowired
    public SerialController(SerialService serialService) {
        this.serialService = serialService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSerial() {

        List<SerialDTO> list = serialService.findAllSerial();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("OK");
        baseResponse.setMessage("List Serial");
        baseResponse.setData(list);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @PostMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> insertSerial(@RequestBody SerialEntity serialEntity) {

        boolean insertSerial = serialService.insertSerial(serialEntity);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("OK");
        baseResponse.setMessage( insertSerial == true ? "Add serial success!":"Add serial fail!");
        baseResponse.setData(insertSerial);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @PutMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> updateSerial(@RequestBody SerialEntity serialEntity) {

        boolean updateSerial = serialService.updateSerial(serialEntity);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("OK");
        baseResponse.setMessage( updateSerial == true ? "Update Serial Success!": "Update Serial Fail!");
        baseResponse.setData(updateSerial);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @DeleteMapping("")
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public ResponseEntity<?> deleteSerial(@RequestParam long id) {

        boolean deleteSerial = serialService.deleteSerial(id);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("OK");
        baseResponse.setMessage( deleteSerial == true ? "Delete Serial Success!": "Delete Serial Fail!");
        baseResponse.setData(deleteSerial);

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }
}
