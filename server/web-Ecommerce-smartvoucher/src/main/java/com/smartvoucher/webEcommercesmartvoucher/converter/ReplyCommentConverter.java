package com.smartvoucher.webEcommercesmartvoucher.converter;

import com.smartvoucher.webEcommercesmartvoucher.dto.ReplyCommentDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.ReplyCommentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReplyCommentConverter {
    public ReplyCommentDTO toReplyCommentDTO(ReplyCommentEntity replyCommentEntity){
        ReplyCommentDTO replyCommentDTO = new ReplyCommentDTO();
        replyCommentDTO.setId(replyCommentEntity.getId());
        replyCommentDTO.setAvatarUrl(replyCommentEntity.getIdUser().getAvatarUrl());
        replyCommentDTO.setUserName(replyCommentEntity.getIdUser().getUsername());
        replyCommentDTO.setReplyComment(replyCommentEntity.getReplyComment());
        replyCommentDTO.setIdComment(replyCommentEntity.getIdComment().getId());
        replyCommentDTO.setIdUser(replyCommentEntity.getIdUser().getId());
        replyCommentDTO.setCreatedBy(replyCommentEntity.getCreatedBy());
        replyCommentDTO.setCreatedAt(replyCommentEntity.getCreatedAt());
        replyCommentDTO.setUpdatedBy(replyCommentEntity.getUpdateBy());
        replyCommentDTO.setUpdatedAt(replyCommentEntity.getUpdateAt());
        return replyCommentDTO;
    }

    public List<ReplyCommentDTO> toListCommentDTO(List<ReplyCommentEntity> replyCommentEntities){
        return replyCommentEntities.stream().map(this::toReplyCommentDTO).collect(Collectors.toList());
    }

    public ReplyCommentEntity toCommentEntity(ReplyCommentDTO replyCommentDTO){
        ReplyCommentEntity replyCommentEntity = new ReplyCommentEntity();
        replyCommentEntity.setReplyComment(replyCommentDTO.getReplyComment());
        replyCommentEntity.setCreatedBy(replyCommentDTO.getCreatedBy());
        replyCommentEntity.setCreatedAt(replyCommentDTO.getCreatedAt());
        replyCommentEntity.setUpdateBy(replyCommentDTO.getUpdatedBy());
        replyCommentEntity.setUpdateAt(replyCommentDTO.getUpdatedAt());
        return  replyCommentEntity;
    }
}
