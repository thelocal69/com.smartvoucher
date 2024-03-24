package com.smartvoucher.webEcommercesmartvoucher.service;

import com.smartvoucher.webEcommercesmartvoucher.dto.CommentDTO;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;

public interface ICommentService {
    ResponseOutput getAllBuyWarehouse(Long idWarehouse, int page, int limit, String sortBy, String sortField);
    CommentDTO insertComment(CommentDTO commentDTO);

}
