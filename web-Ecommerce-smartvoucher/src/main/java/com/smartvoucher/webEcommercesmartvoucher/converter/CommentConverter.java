package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.CommentDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CommentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentConverter {

    public CommentDTO toCommentDTO(CommentEntity commentEntity){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setAvatarUrl(commentEntity.getIdUser().getAvatarUrl());
        commentDTO.setUserName(commentEntity.getIdUser().getUsername());
        commentDTO.setCommentUser(commentEntity.getCommentUser());
        commentDTO.setIdWarehouse(commentEntity.getIdWarehouse().getId());
        commentDTO.setIdUser(commentEntity.getIdUser().getId());
        commentDTO.setCreatedBy(commentEntity.getCreatedBy());
        commentDTO.setCreatedAt(commentEntity.getCreatedAt());
        commentDTO.setUpdatedBy(commentEntity.getUpdateBy());
        commentDTO.setUpdatedAt(commentEntity.getUpdateAt());
        return commentDTO;
    }

    public List<CommentDTO> toListCommentDTO(List<CommentEntity> commentEntityList){
        return commentEntityList.stream().map(this::toCommentDTO).collect(Collectors.toList());
    }

    public CommentEntity toCommentEntity(CommentDTO commentDTO){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentUser(commentDTO.getCommentUser());
        commentEntity.setCreatedBy(commentDTO.getCreatedBy());
        commentEntity.setCreatedAt(commentDTO.getCreatedAt());
        commentEntity.setUpdateBy(commentDTO.getUpdatedBy());
        commentEntity.setUpdateAt(commentDTO.getUpdatedAt());
        return  commentEntity;
    }
}
