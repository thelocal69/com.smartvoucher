package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.ReplyCommentDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

import java.util.List;

public interface IReplyCommentService {
    ResponseOutput getAllReplyComment(Long idComment, int page, int limit, String sortBy, String sortField);
    List<ReplyCommentDTO> getAllReply();
    ReplyCommentDTO insertReplyComment(ReplyCommentDTO replyCommentDTO);
}
