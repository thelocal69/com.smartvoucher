package com.smartvoucher.webEcommercesmartvoucher.controller;

import com.smartvoucher.webEcommercesmartvoucher.dto.ReplyCommentDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseObject;
import com.smartvoucher.webEcommercesmartvoucher.service.impl.ReplyCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reply")
public class ReplyCommentController {

    private final ReplyCommentService replyCommentService;

    public ReplyCommentController(final ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @GetMapping("/api/getAll")
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAllReply(){
        log.info("Get All Reply comment is complete !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Get All Reply comment is complete !",
                        this.replyCommentService.getAllReply()
                )
        );
    }

    @PostMapping("/api/insert")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> insertReply(
            @RequestBody ReplyCommentDTO replyCommentDTO
            ){
        log.info("Insert Reply comment is complete !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert Reply comment is complete !",
                        this.replyCommentService.insertReplyComment(replyCommentDTO)
                )
        );
    }

}
