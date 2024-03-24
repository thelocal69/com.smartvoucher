package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.CommentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<CommentEntity, Long> {
    CommentEntity findOneByIdUser_Id(Long id);
    CommentEntity findOneById(Long id);
    List<CommentEntity> findAllByIdWarehouseId(Long id, Pageable pageable);
    @Query("SELECT count(*) FROM comment c JOIN warehouse w ON c.idWarehouse.id=w.id" +
            " WHERE w.id=:id")
    int countByIdWarehouse(Long id);
}
