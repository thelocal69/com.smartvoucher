package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.ReplyCommentConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.ReplyCommentDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CommentEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.ReplyCommentEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICommentRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IReplyCommentRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.IReplyCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ReplyCommentService implements IReplyCommentService {

    private final IReplyCommentRepository replyCommentRepository;
    private final ReplyCommentConverter replyCommentConverter;
    private final ICommentRepository commentRepository;
    private final UserRepository userRepository;

    public ReplyCommentService( final IReplyCommentRepository replyCommentRepository,
                                final ReplyCommentConverter replyCommentConverter,
                                final ICommentRepository commentRepository,
                                final UserRepository userRepository) {
        this.replyCommentRepository = replyCommentRepository;
        this.replyCommentConverter = replyCommentConverter;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOutput getAllReplyComment(Long idComment, int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<ReplyCommentDTO> replyCommentDTOList = replyCommentConverter.toListCommentDTO(
                replyCommentRepository.findAllByIdCommentId(idComment, pageable)
        );
        if (replyCommentDTOList.isEmpty()){
            log.info("Reply comment list is empty !");
            throw new ObjectNotFoundException(404, "Reply comment list is empty !");
        }
        int totalItem = replyCommentRepository.countByIdComment(idComment);
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                replyCommentDTOList
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyCommentDTO> getAllReply() {
        List<ReplyCommentDTO> replyCommentDTOList = replyCommentConverter.toListCommentDTO(
                replyCommentRepository.findAll()
        );
        if (replyCommentDTOList.isEmpty()){
            log.info("Reply comment list is empty !");
            throw new ObjectNotFoundException(404, "Reply comment list is empty !");
        }
        return replyCommentDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReplyCommentDTO insertReplyComment(ReplyCommentDTO replyCommentDTO) {
        if (!existCommentAndUser(replyCommentDTO)){
            log.info("Comment or user is not exist !");
            throw new ObjectNotFoundException(404, "Comment or user is not exist !");
        }
        ReplyCommentEntity replyCommentEntity = replyCommentConverter.toCommentEntity(replyCommentDTO);
        CommentEntity commentEntity = commentRepository.findOneById(replyCommentDTO.getIdComment());
        UserEntity userEntity = userRepository.findOneById(replyCommentDTO.getIdUser());
        replyCommentEntity.setIdComment(commentEntity);
        replyCommentEntity.setIdUser(userEntity);
        log.info("Insert reply comment is completed !");
        return replyCommentConverter.toReplyCommentDTO(
                replyCommentRepository.save(replyCommentEntity)
        );
    }

    public Boolean existCommentAndUser(ReplyCommentDTO replyCommentDTO){
        boolean comment = commentRepository.existsById(replyCommentDTO.getIdComment());
        boolean user = commentRepository.existsById(replyCommentDTO.getIdUser());
        return comment && user;
    }
}
