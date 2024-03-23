package com.smartvoucher.webEcommercesmartvoucher.repository;

import com.smartvoucher.webEcommercesmartvoucher.entity.ReplyCommentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReplyCommentRepository extends JpaRepository<ReplyCommentEntity, Long> {
    List<ReplyCommentEntity> findAllByIdCommentId(Long id, Pageable pageable);
    List<ReplyCommentEntity> findAllByIdCommentId(Long id);
    @Query("SELECT count(*) FROM reply_comment r JOIN comment c ON r.idComment.id=c.id" +
            " WHERE c.id=:id")
    int countByIdComment(Long id);
}
