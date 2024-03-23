package com.smartvoucher.webEcommercesmartvoucher.service.impl;

import com.smartvoucher.webEcommercesmartvoucher.converter.CommentConverter;
import com.smartvoucher.webEcommercesmartvoucher.dto.CommentDTO;
import com.smartvoucher.webEcommercesmartvoucher.entity.CommentEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.UserEntity;
import com.smartvoucher.webEcommercesmartvoucher.entity.WareHouseEntity;
import com.smartvoucher.webEcommercesmartvoucher.exception.ObjectNotFoundException;
import com.smartvoucher.webEcommercesmartvoucher.payload.ResponseOutput;
import com.smartvoucher.webEcommercesmartvoucher.repository.ICommentRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.IWareHouseRepository;
import com.smartvoucher.webEcommercesmartvoucher.repository.UserRepository;
import com.smartvoucher.webEcommercesmartvoucher.service.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CommentService implements ICommentService {

    private final ICommentRepository commentRepository;
    private final IWareHouseRepository wareHouseRepository;
    private final UserRepository userRepository;
    private final CommentConverter commentConverter;

    @Autowired
    public CommentService(
            final ICommentRepository commentRepository,
            final CommentConverter commentConverter,
            final IWareHouseRepository wareHouseRepository,
            final UserRepository userRepository
    ) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
        this.wareHouseRepository = wareHouseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOutput getAllBuyWarehouse(Long idWarehouse, int page, int limit,String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<CommentDTO> commentDTOList = commentConverter.toListCommentDTO(
                commentRepository.findAllByIdWarehouseId(idWarehouse ,pageable)
        );
        if (commentDTOList.isEmpty()){
            log.info("Comment list is empty !");
            throw new ObjectNotFoundException(404, "Comment list is empty !");
        }
        int totalItem = commentRepository.countByIdWarehouse(idWarehouse);
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                commentDTOList
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentDTO insertComment(CommentDTO commentDTO) {
        if (!existWarehouseAndUser(commentDTO)){
            log.info("Warehouse or user is not exist !");
            throw new ObjectNotFoundException(404, "Warehouse or user is not exist !");
        }
        CommentEntity commentEntity = commentConverter.toCommentEntity(commentDTO);
        WareHouseEntity wareHouseEntity = wareHouseRepository.findOneById(commentDTO.getIdWarehouse());
        UserEntity userEntity = userRepository.findOneById(commentDTO.getIdUser());
        commentEntity.setIdUser(userEntity);
        commentEntity.setIdWarehouse(wareHouseEntity);
        log.info("Insert comment is completed !");
        return commentConverter.toCommentDTO(
                commentRepository.save(commentEntity)
        );
    }

    public Boolean existWarehouseAndUser(CommentDTO commentDTO) {
        boolean warehouse = wareHouseRepository.existsById(commentDTO.getIdWarehouse());
        boolean user = userRepository.existsById(commentDTO.getIdUser());
        return warehouse && user;
    }
}
