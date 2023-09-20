package com.smartvoucher.webEcommercesmartvoucher.Controller;

import com.smartvoucher.webEcommercesmartvoucher.DTO.SerialDTO;
import com.smartvoucher.webEcommercesmartvoucher.Service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> findAllSerial() {

        List<SerialDTO> list = serialService.findAllSerial();

        return new ResponseEntity(list ,HttpStatus.OK);
    }
}
